package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Circle;


public class EnemyBossBarrage extends Skill{
    
    public EnemyBossBarrage()
    {
        super(SkillID.EnemyBossBarrage,Skill.SkillType.OFFENSIVE, ExtendedImageAnimations.RANGEDATTACK,60 * 10,1200);
        

    }
    
    
    public void use(Damage damage, SylverVector2f origin)
    {
        
     
       //Damage is scaled with base damage
        float dAmount = this.getBaseDamage(); 
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.PHYSICAL);
       
         //build goo
        Body body = new Body(new Circle(50), 1);
        Image img = new Image("poison_goo_ball.png");
        img.setDimensions(100, 100);
        img.setColor(new Color(1f,1.2f,1f,1f));
        BarrageHitBox goo = new BarrageHitBox(damage, body, img, user);
        
        
        //Determine vector to target
        SylverVector2f distanceVector = user.distanceVector(user.getTarget());
        distanceVector.normalise();
         
        // Bullet force
        float xforce1 = 700*distanceVector.x;
        float yforce1 = 700*distanceVector.y;

        
        float theta = (float)Math.acos(distanceVector.dot(new SylverVector2f(1,0)));
        if(user.getTarget().getPosition().y < origin.y)
        {
            theta = (float)(2* Math.PI - theta);
        }
        //Dispense goo into the world
        goo.setPosition(origin.x, origin.y);
        goo.getBody().addForce(new Vector2f((int)xforce1 ,(int)yforce1));
        goo.getBody().setRotation((float)theta);
        goo.getImage().setAngle((float)(theta * 180f/Math.PI)); 
        

        user.getOwningScene().add(goo,Layer.MAIN);       

        
        //add sound
        Sound attackSound = Sound.locationSound("buffered/spit1.ogg", origin.x, origin.y, false);               
        user.getOwningScene().add(attackSound);
            
            
        
    }
    
    private class BarrageHitBox extends HitBox
    {
         public BarrageHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
                      
         }
         
         @Override
         public void update()
         {
             super.update();
             
             
         }
         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event);
                         
         }
    }
    

}
