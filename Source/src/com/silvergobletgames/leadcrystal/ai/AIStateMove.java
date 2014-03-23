package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;

public class AIStateMove extends AIState
{    
    
    //singleton instance
    private static AIStateMove instance;
    
    //=============
    // Constructors
    //==============
    
    private AIStateMove()
    {
        this.stateID = StateID.MOVE;
    }
    
    public static AIStateMove getInstance()
    {
        if (instance == null)
            instance = new AIStateMove();
        
        return instance;
    }
    
    
    //=============
    // Class Methods
    //==============
      
    public void enter(Brain brain){
        brain.moveEnter();
    }
    
    public void execute(Brain brain){
        
        brain.moveExecute();  
    }
    
    public void exit(Brain brain){
        brain.moveExit();  
    }
         
}
