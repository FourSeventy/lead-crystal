
package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.CombatEffect;
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
import com.silvergobletgames.leadcrystal.combat.Damage.DamageType;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.GreenGooEmitter;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import net.phys2d.math.ROVector2f;
import net.phys2d.raw.shapes.Circle;

/**
 *
 * @author mike
 */
public class EnemyFlierGooBomb extends Skill 
{
    
    public EnemyFlierGooBomb()
    {
        super(SkillID.EnemyFlierGooBomb,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,200,800);
    }
    
    public void use(Damage damage, SylverVector2f origin)
    {       
        
        //Damage is scaled with base
        float dAmount = this.getBaseDamage(); 
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.PHYSICAL);
        
        //add slow
        damage.addCombatEffect(new StateEffect(StateEffect.StateEffectType.SLOW, 240,.5f,true));
        
        //build goo
        Body body = new Body(new Circle(24), 1);
        Image img = new Image("poison_goo_dart.png");
        img.setDimensions(50, 50);
        img.setColor(new Color(1f,1.2f,1f,1f));
        GooBombHitBox goo = new GooBombHitBox(damage, body, img, user);
        
        
        //Determine vector to target
        SylverVector2f distanceVector = user.distanceVector(user.getTarget());
        distanceVector.normalise();
         
        // Bullet force
        float xforce1 = 900*distanceVector.x;
        float yforce1 = 900*distanceVector.y;

        
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
        AbstractParticleEmitter emitter = new GreenGooEmitter();
        emitter.setAngle((float)(theta * 180f/Math.PI));
        emitter.setDuration(-1);
        goo.addEmitter(emitter);
        

