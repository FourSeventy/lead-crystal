package com.silvergobletgames.leadcrystal.scenes;

import com.jogamp.newt.event.KeyEvent;
import com.silvergobletgames.leadcrystal.core.*;
import com.silvergobletgames.leadcrystal.core.CollisionHandler;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.core.LevelData;
import com.silvergobletgames.leadcrystal.cutscenes.CutsceneManager;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorStat;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorStat.ArmorStatID;
import com.silvergobletgames.leadcrystal.items.Potion;
import com.silvergobletgames.leadcrystal.items.PotionManager;
import com.silvergobletgames.leadcrystal.menus.Hud;
import com.silvergobletgames.leadcrystal.menus.MessageManager.MessageType;
import com.silvergobletgames.leadcrystal.scripting.SceneScriptManager;
import com.silvergobletgames.leadcrystal.scripting.ScriptObject.ScriptTrigger;
import com.silvergobletgames.leadcrystal.skills.Skill;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;
import com.silvergobletgames.leadcrystal.skills.SkillFactory;
import com.silvergobletgames.sylver.audio.AudioRenderer;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.*;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import com.silvergobletgames.sylver.graphics.OpenGLGameWindow;
import com.silvergobletgames.sylver.util.SerializableEntry;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.awt.Point;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.media.opengl.GL2;
import javax.media.opengl.GL3bc;
import javax.media.opengl.glu.GLU;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.World;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Shape;
import net.phys2d.raw.strategies.QuadSpaceStrategy;

/**
 * Server architechture for holding Client, Connection info, Metadata
 * @author Justin Capalbo
 */
public class GameScene extends Scene 
{
    
    //easy reference to player
    private PlayerEntity player; 
    //active save game file
    private SaveGame activeSaveGame;
    
    //active level
    private LevelData activeLevel;
    //queued change level
    private String queuedNextLevel;
    //script interpreter
    private SceneScriptManager scriptManager = new SceneScriptManager(this);
    //cutscene manager
    private CutsceneManager cutsceneManager;
    
    //physics world
    private World physicsWorld; 
    //Collision Listener
    CollisionHandler collisionHandler = new CollisionHandler();  

    //lock input 
    private AtomicBoolean lockInput = new AtomicBoolean(false);
     //World Mouse Location
    private SylverVector2f worldMouseLocation = new SylverVector2f(0, 0); 
    private boolean mouseHoverInRange = false;
    private boolean mouseHover = false;
    private boolean mouseOverMenu = false; 
    private String lastHoveredEntityID;
    private String hoveredEntityID;
    private boolean hoveredEntityInRange;
    private boolean hoveredEntityExited;
    
    //HUD
    private Hud hud;  
    
    
    //===============
    // Constructor
    //===============
    
    public GameScene(SaveGame save)
    {
        //save data
        this.activeSaveGame = save;
        
        //initializes player and adds it to the scene
        player = activeSaveGame.getPlayer();
        player.setID(UUID.randomUUID().toString());
        
        //set modified viewport for this scene
        EnhancedViewport view = new EnhancedViewport();
        view.setPanSpeed(11); 
        this.setViewport(view);        
        
        //build physics world
        physicsWorld = new World(new Vector2f(0.0f, -57.0f), 7, new QuadSpaceStrategy(20, 5));
        physicsWorld.addListener(collisionHandler);
        physicsWorld.enableRestingBodyDetection(1f, 1f, 1f);
        
        
        //figure out which level to load
        String levelToGo;
        if(player.getLevelProgressionManager().levelMap.get(0).mainObjective.complete == true)
            levelToGo = "town.lv";
        else
            levelToGo = "desert0.lv";
        
        //load level
        this.loadLevel(levelToGo);
        
        //move player to starting spot
        SceneObject checkpoint = this.getSceneObjectManager().get("checkpoint1"); //TODO get right spot
        this.movePlayerToPoint( new SylverVector2f(checkpoint.getPosition().x, checkpoint.getPosition().y));
        
        //initializing vewport
        getViewport().quickMoveToCoordinate(player.getPosition().x, player.getPosition().y);
        
        //initializing hud
        hud = new Hud(this);
        this.add(hud,Layer.MENU);          
        
        //cutscenee manager
        this.cutsceneManager = new CutsceneManager(this);
        this.cutsceneManager.update();
        
    }

    
    //=========================
    // Scene Interface Methods
    //=========================
    
