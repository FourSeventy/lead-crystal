/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silvergobletgames.leadcrystal.items;

import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.ItemEntity;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.util.SylverRandom;
import java.util.Random;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;

/**
 *
 * @author Mike
 */
public class LootSpewer extends Entity {
    
    private int lootTicks = 0;
    
    
    //==============
    //Constructor
    //=============
    
    public LootSpewer(int lootTicks)
    {
        super(new Image("blank.png"), new StaticBody(new Box(10,10)));
        this.getBody().setOverlapMask(Entity.OverlapMasks.OVERLAP_ALL.value);
        this.getBody().setBitmask(Entity.BitMasks.NO_COLLISION.value);
        
        this.lootTicks = lootTicks;
    }
    
    
    //==============
    // Methods
    //==============
    @Override
    public void update()
    {
        super.update();
        
        //see if we need to remove 
        this.lootTicks--;
        if(this.lootTicks == 0)
        {
            this.removeFromOwningScene();
            return;
        }
        
        // spew loot!!
        if(this.lootTicks % 7 == 0)
        {
            this.spewLoot();
        }
            
    }
    
    
    private void spewLoot()
    {
        ItemEntity drop = new Currency(1);
        
        drop.setPosition(this.getPosition().x, this.getPosition().y);
        Random r = SylverRandom.random;
        short sign = 1;
        if (Math.random()<.5)
            sign = -1;
        Vector2f force = new Vector2f( 500 + sign*(r.nextFloat()*1500), 2000 + r.nextFloat()*5000 );
        drop.getBody().addForce(force);
                
        this.getOwningScene().add(drop, Scene.Layer.MAIN); 
    }
    
}
