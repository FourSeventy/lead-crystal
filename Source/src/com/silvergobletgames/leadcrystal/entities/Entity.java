package com.silvergobletgames.leadcrystal.entities;


import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectClasses;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.scenes.GameScene;
import com.silvergobletgames.leadcrystal.scripting.ScriptObject;
import com.silvergobletgames.leadcrystal.scripting.ScriptObject.ScriptTrigger;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.core.SceneObjectManager;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.AnimationPack.ImageAnimation;
import com.silvergobletgames.sylver.netcode.NetworkedSceneObject;
import com.silvergobletgames.sylver.netcode.SaveData;
import com.silvergobletgames.sylver.util.SerializableEntry;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import javax.media.opengl.GL2;
import javax.media.opengl.GL3bc;
import net.phys2d.math.ROVector2f;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.*;

/**
 * An Entity is a heavyweight SceneObject that is effected by physics, has an Image, and optional lights and emitters.
 * @author mike
 */
public class Entity extends SceneObject implements AnimationListener 
{
    //The name of the Entity (In English. e.g. Puke Maw, Suspicious Box, Dank Cellar)
    private String name;
    //the physics body
    protected Body body;
    //the entities image
    protected Image image;
    //image offset vector
    protected Vector2f imageOffset = new Vector2f(0,0);
    //the entities light
    protected LightSource light;
    protected float lightDirectionOffset;
    //the entities emitters
    //-emitters get added to the scene along with the entity, and are reponsible for removing themselves
    protected ArrayList<AbstractParticleEmitter> emitters = new ArrayList<>();
    private HashMap<AbstractParticleEmitter,Float> emitterAngleOffset = new HashMap();
    //The entity's script object
    protected ScriptObject scriptObject; 
    //the entitys personal Healthbars/Nametext/etc
    protected EntityTooltip entityTooltip;
    //any entityEffects that we might have
    protected HashMap<String,EntityEffect> entityEffects = new HashMap();
     
    //The entity's target and last target
    protected Entity currentTarget = null;
    protected Entity previousTarget = null;
    //The list of things targeting this entity
    protected HashSet<Entity> targetingMe = new HashSet();
    
    //facing variables
    private FacingDirection facing = FacingDirection.RIGHT;
    
    //lock image to body
    private boolean lockImageToBody = true;
    
    
    /**
     * Enumerated bitmasks for entity bodies.  
     * If body1.bitmask & body2.bitmask == 0, there will be a collision.
     * This means that if both bitmasks have a 1 in the same digit, there will not be a collision.
     */
    public static enum BitMasks 
    {
        //WARNING - Changing the names of this enum will break save data
        PLAYER          (0b0_0000_0000_0001),
        NPE             (0b0_0000_0000_0010), 
        DESTRUCTIBLE    (0b0_0000_0000_0100), 
        COLLIDE_WORLD   (0b0_0000_0111_1111),
        WORLD_OBJ       (0b0_0000_1000_0000),
        COLLIDE_ALL     (0b0_0000_0000_0000),
        NO_COLLISION    (0b1_1111_1111_1111); //no collision
        public long value;

        BitMasks(long b) {
            value = b;
        }

        public static BitMasks valueToEnum(long value) {
            BitMasks[] values = BitMasks.values();

            for (BitMasks bm : values) {
                if (bm.value == value) {
                    return bm;
                }
            }
            return null;
        }
    }

    /**
     * Enumerated overlap masks for entity bodies.  
     * If body1.bitmask & body2.bitmask != 0, there will be a non-physics collision.  There will
     * also be NO physics collision (even if bitmasks say so)
     * (They share a common '1')
     */
    public static enum OverlapMasks 
    {
        //WARNING - Changing the names of this enum will break save data
        NO_OVERLAP        (0b00_0000), //DEFAULT
        PLAYER            (0b01_0001),
        PLAYER_TOUCH      (0b00_0001), 
        PVP_PLAYER        (0b11_0001),
        NPE               (0b10_0100),
        NPE_TOUCH         (0b10_0000),
        ITEM              (0b01_0000),
        OVERLAP_ALL       (0b11_1111); //overlaps all
        
        
        public long value;

