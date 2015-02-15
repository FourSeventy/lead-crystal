package com.silvergobletgames.leadcrystal.scenes;

import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.core.SaveGame;
import com.silvergobletgames.leadcrystal.menus.ErrorMenu;
import com.silvergobletgames.leadcrystal.scenes.NewCharacterScene.PlayerMock;
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
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.media.opengl.GL2;
import javax.media.opengl.GL3bc;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author mike
 */
public class MainMenuScene extends Scene
{   
    
    private boolean inScene = false;
    
    
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

        Image logo = new Image("logo.png");
        logo.setPosition(center - logo.getWidth()/2 - 25, 650);
        this.add(logo,Layer.MAIN);
//        Text leadCrystal = new Text("Titanis",LeadCrystalTextType.MENU60);
//        leadCrystal.setPosition(center - leadCrystal.getWidth()/2, 700);       
//        this.add(leadCrystal,Layer.MAIN);
        
        //single player
        final Text newGameText = new Text("New Game",LeadCrystalTextType.MENU54);
        newGameText.setPosition(center - newGameText.getWidth()/2, 525);
        final Button newGameButton = new Button(new Image("blank.png"), center - newGameText.getWidth()/2, newGameText.getPosition().y, newGameText.getWidth(), newGameText.getHeight());
        this.add(newGameText,Layer.MAIN);
        this.add(newGameButton,Layer.MAIN);
        newGameButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    newGameButtonClick();
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(newGameText.hasTextEffect("small"))
                          newGameText.removeTextEffect("small");
                      
