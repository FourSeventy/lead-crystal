package com.silvergobletgames.leadcrystal.scenes;

import com.jogamp.newt.MonitorDevice;
import com.jogamp.newt.MonitorMode;
import com.jogamp.newt.Screen;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.util.MonitorModeUtil;
import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.core.SaveGame;
import com.silvergobletgames.sylver.audio.AudioRenderer;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.*;
import com.silvergobletgames.sylver.core.EngineSettings.ParticleDensity;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.OpenGLGameWindow;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import com.silvergobletgames.sylver.graphics.TextEffect;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.TextBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.nativewindow.util.Dimension;
import javax.media.opengl.GL3bc;
import javax.media.opengl.glu.GLU;

public class ServerSelectScene extends Scene
{
    private RecentServerList recentServerList;
    private SaveGame saveGame;
    private TextBox ipTextBox;
    
    //================
    //Constructors
    //================
    
    public ServerSelectScene()
    {
        
        //load server list
        File file = new File("");
        String path =file.getAbsolutePath();
        try(FileInputStream f = new FileInputStream(path +"\\System\\recentServerCache");
            ObjectInputStream ois = new ObjectInputStream(f);)
        {
            
            this.recentServerList = (RecentServerList)ois.readObject();
            //close file
            ois.close();
            f.close();
        }
        catch(Exception e)
        {
            //log error to console
           Logger logger =Logger.getLogger(ServerSelectScene.class.getName());
           logger.log(Level.SEVERE, "RecentServerCache load failed ", e); 
           
           this.recentServerList = new RecentServerList();
        }
             
        //================
        // Builds Buttons
        //================
        //center and right
        final int right = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x;
        final int center = right/2;
        
        //build background image
        Image back = new Image("mainMenuBackground.png");
        back.setPosition(0, 0);
        back.setDimensions(1600, 900);
        this.add(back,Layer.BACKGROUND);
        
        //options title
        Text title = new Text("Pick A Server",LeadCrystalTextType.MENU60);      
        title.setPosition(center - title.getWidth()/2, 740);       
        this.add(title,Layer.MAIN);
        
        //enter ip
        final Text enterIp = new Text("Enter IP:",LeadCrystalTextType.MENU40);
        enterIp.setPosition(center-300, 604);
        this.add(enterIp,Layer.MAIN);
        
        //ip holder graphic
//        Image textBoxHolder = new Image("tutorial_tooltip.png");
//        textBoxHolder.setDimensions(290, 65);
//        textBoxHolder.setPosition(center - 130, 585);
//        this.add(textBoxHolder,Layer.MAIN);
        
        //ip text box
        ipTextBox = new TextBox(new Text("127.0.0.1",LeadCrystalTextType.MENU40), center -143 , 595);  
        ipTextBox.setHideBackground(true);
        ipTextBox.setCursorScale(1.85f);
        ipTextBox.setMaxCharacters(15);
        ipTextBox.setDimensions(250, 50);
        this.add(ipTextBox,Layer.MAIN);
        
       //join
        final Text joinText = new Text("Join!",LeadCrystalTextType.MENU54);
        joinText.setPosition(center +200, 600);
        final Button joinButton = new Button(new Image("blank.png"), joinText.getPosition().x, joinText.getPosition().y, joinText.getWidth(), joinText.getHeight());
        this.add(joinText,Layer.MAIN);
        this.add(joinButton,Layer.MAIN);
        joinButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    
                    String ip = ipTextBox.getText();

                    //stop music
                    Sound sound = Sound.newBGM("");
                    add(sound);

                    int tcpPort = GameplaySettings.getInstance().tcpPort; 
                    int udpPort = GameplaySettings.getInstance().udpPort;
                    recentServerList.recentServers.add(ip);
                    MainMenuScene.joinMultiPlayerGame(saveGame, ip, tcpPort,udpPort);
                    
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
        
        //recent servers
        final Text recentServers = new Text("Recent Servers:",LeadCrystalTextType.MENU40);
        recentServers.setPosition(center-326, 460);
        this.add(recentServers,Layer.MAIN);
        
        //recent server holder graphic
        Image recentServerHolder = new Image("tallFrameMenu.png");
        recentServerHolder.setDimensions(650, 300);
        recentServerHolder.setPosition(center - recentServerHolder.getWidth()/2, 150);
        this.add(recentServerHolder,Layer.MAIN);
        
        this.populateSaveList();
        
        //back
        final Text backText = new Text("Back",LeadCrystalTextType.MENU46);
        backText.setPosition(center - backText.getWidth()/2, 100);
        final Button backButton = new Button(new Image("blank.png"), center - backText.getWidth()/2, backText.getPosition().y, backText.getWidth(), backText.getHeight());
        this.add(backText,Layer.MAIN);
        this.add(backButton,Layer.MAIN);
        backButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    ArrayList args = new ArrayList();
                    args.add(saveGame);
                   
                    //change scene
                    Game.getInstance().loadScene(new MultiplayerMenuScene());
                    Game.getInstance().changeScene(MultiplayerMenuScene.class,args); 
                    
                    
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
    
    
    //=======================
    //Scene Interface Methods
    //=======================
    
    public void update()
    {
        super.update();
       
    }

    public void handleInput() 
    {
      

    }
    
    public void sceneEntered(ArrayList args) 
    {
        this.saveGame = (SaveGame)args.get(0);
        
        //set mouse cursor
        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND));
    }
   
    public void sceneExited()
    {     
         //save recent server list
         //get path of the LeadCrystal install folder
        File file = new File("");
        String path =file.getAbsolutePath();
       
        //get the file input stream of our save file
        try( FileOutputStream f = new FileOutputStream(path +"\\System\\recentServerCache");
             ObjectOutputStream oos = new ObjectOutputStream(f);)
        {   
           oos.writeObject(this.recentServerList);
           oos.close();
           f.close();
        }
        catch (IOException e) 
        {
           //log error to console
           Logger logger =Logger.getLogger(ServerSelectScene.class.getName());
           logger.log(Level.SEVERE, "RecentServerCache save failed ", e); 
        }
        Game.getInstance().unloadScene(ServerSelectScene.class);
    }
    
    
    
    //=========================
    // Recent Server List
    //=========================
    
    public static class RecentServerList implements Serializable
    {
        public LinkedHashSet<String> recentServers = new LinkedHashSet<>();
    }
    
    private void populateSaveList()
    {
        //center and right
        final int right = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x;
        final int center = right/2;
        
        //=========================
        // Bulid the Buttons
        //=========================
        
       //gross reverse order
        ArrayList<String> reversedServerList = new ArrayList<>();
        for(String server: this.recentServerList.recentServers)
        {
            reversedServerList.add(server);
        }
        Collections.reverse(reversedServerList);
        
        int number = 0;
        for(String recentServer: reversedServerList)
        {
            //if they have more than 10 characters they wont see them all
            if(number>5)
            {
                break;
            }
            
            final Text serverText = new Text(recentServer,LeadCrystalTextType.MENU30);
            serverText.setPosition(center - 225 - 70, 400 - 55 * number);
            final Button serverButton = new Button(new Image("blank.png"), center - 225 - 70, 400 - 55 * number, serverText.getWidth(), serverText.getHeight());
            this.add(serverText,Layer.MAIN);
            this.add(serverButton,Layer.MAIN);
            serverButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
     
                    ipTextBox.getTextObj().setText(serverText.toString()); 
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(serverText.hasTextEffect("small"))
                          serverText.removeTextEffect("small");
                      
                       serverText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, serverText.getScale(), 1.1));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(serverText.hasTextEffect("big"))
                           serverText.removeTextEffect("big");
                        
                        serverText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, serverText.getScale(), 1));
                }
            }
        }); 

            number++;
        }

    }
}
