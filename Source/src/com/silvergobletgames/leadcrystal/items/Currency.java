package com.silvergobletgames.leadcrystal.items;

import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.netcode.SceneObjectSaveData;
import com.silvergobletgames.leadcrystal.entities.ItemEntity;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectClasses;
import net.phys2d.raw.shapes.Box;

/**
 *
 * @author Mike
 */
public final class Currency extends ItemEntity
{
    //amount
    private int amount;
    
    //================
    // Constructor
    //================
    
    protected Currency(int amount)
    {
        super(new Image("goldCoinSmall.png"));
       
        
        //set amount
        this.amount = amount;
        
        //adjust image
        this.image.setDimensions(32, 32);
        //adjust body
        this.body.set(new Box(27,27),3f); 
        this.body.setRestitution(.5f);
        
    }
    
    //================
    // Class Methods
    //================
    
    public int getAmount()
    {
        return amount;
    }
    
    public void setAmount(int amount)
    {
        this.amount = amount;
    }
       
    
    //==================
    //Save Data Methods
    //==================
    
    public SceneObjectSaveData dumpFullData() 
    {
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
        
        SceneObjectSaveData saveData = new SceneObjectSaveData(ExtendedSceneObjectClasses.CURRENCY,this.getID());
        
        saveData.dataMap.put("amount",amount);
        
        return saveData;
    }
    
    public static Currency buildFromFullData(SceneObjectSaveData data) 
    {
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
        
        Currency curency = new Currency((int)data.dataMap.get("amount"));
        
        return curency;
    }
    
}
