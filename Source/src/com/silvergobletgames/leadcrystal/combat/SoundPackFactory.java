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
        HashMap<SoundPackType,ArrayList<String>> soundMap = new HashMap<>();
        ArrayList<String> soundList = new ArrayList<>();
        soundList.add("buffered/painMale02.ogg");
        soundList.add("buffered/painMale01.ogg");
        soundMap.put(SoundPackType.DEATH, soundList);
        SoundPack pack = new SoundPack(soundMap,SoundPackID.Human);
        packMap.put(SoundPackID.Human,pack );
        
        //build none pack
        soundMap = new HashMap<>();
        pack = new SoundPack(soundMap,SoundPackID.None);
        packMap.put(SoundPackID.None,pack);
        
        //build mechanical pack
        soundMap = new HashMap<>();
        soundList = new ArrayList();
        soundList.add("buffered/tankDeath01.ogg");
        soundList.add("buffered/tankDeath02.ogg");
        soundMap.put(SoundPackType.DEATH, soundList);
        pack = new SoundPack(soundMap,SoundPackID.Mechanical);
        packMap.put(SoundPackID.Mechanical,pack);
        
        //build mammal pack
        soundMap = new HashMap<>();
        soundList = new ArrayList();
        soundList.add("buffered/gore1.ogg");
        soundMap.put(SoundPackType.DEATH, soundList);
        pack = new SoundPack(soundMap,SoundPackID.Mammal);
        packMap.put(SoundPackID.Mammal,pack);
        
        //big mammal pack
        soundMap = new HashMap<>();
        soundList = new ArrayList();
        soundList.add("buffered/muckerDeath1.ogg");
        soundList.add("buffered/muckerDeath2.ogg");
        soundMap.put(SoundPackType.DEATH, soundList);
        pack = new SoundPack(soundMap,SoundPackID.BigMammal);
        packMap.put(SoundPackID.BigMammal,pack);
        
        //build crate pack
        soundMap = new HashMap<>();
        soundList = new ArrayList();
        soundList.add("buffered/crateDeath.ogg");
        soundMap.put(SoundPackType.DEATH, soundList);
        pack = new SoundPack(soundMap,SoundPackID.Crate);
        packMap.put(SoundPackID.Crate,pack);
        
        //build bug pack
        soundMap = new HashMap<>();
        soundList = new ArrayList();
        soundList.add("buffered/bugDeath1.ogg");
        soundList.add("buffered/bugDeath2.ogg");
        soundMap.put(SoundPackType.DEATH, soundList);
        pack = new SoundPack(soundMap,SoundPackID.Bug);
        packMap.put(SoundPackID.Bug,pack);
        
        //build small bug pack
        soundMap = new HashMap<>();
        soundList = new ArrayList();
        soundList.add("buffered/bugDeath3.ogg");
        soundMap.put(SoundPackType.DEATH, soundList);
        pack = new SoundPack(soundMap,SoundPackID.SmallBug);
        packMap.put(SoundPackID.SmallBug,pack);
        
        
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
