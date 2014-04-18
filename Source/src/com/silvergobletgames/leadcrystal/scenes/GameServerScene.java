package com.silvergobletgames.leadcrystal.scenes;

import com.jogamp.newt.event.KeyEvent;
import com.silvergobletgames.leadcrystal.core.*;
import com.silvergobletgames.leadcrystal.core.CollisionHandler;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.core.LevelData;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorModifier;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorModifier.ArmorModifierID;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorStat;
import com.silvergobletgames.leadcrystal.items.Potion;
import com.silvergobletgames.leadcrystal.items.PotionManager;
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
import javax.media.opengl.GL2;
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
public class GameServerScene extends Scene 
{
    
    //Client list
    public ConcurrentHashMap<UUID, ClientData> clientsInScene = new ConcurrentHashMap();
    //easy reference to players
    public ArrayList<PlayerEntity> players = new ArrayList();
      
    //==============
    // Scene Data
    //==============
    //active level
    private LevelData activeLevel; 
    //physics world
    public World physicsWorld; 
    //Collision Listener
    CollisionHandler collisionHandler = new CollisionHandler();
    //interpreter
    private SceneScriptManager scriptManager = new SceneScriptManager(this);
    
    //=====================
    // Outgoing Information
    //=====================
    //counter to get the timing right
    private long sendCounter = 0;
    //render data send list
    private HashMap<String,SceneObjectRenderDataChanges> renderData = new HashMap<>(); 
    //new sceneObjects send list
    private HashMap<String, SerializableEntry<SceneObjectRenderData,Layer>> newSceneObjects = new HashMap<>(); 
    //remove sceneObject list
    private HashSet<String> removeSceneObjects = new HashSet<>(); 
    //sound data send list
    private ArrayList<Sound> outgoingSoundData = new ArrayList(); 
    
    //===============
    // Constructor
    //===============
    
