
package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;
import com.silvergobletgames.leadcrystal.entities.MobSpawner;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.sylver.core.SceneObject.SceneObjectClassMask;
import com.silvergobletgames.leadcrystal.items.Potion;
import com.silvergobletgames.leadcrystal.items.Currency;


public enum ExtendedSceneObjectClasses implements SceneObjectClassMask {
    
      ENTITY(Entity.class),PLAYERENTITY(PlayerEntity.class),
        WORLDOBJECTENTITY(WorldObjectEntity.class),CONSUMABLE(Potion.class),NONPLAYERENTITY(NonPlayerEntity.class),
        MOBSPAWNER(MobSpawner.class), CURRENCY(Currency.class);
        
        public Class representativeClass;
        
        ExtendedSceneObjectClasses(Class c)
        {
            representativeClass = c;
        }

    
        public Class getRepresentativeClass()
        {
            return representativeClass;
        }
}
