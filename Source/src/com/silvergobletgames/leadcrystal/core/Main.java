package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.leadcrystal.scenes.BrandingScene;
import com.silvergobletgames.leadcrystal.scenes.LoadingScene;
import com.silvergobletgames.leadcrystal.scenes.MainMenuScene;
import com.silvergobletgames.sylver.core.EngineSettings;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.Game.SystemExitAction;
import com.silvergobletgames.sylver.core.GameConfiguration;
import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.URI;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main 
{

    public static void main(String[] args) 
    {          
        
        //setting some system properties
        System.setProperty("sun.java2d.noddraw", "true");
        System.setProperty("sun.java2d.opengl", "true"); 
                  
        
        //get resource URI paths       
        File rootDirectory = new File("");
        URI textureFolderURI = rootDirectory.toURI().resolve("Graphics/");
        URI soundFolderURI = rootDirectory.toURI().resolve("Sounds/");
      
        
        //try to load system settings from file        
        EngineSettings engineSettings;
        try
        {   
            File directory = new File("");
            URI systemSettingsPath = directory.toURI().resolve("System/engineSettings.ini");
            engineSettings = EngineSettings.constructFromFile(systemSettingsPath);
        }
        catch(Exception e) 
        {
            e.printStackTrace(System.err);
            engineSettings = new EngineSettings();
            
            //try to save the default system settings                  
            try
            {
                File directory = new File ("");
                URI systemSettingsPath = directory.toURI().resolve("System/engineSettings.ini");
                engineSettings.dumpSettingsToFile(systemSettingsPath);
            }
            catch(Exception ex){ System.err.println("couldnt save settings");}
        }

        //build game configuration
        GameConfiguration configuration = new GameConfiguration(textureFolderURI,soundFolderURI,engineSettings);         
            
        //creates the game
        Game.getInstance().createGame(configuration);
        
        //register game exit actions
        Game.getInstance().registerSystemExitAction(new SystemExitAction(){
            public void action()
            {
                //saving system settings
                try
                {
                    File directory = new File (""); 
                    URI systemSettingsPath = directory.toURI().resolve("System/engineSettings.ini");
                    Game.getInstance().getConfiguration().getEngineSettings().dumpSettingsToFile(systemSettingsPath);
                }
                catch(Exception ex){ System.err.println("couldnt save settings");}
            }
        });      
        Game.getInstance().registerSystemExitAction(new SystemExitAction(){
            public void action()
            {
                //saving gameplay settings
                try
                {
                File directory = new File ("");
                URI systemSettingsPath = directory.toURI().resolve("System/gameSettings.ini");
                GameplaySettings.getInstance().dumpSettingsToFile(systemSettingsPath);
                }
                catch(Exception ex){ System.err.println("couldnt save settings");}
                
            }
        });
        
        //loads gameplay settings      
        try
        {   
            File directory = new File("");
            URI systemSettingsPath = directory.toURI().resolve("System/gameSettings.ini");
            GameplaySettings.getInstance().loadFromFile(systemSettingsPath);
        }
        catch(Exception e) 
        {
            e.printStackTrace(System.err);
            
            //try to save the default system settings                  
            try
            {
                File directory = new File ("");
                URI systemSettingsPath = directory.toURI().resolve("System/gameSettings.ini");
                GameplaySettings.getInstance().dumpSettingsToFile(systemSettingsPath);
            }
            catch(Exception ex){ System.err.println("couldnt save settings");}
        }
        
        //change us into the branding scene
        Game.getInstance().loadScene(new BrandingScene());
        Game.getInstance().changeScene(BrandingScene.class, null);
        
        //load the loading scene (will be switched into once the branding is done)
        Game.getInstance().loadScene(new LoadingScene());
        
                
        //thread to load our textures, sounds, fonts, and levels
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {

                //load game textures 
                ((LoadingScene)Game.getInstance().getScene(LoadingScene.class)).setCurrentProgressText("Loading Textures");
                URI textureURI = Game.getInstance().getConfiguration().getTextureRootFolder(); 
                try
                {
                    Game.getInstance().getAssetManager().getTextureLoader().loadAllTextures(textureURI);
                }
                catch (IOException ex)
                {
                     //log error to console
                    Logger logger =Logger.getLogger(Main.class.getName());
                    logger.log(Level.SEVERE, "Error Loading Textures: {0}", ex.toString());
                    logger.addHandler(new ConsoleHandler()); 
                }

                
                //load all sounds
                ((LoadingScene)Game.getInstance().getScene(LoadingScene.class)).setCurrentProgressText("Loading Sounds");      
                try
                {
                   Game.getInstance().getAudioRenderer().loadAllSounds(Game.getInstance().getConfiguration().getSoundRootFolder().resolve("buffered/")); 
                }
                catch(Exception e)
                {
                    //log error to console
                    Logger logger =Logger.getLogger(Main.class.getName());
                    logger.log(Level.SEVERE, "Error Loading Sounds: {0}", e.toString());
                    logger.addHandler(new ConsoleHandler()); 
                }
                
                //load all levels
                try
                {
                    ((LoadingScene)Game.getInstance().getScene(LoadingScene.class)).setCurrentProgressText("Loading Levels");        
                     LevelLoader.getInstance().loadLevels();
                }
                catch(Exception e)
                {
                    //log error to console
                    Logger logger =Logger.getLogger(Main.class.getName());
                    logger.log(Level.SEVERE, "Error Loading Level: {0}", e.toString());
                    logger.addHandler(new ConsoleHandler()); 
                    
                    //rethrow error because it is critical
                    throw new RuntimeException("Error Loading Level", e);
                }
                       
                
                
                //load text renderers
                try
                {
                    ((LoadingScene)Game.getInstance().getScene(LoadingScene.class)).setCurrentProgressText("Loading Text Renderers");        
                    LeadCrystalTextType.loadTextTypes();
                }
                catch(Exception e)
                {
                    //log error to console
                    Logger logger =Logger.getLogger(Main.class.getName());
                    logger.log(Level.SEVERE, "Error Loading TextRenderer: {0}", e.toString());
                    logger.addHandler(new ConsoleHandler()); 
                }
        
                //load the main menu scene
                Game.getInstance().loadScene(new MainMenuScene());
                //change to the main menu scene
                Game.getInstance().changeScene(MainMenuScene.class,new ArrayList(){{add(true);}});   
                //unload loading scene
                Game.getInstance().unloadScene(LoadingScene.class);
            }
            
        };       
        thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler(){        
            @Override
            public void uncaughtException(Thread t, Throwable e)
            {
                Game.getInstance().uncaughtExceptionHandlingActions(e);
            }
        });
        thread.start();
        
        
        //start the game loop
        Game.getInstance().gameLoop();
        
        
        
    
    }
    
}
