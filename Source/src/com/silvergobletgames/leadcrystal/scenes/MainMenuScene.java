package com.silvergobletgames.leadcrystal.scenes;

import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.core.SaveGame;
import com.silvergobletgames.leadcrystal.menus.ErrorMenu;
import com.silvergobletgames.leadcrystal.netcode.ConnectionException;
import com.silvergobletgames.leadcrystal.netcode.GobletServer;
import com.silvergobletgames.leadcrystal.netcode.GobletServer.ServerConfiguration;
import com.silvergobletgames.sylver.audio.AudioRenderer;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.*;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.OpenGLGameWindow;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import com.silvergobletgames.sylver.graphics.TextureLoader;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.media.opengl.GL3bc;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author mike
 */
public class MainMenuScene extends Scene 
{   
    
    //==============
    // Constructor
    //==============
    
    public MainMenuScene()
    {
            
        
        //build background image
        Image back = new Image("mainMenuBackground.png");
        back.setPosition(0, 0);
        back.setDimensions(1600, 900);
        this.add(back,Layer.BACKGROUND);
        
        //center and right
        final int right = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x;
        final int center = right/2;

        Text leadCrystal = new Text("Lead Crystal",LeadCrystalTextType.MENU60);
        leadCrystal.setPosition(center - leadCrystal.getWidth()/2, 700);       
        this.add(leadCrystal,Layer.MAIN);
        
        //single player
        final Text singlePlayerText = new Text("Single Player",LeadCrystalTextType.MENU54);
        singlePlayerText.setPosition(center - singlePlayerText.getWidth()/2, 575);
        final Button singlePlayerButton = new Button(new Image("blank.png"), center - singlePlayerText.getWidth()/2, singlePlayerText.getPosition().y, singlePlayerText.getWidth(), singlePlayerText.getHeight());
        this.add(singlePlayerText,Layer.MAIN);
        this.add(singlePlayerButton,Layer.MAIN);
        singlePlayerButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    singleplayerButton_click();
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(singlePlayerText.hasTextEffect("small"))
                          singlePlayerText.removeTextEffect("small");
                      
                       singlePlayerText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, singlePlayerText.getScale(), 1.20f));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(singlePlayerText.hasTextEffect("big"))
                           singlePlayerText.removeTextEffect("big");
                        
                        singlePlayerText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, singlePlayerText.getScale(), 1f));
                }
            }
        });
        
        //multiplayer 
        final Text multiPlayerText = new Text("Multiplayer",LeadCrystalTextType.MENU54);
        multiPlayerText.setPosition(center - multiPlayerText.getWidth()/2, 500);
        final Button multiPlayerButton = new Button(new Image("blank.png"), center - multiPlayerText.getWidth()/2, multiPlayerText.getPosition().y, multiPlayerText.getWidth(), multiPlayerText.getHeight());
        this.add(multiPlayerText,Layer.MAIN);
        this.add(multiPlayerButton,Layer.MAIN);
        multiPlayerButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    multiplayerButton_click();
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(multiPlayerText.hasTextEffect("small"))
                          multiPlayerText.removeTextEffect("small");
                      
                       multiPlayerText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, multiPlayerText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(multiPlayerText.hasTextEffect("big"))
                           multiPlayerText.removeTextEffect("big");
                        
                        multiPlayerText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, multiPlayerText.getScale(), 1));
                }
            }
        });
        
        //options
        final Text optionsText = new Text("Options",LeadCrystalTextType.MENU54);
        optionsText.setPosition(center - optionsText.getWidth()/2, 425);
        final Button optionsButton = new Button(new Image("blank.png"), center - optionsText.getWidth()/2, optionsText.getPosition().y, optionsText.getWidth(), optionsText.getHeight());
        this.add(optionsText,Layer.MAIN);
        this.add(optionsButton,Layer.MAIN);
        optionsButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    optionsButton_click();
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(optionsText.hasTextEffect("small"))
                          optionsText.removeTextEffect("small");
                      
                       optionsText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, optionsText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(optionsText.hasTextEffect("big"))
                           optionsText.removeTextEffect("big");
                        
                        optionsText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, optionsText.getScale(), 1));
                }
            }
        });
        
        //level editor
        final Text levelEditorText = new Text("Level Editor",LeadCrystalTextType.MENU54);
        levelEditorText.setPosition(center - levelEditorText.getWidth()/2, 350);
        final Button levelEditorButton = new Button(new Image("blank.png"), center - levelEditorText.getWidth()/2, levelEditorText.getPosition().y, levelEditorText.getWidth(), levelEditorText.getHeight());
        this.add(levelEditorText,Layer.MAIN);
        this.add(levelEditorButton,Layer.MAIN);
        levelEditorButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    levelEditorButton_click();
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(levelEditorText.hasTextEffect("small"))
                          levelEditorText.removeTextEffect("small");
                      
                       levelEditorText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, levelEditorText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(levelEditorText.hasTextEffect("big"))
                           levelEditorText.removeTextEffect("big");
                        
                        levelEditorText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, levelEditorText.getScale(), 1));
                }
            }
        });
        
        //exit
        final Text exitText = new Text("Exit",LeadCrystalTextType.MENU54);
        exitText.setPosition(center - exitText.getWidth()/2, 275);
        final Button exitButton = new Button(new Image("blank.png"), center - exitText.getWidth()/2, exitText.getPosition().y, exitText.getWidth(), exitText.getHeight());
        this.add(exitText,Layer.MAIN);
        this.add(exitButton,Layer.MAIN);
        exitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    exitButton_click();
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(exitText.hasTextEffect("small"))
                          exitText.removeTextEffect("small");
                      
                       exitText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, exitText.getScale(),1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(exitText.hasTextEffect("big"))
                           exitText.removeTextEffect("big");
                        
                        exitText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, exitText.getScale(), 1));
                }
            }
        });

        this.update();
    }
    
    
    //===============
    // Scene Methods
    //===============


    public void handleInput() 
    {
    }  
     

    public void sceneEntered(ArrayList args)
    {
        if(args != null && args.get(0) instanceof Boolean && (boolean)args.get(0) == true)
        {
            Sound sound = Sound.newBGM("streaming/mainMenu.ogg");
            add(sound);
            sound = Sound.adjustSourceVolume("BGM", .2f);
            add(sound);
        }
        
        //set mouse cursor
        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND));
             
              
    }
    
    public void sceneExited()
    {
        Game.getInstance().unloadScene(MainMenuScene.class);
    }
    
    
    //===============
    // Handle Clicks
    //===============
    
    public void singleplayerButton_click() 
    {
        //build args to send to the character selection screen
        ArrayList args = new ArrayList();
        args.add("Singleplayer");
        
        //load and switch scenes
        Game.getInstance().loadScene( new CharacterSelectionScene());
        Game.getInstance().changeScene(CharacterSelectionScene.class,args);
    }
    
    private void multiplayerButton_click()
    {
        //build args to send to the character selection screen
        ArrayList args = new ArrayList();
        args.add("Multiplayer");
        
        //load and switch scenes
        Game.getInstance().loadScene(new CharacterSelectionScene());
        Game.getInstance().changeScene(CharacterSelectionScene.class,args);
    }
    
    public void optionsButton_click()
    {
        Game.getInstance().loadScene(new OptionsMenuScene()); 
        Game.getInstance().changeScene(OptionsMenuScene.class,null);
    }

    public void levelEditorButton_click() 
    {
        Game.getInstance().loadScene(new MapEditorScene());
        Game.getInstance().changeScene(MapEditorScene.class,null);
    }
 
    private void exitButton_click()
    {
        Game.getInstance().exitGame();
    }
    
    
    //=================
    // Static Methods
    //================
    
    public static void startSinglePlayerGame(final SaveGame saveGame)
    {                 
                 
        //load loading scene
        Game.getInstance().loadScene(new LoadingScene());
        Game.getInstance().changeScene(LoadingScene.class, null);

        Thread thread = new Thread()
        {
            @Override
            public void run()
            {

                //start a server
                ServerConfiguration config = new ServerConfiguration();
                config.singlePlayer = true;
                config.tcpPort = GameplaySettings.getInstance().tcpPort; 
                config.udpPort = GameplaySettings.getInstance().udpPort;
                Game.getInstance().addRunnable("Goblet Server", new GobletServer(config));

                //load game client scene
                Game.getInstance().loadScene(new GameClientScene(saveGame)); 

                //connect to server
                try
                {
                    ((GameClientScene)Game.getInstance().getScene(GameClientScene.class)).connectToServer( "127.0.0.1", config.tcpPort,config.udpPort);                   
                }
                catch(ConnectionException e){System.err.println("Couldnt connect: " + e.reason); return;}

                try{
                    Thread.sleep(200);
                }
                catch(Exception e){}
                
                //switch to GameClientScene
                Game.getInstance().changeScene(GameClientScene.class,null); 

                //unload character selection scene
                Game.getInstance().unloadScene(CharacterSelectionScene.class);

                //unload loading scene
                Game.getInstance().unloadScene(LoadingScene.class);
                
                
            }
        };

        thread.start();
        
    }
    
    public static void hostMultiPlayerGame(final SaveGame saveGame, final ServerConfiguration config)
    {
        Game.getInstance().loadScene(new LoadingScene());
        Game.getInstance().changeScene(LoadingScene.class, null);

        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                //if game client is loaded for some reason unload it
                if(Game.getInstance().isLoaded(GameClientScene.class))
                    Game.getInstance().unloadScene(GameClientScene.class); 

                //start a server
                Game.getInstance().addRunnable("Goblet Server", new GobletServer(config));

                //load game client scene
                Game.getInstance().loadScene(new GameClientScene(saveGame)); 

                //connect to server
                try
                {
                   ((GameClientScene)Game.getInstance().getScene(GameClientScene.class)).connectToServer( "127.0.0.1", config.tcpPort,config.udpPort);                   
                }
                catch(ConnectionException e){System.err.println("Couldnt connect: " + e.reason); return;}

                try{
                Thread.sleep(200);
                }
                catch(Exception e){}
                
                //switch to GameClientScene
                Game.getInstance().changeScene(GameClientScene.class,null); 

                //unload character selection scene
                Game.getInstance().unloadScene(CharacterSelectionScene.class);

                //unload loading scene
                Game.getInstance().unloadScene(LoadingScene.class);
                
                
            }
        };

        thread.start();
    }
    
    public static void joinMultiPlayerGame(final SaveGame saveGame,final String ip, final int tcpPort, final int udpPort)
    {
        Game.getInstance().loadScene(new LoadingScene());
        Game.getInstance().changeScene(LoadingScene.class, null);

        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                //if game client is loaded for some reason unload it
                if(Game.getInstance().isLoaded(GameClientScene.class))
                    Game.getInstance().unloadScene(GameClientScene.class); 

                //load game client scene
                Game.getInstance().loadScene(new GameClientScene(saveGame)); 

                //connect to server
                try
                {
                   ((GameClientScene)Game.getInstance().getScene(GameClientScene.class)).connectToServer(ip, tcpPort,udpPort);                   
                }
                catch(ConnectionException e)
                {
                    final int right = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x;
                    final int center = right/2;
        
                    Game.getInstance().loadScene(new MultiplayerMenuScene());
                    ArrayList args = new ArrayList();
                    args.add(saveGame);
                    Game.getInstance().changeScene(MultiplayerMenuScene.class, args); 
                    Game.getInstance().unloadScene(GameClientScene.class); 
                    Game.getInstance().unloadScene(LoadingScene.class);
                    String additional = "";
                    if(e.exception != null)
                        additional = e.exception.getMessage();
                    Game.getInstance().getScene(MultiplayerMenuScene.class).add(new ErrorMenu(e.reason.toString() + ": " + additional, center-300,450-200), Layer.MAIN); 
                    return;
                }

                try
                {
                Thread.sleep(500);
                }
                catch(Exception e){}
                
                //switch to GameClientScene
                Game.getInstance().changeScene(GameClientScene.class,null); 

                //unload loading scene
                Game.getInstance().unloadScene(LoadingScene.class);
                
                
            }
        };

        thread.start();
    }
    
    
    
    
   
}
