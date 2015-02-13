package com.silvergobletgames.leadcrystal.entities;

import com.silvergobletgames.leadcrystal.ai.AIState;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectClasses;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.core.SpawningEffectsFactory;
import com.silvergobletgames.leadcrystal.scenes.GameScene;
import com.silvergobletgames.leadcrystal.scenes.MapEditorScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.netcode.SavableSceneObject;
import com.silvergobletgames.sylver.netcode.SaveData;
import com.silvergobletgames.sylver.netcode.SceneObjectSaveData;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Random;
import javax.media.opengl.GL2;
import javax.media.opengl.GL3bc;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;

/**
 *
 * @author mike
 */
public class MobSpawner extends Entity implements SavableSceneObject 
{   
    //the mob to spawn
    public NonPlayerEntity mobToSpawn;
    
    //How long the mobs should take to spawn, in frames
    public int spawnTime = 60;

    //Over [spawnTime] number of frames, this many mobs will be copied.
    public int numberToSpawn = 1;

    //Max X and Y distance (absolute) from the spawner to spawn)
    public float spawnX=0;        
    public float spawnY=0;     
    
    //spawn on player collide
    public boolean spawnOnPlayerCollide = true;

    //helper variables
    private int spawnTimeCounter =1;
    private int remainingMobs;
    private boolean armed = true;

    //==============
    // Constructor  
    //==============
    
    public MobSpawner(NonPlayerEntity e,Body body)
    {
        super(e.getImage().copy(),body);
        
        this.image.setColor(new Color(1,1,1,.5f));
        this.mobToSpawn = e;

    }

    
    //===============
    // Class Methods
    //===============
    
    public void collidedWith(Entity other, CollisionEvent event)
    {
        super.collidedWith(other, event);
        
        if(other instanceof PlayerEntity && owningScene instanceof GameScene && this.spawnOnPlayerCollide)
        {   
            
            if(this.armed == true)  
            {
                //begin spawning
                this.beginSpawning();
                
                //spawn the first guy right away
                this.spawnMob();
                this.remainingMobs--;
            }
                           
        }
    }
    
    /**
     * Starts up a new spawn cycle with the current fields
     */
    public void beginSpawning()
    {
        //if we are armed spawn mobs
        if(armed == true)
        {

            //queue up the number of mobs to spawn
            this.remainingMobs = this.numberToSpawn;       

            //disarm the spawner so it wont spawn anymore
            this.armed = false;
        
        }
    }

    /**
     * Ends the current spawn cycle
     */
    public void endSpawning()
    {
        this.remainingMobs = 0;
    }

    /**
     * Creates a mob as a copy of mobToSpawn and puts it in the world
     */
    private void spawnMob()
    {
        //build the mob
        SceneObjectSaveData rawData = mobToSpawn.dumpFullData();
        NonPlayerEntity spawningMob = ((NonPlayerEntity)NonPlayerEntity.buildFromFullData(rawData));      

        //set position
        spawningMob.setPosition(this.spawnX , this.spawnY );


        //Bitmasks
        spawningMob.getBody().setBitmask(Entity.BitMasks.NPE.value);
        spawningMob.getBody().setOverlapMask(Entity.OverlapMasks.NPE.value);

        //ai state
        spawningMob.getBrain().getStateMachine().changeState(AIState.StateID.SPAWNING);

        //Add the entity to the scene
        this.owningScene.add(spawningMob, Layer.MAIN);   
        
        //get and add spawn effects to scene
        ArrayList<SimpleEntry<SceneObject,SylverVector2f>> effects = SpawningEffectsFactory.getSpawnEffects(mobToSpawn.getImage().getAnimationPack(),mobToSpawn.getImage().isFlippedVertical());
        for(SimpleEntry<SceneObject,SylverVector2f> entry: effects)
        {
            SceneObject object = entry.getKey();
            
            SylverVector2f pos = entry.getValue();
            
            object.setPosition(spawningMob.getPosition().x + spawningMob.getWidth() * pos.x, spawningMob.getPosition().y + spawningMob.getHeight() * pos.y);
       
            this.owningScene.add(object, Layer.ATTACHED_FG);
        
        }
        
        //add sound
        Sound sound = SpawningEffectsFactory.getSpawnSound(mobToSpawn.getImage().getAnimationPack(), spawnX, spawnY); 
        if(sound != null)
        {
           this.owningScene.add(sound);
        }
        
    }
    
