package com.silvergobletgames.leadcrystal.entities;

import com.silvergobletgames.leadcrystal.items.ArmorManager;
import com.silvergobletgames.leadcrystal.items.Potion;
import com.silvergobletgames.leadcrystal.items.Currency;
import com.silvergobletgames.leadcrystal.items.CurrencyManager;
import com.silvergobletgames.leadcrystal.combat.CombatData;
import com.silvergobletgames.leadcrystal.combat.LevelProgressionManager;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.LightSource;
import com.silvergobletgames.sylver.netcode.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Circle;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.leadcrystal.combat.CombatData.CombatState;
import com.silvergobletgames.leadcrystal.combat.CombatEffect;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.ProcEffect;
import com.silvergobletgames.leadcrystal.core.*;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBlackFrontArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBrownBackArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBrownFrontArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.RocketExplosionEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.SandSpurtEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.SmokeEmitter;
import com.silvergobletgames.leadcrystal.entities.EntityTooltip.EntityTooltipField;
import com.silvergobletgames.leadcrystal.items.*;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorID;
import com.silvergobletgames.leadcrystal.netcode.ClientInputPacket;
import com.silvergobletgames.leadcrystal.netcode.PlayerPredictionData;
import com.silvergobletgames.leadcrystal.scripting.PageCondition;
import com.silvergobletgames.leadcrystal.scripting.ScriptObject;
import com.silvergobletgames.leadcrystal.scripting.ScriptPage;
import com.silvergobletgames.leadcrystal.skills.Skill;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;
import com.silvergobletgames.leadcrystal.skills.SkillManager;
import com.silvergobletgames.sylver.core.InputSnapshot;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.AnimationPack.CoreAnimations;
import com.silvergobletgames.sylver.graphics.AnimationPack.ImageAnimation;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.awt.Point;
import java.security.InvalidParameterException;
import java.util.HashMap;
import javax.media.opengl.GL2;
import net.phys2d.math.ROVector2f;
import net.phys2d.raw.*;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Line;
import net.phys2d.raw.shapes.Polygon;

public class PlayerEntity extends CombatEntity implements SavableSceneObject
{
    //currency manager
    protected CurrencyManager currencyManager;
    //armor system manager
    protected ArmorManager armorManager;
    //Players potions
    protected PotionManager potionManager;
    //level progression manager
    protected LevelProgressionManager levelProgressionManager; 
    
    //body part images
    protected Image head;
    protected Image frontArm;
    protected Image backArm;
     
    //body part positioning maps
    protected HashMap<ImageAnimation,ArrayList<Vector2f>> bodyPartOffsets = new HashMap<>();
        
    //skill assignments
    protected SkillID skill1 = SkillID.PlayerLaser;
    protected SkillID skill2 = SkillID.PlayerBashAttack;
    protected SkillID skill3;
    protected SkillID skill4;
    
    //skill release point
    protected SylverVector2f skillReleasePoint = new SylverVector2f(0,0);
    protected SylverVector2f worldMousePoint = new SylverVector2f(0,0);
    
    //jumping variables
    protected int inAirTimer = 0;
    protected final float MAX_JUMP_ENERGY = 125;
    protected float jumpEnergy = MAX_JUMP_ENERGY;
    protected boolean jumpReleased = true;
    protected boolean waitingToResetEnergy = false;
    //double jump
    protected boolean doubleJumpAvailable = false;
    protected float doubleJumpEnergy = MAX_JUMP_ENERGY - 20;
    
    //movement variables    
    protected final Vector2f BASE_PLAYER_VELOCITY = new Vector2f(50,100);
    protected final float BASE_DAMPING = .1f; 
    protected final float BASE_FRICTION = .8f; 
    private boolean sprinting = false;
    public boolean dashing = false;
    protected SylverVector2f dashVector;
    protected int dashTicks = 0;
    
    
    //status variables
    public boolean touchingLadder = false;
    public boolean onLadder = false;
    public boolean respawnWhenEnterTown = false;
    private RespawnGravestone respawnGravestone;
    
  
    
    
    //=====================
    // Constructors
    //=====================
    
    /**
     * Main constructor for the player
     */
    public PlayerEntity(Image bodyImage, Image head, Image backArm, Image frontArm)
    {
        //Call the superconstructor with appropriate data
        super(bodyImage, new Body(new Polygon(PlayerEntity.getBodyVertices()), 10));        
        
        
        //front arm
        this.frontArm = frontArm;
        this.frontArm.addAnimationListener(this);
         
        //back arm
        this.backArm = backArm;
        this.backArm.addAnimationListener(this);
        
        //head
        this.head = head;
            
        //ID
        this.ID = "Player";
        
        //set image dimensions and offset
        image.setScale(1f);
        this.imageOffset = new Vector2f(0,-20);

        //set body attributes
        body.setFriction(this.BASE_FRICTION);
        body.setDamping(this.BASE_DAMPING);    
        body.setBitmask(BitMasks.PLAYER.value);     
        body.setOverlapMask(OverlapMasks.PLAYER.value);
        body.setRestitution(0);
        body.setRotatable(false);
        
        
        //create potion manager
        potionManager = new PotionManager(this);      
        
        //currency manager
        currencyManager = new CurrencyManager();    
        
        //create armor manager
        armorManager = new ArmorManager();
        armorManager.setPlayerReference(this);
        
        //progression manager
        levelProgressionManager = new LevelProgressionManager(this);

        //create players base combat data
        combatData = new CombatData(this);
        combatData.maxHealth.setBase(100);
        combatData.baseDamage.setBase(10);
        combatData.critChance.setBase(.05f);
        combatData.critModifier.setBase(.50f);
        combatData.xVelocity.setBase(this.BASE_PLAYER_VELOCITY.x);
        combatData.yVelocity.setBase(this.BASE_PLAYER_VELOCITY.y);
        combatData.fullHeal();      
               
        //give the player skills
        skillManager.learnSkill(SkillID.PlayerLaser);
        skillManager.learnSkill(SkillID.PlayerBashAttack);

        //flashlight
        this.setLight(new LightSource());
        this.light.setColor(new Color(.9f,.9f,1f));
        this.light.setSize(600);
        this.light.setConicalRadius(40);
        this.light.setIntensity(0.40f);
        this.light.turnOff();
        
        //entity tooltip
        this.entityTooltip = new EntityTooltip(EntityTooltipField.NAME,EntityTooltipField.HEALTH);
        
        //set group
        this.addToGroup(ExtendedSceneObjectGroups.PLAYER);
        
        //initialize body position maps
        initializeBodyOffsetMap();
               
    }

    
    //=====================
    // Accessor Methods
    //=====================
    
    public PotionManager getPotionManager()
    {
        return this.potionManager;
    }
    
    public CurrencyManager getCurrencyManager()
    {
        return this.currencyManager;
    }
    
    public ArmorManager getArmorManager()
    {
        return this.armorManager;
    }
    
    public LevelProgressionManager getLevelProgressionManager()
    {
        return this.levelProgressionManager;
    }
    
     public Image getFrontArm()
    {
        return frontArm;
    }

    public Image getBackArm()
    {
        return backArm;
    }

    public Image getHead()
    {
        return head;
    }

    
    //=====================
    // Class Methods
    //=====================
     
