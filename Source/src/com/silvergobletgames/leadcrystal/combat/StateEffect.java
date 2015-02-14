package com.silvergobletgames.leadcrystal.combat;

import com.silvergobletgames.sylver.graphics.*;
import net.phys2d.math.Vector2f;


/**
 *
 * @author mike
 */
public class StateEffect extends CombatEffect 
{
    private StateEffectType stateEffectType;
    public float amount;
    private boolean inPercent;
    
    public static enum StateEffectType{
        //WARNING - changing the names of this enum will break save data
        BASEDAMAGE,HEALTH,DAMAGEREDUCTION,CCREDUCTION,CRITCHANCE,CRITMODIFIER,HEALINGMODIFIER,COOLDOWNMODIFIER,SLOW,STUN,IMMOBILIZE,IMMUNE,LABEL,LIFELEECH;
    }
    
    //=============
    // Constructors
    //=============
    
    private StateEffect()
    {
        
    }
    
    public StateEffect(StateEffectType type, int duration)
    {
        this(type,duration,0,false);
    }
    
    public StateEffect(StateEffectType type, int duration,float amount, boolean inPercent)
    {
        this.stateEffectType = type;
        this.duration = duration;
        this.amount = amount;
        this.inPercent = inPercent;
        
        //build default effects for some types
        switch(type)
        {
            case STUN:
            {
                Image stunSpiral = new Image("stunSpiral.png");
                ImageEffect spiralEffect = new ImageEffect(ImageEffect.ImageEffectType.ROTATION, 90, 0, 360);
                spiralEffect.setRepeating(true);
                stunSpiral.addImageEffect(spiralEffect);
                stunSpiral.setAnchor(Anchorable.Anchor.BOTTOMCENTER);
                Overlay overlay = new Overlay(stunSpiral);
                overlay.setRelativePosition(.5f, 1.2f);
                overlay.setRelativeSize(.1f);
                this.overlays.add(overlay);
                this.setName("stun");
                
                break;
            }
                
            case SLOW:
            {
                if(amount > 0)
                {
                    Image slowSpiral = new Image("slow.png");
                    ImageEffect spiralEffect = new ImageEffect(ImageEffect.ImageEffectType.ROTATION, 60, 0, 360);
                    spiralEffect.setRepeating(true);
                    slowSpiral.addImageEffect(spiralEffect);
                    slowSpiral.setAnchor(Anchorable.Anchor.BOTTOMCENTER);
                    Overlay overlay = new Overlay(slowSpiral);
                    overlay.setRelativePosition(.5f, 1.2f);
                    overlay.setRelativeSize(.1f);
                    this.overlays.add(overlay);
                    this.setName("slow");
                }
                break;
            }
            case DAMAGEREDUCTION:
            {
                if(amount > 0 )
                {
                    return;
                }
                
                Image slowSpiral = new Image("vulnerable_curse.png");
                ImageEffect spiralEffect = new ImageEffect(ImageEffect.ImageEffectType.ROTATION, 60, 0, 360);
                spiralEffect.setRepeating(true);
                slowSpiral.addImageEffect(spiralEffect);
                slowSpiral.setAnchor(Anchorable.Anchor.BOTTOMCENTER);
                Overlay overlay = new Overlay(slowSpiral);
                overlay.setRelativePosition(.5f, 1.2f);
                overlay.setRelativeSize(.1f);
                this.overlays.add(overlay);
                this.setName("slow");
                
            }
        }
    }
    
    //=================
    // Effect Methods
    //=================
    
    public boolean canBeApplied()
    {
        return true;
    }
    
    public void setDuration(int i)
    {
        this.duration = i;
    }
    
    public void onApply()
    {
        super.onApply();
        
        switch(stateEffectType)
        { 
            case BASEDAMAGE:
            {
                if(this.inPercent)
                    owningEntity.getCombatData().baseDamage.adjustPercentModifier(amount);
                else
                    owningEntity.getCombatData().baseDamage.adjustModifier(amount);
                
                break;
            }
            case HEALTH:
            {
                if(this.inPercent)
                    owningEntity.getCombatData().maxHealth.adjustPercentModifier(amount);
                else
                    owningEntity.getCombatData().maxHealth.adjustModifier(amount);
                
                break;
            }
            case DAMAGEREDUCTION:
            {     
                if(this.inPercent)
                    owningEntity.getCombatData().damageResistance.adjustPercentModifier(amount);
                else
                    owningEntity.getCombatData().damageResistance.adjustModifier(amount);
                break;               
            }
            case CCREDUCTION:
            {     
                if(this.inPercent)
                    owningEntity.getCombatData().ccResistance.adjustPercentModifier(amount);
                else
                    owningEntity.getCombatData().ccResistance.adjustModifier(amount);
                break;               
            }
            case CRITCHANCE:
            {
                if(this.inPercent)
                    owningEntity.getCombatData().critChance.adjustPercentModifier(amount);
                else
                    owningEntity.getCombatData().critChance.adjustModifier(amount);
                
                break;
            }
            case CRITMODIFIER:
            {
                if(this.inPercent)
                    owningEntity.getCombatData().critModifier.adjustPercentModifier(amount);
                else
                    owningEntity.getCombatData().critModifier.adjustModifier(amount);
                
                break;
            }
           case HEALINGMODIFIER:
            {
                if(this.inPercent)
                    owningEntity.getCombatData().healingModifier.adjustPercentModifier(amount);
                else
                    owningEntity.getCombatData().healingModifier.adjustModifier(amount);
                
                break;
            }
            case COOLDOWNMODIFIER:
            {
                if(this.inPercent)
                    owningEntity.getCombatData().cooldownModifier.adjustPercentModifier(amount);
                else
                    owningEntity.getCombatData().cooldownModifier.adjustModifier(amount);
                
                break;
            }    
            case LIFELEECH:
            {            
                if(this.inPercent)
                    owningEntity.getCombatData().lifeLeech.adjustPercentModifier(amount);
                else
                    owningEntity.getCombatData().lifeLeech.adjustModifier(amount);
                
                break;
                  
            }
            case SLOW:
            {
                if(this.inPercent)
                    owningEntity.getCombatData().xVelocity.adjustPercentModifier(-amount);
                else
                    owningEntity.getCombatData().xVelocity.adjustModifier(-amount);
                
                break;
            }
            case STUN:
            {
                owningEntity.getCombatData().setState(CombatData.CombatState.STUN);
                break;
            }                    
            case IMMOBILIZE:
            {
                owningEntity.getBody().setVelocity(new Vector2f(0,0));
                owningEntity.getBody().setMoveable(false);
                owningEntity.getCombatData().setState(CombatData.CombatState.IMMOBILIZE);
                break;
            }
            case IMMUNE:
            {
                owningEntity.getCombatData().setState(CombatData.CombatState.IMMUNE);
                break;
            }          
        }
    }
            
