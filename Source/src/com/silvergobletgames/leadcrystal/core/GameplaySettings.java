package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.sylver.core.Game;
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
    
    //=====================
    // Debug Variables
    //=====================
    public boolean bodyWireframe = false;
    public boolean viewportFeelers = false;
    public boolean drawPlayerServerTime = false;
    public boolean networkDebugging = false;
    public boolean packetSizeDebugging  = false;
    public boolean drawNetworkingStats = true;
    public boolean debugEnemies = false;
    
    
    //===================
    // Gameplay Settings
    //=================== 
    public boolean showSpawnLocations = false;
    public boolean showCooldownTimers = false;
    
    
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
            //set properties
            Properties iniSaver = new Properties();
            iniSaver.setProperty("debug_bodyWireframe", Boolean.toString(this.bodyWireframe));
            iniSaver.setProperty("debug_viewportFeelers", Boolean.toString(this.viewportFeelers));
            iniSaver.setProperty("debug_drawPlayerServerTime", Boolean.toString(this.drawPlayerServerTime));
            iniSaver.setProperty("debug_networkDebugging", Boolean.toString(this.networkDebugging));
            iniSaver.setProperty("debug_packetSizeDebugging", Boolean.toString(this.packetSizeDebugging));
            iniSaver.setProperty("debug_drawNetworkingStats", Boolean.toString(this.drawNetworkingStats));
            iniSaver.setProperty("debug_debugEnemies",Boolean.toString(this.debugEnemies));
            iniSaver.setProperty("showCooldownTimers",Boolean.toString(this.showCooldownTimers));

            //open output stream
            OutputStream out = Files.newOutputStream(Paths.get(filePath));
            iniSaver.store(out, "Gameplay Settings");
        }
        catch(Exception e)
        {
             //log error to console
            Logger logger =Logger.getLogger(GameplaySettings.class.getName());
            logger.log(Level.SEVERE, "Could not dump gameplaySettings.ini to file: " + e.toString());
            logger.addHandler(new ConsoleHandler());     
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
            
            //get values
            this.bodyWireframe = Boolean.parseBoolean(iniLoader.getProperty("debug_bodyWireframe"));
            this.viewportFeelers = Boolean.parseBoolean(iniLoader.getProperty("debug_viewportFeelers"));
            this.drawPlayerServerTime = Boolean.parseBoolean(iniLoader.getProperty("debug_drawPlayerServerTime"));
            this.networkDebugging = Boolean.parseBoolean(iniLoader.getProperty("debug_networkDebugging"));
            this.packetSizeDebugging = Boolean.parseBoolean(iniLoader.getProperty("debug_packetSizeDebugging"));
            this.drawNetworkingStats = Boolean.parseBoolean(iniLoader.getProperty("debug_drawNetworkingStats"));
            this.debugEnemies = Boolean.parseBoolean(iniLoader.getProperty("debug_debugEnemies"));
            this.showCooldownTimers = Boolean.parseBoolean(iniLoader.getProperty("showCooldownTimers"));

        }
        catch(IOException e)
        {
            //log error to console
            Logger logger =Logger.getLogger(GameplaySettings.class.getName());
            logger.log(Level.SEVERE, "Could not open gameplaySettings.ini file: " + e.toString());
            logger.addHandler(new ConsoleHandler()); 
        }
        
        
    }
    
    
}
