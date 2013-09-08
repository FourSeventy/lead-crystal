package com.silvergobletgames.leadcrystal.combat;

import com.silvergobletgames.sylver.core.Effect;
import com.silvergobletgames.sylver.graphics.Overlay;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.netcode.RenderData;
import java.util.ArrayList;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;


/**
 *
 * @author Justin Capalbo
 */
public abstract class CombatEffect extends Effect
{
    //The entity affected by the effect 
    public CombatEntity owningEntity;
    //infinite duration flag
    protected boolean infiniteDuration = false;
    
    //render effects and overlays
    protected ArrayList<ImageEffect> renderEffects = new ArrayList();
    protected ArrayList<Overlay> overlays = new ArrayList();
    protected ArrayList<AbstractParticleEmitter> emitters = new ArrayList();
    
    //====================
    // Class Methods
    //====================
    
    public abstract boolean canBeApplied();
    
    public void onApply()
    {
        //apply render effects
        for(ImageEffect effect: this.renderEffects)
            owningEntity.getImage().addImageEffect(effect);
        
        //apply overlays
        for(Overlay image: this.overlays)
            owningEntity.getImage().addOverlay(image);
        
        //apply emitters
        for(AbstractParticleEmitter emitter: this.emitters)
            owningEntity.addEmitter(emitter);
    }
            
    public void onRemove()
    {
        for(ImageEffect effect: this.renderEffects)
            owningEntity.getImage().removeImageEffect(effect);
        
        for(Overlay image: this.overlays)
            owningEntity.getImage().removeOverlay(image);
        
        for(AbstractParticleEmitter emitter: this.emitters)
            emitter.stopEmittingThenRemove();
    }
           
    public void update()
    {      
        //increment time elapsed
        timeElapsed++;
        
        if(timeElapsed > duration && !infiniteDuration)
           owningEntity.getCombatData().removeCombatEffect(this);      
    }
    
    public abstract CombatEffect copy();
 
    
    //================
    // Accessors
    //=================
    
    public void setInfinite()
    {
        this.infiniteDuration = true;
    }
    
    public void addImageEffect(ImageEffect effect)
    {
        this.renderEffects.add(effect);
    }
    
    public void addOverlay(Overlay overlay)
    {
        this.overlays.add(overlay);
    }
    
    public void addEmitter(AbstractParticleEmitter emitter)
    {
        this.emitters.add(emitter);
    }
    
    public void clearEffects()
    {
        this.renderEffects.clear();
        this.overlays.clear();
        this.emitters.clear();
    }
    
    
    //================
    // Saving
    //================
    
    public abstract RenderData dumpRenderData();
    
    public static CombatEffect buildFromRenderData(RenderData data)
    {
       switch((String)data.data.get(0)) 
       {
           case "Dot": return DotEffect.buildFromRenderData(data); 
           case "Proc": return ProcEffect.buildFromRenderData(data);
           case "State": return StateEffect.buildFromRenderData(data);
           default: return null;
       }
    }
}
