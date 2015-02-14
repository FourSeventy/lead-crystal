package com.silvergobletgames.leadcrystal.items;

import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.netcode.SceneObjectSaveData;
import com.silvergobletgames.leadcrystal.entities.ItemEntity;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectClasses;

/**
 *
 * @author mike
 */
public class Potion extends ItemEntity
{    
    
    //================
    // Constructor
    //================
    
    public Potion()
    {
        super(new Image("healthPot3.png"));
        
        this.image.setScale(1.5f);
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
        
        SceneObjectSaveData saveData = new SceneObjectSaveData(ExtendedSceneObjectClasses.CONSUMABLE,this.getID());     
        
        return saveData;
    }
    
    public static Potion buildFromFullData(SceneObjectSaveData data) 
    {
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
        
        Potion consumable = new Potion();
        
        return consumable;
    }

}
