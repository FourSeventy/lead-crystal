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
import com.silvergobletgames.leadcrystal.netcode.*;
import com.silvergobletgames.leadcrystal.netcode.BuyPotionPacket;
import com.silvergobletgames.leadcrystal.netcode.ChangeLevelPacket;
import com.silvergobletgames.leadcrystal.netcode.ChatPacket;
import com.silvergobletgames.leadcrystal.netcode.ChooseLevelPacket;
import com.silvergobletgames.leadcrystal.netcode.ClientData;
import com.silvergobletgames.leadcrystal.netcode.ClientInputPacket;
import com.silvergobletgames.leadcrystal.netcode.CloseMenuPacket;
import com.silvergobletgames.leadcrystal.netcode.CursorChangePacket;
import com.silvergobletgames.leadcrystal.netcode.GobletServer;
import com.silvergobletgames.leadcrystal.netcode.MovePlayerToPointPacket;
import com.silvergobletgames.leadcrystal.netcode.OpenDialoguePacket;
import com.silvergobletgames.leadcrystal.netcode.OpenInstructionalTipPacket.InstructionalTip;
import com.silvergobletgames.leadcrystal.netcode.OpenMenuPacket.MenuID;
import com.silvergobletgames.leadcrystal.netcode.RenderDataPacket;
import com.silvergobletgames.leadcrystal.netcode.RespawnPacket;
import com.silvergobletgames.leadcrystal.netcode.SaveGamePacket;
import com.silvergobletgames.leadcrystal.netcode.SkillDataPacket;
import com.silvergobletgames.leadcrystal.netcode.SoundDataPacket;
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
import com.silvergobletgames.sylver.netcode.NetworkedSceneObject;
import com.silvergobletgames.sylver.netcode.Packet;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderDataChanges;
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
    public PlayerEntity player; 
    //active save game file
    private SaveGame activeSaveGame;
    
    //active level
    private LevelData activeLevel;
    //script interpreter
    private SceneScriptManager scriptManager = new SceneScriptManager(this);
    //cutscene manager
    private CutsceneManager cutsceneManager;
    
    //physics world
    public World physicsWorld; 
    //Collision Listener
    CollisionHandler collisionHandler = new CollisionHandler();  

    //lock input 
    public AtomicBoolean lockInput = new AtomicBoolean(false);
     //World Mouse Location
    public Point worldMouseLocation = new Point(0, 0); 
    private boolean mouseHoverInRange = false;
    private boolean mouseHover = false;
    private boolean mouseOverMenu = false; 
    private String lastHoveredEntityID;
    private String hoveredEntityID;
    private boolean hoveredEntityInRange;
    private boolean hoveredEntityExited;
    
    //HUD
    public Hud hud;  
    
    
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
        
        //load level
        
        //add player
        
        //move player to starting spot
        
        //initializing vewport
        getViewport().quickMoveToCoordinate(player.getPosition().x, player.getPosition().y);
        
        //initializing hud
        hud = new Hud(this);
        hud.drawNetworkingStats(GameplaySettings.getInstance().drawNetworkingStats);  
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

        //if the player is not null
        if(this.player != null)
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
            
        }
        
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
        int mouseX = (int) (((float) inputSnapshot.getScreenMouseLocation().x / getViewport().getWidth()) * getViewport().getWidth() + getViewport().getBottomLeftCoordinate().x);
        int mouseY = (int) (((float) inputSnapshot.getScreenMouseLocation().y / getViewport().getHeight()) * getViewport().getHeight() + getViewport().getBottomLeftCoordinate().y);
        this.worldMouseLocation = new Point(mouseX, mouseY);
                


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
                if( this.lastHoveredEntityID != null &&( this.lastHoveredEntityID.equals(entity.getID()) || this.getSceneObjectManager().get(clientData.lastHoveredEntityID) == null))     
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

        //send hover packet
        if((clientData.hoveredEntityID != null && clientData.lastHoveredEntityID == null) || rangeChange) 
        {                    
            this.sendEntityHoverPacket(clientID, clientData.hoveredEntityID, true,clientData.hoveredEntityInRange);                                                
        }
        else if (clientData.hoveredEntityExited) 
        {
            this.sendEntityHoverPacket(clientID, clientData.lastHoveredEntityID, false,clientData.hoveredEntityInRange);
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

    //================
    //Class Methods
    //================
    public boolean loadLevel(String filename) 
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

    public PlayerEntity getPlayer()
    {
        return this.player;
    }
    
    
    public void movePlayerToPoint(String playerID,SylverVector2f point)
    {
        
        //clear player arbiters
        this.physicsWorld.clearArbiters(this.player.getBody());
        
        //set player position
        this.player.setPosition(point.x , point.y);
        this.player.getBody().setVelocity(new Vector2f(0,0));
        this.player.handleSprintReleased();
        
    }
    
    public SceneScriptManager getScriptManager()
    {
        return this.scriptManager;
    }
    
    public void clearArbitersOnBody(Body body)
    {
        physicsWorld.clearArbiters(body); 
    }
    
    public LevelData getActiveLevel()
    {
        return this.activeLevel;
    }
    
    
    //=============================
    //Sending Messages to Clients
    //=============================
    
    private void sendPacket(Packet packet, UUID client)
    {
        ClientData clientData =((GobletServer)Game.getInstance().getRunnable("Goblet Server")).connectedClients.get(client);
        
        //set the packet sequence number
        packet.setSequenceNumber(clientData.packetSequencer.nextSequenceNumber());
        
        //build the client specific ack and the ackbitfield for this packet
        packet.setAck(clientData.remoteSequenceNumber);

        //build the bitfield
        byte bitfield = 0;
        for( byte i = 1; i<=8; i ++)
        {
            if(clientData.recievedSequenceNumberHistory.containsKey(clientData.remoteSequenceNumber - i))
            {
                bitfield += 1L << (i-1);
            }
        }
        packet.setAckBitfield(bitfield);


        // add to sent packets list
        clientData.packetsSentToClient.put(packet.getSequenceNumber(), packet);
        
        //send the packet
        int size =clientData.connection.sendUDP(packet);
        if(GameplaySettings.getInstance().packetSizeDebugging)
            System.out.println("Server Packet: "+ "Size: " + size + " bytes " + " Type: " + packet.getClass().toString().substring(packet.getClass().toString().lastIndexOf(".") + 1, packet.getClass().toString().length()) );
 
    }
    
    private void sendRenderData()
    {             
        //send the packet to each client
        Iterator<UUID> iter = clientsInScene.keySet().iterator();
        while (iter.hasNext())
        {
            //local variables
            UUID currentClientID = iter.next();
            ClientData clientData =clientsInScene.get(currentClientID);
            
            //build the renderData packet
            RenderDataPacket packet = new RenderDataPacket();
            
            //add scene changes
            if(!this.renderData.isEmpty())
                packet.renderData = this.renderData.values().toArray(packet.renderData);
            
            if(!this.newSceneObjects.isEmpty())
                packet.newSceneObjects = this.newSceneObjects.values().toArray(packet.newSceneObjects);
            
            if(!this.removeSceneObjects.isEmpty())
                packet.removeSceneObjects = this.removeSceneObjects.toArray( packet.removeSceneObjects);
                      
                                                  
            //map the input packet that this renderDataPacket corresponds to
            if(clientData.currentInputPacket != null)                
            {
                packet.correspondingInputPacket = clientData.currentInputPacket.getSequenceNumber();
                
                //add client specific player prediction data to the packet
                packet.playersPredictionData = ((PlayerEntity)this.getSceneObjectManager().get(currentClientID.toString())).dumpPredictionData(); 
                
            }
            
            //send the packet
            this.sendPacket(packet, currentClientID);
        }
        
        this.renderData.clear();
        this.newSceneObjects.clear();
        this.removeSceneObjects.clear();
            
    }
    
    private void sendSoundData()
    {      
        //iterate through clients and send sound data to each
        Iterator<UUID> iter = clientsInScene.keySet().iterator(); 
        while (iter.hasNext())
        {
            UUID currentClientID = iter.next();
            if (outgoingSoundData.size() > 0)
            {
                //build packet
                SoundDataPacket soundPacket = new SoundDataPacket();               
                
                for (Sound soundData : outgoingSoundData)
                {
                    //Add the current sound data
                    soundPacket.add(soundData);
                }
                
                if (!soundPacket.isEmpty())
                {  
                    this.sendPacket(soundPacket, currentClientID);                
                }
            }
        }

        //Clear unused  data
        this.outgoingSoundData.clear();
    }
    
    private void sendChatMessages()
    {
        //loop through each client sending chat messages to each one
        Iterator<UUID> iter = clientsInScene.keySet().iterator();
        while (iter.hasNext())
        {
            //client id
            UUID currentClientID = iter.next();
            
            // add player specific chat messages
            ArrayList<String> chatInbox = this.clientsInScene.get(currentClientID).chatInbox;
            if (!chatInbox.isEmpty())
            {
                ChatPacket packet = new ChatPacket();
                
                packet.chatMessages = new ArrayList();
                for (String s : chatInbox)
                {
                    packet.chatMessages.add(s);
                }
                chatInbox.clear();
                
                //send the packet
                this.sendPacket(packet,currentClientID);
            } 
        }
    }
    
    public void sendOpenDialogue(String client, String speaker, String text)
    {      
        //build the packet
        OpenDialoguePacket packet = new OpenDialoguePacket();       
        packet.speaker = speaker;
        packet.text = text;
              
        //send the packet
        this.sendPacket(packet, UUID.fromString(client));
    }
    
    public void sendOpenMenu(String client, MenuID id)
    {
        //bulid the packet
        OpenMenuPacket packet = new OpenMenuPacket();
        packet.menu = id;
                
        //send the packet
        this.sendPacket(packet, UUID.fromString(client));
    }
    
    public void sendSetSideQuestStatus(String text)
    {
        for(UUID clientID:this.clientsInScene.keySet())
        {
            setSideQuestStatusPacket packet = new setSideQuestStatusPacket();
            packet.text = text;
            this.sendPacket(packet, clientID);
        }
    }
    
    public void sendSetMainQuestStatus(String text)
    {
        for(UUID clientID:this.clientsInScene.keySet())
        {
            SetMainQuestStatusPacket packet = new SetMainQuestStatusPacket();
            packet.text = text;
            this.sendPacket(packet, clientID);
        }
    }
    
    public void sendSideObjectiveCompletePacket(String client, short currencyReward, ArmorStatID modifierId)
    {
            //send out side objective complete packet
           SideObjectiveCompletePacket packet = new SideObjectiveCompletePacket();           
           packet.currencyReward = currencyReward;
           packet.modifierID = modifierId;
           this.sendPacket(packet,UUID.fromString(client));             
    }
    
    public void sendMainObjectiveCompletePacket(String client, short currencyReward,ArmorStatID modifierId)
    {
           //send out side objective complete packet
           MainObjectiveCompletePacket packet = new MainObjectiveCompletePacket();           
           packet.currencyReward = currencyReward;
           packet.modifierID = modifierId;
           this.sendPacket(packet,UUID.fromString(client));             
    }
    
    public void sendCloseMenu(String client, MenuID id)
    {
        //bulid the packet
        CloseMenuPacket packet = new CloseMenuPacket();
        packet.menu = id;
                
        //send the packet
        this.sendPacket(packet, UUID.fromString(client));
    }
    
    public void sendRespawnPacket(UUID clientID)
    {     
        //buld packet
        RespawnPacket packet = new RespawnPacket();
      
        //send the packet
        this.sendPacket(packet, clientID);
    }
    
    public void sendCursorChangePacket(UUID clientID, CursorType cursor)
    {
        //build packet
        CursorChangePacket packet = new CursorChangePacket();
        packet.cursor = cursor;
        
        //send the packet
        this.sendPacket(packet, clientID);
    }
    
    public void sendMovePlayerToPointPacket(UUID clientID, SylverVector2f point)
    {
        MovePlayerToPointPacket packet = new MovePlayerToPointPacket();
        packet.point = point;
        
        //send the packet
        this.sendPacket(packet, clientID);
    }
    
    public void sendSaveGamePacket(UUID clientID)
    {
        //build packet
        SaveGamePacket packet = new SaveGamePacket();
        
        //send the packet
        this.sendPacket(packet, clientID); 
    }
    
    public void sendCompleteLevelPacket(UUID clientID, Packet packet)
    {
        this.sendPacket(packet,clientID);
    }
    
    public void sendOpenInstructionalTipPacket(UUID clientID, InstructionalTip tipID)
    {
        OpenInstructionalTipPacket p = new OpenInstructionalTipPacket();
        p.tipID = tipID;
        
        this.sendPacket(p, clientID);
    }
    
    public void sendCloseInstructionalTipPacket(UUID clientID, InstructionalTip tipID)
    {
        CloseInstructionalTipPacket p = new CloseInstructionalTipPacket();
        p.tipID = tipID;
        
        this.sendPacket(p, clientID);
    }
    
    public void sendEntityHoverPacket(UUID clientID, String hoverID, boolean isHover,boolean inRange)
    {
        HoverEntityPacket packet = new HoverEntityPacket();
        packet.currentlyHovering = isHover;
        packet.hoveredID = hoverID;
        packet.inRange = inRange;
        
        this.sendPacket(packet, clientID);
    }
    
    public void sendSkillCooldownPacket(UUID clientID, SkillID skill)
    {
        SkillCooldownPacket packet = new SkillCooldownPacket();
        packet.skill = skill;
        
        this.sendPacket(packet, clientID);
    }
    
    //==============================
    //Handling Messages From Clients
    //==============================
    
    public void delegatePacket(Packet packet)
    {
        if(packet instanceof ClientInputPacket)
        {
            handleClientInputPacket((ClientInputPacket)packet);
        }
        else if(packet instanceof SkillDataPacket)
        {
            handleSkillDataPacket((SkillDataPacket)packet);
        }
        else if(packet instanceof BuyPotionPacket)
        {
            handleBuyPotionPacket((BuyPotionPacket)packet);
        }
        else if(packet instanceof ChooseLevelPacket)
        {
            handleChooseLevelPacket((ChooseLevelPacket)packet);
        }       
        else if(packet instanceof ClientChatPacket)
        {
            handleClientChatPacket((ClientChatPacket)packet);
        }
        else if(packet instanceof BuySkillPacket)
        {
            handleBuySkillPacket((BuySkillPacket)packet);
        }
        else if(packet instanceof RespawnRequestPacket)
        {
            handleRespawnRequestPacket((RespawnRequestPacket)packet);
        }
        else if(packet instanceof BuyStatPacket)
        {
            handleBuyStatPacket((BuyStatPacket)packet);
        }
        else if (packet instanceof DialogueClosedPacket)
        {
            handleDialogueClosedPacket((DialogueClosedPacket)packet);
        }
    }
    
    public void handleBuySkillPacket(BuySkillPacket packet)
    {
        PlayerEntity playerReference = this.clientsInScene.get(packet.getClientID()).player;
        SkillID skillid = packet.skill;
        Skill skill = SkillFactory.getInstance().getSkill(skillid);
        
        //unlock skill
        if(playerReference.getSkillManager().getSkillPoints() >= skill.getUnlockCost())
        {
            //subtract skill points from our pool
            playerReference.getSkillManager().setSkillPoints(playerReference.getSkillManager().getSkillPoints() - skill.getUnlockCost());

            //learn the skill
            playerReference.getSkillManager().learnSkill(skill.getSkillID());
        }
    }
    
    public void handleClientInputPacket(ClientInputPacket packet)
    {
        //get the client data      
        ClientData clientData = this.clientsInScene.get(packet.getClientID());

        if(!clientData.loadingLevel)
        {
            //add the input packet to the queue
            clientData.clientInputPacketQueue.add(packet);
        }
        
    }
    
    public void handleClientChatPacket(ClientChatPacket packet)
    {
        String chatString = packet.chatMessage;
    
        //Add for all players.  In the future, we can handle whispering, etc here.
        String playerName = ((GobletServer)Game.getInstance().getRunnable("Goblet Server")).connectedClients.get(packet.getClientID()).player.getName();
        ((GobletServer)Game.getInstance().getRunnable("Goblet Server")).addGlobalChat("[" + playerName + "] " + chatString);
        
             
    }
    
    public void handleSkillDataPacket(SkillDataPacket packet)
    {      
        //handle the packet
        PlayerEntity player =  this.clientsInScene.get(packet.getClientID()).player;
        player.setSkillAssignment(packet.skill1, 1);
        player.setSkillAssignment(packet.skill2, 2);      
        player.setSkillAssignment(packet.skill3, 3);      
        player.setSkillAssignment(packet.skill4, 4);      
    }
    
    public void handleBuyPotionPacket(BuyPotionPacket potionPacket)
    {
        
        PlayerEntity player = this.clientsInScene.get(potionPacket.getClientID()).player;
        
        //subtract money from player
        boolean success = player.getCurrencyManager().getBalence()>=PotionManager.POTION_PRICE && player.getPotionManager().getNumberOfPotions() < player.getPotionManager().getMaxPotions();

        //if the subtraction succeeded, give potion to player
        if(success)
        {
            player.getCurrencyManager().subtractCurrency(PotionManager.POTION_PRICE);
            player.getPotionManager().addPotion(1);
        }   
    }  
    
    public void handleBuyStatPacket(BuyStatPacket statPacket)
    {
        
        PlayerEntity player = this.clientsInScene.get(statPacket.getClientID()).player;
        
        //subtract money from player
        boolean success = player.getCurrencyManager().getBalence()>=player.getArmorManager().armorStats.get(statPacket.statId).cost 
                          && player.getArmorManager().armorStats.get(statPacket.statId).points < player.getArmorManager().armorStats.get(statPacket.statId).maxPoints;

        //if the subtraction succeeded, give potion to player
        if(success)
        {
            player.getCurrencyManager().subtractCurrency(player.getArmorManager().armorStats.get(statPacket.statId).cost);
            player.getArmorManager().armorStats.get(statPacket.statId).addPoint(1); 
        }   
    }     
    
    public void handleChooseLevelPacket(ChooseLevelPacket packet)
    {
        String levelDataReferenc = this.players.get(0).getLevelProgressionManager().levelMap.get(packet.selectedLevel).levelDataName;
        ((GobletServer)Game.getInstance().getRunnable("Goblet Server")).queueMovePlayerToLevel(packet.getClientID().toString(), levelDataReferenc, "checkpoint1"); 
    }
    
    public void handleRespawnRequestPacket(RespawnRequestPacket packet)
    {
        PlayerEntity player = this.clientsInScene.get(packet.getClientID()).player;
        
        //tell the player to respawn when enter town
        player.respawnWhenEnterTown = true;

        //move to town
        ((GobletServer)Game.getInstance().getRunnable("Goblet Server")).queueMovePlayerToLevel(packet.getClientID().toString(), "town.lv", "checkpoint1");
      
    }
    
    public void handleDialogueClosedPacket(DialogueClosedPacket packet)
    {
        
        ClientData clientData = this.clientsInScene.get(packet.getClientID());
        
        clientData.dialogueClosed = true;
        
        
        //if all clients dialogues are closed, 
        boolean allClosed = true;
        for(ClientData client: this.clientsInScene.values())
        {
            if(client.dialogueClosed == false)
            {
                allClosed = false;
            }
        }
        
        if(allClosed)
        {
            
            //reset to false
            for(ClientData client: this.clientsInScene.values())
            {
                client.dialogueClosed = false;
            }
            
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
          
         
    }
     
    
}
