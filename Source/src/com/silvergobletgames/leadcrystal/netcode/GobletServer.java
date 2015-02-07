package com.silvergobletgames.leadcrystal.netcode;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.core.SaveGame;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.netcode.JoinResponse.ReasonCode;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.leadcrystal.skills.Skill;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.netcode.ClientPacket;
import com.silvergobletgames.sylver.netcode.Packet;
import com.silvergobletgames.sylver.util.Log;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.phys2d.math.Vector2f;


public class GobletServer implements Runnable
{    
     
    //running variable
    private boolean serverRunning = true;
    //Kryonet server
    private Server server;   
    //host Player
    private PlayerEntity host;
    //Client list
    public ConcurrentHashMap<UUID, ClientData> connectedClients;
    //map of scenes
    private ConcurrentHashMap<String,GameServerScene> sceneMap = new ConcurrentHashMap();
    
    //client move queue
    private ConcurrentLinkedQueue<MoveStruct> clientMoveQueue = new ConcurrentLinkedQueue();   
    //join queue
    private ConcurrentLinkedQueue<SimpleEntry<JoinRequest,Connection>> joinQueue = new ConcurrentLinkedQueue();
    //disconnect queue
    private ConcurrentLinkedQueue<DisconnectRequest> disconnectQueue = new ConcurrentLinkedQueue();
    
    //server settings
    private final ServerConfiguration serverConfiguration;
    
    //client move struct
    private static class MoveStruct
    {
        String clientID;
        String levelName;
        String entityID;
        SylverVector2f point;
    }
  
    
    //==============
    // Constructor
    //==============
      
    public GobletServer(ServerConfiguration config)
    {
     
        //===============
        // Set up Server
        //===============

        //set server configuration
        this.serverConfiguration = config;
        
        //initialize the server
        server = new Server(80_192 , 20_048 );     
        server.start();
           
        //initialize the conneted clients map
        connectedClients = new ConcurrentHashMap();

        //Registration of request classes
        Kryo kryo = server.getKryo();
        SerializationRegistrator.registerSerialization(kryo);

        //Port binding
        try
        {
            //TODO handle bind exception better
            server.bind(this.serverConfiguration.tcpPort, this.serverConfiguration.udpPort);
        }
        catch (IOException e)
        {
            Log.error("Server bind error: ", e);
        }

        //Request handling
        server.addListener(new Listener() 
        {          
            public void connected (Connection connection) 
            {
                
            }
            
            public void received(final Connection connection,final Object object)
            {     
                
                //handle join requests               
                if (object instanceof JoinRequest)
                {             
                    handleJoinRequestPacket((JoinRequest)object, connection);
                } 
                //handle disconnect requests
                else if(object instanceof DisconnectRequest)
                {
                    handleDisconnectRequestPacket((DisconnectRequest)object);
                }
                              
                //if the object is a ClientPacket and we have that client, and we haven't seen this packet before
                else if(object instanceof ClientPacket && connectedClients.containsKey(((ClientPacket)object).getClientID()) && !connectedClients.get(((ClientPacket)object).getClientID()).recievedSequenceNumberHistory.containsKey(((ClientPacket)object).getSequenceNumber()))
                {
                    
                    //do stuff that we have to do to every packet
                    initialPacketHandling((ClientPacket)object);

                    //queue up packets to be handled
                    connectedClients.get(((ClientPacket)object).getClientID()).packetsToBeHandledQueue.add((ClientPacket)object);
                    
                }
            }
            
            public void disconnected(Connection connection) 
            {
                //figure out the clientID of the disconnected player
                UUID disconnectClientID = null;             
                for(ClientData client: GobletServer.this.connectedClients.values())
                {
                    if(connection.getID() == client.connection.getID())
                    {
                        disconnectClientID = client.clientID;
                    }
                }
                
                //queue up disconnect
                if(disconnectClientID != null)
                {
                    DisconnectRequest disconnectPacket = new DisconnectRequest();
                    disconnectPacket.setClientID(disconnectClientID);
                    
                    disconnectQueue.add(disconnectPacket);
                }
                
            }
        });
    }
    
    //==============
    // Server Loop
    //==============
    
