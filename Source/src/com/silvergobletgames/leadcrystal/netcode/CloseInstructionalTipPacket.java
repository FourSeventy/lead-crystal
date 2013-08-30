package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.leadcrystal.netcode.OpenInstructionalTipPacket.InstructionalTip;
import com.silvergobletgames.sylver.netcode.Packet;


/**
 *
 * @author Mike
 */
public class CloseInstructionalTipPacket extends Packet{
    
    public InstructionalTip tipID;
    
    public CloseInstructionalTipPacket()
    {
        
    }
    
}
