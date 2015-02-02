
package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.CombatEffect;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.Damage.DamageType;
import com.silvergobletgames.leadcrystal.combat.DotEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.BlackGooEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.BlackGooGroundEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.BombExplosionEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.GreenGooEmitter;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.graphics.Overlay;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;
import net.phys2d.math.ROVector2f;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Circle;

/**
 *
 * @author mike
 */
public class EnemyNadeThrow extends Skill 
{
    
    public EnemyNadeThrow()
    {
        super(SkillID.EnemyNadeThrow,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,200,800);
    }
    
    public void use(Damage damage, SylverVector2f origin)
    {       
        
        //Damage is scaled with base
        float dAmount = this.getBaseDamage(); 
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.NODAMAGE);  
        
        
        //build rock
        //build body of bomb1
        Body body1 = new Body(new Circle(15), 1);
        body1.setFriction(2);
        Image img = new Image("grenade.png");
        img.setDimensions(30, 30);
        img.setColor(new Color(1f,1f,1f,1f));  
        NadeHitbox bomb1 = new NadeHitbox(damage, body1, img, user); 
        
        
        //Determine vector to target
        SylverVector2f distanceVector = user.distanceVector(user.getTarget());
        distanceVector.normalise();
        
         distanceVector.add(new SylverVector2f(0,1f));
         distanceVector.normalise();
        
        
         
        // Bullet force
        float xforce1 = 16_00*distanceVector.x;
        float yforce1 = 16_00*distanceVector.y;

        //Dispense goo into the world
        float theta = (float)Math.atan(xforce1/yforce1);
        bomb1.setPosition(origin.x, origin.y);
        bomb1.getBody().addForce(new Vector2f((int)xforce1 ,(int)yforce1));
        bomb1.getBody().setRotation((float)theta);
        bomb1.getImage().setAngle((float)(theta * 180f/Math.PI)); 
        

        user.getOwningScene().add(bomb1,Layer.MAIN);       

        
        //play sound
        Sound sound = Sound.locationSound("buffered/jump.ogg", user.getPosition().x, user.getPosition().y, false, .8f, .7f);
        user.getOwningScene().add(sound);
               
    }
    
    
    private class NadeHitbox extends HitBox
    {
        private int time = 0;
        private int endTime;
        
         public NadeHitbox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
            this.body.setBitmask(Entity.BitMasks.PLAYER.value);
            this.body.setOverlapMask(Entity.OverlapMasks.NO_OVERLAP.value);
            this.body.setGravityEffected(true);
            this.body.setRestitution(.98f);
            
            this.endTime = (80 + (int)(Math.random() * 40));
         }
         
         public void update()
         {
             super.update();
             
             time++;
             
             if(time >endTime)
             {
                 this.explode();
             }
                 
         }
         
        public void collidedWith(Entity other, CollisionEvent event)
        {
            if( other instanceof WorldObjectEntity)
            {
                this.body.setDamping(.1f);  
            }
        }
        
        
        public void separatedFrom(Entity other, CollisionEvent event)
        {
            if( other instanceof WorldObjectEntity)
            {
                this.body.setDamping(0f);  
            }
        }
         

         
         private void explode()
         {
             
             
             //===============================
             //add explosion hitbox and effects 
             //================================

             //explosion hitbox
             Image img = new Image("flashParticle.png");  
             img.setDimensions(300, 300);
             img.setColor(new Color(3,3,1,1));
             img.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.SCALE, 60, 1, 0f));
             img.setHorizontalFlip(SylverRandom.random.nextBoolean());
             img.setVerticalFlip(SylverRandom.random.nextBoolean());

             String particleImageToUse = SylverRandom.random.nextBoolean()?"flashParticle2.png":"flashParticle3.png";
             Image img2 = new Image(particleImageToUse);  
             img2.setDimensions(300, 300);
             img2.setColor(new Color(3,3,1,1));
             img2.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.SCALE, 60, 1, 0f));
             img2.setHorizontalFlip(SylverRandom.random.nextBoolean());
             img2.setVerticalFlip(SylverRandom.random.nextBoolean()); 
             Overlay ehhovhh = new Overlay(img2);
             img.addOverlay(ehhovhh); 
                
                
            Body beh = new StaticBody(new Circle(130));
            beh.setOverlapMask(Entity.OverlapMasks.PLAYER_TOUCH.value);
            beh.setBitmask(Entity.BitMasks.NO_COLLISION.value);
            damage.setType(Damage.DamageType.PHYSICAL);   

            //add stun and slow to damage
            damage.addCombatEffect(new StateEffect(StateEffect.StateEffectType.STUN, 60));

            HitBox explosionHitbox = new HitBox(damage, beh, img, sourceEntity);
            explosionHitbox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 10, 0, 0));               
            explosionHitbox.setPosition(this.getPosition().x, this.getPosition().y);
            BombExplosionEmitter emitter = new BombExplosionEmitter();
            emitter.setDuration(15);
            AbstractParticleEmitter fire1 = new LeadCrystalParticleEmitters.GroundFireEmitter1(); 
            fire1.setParticlesPerFrame(.75f);
            fire1.setDuration(360);
            explosionHitbox.addEmitter(fire1);
            explosionHitbox.addEmitter(emitter);
            
            this.getOwningScene().add(explosionHitbox, Layer.MAIN);

            //play sound
            Sound sound = Sound.locationSound("buffered/explosion.ogg", user.getPosition().x, user.getPosition().y, false, 1f, 1f);
            this.getOwningScene().add(sound);


            //remove this hitbox
            this.getBody().setVelocity(new Vector2f(0,0));
            this.removeFromOwningScene();
         }
         
    }

}


