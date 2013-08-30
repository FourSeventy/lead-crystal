package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.netcode.Packet;
import java.util.ArrayList;


public class ChatPacket extends Packet
{    
    //List of chat message to deploy
    public ArrayList<String> chatMessages; 
}
