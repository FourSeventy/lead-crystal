package com.silvergobletgames.leadcrystal.combat;

import com.silvergobletgames.sylver.graphics.Overlay;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderData;
import java.util.ArrayList;
import com.silvergobletgames.leadcrystal.combat.StateEffect.StateEffectType;
import com.silvergobletgames.sylver.netcode.SceneObjectDeserializer;

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
        Damage filteredDamage = new Damage(damage);
        
        //only apply life leech once every 4 ticks
        if(!(this.timeElapsed % (frequency * 4) == 0))
        {
            filteredDamage.setLifeLeech(0);
        }
        
        owningEntity.takeDamage(filteredDamage);
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
        for(AbstractParticleEmitter emitter: this.emitters)
            returnCopy.addEmitter(emitter.copyEmitter());
        
        return returnCopy;
    }
    
    public Damage getDamage()
    {
        return this.damage;
    }
    
  

}
