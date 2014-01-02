package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.sylver.graphics.*;
import net.phys2d.math.Vector2f;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.EntityEffect.EntityEffectType;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.core.SceneObjectManager;
import com.silvergobletgames.sylver.graphics.ImageEffect.ImageEffectType;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Circle;

/**
 * 
 * Name: Guard
 * Cost:
 * Cooldown: 30 seconds
 * Description: While active, you take 50% damage.  For the first second that Guard is active, you take no damage.
 */
public class PlayerLeechingBlades extends PlayerSkill{
    
    public PlayerLeechingBlades()
    {
        //super constructor 
        super(SkillID.PlayerLeechingBlades,SkillType.DEFENSIVE, ExtendedImageAnimations.SPELLATTACK,1800,Integer.MAX_VALUE);
 
        //set the skillID and the name
        this.icon = new Image("blade.png") ;
        this.skillName = "Leeching Blades";
        this.skillDescription = "Launches blades that spin around you doing damage and leeching health from enemies.";
        this.unlockCost = 2;
    }
    
    public void use(Damage damage, SylverVector2f origin) 
    {
        Random r = SylverRandom.random;

         //set damage
        int min = 10; 
        int max = 12;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.PHYSICAL);  
        damage.setLifeLeech(.40f); 
        
        //add brightness effect to damage
        damage.addImageEffect(this.getDamageBrightnessEffect());
        
        
        //build body of the blade
        Body body = new Body(new Circle(20), 1);
        Image img = new Image("blade.png");
        img.setDimensions(40, 40);
        
        //add rotation effect to image
        ImageEffect rotationEffect = new ImageEffect(ImageEffectType.ROTATION, 30, 0, 360);
        rotationEffect.setRepeating(true);
        img.addImageEffect(rotationEffect);
        Float[] points1 = {1f, 1.8f, 1f};
        int[] durations1= {15,15};
        ImageEffect blinkEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points1, durations1);
        blinkEffect.setRepeating(true);
        img.addImageEffect(blinkEffect);
  
        //build hitbox
        LeechingBladeHitbox blade = new LeechingBladeHitbox(new Damage(damage), body, img, user, LeechingBladeHitbox.LeechingBladeDirection.one); 
        blade.addEntityEffect(new EntityEffect(EntityEffectType.DURATION, 1800, 0, 0));
        blade.setPosition(user.getPosition().x, user.getPosition().y);
        this.user.getOwningScene().add(blade, Scene.Layer.MAIN);
        
        //build body of the blade
        body = new Body(new Circle(20), 1);
        img = new Image("blade.png");
        img.setDimensions(40, 40);
        
        //add rotation effect to image
        rotationEffect = new ImageEffect(ImageEffectType.ROTATION, 30, 0, 360);
        rotationEffect.setRepeating(true);
        img.addImageEffect(rotationEffect);
        blinkEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points1, durations1);
        blinkEffect.setRepeating(true);
        img.addImageEffect(blinkEffect);
        
  
        //build hitbox
        blade = new LeechingBladeHitbox(new Damage(damage), body, img, user, LeechingBladeHitbox.LeechingBladeDirection.two);  
        blade.addEntityEffect(new EntityEffect(EntityEffectType.DURATION, 1800, 0, 0));
        blade.setPosition(user.getPosition().x, user.getPosition().y);
        this.user.getOwningScene().add(blade, Scene.Layer.MAIN);
        
        
        //build body of the blade
        body = new Body(new Circle(20), 1);
        img = new Image("blade.png");
        img.setDimensions(40, 40);
        
        //add rotation effect to image
        rotationEffect = new ImageEffect(ImageEffectType.ROTATION, 30, 0, 360);
        rotationEffect.setRepeating(true);
        img.addImageEffect(rotationEffect);
        //add image effect
        blinkEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points1, durations1);
        blinkEffect.setRepeating(true);
        img.addImageEffect(blinkEffect);
  
        //build hitbox
        blade = new LeechingBladeHitbox(new Damage(damage), body, img, user, LeechingBladeHitbox.LeechingBladeDirection.three);  
        blade.addEntityEffect(new EntityEffect(EntityEffectType.DURATION, 1800, 0, 0));
        blade.setPosition(user.getPosition().x, user.getPosition().y);
        this.user.getOwningScene().add(blade, Scene.Layer.MAIN);
        
    }

    
    public static class LeechingBladeHitbox extends HitBox
    {

        private int ticks = 0;
        
        private LeechingBladeDirection direction;
        
        protected enum LeechingBladeDirection
        {
            one, two, three;
        }
        
        
         public LeechingBladeHitbox(Damage d, Body b, Image i, Entity user,LeechingBladeDirection dir)
         { 
            super(d, b, i, user); 
            
            this.getBody().setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value); 
            this.getBody().setBitmask(Entity.BitMasks.NO_COLLISION.value);
            
            this.direction = dir;
            
         }
         
         @Override
         public void update()
         {            
             super.update();            
             this.ticks++;
             
             if(this.direction == LeechingBladeDirection.one)
             {
                 //period = 120 ticks
                 
                 double x = this.ticks * 4 * (Math.PI/180); //convert to rad
                 
                float xoffset = (float)Math.sin(x) * ( 75 + 15 * (float)Math.sin(x/1.5));
                float yoffset = (float)Math.cos(x) * ( 75 + 15 * (float)Math.sin(x/1.5));
                this.setPosition(this.sourceEntity.getPosition().x + xoffset, this.sourceEntity.getPosition().y + yoffset);
             }
             else if(this.direction == LeechingBladeDirection.two)
             {
                      //period = 120 ticks
                 double x = (this.ticks - (60 *4)) * 4 * (Math.PI/180); //convert to rad
                 double xx =  this.ticks * 4 * (Math.PI/180);
                 
                float xoffset = (float)Math.sin(x) * ( 75 + 15 * (float)Math.sin(xx/1.5));
                float yoffset = (float)Math.cos(x) * ( 75 + 15 * (float)Math.sin(xx/1.5));
                this.setPosition(this.sourceEntity.getPosition().x  + xoffset, this.sourceEntity.getPosition().y + yoffset);
            
                 
             }
             else if(this.direction == LeechingBladeDirection.three)
             {
                      //period = 120 ticks
                 double x = (this.ticks -(120 * 4)) * 4 * (Math.PI/180); //convert to rad
                 double xx = this.ticks * 4 * (Math.PI/180);
                 
                float xoffset = (float)Math.sin(x) * ( 75 + 15 * (float)Math.sin(xx/1.5));
                float yoffset = (float)Math.cos(x) * ( 75 + 15 * (float)Math.sin(xx/1.5));
                this.setPosition(this.sourceEntity.getPosition().x + xoffset, this.sourceEntity.getPosition().y + yoffset);
            
                 
             }
         }

         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 
             
            

                         
             //remove if we hit a world object, or an enemy
             if( other instanceof NonPlayerEntity)
             {
                 //play sound
//                Sound sound = Sound.locationSound("buffered/smallLaser.ogg", this.getPosition().x, this.getPosition().y, false, .35f,2);
//                this.getOwningScene().add(sound);
                 
             }
         }
         
    }
}
