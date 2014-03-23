package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.ArrayList;
import net.phys2d.raw.Body;
import net.phys2d.raw.Collide;
import net.phys2d.raw.Contact;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;

/**
 *
 * @author Mike
 */
public abstract class BrainGround extends Brain
{
    //========Wander Variables===============
    protected float currentWanderGoal;
    protected boolean wanderStopped= false;
    protected int wanderStoppedTime = 0;
    
    //========Patrol Variables ==============
    protected boolean patrolStopped = false;
    protected int patrolStoppedTime = 0;
    
    //======== Holding Pattern Variables ==========
    protected float currentPatternGoal;
    
    
    protected long moveCheckTimer = 0;
    
    
    //====================
    // Movement Methods
    //====================
    
    /**
     * Moves towards the given point. Will use edge detection if specified.
     * @param point Point to move towards.
     * @param edgeDetection Specifies if the method will use edge detection or not
     */
    public void moveTowardsPoint(SylverVector2f destinationPoint, boolean edgeDetection)
    {
        //increment timer
        this.moveCheckTimer++;
        
        //face towards target
        if (destinationPoint.x < self.getPosition().x)          
            self.face(Entity.FacingDirection.LEFT);           
        else            
            self.face(Entity.FacingDirection.RIGHT);
        
        //get direction to move
        float dx = destinationPoint.getX() - self.getPosition().getX();
        float dy = destinationPoint.getY() - self.getPosition().getY();
        SylverVector2f directionToMove = new SylverVector2f(dx,dy);
        
        //normalise and scale
        directionToMove.normalise();
        directionToMove.scale(10);
        
        //==============
        //Enemy Spacing
        //==============
        if(this.unitSpacingEnabled)
        {
            SylverVector2f adjustmentVector = new SylverVector2f(0,0);
            //get all enemies
            ArrayList<SceneObject> allEnemies = self.getOwningScene().getSceneObjectManager().get(ExtendedSceneObjectGroups.FIGHTER);        

            //iterate through enemies, if closer than 100 add a bit of spacing force to vector based on proximity
            for(SceneObject sceneObject: allEnemies)
            {
                NonPlayerEntity enemy = (NonPlayerEntity)sceneObject;
                if(!enemy.getCombatData().isDead() && enemy.distanceAbs(self) < 100)
                {
                    SylverVector2f ehh = enemy.distanceVector(self);
                    ehh.scale(enemy.distanceAbs(self)/30);
                    adjustmentVector.add(ehh);
                }
            }   
            
            //apply adjustment vector for enemy spacing to directionToMove vector
            directionToMove.add(adjustmentVector);
        }

        //if we have edge detection check for ground  
        if(edgeDetection && this.moveCheckTimer % 10 == 0)
        {
            if( this.checkForGround() == true)
                self.move(new SylverVector2f(directionToMove.x,0));
        }
        else
            self.move(new SylverVector2f(directionToMove.x,0));
           
    }
    
    /**
     * Retreats from given target. Fleeing does not have edge detection.
     * @param target Target to flee from
     */
    public void flee(Entity target)
    {
       if (self.getCombatData().canMove() && target != null)
       {
            //face away from target
            if (target.getBody().getPosition().getX() < self.getBody().getPosition().getX()) 
                self.face(Entity.FacingDirection.RIGHT);           
            else             
                self.face(Entity.FacingDirection.LEFT);
             
            //move
            self.move(self.getFacingDirection());
                   
        } 
        
    }
    