        OverlapMasks(long b) {
            value = b;
        }

        public static OverlapMasks valueToEnum(long value) {
            OverlapMasks[] values = OverlapMasks.values();

            for (OverlapMasks bm : values) {
                if (bm.value == value) {
                    return bm;
                }
            }
            return null;
        }
    }
    
    /**
     * Enumerated type to represent which direction an entity is facing
     */
    public static enum FacingDirection
    {
        RIGHT(1),
        LEFT(-1);
        
        public int value;
        
        FacingDirection(int value)
        {
            this.value = value;
        }
    }

    
    //===================
    // Constructors
    //===================   
    

    /**
     * Constructor for an entity with an Image and a Body
     *
     * @param image The sprite of this entity
     * @param body The body of this entity
     */
    public Entity(Image image, Body body) 
    {       
        //set default name
        name = "";
        
        //sets body
        this.body = body;       
        if(this.body != null)
        {                   
            //set a sanity value for max velocity 
            this.body.setMaxVelocity(1000, 1000);

            //make sure the body has a reference to the entity that contains it
            body.setUserData(this);
        }
        
        //set image
        this.setImage(image);       
        
    }

    
    //=====================
    // Scene Object Methods
    //=====================
     
    /**
     * Update the entity's image, and determine the correct way to face the image.
     */
    public void update()
    {
        //sets position to new body position
        if(body != null)
        {
            Vector2f physVector = (Vector2f)this.body.getPosition();
            super.setPosition(physVector.x,physVector.y);
        }
        //adds gear icon if entity is interactable
        if(this.scriptObject != null && this.scriptObject.getTrigger().equals(ScriptTrigger.RIGHTCLICK))
        {
            //if we dont already have the overlay add a new one
            if(!image.hasOverlay("interact"))
            {
                Image gearImage =new Image("gear2.png");
                gearImage.setAnchor(Anchorable.Anchor.CENTER);
                Overlay gear = new Overlay(gearImage);
                gear.setRelativePosition(.5f, 1.2f);
                gear.setRelativeSize(.125f);
                image.addOverlay("interact",gear );  
                
                //add overlay movement
                Object[] points = {1.2f,1.3f,1.2f};
                int[] durations = {60,60};
                MultiImageEffect bobEffect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
                bobEffect.setRepeating(true);
                image.addImageEffect(bobEffect);
            }
        }
        else
        {
            image.removeOverlay("interact");
        }
        
        //updates the image
        if (image != null) 
        {
            image.update();
            image.setPositionAnchored(this.getPosition().x + this.imageOffset.x,this.getPosition().y + this.imageOffset.y);  
            
            if(this.lockImageToBody)
            {
               image.setAngle((float) (this.body.getRotation() * 180 / Math.PI));
            }
        }
        
        //updates the emitters positions in the world
        ArrayList<AbstractParticleEmitter> emitterUpdateList = new ArrayList(emitters);
        for(AbstractParticleEmitter emitter : emitterUpdateList)
        {
            if (emitter.isFinished())
                emitters.remove(emitter);
            else 
            {
                emitter.setPosition(getPosition().x, getPosition().y);
                emitter.setAngle(emitterAngleOffset.get(emitter) + (float)(this.body.getRotation() * 180/ Math.PI)% 360 ); 
            }
        }
        
        //Updates the light
        if(light != null)
        {
            light.setPosition(getPosition().x,getPosition().y);
            light.update();
            light.setDirection(lightDirectionOffset + (float)(this.body.getRotation() * 180/ Math.PI)% 360);          
        }
        
        //Updates the script object
        if (scriptObject != null && owningScene instanceof GameScene)
            scriptObject.update();
        
        //updates the tooltip
        if(entityTooltip != null)   
        {
            entityTooltip.setPosition(this.getPosition().x, this.getPosition().y + this.getHeight()/2 + 10); 
            entityTooltip.update();
        }                      
        
        //updates effects
        HashMap<String,EntityEffect> updateList = new HashMap(this.entityEffects);
        for(EntityEffect effect: updateList.values())
        {
            effect.update();
        }
        
               
    }    
    
