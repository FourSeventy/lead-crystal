package com.silvergobletgames.leadcrystal.items;

import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderDataChanges;
import com.silvergobletgames.sylver.netcode.SceneObjectSaveData;
import java.util.ArrayList;
import java.util.Arrays;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.ItemEntity;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectClasses;
import com.silvergobletgames.leadcrystal.entities.EntityTooltip;

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
        
        this.image.setScale(2);
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
