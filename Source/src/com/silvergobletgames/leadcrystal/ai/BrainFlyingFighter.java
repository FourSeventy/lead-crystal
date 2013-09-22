
package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.skills.Skill;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.ArrayList;
import java.util.Random;
import net.phys2d.math.Vector2f;

/**
 *
 * @author Mike
 */
public class BrainFlyingFighter extends BrainFlying{
    
    private int timeSinceLastAttack = 0;
    private int lostLOSCounter = 0;
    private long fuzzyLogicTimer = 0;
    private float maxRangeFuzz = 1f;
    private SylverVector2f lostTargetPosition = new SylverVector2f();
    
    
    //=============
    // Constructor
    //=============
    
    /**
     * Fighter brain.
     */
    public BrainFlyingFighter() 
    {
        super();
        relevantGroups.add(ExtendedSceneObjectGroups.FIGHTER);
        ID = BrainFactory.BrainID.FlyingFighter;
    }
    

    
    //=====================
    // State Functionality
    //=====================
    
    /**
     * How do I react to various messages?
     * 
     * This brain responds to an enemy found message if a number of conditions are met.
     * I'm not dying.
     * I'm not already aggressive.
     * I'm not a healer (might be, if this brain is extended, but we should
     *                   avoid having a need for this condition).
     * @param msg
     * @return 
     */
    public void onMessage(AIMessage msg) 
    {
        if (msg.messageID == AIMessage.MSG_ENEMY_FOUND
                && !this.getStateMachine().isInState(AIState.StateID.DYING)
                && !this.getStateMachine().isInState(AIState.StateID.AGGRESSIVE)
                && !this.getStateMachine().isInState(AIState.StateID.SPAWNING)
                && !self.isInGroup(ExtendedSceneObjectGroups.HEALER)
                && !self.getCombatData().isDead()) 
        {
            this.targetClosestPlayer();
            this.getStateMachine().changeState(AIState.StateID.AGGRESSIVE);
        }
       
    }
    
