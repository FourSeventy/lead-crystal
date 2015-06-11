package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.sylver.graphics.TextureLoader;
import com.silvergobletgames.sylver.util.Log;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class LevelLoader 
{
    //level map
    private ConcurrentHashMap<String,LevelData> levelMap = new ConcurrentHashMap<>();
            
    
    //===============
    // Constructor
    //===============
    
    private LevelLoader()
    {
    }
    
    public static LevelLoader getInstance()
    {
        return LevelLoaderHolder.INSTANCE;
    }
    
    private static class LevelLoaderHolder 
    {
        private static final LevelLoader INSTANCE = new LevelLoader();
    }
    
    
    //===============
    // Class Methods
    //===============
    
    public void loadLevels()
    {
        
        //get path of the LeadCrystal install folder
        File file = new File("");
        String path =file.getAbsolutePath();

        File levelFolder = new File(path+"\\Levels\\");
        
        File[] levelFiles = levelFolder.listFiles();

        //iterate through files in the folder and load them
        String fileName;
        for (int i = 0; i < levelFiles.length; i++) 
        {
            if (levelFiles[i].isFile()) 
            {
                fileName = levelFiles[i].getName();
                
                LevelData data = null;
                try
                {
  
                    
                    data = new LevelData(path +"\\Levels\\" +fileName);
                }
                catch(Exception e)
                {
                  System.err.println("cant load Level");
                }
                
                if(data != null)
                {
                    this.levelMap.put(fileName, data);
                }

            }
        }
        
        //if level editor is enabled load custom levels
        if(GameplaySettings.getInstance().levelEditor)
        {
            File customFolder = new File(path+"\\Custom Levels\\");
            File[] customFiles = customFolder.listFiles();
            for (int i = 0; i < customFiles.length; i++) 
            {
                if (customFiles[i].isFile()) 
                {
                    fileName = customFiles[i].getName();

                    LevelData data = null;
                    try
                    {
                        data = new LevelData(path +"\\Custom Levels\\" +fileName);
                    }
                    catch(Exception e)
                    {
                      System.err.println("cant load Level");
                    }

                    if(data != null)
                    {
                        this.levelMap.put(fileName, data);
                    }

                }
            }
        }
        
    }
    
    public void reloadLevel(String filename)
    {
        this.levelMap.remove(filename);
        LevelData data = null;
        try
        {
            File file = new File("");
            String path =file.getAbsolutePath();
             data = new LevelData(path +"\\Custom Levels\\" +filename);
        }
        catch (IOException | ClassNotFoundException ex)
        {
            Log.error( "Level loading problem", ex);
        }
        this.levelMap.put(filename, data);
        
    }
    
    public LevelData getLevelData(String levelName)
    {
        return this.levelMap.get(levelName);
    }
    
    public boolean isLevelLoaded(String filename)
    {
        return this.levelMap.containsKey(filename);
    }
    
    
    
}
