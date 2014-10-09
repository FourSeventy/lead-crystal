package com.silvergobletgames.leadcrystal.scenes;

import com.silvergobletgames.leadcrystal.core.SaveGame;
import com.silvergobletgames.sylver.graphics.Color;
import com.esotericsoftware.kryonet.Client;
import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.sylver.core.*;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.ImageEffect.ImageEffectType;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.TextBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.media.opengl.GL3bc;
import javax.media.opengl.glu.GLU;
import com.silvergobletgames.leadcrystal.netcode.ConnectionException;
import com.silvergobletgames.leadcrystal.netcode.GobletServer.ServerConfiguration;
import com.silvergobletgames.sylver.audio.AudioRenderer;
import com.silvergobletgames.sylver.audio.Sound;
import javax.media.opengl.GL2;


public class MultiplayerMenuScene extends Scene 
{   
    
    //save game
    private SaveGame saveGame;
    private String actionArg;
   
    
    //==============
    // Constructor
    //==============
    
    public MultiplayerMenuScene()
    {
        //center and right
        final int right = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x;
        final int center = right/2;
        
        //build background image
        Image back = new Image("mainMenuBackground.png");
        back.setPosition(0, 0);
        back.setDimensions(1600, 900);
        this.add(back,Layer.BACKGROUND);
        
        //multiplayer title
        Text title = new Text("Multiplayer",LeadCrystalTextType.MENU60);      
        title.setPosition(center - title.getWidth()/2, 730);       
        this.add(title,Layer.MAIN);
        
        //join
        final Text joinText = new Text("Join",LeadCrystalTextType.MENU46);
        joinText.setPosition(center - joinText.getWidth()/2, 600);
        final Button joinButton = new Button(new Image("blank.png"), center - joinText.getWidth()/2, joinText.getPosition().y, joinText.getWidth(), joinText.getHeight());
        this.add(joinText,Layer.MAIN);
        this.add(joinButton,Layer.MAIN);
        joinButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {

                    //build args to send to the character selection screen
                    ArrayList args = new ArrayList();
                    args.add(saveGame);

                    //load and switch scenes
                    Game.getInstance().loadScene( new ServerSelectScene());
                    Game.getInstance().changeScene(ServerSelectScene.class,args);   
                    
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(joinText.hasTextEffect("small"))
                          joinText.removeTextEffect("small");
                      
                       joinText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, joinText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(joinText.hasTextEffect("big"))
                           joinText.removeTextEffect("big");
                        
                        joinText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, joinText.getScale(), 1));
                }
            }
        });      
        
        //host
        final Text hostText = new Text("Host",LeadCrystalTextType.MENU46);
        hostText.setPosition(center - hostText.getWidth()/2, 525);
        final Button hostButton = new Button(new Image("blank.png"), center - hostText.getWidth()/2, hostText.getPosition().y, hostText.getWidth(), hostText.getHeight());
        this.add(hostText,Layer.MAIN);
        this.add(hostButton,Layer.MAIN);
        hostButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    //do host logic
                    ServerConfiguration config = new ServerConfiguration();
                    config.tcpPort = GameplaySettings.getInstance().tcpPort; 
                    config.udpPort = GameplaySettings.getInstance().udpPort;
                    config.singlePlayer = false;
                    config.privateGame = false;
                    //stop music
                    Sound sound = Sound.newBGM("");
                    add(sound);

                    MainMenuScene.hostMultiPlayerGame(saveGame,config);
                                                     
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(hostText.hasTextEffect("small"))
                          hostText.removeTextEffect("small");
                      
                       hostText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, hostText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(hostText.hasTextEffect("big"))
                           hostText.removeTextEffect("big");
                        
                        hostText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, hostText.getScale(), 1));
                }
            }
        });  
        
        
       
       
        //back
        final Text backText = new Text("Back",LeadCrystalTextType.MENU46);
        backText.setPosition(center - backText.getWidth()/2, 450);
        Button backButton = new Button(new Image("blank.png"), center - backText.getWidth()/2, backText.getPosition().y, backText.getWidth(), backText.getHeight());
        this.add(backText,Layer.MAIN);
        this.add(backButton,Layer.MAIN);
        backButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    //change scene
                    if(actionArg != null)
                    {
                        Game.getInstance().loadScene(new CharacterSelectionScene());
                        ArrayList<String> args = new ArrayList();
                        args.add(actionArg);
                        Game.getInstance().changeScene(CharacterSelectionScene.class,args);  
                    }
                    else
                    {
                        Game.getInstance().loadScene(new MainMenuScene());
                        Game.getInstance().changeScene(MainMenuScene.class,null);
                    }
                    
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(backText.hasTextEffect("small"))
                          backText.removeTextEffect("small");
                      
                       backText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, backText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(backText.hasTextEffect("big"))
                           backText.removeTextEffect("big");
                        
                        backText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, backText.getScale(), 1));
                }
            }
        });      
        
    }
    
    
    //===============
    // Scene Methods
    //===============

 
    public void handleInput() 
    {


    }
    
 
    public void sceneEntered(ArrayList args)
    {
        this.saveGame = (SaveGame)args.get(0);
        if(args.size() > 1)
           this.actionArg = (String)args.get(1);
        
        //set mouse cursor
        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND));
        
        
    }

    public void sceneExited() 
    {
        Game.getInstance().unloadScene(MultiplayerMenuScene.class);
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
    


    
}
