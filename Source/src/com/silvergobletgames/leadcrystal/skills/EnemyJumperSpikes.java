
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
public class EnemyJumperSpikes extends Skill 
{
    
    public EnemyJumperSpikes()
    {
        super(SkillID.EnemyJumperSpikes,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,180,475);
        

    }
    
    public void use(Damage damage, SylverVector2f origin)
    {       
        
        //Damage is scaled with base
        float dAmount = this.getBaseDamage() * .25f; 
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.PHYSICAL);
        
        //build goo
        Body body = new Body(new Box(20,20), 10);
        Image img = new Image("jumper_spike.png");
        //img.setDimensions(20, 20);
        img.setColor(new Color(1f,1.6f,1f,1f));
        SpikeHitBox goo1 = new SpikeHitBox(damage, body, img, user);
        
        body = new Body(new Box(20,20), 10);
        img = new Image("jumper_spike.png");
       // img.setDimensions(20, 20);
        img.setColor(new Color(1f,1.6f,1f,1f));
        SpikeHitBox goo2 = new SpikeHitBox(damage, body, img, user);
        
        body = new Body(new Box(20,20), 10);
        img = new Image("jumper_spike.png");
       // img.setDimensions(20, 20);
        img.setColor(new Color(1f,1.6f,1f,1f));
        SpikeHitBox goo3 = new SpikeHitBox(damage, body, img, user);
        
        body = new Body(new Box(20,20), 10);
        img = new Image("jumper_spike.png");
       // img.setDimensions(20, 20);
        img.setColor(new Color(1f,1.6f,1f,1f));
        SpikeHitBox goo4 = new SpikeHitBox(damage, body, img, user);
        
        
        
        
        //Determine vector to target
        SylverVector2f upRightVector = new SylverVector2f(1,1);
        upRightVector.normalise();
        
        Vector2f upLeftVector = new Vector2f(-1,1);
        upLeftVector.normalise();
        
        Vector2f downLeftVector = new Vector2f(-1,-1);
        downLeftVector.normalise();
        
        Vector2f downRightVector = new Vector2f(1,-1);
        downRightVector.normalise();
         
        // Bullet force
        float xforce1 = 10000*upRightVector.x;
        float yforce1 = 10000*upRightVector.y;
        
        float xforce2 = 10000*upLeftVector.x;
        float yforce2 = 10000*upLeftVector.y;
        
        float xforce3 = 10000*downLeftVector.x;
        float yforce3 = 10000*downLeftVector.y;
        
        float xforce4 = 10000*downRightVector.x;
        float yforce4 = 10000*downRightVector.y;
       
        //Dispense goo into the world
        float theta = (float)Math.atan(xforce1/yforce1);
        goo1.setPosition(origin.x, origin.y);
        goo1.getBody().addForce(new Vector2f((int)xforce1 ,(int)yforce1));
        goo1.getBody().setRotation((float)theta + 1.5f);
        goo1.getImage().setAngle((float)(theta * 180f/Math.PI)); 
        AbstractParticleEmitter emitter = new GreenGooEmitter();
        emitter.setAngle((float)(theta * 180f/Math.PI));
        emitter.setDuration(-1);
        goo1.addEmitter(emitter);
        
        theta = (float)Math.atan(xforce2/yforce2);
        goo2.setPosition(origin.x, origin.y);
        goo2.getBody().addForce(new Vector2f((int)xforce2 ,(int)yforce2));
        goo2.getBody().setRotation((float)theta + 1.5f);
        goo2.getImage().setAngle((float)(theta * 180f/Math.PI)); 
        emitter = new GreenGooEmitter();
        emitter.setAngle((float)(theta * 180f/Math.PI));
        emitter.setDuration(-1);
        goo2.addEmitter(emitter);
        
        theta = (float)Math.atan(xforce3/yforce3);
        goo3.setPosition(origin.x, origin.y);
        goo3.getBody().addForce(new Vector2f((int)xforce3 ,(int)yforce3));
        goo3.getBody().setRotation((float)theta + 1.5f);
        goo3.getImage().setAngle((float)(theta * 180f/Math.PI)); 
        emitter = new GreenGooEmitter();
        emitter.setAngle((float)(theta * 180f/Math.PI));
        emitter.setDuration(-1);
        goo3.addEmitter(emitter);
        
        theta = (float)Math.atan(xforce4/yforce4);
        goo4.setPosition(origin.x, origin.y);
        goo4.getBody().addForce(new Vector2f((int)xforce4 ,(int)yforce4));
        goo4.getBody().setRotation((float)theta + 1.5f);
        goo4.getImage().setAngle((float)(theta * 180f/Math.PI)); 
        emitter = new GreenGooEmitter();
        emitter.setAngle((float)(theta * 180f/Math.PI));
        emitter.setDuration(-1);
        goo4.addEmitter(emitter);

        user.getOwningScene().add(goo1,Layer.MAIN);       
        user.getOwningScene().add(goo2,Layer.MAIN);       
        user.getOwningScene().add(goo3,Layer.MAIN);
        user.getOwningScene().add(goo4,Layer.MAIN);
        
        //add sound
        Sound attackSound = Sound.locationSound("buffered/spit1.ogg", origin.x, origin.y, false);               
        user.getOwningScene().add(attackSound);
        

        
    }
    
    
    private class SpikeHitBox extends HitBox
    {
         public SpikeHitBox(Damage d, Body b, Image i, Entity user)
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
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
             
                
         }
    }

}


