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
import com.silvergobletgames.leadcrystal.skills.Skill;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillType;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;

/**
 * The meat of the AI System.  Holds the methods necessary to command the entity
 * to behave in a certain way.
 * @author Justin Capalbo
 */
public class BrainFighter extends BrainGround
{
    private int timeWithThisSkill = 0;
    private float timeWithSkillFuzz = 1;
    private int timeSinceAttack = 0;
    private int lostLOSCounter = 0;
    private long fuzzyLogicTimer = 0;
    private float maxRangeFuzz = 1f;
    private SylverVector2f lostTargetPosition = new SylverVector2f();
    Random r = SylverRandom.random;
    
    
    /**
     * Fighter brain.
     */
    public BrainFighter() 
    {
        super();
        relevantGroups.add(ExtendedSceneObjectGroups.FIGHTER);
        ID = BrainID.Fighter;
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
//        if (msg.messageID == AIMessage.MSG_ENEMY_FOUND
//                && !this.getStateMachine().isInState(AIState.StateID.DYING)
//                && !this.getStateMachine().isInState(AIState.StateID.AGGRESSIVE)
//                && !this.getStateMachine().isInState(AIState.StateID.SPAWNING)
//                && !self.isInGroup(ExtendedSceneObjectGroups.HEALER)
//                && !self.getCombatData().isDead()) 
//        {
//            this.targetClosestPlayer();
//            this.getStateMachine().changeState(AIState.StateID.AGGRESSIVE);
//        }
       
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
        
        //if we can locate the target within unit's locate distance, switch to agressive. else wander
        if( self.getTarget() != null && this.locate(self.getTarget()) )
            this.getStateMachine().changeState(StateID.AGGRESSIVE);
        else
        {
            self.clearTarget();         
            this.patrol(self.placedLocation,true);
        }
        
    }
    
    public void idleExit()
    {
    
         self.getCombatData().xVelocity.adjustPercentModifier(.4f);
         
          //play aggro sound

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
        if(fuzzyLogicTimer %30 == 0)
        {
            if(this.checkLOS(self.getTarget()) == false)
            {
                this.lostLOSCounter++;  
            }
            if(this.lostLOSCounter >= 2)
            {
                this.getStateMachine().changeState(StateID.LOSTTARGET);
                this.lostLOSCounter = 0;
                return;
            }
        }
        
        
        //if we are ranged, and its been a while since we attacked, declare lost target
        if(this.selectedSkill != null && this.selectedSkill.getRange() > 150 && this.timeSinceAttack > 300)
        {
            this.getStateMachine().changeState(StateID.LOSTTARGET);
            this.timeSinceAttack = 0;
            return;
        }
        
        //reroll fuzzy logic things
        fuzzyLogicTimer++;
        if(fuzzyLogicTimer % 180 == 0)
        {
            
            this.maxRangeFuzz = 1 + r.nextFloat()/2;
            this.timeWithSkillFuzz = .5f + r.nextFloat();
        }
        
        //if our y distance is large and our x distance is close to 0, target is unreachable
//        if(Math.abs(self.getTarget().distanceVector(self).x) < 50 && Math.abs(self.getTarget().distanceVector(self).y) > 250)
//        {   
//            this.getStateMachine().changeState(StateID.LOSTTARGET);
//            return;
//        }
            

        //===============================
        // Attempt to use selected Skill
        //===============================
        if(this.selectedSkill != null)
        {
            //increment time with this skill
            this.timeWithThisSkill++;
            //if we haven't attacked in 6 seconds, select a new skill
            if(this.timeWithThisSkill >= 360 * timeWithSkillFuzz)
            {
                this.selectSkill();
                this.timeWithThisSkill = 0;
            }
            
            //if we are out of range of the skill move into range with some fuzzy logic in there
            if((self.getTarget().distanceAbs(self) * this.maxRangeFuzz) > this.selectedSkill.getRange())
            {               
             
                
                //if the selected skill is melee, move towards target without edge detection
                if(this.selectedSkill.getRange() <= 150)
                    this.moveTowardsPoint(self.getTarget().getPosition(), false);
                else
                    this.moveTowardsPoint(self.getTarget().getPosition(), true);
                

                //random chance to jump
                if(Math.random()< .001f)
                    self.jump();
                
                //increment time since attack
                this.timeSinceAttack++;

            }
            else // if we are in range, cast the skill
            {
                //TODO- random delay
                self.faceTarget();        
                self.attack(selectedSkill);
                this.selectedSkill = null;
                this.timeSinceAttack = 0;
            }
        }
        
    }
    
    public void aggressiveExit()
    {
        this.unitSpacingEnabled = false;
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
            this.getStateMachine().changeState(StateID.AGGRESSIVE);
        else
        {
            if(this.selectedSkill != null && this.selectedSkill.getRange() <= 150)
                this.patrol(lostTargetPosition,false);
            else
                this.patrol(lostTargetPosition,true);
        }
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
    
    
}
