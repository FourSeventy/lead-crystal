package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;

public class AIStateLostTarget extends AIState
{    
    
    //singleton instance
    private static AIStateLostTarget instance;
    
    //=============
    // Constructors
    //==============
    
    private AIStateLostTarget()
    {
        this.stateID = StateID.LOSTTARGET;
    }
    
    public static AIStateLostTarget getInstance()
    {
        if (instance == null)
            instance = new AIStateLostTarget();
        
        return instance;
    }
    
    
    //=============
    // Class Methods
    //==============
      
    public void enter(Brain brain){
        brain.lostTargetEnter();
    }
    
    public void execute(Brain brain){
        
        brain.lostTargetExecute();  
    }
    
    public void exit(Brain brain){
        brain.lostTargetExit();  
    }
         
}
