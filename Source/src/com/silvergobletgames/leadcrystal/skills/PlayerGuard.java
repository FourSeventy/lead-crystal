package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.sylver.graphics.*;
import net.phys2d.math.Vector2f;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.EntityEffect.EntityEffectType;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
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
public class PlayerGuard extends Skill{
    
    public PlayerGuard()
    {
        //super constructor
        super(SkillID.PlayerGuard,SkillType.DEFENSIVE, ExtendedImageAnimations.SPELLATTACK,1800,Integer.MAX_VALUE);
 
        //set the skillID and the name
        this.icon = new Image("guardIcon.jpg") ;
        this.skillName = "Guard";
        this.skillDescription = "Creates a defensive shield around the player mitigating damage. For the first 1 second after use, the damage mitigation is 100%";
        this.unlockCost = 2;
    }
    
    public void use(Damage damage, SylverVector2f origin) 
    {
        //put overlay on
        Image image = new Image("newShield.png");
        image.setAnchor(Anchorable.Anchor.CENTER);
        image.setColor(new Color(3,3,4,1f));
        Color[] points = {new Color(4,4,5,1f), new Color(5,5,6.25f,1f), new Color(4,4,5,1f)};
        int[] durations = {60,60};
        MultiImageEffect renderEffect = new MultiImageEffect(ImageEffect.ImageEffectType.COLOR, points, durations);
        renderEffect.setRepeating(true);
        image.addImageEffect(renderEffect);
        Overlay overlay = new Overlay(image, 300,new SylverVector2f(.5f, .65f));
        overlay.setRelativeSize(.5f);
        user.getImage().addOverlay("guardOverlay", overlay);
        
        //add the combat effect
        user.getCombatData().addCombatEffect(new StateEffect(StateEffect.StateEffectType.DAMAGEREDUCTION, 300, .5f, false));
        
        //add the second combat effect that makes 100% damage reduction for the first 1 second
        user.getCombatData().addCombatEffect(new StateEffect(StateEffect.StateEffectType.DAMAGEREDUCTION, 60, .5f, false));
        
        //create entity that pushes away projectiles
        Body b  = new StaticBody(new Circle(10));
        
        GravityWell well = new GravityWell(b,(PlayerEntity)user);
        well.getBody().setBitmask(Entity.BitMasks.NO_COLLISION.value);
        well.getBody().setOverlapMask(Entity.OverlapMasks.NO_OVERLAP.value);
        well.addEntityEffect(new EntityEffect(EntityEffectType.DURATION, 300, 0, 0));
        user.getOwningScene().add(well, Scene.Layer.MAIN);
        
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
                        enemyHitbox.getBody().setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);
                        enemyHitbox.getBody().removeExcludedBody(enemyHitbox.getSource().getBody());
                        
                        SylverVector2f v = new SylverVector2f(enemyHitbox.getBody().getVelocity().getX(),enemyHitbox.getBody().getVelocity().getY());
                        v = v.negate();
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
                         
                     }
                 }
             }
         }
         
    }
}
