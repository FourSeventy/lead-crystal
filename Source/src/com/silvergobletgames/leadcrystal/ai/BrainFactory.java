package com.silvergobletgames.leadcrystal.ai;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

/**
 * Contains a system for building a Brain object and spitting it back to us, much in the same way
 * that we use AnimationSpriteFactory to get an appropriate list of textures.  
 */
public class BrainFactory 
{

    public static enum BrainID
    { 
        //WARNING - Changing the names of this enum will break save data
        None,Fighter,NPC,FlyingFighter,Jumper,DesertBoss,Healer,Crate,FinalBoss1
    }

    private static BrainFactory instance = new BrainFactory();
    
    
    //================
    // Constructors
    //================
    
    private BrainFactory()
    {
   
    }
    
    public static BrainFactory getInstance()
    {
        return instance;
    }
    
    
    /**
     * Given the correct ID, will get the appropriate brain from the map.  If the brain does not
     * exist in the map, a blank brain will be given.
     */
    public Brain getBrain(BrainID id) 
    {
        switch(id)
        {
            case Fighter: return new BrainFighter();
            case NPC: return new BrainNpc();
            case FlyingFighter: return new BrainFlyingFighter();
            case Jumper: return new BrainJumper();
            case DesertBoss: return new BrainDesertBoss();
            case Healer: return new BrainHealer();
            case Crate: return new BrainCrate();
            case FinalBoss1: return new BrainFinalBoss1();
            default: return new Brain();
        }    
    }


    /**
     * Returns a sorted Set of the different types of Brains that is used for building
     * NPEs in the map editor.
     */
    public  Set<String> getAvailableBrainTypes() 
    {
        HashSet<String> set = new HashSet();
        
        for(BrainID type :BrainID.values())
        {
            set.add(type.name());
        }
        
        return set;
    }
}