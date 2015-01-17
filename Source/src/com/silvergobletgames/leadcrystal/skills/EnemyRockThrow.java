
package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.CombatEffect;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.Damage.DamageType;
import com.silvergobletgames.leadcrystal.combat.DotEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.BlackGooEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.BlackGooGroundEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.GreenGooEmitter;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;
import net.phys2d.math.ROVector2f;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Circle;

/**
 *
 * @author mike
 */
public class EnemyRockThrow extends Skill 
{
    
    public EnemyRockThrow()
    {
        super(SkillID.EnemyRockThrow,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,200,800);
    }
    
    public void use(Damage damage, SylverVector2f origin)
    {       
        
        //Damage is scaled with base
        float dAmount = this.getBaseDamage(); 
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.PHYSICAL);
        
        
        //build rock
        Body body = new Body(new Circle(20), 3);
        Image img = new Image("poison_goo_ball.png");
        img.setDimensions(40,40);
        img.setColor(new Color(.2f,.2f,.2f,1f));
        EnemyTarHitBox goo = new EnemyTarHitBox(damage, body, img, user);
        goo.getBody().setGravityEffected(true);
        
        
        //Determine vector to target
        SylverVector2f distanceVector = user.distanceVector(user.getTarget());
        distanceVector.normalise();
        
         distanceVector.add(new SylverVector2f(0,1f));
         distanceVector.normalise();
        
        
         
        // Bullet force
        float xforce1 = 50_00*distanceVector.x;
        float yforce1 = 50_00*distanceVector.y;

        //Dispense goo into the world
        float theta = (float)Math.atan(xforce1/yforce1);
        goo.setPosition(origin.x, origin.y);
        goo.getBody().addForce(new Vector2f((int)xforce1 ,(int)yforce1));
        goo.getBody().setRotation((float)theta);
        goo.getImage().setAngle((float)(theta * 180f/Math.PI)); 
        AbstractParticleEmitter emitter = new BlackGooEmitter();
        emitter.setAngle((float)(theta * 180f/Math.PI));
        emitter.setDuration(-1);
        goo.addEmitter(emitter);
        

        user.getOwningScene().add(goo,Layer.MAIN);       

        
        //play sound
        Sound sound = Sound.locationSound("buffered/jump.ogg", user.getPosition().x, user.getPosition().y, false, .8f, .7f);
        user.getOwningScene().add(sound);
               
    }
    
    
    private class EnemyTarHitBox extends HitBox
    {
         public EnemyTarHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
                      
         }
         
         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event);
             
             //remove if we hit a world object
             if(other instanceof WorldObjectEntity )
             {
                          
                
                //if we landed on the top of a worldObjectEntity
                if(-event.getNormal().getY() > .65 )
                {
                     //put slowing tar puddle in the world
                    HitBox slowingTar = new SlowingTarHitBox(user);
                    slowingTar.setPosition(this.getPosition().x, this.getPosition().y);
                    slowingTar.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 600, 0, 0));
                    this.getOwningScene().add(slowingTar, Layer.MAIN);
                    
                    //play sound
                    Sound sound = Sound.locationSound("buffered/usePotion.ogg", user.getPosition().x, user.getPosition().y, false, 1.3f, .5f);
                    this.getOwningScene().add(sound);
                                     
                }
                 
                 //remove from world
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
             else if(other instanceof CombatEntity)
             {
                 this.getBody().setVelocity(new Vector2f(0,0));
                 this.removeFromOwningScene();
             }
             
                
         }
    }
    
    
    private class SlowingTarHitBox extends HitBox
    {
        
        public SlowingTarHitBox(CombatEntity user)
        {
            super(new Damage(Damage.DamageType.NODAMAGE, 0), new Body(new Box(200,50), 4), new Image("icefloor.png"),user); 
           // this.getImage().setAlphaBrightness(.4f);
            this.image.setDimensions(200, 50);
            this.image.setColor(new Color(.2f,.2f,.2f,1));
            this.body.setGravityEffected(false);
            this.body.removeExcludedBody(user.getBody());
            this.getBody().setBitmask(BitMasks.NO_COLLISION.value);
            this.getBody().setOverlapMask(OverlapMasks.OVERLAP_ALL.value);
            
            //add its praticle effects
            AbstractParticleEmitter emitter = new BlackGooGroundEmitter();
            emitter.setDuration(600);
            this.addEmitter(emitter);
        }
        
        public void collidedWith(Entity other, CollisionEvent event)
        {
            //if we collided with enemy add damage
            if(other instanceof PlayerEntity)
            {
                //remove finite effects
                ((CombatEntity)other).getCombatData().removeCombatEffect("tarFinite");
                ((CombatEntity)other).getCombatData().removeCombatEffect("tarDot");
                
                //apply slow
                StateEffect slow = new StateEffect(StateEffect.StateEffectType.SLOW, 10, .50f, true);
                slow.setInfinite();
                ((CombatEntity)other).getCombatData().addCombatEffect("tarSlow", slow);

                
                DotEffect tarDot = new DotEffect( 5 * 60, 30, new Damage(DamageType.POISON, 2));    
                tarDot.setInfinite();
                ((CombatEntity)other).getCombatData().addCombatEffect("tarDotInfinite", tarDot);
               // ((CombatEntity)other).getImage().addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.COLOR, 5 * 60, new Color(150,40,150), new Color(1f,1f,1f)));
                               
            }
            
        }
        
        public void separatedFrom(Entity other, CollisionEvent event)
        {
            if(other instanceof PlayerEntity)
            {
                //remove infinite effects 
                ((CombatEntity)other).getCombatData().removeCombatEffect("tarSlow");
                ((CombatEntity)other).getImage().setColor(new Color(1,1,1,1));
                
                ((CombatEntity)other).getCombatData().removeCombatEffect("tarDotInfinite");
    
                
                //apply finite slow
                StateEffect slow = new StateEffect(StateEffect.StateEffectType.SLOW, 180, .50f, true);
                ((CombatEntity)other).getCombatData().addCombatEffect("tarFinite", slow);
                
                //apply finite dot
                DotEffect tarDot = new DotEffect( 3 * 60, 30, new Damage(DamageType.POISON, 2));    
                ((CombatEntity)other).getCombatData().addCombatEffect("tarDot", tarDot);
            
            }
        }
        

    }

}


