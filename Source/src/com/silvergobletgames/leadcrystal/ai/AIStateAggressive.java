package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;

public class AIStateAggressive extends AIState
{    
    
    //singleton instance
    private static AIStateAggressive instance;
    
    //=============
    // Constructors
    //==============
    
    private AIStateAggressive()
    {
        this.stateID = StateID.AGGRESSIVE;
    }
    
    public static AIStateAggressive getInstance()
    {
        if (instance == null)
            instance = new AIStateAggressive();
        
        return instance;
    }
    
    
    //=============
    // Class Methods
    //==============
      
    public void enter(Brain brain){
        brain.aggressiveEnter();
    }
    
    public void execute(Brain brain){
        
        brain.aggressiveExecute();  
    }
    
    public void exit(Brain brain){
        brain.aggressiveExit();  
    }
         
}
