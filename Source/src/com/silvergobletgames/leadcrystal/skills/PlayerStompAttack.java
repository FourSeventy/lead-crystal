package com.silvergobletgames.leadcrystal.skills;

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
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.core.SceneObjectManager;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;
import java.util.UUID;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.forcesource.ForceSource;
import net.phys2d.raw.forcesource.WindSource;

/**
 * 
 * Name: Guard
 * Cost:
 * Cooldown: 30 seconds
 * Description: While active, you take 50% damage.  For the first second that Guard is active, you take no damage.
 */
public class PlayerStompAttack extends Skill
{
    
    public PlayerStompAttack()
    {
        //super constructor
        super(SkillID.PlayerStomp,SkillType.OFFENSIVE,ExtendedImageAnimations.SPELLATTACK,300,Integer.MAX_VALUE);

        //set the skillID and the name
        this.icon = new Image("computer.png");
        this.skillName = "Shockwave";
        this.skillDescription = "Short range low damage AOE attack that causes the affected enemies to be stunned for 3 seconds.";
        this.unlockCost = 1;
        
    }
    
    public void use(Damage damage, SylverVector2f origin) 
    {
        Random r = SylverRandom.random;
                        
        //set damage
        int min = 10; 
        int max = 12;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.PHYSICAL);       
        
        //add brightness effect to damage
        Float[] points = {1f,2f,1f};
        int[] durations = {10,5};
        ImageEffect brightnessEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points,durations);
        damage.addImageEffect(brightnessEffect);
        
        //add stun effect to the damage
        damage.addCombatEffect(new StateEffect(StateEffect.StateEffectType.STUN, 240));
        
        //build hitbox            
        Body b = new StaticBody(new Circle(100));
        HitBox hitBox = new ShockwaveHitbox(damage, b, new Image("blank.png"), user);
        hitBox.getImage().setAlphaBrightness(.2f);
        hitBox.getBody().setBitmask(Entity.BitMasks.NO_COLLISION.value);
        hitBox.setPosition(user.getPosition().x, user.getPosition().y);
        hitBox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 40, 0, 0));
        
        Image img = new Image("shockwaveEffect.png");
        img.setColor(new Color(1.2f,.5f,.5f,.7f));
        img.setAnchor(Anchorable.Anchor.CENTER);
        img.setScale(.2f);
        img.setPositionAnchored(user.getPosition().x, user.getPosition().y);
        Float[] points2 = {.1f, 1f,.1f};
        int[] durations2 = {20,20};     
        img.addImageEffect(new MultiImageEffect(ImageEffect.ImageEffectType.SCALE, points2, durations2));
        Float[] points3 = {1f, 3f,1f};
        int[] durations3 = {20,20};     
        img.addImageEffect(new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points3, durations3));
        Overlay o = new Overlay(img, 40, new SylverVector2f(.5f,.5f));
        user.getImage().addOverlay(o);
        
        //add to world
        user.getOwningScene().add(hitBox, Layer.MAIN);
        
        
        
        
    }
    
    
    public static class ShockwaveHitbox extends HitBox
    {
         private int ticks;
        
         public ShockwaveHitbox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 

         }

         @Override
         public void update()
         {
             super.update();
             
             this.ticks++;
             
             if(ticks <= 30 )
             {
                 ((StaticBody)this.body).setShape(new Circle(ticks * 10));
                 body.setPosition(this.sourceEntity.getPosition().x, this.sourceEntity.getPosition().y);
             }
             // 300 is max size
             this.setPosition(this.sourceEntity.getPosition().x, this.sourceEntity.getPosition().y);
             
         }
         
    }

}

   