    /**
     * Requests that this entity draw itself.
     *
     * @param gl The graphics context.
     */
    public void draw(GL2 gl)
    {       
        
        //draws image
        if (image != null) 
            image.draw(gl);
        
        //draws entityTooltip
        if(entityTooltip != null && this.getOwningScene() instanceof GameScene &&((GameScene)this.getOwningScene()).getPlayer().getArmorManager().seeEnemyHealth.isMaxPoints())
            entityTooltip.draw(gl); 
        
        
        //===========
        // DEBUGGING
        //============
        
        //debug draw to see the wireframe of the body
        if(GameplaySettings.getInstance().bodyWireframe == true && this.body != null)
        {
            // <editor-fold defaultstate="collapsed" desc=" Body Wireframe Draw ">
            
            gl.glDisable(GL3bc.GL_TEXTURE_2D); 
            gl.glColor4f(0, 0, 1,1);
            
            if (this.body.getShape() instanceof Box)
            {
                Vector2f[] points = ((Box) this.body.getShape()).getPoints(new Vector2f(this.body.getPosition().getX(), this.body.getPosition().getY()), this.body.getRotation());
                
                gl.glLineWidth(2);
                
                gl.glBegin(GL3bc.GL_LINES);
                {
                  
                    gl.glVertex2f(points[0].x, points[0].y);
                    gl.glVertex2f(points[1].x, points[1].y);                    
                    
                    gl.glVertex2f(points[1].x, points[1].y);                    
                    gl.glVertex2f(points[2].x, points[2].y);
                    
                    gl.glVertex2f(points[2].x, points[2].y);
                    gl.glVertex2f(points[3].x, points[3].y);
                    
                    gl.glVertex2f(points[3].x, points[3].y);
                    gl.glVertex2f(points[0].x, points[0].y);                    
                    
                }
                gl.glEnd();
                
            }
            else if (this.body.getShape() instanceof Circle) 
            {

                float radius = this.getWidth() / 2;
                //gl.glLineWidth(1);
                gl.glBegin(GL3bc.GL_LINE_LOOP);
                for (int angle = 0; angle < 360; angle += 1) {
                    gl.glVertex2f((float) (this.body.getPosition().getX() + Math.sin(Math.toRadians(angle)) * radius), (float) (this.body.getPosition().getY() + Math.cos(angle * Math.PI / 180) * radius));
                    
                }
                gl.glEnd();
                
            } 
            else if (this.body.getShape() instanceof Polygon)
            {
                Vector2f[] points = ((Polygon) this.body.getShape()).getVertices(new Vector2f(this.body.getPosition().getX(), this.body.getPosition().getY()), this.body.getRotation());
                gl.glBegin(GL3bc.GL_LINES);
                {
                    for(int i = 0; i < points.length; i ++)
                    {
                        if(i == points.length -1)
                        {
                            gl.glVertex2f(points[i].x, points[i].y);
                            gl.glVertex2f(points[0].x, points[0].y);
                        }
                        else
                        {
                            gl.glVertex2f(points[i].x, points[i].y);
                            gl.glVertex2f(points[i +1].x, points[i+1].y);                    
                        }                       
                    }
                }
                gl.glEnd();                
            }
            
            gl.glColor4f(1, 1, 1,1);
            

// </editor-fold>            
        }
    }

