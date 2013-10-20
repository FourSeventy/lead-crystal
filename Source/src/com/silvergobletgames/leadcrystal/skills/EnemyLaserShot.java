
package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import net.phys2d.math.Vector2f;
import java.util.ArrayList;
import java.util.Random;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Box;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.Damage.DamageType;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.LaserBitsEmitter;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.util.SylverVector2f;


/**
 * Skill that creates our basic projectile entity and fires it into the scene from
 * the user's current location to the mouse clicked location if the user is a player
 * or the user's target if the target is an enemy.
 * @author Justin Capalbo
 */
public class EnemyLaserShot extends Skill{
        
    /**
     * Here we define the skill types and set up other initial params if any
     */
    public EnemyLaserShot()
    {
        super(SkillID.EnemyLaser,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,120,700);


    }
    
    /**
     * Executes this skill
     * @param user 
     */
    public void use(Damage damage, SylverVector2f origin)
    {
        
        //Damage is scaled with weapon damage
        float dAmount =  this.getBaseDamage(); 
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.PHYSICAL);
        
         //add brightness effect to damage
        Float[] points = {1f,2f,1f};
        int[] durations = {10,5};
        ImageEffect brightnessEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points,durations);
        damage.addImageEffect(brightnessEffect);
              

        //build laser
        Body body = new Body(new Box(20,5), 10);
        Image img = new Image("statbar.png");
        img.setDimensions(20, 5);
        img.setColor(new Color(7f,1f,1f,1f));
        LaserHitbox laser = new LaserHitbox(damage, body, img, user);        
        
        //Determine vector to target
        SylverVector2f distanceVector = user.distanceVector(user.getTarget());
        distanceVector.normalise();
         
        // Bullet force
        float xforce = 10000*distanceVector.x;
        float yforce = 10000*distanceVector.y;
       
        //Dispense laser into the world
        float theta = (float)Math.atan(distanceVector.y/distanceVector.x);
        laser.setPosition(origin.x, origin.y);
        laser.getBody().addForce(new Vector2f((int)xforce ,(int)yforce));
        laser.getBody().setRotation((float)theta);
        laser.getImage().setAngle((float)(theta * 180f/Math.PI)); 

        user.getOwningScene().add(laser,Layer.MAIN);
 
        //play sound
    
        
        
    }
    
    private class LaserHitbox extends HitBox
    {
         public LaserHitbox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
         }
         
         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event);
             
             //add emitter
             AbstractParticleEmitter emitter = new LaserBitsEmitter();
             emitter.setPosition(event.getPoint().getX(), event.getPoint().getY());
             emitter.setDuration(1);
             emitter.setParticlesPerFrame(5);
             owningScene.add(emitter,Layer.MAIN);
             
             //remove if we hit a world object or player
             if(other instanceof WorldObjectEntity || other instanceof CombatEntity)
             {
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
             
                
         }
    }
}  
