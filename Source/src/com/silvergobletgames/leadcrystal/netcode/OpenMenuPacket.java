package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.netcode.Packet;


/**
 *
 * @author Mike
 */
public class OpenMenuPacket extends Packet{
    
    public MenuID menu;
    
    public static enum MenuID{
        POTION,ARMOR,INVENTORY,SKILL,MAP
    }
    
    public OpenMenuPacket()
    {
        
    }
    
}
