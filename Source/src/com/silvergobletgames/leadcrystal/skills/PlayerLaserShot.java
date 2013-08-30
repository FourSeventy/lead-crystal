package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.CombatEffect;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.ImageEffect.ImageEffectType;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Box;
import com.silvergobletgames.leadcrystal.combat.Damage.DamageType;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect.StateEffectType;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.BlueLaserBitsEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.LaserBitsEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.SmokeEmitter;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;



/**
 * Description: Shoots a laser that damages enemies when struck.
 * Damage: 5-10
 * Cooldown: .5 seconds
 * @author Mike
 */
public class PlayerLaserShot extends Skill{
        
    
    public PlayerLaserShot()
    {
        super(SkillID.PlayerLaser,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,50,Integer.MAX_VALUE);
        
        //set the skillID and the name
        this.icon = new Image("ChargeShotIcon.png");
        this.skillName = "Laser Shot";
        this.skillDescription = "Fast and accurate laser shot with medium damage.";

    }
    

    public void use(Damage damage, SylverVector2f origin)
    {
        
        PlayerEntity player = (PlayerEntity) user;
        Random r = SylverRandom.random;
                        
        //set damage
        int min = 10; 
        int max = 12;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.PHYSICAL);       
        
        //add brightness effect to damage
        Float[] points = {1f,2f,1f};
        int[] durations = {10,5};
        ImageEffect brightnessEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points,durations);
        damage.addImageEffect(brightnessEffect);
        
        //build body of the laser
        Body body = new Body(new Box(57,33), 1);
        Image img = new Image("laserPew.png");
        img.setAnchor(Anchorable.Anchor.LEFTCENTER);
        img.setDimensions(57, 33);
        img.setColor(new Color(1f,1f,1.5f,1f)); 
        LaserHitbox laser = new LaserHitbox(damage, body, img, user); 
        
        //add image effect
        Float[] points1 = {1f, 1.8f, 1f};
        int[] durations1= {15,15};
        ImageEffect blinkEffect = new MultiImageEffect(ImageEffectType.BRIGHTNESS, points1, durations1);
        blinkEffect.setRepeating(true);
        img.addImageEffect(blinkEffect);
        
        //Get target X and Y
        float targetX = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationX;
        float targetY = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationY;
        
        //Get user X and Y
        float userX = origin.x;
        float userY = origin.y;
        
        //get vector to target
        Vector2f vectorToTarget = new Vector2f(targetX - userX, targetY - userY);
        vectorToTarget.normalise();
        
        //calculate force for the bullet
        float xforce = 2500*vectorToTarget.x;
        float yforce = 2500*vectorToTarget.y;
        
        //determine angle for the image
        float theta = (float)Math.acos(vectorToTarget.dot(new Vector2f(1,0)));
        if(targetY < userY)
            theta = (float)(2* Math.PI - theta);
        
        //Dispense laser into the world
        laser.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        laser.getBody().addForce(new Vector2f(xforce ,yforce));
        laser.getBody().setRotation((float)theta);
        laser.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(laser,Layer.MAIN);
        
        //dispense muzzle flash
        Image flash = new Image("flash.png");
        flash.setScale(.14f);
        flash.addImageEffect(new ImageEffect(ImageEffectType.DURATION, 7, 1, 1));
        flash.setRotationPoint(0, .5f);
        flash.setAnchor(Anchorable.Anchor.LEFTCENTER);
        flash.setColor(new Color(3f,2f,2f,.45f));
        flash.setAngle((float)(theta * (180f/Math.PI))); 
        flash.setPositionAnchored(origin.x - vectorToTarget.x * 15, origin.y - vectorToTarget.y * 15); 
        user.getOwningScene().add(flash,Layer.MAIN);
        
        //add smoke
        ParticleEmitter smokeEmitter = new SmokeEmitter();
        smokeEmitter.setPosition(origin.x, origin.y);
        smokeEmitter.setDuration(8);
        smokeEmitter.setParticlesPerFrame(10);
        user.getOwningScene().add(smokeEmitter,Layer.MAIN);

      
         //play sound
        Sound sound = Sound.locationSound("buffered/smallLaser.ogg", user.getPosition().x, user.getPosition().y, false, .8f);
        user.getOwningScene().add(sound);


    }
    
    public static class LaserHitbox extends HitBox
    {

        
         public LaserHitbox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
            
            //label effect for destruction disk
            StateEffect labelEffect = new StateEffect(StateEffectType.LABEL, 1) ;
            labelEffect.setName("playerLaser");
            labelEffect.setInfinite();            
            this.getDamage().addCombatEffect(labelEffect); 
         }

         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 
             
             //laser bits
             if(!(other instanceof HitBox))
             {
                 //get angle of impact
                 Vector2f normalVector = (Vector2f)event.getNormal();
                 normalVector =normalVector.negate();
                 Vector2f xVector = new Vector2f(1,0);
                 float angle = (float)(Math.acos(normalVector.dot(xVector) ) * 180 / Math.PI);
                 if(normalVector.getY() < 0)
                     angle += 180;
                 
                 //make emitter
                 ParticleEmitter emitter = new BlueLaserBitsEmitter();
                 emitter.setPosition(event.getPoint().getX(), event.getPoint().getY());
                 emitter.setDuration(1);
                 emitter.setAngle(angle);
                 emitter.setParticlesPerFrame(5);
                 owningScene.add(emitter,Layer.MAIN);
                 
             }

                         
             //remove if we hit a world object, or an enemy
             if(other instanceof WorldObjectEntity || other instanceof CombatEntity)
             {
                 //play sound
                Sound sound = Sound.locationSound("buffered/smallLaser.ogg", this.getPosition().x, this.getPosition().y, false, .35f,2);
                this.getOwningScene().add(sound);
        
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
         }
         
    }
    
}  

