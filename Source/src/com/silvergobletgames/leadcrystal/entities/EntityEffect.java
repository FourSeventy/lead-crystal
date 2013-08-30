package com.silvergobletgames.leadcrystal.entities;

import com.silvergobletgames.sylver.core.Effect;
import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.util.LinearInterpolator;
import java.util.HashMap;
import net.phys2d.math.Vector2f;


public class EntityEffect extends Effect
{

    //the entity this effect is on
    private Entity owningEntity;
    //the type of render effect that this is
    protected EntityEffectType entityEffectType;
    //start value
    private Object start;
    //end value
    private Object end;
    //should this renderEffect repeat
    protected boolean repeat;
    //interpolators
    protected HashMap<String,LinearInterpolator> interpolators = new HashMap<>(); 
    
    //render effect type enum
    public static enum EntityEffectType{
        MASS(Float.class),DURATION(Float.class),ROTATION(Float.class),XTRANSLATE(Float.class),YTRANSLATE(Float.class),VFORCE(Float.class),HFORCE(Float.class);
        
        public Class type;
        
        EntityEffectType(Class clas)
        {
            this.type = clas;
        }
        
    }
    
    //===============
    // Constructor
    //===============
    
    public EntityEffect(EntityEffectType type,int duration, Object start, Object end)
    {
        this.entityEffectType = type;
        this.duration = duration;
        this.start = start;
        this.end = end;
        
        //set up interpolator
         LinearInterpolator interpolator = new LinearInterpolator(((Number)start).floatValue(), ((Number)end).floatValue(), 1, duration);
         interpolators.put("interpolator", interpolator);
    }
    
    
    //================
    // Effect Methods
    //================
    
    public void onApply()
    {

    }


    public void onRemove()
    {
        
        if(entityEffectType == EntityEffectType.DURATION)
        {
            //if the owning entity has an owning scene remove it
            if(this.owningEntity.getOwningScene() != null)   
            {
                this.owningEntity.getOwningScene().remove(this.owningEntity);
            }
            
        }

    }

  
    public void update()
    {
        
        //increment time elapsed
        timeElapsed++;
        
        //if our duration is up, remove the effect
        if (timeElapsed > duration && !repeat) 
        {
            if (owningEntity != null)            
                owningEntity.removeEntityEffect(this);  
            
            return;
        }
        else if(timeElapsed > duration && repeat)
        {
            timeElapsed = 1;
        }
        
        //adjust the correct amount of the images value
        this.adjustEntity();

    }

   
    public boolean canBeApplied()
    {
        return true;
    }

    
    public EntityEffect copy()
    {
       return new EntityEffect(this.entityEffectType, this.duration, this.start, this.end);
    }
    
    private void adjustEntity()
    {
        switch(this.entityEffectType)
        {
            case MASS:
            {
                break;
            }
            case DURATION:
            {
                break;
            }
            case ROTATION:
            {
                float adjustmentValue = (float)interpolators.get("interpolator").interp(timeElapsed);
                this.owningEntity.getBody().setRotation(adjustmentValue * (float)(Math.PI/180)); 
                break;
            }
            case XTRANSLATE:
            {
                float adjustmentValue = (float)interpolators.get("interpolator").interp(timeElapsed);
                this.owningEntity.setPosition(adjustmentValue,this.owningEntity.getPosition().y);

                break;
            }
            case YTRANSLATE:
            {
                float adjustmentValue = (float)interpolators.get("interpolator").interp(timeElapsed);
                this.owningEntity.setPosition(this.owningEntity.getPosition().x, adjustmentValue);
                break;
            }             
        }
        
    }
    
    public void setRepeating(boolean value)
    {
        this.repeat = value;
    }
    
    
    
    //=============
    // Accessors
    //=============

    public void setOwningEntity(Entity entity)
    {
        this.owningEntity = entity;
    }
    
    
    //=================
    // Reder Data
    //=================
    
    public RenderData dumpRenderData() 
    {
         
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
        
        RenderData renderData = new RenderData();
        
        renderData.data.add(0,this.entityEffectType);
        renderData.data.add(1,this.duration);
        renderData.data.add(2,this.start);
        renderData.data.add(3,this.end);
        renderData.data.add(4,this.repeat);
        
        return renderData;
    }
    
    public static EntityEffect buildFromRenderData(RenderData renderData)
    {
        EntityEffect effect = new EntityEffect((EntityEffect.EntityEffectType)renderData.data.get(0),(int)renderData.data.get(1),renderData.data.get(2),renderData.data.get(3)); 
        effect.repeat =((boolean)renderData.data.get(4));
        return effect;
    }
}
