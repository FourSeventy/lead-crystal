package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.netcode.Packet;
import com.silvergobletgames.sylver.netcode.RenderData;
import java.io.IOException;
import java.net.URL;


public class JoinResponse extends Packet
{
    public boolean okToJoin;
    public RenderData hostProgressionManager;
    public ReasonCode reasonCode;    
    
    public static enum ReasonCode
    {
        ServerFull, ServerPrivate, IncorrectPassword, Connection, NoResponse;
    }
    
   
 
    public JoinResponse()
    {
    }
    
    
}
