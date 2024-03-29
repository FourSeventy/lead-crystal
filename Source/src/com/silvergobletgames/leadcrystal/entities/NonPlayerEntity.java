package com.silvergobletgames.leadcrystal.entities;


import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.CombatData;
import com.silvergobletgames.leadcrystal.ai.AIState;
import com.silvergobletgames.leadcrystal.ai.Brain;
import com.silvergobletgames.leadcrystal.ai.BrainFactory;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectClasses;
import com.silvergobletgames.leadcrystal.combat.SoundPackFactory;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.netcode.*;
import java.io.Serializable;
import java.util.*;
import net.phys2d.raw.Body;
import com.silvergobletgames.leadcrystal.ai.BrainFactory.BrainID;
import com.silvergobletgames.leadcrystal.combat.SoundPack;
import com.silvergobletgames.leadcrystal.entities.EntityTooltip.EntityTooltipField;
import com.silvergobletgames.leadcrystal.scenes.GameScene;
import com.silvergobletgames.leadcrystal.skills.SkillManager;
import com.silvergobletgames.leadcrystal.combat.SoundPack.SoundPackID;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.CommonCrateAnimationPack;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.scripting.ScriptObject;
import com.silvergobletgames.leadcrystal.scripting.ScriptObject.ScriptTrigger;
import com.silvergobletgames.sylver.graphics.AnimationPack.CoreAnimations;
import com.silvergobletgames.sylver.graphics.AnimationPack.ImageAnimation;
import com.silvergobletgames.sylver.util.SylverVector2f;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Circle;


public class NonPlayerEntity extends CombatEntity implements SavableSceneObject 
{
    
    // The logical component of this entity, contains behavioral logic and functionality 
    protected Brain brain;
    //some brain related variables
    protected float locateDistance = 500;
    protected float wanderDistance = 400;   
    
    //the location thta this NPE was placed at, or spawned at (used for wandering patrolling etc)
    public SylverVector2f placedLocation;
    
    private long stuckInEnemyCount = 0;

    
    //==================
    // Constructors
    //==================

    /**
     * Builds a rudimentary Non Player Entity with the basic things that it needs.
     * Additional components are added after the fact.
     * @param spriteRef
     * @param animated
     * @param body 
     */
    public NonPlayerEntity(Image image, Body b)
    {
        super(image, b);    
        
        //entity tooltip
        this.entityTooltip = new EntityTooltip(EntityTooltipField.NAME,EntityTooltipField.HEALTH);
        
        //add to NPE group
        this.addToGroup(ExtendedSceneObjectGroups.NPE);

        //A blank brain.
        setBrain(new Brain());
        
        //body settings
        if(!(this.getImage().getAnimationPack() instanceof AnimationPackClasses.CrateInterface))
        {
          body.setRotatable(false);
        }
        body.setDamping(.05f);

    }

    
    //==============
    // Class Methods
    //==============

    /**
     * Calls functionality related to death.
     */
    public void die() 
    {
        //activate script
        if(this.getScriptObject() != null && this.getScriptObject().getTrigger() == ScriptTrigger.DEATH)
        {
            this.scriptObject.runScript(this);
        }
        
        brain.getStateMachine().changeState(AIState.StateID.DEAD);
        
        //super die
        super.die();
        
        
        
        //set tooltip to dead
        this.entityTooltip.specialDoNotDisplay = true;      
        
        //set friction
        this.body.setFriction(10);
        
        //clear arbiters
        if(this.owningScene instanceof GameScene)
        {
            ((GameScene)this.owningScene).clearArbitersOnBody(body);
        }       
        //set masks
        body.setBitmask(Entity.BitMasks.COLLIDE_WORLD.value);
        body.setOverlapMask(Entity.OverlapMasks.NO_OVERLAP.value);
        this.removeFromGroup(ExtendedSceneObjectGroups.NPE);
        
        
       
    }
    
