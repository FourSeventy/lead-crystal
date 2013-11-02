package com.silvergobletgames.leadcrystal.scenes;

import com.silvergobletgames.leadcrystal.netcode.SizeTest;
import com.silvergobletgames.leadcrystal.netcode.SerializationRegistrator;
import com.silvergobletgames.leadcrystal.netcode.CursorChangePacket;
import com.silvergobletgames.leadcrystal.netcode.ChatPacket;
import com.silvergobletgames.leadcrystal.netcode.BuyPotionPacket;
import com.silvergobletgames.leadcrystal.netcode.MovePlayerToPointPacket;
import com.silvergobletgames.leadcrystal.netcode.SkillDataPacket;
import com.silvergobletgames.leadcrystal.netcode.OpenDialoguePacket;
import com.silvergobletgames.leadcrystal.netcode.ArmorAdjustPacket;
import com.silvergobletgames.leadcrystal.netcode.DisconnectRequest;
import com.silvergobletgames.leadcrystal.netcode.ClientInputPacket;
import com.silvergobletgames.leadcrystal.netcode.SoundDataPacket;
import com.silvergobletgames.leadcrystal.netcode.ChooseLevelPacket;
import com.silvergobletgames.leadcrystal.netcode.JoinRequest;
import com.silvergobletgames.leadcrystal.netcode.BuyArmorPacket;
import com.silvergobletgames.leadcrystal.netcode.RenderDataPacket;
import com.silvergobletgames.leadcrystal.netcode.SaveGamePacket;
import com.silvergobletgames.leadcrystal.netcode.CloseMenuPacket;
import com.silvergobletgames.leadcrystal.netcode.RespawnPacket;
import com.silvergobletgames.leadcrystal.netcode.ChangeLevelPacket;
import com.silvergobletgames.leadcrystal.netcode.OpenMenuPacket;
import com.silvergobletgames.leadcrystal.netcode.ConnectionException;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.ClientPlayerEntity;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.core.EnhancedViewport;
import com.silvergobletgames.leadcrystal.menus.Hud;
import com.silvergobletgames.leadcrystal.core.LevelData;
import com.silvergobletgames.leadcrystal.core.SaveGame;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.jogamp.newt.event.KeyEvent;
import com.silvergobletgames.leadcrystal.combat.LevelProgressionManager;
import com.silvergobletgames.leadcrystal.core.*;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.cutscenes.CutsceneManager;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.sylver.core.*;
import com.silvergobletgames.sylver.core.SceneEffectsManager.PostEffectExecutor;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.OpenGLGameWindow;
import com.silvergobletgames.sylver.graphics.RenderingPipelineGL3;
import com.silvergobletgames.sylver.netcode.*;
import com.silvergobletgames.sylver.util.SerializableEntry;
import java.awt.Point;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.media.opengl.GL3bc;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.World;
import net.phys2d.raw.strategies.QuadSpaceStrategy;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorID;
import com.silvergobletgames.leadcrystal.netcode.*;
import com.silvergobletgames.leadcrystal.netcode.JoinResponse.ReasonCode;
import com.silvergobletgames.sylver.audio.AudioRenderer;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.SceneObject.CoreGroups;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import net.phys2d.raw.*;


public final class GameClientScene extends Scene 
{
    
    //======================
    // Client Specific Data
    //=====================
    