    /**
     * Handles updating the physics world and all of the objects in the scene.
     */
    public void update()
    {
          
        //==============            
        // Handle Input
        //==============
        this.handleInput();


        //================
        // Handle Physics
        //================

        //clear the physics world of resting bodies (this needs to be done for isTouching() to work)
        physicsWorld.clearRestingState(); 


        //disable out of range bodies
        ArrayList<SceneObject> objects = this.getSceneObjectManager().get(Layer.MAIN);
        for(SceneObject s:  objects)
        {
            if( s instanceof Entity)
            {
                Entity ent = (Entity)s;

                int distance = 2_900;
                if( !ent.getBody().isStatic())
                {
                    distance = 2_500;
                }

                //if out of range of the player                  
                if(player.distanceAbs(ent) >= distance)
                {
                    // ent.getBody().setVelocity(new Vector2f(0,0));
                    // this.physicsWorld.clearArbiters(ent.getBody());
                     ent.getBody().setEnabled(false);  
                }
                else
                {
                    ent.getBody().setEnabled(true);
                }
            }
        }

       // long time = System.currentTimeMillis();
        physicsWorld.step(5 / 60F);


       // System.err.println(System.currentTimeMillis() - time);

        //resolve collisions
        this.collisionHandler.resolveCollisions();        


        //================
        // Logic Update
        //================

        //update everything in the scene          
        for (Layer layer: Layer.values())
        {
            ArrayList<SceneObject> parallax = new ArrayList(this.getSceneObjectManager().get(layer));
            for (int j = 0; j < parallax.size(); j++)
            {
                //assign local variable
                SceneObject currentObject = parallax.get(j);
                //update the SceneObject
                currentObject.update();
            }
        }    
        
        //update hud
        hud.update();

        //update scene effects manager
        this.getSceneEffectsManager().update();

        //move viewport
        handleViewportMovement();
        
        //Move the sound listener
        Game.getInstance().getAudioRenderer().setListenerPosition(player.getPosition().x, player.getPosition().y, 500);
        Game.getInstance().getAudioRenderer().setListenerVelocity(player.getBody().getVelocity().getX(), player.getBody().getVelocity().getY(), 0);

         //update cutscene
        this.cutsceneManager.update();  
                
        //update mouse cursor state
        this.handleMouseCursorState();
        
    }

