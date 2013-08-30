package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;

public class AIStateIdle extends AIState
{    
    
    //singleton instance
    private static AIStateIdle instance;
    
    //=============
    // Constructors
    //==============
    
    private AIStateIdle()
    {
        this.stateID = StateID.IDLE;
    }
    
    public static AIStateIdle getInstance()
    {
        if (instance == null)
            instance = new AIStateIdle();
        
        return instance;
    }
    
    
    //=============
    // Class Methods
    //==============
      
    public void enter(Brain brain){
        brain.idleEnter();
    }
    
    public void execute(Brain brain){
        
        brain.idleExecute();  
    }
    
    public void exit(Brain brain){
        brain.idleExit();  
    }
         
}