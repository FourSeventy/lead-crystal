
package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.CombatEffect;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import java.util.Random;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.Damage.DamageType;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.GreenGooEmitter;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import net.phys2d.math.ROVector2f;
import net.phys2d.raw.shapes.Circle;

/**
 *
 * @author mike
 */
public class EnemyGooHeal extends Skill 
{
    
    public EnemyGooHeal()
    {
        super(SkillID.EnemyGooHeal,SkillType.HEAL,ExtendedImageAnimations.RANGEDATTACK,200,800);
    }
    
    public void use(Damage damage, SylverVector2f origin)
    {       
        //Damage is scaled with base
        float dAmount = this.getBaseDamage(); 
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.HEAL);
        
        //add slow
        
        //build goo
        Body body = new Body(new Circle(22), 1);
        Image img = new Image("plantSpit.png");
        img.setDimensions(45, 45);
        img.setColor(new Color(1f,1.6f,1f,1f));
        GooHealHitBox goo = new GooHealHitBox(damage, body, img, user);
        
        
        //Determine vector to target
        SylverVector2f distanceVector = user.distanceVector(user.getTarget());
        distanceVector.normalise();
         
        // Bullet force
        float xforce1 = 700*distanceVector.x;
        float yforce1 = 700*distanceVector.y;

        //Dispense goo into the world
        float theta = (float)Math.atan(xforce1/yforce1);
        goo.setPosition(origin.x, origin.y);
        goo.getBody().addForce(new Vector2f((int)xforce1 ,(int)yforce1));
        goo.getBody().setRotation((float)theta);
        goo.getImage().setAngle((float)(theta * 180f/Math.PI)); 
        AbstractParticleEmitter emitter = new GreenGooEmitter();
        emitter.setAngle((float)(theta * 180f/Math.PI));
        emitter.setDuration(-1);
        goo.addEmitter(emitter);
        

        user.getOwningScene().add(goo,Layer.MAIN);       

        
        //play sound
        
    
    }
    
    
    private class GooHealHitBox extends HitBox
    { 
         public GooHealHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
            body.setBitmask(BitMasks.COLLIDE_WORLD.value);
                body.setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);
                      
         }
         
         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event);
             
             
          if(other instanceof WorldObjectEntity || other instanceof CombatEntity)
             {
                 this.getBody().setVelocity(new Vector2f(0,0));
                 this.removeFromOwningScene();
             }
             
                
         }
    }
    
 
}


