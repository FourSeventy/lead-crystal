package com.silvergobletgames.leadcrystal.combat;

import com.silvergobletgames.sylver.graphics.Overlay;
import com.silvergobletgames.sylver.graphics.ParticleEmitter;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderData;
import java.util.ArrayList;
import com.silvergobletgames.leadcrystal.combat.StateEffect.StateEffectType;

/**
 *
 * @author mike
 */
public class DotEffect extends CombatEffect
{
    
    private int frequency;
    private Damage damage;
    
        
    //===============
    // Constructors
    //===============
    
    public DotEffect(int duration, int frequency, Damage damage)
    {
        this.duration = duration;
        this.frequency = frequency;
        this.damage = damage;
    }
    
    
    //==================
    // Effect Methods
    //==================
    
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
    
    public void update()
    {
        super.update();
        
        if(this.timeElapsed % frequency == 0)
            this.tick();
    }
    
    public void tick()
    {
        owningEntity.takeDamage(damage);
    }
    
    public DotEffect copy()
    {
        DotEffect returnCopy = new DotEffect(duration,frequency,damage);
        
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
    
    public Damage getDamage()
    {
        return this.damage;
    }
    
    
    //==================
    // Saving Methods
    //==================
    
    public RenderData dumpRenderData() 
    {
         
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
                         
        RenderData renderData = new RenderData();
        
        renderData.data.add(0,"Dot");
        renderData.data.add(1,this.frequency);
        renderData.data.add(2,this.duration);
        renderData.data.add(3,this.damage.dumpRenderData());
        renderData.data.add(4,this.infiniteDuration);
        renderData.data.add(5,this.name);
        
        //dump render effects
        ArrayList<RenderData> renderEffectData = new ArrayList();
        for(ImageEffect effect: this.renderEffects)
            renderEffectData.add(effect.dumpRenderData());
        renderData.data.add(6,renderEffectData);
        
        //dump overlay
        ArrayList<RenderData> overlayData = new ArrayList();
        for(Overlay overlay: this.overlays)
            overlayData.add(overlay.dumpRenderData());
        renderData.data.add(7,overlayData);
        
        //dump particles
        ArrayList<SceneObjectRenderData> particleData = new ArrayList();
        for(ParticleEmitter emitter: this.emitters)
            particleData.add(emitter.dumpRenderData());
        renderData.data.add(8,particleData);
        

        
        return renderData;
     }
     
    public static DotEffect buildFromRenderData(RenderData renderData)
    {
        int duration = (int)renderData.data.get(2);
        int frequency = (int)renderData.data.get(1);
        Damage damage = Damage.buildFromRenderData((RenderData)renderData.data.get(3));
        
        DotEffect effect = new DotEffect(duration, frequency, damage);
        effect.setName((String)renderData.data.get(5));
        
        if((boolean)renderData.data.get(4))
            effect.setInfinite();
        
        //build render effects
        for(RenderData effectData: (ArrayList<RenderData>)renderData.data.get(6))
            effect.addImageEffect(ImageEffect.buildFromRenderData(effectData)); 
        
        //buld overlays
        for(RenderData overlayData: (ArrayList<RenderData>)renderData.data.get(7))
            effect.addOverlay(Overlay.buildFromRenderData(overlayData)); 
        
        //buld particles
        for(SceneObjectRenderData particleData: (ArrayList<SceneObjectRenderData>)renderData.data.get(8))
            effect.addEmitter(ParticleEmitter.buildFromRenderData(particleData)); 
       
       return effect;
    }

}
