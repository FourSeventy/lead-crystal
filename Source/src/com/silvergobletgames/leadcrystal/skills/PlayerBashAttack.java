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
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.UUID;
import net.phys2d.raw.CollisionEvent;



public class PlayerBashAttack extends Skill{
    
    public PlayerBashAttack()
    {
        super(SkillID.PlayerBashAttack,SkillType.OFFENSIVE,ExtendedImageAnimations.MELEEATTACK,180,150);
        
        //set the name description image and unlock cost
        this.icon = new Image("icetrap.png");
        this.skillName = "Bash Attack";      
        this.skillDescription = "An Aoe strike with short range that bashes enemies backwards and stuns them.";
        

    }
    
    
    public void use(Damage damage, SylverVector2f origin)
    {     
        Random r = SylverRandom.random;
        
        //set damage
        int min = 13; 
        int max = 16;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.PHYSICAL);    
        damage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, 10, 0.0f, 1f));
        
        //add stun effect
        damage.addCombatEffect(new StateEffect(StateEffect.StateEffectType.STUN, 240));
        
        //Get target X and Y
        PlayerEntity player = (PlayerEntity) user;
        float targetX = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationX;
        float targetY = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationY;
        
        //Get user X and Y
        float userX = origin.x;
        float userY = origin.y;
        
        //get vector to target
        Vector2f vectorToTarget = new Vector2f(targetX - userX, targetY - userY);
        vectorToTarget.normalise();
        
        //determine angle for the image
        float theta = (float)Math.acos(vectorToTarget.dot(new Vector2f(1,0)));
        if(targetY < userY)
            theta = (float)(2* Math.PI - theta);
        
        //build body and image
        Body body = new StaticBody(new Box(90, 90));
        Image swipe = new Image("swipe.png");            
        swipe.setHorizontalFlip(true);
        swipe.setDimensions(90, 90);
        swipe.setColor(new Color(2f,.7f,.7f));
        
        //build and position hitbox
        BashHitBox hitBox = new BashHitBox(damage,body, swipe,this.user);
        hitBox.vector = new Vector2f(vectorToTarget);
        hitBox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 15, 1, 1));
        hitBox.getBody().setRotation((float)theta);
        hitBox.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        hitBox.setPosition(user.getPosition().x + 100*vectorToTarget.x, user.getPosition().y + 100*vectorToTarget.y);
        this.user.getOwningScene().add(hitBox,Layer.MAIN);    
        
        
    }

    public static class BashHitBox extends HitBox
    {

        private Vector2f vector;
        
         public BashHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 

         }

         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 


             if( other instanceof CombatEntity)
             {
                 other.getBody().setForce(0,0);
                 other.getBody().addForce(new Vector2f(10_000 * vector.x ,10_000 * vector.y)); 
             }
         }
         
    }
}
