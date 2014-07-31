package com.silvergobletgames.leadcrystal.menus;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map.Entry;

/**
 *
 * @author Mike
 */
public class MessageManager 
{
    
    private LinkedList<SimpleEntry<MessageType,ArrayList<String>>> messageQueue = new LinkedList<>();
    private Hud hudReference;
    private long messageTimer = 0;
    
    public static enum MessageType{
        OBJECTIVE_STATUS,OBJECTIVE_COMPLETE,MODIFIER_UNLOCKED;
    }
    
    //================
    // Constructor
    //================
    public MessageManager(Hud hud)
    {
        this.hudReference = hud;
    }
    
    
    //=============
    // Methods
    //=============
    
    public void queueMessage(MessageType type, String... message)
    {
        //convert incoming array of messages to array list
        ArrayList<String> messages = new ArrayList<>(Arrays.asList(message));
        
        //queue up the message
        SimpleEntry<MessageType,ArrayList<String>> messageEntry = new SimpleEntry<>(type,messages);
        messageQueue.add(messageEntry);
    }
    
    
    public void update()
    {
        //update timer
        this.messageTimer++;
        
        //every 7 seconds put next message into scene
        if(this.messageTimer % (60 *7) == 0) 
        {
            
        }
    }
    
    private void putMessageIntoScene()
    {
        
    }
    
    
}
