package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.CombatEffect;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.Damage.DamageType;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.ImageEffect.ImageEffectType;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Box;
import com.silvergobletgames.leadcrystal.combat.DotEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect.StateEffectType;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.LaserBitsEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.PoisonGasEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.SmokeEmitter;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;



/**
 * Description: Shoots a laser that damages enemies when struck.
 * Damage: 5-10
 * Cooldown: .5 seconds
 * @author Mike
 */
public class PlayerPoisonBomb extends PlayerSkill{
        
    
    public PlayerPoisonBomb()
    {
        super(SkillID.PlayerPoisonBomb,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,25 * 60,Integer.MAX_VALUE);
        
        //set the skillID and the name
        this.icon = new Image("poisonBombIcon.png");
        this.skillName = "Poison Bomb";
        this.skillDescription = "A poison bomb that trails poisonous gas that slows and damages.";
        this.unlockCost = 1;
    }
    

    public void use(Damage damage, SylverVector2f origin)
    {
        
        Random r = SylverRandom.random;
        
        //get targeting data
        TargetingData targetingData = this.getTargetingData(origin);
        SylverVector2f vectorToTarget = targetingData.vectorToTarget;
        float theta = targetingData.theta;
                        
        //set damage
//        int min = 10; 
//        int max = 12;
//        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
//        damage.getAmountObject().adjustBase(damageAmout);
//        damage.setType(Damage.DamageType.PHYSICAL);
//        damage.addImageEffect(this.getDamageBrightnessEffect());
        
        //build body of the laser
        Body body = new Body(new Box(60,60), 1);
        Image img = new Image("poisonBomb.png");
        img.setAnchor(Anchorable.Anchor.LEFTCENTER);
        img.setDimensions(60, 60);
        img.setColor(new Color(1f,1f,1.5f,1f)); 
        PoisonBombHitbox laser = new PoisonBombHitbox(new Damage(DamageType.NODAMAGE, 0), body, img, user,damage);  
        
        //add image effect
//        Float[] points1 = {1f, 1.8f, 1f};
//        int[] durations1= {15,15};
//        ImageEffect blinkEffect = new MultiImageEffect(ImageEffectType.BRIGHTNESS, points1, durations1);
//        blinkEffect.setRepeating(true);
//        img.addImageEffect(blinkEffect);
        
           
        //calculate force for the bullet
        float xforce = 2500*vectorToTarget.x;
        float yforce = 2500*vectorToTarget.y;
        
        //Dispense bomb into the world
        laser.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        laser.getBody().addForce(new Vector2f(xforce ,yforce));
        laser.getBody().setRotation((float)theta);
        laser.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(laser,Layer.MAIN);      
        
        //dispense muzzle flash
        Image flash = this.getMuzzleFlash(targetingData, origin);
        user.getOwningScene().add(flash,Scene.Layer.MAIN);
        
        //add smoke
        AbstractParticleEmitter smokeEmitter = this.getMuzzleEffect(origin);
        user.getOwningScene().add(smokeEmitter,Scene.Layer.MAIN);
        
 
        Sound sound = Sound.locationSound("buffered/bang.ogg", user.getPosition().x, user.getPosition().y, false, .6f,.89f);
        user.getOwningScene().add(sound);
        
        sound = Sound.locationSound("buffered/steam.ogg", user.getPosition().x, user.getPosition().y, false, .6f,.89f);
        user.getOwningScene().add(sound);


    }
    
   
    
    
    
    public static class PoisonBombHitbox extends HitBox
    {
         private Damage passthroughDamage;
         private int ticks = 29;
         private boolean done = false;
        
         public PoisonBombHitbox(Damage d, Body b, Image i, Entity user, Damage passthrough)
         { 
            super(d, b, i, user); 
            
            this.getBody().setGravityEffected(true);
            this.passthroughDamage = passthrough;

         }
         
         private void makeDropperHitbox()
         {
             
            Body body = new Body(new Box(60,60), 1);
            body.setDamping(.5f);
            Image img = new Image("blank.png"); 
            img.setDimensions(1, 1);
            PoisonDropperHitbox poisonHitbox = new PoisonDropperHitbox(new Damage(DamageType.NODAMAGE, 0), body, img, this.sourceEntity,this.passthroughDamage);
            poisonHitbox.setPosition(this.getPosition().x, this.getPosition().y);
            this.getOwningScene().add(poisonHitbox, Layer.MAIN); 
         }
         
