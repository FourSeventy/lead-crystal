 package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.DashParticleEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.LaserBitsEmitter;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.graphics.ImageParticleEmitter;
import com.silvergobletgames.sylver.graphics.MultiImageEffect;
import com.silvergobletgames.sylver.graphics.PointParticleEmitter;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;
import java.util.UUID;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;


public class PlayerDashAttack extends Skill{
    
    public PlayerDashAttack()
    {
        //super constructor
        super(SkillID.PlayerDash,SkillType.OFFENSIVE, ExtendedImageAnimations.MELEEATTACK,180, 150);

        //set the skillID and the name
        this.icon = new Image("dashIcon.png");
        this.skillName = "Dash Attack";
        this.skillDescription = "Dashes forward passing through enemies while doing damage and slowing them.";
        this.skillDamageDescription = "Damage: 12-14";
        this.unlockCost = 1;
    }
    
    public void use(Damage damage, SylverVector2f origin) 
    {
        PlayerEntity player = (PlayerEntity) user;
        
        Random r = SylverRandom.random;
                        
       
         //Get target X and Y
        float targetX = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationX;
        float targetY = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationY;
        
        //Get user X and Y
        float userX = user.getPosition().x;
        float userY = user.getPosition().y;
        
        //get vector to target
        SylverVector2f vectorToTarget = new SylverVector2f(targetX - userX, targetY - userY);
        vectorToTarget.normalise();
        
        //set damage
        int min = 12; 
        int max = 14;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.PHYSICAL);    
        
        //add stun effect
        damage.addCombatEffect(new StateEffect(StateEffect.StateEffectType.SLOW, 3 * 60,.50f, true));
        
         //determine angle for the image
        float theta = (float)Math.acos(vectorToTarget.dot(new SylverVector2f(1,0)));
        if(targetY < userY)
            theta = (float)(2* Math.PI - theta);
        
        //add brightness effect to damage
        Float[] points = {1f,2f,1f};
        int[] durations = {10,5};
        ImageEffect brightnessEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points,durations);
        damage.addImageEffect(brightnessEffect);
        
        // make dash hitbox that will follow the player, and add force to the player
        Image swipe = new Image("dashEffect.png");            
        swipe.setHorizontalFlip(false);
        swipe.setColor(new Color(1.2f,1.2f,1.2f,.7f));
        swipe.setScale(1.2f);
        DashHitBox box = new DashHitBox(damage,new Body(new Box(100,100),1),swipe, user,vectorToTarget);; 
        box.getBody().setRotation((float)theta);
        box.getImage().setAngle((float)(theta * (180f/Math.PI)));
        box.setPosition(user.getPosition().x + 100 * vectorToTarget.x, user.getPosition().y + 100 * vectorToTarget.y);
        user.getOwningScene().add(box, Scene.Layer.MAIN);  
        box.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 30, 0, 0)); 
        
        //add particle effect
        ImageParticleEmitter particles = new DashParticleEmitter();
        particles.setAngle(theta);
        particles.setDuration(-1);
        particles.setParticleRotation((float)(theta * (180f/Math.PI))); 
        box.addEmitter(particles);
        
        
        //tell player to handle dash
        ((PlayerEntity)user).handleDash(vectorToTarget);
        
        //play sound
        Sound sound = Sound.locationSound("buffered/woosh2.ogg", user.getPosition().x, user.getPosition().y, false, 1f,.4f);
        user.getOwningScene().add(sound);
        
        
    }
    
    
    public static class DashHitBox extends HitBox
    {
        private SylverVector2f vectorToTarget; 
        
         public DashHitBox(Damage d, Body b, Image i, Entity user,SylverVector2f vectorToTarget)
         { 
            super(d, b, i, user);    
            this.vectorToTarget = vectorToTarget;
         }  
         
         @Override
         public void update()
         {
             super.update();
             
             setPosition(sourceEntity.getPosition().x + 100 * vectorToTarget.x, sourceEntity.getPosition().y + 100 * vectorToTarget.y);
         }
         

    }
}


