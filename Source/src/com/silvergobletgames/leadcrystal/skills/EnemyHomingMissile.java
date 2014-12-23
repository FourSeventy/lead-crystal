
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
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import net.phys2d.raw.shapes.Circle;

/**
 *
 * @author mike
 */
public class EnemyHomingMissile extends Skill 
{
    
    public EnemyHomingMissile()
    {
        super(SkillID.EnemyHomingMissile,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,120,800);
        

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
        
        // Bullet force
        float xforce = 1800*distanceVector.x;
        float yforce = 1800*distanceVector.y;

       
        //Dispense goo into the world
        float theta = (float)Math.acos(distanceVector.dot(new SylverVector2f(1,0)));
        if(user.getTarget().getPosition().y < user.getPosition().y)
            theta = (float)(2* Math.PI - theta);
        
        //body and image
        Body body = new Body(new Box(50,30), 1);
        Image img = new Image("rocket.png");
        img.setDimensions(40, 20);
         
        //construct hitbox
        PlayerRocket.RocketHitbox rocket = new PlayerRocket.RocketHitbox(damage, body, img, user);
        
       
        //set rocket image
        rocket.getBody().setRotation((float)theta);
        rocket.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        
        //add smoke effect
        AbstractParticleEmitter rocketEmitter = new LeadCrystalParticleEmitters.RocketSmokeEmitter();
        rocketEmitter.setAngle(180);
        rocket.addEmitter(rocketEmitter);

        //add rocket engine effect
        Image flash = new Image("rocket_fire.png");
        flash.setScale(.5f);
        
        //add image effect
        Float[] points1 = {1f, 1.8f, 1f};
        int[] durations1= {15,15};
        ImageEffect flashEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points1, durations1);
        flashEffect.setRepeating(true);
        flash.addImageEffect(flashEffect);
        flash.setRotationPoint(1.4f, .5f);
        flash.setAnchor(Anchorable.Anchor.RIGHTCENTER);
        flash.setHorizontalFlip(false);
        flash.setColor(new Color(2.5f,1.7f,1.7f,.45f));
        flash.setAngle(rocket.getImage().getAngle()); 
        Overlay o = new Overlay(flash, -1, new SylverVector2f(.1f,.5f));
        o.setInfiniteDuration();
        rocket.getImage().addOverlay(o);
        

      //Dispense rocket into the world
        rocket.setPosition(origin.x + distanceVector.x * 25, origin.y + distanceVector.y * 25);
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
        muzzleFlash.setPositionAnchored(origin.x - distanceVector.x * 15, origin.y - distanceVector.y * 15); 
        
        user.getOwningScene().add(muzzleFlash,Scene.Layer.MAIN);
        
        //add smoke
         AbstractParticleEmitter smokeEmitter = new LeadCrystalParticleEmitters.SmokeEmitter();
        smokeEmitter.setPosition(origin.x, origin.y);
        smokeEmitter.setDuration(8);
        smokeEmitter.setParticlesPerFrame(10);
        user.getOwningScene().add(smokeEmitter,Scene.Layer.MAIN);

      
        //play sound
        Sound sound = Sound.locationSound("buffered/rocketShoot.ogg", user.getPosition().x, user.getPosition().y, false, 1f,1f);
        user.getOwningScene().add(sound);
        
        

        
    }
    
    
     private static class EnemyRocketHitbox extends HitBox
    {

        
         public EnemyRocketHitbox(Damage d, Body b, Image i, Entity user)
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
                 Image img = new Image("flashParticle.png");  
                 img.setDimensions(95, 95);
                 img.setColor(new Color(3,3,1,1));
                 img.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.SCALE, 60, 1, 0f));
                 img.setHorizontalFlip(SylverRandom.random.nextBoolean());
                 img.setVerticalFlip(SylverRandom.random.nextBoolean());
                 
                 String particleImageToUse = SylverRandom.random.nextBoolean()?"flashParticle2.png":"flashParticle3.png";
                 Image img2 = new Image(particleImageToUse);  
                 img2.setDimensions(95, 95);
                 img2.setColor(new Color(3,3,1,1));
                 img2.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.SCALE, 60, 1, 0f));
                 img2.setHorizontalFlip(SylverRandom.random.nextBoolean());
                 img2.setVerticalFlip(SylverRandom.random.nextBoolean()); 
                 Overlay ehhovhh = new Overlay(img2);
                 img.addOverlay(ehhovhh); 
                 Body beh = new StaticBody(new Circle(75));
                 beh.setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);
                 beh.setBitmask(Entity.BitMasks.NO_COLLISION.value);
                 damage.setType(Damage.DamageType.BURN);   
                 HitBox explosionHitbox = new HitBox(damage, beh, img, sourceEntity);
                 explosionHitbox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 10, 0, 0));
                 explosionHitbox.setPosition(this.getPosition().x, this.getPosition().y);
                 this.getOwningScene().add(explosionHitbox, Layer.MAIN);
                 
                 
                 
                 //explosion particle emitter
                 AbstractParticleEmitter explosionEmitter = new LeadCrystalParticleEmitters.RocketExplosionEmitter();
                 explosionEmitter.setPosition(this.getPosition().x, this.getPosition().y);
                 explosionEmitter.setDuration(10);
                 this.getOwningScene().add(explosionEmitter, Layer.ATTACHED_FG);
                 
                 //flying chunks 
                  //play sound
                Sound sound = Sound.locationSound("buffered/bang.ogg", this.getPosition().x, this.getPosition().y, false, .90f,.4f);
                this.getOwningScene().add(sound);
                 
                 
                 //remove this hitbox
                 this.getBody().setVelocity(new Vector2f(0,0));
                 this.removeFromOwningScene();
                 
                
             }
         }
         
    }

}


