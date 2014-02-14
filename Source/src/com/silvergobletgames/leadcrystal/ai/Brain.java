package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.core.SceneObjectManager;
import java.util.*;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.*;
import net.phys2d.raw.shapes.Polygon;
import com.silvergobletgames.leadcrystal.ai.AIState.StateID;
import com.silvergobletgames.leadcrystal.ai.BrainFactory.BrainID;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.leadcrystal.skills.Skill;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;
import com.silvergobletgames.sylver.util.SylverRandom;

/**
 * The meat of the NPE System.  Holds the methods necessary to command the entity
 * to behave in a certain way as well as respond to stimuli.
 * 
 * An important concept in the Brain is the concept of the self.  'self' refers to the
 * Entity that this brain currently belongs to.  This provides a very intuitive way,
 * in English-sounding terms, to denote the Entity we are interested in having
 * actions performed by in response to the experiences of its brain.  Not to be 
 * confused with 'this', which refers to the Brain instance.
 * 
 * BRAIIINNNNSSSSSSSSSS
 * 
 * @author Justin Capalbo
 */
public class Brain
{
    //reference to the entity that owns this brain
    protected NonPlayerEntity self;
    //The finite state machine that maintains an enum state for this brain 
    protected AIStateMachine stateMachine;
    //brain ID
    protected BrainID ID;  
    //groups this brain belongs to
    protected HashSet<ExtendedSceneObjectGroups> relevantGroups = new HashSet();
    //Incoming message queue
    private LinkedList<AIMessage> incomingMessages = new LinkedList();
    
    //skill that has been selected
    protected Skill selectedSkill; 
    //unit spacing
    protected boolean unitSpacingEnabled = false;
    
    
    //==============
    // Constructor
    //==============
    
    /**
     * Brain with no arguments
     */
    public Brain()
    {
        ID = BrainID.None;
        
        stateMachine = new AIStateMachine(this, AIState.StateID.IDLE);
    }
    
    
    //================
    // Global Methods
    //================
    
    /**
     * Attempts to locate the target. Checks range and line of sight. Uses the locate distance
     * @param target Target to locate
     * @return True if the target was in range and in LOS
     */
    public final boolean locate(Entity target)
    {
        if (target != null) 
        {
            
            
            //range check
            if (self.distanceAbs(target) > self.getLocateDistance())         
                return false;
            
            //check los
            return checkLOS(target);
            
            
        }
        else
            return false;
    }  
    
    /**
     * Checks the LOS to target, returns true if there is an unobstructed LOS
     * @param target
     * @return 
     */
    public final boolean checkLOS(Entity target)
    {
        //==========LOS check=================
        if(target != null)
        {
            //make line from self to target
            Vector2f point1 = new Vector2f(self.getPosition().x, self.getPosition().y);
            Vector2f point2 = new Vector2f(self.getPosition().x +5, self.getPosition().y +5);
            Vector2f point3 = new Vector2f(target.getPosition().x +5, target.getPosition().y +5);
            Vector2f point4 = new Vector2f(target.getPosition().x , target.getPosition().y );
            Vector2f[] vectorArray = {point1,point2,point3,point4};
            Polygon line = new Polygon(vectorArray);   
            Body lineBody = new StaticBody(line);
            lineBody.setBitmask(Entity.BitMasks.COLLIDE_WORLD.value);
            
            
            //get list of LOS blocking terrain
            ArrayList<SceneObject> terrain = self.getOwningScene().getSceneObjectManager().get(ExtendedSceneObjectGroups.TERRAIN);
            
            //collide everything
            for(SceneObject object: terrain)
            {
                WorldObjectEntity blocker = (WorldObjectEntity)object;
                
                Contact[] contacts = new Contact[10];
                for (int i=0;i<10;i++) {
			contacts[i] = new Contact();
		}
                int numContacts = Collide.collide(contacts,blocker.getBody() , lineBody, 1);
                
                if(numContacts > 0)
                {
                    return false;
                }
            }          
            return true;
        }
        else return false;
    }
    
    /**
     *  Try to target the closest player
     */
    public final PlayerEntity targetClosestPlayer()
    {
        float closestDistance = Float.MAX_VALUE;
        PlayerEntity closestPlayer = null;
        
        ArrayList<PlayerEntity> players = ((GameServerScene)self.getOwningScene()).getPlayers(); 
        for(PlayerEntity player: players)
        {
            if(self.distanceAbs(player) < closestDistance)
            {
                closestDistance = self.distanceAbs(player);
                closestPlayer = player;
            }
        }
        
        self.target(closestPlayer);
        
        return closestPlayer;
    }
    
    /**
     * This method will select a random known skill. This method should be overridden to allow 
     * more advanced skill selection
     */
    public void selectSkill()
    {
        HashSet<SkillID> knownSkills = self.getSkillManager().getKnownSkills();
        
        ArrayList<SkillID> knownSkillsArray = new ArrayList(knownSkills);
        
        Random r = SylverRandom.random;
        int randomNumber = r.nextInt(knownSkillsArray.size());
        
        this.selectedSkill = self.getSkillManager().getSkill(knownSkillsArray.get(randomNumber));
    }
     