    /**
     * Set the position of this entity.
     *
     * @param xPosition The new x position for the sprite.
     */
    public void setPosition(float x, float y) 
    {
        
        super.setPosition(x,y);
        
        //set body position
        body.setPosition(x, y);
        
        //set image position
        if(image != null)           
            image.setPositionAnchored(x + this.imageOffset.x, y+ this.imageOffset.y);                     
                        
        
        //set light position
        if(light != null)             
            light.setPosition(x, y);
        
        //set praticle emitter positions
        for(AbstractParticleEmitter emitter: emitters)     
            emitter.setPosition(getPosition().x, getPosition().y);
        
        //set tooltip position
        if(this.entityTooltip != null)
            entityTooltip.setPosition(x, y + this.getHeight()/2 + 10);
        
    }
    
    public void addedToScene()
    {
        //add emitters to the scene
        for(AbstractParticleEmitter emitter: emitters) 
        {
            owningScene.add(emitter,Layer.MAIN);
        }
        
        //add light to scene
        if(this.light != null)
            owningScene.add(this.light, Layer.MAIN);
    }
    
    public void removedFromScene()
    {
        //tell the emitters to stop emitting 
        for(AbstractParticleEmitter emitter:emitters)
        {
            emitter.stopEmittingThenRemove();            
        }   
        
        //remove light
        if(this.light != null)
        {
            owningScene.remove(this.light);
        }
    }
   
    
    //==================
    // Class Methods
    //==================
    
    /**
     * Removes entity from whatever scene it belongs to
     */
    public void removeFromOwningScene()
    {
        if(owningScene != null)
            owningScene.remove(this);           
    }

    /**
     * Distance between two entities in absolute form
     * @param other Entity to judge distance from
     */
    public float distanceAbs(Entity other) 
    {      
        return this.body.getPosition().distance(other.body.getPosition());
    }

    /**
     * Distance between two entities in vector form
     * @param other Entity to judge distance from
     */
    public SylverVector2f distanceVector(Entity other) {
        float dx = other.body.getPosition().getX() - this.body.getPosition().getX();
        float dy = other.body.getPosition().getY() - this.body.getPosition().getY();
        return new SylverVector2f(dx, dy);
    }
    
    /**
     * Base definition for finishedAnimating.
     * @param animation 
     */
    public void finishedAnimating(Image image,ImageAnimation animation)
    {
    }
    
    /**
     * Notification that this entity collided with another.
     * sends the object that was collided, and the collision event.
     *
     * @param other The entity with which this entity collided.
     */
    public void collidedWith(Entity other, CollisionEvent event)
    {
        //trigger any scripts
        if(this.scriptObject != null && other instanceof PlayerEntity)
        {
            
            //if trigger is on player collide, run the script
            if (scriptObject.getTrigger() == ScriptTrigger.COLLIDE)
                scriptObject.runScript(other);  
            
        }
               
    }
    
    public void separatedFrom(Entity other, CollisionEvent event)
    {       
        // SEPERATE_PLAYER trigger
        if (scriptObject != null && other instanceof PlayerEntity)
        {
           //if the trigger is separate
            if (scriptObject.getTrigger() == ScriptTrigger.SEPERATE)
                    scriptObject.runScript(other);
            
        }
    }
    
    public void addEntityEffect(EntityEffect effect)
    {
        //add effect to the list
        this.addEntityEffect(Integer.toString(effect.hashCode()),effect);
              
    }
    
    public void addEntityEffect(String key, EntityEffect effect)
    {
        //add effect
        this.entityEffects.put(key,effect);
        
        //set owning entity
        effect.setOwningEntity(this);
        
        //notify it that it was applied
        effect.onApply();
    }
    
    public void removeEntityEffect(EntityEffect effect)
    {
        //remove the effect from the list
        this.entityEffects.values().remove(effect);
        
        //notify the effect that it was removed
        effect.onRemove();
    }
    
    public void removeEntityEffect(String key)
    {
        EntityEffect effect = this.entityEffects.remove(key);
        
        if(effect != null)
            effect.onRemove();
    }
    
