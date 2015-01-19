
package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Cursor;
import com.silvergobletgames.sylver.graphics.Image;

public class CursorFactory 
{

     //cursor variables for changing the cursor
    public enum CursorType{
        RETICULE, HAND, NONE, WRENCH,ACTIVEHAND,UNACTIVEHAND;
    }
    
    private static CursorFactory instance;
    
    //=================
    // Constructor
    //=================
    
    private CursorFactory()
    {
        
    }
    
    public static CursorFactory getInstance()
    {
        if( instance == null)
            instance = new CursorFactory();
        
        return instance;
    }
    
    
    //================
    // Class Methods
    //================
    
    /**
     * Returns a pre built cursor
     * @param type
     * @return 
     */
    public Cursor getCursor(CursorType type)
    {
        Cursor cursor;
        switch(type)
        {
            case RETICULE: cursor = new Cursor(new Image("reticle06.png"){{ setDimensions(39,39); setColor(new Color(1f,1f,1f));}}, Anchorable.Anchor.CENTER);  break;
            case WRENCH:cursor = new Cursor(new Image("wrench.png"){{ setDimensions(24,24);}}, Anchorable.Anchor.TOPLEFT);  break;
            case ACTIVEHAND: cursor = new Cursor(new Image("activeHand.png"){{ setDimensions(24,24);}}, Anchorable.Anchor.CENTER); break;
            case UNACTIVEHAND: cursor = new Cursor(new Image("unActiveHand.png"){{ setDimensions(24,24);}}, Anchorable.Anchor.CENTER);  break;
            case HAND: cursor = new Cursor(new Image("mouse_hand.png"){{ setDimensions(24,24);}}, Anchorable.Anchor.TOPLEFT); break;
            default: cursor = null;
        }
        
        return cursor;
    }
}
