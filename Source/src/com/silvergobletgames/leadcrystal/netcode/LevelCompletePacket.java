package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.netcode.Packet;

/**
 *
 * @author Mike
 */
public class LevelCompletePacket extends Packet{
    
    public byte levelNumber;
    public boolean mainObjective;
    public boolean sideObjective;
    
    public LevelCompletePacket()
    {
        super();
    }
    
}
