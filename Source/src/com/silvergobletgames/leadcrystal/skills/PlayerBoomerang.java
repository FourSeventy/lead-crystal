package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.graphics.MultiImageEffect;
import com.silvergobletgames.sylver.graphics.Overlay;
import com.silvergobletgames.sylver.graphics.PointParticleEmitter;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;
import java.util.UUID;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Box;

/**
 *
 * @author Mike
 */
public class PlayerBoomerang extends PlayerSkill 
{
         
    //==============
    // Constructor
    //==============
    public PlayerBoomerang()
    {
        super(SkillID.PlayerBoomerang,SkillType.OFFENSIVE,ExtendedImageAnimations.SPELLATTACK,540,Integer.MAX_VALUE);
        
        //set the skillID and the name
        this.icon = new Image("boomerangIcon.png");
        this.skillName = "Boomerang Throw";
        this.skillDescription = "A boomerang attack flies in an arch, catch the boomerang on the way back to reset the cooldown.";
        this.unlockCost = 1;
        
    }
    

    public void use(Damage damage, SylverVector2f origin)
    {
        
        Random r = SylverRandom.random;
        
        //get targeting data
        TargetingData targetingData = this.getTargetingData(origin);
                        
        //set damage
        int min = 14; 
        int max = 16;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.PHYSICAL);   
        
        //add brightness effect to damage
        damage.addImageEffect(this.getDamageBrightnessEffect());
        
        //build body of the laser
        Body body = new Body(new Box(60,60), .1f);
        Image img = new Image("boomerang.png");
        img.setAnchor(Anchorable.Anchor.LEFTCENTER);
        img.setColor(new Color(1.1f,1.1f,1.1f,1f)); 
        BoomerangHitbox laser = new BoomerangHitbox(damage, body, img, user,targetingData.vectorToTarget,this); 
        
        //add image effect
        Float[] points1 = {1.1f, 1.35f, 1.1f};
        int[] durations1= {15,15};
        ImageEffect blinkEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points1, durations1);
        blinkEffect.setRepeating(true);
        img.addImageEffect(blinkEffect);
        
        //add rotation effect to image
        ImageEffect rotationEffect = new ImageEffect(ImageEffect.ImageEffectType.ROTATION, 60, 0, 360);
        rotationEffect.setRepeating(true);
        img.addImageEffect(rotationEffect);
        
    
        //calculate force for the bullet
        float xforce = 250*targetingData.vectorToTarget.x;
        float yforce = 250*targetingData.vectorToTarget.y;        
        
        //Dispense boomerang into the world
        laser.setPosition(origin.x + targetingData.vectorToTarget.x * 25, origin.y + targetingData.vectorToTarget.y * 25);
        laser.getBody().addForce(new Vector2f(xforce ,yforce));
        laser.getBody().setRotation((float)targetingData.theta);
        laser.getImage().setAngle((float)(targetingData.theta * (180f/Math.PI))); 
        user.getOwningScene().add(laser,Scene.Layer.MAIN);
        
        //make emitter
        PointParticleEmitter emitter = new LeadCrystalParticleEmitters.LaserBitsEmitter();
        emitter.setPosition(origin.x, origin.y);
        emitter.setDuration(1);
        emitter.setAngle(0);
        emitter.setParticlesPerFrame(5);
        emitter.setSize(3);
        emitter.setColor(new Color(1.5f,1.5f,1.5f));
        user.getOwningScene().add(emitter,Scene.Layer.MAIN);


      
        Sound sound = Sound.locationSound("buffered/clang1.ogg", user.getPosition().x, user.getPosition().y, false, 1f,1f);
        user.getOwningScene().add(sound);


    }
    
    public static class BoomerangHitbox extends HitBox
    {
        
        private int ticks = 0;
        private SylverVector2f vectorToTarget;
        private Skill skill;

        
         public BoomerangHitbox(Damage d, Body b, Image i, Entity user, SylverVector2f vector, Skill skill)
         { 
            super(d, b, i, user); 
            
            this.vectorToTarget = vector;            
            this.getBody().removeExcludedBody(this.sourceEntity.getBody());
            this.getBody().setOverlapMask(0b11_0000); 
            this.skill = skill;

         }
         
         @Override
         public void update()
         {
             super.update();
             
             this.ticks++;
             
             if(this.ticks < 140)
             {
                SylverVector2f ehh = vectorToTarget.negate();
                Vector2f vector = new Vector2f(ehh.x *4,ehh.y * 4);
                this.getBody().addForce(vector);
             }
             
             
         }

         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 
             
      

                         
             //remove if we hit a world object, or an enemy
             if(other instanceof WorldObjectEntity)
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
                 emitter.setSize(4);
                 emitter.setColor(new Color(1.5f,1.5f,1.5f));
                 
                 owningScene.add(emitter,Scene.Layer.MAIN);
                 
                 //play sound
                Sound sound = Sound.locationSound("buffered/clang1.ogg", this.getPosition().x, this.getPosition().y, false, .45f,1.7f);
                this.getOwningScene().add(sound);
        
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
             
             if( other == this.sourceEntity)
             {
                //reset cooldown
                 skill.setCooldownRemaining(0);
                 ((GameServerScene)this.getOwningScene()).sendSkillCooldownPacket(UUID.fromString(this.sourceEntity.getID()), skill.getSkillID());
                 
                //add effect
                Image effectImage = new Image("shockwaveParticle.png");
                effectImage.setColor(new Color(1.5f,1.5f,1.5f,.7f));
                effectImage.setAnchor(Anchorable.Anchor.CENTER);
                effectImage.setScale(1.6f);
                effectImage.setPositionAnchored(sourceEntity.getPosition().x, sourceEntity.getPosition().y);
                Float[] points2 = {1.6f, .05f};
                int[] durations2 = {25};     
                effectImage.addImageEffect(new MultiImageEffect(ImageEffect.ImageEffectType.SCALE, points2, durations2));
                Float[] points3 = {1f, 1.5f,1f};
                int[] durations3 = {12,12};     
                effectImage.addImageEffect(new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points3, durations3));
                Overlay o = new Overlay(effectImage, 25, new SylverVector2f(.5f,.5f));
                sourceEntity.getImage().addOverlay(o);
                
                //play sound
                Sound sound = Sound.locationSound("buffered/bigLaser.ogg", this.getPosition().x, this.getPosition().y, false, 1,.5f);
                this.getOwningScene().add(sound);
                 
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
         }
         
    }
}