    public void onRemove()
    {
        super.onRemove();
        
        switch(stateEffectType)
        {            
            case BASEDAMAGE:
            {
                if(this.inPercent)
                    owningEntity.getCombatData().baseDamage.adjustPercentModifier(-amount);
                else
                    owningEntity.getCombatData().baseDamage.adjustModifier(-amount);
                
                break;
            }
            case HEALTH:
            {
                if(this.inPercent)
                    owningEntity.getCombatData().maxHealth.adjustPercentModifier(-amount);
                else
                    owningEntity.getCombatData().maxHealth.adjustModifier(-amount);
                
                break;
            }
            case DAMAGEREDUCTION:
            {     
                if(this.inPercent)
                    owningEntity.getCombatData().damageResistance.adjustPercentModifier(-amount);
                else
                    owningEntity.getCombatData().damageResistance.adjustModifier(-amount);
                break;               
            }
            case CCREDUCTION:
            {     
                if(this.inPercent)
                    owningEntity.getCombatData().ccResistance.adjustPercentModifier(-amount);
                else
                    owningEntity.getCombatData().ccResistance.adjustModifier(-amount);
                break;               
            }
            case CRITCHANCE:
            {
                if(this.inPercent)
                    owningEntity.getCombatData().critChance.adjustPercentModifier(-amount);
                else
                    owningEntity.getCombatData().critChance.adjustModifier(-amount);
                
                break;
            }
            case CRITMODIFIER:
            {
                if(this.inPercent)
                    owningEntity.getCombatData().critModifier.adjustPercentModifier(-amount);
                else
                    owningEntity.getCombatData().critModifier.adjustModifier(-amount);
                
                break;
            }
            case HEALINGMODIFIER:
            {
                if(this.inPercent)
                    owningEntity.getCombatData().healingModifier.adjustPercentModifier(-amount);
                else
                    owningEntity.getCombatData().healingModifier.adjustModifier(-amount);
                
                break;
            }
            case COOLDOWNMODIFIER:
            {
                if(this.inPercent)
                    owningEntity.getCombatData().cooldownModifier.adjustPercentModifier(-amount);
                else
                    owningEntity.getCombatData().cooldownModifier.adjustModifier(-amount);
                
                break;
            }   
            case LIFELEECH:
            {
                if(this.inPercent)
                    owningEntity.getCombatData().lifeLeech.adjustPercentModifier(-amount);
                else
                    owningEntity.getCombatData().lifeLeech.adjustModifier(-amount);
                
                break;
            }
            case SLOW:
            {
                if(this.inPercent)
                    owningEntity.getCombatData().xVelocity.adjustPercentModifier(amount);
                else
                    owningEntity.getCombatData().xVelocity.adjustModifier(amount);
                
                break;
            }
            case STUN:
            {
                owningEntity.getCombatData().removeState(CombatData.CombatState.STUN);
                break;
            }                     
            case IMMOBILIZE:
            {
                owningEntity.getBody().setMoveable(true);
                owningEntity.getCombatData().removeState(CombatData.CombatState.IMMOBILIZE);
                break;
            }
            case IMMUNE:
            {
                owningEntity.getCombatData().removeState(CombatData.CombatState.IMMUNE);
                break;
            }          
        }
    }
    
    public StateEffect copy()
    {
        StateEffect returnCopy = new StateEffect();
        returnCopy.amount = this.amount;
        returnCopy.duration = this.duration;
        returnCopy.stateEffectType = this.stateEffectType;
        returnCopy.inPercent = this.inPercent;
        returnCopy.name = this.name;
        
        //add effects
        for(ImageEffect effect:this.renderEffects)
            returnCopy.addImageEffect(effect.copy());
        
        //add overlays
        for(Overlay overlay: this.overlays)
            returnCopy.addOverlay(overlay.copy());
        
        //addEmitters
        for(AbstractParticleEmitter emitter: this.emitters)
            returnCopy.addEmitter(emitter.copyEmitter());
        
        return returnCopy;
    }
    
    public StateEffectType getStateEffectType()
    {
        return this.stateEffectType;
    }


}
