package com.silvergobletgames.leadcrystal.entities;

import com.silvergobletgames.leadcrystal.combat.CombatData;
import com.silvergobletgames.leadcrystal.combat.CombatData.CombatState;
import com.silvergobletgames.leadcrystal.combat.CombatEffect;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.Damage.DamageType;
import com.silvergobletgames.leadcrystal.combat.LevelProgressionManager;
import com.silvergobletgames.leadcrystal.combat.ProcEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.*;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBlackBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBlackFrontArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBlueBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBrownBackArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBrownBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBrownFrontArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashGreenBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashRedBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashWhiteBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashYellowBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.JetpackEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.RocketExplosionEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.SandSpurtEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.SmokeEmitter;
import com.silvergobletgames.leadcrystal.entities.EntityTooltip.EntityTooltipField;
import com.silvergobletgames.leadcrystal.items.*;
import com.silvergobletgames.leadcrystal.items.ArmorManager;
import com.silvergobletgames.leadcrystal.items.Currency;
import com.silvergobletgames.leadcrystal.items.CurrencyManager;
import com.silvergobletgames.leadcrystal.items.Potion;
import com.silvergobletgames.leadcrystal.scenes.GameScene;
import com.silvergobletgames.leadcrystal.scripting.PageCondition;
import com.silvergobletgames.leadcrystal.scripting.ScriptObject;
import com.silvergobletgames.leadcrystal.scripting.ScriptPage;
import com.silvergobletgames.leadcrystal.skills.PlayerBoomerang.BoomerangHitbox;
import com.silvergobletgames.leadcrystal.skills.Skill;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;
import com.silvergobletgames.leadcrystal.skills.SkillManager;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.InputSnapshot;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.AnimationPack.CoreAnimations;
import com.silvergobletgames.sylver.graphics.AnimationPack.ImageAnimation;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.LightSource;
import com.silvergobletgames.sylver.netcode.*;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.awt.Point;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import javax.media.opengl.GL2;
import net.phys2d.math.ROVector2f;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.*;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Circle;
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
    protected final float MAX_JUMP_ENERGY = 100;
    protected float jumpEnergy = MAX_JUMP_ENERGY;
    protected boolean jumpReleased = true;
    protected boolean waitingToResetEnergy = false;
    //double jump
    protected boolean doubleJumpAvailable = true;
    protected boolean doubleJumpTimingRight = false;
    protected final int DOUBLE_JUMP_TIMING_WINDOW = 60;
    protected final int DOUBLE_JUMP_TIME_SINCE_RELEASING_WINDOW = 40;
    protected int timeSinceReleasing = 0;
    //jetpack stuff
    protected boolean jetpackReady = false;  
    protected final int MAX_JETPACK_JUICE = 60;
    protected int jetpackJuice = MAX_JETPACK_JUICE;

    
    //movement variables    
    protected final Vector2f BASE_PLAYER_VELOCITY = new Vector2f(55,128);
    protected final float BASE_DAMPING = .1f; 
    protected final float BASE_FRICTION = 1.45f; 
    protected final float AIR_DAMPENING = .5f;
    private boolean sprinting = false;
    public boolean dashing = false;
    protected SylverVector2f dashVector;
    protected int dashTicks = 0;
    
    
    //status variables
    public boolean touchingLadder = false;
    public boolean onLadder = false;
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
        super(bodyImage, new Body(new Polygon(PlayerEntity.getBodyVertices()), 6));        
        
        
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
        this.imageOffset = new Vector2f(0,-19);

        //set body attributes
        body.setFriction(this.BASE_FRICTION);
        body.setDamping(this.BASE_DAMPING);    
        body.setBitmask(BitMasks.PLAYER.value);     
        body.setOverlapMask(OverlapMasks.PLAYER.value);
        body.setRestitution(0);
        body.setRotatable(false);
        body.setMutableFriction(true);
        
        
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
        combatData.critChance.setBase(.1f);
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
        this.light.setIntensity(0.90f);
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

        
        //check if in air
        if(this.body.getTouching().size() == 0)         
            this.feetOnTheGround = false;            
        
         
        //if our feet arent on the ground increment in air timer
        if(!this.feetOnTheGround)
            this.inAirTimer++;
        
        //clear jump energy if we are in air for too long
        if(this.inAirTimer > 15 && this.jumpEnergy == this.MAX_JUMP_ENERGY)
            this.jumpEnergy = 0;
        
        //update time since releasing jump
        this.timeSinceReleasing++;
           
    
         //entity tooltip
         entityTooltip.setHealthField(combatData.getPercentHealth());        
         
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
         
         //if not on ladder and dont think on ground, and velocity = 0 reset ground (fixes hovering edge case thing)
         if(!this.onLadder && !this.feetOnTheGround && this.getBody().getVelocity().getX() ==0 && this.getBody().getVelocity().getY() == 0 && this.inAirTimer > 200)
         {
                this.refreshJumpVariables();
             
         }
         
         
         //turn off friction while in air to avoid sticking to walls
         if(this.body.getFriction() != .03f &&  this.inAirTimer > 10)
         {
             this.body.setFriction(.03f);
         }
         else if(this.body.getFriction() != this.BASE_FRICTION && this.feetOnTheGround)
         {
             this.body.setFriction(this.BASE_FRICTION);
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
        
        //position flashlight
        this.light.setPosition((int) this.skillReleasePoint.x, this.skillReleasePoint.y + 10);
        //face flashlight towards mouse
        float angle = this.frontArm.getAngle();
        if(frontArm.isFlippedHorizontal()) 
        {
            angle+= 180;
        }
        this.light.setDirection(angle); 
        
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
        //if we hit someone elses boomerang return
        if(other instanceof BoomerangHitbox && ((BoomerangHitbox)other).sourceEntity != this)
        {
            return;
        }
        
        super.collidedWith(other, event);

                 
        //if we collided with currency, collect it and remove it from the world
        if (other instanceof Currency)
        {
            int currencyAmount = ((Currency)other).getAmount();
            
            if(this.getArmorManager().doubleGoldFind.isMaxPoints() == true)
            {
                
                Damage damage = new Damage(Damage.DamageType.HEAL, 1);
                this.takeDamage(damage);
            }
            
            //add to currency manager
            this.currencyManager.addCurrency(currencyAmount);

            //add currency text
            Text currencyText = new Text("+" + Integer.toString(currencyAmount), LeadCrystalTextType.COMBAT);
            currencyText.setColor(new Color(240,194,12));
            currencyText.setPosition(other.getPosition().x + SylverRandom.random.nextInt(10), other.getPosition().y);
            currencyText.addTextEffect(new TextEffect(TextEffect.TextEffectType.YTRANSLATE, 120, other.getPosition().y, other.getPosition().y + 250));
            currencyText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 120, 0, 0));
            TextEffect fadeEffect = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(new Color(240,194,12),1), new Color(new Color(240,194,12),0));
            fadeEffect.setDelay(90);
            currencyText.addTextEffect(fadeEffect);
            this.getOwningScene().add(currencyText, Scene.Layer.MAIN);
            
            //add sound
            Sound goldSound = Sound.locationSound("buffered/jump.ogg", this.getPosition().x, this.getPosition().y, false, .6f,2f);               
            this.getOwningScene().add(goldSound);


            //remove the item from the world
            other.removeFromOwningScene();
        }
        
        //if we collided with a potion add it to the potion manager
        if(other instanceof Potion)
        {
            
            if(this.potionManager.getNumberOfPotions() < this.potionManager.getMaxPotions())
            {
                //add the potion
                this.potionManager.addPotion(1);
                
                //add potion text
                Text currencyText = new Text("+1", LeadCrystalTextType.COMBAT);
                currencyText.setColor(new Color(126,11,15));
                currencyText.setPosition(other.getPosition().x + SylverRandom.random.nextInt(10), other.getPosition().y );
                currencyText.addTextEffect(new TextEffect(TextEffect.TextEffectType.YTRANSLATE, 120, other.getPosition().y, other.getPosition().y + 250));
                currencyText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 120, 0, 0));
                TextEffect fadeEffect = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(new Color(126,11,15),1), new Color(new Color(126,11,15),0));
                fadeEffect.setDelay(90);
                currencyText.addTextEffect(fadeEffect);
                this.getOwningScene().add(currencyText, Scene.Layer.MAIN);
            }
            else
            {
                //use the potion
                this.potionManager.addPotion(1);
                this.potionManager.usePotion();
            }
            
            //sound
            Sound sound = Sound.locationSound("buffered/blip.ogg", this.getPosition().x, this.getPosition().y, false,.8f);  
            this.getOwningScene().add(sound);
            
            //remove the item from the world
            other.removeFromOwningScene();
        }
                      
        
            
        //if we collided with the ground
        if (other instanceof WorldObjectEntity) 
        {                                
            
            //if we landed on the top of a worldObjectEntity
            if(-event.getNormal().getY() > .35)
            {
                //play sound
                if(this.jumpEnergy != MAX_JUMP_ENERGY)
                {                                   

                    Sound sound = Sound.locationSound("buffered/bodyFall.ogg", this.getPosition().x, this.getPosition().y, false,.40f);
                    this.getOwningScene().add(sound);
                    
                }
                
                //refresh jump variables
               this.refreshJumpVariables();
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
            if(-event.getNormal().getY() > .35)
            {
               //refresh jump variables
               this.refreshJumpVariables();
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
        //if in level0 you cant die
        if(this.getOwningScene() != null && this.getOwningScene() instanceof GameScene && ((GameScene)this.getOwningScene()).getActiveLevel() != null && ((GameScene)this.getOwningScene()).getActiveLevel().filename.equals("desert0.lv"))
        {
            this.getCombatData().fullHeal();
            return;
        }
        
        //clear arbiters
        ((GameScene) owningScene).getPhysicsWorld().clearArbiters(this.body);

        
        //Essential STATUS flag when dead
        this.combatData.setState(CombatState.DEAD);
                
        //Remove all effects
        this.combatData.removeAllCombatEffects();
        this.getImage().removeAllImageEffects();
        
        //play sound
        Sound sound = Sound.locationSound("buffered/bashDeath01.ogg", this.getPosition().x, this.getPosition().y, false, 1f,1f);
        this.getOwningScene().add(sound);

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
        this.respawnGravestone.getImage().setScale(1.1f);
        
        Float[] points = {0f,0f,1f};
        int[] durations = {60,60};
        ImageEffect fadeEffect = new MultiImageEffect(ImageEffect.ImageEffectType.ALPHABRIGHTNESS, points,durations);
        this.respawnGravestone.getImage().addImageEffect(fadeEffect);
        
        this.getOwningScene().add(this.respawnGravestone, Scene.Layer.MAIN);
        
     
    }
   
    public void respawn()
    {
        
        //clear arbiters
        if(this.owningScene instanceof GameScene)
           ((GameScene) owningScene).getPhysicsWorld().clearArbiters(this.body);
        
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
        if(this.respawnGravestone != null)
        {
            this.getOwningScene().remove(this.respawnGravestone);
            this.respawnGravestone = null;
        }
       
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
    public void takeDamage(Damage dmg)
    {
        //handle proximityDamageReduction 
       if(dmg.getSource() != null && dmg.getType() != Damage.DamageType.HEAL && dmg.getSource().distanceAbs(this) < 250)
       {
           int points = this.armorManager.proximityDamageReduction.points;
           
           dmg.getAmountObject().adjustPercentModifier(-.15f * points); 
           
       }
       
       super.takeDamage(dmg);
       
       //handle hardToKill upgrade
       if(this.armorManager.hardToKill.isMaxPoints() == true)
       {           
           if(this.combatData.getPercentHealth() <= .33f)
           {
               if(!this.combatData.containsEffect("DRModifier"))
               {
                  CombatEffect effect = new StateEffect(StateEffect.StateEffectType.DAMAGEREDUCTION, 500, .50f, false);
                  effect.setInfinite();
                  this.combatData.addCombatEffect("DRModifier",effect );
               }
           }
           else
           {
               this.combatData.removeCombatEffect("DRModifier"); 
           }
       }
       
       if(dmg.getType() != DamageType.HEAL && dmg.getType() != DamageType.NODAMAGE && !this.getCombatData().isDead())
       {
            Sound sound = Sound.locationSound("buffered/fleshExplosion.ogg", this.getPosition().x, this.getPosition().y, false, .4f,.8f);               
            this.getOwningScene().add(sound);
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
        
        // meleeAttackDamageBonus modifier
        if(this.castingSkill.getRange() < 200)
        {
            int points = this.getArmorManager().meleeAttackDamageBonus.points;
            damage.getAmountObject().adjustPercentModifier(.25f * points);
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
        
//        if(this.owningScene instanceof GameScene)
//        {
//            if(((GameScene)this.owningScene)..equals("town.lv") && this.respawnWhenEnterTown == true)
//            {
//                //respawn the player
//                 this.respawn();
//
//                //send respawn packet
//                ((GameScene)this.getOwningScene()).sendRespawnPacket(UUID.fromString(this.ID));
//                
//                this.respawnWhenEnterTown = false;
//
//            }
//        }
    }
    
    public float getMaxJumpEnergy()
    {
        return this.MAX_JUMP_ENERGY;
    } 
  
    
    private static ROVector2f[] getBodyVertices()
    {
        ROVector2f[] vertices = new ROVector2f[17];
                
        vertices[0] = new Vector2f(8,55);
        vertices[1] = new Vector2f(0,65);
        vertices[2] = new Vector2f(-8,55); 
        
        //points on circle radius 25
        int ehh = 3;
        for(double t = Math.PI; t <= 2* Math.PI + Math.PI/10; t+= Math.PI/12)
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
         float xPosOffset, yPosOffset;
        if(this.image.getAnimationPack() instanceof AnimationPackClasses.BashBrownBodyAnimationPack)
        {
            xPosOffset = -2;
            yPosOffset = 15;
        }
        else if(this.image.getAnimationPack() instanceof AnimationPackClasses.BashBlackBodyAnimationPack )
        {
            xPosOffset = -2;
            yPosOffset = 15;
        }
        else if(this.image.getAnimationPack() instanceof AnimationPackClasses.BashBlueBodyAnimationPack )
        {
            xPosOffset = -3;
            yPosOffset = 14;
        }
        else if(this.image.getAnimationPack() instanceof AnimationPackClasses.BashGreenBodyAnimationPack)
        {
            xPosOffset = -2;
            yPosOffset = 14;
        }
        else if(this.image.getAnimationPack() instanceof AnimationPackClasses.BashRedBodyAnimationPack)
        {
            xPosOffset = -2;
            yPosOffset = 15;
        }
        else if(this.image.getAnimationPack() instanceof AnimationPackClasses.BashWhiteBodyAnimationPack)
        {
            xPosOffset = -2;
            yPosOffset = 15;
        }
        else if(this.image.getAnimationPack() instanceof AnimationPackClasses.BashYellowBodyAnimationPack)
        {
            xPosOffset = -2;
            yPosOffset = 13;
        }
        else
        {
            xPosOffset = -2;
            yPosOffset = 15;
        }
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
             
             
             
             float xPos = xPosOffset +this.image.getPosition().x + this.image.getWidth()*xOffset;
             float yPos = yPosOffset+this.image.getPosition().y + this.image.getHeight()*yOffset;           
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
             float xPos = -xPosOffset + this.image.getPosition().x + this.image.getWidth()*xOffset; 
             float yPos = yPosOffset+this.image.getPosition().y + this.image.getHeight()*yOffset;            
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
    
    public void handleJumping()
    {               
        
        //regular jumping logic
        if(combatData.canMove() && this.jumpEnergy > 0  ) 
        {

            //if its the first press for the jump give the player an extra boost
            if (this.jumpEnergy == MAX_JUMP_ENERGY) 
            {   

                //add the jump force
                this.jumpEnergy -= 20; 
                this.getBody().addSoftForce(new Vector2f(0, 15_000));
                
                //play the jump sound   
                Sound jumpSound = Sound.locationSound("buffered/jump.ogg", this.getPosition().x, this.getPosition().y, false, .4f);               
                this.getOwningScene().add(jumpSound);
                
            } 
            else //add the normal amount of jump force
            {
                this.jumpEnergy -= 6; //6 /100
                this.getBody().addSoftForce(new Vector2f(0, 1_000));
            }
        } 
        
        //double jumping
        if(this.getArmorManager().doubleJump.isMaxPoints() && combatData.canMove() && doubleJumpAvailable && doubleJumpTimingRight && this.inAirTimer < this.DOUBLE_JUMP_TIMING_WINDOW && this.timeSinceReleasing < this.DOUBLE_JUMP_TIME_SINCE_RELEASING_WINDOW)  
        {         
            //add the jump force
            this.getBody().addSoftForce(new Vector2f(0, 20_000));  
            this.doubleJumpAvailable = false;
            this.doubleJumpTimingRight = false;
            
            //add effects and sounds

            Sound jumpSound = Sound.locationSound("buffered/jump.ogg", this.getPosition().x, this.getPosition().y, false, .4f);               
            this.getOwningScene().add(jumpSound);
            
            AbstractParticleEmitter emitter = new SmokeEmitter();
            emitter.setAngle(270);
            emitter.setParticlesPerFrame(10);
            emitter.setDuration(5);
            emitter.setPosition(this.getPosition().x, this.getPosition().y - 50); 
            this.getOwningScene().add(emitter,Layer.MAIN);
                      
        }
        
        
        //jetpacking       
        if(this.getArmorManager().jetpack.isMaxPoints() && this.jetpackReady && combatData.canMove()  ) 
        {
            //we must be in the double jump timing window to start jetpacking
            if(this.jetpackJuice != this.MAX_JETPACK_JUICE ||  this.timeSinceReleasing < this.DOUBLE_JUMP_TIME_SINCE_RELEASING_WINDOW && ((this.getArmorManager().doubleJump.isMaxPoints() && this.inAirTimer < this.DOUBLE_JUMP_TIMING_WINDOW + 20)||(!this.getArmorManager().doubleJump.isMaxPoints() && this.inAirTimer < this.DOUBLE_JUMP_TIMING_WINDOW )))
            {
                        
                //add jetpack emitters
                if(this.jetpackJuice == this.MAX_JETPACK_JUICE)
                {
                    
                    AbstractParticleEmitter explosionEmitter = new LeadCrystalParticleEmitters.JetpackEmitter();
                    explosionEmitter.setAngle(270);
                    explosionEmitter.setParticlesPerFrame(1);
                    explosionEmitter.setDuration(-1);
                    this.addEmitter(explosionEmitter);
                    
                    //start sound
                    Sound sound = Sound.locationSound("buffered/jetpackLoop.ogg", this.getPosition().x  , this.getPosition().y, false, 1f, 1f, true);
                    sound.name = "jetpackLoop";
                    this.getOwningScene().add(sound);
                }

                //set gravity
                if(this.jetpackJuice > 0)
                {
                    this.jetpackJuice -= 1;
                    this.getBody().setGravityEffected(false);
                    this.getBody().setVelocity(new Vector2f(this.getBody().getVelocity().getX(),0)); 

                }
                else
                {
                    
                    Game.getInstance().getAudioRenderer().getSoundSystem().stop("jetpackLoop");
                    Game.getInstance().getAudioRenderer().getSoundSystem().removeSource("jetpackLoop");
                    
                    //turn gravity back on for player
                    this.getBody().setGravityEffected(true);

                    //remove jetpack emitters
                    for(AbstractParticleEmitter e :this.getEmitters())
                    {
                        if(e instanceof SmokeEmitter || e instanceof JetpackEmitter)
                        {
                            e.stopEmittingThenRemove();
                        }
                    }
                }
            }
                               
        }
       
        
        //set jump released flag
        this.jumpReleased = false;
        
        //set correct animation for this frame
        this.setCorrectAnimation();
        
    }
    
    public void handleJumpReleased()
    {
        Game.getInstance().getAudioRenderer().getSoundSystem().stop("jetpackLoop");
        Game.getInstance().getAudioRenderer().getSoundSystem().removeSource("jetpackLoop");
        
        //save the released jump energy into local var
        float jumpEnergyAtRelease = this.jumpEnergy;
       
        
        //set jump released flag and reset jump energy
        this.jumpReleased = true;
        this.jetpackReady = false;
        this.jumpEnergy = 0;
        this.timeSinceReleasing = 0;
               
        //if we are on the ground but our jump energy isnt back reset jump energy
        if(this.waitingToResetEnergy)
        {
            jumpEnergyAtRelease = 100; 
            this.jumpEnergy = this.MAX_JUMP_ENERGY;
            this.waitingToResetEnergy = false;
        }


        //allow for double jump
        if(jumpEnergyAtRelease <= 80 && this.inAirTimer < this.DOUBLE_JUMP_TIMING_WINDOW)
        {
            this.doubleJumpTimingRight = true;
        }
        
        //allow for jetpack given there is no double jump
        if( this.jetpackJuice == this.MAX_JETPACK_JUICE && jumpEnergyAtRelease <= 80 && (!this.getArmorManager().doubleJump.isMaxPoints() || doubleJumpAvailable == false))
        {
            if((this.getArmorManager().doubleJump.isMaxPoints() && this.inAirTimer < this.DOUBLE_JUMP_TIMING_WINDOW + 20)
             ||(!this.getArmorManager().doubleJump.isMaxPoints() && this.inAirTimer < this.DOUBLE_JUMP_TIMING_WINDOW ))
            this.jetpackReady = true;
        }
        
        
        //remove jetpack emitters
        for(AbstractParticleEmitter e :this.getEmitters())
        {
            if(e instanceof SmokeEmitter || e instanceof JetpackEmitter)
            {
                e.stopEmittingThenRemove();
            }
        }
        
        
        
        
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
            
            float horizontalForce = 2_300;
            if(!this.feetOnTheGround)
            {
                horizontalForce *= this.AIR_DAMPENING;
            }       
            float verticalForce = 0;
            if(this.onLadder)
            { 
                horizontalForce = 700;
                verticalForce = 833;
            }
            
            this.body.addSoftForce(new Vector2f(horizontalForce *vector.x, verticalForce* vector.y));
            
            
        }
        
        
        //set correct animation for this frame
        this.setCorrectAnimation();
        
              
    }       
    
    public void useActionBarSkill(SkillID skill)
    {       
        this.attack(this.skillManager.getSkill(skill));
    } 
    
    
    protected void refreshJumpVariables()
    {
         //set flags
        this.feetOnTheGround = true;
        this.inAirTimer = 0;

        //reset jump energy
        if(this.jumpReleased == true)
        {
            this.jumpEnergy = MAX_JUMP_ENERGY;
        }
        else
        {
            this.waitingToResetEnergy = true;
        }

        //double jump things
        this.doubleJumpAvailable = true;
        this.doubleJumpTimingRight = false;
        
        //jetpack
        this.jetpackJuice = this.MAX_JETPACK_JUICE;
        this.jetpackReady = false;
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
