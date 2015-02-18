/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.sylver.core.InputSnapshot;

/**
 *
 * @author Mike
 */
public class ControlsAdapter {
    
    public static enum GameControl{
        UP, DOWN, LEFT, RIGHT, JUMP, SPRINT, POTION, FLASHLIGHT, SKILL3, SKILL4
    }
    
    private InputSnapshot snapshot;
    
    
    public ControlsAdapter(InputSnapshot inputSnapshot)
    {
        this.snapshot = inputSnapshot;
    }
    
    
    public boolean isControlPressed(GameControl control)
    {
        //get key from settings
        short boundKey = this.getBoundKey(control);
           
        //check against inputSnapshot
        return this.snapshot.isKeyPressed(boundKey);
        
    }
    
    
    public boolean isControlReleased(GameControl control)
    {
        //get key from settings
        short boundKey = this.getBoundKey(control);
           
        //check against inputSnapshot
        return this.snapshot.isKeyReleased(boundKey);
    }
    
    
    
    private short getBoundKey(GameControl control)
    {
        short boundKey = 0;       
        switch(control)
        {
            case UP: boundKey = GameplaySettings.getInstance().up; break;
            case DOWN:  boundKey = GameplaySettings.getInstance().down; break;
            case LEFT:  boundKey = GameplaySettings.getInstance().left; break;
            case RIGHT:  boundKey = GameplaySettings.getInstance().right; break;
            case JUMP:  boundKey = GameplaySettings.getInstance().jump; break;
            case SPRINT: boundKey = GameplaySettings.getInstance().sprint;  break;
            case POTION: boundKey = GameplaySettings.getInstance().potion;  break;
            case FLASHLIGHT: boundKey = GameplaySettings.getInstance().flashlight;  break;
            case SKILL3:  boundKey = GameplaySettings.getInstance().skill3; break;
            case SKILL4:  boundKey = GameplaySettings.getInstance().skill4; break;
                                        
        }
        return boundKey;
    }
    
}
