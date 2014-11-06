package com.silvergobletgames.leadcrystal.ai;

import net.phys2d.math.Vector2f;
import com.silvergobletgames.leadcrystal.ai.BrainFactory.BrainID;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.items.DropGenerator;
import com.silvergobletgames.leadcrystal.items.LootSpewer;
import com.silvergobletgames.leadcrystal.netcode.GobletServer;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.util.SylverVector2f;

/**
 *
 * @author mike
 */
public class BrainCrate extends Brain
{
    
    
    /**
     * NPC brain.
     */
    public BrainCrate() 
    {
        super();
        this.ID =BrainID.Crate;
        relevantGroups.add(ExtendedSceneObjectGroups.CRATE);
    }
    
    
    /**
     * Behavior executed in the IDLE state:
     * Wander back and forth semi randomly, never go more than 500 units in either direction
     */
    public void idleExecute()
    {        
        self.getTooltip().specialDoNotDisplay = true; 
    }
    
    /**
     * Behavior executed in the AGGRESSIVE state:
     * Wander but if a player is near move towards him
     */
    public void aggressiveExecute()
    {

    }
    
    public void damageTaken(Damage d) 
    {

        if(self.getCombatData().getPercentHealth() <= .5f)
        {
            //switch animation to damaged
            self.getImage().setAnimation(ExtendedImageAnimations.RUNNING); 
        }
    }
    
    public void deadEnter()
    {
        //if we are a gold chest
        if(self.getCombatData().dropGoldChance == DropGenerator.DropChance.ALWAYS)
        {
            //add spewer to the world
            LootSpewer spew = new LootSpewer(60);
            spew.setPosition(self.getPosition().x, self.getPosition().y);
            self.getOwningScene().add(spew, Scene.Layer.MAIN);
        }
    }
    
    

    
    
    
    
    
    
    
    
}
