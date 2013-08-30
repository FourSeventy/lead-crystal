package com.silvergobletgames.leadcrystal.combat;

import com.silvergobletgames.sylver.graphics.Overlay;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.netcode.SaveData;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import java.util.ArrayList;

/**
 * Serves as a wrapper for an instance of damage.  Note that a heal IS DAMAGE.
 * It just moves your health bar the other way.  
 * @author Justin Capalbo
 */
public class Damage 
{
 
    //Amount of damage to be dealt
    private Stat amount = new Stat(0);
    //Type of damage
    private DamageType damageType;
    //Entity that is damage dealer
    private CombatEntity source;
    //Any combat effects that get applied when this damage is dealt
    private ArrayList<CombatEffect> combatEffects = new ArrayList<>();
    //Any render efefcts that get applied when this damage is dealt
    private ArrayList<ImageEffect> renderEffects = new ArrayList<>();
    //any overlay effects that should be applied
    private ArrayList<Overlay> overlays = new ArrayList<>();
    //flag marking this damage as a crit
    private boolean crit = false;
    //life leech percent
    private float lifeLeech = 0;
    
    //damage type enum
    public enum DamageType{
        //WARNING- changing these names could break save data
        NODAMAGE,
        PHYSICAL, BURN, FROST, SHOCK, HEAL, //Types for actual damage
        STUN, SLOW, VULNERABILITY; //Types for status effects/ccs
    }
    

    //=================
    // Constructors
    //=================
    
    /**
     * Copy of this damage.
     */
    public Damage(Damage other)
    {
        this.amount = new Stat(other.amount);
        this.damageType = other.damageType;
        this.source = other.source;
        this.crit = other.crit;
        this.lifeLeech = other.lifeLeech;
        
        for(CombatEffect effect:other.combatEffects)       
            this.combatEffects.add(effect.copy());
                
        for(ImageEffect effect: other.renderEffects)    
            this.renderEffects.add(effect.copy());
        
        for(Overlay overlay: other.overlays)
            this.overlays.add(overlay.copy());
        
        
    }   
    
    /**
     * Damage with only a type.
     */
    public Damage(DamageType type, float amount)
    {
        this(type,amount,null);
    }
    
    /**
     * Damage with only a type.
     */
    public Damage(DamageType type, float amount, CombatEntity source)
    {
        this.damageType = type;
        this.amount.setBase(amount);
        this.source = source;
    }

    
    
    //==============
    // Accessors
    //==============
    
    /**
     * Return the amount of damage that should actually be inflicted to the enemy's health.
     * Base damage/heal is returned before any modifiers.  The entity should take care of that.
     * 
     * If the damage is of type Heal, the opposite will be returned.
     * 
     * Crit is applied here as well
     * 
     * @return 
     */
    public float getAmount()
    {
        return amount.getTotalValue();
    }   
    
    public Stat getAmountObject()
    {
        return this.amount;
    }
    
    /**
     * Sets if this damage was a crit or not
     * @param value true for crit
     */
    public void setCrit(boolean value)
    {
        this.crit = value;
    }
    
    /**
     * Tells us if this is a critical hit
     * @return 
     */
    public boolean isCrit()
    {
        return crit;
    }

    /**
     * Returns the source of the damage, used for things like determining if player damage, etc.
     * @return 
     */
    public CombatEntity getSource()
    {
        return this.source;
    }
    
    /**
     * Return the type/element/classification of this damage
     * @return 
     */
    public DamageType getType()
    {
        return damageType;
    }
    
    public void setType(DamageType type)
    {
        this.damageType = type;
    }
    
    /**
     * Applies the combat and render effects of this damage to the entity
     * @param e 
     */
    public ArrayList<CombatEffect> getCombatEffects()
    {
        return this.combatEffects;
    }
    
    public void addCombatEffect(CombatEffect effect)
    {
        this.combatEffects.add(effect);
    }
    
    public ArrayList<ImageEffect> getImageEffects()
    {
        return this.renderEffects;
    }
    
    public void addImageEffect(ImageEffect effect)
    {
        this.renderEffects.add(effect);
    }  
      
    /**
     * Gets overlay images that might be applied on this damage.
     * @return 
     */
    public  ArrayList<Overlay> getOverlays()
    {
        return this.overlays;
    }
    
    public void addOverlay(Overlay image)
    {
        this.overlays.add(image);
    }
    
    public float getLifeLeech()
    {
        return this.lifeLeech;
    }
    
    public void setLifeLeech(float leech)
    {
        this.lifeLeech = leech;
    }
    
    //==================
    // Saving Methods
    //==================
    
    public RenderData dumpRenderData() 
    {
         RenderData renderData = new RenderData();
         
         renderData.data.add(0,this.amount.dumpFullData());
         renderData.data.add(1,this.damageType.name());
         renderData.data.add(2,this.crit);
         
         //combat effects
         ArrayList<RenderData> combatEffectData = new ArrayList();
         for(CombatEffect effect: this.combatEffects)
             combatEffectData.add(effect.dumpRenderData());       
         renderData.data.add(3,combatEffectData);
         
         //Render Effects
         ArrayList<RenderData> renderEffectData = new ArrayList();
         for(ImageEffect effect: this.renderEffects)
             renderEffectData.add(effect.dumpRenderData());
         renderData.data.add(4,renderEffectData);
         
         //Overlays
         ArrayList<RenderData> overlayData = new ArrayList();
         for(Overlay overlay: this.overlays)
             overlayData.add(overlay.dumpRenderData());
         renderData.data.add(5,overlayData);
         
         //life leech
         renderData.data.add(6,this.lifeLeech);
         
         return renderData;
    }
   
    public static Damage buildFromRenderData(RenderData renderData)
    {
        Stat amount = Stat.buildFromFullData((SaveData)renderData.data.get(0));
        DamageType damageType = DamageType.valueOf((String)renderData.data.get(1));
        boolean crit = (boolean)renderData.data.get(2);
        
        Damage damage = new Damage(damageType, 0);
        damage.amount = amount;
        damage.setCrit(crit);
        
        //add combat effects
        for(RenderData data: (ArrayList<RenderData>)renderData.data.get(3))      
            damage.addCombatEffect(CombatEffect.buildFromRenderData(data));
        
        //add render effects
        for(RenderData data: (ArrayList<RenderData>)renderData.data.get(4))      
            damage.addImageEffect(ImageEffect.buildFromRenderData(data));
        
        //add overlays
        for(RenderData data: (ArrayList<RenderData>)renderData.data.get(5))      
            damage.addOverlay(Overlay.buildFromRenderData(data));
        
        //life leech
        damage.setLifeLeech((float)renderData.data.get(6));
        
        return damage;     
    }
    
    
   
    
    
}