    /**
     * The main game loop. This loop is running during all game
     * play as is responsible for the following activities:
     *
     * - Updates current state
     * - Handles input for current state
     * - Renders current state
     * 
     */
    public void serverLoop()
    {
        
        try
        {
        //timing variables  
        long startOfLoopTime = 0;
        long endOfLoopTime=0;
        long lastFrameTime = 0;
        
        final long updateTimestep = 16_666_667;      
        long updateAccumulator = 0;
        
        //updates 60 times per second, handles input 60 times per second
        //loop goes around every 16.60 milliseconds, or 16_600_000 nanoseconds
        while(this.serverRunning)
        {
            //find out how long the last frame took
            lastFrameTime = endOfLoopTime - startOfLoopTime;           
            //System.out.println("Server: " +(float)(lastFrameTime)/1_000_000f);
            
            //save start of the loop time
            startOfLoopTime = System.nanoTime();
            
            //===============================
            //updates all the scenes (60 hz)
            //===============================
            updateAccumulator += lastFrameTime;
            
            //safety so we dont spin out of control
            if(updateAccumulator >= 20* updateTimestep)
                updateAccumulator = 0;
            
            //60hz timer
            while(updateAccumulator >= updateTimestep)
            {
                //for each scene
                for(Scene currentScene:this.sceneMap.values())
                {                   
                    //update at least once
                    currentScene.update();
                    
                    //find the client that has the least number of queued inputs and update that many times
                    int updateTicks = Integer.MAX_VALUE;
                    for(ClientData clientData:((GameServerScene)currentScene).clientsInScene.values())
                    {
                        if(clientData.clientInputPacketQueue.size() < updateTicks)
                            updateTicks = clientData.clientInputPacketQueue.size();
                    }
                    if(updateTicks == Integer.MAX_VALUE) //safety
                        updateTicks = 0;
                    

                    //while we have more than 4 update ticks buffered
                    while(updateTicks > 4)
                    {
                      currentScene.update();  
                      updateTicks--;
                    }
                }
                
                updateAccumulator -= updateTimestep;   
            }
            
            
            //performs client moves          
            while(!clientMoveQueue.isEmpty())
            {
                MoveStruct struct = clientMoveQueue.poll();
                performPlayerMove(struct.clientID, struct.levelName, struct.point,struct.entityID);
            }              
                      
            //performs client joins          
            while(!joinQueue.isEmpty())
            {
                SimpleEntry<JoinRequest,Connection> entry = joinQueue.poll();
                performPlayerJoin(entry.getKey(), entry.getValue());
            }                    
                     
            //performs client disconnects          
            while(!disconnectQueue.isEmpty())
            {
                DisconnectRequest packet = disconnectQueue.poll();
                performPlayerDisconnect(packet);
            }    
                        
            //flushing output streams for debugging           
//            System.out.flush();
//            System.err.flush();

            //note the current time
            endOfLoopTime = System.nanoTime();           
            
            //pause to set frame rate   
            while(endOfLoopTime-startOfLoopTime <= 12_166_667)
            {
                try{ Thread.sleep(0,250_000);} catch(java.lang.InterruptedException e){ System.err.println("sleep problem GobletServer");}   
                endOfLoopTime = System.nanoTime();
            }                       
            while (endOfLoopTime - startOfLoopTime <= 16_666_667)
            {              
                Thread.yield();
                endOfLoopTime = System.nanoTime();
            }                        

        }
        }
        catch(Exception e)
        {
            //log and rethrow, this error should get caught by the thread
            Log.error("Server Error: ", e);
            this.stopServer();
            
            throw e;
        }
        
    }
    
    public void run()
    {
        this.serverLoop();
    }
    
    //==============
    // Class Methods
    //==============
    
    public void stopServer()
    {
        this.serverRunning = false;
        this.server.stop();
        this.sceneMap.clear();
    }
    
    public GameServerScene getScene(String level)
    {
        return sceneMap.get(level);
    }
    
    public PlayerEntity getHost()
    {
        return host;
    }
    
    /**
     * Add chat to the inbox of every client.
     * @param s 
     */
    public void addGlobalChat(String s)
    {
        Set<UUID> clientIDs = this.connectedClients.keySet();
        for (UUID id : clientIDs)
        {
            this.connectedClients.get(id).chatInbox.add(s);         
        }
    }   
    
    private void sendPacket(Packet packet, UUID client)
    {
        ClientData clientData =connectedClients.get(client);
        
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
            System.out.println("GobletServer Packet: " + size+" Type: " + packet.getClass().toString().substring(packet.getClass().toString().lastIndexOf(".") + 1, packet.getClass().toString().length()));
   
    }
    
     
    
    public void queueMovePlayerToLevel(String playerID, String levelname, SylverVector2f point)
    {
        MoveStruct struct = new MoveStruct();
        struct.clientID = playerID;
        struct.levelName = levelname;
        struct.point = point;
        
        this.clientMoveQueue.add(struct);
    }
    
