
package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.netcode.Packet;


public class OpenDialoguePacket extends Packet
{
        
    //The speaker, if any
    public String speaker;
    
    //The text
    public String text; 
    
    
    public OpenDialoguePacket()
    {
        
    }
    

    
}
