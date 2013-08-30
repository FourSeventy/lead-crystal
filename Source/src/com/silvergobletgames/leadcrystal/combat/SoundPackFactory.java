package com.silvergobletgames.leadcrystal.combat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import com.silvergobletgames.leadcrystal.combat.SoundPack.SoundPackID;
import com.silvergobletgames.leadcrystal.combat.SoundPack.SoundPackType;

/**
 *
 * @author mike
 */
public class SoundPackFactory 
{
    //map of sound packs
    private HashMap<SoundPackID,SoundPack> packMap = new HashMap();
    
    //private instance
    private static SoundPackFactory instance;
    
    //==============
    // Constructors
    //==============
    
    private SoundPackFactory()
    {
        //build Human pack and add it to the map
        HashMap<SoundPackType,ArrayList<String>> soundMap = new HashMap();
        ArrayList<String> soundList = new ArrayList();
        soundList.add("buffered/bodyFall.ogg");
        soundList.add("buffered/bodyFall.ogg");
        soundMap.put(SoundPackType.DEATH, soundList);
        SoundPack pack = new SoundPack(soundMap,SoundPackID.Human);
        packMap.put(SoundPackID.Human,pack );
        
        //build none pack
        soundMap = new HashMap();
        pack = new SoundPack(soundMap,SoundPackID.None);
        packMap.put(SoundPackID.None,pack);
        
    }
    
    public static SoundPackFactory getInstance()
    {
        if(instance == null)
            instance = new SoundPackFactory();
        
        return instance;
    }
    
    
    //================
    // Class Methods
    //================
    
    public SoundPack getSoundPack(SoundPackID packID)
    {
        return packMap.get(packID);
    }
    
     public  Set<String> getAvailableSoundPacks() 
    {
        HashSet<String> set = new HashSet();
        
        for(SoundPackID type :SoundPackID.values())
        {
            set.add(type.name());
        }
        
        return set;
    }

}
