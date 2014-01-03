package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.CombatEffect;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Image;
import java.util.Random;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.DotEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.UUID;
import net.phys2d.raw.CollisionEvent;



public class PlayerCrushingStrike extends PlayerSkill{
    
    public PlayerCrushingStrike()
    {
        super(SkillID.PlayerCrushingStrike,SkillType.OFFENSIVE,ExtendedImageAnimations.MELEEATTACK,300,150);
        
        //set the name description image and unlock cost
        this.icon = new Image("computer.png");
        this.skillName = "Crushing Strike";      
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
        
        //set damage
        float damageAmout = 0;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.PHYSICAL);    
        damage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, 10, 0.0f, 1f));
        
        
        //============
        // Build Body
        //=============
        
        //calculate force for the bullet
        float xforce = 2500*vectorToTarget.x;
        float yforce = 2500*vectorToTarget.y;
 
        Body body = new Body(new Box(40, 250),1);
        body.setRotatable(false);
        body.addExcludedBody(user.getBody());
        Image image = new Image("swipe.png"); 
        image.setDimensions(100, 250);
        image.setColor(new Color(1,1,1f,1f));
       // image.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.COLOR, 50, new Color(1,1,1f,1f), new Color(1,1,1f,0f)));
        image.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 300, 1, 1));
        image.setAnchor(Anchorable.Anchor.CENTER);
        image.setPositionAnchored(origin.x,origin.y);
        //this.user.getOwningScene().add(image,Layer.MAIN);
        
        StrikeHitbox hitBox = new StrikeHitbox(new Damage(Damage.DamageType.NODAMAGE, 0),body,image,this.user,damage);
        hitBox.getBody().setBitmask(Entity.BitMasks.NO_COLLISION.value);
        hitBox.getBody().setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);
        hitBox.getBody().setRotation((float)theta);
        hitBox.getImage().setAngle((float)(theta * (180f/Math.PI)));
        hitBox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 300, 1, 1));
        hitBox.setPosition(origin.x,origin.y);
        hitBox.getBody().addForce(new Vector2f(xforce,yforce));
        this.user.getOwningScene().add(hitBox,Layer.MAIN);      
        
        
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
                 float maxDamage = 30; //y1
                 float minDamage = 5; //y2
                 float closeDistance = 70; //x1
                 float farDistance = 600; //x2
                 
                 //linear equation
                 float damage = ((minDamage - maxDamage)/(farDistance-closeDistance)) * (distance - closeDistance) + maxDamage;
                
                 //set damage between 5 and 25
                 damage = Math.max(damage, minDamage);
                 damage = Math.min(damage, maxDamage);
                 
                 this.passthrough.getAmountObject().setBase(damage);
                 
                 //give damage to other
                 ((CombatEntity)other).takeDamage(passthrough);

             }
         }
         
    }
}
