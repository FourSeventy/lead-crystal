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
public class PlayerFlashbang extends Skill
{
    
    public PlayerFlashbang()
    {
        //super constructor
        super(SkillID.PlayerFlashbang,SkillType.OFFENSIVE,ExtendedImageAnimations.SPELLATTACK,900, Integer.MAX_VALUE);
        //set the skillID and the name
        this.icon = new Image("flashbangIcon.jpg");
        this.skillName = "Flashbang";
        this.skillDescription = "Throws a flashbang that stuns and slows all the enemies in a large radius."; 
        this.unlockCost = 2;
        
    }
    
    public void use(Damage damage, SylverVector2f origin) 
    {
      
        PlayerEntity player = (PlayerEntity) user;
        Random r = SylverRandom.random;
                        
        //set damage
        int min = 5; 
        int max = 7;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.NODAMAGE);  
        damage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, 10, 0.0f, 1f));
        
        //build body of the shock
        Body body = new Body(new Circle(15), 1);
        body.setFriction(2);
        Image img = new Image("destructionDisk.png");
        img.setDimensions(30, 30);
        img.setColor(new Color(1f,1f,1f,1f)); 
  
        FlashbangHitbox laser = new FlashbangHitbox(damage, body, img, user);        
        
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
        float xforce = 2000*vectorToTarget.x;
        float yforce = 2000*vectorToTarget.y;
        
        //determine angle for the image
        float theta = (float)Math.acos(vectorToTarget.dot(new Vector2f(1,0)));
        if(targetY < userY)
            theta = (float)(2* Math.PI - theta);
        
        //Dispense shock into the world
        laser.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        laser.getBody().addForce(new Vector2f(xforce ,yforce));
        laser.getBody().setRotation((float)theta);
        laser.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(laser,Layer.MAIN);
        

      
        //play sound
        
    }

    private class FlashbangHitbox extends HitBox
    {
        private int time = 0;
        
         public FlashbangHitbox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
            this.body.setBitmask(Entity.BitMasks.PLAYER.value);
            this.body.setOverlapMask(Entity.OverlapMasks.NO_OVERLAP.value);
            this.body.setGravityEffected(true);
            this.body.setRestitution(.98f);
         }
         
         public void update()
         {
             super.update();
             
             time++;
             
             if(time >180)
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
                img.setDimensions(1000, 1000);
                img.setColor(new Color(3,3,1,1));
                img.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.SCALE, 40, 1, 0f));
                Body beh = new StaticBody(new Circle(500));
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


                //remove this hitbox
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
         }
         
    }
}
