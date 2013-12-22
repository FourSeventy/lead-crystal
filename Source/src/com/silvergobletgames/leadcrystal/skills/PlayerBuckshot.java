package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.Color;
import java.util.Random;
import java.util.UUID;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.DotEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.LaserBitsEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.SmokeEmitter;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;


/**
 * Description: Shoots 5 pieces of red hot buckshot.
 * Damage: 5-10 per piece
 * Cooldown: 5 seconds
 * @author Mike
 */
public class PlayerBuckshot extends Skill{
    
    public PlayerBuckshot()
    {
        super(SkillID.PlayerBuckshot,SkillType.OFFENSIVE, ExtendedImageAnimations.RANGEDATTACK,50,500);
        
        //set the name description image and unlock cost
        this.skillName = "Buckshot";          
        this.skillDescription = "Shoots a blast of buckshot that does high damage but has low range.";
        this.unlockCost = 1;
        this.icon = new Image("buckshotIcon.jpg");

    }
    
    
    public void use(Damage damage, SylverVector2f origin)
    {
        Random r = SylverRandom.random;
        PlayerEntity player = (PlayerEntity) user;
        
        //set damage
        int min = 3; 
        int max = 6;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.PHYSICAL);    
        damage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, 10, 0.0f, 1f));
        
        //Get target X and Y
        float targetX = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationX;
        float targetY = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationY;
        
        //Get user X and Y
        float userX = origin.x;
        float userY = origin.y;
        
        //get vector to target
        Vector2f vectorToTarget = new Vector2f(targetX - userX, targetY - userY);
        vectorToTarget.normalise();
        
        //build bodies
        Body body = new Body(new Box(18,18), 1.5f);
        Image img = new Image("chunk1.png");
        img.setDimensions(10,10);
        img.setColor(new Color(5f,.5f,.5f,1f)); 
        HitBox upUp = new BuckshotHitBox(damage, body, img, user);  
        
        body = new Body(new Box(18,18), 1.5f);
        img = new Image("chunk1.png");
        img.setDimensions(15,15);
        img.setColor(new Color(5f,.5f,.5f,1f)); 
        HitBox up = new BuckshotHitBox(damage, body, img, user);  
        
        body = new Body(new Box(18,18), 1.5f);
        img = new Image("chunk1.png");
        img.setDimensions(15,15);
        img.setColor(new Color(5f,.5f,.5f,1f)); 
        HitBox center = new BuckshotHitBox(damage, body, img, user);  
        
        body = new Body(new Box(18,18), 1.5f);
        img = new Image("chunk1.png");
        img.setDimensions(15,15);
        img.setColor(new Color(5f,.5f,.5f,1f));
        HitBox down = new BuckshotHitBox(damage, body, img, user);  
        
        body = new Body(new Box(18,18), 1.5f);
        img = new Image("chunk1.png");
        img.setDimensions(10,10);
        img.setColor(new Color(3f,.5f,.5f,1f));
        HitBox downDown = new BuckshotHitBox(damage, body, img, user);  
        
        //calculate vectors
        int degrees = 7 +r.nextInt(6);
        Vector2f upVector = new Vector2f((float)(Math.cos(degrees * Math.PI/180) *vectorToTarget.x  - (Math.sin(degrees * Math.PI/180))*vectorToTarget.y ),(float)((Math.sin(degrees * Math.PI/180))*vectorToTarget.x  + (Math.cos(degrees * Math.PI/180))*vectorToTarget.y )); // 5 degreees
        upVector.normalise();
        
        degrees = 12 +r.nextInt(8);
        Vector2f upUpVector = new Vector2f((float)(Math.cos(degrees * Math.PI/180)*vectorToTarget.x  - (Math.sin(degrees * Math.PI/180))*vectorToTarget.y ),(float)((Math.sin(degrees * Math.PI/180))*vectorToTarget.x  + (Math.cos(degrees * Math.PI/180))*vectorToTarget.y )); // 10 degrees
        upUpVector.normalise();
        
        degrees = 7 +r.nextInt(6);
        Vector2f downVector = new Vector2f((float)(Math.cos(-degrees * Math.PI/180)*vectorToTarget.x  - (Math.sin(-degrees * Math.PI/180))*vectorToTarget.y ),(float)((Math.sin(-degrees * Math.PI/180))*vectorToTarget.x  + (Math.cos(-degrees * Math.PI/180))*vectorToTarget.y )); // -5 degrees
        downVector.normalise();
        
        degrees = 12 +r.nextInt(8);
        Vector2f downDownVector = new Vector2f((float)(Math.cos(-degrees * Math.PI/180)*vectorToTarget.x  - (Math.sin(-degrees * Math.PI/180))*vectorToTarget.y ),(float)((Math.sin(-degrees * Math.PI/180))*vectorToTarget.x  + (Math.cos(-degrees * Math.PI/180))*vectorToTarget.y )); // -10 degrees
        downDownVector.normalise();
        
        
        //apply forces
        upUp.getBody().addForce(new Vector2f( 4000 * upUpVector.getX(),4000 * upUpVector.getY()));
        up.getBody().addForce(new Vector2f( 4000 * upVector.getX(),4000 * upVector.getY()));
        center.getBody().addForce(new Vector2f( 4000 * vectorToTarget.getX(),4000 * vectorToTarget.getY()));
        down.getBody().addForce(new Vector2f( 4000 * downVector.getX(),4000 * downVector.getY()));
        downDown.getBody().addForce(new Vector2f( 4000 * downDownVector.getX(),4000 * downDownVector.getY()));
        
        //determine angle for the image
        float theta = (float)Math.acos(vectorToTarget.dot(new Vector2f(1,0)));
        if(targetY < userY)
            theta = (float)(2* Math.PI - theta);
        
        //add to world
        upUp.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        upUp.getBody().setRotation((float)theta);
        upUp.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(upUp,Layer.MAIN);
        
        up.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        up.getBody().setRotation((float)theta);
        up.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(up,Layer.MAIN);
        
        center.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        center.getBody().setRotation((float)theta);
        center.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(center,Layer.MAIN);
        
        down.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        down.getBody().setRotation((float)theta);
        down.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(down,Layer.MAIN);
        
        downDown.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        downDown.getBody().setRotation((float)theta);
        downDown.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(downDown,Layer.MAIN);
        
        
        
        
        
    }
    
     
     
    private class BuckshotHitBox extends HitBox
    {
       
        
         public BuckshotHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
            
            //this.body.setDamping(1);
            this.body.setGravityEffected(true);
            this.body.setRestitution(.98f);
            
            AbstractParticleEmitter smokeEmitter = new SmokeEmitter();
            smokeEmitter.setDuration(16);
            smokeEmitter.setParticlesPerFrame(10);
            this.addEmitter(smokeEmitter);
            
            //label effect for destruction disk
            StateEffect labelEffect = new StateEffect(StateEffect.StateEffectType.LABEL, 1) ;
            labelEffect.setName("playerLaser");
            labelEffect.setInfinite();            
            this.getDamage().addCombatEffect(labelEffect);
         }
         
         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 
             
             //remove if we hit a combat entity
             if( other instanceof CombatEntity)
             {
                AbstractParticleEmitter emitter = new LaserBitsEmitter();
                emitter.setPosition(event.getPoint().getX(), event.getPoint().getY());
                emitter.setDuration(1);
                emitter.setParticlesPerFrame(5);
                owningScene.add(emitter,Layer.MAIN);
                 
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
             if(other instanceof WorldObjectEntity)
             {
                 this.body.setOverlapMask(Entity.OverlapMasks.NO_OVERLAP.value);
                 this.body.setRestitution(0);
                 
                 if(!this.getImage().hasImageEffect("fade"))
                 {
                    Object[] points = {new Color(this.getImage().getColor()),new Color(Color.white),new Color(Color.transparent),new Color(Color.transparent)};
                    int[] durations = {100,100,500};
                    ImageEffect fadeEffect =new MultiImageEffect(ImageEffect.ImageEffectType.COLOR,points,durations);
                    fadeEffect.setDelay(300);
                    this.getImage().addImageEffect("fade",fadeEffect);
                    this.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION,499,1,1));
                 }
                 
                 
             }
         }
         
    }

}
