
package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Cursor;
import com.silvergobletgames.sylver.graphics.Image;

public class CursorFactory 
{

     //cursor variables for changing the cursor
    public enum CursorType{
       NONE, RETICLE, POINTERHAND,ACTIVEHAND,UNACTIVEHAND,PURCHASEHAND;
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
            case RETICLE: cursor = new Cursor(new Image("reticle06.png"){{ setDimensions(39,39); setColor(new Color(1f,1f,1f));}}, Anchorable.Anchor.CENTER);  break;
            case ACTIVEHAND: cursor = new Cursor(new Image("reticle08.png"){{ setDimensions(39,39);}}, Anchorable.Anchor.CENTER); break;
            case UNACTIVEHAND: cursor = new Cursor(new Image("reticle10.png"){{ setDimensions(39,39);}}, Anchorable.Anchor.CENTER);  break;
            case POINTERHAND: cursor = new Cursor(new Image("reticle07.png"){{ setDimensions(39,39);}}, Anchorable.Anchor.TOPCENTER); break;
            case PURCHASEHAND: cursor = new Cursor(new Image("reticle09.png"){{ setDimensions(39,39);}}, Anchorable.Anchor.CENTER); break;
            default: cursor = null;
        }
        
        return cursor;
    }
}
