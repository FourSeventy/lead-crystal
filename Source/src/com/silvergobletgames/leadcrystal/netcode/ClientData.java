package com.silvergobletgames.leadcrystal.netcode;

import com.esotericsoftware.kryonet.Connection;
import com.silvergobletgames.sylver.netcode.ClientPacket;
import com.silvergobletgames.sylver.netcode.Packet;
import com.silvergobletgames.sylver.netcode.PacketSequencer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;


public class ClientData 
{
    //the connection associated with this client
    public Connection connection;
    
    //the UUID associated with this client
    public UUID clientID;
    
    //packetSequencer
    public PacketSequencer packetSequencer = new PacketSequencer();
    //sent packets map (packets we sent to this client)
    public LinkedHashMap<Long,Packet> packetsSentToClient = new LinkedHashMap(150,.75f, false){ 
            private static final int MAX_ENTRIES = 100;

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
               return size() > MAX_ENTRIES;
            }};
    //remote sequence number
    public long remoteSequenceNumber;
    //set of recieved packet sequence numbers we have received from the client( max size 30)
    public LinkedHashMap<Long,Packet> recievedSequenceNumberHistory = new LinkedHashMap(30,.75f,false){ 
            private static final int MAX_ENTRIES = 30;

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
               return size() > MAX_ENTRIES;
            }};
       
    //the clients last input packet
    public ConcurrentLinkedQueue<ClientInputPacket> clientInputPacketQueue = new ConcurrentLinkedQueue();
    public ClientInputPacket currentInputPacket;
    
    //packets that came from the client that we have to handle
    public ConcurrentLinkedQueue<ClientPacket> packetsToBeHandledQueue = new ConcurrentLinkedQueue<>();   
      
    //the playerEntity associated with this client
    public PlayerEntity player;
    //current level
    public String currentLevel = "town.lv";
    
    //Mouse Hover Variables (try hovering)
    public String hoveredEntityID = null;
    public boolean hoveredEntityInRange = false;
    public String lastHoveredEntityID = null;
    public boolean hoveredEntityExited = false;
    
    //is this client moving to a new level
    public boolean loadingLevel = false;
    
    //chat inbox
    public ArrayList<String> chatInbox = new ArrayList();

    
    
    public ClientData(Connection connection)
    {
        this.connection = connection;
    }
}