    public void setArmed(boolean value)
    {
        this.armed = value;
    }

    /**
        * Handles time based state changes of this object, in this case mob spawning
        * and accumulation.
        */
    public void update()
    {
        super.update();
        
        //handle spawning mobs
        if(this.remainingMobs > 0)
        {
            this.spawnTimeCounter++;
            
            if(this.spawnTimeCounter % this.spawnTime == 0)
            {
                this.spawnMob();
                this.remainingMobs--;
            }
            
        }
        
        //image positioning
        if(this.image != null)
        {
            this.image.setPositionAnchored(this.spawnX,this.spawnY);
        }
    }
    
    @Override
    public void draw(GL2 gl)
    {
        if(this.owningScene instanceof MapEditorScene ||this.owningScene == null)
        {
            super.draw(gl);
            
            //draw spawn icon
            Image icon = new Image("spawnerIcon.png");
            icon.setScale(.6f);
            icon.setAnchor(Anchorable.Anchor.CENTER);
            icon.setPositionAnchored(this.getPosition().x, this.getPosition().y);
            icon.draw(gl);
            
            //draw arrow to image
            gl.glDisable(GL3bc.GL_TEXTURE_2D);
            gl.glLineWidth(3);
            gl.glColor3f(1, 0, 0);
            gl.glBegin(GL3bc.GL_LINES);
            {
                gl.glVertex2f(this.getPosition().x, this.getPosition().y);
                gl.glVertex2f(this.spawnX,this.spawnY);
            }
            gl.glEnd();
            gl.glColor3f(1,1,1);                     
            
        }
        
    }
    
    //====================
    // Saving Methods
    //====================

    /**
        * Dumps the save data
        * @return 
        */
    public SceneObjectSaveData dumpFullData()
    {
        SceneObjectSaveData saved = new SceneObjectSaveData(ExtendedSceneObjectClasses.MOBSPAWNER,this.ID);

        saved.dataMap.put("spawnTime",this.spawnTime);    
        saved.dataMap.put("number",this.numberToSpawn); 
        saved.dataMap.put("x",this.spawnX);         
        saved.dataMap.put("y",this.spawnY);         
        saved.dataMap.put("mob",this.mobToSpawn.dumpFullData()); 
        saved.dataMap.put("body",Entity.dumpBodySavable(this.body)); 
        saved.dataMap.put("spawnOnCollide",this.spawnOnPlayerCollide);

        return saved;
    }

    /**
        * Rebuilds the object
        */
    public static MobSpawner buildFromFullData(SceneObjectSaveData saveData) 
    {
        Body body = Entity.buildBodyFromSavedData((SaveData)saveData.dataMap.get("body"));
        NonPlayerEntity mob = NonPlayerEntity.buildFromFullData((SceneObjectSaveData)saveData.dataMap.get("mob"));
        
        MobSpawner spawner = new MobSpawner(mob,body);
        spawner.ID = (String)saveData.dataMap.get("id");
        
        spawner.spawnTime = (int)saveData.dataMap.get("spawnTime");
        spawner.numberToSpawn = (int)saveData.dataMap.get("number");
        spawner.spawnX = (float)saveData.dataMap.get("x");
        spawner.spawnY = (float)saveData.dataMap.get("y"); 
        spawner.spawnOnPlayerCollide = (boolean)saveData.dataMap.get("spawnOnCollide");

        return spawner;
    }

}
