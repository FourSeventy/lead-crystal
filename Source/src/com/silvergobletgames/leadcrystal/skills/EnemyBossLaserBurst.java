
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
public class EnemyBossLaserBurst extends Skill 
{
    
    public EnemyBossLaserBurst()
    {
        super(SkillID.EnemyBossLaserBurst,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,4 * 60, 800);
        

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
        
         LaserBurstProducer producer = new LaserBurstProducer(damage,distanceVector,new SylverVector2f(user.getTarget().getPosition()),user,origin);
       producer.setPosition(origin.getX(), origin.getY());
       
       user.getOwningScene().add(producer, Layer.MAIN); 
       
        float theta = (float)Math.acos(distanceVector.dot(new SylverVector2f(1,0)));
           if(user.getTarget().getPosition().y < user.getPosition().y)
               theta = (float)(2* Math.PI - theta);
           
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
    
    private class LaserBurstProducer extends Entity
    {
        private long ticks = 0;
        private SylverVector2f origin;
        private Damage passthroughDamage;
        private SylverVector2f vectorToTarget;
        private SylverVector2f targetPosition;
        private CombatEntity caster;
        
        
        public LaserBurstProducer(Damage passthroughDamage, SylverVector2f vectorToTarget,SylverVector2f targetPosition, CombatEntity caster,SylverVector2f origin)
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
                float degrees = Math.copySign(SylverRandom.random.nextInt(20), Math.random()>.5?1:-1);
                
                SylverVector2f upVector = new SylverVector2f((float)(Math.cos(degrees * Math.PI/180) *vectorToTarget.x  - (Math.sin(degrees * Math.PI/180))*vectorToTarget.y ),(float)((Math.sin(degrees * Math.PI/180))*vectorToTarget.x  + (Math.cos(degrees * Math.PI/180))*vectorToTarget.y )); // 5 degreees
                upVector.normalise();
        
                this.shootBullet(upVector, passthroughDamage, this.targetPosition,caster, origin);
                
               
            }
            else if(this.ticks == 15)
            {
                
                 SylverVector2f vectorToTarget = new SylverVector2f(this.vectorToTarget);
                vectorToTarget.normalise();
                float degrees =  Math.copySign(SylverRandom.random.nextInt(20), Math.random()>.5?1:-1);
                
                SylverVector2f upVector = new SylverVector2f((float)(Math.cos(degrees * Math.PI/180) *vectorToTarget.x  - (Math.sin(degrees * Math.PI/180))*vectorToTarget.y ),(float)((Math.sin(degrees * Math.PI/180))*vectorToTarget.x  + (Math.cos(degrees * Math.PI/180))*vectorToTarget.y )); // 5 degreees
                upVector.normalise();
        
                this.shootBullet(upVector, passthroughDamage, this.targetPosition, caster, origin);
            }
            else if(this.ticks == 30)
            {
                    
                 SylverVector2f vectorToTarget = new SylverVector2f(this.vectorToTarget);
                vectorToTarget.normalise();
                float degrees = - Math.copySign(SylverRandom.random.nextInt(30), Math.random()>.5?1:-1)  ;              
                
                SylverVector2f upVector = new SylverVector2f((float)(Math.cos(degrees * Math.PI/180) *vectorToTarget.x  - (Math.sin(degrees * Math.PI/180))*vectorToTarget.y ),(float)((Math.sin(degrees * Math.PI/180))*vectorToTarget.x  + (Math.cos(degrees * Math.PI/180))*vectorToTarget.y )); // 5 degreees
                upVector.normalise();
        
                this.shootBullet(upVector, passthroughDamage, this.targetPosition,caster, origin);
            }
            else if (this.ticks == 45)
            {
                SylverVector2f vectorToTarget = new SylverVector2f(this.vectorToTarget);
                vectorToTarget.normalise();
                float degrees = - Math.copySign(SylverRandom.random.nextInt(30), Math.random()>.5?1:-1)  ;              
                
                SylverVector2f upVector = new SylverVector2f((float)(Math.cos(degrees * Math.PI/180) *vectorToTarget.x  - (Math.sin(degrees * Math.PI/180))*vectorToTarget.y ),(float)((Math.sin(degrees * Math.PI/180))*vectorToTarget.x  + (Math.cos(degrees * Math.PI/180))*vectorToTarget.y )); // 5 degreees
                upVector.normalise();
        
                this.shootBullet(upVector, passthroughDamage, this.targetPosition,caster, origin);
            }
            else if (this.ticks == 60)
            {
                SylverVector2f vectorToTarget = new SylverVector2f(this.vectorToTarget);
                vectorToTarget.normalise();
                float degrees = - Math.copySign(SylverRandom.random.nextInt(30), Math.random()>.5?1:-1)  ;              
                
                SylverVector2f upVector = new SylverVector2f((float)(Math.cos(degrees * Math.PI/180) *vectorToTarget.x  - (Math.sin(degrees * Math.PI/180))*vectorToTarget.y ),(float)((Math.sin(degrees * Math.PI/180))*vectorToTarget.x  + (Math.cos(degrees * Math.PI/180))*vectorToTarget.y )); // 5 degreees
                upVector.normalise();
        
                this.shootBullet(upVector, passthroughDamage, this.targetPosition,caster, origin);
            }
            if(this.ticks > 180)
            {
                this.removeFromOwningScene();
            }
            
            
        }
        
