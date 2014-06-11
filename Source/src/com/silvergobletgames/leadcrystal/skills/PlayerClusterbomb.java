package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.CombatEffect;
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
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;
import java.util.UUID;
import net.phys2d.raw.StaticBody;

/**
 * 
 * Name: Guard
 * Cost:
 * Cooldown: 30 seconds
 * Description: While active, you take 50% damage.  For the first second that Guard is active, you take no damage.
 */
public class PlayerClusterbomb extends PlayerSkill
{
    
    public PlayerClusterbomb()
    {
        //super constructor
        super(SkillID.PlayerClusterbomb,SkillType.OFFENSIVE,ExtendedImageAnimations.SPELLATTACK,420, Integer.MAX_VALUE);
        //set the skillID and the name
        this.icon = new Image("clusterBombIcon.png");
        this.skillName = "Cluster Bomb";
        this.skillDescription = "Throws a cluster of bombs that stun enemies in a medium radius."; 
        this.unlockCost = 1;
        
    }
    
    public void use(Damage damage, SylverVector2f origin) 
    {

        Random r = SylverRandom.random;
        
        //get targeting data
        TargetingData targetingData = this.getTargetingData(origin);
        SylverVector2f vectorToTarget = targetingData.vectorToTarget;
        float theta = targetingData.theta;
                        
        //set damage
        int min = 10; 
        int max = 12;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.NODAMAGE);  
        damage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, 10, 0.0f, 1f));
        
        //build body of bomb1
        Body body1 = new Body(new Circle(15), 1);
        body1.setFriction(2);
        Image img = new Image("destructionDisk.png");
        img.setDimensions(30, 30);
        img.setColor(new Color(1f,1f,1f,1f));  
        ClusterBombHitbox bomb1 = new ClusterBombHitbox(damage, body1, img, user); 
        
        //build body of bomb2
        Body body2 = new Body(new Circle(15), 1);
        body2.setFriction(2);
        Image img2 = new Image("destructionDisk.png");
        img2.setDimensions(30, 30);
        img2.setColor(new Color(1f,1f,1f,1f));  
        ClusterBombHitbox bomb2 = new ClusterBombHitbox(damage, body2, img2, user);
        
        //build body of bomb3
        Body body3 = new Body(new Circle(15), 1);
        body3.setFriction(2);
        Image img3 = new Image("destructionDisk.png");
        img3.setDimensions(30, 30);
        img3.setColor(new Color(1f,1f,1f,1f));  
        ClusterBombHitbox bomb3 = new ClusterBombHitbox(damage, body3, img3, user);
        
        //calculate angles
        int degrees = 12 +r.nextInt(8);
        Vector2f upVector = new Vector2f((float)(Math.cos(degrees * Math.PI/180)*vectorToTarget.x  - (Math.sin(degrees * Math.PI/180))*vectorToTarget.y ),(float)((Math.sin(degrees * Math.PI/180))*vectorToTarget.x  + (Math.cos(degrees * Math.PI/180))*vectorToTarget.y )); // 10 degrees
        upVector.normalise();
        
        degrees = 7 +r.nextInt(6);
        Vector2f downVector = new Vector2f((float)(Math.cos(-degrees * Math.PI/180)*vectorToTarget.x  - (Math.sin(-degrees * Math.PI/180))*vectorToTarget.y ),(float)((Math.sin(-degrees * Math.PI/180))*vectorToTarget.x  + (Math.cos(-degrees * Math.PI/180))*vectorToTarget.y )); // -5 degrees
        downVector.normalise();
        
        //Dispense cluster into the world
        bomb1.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        bomb1.getBody().setRotation((float)theta);
        bomb1.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(bomb1,Layer.MAIN);
        
        bomb2.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        bomb2.getBody().setRotation((float)theta);
        bomb2.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(bomb2,Layer.MAIN);
        
        bomb3.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        bomb3.getBody().setRotation((float)theta);
        bomb3.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(bomb3,Layer.MAIN);
        
        bomb1.getBody().addForce(new Vector2f( (1500 + (int)(r.nextDouble() * 500) ) * upVector.getX(),(1400 + (int)(r.nextDouble() * 600) ) * upVector.getY()));
        bomb2.getBody().addForce(new Vector2f( (1500 + (int)(r.nextDouble() * 500) )* vectorToTarget.getX(),(1400 + (int)(r.nextDouble() * 600) ) * vectorToTarget.getY()));
        bomb3.getBody().addForce(new Vector2f( (1500 + (int)(r.nextDouble() * 500) )* downVector.getX(),(1400 + (int)(r.nextDouble() * 600) ) * downVector.getY()));
       
        
        //play sound
        Sound sound = Sound.locationSound("buffered/jump.ogg", user.getPosition().x, user.getPosition().y, false, .8f, 1.4f);
        user.getOwningScene().add(sound);
        
    }

    private class ClusterBombHitbox extends HitBox
    {
        private int time = 0;
        private int endTime;
        
         public ClusterBombHitbox(Damage d, Body b, Image i, Entity user)
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
                Image img = new Image("gradientCircle.png");  
                img.setDimensions(400, 400);
                img.setColor(new Color(3,3,1,1));
                img.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.SCALE, 35, 1, 0f));
                Body beh = new StaticBody(new Circle(200));
                beh.setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);
                beh.setBitmask(Entity.BitMasks.NO_COLLISION.value);
                damage.setType(Damage.DamageType.PHYSICAL);   
                
                //add stun and slow to damage
                damage.addCombatEffect(new StateEffect(StateEffect.StateEffectType.STUN, 240));
                
                HitBox explosionHitbox = new HitBox(damage, beh, img, sourceEntity);
                explosionHitbox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 60, 0, 0));               
//                LightSource l = new LightSource();
//                l.setSize(1000);
//                l.setIntensity(2);
//                explosionHitbox.setLight(l);
                explosionHitbox.setPosition(this.getPosition().x, this.getPosition().y);
                this.getOwningScene().add(explosionHitbox, Layer.MAIN);
                
                //play sound
                Sound sound = Sound.locationSound("buffered/explosion.ogg", user.getPosition().x, user.getPosition().y, false, 1f, 1f);
                user.getOwningScene().add(sound);


                //remove this hitbox
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
         }
         
    }
}
