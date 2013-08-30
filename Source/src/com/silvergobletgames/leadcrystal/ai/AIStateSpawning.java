package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;

public class AIStateSpawning extends AIState
{    
    
    //singleton instance
    private static AIStateSpawning instance;
    
    //=============
    // Constructors
    //==============
    
    private AIStateSpawning()
    {
        this.stateID = StateID.AGGRESSIVE;
    }
    
    public static AIStateSpawning getInstance()
    {
        if (instance == null)
            instance = new AIStateSpawning();
        
        return instance;
    }
    
    
    //=============
    // Class Methods
    //==============
      
    public void enter(Brain brain){
        brain.spawningEnter();
    }
    
    public void execute(Brain brain){
        
        brain.spawningExecute();  
    }
    
    public void exit(Brain brain){
        brain.spawningExit();  
    }
         
}