    public GameServerScene()
    {
        //===============
        // Set up Scene
        //===============

        //build physics world
        physicsWorld = new World(new Vector2f(0.0f, -45.0f), 10, new QuadSpaceStrategy(20, 5));
        physicsWorld.addListener(collisionHandler);
        physicsWorld.enableRestingBodyDetection(1f, 1f, 1f);
        
    }

    
    //=========================
    // Scene Interface Methods
    //=========================
    public void update()
    {

        //if we have any clients update the scene and send them data
        if (this.clientsInScene.size() > 0)
        {
          
            //===================
            // Update The Scene
            //===================
            
            //dump all scene object renderData to a map
            HashMap<String, SceneObjectRenderData> preUpdateData = new HashMap<>();
            ArrayList<SceneObject> parallax;
            for (Layer layer: Layer.values())
            {
                parallax = new ArrayList(this.getSceneObjectManager().get(layer));
                for (int j = 0; j < parallax.size(); j++)
                {
                    //assign local variable
                    SceneObject currentObject = parallax.get(j);
                    
                    //dump to map
                    if(currentObject instanceof NetworkedSceneObject)
                    preUpdateData.put(currentObject.getID(), ((NetworkedSceneObject)currentObject).dumpRenderData());                  
                }                
            }
            
            //handle client packets           
            for(ClientData clientData: this.clientsInScene.values())
            {
                while(!clientData.packetsToBeHandledQueue.isEmpty())
                {
                    Packet packet = clientData.packetsToBeHandledQueue.poll();

                    this.delegatePacket(packet);
                }
            }
                 
            //handle client input
            this.handleInput();
                    
            //tick the physics world
            physicsWorld.clearRestingState(); //clear the physics world of resting bodies (this needs to be done for isTouching() to work)
            
           // long time = System.currentTimeMillis();
            physicsWorld.step(5 / 60F);
            
           // System.err.println(System.currentTimeMillis() - time);
            
            //resolve collisions
            this.collisionHandler.resolveCollisions();        
            
            //update everything in the scene          
            for (Layer layer: Layer.values())
            {
                parallax = new ArrayList(this.getSceneObjectManager().get(layer));
                for (int j = 0; j < parallax.size(); j++)
                {
                    //assign local variable
                    SceneObject currentObject = parallax.get(j);
                    //update the SceneObject
                    currentObject.update();
                }
            }           
            
                      
            //dump all scene objects renderData, and generate SceneObjectRenderDataChanges if there is any
            SceneObjectRenderDataChanges changes;
            SceneObject currentObject;
            SceneObjectRenderData currentRenderData;
            for (Layer layer: Layer.values())
            {
                parallax = new ArrayList(this.getSceneObjectManager().get(layer));
                for (int j = 0; j < parallax.size(); j++)
                {
                    //assign local variables
                    currentObject = parallax.get(j);
                    currentRenderData = ((NetworkedSceneObject)currentObject).dumpRenderData();
                    
                    //compare current render data against old render data to get change data
                    changes = null;
                    if(preUpdateData.containsKey(currentObject.getID()))
                    {
                        changes = ((NetworkedSceneObject)currentObject).generateRenderDataChanges(preUpdateData.get(currentObject.getID()), currentRenderData);
                    }
                    //if the current render data didnt have a match in the old list it is a new object that must be sent
                    else
                    {
                         this.newSceneObjects.put(currentObject.getID(), new SerializableEntry(currentRenderData,layer));
                    }
                    
                    //if there were any changes add them to list to send
                    if(changes != null)
                    {
                        //if there are already changes for this object, merge the changes
                        if(renderData.containsKey(currentObject.getID()) )
                        {
                            SceneObjectRenderDataChanges mergedChanges =this.mergeChanges(renderData.get(currentObject.getID()),changes);
                            renderData.put(currentObject.getID(), mergedChanges);
                        }
                        else
                           renderData.put(currentObject.getID(),changes);
                    }                                    
                } 
                
            }
            
            
                           
            
            //===================================
            // Check For Host Progression Changes
            //===================================
            
            HostLevelProgressionAdjust progressionPacket= null;
            //check list of changes for host level progression change, and send that to clients
            if(renderData.containsKey(((GobletServer)Game.getInstance().getRunnable("Goblet Server")).getHost().getID()))
            {
                SceneObjectRenderDataChanges playerRenderChanges = renderData.get(((GobletServer)Game.getInstance().getRunnable("Goblet Server")).getHost().getID());
                
                //======== Construct arraylist from playerRenderDataChanges=============
                int fieldMap = playerRenderChanges.fields;
                ArrayList rawData = new ArrayList();
                rawData.addAll(Arrays.asList(playerRenderChanges.data)); 

                //construct an arraylist of data that we got, nulls will go where we didnt get any data
                ArrayList playerChangeData = new ArrayList();
                for(int i = 0; i <12; i ++)
                {
                    // The bit was set
                    if ((fieldMap & (1L << i)) != 0)
                    {
                        playerChangeData.add(rawData.get(0));
                        rawData.remove(0);
                    }
                    else
                        playerChangeData.add(null);          
                }
                
                if(playerChangeData.get(6) != null)
                {
                    progressionPacket = new HostLevelProgressionAdjust();
                    progressionPacket.changes = (SceneObjectRenderDataChanges)playerChangeData.get(6);
                }
            }
            
            if(progressionPacket != null)
            {
                //send the packet to each client
                Iterator<UUID> iter = ((GobletServer)Game.getInstance().getRunnable("Goblet Server")).connectedClients.keySet().iterator();
                while (iter.hasNext())
                {
                    //local variables
                    UUID currentClientID = iter.next();

                    this.sendPacket(progressionPacket, currentClientID);
                }
            }
            
            
            
            
            //=====================
            // Send Data To Clients
            //=====================        
            sendCounter++;
            if (sendCounter % 3 == 0) // 20hz
            {               
                //send render data
                this.sendRenderData();
                
                //send sound data
                this.sendSoundData();
                
                //send chat data
                this.sendChatMessages();
            }
            
                     
        }
        
    }