    /**
     * Updates the current state of affairs for this NPE.
     */
    public void update()
    {
        super.update();
        
        if (!combatData.isDead()) 
        {
                     
            //update brain if we are near a player
            PlayerEntity p = this.brain.getPlayer();
            if(p != null && this.distanceAbs(p) <= 2000)
            {
                brain.update();
                
                //if we are stuck in said player, self destruct
                if(this.distanceAbs(p) <= 47 && this.getBody().getBitmask() == Entity.BitMasks.NPE.value && this.getBody().getOverlapMask() ==Entity.OverlapMasks.NPE.value && !p.dashing)
                {
                    this.stuckInEnemyCount++;
                    
                    if(this.stuckInEnemyCount == 10)
                    {
                      this.die();
                    }
                }
                else
                {
                    this.stuckInEnemyCount = 0;
                }

            }
            
            

            //some entity tooltip settings
            if(this.combatData.getPercentHealth() != 1 )
                entityTooltip.specialDisplay = true;
            else
                entityTooltip.specialDisplay = false;

            entityTooltip.setHealthField(combatData.getPercentHealth());

            //animation handling
            if(this.image.getAnimation() != ExtendedImageAnimations.MELEEATTACK && this.image.getAnimation() != ExtendedImageAnimations.RANGEDATTACK && this.image.getAnimation() != ExtendedImageAnimations.SPELLATTACK && this.image.getAnimation() != ExtendedImageAnimations.SPAWN && !(this.image.getAnimationPack() instanceof AnimationPackClasses.CrateInterface))
            {
                if (Math.abs(body.getVelocity().getX()) < 2)  
                { 
                    image.setAnimation(CoreAnimations.IDLE); 
                }
            }
        } 
    }
    
    public void finishedAnimating(Image image,ImageAnimation animation)
    {
        super.finishedAnimating(image,animation);
        
        if(animation == ExtendedImageAnimations.SPAWN)
        {
            this.getImage().setAnimation(CoreAnimations.IDLE);
            this.getBrain().getStateMachine().changeState(AIState.StateID.AGGRESSIVE);
        }
    }

    /**
     * Respond to damage taken.
     * @param d p
     */
    public void takeDamage(Damage d)
    {      
        //if we dont have an owning scene get out of here
        if(this.getOwningScene() == null)
        {
            return;
        }

        super.takeDamage(d);
        
        brain.damageTaken(d);
    } 

    @Override
    public void addedToScene()
    {
        super.addedToScene();
        
        
        this.placedLocation = new SylverVector2f(this.getPosition());
    }
    
    public void collidedWith(Entity other, CollisionEvent event)
    { 
        super.collidedWith(other, event);
        
        //if we landed on the top of a worldObjectEntity
        if(other instanceof WorldObjectEntity)
        {
            if(-event.getNormal().getY() > .75)
            {
                //refill jump energy
                this.feetOnTheGround = true;
            }
        }
    
    }
    
    //=================
    // Accessor Methods
    //=================
    
    /**
     * Gives this NPE a brain. (inb4 if I only had a brain)
     * @param b 
     */
    public final void setBrain(Brain b) 
    {
        //Setup the new brain
        this.brain = b;
        brain.setOwner(this);
    }
    
    /**
     * Returns my brain.
     */
    public final Brain getBrain() 
    {
        return brain;
    }

    public float getLocateDistance()
    {
        return locateDistance;
    }
    
    public void setLocateDistance(float distance)
    {
        this.locateDistance = distance;
    }
    
    public float getWanderDistance()
    {
        return this.wanderDistance;
    }
    
    public void setWanderDistance(float distance)
    {
        this.wanderDistance = distance;
    }
    
    public void jump()
    {
       this.jump(0,16_500);
    }
    
    public void jump(int x, int y)
    {
         if (combatData.canMove() && this.feetOnTheGround)
        { 
            this.getBody().addForce(new Vector2f(x, y));
            this.feetOnTheGround = false;
        }
    }
    

    
    //===========================
    // Saving Functions
    //=========================== 
    

