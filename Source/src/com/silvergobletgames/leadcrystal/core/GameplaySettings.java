package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.util.Log;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Mike
 */
public class GameplaySettings{
    
    private static GameplaySettings instance;
    
    private GameplaySettings()
    {
        
    }
    
    public static GameplaySettings getInstance()
    {
        if(instance == null)
            instance = new GameplaySettings();
        
        return instance;
    }
    
    
    
    //===================
    // Gameplay Settings
    //=================== 
    public boolean showCooldownTimers = false;

    
    //=====================
    // Dev Settings
    //=====================
    public boolean bodyWireframe = false;
    public boolean viewportFeelers = false;  
    public boolean debugEnemies = false;
    public boolean levelEditor = false;
    
    
    
    
    
     //===============
    // Class Methods
    //=============== 
    
    /**
     * Dump the engine settings to an .ini file with given path. Path must include the filename
     * @param filePath URI to dump the file. Must include the filename. eg. C:\folder\engineSettings.ini
     */
    public void dumpSettingsToFile(URI filePath)
    {

        try
        {
            Properties iniSaver = new Properties();
            iniSaver.setProperty("showCooldownTimers",Boolean.toString(this.showCooldownTimers));         
            iniSaver.setProperty("debug_bodyWireframe", Boolean.toString(this.bodyWireframe));
            iniSaver.setProperty("debug_viewportFeelers", Boolean.toString(this.viewportFeelers));          
            iniSaver.setProperty("debug_debugEnemies",Boolean.toString(this.debugEnemies));
            iniSaver.setProperty("levelEditor",Boolean.toString(this.levelEditor));
            
            
           
            //open output stream
            OutputStream out = Files.newOutputStream(Paths.get(filePath));
            iniSaver.store(out, "Gameplay Settings");
        }
        catch(IOException e)
        {
             //log error to console
            Log.error("Could not dump gameplaySettings.ini to file: ", e);  
        }
    }
    
    /**
     * Load the settings in from a file.
     * @param filePath URI to .ini file that contains the settings to load
     * @return returns a constructed EngineSettings object with the settings from the file
     */
    public void loadFromFile(URI filePath)
    {       
        try
        {
            //open file
            InputStream inputStream = Files.newInputStream(Paths.get(filePath));
           
            //load file
            Properties iniLoader = new Properties();
            iniLoader.load(inputStream);
            
            //load game configs
            this.showCooldownTimers = Boolean.parseBoolean(iniLoader.getProperty("showCooldownTimers","false"));
            
            //load dev configs
            this.bodyWireframe = Boolean.parseBoolean(iniLoader.getProperty("debug_bodyWireframe","false"));
            this.viewportFeelers = Boolean.parseBoolean(iniLoader.getProperty("debug_viewportFeelers","false"));          
            this.debugEnemies = Boolean.parseBoolean(iniLoader.getProperty("debug_debugEnemies","false"));
            this.levelEditor = Boolean.parseBoolean(iniLoader.getProperty("levelEditor","false"));
            
            

        }
        catch(IOException | NumberFormatException e)
        {
            //log error to console
            Log.error( "Problem loading gameplaySettings.ini: ",e);
        }
        
        
    }
    
    
}
