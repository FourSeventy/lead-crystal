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


public class EnemySmallMelee extends Skill{
    
    public EnemySmallMelee()
    {
        super(SkillID.EnemySmallMelee,Skill.SkillType.OFFENSIVE, ExtendedImageAnimations.MELEEATTACK,40,120);
        

    }
    
    
    public void use(Damage damage, SylverVector2f origin)
    {
        
        //Damage is scaled with weapon damage
        float dAmount = this.getBaseDamage(); 
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.PHYSICAL);
        
        //build body of attack
        Body body = new StaticBody(new Box(50, 36));
        body.addExcludedBody(user.getBody());
        Image image = new Image("swipe.png");        
        image.setColor(new Color(1,1,1f,1f));
        image.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.COLOR, 50, new Color(1,1,1f,1f), new Color(1,1,1f,0f)));
        image.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 50, 1, 1));
        if (user.getFacingDirection().equals(FacingDirection.RIGHT))
            image.setHorizontalFlip(true);
        image.setAnchor(Anchorable.Anchor.CENTER);
        image.setPositionAnchored(user.getPosition().x + 65*user.getFacingDirection().value, user.getPosition().y);
        this.user.getOwningScene().add(image,Layer.MAIN);
        
        HitBox hitBox = new HitBox(damage,body,new Image("blank.png"),this.user);
        hitBox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 25, 1, 1));
        hitBox.setPosition(user.getPosition().x + 65*user.getFacingDirection().value, user.getPosition().y);
        this.user.getOwningScene().add(hitBox,Layer.MAIN);        
    }
    

}