    public void update()
    {
        //Update the superclass
        super.update();
       
        //position flashlight
        this.light.setPosition((int) this.body.getPosition().getX() + 20, (int) this.body.getPosition().getY());
        //angle flashlight
        if(this.owningScene instanceof GameServerScene && ((GameServerScene)owningScene).clientsInScene.get(UUID.fromString(this.ID)).currentInputPacket != null)
        {
            ClientInputPacket input = ((GameServerScene)owningScene).clientsInScene.get(UUID.fromString(this.ID)).currentInputPacket;
            this.light.faceTowardsPoint(new Point(input.mouseLocationX,input.mouseLocationY));
        }
        
        //check if in air
        if(this.body.getTouching().size() == 0)         
            this.feetOnTheGround = false;            
        
         
        //if our feet arent on the ground increment in air timer
        if(!this.feetOnTheGround)
            this.inAirTimer++;
        
        //clear jump energy if we are in air for too long
        if(this.inAirTimer > 15 && this.jumpEnergy == this.MAX_JUMP_ENERGY)
            this.jumpEnergy = 0;
           
    
         //entity tooltip
         entityTooltip.setHealthField(combatData.percentHealth());        
         
         //update ladder settings
         if(this.onLadder)
         {
             this.body.setGravityEffected(false);
             this.combatData.yVelocity.setBase(this.BASE_PLAYER_VELOCITY.x); 
             this.body.setDamping(2);
         }
         else
         {
             this.body.setGravityEffected(true);
             this.combatData.yVelocity.setBase(this.BASE_PLAYER_VELOCITY.y); 
             this.body.setDamping(this.BASE_DAMPING);
         }
         
         
         
        //===============================
        // Calculate Skill Release Point
        //===============================
         
         //determine flipped
        boolean flipped;
        if(this.worldMousePoint.x < this.getPosition().x)
             flipped = true;
        else
            flipped = false;
        
        //Get user X and Y
        float userX; 
        float userY; 
        
        if(!flipped)
        {
            userX = this.getPosition().x-16;
            userY = this.getPosition().y  + 6;
        }
        else
        {
            userX = this.getPosition().x+16;
            userY = this.getPosition().y  + 6;
        }
              
        //get vector to target
        Vector2f vectorToTarget = new Vector2f(this.worldMousePoint.x - userX, this.worldMousePoint.y - userY);
        vectorToTarget.normalise();
        
        float theta = (float)Math.acos(vectorToTarget.dot(new Vector2f(1,0)));
        if(this.worldMousePoint.y < userY)
            theta = (float)(2* Math.PI - theta);
        
        theta = (float)Math.toDegrees(theta);
        if(theta >= 60 && theta <= 90)
        {
            //set vector to 60
            vectorToTarget = new Vector2f((float)Math.cos(Math.toRadians(60)),(float)Math.sin(Math.toRadians(60)));
        }
        else if(theta > 90 && theta <= 120)
        {
            //set vector to 120
            vectorToTarget = new Vector2f((float)Math.cos(Math.toRadians(120)),(float)Math.sin(Math.toRadians(120)));
        }
        else if(theta >= 240 && theta < 270)
        {
            //set to 240
            vectorToTarget = new Vector2f((float)Math.cos(Math.toRadians(240)),(float)Math.sin(Math.toRadians(240)));
        }
        else if(theta >= 270 && theta <= 300)
        {
            //set to 300
            vectorToTarget = new Vector2f((float)Math.cos(Math.toRadians(300)),(float)Math.sin(Math.toRadians(300)));
        }
        
        vectorToTarget.scale(this.getFrontArm().getWidth()* .5f) ;
        vectorToTarget.add(new Vector2f(userX,userY)); 
        this.skillReleasePoint = new SylverVector2f(vectorToTarget.x,vectorToTarget.y);
        
        //update body parts
        this.frontArm.update();
        this.backArm.update();
        this.head.update();
        
        //set correct animation for this frame
        this.setCorrectAnimation();
                
    }   
    
    public void draw(GL2 gl)
    {
        this.updateBodyParts();
        
        //if we are dead hide body
        if(this.combatData.isDead())
        {
             //hide body
            this.getImage().setAlphaBrightness(0);
            this.head.setAlphaBrightness(0);
            this.frontArm.setAlphaBrightness(0);
            this.backArm.setAlphaBrightness(0);
        }
        else
        {
             //show body
            this.getImage().setAlphaBrightness(1);
            this.head.setAlphaBrightness(1);
            this.frontArm.setAlphaBrightness(1);
            this.backArm.setAlphaBrightness(1);
        }
                   
        this.backArm.draw(gl);
        
        super.draw(gl);
        
        this.head.draw(gl);
        this.frontArm.draw(gl);
        
    } 
    
