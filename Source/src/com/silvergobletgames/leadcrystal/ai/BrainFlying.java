
package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Mike
 */
public abstract class BrainFlying extends Brain {
    
    
    //========Wander Variables===============
    protected SylverVector2f currentWanderGoal;
    
    //========Patrol Variables ==============
    protected SylverVector2f currentPatrolGoal;

    
    //======== Holding Pattern Variables ==========
    protected float currentPatternGoal;
    
    
    //====================
    // Movement Methods
    //====================
    
    public void moveTowardsPoint(SylverVector2f destinationPoint)
    {
               
        //face
        if (destinationPoint.x < self.getPosition().x)          
            self.face(Entity.FacingDirection.LEFT);           
        else            
            self.face(Entity.FacingDirection.RIGHT);
        
        //set image angle
        
        //get direction to move
        float dx = destinationPoint.getX() - self.getPosition().getX();
        float dy = destinationPoint.getY() - self.getPosition().getY();
        SylverVector2f directionToMove = new SylverVector2f(dx,dy);
        
        //==============
        //Enemy Spacing
        //==============
        
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
                ehh.scale(enemy.distanceAbs(self)/10);
                adjustmentVector.add(ehh);
            }
        }      
        
        //apply adjustment vector for enemy spacing to directionToMove vector
        directionToMove.normalise();
        directionToMove.scale(10);
        directionToMove.add(adjustmentVector);
        
        //move
        self.move(directionToMove);
    }
    
    public void flee(Entity target)
    {
       if (self.getCombatData().canMove() && target != null)
       {
            
            //get direction to move
            SylverVector2f vectorToTarget = self.distanceVector(target);
            vectorToTarget= vectorToTarget.negate();
            
            //get a point in the distance
            SylverVector2f point = self.getPosition();
            point.add(new SylverVector2f(vectorToTarget));

            //set image angle

            //move
            this.moveTowardsPoint(point); 
       }
    }
    
    /**
     * Wanders around within 400 units of start of wandering. Wandering has edge detection
     */
    public void wander(SylverVector2f wanderPoint)
    {
        //decide if we need a new goal
        if(
            this.currentWanderGoal == null || //dont have a goal yet
            (self.getPosition().distance(wanderPoint) >= self.getWanderDistance() && self.getPosition().distance(wanderPoint) > this.currentWanderGoal.distance(wanderPoint) ) || //at max wander distance and current goal wont get us closer
            self.getPosition().distance(currentWanderGoal) < 10 //reached wander goal
           )
        {
            //generate a new goal
            Random r = SylverRandom.random;
            float x = wanderPoint.x + r.nextInt((int)self.getWanderDistance() +1) * (Math.random() > .5f ? 1: -1);
            float y = wanderPoint.y + r.nextInt((int)self.getWanderDistance()/4 +1) * (Math.random() > .5f ? 1: -1);
            this.currentWanderGoal = new SylverVector2f(x,y);
        }
        else //if we dont need a new goal
        {
            this.moveTowardsPoint(this.currentWanderGoal);
        }
    }
            
    /**
     *  Patrols back and forth in a rigid pattern. Has edge detection.
     */
    public void patrol(SylverVector2f patrolPoint)
    {
        
        
    }
    
    /**
     * A tighter wandering type pattern meant for in combat movement.
     */
    public void holdingPattern(SylverVector2f holdingPoint)
    {
         //decide if we need a new goal
        if(
            this.currentWanderGoal == null || //dont have a goal yet
            (self.getPosition().distance(holdingPoint) >= self.getWanderDistance() && self.getPosition().distance(holdingPoint) > this.currentWanderGoal.distance(holdingPoint) ) || //at max wander distance and current goal wont get us closer
            self.getPosition().distance(currentWanderGoal) < 10 //reached wander goal
           )
        {
            //generate a new goal
            Random r = SylverRandom.random;
            float x = holdingPoint.x + r.nextInt((int)self.getWanderDistance() +1) * (Math.random() > .5f ? 1: -1);
            float y = holdingPoint.y + r.nextInt((int)self.getWanderDistance()/4 +1) * (Math.random() > .5f ? 1: -1);
            this.currentWanderGoal = new SylverVector2f(x,y);
        }
        else //if we dont need a new goal
        {
            this.moveTowardsPoint(this.currentWanderGoal);
        }
    }
}
