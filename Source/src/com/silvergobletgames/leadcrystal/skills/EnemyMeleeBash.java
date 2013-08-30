package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Image;
import java.util.Random;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Shape;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.EntityEffect.EntityEffectType;
import com.silvergobletgames.sylver.util.SylverVector2f;


public class EnemyMeleeBash extends Skill
{
   
    public EnemyMeleeBash()
    {
        super(SkillID.EnemyMeleeBash,SkillType.OFFENSIVE, ExtendedImageAnimations.MELEEATTACK,180, 150);

    }
    
    public void use(Damage damage, SylverVector2f origin)
    {
        
        //Damage is scaled with weapon damage
        float dAmount =  this.getBaseDamage();
        damage.getAmountObject().adjustBase(dAmount);
        damage.setType(Damage.DamageType.PHYSICAL);
        
        // stun
        damage.addCombatEffect(new StateEffect(StateEffect.StateEffectType.STUN, 120)); //2 second stun
        
        Body body = new StaticBody(new Box(90, 90));
        body.addExcludedBody(user.getBody());
        Image image = new Image("blank.png");
        image.setDimensions(90, 90);
        HitBox hitBox = new HitBox(damage,body, image,this.user);
        hitBox.addEntityEffect(new EntityEffect(EntityEffectType.DURATION, 20, 1, 1));
        hitBox.setPosition(user.getPosition().x + 100*user.getFacingDirection().value, user.getPosition().y);
        this.user.getOwningScene().add(hitBox,Layer.MAIN);        
    }
       
}

