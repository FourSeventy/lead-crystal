package com.silvergobletgames.leadcrystal.combat;

import com.silvergobletgames.sylver.graphics.Overlay;
import com.silvergobletgames.sylver.graphics.ParticleEmitter;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderData;
import java.util.ArrayList;

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
        for(ParticleEmitter emitter: this.emitters)
            returnCopy.addEmitter(emitter.copy());
        
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
    
    //===============
    // Saving
    //===============
    public RenderData dumpRenderData()
    {
        
        RenderData renderData = new RenderData();
        
        renderData.data.add(0,"Proc");
        renderData.data.add(1,procType.name());
        renderData.data.add(2,proccedEffect.dumpRenderData());
        renderData.data.add(3,chance);
        renderData.data.add(4,duration);
        renderData.data.add(5,name);
        renderData.data.add(6,this.infiniteDuration);
        
        //dump render effects
        ArrayList<RenderData> renderEffectData = new ArrayList();
        for(ImageEffect effect: this.renderEffects)
            renderEffectData.add(effect.dumpRenderData());
        renderData.data.add(7,renderEffectData);
        
        //dump overlay
        ArrayList<RenderData> overlayData = new ArrayList();
        for(Overlay overlay: this.overlays)
            overlayData.add(overlay.dumpRenderData());
        renderData.data.add(8,overlayData);
        
        //dump particles
        ArrayList<SceneObjectRenderData> particleData = new ArrayList();
        for(ParticleEmitter emitter: this.emitters)
            particleData.add(emitter.dumpRenderData());
        renderData.data.add(9,particleData);
        
        return renderData;
        
    }
    
    public static ProcEffect buildFromRenderData(RenderData renderData)
    {
        ProcType procType = ProcType.valueOf((String)renderData.data.get(1));
        CombatEffect procResult = CombatEffect.buildFromRenderData((RenderData)renderData.data.get(2));
        float chance = (float)renderData.data.get(3);
        int duration = (int)renderData.data.get(4);
        
        ProcEffect effect = new ProcEffect(procType,duration,procResult,chance);
        effect.setName((String)renderData.data.get(5));
        
        if((boolean)renderData.data.get(6))
            effect.setInfinite();
        
        //build render effects
        for(RenderData effectData: (ArrayList<RenderData>)renderData.data.get(7))
            effect.addImageEffect(ImageEffect.buildFromRenderData(effectData)); 
        
        //buld overlays
        for(RenderData overlayData: (ArrayList<RenderData>)renderData.data.get(8))
            effect.addOverlay(Overlay.buildFromRenderData(overlayData)); 
        
        //buld particles
        for(SceneObjectRenderData particleData: (ArrayList<SceneObjectRenderData>)renderData.data.get(9))
            effect.addEmitter(ParticleEmitter.buildFromRenderData(particleData)); 
        
        return effect;     
    }
}
