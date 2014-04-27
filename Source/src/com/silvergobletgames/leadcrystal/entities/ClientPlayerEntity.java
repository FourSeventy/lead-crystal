package com.silvergobletgames.leadcrystal.entities;

import com.jogamp.newt.event.KeyEvent;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderDataChanges;
import java.util.ArrayList;
import java.util.Arrays;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.netcode.ClientInputPacket;
import com.silvergobletgames.leadcrystal.netcode.PlayerPredictionData;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.leadcrystal.skills.PlayerDashAttack;
import com.silvergobletgames.leadcrystal.skills.Skill;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;
import com.silvergobletgames.sylver.core.InputSnapshot;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.AnimationPack.CoreAnimations;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.security.InvalidParameterException;
import net.phys2d.raw.shapes.Box;


public class ClientPlayerEntity extends PlayerEntity
{
    
    
    
    //===============
    // Constructor
    //===============
    public ClientPlayerEntity(PlayerEntity player)
    {
        super(player.getImage().copy(),player.head.copy(),player.backArm.copy(),player.frontArm.copy());
        
        this.ID = player.getID();
        this.setName(player.getName()); 
        
        this.combatData =player.getCombatData();
        this.potionManager = player.getPotionManager();
        this.currencyManager = player.getCurrencyManager();
        this.armorManager = player.getArmorManager();
        this.levelProgressionManager = player.getLevelProgressionManager();
        this.skillManager = player.getSkillManager();
        this.MAX_JUMP_ENERGY = player.MAX_JUMP_ENERGY;
        
        //skills
        this.skill1 = player.skill1;
        this.skill2 = player.skill2;
        this.skill3 = player.skill3;
        this.skill4 = player.skill4;       
        
    }
    
    
    //====================
    // Overridden Methods
    //====================
    public void update()
    {
        //sets position to new body position
        if(body != null)
        {
            Vector2f physVector = (Vector2f)this.body.getPosition();
            this.position.set(physVector.x,physVector.y);
        }
        
        //updates the image
        if (image != null) 
        {
            image.setPositionAnchored(this.getPosition().x + this.imageOffset.x, this.getPosition().y + this.imageOffset.y);
            image.setAngle((float) (this.body.getRotation() * 180 / Math.PI));
        }
        
        //updates the emitters positions in the world
        for(AbstractParticleEmitter emitter: emitters)
        {
            emitter.setPosition(getPosition().x, getPosition().y);
        }
        
        //update velocity
        body.setSoftMaxVelocity(combatData.xVelocity.getTotalValue(), combatData.yVelocity.getTotalValue());                           
       
        //position flashlight
        this.light.setPosition((int) this.body.getPosition().getX() + 20, (int) this.body.getPosition().getY());
        
        //face toward mouse
        if(this.owningScene instanceof GameClientScene)
            this.light.faceTowardsPoint(((GameClientScene)owningScene).worldMouseLocation);      
        
        //check if in air
         if(this.body.getTouching().size() == 0)         
             this.feetOnTheGround = false;    
                
         //if our feet arent on the ground increment in air timer
         if(!this.feetOnTheGround)
             this.inAirTimer++;
         
         //clear jump energy if we are in air for too long
        if(this.inAirTimer > 15 && this.jumpEnergy == this.MAX_JUMP_ENERGY)
            this.jumpEnergy = 0;
               
         
         //update ladder settings         
         if(this.onLadder)
         {
             this.body.setGravityEffected(false);
             this.combatData.yVelocity.setBase(this.BASE_PLAYER_VELOCITY.x); 
             this.body.setDamping(2);
         }
         else
         {
             this.body.setGravityEffected(true);
             this.combatData.yVelocity.setBase(this.BASE_PLAYER_VELOCITY.y); 
             this.body.setDamping(this.BASE_DAMPING);
         }
         
         //update skill manager
         skillManager.update();       
         
         //update body parts
        this.frontArm.update();
        this.backArm.update();
        this.head.update();
        
        this.setCorrectAnimation();
     
    }
    
    public void reconcileRenderDataChanges(long lastTime,long futureTime,SceneObjectRenderDataChanges renderDataChanges )
    {  
        int fieldMap = renderDataChanges.fields;
        ArrayList rawData = new ArrayList();
        rawData.addAll(Arrays.asList(renderDataChanges.data)); 
        
        //construct an arraylist of data that we got, nulls will go where we didnt get any data
        ArrayList changeData = new ArrayList();
        for(int i = 0; i <16; i ++)
        {
            // The bit was set
            if ((fieldMap & (1L << i)) != 0)
            {
                changeData.add(rawData.get(0));
                rawData.remove(0);
            }
            else
                changeData.add(null);          
        }
        
        if(this.image != null && changeData.get(0) != null)
        {     
            //Get the changes and make the raw data in a temp list
            SceneObjectRenderDataChanges imageChanges = (SceneObjectRenderDataChanges)changeData.get(0);
            ArrayList raw = new ArrayList();
            raw.addAll(Arrays.asList(imageChanges.data)); 
                                    
            int changeCount = 0;
            for (byte b = 0; b < 25; b++)
            {
                //If there is a one present
                if ((imageChanges.fields & (1 << b)) != 0)
                {
                    //Remove particular ones.  Decrement the index as well.
                    if (b == 3 || b == 4 || b == 5 || b == 14) //X, Y, Angle, Animation
                    {
                        imageChanges.fields -= 1L << b;
                        raw.remove(changeCount);
                        changeCount--;
                    }
                    //Add one so that we know we had an element, regardless of whether we removed it.
                    changeCount++;
                }
                
            }
  
            imageChanges.data = raw.toArray();
            
            this.image.reconcileRenderDataChanges(lastTime, futureTime, imageChanges);
        }
        
//        if(this.light != null && changeData.get(1) != null)
//            this.light.reconcileRenderDataChanges(lastTime,futureTime,(SceneObjectRenderDataChanges)changeData.get(1));
        
        if(changeData.get(2) != null)
            this.combatData.reconcileRenderDataChanges(lastTime,futureTime,(SceneObjectRenderDataChanges)changeData.get(2));
        
        if(changeData.get(3) != null)
            this.skillManager.reconcileRenderDataChanges(lastTime,futureTime,(SceneObjectRenderDataChanges)changeData.get(3));
        
        if(changeData.get(4) != null)
            this.currencyManager.reconcileRenderDataChanges(lastTime, futureTime, (SceneObjectRenderDataChanges)changeData.get(4));       
        
        if(changeData.get(5) != null)
            this.armorManager.reconcileRenderDataChanges(lastTime,futureTime,(SceneObjectRenderDataChanges)changeData.get(5));
        
        if(changeData.get(6) != null)
           this.levelProgressionManager.reconcileRenderDataChanges(lastTime, futureTime, (SceneObjectRenderDataChanges)changeData.get(6));
        
        if(changeData.get(15) != null)
        {
            this.MAX_JUMP_ENERGY = (float)changeData.get(15);
        }
        
        if(this.potionManager != null && changeData.get(13) != null)
            this.potionManager.reconcileRenderDataChanges(lastTime,futureTime,(SceneObjectRenderDataChanges)changeData.get(13));
    }
    
