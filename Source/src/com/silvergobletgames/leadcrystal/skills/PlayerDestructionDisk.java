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
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.LaserBitsEmitter;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Circle;



/**
 * Skill that creates our basic projectile entity and fires it into the scene from
 * the user's current location to the mouse clicked location if the user is a player
 * or the user's target if the target is an enemy.
 * @author Justin Capalbo
 */
public class PlayerDestructionDisk extends Skill{
        
    
    /**
     * Here we define the skill types and set up other initial params if any
     */
    public PlayerDestructionDisk()
    {
        super(SkillID.PlayerDestructionDisk,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,600,800);
        
        //set the skillID and the name
        this.icon = new Image("destructionDisk.png");
        this.skillName = "Destruction Disk";
        this.skillDescription = "Hurls a slow moving explosive disk. If the disk is struck by your primary"
                + " attack, it will cause a powerful explosion.";
        this.unlockCost = 1;
       

    }
    
    /**
     * Executes this skill
     * @param user 
     */
    public void use(Damage damage, SylverVector2f origin)
    {
        
        PlayerEntity player = (PlayerEntity) user;
        Random r = SylverRandom.random;
                        
        //set damage
        int min = 23; 
        int max = 25;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.NODAMAGE);  
        damage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, 10, 0.0f, 1f));
        
        //build body of the shock
        Body body = new Body(new Circle(30), 1);
        Image img = new Image("destructionDisk.png");
        img.setDimensions(60, 60);
        img.setColor(new Color(1f,1f,1f,1f)); 
  
        DestructionDiskHitbox laser = new DestructionDiskHitbox(damage, body, img, user);        
        
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
        float xforce = 1000*vectorToTarget.x;
        float yforce = 1000*vectorToTarget.y;
        
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
    
    
    private class DestructionDiskHitbox extends HitBox
    {
        
         public DestructionDiskHitbox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
         }
         
         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 
             
             //remove if we hit a world object
             if(other instanceof WorldObjectEntity || other instanceof CombatEntity)
             {
                 //explode
                 this.explode();
             }
             
             if(other instanceof HitBox)
             {
                 //see if we collided with a player laser
                 if(!((HitBox)other).getDamage().getCombatEffects().isEmpty())
                 {
                     CombatEffect labelEffect = ((HitBox)other).getDamage().getCombatEffects().get(0);
                     
                     if(labelEffect instanceof StateEffect)
                     {
                         if (((StateEffect)labelEffect).getStateEffectType() == StateEffectType.LABEL)
                         {
                             if(((StateEffect)labelEffect).getName().equals("playerLaser"))
                             {
                                 //==========================
                                 //we collided a player laser
                                 //===========================
                                 
                                 this.explode();
                                 
                             }
                         }
                     }
                 }
             }
         }
         
         private void explode()
         {
             
                //===============================
                //add explosion hitbox and effects 
                //================================

                //explosion hitbox
                Image img = new Image("gradientCircle.png");  
                img.setDimensions(300, 300);
                img.setColor(new Color(3,3,1,1));
                img.addImageEffect(new ImageEffect(ImageEffectType.SCALE, 40, 1, 0f));
                Body beh = new StaticBody(new Circle(150));
                beh.setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);
                beh.setBitmask(Entity.BitMasks.NO_COLLISION.value);
                damage.setType(Damage.DamageType.PHYSICAL);   
                HitBox explosionHitbox = new HitBox(damage, beh, img, sourceEntity);
                explosionHitbox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 60, 0, 0));
                explosionHitbox.setPosition(this.getPosition().x, this.getPosition().y);
                this.getOwningScene().add(explosionHitbox, Layer.MAIN);

                //explosion particle emitter
                ParticleEmitter explosionEmitter = new LeadCrystalParticleEmitters.RocketExplosionEmitter();
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

