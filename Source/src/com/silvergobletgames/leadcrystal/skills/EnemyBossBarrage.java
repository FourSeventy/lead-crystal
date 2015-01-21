package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.graphics.PointParticleEmitter;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Circle;


public class EnemyBossBarrage extends Skill{
    
    public EnemyBossBarrage()
    {
        super(SkillID.EnemyBossBarrage,Skill.SkillType.OFFENSIVE, ExtendedImageAnimations.RANGEDATTACK,60 * 10,1500);
        
    }
    
    
    public void use(Damage damage, SylverVector2f origin)
    {
        
     
       //Damage is scaled with base damage
        float dAmount = this.getBaseDamage(); 
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.PHYSICAL);
       
         //build goo
        Body body = new Body(new Circle(75), 1);
        Image img = new Image("poison_goo_ball.png");
        img.setDimensions(150, 150);
        img.setColor(new Color(0f,0f,2f,1f));
        BarrageHitBox goo = new BarrageHitBox(damage, body, img, user);
        
        
        //Determine vector to target
        SylverVector2f distanceVector = user.distanceVector(user.getTarget());
        distanceVector.normalise();
         
        // Bullet force
        float xforce1 = 700*distanceVector.x;
        float yforce1 = 700*distanceVector.y;

        
        float theta = (float)Math.acos(distanceVector.dot(new SylverVector2f(1,0)));
        if(user.getTarget().getPosition().y < origin.y)
        {
            theta = (float)(2* Math.PI - theta);
        }
        //Dispense goo into the world
        goo.setPosition(origin.x, origin.y);
        goo.getBody().addForce(new Vector2f((int)xforce1 ,(int)yforce1));
        goo.getBody().setRotation((float)theta);
        goo.getImage().setAngle((float)(theta * 180f/Math.PI)); 
        

        user.getOwningScene().add(goo,Layer.MAIN);       

        
        //play sound
        Sound sound = Sound.locationSound("buffered/laser04.ogg", user.getPosition().x, user.getPosition().y, false, 1f,1f);
        user.getOwningScene().add(sound);
            
            
        
    }
    
    private class BarrageHitBox extends HitBox
    {
        
        private long ticks = 0;
        
         public BarrageHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
                                
            this.getBody().setOverlapMask(Entity.OverlapMasks.PLAYER_TOUCH.value);
            this.getBody().setBitmask(Entity.BitMasks.NO_COLLISION.value);
            
         }
         
         @Override
         public void update()
         {
             super.update();
             
             this.ticks++;
                     
             if(ticks % 15 == 0 && this.getOwningScene() != null)
             {
               this.shootBullet(0); 
               this.shootBullet((float)Math.PI/2); 
               this.shootBullet((float)Math.PI); 
               this.shootBullet((float)Math.PI * ((float)3/2)); 
             }
             
             
             
         }
         
         private void shootBullet(float radianOffset)
         {           
              final int radius = 85;
              
              Damage d = new Damage(damage);
              d.getAmountObject().setBase(8);
              //build goo
              Body body = new Body(new Circle(25), 1);
              Image img = new Image("poison_goo_ball.png");
              img.setDimensions(50, 50);
              img.setColor(new Color(0f,0f,2f,1f));
              HitBox goo = new HitBox(d, body, img, user){
              @Override
              public void collidedWith(Entity other, CollisionEvent event)
              {
                  super.collidedWith(other, event);
                  
                  if(other instanceof PlayerEntity)
                  {
                       //get angle of impact
                    Vector2f normalVector = (Vector2f)event.getNormal();
                    normalVector =normalVector.negate();
                    Vector2f xVector = new Vector2f(1,0);
                    float angle = (float)(Math.acos(normalVector.dot(xVector) ) * 180 / Math.PI);
                    if(normalVector.getY() < 0)
                        angle += 180;

                    //make emitter
                    PointParticleEmitter emitter = new LeadCrystalParticleEmitters.LaserBitsEmitter();
                    emitter.setPosition(event.getPoint().getX(), event.getPoint().getY());
                    emitter.setDuration(1);
                    emitter.setAngle(angle);
                    emitter.setParticlesPerFrame(5);
                    emitter.setColor(new Color((50/255f) * 4f,(50/255f) * 4f,(103f/255f) * 4f));
                    emitter.setSize(3);                
                    owningScene.add(emitter,Layer.MAIN);

                   this.getBody().setVelocity(new Vector2f(0,0));
                   this.removeFromOwningScene();
                  }
              }};
              goo.getBody().setOverlapMask(Entity.OverlapMasks.PLAYER_TOUCH.value);
              goo.getBody().setBitmask(Entity.BitMasks.NO_COLLISION.value);


              //Determine vector to target
              double x = Math.toRadians(this.ticks  ) + radianOffset;
             
              float xoffset = (float)Math.sin(x) * ( radius + 15 * (float)Math.sin(x/1.5));
              float yoffset = (float)Math.cos(x) * ( radius + 15 * (float)Math.sin(x/1.5));
              SylverVector2f directionVector = new SylverVector2f(xoffset , yoffset );
              directionVector.normalise();


              // Bullet force //TODO: make it 700 more than velocity
              float xforce1 = 1000*directionVector.x;// + this.getBody().getVelocity().getX() *4;
              float yforce1 = 1000*directionVector.y;// + this.getBody().getVelocity().getY() *4f;


              //Dispense goo into the world
              goo.setPosition(this.getPosition().x + xoffset, this.getPosition().y + yoffset);
              goo.getBody().addForce(new Vector2f((int)xforce1 ,(int)yforce1)); 
              goo.getBody().setVelocity(new Vector2f(this.getBody().getVelocity().getX(),this.getBody().getVelocity().getY()));


              this.getOwningScene().add(goo,Layer.MAIN);  
              
              //play sound
            Sound sound = Sound.locationSound("buffered/smallLaser.ogg", this.getPosition().x, this.getPosition().y, false, .15f,.55f);
            this.getOwningScene().add(sound);
         }
         
         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event);
             
            
                
                         
         }
    }
    

}