    public void queueMovePlayerToLevel(String playerID, String levelname, String entity)
    {
        MoveStruct struct = new MoveStruct();
        struct.clientID = playerID;
        struct.levelName = levelname;
        struct.entityID = entity;
        
        this.clientMoveQueue.add(struct);
    } 
    
    
    private void performPlayerMove(String id, String levelName, SylverVector2f point, String entity)
    {
        UUID clientID = UUID.fromString(id);
        
        //If the scene doesnt exist, or it does exist and our player isn't in it.
        if(!sceneMap.containsKey(levelName) || sceneMap.get(levelName).getSceneObjectManager().get(clientID.toString()) == null)
        {
            //mark that this player is in the middle of a level load
            connectedClients.get(clientID).loadingLevel = true;
            
            //reset dialogue clicked
            connectedClients.get(clientID).dialogueClosed = false;
            
            //remove the player from the current scene
            sceneMap.get(connectedClients.get(clientID).currentLevel).removeClient(UUID.fromString(id));
                        
            //if there are no clients left in the scene get rid of it            
            if(this.sceneMap.get( this.connectedClients.get(clientID).currentLevel).clientsInScene.isEmpty() )
            {  
                this.sceneMap.get( this.connectedClients.get(clientID).currentLevel).sceneExited();
                this.sceneMap.remove(this.connectedClients.get(clientID).currentLevel);
            }
            
            //if we dont have a scene for the new level already construct a new one
            if(!this.sceneMap.containsKey(levelName))
            {
               
                this.constructScene(clientID, levelName, entity);
            }
            else //add the client to that scene
            {    
                connectedClients.get(clientID).currentLevel = levelName;
                this.sceneMap.get(levelName).addClient(connectedClients.get(clientID)); 
                
                //get spawn point
                SylverVector2f spawnPoint;
                if(point != null)
                    spawnPoint = point;
                else //get point from entityID                                  
                    spawnPoint = this.sceneMap.get(levelName).getSceneObjectManager().get(entity).getPosition();
                
                this.sceneMap.get(levelName).movePlayerToPoint(clientID.toString(), spawnPoint);
            }

            
        } 
        
        //mark that the client is moved
        connectedClients.get(clientID).loadingLevel = false;
    }
    
    private void performPlayerJoin(JoinRequest request, Connection connection)
    {
        
        //if the server is full
        if(connectedClients.size() == this.serverConfiguration.maxPlayers)
        {
            JoinResponse response = new JoinResponse();
            response.okToJoin = false;
            response.reasonCode = ReasonCode.ServerFull;
            connection.sendUDP(response);
            return;
        }
        
        //if the server is single player
        if(connectedClients.size() != 0 && this.serverConfiguration.singlePlayer == true)
        {
            JoinResponse response = new JoinResponse();
            response.okToJoin = false;
            response.reasonCode = ReasonCode.ServerPrivate;
            connection.sendUDP(response);
            return;
        }
                                        
        //build the players save data
        SaveGame saveData = new SaveGame(request.rawSaveData);

        //put the client in the client list 
        ClientData clientData = new ClientData(connection);
        this.connectedClients.put(request.getClientID(), clientData);

        //set clientID in client data
        clientData.clientID = request.getClientID();

        //build player and add it to client data
        PlayerEntity player = saveData.getPlayer();
        player.setID(request.getClientID().toString());
        clientData.player = player;
        if(connectedClients.size() == 1)
            this.host = player;  

        //send join response
        JoinResponse response = new JoinResponse();
        response.okToJoin = true;
        response.hostProgressionManager = this.host.getLevelProgressionManager().dumpRenderData();
        this.sendPacket(response, clientData.clientID);

        //determine if we should go into the town or level0
        String levelToGo;
        if(player.getLevelProgressionManager().levelMap.get(0).mainObjective.complete == true)
            levelToGo = "town.lv";
        else
            levelToGo = "desert0.lv";

        //if we dont have a scene for the levelToGo already construct a new scene
        if(this.sceneMap.get(levelToGo) == null)
        {
            this.constructScene(clientData.clientID, levelToGo, null);
        }
        else // just add the player to the level
        {
            //add our player to this scene
            clientData.currentLevel = levelToGo;
            sceneMap.get(clientData.currentLevel).addClient(clientData);
        }            

        //Alert the players
        this.addGlobalChat("[Server] " + clientData.player.getName() + " has connected.");           
            
      
    }
    
