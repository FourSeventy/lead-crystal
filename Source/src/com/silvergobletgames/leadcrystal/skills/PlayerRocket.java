package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Circle;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.RocketExplosionEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.RocketSmokeEmitter;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.ImageEffect.ImageEffectType;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;
import java.util.UUID;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;

/**
 * 
 * Name: Guard
 * Cost:
 * Cooldown: 30 seconds
 * Description: While active, you take 50% damage.  For the first second that Guard is active, you take no damage.
 */
public class PlayerRocket extends Skill
{
    
    public PlayerRocket()
    {
        //super constructor
        super(SkillID.PlayerRocket,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,50,Integer.MAX_VALUE);

        //set the skillID and the name
        this.icon = new Image("rocket.png");
        this.skillName = "Rocket Launcher";
        this.skillDescription = "Shoots a rocket that explodes and does AOE damage.";
        
        this.unlockCost = 3;
        
    }
    
    public void use(Damage damage, SylverVector2f origin) 
    {
        //get player and random references
        PlayerEntity player = (PlayerEntity) user;
        Random r = SylverRandom.random;
                        
        //calculate damage
        int min = 14; 
        int max = 16;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout); 
        damage.setType(Damage.DamageType.NODAMAGE); //this toggles back in the explosion
        
        //add brightness effect to damage
        Float[] points = {1f,2f,1f};
        int[] durations = {10,5};
        ImageEffect brightnessEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points,durations);
        damage.addImageEffect(brightnessEffect);
        
        //============================
        // Calculate Vector To Target
        //============================
        
        //Get target X and Y
        float targetX = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationX;
        float targetY = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationY;
        
        //Get user X and Y
        float userX = origin.x;
        float userY = origin.y;
        
        //get vector to target
        Vector2f vectorToTarget = new Vector2f(targetX - userX, targetY - userY);
        vectorToTarget.normalise();
        
        //=====================
        // Build Rocket Hitbox
        //=====================
        
        //body and image
        Body body = new Body(new Box(50,30), 1);
        Image img = new Image("rocket.png");
        img.setDimensions(40, 20);
        img.setScale(1.2f);
         
        //construct hitbox
        RocketHitbox rocket = new RocketHitbox(damage, body, img, user);
        
        //determine angle for the image
        float theta = (float)Math.acos(vectorToTarget.dot(new Vector2f(1,0)));
        if(targetY < userY)
            theta = (float)(2* Math.PI - theta);
        
        //set rocket image
        rocket.getBody().setRotation((float)theta);
        rocket.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        
        //add smoke effect
        ParticleEmitter rocketEmitter = new RocketSmokeEmitter();
        rocketEmitter.setAngle(180);
        rocket.addEmitter(rocketEmitter);

        //add rocket engine effect
        Image flash = new Image("flash.png");
        flash.setScale(.05f);
        
        //add image effect
        Float[] points1 = {1f, 2f, 1f};
        int[] durations1= {15,15};
        ImageEffect flashEffect = new MultiImageEffect(ImageEffectType.BRIGHTNESS, points1, durations1);
        flashEffect.setRepeating(true);
        flash.addImageEffect(flashEffect);
        flash.setRotationPoint(1.5f, .5f);
        flash.setAnchor(Anchorable.Anchor.RIGHTCENTER);
        flash.setHorizontalFlip(true);
        flash.setColor(new Color(3f,2f,2f,.45f));
        flash.setAngle(rocket.getImage().getAngle()); 
        Overlay o = new Overlay(flash, -1, new SylverVector2f(.1f,.5f));
        o.setInfiniteDuration();
        rocket.getImage().addOverlay(o);
        
        
      
        //===========================
        // Dispense Rocket Into World
        //===========================
               
        //calculate force for the rocket
        float xforce = 1800*vectorToTarget.x;
        float yforce = 1800*vectorToTarget.y;
            
        
        //Dispense rocket into the world
        rocket.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        rocket.getBody().addForce(new Vector2f(xforce ,yforce));        
        user.getOwningScene().add(rocket,Layer.MAIN);
        
        //dispense muzzle flash
        Image muzzleFlash = new Image("flash.png");
        muzzleFlash.setScale(.14f);
        muzzleFlash.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 7, 1, 1));
        muzzleFlash.setRotationPoint(0, .5f);
        muzzleFlash.setAnchor(Anchorable.Anchor.LEFTCENTER);
        muzzleFlash.setColor(new Color(3f,2f,2f,.45f));
        muzzleFlash.setAngle((float)(theta * (180f/Math.PI))); 
        muzzleFlash.setPositionAnchored(origin.x - vectorToTarget.x * 15, origin.y - vectorToTarget.y * 15); 
        user.getOwningScene().add(muzzleFlash,Layer.MAIN);
        
        //add smoke
//        ParticleEmitter smokeEmitter = new ConcreteParticleEmitters.SmokeEmitter();
//        smokeEmitter.setPosition(origin.x, origin.y);
//        smokeEmitter.setDuration(8);
//        smokeEmitter.setParticlesPerFrame(10);
//        user.getOwningScene().add(smokeEmitter,Layer.MAIN);

      
        //play sound
        
    }

    public static class RocketHitbox extends HitBox
    {

        
         public RocketHitbox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
                       
            //label effect for destruction disk
            StateEffect labelEffect = new StateEffect(StateEffect.StateEffectType.LABEL, 1) ;
            labelEffect.setName("playerLaser");
            labelEffect.setInfinite();            
            this.getDamage().addCombatEffect(labelEffect);
            
            
         }

         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 
             
                         
             //remove if we hit a world object, or an enemy
             if(other instanceof WorldObjectEntity || other instanceof CombatEntity)
             {
                 //===============================
                 //add explosion hitbox and effects 
                 //================================
                 
                 //explosion hitbox
                 Image img = new Image("gradientCircle.png");  
                 img.setDimensions(85, 85);
                 img.setColor(new Color(3,3,1,1));
                 img.addImageEffect(new ImageEffect(ImageEffectType.SCALE, 60, 1, 0f));
                 Body beh = new StaticBody(new Circle(75));
                 beh.setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);
                 beh.setBitmask(Entity.BitMasks.NO_COLLISION.value);
                 damage.setType(Damage.DamageType.PHYSICAL);   
                 HitBox explosionHitbox = new HitBox(damage, beh, img, sourceEntity);
                 explosionHitbox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 60, 0, 0));
                 explosionHitbox.setPosition(this.getPosition().x, this.getPosition().y);
                 this.getOwningScene().add(explosionHitbox, Layer.MAIN);
                 
                 //explosion particle emitter
                 ParticleEmitter explosionEmitter = new RocketExplosionEmitter();
                 explosionEmitter.setPosition(this.getPosition().x, this.getPosition().y);
                 explosionEmitter.setDuration(10);
                 this.getOwningScene().add(explosionEmitter, Layer.ATTACHED_FG);
                 
                 //flying chunks 
                 
                 
                 //remove this hitbox
                 this.getBody().setVelocity(new Vector2f(0,0));
                 this.removeFromOwningScene();
             }
         }
         
    }
}