    public void setLockImageToBody(boolean value)
    {
        this.lockImageToBody = value;
    }
    
    
    //=====================
    // Facing Functionality
    //=====================
 
    /**
     * Makes the entity do an about face.
     * 
     * TURN AROUND BRIGHT EYEEEESSS (SHIT!) TURN AROUND!
     */
    public void turnAround() 
    {
        if(this.facing == FacingDirection.LEFT)
            this.face(FacingDirection.RIGHT);
        else
            this.face(FacingDirection.LEFT);
    }

    /**
     * Called as such:
     * this.face(LEFT); etc.
     * @param dir 
     */
    public void face(FacingDirection direction) 
    {
        //if the direction isnt fixed
        if (this.image != null) 
        {
            if(direction.equals(FacingDirection.RIGHT))
                image.setHorizontalFlip(false);
            else
                image.setHorizontalFlip(true);          
        }
        
        //set the facing direction
        this.facing = direction;
    }

    /**
     * Face your current target if it exists.  Otherwise do nothing
     */
    public void faceTarget() 
    {
        Entity target = this.getTarget();
        if (target != null) 
        {
            if (this.distanceVector(target).x < 0)            
                this.face(FacingDirection.LEFT);
             else 
                this.face(FacingDirection.RIGHT);           
        }
    }
    
    public FacingDirection getFacingDirection()
    {
        return this.facing;
    }

    

    //=====================
    // Acessor Methods
    //=====================
      
    /**
     * Gets the body of the entity
     * @return the body of the entity
     */
    public final Body getBody() {
        return body;
    }

    public final void setBody(Body body) {
        //gets rid of old reference
        if (this.body != null) {
            this.body.setUserData(null);
        }

        //give new body reference to the entity
        this.body = body;
        this.body.setUserData(this);
    }

    public final Image getImage() {
        return this.image;
    }

    public final void setImage(Image i){
        //remove listener from old image
        if (this.image != null) {
            this.image.removeAnimationListener(this);
        }

        //set new image and set listener
        this.image = i;
        this.image.addAnimationListener(this);
        this.image.setAnchor(Anchorable.Anchor.CENTER);
    }

    public final LightSource getLight() {
        return this.light;
    }

    public final void setLight(LightSource l)
    {
        //remove the old light from the scene
        if(this.light != null && this.owningScene != null)
        {
            this.owningScene.remove(this.light);
        }
        
        //set the light
        this.light = l;
        
        //if its not null set the direction offset 
        if(l != null)       
        {
            this.lightDirectionOffset = l.getDirection();
            
            //add new light to the scene
            if(this.owningScene != null)
                this.owningScene.add(this.light,Scene.Layer.MAIN);
        }
        
    }

    public final void addEmitter(AbstractParticleEmitter emitter) 
    {
        //add to local list
        this.emitters.add(emitter);
        
        //add the emitter to the offset map
        this.emitterAngleOffset.put(emitter, emitter.getAngle());
        
        //set the emitter position
        emitter.setPosition(this.getPosition().x, this.getPosition().y);
        
        //if we're already in a scene add it to the scene
        if(this.owningScene != null)
            owningScene.add(emitter,Layer.MAIN);
    }

    public final ArrayList<AbstractParticleEmitter> getEmitters() {
        return this.emitters;
    }

    public final ScriptObject getScriptObject() {
        return scriptObject;
    }

    public final void setScriptObject(ScriptObject s) 
    {
        scriptObject = s;
        
        if(s != null)
        {
            s.setOwningEnity(this);
            if(s.getTrigger() == ScriptTrigger.RIGHTCLICK)
            {
                this.addToGroup(ExtendedSceneObjectGroups.CLICKABLE); 
            }
            
            this.addToGroup(ExtendedSceneObjectGroups.SCRIPT); 
        }
    }

