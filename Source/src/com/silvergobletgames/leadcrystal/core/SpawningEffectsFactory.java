package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.PlantAnimationPack;
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
        
        if(pack instanceof PlantAnimationPack)
        {
            
            effects.add(new SimpleEntry(new SandSpawnEmitter(),new SylverVector2f(0,-.70f)));
        }
        
        return effects;
    }
}