    public SceneObjectSaveData dumpFullData() 
    {       
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
        
        com.silvergobletgames.sylver.netcode.SceneObjectSaveData saved = new com.silvergobletgames.sylver.netcode.SceneObjectSaveData(ExtendedSceneObjectClasses.NONPLAYERENTITY,this.ID);
       
        saved.dataMap.put("image", this.image.dumpFullData());
        saved.dataMap.put("body", Entity.dumpBodySavable(this.body));
        saved.dataMap.put("brain", this.brain.getID().name());
        saved.dataMap.put("combatData", this.combatData.dumpFullData());
        saved.dataMap.put("scriptObject", this.scriptObject != null ? this.scriptObject.dumpFullData() : null);
        saved.dataMap.put("skillManager", this.skillManager.dumpFullData());
        saved.dataMap.put("name", this.getName());
        saved.dataMap.put("soundPack", this.soundPack.getID().name());
        saved.dataMap.put("imageOffsetX",this.imageOffset.x);
        saved.dataMap.put("imageOffsetY",this.imageOffset.y);
        saved.dataMap.put("locateDistance",this.locateDistance);
        saved.dataMap.put("wanderDistance",this.wanderDistance);

        return saved;
    }

   
    public static NonPlayerEntity buildFromFullData(SceneObjectSaveData saveData) 
    {
        
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
        
        Image myImage = (Image) Image.buildFromFullData((com.silvergobletgames.sylver.netcode.SceneObjectSaveData) saveData.dataMap.get("image"));

        String brainName = (String) saveData.dataMap.get("brain");
        BrainID brainId = BrainID.valueOf(brainName);
        com.silvergobletgames.sylver.netcode.SaveData scriptData = (com.silvergobletgames.sylver.netcode.SaveData) saveData.dataMap.get("scriptObject");
        //Kept serializable so I can just check if it's null below.
        String name = (String) saveData.dataMap.get("name");
        
        SoundPackID soundID = null;
        if(saveData.dataMap.get("soundPack") != null)
            soundID = SoundPackID.valueOf((String)saveData.dataMap.get("soundPack"));

        //Build Body
        Body myBody = Entity.buildBodyFromSavedData((com.silvergobletgames.sylver.netcode.SaveData) saveData.dataMap.get("body"));
        float x = myBody.getPosition().getX();
        float y = myBody.getPosition().getY();
        
        //construct npe
        NonPlayerEntity newNpe = new NonPlayerEntity(myImage, myBody);

        //Script object
        if (scriptData != null) {
            ScriptObject sObj = ScriptObject.buildFromFullData(scriptData);
            newNpe.setScriptObject(sObj);
        }

        //Build and set brain
        Brain brain = BrainFactory.getInstance().getBrain(brainId);
        newNpe.setBrain(brain);
        
        //build skills
        SkillManager skillManager = SkillManager.buildFromFullData((com.silvergobletgames.sylver.netcode.SaveData)saveData.dataMap.get("skillManager"));
        skillManager.setOwner(newNpe);
        newNpe.setSkillManager(skillManager);

        //Combat Data
        CombatData combatData = CombatData.buildFromFullData((com.silvergobletgames.sylver.netcode.SaveData)saveData.dataMap.get("combatData"));
        combatData.setOwner(newNpe);
        newNpe.setCombatData(combatData);
        
        combatData.critChance.setBase(.05f);
        combatData.critModifier.setBase(.50f);
        
        //Sound Pack       
        if(soundID != null)
        {
            SoundPack soundPack = SoundPackFactory.getInstance().getSoundPack(soundID);
            newNpe.setSoundPack(soundPack);
        }

        //set ID and name
        newNpe.ID = (String)saveData.dataMap.get("id");
        newNpe.setName(name); 
        
        newNpe.entityTooltip.setHealthField(combatData.getPercentHealth());
        
        Float imageOffsetX = (Float)saveData.dataMap.get("imageOffsetX");
        Float imageOffsetY = (Float)saveData.dataMap.get("imageOffsetY");
        
        if(imageOffsetX != null)       
            newNpe.imageOffset.set(imageOffsetX, imageOffsetY);
        else
            newNpe.imageOffset.set(0, 0);
        
        //locate and wander distance
        if(saveData.dataMap.containsKey("locateDistance"))
        {
            newNpe.locateDistance = (float)saveData.dataMap.get("locateDistance");
        }
        if(saveData.dataMap.containsKey("wanderDistance"))
        {
            newNpe.wanderDistance = (float)saveData.dataMap.get("wanderDistance");
        }
        
            
        
        //set position
        newNpe.setPosition(x, y);

        return newNpe;
    }
    
   
}
