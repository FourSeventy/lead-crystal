package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;

/**
 * Public "Struct" that represents a telegram that can be transmitted between 
 * non-player entities and is designed to be received by the AI State.
 * @author Justin Capalbo
 */
public class AIMessage {
    
    public NonPlayerEntity sender;
    public NonPlayerEntity receiver;
    public int messageID;

    
    //Final constants for messages   
    public final static int MSG_ENEMY_FOUND = 0;
    public final static int MSG_DYING_PLEASE_FIGHT = 1;
    public final static int MSG_NEED_HEAL = 2;
    public final static int MSG_ALL_CLEAR = 3;
    public final static int MSG_IVE_DIED = 4;
   

    public AIMessage(NonPlayerEntity snd, NonPlayerEntity rec, int msgID)
    {
        this.sender = snd;
        this.receiver = rec;
        this.messageID = msgID;

    }
        
    public String toString(){
        return "Message from " + sender.getID() + " to " + receiver.getID() + " of type " + messageID;
    }
}
