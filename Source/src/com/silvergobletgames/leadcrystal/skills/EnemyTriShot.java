
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
import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.PointParticleEmitter;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;

/**
 *
 * @author mike
 */
public class EnemyTriShot extends Skill 
{
    
    public EnemyTriShot()
    {
        super(SkillID.EnemyTriShot,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,120,800);
        

    }
    
    public void use(Damage damage, SylverVector2f origin)
    {       
        
        float dAmount = this.getBaseDamage() * .33f; 
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.PHYSICAL);
        
        
     
       TriShotProducer producer = new TriShotProducer(damage,user.distanceVector(user.getTarget()),user.getTarget().getPosition(),user,origin);
       producer.setPosition(origin.getX(), origin.getY());
       
       user.getOwningScene().add(producer, Layer.MAIN); 
       
   
    }
    
    private class TriShotProducer extends Entity
    {
        private long ticks = 0;
        private SylverVector2f origin;
        private Damage passthroughDamage;
        private SylverVector2f vectorToTarget;
        private SylverVector2f targetPosition;
        private CombatEntity caster;
        
        
        public TriShotProducer(Damage passthroughDamage, SylverVector2f vectorToTarget,SylverVector2f targetPosition, CombatEntity caster,SylverVector2f origin)
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
            
            
            if(this.ticks == 1)
            {
                
                SylverVector2f vectorToTarget = new SylverVector2f(this.vectorToTarget);
                vectorToTarget.normalise();
                float degrees = Math.copySign(SylverRandom.random.nextInt(4), vectorToTarget.getX());
                
                SylverVector2f upVector = new SylverVector2f((float)(Math.cos(degrees * Math.PI/180) *vectorToTarget.x  - (Math.sin(degrees * Math.PI/180))*vectorToTarget.y ),(float)((Math.sin(degrees * Math.PI/180))*vectorToTarget.x  + (Math.cos(degrees * Math.PI/180))*vectorToTarget.y )); // 5 degreees
                upVector.normalise();
        
                this.shootBullet(upVector, passthroughDamage, this.targetPosition,caster, origin);
                
               
            }
            else if(this.ticks == 10)
            {
                
                 SylverVector2f vectorToTarget = new SylverVector2f(this.vectorToTarget);
                vectorToTarget.normalise();
                int degrees = 0;
                
                SylverVector2f upVector = new SylverVector2f((float)(Math.cos(degrees * Math.PI/180) *vectorToTarget.x  - (Math.sin(degrees * Math.PI/180))*vectorToTarget.y ),(float)((Math.sin(degrees * Math.PI/180))*vectorToTarget.x  + (Math.cos(degrees * Math.PI/180))*vectorToTarget.y )); // 5 degreees
                upVector.normalise();
        
                this.shootBullet(upVector, passthroughDamage, this.targetPosition, caster, origin);
            }
            else if(this.ticks == 20)
            {
                    
                 SylverVector2f vectorToTarget = new SylverVector2f(this.vectorToTarget);
                vectorToTarget.normalise();
                float degrees = - Math.copySign(SylverRandom.random.nextInt(4), vectorToTarget.getX())  ;              
                
                SylverVector2f upVector = new SylverVector2f((float)(Math.cos(degrees * Math.PI/180) *vectorToTarget.x  - (Math.sin(degrees * Math.PI/180))*vectorToTarget.y ),(float)((Math.sin(degrees * Math.PI/180))*vectorToTarget.x  + (Math.cos(degrees * Math.PI/180))*vectorToTarget.y )); // 5 degreees
                upVector.normalise();
        
                this.shootBullet(upVector, passthroughDamage, this.targetPosition,caster, origin);
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

            //build goo
            Body body = new Body(new Box(57,33), 10);
            Image img = new Image("laserPewEnemy.png");
            img.setAnchor(Anchorable.Anchor.LEFTCENTER);
            img.setDimensions(57, 33);
            img.setColor(new Color(1.5f,1f,1f,1f)); 
            TriShotHitBox goo1 = new TriShotHitBox(damage, body, img, caster);                   
            
             // Bullet force
            float xforce1 = 15000*vectorToTarget.x;
            float yforce1 = 15000*vectorToTarget.y;
            
            SylverVector2f myPos = caster.getPosition();
            if( myPos.distance(vectorToTarget)< myPos.distance(origin))
            {
                vectorToTarget = vectorToTarget.negate();
            }

            float theta = (float)Math.acos(vectorToTarget.dot(new SylverVector2f(1,0)));
            if(vectorToTarget.y < 0)
            theta = (float)(2* Math.PI - theta);
        
            goo1.setPosition(origin.x, origin.y);
            goo1.getBody().addForce(new Vector2f((int)xforce1 ,(int)yforce1));
            goo1.getBody().setRotation((float)theta);
            goo1.getImage().setAngle((float)(theta * 180f/Math.PI)); 
            this.getOwningScene().add(goo1,Layer.MAIN);  
            
            //play sound
            Sound sound = Sound.locationSound("buffered/smallLaser.ogg", user.getPosition().x, user.getPosition().y, false, 1f,1f);
            this.getOwningScene().add(sound);
        
        }
        
        
    }
    
    private class TriShotHitBox extends HitBox
    {
         public TriShotHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
                      
         }
         
         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event);
             
             //remove if we hit a world object
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


