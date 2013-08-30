package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.sylver.netcode.Packet;


/**
 *
 * @author mike
 */
public class HoverEntityPacket extends Packet
{
    public String hoveredID;
    public boolean inRange;
    public boolean currentlyHovering;
    
    
    public HoverEntityPacket()
    {
        
    }
    


}