    public void selectSkill()
    {        
        //if we dont have a target, select a random skill
        if(self.getTarget() == null)
        {
            super.selectSkill();
            return;
        }

        //get all offensive skills
        ArrayList<Skill> skillPool = self.getSkillManager().getKnownSkillsOfType(Skill.SkillType.OFFENSIVE);
        
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
    
    /**
     * Denotes a response to damage being taken.
     * @param d 
     */
    public void damageTaken(Damage d) 
    {

        //If not aggressive and damaged by a player
        if (!this.getStateMachine().isInState(AIState.StateID.AGGRESSIVE) && !this.getStateMachine().isInState(AIState.StateID.SPAWNING) && d.getSource() instanceof PlayerEntity)
        {
            if (!d.getSource().getCombatData().isDead())
            {
                int delay = (int) ((Math.random() * 150.0) + 50.0);
                if (this.getStateMachine().isInState(AIState.StateID.DYING)) 
                {
                    delay = 0;
                }
                messageGroup(ExtendedSceneObjectGroups.FIGHTER, 500, AIMessage.MSG_ENEMY_FOUND);
                if (!self.isInGroup(ExtendedSceneObjectGroups.HEALER)) 
                {
                    this.targetClosestPlayer();
                    this.getStateMachine().changeState(AIState.StateID.AGGRESSIVE);
                }
            }
        }
    }
    
    
    protected void spawningEnter()
    {
        self.getImage().setAnimation(ExtendedImageAnimations.SPAWN);
    }
    
    protected void spawningExecute()
    {
        
    }
    
    
    public void idleEnter()
    {
        self.getCombatData().xVelocity.adjustPercentModifier(-.4f);
    }
    
    /**
     * In the idle state the Fighter wanders until a player gets close. Then the Fighter will enter the aggresive state
     */
    public void idleExecute()
    {
        //target the closest player
        this.targetClosestPlayer();
        
        //if we can locate the target within 500 units, switch to agressive. else wander
        if( self.getTarget() != null && this.locate(self.getTarget()))
            this.getStateMachine().changeState(AIState.StateID.AGGRESSIVE);
        else
        {
            self.clearTarget();         
            this.wander(self.placedLocation);
        }
        
    }
    
    public void idleExit()
    {
         self.getCombatData().xVelocity.adjustPercentModifier(.4f);
         
         //play aggro sound
             
    }
    
    
    public void aggressiveEnter()
    {
        
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
        
        // if the target is way out of our range revert out of the aggresive state
        if (self.distanceAbs(self.getTarget()) > 2000)
        {
            stateMachine.revertToPreviousState();
            return;
        }
                 
        //if our selected skill is null, select a skill to use
        if(this.selectedSkill == null)
        {
            this.selectSkill();
        }
        
        //check los
        if(this.checkLOS(self.getTarget()) == false)
            this.lostLOSCounter++;       
        if(this.lostLOSCounter > 180)
        {
            this.getStateMachine().changeState(AIState.StateID.LOSTTARGET);
            this.lostLOSCounter = 0;
            return;
        }
        
        //reroll fuzzy logic things
        fuzzyLogicTimer++;
        if(fuzzyLogicTimer % 180 == 0)
        {
            Random r = SylverRandom.random;
            this.maxRangeFuzz = 1 + r.nextFloat()/3;
        }
        
//        //if our y distance is large and our x distance is close to 0, target is unreachable
//        if(Math.abs(self.getTarget().distanceVector(self).x) < 50 && Math.abs(self.getTarget().distanceVector(self).y) > 100)
//        {   
//            this.getStateMachine().changeState(AIState.StateID.LOSTTARGET);
//            return;
//        }
            

        //===============================
        // Attempt to use selected Skill
        //===============================
        if(this.selectedSkill != null)
        {
            //if we are out of range of the skill move into range with some fuzzy logic in there
            if((self.getTarget().distanceAbs(self) * this.maxRangeFuzz) > this.selectedSkill.getRange()  )
            {               

                //move towards target 
                SylverVector2f patternPosition = new SylverVector2f(self.getTarget().getPosition());
                patternPosition.add(new SylverVector2f(0,300));
                 this.moveTowardsPoint(patternPosition);


                //random chance to jump
                if(Math.random()< .005f)
                    self.jump();

                //keep track of our time since last attack
                timeSinceLastAttack++;

                //if we haven't attacked in 7 seconds, select a new skill
                if(timeSinceLastAttack >= 420)
                    this.selectSkill();

            }
            else // if we are in range, cast the skill
            {
                //TODO- random delay
                self.faceTarget();        
                self.attack(selectedSkill);
                SylverVector2f patternPosition = new SylverVector2f(self.getTarget().getPosition());
                patternPosition.add(new SylverVector2f(0,300));
                this.holdingPattern(patternPosition);
                this.selectedSkill = null;
                this.timeSinceLastAttack =0;
            }
        }
        
    }
    
    /**
     * Behavior executed when entering the lostTarget state
     */
    protected void lostTargetEnter()
    {
        this.lostTargetPosition = new SylverVector2f(self.getPosition());
    }
    
    /**
     * Behavior executed while in the lostTarget state
     */
    protected void lostTargetExecute()
    {
        //target the closest player
        this.targetClosestPlayer();
        
        //if we can locate the target within unit's locate distance, switch to agressive. else wander
        if( self.getTarget() != null && this.locate(self.getTarget()))
            this.getStateMachine().changeState(AIState.StateID.AGGRESSIVE);
        else
        {
            this.wander(lostTargetPosition);
        }
    }
    
    
    /**
     * Behavior executed when entering the dying state
     */
    protected void deadEnter()
    {
        self.getBody().setGravityEffected(true);
        
        int dir;
        dir = Math.random() > 0.5f ? 1: -1;
        self.getBody().addForce(new Vector2f(dir *2000,3000));
    }
    
    
    
    
    
}
