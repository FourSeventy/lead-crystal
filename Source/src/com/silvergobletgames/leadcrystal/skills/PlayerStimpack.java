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
public class PlayerStimpack extends Skill
{
    
    public PlayerStimpack()
    {
        //super constructor
        super(SkillID.PlayerStimpack,SkillType.OFFENSIVE,ExtendedImageAnimations.SPELLATTACK,3600,Integer.MAX_VALUE);

        //set the skillID and the name
        this.icon = new Image("stimpack.gif");
        this.skillName = "Stimpack";
        this.skillDescription = "Gives 80% incresed attack speed and 15% move speed for 15 seconds.";
        
        this.unlockCost = 3;
        
    }
    
    public void use(Damage damage, SylverVector2f origin) 
    {
      
        //apply combat effects
        StateEffect cooldownEffect = new StateEffect(StateEffect.StateEffectType.COOLDOWNMODIFIER, 900, -.8f, false){
        @Override
        public void onRemove()
        {
            super.onRemove();
            
            owningEntity.getImage().removeImageEffect("stim");
        }};
        user.getCombatData().addCombatEffect(cooldownEffect);
        user.getCombatData().addCombatEffect(new StateEffect(StateEffect.StateEffectType.SLOW, 900, -.15f, true));
        
        //==============================
        //apply effects to player image
        //==============================
        
        //blinking effect
        Float[] points  ={ 1f, 2f, 1f};
        int[] durations = { 30,30};
        MultiImageEffect stimEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points, durations);
        stimEffect.setRepeating(true);
        user.getImage().addImageEffect("stim",stimEffect);
        
        //stim overlay
        Image overlayImage = new Image("stimpack.gif");
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
