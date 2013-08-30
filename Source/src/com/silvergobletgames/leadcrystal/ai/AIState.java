package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;
import java.util.HashMap;


public abstract class AIState
{

    //Holds the states state id
    protected StateID stateID;
    
    
    
    public static enum StateID{
        DEAD,IDLE,AGGRESSIVE,LOSTTARGET,DYING,ASSIST,SPAWNING;
    }
    
      
    /*
     * Returns a reference to the singleton state with the given stateID
     */
    public static AIState get(StateID stateID)
    {
        switch(stateID)
        {
            case DEAD: return AIStateDead.getInstance();
            case IDLE: return AIStateIdle.getInstance();
            case AGGRESSIVE: return AIStateAggressive.getInstance();
            case DYING: return AIStateDying.getInstance();
            case ASSIST: return AIStateAssist.getInstance();
            case SPAWNING: return AIStateSpawning.getInstance();
            case LOSTTARGET: return AIStateLostTarget.getInstance();
            default: return null;              
        }
    }
    
    
    public abstract void enter(Brain brain);
    public abstract void execute(Brain brain);
    public abstract void exit(Brain brain);
}
