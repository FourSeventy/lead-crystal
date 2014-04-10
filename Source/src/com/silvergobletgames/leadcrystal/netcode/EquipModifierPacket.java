package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorModifier.ArmorModifierID;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorStat;
import com.silvergobletgames.sylver.netcode.ClientPacket;

/**
 *
 * @author Mike
 */
public class EquipModifierPacket extends ClientPacket{
    
    public ArmorModifierID modifierID;
    
    public EquipModifierPacket()
    {
        
    }
}
