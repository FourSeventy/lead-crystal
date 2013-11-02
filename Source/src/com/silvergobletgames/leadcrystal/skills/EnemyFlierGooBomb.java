
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
        
        //build goo
        Body body = new Body(new Circle(22), 1);
        Image img = new Image("plantSpit.png");
        img.setDimensions(45, 45);
        img.setColor(new Color(1f,1.6f,1f,1f));
        GooBombHitBox goo = new GooBombHitBox(damage, body, img, user);
        
        
        //Determine vector to target
        SylverVector2f distanceVector = user.distanceVector(user.getTarget());
        distanceVector.normalise();
         
        // Bullet force
        float xforce1 = 700*distanceVector.x;
        float yforce1 = 700*distanceVector.y;

        //Dispense goo into the world
        float theta = (float)Math.atan(xforce1/yforce1);
        goo.setPosition(origin.x, origin.y);
        goo.getBody().addForce(new Vector2f((int)xforce1 ,(int)yforce1));
        goo.getBody().setRotation((float)theta);
        goo.getImage().setAngle((float)(theta * 180f/Math.PI)); 
        AbstractParticleEmitter emitter = new GreenGooEmitter();
        emitter.setAngle((float)(theta * 180f/Math.PI));
        emitter.setDuration(-1);
        goo.addEmitter(emitter);
        

        user.getOwningScene().add(goo,Layer.MAIN);       

        
        //play sound
        
        

        
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
             
             //determine if we hit a player hitbox
             boolean playerHitbox = false;
             if(other instanceof HitBox)
             {
                if(!((HitBox)other).getDamage().getCombatEffects().isEmpty())
                 {
                     CombatEffect labelEffect = ((HitBox)other).getDamage().getCombatEffects().get(0);
                     
                     if(labelEffect instanceof StateEffect)
                     {
                         if (((StateEffect)labelEffect).getStateEffectType() == StateEffect.StateEffectType.LABEL)
                         {
                             if(((StateEffect)labelEffect).getName().equals("playerLaser"))
                             {
                                 playerHitbox = true;
                             }
                         }
                     }
                 }
             }
             
             //remove if we hit a world object
             if(other instanceof WorldObjectEntity || playerHitbox )
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
                Damage dam = new Damage(DamageType.PHYSICAL, damage.getAmount()/2);

                //build goo
                Body body = new Body(new Circle(10), 1);
                Image img = new Image("plantSpit.png");
                img.setDimensions(20, 20);
                img.setColor(new Color(1f,1.6f,1f,1f));
                GooBitsHitBox goo = new GooBitsHitBox(dam, body, img, user);
                
                Body body2 = new Body(new Circle(10), 1);
                Image img2 = new Image("plantSpit.png");
                img2.setDimensions(20, 20);
                img2.setColor(new Color(1f,1.6f,1f,1f));
                GooBitsHitBox goo2 = new GooBitsHitBox(dam, body2, img2, user);
                
                Body body3 = new Body(new Circle(10), 1);
                Image img3 = new Image("plantSpit.png");
                img3.setDimensions(20, 20);
                img3.setColor(new Color(1f,1.6f,1f,1f));
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
                
                
                
              
                 
                 //remove fromt world
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
             else if(other instanceof CombatEntity)
             {
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
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
             
                
         }
    }

}


