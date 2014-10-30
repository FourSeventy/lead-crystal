package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.sylver.graphics.*;
import net.phys2d.math.Vector2f;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.EntityEffect.EntityEffectType;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.core.SceneObjectManager;
import com.silvergobletgames.sylver.util.SylverVector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Circle;

/**
 * 
 * Name: Guard
 * Cost:
 * Cooldown: 30 seconds
 * Description: While active, you take 50% damage.  For the first second that Guard is active, you take no damage.
 */
public class PlayerGravityShield extends PlayerSkill{
    
    public PlayerGravityShield()
    {
        //super constructor 
        super(SkillID.PlayerGravityShield,SkillType.DEFENSIVE, ExtendedImageAnimations.SPELLATTACK,1200,Integer.MAX_VALUE);
 
        //set the skillID and the name
        this.icon = new Image("gravityShieldIcon.png") ;
        this.skillName = "Gravity Shield";
        this.skillDescription = "Creates a defensive shield around the player mitigating melee damage and deflecting ranged attacks. ";
        this.unlockCost = 1;
    }
    
    public void use(Damage damage, SylverVector2f origin) 
    {
        //put overlay on
        Image image = new Image("shield.png");
        image.setAnchor(Anchorable.Anchor.CENTER);
        image.setColor(new Color(2f,2f,2f,.5f));
        Color[] points = {new Color(2f,2f,2.5f,1f), new Color(2.5f,2.5f,3f,1f), new Color(2f,2f,2.5f,1f)};
        int[] durations = {60,60};
        MultiImageEffect renderEffect = new MultiImageEffect(ImageEffect.ImageEffectType.COLOR, points, durations);
        renderEffect.setRepeating(true);
        image.addImageEffect(renderEffect);
        Overlay overlay = new Overlay(image, 300,new SylverVector2f(.5f, .65f));
        overlay.setRelativeSize(2f);
        user.getImage().addOverlay("guardOverlay", overlay);
        
        //add the combat effect
        user.getCombatData().addCombatEffect(new StateEffect(StateEffect.StateEffectType.DAMAGEREDUCTION, 300, .5f, false));
        
        //create entity that pushes away projectiles
        Body b  = new StaticBody(new Circle(10));
        
        GravityWell well = new GravityWell(b,(PlayerEntity)user);
        well.getBody().setBitmask(Entity.BitMasks.NO_COLLISION.value);
        well.getBody().setOverlapMask(Entity.OverlapMasks.NO_OVERLAP.value);
        well.addEntityEffect(new EntityEffect(EntityEffectType.DURATION, 300, 0, 0));
        user.getOwningScene().add(well, Scene.Layer.MAIN);
        
        //play sound
        Sound sound = Sound.locationSound("buffered/powerUpShort.ogg", user.getPosition().x, user.getPosition().y, false, 1f,1.2f);
        user.getOwningScene().add(sound);
        
    }

    public static class GravityWell extends Entity
    {
        PlayerEntity user;
        
         public GravityWell(Body b, PlayerEntity user)
         { 
            super(new Image("blank.png"),b ); 
            this.user = user;

         }

         
         @Override
         public void update()
         {
             super.update();
             
             this.setPosition(this.user.getPosition().x, this.user.getPosition().y);
             
             if(this.owningScene != null)
             for(SceneObject object: this.owningScene.getSceneObjectManager().get(ExtendedSceneObjectGroups.ENEMYPROJECTILE))
             {
                 
                 if(object instanceof HitBox)
                 {                                                         
                     HitBox enemyHitbox = (HitBox)object;
                     
                     
                     if(enemyHitbox.getBody() != null && enemyHitbox.distanceAbs(this) < 75 && enemyHitbox.getBody().getOverlapMask() == Entity.OverlapMasks.PLAYER_TOUCH.value)
                     {
                         //get vector
                         SylverVector2f v = new SylverVector2f(enemyHitbox.getBody().getVelocity().getX(),enemyHitbox.getBody().getVelocity().getY());
                         v = v.negate();
                         
                         SylverVector2f vecToTarget = new SylverVector2f(v.x,v.y);
                         vecToTarget.normalise();
                         
                        
                         //add effect
                         
                        float theta = (float)Math.acos(vecToTarget.dot(new SylverVector2f(1,0)));
                        if(enemyHitbox.getPosition().y < this.user.getPosition().y)
                            theta = (float)(2* Math.PI - theta);                      
     
                        //sparks
                        PointParticleEmitter emitter = new LeadCrystalParticleEmitters.LaserBitsEmitter();
                        emitter.setPosition(enemyHitbox.getPosition().x, enemyHitbox.getPosition().y);
                        emitter.setDuration(1);
                        emitter.setAngle((float)(theta * (180f/Math.PI)));
                        emitter.setParticlesPerFrame(5);
                        emitter.setSize(3);
                        emitter.setColor(new Color(1.5f,1.5f,1.5f));
                        user.getOwningScene().add(emitter,Scene.Layer.MAIN);

                         //redirect projectile
                        enemyHitbox.getBody().setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);
                        enemyHitbox.getBody().removeExcludedBody(enemyHitbox.getSource().getBody());
                        enemyHitbox.setSource(this);
                        
                        
                        enemyHitbox.getBody().setVelocity(new Vector2f(v.x,v.y));
//                        SylverVector2f vector = enemyHitbox.distanceVector(this);
//                        vector.normalise();
//                        float scalingFactor = enemyHitbox.getBody().getMass();
//                        if(scalingFactor > 20)
//                            scalingFactor = 1;
//                        vector.scale(100 * scalingFactor );
//
//                        Vector2f vec = new Vector2f(vector.x,vector.y);
//                        vec = vec.negate();
//                        enemyHitbox.getBody().addForce(vec);
                        
                         //play sound
                        Sound sound = Sound.locationSound("buffered/powerBoop.ogg", user.getPosition().x, user.getPosition().y, false, 1f,1.2f);
                        user.getOwningScene().add(sound);
                         
                     }
                 }
             }
         }
         
    }
}
