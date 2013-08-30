package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.netcode.ClientPacket;
import java.util.ArrayList;
import java.util.UUID;

public class JoinRequest extends ClientPacket
{
    
    //save game data
    public ArrayList rawSaveData;
    
    public JoinRequest()
    {
        
    }
    
    
}