        user.getOwningScene().add(goo,Layer.MAIN);       

        
        //add sound
        Sound attackSound = Sound.locationSound("buffered/spit1.ogg", origin.x, origin.y, false);               
        user.getOwningScene().add(attackSound);
        
        

        
    }
    
    
    private class GooBombHitBox extends HitBox
    {
         public GooBombHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
                      
         }
         
         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event);
             
             
             //remove if we hit a world object
             if(other instanceof WorldObjectEntity  )
             {
                 //spit out goop 
                Vector2f normalVector =(Vector2f)event.getNormal();
                normalVector = normalVector.negate();
                
                float randomFloat = SylverRandom.random.nextFloat();
                Vector2f normalVector2 = new Vector2f(normalVector.x + .3f + randomFloat/2, normalVector.y);
                normalVector2.normalise();
                
                randomFloat = SylverRandom.random.nextFloat();
                Vector2f normalVector3 = new Vector2f(normalVector.x - .3f - randomFloat/2, normalVector.y);
                normalVector3.normalise();
                
                //Damage is scaled with base
                Damage dam = new Damage(DamageType.PHYSICAL, damage.getAmount()/3);
                //add slow
                dam.addCombatEffect(new StateEffect(StateEffect.StateEffectType.SLOW, 240,.25f,true));

                //build goo
                Body body = new Body(new Circle(10), 1);
                Image img = new Image("poison_goo_ball.png");
                img.setDimensions(20, 20);
                img.setColor(new Color(1f,1.2f,1f,1f));
                GooBitsHitBox goo = new GooBitsHitBox(dam, body, img, user);
                
                Body body2 = new Body(new Circle(10), 1);
                Image img2 = new Image("poison_goo_ball.png");
                img2.setDimensions(20, 20);
                img2.setColor(new Color(1f,1.2f,1f,1f));
                GooBitsHitBox goo2 = new GooBitsHitBox(dam, body2, img2, user);
                
                Body body3 = new Body(new Circle(10), 1);
                Image img3 = new Image("poison_goo_ball.png");
                img3.setDimensions(20, 20);
                img3.setColor(new Color(1f,1.2f,1f,1f));
                GooBitsHitBox goo3 = new GooBitsHitBox(dam, body3, img3, user);


                // Bullet force
                float xforce1 = 1400*normalVector.x + (SylverRandom.random.nextFloat() * 200 );
                float yforce1 = 1400*normalVector.y + (SylverRandom.random.nextFloat() * 200 );
                
                float xforce2 = 1400*normalVector2.x + (SylverRandom.random.nextFloat() * 200 );
                float yforce2 = 1400*normalVector2.y + (SylverRandom.random.nextFloat() * 200 );
                
                float xforce3 = 1400*normalVector3.x + (SylverRandom.random.nextFloat() * 200 );
                float yforce3 = 1400*normalVector3.y + (SylverRandom.random.nextFloat() * 200 );
                
                

                //Dispense goo into the world
                float theta = (float)Math.atan(xforce1/yforce1);
                goo.setPosition(this.getPosition().getX(), this.getPosition().getY()+20);
                goo.getBody().addForce(new Vector2f((int)xforce1 ,(int)yforce1));
                goo.getBody().setRotation((float)theta);
                goo.getImage().setAngle((float)(theta * 180f/Math.PI)); 
                AbstractParticleEmitter emitter = new GreenGooEmitter();
                emitter.setAngle((float)(theta * 180f/Math.PI));
                emitter.setDuration(-1);
                goo.addEmitter(emitter);
                this.getOwningScene().add(goo,Layer.MAIN); 
                
                float theta2 = (float)Math.atan(xforce2/yforce2);
                goo2.setPosition(this.getPosition().getX()+20, this.getPosition().getY()+20);
                goo2.getBody().addForce(new Vector2f((int)xforce2 ,(int)yforce2));
                goo2.getBody().setRotation((float)theta2);
                goo2.getImage().setAngle((float)(theta2 * 180f/Math.PI)); 
                AbstractParticleEmitter emitter2 = new GreenGooEmitter();
                emitter2.setAngle((float)(theta2 * 180f/Math.PI));
                emitter2.setDuration(-1);
                goo2.addEmitter(emitter2);
                this.getOwningScene().add(goo2,Layer.MAIN); 
                
                float theta3 = (float)Math.atan(xforce3/yforce3);
                goo3.setPosition(this.getPosition().getX()-20, this.getPosition().getY()+20);
                goo3.getBody().addForce(new Vector2f((int)xforce3 ,(int)yforce3));
                goo3.getBody().setRotation((float)theta3);
                goo3.getImage().setAngle((float)(theta3 * 180f/Math.PI)); 
                AbstractParticleEmitter emitter3 = new GreenGooEmitter();
                emitter3.setAngle((float)(theta3 * 180f/Math.PI));
                emitter3.setDuration(-1);
                goo3.addEmitter(emitter3);
                this.getOwningScene().add(goo3,Layer.MAIN); 
                
                
                //add sound
                float pitch = (float)(1.9f - (Math.random() * .4f));
                Sound attackSound = Sound.locationSound("buffered/spit1.ogg", this.getPosition().x, this.getPosition().y, false,.8f,pitch);               
                this.getOwningScene().add(attackSound);
              
                 
                 //remove fromt world
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
             else if(other instanceof CombatEntity)
             {
                 //add sound
                float pitch = (float)(1.9f - (Math.random() * .4f));
                Sound attackSound = Sound.locationSound("buffered/spit1.ogg", this.getPosition().x, this.getPosition().y, false,.8f,pitch);               
                this.getOwningScene().add(attackSound);
            
                 this.getBody().setVelocity(new Vector2f(0,0));
                 this.removeFromOwningScene();
             }
             
                
             
         }
    }
    
    private class GooBitsHitBox extends HitBox
    {
         public GooBitsHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
            body.setGravityEffected(true);
                      
         }
         
         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event);
             
             if(!(other instanceof HitBox))
             {
                //add emitter
//                ParticleEmitter emitter =new GreenGooEmitter();
//                emitter.setPosition(event.getPoint().getX(), event.getPoint().getY());
//                emitter.setDuration(5);
//                emitter.setParticlesPerFrame(5);
//                owningScene.add(emitter,Layer.MAIN);
             }
             
             //remove if we hit a world object
             if(other instanceof WorldObjectEntity || other instanceof CombatEntity)
             {
                 //add sound
                float pitch = (float)(1.9f - (Math.random() * .4f));
                Sound attackSound = Sound.locationSound("buffered/spit1.ogg", this.getPosition().x, this.getPosition().y, false,.8f,pitch);               
                this.getOwningScene().add(attackSound);
                
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
             
                
         }
    }

}


