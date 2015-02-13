package com.silvergobletgames.leadcrystal.ai;

import net.phys2d.math.Vector2f;
import com.silvergobletgames.leadcrystal.ai.BrainFactory.BrainID;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.netcode.GobletServer;
import com.silvergobletgames.leadcrystal.scenes.GameScene;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.util.SylverVector2f;

/**
 *
 * @author mike
 */
public class BrainNpc extends BrainGround 
{
    
    boolean runover = false;   
    SylverVector2f startingSpot;
    
    
     /**
     * NPC brain.
     */
    public BrainNpc() {
        super();
        relevantGroups.add(ExtendedSceneObjectGroups.NPC);
        this.ID =BrainID.NPC;
    }
    
    /**
     * Behavior executed in the IDLE state:
     * Wander back and forth semi randomly, never go more than 500 units in either direction
     */
    public void idleExecute()
    {        
       //this.wander(self.placedLocation,true);     
    }
    
    /**
     * Behavior executed in the AGGRESSIVE state:
     * Wander but if a player is near move towards him
     */
    public void aggressiveExecute()
    {
//        PlayerEntity player =((GobletServer)Game.getInstance().getRunnable("Goblet Server")).getHost();
//        //set the entitys initial position
//        if(startingSpot == null)
//            startingSpot = self.getPosition();
//        
//        if(startingSpot.distance(self.getPosition()) <= 200 || (runover && startingSpot.distance(player.getPosition()) < startingSpot.distance(self.getPosition())) )
//        {
//            runover = false;
//            //if we can locate the player (within 500 units)
//            if(locate(player))
//            {
//                if(Math.abs(self.getPosition().x - player.getPosition().x) > 50)
//                    this.moveTowardsPoint(player.getPosition(),true);           
//            }
//            else 
//                this.wander(self.placedLocation,true);
//        }
//        else if(startingSpot.distance(self.getPosition()) > 200)
//        {
//            runover = true;
//        }
//        
    }
    
    

    
    
    
    
    
    
    
    
}
