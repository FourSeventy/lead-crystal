package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.netcode.Packet;


public class ChangeLevelPacket extends Packet 
{
     //the level to load
    public String gameLevelName;
    
    //the differences that need to be appplied
    public RenderDataPacket levelDifferences;
    
       
    public ChangeLevelPacket()
    {
    }
    

}
