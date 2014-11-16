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
        
        //signal not done loading
        Game.getInstance().setStateVariable("finishedLoading", true);
        
        //change us into the branding scene
        Game.getInstance().loadScene(new BrandingScene());
        Game.getInstance().changeScene(BrandingScene.class, null);
        
           
        //thread to load our textures, sounds, fonts, and levels
        Thread mainLoadingThread = new Thread()
        {
            @Override
            public void run()
            {
                //load text renderers
                try
                {
                    //((LoadingScene)Game.getInstance().getScene(MainMenuScene.class)).setCurrentProgressText("Loading Text Renderers");        
                    LeadCrystalTextType.loadTextTypes();
                }
                catch(Exception e)
                {
                    //log error to console
                    Logger logger =Logger.getLogger(Main.class.getName());
                    logger.log(Level.SEVERE, "Error Loading TextRenderer: {0}", e.toString());
                    logger.addHandler(new ConsoleHandler()); 
                }
                
                //load textures we need for menus
                URI textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("misc/mainMenuBackground.png");  
                Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "mainmenubackground.png");
                textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("ui/tallFrameMenu.png");  
                Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "tallframeMmenu.png");
                textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("misc/blank.png");  
                Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "blank.png"); 
                textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("ui/map_arrow.png");  
                Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "map_arrow.png");               
                textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("ui/goldCoin.png");  
                Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "goldcoin.png");              
                textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("ui/tallFrameMenu.png");  
                Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "tallframemenu.png");
                textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("misc/mouse_hand.png");  
                Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "mouse_hand.png");

                //load the main menu scene (will be switched into once the branding is done)
                Game.getInstance().loadScene(new MainMenuScene()); //TODO: eliminate race condition

                //load game textures 
                textureURI = Game.getInstance().getConfiguration().getTextureRootFolder(); 
                try
                {
                    Game.getInstance().getAssetManager().getTextureLoader().loadAllTexturesInDirectory(textureURI);
                }
                catch (IOException ex)
                {
                     //log error to console
                    Logger logger =Logger.getLogger(Main.class.getName());
                    logger.log(Level.SEVERE, "Error Loading Textures: {0}", ex.toString());
                    logger.addHandler(new ConsoleHandler()); 
                }

                
                //load all sounds    
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
                    //load game textures 
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
                
                //signal done loading
                Game.getInstance().setStateVariable("finishedLoading", true);
                   
            }
            
        };       
        mainLoadingThread.setUncaughtExceptionHandler(new UncaughtExceptionHandler(){        
            @Override
            public void uncaughtException(Thread t, Throwable e)
            {
                Game.getInstance().uncaughtExceptionHandlingActions(e);
            }
        });
        mainLoadingThread.start();
        
        
        //start the game loop
        Game.getInstance().gameLoop();
        
        
        
    
    }
    
}
