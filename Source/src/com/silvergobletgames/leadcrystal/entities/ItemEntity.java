package com.silvergobletgames.leadcrystal.entities;

import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.graphics.ImageEffect.ImageEffectType;
import com.silvergobletgames.sylver.netcode.SavableSceneObject;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Box;
import com.silvergobletgames.leadcrystal.entities.EntityTooltip.EntityTooltipField;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.graphics.MultiImageEffect;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.ArrayList;


public abstract class ItemEntity extends Entity implements SavableSceneObject
{

    int dontFloatDelay = 120;
    //===============
    // Constructors
    //===============
    
    protected ItemEntity(Image image)
    {
        super(image,new Body(new Box(30,30),3f));
        
        //sets bitmasks and overlap masks
        body.setBitmask(BitMasks.COLLIDE_WORLD.value);
        body.setOverlapMask(OverlapMasks.ITEM.value);
        body.setRotatable(false);
        body.setFriction(2);
        
        //set up image size
        this.image.setDimensions(30, 30);
        
        //entity tooltip
        this.entityTooltip = new EntityTooltip(EntityTooltipField.NAME);
        
        //flash effect
        Float[] points = { 1f,1.5f,1f,1f};
        int[] durations = {15,15,300};
        MultiImageEffect flash = new MultiImageEffect(ImageEffectType.BRIGHTNESS, points, durations);
        flash.setRepeating(true);
        this.image.addImageEffect(flash);
    }
    
    //==============
    //Class Methods
    //==============

    public void update()
    {       
        super.update();
        
        if(this.dontFloatDelay > 0)
            this.dontFloatDelay--;
        
        //code to make the item move to the player if within a certain distance
        if (owningScene != null && this.dontFloatDelay <=0)
        {
            // get closest player
            float closestDistance = Float.MAX_VALUE;
            PlayerEntity closestPlayer = null;

            ArrayList<PlayerEntity> players = ((GameServerScene)this.getOwningScene()).getPlayers(); 
            for(PlayerEntity player: players)
            {
                if(this.distanceAbs(player) < closestDistance)
                {
                    closestDistance = this.distanceAbs(player);
                    closestPlayer = player;
                }
            }
        
            //hover toward closest player
            if(closestPlayer != null)
            {              
                if (closestDistance <= 200 )
                {

                    //get vector to player
                    SylverVector2f vector = this.distanceVector(closestPlayer);
                    vector.normalise();
                    vector.scale(100);

                    this.body.setVelocity(new Vector2f(vector.x,vector.y));                 
                }
            }
            
            

            
        }
    }

    public void collidedWith(Entity other, CollisionEvent event)
    {
        super.collidedWith(other, event);
        if (other instanceof PlayerEntity)
        {
            this.getImage().removeAllImageEffects();
        }
    }

      

}
