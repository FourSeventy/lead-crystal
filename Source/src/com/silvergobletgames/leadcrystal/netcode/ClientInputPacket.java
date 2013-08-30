package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.core.InputHandler;
import com.silvergobletgames.sylver.core.InputSnapshot;
import com.silvergobletgames.sylver.netcode.ClientPacket;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 *
 * @author Justin Capalbo
 */
public class ClientInputPacket extends ClientPacket
{        
    
//    public static enum InputDataEnum
//    {       
//        PRESSED_EVENT,RELEASED_EVENT,MOUSEDOWN,MOUSECLICKED,MOUSEROTATION,MOUSEBUTTONCLICKED,MOUSEOVERMENU;        
//    }        
//    //input variables
//    public ArrayList<InputDataEnum> inputData;
    
    //World Mouse Location
    public short mouseLocationX;
    public short mouseLocationY;
    
    public HashSet<Short> pressedKeys;
    public HashSet<Short> releasedKeys;
    public boolean mouseDown = false; //doesnt get cleared
    public boolean mouseClicked = false;
    public byte mouseWheelRotation = 0;
    public byte mouseButtonClicked = 0;
    //mouse over menu
    public boolean mouseOverMenu = false;
    
    
       
    
    

    
    public ClientInputPacket()
    {
    }
    
    
    public InputSnapshot getInputSnapshot()
    {
       if(releasedKeys == null)
           releasedKeys = new HashSet();
       if(pressedKeys == null)
           pressedKeys = new HashSet();
       
       HashMap<Short,Byte> releasedHashMap = new HashMap<>();
       HashMap<Short,Byte> pressedHashMap = new HashMap();
       
       for(Short s: releasedKeys)
       {
           releasedHashMap.put(s, (byte)0);
       }
       for(Short s: pressedKeys)
       {
           pressedHashMap.put(s, (byte)0);
       }
       
       return new InputSnapshot(new ArrayList(),releasedHashMap,pressedHashMap,mouseDown,mouseClicked,false,mouseWheelRotation,mouseButtonClicked,false,new Point(0,0));
    }
    
    
    public boolean isMouseOverMenu()
    {
        return this.mouseOverMenu;
    }
    
}