    /**
     * Wanders around remaining within self.getWanderDistance() of wanderPoint. Wandering has edge detection
     * @param wanderPoint Point to stay near
     */
    public void wander(SylverVector2f wanderPoint, boolean edgeDetection)
    {          
        //if we are stopped
        if(this.wanderStopped)
        {
            this.wanderStoppedTime--;
            if(this.wanderStopped && this.wanderStoppedTime <= 0)
                this.wanderStopped = false;
            
            return;
        }       
        
        
        boolean groundCheck= true;
        //decide if we need a new goal or not
        if( this.currentWanderGoal == 0 || //dont have a goal
            Math.abs(self.getPosition().x - wanderPoint.x) > self.getWanderDistance()|| //are greater than max wander, and our current goal isnt moving us in the right direction
            Math.abs(self.getPosition().x - (wanderPoint.x + this.currentWanderGoal)) < 2|| //reached wander goal           
            (edgeDetection == true && this.moveCheckTimer %10 == 0 &&!(groundCheck = this.checkForGround())  ) // we hit an edge
          )
        { 
           //============ Roll a new goal ======================
            
            if(Math.random() < .3f)
            {
                this.wanderStopped = true;
                this.wanderStoppedTime = (int)(90 + Math.random() *150);
                return;
            }
             
        
            //roll a new goal           
            float newWanderGoal = ((float)Math.random() * self.getWanderDistance() *(Math.random() > .5f? 1: -1));
            this.currentWanderGoal = newWanderGoal;
                         
            //special handling for edge detection
            if(groundCheck == false)
            {
                self.turnAround();
                
                //if we are still moving toward the same edge, negate our wander goal
                if((self.getFacingDirection().value > 0 && (wanderPoint.x +this.currentWanderGoal < self.getPosition().x))||
                   (self.getFacingDirection().value < 0 && (wanderPoint.x + this.currentWanderGoal > self.getPosition().x)) 
                  )    
                {
                    this.currentWanderGoal = - this.currentWanderGoal;
                }   
                
                
            }

        }
        else
        {
            //============ move towards current goal ================
            if(self.getCombatData().canMove())
            {
                SylverVector2f point = new SylverVector2f(wanderPoint.x + currentWanderGoal, 0);
                this.moveTowardsPoint(point,false);
            }
                              
        }


    }
    
    /**
     * Patrols back and forth in a rigid pattern. Has edge detection.
     * @param patrolPoint Point to stay near
     */
    public void patrol(SylverVector2f patrolPoint, boolean edgeDetection)
    {
        //if we are stopped
        if(this.patrolStopped)
        {
            this.patrolStoppedTime--;
            if(this.patrolStopped && this.patrolStoppedTime <= 0)
                this.patrolStopped = false;
            
            return;
        }
        
        //decide if we need to turn around or not
        if(             
            (Math.abs(self.getPosition().x - patrolPoint.x) > self.getWanderDistance()) && ((self.getFacingDirection().value > 0 && self.getPosition().x > self.placedLocation.x) || (self.getFacingDirection().value < 0 && self.getPosition().x < self.placedLocation.x))|| //are greater than max wander, and our current goal isnt moving us in the right direction                      
            (edgeDetection == true && this.moveCheckTimer %10 == 0 && this.checkForGround() == false )||// we hit an edge
             (this.moveCheckTimer %10 == 0 &&this.checkForWall() == true) //we hit a wall
          )
        {
            //turn around
            self.turnAround();
             
            //chance to stop
            if(Math.random() < .3f)
            {
                this.patrolStopped = true;
                this.patrolStoppedTime = (int)(90 + Math.random() *150);
                return;
            }
            else
            {
                if(self.getCombatData().canMove())
                {
                    SylverVector2f point = new SylverVector2f(self.getPosition().x + self.getFacingDirection().value, 0);
                    this.moveTowardsPoint(point,false);
                }
            }
             
             
        }
        else
        {
            //============ move towards current goal ================
            if(self.getCombatData().canMove())
            {
                SylverVector2f point = new SylverVector2f(self.getPosition().x + self.getFacingDirection().value, 0);
                this.moveTowardsPoint(point,false);
            }
                              
        }
        
    }
    
    /**
     * A tighter wandering type pattern meant for in combat movement.
     */
    public void holdingPattern(SylverVector2f holdingPoint)
    {        
        
        boolean groundCheck= true;
        //decide if we need a new goal or not
        if( this.currentPatternGoal == 0 || //dont have a goal
            Math.abs(self.getPosition().x - holdingPoint.x) > self.getWanderDistance()/2|| //are greater than max wander, and our current goal isnt moving us in the right direction
            Math.abs(self.getPosition().x - (holdingPoint.x + this.currentPatternGoal)) < 2|| //reached wander goal           
            (this.moveCheckTimer %10 == 0 && !(groundCheck = this.checkForGround())) // we hit an edge
          )
        {
           //============ Roll a new goal ======================             
            //roll a new goal           
            float newPatternGoal = ((float)Math.random() * self.getWanderDistance()/2 *(Math.random() > .5f? 1: -1));
            this.currentPatternGoal = newPatternGoal;
                         
            //special handling for edge detection
            if(groundCheck == false)
            {
                self.turnAround();
                
                //if we are still moving toward the same edge, negate our wander goal
                if((self.getFacingDirection().value > 0 && (holdingPoint.x +this.currentPatternGoal < self.getPosition().x))||
                   (self.getFacingDirection().value < 0 && (holdingPoint.x + this.currentPatternGoal > self.getPosition().x)) 
                  )    
                {
                    this.currentPatternGoal = - this.currentPatternGoal;
                }   
                
                
            }

        }
        else
        {
            //============ move towards current goal ================
            if(self.getCombatData().canMove())
            {
                SylverVector2f point = new SylverVector2f(holdingPoint.x + currentPatternGoal, 0);
                this.moveTowardsPoint(point,false);
            }
                              
        }


    }
    
