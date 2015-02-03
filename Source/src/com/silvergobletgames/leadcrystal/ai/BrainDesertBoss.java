package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.sylver.core.SceneObjectManager;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.ImageEffect.ImageEffectType;
import java.util.ArrayList;
import java.util.Random;
import net.phys2d.math.Vector2f;
import com.silvergobletgames.leadcrystal.ai.AIState.StateID;
import com.silvergobletgames.leadcrystal.ai.BrainFactory.BrainID;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.items.DropGenerator;
import com.silvergobletgames.leadcrystal.items.LootSpewer;
import com.silvergobletgames.leadcrystal.skills.Skill;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillType;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;

/**
 * The meat of the AI System.  Holds the methods necessary to command the entity
 * to behave in a certain way.
 * @author Justin Capalbo
 */
public class BrainDesertBoss extends BrainGround
{

    Random r = SylverRandom.random;
    
    private Entity moveTarget;
    private int timer = 0;
    
    
    /**
     * Fighter brain.
     */
    public BrainDesertBoss() 
    {
        super();
        ID = BrainID.DesertBoss;
        
        relevantGroups.add(ExtendedSceneObjectGroups.FIGHTER);
    }

  
    //=====================
    // State Functionality
    //=====================

    
    public void selectSkill()
    {        

        //get all offensive skills
        ArrayList<Skill> skillPool = self.getSkillManager().getKnownSkillsOfType(SkillType.OFFENSIVE);
  
        if(r.nextFloat() < .33) //33% chance
        {
            this.selectedSkill = skillPool.get(1);
        }
        else //66%  change
        {
            this.selectedSkill = skillPool.get(0);
        }
    }
    
    
    protected void spawningEnter()
    {
        self.getImage().setAnimation(ExtendedImageAnimations.SPAWN);
    }
    
    protected void spawningExecute()
    {
        if(self.getImage().getAnimation() != ExtendedImageAnimations.SPAWN)
        {
            this.getStateMachine().changeState(StateID.AGGRESSIVE);
        }
    }
    
    
    /**
     * Behavior executed when entering the move state
     */
    protected void moveEnter()
    {
        // if previous move target is null, we are in starting state
        if(this.moveTarget == null)
        {
            this.moveTarget = (Entity)self.getOwningScene().getSceneObjectManager().get("rightGround");
        }
        
        
        //list of all targets  
        ArrayList<String> targetIds = new ArrayList<>();
        
        
        
        String previousID = this.moveTarget.getID();
        
        //add available move targets      
        switch(previousID)
        {
            case "rightPlat": 
                targetIds.add("midPlat");
            break;
            case "midPlat": 
                targetIds.add("rightPlat");
                targetIds.add("leftPlat");
                targetIds.add("leftGround");
                targetIds.add("rightGround");
            break;
            case "leftPlat": 
                targetIds.add("midPlat");
            break;
            case "rightGround": 
                targetIds.add("rightPlat");
                targetIds.add("midPlat");
            break;
            case "leftGround":
                targetIds.add("midPlat");
                targetIds.add("leftPlat");
            break;
        }
        
        String targetID = targetIds.get(r.nextInt(targetIds.size()));
        this.moveTarget = (Entity)self.getOwningScene().getSceneObjectManager().get(targetID);
        
        timer = 0;
        
    }
    
    /**
     * Behavior executed while in the move state
     */
    protected void moveExecute()
    {
        timer++;      
        SylverVector2f distanceVector = self.distanceVector(moveTarget);
        
      
        this.moveTowardsPoint(moveTarget.getPosition(), false);
        
        if( timer %60 == 0 && moveTarget.getPosition().y > self.getPosition().y + 100)
        {       
            
            float mag = distanceVector.length();
            distanceVector.normalise();
            
            float scaling = 1;
            if(mag < 400)
                scaling = .55f;
            self.jump((int)(20_000* scaling * distanceVector.x), (int)(28_000* scaling * distanceVector.y)); 
        }
        
        //if we are stuck
        if(timer > 7 * 60)
        {
            this.moveEnter(); //pick a new target
        }
        
        
        //if distance is close to target, switch to aggressive
        if(this.moveTarget.distanceAbs(self) < 120)
        {
            this.getStateMachine().changeState(StateID.AGGRESSIVE);
        }
        
    }
    
    /**
     * Behavior executed leaving the move state
     */
    protected void moveExit(){
        
    }
    
    
    
    public void aggressiveEnter()
    {      
        this.unitSpacingEnabled = true;
    }
    
    /**
     * 
     */
    public void aggressiveExecute() 
    {
             
        //if we dont have a target get one
        if(self.getTarget() == null)
        {
            this.targetClosestPlayer();
        }
        
                 
        //if our selected skill is null, select a skill to use
        if(this.selectedSkill == null)
        {
            this.selectSkill();
        }
        

        //===============================
        // Attempt to use selected Skill
        //===============================
        if(this.selectedSkill != null)
        {

            //TODO- random delay
            self.faceTarget();        
            self.attack(selectedSkill);
            this.selectedSkill = null;
            
        }
        
        if( r.nextFloat() < .0040f)
        {
            this.getStateMachine().changeState(StateID.MOVE);
        }
        
    }
    
    public void aggressiveExit()
    {
        this.unitSpacingEnabled = false;
    }

 
    /**
     * Behavior executed in the DYING state
     */
    public void dyingExecute()
    {
        //message enemies for help
        messageGroup(ExtendedSceneObjectGroups.FIGHTER, 500, AIMessage.MSG_ENEMY_FOUND);

        //if im not being healed run away
        if (!targetOf(ExtendedSceneObjectGroups.HEALER))         
            flee(self.getTarget());       

        if (stateMachine.getTimeInCurrentState() % 15 == 0) 
        {
            //add a close to death effect
            self.getImage().addImageEffect(new ImageEffect(ImageEffectType.COLOR, 4, new Color(Color.darkGray), new Color(Color.white)));
        }
    }

    /**
     * Denotes a response to damage being taken.
     * @param d 
     */
    public void damageTaken(Damage d) 
    {

        //If not aggressive and damaged by a player
//        if (!this.getStateMachine().isInState(AIState.StateID.AGGRESSIVE) && !this.getStateMachine().isInState(AIState.StateID.SPAWNING) && d.getSource() instanceof PlayerEntity)
//        {
//            if (!d.getSource().getCombatData().isDead())
//            {
//                int delay = (int) ((Math.random() * 150.0) + 50.0);
//                if (this.getStateMachine().isInState(AIState.StateID.DYING)) 
//                {
//                    delay = 0;
//                }
//                messageGroup(ExtendedSceneObjectGroups.FIGHTER, 500, AIMessage.MSG_ENEMY_FOUND);
//                if (!self.isInGroup(ExtendedSceneObjectGroups.HEALER)) 
//                {
//                    this.targetClosestPlayer();
//                    this.getStateMachine().changeState(AIState.StateID.AGGRESSIVE);
//                }
//            }
//        }
    }
    
    public void deadEnter()
    {

        //add spewer to the world
        LootSpewer spew = new LootSpewer(60 * 5);
        spew.setPosition(self.getPosition().x, self.getPosition().y);
        self.getOwningScene().add(spew, Scene.Layer.MAIN);
        
    }
    
    
}
