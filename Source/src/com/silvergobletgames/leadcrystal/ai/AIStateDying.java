package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;

public class AIStateDying extends AIState
{    
    
    //singleton instance
    private static AIStateDying instance;
    
    //=============
    // Constructors
    //==============
    
    private AIStateDying()
    {
        this.stateID = StateID.DYING;
    }
    
    public static AIStateDying getInstance()
    {
        if (instance == null)
            instance = new AIStateDying();
        
        return instance;
    }
    
    
    //=============
    // Class Methods
    //==============
      
    public void enter(Brain brain){
        brain.dyingEnter();
    }
    
    public void execute(Brain brain){
        
        brain.dyingExecute();  
    }
    
    public void exit(Brain brain){
        brain.dyingExit();  
    }
         
}