                       newGameText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, newGameText.getScale(), 1.20f));
                    
                    //play sound
                    if(MainMenuScene.this.inScene)
                    {
                        Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                        add(sound);
                    }
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(newGameText.hasTextEffect("big"))
                           newGameText.removeTextEffect("big");
                        
                        newGameText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, newGameText.getScale(), 1f));
                }
            }
        });
        
        //multiplayer 
        final Text loadGameText = new Text("Load Game",LeadCrystalTextType.MENU54);
        loadGameText.setPosition(center - loadGameText.getWidth()/2, 425);
        final Button loadGameButton = new Button(new Image("blank.png"), center - loadGameText.getWidth()/2, loadGameText.getPosition().y, loadGameText.getWidth(), loadGameText.getHeight());
        this.add(loadGameText,Layer.MAIN);
        this.add(loadGameButton,Layer.MAIN);
        loadGameButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    loadGameButtonClick();
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(loadGameText.hasTextEffect("small"))
                          loadGameText.removeTextEffect("small");
                      
                       loadGameText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, loadGameText.getScale(), 1.2));
                    
                    //play sound
                    if(MainMenuScene.this.inScene)
                    {
                        Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                        add(sound);
                    }
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(loadGameText.hasTextEffect("big"))
                           loadGameText.removeTextEffect("big");
                        
                        loadGameText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, loadGameText.getScale(), 1));
                }
            }
        });
        
        //options
        final Text optionsText = new Text("Options",LeadCrystalTextType.MENU54);
        optionsText.setPosition(center - optionsText.getWidth()/2, 325);
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
                    if(MainMenuScene.this.inScene)
                    {
                        Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                        add(sound);
                    }
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(optionsText.hasTextEffect("big"))
                           optionsText.removeTextEffect("big");
                        
                        optionsText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, optionsText.getScale(), 1));
                }
            }
        });
        
        if(GameplaySettings.getInstance().levelEditor == true)
        {
             //level editor
            final Text levelEditorText = new Text("Level Editor",LeadCrystalTextType.MENU54);
            levelEditorText.setPosition(center - levelEditorText.getWidth()/2, 225);
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
                        if(MainMenuScene.this.inScene)
                        {
                            Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                            add(sound);
                        }
                    }
                    if (e.getActionCommand().equals("mouseExited"))
                    {
                            if(levelEditorText.hasTextEffect("big"))
                               levelEditorText.removeTextEffect("big");

                            levelEditorText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, levelEditorText.getScale(), 1));
                    }
                }
            });
        }
        
        int exitPosition = 225;
        if(GameplaySettings.getInstance().levelEditor == true)
        {
            exitPosition = 125;
        }
        //exit
        final Text exitText = new Text("Exit",LeadCrystalTextType.MENU54);
        exitText.setPosition(center - exitText.getWidth()/2, exitPosition);
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
                    if(MainMenuScene.this.inScene)
                    {
                        Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                        add(sound);
                    }
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(exitText.hasTextEffect("big"))
                           exitText.removeTextEffect("big");
                        
                        exitText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, exitText.getScale(), 1));
                }
            }
        });
        
        
        final Text versionText = new Text("Version 0.9.9 \u00a9 Silver Goblet Games",LeadCrystalTextType.MENU15);
        versionText.setColor(new Color(Color.black));
        versionText.setPosition(10, 10);
        this.add(versionText,Layer.MAIN);
            

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
            sound = Sound.adjustSourceVolume("BGM", .15f);
            add(sound);
        }
        
        this.inScene = true;
        
        //set mouse cursor
        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.POINTERHAND));
             
              
    }
    
    public void sceneExited()
    {
        Game.getInstance().unloadScene(MainMenuScene.class);
    }
    
    /**
     * Renders everything in the scene using either the GL2 or GL3 renderer, based on the GlCapabilities
     * @param gl 
     */
    public void render(GL2 gl)
    {
        //set viewport size
        getViewport().setDimensions(Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x, Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().y);
                  
       
        //===============
        // GL2 rendering
        //===============
        RenderingPipelineGL2.render(gl, getViewport(), getSceneObjectManager(), getSceneEffectsManager()); 
                
    }
 
    
    
    //===============
    // Handle Clicks
    //===============
    
    public void newGameButtonClick() 
    {
        //change to new character scene
        Game.getInstance().loadScene(new NewCharacterScene());
        ArrayList<String> args = new ArrayList();
        Game.getInstance().changeScene(NewCharacterScene.class, args);
    }
    
    private void loadGameButtonClick()
    {
        //build args to send to the character selection screen
        ArrayList args = new ArrayList();
        
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
        
        
        //check if we are still loading assets
        if((boolean)Game.getInstance().getStateVariable("finishedLoading") != true )
        {
            //load loading scene
            Game.getInstance().loadScene(new LoadingScene());
            Game.getInstance().changeScene(LoadingScene.class, null);
            
            ((LoadingScene)Game.getInstance().getScene(LoadingScene.class)).setCurrentProgressText("Loading Assets");
            
            //if so start up a thread that spins waiting for it to be done, and calls gameLaunchThread.start() upon completion
            Thread assetLoadThread = new Thread(){
                
                @Override
                public void run()
                {
                    while((boolean)Game.getInstance().getStateVariable("finishedLoading") != true)
                    {
                        try{Thread.sleep(50);}
                        catch(Exception e){}
                    }
                    
                    Game.getInstance().loadScene(new MapEditorScene());
                    Game.getInstance().changeScene(MapEditorScene.class,null);
                    Game.getInstance().unloadScene(LoadingScene.class);
                }
            };
            assetLoadThread.start();
        }
        else //if not, just launch the game
        {
            Game.getInstance().loadScene(new MapEditorScene());
            Game.getInstance().changeScene(MapEditorScene.class,null);
        }
    }
 
    private void exitButton_click()
    {
        Game.getInstance().exitGame();
    }
    
    
    //=================
    // Static Methods
    //================
    
    public static void startGame(final PlayerMock playerMock)
    {                 
                        
        //load loading scene
        Game.getInstance().loadScene(new LoadingScene());
        Game.getInstance().changeScene(LoadingScene.class, null);
        
        final Thread gameLaunchThread = new Thread()
        {
            @Override
            public void run()
            {

                // once we are all loaded get save game from player mock
                SaveGame saveGame = playerMock.buildSaveGameData();
       
                
                //load game scene
                Game.getInstance().loadScene(new GameScene(saveGame));                 
                
                //switch to GameClientScene
                Game.getInstance().changeScene(GameScene.class,null); 

                //unload character selection scene
                Game.getInstance().unloadScene(CharacterSelectionScene.class);

                //unload loading scene
                Game.getInstance().unloadScene(LoadingScene.class);
                
                
            }
        };
        
        //check if we are still loading assets
        if((boolean)Game.getInstance().getStateVariable("finishedLoading") != true )
        {
            ((LoadingScene)Game.getInstance().getScene(LoadingScene.class)).setCurrentProgressText("Loading Assets");
            
            //if so start up a thread that spins waiting for it to be done, and calls gameLaunchThread.start() upon completion
            Thread assetLoadThread = new Thread(){
                
                @Override
                public void run()
                {
                    while((boolean)Game.getInstance().getStateVariable("finishedLoading") != true)
                    {
                        try{Thread.sleep(50);}
                        catch(Exception e){}
                    }
                    
                    gameLaunchThread.start();
                }
            };
            assetLoadThread.start();
        }
        else //if not, just launch the game
        {
            gameLaunchThread.start();
        }
                 
    }
    

}