    public void handleInput()
    {                
        //iterate through each client
        for (Entry clientEntry: this.clientsInScene.entrySet())
        {
            //build some local variables
            ClientData clientData = (ClientData) clientEntry.getValue();
            UUID clientID = (UUID)clientEntry.getKey();           
            PlayerEntity player = (PlayerEntity) this.getSceneObjectManager().get(clientID.toString());
            
            //dash
            if(player.dashing)
            {
                player.handleDash(null);
            }
            
                    
            //=================
            // Handle the Input
            //=================
            do
            {
                //get the input we want to process
                clientData.currentInputPacket = clientData.clientInputPacketQueue.poll();                      
                
                //if we have new input
                if(clientData.currentInputPacket != null)
                {

                    //get input snapshot from input data
                    InputSnapshot inputSnapshot = clientData.currentInputPacket.getInputSnapshot();
                   
                    //===============================
                    // Handle players facing position
                    //===============================

                    if (clientData.currentInputPacket.mouseLocationX + 5 < player.getPosition().x)
                        player.face(FacingDirection.LEFT);
                    else if(clientData.currentInputPacket.mouseLocationX -5 > player.getPosition().x)
                        player.face(FacingDirection.RIGHT);


                    //============================
                    // Check for entity hovering
                    //============================

                    if(!clientData.currentInputPacket.mouseOverMenu)
                    {
                        float mouseX =  clientData.currentInputPacket.mouseLocationX;
                        float mouseY =  clientData.currentInputPacket.mouseLocationY;
                        Body mouse = new Body(new Box(2, 2), 1);
                        mouse.setPosition(mouseX, mouseY);
                        clientData.lastHoveredEntityID = clientData.hoveredEntityID;
                        clientData.hoveredEntityID = null;
                        clientData.hoveredEntityInRange = false;
                        clientData.hoveredEntityExited = false;

                        ArrayList<SceneObject> clickableGroup = this.getSceneObjectManager().get(ExtendedSceneObjectGroups.CLICKABLE);
                        for (SceneObject hoverObject : clickableGroup) 
                        {
                            boolean mouseOn = false;

                            Entity entity = (Entity)hoverObject;

                            Shape shape = entity.getBody().getShape();

                            if(shape.contains(new Vector2f(clientData.currentInputPacket.mouseLocationX,clientData.currentInputPacket.mouseLocationY), new Vector2f(entity.getPosition().x,entity.getPosition().y), entity.getBody().getRotation()))
                                mouseOn = true;    


                            //if the mouse is on something
                            if (mouseOn) 
                            {
                                clientData.hoveredEntityID = entity.getID();
                                //if the entity is in range of the player
                                if (Point.distance(player.getPosition().x, player.getPosition().y, entity.getPosition().x, entity.getPosition().y) < 400)             
                                    clientData.hoveredEntityInRange = true;             
                                else              
                                    clientData.hoveredEntityInRange = false;
                            }
                            else // mouse is outside an entity
                            {
                                //if the old hovered entity is equal to this entity 
                                if (clientData.lastHoveredEntityID != null )                                                                            
                                    clientData.hoveredEntityExited = true;                                                 
                            }
                        }

                        //change mouse cursor if hover
                        if (clientData.hoveredEntityID != null) 
                        {
                            if (clientData.hoveredEntityInRange) 
                            {
                                this.sendCursorChangePacket(clientID, CursorType.ACTIVEHAND);  
                                this.sendEntityHoverPacket(clientID, clientData.hoveredEntityID, true,clientData.hoveredEntityInRange);
                            }
                            else 
                                this.sendCursorChangePacket(clientID, CursorType.UNACTIVEHAND);
                        }
                        else if (clientData.hoveredEntityExited) 
                        {
                            this.sendCursorChangePacket(clientID, CursorType.RETICULE);
                            this.sendEntityHoverPacket(clientID, clientData.lastHoveredEntityID, false,clientData.hoveredEntityInRange);
                        }
                    }


                    //================
                    //Keyboard Input
                    //================
                    
                    player.setWorldMouseLocationPoint(clientData.currentInputPacket.mouseLocationX,clientData.currentInputPacket.mouseLocationY); 


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
                        player.handleJumping(clientData.currentInputPacket.mouseLocationX,clientData.currentInputPacket.mouseLocationY);
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
                    if (inputSnapshot.isKeyReleased(KeyEvent.VK_P))
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
                    if (inputSnapshot.isKeyReleased(KeyEvent.VK_F))
                    {
                       player.getPotionManager().usePotion(); 
                    }

                   

                    //test
                    if (inputSnapshot.isKeyReleased(KeyEvent.VK_M))
                    {
                    }
                    if (inputSnapshot.isKeyReleased(KeyEvent.VK_N))
                    {
                     
                       
                    }



                    //=============
                    // Mouse Input
                    //=============
                    
                   

                    //mouse CLICKED handling
                   if(inputSnapshot.isMouseClicked() && !clientData.currentInputPacket.isMouseOverMenu())
                    {
                        
                        //if we clicked on an interactable object
                        if (clientData.hoveredEntityID != null && clientData.hoveredEntityInRange)
                        {
                            if(((Entity) this.getSceneObjectManager().get(clientData.hoveredEntityID)).getScriptObject().getTrigger() == ScriptTrigger.RIGHTCLICK)
                            {
                                ((Entity) this.getSceneObjectManager().get(clientData.hoveredEntityID)).getScriptObject().runScript(player);
                                inputSnapshot.killMouseClick();
                            }
                        }
                        
                    }

                    //mouse DOWN handling
                    if (inputSnapshot.isMouseDown() && !clientData.currentInputPacket.isMouseOverMenu())
                    {
                        if (inputSnapshot.buttonClicked() == 1 && !(clientData.hoveredEntityID != null && clientData.hoveredEntityInRange) )
                        {
                            if (player.getSkillAssignment(1) != null && player.getSkillManager().getSkill(player.getSkillAssignment(1)).isUsable())
                            {
                                player.useActionBarSkill(player.getSkillAssignment(1));
                            }
                        }
                        else if(inputSnapshot.buttonClicked() == 3 && !(clientData.hoveredEntityID != null && clientData.hoveredEntityInRange))
                        {
                            if (player.getSkillAssignment(2) != null && player.getSkillManager().getSkill(player.getSkillAssignment(2)).isUsable())
                            {
                                player.useActionBarSkill(player.getSkillAssignment(2));
                            }
                        }

                    }            


                }
                else //if client input is null
                {
                    //System.out.println("client Input Null");

                }
            }
            //safetey net so the input queue cant get too big
            while((clientData.clientInputPacketQueue.size() >= 8)? !clientData.clientInputPacketQueue.isEmpty(): false );
        }
    }

