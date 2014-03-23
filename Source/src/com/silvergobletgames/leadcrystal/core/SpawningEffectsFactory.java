package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.FlierAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.JumperAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.MoleAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.PlantAnimationPack;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.GreenGooEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.SandSpawnEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.SandSpurtEmitter;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.graphics.AnimationPack;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;


public class SpawningEffectsFactory 
{
        
    
    public static ArrayList<SimpleEntry<SceneObject,SylverVector2f>> getSpawnEffects(AnimationPack pack)
    {
        
        ArrayList<SimpleEntry<SceneObject,SylverVector2f>> effects = new ArrayList();
        
        if(pack instanceof PlantAnimationPack ||  pack instanceof MoleAnimationPack)
        {
            
            effects.add(new SimpleEntry(new SandSpawnEmitter(),new SylverVector2f(0,-.70f)));
        }
        else if(pack instanceof FlierAnimationPack)
        {
            effects.add(new SimpleEntry(new GreenGooEmitter(),new SylverVector2f(0,0f)));
        }
        else if(pack instanceof JumperAnimationPack)
        {
            effects.add(new SimpleEntry(new SandSpawnEmitter(),new SylverVector2f(0,-.70f)));
        }
        return effects;
    }
}
