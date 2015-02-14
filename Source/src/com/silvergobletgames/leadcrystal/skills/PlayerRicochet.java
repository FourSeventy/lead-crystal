package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.CombatEffect;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.combat.Damage;
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
import com.silvergobletgames.leadcrystal.combat.Damage.DamageType;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect.StateEffectType;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.LaserBitsEmitter;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.scenes.GameScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import net.phys2d.raw.shapes.Circle;



/**
 * Skill that creates our basic projectile entity and fires it into the scene from
 * the user's current location to the mouse clicked location if the user is a player
 * or the user's target if the target is an enemy.
 * @author Justin Capalbo
 */
public class PlayerRicochet extends PlayerSkill{
        
    
    /**
     * Here we define the skill types and set up other initial params if any
     */
    public PlayerRicochet()
    {
        super(SkillID.PlayerRicochet,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,50,Integer.MAX_VALUE);
        
        //set the skillID and the name
        this.icon = new Image("ricochetBladeIcon.png");
        this.skillName = "Ricochet Blade";
        this.skillDescription = "Shoots a deadly blade that bounces off walls and pierces enemies.";
        this.skillDamageDescription = "Damage: 7-9";
        this.unlockCost = 1;

    }
    
    /**
     * Executes this skill
     * @param user 
     */
    public void use(Damage damage, SylverVector2f origin)
    {
        
        PlayerEntity player = (PlayerEntity) user;
        Random r = SylverRandom.random;
        
        //get targeting data
        TargetingData targetingData = this.getTargetingData(origin);
        SylverVector2f vectorToTarget = targetingData.vectorToTarget;
        float theta = targetingData.theta;
                        
        //set damage
        int min = 7; 
        int max = 9;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.PHYSICAL);  
        damage.addImageEffect(this.getDamageBrightnessEffect());
        
        //build body of the blade
        Body body = new Body(new Circle(20), 1);
        body.setRestitution(3);
        body.setFriction(0);
        Image img = new Image("ricochet2.png");
        img.setDimensions(43, 43);
        
        //add rotation effect to image
        ImageEffect rotationEffect = new ImageEffect(ImageEffectType.ROTATION, 30, 0, 360);
        rotationEffect.setRepeating(true);
        img.addImageEffect(rotationEffect);
  
        //build hitbox
        BladeHitBox blade = new BladeHitBox(damage, body, img, user);  
        
     
        //calculate force for the bullet
        float xforce = 2000*vectorToTarget.x;
        float yforce = 2000*vectorToTarget.y;

        //Dispense blade into the world
        blade.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        blade.getBody().addForce(new Vector2f(xforce ,yforce));
        blade.getBody().setRotation((float)theta);
        blade.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(blade,Layer.MAIN);
        
        //dispense muzzle flash
        Image flash = this.getMuzzleFlash(targetingData, origin);
        user.getOwningScene().add(flash,Scene.Layer.MAIN);
        
        //add smoke
        AbstractParticleEmitter smokeEmitter = this.getMuzzleEffect(origin);
        user.getOwningScene().add(smokeEmitter,Scene.Layer.MAIN);
        
        //play sound
        Sound sound = Sound.locationSound("buffered/bang.ogg", user.getPosition().x, user.getPosition().y, false, .6f,1f);
        user.getOwningScene().add(sound);
        
        sound = Sound.locationSound("buffered/clang1.ogg", user.getPosition().x, user.getPosition().y, false, 1f,1f);
        user.getOwningScene().add(sound);

    }
    
    
    public static class BladeHitBox extends HitBox
    {

        long counter;
        private Vector2f maxVelocity = new Vector2f(0,0);
        boolean removeOnNextContact = false;
        
         public BladeHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
            
            //label effect for destruction disk
            StateEffect labelEffect = new StateEffect(StateEffectType.LABEL, 1) ;
            labelEffect.setName("playerLaser");
            labelEffect.setInfinite();            
            this.getDamage().addCombatEffect(labelEffect);
            this.setLockImageToBody(false);
         }

         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 
             
              //play sound
                Sound sound = Sound.locationSound("buffered/clang1.ogg", this.getPosition().x, this.getPosition().y, false, .45f,2f);
                this.getOwningScene().add(sound);
             
             //laser bits
             if(!(other instanceof HitBox))
             {
                 //get angle of impact
                 Vector2f normalVector = (Vector2f)event.getNormal();
                 normalVector =normalVector.negate();
                 Vector2f xVector = new Vector2f(1,0);
                 float angle = (float)(Math.acos(normalVector.dot(xVector) ) * 180 / Math.PI);
                 if(normalVector.getY() < 0)
                     angle += 180;
                 
                 //make emitter
                 PointParticleEmitter emitter = new LaserBitsEmitter();
                 emitter.setPosition(event.getPoint().getX(), event.getPoint().getY());
                 emitter.setDuration(1);
                 emitter.setAngle(angle);
                 emitter.setSize(2);
                 emitter.setParticlesPerFrame(5);
                 emitter.setColor(new Color(2f,2f,2f));
                 owningScene.add(emitter,Layer.MAIN);
                 
             }
             
             //remove if we hit a world object, or an enemy
             if(this.removeOnNextContact &&(other instanceof WorldObjectEntity || other instanceof CombatEntity))
             {
                
        
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
         }
         
         public void update()
         {
             super.update();
             this.counter++;
             
             if(this.counter > 180)
                 this.removeOnNextContact = true;
             
             //keep track of max velocity
             if(Math.abs(this.body.getVelocity().length()) > Math.abs(this.maxVelocity.length()))
             {
                 this.maxVelocity = new Vector2f(this.body.getVelocity());
             }
             
            //keep at constant speed
            if(Math.abs(this.body.getVelocity().length()) < Math.abs(this.maxVelocity.length()))
            {
                float scaling = Math.abs(this.maxVelocity.length())/ Math.abs(this.body.getVelocity().length());
                Vector2f newVelocity = new Vector2f(this.body.getVelocity().getX() * scaling, this.body.getVelocity().getY() * scaling);
                this.body.setVelocity(newVelocity);
            }
         }
         
         
    }
    
    
    
}  