    public void handleInput()
    {      
        
        //if input is locked
        if(this.lockInput.get())
        {
            return;
        }
        
        //input snapshot
        InputSnapshot inputSnapshot = Game.getInstance().getInputHandler().getInputSnapshot();
        
        //======================= 
        // Handle Mouse Location
        //=======================           

        //calculate mouse position
        float mouseX = (((float) inputSnapshot.getScreenMouseLocation().x / getViewport().getWidth()) * getViewport().getWidth() + getViewport().getBottomLeftCoordinate().x);
        float mouseY =  (((float) inputSnapshot.getScreenMouseLocation().y / getViewport().getHeight()) * getViewport().getHeight() + getViewport().getBottomLeftCoordinate().y);
        this.worldMouseLocation = new SylverVector2f(mouseX, mouseY);
                


        //===============================
        // Handle players facing position
        //===============================

        if (player.getCombatData().canMove())
        {
            if (mouseX < player.getPosition().x)
            {
                player.face(FacingDirection.LEFT);
            }
            else
            {
                player.face(FacingDirection.RIGHT);
            }
        }


        //============================
        // Check for entity hovering
        //============================
        Body mouse = new Body(new Box(2, 2), 1);
        mouse.setPosition(mouseX, mouseY);
        this.lastHoveredEntityID = this.hoveredEntityID;
        boolean lastRange = this.hoveredEntityInRange;
        this.hoveredEntityID = null;
        this.hoveredEntityInRange = false;
        this.hoveredEntityExited = false;
        boolean rangeChange = false;

        ArrayList<SceneObject> clickableGroup = this.getSceneObjectManager().get(ExtendedSceneObjectGroups.CLICKABLE);
        for (SceneObject hoverObject : clickableGroup) 
        {
            boolean mouseOn = false;

            Entity entity = (Entity)hoverObject;

            Shape shape = entity.getBody().getShape();

            if(shape.contains(new Vector2f(mouseX,mouseY), new Vector2f(entity.getPosition().x,entity.getPosition().y), entity.getBody().getRotation()))
            {
                mouseOn = true;    
            }


            //if the mouse is on something
            if (mouseOn) 
            {
                this.hoveredEntityID = entity.getID();
                //if the entity is in range of the player
                if(Point.distance(player.getPosition().x, player.getPosition().y, entity.getPosition().x, entity.getPosition().y) < 250) 
                {
                    if(lastRange == false)
                    {
                        rangeChange = true;
                    }
                    this.hoveredEntityInRange = true;  
                }
                else   
                {
                    if(lastRange == true)
                    {
                        rangeChange = true;
                    }
                    this.hoveredEntityInRange = false;
                }
            }
            else // mouse is outside an entity
            {
                //if the old hovered entity is equal to this entity, or the old entity isnt in the scene anymore 
                if( this.lastHoveredEntityID != null &&( this.lastHoveredEntityID.equals(entity.getID()) || this.getSceneObjectManager().get(lastHoveredEntityID) == null))     
                {
                    this.hoveredEntityExited = true;   
                }
            }
        }

        //check for edge case for not hovering
        if(clickableGroup.isEmpty() && this.lastHoveredEntityID != null)
        {

            this.hoveredEntityExited = true;   
        }

        //if we are hovering
        if(this.mouseHover && this.mouseHoverInRange)
        {
            //get entity that we are hovering on
            Entity hoveredEntity = (Entity)this.getSceneObjectManager().get(this.hoveredEntityID);
            
            //apply brightness effect
            if(hoveredEntity != null && !hoveredEntity.getImage().hasImageEffect("hover"))
            {
                Float[] points = {1.3f,1.6f,1.3f};
                int[] durations = {60,60};
                ImageEffect brightnessEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points,durations);
                hoveredEntity.getImage().addImageEffect("hover",brightnessEffect);
            }
        }
        else
        {
            //get entity that we are hovering on
            Entity hoveredEntity = (Entity)this.getSceneObjectManager().get(this.hoveredEntityID);
            
            
            if(hoveredEntity != null) //prevents race condition timing bugs
            {
                //remove hover effect
                hoveredEntity.getImage().removeImageEffect("hover");
                hoveredEntity.getImage().setBrightness(1);
            }
        }
                    


        //================
        // Keyboard Input
        //================

        player.setWorldMouseLocationPoint(mouseX,mouseY); 


        //move left
        if (inputSnapshot.isKeyPressed(KeyEvent.VK_A))
        {
            player.move(FacingDirection.LEFT);
        }

        //move right
        if (inputSnapshot.isKeyPressed(KeyEvent.VK_D))
        {
            player.move(FacingDirection.RIGHT);
        }

        //jump
        if (inputSnapshot.isKeyPressed(KeyEvent.VK_SPACE) == true)
        {
            player.handleJumping();
        }

        //jump released
        if (inputSnapshot.isKeyReleased(KeyEvent.VK_SPACE) == true)
        {
            player.handleJumpReleased();
        }

        //down
        if (inputSnapshot.isKeyPressed(KeyEvent.VK_S) == true)
        {
            player.move(new SylverVector2f(0,-1));
        }
        //up
        if (inputSnapshot.isKeyPressed(KeyEvent.VK_W) == true)
        {
            player.move(new SylverVector2f(0,1));
        }

        //sprint
        if (inputSnapshot.isKeyPressed(KeyEvent.VK_SHIFT))
        {
            player.handleSprint();
        }
        if(inputSnapshot.isKeyReleased(KeyEvent.VK_SHIFT))
        {
            player.handleSprintReleased();
        }

        //toggle flashlight
        if (inputSnapshot.isKeyReleased(KeyEvent.VK_T))
        {
            player.toggleFlashlight();
        }

