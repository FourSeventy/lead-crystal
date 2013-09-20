
package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.ai.AIState;
import com.silvergobletgames.leadcrystal.ai.BrainFactory;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.MoleAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.TikiGuyAnimationPack;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.skills.PlayerLaserShot.LaserHitbox;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.graphics.MultiImageEffect;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Circle;

/**
 *
 * @author Mike
 */
public class EnemyAntThrow extends Skill {
    
    
    public EnemyAntThrow()
    {
        super(SkillID.EnemyAntThrow,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,250,1200);

    }
    
    /**
     * Executes this skill
     * @param user 
     */
    public void use(Damage damage, SylverVector2f origin)
    {
        
        //Damage is scaled with weapon damage
        float dAmount = this.getBaseDamage(); 
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.PHYSICAL);
        
        //add brightness effect to damage
        Float[] points = {1f,2f,1f};
        int[] durations = {10,5};
        ImageEffect brightnessEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points,durations);
        damage.addImageEffect(brightnessEffect);
              

        //build body
        Body body = new Body(new Box(20,5), 10);
        Image img = new Image(new MoleAnimationPack());
        img.setScale(.7f);
        ImageEffect spin = new ImageEffect(ImageEffect.ImageEffectType.ROTATION, 60, 0, 360);
        spin.setRepeating(true);
        img.addImageEffect(spin);
        AntHitbox tossedAnt = new AntHitbox(damage, body, img, user);        
        
        //Determine vector to target
        SylverVector2f distanceVector = user.distanceVector(user.getTarget());
        distanceVector.add(new SylverVector2f(0,400));
        distanceVector.normalise();
         
        // Bullet force
        float scale = 10_000 + (float)Math.random() * 20_000;
        float xforce = scale*distanceVector.x;
        float yforce = scale*distanceVector.y;
       
        //Dispense ant into the world
        float theta = (float)Math.atan(distanceVector.y/distanceVector.x);
        tossedAnt.setPosition(origin.x, origin.y);
        tossedAnt.getBody().addForce(new Vector2f((int)xforce ,(int)yforce));
        tossedAnt.getBody().setRotation((float)theta);
        tossedAnt.getImage().setAngle((float)(theta * 180f/Math.PI)); 

        user.getOwningScene().add(tossedAnt,Layer.MAIN);
 
        //play sound

    
        
        
    }
    
    
    public static class AntHitbox extends HitBox
    {
        public AntHitbox(Damage d, Body b, Image i, Entity user)
        { 
            super(d, b, i, user); 
            this.body.setGravityEffected(true);
         }
        
        public void collidedWith(Entity other, CollisionEvent event)
        {
            
             //if we collided with a world object or player
             if(other instanceof WorldObjectEntity || other instanceof CombatEntity)
             {
                 
                 //==================
                 // Create ant NPE 
                 //==================
                 
                 Image antImage = new Image(new TikiGuyAnimationPack());
                 antImage.setScale(.7f);
                 Body antBody = new Body(new Circle(35),3);
                 antBody.setFriction(1);
                 antBody.setGravityEffected(true);
                 antBody.setOverlapMask(Entity.OverlapMasks.NPE.value);
                 antBody.setBitmask(Entity.BitMasks.NPE.value);
                 NonPlayerEntity ant = new NonPlayerEntity(antImage,antBody);
                 ant.setBrain(BrainFactory.getInstance().getBrain(BrainFactory.BrainID.Fighter));
                 ant.getSkillManager().learnSkill(SkillID.EnemySmallMelee);
                 ant.getCombatData().baseDamage.setBase(5);
                 ant.getCombatData().maxHealth.setBase(20);
                 ant.getCombatData().currentHealth = 20;
                 ant.setPosition(this.body.getLastPosition().getX(), this.body.getLastPosition().getY() + 10);
                 ant.getBrain().getStateMachine().changeState(AIState.StateID.SPAWNING);
                 this.getOwningScene().add(ant,Layer.MAIN);
                 
                 
                 //remove ant hitbox from scene
                 this.getBody().setVelocity(new Vector2f(0,0));
                 this.removeFromOwningScene();
             }
        }
    }

}