    private void performPlayerDisconnect(DisconnectRequest packet)
    {
        //remove the client from the scene
        this.sceneMap.get( this.connectedClients.get(packet.getClientID()).currentLevel).removeClient(packet.getClientID());
        
        //if there are no clients left in the scene get rid of it
        if(this.sceneMap.get( this.connectedClients.get(packet.getClientID()).currentLevel).clientsInScene.isEmpty())
            this.sceneMap.remove(this.connectedClients.get(packet.getClientID()).currentLevel);
        
        //send a message that the player left
        this.addGlobalChat("[Server] Player " + this.connectedClients.get(packet.getClientID()).player.getName()  + " has disconnected.");
        
        //remove the client
        this.connectedClients.remove(packet.getClientID());               
        
        //if the host left, stop the server
        if(packet.getClientID().toString().equals(host.getID()))
            this.stopServer();
    }
    
    /**
     * Constructs a new scene from the given levelName, adds the player to it at the given spawn point
     * and puts the constructed scene in the constructedSceneQueue
     * @param clientID
     * @param levelName
     * @param spawnPoint 
     */
    private void constructScene(final UUID clientID,final String levelName, final Object spawnPoint)
    {

        GameServerScene scene = new GameServerScene();
        String levelToLoad = levelName;

        //try to load the correct level
        scene.loadLevel(levelToLoad);

        //add the client to this scene
        connectedClients.get(clientID).currentLevel = levelName;
        scene.addClient(connectedClients.get(clientID));


        //move player to spawn point 
        if(spawnPoint != null)
        {
            //if we got a spawn point
            if(spawnPoint instanceof SylverVector2f)
            {
            scene.movePlayerToPoint(clientID.toString(), (SylverVector2f)spawnPoint);
            }
            else if(spawnPoint instanceof String) //if we got a spawn entity
            {
                SylverVector2f point;
                if(scene.getSceneObjectManager().get((String)spawnPoint) != null)
                    point = scene.getSceneObjectManager().get((String)spawnPoint).getPosition();
                else
                    point = scene.getSceneObjectManager().get("checkpoint1").getPosition();

                scene.movePlayerToPoint(clientID.toString(), point);

            }
        }

        //add the scene to the map
        this.sceneMap.put(levelToLoad, scene); 
       
    }
    
    
    //=================
    // Handle Requests
    //=================
    
    public void initialPacketHandling(ClientPacket packet)
    {
        ClientData clientData = this.connectedClients.get(packet.getClientID());
        if(clientData != null)
        {

            //note that we recieved this packet
            clientData.recievedSequenceNumberHistory.put(packet.getSequenceNumber(), packet);
            //set remote sequence number
            if(packet.getSequenceNumber() >  clientData.remoteSequenceNumber)
                clientData.remoteSequenceNumber = packet.getSequenceNumber();

            //check server acks for packet loss and resend packets if its later than 150ms we resend
            if(packet.getAck() > 100) //wait 5 seconds before we start to resend missed packets
            {
                for(byte i = 3; i <=7; i ++) //the 3rd bit is the 150ms cutoff
                {
                    // if the bit was not set
                    if ((packet.getAckBitfield() & (1L << i)) == 0)
                    {
                        //resend it
                        this.resendPacket(packet.getClientID(),packet.getAck() - i - 1);               
                    }
                }
            }
        }
    }
    
    private void handleJoinRequestPacket(JoinRequest request, Connection connection)
    {
        joinQueue.add(new SimpleEntry(request,connection));
    }
    
    private void handleDisconnectRequestPacket(DisconnectRequest packet)
    {
        disconnectQueue.add(packet);
    }
    
    public void resendPacket(UUID clientID,long sequenceNumber)
    {
        if(GameplaySettings.getInstance().networkDebugging)
            System.out.println("Server ResendingPacket: " + sequenceNumber);
        
        //get packet to resend
        Packet packet = this.connectedClients.get(clientID).packetsSentToClient.get(sequenceNumber);
        
        //if packet typeof checking to see if we actually want to resend it
        
        //send the packet 
        try
        {  
            if(packet != null)
                this.connectedClients.get(clientID).connection.sendUDP(packet);
        }
        catch(java.lang.IllegalStateException e){System.err.print("Resend Packet Fail: "); e.printStackTrace(System.err);}
        
    }
    
    
    public static class ServerConfiguration
    {
        public String name = "";
        public int maxPlayers = 4;
        public int tcpPort = 50501;
        public int udpPort = 50511;
        public boolean singlePlayer = true;
        public boolean privateGame = false;
        public String password;
        
        public ServerConfiguration()
        {
            
        }
    }
    
    
    
}