    public float getWidth() 
    {
        if (body.getShape() instanceof Box)      
            return ((Box) body.getShape()).getSize().getX();  
                
        else if (body.getShape() instanceof Circle)   
            return ((Circle) body.getShape()).getRadius() * 2;
        
        else if (body.getShape() instanceof ConvexPolygon)  
        {
            ROVector2f[] verts = ((ConvexPolygon)body.getShape()).getVertices();
            float max = -1000000f, min = 1000000f;
            for(ROVector2f vert: verts)
            {
                if(vert.getX() > max)
                    max = vert.getX();
                
                if(vert.getX() < min)
                    min = vert.getX();
            }
            
            return Math.abs(max - min);
        }
        
        else
            return 0;
    }

    public float getHeight() 
    {
        if (body.getShape() instanceof Box)      
            return ((Box) body.getShape()).getSize().getY();
         
        else if (body.getShape() instanceof Circle)      
            return ((Circle) body.getShape()).getRadius() * 2;
                
        else if (body.getShape() instanceof ConvexPolygon) 
        {
            ROVector2f[] verts = ((ConvexPolygon)body.getShape()).getVertices();
            float max = -1000000f, min = 1000000f;
            for(ROVector2f vert: verts)
            {
                if(vert.getY() > max)
                    max = vert.getY();
                
                if(vert.getY() < min)
                    min = vert.getY();
            }
            
            return Math.abs(max - min);
        }
        
        else     
            return 0;
        
    }

    public void setName(String s)
    {
        this.name = s;
        
        if(entityTooltip != null)
            entityTooltip.setNameField(name);
        
    }

    public String getName() 
    {
        return name;
    }
    
    public EntityTooltip getTooltip()
    {
        return this.entityTooltip;
    }
    
    protected void setTooltip(EntityTooltip tooltip)
    {
        this.entityTooltip = tooltip;
    }
    
    public void setImageOffset(Vector2f offset)
    {
        this.imageOffset = offset;
    }
    
    public Vector2f getImageOffset()
    {
        return this.imageOffset;
    }

    //===========================
    // Targeting Functionality
    //===========================
     
    /**
     * Target an entity
     * @param newTarget 
     */
    public void target(Entity newTarget)
    {
        if (newTarget == null)
        {
            this.clearTarget();
        }
        else
        {
            //change targets
            this.previousTarget = this.currentTarget;
            this.currentTarget = newTarget;

            //Tell the last target that i'm not targeting it anymore
            if (this.previousTarget != null)              
                this.previousTarget.setUntargetedBy(this);

            //Tell the new target that I am targeting it.
            if (this.currentTarget != null)               
                this.currentTarget.setTargetedBy(this);             
            
        }
    }
    
    /**
     * Versions returning entities
     */
    public final Entity getTarget() 
    {
        return currentTarget;
    }

    public final Entity getLastTarget() 
    {
        return previousTarget;
    }

    /**
     * Clears the target
     */
    public void clearTarget() 
    {
        if (this.currentTarget != null)
        {
            //I'm clearing my target, so let it know that I'm not targeting it 
            //any longer.
            this.currentTarget.setUntargetedBy(this);
            //Make my current target the new last target, gets from manager by ID to avoid reference problem
            this.previousTarget = currentTarget;
            //Set my current target to null
            this.currentTarget = null;
        }
        //Else, do nothing because we don't want to clear the lastTarget.
    }

    /**
     * Returns the list of things that are targeting me as an array.
     *
     */
    public Entity[] getTargetedBy() 
    {
        if (!targetingMe.isEmpty()) {
            Entity[] list = new Entity[targetingMe.size()];
            Iterator<Entity> iter = targetingMe.iterator();
            int cnt = 0;
            while (iter.hasNext()) {
                list[cnt] = iter.next();
                cnt++;
            }
            //Return the list
            return list;
        }
        //Else return an empty array
        return new Entity[0];
    }

    /**
     * Targets my last target.
     */
    public void targetLastTarget() 
    {
        target(previousTarget);
    }

