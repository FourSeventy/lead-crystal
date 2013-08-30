package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;

public class AIStateDead extends AIState
{    
    
    //singleton instance
    private static AIStateDead instance;
    
    //=============
    // Constructors
    //==============
    
    private AIStateDead()
    {
        this.stateID = StateID.DEAD;
    }
    
    public static AIStateDead getInstance()
    {
        if (instance == null)
            instance = new AIStateDead();
        
        return instance;
    }
    
    
    //=============
    // Class Methods
    //==============
      
    public void enter(Brain brain){
        brain.deadEnter();
    }
    
    public void execute(Brain brain){
        
        brain.deadExecute();  
    }
    
    public void exit(Brain brain){
        brain.deadExit();  
    }
         
}
