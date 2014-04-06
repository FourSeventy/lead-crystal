package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorStat;
import com.silvergobletgames.sylver.netcode.ClientPacket;

/**
 *
 * @author Mike
 */
public class BuyStatPacket extends ClientPacket{
    
    public ArmorStat.ArmorStatID statId;
    
    public BuyStatPacket()
    {
        
    }
}