    public void reconcilePredictionData(PlayerPredictionData old, PlayerPredictionData server)
    {     
        
        //adjust position
        float serverX,serverY;  
        serverX = (float)server.positionX;
        serverY = (float)server.positionY;        
        //this.body.setPosition(serverX,serverY);
        this.setPosition(serverX, serverY); 
                 
        //adjust x velocity
        if(old.velocityX != server.velocityX )
        {
            this.body.setVelocity(new Vector2f(server.velocityX,this.body.getVelocity().getY()));
        }
        
        // adjust y velocity
        if(old.velocityY != server.velocityY)
        {
            this.body.setVelocity(new Vector2f(this.body.getVelocity().getX(),server.velocityY));
        }     
        
    }
    
    public void simulateInputForPredictionErrorCorrection(ClientInputPacket input)
    {
        InputSnapshot snapshot = input.getInputSnapshot();
        
        //move left
        if(snapshot.isKeyPressed(KeyEvent.VK_A))
        {
            this.move(FacingDirection.LEFT);
        }
        
        //move right
        if(snapshot.isKeyPressed(KeyEvent.VK_D))
        {
            this.move(FacingDirection.RIGHT);
        }

        //jump
        if(snapshot.isKeyPressed(KeyEvent.VK_SPACE) == true)
        {
            this.handleJumping(input.mouseLocationX,input.mouseLocationY);
        }

        //jump released
        if(snapshot.isKeyReleased(KeyEvent.VK_SPACE) == true)
        {
            this.handleJumpReleased();
        }

        //down
        if(snapshot.isKeyPressed(com.jogamp.newt.event.KeyEvent.VK_S) == true)
        {
            this.move(new SylverVector2f(0,-1));
        }

        //up
        if(snapshot.isKeyPressed(com.jogamp.newt.event.KeyEvent.VK_W) == true)
        {
            this.move(new SylverVector2f(0,1));
        }

        //sprint
        if(snapshot.isKeyPressed(KeyEvent.VK_SHIFT))
        {
            this.handleSprint();
        }
        if(snapshot.isKeyReleased(KeyEvent.VK_SHIFT))
        {
            this.handleSprintReleased();
        }
        
        if(this.dashing)
        {
            this.handleDash(dashVector);
        }
    }
    
    public void useActionBarSkill(SkillID skillToUse)
    {       
        //get the skill we are using
        Skill skill =skillManager.getSkill(skillToUse); 
        
        //switch its animation
        if(skill != null && skill.isUsable() && this.combatData.canAttack() && !this.inAttackAnimation())
        {
            //change animation
            this.getFrontArm().setAnimation(skill.getImageAnimation());
            this.getBackArm().setAnimation(skill.getImageAnimation());
            
            //start cooldown
            skill.beginCooldown();
            
            //dash
            if(skill instanceof PlayerDashAttack)
            {
                //Get target X and Y
                float targetX = ((GameClientScene)this.getOwningScene()).worldMouseLocation.x;
                float targetY = ((GameClientScene)this.getOwningScene()).worldMouseLocation.y;

                //Get user X and Y
                float userX = this.getPosition().x;
                float userY = this.getPosition().y;

                //get vector to target
                SylverVector2f vectorToTarget = new SylverVector2f(targetX - userX, targetY - userY);
                vectorToTarget.normalise();
                this.handleDash(vectorToTarget);
            }
        }
        
        

        
    }
    
    public void interpolate(long currentTime)
    {
    }
    
    public void setSkillAssignment(SkillID skill, int number)
    {
        switch(number)
        {
            case 1:  this.skill1 = skill; ((GameClientScene)this.owningScene).sendSkillPacket(); break; 
            case 2:  this.skill2 = skill; ((GameClientScene)this.owningScene).sendSkillPacket(); break;
            case 3:  this.skill3 = skill; ((GameClientScene)this.owningScene).sendSkillPacket(); break;
            case 4:  this.skill4 = skill; ((GameClientScene)this.owningScene).sendSkillPacket(); break;
            default: throw new InvalidParameterException("Skill Assignment Request Out Of Bounds");
        }
        
        
    }
    
    

}
