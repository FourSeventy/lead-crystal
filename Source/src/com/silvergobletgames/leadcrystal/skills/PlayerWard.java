package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.DotEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.IceEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.IceEmitter2;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.scenes.GameScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.UUID;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Circle;

/**
 * 
 * Name: Guard
 * Cost:
 * Cooldown: 30 seconds
 * Description: While active, you take 50% damage.  For the first second that Guard is active, you take no damage.
 */
public class PlayerWard extends PlayerSkill{
    
    public PlayerWard()
    {
        //super constructor 
        super(SkillID.PlayerWard,SkillType.OFFENSIVE,ExtendedImageAnimations.SPELLATTACK, 1800, Integer.MAX_VALUE);
        
        //set the skillID and the name
        this.icon = new Image("iceWardIcon.png");
        this.skillName = "Ice Storm";
        this.skillDescription = "Creates an icey storm in an area damaging and slowing all enemies in its radius.";
        this.skillDamageDescription = "Damage: 2 per second";
        
        this.unlockCost = 1;

    }
    
    public void use(Damage damage, SylverVector2f origin) 
    {
        
        //get targeting data
        TargetingData targetingData = this.getTargetingData(origin);
        SylverVector2f vectorToTarget = targetingData.vectorToTarget;
        float theta = targetingData.theta;
        
        PlayerEntity player = (PlayerEntity) user;
        float targetX = ((GameScene)player.getOwningScene()).getWorldMouseLocation().x;
        float targetY = ((GameScene)player.getOwningScene()).getWorldMouseLocation().x;
        
       
       //build body of the hitbox
        Body body = new Body(new Box(100,15), 1);
        Image img = new Image("icefloor.png");
        img.setDimensions(100, 40);
        IceTrapHitbox hitbox = new IceTrapHitbox(new Damage(Damage.DamageType.NODAMAGE, 0), body, img, user,damage); 
             
         //calculate force for the hitbox
        float xforce = 1000*vectorToTarget.x;
        float yforce = 1000*vectorToTarget.y;
        
        if(targetX < origin.x)
        {
           img.setVerticalFlip(true);
        }
        
        //Dispense hitbox into the world
        hitbox.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        hitbox.getBody().addForce(new Vector2f(xforce ,yforce));
        hitbox.getBody().setRotation((float)theta);
        //hitbox.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(hitbox,Layer.MAIN);     
        
        //play sound
        Sound sound = Sound.locationSound("buffered/jump.ogg", user.getPosition().x, user.getPosition().y, false, .8f, 1.4f);
        user.getOwningScene().add(sound);
     
        
       
    }
    
    
    
    private class IceTrapHitbox extends HitBox
    {
        private Damage passthroughDamage;
        private boolean used = false;
        
         public IceTrapHitbox(Damage d, Body b, Image i, Entity user,Damage passthroughDamage)
         { 
            super(d, b, i, user); 
            
            this.getBody().setGravityEffected(true);
            this.passthroughDamage = passthroughDamage;
            
         }
         
         public void collidedWith(Entity other, CollisionEvent event)
         {            
             if(other instanceof WorldObjectEntity && !used)
             {
                 //if we landed on the top of a worldObjectEntity
                if(-event.getNormal().getY() > .75)
                {
                    this.used = true;

                    this.getBody().setDamping(1);
                    this.getBody().setRotDamping(1);


                    //put in ice damage
                    IceWardHitbox iceHitbox = new IceWardHitbox((CombatEntity)this.sourceEntity,this.passthroughDamage);
                    iceHitbox.setPosition(this.getPosition().x, this.getPosition().y);
                    iceHitbox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 900, 0, 0));
                    this.getOwningScene().add(iceHitbox, Layer.MAIN); 

                    //put in own duration effect
                    this.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 1020, 0, 0)); 
                    ImageEffect fadeEffect =new ImageEffect(ImageEffect.ImageEffectType.COLOR, 100,new Color(Color.white),new Color(Color.transparent));
                    fadeEffect.setDelay(920);
                    this.getImage().addImageEffect(fadeEffect); 
                }
                 
             }
         }
        
        
    }
    
    private class IceWardHitbox extends HitBox
    {
        private Damage passthroughDamage;
        
        public IceWardHitbox(CombatEntity user, Damage damage)
        {
            super(new Damage(Damage.DamageType.NODAMAGE, 0), new Body(new Circle(250), 4), new Image("blank.png"),user); 
            this.getImage().setAlphaBrightness(.4f);
            this.body.setGravityEffected(false);
            this.body.removeExcludedBody(user.getBody());
            this.getBody().setBitmask(BitMasks.NO_COLLISION.value);
            this.getBody().setOverlapMask(OverlapMasks.OVERLAP_ALL.value);
            
            //add its praticle effects
            AbstractParticleEmitter emitter = new IceEmitter();
            emitter.setDuration(900);
            this.addEmitter(emitter);
            
            emitter = new IceEmitter2();
            emitter.setDuration(900);
            this.addEmitter(emitter);
            
            this.passthroughDamage = damage;
            this.passthroughDamage.setType(Damage.DamageType.FROST);
            this.passthroughDamage.getAmountObject().setBase(1);
        }
        
        public void collidedWith(Entity other, CollisionEvent event)
        {
            //if we collided with enemy add damage
            if(other instanceof NonPlayerEntity)
            {
                //apply slow
                StateEffect slow = new StateEffect(StateEffect.StateEffectType.SLOW, 10, .75f, true);
                slow.setInfinite();
                ((CombatEntity)other).getCombatData().addCombatEffect("iceSlow", slow);
                
                //apply attack speed slow
                StateEffect attackSpeedSlow = new StateEffect(StateEffect.StateEffectType.COOLDOWNMODIFIER, 10, 1.5f, true);
                attackSpeedSlow.setInfinite();
                ((CombatEntity)other).getCombatData().addCombatEffect("iceAttackSlow", attackSpeedSlow);
                
                //apply image effect
                ((CombatEntity)other).getImage().setColor(new Color(.5f,.5f,2)); 
                
                //apply damage dot
                DotEffect heal = new DotEffect(1, 30, this.passthroughDamage);
                heal.setInfinite();                
                ((CombatEntity)other).getCombatData().addCombatEffect("iceDot", heal);
            }
            
        }
        
        public void separatedFrom(Entity other, CollisionEvent event)
        {
            if(other instanceof NonPlayerEntity)
            {
                //remove infinite effects 
                ((CombatEntity)other).getCombatData().removeCombatEffect("iceDot");
                ((CombatEntity)other).getCombatData().removeCombatEffect("iceSlow");
                ((CombatEntity)other).getCombatData().removeCombatEffect("iceAttackSlow");
                ((CombatEntity)other).getImage().setColor(new Color(1,1,1,1));
                
                //apply finite effects
                
                //apply slow
                StateEffect slow = new StateEffect(StateEffect.StateEffectType.SLOW, 180, .75f, true);
                ((CombatEntity)other).getCombatData().addCombatEffect("iceSlow", slow);
                
                //apply image effect
                ((CombatEntity)other).getImage().addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.COLOR, 180, new Color(.5f,.5f,2), new Color(1f,1f,1f)));
                
                //apply damage dot
                Damage healDamage = new Damage(Damage.DamageType.FROST, 1);
                DotEffect heal = new DotEffect(180, 30, healDamage);             
                ((CombatEntity)other).getCombatData().addCombatEffect("iceDot", heal);
            }
        }
        

    }
     
}