         @Override
         public void update()
         {
             super.update();
             
             this.ticks++;
             
             //every half a second
             if(this.ticks % 15 == 0 && !this.done)
             {   
                 this.makeDropperHitbox();
             }
             
             
         }

         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 
                                    
             //remove if we hit a world object, or an enemy
             if(other instanceof WorldObjectEntity || other instanceof CombatEntity)
             {
                 //add another poision Floater
                 this.makeDropperHitbox();
                 
                this.done = true;
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
         }
         
    }
    
    public static class PoisonDropperHitbox extends HitBox
    {
        private Damage passthroughDamage;
        private int ticks = 29;
        private boolean done = false;
        
         public PoisonDropperHitbox(Damage d, Body b, Image i, Entity user, Damage passthrough)
         { 
            super(d, b, i, user); 
            
            this.getBody().setGravityEffected(true);
            
            this.addEmitter(new PoisonGasEmitter());
            this.passthroughDamage = passthrough;

         }
         
         private void makeFloaterHitbox()
         {
                Body body = new Body(new Box(200,200), 1);
                body.setDamping(.5f);
                Image img = new Image("blank.png"); 
                img.setDimensions(1,1);
                Damage d = new Damage(DamageType.NODAMAGE, 0);
                PoisonFloaterHitbox poisonHitbox = new PoisonFloaterHitbox(d, body, img, this.sourceEntity,this.passthroughDamage);
                poisonHitbox.setPosition(this.getPosition().x, this.getPosition().y);
                this.getOwningScene().add(poisonHitbox, Layer.MAIN); 
         }
         
         @Override
         public void update()
         {
             super.update();
             
             this.ticks++;
             
             //every half a second
             if(this.ticks % 15 == 0 && !this.done)
             {   
                this.makeFloaterHitbox();
             }
             
             
         }

         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 
             
                         
             //remove if we hit a world object, or an enemy
             if(other instanceof WorldObjectEntity || other instanceof CombatEntity)
             {               
                 AbstractParticleEmitter emitter =  new PoisonGasEmitter();
                 emitter.setPosition(this.getPosition().x, this.getPosition().y);
                 emitter.setDuration(10);
                 this.getOwningScene().add(emitter, Layer.MAIN); 
                 
                //add another poision Floater
                this.makeFloaterHitbox();
                 
                this.done = true;
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
         }
        
    }
    
    public static class PoisonFloaterHitbox extends HitBox
    {
        private Damage passthroughDamage;
        
        public PoisonFloaterHitbox(Damage d, Body b, Image i, Entity user,Damage passthroughDamage)
        { 
            super(d, b, i, user); 
            
            this.getBody().setBitmask(Entity.BitMasks.NO_COLLISION.value);
            this.getBody().setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);
            
            //put in own duration effect
            this.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 420, 0, 0)); 
           // this.getBody().setGravityEffected(true);
            
            this.passthroughDamage = passthroughDamage;
            this.passthroughDamage.setType(Damage.DamageType.POISON);
                this.passthroughDamage.getAmountObject().setBase(2);

         }
        
        public void collidedWith(Entity other, CollisionEvent event)
        {
            //if we collided with an enemy apply the poison
            if(other instanceof NonPlayerEntity)
            {
            
                //apply slow
                StateEffect slow = new StateEffect(StateEffect.StateEffectType.SLOW, 420, .30f, true);
               ((CombatEntity)other).getCombatData().addCombatEffect("poisonSlow", slow);
                
                //apply image effect
                ((CombatEntity)other).getImage().addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.COLOR, 420, new Color(181,47,177), new Color(1f,1f,1f)));
                
                
                //apply damage dot
                
                DotEffect poison = new DotEffect(420, 22, this.passthroughDamage);                
                ((CombatEntity)other).getCombatData().addCombatEffect("poisonDot", poison);
            }
            
        }
   
    }
    
}  




