
package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import java.util.Random;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.GreenGooEmitter;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.graphics.MultiImageEffect;
import com.silvergobletgames.sylver.graphics.Overlay;
import com.silvergobletgames.sylver.graphics.PointParticleEmitter;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import net.phys2d.raw.shapes.Circle;

/**
 *
 * @author mike
 */
public class EnemySinBlast extends Skill 
{
    
    public EnemySinBlast()
    {
        super(SkillID.EnemySinBlast,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,180,800);
        

    }
    
    public void use(Damage damage, SylverVector2f origin)
    {       
        
        //Damage is scaled with base
        float dAmount = this.getBaseDamage(); 
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.PHYSICAL);
           
        //Determine vector to target
        SylverVector2f distanceVector = user.distanceVector(user.getTarget());
        distanceVector.normalise();


       
        //determine theta
        float theta = (float)Math.acos(distanceVector.dot(new SylverVector2f(1,0)));
        if(user.getTarget().getPosition().y < user.getPosition().y)
            theta = (float)(2* Math.PI - theta);
        
      SinProducer producer = new SinProducer(damage,user.distanceVector(user.getTarget()),user.getTarget().getPosition(),user,origin);
       producer.setPosition(origin.getX(), origin.getY());
       
       user.getOwningScene().add(producer, Layer.MAIN); 
        
