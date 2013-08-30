package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.netcode.ClientPacket;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorID;

/**
 *
 * @author Mike
 */
public class BuyArmorPacket extends ClientPacket{
    
    public ArmorID armor;
    
    public BuyArmorPacket()
    {
        
    }
}
