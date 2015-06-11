package com.silvergobletgames.leadcrystal.core;


import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.netcode.SceneObjectDeserializer;
import com.silvergobletgames.sylver.netcode.SceneObjectSaveData;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import net.phys2d.math.Vector2f;

//make max level size is +-32,767. Constrained by the short that sends mouse location
public class LevelData 
{
    
    //Scene objects that this level has 
    private ArrayList<SceneObjectSaveData> sceneObjectData;
    //ambient light in the level
    private Color ambientLight = new Color(1f, 1f, 1f, 1f);
    //name of the file this level is saved to
    public String filename;

    
    //===============
    // Constructors
    //===============
    
    /**
     * Constructs the LevelData by providing the data explicitly
     * @param rawGameData
     * @param backgroundMusic
     * @param ambientLight 
     */
    public LevelData(ArrayList<SceneObjectSaveData> rawGameData, Color ambientLight)
    {
        this.sceneObjectData = rawGameData;
        this.ambientLight = ambientLight;
    }
    
    /** 
     * Constructs the LevelData from a file
     * @param filename The name of the level we want to load. The level must be located in the the LeadCrystal/Levels folder of your installation path
     * 
     */
    protected LevelData(String filePath) throws IOException, ClassNotFoundException
    {
       
        //get the file input stream of our level file       
        try( FileInputStream f = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(f);)
        {     
          
            //read in the raw data
            ArrayList<Object> rawGameLevelData = (ArrayList<Object>)ois.readObject();

            //Gets the scene objects from the first data slot
            this.sceneObjectData = (ArrayList<SceneObjectSaveData>)rawGameLevelData.get(0);
            this.ambientLight = (Color)rawGameLevelData.get(7);
            
            //get filename
            String[] pieces = filePath.split("\\\\");          
            this.filename = pieces[pieces.length-1];
        }
        catch(ClassNotFoundException|IOException e){System.err.println("Level Load Failed: "); e.printStackTrace(System.err); throw e;}

    }
      
    
    //===============
    // Class Methods
    //===============
    
    /**
     * Saves the raw level data to the disk
     * @param name 
     */
    public void writeLevelDataToDisk(String name)
    {
        //build the raw data array that will be saved
        ArrayList<Object> levelData = new ArrayList();  
        levelData.add(0,sceneObjectData);
        levelData.add(1,0);
        levelData.add(2,0);
        levelData.add(3,0);
        levelData.add(4,0);
        levelData.add(5,0);
        levelData.add(6,0);
        levelData.add(7,ambientLight);

        
        //get path of the LeadCrystal install folder
        File file = new File("");
        String path = file.getAbsolutePath();
        
        
        //write that raw data to the disk
         try (FileOutputStream f = new FileOutputStream(path +"\\Custom Levels\\" +name); 
              ObjectOutputStream oos = new ObjectOutputStream(f);)
         {           
             oos.writeObject(levelData);
             oos.close();
         }        
         catch (Exception e) {System.err.println("Level save failed: "); e.printStackTrace(System.err);}     
    }
    
    //==================
    // Accessor Methods
    //==================
    
    /**
     * Gets the ambient light of the level
     * @return the ambient light of the level
     */
    public Color getAmbientLight()
    {
        return this.ambientLight;
    }
    
    public ArrayList<SimpleEntry<SceneObject,Layer>> getSceneObjects()
    {
        
        ArrayList<SimpleEntry<SceneObject,Layer>> returnList = new ArrayList();
         
        //Iterates through the scene object save data, builds them, and adds them to the return list
        for (SceneObjectSaveData saveData : sceneObjectData)
        {
            SceneObject sceneObject;
            try
            {               
                sceneObject = SceneObjectDeserializer.buildSceneObjectFromSaveData(saveData);                            
            }
            catch(Exception e)
            {
                System.err.println("Fillscene Fail");
                sceneObject = new Image("textureMissing.jpg");
            }
            
            //Add the object to the return list, with its layer
            Layer layer = Layer.fromIntToLayer((Integer)saveData.dataMap.get("layer")); 
            SimpleEntry<SceneObject,Layer> entry = new SimpleEntry(sceneObject, layer); 
            returnList.add(entry);
        }  
        
        return returnList;
    }
      
    
    
}
