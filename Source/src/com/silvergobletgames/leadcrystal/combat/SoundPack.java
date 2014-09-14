package com.silvergobletgames.leadcrystal.combat;

import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.util.SylverRandom;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

public class SoundPack
{ 
    //map of sounds
    private HashMap<SoundPackType, ArrayList<String>> soundMap;
    
    //sound packs id
    private SoundPackID soundPackID;
    
    
    public static enum SoundPackID
    { 
        //WARNING - Changing the names of this enum will break save data
        None,Human,Bug,Mammal,Crate
    }
    
    public static enum SoundPackType 
    {
        IDLE, DEATH, AGGRO, DAMAGED, GREETING, FAREWELL;
    }

    //==============
    // Constructor
    //==============
    
    /**
     * Creates a new sound pack consisting of the given sound map, and id.
     */
    public SoundPack(HashMap<SoundPackType, ArrayList<String>> soundMap, SoundPackID id) 
    {
        this.soundMap = soundMap;
        this.soundPackID = id;
    }
    
    
    //================
    // Class Methods
    //================


    /**
     * Returns the sound data for a random sound that corresponds to the sound type. If there was a problem
     * it returns null
     */
    public Sound playRandom(SoundPackType type, float x, float y)
    {
        //Try to get a list
        ArrayList<String> list = soundMap.get(type);
        if (list != null) 
        {
            //Only if we have a sound for this type
            if (list.size() > 0) {
                String ref = "";
                if (list.size() == 1) {
                    //First sound
                    ref = list.get(0);
                } else {
                    //Random sound
                    Random r = SylverRandom.random;
                    int index = r.nextInt(list.size());
                    ref = list.get(index);
                }

                Sound sound = Sound.locationSound(ref, x, y, false);
                return sound;
            }
        }
        
        return null;
    }
    
    public SoundPackID getID()
    {
        return this.soundPackID;
    }
    
    
}