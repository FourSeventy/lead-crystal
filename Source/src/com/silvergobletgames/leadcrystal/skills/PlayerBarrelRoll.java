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
import com.silvergobletgames.leadcrystal.combat.DotEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect.StateEffectType;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.LaserBitsEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.SmokeEmitter;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Circle;



/**
 * Description: Shoots a laser that damages enemies when struck.
 * Damage: 5-10
 * Cooldown: .5 seconds
 * @author Mike
 */
public class PlayerBarrelRoll extends PlayerSkill{
        
    
    public PlayerBarrelRoll()
    {
        super(SkillID.PlayerBarrelRoll,SkillType.OFFENSIVE,ExtendedImageAnimations.SPELLATTACK,30,Integer.MAX_VALUE);
        
        //set the skillID and the name
        this.icon = new Image("barrel.png");
        this.skillName = "Barrel Roll";
        this.skillDescription = "Rolls an explosive barrel that explodes into high damaging shrapnal and leaves damaging fire on the ground.";

    }
    

    public void use(Damage damage, SylverVector2f origin)
    {
        
        Random r = SylverRandom.random;
        
        //get targeting data
        TargetingData targetingData = this.getTargetingData(origin);
        SylverVector2f vectorToTarget = targetingData.vectorToTarget;
        float theta = targetingData.theta;
                        
        //set damage
        int min = 18; 
        int max = 20;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.NODAMAGE);       
        damage.addImageEffect(this.getDamageBrightnessEffect());
        
        //build body of the barrel
        Body body = new Body(new Circle(30), 4);
        Image img = new Image("barrel.png");
        img.setAnchor(Anchorable.Anchor.CENTER);
        img.setDimensions(60, 60);
        img.setColor(new Color(1.1f,1.1f,1.1f,1f)); 
        BarrelHitBox barrel = new BarrelHitBox(damage, body, img, user); 
        
        //add image effect
        Float[] points1 = {1.1f, 1.15f, 1.1f};
        int[] durations1= {15,15};
        ImageEffect blinkEffect = new MultiImageEffect(ImageEffectType.BRIGHTNESS, points1, durations1);
        blinkEffect.setRepeating(true);
        img.addImageEffect(blinkEffect);
        
           
        //calculate force for the bullet
        float xforce = 4000*vectorToTarget.x;
        float yforce = 4000*vectorToTarget.y;
        
        //Dispense barrel into the world
        barrel.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        barrel.getBody().addForce(new Vector2f(xforce ,yforce));
        barrel.getBody().setRotation((float)theta);
        barrel.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(barrel,Layer.MAIN);      
        
 
        //play sound
//        Sound sound = Sound.locationSound("buffered/smallLaser.ogg", user.getPosition().x, user.getPosition().y, false, .8f);
//        user.getOwningScene().add(sound);


    }
    
    public static class BarrelHitBox extends HitBox
    {

        
         public BarrelHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
            
            this.getBody().setGravityEffected(true);
            this.getBody().setRotatable(true);
            
         }
         
         private void explode()
         {
             
             
             //explosion hitbox
             Image img = new Image("gradientCircle.png");  
             img.setDimensions(400, 400);
             img.setColor(new Color(3,3,1,1));
             img.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.SCALE, 35, 1, 0f));
             Body beh = new StaticBody(new Circle(200));
             beh.setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);
             beh.setBitmask(Entity.BitMasks.NO_COLLISION.value);
             damage.setType(Damage.DamageType.BURN);   

             //explosion hitbox
             HitBox explosionHitbox = new HitBox(damage, beh, img, sourceEntity);
             explosionHitbox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 60, 0, 0));               
             explosionHitbox.setPosition(this.getPosition().x, this.getPosition().y);
             this.getOwningScene().add(explosionHitbox, Layer.MAIN); 
                
             //shoot out debris
                
             //lay down damaging fire
             Body body = new Body(new Box(300,50), 1);
             body.setDamping(1);
             Image image = new Image("black.png"); 
             image.setDimensions(300, 50);
             BarrelFireHitbox fireHitbox = new BarrelFireHitbox(body,image,this.sourceEntity);
             fireHitbox.setPosition(this.getPosition().x, this.getPosition().y);
             fireHitbox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 300, 0, 0));
             this.getOwningScene().add(fireHitbox, Layer.MAIN); 
                
             //remove this hitbox from the world
            this.getBody().setVelocity(new Vector2f(0,0));
            this.removeFromOwningScene();
         }

         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event);
             
             //see if we collided with a player primary skill
             if(other instanceof HitBox)
             {                
                 if(!((HitBox)other).getDamage().getCombatEffects().isEmpty())
                 {
                     CombatEffect labelEffect = ((HitBox)other).getDamage().getCombatEffects().get(0);
                     
                     if(labelEffect instanceof StateEffect)
                     {
                         if (((StateEffect)labelEffect).getStateEffectType() == StateEffectType.LABEL)
                         {
                             if(((StateEffect)labelEffect).getName().equals("playerLaser"))
                             {
                                
                                //explode 
                                 this.explode();
                                 
                             }
                         }
                     }
                 }
             }
             

                         
             //remove if we hit a world object, or an enemy
             if(other instanceof CombatEntity)
             {
                //explode
                this.explode();
               
             }
         }
         
    }
    
    public static class BarrelFireHitbox extends HitBox
    {
        
        public BarrelFireHitbox(Body b, Image i, Entity user)
        {
            super(new Damage(Damage.DamageType.NODAMAGE,0),b,i,user);
            
            this.getBody().setBitmask(BitMasks.NO_COLLISION.value);
            this.getBody().setOverlapMask(OverlapMasks.OVERLAP_ALL.value);
        }
        
        
        public void collidedWith(Entity other, CollisionEvent event)
        {
            //if we collided with a player add a heal dot
            if(other instanceof NonPlayerEntity)
            {
                
                //apply image effect
                ((CombatEntity)other).getImage().setColor(new Color(1.3f,.5f,.5f)); 
                
                //apply damage dot
                DotEffect fireDot = new DotEffect(1, 30, new Damage(DamageType.BURN, 3));
                fireDot.setInfinite();                
                ((CombatEntity)other).getCombatData().addCombatEffect("fireDot", fireDot);
            }
            
        }
        
        public void separatedFrom(Entity other, CollisionEvent event)
        {
            if(other instanceof NonPlayerEntity)
            {
                //remove infinite effects 
                ((CombatEntity)other).getCombatData().removeCombatEffect("fireDot");
                ((CombatEntity)other).getImage().setColor(new Color(1,1,1,1));
                
            }
        }
        
        
    }
    
}  

