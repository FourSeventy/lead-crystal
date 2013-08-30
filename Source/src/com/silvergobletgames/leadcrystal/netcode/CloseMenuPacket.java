package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.netcode.Packet;
import com.silvergobletgames.leadcrystal.netcode.OpenMenuPacket.MenuID;

/**
 *
 * @author Mike
 */
public class CloseMenuPacket extends Packet{
    
    public MenuID menu;
    
    public CloseMenuPacket()
    {
        
    }
    
}