        //use hotbar skills 
        if (inputSnapshot.isKeyReleased(KeyEvent.VK_Q))
        {
            if(player.getSkillAssignment(3) != null && player.getSkillManager().getSkill(player.getSkillAssignment(3)).isUsable())
            {
                player.useActionBarSkill(player.getSkillAssignment(3));
            }
        }
        if (inputSnapshot.isKeyReleased(KeyEvent.VK_E))
        {
            if(player.getSkillAssignment(4) != null && player.getSkillManager().getSkill(player.getSkillAssignment(4)).isUsable())
            {
                player.useActionBarSkill(player.getSkillAssignment(4));
            }
        }

        //use potion
        if (inputSnapshot.isKeyReleased(KeyEvent.VK_F))
        {
            if(!player.getCombatData().isDead())
            {
                 player.getPotionManager().usePotion(); 
            }
        }
        
        //dash
        if(player.dashing)
        {
            player.handleDash(null);
        }
        
        //open esc menu
        if(inputSnapshot.isKeyReleased(KeyEvent.VK_ESCAPE))
        {        
            if(hud.armorMenu.isOpen() || hud.mapMenu.isOpen() || hud.potionsMenu.isOpen() || hud.questMenu.isOpen() || hud.skillMenu.isOpen() || hud.optionsMenu.isOpen() || (hud.activeDialogue != null && hud.activeDialogue.isOpen()))
            {
                hud.armorMenu.close();
                hud.mapMenu.close();
                hud.potionsMenu.close();
                hud.questMenu.close();
                hud.skillMenu.close();
                hud.optionsMenu.close();

                if(hud.activeDialogue != null)
                {
                   hud.activeDialogue.close();
                }
            }
            else
            {
                hud.escapeMenu.toggle();
            }
        }

        //quest menu
        if(inputSnapshot.isKeyReleased(com.jogamp.newt.event.KeyEvent.VK_L))
        {
            hud.questMenu.toggle();
        }



        //debug
        if (inputSnapshot.isKeyReleased(com.jogamp.newt.event.KeyEvent.VK_M))
        {

        }



        //=============
        // Mouse Input
        //=============

        //mouse CLICKED handling
       if(inputSnapshot.isMouseClicked() && !hud.isMouseOverMenu())
        {
            //if we clicked on an interactable object
            if (this.hoveredEntityID != null && this.hoveredEntityInRange)
            {
                if(((Entity) this.getSceneObjectManager().get(this.hoveredEntityID)).getScriptObject().getTrigger() == ScriptTrigger.RIGHTCLICK)
                {
                    ((Entity) this.getSceneObjectManager().get(this.hoveredEntityID)).getScriptObject().runScript(player);
                    inputSnapshot.killMouseClick();
                }
            }

        }

