package com.silvergobletgames.leadcrystal.combat;

import com.silvergobletgames.sylver.netcode.SaveData;



public class Stat
{
    private float base;
    private float absoluteModifier;
    private float percentModifier;
    
    
    //===============
    // Constructor
    //===============
    
    public Stat()
    {
        
    }
    public Stat( float base)
    {        
        this.base = base;  
        
        this.absoluteModifier = 0;
        this.percentModifier = 1;
    }
    
    public Stat(Stat other)
    {
        this.base = other.base;
        this.absoluteModifier = other.absoluteModifier;
        this.percentModifier = other.percentModifier;
    }
          
    
    //============
    // Accessors
    //============
    
    public void setBase(float base)
    {
        this.base = base;
    }
    
    public float getBase()
    {
        return this.base;
    }
    
    public void adjustBase(float adjustment)
    {
        this.base += adjustment;
    }
    
    
    public void setAbsoluteModifier(float value)
    {
        this.absoluteModifier = value;
    }
    
    public float getAbsoluteModifier()
    {
        return this.absoluteModifier;
    }
    
    public void adjustModifier(float value)
    {
        this.absoluteModifier += value;
    }
    
    
    public void setPercentModifier(float value)
    {
        this.percentModifier = value;
    }
    
    public float getPercentModifier()
    {
        return this.percentModifier;
    }
    
    public void adjustPercentModifier(float value)
    {
        this.percentModifier += value;
    }
    
    public float getTotalValue()
    {
        return ((base + absoluteModifier) * percentModifier);
    }
    
    public int getTotalValueInt()
    {
        return (int)((base + absoluteModifier) * percentModifier);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + Float.floatToIntBits(this.base);
        hash = 97 * hash + Float.floatToIntBits(this.absoluteModifier);
        hash = 97 * hash + Float.floatToIntBits(this.percentModifier);
        return hash;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if(!(other instanceof Stat))
            return false;
        
        if(this.base == ((Stat)other).base &&
           this.absoluteModifier == ((Stat)other).absoluteModifier &&
           this.percentModifier == ((Stat)other).percentModifier
          )
            return true;
        else
            return false;
    }
    
    
    //================
    // Save Methods
    //================
    
    public SaveData dumpFullData() 
    {
         
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
         
        SaveData saveData = new SaveData();
        
        saveData.dataMap.put("base",base);
        saveData.dataMap.put("absoluteModifier",absoluteModifier);
        saveData.dataMap.put("percentModifier",percentModifier);
        
        return saveData;
     }
    
    public static Stat buildFromFullData(SaveData saved) 
    {
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
        
        if(saved == null)
            return new Stat(0);
        
        Stat newStat = new Stat((float)saved.dataMap.get("base"));
        newStat.absoluteModifier = (float)saved.dataMap.get("absoluteModifier");
        newStat.percentModifier = (float)saved.dataMap.get("percentModifier");
        
        return newStat;
     }
    
}
