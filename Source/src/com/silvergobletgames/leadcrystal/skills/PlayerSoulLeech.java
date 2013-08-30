package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Circle;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.util.SylverVector2f;

/**
 * 
 * Name: Guard
 * Cost:
 * Cooldown: 30 seconds
 * Description: While active, you take 50% damage.  For the first second that Guard is active, you take no damage.
 */
public class PlayerSoulLeech extends Skill
{
    
    public PlayerSoulLeech()
    {
        //super constructor
        super(SkillID.PlayerSoulLeech,SkillType.OFFENSIVE,ExtendedImageAnimations.SPELLATTACK,3600,Integer.MAX_VALUE);

        //set the skillID and the name
        this.icon = new Image("soulLeech.jpg");
        this.skillName = "Soul Leech";
        this.skillDescription = "Activates an aura that causes 30% of your damage to leech life for 30 seconds.";
        
        this.unlockCost = 3;
        
    }
    
    public void use(Damage damage, SylverVector2f origin) 
    {
      
        //apply life leech combat effect
        user.getCombatData().addCombatEffect(new StateEffect(StateEffect.StateEffectType.LIFELEECH, 900, .30f, false));
        
        //==============================
        //apply effects to player image
        //==============================
        
        //leech overlay overlay
        Image overlayImage = new Image("soulLeech.jpg");
        overlayImage.setDimensions(35, 35);
        overlayImage.setAnchor(Anchorable.Anchor.CENTER);
        Float[] pointss  ={ 1f, 0f, 1f};       
        int[] durationss = { 30,30};
        MultiImageEffect overlayEffect = new MultiImageEffect(ImageEffect.ImageEffectType.ALPHABRIGHTNESS, pointss, durationss);
        overlayEffect.setRepeating(true);
        overlayEffect.setDelay(660); 
        overlayImage.addImageEffect(overlayEffect);
        Overlay overlay = new Overlay(overlayImage, 900, new SylverVector2f(.5f,1.1f));
        overlay.useRelativeSize = false;
        user.getImage().addOverlay(overlay);
        
    }

}