        //mouse DOWN handling
        if (inputSnapshot.isMouseDown() && !hud.isMouseOverMenu())
        {
            if (inputSnapshot.buttonClicked() == 1 && !(this.hoveredEntityID != null && this.hoveredEntityInRange) )
            {
                if (player.getSkillAssignment(1) != null && player.getSkillManager().getSkill(player.getSkillAssignment(1)).isUsable())
                {
                    player.useActionBarSkill(player.getSkillAssignment(1));
                }
            }
            else if(inputSnapshot.buttonClicked() == 3 && !(this.hoveredEntityID != null && this.hoveredEntityInRange))
            {
                if (player.getSkillAssignment(2) != null && player.getSkillManager().getSkill(player.getSkillAssignment(2)).isUsable())
                {
                    player.useActionBarSkill(player.getSkillAssignment(2));
                }
            }

        }            

        
    }

    /**
     * Uses RenderingPipelineGL2 for rendering.
     * @param gl The graphics context 
     */
    public void render(GL2 gl)
    {    
        
        //set viewport size
        Point aspectRatio = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio();
        float ratio = ((float) aspectRatio.x) / aspectRatio.y;
        int zoom = 0;// -96;
        getViewport().setDimensions(aspectRatio.x + zoom*ratio , aspectRatio.y + zoom);


        //==================
        // Render the scene
        //==================
        if(this.cutsceneManager.getCutscene() != null)
        {           
            this.cutsceneManager.getCutscene().draw(gl, getViewport());
        }
        else
        {
            if(gl.getGLProfile().isGL3bc())
            {
                //GL3bc
                RenderingPipelineGL3.render((GL3bc)gl, getViewport(), getSceneObjectManager(),getSceneEffectsManager()); 
            }
            else
            {
                //GL2
                RenderingPipelineGL2.render(gl, getViewport(), getSceneObjectManager(),getSceneEffectsManager()); 
            }
        }
        
        //debug viewport feeler rendering
        if(GameplaySettings.getInstance().viewportFeelers)
        {
            EnhancedViewport viewport = (EnhancedViewport)getViewport();
            gl.glMatrixMode(GL3bc.GL_MODELVIEW);
            gl.glDisable(GL3bc.GL_BLEND);
            gl.glDisable(GL3bc.GL_TEXTURE_2D); 
            GLU glu = new GLU();
            gl.glLoadIdentity();
            glu.gluLookAt(viewport.getBottomLeftCoordinate().x * Layer.MAIN.coordinateScalingFactor, viewport.getBottomLeftCoordinate().y * Layer.MAIN.coordinateScalingFactor, 1, viewport.getBottomLeftCoordinate().x * Layer.MAIN.coordinateScalingFactor, viewport.getBottomLeftCoordinate().y * Layer.MAIN.coordinateScalingFactor, 0, 0, 1, 0);


            gl.glColor4f(0,1,0,1);
            gl.glBegin(GL3bc.GL_LINES);
                gl.glVertex2f(viewport.bottomLine.getStart().getX(),viewport.bottomLine.getStart().getY());
                gl.glVertex2f(viewport.bottomLine.getEnd().getX(),viewport.bottomLine.getEnd().getY());
            gl.glEnd();

            gl.glColor4f(0,0,1,1);
            gl.glBegin(GL3bc.GL_LINES);
                gl.glVertex2f(viewport.leftLine.getStart().getX(),viewport.leftLine.getStart().getY());
                gl.glVertex2f(viewport.leftLine.getEnd().getX(),viewport.leftLine.getEnd().getY());
            gl.glEnd();

            gl.glColor4f(1,1,0,1);
            gl.glBegin(GL3bc.GL_LINES);
                gl.glVertex2f(viewport.rightLine.getStart().getX(),viewport.rightLine.getStart().getY());
                gl.glVertex2f(viewport.rightLine.getEnd().getX(),viewport.rightLine.getEnd().getY());
            gl.glEnd();
        }
    
    }

    /**
     * Adds any Updateable and Drawable object to the GameScene one special case exists for entities.
     * @param <A>
     * @param item
     * @param layer 
     */
    public void add(SceneObject item, Layer layer)
    {
        //super add
        super.add(item,layer);
        
        //do special case for entities
        if (item instanceof Entity)
        {
            //add the entity to the physics world if the entity is in the main layer
            if (layer == Layer.MAIN)
            {
                
                //if it isnt no collide/no-overlap add it
                if(!(Entity.BitMasks.valueToEnum(((Entity)item).getBody().getBitmask()) == Entity.BitMasks.NO_COLLISION && Entity.OverlapMasks.valueToEnum(((Entity)item).getBody().getOverlapMask()) == Entity.OverlapMasks.NO_OVERLAP))
                {
                   physicsWorld.add(((Entity) item).getBody());
                }
                
                //if the group is crate, add all other crates into included bodies
                if(((Entity)item).isInGroup(ExtendedSceneObjectGroups.CRATE))
                {
                    //get list of all other crates
                    ArrayList<SceneObject> otherCrates = this.getSceneObjectManager().get(ExtendedSceneObjectGroups.CRATE);
                                     
                    for(SceneObject crate: otherCrates)
                    {
                        //add all other creates to this creates included list
                        ((Entity)crate).getBody().addIncludedBody(((Entity)item).getBody());
                        
                        //add this crate to all other crates included list
                        ((Entity)item).getBody().addIncludedBody(((Entity)crate).getBody());
                    }
                    
                }
            }
        }   
    }
    
    public void add(Sound sound)
    {
         AudioRenderer.playSound(sound);
    }

    public void remove(SceneObject item)
    {       
        //if the item is null just return
        if(item == null)
        {
            return;
        }
        
        //entity special case
        if (item instanceof Entity)
        {   //TODO: consider special case with bitmasks
            //remove the entity from the physics world if the entity was in the main layer
            if (this.getSceneObjectManager().get(Layer.MAIN)  != null && this.getSceneObjectManager().get(Layer.MAIN).contains(item))
            {
                physicsWorld.clearArbiters(((Entity) item).getBody()); 
                physicsWorld.remove(((Entity) item).getBody());
            }
        }           
       
        //super method
        super.remove(item);

    }

    public void sceneEntered(ArrayList args)
    {
        //reset viewport size
        getViewport().setDimensions(Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x, Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().y);
        
        //change cursor
        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.RETICLE));      
        
        //fade from black
        getSceneEffectsManager().fadeFromBlack(new SceneEffectsManager.PostEffectExecutor());
    }

    public void sceneExited()
    {          
        //cleanup some stuff
        this.physicsWorld.clear();       
    }

    
    //===================
    // Game Methods
    //====================
    private boolean loadLevel(String filename) 
    {
        
        //Build the new level
        LevelData level = LevelLoader.getInstance().getLevelData(filename);
        
        //empty old scene
        for (Layer layer: Layer.values()) 
        {
            if(layer == Layer.HUD || layer == Layer.MENU)
                continue;
            
            ArrayList<SceneObject> layerObjs = (ArrayList<SceneObject>) this.getSceneObjectManager().get(layer);
            for (int i = 0; i < layerObjs.size(); i++) 
            {
                this.remove(layerObjs.get(i));
                i--;
            }
        }
        
        this.physicsWorld.clear();
                 
        //Load the objects to the scene
        ArrayList<SimpleEntry<SceneObject,Layer>> sceneObjectList = level.getSceneObjects();
        for(SimpleEntry<SceneObject,Layer> entry:sceneObjectList)
        {
            this.add(entry.getKey(), entry.getValue());
        }
        
        //add player back into scene
        this.add(this.player, Layer.MAIN);
        
      
        //set which level is active
        this.activeLevel = level;
        
        //run all auto scripts
        ArrayList<SceneObject> scripts = this.getSceneObjectManager().get(ExtendedSceneObjectGroups.SCRIPT);       
        for(SceneObject obj: scripts)
        {
            if(obj instanceof Entity && ((Entity)obj).getScriptObject().getTrigger() == ScriptTrigger.AUTO)
                ((Entity)obj).getScriptObject().runScript(null);
        }
        
        return true;
    }
    
    public void changeLevel(int levelNumber)
    {
        
        String levelName = this.player.getLevelProgressionManager().levelMap.get(levelNumber).levelDataName;
        
        this.changeLevel(levelName);
        
    }
    
    public void changeLevel(String levelName)
    {
        //close menus
        hud.mapMenu.close();
        hud.questMenu.close();
        
       //lock input for 3 seconds
        Thread inputLock = new Thread(){
            
            @Override
            public void run()
            {
                lockInput.set(true);
                
                try
                {
                    Thread.sleep(3_000);
                }
                catch (InterruptedException ex){}
                
                lockInput.set(false);
                
            }
        };
        inputLock.start();
        
        //queue up level change
        if(this.activeLevel!= null)
        {
            queuedNextLevel = levelName;

            this.getSceneEffectsManager().fadeToBlack(new SceneEffectsManager.PostEffectExecutor(){
                public void execute()
                {                      
                    loadLevel(queuedNextLevel); 
                    queuedNextLevel = null;
                }});
        }
        else 
        {
            loadLevel(levelName);   
        }
    }
    
    public void saveGameToDisk()
    {
        //build the save
        SaveGame save = new SaveGame();
        save.setPlayer(this.player);
        //actually save it
        save.save(this.activeSaveGame.fileName);
    }   
    
    public void saveAndQuit()
    {
        saveGameToDisk();
        Game.getInstance().unloadScene(GameScene.class);
        Game.getInstance().loadScene(new MainMenuScene());
        Game.getInstance().changeScene(MainMenuScene.class,new ArrayList(){{add(true);}}); 
    }
     
    public void movePlayerToPoint(SylverVector2f point)
    {
        
        //clear player arbiters
        this.physicsWorld.clearArbiters(this.player.getBody());
        
        //set player position
        this.player.setPosition(point.x , point.y);
        this.player.getBody().setVelocity(new Vector2f(0,0));
        this.player.handleSprintReleased();
        
    }
    
    public void clearArbitersOnBody(Body body)
    {
        physicsWorld.clearArbiters(body); 
    }
     
    private void handleMouseCursorState()
    {
        //if the moues is over a menu, the menu controls the cursor
        if(this.mouseOverMenu)
        {
            return;
        }
        
        if(this.mouseHoverInRange)
        {
            Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.ACTIVEHAND));
        }
        else if(this.mouseHover)
        {
            Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.UNACTIVEHAND));       
        }
        else
        {
            Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.RETICLE));       
        }
    }
    
    private void handleViewportMovement()           
    {
        
        //gets the viewportBlockers
        ArrayList<SceneObject> list = this.getSceneObjectManager().get(ExtendedSceneObjectGroups.VIEWPORTBLOCKER);
        ArrayList<Entity> allBlockers = new ArrayList<>();
            for(SceneObject e:list)
                allBlockers.add((Entity)e);
            
        //move viewport with blocker correction
        ((EnhancedViewport)getViewport()).moveToCoordinateWithCorrection(player.getPosition().x, player.getPosition().y, allBlockers);
        
    } 
    
    public AtomicBoolean getInputLock()
    {
        return this.lockInput;
    }
    
    public SylverVector2f getWorldMouseLocation()
    {
        return this.worldMouseLocation;
    }
    

    public void buySkill(SkillID skillid)
    {
        Skill skill = SkillFactory.getInstance().getSkill(skillid);
        
        //unlock skill
        if(this.player.getSkillManager().getSkillPoints() >= skill.getUnlockCost())
        {
            //subtract skill points from our pool
            this.player.getSkillManager().setSkillPoints(this.player.getSkillManager().getSkillPoints() - skill.getUnlockCost());

            //learn the skill
            this.player.getSkillManager().learnSkill(skill.getSkillID());
        }
    }
     
    public void buyPotion()
    {       
        //subtract money from player
        boolean success = player.getCurrencyManager().getBalence()>=PotionManager.POTION_PRICE && player.getPotionManager().getNumberOfPotions() < player.getPotionManager().getMaxPotions();

        //if the subtraction succeeded, give potion to player
        if(success)
        {
            player.getCurrencyManager().subtractCurrency(PotionManager.POTION_PRICE);
            player.getPotionManager().addPotion(1);
        }   
    }  
    
    public void buyStat(ArmorStatID statId)
    {     
        //subtract money from player
        boolean success = player.getCurrencyManager().getBalence()>=player.getArmorManager().armorStats.get(statId).cost 
                          && player.getArmorManager().armorStats.get(statId).points < player.getArmorManager().armorStats.get(statId).maxPoints;

        //if the subtraction succeeded, give potion to player
        if(success)
        {
            player.getCurrencyManager().subtractCurrency(player.getArmorManager().armorStats.get(statId).cost);
            player.getArmorManager().armorStats.get(statId).addPoint(1); 
        }   
    }     
      
    public void respawnPlayer()
    {       
        //tell the player to respawn when enter town
        player.respawnWhenEnterTown = true;

        //TODO: move to town       
    }
    
    public void closeDialogue()
    {
        
       //search scene for script objects that have a dialogue closed trigger, and trigger them
       ArrayList<SceneObject> scripts = this.getSceneObjectManager().get(ExtendedSceneObjectGroups.SCRIPT);
       for(SceneObject so: scripts)
       {
           if( !(so instanceof WorldObjectEntity))
           {
               continue;
           }


           WorldObjectEntity wo = (WorldObjectEntity)so;

           if(wo.getScriptObject() != null && wo.getScriptObject().getTrigger() == ScriptTrigger.DIALOGUECLOSED)
           {
               wo.getScriptObject().runScript(wo);
           }
       }
            
    
    }
    
    public void setSideQuestStatus(String text)
    {
        //set the status
        this.hud.questMenu.setSideObjectiveStatus(text);
        
        // if status isnt "complete", send quest status to the screen through message manager
        if(!text.equals("complete"))
        {
            ArrayList<String> message = new ArrayList<>();
            message.add(text);
            this.hud.getMessageManager().queueMessage(MessageType.SIDE_OBJECTIVE_STATUS,message );
        }
    }
      
    public void setMainQuestStatus(String text)
    {
        //set the status
        this.hud.questMenu.setMainObjectiveStatus(text);
                
        // if status isnt "complete", send quest status to the screen through message manager
        if(!text.equals("complete"))
        {
            ArrayList<String> message = new ArrayList<>();
            message.add(text);
            this.hud.getMessageManager().queueMessage(MessageType.MAIN_OBJECTIVE_STATUS,message );
        }
    }
    
    public void completeSideObjective(int currencyReward, ArmorStatID unlockStat)
    {
        //queue objective complete message
        ArrayList<String> objectiveDetails = new ArrayList<>();
        objectiveDetails.add("Side Objective Complete!");
        this.hud.getMessageManager().queueMessage(MessageType.OBJECTIVE_COMPLETE, objectiveDetails);
        
        //queue objective reward message
        if(currencyReward != 0)
        {
            ArrayList<String> rewardDetails = new ArrayList<>();
            rewardDetails.add(Integer.toString(currencyReward));
            rewardDetails.add("1"); //skill points
            this.hud.getMessageManager().queueMessage(MessageType.OBJECTIVE_REWARD, rewardDetails);
        }
             
        //queue modifier unlock message
        if(unlockStat != null)
        {
            ArrayList<String> modifierDetails = new ArrayList<>();
            String modifierString =this.player.getArmorManager().armorStats.get(unlockStat).name;
            modifierDetails.add(modifierString);
            String modifierImage = this.player.getArmorManager().armorStatLookup(unlockStat).image.getTextureReference();
            modifierDetails.add(modifierImage);
            this.hud.getMessageManager().queueMessage(MessageType.MODIFIER_UNLOCKED, modifierDetails);
        }
            
    }
    
    public void completeMainObjective(int currencyReward, ArmorStatID unlockStat)
    {
        //queue objective complete message
        ArrayList<String> objectiveDetails = new ArrayList<>();
        objectiveDetails.add("Main Objective Complete!");
        this.hud.getMessageManager().queueMessage(MessageType.OBJECTIVE_COMPLETE, objectiveDetails);
        
        //queue objective reward message
        if(currencyReward != 0)
        {
            ArrayList<String> rewardDetails = new ArrayList<>();
            rewardDetails.add(Integer.toString(currencyReward));
            rewardDetails.add("1"); //skill points
            this.hud.getMessageManager().queueMessage(MessageType.OBJECTIVE_REWARD, rewardDetails);
        }
             
        //queue modifier unlock message
        if(unlockStat != null)
        {
            ArrayList<String> modifierDetails = new ArrayList<>();
            String modifierString = this.player.getArmorManager().armorStats.get(unlockStat).name;
            modifierDetails.add(modifierString);
            String modifierImage = this.player.getArmorManager().armorStatLookup(unlockStat).image.getTextureReference();
            modifierDetails.add(modifierImage);
            this.hud.getMessageManager().queueMessage(MessageType.MODIFIER_UNLOCKED, modifierDetails);
        }
              
    }
    
    
    
    
    //================
    // Acessor Methods
    //================
    
    public PlayerEntity getPlayer()
    {
        return this.player;
    }
    
    public LevelData getActiveLevel()
    {
        return this.activeLevel;
    }
    
    public SceneScriptManager getScriptManager()
    {
        return this.scriptManager;
    }
     
    public Hud getHud()
    {
        return this.hud;
    }
    
    public World getPhysicsWorld()
    {
        return this.physicsWorld;
    }
}