    private void setTargetedBy(Entity e)
    {
        targetingMe.add(e);
    }

    /**
     * Calls when I am un-targeted by eID or an entity. Only called by other targeting functions  
     */
    private void setUntargetedBy(Entity e) 
    {
        targetingMe.remove(e);
    }
    

    
    //================
    // Saving Body
    //================
     
    public static SaveData dumpBodySavable(Body body) 
    {
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
        SaveData saveData = new SaveData();

        //figure out shape
        String shape;
        if(body.getShape() instanceof Box)
            shape = "box";
        else if(body.getShape() instanceof Polygon)
            shape = "polygon";
        else
            shape = "circle";
        
        //figure out dimensions
        float width, height;
        ROVector2f[] verts = new ROVector2f[0];
        if(body.getShape() instanceof Box)
        {
            width = ((Box)body.getShape()).getSize().getX();
            height = ((Box)body.getShape()).getSize().getY();
        }          
        else if(body.getShape() instanceof Polygon)
        {
            verts = ((Polygon)body.getShape()).getVertices();
            float max = -1000000f, min = 1000000f;
            for(ROVector2f vert: verts)
            {
                if(vert.getX() > max)
                    max = vert.getX();
                
                if(vert.getX() < min)
                    min = vert.getX();
            }
            
            width = Math.abs(max - min);
            
            verts = ((Polygon)body.getShape()).getVertices();
            max = -1000000f; min = 1000000f;
            for(ROVector2f vert: verts)
            {
                if(vert.getY() > max)
                    max = vert.getY();
                
                if(vert.getY() < min)
                    min = vert.getY();
            }
            
            height = Math.abs(max - min);
        }        
        else
        {
             width = ((Circle)body.getShape()).getRadius();
             height = 0;
        }
        
        ArrayList<SerializableEntry<Float,Float>> vertices = new ArrayList();
        for (ROVector2f vertex : verts)
        {
            vertices.add(new SerializableEntry(vertex.getX(), vertex.getY()));
        }
       
        saveData.dataMap.put("shape", shape); //bodys shape
        saveData.dataMap.put("width", width); //width or diameter
        saveData.dataMap.put("height", height); //height or nothing
        saveData.dataMap.put("angle", body.getRotation()); //angle
        saveData.dataMap.put("static", body instanceof StaticBody ? "static" : "dynamic"); //static/dynamic
        saveData.dataMap.put("bitmask", BitMasks.valueToEnum(body.getBitmask()).name()); //collision mask name
        saveData.dataMap.put("overlapMask", OverlapMasks.valueToEnum(body.getOverlapMask()).name()); //special tactics mask name
        saveData.dataMap.put("friction", body.getFriction()); //friction 
        saveData.dataMap.put("mass", body.getMass()); //mass
        saveData.dataMap.put("gravity", body.getGravityEffected()); //gravity effected
        saveData.dataMap.put("x", body.getPosition().getX()); // x position
        saveData.dataMap.put("y", body.getPosition().getY()); // y position
        saveData.dataMap.put("rotatable",body.isRotatable());
        saveData.dataMap.put("vertices", vertices); //vertices if polygon
        return saveData;
    }

