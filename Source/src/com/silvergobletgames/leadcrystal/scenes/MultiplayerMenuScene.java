package com.silvergobletgames.leadcrystal.scenes;

import com.silvergobletgames.leadcrystal.core.SaveGame;
import com.silvergobletgames.sylver.graphics.Color;
import com.esotericsoftware.kryonet.Client;
import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
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


public class MultiplayerMenuScene extends Scene 
{   
    
    //save game
    private SaveGame saveGame;
    private String actionArg;
    
    //connection info
    private final Text portLabel, ipLabel, startText;
    private TextBox portTextBox;
    private TextBox ipTextBox;
    private Button startButton;
    
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
        Text title = new Text("Multiplayer",Text.CoreTextType.MENU);      
        title.setScale(1f);
        title.setPosition(center - title.getWidth()/2, 730);       
        this.add(title,Layer.MAIN);
        
        //join
        final Text joinText = new Text("Join",LeadCrystalTextType.MENUBUTTONS);
        joinText.setScale(1.5f);
        joinText.setPosition(center - joinText.getWidth()/2, 600);
        final Button joinButton = new Button(new Image("blank.png"), center - joinText.getWidth()/2, joinText.getPosition().y, joinText.getWidth(), joinText.getHeight());
        this.add(joinText,Layer.MAIN);
        this.add(joinButton,Layer.MAIN);
        joinButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    //==================
                    // Hide Everything
                    //==================
                    
                    portLabel.setColor(new Color(1,1,1,0));
                    portTextBox.setDisabled(true);
                    portTextBox.setHidden(true);
                    ipLabel.setColor(new Color(1,1,1,0));
                    ipTextBox.setDisabled(true);
                    ipTextBox.setHidden(true);
                    startText.setColor(new Color(1,1,1,0));
                    startButton.setDisabled(true);
                    startButton.setHidden(true);
                    
                    //======================
                    // Unhide proper things
                    //======================
                    portLabel.setColor(new Color(1,1,1,1));
                    portTextBox.setDisabled(false);
                    portTextBox.setHidden(false);
                    ipLabel.setColor(new Color(1,1,1,1));
                    ipTextBox.setDisabled(false);
                    ipTextBox.setHidden(false);
                    startText.setColor(new Color(1,1,1,1));
                    startButton.setDisabled(false);
                    startButton.setHidden(false);
                             
                    
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(joinText.hasTextEffect("small"))
                          joinText.removeTextEffect("small");
                      
                       joinText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, joinText.getScale(), 1.8));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(joinText.hasTextEffect("big"))
                           joinText.removeTextEffect("big");
                        
                        joinText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, joinText.getScale(), 1.5));
                }
            }
        });      
        
        //host
        final Text hostText = new Text("Host",LeadCrystalTextType.MENUBUTTONS);
        hostText.setScale(1.5f);
        hostText.setPosition(center - hostText.getWidth()/2, 525);
        final Button hostButton = new Button(new Image("blank.png"), center - hostText.getWidth()/2, hostText.getPosition().y, hostText.getWidth(), hostText.getHeight());
        this.add(hostText,Layer.MAIN);
        this.add(hostButton,Layer.MAIN);
        hostButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    //==================
                    // Hide Everything
                    //==================
                    
                    portLabel.setColor(new Color(1,1,1,0));
                    portTextBox.setDisabled(true);
                    portTextBox.setHidden(true);
                    ipLabel.setColor(new Color(1,1,1,0));
                    ipTextBox.setDisabled(true);
                    ipTextBox.setHidden(true);
                    startText.setColor(new Color(1,1,1,0));
                    startButton.setDisabled(true);
                    startButton.setHidden(true);
                    
                    //======================
                    // Unhide proper things
                    //======================
                    portLabel.setColor(new Color(1,1,1,1));
                    portTextBox.setDisabled(false);
                    portTextBox.setHidden(false);
                    startText.setColor(new Color(1,1,1,1));
                    startButton.setDisabled(false);
                    startButton.setHidden(false);
                                    
                    
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(hostText.hasTextEffect("small"))
                          hostText.removeTextEffect("small");
                      
                       hostText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, hostText.getScale(), 1.8));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(hostText.hasTextEffect("big"))
                           hostText.removeTextEffect("big");
                        
                        hostText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, hostText.getScale(), 1.5));
                }
            }
        });  
        
        //port
        portLabel = new Text("Port:",LeadCrystalTextType.MENUBUTTONS);
        portLabel.setScale(1.2f);
        portLabel.setPosition(center + 200, 550);
        this.add(portLabel, Layer.MAIN);
        portTextBox = new TextBox("50501", center + 320, 550);        
        this.add(portTextBox,Layer.MAIN);
        portLabel.setColor(new Color(1,1,1,0));
        portTextBox.setDisabled(true);
        portTextBox.setHidden(true);
        
        //IP
        ipLabel = new Text("IP:",LeadCrystalTextType.MENUBUTTONS);
        ipLabel.setScale(1.2f);
        ipLabel.setPosition(center + 200, 500);
        this.add(ipLabel, Layer.MAIN);
        ipTextBox = new TextBox("127.0.0.1", center + 275, 500);        
        this.add(ipTextBox,Layer.MAIN);
        ipLabel.setColor(new Color(1,1,1,0));
        ipTextBox.setDisabled(true);
        ipTextBox.setHidden(true);
        
        //start
        startText = new Text("Start",LeadCrystalTextType.MENUBUTTONS);
        startText.setScale(1.5f);
        startText.setPosition(center +200 , 450);
        startButton = new Button(new Image("blank.png"), startText.getPosition().x, startText.getPosition().y, startText.getWidth(), startText.getHeight());
        this.add(startText,Layer.MAIN);
        this.add(startButton,Layer.MAIN);
        startButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {                                    
                    
                    //if we are doing a host
                    if(ipTextBox.isDisabled())
                    {
                        ServerConfiguration config = new ServerConfiguration();
                        config.port = Integer.parseInt(portTextBox.getText());
                        config.singlePlayer = false;
                        config.privateGame = false;
                        //stop music
                        Sound sound = Sound.newBGM("");
                        add(sound);
         
                        MainMenuScene.hostMultiPlayerGame(saveGame,config);
                    }
                    else //we are doing a join
                    {
                        String ip = ipTextBox.getText();
                        int port = Integer.parseInt(portTextBox.getText());
                        //stop music
                        Sound sound = Sound.newBGM("");
                        add(sound);
         
                        MainMenuScene.joinMultiPlayerGame(saveGame, ip, port);
                    }
                    
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(startText.hasTextEffect("small"))
                          startText.removeTextEffect("small");
                      
                       startText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, startText.getScale(), 1.8));
                    
                   //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(startText.hasTextEffect("big"))
                           startText.removeTextEffect("big");
                        
                        startText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, startText.getScale(), 1.5));
                }
            }
        });  
        startText.setColor(new Color(1,1,1,0));
        startButton.setDisabled(true);
        startButton.setHidden(true);

        //back
        final Text backText = new Text("Back",LeadCrystalTextType.MENUBUTTONS);
        backText.setScale(1.5f);
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
                      
                       backText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, backText.getScale(), 1.8));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(backText.hasTextEffect("big"))
                           backText.removeTextEffect("big");
                        
                        backText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, backText.getScale(), 1.5));
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
    


    
}
