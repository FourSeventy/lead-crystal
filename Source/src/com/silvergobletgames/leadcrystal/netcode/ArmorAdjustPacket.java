
package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.netcode.ClientPacket;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorID;

/**
 *
 * @author Mike
 */
public class ArmorAdjustPacket extends ClientPacket {
    
    public ArmorID armorToEquip;
    
    public ArmorAdjustPacket()
    {
        super();
    }
}
