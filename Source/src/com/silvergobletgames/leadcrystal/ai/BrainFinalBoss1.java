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
import com.silvergobletgames.leadcrystal.combat.CombatData;
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
public class BrainFinalBoss1 extends BrainGround
{

    Random r = SylverRandom.random;
    
    private Entity moveTarget;
    private int timer = 0;
    
    
    /**
     * Fighter brain.
     */
    public BrainFinalBoss1() 
    {
        super();
        ID = BrainID.FinalBoss1;
    }

  
    //=====================
    // State Functionality
    //=====================

    
    public void selectSkill()
    {        
        //if we dont have a target, select a random skill
        if(self.getTarget() == null)
        {
            super.selectSkill();
            return;
        }

        //get all offensive skills
        ArrayList<Skill> skillPool = self.getSkillManager().getKnownSkillsOfType(SkillType.OFFENSIVE);
        
        Random r = SylverRandom.random;
        
        //get range from target
        float rangeToTarget = self.distanceAbs(self.getTarget());
        //blur the range
        rangeToTarget = (float)Math.abs(r.nextGaussian()  * (rangeToTarget/6) + rangeToTarget);
        
        //try to select the skill that has the smallest range that is still in range
        float skillRange = Float.MAX_VALUE;
        Skill closestSkill = null;      
        for(Skill skill: skillPool)
        {
            //if this skill is in range, and it is smaller than the current skill range
            if(skill.getRange() >= rangeToTarget && skill.getRange() < skillRange)
            {
                skillRange = skill.getRange();
                closestSkill = skill;               
            }
        }
        
        //if none of the skills were in range, select the skill with the largest range
        if(closestSkill == null)
        {
            skillRange = Float.MIN_VALUE;
            for(Skill skill: skillPool)
            {
                //if this skill is in range, and it is smaller than the current skill range
                if(skill.getRange() >= skillRange )
                {
                    skillRange = skill.getRange();
                    closestSkill = skill;               
                }
            }
            
        }
        
        
        //set active skill
        this.selectedSkill = closestSkill;
           
    }
    
    
    protected void spawningEnter()
    {
        self.getImage().setAnimation(ExtendedImageAnimations.SPAWN);
    }
    
    public void idleEnter()
    {
        
    }
    public void idleExecute()
    {
       self.getCombatData().setState(CombatData.CombatState.IMMUNE); 
    }
    
    /**
     * Behavior executed when entering the move state
     */
    protected void moveEnter()
    {
        self.getCombatData().removeState(CombatData.CombatState.IMMUNE);
        
        // if previous move target is null, we are in starting state
        if(this.moveTarget == null)
        {
            this.moveTarget = (Entity)self.getOwningScene().getSceneObjectManager().get("rightGround");
        }
        
        
        //list of all targets  
        ArrayList<String> targetIds = new ArrayList<>();
        
        
        
        String previousID = this.moveTarget.getID();
        
        //leftGround
        //midGround
        //rightGround
        //leftPlat
        //rightPlat
        //midPlat
        //add available move targets      
        switch(previousID)
        {
            case "leftGround": 
                targetIds.add("midGround");
                targetIds.add("leftPlat");
            break;
            case "leftPlat": 
                targetIds.add("rightPlat");
                targetIds.add("midGround");
            break;
            case "rightGround": 
                targetIds.add("midGround");
                targetIds.add("rightPlat");
            break;
            case "rightPlat": 
                targetIds.add("leftPlat");
                targetIds.add("midGround");
            break;
            case "midGround":
                targetIds.add("leftGround");
                targetIds.add("rightGround");
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
        
         if( r.nextFloat() < .0025f)
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
    
    
    
}