    //the client object we will use to communicate to the server
    private Client client;
    //ClientID assigned to this client
    protected UUID clientID;     
    //packetSequencer
    private PacketSequencer packetSequencer = new PacketSequencer();
    //sent packets map
    private LinkedHashMap<Long,Packet> sentPacketsMap = new LinkedHashMap(150,.75f, false){ 
            private static final int MAX_ENTRIES = 100;

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
               return size() > MAX_ENTRIES;
            }};
    //set of recieved packet sequence numbers ( max size 30)
    private LinkedHashMap<Long,Packet> receivedSequenceNumbers = new LinkedHashMap(30,.75f,false){ 
            private static final int MAX_ENTRIES = 30;

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
               return size() > MAX_ENTRIES;
            }};
    //remove sequence number
    private long remoteSequenceNumber;   
    //player historical position data
    private LinkedHashMap<Long,PlayerPredictionData> playerHistoricalData = new LinkedHashMap(450){ 
            private static final int MAX_ENTRIES = 300;

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > MAX_ENTRIES;
            }};
    private long lastInputPacket = 0; 
    private HashMap<Long,ClientInputPacket> historicalInputMap = new LinkedHashMap(450){ 
            private static final int MAX_ENTRIES = 30;

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > MAX_ENTRIES;
            }};
    //image animation list
    private CopyOnWriteArrayList <Image> imageUpdateList = new CopyOnWriteArrayList();
    //host data used for host specific
    public LevelProgressionManager hostLevelProgression;
    //ping stuff
    private long pingTimer = 0;
    private int lastPingTime = 0;
    private long lostPackets = 0;
    //lock input 
    public AtomicBoolean lockInput = new AtomicBoolean(false);
    
    //==========================================
    // Incoming Data
    // - This is the data that will be coming 
    //   in from the server
    //==========================================
    
    //incoming packet queue
    public ConcurrentLinkedQueue<Packet> incomingPacketQueue = new ConcurrentLinkedQueue();
    
    //the last packets of scene data recieved from the server
    private long renderPacketArrivedTimestamp;
    private final int interpolationTime = 50; //50ms   this is the time in ms between expected renderData packets
    private RenderDataPacket workingRenderDataPacket;
    private ConcurrentLinkedQueue<RenderDataPacket> renderDataQueue = new ConcurrentLinkedQueue<>();
    
    //sound data queue
    private SoundDataPacket workingSoundDataPacket;
    private ConcurrentLinkedQueue<SoundDataPacket> soundDataQueue = new ConcurrentLinkedQueue<>();
    
    //queued level change
    private ChangeLevelPacket workingChangeLevel;
    
    
    //===============
    // Scene Data
    //===============
       
    //physics world
    public World physicsWorld;
    private ClientCollisionHandler collisionHandler = new ClientCollisionHandler();  
    //Player Entity
    public ClientPlayerEntity player;    
    public PlayerEntity playerServerTime;
    //HUD
    public Hud hud;  
    //World Mouse Location
    public Point worldMouseLocation = new Point(0, 0); 
    private boolean mouseHoverInRange = false;
    //active save game file
    private SaveGame activeSaveGame;
    //active level data
    public LevelData activeLevelData;   
    //Map editor flag (loaded level path)
    private String mapEditorPath;    
    //cutscene manager
    private CutsceneManager cutsceneManager;
    
   
    
    
  
    //================
    //Constructors
    //================
    
    public GameClientScene(SaveGame save)
    {                             
        
        //creates client ID 
        this.clientID = UUID.randomUUID();
        
        //set modified viewport for this scene
        this.setViewport(new EnhancedViewport()); 
        
        //build physics world
        physicsWorld = new World(new Vector2f(0.0f, -45.0f), 10, new QuadSpaceStrategy(20, 5));
        physicsWorld.addListener(collisionHandler);
        physicsWorld.enableRestingBodyDetection(1f, 1f, 1f);
        
        //save data
        this.activeSaveGame = save;
        
        //initializes player and adds it to the scene
        player = new ClientPlayerEntity(activeSaveGame.getPlayer());
        player.setID(this.clientID.toString());  
        //player server time
        playerServerTime = new PlayerEntity(player.getImage().copy(),player.getHead().copy(),player.getBackArm().copy(),player.getFrontArm().copy());
        playerServerTime.getImage().setColor(new Color(1,1,1,.5f));
        
     
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
    
    //=======================
    //Scene Interface Methods
    //=======================
    
    public void update() 
    {  
        //handle all packets queued up from the server, dont handle packets if we are in the middle of a level change
        while(!this.incomingPacketQueue.isEmpty() && workingChangeLevel == null)
        {
            this.delegatePacket(this.incomingPacketQueue.poll());
        }

        //assign working render data
        this.workingRenderDataPacket = this.renderDataQueue.poll();

        //assign working sound data
        this.workingSoundDataPacket = this.soundDataQueue.poll();       

        //clear the physics world of resting bodies (this needs to be done for isTouching() to work)
        physicsWorld.clearRestingState(); 
        //step physics world
        physicsWorld.step(5 / 60F);
        //resolve collisions
        this.collisionHandler.resolveCollisions();    
       
        //save player historical data
        this.playerHistoricalData.put(this.lastInputPacket, player.dumpPredictionData()); 


        //===========================
        //Apply Changes from Server
        //===========================
        if(this.workingRenderDataPacket != null)
        {                 

            //build new sceneObjects and add them to the appropriate layer
            if(this.workingRenderDataPacket.newSceneObjects != null)
            {
                for(SerializableEntry<SceneObjectRenderData,Layer> objectEntry: this.workingRenderDataPacket.newSceneObjects)
                {
                    SceneObjectRenderData objectRenderData =objectEntry.getKey();
                    Layer objectLayer = objectEntry.getValue();

                    //pass object render data to factory to get scene object
                    SceneObject object = SceneObjectDeserializer.buildSceneObjectFromRenderData(objectRenderData);

                    //if its not our player add it to the world
                    if(!object.getID().toString().equals(this.clientID.toString()))                           
                        this.add(object,objectLayer); 

                }
                this.workingRenderDataPacket.newSceneObjects = null;
            }



            //reconcile sceneObjects
            if(this.workingRenderDataPacket.renderData != null)
            {
                for(SceneObjectRenderDataChanges renderDataChanges: this.workingRenderDataPacket.renderData)
                {
                    //get the scene object that the render data changes apply to
                    SceneObject sceneObject = this.getSceneObjectManager().get(renderDataChanges.ID);

                    //if the data doesnt concern our player apply it
                    if(sceneObject != null && !(renderDataChanges.ID.equals( this.clientID.toString())) && sceneObject instanceof NetworkedSceneObject)
                    { 
                        ((NetworkedSceneObject)sceneObject).reconcileRenderDataChanges(this.renderPacketArrivedTimestamp - interpolationTime,this.renderPacketArrivedTimestamp,renderDataChanges);        

                    }   
                    //if the data does concern our player, handle the special case
                    else if(renderDataChanges.ID.equals( this.clientID.toString()))
                    {        
                        //keep track of our player in server time
                        this.playerServerTime.reconcileRenderDataChanges(1,1,renderDataChanges);  
                        this.playerServerTime.interpolate(1);

                        //reconcile player data
                        this.player.reconcileRenderDataChanges(this.renderPacketArrivedTimestamp - interpolationTime,this.renderPacketArrivedTimestamp,renderDataChanges);                          

                    }                      
                }                                           
            }

            //==================
            // Reconcile Player
            //==================
            {
                //get the historical data that corresponds with the data coming from the server
                PlayerPredictionData historicalData =this.playerHistoricalData.get(this.workingRenderDataPacket.correspondingInputPacket);
                PlayerPredictionData serverData = (PlayerPredictionData)this.workingRenderDataPacket.playersPredictionData;
                
                //if we got prediction data from the server this tick
                if(serverData != null)
                {                   

                    //check to see if a prediction error occured            
                    boolean error = false;
                    if(historicalData != null)
                    {
                        error = !historicalData.equals(serverData);
                    }
                    else
                    {
                            System.out.println("historical data null : " + Long.toString(this.workingRenderDataPacket.correspondingInputPacket));
                    }

                    //========================
                    // Handle Prediction Error
                    //=========================
                    if( error == true)
                    {
                        //error reporting
                        if(GameplaySettings.getInstance().networkDebugging)
                        {
                            System.out.println("Client Side Prediction Error: " + System.currentTimeMillis());
                            //in game message
                            hud.chatManager.receiveMessage("Client Side P Error"); 
                            //debugging data
                            if(historicalData != null)
                            {
                                System.out.print("Historical: "+ "Position: (" + historicalData.positionX + "," +  historicalData.positionY + ")" + "   Velocity: (" + historicalData.velocityX +"," +historicalData.velocityY + ")       ");
                                System.out.println("Server: " + "Position:  (" + serverData.positionX+ "," + serverData.positionY+ ")" + "   Velocity: (" + serverData.velocityX +"," +serverData.velocityY + ")");
                            }
                            
                        }
                        
                        //reconcile the player, back to server position
                        this.player.reconcilePredictionData(historicalData, serverData); 
                        
                        //get all inputs since the error
                        long inputNumber = this.workingRenderDataPacket.correspondingInputPacket;
                        ArrayList<ClientInputPacket> historicalInputList = new ArrayList();
                        for(int i = 1; i < 100; i++)
                        {
                            ClientInputPacket packet = this.historicalInputMap.get((long)(inputNumber +i)); 
                            if(packet != null)
                                historicalInputList.add(packet);
                        }
                       
                        //use historical inputs to simulate back to current time
                        for(ClientInputPacket inputPacket: historicalInputList)
                        {
                            if(player.dashing)
                                continue;
                            
                            //simulate player forces
                            this.player.simulateInputForPredictionErrorCorrection(inputPacket);
                            
                            //clear the physics world of resting bodies (this needs to be done for isTouching() to work)
                            physicsWorld.clearRestingState(); 
                            //step physics world
                            physicsWorld.step(5 / 60f);
                            //resolve collisions
                            this.collisionHandler.resolveCollisions();                                                        

                             //repopulate historical positions
                            this.playerHistoricalData.put(inputPacket.getSequenceNumber(), player.dumpPredictionData()); 
                        }
                       
                                              
                    }
                }
                else
                    System.out.println("player prediction data is null from sever");
            }

            //remove old sceneObjects
            if(this.workingRenderDataPacket.removeSceneObjects !=  null)
            {
                for(String oldObject:this.workingRenderDataPacket.removeSceneObjects)
                {
                    SceneObject obj = this.getSceneObjectManager().get(oldObject);

                    this.remove(obj);
                }
                this.workingRenderDataPacket.removeSceneObjects = null;
            }                               


        }

        //==========================
        //Handle sound
        //==========================
        if(this.workingSoundDataPacket != null)
        {

            for (Sound sound : workingSoundDataPacket.soundData)
            {
                AudioRenderer.playSound(sound);
            }

            this.workingSoundDataPacket.soundData.clear();
        }            

        //==========================
        //update client side objects
        //==========================

        //set the world mouse location 
        // world mouse location is going to be the viewport postion + how far into the viewport the cursor is poportional to the current viewport size
        InputSnapshot inputSnapshot = Game.getInstance().getInputHandler().getInputSnapshot();
        int mouseX = (int)(getViewport().getBottomLeftCoordinate().x + (((float)inputSnapshot.getScreenMouseLocation().x / (float)Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x)  *  getViewport().getWidth()));     
        int mouseY = (int)(getViewport().getBottomLeftCoordinate().y + ((float)(inputSnapshot.getScreenMouseLocation().y / (float)Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().y)  *  getViewport().getHeight())); 
        MapEditorScene.worldMouseLocation = new Point(mouseX, mouseY);


        //update player
        player.update();
        
        //update hud
        hud.update();

        //update scene effects manager
        this.getSceneEffectsManager().update();

        //move viewport
        handleViewportMovement();
        
        //Move the sound listener
        Game.getInstance().getAudioRenderer().setListenerPosition(player.getPosition().x, player.getPosition().y, 500);
        Game.getInstance().getAudioRenderer().setListenerVelocity(player.getBody().getVelocity().getX(), player.getBody().getVelocity().getY(), 0);

        //update emitters
        ArrayList<SceneObject> emitterUpdateList = this.getSceneObjectManager().get(CoreGroups.EMITTER);
        for(SceneObject emitter: emitterUpdateList)
        {
            emitter.update();
        }

        //update images
        for(Image image: this.imageUpdateList)
        {
            image.update();
        }

        //Update light effects
        for (SceneObject lightSourceObject : this.getSceneObjectManager().get(CoreGroups.LIGHTSOURCE))
        {
            ((LightSource)lightSourceObject).update();
        }
        
        //Update light effects
        for (SceneObject textObjects : this.getSceneObjectManager().get(CoreGroups.TEXT))
        {
            ((Text)textObjects).update();
        }

        //ping
        pingTimer++;
        if(pingTimer % 60 == 0)
            this.client.updateReturnTripTime();

        if (this.client.getReturnTripTime() != -1)
        {  
            this.lastPingTime = this.client.getReturnTripTime();
            hud.pingText.getText().setText("Ping: " +Integer.toString(this.client.getReturnTripTime()) + "ms");
        }

        //FPS
        if(pingTimer %60 == 0)
            hud.fpsText.getText().setText(Float.toString(Game.getInstance().getGraphicsWindow().getFPS()) + " FPS");

        //make sure we are still connected, if not save and exit to the main menu
        if(!client.isConnected())
        {
            this.saveGameToDisk();
//            Game.getInstance().unloadScene(GameClientScene.class);
//            if(!Game.getInstance().isLoaded(MainMenuScene.class))
//                Game.getInstance().loadScene(new MainMenuScene());
//            
//            Game.getInstance().changeScene(MainMenuScene.class, null);
        }       
        
        //update cutscene
        this.cutsceneManager.update();       
     
    }
    
    /**
     * Uses RenderingPipelineGL2 for rendering.
     * @param gl The graphics context 
     */
    public void render(GL2 gl)
    {    
        
        //interpolate everything
        this.interpolateSceneObjects();
       
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

            gl.glColor4f(1,0,0,1);
            gl.glLineWidth(2);
            gl.glBegin(GL3bc.GL_LINES);
                gl.glVertex2f(viewport.topLine.getStart().getX(),viewport.topLine.getStart().getY());
                gl.glVertex2f(viewport.topLine.getEnd().getX(),viewport.topLine.getEnd().getY());
            gl.glEnd();

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
    * Handles input which can include client input.
    */
    public void handleInput() 
    {

        //input snapshot
        InputSnapshot inputSnapshot = Game.getInstance().getInputHandler().getInputSnapshot();
        
        //======================= 
        // Handle Mouse Location
        //=======================           

        //calculate mouse position
        int mouseX = (int) (((float) inputSnapshot.getScreenMouseLocation().x / getViewport().getWidth()) * getViewport().getWidth() + getViewport().getBottomLeftCoordinate().x);
        int mouseY = (int) (((float) inputSnapshot.getScreenMouseLocation().y / getViewport().getHeight()) * getViewport().getHeight() + getViewport().getBottomLeftCoordinate().y);
        this.worldMouseLocation = new Point(mouseX, mouseY);
        
        //==============
        // Handle Chat
        //==============
        
        //toggle chat prompt       
        if(this.lockInput.get() == false)
        {
            if(inputSnapshot.isKeyReleased(KeyEvent.VK_ENTER)) 
            {
                if (hud.chatManager.chatPromptOpen)
                {
                    hud.chatManager.sendMessage();
                    hud.chatManager.closePrompt();
                }
                else
                {
                    hud.chatManager.openPrompt();
                }
            }
        }

        
              
        
        //================
        //Keyboard input
        //================
        
        if(!hud.chatManager.chatPromptOpen && this.lockInput.get() == false)
        {
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
                player.handleJumping(this.worldMouseLocation.x,this.worldMouseLocation.y);
            }

            //jump released
            if (inputSnapshot.isKeyReleased(KeyEvent.VK_SPACE) == true)
            {
                player.handleJumpReleased();
            }

            //down
            if (inputSnapshot.isKeyPressed(com.jogamp.newt.event.KeyEvent.VK_S) == true)
            {
                player.move(new SylverVector2f(0,-1));
            }

            //up
            if (inputSnapshot.isKeyPressed(com.jogamp.newt.event.KeyEvent.VK_W) == true)
            {
                player.move(new SylverVector2f(0,1));
            }

            //toggle flashlight
            if (inputSnapshot.isKeyReleased(KeyEvent.VK_P) == true)
            {
                player.toggleFlashlight();
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


            //open esc menu
            if (inputSnapshot.isKeyReleased(KeyEvent.VK_ESCAPE))
            {
                if (this.mapEditorPath == null) 
                    hud.escapeMenu.toggle();
                else
                {
                    this.sendDisconnectRequest();                       
                    ArrayList args = new ArrayList();
                    args.add(mapEditorPath);
                    args.add(this.activeSaveGame);
                    Game.getInstance().unloadScene(GameClientScene.class);
                    Game.getInstance().changeScene(MapEditorScene.class,args);  
                }
            }

            //open skill menu
            if (inputSnapshot.isKeyReleased(KeyEvent.VK_T)) 
            {
                hud.skillMenu.toggle();
            }
            //open inventory menu
            if (inputSnapshot.isKeyReleased(KeyEvent.VK_I)) 
            {
                //toggle the inventory menu
                hud.inventoryMenu.toggle();
            }
            //quest menu
            if (inputSnapshot.isKeyReleased(com.jogamp.newt.event.KeyEvent.VK_L))
            {
                hud.questMenu.toggle();
            }

            //use hotbar skills 
            if (inputSnapshot.isKeyReleased(com.jogamp.newt.event.KeyEvent.VK_Q))
            {
                if (this.player.getSkillAssignment(3)  != null && player.getSkillManager().getSkill(this.player.getSkillAssignment(3)).isUsable())
                {
                    player.useActionBarSkill(this.player.getSkillAssignment(3));
                }
            }
            if (inputSnapshot.isKeyReleased(com.jogamp.newt.event.KeyEvent.VK_E))
            {
                if (this.player.getSkillAssignment(4) != null && player.getSkillManager().getSkill(this.player.getSkillAssignment(4)).isUsable())
                {
                    player.useActionBarSkill(this.player.getSkillAssignment(4));
                }
            }
            
            //debug
            if (inputSnapshot.isKeyReleased(com.jogamp.newt.event.KeyEvent.VK_M))
            {
                sendSizeTest();
            }



            //==============
            // Mouse Input
            //==============
            
            this.player.setWorldMouseLocationPoint(mouseX, mouseY); 
            if (inputSnapshot.isMouseDown() && !hud.isMouseOverMenu() && !this.mouseHoverInRange)
            {
                if (inputSnapshot.buttonClicked() == 1 )
                {
                    if (this.player.getSkillAssignment(1) != null && player.getSkillManager().getSkill(this.player.getSkillAssignment(1)).isUsable())
                    {
                        player.useActionBarSkill(this.player.getSkillAssignment(1));
                    }
                }
                else if(inputSnapshot.buttonClicked() == 3)
                {                        
                    if (this.player.getSkillAssignment(2) != null && player.getSkillManager().getSkill(this.player.getSkillAssignment(2)).isUsable())
                    {
                        player.useActionBarSkill(this.player.getSkillAssignment(2));
                    }
                }
            }


            //===========================
            // Calculate Facing Direction
            //===========================
            if (player.getCombatData().canMove())
            {
                if (mouseX < player.getPosition().x)
                    player.face(FacingDirection.LEFT);
                else
                    player.face(FacingDirection.RIGHT);
            }


            //===================== 
            // Sent Input to Server 
            //=====================    
             this.sendInputPacket();
        
        }
        else
        {
            //send a blank input packet
            this.sendBlankInputPacket();
            
        }
    }

    /**
     * Adds a SceneObject to the Scene at the specified layer
     * @param item
     * @param layer 
     */
    public void add(SceneObject item, Layer layer)
    {
            
        //do special case stuff for entities
        if (item instanceof Entity) 
        {           
            //add the entity to the physics world if the entity is in the main layer and it has a static body
            if ((layer == Layer.MAIN &&((Entity)item).getBody() instanceof StaticBody && !(item instanceof HitBox)) || (layer == Layer.MAIN && item instanceof NonPlayerEntity)) 
            {
                if(item instanceof NonPlayerEntity)
                {
                    ((Entity) item).getBody().setGravityEffected(false);
                }
                physicsWorld.add(((Entity) item).getBody());
            }
            

            //if it has an image
            if(((Entity)item).getImage() != null)           
                this.imageUpdateList.add(((Entity)item).getImage());
            
            //strip out script objects
            ((Entity)item).setScriptObject(null);

        }
        else if(item instanceof Image)
        {
            //if it has an animated image, add it to the list
            this.imageUpdateList.add((Image)item);
        }
        
        //super method
        super.add(item,layer);
    
    }
    
    public void add(Sound sound)
    {
        AudioRenderer.playSound(sound);
    }

    public void remove(SceneObject item)
    {     
        
        //if the item is null print an error
        if(item == null)
        {
            //System.err.println("Attempting to remove a null object on client: " +  Thread.currentThread().getStackTrace());
            return;
        }
        
        if (item instanceof Entity)
        { 
            //remove the entity from the physics world if the entity was in the main layer
            if ((this.getSceneObjectManager().get(Layer.MAIN).contains(item) &&((Entity)item).getBody() instanceof StaticBody) || (this.getSceneObjectManager().get(Layer.MAIN).contains(item) && item instanceof NonPlayerEntity))
            {
                physicsWorld.clearArbiters(((Entity) item).getBody()); 
                physicsWorld.remove(((Entity) item).getBody());
            }
            
            //if it has an animated image, remove it from the list
            if(((Entity)item).getImage().isAnimated())
                this.imageUpdateList.remove(((Entity)item).getImage());
        }
        else if(item instanceof Image)
        {
             //remove it from the update list
             this.imageUpdateList.remove((Image)item);
        }    
        
        //super method
        super.remove(item);
               
    }
  
    public void sceneEntered(ArrayList args) 
    {       
        //reset viewport size
        getViewport().setDimensions(Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x, Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().y);
        
        //change cursor
        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.RETICULE));      
        
        //Check for map editor flag
        if (args != null && args.size() > 0)
        {
            this.mapEditorPath = (String)args.get(0);
        }
        else
            this.mapEditorPath = null;
        
        //fade from black
        getSceneEffectsManager().fadeFromBlack(new PostEffectExecutor()); 
    }
 
    public void sceneExited()
    {
        
    }
    
    
    //================
    //Class Methods
    //================
    
    /**
     * Attempts to connect the client scene to the given server. Spins until it hears back a response,
     * or times out after 5 seconds.
     * @param ip ip to connect to
     * @param port port to connect to
     * @throws ConnectionException Throws a connection exception error if it couldnt connect
     */
    public void connectToServer(String ip, int port) throws ConnectionException
    { 
        //loading screen text
        ((LoadingScene)Game.getInstance().getScene(LoadingScene.class)).setCurrentProgressText("Connecting To Server");
               
        //client initialization
        client = new Client(80_192  , 20_048 );      
        client.start();          
        Kryo kryo = client.getKryo();
        
        //set variable
        hud.serverIpText.getText().setText("IP: " +ip);
        hud.serverPortText.getText().setText("Port: " + Integer.toString(port));
        
        //register classes for serialization
        SerializationRegistrator.registerSerialization(kryo);
        
        //connect to server
        try
        {   //TODO handle connection errors better
            client.connect(10000, ip, port, port + 10);
        }
        catch(IOException e)
        {
            ConnectionException ex = new ConnectionException(ReasonCode.Connection);
            ex.exception = e;
            throw ex;
        }
        
        
        
        //build a join response variable and listener, also listen for level data
        final AtomicBoolean joinResponseReceived = new AtomicBoolean();
        final JoinResponse joinResponsePacket = new JoinResponse();
        final AtomicBoolean levelDataReceived = new AtomicBoolean();
        final ChangeLevelPacket levelPacket = new ChangeLevelPacket();
        Listener joinResponseListener =new Listener() 
        {           
             public void received(Connection connection, Object object) 
             {                     
                //if we haven't already seen this packet, handle it
                if( (object instanceof Packet) &&!receivedSequenceNumbers.containsKey(((Packet)object).getSequenceNumber()))
                {   
                    //initial packet handling
                    initialPacketHandling((Packet)object);
                    
                    //if we get a join response, handle it
                    if(object instanceof JoinResponse)
                    {
                        joinResponseReceived.compareAndSet(false, true);
                        joinResponsePacket.okToJoin =((JoinResponse)object).okToJoin;
                        joinResponsePacket.reasonCode = ((JoinResponse)object).reasonCode;
                        joinResponsePacket.hostProgressionManager = ((JoinResponse)object).hostProgressionManager;
                    }
                    if(object instanceof ChangeLevelPacket)
                    {
                        levelDataReceived.compareAndSet(false, true);
                        levelPacket.gameLevelName =((ChangeLevelPacket)object).gameLevelName;
                        levelPacket.levelDifferences = ((ChangeLevelPacket)object).levelDifferences;
                    }
                    else
                    {
                        //add to packet queue to be handled
                        incomingPacketQueue.add((Packet)object);
                    }
                }
             }
        };
        client.addListener(joinResponseListener);
                
        //start handshake
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.rawSaveData = this.activeSaveGame.getRawData();
        this.sendPacket(joinRequest);  
        
        //wait for response from the server
        long tries = 0;
        while(joinResponseReceived.get() != true)
        {
            tries++;        
            //if it takes longer than 5 seconds return false
            if(tries > 200 * 5)
                throw new ConnectionException(ReasonCode.NoResponse);
            
            //sleep a bit
            try
            {
                Thread.sleep(5);
            }
            catch(Exception e)
            {System.err.println("problem sleeping");}
            
            ((LoadingScene)Game.getInstance().getScene(LoadingScene.class)).setCurrentProgressText("Completing Handshake");
        }
               
        //check join response
        if(joinResponsePacket.okToJoin == false)
        {
            throw new ConnectionException(joinResponsePacket.reasonCode);
        }
        else
        {
            this.hostLevelProgression = LevelProgressionManager.buildFromRenderData(joinResponsePacket.hostProgressionManager);
        }
        
        //wait for level data
        tries = 0;
        while(levelDataReceived.get() != true)
        {
            tries++;
            //if it takes longer than 5 seconds return false
            if(tries > 200 * 5)
                throw new ConnectionException(ReasonCode.NoResponse);
            
            //sleep a bit
            try
            {
                Thread.sleep(5);
            }
            catch(Exception e){System.err.println("problem sleeping");}
            
            ((LoadingScene)Game.getInstance().getScene(LoadingScene.class)).setCurrentProgressText("Loading Level");
        }
        
        //once we have level data, load the level
        this.loadLevel(levelPacket);
        
        //reticulating splines
        ((LoadingScene)Game.getInstance().getScene(LoadingScene.class)).setCurrentProgressText("Reticulating Splines");
        
        
        //remove handshake listener
        client.removeListener(joinResponseListener);
        
        
        //add final listener for packets
        client.addListener(new Listener() 
        {           
             public void received(Connection connection, Object object) 
             {
                     
                //if we havnt already seen this packet, handle it
                if( (object instanceof Packet) &&!receivedSequenceNumbers.containsKey(((Packet)object).getSequenceNumber()))
                {
                    //perform initial packet handling ( sequence number handling etc)
                    initialPacketHandling((Packet)object);
                    
                    //add to packet queue to be handled
                    incomingPacketQueue.add((Packet)object);

                }
             }
        });  
            
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
    
    public void saveGameToDisk()
    {
        //build the save
        SaveGame save = new SaveGame();
        save.setPlayer(this.player);
        //actually save it
        save.save(this.activeSaveGame.fileName);
    }    
    
    public void loadLevel(ChangeLevelPacket packet)
    {
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
        //refresh physics world        
        physicsWorld.clear();

        //load the level
        LevelData levelData = LevelLoader.getInstance().getLevelData(packet.gameLevelName);
        

        //set active level data
        this.activeLevelData = levelData;
        this.getSceneEffectsManager().sceneAmbientLight = levelData.getAmbientLight();
        

        //load the level into the scene
        ArrayList<SimpleEntry<SceneObject,Layer>> sceneObjectList = levelData.getSceneObjects();
        for(SimpleEntry<SceneObject,Layer> entry:sceneObjectList)
        {
            this.add(entry.getKey(), entry.getValue());
        }

        //remove things from the remove list
        for(String oldObject:packet.levelDifferences.removeSceneObjects)
        {
            SceneObject obj = this.getSceneObjectManager().get(oldObject);

            this.remove(obj);
        }         

        //update things from the update list          
        for(SceneObjectRenderDataChanges changes :packet.levelDifferences.renderData)
        {

            SceneObject sceneObject = this.getSceneObjectManager().get(changes.ID);
            if(sceneObject != null && sceneObject instanceof NetworkedSceneObject)
                ((NetworkedSceneObject)sceneObject).reconcileRenderDataChanges(0,1,changes);                  
        }


        //build new sceneObjects and add them to the appropriate layer
        for(SerializableEntry<SceneObjectRenderData,Layer> entry :packet.levelDifferences.newSceneObjects)
        {

            SceneObjectRenderData renderData = entry.getKey();
            Layer objectLayer = entry.getValue();

            //pass object render data to factory to get scene object
            SceneObject object = SceneObjectDeserializer.buildSceneObjectFromRenderData(renderData);

            //if its not our player add it to the scene
            if(!object.getID().toString().equals(this.clientID.toString())) 
                this.add(object,objectLayer); 

        }
        
        //interpolate everything
        this.interpolateSceneObjects();

        //add the player
        this.add(player,Layer.MAIN);
        this.physicsWorld.add(player.getBody());
        SceneObject savePoint = this.getSceneObjectManager().get("checkpoint1");
        player.setPosition(savePoint.getPosition().x,savePoint.getPosition().y);
        player.getBody().setVelocity(new Vector2f(0,0));
        
        //clear some player data
        this.mouseHoverInRange = false;       
       
        //clear player historical positions
        this.playerHistoricalData.clear();
        
        //lock input for 2 seconds
        Thread inputLock = new Thread(){
            
            @Override
            public void run()
            {
                lockInput.set(true);
                
                try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException ex){}
                
                lockInput.set(false);
                
            }
        };
        inputLock.start();
        
        //add player server time
        if(GameplaySettings.getInstance().drawPlayerServerTime == true)
        {
            this.add(playerServerTime,Layer.MAIN);
            playerServerTime.setPosition(savePoint.getPosition().x,savePoint.getPosition().y);
        }
        
        //gets the viewportBlockers
        ArrayList<SceneObject> list = this.getSceneObjectManager().get(ExtendedSceneObjectGroups.VIEWPORTBLOCKER);
        ArrayList<Entity> allBlockers = new ArrayList<>();
            for(SceneObject e:list)
                allBlockers.add((Entity)e);

        //center viewport
        ((EnhancedViewport)getViewport()).snapToCoordinateWithCorrection(player.getPosition().x, player.getPosition().y,allBlockers); 
        
        
        //set some flags
        this.hud.activeLevelName = levelData.filename;
        
        //reset mouse cursor to default
        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.RETICULE)); 
        
        //fade from black
        getSceneEffectsManager().fadeFromBlack(new PostEffectExecutor()); 
        
    }
    
    public void clearArbitersOnBody(Body body)
    {
        physicsWorld.clearArbiters(body); 
    }
    
    private void interpolateSceneObjects()
    {
        //=======================
        //Interpolate Everything
        //=======================
        ArrayList<SceneObject> parallaxInterp;
        for (Layer layer: Layer.values())
        {
            parallaxInterp = this.getSceneObjectManager().get(layer);
            for (int j = 0; j < parallaxInterp.size(); j++)
            {
                //assign local variable
                SceneObject currentObject = parallaxInterp.get(j);

                if(currentObject instanceof NetworkedSceneObject)
                {
                    //interpolate the SceneObject
                    ((NetworkedSceneObject)currentObject).interpolate(System.currentTimeMillis()-this.interpolationTime);
                }
            }
        }
    }
      
    
    //==============================
    //Sending Messages to the Server
    //==============================
    public void sendPacket(ClientPacket packet)
    {
        //give the packet its sequence number
        packet.setSequenceNumber(this.packetSequencer.nextSequenceNumber());
        
        //set the client id
        packet.setClientID(clientID);
        
        //mark last input packet
        if(packet instanceof ClientInputPacket)            
            this.lastInputPacket = packet.getSequenceNumber();
        
        //build the ack and the ackbitfield for this packet
        packet.setAck(this.remoteSequenceNumber);

        //build the bitfield
        byte bitfield = 0;
        for( byte i = 1; i<=8; i ++)
        {
            if(this.receivedSequenceNumbers.containsKey(this.remoteSequenceNumber - i))
            {
                bitfield += 1L << (i-1);
            }
        }
        packet.setAckBitfield(bitfield);
        
        //store the packet in local map
        this.sentPacketsMap.put(packet.getSequenceNumber(), packet);
        
        //send packet
        int size =this.client.sendUDP(packet);
        if(GameplaySettings.getInstance().packetSizeDebugging)
            System.out.println("Client Packet: " + "Size: " + size + " bytes " + " Type: " + packet.getClass().toString().substring(packet.getClass().toString().lastIndexOf(".") + 1, packet.getClass().toString().length())  );
            
          
    }
    
    public void sendSkillPacket()
    {
        //build the packet
        SkillDataPacket packet = new SkillDataPacket();
        packet.skill1 = this.player.getSkillAssignment(1);
        packet.skill2 = this.player.getSkillAssignment(2);
        packet.skill3 = this.player.getSkillAssignment(3);
        packet.skill4 = this.player.getSkillAssignment(4);
        
        this.sendPacket(packet);
                
    }
    
    public void sendDisconnectRequest()
    {
        DisconnectRequest request = new DisconnectRequest();
        
        this.sendPacket(request);
        this.client.stop();
    }
    
    private void sendInputPacket()
    {
        //build the data packet
        ClientInputPacket packet = new ClientInputPacket();
        InputSnapshot inputSnapshot = Game.getInstance().getInputHandler().getInputSnapshot();
        
        packet.pressedKeys= new HashSet(inputSnapshot.getPressedMap().keySet());        
        packet.releasedKeys=new HashSet(inputSnapshot.getReleasedMap().keySet()); 
        if(packet.pressedKeys.isEmpty())
            packet.pressedKeys = null;
        if(packet.releasedKeys.isEmpty())
            packet.releasedKeys = null;
        
        packet.mouseDown = inputSnapshot.isMouseDown();
        packet.mouseClicked =inputSnapshot.isMouseClicked();
        packet.mouseWheelRotation = (byte)inputSnapshot.getWheelRotation();
        packet.mouseButtonClicked = (byte)inputSnapshot.buttonClicked();
        
        packet.mouseLocationX = (short)this.worldMouseLocation.x;
        packet.mouseLocationY = (short)this.worldMouseLocation.y;
        packet.mouseOverMenu = hud.isMouseOverMenu();
        
        this.sendPacket(packet);
        
        //add packet to historical inputs
        this.historicalInputMap.put(packet.getSequenceNumber(), packet);
        
    }
    
    private void sendBlankInputPacket()
    {
         //build the data packet
        ClientInputPacket packet = new ClientInputPacket();
        this.sendPacket(packet);
    }
    
    public void sendBuyPotionPacket()
    {
        //build the packet
        BuyPotionPacket packet = new BuyPotionPacket();
        
        //send packet
        this.sendPacket(packet);
    }
    
    public void sendBuyArmorPacket(ArmorID id)
    {
        //build the packet
        BuyArmorPacket packet = new BuyArmorPacket();
        packet.armor = id;
        
        //send packet
        this.sendPacket(packet);
    }
    
    public void sendArmorAdjustPacket(ArmorID id)
    {
        //build the packet
        ArmorAdjustPacket packet = new ArmorAdjustPacket();
        packet.armorToEquip = id;
        
        //send packet
        this.sendPacket(packet);
    }
    
    public void sendBuySkillPacket(SkillID id)
    {
        //build the packet
        BuySkillPacket packet = new BuySkillPacket();
        packet.skill = id;
        
        //send packet
        this.sendPacket(packet);
    }
    
    public void sendChooseLevelPacket(int level)
    {
        ChooseLevelPacket packet = new ChooseLevelPacket();
        packet.selectedLevel = level;
        
        //send
        this.sendPacket(packet);
    }
    
    public void sendRespawnRequestPacket()
    {
        RespawnRequestPacket packet = new RespawnRequestPacket();
        
        //send
        this.sendPacket(packet);
    }
    
    private void resendPacket(long sequenceNumber)
    {
        if(GameplaySettings.getInstance().networkDebugging)
            System.out.println("Client ResendingPacket: " + sequenceNumber);
        
        //get packet to resend
        Packet packet = this.sentPacketsMap.get(sequenceNumber);
        
        //send the packet 
        try
        {  
            if(packet != null)
                this.client.sendUDP(packet);
        }
        catch(java.lang.IllegalStateException e){System.err.print("Resend Packet Fail: "); e.printStackTrace(System.err);}
        
        //set packetloss text
        this.lostPackets ++;
        float number = (((float)lostPackets)/sequenceNumber) * 100 ;
        DecimalFormat df = new DecimalFormat("00");
        
        hud.packetLossText.getText().setText("Packet Loss: " +df.format(number) + "%");
    }
    
    //debug method
    public void sendSizeTest()
    {
        SizeTest test = new SizeTest();
        System.out.println("Size test: " +this.client.sendUDP(test));
    }
    
    
    //==============================
    //Handling Messages From Server
    //==============================
    
    /**
     * Handles the UDP reliability for all incoming packets. Should be called for every received packet.
     * @param packet 
     */
    private void initialPacketHandling(Packet packet)
    {
        //note that we recieved this packet
        this.receivedSequenceNumbers.put(packet.getSequenceNumber(), packet);
        
        //set remote sequence number
        if(packet.getSequenceNumber() > this.remoteSequenceNumber)
            this.remoteSequenceNumber = packet.getSequenceNumber();

        //check server acks for packet loss and resend packets if its later than 150ms we resend
        if(packet.getAck() > 100) //wait 5 seconds since the game started before we start to resend missed packets
        {
            for(byte i = 3; i <=7; i ++) //the 3rd bit is the 150ms cutoff
            {
                // if the bit was not set
                if((packet.getAckBitfield() & (1L << i)) == 0)
                {
                    //resend it
                    this.resendPacket(packet.getAck() - i - 1);               
                }
            }
        }
    }   
    
    /**
     * Delegates the given packet to the correct packet handling method
     * @param object 
     */
    private void delegatePacket(Packet object)
    {
        if (object instanceof RenderDataPacket)
        {
            handleRenderDataPacket((RenderDataPacket)object);
        }
        else if (object instanceof SoundDataPacket)
        {
            handleSoundDataPacket((SoundDataPacket)object);
        }
        else if (object instanceof OpenDialoguePacket)
        {
            handleOpenDialoguePacket((OpenDialoguePacket)object);
        }
        else if(object instanceof RespawnPacket)
        {
            handleRespawnPacket((RespawnPacket)object);
        }
        else if(object instanceof setSideQuestStatusPacket)
        {
            handleSetSideQuestStatusPacket((setSideQuestStatusPacket)object);
        }
        else if(object instanceof CursorChangePacket)
        {
            handleCursorChangePacket((CursorChangePacket)object);
        }
        else if(object instanceof ChangeLevelPacket)
        {
            handleChangeLevelPacket((ChangeLevelPacket)object);
        }
        else if(object instanceof MovePlayerToPointPacket)
        {
            handleMovePlayerToPointPacket((MovePlayerToPointPacket)object);
        }
        else if(object instanceof SaveGamePacket)
        {
            handleSaveGamePacket((SaveGamePacket)object);
        }
        else if (object instanceof ChatPacket)
        {
            handleChatPacket((ChatPacket)object);
        }
        else if (object instanceof OpenMenuPacket)
        {
            handleOpenMenuPacket((OpenMenuPacket)object);
        }
        else if(object instanceof CloseMenuPacket)
        {
            handleCloseMenuPacket((CloseMenuPacket)object);
        }
        else if(object instanceof OpenInstructionalTipPacket)
        {
            handleOpenInstructionalTipPacket((OpenInstructionalTipPacket)object);
        }
        else if(object instanceof CloseInstructionalTipPacket)
        {
            handleCloseInstructionalTipPacket((CloseInstructionalTipPacket)object);
        }
        else if(object instanceof HostLevelProgressionAdjust)
        {
            handleHostLevelProgressionAdjust((HostLevelProgressionAdjust)object);
        }
        else if(object instanceof HoverEntityPacket)
        {
            handleHoverEntityPacket((HoverEntityPacket)object);
        }
        else if(object instanceof SideObjectiveCompletePacket)
        {
            handleSideObjectiveCompletePacket((SideObjectiveCompletePacket)object);
        }
        else if(object instanceof MainObjectiveCompletePacket)
        {
            handleMainObjectiveCompletePacket((MainObjectiveCompletePacket)object);
        }
    }
    
    private void handleChangeLevelPacket( final ChangeLevelPacket packet)
    {
        //close chat window
        hud.chatManager.closePrompt();
        
        //close menus
        hud.mapMenu.close();
        hud.inventoryMenu.close();
        hud.questMenu.close();
        
        //queue up level change
        if(this.activeLevelData != null)
        {
            workingChangeLevel = packet;

            this.getSceneEffectsManager().fadeToBlack(new PostEffectExecutor(){
                public void execute()
                {                      
                    loadLevel(workingChangeLevel); 
                    workingChangeLevel = null;
                }});
        }
        else 
            loadLevel(packet);     
    }
     
    private void handleRenderDataPacket(RenderDataPacket packet)
    {             
        //gets the sequence number of the top packet on the queue
        Packet topPacket = this.renderDataQueue.peek();
        long topSequence = 0;
        if(topPacket != null)
            topSequence = topPacket.getSequenceNumber();
               
        //adds render data packet to the queue only if its sequence number is higher
        if(packet.getSequenceNumber() > topSequence)
        {
            this.renderDataQueue.add(packet);
            this.renderPacketArrivedTimestamp = System.currentTimeMillis();
        }
                  
    }
    
    private void handleSoundDataPacket(SoundDataPacket packet)
    {

        //add sound packet to queue
        this.soundDataQueue.add(packet); 

    }
    
    private void handleOpenDialoguePacket(OpenDialoguePacket packet)
    {
        this.hud.openDialogue(packet.speaker, packet.text);
    }  
    
    private void handleRespawnPacket(RespawnPacket packet)
    {
        this.player.respawn();  
    }
    
    private void handleMovePlayerToPointPacket(MovePlayerToPointPacket packet)
    {
        SylverVector2f point = packet.point;
        
        //gets the viewportBlockers
        ArrayList<SceneObject> list = this.getSceneObjectManager().get(ExtendedSceneObjectGroups.VIEWPORTBLOCKER);
        ArrayList allBlockers = new ArrayList(list);
        
        this.physicsWorld.clearArbiters(((PlayerEntity)player).getBody());
        player.setPosition(point.x, point.y); 
        player.getBody().setVelocity(new Vector2f(0,0));
            
       ((EnhancedViewport)getViewport()).snapToCoordinateWithCorrection(point.x, point.y,allBlockers); 

    }
    
    private void handleSaveGamePacket(SaveGamePacket packet)
    {
        
        //Save the game.
        this.saveGameToDisk();

    }
    
    private void handleCursorChangePacket(CursorChangePacket packet)
    {
        //build cursor
        Cursor cursor = CursorFactory.getInstance().getCursor(packet.cursor);
        
        //set the cursor
        Game.getInstance().getGraphicsWindow().setCursor(cursor);
    }
    
    private void handleChatPacket(ChatPacket packet)
    {
        if(packet.chatMessages != null)
        {
            for (String s : packet.chatMessages)
            {
                hud.chatManager.receiveMessage(s);
            }
        }
    }
    
    private void handleOpenMenuPacket(OpenMenuPacket packet)
    {
        switch(packet.menu)
        {
            case POTION:
               hud.potionsMenu.open();
               hud.inventoryMenu.open();
            break;
            case ARMOR:
                hud.armorMenu.open();
                hud.inventoryMenu.open();
            break;           
            case MAP:
                hud.mapMenu.open();
            break;
            case INVENTORY:
                hud.inventoryMenu.open();
            break;
            case SKILL:
                hud.skillMenu.open();
            break;
                
        }             
            
    }
    
    private void handleCloseMenuPacket(CloseMenuPacket packet)
    {
        switch(packet.menu)
        {
            case POTION:
               hud.potionsMenu.close();
               hud.inventoryMenu.close();
            break;
            case ARMOR:
                hud.armorMenu.close();
                hud.inventoryMenu.close();
            break;    
            case MAP:
                hud.mapMenu.close();
            break;
            case INVENTORY:
                hud.inventoryMenu.close();
            break;
            case SKILL:
                hud.skillMenu.close();
            break;
        }             
            
    }
    
    private void handleSetSideQuestStatusPacket(setSideQuestStatusPacket packet)
    {
        //set the status
        this.hud.questMenu.setSideObjectiveStatus(packet.text);
        
       
    }
    
    private void handleSideObjectiveCompletePacket(SideObjectiveCompletePacket packet)
    {
        //complete text
        Text completeText = new Text("Side Objective Complete!", LeadCrystalTextType.MESSAGE);
        completeText.setColor(new Color(Color.green));
        completeText.setScale(1.3f);
        float center = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x/2;
        completeText.setPosition(center- completeText.getWidth()/2, 650);
        completeText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 240, 0, 0));
        
        //currency text
        String currencyString = "+" + packet.currencyReward;
        Text currencyText = new Text(currencyString,LeadCrystalTextType.MESSAGE);
        currencyText.setScale(1.3f);
        currencyText.setPosition(center- currencyText.getWidth()/2 - 20, 600);
        currencyText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 240, 0, 0));
             
        //currency image
        Image currencyImage = new Image("currency2.png");
        currencyImage.setScale(1f);
        currencyImage.setPosition(center- currencyText.getWidth()/2 + 75, 600);
        currencyImage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 240, 0, 0));
        
        
        //add fade effects
        TextEffect fade = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(Color.green), new Color(Color.green,0));
        fade.setDelay(210);
        completeText.addTextEffect(fade);
        fade = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(Color.white), new Color(Color.white,0));
        fade.setDelay(210);
        currencyText.addTextEffect(fade);
        
        //add scale effects
        Float[] points ={1.3f,1.5f,1.3f};
        int[] durations = {45,45};
        completeText.addTextEffect(new MultiTextEffect(TextEffect.TextEffectType.SCALE, points, durations));
        currencyText.addTextEffect(new MultiTextEffect(TextEffect.TextEffectType.SCALE, points, durations));
        
        Float[] points2 ={1f,1.2f,1f};
        int[] durations2 = {45,45};
        currencyImage.addImageEffect(new MultiImageEffect(ImageEffect.ImageEffectType.SCALE,points2,durations2));
       
        //add text to scene
        this.add(completeText, Scene.Layer.HUD);
        
        if(packet.currencyReward != 0)
        {
            this.add(currencyText, Scene.Layer.HUD);
            this.add(currencyImage, Scene.Layer.HUD);
        }
        
    }
    
    private void handleMainObjectiveCompletePacket(MainObjectiveCompletePacket packet)
    {
        //complete text
        Text completeText = new Text("Main Objective Complete!", LeadCrystalTextType.MESSAGE);
        completeText.setColor(new Color(Color.green));
        completeText.setScale(1.3f);
        float center = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x/2;
        completeText.setPosition(center- completeText.getWidth()/2, 650);
        completeText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 240, 0, 0));
        
        //currency text
        String currencyString = "+" + packet.currencyReward;
        Text currencyText = new Text(currencyString,LeadCrystalTextType.MESSAGE);
        currencyText.setScale(1f);
        currencyText.setPosition(center- currencyText.getWidth()/2 - 20, 600);
        currencyText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 240, 0, 0));
             
        //currency image
        Image currencyImage = new Image("currency2.png");
        currencyImage.setScale(1f);
        currencyImage.setPosition(center- currencyText.getWidth()/2 + 75, 600);
        currencyImage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 240, 0, 0));
        
        //skill text
        Text skillText = new Text("+1 Skill Point",LeadCrystalTextType.MESSAGE);
        skillText.setScale(1f);
        skillText.setPosition(center- skillText.getWidth()/2 - 20, 540);
        skillText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 240, 0, 0));
        
        
        //add fade effects
        TextEffect fade = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(Color.green), new Color(Color.green,0));
        fade.setDelay(210);
        completeText.addTextEffect(fade);
        fade = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(Color.white), new Color(Color.white,0));
        fade.setDelay(210);
        currencyText.addTextEffect(fade);
        fade = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(Color.white), new Color(Color.white,0));
        fade.setDelay(210);
        skillText.addTextEffect(fade);
        
        //add scale effects
        Float[] points ={1.3f,1.5f,1.3f};
        int[] durations = {45,45};
        completeText.addTextEffect(new MultiTextEffect(TextEffect.TextEffectType.SCALE, points, durations));
        Float[] points3 ={1f,1.2f,1f};
        int[] durations3 = {45,45};
        currencyText.addTextEffect(new MultiTextEffect(TextEffect.TextEffectType.SCALE, points3, durations3));
        skillText.addTextEffect(new MultiTextEffect(TextEffect.TextEffectType.SCALE, points3, durations3));
        
        Float[] points2 ={1f,1.2f,1f};
        int[] durations2 = {45,45};
        currencyImage.addImageEffect(new MultiImageEffect(ImageEffect.ImageEffectType.SCALE,points2,durations2));
       
        //add text to scene
        this.add(completeText, Scene.Layer.HUD);
        
        if(packet.currencyReward != 0)
        {
            this.add(currencyText, Scene.Layer.HUD);
            this.add(currencyImage, Scene.Layer.HUD);
            this.add(skillText, Scene.Layer.HUD);
            //toggle on skillup icon
           this.hud.openTooltip(OpenInstructionalTipPacket.InstructionalTip.SkillUp); 
        }
        
        
        
        
    }
    
    private void handleOpenInstructionalTipPacket(OpenInstructionalTipPacket packet)
    {
        this.hud.openTooltip(packet.tipID);
    }
    
    private void handleCloseInstructionalTipPacket(CloseInstructionalTipPacket packet)
    {
        this.hud.closeTooltip(packet.tipID);
    }
    
    private void handleHostLevelProgressionAdjust(HostLevelProgressionAdjust packet)
    {
        this.hostLevelProgression.reconcileRenderDataChanges(0, 0, packet.changes);   
    }
    
    private void handleHoverEntityPacket(HoverEntityPacket packet)
    {
        //if we are hovering
        if(packet.currentlyHovering)
        {
            //get entity that we are hovering on
            Entity hoveredEntity = (Entity)this.getSceneObjectManager().get(packet.hoveredID);
            
            //apply brightness effect
            if(!hoveredEntity.getImage().hasImageEffect("hover"))
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
            Entity hoveredEntity = (Entity)this.getSceneObjectManager().get(packet.hoveredID);
            
            
            //remove hover effect
            hoveredEntity.getImage().removeImageEffect("hover");
            hoveredEntity.getImage().setBrightness(1);
        }
        
        this.mouseHoverInRange = packet.inRange;
    }
    
    
    
}
