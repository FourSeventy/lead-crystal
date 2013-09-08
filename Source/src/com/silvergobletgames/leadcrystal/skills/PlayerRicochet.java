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
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.LaserBitsEmitter;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import net.phys2d.raw.shapes.Circle;



/**
 * Skill that creates our basic projectile entity and fires it into the scene from
 * the user's current location to the mouse clicked location if the user is a player
 * or the user's target if the target is an enemy.
 * @author Justin Capalbo
 */
public class PlayerRicochet extends Skill{
        
    
    /**
     * Here we define the skill types and set up other initial params if any
     */
    public PlayerRicochet()
    {
        super(SkillID.PlayerRicochet,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,50,Integer.MAX_VALUE);
        
        //set the skillID and the name
        this.icon = new Image("blade.png");
        this.skillName = "Ricochet Blade";
        this.skillDescription = "Shoots a deadly blade that bounces off walls and pierces enemies.";
        this.unlockCost = 2;

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
        int min = 7; 
        int max = 9;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.PHYSICAL);  
        
        
        //add brightness effect to damage
        Float[] points = {1f,2f,1f};
        int[] durations = {10,5};
        ImageEffect brightnessEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points,durations);
        damage.addImageEffect(brightnessEffect);
        
        //build body of the blade
        Body body = new Body(new Circle(20), 1);
        body.setRestitution(3);
        body.setFriction(0);
        Image img = new Image("blade.png");
        img.setDimensions(40, 40);
        
        //add rotation effect to image
        ImageEffect rotationEffect = new ImageEffect(ImageEffectType.ROTATION, 30, 0, 360);
        rotationEffect.setRepeating(true);
        img.addImageEffect(rotationEffect);
  
        //build hitbox
        BladeHitBox blade = new BladeHitBox(damage, body, img, user);  
        
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
        
        //Dispense blade into the world
        blade.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        blade.getBody().addForce(new Vector2f(xforce ,yforce));
        blade.getBody().setRotation((float)theta);
        blade.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(blade,Layer.MAIN);
        
        
        //play sound

    }
    
    
    public static class BladeHitBox extends HitBox
    {

        long counter;
        private Vector2f maxVelocity = new Vector2f(0,0);
        boolean removeOnNextContact = false;
        
         public BladeHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
            
            //label effect for destruction disk
            StateEffect labelEffect = new StateEffect(StateEffectType.LABEL, 1) ;
            labelEffect.setName("playerLaser");
            labelEffect.setInfinite();            
            this.getDamage().addCombatEffect(labelEffect);
         }

         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 
             
             //laser bits
             if(!(other instanceof HitBox))
             {
                 //get angle of impact
                 Vector2f normalVector = (Vector2f)event.getNormal();
                 normalVector =normalVector.negate();
                 Vector2f xVector = new Vector2f(1,0);
                 float angle = (float)(Math.acos(normalVector.dot(xVector) ) * 180 / Math.PI);
                 if(normalVector.getY() < 0)
                     angle += 180;
                 
                 //make emitter
                 AbstractParticleEmitter emitter = new LaserBitsEmitter();
                 emitter.setPosition(event.getPoint().getX(), event.getPoint().getY());
                 emitter.setDuration(1);
                 emitter.setAngle(angle);
                 emitter.setParticlesPerFrame(5);
                 owningScene.add(emitter,Layer.MAIN);
                 
             }
             
             //remove if we hit a world object, or an enemy
             if(this.removeOnNextContact &&(other instanceof WorldObjectEntity || other instanceof CombatEntity))
             {
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
         }
         
         public void update()
         {
             super.update();
             this.counter++;
             
             if(this.counter > 180)
                 this.removeOnNextContact = true;
             
             //keep track of max velocity
             if(Math.abs(this.body.getVelocity().length()) > Math.abs(this.maxVelocity.length()))
             {
                 this.maxVelocity = new Vector2f(this.body.getVelocity());
             }
             
            //keep at constant speed
            if(Math.abs(this.body.getVelocity().length()) < Math.abs(this.maxVelocity.length()))
            {
                float scaling = Math.abs(this.maxVelocity.length())/ Math.abs(this.body.getVelocity().length());
                Vector2f newVelocity = new Vector2f(this.body.getVelocity().getX() * scaling, this.body.getVelocity().getY() * scaling);
                this.body.setVelocity(newVelocity);
            }
         }
         
         
    }
    
    
    
}  

