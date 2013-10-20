package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.sylver.graphics.Image;
import net.phys2d.math.Vector2f;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;
import java.util.UUID;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;


public class PlayerDashAttack extends Skill{
    
    public PlayerDashAttack()
    {
        //super constructor
        super(SkillID.PlayerDash,SkillType.OFFENSIVE, ExtendedImageAnimations.MELEEATTACK,30, 200);

        //set the skillID and the name
        this.icon = new Image("dashIcon.jpg");
        this.skillName = "Dash Attack";
        this.skillDescription = "Dashes forward passing through enemies while doing damage to them.";
        this.unlockCost = 1;
    }
    
    public void use(Damage damage, SylverVector2f origin) 
    {
        PlayerEntity player = (PlayerEntity) user;
        Random r = SylverRandom.random;
        
        // make dash hitbox that will follow the player, and add force to the player
        DashHitBox box = new DashHitBox(damage,new Body(new Box(10,10),1),new Image("blank.png"), user);
        box.setPosition(user.getPosition().x, user.getPosition().y);
        user.getOwningScene().add(box, Scene.Layer.MAIN);
        
         //Get target X and Y
        float targetX = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationX;
        float targetY = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationY;
        
        //Get user X and Y
        float userX = user.getPosition().x;
        float userY = user.getPosition().y;
        
        //get vector to target
        SylverVector2f vectorToTarget = new SylverVector2f(targetX - userX, targetY - userY);
        vectorToTarget.normalise();
        
        ((PlayerEntity)user).handleDash(vectorToTarget);
        
        
    }
    
    
    public static class DashHitBox extends HitBox
    {

         
         public DashHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user);          
         }  
         

    }
}