        //dispense muzzle flash
        Image muzzleFlash = new Image("flash.png");
        muzzleFlash.setScale(.14f);
        muzzleFlash.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 7, 1, 1));
        muzzleFlash.setRotationPoint(0, .5f);
        muzzleFlash.setAnchor(Anchorable.Anchor.LEFTCENTER);
        muzzleFlash.setColor(new Color(3f,2f,2f,.45f));
        muzzleFlash.setAngle((float)(theta * (180f/Math.PI))); 
        muzzleFlash.setPositionAnchored(origin.x - distanceVector.x * 15, origin.y - distanceVector.y * 15); 
        
        user.getOwningScene().add(muzzleFlash,Scene.Layer.MAIN);
        
        //add smoke
        AbstractParticleEmitter smokeEmitter = new LeadCrystalParticleEmitters.SmokeEmitter();
        smokeEmitter.setPosition(origin.x, origin.y);
        smokeEmitter.setDuration(8);
        smokeEmitter.setParticlesPerFrame(10);
        user.getOwningScene().add(smokeEmitter,Scene.Layer.MAIN);

      
        
        
        

        
    }
    
    private class SinProducer extends Entity
    {
        private long ticks = 0;
        private SylverVector2f origin;
        private Damage passthroughDamage;
        private SylverVector2f vectorToTarget;
        private SylverVector2f targetPosition;
        private CombatEntity caster;
        
        
        public SinProducer(Damage passthroughDamage, SylverVector2f vectorToTarget,SylverVector2f targetPosition, CombatEntity caster,SylverVector2f origin)
        { 
           super( new Image("blank.png"),new Body(new Box(10,10), 1));
           this.getBody().setGravityEffected(false);
           this.getBody().setOverlapMask(Entity.OverlapMasks.NO_OVERLAP.value);
           this.getBody().setBitmask(Entity.BitMasks.NO_COLLISION.value);
           this.passthroughDamage = passthroughDamage;
           this.vectorToTarget = vectorToTarget;
           this.targetPosition = targetPosition;
           this.caster = caster;
           this.origin = origin;
        }
        
        @Override
        public void update()
        {
            this.ticks++;
            
            if(caster.getCombatData().isDead() || !caster.getCombatData().canAttack())
            {
                this.removeFromOwningScene();
                return;
            }
            
            if(this.ticks == 1)
            {
                
                SylverVector2f vectorToTarget = new SylverVector2f(this.vectorToTarget);
                vectorToTarget.normalise();
               
        
                this.shootBullet(vectorToTarget, passthroughDamage, this.targetPosition,caster, origin);
                
               
            }
            else if(this.ticks == 30)
            {
                
                SylverVector2f vectorToTarget = new SylverVector2f(this.vectorToTarget);
                vectorToTarget.normalise();
               
        
                this.shootBullet(vectorToTarget, passthroughDamage, this.targetPosition,caster, origin);
            }
            else if(this.ticks == 20)
            {
                    

            }
            if(this.ticks > 180)
            {
                this.removeFromOwningScene();
            }
            
            
        }
        
        private void shootBullet(SylverVector2f vectorToTarget, Damage d, SylverVector2f targetPosition, CombatEntity caster, SylverVector2f origin)
        {
            //Damage is scaled with base
            Damage damage = new Damage(d);
            
             // Bullet force
            float xforce = 1000*vectorToTarget.x;
            float yforce = 1000*vectorToTarget.y;


            //determine theta
            float theta = (float)Math.acos(vectorToTarget.dot(new SylverVector2f(1,0)));
            if(user.getTarget().getPosition().y < user.getPosition().y)
                theta = (float)(2* Math.PI - theta);
                //body and image and hitbox
            Body body = new Body(new Box(45,45), 1);
            Image img = new Image("poison_goo_ball.png");
            img.setDimensions(50, 50);
            EnemySinHitbox bullet = new EnemySinHitbox(damage, body, img, user, new SylverVector2f(vectorToTarget),1);

            //set bullet rotation
            bullet.getBody().setRotation((float)theta);
            bullet.getImage().setAngle((float)(theta * (180f/Math.PI))); 


            //Dispense bullet into the world
            bullet.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
            bullet.getBody().addForce(new Vector2f(xforce ,yforce));        
            this.getOwningScene().add(bullet,Layer.MAIN);

             //body and image and hitbox
            body = new Body(new Box(50,50), 1);
            img = new Image("poison_goo_ball.png");
            img.setDimensions(50, 50);
            bullet = new EnemySinHitbox(damage, body, img, user, new SylverVector2f(vectorToTarget),-1);

            //set bullet rotation
            bullet.getBody().setRotation((float)theta);
            bullet.getImage().setAngle((float)(theta * (180f/Math.PI))); 


            //Dispense bullet into the world
            bullet.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
            bullet.getBody().addForce(new Vector2f(xforce ,yforce));        
            this.getOwningScene().add(bullet,Layer.MAIN);
            
            //play sound
            Sound sound = Sound.locationSound("buffered/smallLaser.ogg", user.getPosition().x, user.getPosition().y, false, 1f,1f);
            this.getOwningScene().add(sound);

            
        
        }
        
        
    }
    
    
     private static class EnemySinHitbox extends HitBox
    {
        private SylverVector2f vectorToTarget;
        private SylverVector2f perpendicularVector;
        private int ticks = 0;
        private int startDirection = 1;
        
         public EnemySinHitbox(Damage d, Body b, Image i, Entity user, SylverVector2f vector, int startDirection)
         { 
            super(d, b, i, user); 
            
            this.vectorToTarget = vector;
            this.perpendicularVector = new SylverVector2f(-vector.y,vector.x);
            this.getBody().setOverlapMask(Entity.OverlapMasks.PLAYER_TOUCH.value);
            this.getBody().setBitmask(Entity.BitMasks.NO_COLLISION.value);
            this.startDirection = startDirection;
                                
         }
         
         @Override
         public void update()
         {            
             super.update();
            
             this.ticks++;
             
             int magnitude = 8;

             
             float multiplier = (float)Math.sin(Math.toRadians(ticks * 3) + (2 *Math.PI/4)) * magnitude * this.startDirection;
             
             this.getBody().adjustPosition(new Vector2f(perpendicularVector.x, perpendicularVector.y),multiplier);
          
         }

         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 
             
//             if(!(other instanceof HitBox))
//             {
//                 //get angle of impact
//                 Vector2f normalVector = (Vector2f)event.getNormal();
//                 normalVector =normalVector.negate();
//                 Vector2f xVector = new Vector2f(1,0);
//                 float angle = (float)(Math.acos(normalVector.dot(xVector) ) * 180 / Math.PI);
//                 if(normalVector.getY() < 0)
//                     angle += 180;
//                 
//                 //make emitter
//                 PointParticleEmitter emitter = new LeadCrystalParticleEmitters.LaserBitsEmitter();
//                 emitter.setPosition(event.getPoint().getX(), event.getPoint().getY());
//                 emitter.setDuration(1);
//                 emitter.setAngle(angle);
//                 emitter.setParticlesPerFrame(5);
//                 emitter.setColor(new Color((61f/255f) * 4f,(40f/255f) * 4f,(86f/255f) * 4f));
//                 emitter.setSize(3);                
//                 owningScene.add(emitter,Layer.MAIN);
//                 
//             }

                         
//             //remove if we hit a world object, or an enemy
//             if(other instanceof WorldObjectEntity || other instanceof CombatEntity)
//             {
//                 //play sound
//                Sound sound = Sound.locationSound("buffered/smallLaser.ogg", this.getPosition().x, this.getPosition().y, false, .5f,2);
//                this.getOwningScene().add(sound);
//        
//                this.getBody().setVelocity(new Vector2f(0,0));
//                this.removeFromOwningScene();
//             }           
            
         }
         
    }

}


