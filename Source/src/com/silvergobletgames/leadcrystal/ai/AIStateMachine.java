package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.TextEffect;

public class AIStateMachine 
{
    //owning entity
    private Brain owningBrain;
    //current and previous state
    private AIState currentState = null;
    private AIState prevState = null;
    //time in current state
    private int timeInCurrentState;   
    
    
    //==============
    // Constructor
    //==============
    
    public AIStateMachine(Brain owner, AIState.StateID current)
    {
        this.owningBrain = owner;
        this.currentState = AIState.get(current);
        this.prevState = AIState.get(current);
    }
    
    public void update()
    {
               
        if (currentState != null)
            currentState.execute(owningBrain);
        
        timeInCurrentState++;
    }
   
    
    
    //===============
    // Class Methods
    //===============
    
    /**
     * Attempts to change from the current state to the state of type nextState by
     * finding the State singleton in the AIState map.
     */
    public void changeState(AIState.StateID nextState)
    {
        if (nextState != null && AIState.get(nextState) !=currentState)
        {
            //exit current state
            currentState.exit(owningBrain);
            
            //set previous state
            prevState = currentState;
            
            //set current state
            currentState = AIState.get(nextState);
            currentState.enter(owningBrain);
            
            //Reset clock
            timeInCurrentState = 0;
            
            //debug
            if(GameplaySettings.getInstance().debugEnemies)
            {
                Text debugText = new Text(nextState.toString());
                debugText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 60, 0, 0)); 
                debugText.setPosition(owningBrain.self.getPosition().x, owningBrain.self.getPosition().y + 20);
                if(owningBrain.self.getOwningScene() != null)
                    owningBrain.self.getOwningScene().add(debugText, Scene.Layer.MAIN);
            }
            
        }
    }
    
    public void revertToPreviousState()
    {
        changeState(prevState.stateID);
    }
    
    public AIState getCurrentState()
    {
        return currentState;
    }
    
    public AIState getPrevState()
    {
        return prevState;
    }
    
    public int getTimeInCurrentState()
    {
        return timeInCurrentState;
    }
    
    public boolean isInState(AIState.StateID t)
    {
        if (t == null)
            return false;
        return currentState.stateID == t;
    }


}
