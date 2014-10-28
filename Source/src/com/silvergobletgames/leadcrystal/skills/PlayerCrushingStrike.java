package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.CombatEffect;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.DotEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.FlameStrikeParticleEmitter;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;
import java.util.UUID;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;



public class PlayerCrushingStrike extends PlayerSkill{
    
    public PlayerCrushingStrike()
    {
        super(SkillID.PlayerCrushingStrike,SkillType.OFFENSIVE,ExtendedImageAnimations.MELEEATTACK,6 * 60,150);
        
        //set the name description image and unlock cost
        this.icon = new Image("flameStrikeIcon.png");
        this.skillName = "Flame Strike";      
        this.skillDescription = "An aoe melee attack that does more damage to closer enemies.";
        this.unlockCost =1;
        

    }
    
    
    public void use(Damage damage, SylverVector2f origin)
    {     
        Random r = SylverRandom.random;
        
        //get targeting data
        TargetingData targetingData = this.getTargetingData(origin);
        SylverVector2f vectorToTarget = targetingData.vectorToTarget;
        float theta = targetingData.theta;
        
        
        Damage passthrough = new Damage(damage);
        //set damage
        int min = 15; 
        int max = 17;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.PHYSICAL);    
        damage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, 10, 0.0f, 1f));
        
         //apply image effect
         damage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.COLOR, 5 * 60, new Color(255,40,5), new Color(1f,1f,1f)));
                
        
        
        
        
        //============
        // Build Body
        //=============
        
        //calculate force for the bullet
        float xforce = 1000*vectorToTarget.x;
        float yforce = 1000*vectorToTarget.y;
 
        Body body = new Body(new Box(40, 200),1);
        body.setRotatable(false);
        body.addExcludedBody(user.getBody());
        Image image = new Image("flameStrike.png"); 
        image.setDimensions(100, 200);
        image.setColor(new Color(1.4f,1,1f,1f));
       // image.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.COLOR, 50, new Color(1,1,1f,1f), new Color(1,1,1f,0f)));
        image.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 35, 1, 1));
        image.setAnchor(Anchorable.Anchor.CENTER);
        image.setPositionAnchored(origin.x,origin.y);
        
        StrikeHitbox hitBox = new StrikeHitbox(damage,body,image,this.user,passthrough);
        hitBox.getBody().setBitmask(Entity.BitMasks.NO_COLLISION.value);
        hitBox.getBody().setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);
        hitBox.getBody().setRotation((float)theta);
        hitBox.getImage().setAngle((float)(theta * (180f/Math.PI)));
        hitBox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 35, 1, 1));
        hitBox.setPosition(origin.x,origin.y);
        hitBox.getBody().addForce(new Vector2f(xforce,yforce));
        
        //add particle emitter
        FlameStrikeParticleEmitter particles = new FlameStrikeParticleEmitter();
        particles.setAngle(theta);
        hitBox.addEmitter(particles);
        
        this.user.getOwningScene().add(hitBox,Layer.MAIN);
        
        Sound sound = Sound.locationSound("buffered/fireball.ogg", user.getPosition().x, user.getPosition().y, false);
        user.getOwningScene().add(sound);
        
        
    }

    public static class StrikeHitbox extends HitBox
    {
        private Damage passthrough;
        
         public StrikeHitbox(Damage d, Body b, Image i, Entity user, Damage passthrough)
         { 
            super(d, b, i, user); 
            
            this.passthrough = passthrough;

         }

         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 


             if( other instanceof CombatEntity)
             {
                 
                 //get distance to user
                 float distance = other.distanceAbs(this.sourceEntity);
                 
                 //damage goes from 30 to 5 linearly as the distance goes from 70 to 600               
                 float maxDamage = 20; //y1
                 float minDamage = 10; //y2
                 float closeDistance = 70; //x1
                 float farDistance = 280; //x2
                 
                 //linear equation
                 float damage = ((minDamage - maxDamage)/(farDistance-closeDistance)) * (distance - closeDistance) + maxDamage;
                
                 //set damage between 5 and 25
                 damage = Math.max(damage, minDamage);
                 damage = Math.min(damage, maxDamage);
                 
                 this.passthrough.getAmountObject().setBase(damage/10);
                 this.passthrough.setType(Damage.DamageType.BURN);
                 
                 //build dot
                 CombatEffect burn = new DotEffect(5 * 60, 30, this.passthrough);
                 //give damage to other
                 ((CombatEntity)other).getCombatData().addCombatEffect("flameDot", burn);

             }
         }
         
    }
}
