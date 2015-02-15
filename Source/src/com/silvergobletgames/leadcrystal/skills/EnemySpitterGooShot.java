
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
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.GreenGooEmitter;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.util.SylverVector2f;

/**
 *
 * @author mike
 */
public class EnemySpitterGooShot extends Skill 
{
    
    public EnemySpitterGooShot()
    {
        super(SkillID.EnemyGooShot,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,120,950);
        

    }
    
    public void use(Damage damage, SylverVector2f origin)
    {       
        
        //Damage is scaled with base
        float dAmount = this.getBaseDamage() * .33f; 
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.PHYSICAL);
        
        //build goo
        Body body = new Body(new Box(23,23), 10);
        Image img = new Image("poison_goo_ball.png");
        img.setDimensions(25, 25);
        img.setColor(new Color(1f,1.2f,1f,1f));
        GooHitBox goo1 = new GooHitBox(new Damage(damage), body, img, user);
        
        body = new Body(new Box(23,23), 10);
        img = new Image("poison_goo_ball.png");
        img.setDimensions(25, 25);
        img.setColor(new Color(1f,1.2f,1f,1f));
        GooHitBox goo2 = new GooHitBox(new Damage(damage), body, img, user);
        
        body = new Body(new Box(23,23), 10);
        img = new Image("poison_goo_ball.png");
        img.setDimensions(25, 25);
        img.setColor(new Color(1f,1.2f,1f,1f));
        GooHitBox goo3 = new GooHitBox(new Damage(damage), body, img, user);
        
        
        
        //Determine vector to target
        SylverVector2f distanceVector = user.distanceVector(user.getTarget());
        distanceVector.normalise();
        
        Vector2f upVector = new Vector2f((float)((.939)*distanceVector.x  - (.342)*distanceVector.y ),(float)((.342)*distanceVector.x  + (.939)*distanceVector.y ));
        upVector.normalise();
        
        Vector2f downVector = new Vector2f((float)((.939)*distanceVector.x  + (.342)*distanceVector.y),(float)(-(.342)*distanceVector.x  + (.939)*distanceVector.y ));
        downVector.normalise();
         
        // Bullet force
        float xforce1 = 10000*distanceVector.x;
        float yforce1 = 10000*distanceVector.y;
        
        float xforce2 = 10000*upVector.x;
        float yforce2 = 10000*upVector.y;
        
        float xforce3 = 10000*downVector.x;
        float yforce3 = 10000*downVector.y;
       
        //Dispense goo into the world
        float theta = (float)Math.atan(xforce1/yforce1);
        goo1.setPosition(origin.x, origin.y);
        goo1.getBody().addForce(new Vector2f((int)xforce1 ,(int)yforce1));
        goo1.getBody().setRotation((float)theta);
        goo1.getImage().setAngle((float)(theta * 180f/Math.PI)); 
        AbstractParticleEmitter emitter = new GreenGooEmitter();
        emitter.setAngle((float)(theta * 180f/Math.PI));
        emitter.setDuration(-1);
        goo1.addEmitter(emitter);
        
        theta = (float)Math.atan(xforce2/yforce2);
        goo2.setPosition(origin.x, origin.y);
        goo2.getBody().addForce(new Vector2f((int)xforce2 ,(int)yforce2));
        goo2.getBody().setRotation((float)theta);
        goo2.getImage().setAngle((float)(theta * 180f/Math.PI)); 
        emitter = new GreenGooEmitter();
        emitter.setAngle((float)(theta * 180f/Math.PI));
        emitter.setDuration(-1);
        goo2.addEmitter(emitter);
        
        theta = (float)Math.atan(xforce3/yforce3);
        goo3.setPosition(origin.x, origin.y);
        goo3.getBody().addForce(new Vector2f((int)xforce3 ,(int)yforce3));
        goo3.getBody().setRotation((float)theta);
        goo3.getImage().setAngle((float)(theta * 180f/Math.PI)); 
        emitter = new GreenGooEmitter();
        emitter.setAngle((float)(theta * 180f/Math.PI));
        emitter.setDuration(-1);
        goo3.addEmitter(emitter);

        user.getOwningScene().add(goo1,Layer.MAIN);       
        user.getOwningScene().add(goo2,Layer.MAIN);       
        user.getOwningScene().add(goo3,Layer.MAIN);
        
        //add sound
        Sound attackSound = Sound.locationSound("buffered/spit1.ogg", origin.x, origin.y, false, .80f);               
        user.getOwningScene().add(attackSound);
              
    }
    
    
    private class GooHitBox extends HitBox
    {
         public GooHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
                      
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
                Sound attackSound = Sound.locationSound("buffered/spit1.ogg", this.getPosition().x, this.getPosition().y, false,.45f,pitch);               
                this.getOwningScene().add(attackSound);
                
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
             
                
         }
    }

}


