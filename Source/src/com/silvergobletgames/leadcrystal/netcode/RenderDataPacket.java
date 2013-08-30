package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.netcode.Packet;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderDataChanges;
import com.silvergobletgames.sylver.util.SerializableEntry;
import java.util.ArrayList;


public class RenderDataPacket extends Packet
{   
    //input packet that this render data corresponds with
    public long correspondingInputPacket;
        
    //the players prediction data (it has to go every time)
    public PlayerPredictionData playersPredictionData;
    //update data send list
    public SceneObjectRenderDataChanges[] renderData = new SceneObjectRenderDataChanges[0];
    //sceneObjects send list
    public SerializableEntry[] newSceneObjects = new SerializableEntry[0];
    //remove sceneObject list
    public String[] removeSceneObjects = new String[0];
    

    public RenderDataPacket()
    {
    }

    
}