    /**
     * Notification that this entity collided with another.
     * sends the object that was collided, and the collision event.
     *
     * @param other The entity with which this entity collided.
     */
    public void collidedWith(Entity other, CollisionEvent event)
    {        
        super.collidedWith(other, event);

                 
        //if we collided with currency, collect it and remove it from the world
        if (other instanceof Currency)
        {
            
            
            //add to currency manager
            this.currencyManager.addCurrency(((Currency)other).getAmount());

            //add currency text
            Text currencyText = new Text("+" + Integer.toString(((Currency)other).getAmount()), LeadCrystalTextType.COMBAT);
            currencyText.setColor(new Color(Color.blue));
            currencyText.setPosition(other.getPosition().x + SylverRandom.random.nextInt(10), other.getPosition().y + 100);
            currencyText.addTextEffect(new TextEffect(TextEffect.TextEffectType.YTRANSLATE, 120, other.getPosition().y, other.getPosition().y + 250));
            currencyText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 120, 0, 0));
            TextEffect fadeEffect = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(Color.blue,1), new Color(Color.blue,0));
            fadeEffect.setDelay(90);
            currencyText.addTextEffect(fadeEffect);
            this.getOwningScene().add(currencyText, Scene.Layer.MAIN);


            //remove the item from the world
            other.removeFromOwningScene();
        }
        
        //if we collided with a potion add it to the potion manager
        if(other instanceof Potion)
        {
            //add the potion
            this.potionManager.addPotion(1);
            
            //remove the item from the world
            other.removeFromOwningScene();
        }
                      
        
            
        //if we collided with the ground
        if (other instanceof WorldObjectEntity) 
        {                                
            
            //if we landed on the top of a worldObjectEntity
            if(-event.getNormal().getY() > .75)
            {
                //play sound
                if(this.jumpEnergy != MAX_JUMP_ENERGY)
                {                                   
                    if(this.getOwningScene() instanceof GameClientScene)
                    {
                        Sound sound = Sound.locationSound("buffered/bodyFall.ogg", this.getPosition().x, this.getPosition().y, false,.95f);
                        this.getOwningScene().add(sound);
                    }
                }
                
                //set flags
                this.feetOnTheGround = true;
                this.inAirTimer = 0;
                
                //reset jump energy
                if(this.jumpReleased == true)              
                    this.jumpEnergy = MAX_JUMP_ENERGY;                                 
                else
                    this.waitingToResetEnergy = true;
                
                //double jump things
                this.doubleJumpEnergy = MAX_JUMP_ENERGY - 20;
                this.doubleJumpAvailable = false;
            }
            
            //if we collided with a ladder
            if(((WorldObjectEntity)other).isLadder())
            {
                this.touchingLadder = true;
            }          
            
            
        }
        
        //if we collided with an enemy
        if (other instanceof NonPlayerEntity) 
        {   
            //if we landed on the top of an enemy
            if(-event.getNormal().getY() > .75)
            {
                //set flags
                this.feetOnTheGround = true;
                this.inAirTimer = 0;
                
                //reset jump energy
                if(this.jumpReleased == true)
                    this.jumpEnergy = MAX_JUMP_ENERGY;
                else
                    this.waitingToResetEnergy = true;
                
                //double jump things
                this.doubleJumpEnergy = MAX_JUMP_ENERGY-20;
                this.doubleJumpAvailable = false;
            }
            
        }
               
    }
    
    /**
     * Notification that this entity collided with another. 
     * Sends the object that was collided and the CollisionEvent
     * @param other
     * @param event 
     */
    public void separatedFrom(Entity other, CollisionEvent event)
    {
        super.separatedFrom(other, event);
        
        if(other instanceof WorldObjectEntity)
        {
            
            //if we seperated from a ladder
            if(((WorldObjectEntity)other).isLadder())
            {
                this.touchingLadder = false;
                this.onLadder = false;
            }
            
        }
        
         
    }

    /**
     * Notification that the current animation has finished.
     *
     * @param other The animation set that just finished
     */
    public void finishedAnimating(Image image,ImageAnimation action) 
    {

        if(action == ExtendedImageAnimations.MELEEATTACK || action == ExtendedImageAnimations.RANGEDATTACK || action == ExtendedImageAnimations.SPELLATTACK)
        {            
            this.backArm.setAnimation(CoreAnimations.IDLE);  
            this.frontArm.setAnimation(CoreAnimations.IDLE);   
        }

    }
    
    /**
     * EUUUGUHH
     */
    public void die() 
    {       
        //clear arbiters
        if(this.owningScene instanceof GameServerScene)
           ((GameServerScene) owningScene).physicsWorld.clearArbiters(this.body);
        else if(this.owningScene instanceof GameClientScene)
           ((GameClientScene) owningScene).physicsWorld.clearArbiters(this.body);
        
        //Essential STATUS flag when dead
        this.combatData.setState(CombatState.DEAD);
                
        //Remove all effects
        this.combatData.removeAllCombatEffects();
        this.getImage().removeAllImageEffects();

        //Stop casting
        this.interruptAttacking();
      
        //turn off light
        this.light.turnOff();
        
        //combat state
        this.combatData.setState(CombatState.IMMOBILIZE);
        this.combatData.setState(CombatState.STUN);
        
        //body 
        this.body.setBitmask(Entity.BitMasks.COLLIDE_WORLD.value);
        
        //emit death chunks
        super.emitDeathChunks();
        
        //==================
        //add respawn gravestone
        //==================

        Body gravestoneBody = new Body(new Box(100,130),1 );
        gravestoneBody.setBitmask(Entity.BitMasks.NO_COLLISION.value);
        gravestoneBody.setOverlapMask(Entity.OverlapMasks.NO_OVERLAP.value);
        
        this.respawnGravestone = new RespawnGravestone(new Image("gravestone.png"),gravestoneBody,this);
        //this.respawnGravestone.getImage().setDimensions(100, 130);
        this.respawnGravestone.getImage().setScale(.2f);
        
        Float[] points = {0f,0f,1f};
        int[] durations = {60,60};
        ImageEffect fadeEffect = new MultiImageEffect(ImageEffect.ImageEffectType.ALPHABRIGHTNESS, points,durations);
        this.respawnGravestone.getImage().addImageEffect(fadeEffect);
        
        //building respawn script
        ScriptPage page = new ScriptPage();
        page.setScript("if(\"" + this.getID() + "\" != invoker.getID()){scriptManager.respawnPlayer(\"" + this.getID() + "\",invoker.getID());}"); 
        
        PageCondition condition = new PageCondition();
        condition.setConditionScript("conditionValue = true;");
        
        ScriptObject obj = new ScriptObject();
        obj.addPage(page, condition);
        obj.setTrigger(ScriptObject.ScriptTrigger.RIGHTCLICK);
        this.respawnGravestone.setScriptObject(obj); 
        
        this.getOwningScene().add(this.respawnGravestone, Scene.Layer.MAIN);
        
        
        
        
    }
   
    public void respawn()
    {
        
        //clear arbiters
        if(this.owningScene instanceof GameServerScene)
           ((GameServerScene) owningScene).physicsWorld.clearArbiters(this.body);
        else if(this.owningScene instanceof GameClientScene)
           ((GameClientScene) owningScene).physicsWorld.clearArbiters(this.body);
        
        //recharge health
        this.combatData.fullHeal();
        
        //change animation
        image.setAnimation(CoreAnimations.IDLE);
        
        //body 
        this.body.setBitmask(Entity.BitMasks.PLAYER.value);
        
        //clear states
        this.combatData.removeState(CombatState.DEAD);
        this.combatData.removeState(CombatState.IMMOBILIZE);
        this.combatData.removeState(CombatState.STUN);     
        this.combatData.removeState(CombatState.ATTACKING);
        this.combatData.removeState(CombatState.SLOW);
        this.combatData.removeState(CombatState.IMMUNE);
        
        //remove respawn gravestone
        this.getOwningScene().remove(this.respawnGravestone);
        this.respawnGravestone = null;
       
    }
    
    public void toggleFlashlight()
    {
        if (light.isOn()) 
            this.light.turnOff();
        else 
            this.light.turnOn();
        
    }
    
    public SkillID getSkillAssignment(int number)
    {
        switch(number)
        {
            case 1: return this.skill1; 
            case 2: return this.skill2; 
            case 3: return this.skill3; 
            case 4: return this.skill4;
            default: throw new InvalidParameterException("Skill Assignment Request Out Of Bounds");
        }
    }
    
    public void setSkillAssignment(SkillID skill, int number)
    {
        switch(number)
        {
            case 1:  this.skill1 = skill; break;
            case 2:  this.skill2 = skill; break;
            case 3:  this.skill3 = skill; break;
            case 4:  this.skill4 = skill; break;
            default: throw new InvalidParameterException("Skill Assignment Request Out Of Bounds");
        }
    }
    
    /**
     * Set the world mouse location for the player. This is used for the skill release
     * point and aiming body parts.
     * @param worldMouseX
     * @param worldMouseY 
     */
    public void setWorldMouseLocationPoint(float worldMouseX, float worldMouseY)
    {      
      // this.worldMousePoint.set(worldMouseX, worldMouseY);
        this.worldMousePoint = new SylverVector2f(worldMouseX,worldMouseY);
    }

    /**
     * Begins to cast the skill that is passed as a parameter.  The cast will
     * finish after the skills cast time has elapsed.
     * @param skill The skill to be cast.
     */
    @Override
    public void attack(Skill skill) 
    {

        //start to cast the skill if we aren't already casting , and the skill is usable
        if(skill != null && skill.isUsable() && this.combatData.canAttack() && !this.inAttackAnimation())
        {   
            //set variables
            this.castingSkill = skill;      
            this.attackDelay = this.image.getAnimationPack().getTimingDelay(skill.getImageAnimation());
             
            //change states
            this.combatData.setState(CombatData.CombatState.ATTACKING);
            
            //change animation
            this.frontArm.setAnimation(skill.getImageAnimation());
            this.backArm.setAnimation(skill.getImageAnimation());

            //if there is no attack delay call finish attack
            if(this.attackDelay <= 0)
                this.finishAttack();             
           
        }
    }
    
    @Override
    public boolean inAttackAnimation()
    {
        return (this.frontArm.getAnimation() == ExtendedImageAnimations.MELEEATTACK || this.frontArm.getAnimation() == ExtendedImageAnimations.RANGEDATTACK || this.frontArm.getAnimation() == ExtendedImageAnimations.SPELLATTACK);
    }
    
    @Override
    protected void finishAttack()
    {
        
        //build the damage object
        Damage damage = new Damage(Damage.DamageType.PHYSICAL, 0, this);
        damage.getAmountObject().setPercentModifier(this.getCombatData().baseDamage.getPercentModifier());
        
        //go through on attack procs
        for(CombatEffect effect: this.combatData.getCombatEffects())
        {
            if(effect instanceof ProcEffect && ((ProcEffect)effect).getProcType() == ProcEffect.ProcType.ONSKILL)
            {
                boolean procResult = ((ProcEffect)effect).rollProc();
                
                if(procResult == true)
                {
                    damage.addCombatEffect(((ProcEffect)effect).getProccedEffect());
                }
            }
        }
        
        //roll for crit
        double critRoll = Math.random();
        if(critRoll <= this.combatData.critChance.getTotalValue())
        {
            //modify damage
            damage.setCrit(true);
            damage.getAmountObject().adjustPercentModifier(this.combatData.critModifier.getTotalValue());
            
        }
        
        //set damage life leech
        damage.setLifeLeech(this.combatData.lifeLeech.getTotalValue());        
        
        //use the skill
        this.castingSkill.use(damage, new SylverVector2f(this.skillReleasePoint));
        
        //start the cooldown
        this.castingSkill.beginCooldown(); 
        
        //leave attacking state
        this.combatData.removeState(CombatData.CombatState.ATTACKING);
              
    }

    @Override
    public void addedToScene()
    {
        super.addedToScene();
        
        if(this.owningScene instanceof GameServerScene)
        {
            if(((GameServerScene)this.owningScene).clientsInScene.get(UUID.fromString(this.ID)).currentLevel.equals("town.lv") && this.respawnWhenEnterTown == true)
            {
                //respawn the player
                 this.respawn();

                //send respawn packet
                ((GameServerScene)this.getOwningScene()).sendRespawnPacket(UUID.fromString(this.ID));
                
                this.respawnWhenEnterTown = false;

            }
        }
    }
    
  
    
    private static ROVector2f[] getBodyVertices()
    {
        ROVector2f[] vertices = new ROVector2f[11];
                
        vertices[0] = new Vector2f(15,55);
        vertices[1] = new Vector2f(-15,55); 
        
        //points on circle radius 25
        int ehh = 2;
        for(double t = Math.PI; t <= 2* Math.PI + Math.PI/10; t+= Math.PI/8)
        {
          double x = 25*Math.cos(t) + 0;
          double y = 25*Math.sin(t) - 55;           
          vertices[ehh] = new Vector2f((float)x,(float)y);                   
          ehh++;
        }
      
        return vertices;
    }
    
    private void initializeBodyOffsetMap()
    {  
        //walking
        ArrayList<Vector2f> walkingPosition = new ArrayList<>();
        walkingPosition.add(new Vector2f(0.506f,0.889f)); 
        walkingPosition.add(new Vector2f(0.509f,0.889f)); 
        walkingPosition.add(new Vector2f(0.517f,0.889f)); 
        walkingPosition.add(new Vector2f(0.521f,0.889f)); 
        walkingPosition.add(new Vector2f(0.524f,0.894f)); 
        walkingPosition.add(new Vector2f(0.521f,0.904f)); 
        walkingPosition.add(new Vector2f(0.517f,0.919f));
        walkingPosition.add(new Vector2f(0.517f,0.911f)); 
        walkingPosition.add(new Vector2f(0.517f,0.904f));
        walkingPosition.add(new Vector2f(0.513f,0.889f)); 
        walkingPosition.add(new Vector2f(0.509f,0.889f)); 
        walkingPosition.add(new Vector2f(0.513f,0.889f)); 
        walkingPosition.add(new Vector2f(0.517f,0.889f));
        walkingPosition.add(new Vector2f(0.524f,0.889f)); 
        walkingPosition.add(new Vector2f(0.528f,0.896f)); 
        walkingPosition.add(new Vector2f(0.528f,0.904f)); 
        walkingPosition.add(new Vector2f(0.528f,0.916f)); 
        walkingPosition.add(new Vector2f(0.532f,0.923f)); 
        walkingPosition.add(new Vector2f(0.517f,0.911f)); 
        walkingPosition.add(new Vector2f(0.517f,0.904f)); 
        walkingPosition.add(new Vector2f(0.513f,0.896f)); 
        walkingPosition.add(new Vector2f(0.506f,0.889f)); 
        this.bodyPartOffsets.put(ExtendedImageAnimations.RUNNING, walkingPosition);
        
        //walking reverse
        ArrayList<Vector2f> walkingReverse = new ArrayList<>();          
        walkingReverse.add(new Vector2f(0.506f,0.889f)); 
        walkingReverse.add(new Vector2f(0.513f,0.896f)); 
        walkingReverse.add(new Vector2f(0.517f,0.904f)); 
        walkingReverse.add(new Vector2f(0.517f,0.911f)); 
        walkingReverse.add(new Vector2f(0.532f,0.923f)); 
        walkingReverse.add(new Vector2f(0.528f,0.916f)); 
        walkingReverse.add(new Vector2f(0.528f,0.904f)); 
        walkingReverse.add(new Vector2f(0.528f,0.896f)); 
        walkingReverse.add(new Vector2f(0.524f,0.889f)); 
        walkingReverse.add(new Vector2f(0.517f,0.889f));
        walkingReverse.add(new Vector2f(0.513f,0.889f)); 
        walkingReverse.add(new Vector2f(0.509f,0.889f)); 
        walkingReverse.add(new Vector2f(0.513f,0.889f)); 
        walkingReverse.add(new Vector2f(0.517f,0.904f));
        walkingReverse.add(new Vector2f(0.517f,0.911f)); 
        walkingReverse.add(new Vector2f(0.517f,0.919f));
        walkingReverse.add(new Vector2f(0.521f,0.904f)); 
        walkingReverse.add(new Vector2f(0.524f,0.894f)); 
        walkingReverse.add(new Vector2f(0.521f,0.889f)); 
        walkingReverse.add(new Vector2f(0.517f,0.889f)); 
        walkingReverse.add(new Vector2f(0.509f,0.889f)); 
        walkingReverse.add(new Vector2f(0.506f,0.889f)); 
        this.bodyPartOffsets.put(ExtendedImageAnimations.RUNNINGREVERSE, walkingReverse);
        
        //idle
        ArrayList<Vector2f> idlePosition = new ArrayList<>();     
        idlePosition.add(new Vector2f(0.494f,0.881f));
        idlePosition.add(new Vector2f(0.498f,0.881f));
        idlePosition.add(new Vector2f(0.502f,0.881f));
        idlePosition.add(new Vector2f(0.506f,0.881f));
        idlePosition.add(new Vector2f(0.506f,0.881f));
        idlePosition.add(new Vector2f(0.506f,0.881f));
        idlePosition.add(new Vector2f(0.509f,0.881f));
        idlePosition.add(new Vector2f(0.513f,0.881f));
        idlePosition.add(new Vector2f(0.509f,0.881f));
        idlePosition.add(new Vector2f(0.506f,0.881f));
        idlePosition.add(new Vector2f(0.506f,0.881f));
        idlePosition.add(new Vector2f(0.506f,0.881f));
        idlePosition.add(new Vector2f(0.502f,0.881f));
        idlePosition.add(new Vector2f(0.498f,0.881f));
        idlePosition.add(new Vector2f(0.494f,0.881f));
        this.bodyPartOffsets.put(CoreAnimations.IDLE, idlePosition);
        
        
        //jumping
        ArrayList<Vector2f> jumpingPosition = new ArrayList<>();        
       jumpingPosition.add(new Vector2f(0.494f,0.881f));
       jumpingPosition.add(new Vector2f(0.498f,0.881f));
       jumpingPosition.add(new Vector2f(0.502f,0.881f));
       jumpingPosition.add(new Vector2f(0.506f,0.881f));
       jumpingPosition.add(new Vector2f(0.506f,0.881f));
       jumpingPosition.add(new Vector2f(0.506f,0.881f));
       jumpingPosition.add(new Vector2f(0.509f,0.881f));
       jumpingPosition.add(new Vector2f(0.513f,0.881f));
       jumpingPosition.add(new Vector2f(0.509f,0.881f));
       jumpingPosition.add(new Vector2f(0.506f,0.881f));
       jumpingPosition.add(new Vector2f(0.506f,0.881f));
       jumpingPosition.add(new Vector2f(0.506f,0.881f));
       jumpingPosition.add(new Vector2f(0.502f,0.881f));
       jumpingPosition.add(new Vector2f(0.498f,0.881f));
       jumpingPosition.add(new Vector2f(0.494f,0.881f));
        this.bodyPartOffsets.put(ExtendedImageAnimations.JUMPING, jumpingPosition);
        
        //climbing
        ArrayList<Vector2f> climbingPosition = new ArrayList<>();    
        climbingPosition.add(new Vector2f(0.506f,0.874f));
        climbingPosition.add(new Vector2f(0.506f,0.874f));
        climbingPosition.add(new Vector2f(0.506f,0.874f));
        climbingPosition.add(new Vector2f(0.506f,0.874f));
        climbingPosition.add(new Vector2f(0.506f,0.874f));
        climbingPosition.add(new Vector2f(0.506f,0.874f));
        climbingPosition.add(new Vector2f(0.506f,0.874f));
        climbingPosition.add(new Vector2f(0.506f,0.874f));
        this.bodyPartOffsets.put(ExtendedImageAnimations.CLIMBING, climbingPosition);
        
       
    }
    
    private void updateBodyParts()
    {
        //determine flipped
        boolean flipped;
        if(this.worldMousePoint.x < this.getImage().getPosition().x + this.getImage().getWidth() * .5f)
             flipped = true;
        else
            flipped = false;
        
        //Get user X and Y
        float userX; 
        float userY; 
        
        if(!flipped)
        {
            userX = this.getImage().getPosition().x + this.getImage().getWidth() * .5f -15;
            userY = this.getImage().getPosition().y + this.getImage().getHeight() * .5f +25;
        }
        else
        {
            userX = this.getImage().getPosition().x + this.getImage().getWidth() * .5f +15;
            userY = this.getImage().getPosition().y + this.getImage().getHeight() * .5f +25;
        }
        
        //get vector to mouse
        Vector2f vectorToTarget = new Vector2f(this.worldMousePoint.x - userX, this.worldMousePoint.y - userY);
        vectorToTarget.normalise();

        //determine angle to mouse
        float theta = (float)Math.acos(vectorToTarget.dot(new Vector2f(1,0)));
        if(this.worldMousePoint.y < userY)
            theta = (float)(2* Math.PI - theta);
        
        
        
        //flip body if we are flipped
        if(flipped)
            this.image.setHorizontalFlip(true);
        else
            this.image.setHorizontalFlip(false);

         //=============
         // Front Arm
         //=============
         this.frontArm.setHorizontalFlip(flipped);        
         if(!flipped)
         {
             //twiddle angle stuff
             float angle =(float)(theta * (180f/Math.PI));
             if(angle >= 60 && angle <= 90)
                 angle = 60;
             else if(angle <= 300 && angle >= 270)
                 angle = 300;
             
             //set angle and rotation point
             this.frontArm.setAngle(angle + 2);
             this.frontArm.setRotationPoint(.18f, .53f);
             this.frontArm.setAnchor(Anchorable.Anchor.LEFTCENTER); 
                     
             //set anchored position
             float xOffset = this.bodyPartOffsets.get(this.image.getAnimation()).get(this.image.getAnimationIndex()).x;
             float yOffset = this.bodyPartOffsets.get(this.image.getAnimation()).get(this.image.getAnimationIndex()).y;
             float xPos = -47 +this.image.getPosition().x + this.image.getWidth()*xOffset; 
             float yPos = -15 + this.image.getPosition().y + this.image.getHeight()*yOffset;            
             this.frontArm.setPositionAnchored(xPos,yPos);           
         }
         else
         {         
             //twiddle some angle stuff
             float angle =(float)((theta- Math.PI) * (180f/Math.PI));
             if(angle <= -60 && angle >= -90)
                 angle = -60;
             else if(angle >= 60 && angle <= 90)
                 angle = 60;
             
             //set angle and rotation point
             this.frontArm.setAngle(angle -2);
             this.frontArm.setRotationPoint(.82f, .55f);
             this.frontArm.setAnchor(Anchorable.Anchor.RIGHTCENTER); 
             
             //set offset
             float xOffset = 1 - this.bodyPartOffsets.get(this.image.getAnimation()).get(this.image.getAnimationIndex()).x;
             float yOffset =  this.bodyPartOffsets.get(this.image.getAnimation()).get(this.image.getAnimationIndex()).y;
             float xPos = 47 +this.image.getPosition().x + this.image.getWidth()* xOffset; 
             float yPos = -15 +this.image.getPosition().y + this.image.getHeight()* yOffset;        
             this.frontArm.setPositionAnchored(xPos,yPos);
         }   
         
         //=============
         // Back Arm
         //=============
         this.backArm.setHorizontalFlip(flipped);        
         if(!flipped)
         {
             
             //twiddle some angle stuff
             float angle =(float)(theta * (180f/Math.PI));
             if(angle >= 60 && angle <= 90)
                 angle = 60;
             else if(angle <= 334 && angle >= 270)
                 angle = 334;
             
             //set rotation
             this.backArm.setAngle(angle +2 );
             this.backArm.setRotationPoint(-.17f, .55f);
             this.backArm.setAnchor(Anchorable.Anchor.LEFTCENTER); 
             
             //set offset
             float xOffset = this.bodyPartOffsets.get(this.image.getAnimation()).get(this.image.getAnimationIndex()).x;
             float yOffset = this.bodyPartOffsets.get(this.image.getAnimation()).get(this.image.getAnimationIndex()).y;             
             float xPos = -8 +this.image.getPosition().x + this.image.getWidth()*xOffset; 
             float yPos = -17 + this.image.getPosition().y + this.image.getHeight()*yOffset;   
             this.backArm.setPositionAnchored(xPos,yPos);
            
         }
         else
         {
             //twiddle some angle
             float angle =(float)((theta- Math.PI) * (180f/Math.PI));
             if(angle <= -60 && angle >= -90)
                 angle = -60;
             else if(angle >= 26 && angle <= 90)
                 angle = 26;
             
             //set rotation
             this.backArm.setAngle(angle -2);
             this.backArm.setRotationPoint(1.17f, .55f);
             this.backArm.setAnchor(Anchorable.Anchor.RIGHTCENTER); 
             
             //set offset
             float xOffset = 1 -this.bodyPartOffsets.get(this.image.getAnimation()).get(this.image.getAnimationIndex()).x;
             float yOffset = this.bodyPartOffsets.get(this.image.getAnimation()).get(this.image.getAnimationIndex()).y;
             float xPos = +8 +this.image.getPosition().x + this.image.getWidth()* xOffset; 
             float yPos = -17 +this.image.getPosition().y + this.image.getHeight()* yOffset;        
             this.backArm.setPositionAnchored(xPos,yPos);
         }   
     
         
         
         //=============head============
         this.head.setHorizontalFlip(flipped);
         this.head.setScale(1.10f);
         this.head.setAnchor(Anchorable.Anchor.CENTER);
         if(!flipped)
         {
             float angle =(float)(theta * (180f/Math.PI));
             if(angle >= 60 && angle <= 90)
                 angle = 60;
             else if(angle <= 334 && angle >= 270)
                 angle = 334;
             
             this.head.setRotationPoint(.5f, .25f);
             this.head.setAngle(angle);    
             
             
             float xOffset = this.bodyPartOffsets.get(this.image.getAnimation()).get(this.image.getAnimationIndex()).x;
             float yOffset = this.bodyPartOffsets.get(this.image.getAnimation()).get(this.image.getAnimationIndex()).y;           
             float xPos = -2 +this.image.getPosition().x + this.image.getWidth()*xOffset;
             float yPos = 15+this.image.getPosition().y + this.image.getHeight()*yOffset;           
             this.head.setPositionAnchored(xPos,yPos);
             
         }
         else
         {
           
             float angle =(float)((theta- Math.PI) * (180f/Math.PI));
             if(angle <= -60 && angle >= -90)
                 angle = -60;
             else if(angle >= 26 && angle <= 90)
                 angle = 26;
             
             this.head.setRotationPoint(.5f, .25f);
             this.head.setAngle(angle);   
             
             
             float xOffset = 1- this.bodyPartOffsets.get(this.image.getAnimation()).get(this.image.getAnimationIndex()).x;
             float yOffset = this.bodyPartOffsets.get(this.image.getAnimation()).get(this.image.getAnimationIndex()).y;           
             float xPos = 2 + this.image.getPosition().x + this.image.getWidth()*xOffset; 
             float yPos = 15+this.image.getPosition().y + this.image.getHeight()*yOffset;            
             this.head.setPositionAnchored(xPos,yPos);
         }         
    
    }
    
    protected void setCorrectAnimation()
    {
        //============================
        // Determine Running Animation
        //============================
 
        if((this.feetOnTheGround) && !this.onLadder && 
                                  ((this.body.getVelocity().getX() >= 5 && this.getFacingDirection() == FacingDirection.RIGHT)
                                  || (this.body.getVelocity().getX() < -5 && this.getFacingDirection() == FacingDirection.LEFT)))
        {
            
           
            
            if(image.getAnimation() == ExtendedImageAnimations.RUNNINGREVERSE)
            {
                int setIndex = image.getAnimationPack().animationSet.get(ExtendedImageAnimations.RUNNINGREVERSE).size() - 1 -image.getAnimationIndex();
                image.setAnimation(ExtendedImageAnimations.RUNNING);
                image.setAnimationIndex(setIndex);
                image.update();
                image.update();
                image.update();
            }
            else
            {
               image.setAnimation(ExtendedImageAnimations.RUNNING);

            }

        }
        else if((this.feetOnTheGround) && !this.onLadder && 
                                         ((this.body.getVelocity().getX() >= 5 && this.getFacingDirection() == FacingDirection.LEFT)
                                       || (this.body.getVelocity().getX() < -5 && this.getFacingDirection() == FacingDirection.RIGHT)))
        {
            
          

            if(image.getAnimation() == ExtendedImageAnimations.RUNNING)
            {
                 int setIndex = image.getAnimationPack().animationSet.get(ExtendedImageAnimations.RUNNINGREVERSE).size() -1 -image.getAnimationIndex();
                 image.setAnimation(ExtendedImageAnimations.RUNNINGREVERSE); 
                 image.setAnimationIndex(setIndex);
                 image.update();
                 image.update();
                 image.update();
            }
            else
            {
                image.setAnimation(ExtendedImageAnimations.RUNNINGREVERSE); 

            }


        }
            
        //=============================
        // 
        //=============================
         if (!combatData.isDead() && !this.onLadder)
         {
             //change animation if we are in the air
             if(inAirTimer > 30 || jumpEnergy < this.MAX_JUMP_ENERGY && !(this.feetOnTheGround))           
                 this.image.setAnimation(ExtendedImageAnimations.JUMPING);
             
                 
             //change the animation if we are resting
             if((Math.abs(this.body.getVelocity().getX()) < 5 || (Math.abs(this.body.getVelocity().getX()) < 20 && body.getVelocity().getX() == body.getLastVelocity().getX()) )  && (this.feetOnTheGround))              
                 this.image.setAnimation(CoreAnimations.IDLE); 
             
         }
         
         
         //ladder animation
         if(this.onLadder)
         {
             if(this.body.getVelocity().length() > 1.5)
                 this.getImage().setAnimation(ExtendedImageAnimations.CLIMBING); 
             else
                 this.getImage().setAnimation(CoreAnimations.IDLE);
         }
         
           
    }
    

    
    //================
    // Input Methods
    //================
    
    public void handleSprint()
    {
        if (!sprinting)
        {
            this.combatData.xVelocity.setBase(BASE_PLAYER_VELOCITY.x + 20);
            sprinting = true;
            
        }
        
    }
    
    public void handleSprintReleased()
    {
        if (sprinting)
        {
            this.combatData.xVelocity.setBase(BASE_PLAYER_VELOCITY.x );
            sprinting = false;
        }
    }
    
    public void handleJumping(float x, float y)
    {
                   
        //if we can move and have jump energy
        if(combatData.canMove() && this.jumpEnergy > 0  ) 
        {
            //if its the first press for the jump give the player an extra boost
            if (this.jumpEnergy == MAX_JUMP_ENERGY) 
            {   
                //image.setAnimation(ExtendedImageAnimations.JUMPING);

                //add the jump force
                this.jumpEnergy -= 20; //20 /100
                this.getBody().addSoftForce(new Vector2f(0, 15_000));
                
                //play the jump sound   
                if(this.getOwningScene() instanceof GameClientScene)
                {
                    Sound jumpSound = Sound.locationSound("buffered/jump.ogg", this.getPosition().x, this.getPosition().y, false, .75f);               
                    this.getOwningScene().add(jumpSound);
                }
            } 
            else //add the normal amount of jump force
            {
                this.jumpEnergy -= 6; //6 /100
                this.getBody().addSoftForce(new Vector2f(0, 4_500));
            }
        } 
        //double jumping
        if(combatData.canMove() && doubleJumpAvailable && doubleJumpEnergy >0 && this.armorManager.getBootsEquippedID() == ArmorID.BOOTS2) 
        {
            if(this.doubleJumpEnergy == this.MAX_JUMP_ENERGY-20)
            {
                //add the jump force
                this.doubleJumpEnergy -= 20; //20 /100
                this.getBody().addSoftForce(new Vector2f(0, 15_000));
            }
            else
            {
                this.doubleJumpEnergy -= 6; //6 /100
                this.getBody().addSoftForce(new Vector2f(0, 4_500));
            }
        }
        //jetpacking
        if(combatData.canMove() && doubleJumpAvailable && doubleJumpEnergy >0 &&this.jumpEnergy < this.MAX_JUMP_ENERGY && this.armorManager.getBootsEquippedID() == ArmorID.BOOTS3) 
        {
                 //add jetpack emitters
                if( this.doubleJumpEnergy == this.MAX_JUMP_ENERGY -20)
                {
                    AbstractParticleEmitter emitter = new SmokeEmitter();
                    emitter.setAngle(270);
                    emitter.setParticlesPerFrame(3);
                    emitter.setDuration(-1);
                    this.addEmitter(emitter);
                    
                    AbstractParticleEmitter explosionEmitter = new LeadCrystalParticleEmitters.RocketExplosionEmitter();
                    explosionEmitter.setAngle(270);
                    explosionEmitter.setParticlesPerFrame(1);
                    explosionEmitter.setDuration(-1);
                    this.addEmitter(explosionEmitter);
                }
                
                this.doubleJumpEnergy -= 1f; 
                this.getBody().addSoftForce(new Vector2f(0, 550));
                
                
        }
        
        //teleport
        if(combatData.canMove() && doubleJumpAvailable && doubleJumpEnergy >0 &&this.jumpEnergy < this.MAX_JUMP_ENERGY && this.armorManager.getBootsEquippedID() == ArmorID.BOOTS4) 
        {
            this.handleTeleport(x,y);
            this.doubleJumpAvailable = false;
        }
        
        //set jump released flag
        this.jumpReleased = false;
        
        //set correct animation for this frame
        this.setCorrectAnimation();
        
    }
    
    public void handleTeleport(float x, float y)
    {
         //======================
        // Get Vector To Target
        //======================
        
        //Get target X and Y
        float targetX = x;
        float targetY = y;
        //Get user X and Y
        float userX = this.getPosition().x;
        float userY = this.getPosition().y;
        
        //get vector to target
        Vector2f vectorToTarget = new Vector2f(targetX - userX, targetY - userY);
        vectorToTarget.normalise();
        
        //===============================
        // Determine Rail Beam End Point
        //===============================
        
        SylverVector2f endPoint = new SylverVector2f((vectorToTarget.x * 2000) + userX , (vectorToTarget.y * 2000) + userY);
        ArrayList<SylverVector2f> potentialEnds = new ArrayList();
        //collision body
        //make line from self to target
        Vector2f h = new Vector2f(vectorToTarget );
        h.scale(2000);
        Line line = new Line(new Vector2f(0, 0),h);
 
        Body lineBody = new StaticBody(line);
        lineBody.setPosition(userX, userY);
        lineBody.setBitmask(Entity.BitMasks.COLLIDE_WORLD.value);
        lineBody.setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);        
        
        //get list terrain
        ArrayList<SceneObject> terrain = this.getOwningScene().getSceneObjectManager().get(ExtendedSceneObjectGroups.TERRAIN);

        //collide everything
        for(SceneObject object: terrain)
        {
            WorldObjectEntity blocker = (WorldObjectEntity)object;

            Contact[] contacts = new Contact[10];
            for (int i=0;i<10;i++) {
                    contacts[i] = new Contact();
            }
            int numContacts = Collide.collide(contacts,blocker.getBody() , lineBody, 1);

            if(numContacts > 0)
            {
                for(int i = 0; i < numContacts; i++)
                    potentialEnds.add( new SylverVector2f(contacts[i].getPosition().getX(),contacts[i].getPosition().getY()));
            }
            
        }  
        
        
        //find closest end and make that the endPoint
        for(SylverVector2f end: potentialEnds)
        { 
//            //debug
//            Image hh = new Image("black.png");
//            hh.setDimensions(10, 10);
//            hh.setPosition(end.getX(), end.getY());
//            user.getOwningScene().add(hh, Layer.MAIN);
                               
            if(end.distance(this.getPosition())< endPoint.distance(this.getPosition()))
                endPoint = end;
        }
        
        //mouse
        SylverVector2f mouse = new SylverVector2f(x,y);       
        if(mouse.distance(this.getPosition()) < endPoint.distance(this.getPosition()))
            endPoint = mouse;
        
        //if endpoint is not mouse move it back a little
        if(endPoint != mouse)
        {
            SylverVector2f backVector = new SylverVector2f(this.getPosition().x - endPoint.x, this.getPosition().y - endPoint.y);
            backVector.normalise();
            backVector.scale(100);
            endPoint.add(backVector);
        }
        
        this.setPosition(endPoint.x, endPoint.y);
    }
    
    public void handleDash(SylverVector2f dashVector)
    {
       //handle dash
         if(this.dashing)
         {
              
             this.dashTicks++;
             if(dashTicks > 30)
             {
                 this.dashing = false;
                 this.dashTicks = 0;
                 this.getBody().setVelocity(new Vector2f(0f,0f));
                 this.getBody().setOverlapMask(Entity.OverlapMasks.PLAYER.value); 
                 
             }
             else
             {   
                 this.getBody().setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value); 
                 this.getBody().setVelocity(new Vector2f(this.dashVector.x * 150, this.dashVector.y * 150));
             }
           
             
         }
         else
         {       
            this.dashVector = dashVector;
            this.dashing = true;
         }
    }
    
    public void handleJumpReleased()
    {
        //double jump
        if(this.doubleJumpEnergy == this.MAX_JUMP_ENERGY -20)
            this.doubleJumpAvailable = true;
        else
            this.doubleJumpAvailable = false;
        
        for(AbstractParticleEmitter e :this.getEmitters())
        {
            if(e instanceof SmokeEmitter || e instanceof RocketExplosionEmitter)
            {
                e.stopEmittingThenRemove();
            }
        }
        
        //set jump released flag
        this.jumpReleased = true;
        this.jumpEnergy = 0;
        
        
        
        //if we are on the ground but our jump energy isnt back reset jump energy
        if(this.waitingToResetEnergy)
        {
            this.jumpEnergy = this.MAX_JUMP_ENERGY;
            this.waitingToResetEnergy = false;
        }
        
        
    }
    
    public void move(SylverVector2f vector)
    {
        if (combatData.canMove())
        {
            //check some ladder logic
            if(vector.y == 1)
            {
                if(this.touchingLadder)
                {
                    this.onLadder = true;
                }
            }

            //add force to the body
            vector.normalise();
            this.body.addSoftForce(new Vector2f(3000 * vector.x,(this.onLadder?833:0)* vector.y));
            
            
        }
        
        
        //set correct animation for this frame
        this.setCorrectAnimation();
        
              
    }       
    
    public void useActionBarSkill(SkillID skill)
    {       
        this.attack(this.skillManager.getSkill(skill));
    } 
    
    
    
    //====================
    // RenderData Methods
    //==================== 
    
    public SceneObjectRenderData dumpRenderData() 
    {
        SceneObjectRenderData renderData = new SceneObjectRenderData(ExtendedSceneObjectClasses.PLAYERENTITY,this.ID);

        renderData.data.add(0,this.image.dumpRenderData());
        renderData.data.add(1,this.light != null ? this.light.dumpRenderData() : null);
        renderData.data.add(2,this.combatData.dumpRenderData());
        renderData.data.add(3,this.skillManager.dumpRenderData());
        renderData.data.add(4,this.currencyManager.dumpRenderData());
        renderData.data.add(5,this.armorManager.dumpRenderData());
        renderData.data.add(6,this.levelProgressionManager.dumpRenderData());
        renderData.data.add(7,this.worldMousePoint);
        renderData.data.add(8,this.head.dumpRenderData()); 
        renderData.data.add(9,this.frontArm.dumpRenderData());
        renderData.data.add(10,this.backArm.dumpRenderData());
        renderData.data.add(11,this.entityTooltip.dumpRenderData());
        renderData.data.add(12,this.getName());
        renderData.data.add(13,this.potionManager.dumpRenderData());
        renderData.data.add(14,this.frontArm.getAnimation());
            
        
        return renderData;
    }
    
    public static PlayerEntity buildFromRenderData(SceneObjectRenderData renderData)
    {
        
        Image image = Image.buildFromRenderData((SceneObjectRenderData)renderData.data.get(0));
        
        //build the player
        PlayerEntity player = new PlayerEntity(image,Image.buildFromRenderData((SceneObjectRenderData)renderData.data.get(8)),Image.buildFromRenderData((SceneObjectRenderData)renderData.data.get(10)),Image.buildFromRenderData((SceneObjectRenderData)renderData.data.get(9)));
        player.setID(renderData.getID());
        player.setImage(image);
        
        EntityTooltip tooltip = EntityTooltip.buildFromRenderData((RenderData)renderData.data.get(11));
        player.entityTooltip= tooltip;
        
        if(renderData.data.get(1) != null)
        {
             LightSource light = LightSource.buildFromRenderData((SceneObjectRenderData)renderData.data.get(1));
             player.setLight(light);
        }
        
        player.setName((String)renderData.data.get(12));
        
        return player;
    }
    
    public SceneObjectRenderDataChanges generateRenderDataChanges(SceneObjectRenderData oldData,SceneObjectRenderData newData)
    {
        SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();
        
        int changeMap = 0;
        changes.ID = this.ID;
        ArrayList changeList = new ArrayList();
        
        //image
        SceneObjectRenderDataChanges imageChanges = this.image.generateRenderDataChanges((SceneObjectRenderData)oldData.data.get(0), (SceneObjectRenderData)newData.data.get(0));
        if(imageChanges != null)
        {
            changeList.add(imageChanges);
            changeMap += 1L ;
        }
        
        //light
        if(this.light.isOn())
        {
            SceneObjectRenderDataChanges lightChanges = this.light.generateRenderDataChanges((SceneObjectRenderData)oldData.data.get(1), (SceneObjectRenderData)newData.data.get(1));
            if(lightChanges != null)
            {
                changeList.add(lightChanges);
                changeMap += 1L <<1;
            }
        }
        
        //combat data
        SceneObjectRenderDataChanges combatChanges = this.combatData.generateRenderDataChanges((RenderData)oldData.data.get(2), (RenderData)newData.data.get(2));
        if(combatChanges != null)
        {
            changeList.add(combatChanges);
            changeMap += 1L <<2;
        }
        
        //skill data
        SceneObjectRenderDataChanges skillChanges = this.skillManager.generateRenderDataChanges((RenderData)oldData.data.get(3), (RenderData)newData.data.get(3));
        if(skillChanges != null)
        {
            changeList.add(skillChanges);
            changeMap += 1L <<3;
        } 
        
        //currency data
        SceneObjectRenderDataChanges currencyChanges = this.currencyManager.generateRenderDataChanges((RenderData)oldData.data.get(4), (RenderData)newData.data.get(4));
        if(currencyChanges != null)
        {
            changeList.add(currencyChanges);
            changeMap += 1L <<4;
        }
        
        //armor data
        SceneObjectRenderDataChanges armorChanges = this.armorManager.generateRenderDataChanges((RenderData)oldData.data.get(5), (RenderData)newData.data.get(5));
        if(armorChanges != null)
        {
            changeList.add(armorChanges);
            changeMap += 1L <<5;
        } 
        
        //level progression
        SceneObjectRenderDataChanges progressionChanges = this.levelProgressionManager.generateRenderDataChanges((RenderData)oldData.data.get(6), (RenderData)newData.data.get(6));
        if(progressionChanges != null)
        {
            changeList.add(progressionChanges);
            changeMap += 1L <<6;
        }   
        
        if(!oldData.data.get(7).equals( newData.data.get(7)))
        {
            changeList.add(newData.data.get(7));
            changeMap += 1L << 7;
        }
        
        

        
        
        if(entityTooltip != null)
        {
            SceneObjectRenderDataChanges tooltipChanges = this.entityTooltip.generateRenderDataChanges((RenderData)oldData.data.get(11), (RenderData)newData.data.get(11));
            if(tooltipChanges != null)
            {
                changeList.add(tooltipChanges);
                changeMap += 1L<<11;
            }
        }
        
        //potion data
        SceneObjectRenderDataChanges potionChanges = this.potionManager.generateRenderDataChanges((RenderData)oldData.data.get(13), (RenderData)newData.data.get(13));
        if(potionChanges != null)
        {
            changeList.add(potionChanges);
            changeMap += 1L <<13;
        }
        
        if(!oldData.data.get(14).equals( newData.data.get(14)))
        {
            changeList.add(newData.data.get(14));
            changeMap += 1L << 14;
        }
        
        changes.fields = changeMap;
        changes.data = changeList.toArray();
        
        if(changeList.size() > 0)
            return changes;
        else
            return null;
        
        
    }
    
    /**
     * This reconcile method should be used on other players, not on your client side predicted player
     * @param lastTime
     * @param currentTime
     * @param futureTime
     * @param renderData
     * @param freshPacket 
     */
    public void reconcileRenderDataChanges(long lastTime, long futureTime, SceneObjectRenderDataChanges renderDataChanges)
    {
        int fieldMap = renderDataChanges.fields;
        ArrayList rawData = new ArrayList();
        rawData.addAll(Arrays.asList(renderDataChanges.data)); 
        
        //construct an arraylist of data that we got, nulls will go where we didnt get any data
        ArrayList changeData = new ArrayList();
        for(int i = 0; i <15; i ++)
        {
            // The bit was set
            if ((fieldMap & (1L << i)) != 0)
            {
                changeData.add(rawData.get(0));
                rawData.remove(0);
            }
            else
                changeData.add(null);          
        }
        
        if( this.image != null && changeData.get(0) != null)
            this.image.reconcileRenderDataChanges(lastTime,futureTime,(SceneObjectRenderDataChanges)changeData.get(0));
        
        if(this.light != null && changeData.get(1) != null)
            this.light.reconcileRenderDataChanges(lastTime,futureTime,(SceneObjectRenderDataChanges)changeData.get(1)); 
        
        if(changeData.get(2) != null)
            this.combatData.reconcileRenderDataChanges(lastTime,futureTime,(SceneObjectRenderDataChanges)changeData.get(2));
               
        if(changeData.get(6) != null)
            this.levelProgressionManager.reconcileRenderDataChanges(lastTime, futureTime, (SceneObjectRenderDataChanges)changeData.get(6));
        
        if(this.entityTooltip != null && changeData.get(11) != null)
            this.entityTooltip.reconcileRenderDataChanges(lastTime, futureTime, (SceneObjectRenderDataChanges)changeData.get(11));

        if(changeData.get(7) != null && !(this instanceof ClientPlayerEntity))
        {
            this.worldMousePoint = new SylverVector2f((SylverVector2f)changeData.get(7));
        }
        
        if(changeData.get(14) != null)
        {
            this.frontArm.setAnimation((ImageAnimation)changeData.get(14));
            this.backArm.setAnimation((ImageAnimation)changeData.get(14));
        }
        

    }
    
    public void interpolate(long currentTime)
    {

        if(image != null)
            image.interpolate(currentTime);

        if(light != null)
            light.interpolate(currentTime);

        if(entityTooltip != null)
            entityTooltip.interpolate(currentTime);
        
        if(frontArm != null)
           this.frontArm.update();
        
        if(backArm != null)
            this.backArm.update();
    }  
    
    public PlayerPredictionData dumpPredictionData()
    {
        PlayerPredictionData data = new PlayerPredictionData();
        
   
        data.positionX = this.body.getPosition().getX();
        data.positionY = this.body.getPosition().getY();
        data.velocityX = this.body.getVelocity().getX();
        data.velocityY = this.body.getVelocity().getY();
        
        return data;
    }
    
    //================
    // Save Methods
    //================
    
   
    public SceneObjectSaveData dumpFullData() 
    {
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
        
        SceneObjectSaveData saveData = new SceneObjectSaveData(ExtendedSceneObjectClasses.PLAYERENTITY,this.ID);
       
        saveData.dataMap.put("name",this.getName());
        saveData.dataMap.put("skillManager",this.skillManager.dumpFullData());        
        saveData.dataMap.put("potions",this.potionManager.dumpFullData());
        saveData.dataMap.put("currencyManager",this.currencyManager.dumpFullData());
        saveData.dataMap.put("armorManager",this.armorManager.dumpFullData());
        saveData.dataMap.put("levelManager",this.levelProgressionManager.dumpFullData());
        saveData.dataMap.put("skill1",this.skill1!= null? this.skill1.name(): null);
        saveData.dataMap.put("skill2",this.skill2!= null? this.skill2.name(): null);
        saveData.dataMap.put("skill3",this.skill3!= null? this.skill3.name(): null);
        saveData.dataMap.put("skill4",this.skill4!= null? this.skill4.name(): null);
        saveData.dataMap.put("headImage", this.head.dumpFullData());
        saveData.dataMap.put("bodyImage", this.image.dumpFullData());
        saveData.dataMap.put("frontArm", this.frontArm.dumpFullData());
        saveData.dataMap.put("backArm", this.backArm.dumpFullData());
           
       return saveData;
    }


    public static PlayerEntity buildFromFullData(SceneObjectSaveData saveData) 
    {      
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
        
        Image headImage = Image.buildFromFullData((SceneObjectSaveData)saveData.dataMap.get("headImage"));
        Image bodyImage = Image.buildFromFullData((SceneObjectSaveData)saveData.dataMap.get("bodyImage"));
        Image frontArm = Image.buildFromFullData((SceneObjectSaveData)saveData.dataMap.get("frontArm"));
        Image backArm = Image.buildFromFullData((SceneObjectSaveData)saveData.dataMap.get("backArm"));
        
        PlayerEntity player = new PlayerEntity(bodyImage,headImage,backArm,frontArm);
        
        //skill manager
        SkillManager skillManager = SkillManager.buildFromFullData((SaveData)saveData.dataMap.get("skillManager"));
        skillManager.setOwner(player);
        player.setSkillManager(skillManager);
        
        //belt data
        PotionManager potionManager = PotionManager.buildFromFullData((SaveData)saveData.dataMap.get("potions"));
        potionManager.playerReference = player;
        player.potionManager = potionManager;
        
        //currency data
        CurrencyManager currencyManagerData = CurrencyManager.buildFromFullData((SaveData)saveData.dataMap.get("currencyManager"));
        player.currencyManager = currencyManagerData;
        
        //Armor data
        ArmorManager armorManagerData = ArmorManager.buildFromFullData((SaveData)saveData.dataMap.get("armorManager"));
        armorManagerData.setPlayerReference(player);
        player.armorManager = armorManagerData;
        
        //level data
        LevelProgressionManager progressionData = LevelProgressionManager.buildFromFullData((SaveData)saveData.dataMap.get("levelManager"));
        progressionData.playerReference = player;
        player.levelProgressionManager = progressionData;
        
        //name
        player.setName((String)saveData.dataMap.get("name"));
        
        //skill data
        if(saveData.dataMap.get("skill1") != null)
            player.setSkillAssignment(SkillID.valueOf((String)saveData.dataMap.get("skill1")), 1);
        if(saveData.dataMap.get("skill2") != null)
            player.setSkillAssignment(SkillID.valueOf((String)saveData.dataMap.get("skill2")), 2);
        if(saveData.dataMap.get("skill3") != null)
            player.setSkillAssignment(SkillID.valueOf((String)saveData.dataMap.get("skill3")), 3);
        if(saveData.dataMap.get("skill4") != null)
            player.setSkillAssignment(SkillID.valueOf((String)saveData.dataMap.get("skill4")), 4);
       
        return player;

        
    }
    
    
    
    
    
    //====================
    // Respawn Gravestone
    //====================
    
    public static class RespawnGravestone extends Entity
    {
        private PlayerEntity player;
        private int ticks;
        
        public RespawnGravestone(Image image, Body body, PlayerEntity player)
        {
            super(image,body);
            
            this.player = player;
            
            Image gearImage =new Image("gear2.png");
            gearImage.setAnchor(Anchorable.Anchor.CENTER);
            Overlay gear = new Overlay(gearImage);
            gear.setRelativePosition(.5f, 1.2f);
            gear.setRelativeSize(.2f);
            image.addOverlay("interact",gear );  

            //add overlay movement
            Object[] points = {1.2f,1.3f,1.2f};
            int[] durations = {60,60};
            MultiImageEffect bobEffect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
            bobEffect.setRepeating(true);
            image.addImageEffect(bobEffect);
            
               Float[] points2 = {0f,0f,1f};
            int[] durations2 = {60,60};
            ImageEffect fadeEffect = new MultiImageEffect(ImageEffect.ImageEffectType.ALPHABRIGHTNESS, points2,durations2);
        gear.getImage().addImageEffect(fadeEffect);
        }
        
        
        @Override
        public void update()
        {
            super.update();
            
            this.ticks++;
            
            
            this.setPosition(player.getPosition().x, player.getPosition().y);
            
            
            
        }
        
    }
    
    
}
