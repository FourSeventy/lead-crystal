package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
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
import com.silvergobletgames.leadcrystal.combat.DotEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.IceEmitter;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.UUID;
import net.phys2d.raw.shapes.Box;

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
        //super constructor 1800
        super(SkillID.PlayerWard,SkillType.OFFENSIVE,ExtendedImageAnimations.SPELLATTACK, 30, Integer.MAX_VALUE);
        
        //set the skillID and the name
        this.icon = new Image("icetrapIcon.jpg");
        this.skillName = "Ice Ward";
        this.skillDescription = "Places a ward on the ground damaging and slowing all enemies in its radius.";
        
        this.unlockCost = 1;

    }
    
    public void use(Damage damage, SylverVector2f origin) 
    {
        
        //get targeting data
        TargetingData targetingData = this.getTargetingData(origin);
        SylverVector2f vectorToTarget = targetingData.vectorToTarget;
        float theta = targetingData.theta;
        
        //place ice trap hitbox in the world
       //build body of the laser
        Body body = new Body(new Box(57,33), 1);
        Image img = new Image("computer.png");
        img.setAnchor(Anchorable.Anchor.LEFTCENTER);
        img.setDimensions(57, 33);
        IceTrapHitbox hitbox = new IceTrapHitbox(new Damage(Damage.DamageType.NODAMAGE, 0), body, img, user); 
        
         //calculate force for the bullet
        float xforce = 1000*vectorToTarget.x;
        float yforce = 1000*vectorToTarget.y;
        
        //Dispense laser into the world
        hitbox.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        hitbox.getBody().addForce(new Vector2f(xforce ,yforce));
        hitbox.getBody().setRotation((float)theta);
        hitbox.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(hitbox,Layer.MAIN);     
        
        
     
        
       
    }
    
    private class IceTrapHitbox extends HitBox
    {
        boolean used = false;
         public IceTrapHitbox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
            
            this.getBody().setGravityEffected(true);
            
         }
         
         public void collidedWith(Entity other, CollisionEvent event)
         {            
             if(other instanceof WorldObjectEntity && !used)
             {
                 this.used = true;
                 
                 this.getBody().setDamping(1);
                 
                 
                 //put in ice damage
                 IceWardHitbox iceHitbox = new IceWardHitbox((CombatEntity)this.sourceEntity);
                 iceHitbox.setPosition(this.getPosition().x, this.getPosition().y);
                 this.getOwningScene().add(iceHitbox, Layer.MAIN); 
             }
         }
        
        
    }
    
    private class IceWardHitbox extends HitBox
    {
        
        public IceWardHitbox(CombatEntity user)
        {
            super(new Damage(Damage.DamageType.NODAMAGE, 0), new Body(new Circle(200), 4), new Image("blank.png"),user); 
            
            this.body.setGravityEffected(false);
            this.body.removeExcludedBody(user.getBody());
            this.getBody().setBitmask(BitMasks.NO_COLLISION.value);
            this.getBody().setOverlapMask(OverlapMasks.OVERLAP_ALL.value);
            
            //add its praticle effects
            AbstractParticleEmitter emitter = new IceEmitter();
            emitter.setDuration(900);
            this.addEmitter(emitter);
        }
        
        public void collidedWith(Entity other, CollisionEvent event)
        {
            //if we collided with a player add a heal dot
            if(other instanceof NonPlayerEntity)
            {
                //apply slow
                StateEffect slow = new StateEffect(StateEffect.StateEffectType.SLOW, 10, .75f, true);
                slow.setInfinite();
                ((CombatEntity)other).getCombatData().addCombatEffect("iceSlow", slow);
                
                //apply image effect
                ((CombatEntity)other).getImage().setColor(new Color(.5f,.5f,2)); 
                
                //apply damage dot
                Damage healDamage = new Damage(Damage.DamageType.FROST, 1);
                DotEffect heal = new DotEffect(1, 30, healDamage);
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