        private void shootBullet(SylverVector2f vector, Damage d, SylverVector2f targetPosition, CombatEntity caster, SylverVector2f origin)
        {
            //Damage is scaled with base
            Damage damage = new Damage(d);

           // Bullet force
           float xforce = 15000*vector.x;
           float yforce = 15000*vector.y;


           //Dispense goo into the world
           float theta = (float)Math.acos(vector.dot(new SylverVector2f(1,0)));
           if(vector.y  < 0)
               theta = (float)(2* Math.PI - theta);

            Body body = new Body(new Box(75,44), 10);
            Image img = new Image("rocket_fire.png");
            img.setAnchor(Anchorable.Anchor.LEFTCENTER);
            img.setDimensions(82, 48);
            img.setColor(new Color(1.5f,1f,1f,1f)); 
             //add image effect
           Float[] points1 = {1f, 1.8f, 1f};
           int[] durations1= {15,15};
           ImageEffect flashEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points1, durations1);
           flashEffect.setRepeating(true);
           img.addImageEffect(flashEffect);

           //construct hitbox
           EnemyLaserHitbox rocket = new EnemyLaserHitbox(damage, body, img, user);


           //set rocket image
           rocket.getBody().setRotation((float)theta);
           rocket.getImage().setAngle((float)(theta * (180f/Math.PI))); 

           //add smoke effect
           AbstractParticleEmitter rocketEmitter = new LeadCrystalParticleEmitters.RocketSmokeEmitter();
           rocketEmitter.setAngle(180);
           rocket.addEmitter(rocketEmitter);

        
           //Dispense rocket into the world
           rocket.setPosition(origin.x + vector.x * 25, origin.y + vector.y * 25);
           rocket.getBody().addForce(new Vector2f(xforce ,yforce));        
           this.getOwningScene().add(rocket,Layer.MAIN); 
        
           //play sound
            Sound sound = Sound.locationSound("buffered/smallLaser.ogg", user.getPosition().x, user.getPosition().y, false, 1f,1f);
            this.getOwningScene().add(sound);
        }
        
        
    }
    
     private static class EnemyLaserHitbox extends HitBox
    {

        
         public EnemyLaserHitbox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
                       
            
            
         }

         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 
             
                         
             //remove if we hit a world object, or an enemy
             if(other instanceof WorldObjectEntity || other instanceof CombatEntity)
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
                 emitter.setColor(new Color((103/255f) * 4f,(50/255f) * 4f,(50f/255f) * 4f));
                 emitter.setSize(3);                
                 owningScene.add(emitter,Layer.MAIN);
                 
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
                
             }
         }
         
    }

}


