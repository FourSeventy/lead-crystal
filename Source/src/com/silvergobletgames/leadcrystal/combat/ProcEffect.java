package com.silvergobletgames.leadcrystal.combat;

import com.silvergobletgames.sylver.graphics.Overlay;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import com.silvergobletgames.sylver.graphics.ImageEffect;

/**
 *
 * @author mike
 */
public class ProcEffect extends CombatEffect
{

    private ProcType procType;
    private ProcDestination procDestination;
    private CombatEffect proccedEffect;
    private float chance;
    
    
    public static enum ProcType{
        //WARNING - changing these names will effect saved data
        ONSKILL,ONSKILLCRIT,TAKEDAMAGE,TAKECRIT;
    }
    
    public static enum ProcDestination{
        SELF, DAMAGE
    }
    
    
    //=============
    // Constructors
    //=============
    
    public ProcEffect(ProcType type, int duration, CombatEffect procResult,float chance)
    {
        this.procType = type;
        this.duration = duration;
        this.proccedEffect = procResult;
        this.chance = chance;
    }
    
    //=================
    // Effect Methods
    //=================
    
    public boolean canBeApplied()
    {
        return true;
    }
    
    public void onApply()
    {
        super.onApply();
    }
            
    public void onRemove()
    {
        super.onRemove();
    }
    
    public boolean rollProc()
    {
        double roll = Math.random();
        
        return  roll<=chance;
    }
    
    public ProcEffect copy()
    {
        ProcEffect returnCopy = new ProcEffect(procType,duration,proccedEffect,chance);
        
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
    
    
    
    //==============
    // Accessors
    //==============
    public CombatEffect getProccedEffect()
    {
        return this.proccedEffect;
    }
    
    public ProcType getProcType()
    {
        return this.procType;
    }
    
    public float getChance()
    {
        return this.chance;
    }

}
