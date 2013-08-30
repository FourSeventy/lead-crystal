package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;

public class AIStateAssist extends AIState
{    
    
    //singleton instance
    private static AIStateAssist instance;
    
    //=============
    // Constructors
    //==============
    
    private AIStateAssist()
    {
        this.stateID = StateID.ASSIST;
    }
    
    public static AIStateAssist getInstance()
    {
        if (instance == null)
            instance = new AIStateAssist();
        
        return instance;
    }
    
    
    //=============
    // Class Methods
    //==============
      
    public void enter(Brain brain){
        brain.assistEnter();
    }
    
    public void execute(Brain brain){
        
        brain.assistExecute();  
    }
    
    public void exit(Brain brain){
        brain.assistExit();  
    }
         
}