    /**
     * Checks for ground infront of NPE, returns True if there is walkable ground
     * @return True if there is ground ahead
     */
    protected boolean checkForGround()
    {       
        //build feeler box
        Box feelerBox = new Box(5,120);
        Body feelerBody = new StaticBody(feelerBox);
        feelerBody.setBitmask(Entity.BitMasks.COLLIDE_WORLD.value);
        
        //position feeler       
        float xPosition = self.getPosition().x +  self.getFacingDirection().value *(self.getWidth()/2 + 20);
        float yPosition = self.getPosition().y - self.getHeight()/2 ;       
        feelerBody.setPosition(xPosition, yPosition);
        
        //debug image
        if(GameplaySettings.getInstance().debugEnemies)
        {
            Image image = new Image("black.png");
            image.setAnchor(Anchorable.Anchor.CENTER);
            image.setDimensions(5,120);
            image.setPositionAnchored(xPosition, yPosition);
            image.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 15, 0, 0)); 
            self.getOwningScene().add(image, Scene.Layer.MAIN);
        }
        //get list of terrain
        ArrayList<SceneObject> terrain = self.getOwningScene().getSceneObjectManager().get(ExtendedSceneObjectGroups.TERRAIN);
        
        //collide it
        for(SceneObject object: terrain)
        {
            //if it a reasonable distance from us
            if(object.getPosition().distance(self.getPosition()) <1600)
            {
                WorldObjectEntity blocker = (WorldObjectEntity)object;

                Contact[] contacts = new Contact[10];
                for (int i=0;i<10;i++) {
                        contacts[i] = new Contact();
                }
                int numContacts =Collide.collide(contacts,blocker.getBody() , feelerBody, 1);

                if(numContacts > 0)
                {
                    return true;
                }
            }
        }
           
        return false;
               
    }
    
    /**
     * Checks for a wall infront of NPE, returns True if there is a wall
     * @return True if there is a wall ahead
     */
    protected boolean checkForWall()
    {       
        //build feeler box
        Box feelerBox = new Box(65,30);
        Body feelerBody = new StaticBody(feelerBox);
        feelerBody.setBitmask(Entity.BitMasks.COLLIDE_WORLD.value);
        
        //position feeler       
        float xPosition = self.getPosition().x +  self.getFacingDirection().value *(self.getWidth()/2 + 10);
        float yPosition = self.getPosition().y + self.getHeight()/2 + 20;       
        feelerBody.setPosition(xPosition, yPosition);
        
        //debug image
        if(GameplaySettings.getInstance().debugEnemies)
        {
            Image image = new Image("black.png");
            image.setDimensions(65,30);
            image.setAnchor(Anchorable.Anchor.CENTER);
            image.setPositionAnchored(xPosition, yPosition);
            image.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 15, 0, 0)); 
            self.getOwningScene().add(image, Scene.Layer.MAIN);
        }
        //get list of terrain
        ArrayList<SceneObject> terrain = self.getOwningScene().getSceneObjectManager().get(ExtendedSceneObjectGroups.TERRAIN);
        
        //collide it
        for(SceneObject object: terrain)
        {
            //if it a reasonable distance from us
            if(object.getPosition().distance(self.getPosition()) <1600)
            {
                WorldObjectEntity blocker = (WorldObjectEntity)object;

                Contact[] contacts = new Contact[10];
                for (int i=0;i<10;i++) {
                        contacts[i] = new Contact();
                }
                int numContacts =Collide.collide(contacts,blocker.getBody() , feelerBody, 1);

                if(numContacts > 0)
                {
                    return true;
                }
            }
        }
           
        return false;
               
    }
    
}
