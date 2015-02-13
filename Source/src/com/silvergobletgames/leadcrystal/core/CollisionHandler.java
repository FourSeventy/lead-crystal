package com.silvergobletgames.leadcrystal.core;

import java.util.ArrayList;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.CollisionListener;
import com.silvergobletgames.leadcrystal.entities.Entity;

public class CollisionHandler implements CollisionListener 
{
    private ArrayList<CollisionEvent> collisions = new ArrayList<>();
    private ArrayList<CollisionEvent> separations = new ArrayList<>();
    
    public void collisionOccured(CollisionEvent event)
    { 
       collisions.add(event);
    }
    
    public void separationOccured(CollisionEvent event)
    {
        separations.add(event);
    }
    
    public void resolveCollisions()
    {
        for(CollisionEvent event: collisions)
        {
            Entity a = (Entity)event.getBodyA().getUserData();
            Entity b = (Entity)event.getBodyB().getUserData();
            if (a.getOwningScene() != null && b.getOwningScene() != null)
            {
                a.collidedWith(b, event);
                
                Vector2f normal = new Vector2f(event.getNormal());
                normal =normal.negate();
                CollisionEvent event2 = new CollisionEvent(event.getTime(), event.getBodyA(), event.getBodyB(), event.getPoint(), normal, event.getPenetrationDepth());    
                b.collidedWith(a, event2);
            }
        }
        collisions.clear();
        
        for(CollisionEvent event: separations)
        {
            Entity a = (Entity)event.getBodyA().getUserData();
            Entity b = (Entity)event.getBodyB().getUserData();
            
            //notify entity a
            a.separatedFrom(b, event);              
          
            //notify entity b
            b.separatedFrom(a, event);
            
        }
        separations.clear();
    }
    
    public void clearCollisions()
    {
        collisions.clear();
        separations.clear();
    }
}
