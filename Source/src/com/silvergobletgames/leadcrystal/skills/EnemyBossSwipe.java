package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import java.util.Random;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.sylver.util.SylverVector2f;


public class EnemyBossSwipe extends Skill{
    
    public EnemyBossSwipe()
    {
        super(SkillID.EnemyBossSwipe,Skill.SkillType.OFFENSIVE, ExtendedImageAnimations.MELEEATTACK,180,1000);
        

    }
    
    
    public void use(Damage damage, SylverVector2f origin)
    {
        
        System.err.println("HUEH?");
        //Damage is scaled with base damage
        float dAmount = this.getBaseDamage(); 
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.PHYSICAL);
        
        for( int i =0; i <=1; i++)
        {   
        
            //build body of attack
            Body body = new Body(new Box(40, 150),1);
            body.setRotatable(false);
            body.addExcludedBody(user.getBody());
            Image image = new Image("swipe.png"); 
            image.setDimensions(100, 150);
            image.setColor(new Color(1,1,1f,1f));
           // image.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.COLOR, 50, new Color(1,1,1f,1f), new Color(1,1,1f,0f)));
            image.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 300, 1, 1));
            if (user.getFacingDirection().equals(FacingDirection.RIGHT))
                image.setHorizontalFlip(true);
            image.setAnchor(Anchorable.Anchor.CENTER);
            image.setPositionAnchored(origin.x,origin.y);
            this.user.getOwningScene().add(image,Layer.MAIN);

            HitBox hitBox = new HitBox(damage,body,image,this.user);
            hitBox.getBody().setBitmask(Entity.BitMasks.NO_COLLISION.value);
            hitBox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 300, 1, 1));
            hitBox.setPosition(origin.x,origin.y);
            
         
            SylverVector2f vectorToTarget = user.distanceVector(user.getTarget());
            int degrees = i * 20;
                
            SylverVector2f upVector = new SylverVector2f((float)(Math.cos(degrees * Math.PI/180) *vectorToTarget.x  - (Math.sin(degrees * Math.PI/180))*vectorToTarget.y ),(float)((Math.sin(degrees * Math.PI/180))*vectorToTarget.x  + (Math.cos(degrees * Math.PI/180))*vectorToTarget.y )); // 5 degreees
            upVector.normalise();
            hitBox.getBody().addForce(new Vector2f(1000*upVector.x,1000*upVector.y));
            
                   
            float theta = (float)Math.acos(upVector.dot(new SylverVector2f(1,0)));
            if(user.getTarget().getPosition().y < user.getPosition().y)
            theta = (float)(2* Math.PI - theta);
            
             
            hitBox.getBody().setRotation((float)theta);
            hitBox.getImage().setAngle((float)(theta * 180f/Math.PI)); 
            
            System.out.println(theta);
            
            this.user.getOwningScene().add(hitBox,Layer.MAIN);  
            
            
        }
    }
    

}
