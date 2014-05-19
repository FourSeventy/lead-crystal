
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
        Image img = new Image("plantSpit.png");
        img.setDimensions(40,40);
        img.setColor(new Color(.5f,.5f,.5f,1f));
        GooBombHitBox goo = new GooBombHitBox(damage, body, img, user);
        goo.getBody().setGravityEffected(true);
        
        
        //Determine vector to target
        SylverVector2f distanceVector = user.distanceVector(user.getTarget());
        System.err.println(distanceVector);
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
        AbstractParticleEmitter emitter = new GreenGooEmitter();
        emitter.setAngle((float)(theta * 180f/Math.PI));
        emitter.setDuration(-1);
        goo.addEmitter(emitter);
        

        user.getOwningScene().add(goo,Layer.MAIN);       

        
        //play sound
        
        

        
    }
    
    
    private class GooBombHitBox extends HitBox
    {
         public GooBombHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
                      
         }
         
         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event);
             
             //remove if we hit a world object
             if(other instanceof WorldObjectEntity )
             {
                             
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

}