    /**
     * Generic message dispatch method.
     * @param group The group to message
     * @param exceptState Don't message an entity if it's in a certain state
     * @param range Don't message entities that are outside a certain distance
     * @param msgType The message to send
     * @param delay How long to wait before sending the message
     */
    public void messageGroup(ExtendedSceneObjectGroups group, float range, int msgType) 
    {
        //First, get the list of the group
        ArrayList<SceneObject> groupList = self.getOwningScene().getSceneObjectManager().get(group);
        
        //for each object in the group
        for(SceneObject sceneObject: groupList) 
        {
            //Check to make sure we only send things to NPE's
            if (sceneObject instanceof NonPlayerEntity)
            {
                NonPlayerEntity other = (NonPlayerEntity)sceneObject;
                if (other.distanceAbs(self) <= range && other != self ) 
                {
                    //send message to other brain
                    AIMessage msg = new AIMessage(self, other, msgType);
                    other.getBrain().incomingMessages.add(msg);
                }
            }
        }
    }    
    
    /**
     * Returns true if I'm the target of a particular type of entity in a group.
     * @param group
     * @return 
     */
    protected boolean targetOf(ExtendedSceneObjectGroups group)
    {
        Entity[] tgtMe = self.getTargetedBy();
        for (int i = 0; i< tgtMe.length; i++){
            //Just in case.
            if (tgtMe[i] instanceof NonPlayerEntity){
                NonPlayerEntity e = (NonPlayerEntity)tgtMe[i];
                if(e.isInGroup(group)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //=================
    // State Methods
    //================
    
    /**
     * Handles any per-world-update related tasks.
     */
    public void update()
    {
        //handle all messages from the queue
        while(!this.incomingMessages.isEmpty())
        {
            AIMessage incomingMessage = this.incomingMessages.pop();
            this.onMessage(incomingMessage);
        }
        
        stateMachine.update();
    }
    
    /**
     * Abstract message handling of this brain
     * @param msg
     * @return 
     */
    public void onMessage(AIMessage msg)
    {
       
    }

      
    public void damageTaken(Damage d) 
    {
        //if we are less than 15% health, enter the dying state
        if(self.getCombatData().percentHealth() <= .15)
            this.stateMachine.changeState(StateID.DYING);
        else if(this.stateMachine.getCurrentState().stateID == StateID.DYING) //if we are in the dying state but are above 15% health, revert
        {
            this.stateMachine.revertToPreviousState();
        }
        
    }
    
    
    /**
     * Behavior executed when entering the idle state
     */
    protected void idleEnter(){
        
    }
    
    /**
     * Behavior executed while in the idle state
     */
    protected void idleExecute(){
        
    }
    
    /**
     * Behavior executed leaving the idle state
     */
    protected void idleExit(){
        
    }
    
    
    /**
     * Behavior executed when entering the aggressive state
     */
    protected void aggressiveEnter(){
        
    }
    
    /**
     * Behavior executed while in the aggressive state
     */
    protected void aggressiveExecute(){
        
    }
    
    /**
     * Behavior executed leaving the aggressive state
     */
    protected void aggressiveExit(){
        
    }
    
    
    
    /**
     * Behavior executed when entering the move state
     */
    protected void moveEnter(){
        
    }
    
    /**
     * Behavior executed while in the move state
     */
    protected void moveExecute(){
        
    }
    
    /**
     * Behavior executed leaving the move state
     */
    protected void moveExit(){
        
    }
    
    
    /**
     * Behavior executed when entering the lostTarget state
     */
    protected void lostTargetEnter(){
        
    }
    
    /**
     * Behavior executed while in the lostTarget state
     */
    protected void lostTargetExecute(){
        
    }
    
    /**
     * Behavior executed leaving the lostTarget state
     */
    protected void lostTargetExit(){
        
    }
    
    
    /**
     * Behavior executed when entering the assist state
     */
    protected void assistEnter(){
        
    }
    
    /**
     * Behavior executed while in the assist state
     */
    protected void assistExecute(){
        
    }
    
    /**
     * Behavior executed leaving the assist state
     */
    protected void assistExit(){
        
    }
    
    
    /**
     * Behavior executed when entering the dying state
     */
    protected void dyingEnter(){
        
    }
    
    /**
     * Behavior executed while in the dying state
     */
    protected void dyingExecute(){
        
    }
    
    /**
     * Behavior executed leaving the dying state
     */
    protected void dyingExit(){
        
    }
    
    
    /**
     * Behavior executed when entering the dying state
     */
    protected void deadEnter(){
        
    }
    
    /**
     * Behavior executed while in the dying state
     */
    protected void deadExecute(){
        
    }
    
    /**
     * Behavior executed leaving the dying state
     */
    protected void deadExit(){
        
    }
    
    
    protected void spawningEnter()
    {
        
    }
    
    protected void spawningExecute()
    {
        
    }
    
    protected void spawningExit()
    {
        
    }
    
    
    
    
    
    
    
    //=============
    // Accessors
    //=============
      
    public BrainID getID()
    {
        return ID;
    }
    
    /**
     * Designates the NPE that this Brain belongs to.
     * @param self 
     */
    public void setOwner(NonPlayerEntity owner)
    {
        this.self = owner;
        Iterator<ExtendedSceneObjectGroups> iter = relevantGroups.iterator();
        while (iter.hasNext()){
            self.addToGroup(iter.next());
        }
    }
    
    /**
     * Returns the NPE that this Brain belongs to
     * @return 
     */
    public NonPlayerEntity getOwner()
    {
        return self;
    }
    
    public AIStateMachine getStateMachine()
    {
        return this.stateMachine;
    }
    

    
    
}
