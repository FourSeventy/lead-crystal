package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.sylver.netcode.SceneObjectSaveData;
import java.io.*;
import java.util.ArrayList;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;


public class SaveGame 
{
    
    //Savable form of the raw save game data 
    private ArrayList<Object> rawSaveGameData = new ArrayList();
    
    //the name of this save
    public String fileName;
    
    //the player 
    private PlayerEntity player;
    
    
    
    //===============
    // Constructors
    //===============
    
    public SaveGame()
    {
        
    }
    
    /**
     * 
     * @param path name of the save game file on disk
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException 
     * 
     * This static method will load the save file from the disk
     */
    public static SaveGame loadSaveFromFile(String name) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        //save game we are constructing
        SaveGame save = new SaveGame();
        
        //get path of the LeadCrystal install folder
        File file = new File("");
        String path =file.getAbsolutePath();
        save.fileName = name;
       
        //get the file input stream of our save file 
        FileInputStream f = new FileInputStream(path +"\\Save Games\\" +name);
        ObjectInputStream ois = new ObjectInputStream(f);

        //read in raw data
        save.rawSaveGameData = (ArrayList<Object>)ois.readObject();

        //build the player
        save.player = PlayerEntity.buildFromFullData((SceneObjectSaveData)save.rawSaveGameData.get(0));
        
        //return the save game
        return save;
         
    }
    
    /**
     * Constructs a save game from the raw data.
     * @param rawData 
     */
    public SaveGame(ArrayList rawData)
    {
        //read in raw data
        this.rawSaveGameData = rawData;

        //build the player
        this.player = PlayerEntity.buildFromFullData((SceneObjectSaveData)rawSaveGameData.get(0));
    }
    
    
    //================
    // Class Methods
    //================
    
    public void save(String name)
    {
        //set raw data
        this.rawSaveGameData.add(player.dumpFullData());
        
        
        //get path of the LeadCrystal install folder
        File file = new File("");
        String path =file.getAbsolutePath();
       
        //get the file input stream of our save file
        try( FileOutputStream f = new FileOutputStream(path +"\\Save Games\\" +name);
             ObjectOutputStream oos = new ObjectOutputStream(f);)
        {   
            oos.writeObject(rawSaveGameData);
            oos.close();
        }
         catch (IOException e) {System.err.println("SaveGame save failed: "); e.printStackTrace(System.err);}   
        
        this.fileName = name;
    }
    
    public PlayerEntity getPlayer()
    {
        return this.player;
    }
    
    public void setPlayer(PlayerEntity player)
    {
        this.player = player;
    }
    
    public ArrayList getRawData()
    {
        return this.rawSaveGameData;
    }
    
    

}
