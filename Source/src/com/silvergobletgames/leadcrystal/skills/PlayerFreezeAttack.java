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
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.UUID;



public class PlayerFreezeAttack extends Skill{
    
    public PlayerFreezeAttack()
    {
        super(SkillID.PlayerFreezeAttack,SkillType.OFFENSIVE,ExtendedImageAnimations.MELEEATTACK,180,150);
        
        this.icon =new Image("impaleIcon.jpg") ;
        this.skillName = "Freeze Attack";      
        this.skillDescription = "An Aoe strike with short range and low damage that freezes all enemies hit for 3 seconds.";
        this.unlockCost = 2;

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
        
        //add freeze effect
        damage.addCombatEffect(new StateEffect(StateEffect.StateEffectType.SLOW, 360, .5f, true));
        damage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.COLOR, 360, new Color(.5f,.5f,2), new Color(1f,1f,1f)));
        
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
        Body body = new StaticBody(new Box(130, 130));
        Image swipe = new Image("swipe.png");            
        swipe.setHorizontalFlip(true);
        swipe.setDimensions(130, 130);
        swipe.setColor(new Color(.7f,.7f,2));
        
        //build and position hitbox
        HitBox hitBox = new HitBox(damage,body, swipe,this.user);
        hitBox.getBody().setBitmask(Entity.BitMasks.NO_COLLISION.value); 
        hitBox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 30, 1, 1));
        hitBox.getBody().setRotation((float)theta);
        hitBox.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        hitBox.setPosition(user.getPosition().x + 100*vectorToTarget.x, user.getPosition().y + 100*vectorToTarget.y);
        
        //add spiral and moving effects
        hitBox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.XTRANSLATE, 30, hitBox.getPosition().x, hitBox.getPosition().x + 150 * vectorToTarget.x));
        hitBox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.YTRANSLATE, 30, hitBox.getPosition().y, hitBox.getPosition().y + 150 * vectorToTarget.y));
        this.user.getOwningScene().add(hitBox,Layer.MAIN);    
        
        
    }


}
