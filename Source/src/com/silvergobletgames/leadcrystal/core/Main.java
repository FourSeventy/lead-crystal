package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.leadcrystal.scenes.BrandingScene;
import com.silvergobletgames.leadcrystal.scenes.LoadingScene;
import com.silvergobletgames.leadcrystal.scenes.MainMenuScene;
import com.silvergobletgames.sylver.core.EngineSettings;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.Game.SystemExitAction;
import com.silvergobletgames.sylver.core.GameConfiguration;
import com.silvergobletgames.sylver.util.Log;
import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.URI;
import java.util.ArrayList;


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
        
        //set title
        Game.getInstance().getGraphicsWindow().setTitle("Titanis");
        
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
        Game.getInstance().setStateVariable("finishedLoading", false);
        
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
                    Log.error( "Error Loading TextRenderer: {0}", e.toString());
                }
                
                //load textures we need for menus
                Main.loadMenuTextures();
                

                //load the main menu scene (will be switched into once the branding is done)
                Game.getInstance().loadScene(new MainMenuScene()); //TODO: eliminate race condition

                //load game textures 
                URI textureURI = Game.getInstance().getConfiguration().getTextureRootFolder(); 
                try
                {
                    Game.getInstance().getAssetManager().getTextureLoader().loadAllTexturesInDirectory(textureURI);
                }
                catch (IOException ex)
                {
                     //log error to console
                    Log.error( "Error Loading Textures: {0}", ex.toString());
                    
                }

                
                //load all sounds    
                try
                {
                   Game.getInstance().getAudioRenderer().loadAllSounds(Game.getInstance().getConfiguration().getSoundRootFolder().resolve("buffered/")); 
                }
                catch(Exception e)
                {
                    //log error to console
                    Log.error( "Error Loading Sounds: {0}", e.toString());
                    
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
                    Log.error( "Error Loading Level: {0}", e.toString());
                     
                    
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
    
    
    private static void loadMenuTextures()
    {
        URI textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("misc/mainMenuBackground.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "mainmenubackground.png");
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("ui/tallFrameMenu.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "tallframemenu.png");
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("misc/blank.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "blank.png"); 
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("ui/map_arrow.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "map_arrow.png");               
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("ui/goldCoin.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "goldcoin.png");              
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("ui/tallFrameMenu.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "tallframemenu.png");
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("ui/reticle07.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "reticle07.png");      
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("characters/bash/head/bash-head0.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "bash-head0.png");
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("characters/bash/head/bash-head1.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "bash-head1.png");
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("characters/bash/head/bash-head2.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "bash-head2.png");
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("characters/bash/head/bash-head3.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "bash-head3.png");
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("characters/bash/head/bash-head4.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "bash-head4.png");
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("characters/bash/body/brown/bash_brown.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "bash_brown.png");
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("characters/bash/body/black/bash_black.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "bash_black.png");
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("characters/bash/body/blue/bash_blue.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "bash_blue.png");
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("characters/bash/body/green/bash_green.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "bash_green.png");
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("characters/bash/body/white/bash_white.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "bash_white.png");
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("characters/bash/body/red/bash_red.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "bash_red.png");
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("characters/bash/body/yellow/bash_yellow.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "bash_yellow.png");    
        
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("ui/logo.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "logo.png"); 
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("effects/leftClickIcon.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "leftclickicon.png"); 
        
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("misc/slow.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "slow.png"); 
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("misc/stunSpiral.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "stunspiral.png"); 
        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("effects/teleport2.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "teleport2.png"); 
        
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/attackSpeedIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "attackspeedicon.png");             
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/ccReductionIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "ccreductionicon.png");       
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/critChanceIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "critchanceicon.png");      
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/critDamageIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "critdamageicon.png");      
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/damageIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "damageicon.png");      
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/goldFindIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "goldfindicon.png");        
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/hardenedArmorIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "hardenedarmoricon.png");      
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/healingEffectivenessIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "healingeffectivenessicon.png");       
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/healthIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "healthicon.png");       
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/lifeLeechIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "lifeleechicon.png");       
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/meleeDamageIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "meleedamageicon.png");       
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/moveSpeedIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "movespeedicon.png");        
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/numberOfPotionsIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "numberofpotionsicon.png");     
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/radarUpgradeIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "radarupgradeicon.png");        
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/seeEnemyHealthIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "seeenemyhealthicon.png");        
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/specialArmorIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "specialarmoricon.png");   
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/steroidPotionIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "steroidpotionicon.png");       
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/superArmorIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "superarmoricon.png");       
//        textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("icons/thornsIcon.png");  
//        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "thornsicon.png");
        
        
        
        

    }
}