    /** IMPORT FORMAT
     * 0 - BODY SHAPE (String)
     * 1 - WIDTH/DIAMETER (float)
     * 2 - HEIGHT/NULL (float)
     * 3 - ANGLE (float)
     * 4 - STATIC/DYANAMIC (string)
     * 5 - COLLISION MASK (long)
     * 6 - SPECIAL MASK (long)
     * 7 - FRICTION (float)
     * 8 - MASS (float)
     * 9 - GravityEffected (bool)
     * 10 - x position (float)
     * 11 - y position (float)
     * 12 - rotatable (boolean)
     * 
     * Creates a new world object from a savable data object containing information about this object
     * using the following index-object mapping (element 0 and 1 are reserved for classname and layer)
     */
    public static Body buildBodyFromSavedData(SaveData saved)
    {
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
        Body body;

        //get dimensions
        float dimension1 = (float) saved.dataMap.get("width"); //width or diameter    
        float dimension2 = (float) saved.dataMap.get("height"); //height or null
        float mass = (float) saved.dataMap.get("mass");

        //if its static or dynamic
        if (saved.dataMap.get("static").equals("static")) 
        {
            if (saved.dataMap.get("shape").equals("box")) 
            {
                body = new StaticBody(new Box(dimension1, dimension2));
            } 
            else if(saved.dataMap.get("shape").equals("triangle"))
            {
                Vector2f[] triangleVerts = { new Vector2f(-dimension1/2, -dimension2/2), new Vector2f(dimension1/2, -dimension2/2), new Vector2f(-dimension1/2, dimension2/2)  };  
                Polygon cp = new Polygon(triangleVerts);                    
                body = new StaticBody(cp);
            }
            else if(saved.dataMap.get("shape").equals(("polygon")))
            {           
                ArrayList<SerializableEntry<Float,Float>> vertices = (ArrayList)saved.dataMap.get("vertices");
                ArrayList<ROVector2f> vectorList = new ArrayList();
                for (SerializableEntry<Float,Float> vertex : vertices)
                {
                    vectorList.add(new Vector2f(vertex.getKey(), vertex.getValue()));
                }
                ROVector2f[] verts = new ROVector2f[vectorList.size()];
                verts = vectorList.toArray(verts);
                Polygon cp = new Polygon(verts);                    
                body = new StaticBody(cp);
            }
            else // its a circle
            {
                body = new StaticBody(new Circle(dimension1));
            }
        } 
        else //its dynamic
        {
            if (saved.dataMap.get("shape").equals("box")) 
            {
                body = new Body(new Box(dimension1, dimension2), mass);
            } 
            else if(saved.dataMap.get("shape").equals("triangle"))
            {
                Vector2f[] triangleVerts = { new Vector2f(-dimension1/2, -dimension2/2), new Vector2f(dimension1/2, -dimension2/2), new Vector2f(-dimension1/2, dimension2/2)  };  
                Polygon cp = new Polygon(triangleVerts);                    
                body = new Body(cp, mass);
            }
            else if(saved.dataMap.get("shape").equals(("polygon")))
            {           
                ArrayList<SerializableEntry<Float,Float>> vertices = (ArrayList)saved.dataMap.get("vertices");
                ArrayList<ROVector2f> vectorList = new ArrayList();
                for (SerializableEntry<Float,Float> vertex : vertices)
                {
                    vectorList.add(new Vector2f(vertex.getKey(), vertex.getValue()));
                }
                ROVector2f[] verts = new ROVector2f[vectorList.size()];
                verts = vectorList.toArray(verts);          
                Polygon cp = new Polygon(verts);                    
                body = new Body(cp, mass);
            }
            else//its a circle
            {
                body = new Body(new Circle(dimension1), mass);
            }
        }

        //set angle
        body.setRotation((float) saved.dataMap.get("angle"));
        //set collision mask
        String se = (String)saved.dataMap.get("bitmask");
        if(se.equals("PASS_THROUGH"))
            se = "NO_COLLISION";
        body.setBitmask(BitMasks.valueOf(se).value);
       // body.setBitmask(BitMasks.valueOf((String)saved.dataMap.get("bitmask")).value);
        //set overlap mask            
        body.setOverlapMask(OverlapMasks.valueOf((String)saved.dataMap.get("overlapMask")).value);
        //set friction
        body.setFriction((float) saved.dataMap.get("friction"));
        //set gravity effected
        body.setGravityEffected((boolean) saved.dataMap.get("gravity"));
        //set position
        body.setPosition((float) saved.dataMap.get("x"), (float) saved.dataMap.get("y"));
        //set rotatable
        body.setRotatable((boolean)saved.dataMap.get("rotatable")); 

        return body;
    }
       
}
