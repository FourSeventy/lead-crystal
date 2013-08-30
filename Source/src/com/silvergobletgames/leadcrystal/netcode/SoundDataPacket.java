package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.netcode.Packet;
import java.util.ArrayList;

/**
 *
 * @author Justin Capalbo
 */
public class SoundDataPacket extends Packet
{
    
    public ArrayList<Sound> soundData = new ArrayList();
    
   
    public SoundDataPacket()
    {
    }   
    
    public void add(Sound s){
        soundData.add(s);
    }
    
    public boolean isEmpty(){
        return soundData.isEmpty();
    }
}
