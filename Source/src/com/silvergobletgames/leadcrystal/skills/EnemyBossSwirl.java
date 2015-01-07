package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;
import com.silvergobletgames.leadcrystal.skills.EnemyBossSwirl.SwirlHitbox.SwirlDirection;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.graphics.MultiImageEffect;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Circle;


public class EnemyBossSwirl extends Skill{
    
    
    private static int BLADE_DURATION = 360 * 4 + 200 + 360;
    
    
    public EnemyBossSwirl()
    {
        super(SkillID.EnemyBossSwirl,Skill.SkillType.OFFENSIVE, ExtendedImageAnimations.SPELLATTACK,BLADE_DURATION + (60 * 10),1000);
        

    }
    
    
    public void use(Damage damage, SylverVector2f origin)
    {
           
        //Damage is scaled with base damage
        float dAmount = this.getBaseDamage(); 
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.PHYSICAL);
        
        
        //build body of the blade
        Body body = new Body(new Circle(25), 1);
        Image img = new Image("poisonbomb.png");
        img.setDimensions(50, 50);
        
        //add rotation effect to image
        ImageEffect rotationEffect = new ImageEffect(ImageEffect.ImageEffectType.ROTATION, 30, 0, 360);
        rotationEffect.setRepeating(true);
        img.addImageEffect(rotationEffect);
        Float[] points1 = {1f, 1.8f, 1f};
        int[] durations1= {15,15};
        ImageEffect blinkEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points1, durations1);
        blinkEffect.setRepeating(true);
        img.addImageEffect(blinkEffect);
  
        //build hitbox
        SwirlHitbox blade = new SwirlHitbox(new Damage(damage), body, img, user, SwirlDirection.one); 
        blade.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, BLADE_DURATION, 0, 0));
        blade.setPosition(user.getPosition().x, user.getPosition().y);
        this.user.getOwningScene().add(blade, Scene.Layer.MAIN);
        
        //build body of the blade
        body = new Body(new Circle(25), 1);
        img = new Image("poisonbomb.png");
        img.setDimensions(50, 50);
        
        //add rotation effect to image
        rotationEffect = new ImageEffect(ImageEffect.ImageEffectType.ROTATION, 30, 0, 360);
        rotationEffect.setRepeating(true);
        img.addImageEffect(rotationEffect);
        blinkEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points1, durations1);
        blinkEffect.setRepeating(true);
        img.addImageEffect(blinkEffect);
        
  
        //build hitbox
        blade = new SwirlHitbox(new Damage(damage), body, img, user, SwirlDirection.two);  
        blade.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, BLADE_DURATION, 0, 0));
        blade.setPosition(user.getPosition().x, user.getPosition().y);
        this.user.getOwningScene().add(blade, Scene.Layer.MAIN);
        
        
        //build body of the blade
        body = new Body(new Circle(25), 1);
        img = new Image("poisonbomb.png");
        img.setDimensions(50, 50);
        
        //add rotation effect to image
        rotationEffect = new ImageEffect(ImageEffect.ImageEffectType.ROTATION, 30, 0, 360);
        rotationEffect.setRepeating(true);
        img.addImageEffect(rotationEffect);
        //add image effect
        blinkEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points1, durations1);
        blinkEffect.setRepeating(true);
        img.addImageEffect(blinkEffect);
  
        //build hitbox
        blade = new SwirlHitbox(new Damage(damage), body, img, user, SwirlDirection.three);  
        blade.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, BLADE_DURATION, 0, 0));
        blade.setPosition(user.getPosition().x, user.getPosition().y);
        this.user.getOwningScene().add(blade, Scene.Layer.MAIN);
        
        //build body of the blade
        body = new Body(new Circle(25), 1);
        img = new Image("poisonbomb.png");
        img.setDimensions(50, 50);
        
        //add rotation effect to image
        rotationEffect = new ImageEffect(ImageEffect.ImageEffectType.ROTATION, 30, 0, 360);
        rotationEffect.setRepeating(true);
        img.addImageEffect(rotationEffect);
        //add image effect
        blinkEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points1, durations1);
        blinkEffect.setRepeating(true);
        img.addImageEffect(blinkEffect);
  
        //build hitbox
        blade = new SwirlHitbox(new Damage(damage), body, img, user, SwirlDirection.four);  
        blade.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, BLADE_DURATION, 0, 0));
        blade.setPosition(user.getPosition().x, user.getPosition().y);
        this.user.getOwningScene().add(blade, Scene.Layer.MAIN);
        
        
        //build body of the blade
        body = new Body(new Circle(25), 1);
        img = new Image("poisonbomb.png");
        img.setDimensions(50, 50);
        
        //add rotation effect to image
        rotationEffect = new ImageEffect(ImageEffect.ImageEffectType.ROTATION, 30, 0, 360);
        rotationEffect.setRepeating(true);
        img.addImageEffect(rotationEffect);
        //add image effect
        blinkEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points1, durations1);
        blinkEffect.setRepeating(true);
        img.addImageEffect(blinkEffect);
  
        //build hitbox
        blade = new SwirlHitbox(new Damage(damage), body, img, user, SwirlDirection.five);  
        blade.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, BLADE_DURATION, 0, 0));
        blade.setPosition(user.getPosition().x, user.getPosition().y);
        this.user.getOwningScene().add(blade, Scene.Layer.MAIN);
        
        
        //build body of the blade
        body = new Body(new Circle(25), 1);
        img = new Image("poisonbomb.png");
        img.setDimensions(50, 50);
        
        //add rotation effect to image
        rotationEffect = new ImageEffect(ImageEffect.ImageEffectType.ROTATION, 30, 0, 360);
        rotationEffect.setRepeating(true);
        img.addImageEffect(rotationEffect);
        //add image effect
        blinkEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points1, durations1);
        blinkEffect.setRepeating(true);
        img.addImageEffect(blinkEffect);
  
        //build hitbox
        blade = new SwirlHitbox(new Damage(damage), body, img, user, SwirlDirection.six);  
        blade.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, BLADE_DURATION, 0, 0));
        blade.setPosition(user.getPosition().x, user.getPosition().y);
        this.user.getOwningScene().add(blade, Scene.Layer.MAIN);
        
        
         //build body of the blade
        body = new Body(new Circle(25), 1);
        img = new Image("poisonbomb.png");
        img.setDimensions(50, 50);
        
        //add rotation effect to image
        rotationEffect = new ImageEffect(ImageEffect.ImageEffectType.ROTATION, 30, 0, 360);
        rotationEffect.setRepeating(true);
        img.addImageEffect(rotationEffect);
        //add image effect
        blinkEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points1, durations1);
        blinkEffect.setRepeating(true);
        img.addImageEffect(blinkEffect);
  
        //build hitbox
        blade = new SwirlHitbox(new Damage(damage), body, img, user, SwirlDirection.seven);  
        blade.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, BLADE_DURATION, 0, 0));
        blade.setPosition(user.getPosition().x, user.getPosition().y);
        this.user.getOwningScene().add(blade, Scene.Layer.MAIN);
        
        
        Sound sound = Sound.locationSound("buffered/clang1.ogg", user.getPosition().x, user.getPosition().y, false, 1f,1f);
        user.getOwningScene().add(sound);
        
       
    }
    
    
    public static class SwirlHitbox extends HitBox
    {

        private int ticks = 0;
        private boolean shooting = false;
        
        private SwirlDirection direction;
        
        protected enum SwirlDirection
        {
            one, two, three, four, five, six, seven;
        }
        
        
         public SwirlHitbox(Damage d, Body b, Image i, Entity user,SwirlDirection dir)
         { 
            super(d, b, i, user); 
            
            this.getBody().setOverlapMask(Entity.OverlapMasks.PLAYER_TOUCH.value); 
            this.getBody().setBitmask(Entity.BitMasks.NO_COLLISION.value);
            
            this.direction = dir;
         
            
         }
         
         @Override
         public void update()
         {            
             super.update();            
             this.ticks++;
             
             if(this.shooting)
             {
                 return;
             }
           
             
             if(this.direction == SwirlDirection.one)
             {
                 
                 //shoot the bullet
                 if(ticks > 360 * 4 + 200 + 20 )
                 {
                     this.shooting = true;
                     SylverVector2f vector = this.distanceVector(this.sourceEntity.getTarget());
                     vector.normalise();
                     
                     // Bullet force
                    float xforce = 1800*vector.x;
                    float yforce = 1800*vector.y;

                    this.getBody().addForce(new Vector2f(xforce, yforce)); 
                     return;
                 }
                
                          
                double x = Math.toRadians(this.ticks ); //convert to rad
                float radius;
                if(ticks < 200 || ticks > (360 * 4 + 200 ))
                {
                    radius = 200;
                }
                else
                {
                    radius =   200 + (200 - 200 * (float)Math.cos(Math.toRadians(this.ticks - 200)) );      
                }
                
               
                float xoffset = (float)Math.sin(x) * ( radius + 15 * (float)Math.sin(x/1.5));
                float yoffset = (float)Math.cos(x) * ( radius + 15 * (float)Math.sin(x/1.5));
                this.setPosition(this.sourceEntity.getPosition().x + xoffset, this.sourceEntity.getPosition().y + yoffset);
             }
             else if(this.direction == SwirlDirection.two)
             {
                
                 if(ticks > 360 * 4 + 200 + 40 )
                 {
                     
                     this.shooting = true;
                     
                     SylverVector2f vector = this.distanceVector(this.sourceEntity.getTarget());
                     vector.normalise();
                     
                     // Bullet force
                    float xforce = 1800*vector.x;
                    float yforce = 1800*vector.y;

                    this.getBody().addForce(new Vector2f(xforce, yforce)); 
                     return;
                 }
                 
                double x = Math.toRadians( (this.ticks - 51 ) ); //convert to rad
                float radius;
                if(ticks < 200 || ticks > (360 * 4 + 200 ))
                {
                    radius = 200;
                }
                else
                {
                    radius =   200 + (200 - 200 * (float)Math.cos(Math.toRadians(this.ticks - 200)) );      
                }
                
                float xoffset = (float)Math.sin(x) * ( radius + 15 * (float)Math.sin(x/1.5));
                float yoffset = (float)Math.cos(x) * ( radius + 15 * (float)Math.sin(x/1.5));
                this.setPosition(this.sourceEntity.getPosition().x  + xoffset, this.sourceEntity.getPosition().y + yoffset);
            
                 
             }
             else if(this.direction == SwirlDirection.three)
             {
          
                 if(ticks > 360 * 4 + 200 + 60 )
                 {
                     this.shooting = true;
                     
                     SylverVector2f vector = this.distanceVector(this.sourceEntity.getTarget());
                     vector.normalise();
                     
                     // Bullet force
                    float xforce = 1800*vector.x;
                    float yforce = 1800*vector.y;

                    this.getBody().addForce(new Vector2f(xforce, yforce)); 
                     return;
                 }
                  
                double x = Math.toRadians( (this.ticks - 51 * 2)  ); //convert to rad
                float radius;
                if(ticks < 200 || ticks > (360 * 4 + 200 ))
                {
                    radius = 200;
                }
                else
                {
                    radius =   200 + (200 - 200 * (float)Math.cos(Math.toRadians(this.ticks - 200)) );      
                }
                
                float xoffset = (float)Math.sin(x) * ( radius + 15 * (float)Math.sin(x/1.5));
                float yoffset = (float)Math.cos(x) * ( radius + 15 * (float)Math.sin(x/1.5));
                this.setPosition(this.sourceEntity.getPosition().x + xoffset, this.sourceEntity.getPosition().y + yoffset);
            
                 
             }
             else if(this.direction == SwirlDirection.four)
             {
             
                 if(ticks > 360 * 4 + 200 + 80)
                 {
                     this.shooting = true;
                     
                     SylverVector2f vector = this.distanceVector(this.sourceEntity.getTarget());
                     vector.normalise();
                     
                     // Bullet force
                    float xforce = 1800*vector.x;
                    float yforce = 1800*vector.y;

                    this.getBody().addForce(new Vector2f(xforce, yforce)); 
                     return;
                 }
                  
                double x = Math.toRadians( (this.ticks - 51 * 3)  ); //convert to rad
                float radius;
                if(ticks < 200 || ticks > (360 * 4 + 200 ))
                {
                    radius = 200;
                }
                else
                {
                    radius =   200 + (200 - 200 * (float)Math.cos(Math.toRadians(this.ticks - 200)) );      
                }
                
                float xoffset = (float)Math.sin(x) * ( radius + 15 * (float)Math.sin(x/1.5));
                float yoffset = (float)Math.cos(x) * ( radius + 15 * (float)Math.sin(x/1.5));
                this.setPosition(this.sourceEntity.getPosition().x + xoffset, this.sourceEntity.getPosition().y + yoffset);
            
                 
             }
             else if(this.direction == SwirlDirection.five)
             {

                 
                 
                 if(ticks > 360 * 4 + 200  + 100)
                 {
                     this.shooting = true;
                     
                     SylverVector2f vector = this.distanceVector(this.sourceEntity.getTarget());
                     vector.normalise();
                     
                     // Bullet force
                    float xforce = 1800*vector.x;
                    float yforce = 1800*vector.y;

                    this.getBody().addForce(new Vector2f(xforce, yforce)); 
                     return;
                 }
                 
                double x = Math.toRadians( (this.ticks - 51 * 4)  ); //convert to rad
                float radius;
                if(ticks < 200 || ticks > (360 * 4 + 200 ))
                {
                    radius = 200;
                }
                else
                {
                    radius =   200 + (200 - 200 * (float)Math.cos(Math.toRadians(this.ticks - 200)) );      
                }
       
             
                float xoffset = (float)Math.sin(x) * ( radius + 15 * (float)Math.sin(x/1.5));
                float yoffset = (float)Math.cos(x) * ( radius + 15 * (float)Math.sin(x/1.5));
                this.setPosition(this.sourceEntity.getPosition().x + xoffset, this.sourceEntity.getPosition().y + yoffset);
            
                 
             }
             else if(this.direction == SwirlDirection.six)
             {

                 
                 
                 if(ticks > 360 * 4 + 200 + 120)
                 {
                     this.shooting = true;
                     
                     SylverVector2f vector = this.distanceVector(this.sourceEntity.getTarget());
                     vector.normalise();
                     
                     // Bullet force
                    float xforce = 1800*vector.x;
                    float yforce = 1800*vector.y;

                    this.getBody().addForce(new Vector2f(xforce, yforce)); 
                     return;
                 }
                 
                double x = Math.toRadians( (this.ticks - 51 * 5)  ); //convert to rad
                float radius;
                if(ticks < 200 || ticks > (360 * 4 + 200 ))
                {
                    radius = 200;
                }
                else
                {
                    radius =   200 + (200 - 200 * (float)Math.cos(Math.toRadians(this.ticks - 200)) );      
                }
       
             
                float xoffset = (float)Math.sin(x) * ( radius + 15 * (float)Math.sin(x/1.5));
                float yoffset = (float)Math.cos(x) * ( radius + 15 * (float)Math.sin(x/1.5));
                this.setPosition(this.sourceEntity.getPosition().x + xoffset, this.sourceEntity.getPosition().y + yoffset);
            
                 
             }
              else if(this.direction == SwirlDirection.seven)
             {

                 if(ticks > 360 * 4 + 200 + 140 )
                 {
                     this.shooting = true;
                     
                     SylverVector2f vector = this.distanceVector(this.sourceEntity.getTarget());
                     vector.normalise();
                     
                     // Bullet force
                    float xforce = 1800*vector.x;
                    float yforce = 1800*vector.y;

                    this.getBody().addForce(new Vector2f(xforce, yforce)); 
                     return;
                 }
                 
                double x = Math.toRadians( (this.ticks - 51 * 6)  ); //convert to rad
                float radius;
                if(ticks < 200 || ticks > (360 * 4 + 200 ))
                {
                    radius = 200;
                }
                else
                {
                    radius =   200 + (200 - 200 * (float)Math.cos(Math.toRadians(this.ticks - 200)) );      
                }
       
             
                float xoffset = (float)Math.sin(x) * ( radius + 15 * (float)Math.sin(x/1.5));
                float yoffset = (float)Math.cos(x) * ( radius + 15 * (float)Math.sin(x/1.5));
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
