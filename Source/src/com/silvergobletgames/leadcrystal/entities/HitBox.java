package com.silvergobletgames.leadcrystal.entities;


import com.silvergobletgames.sylver.graphics.Image;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.CollisionEvent;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import net.phys2d.raw.Body;
import com.silvergobletgames.leadcrystal.scenes.GameScene;
import com.silvergobletgames.sylver.graphics.AnimationPack.ImageAnimation;


public class HitBox extends Entity 
{
    //the damage this hitbox will deal
    protected Damage damage;
    //the source of this hitbox
    protected Entity sourceEntity;
    
    
    //==================
    // Constructors
    //==================
    
    /**
     * A generic hitbox (eventually)
     * @param damage Damage that this projectile will deal out.
     * @param effects Effects that this projectile will apply.
     */
    public HitBox(Damage dmg, Body b, Image i, Entity sourceEntity)
    {
        super(i,b);
        
        body.setDamping(0);

        //set source
        this.sourceEntity = sourceEntity;
        
        //assigns damage that this projectile will give out
        this.damage = dmg;
        if (sourceEntity != null)
        {
            if (sourceEntity instanceof PlayerEntity) 
            { 
                //Player bullet
                body.setBitmask(BitMasks.COLLIDE_WORLD.value);
                body.setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value); //Overlaps/collides with enemy
                body.addExcludedBody(sourceEntity.getBody());
            }
            else //Enemy 
            {
                body.setBitmask(BitMasks.COLLIDE_WORLD.value);
                body.setOverlapMask(Entity.OverlapMasks.PLAYER_TOUCH.value); //Overlaps/collides with player
                body.addExcludedBody(sourceEntity.getBody());
                this.addToGroup(ExtendedSceneObjectGroups.ENEMYPROJECTILE);
            }
        }
        body.setGravityEffected(false);
    }

    
    //=====================
    // Class Methods
    //=====================
    
    public void collidedWith(Entity other, CollisionEvent event)
    {

    }

    public void update()
    {
        super.update();
        
        if (owningScene != null && sourceEntity != null) 
        {
            //Get rid of this if its really far away
            if (this.distanceAbs(sourceEntity) > 3000) 
            {
                removeFromOwningScene();
            }
        }
    }

    

    
    //=====================
    // Accessor Methods
    //=====================
    
    public final void setSource(Entity e){
        this.sourceEntity = e;
    }
    
    public Entity getSource(){
        return sourceEntity;
    }
    
    public void setDamage(Damage d) {
        this.damage = d;
    }
    
    public Damage getDamage() {
        return this.damage;
    }
    
}
