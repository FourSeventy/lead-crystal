package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorStat.ArmorStatID;
import com.silvergobletgames.sylver.netcode.Packet;


/**
 *
 * @author mike
 */
public class SideObjectiveCompletePacket extends Packet
{
    public short currencyReward = 0;
    public ArmorStatID modifierID;
    
    
    public SideObjectiveCompletePacket()
    {
        
    }
    


}