    public void render(GL2 gl)
    {
        //doesnt do anything for server
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
                physicsWorld.add(((Entity) item).getBody());
                
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
        if(sound != null)
            this.outgoingSoundData.add(sound);
    }

    public void remove(SceneObject item)
    {       
        //entity special case
        if (item instanceof Entity)
        { 
            //remove the entity from the physics world if the entity was in the main layer
            if (this.getSceneObjectManager().get(Layer.MAIN)  != null && this.getSceneObjectManager().get(Layer.MAIN).contains(item))
            {
                physicsWorld.clearArbiters(((Entity) item).getBody()); 
                physicsWorld.remove(((Entity) item).getBody());
            }
        }           
       
        //super method
        super.remove(item);

        //compile a list of objects that need to be removed on the client side
        if(!(item instanceof AbstractParticleEmitter))
             this.removeSceneObjects.add(item.getID());
    }

    public void sceneEntered(ArrayList args)
    {
        
    }

    public void sceneExited()
    {
            
        //cleanup some stuff
        this.physicsWorld.clear();
        this.clientsInScene.clear();
        
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

    public ArrayList<PlayerEntity> getPlayers()
    {
        return this.players;
    }
    
    /**
     * Merges the renderDataChanges to prepare for sending to the client. Since the server updates 3 times for every 1 packet sent to the client, 
     * those changes need to be merged together.
     * @param oldData
     * @param newData
     * @return 
     */
    private SceneObjectRenderDataChanges mergeChanges(SceneObjectRenderDataChanges oldData, SceneObjectRenderDataChanges newData)
    {
       
        int fieldMap = oldData.fields;
        ArrayList rawData = new ArrayList();
        rawData.addAll(Arrays.asList(oldData.data)); 
        
        //construct an arraylist of data that we got, nulls will go where we didnt get any data
        ArrayList oldChangeData = new ArrayList();
        for(int i = 0; i <32; i ++)
        {
            // The bit was set
            if ((fieldMap & (1L << i)) != 0)
            {
                oldChangeData.add(rawData.get(0));
                rawData.remove(0);
            }
            else
                oldChangeData.add(null);          
        }
        
        
        
        fieldMap = newData.fields;
        rawData = new ArrayList();
        rawData.addAll(Arrays.asList(newData.data)); 
        
        //construct an arraylist of data that we got, nulls will go where we didnt get any data
        ArrayList newChangeData = new ArrayList();
        for(int i = 0; i <32; i ++)
        {
            // The bit was set
            if ((fieldMap & (1L << i)) != 0)
            {
                newChangeData.add(rawData.get(0));
                rawData.remove(0);
            }
            else
                newChangeData.add(null);          
        }
        
        
        //merge the two lists
        for(int i = 0; i < 32; i++)
        {
            if(newChangeData.get(i) != null)
            {
                
                if(newChangeData.get(i) instanceof SceneObjectRenderDataChanges && oldChangeData.get(i) instanceof SceneObjectRenderDataChanges) //merge renderdata
                    oldChangeData.set(i,this.mergeChanges((SceneObjectRenderDataChanges)oldChangeData.get(i), (SceneObjectRenderDataChanges)newChangeData.get(i)));
                else if(newChangeData.get(i) instanceof ArrayList && oldChangeData.get(i) instanceof ArrayList) //merge arraylists
                {
                    ArrayList old = (ArrayList)oldChangeData.get(i);
                    ArrayList neww = (ArrayList)newChangeData.get(i);
                    
                    for(Object obj: neww)
                    {
                        old.add(obj);
                    }
                }
                else //overwrite
                    oldChangeData.set(i,newChangeData.get(i));
                
                
            }                         
        }
        
        //build the fieldmap
        ArrayList finalChanges = new ArrayList();
        int newFieldMap = 0;
        for(int i = 0; i < 32; i++)
        {
           if(oldChangeData.get(i) != null)
           {
               finalChanges.add(oldChangeData.get(i));
               newFieldMap += 1L << i;
           }
        }
        
        //buld the new merged change data
         SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();
         changes.ID = oldData.ID;
         changes.fields = newFieldMap;
         changes.data = finalChanges.toArray();
         
         return changes;
        
        
    }
       
    public void addClient(ClientData client)
    {    
        //add the client data to the local map
        this.clientsInScene.put(client.clientID, client);
        
        //clear old input packet info
        client.clientInputPacketQueue.clear();
        client.currentInputPacket = null;
        
        //clear entity hover stuff
        client.hoveredEntityExited = false;
        client.hoveredEntityID = null;
        client.hoveredEntityInRange = false;
        client.lastHoveredEntityID = null;
              
        //the scene data differences we will send to the client
        ArrayList<SceneObjectRenderDataChanges> updateList = new ArrayList<>(); 
        ArrayList<SerializableEntry<SceneObjectRenderData,Integer>> newObjectList = new ArrayList<>();
        ArrayList<String> removeList = new ArrayList<>(); 

        //load scene objects from level into temp list
        ArrayList<SimpleEntry<SceneObject,Layer>> sceneObjectList = activeLevel.getSceneObjects();

        //diff changes between the initial state of the level, and the servers current state
        for(SimpleEntry<SceneObject,Layer> entry:sceneObjectList)
        {
            SceneObject object = entry.getKey();
            SceneObject inSceneObject = this.getSceneObjectManager().get(object.getID());

            //if the scene object is in the scene
            if(inSceneObject != null )
            {
                SceneObjectRenderData object1Dump = ((NetworkedSceneObject)object).dumpRenderData();
                SceneObjectRenderData object2Dump = ((NetworkedSceneObject)inSceneObject).dumpRenderData();

                //if the scene object is different then the one in the scene add the render data to the list to send
                SceneObjectRenderDataChanges changes = ((NetworkedSceneObject)object).generateRenderDataChanges(object1Dump, object2Dump);
                if(changes != null)
                    updateList.add( changes);
            }
            else //the sceneObject isn't in the scene so mark it for removal 
            {
                removeList.add(object.getID());
            }           
        }

        //dump initial sceneobjects into a hashSet so we can check for quick lookup
        HashSet<String> initialSceneObjectsIDs = new HashSet<>(); 
        for(SimpleEntry<SceneObject,Layer> entry:sceneObjectList)
        {
            initialSceneObjectsIDs.add(entry.getKey().getID());
        }

        //iterate through scene list to find things that arn't in the initial state of the level
        for (Layer layer: Layer.values())
        {
            ArrayList<SceneObject> layerObjs = this.getSceneObjectManager().get(layer);
            for (int i = 0; i < layerObjs.size(); i++)
            {
                SceneObject sceneObject =layerObjs.get(i);
                //if this object isnt in the initial state, add it to a list to be added to the client, and its not an added client
                if(!initialSceneObjectsIDs.contains(sceneObject.getID()) && !(sceneObject instanceof LightSource) && !(sceneObject instanceof AbstractParticleEmitter) && !( this.newSceneObjects.containsKey(sceneObject.getID())))
                    newObjectList.add( new SerializableEntry(((NetworkedSceneObject)sceneObject).dumpRenderData(),layer));
            }
        }

        //build render data packet to send with change level packet
        RenderDataPacket dataPacket = new RenderDataPacket();
        dataPacket.newSceneObjects =newObjectList.toArray(dataPacket.newSceneObjects);
        dataPacket.removeSceneObjects =removeList.toArray( dataPacket.removeSceneObjects); 
        dataPacket.renderData = updateList.toArray(dataPacket.renderData);      

        //build the change level packet and send it
        ChangeLevelPacket changeLevelPacket = new ChangeLevelPacket();          
        changeLevelPacket.levelDifferences = dataPacket;
        changeLevelPacket.gameLevelName= activeLevel.filename;
        this.sendPacket(changeLevelPacket, client.clientID); 
        
        //set and add the player to the scene
        SceneObject savePoint = this.getSceneObjectManager().get("checkpoint1");       
        client.player.setPosition(savePoint.getPosition().x,savePoint.getPosition().y);
        client.player.getBody().setVelocity(new Vector2f(0,0));
        client.player.handleSprintReleased();
        add(client.player,Layer.MAIN);
        this.players.add(client.player);
        
        //send client to other players
        this.newSceneObjects.put(client.clientID.toString(), new SerializableEntry(client.player.dumpRenderData(),Scene.Layer.MAIN));
        
        
         
    } 
    
    public void removeClient(UUID clientID)
    {
        //flush any pending outgoing data to clients
        this.sendRenderData();
        this.sendSoundData();
        this.sendChatMessages();
        
        //remove player
        this.clientsInScene.remove(clientID);
        this.players.remove((PlayerEntity)this.getSceneObjectManager().get(clientID.toString()));

        //remove clients player
        this.remove(this.getSceneObjectManager().get(clientID.toString())); 
    }
    
    public void movePlayerToPoint(String playerID,SylverVector2f point)
    {
        //get player
        SceneObject player = this.getSceneObjectManager().get(playerID);
        
        //clear player arbiters
        this.physicsWorld.clearArbiters(((PlayerEntity)player).getBody());
        
        //set player position
        player.setPosition(point.x , point.y);
        
        //send teleport packet
        this.sendMovePlayerToPointPacket(UUID.fromString(player.getID()), point); 
    }
    
    public SceneScriptManager getScriptManager()
    {
        return this.scriptManager;
    }
    
    public void clearArbitersOnBody(Body body)
    {
        physicsWorld.clearArbiters(body); 
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
    
    public void sendSideObjectiveCompletePacket(String client, short currencyReward, ArmorModifierID modifierId)
    {
            //send out side objective complete packet
           SideObjectiveCompletePacket packet = new SideObjectiveCompletePacket();           
           packet.currencyReward = currencyReward;
           packet.modifierID = modifierId;
           this.sendPacket(packet,UUID.fromString(client));             
    }
    
    public void sendMainObjectiveCompletePacket(String client, short currencyReward,ArmorModifierID modifierId)
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
        else if(packet instanceof EquipModifierPacket)
        {
            handleEquipModifierPacket((EquipModifierPacket)packet);
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
        boolean success = player.getCurrencyManager().getBalence()>=PotionManager.POTION_PRICE && player.getPotionManager().getNumberOfPotions() <5;

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
                          && player.getArmorManager().armorStats.get(statPacket.statId).points < ArmorStat.MAX_POINTS;

        //if the subtraction succeeded, give potion to player
        if(success)
        {
            player.getCurrencyManager().subtractCurrency(player.getArmorManager().armorStats.get(statPacket.statId).cost);
            player.getArmorManager().armorStats.get(statPacket.statId).addPoint(1); 
        }   
    }  
    
    public void handleEquipModifierPacket(EquipModifierPacket modifierPacket)
    {
        PlayerEntity player = this.clientsInScene.get(modifierPacket.getClientID()).player;
        
         player.getArmorManager().equipModifier(modifierPacket.modifierID);
     
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
     
    
}
