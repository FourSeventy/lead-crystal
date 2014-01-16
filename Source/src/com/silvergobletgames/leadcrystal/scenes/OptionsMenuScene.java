package com.silvergobletgames.leadcrystal.scenes;

import com.jogamp.newt.MonitorDevice;
import com.jogamp.newt.MonitorMode;
import com.jogamp.newt.Screen;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.util.MonitorModeUtil;
import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.media.nativewindow.util.Dimension;
import javax.media.opengl.GL3bc;
import javax.media.opengl.glu.GLU;

public class OptionsMenuScene extends Scene
{
   
    //screen resolution vars
    private ArrayList<MonitorMode> monitorModes;
    private int currentIndex;
    private int newIndex;
    
    private Text vSyncText;
    
    //================
    //Constructors
    //================
    
    public OptionsMenuScene()
    {
        
        //==========================
        // Fills in Resolution List
        //==========================
        
        //get our screen mode
        MonitorDevice mainMonitor = Game.getInstance().getGraphicsWindow().getMainMonitor();
        MonitorMode currentScreenMode = mainMonitor.getCurrentMode();

        //filters screen modes
        monitorModes = new ArrayList(mainMonitor.getSupportedModes());
        if(monitorModes.size()>1) 
        { 
            monitorModes = new ArrayList(MonitorModeUtil.filterByRate(monitorModes, currentScreenMode.getRefreshRate())); 
            monitorModes = new ArrayList(MonitorModeUtil.filterByRotation(monitorModes, 0)); 
            monitorModes = new ArrayList (MonitorModeUtil.getHighestAvailableBpp(monitorModes)); 
        } 
        
        
        //set current index
        Dimension currentResolution = new Dimension(Game.getInstance().getConfiguration().getEngineSettings().screenResolution.getWidth(),Game.getInstance().getConfiguration().getEngineSettings().screenResolution.getHeight()); 
        for(int i = 0; i < monitorModes.size(); i++)
        {
            if(currentResolution.equals(monitorModes.get(i).getSurfaceSize().getResolution()))
            {
                currentIndex = i;
                newIndex = i;
            }
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
        Text title = new Text("Options",CoreTextType.MENU);      
        title.setScale(1.2f);
        title.setPosition(center - title.getWidth()/2, 700);       
        this.add(title,Layer.MAIN);
        
        // fullscreen
        final Text fullScreenText = new Text(Game.getInstance().getConfiguration().getEngineSettings().fullScreen?"Full Screen: ON":"Full Screen: OFF",LeadCrystalTextType.MENUBUTTONS);
        fullScreenText.setScale(1.2f);
        fullScreenText.setPosition(center - fullScreenText.getWidth()/2, 575);
        final Button fullScreenButton = new Button(new Image("blank.png"), center - fullScreenText.getWidth()/2, fullScreenText.getPosition().y, fullScreenText.getWidth(), fullScreenText.getHeight());
        this.add(fullScreenText,Layer.MAIN);
        this.add(fullScreenButton,Layer.MAIN);
        fullScreenButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    Game.getInstance().getConfiguration().getEngineSettings().fullScreen = !Game.getInstance().getConfiguration().getEngineSettings().fullScreen;
                    Game.getInstance().getGraphicsWindow().toggleFullScreen();
                    
                    if(Game.getInstance().getConfiguration().getEngineSettings().fullScreen == true)
                        fullScreenText.setText("Full Screen: On");
                    else
                        fullScreenText.setText("Full Screen: Off");
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(fullScreenText.hasTextEffect("small"))
                          fullScreenText.removeTextEffect("small");
                      
                       fullScreenText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, fullScreenText.getScale(), 1.5));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(fullScreenText.hasTextEffect("big"))
                           fullScreenText.removeTextEffect("big");
                        
                        fullScreenText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, fullScreenText.getScale(), 1.2));
                }
            }
        });
        
        //vsync
        final Text vsyncText = new Text(Game.getInstance().getConfiguration().getEngineSettings().vSync?"VSync: ON":"VSync: OFF",LeadCrystalTextType.MENUBUTTONS);
        vsyncText.setScale(1.2f);
        vsyncText.setPosition(center - vsyncText.getWidth()/2, 525);
        final Button vSyncButton = new Button(new Image("blank.png"), center - vsyncText.getWidth()/2, vsyncText.getPosition().y, vsyncText.getWidth(), vsyncText.getHeight());
        this.add(vsyncText,Layer.MAIN);
        this.add(vSyncButton,Layer.MAIN);
        vSyncButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    
                    if(Game.getInstance().getGraphicsWindow().isFullscreen())
                    {                   
                        if(Game.getInstance().getConfiguration().getEngineSettings().vSync == false)
                        {
                            Game.getInstance().getConfiguration().getEngineSettings().vSync = true;
                            //vsyncText.setText("VSync: On");   
                            Game.getInstance().getGraphicsWindow().setVSync(true);
                        }
                        else
                        {
                            Game.getInstance().getConfiguration().getEngineSettings().vSync = false;
                           // vsyncText.setText("VSync: Off");
                            Game.getInstance().getGraphicsWindow().setVSync(false);
                        }
                    }
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    if(Game.getInstance().getGraphicsWindow().isFullscreen())
                    {  
                      if(vsyncText.hasTextEffect("small"))
                          vsyncText.removeTextEffect("small");
                      
                       vsyncText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, vsyncText.getScale(), 1.5));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    add(sound);
                    }
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                    if(Game.getInstance().getGraphicsWindow().isFullscreen())
                    {  
                        if(vsyncText.hasTextEffect("big"))
                           vsyncText.removeTextEffect("big");
                        
                        vsyncText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, vsyncText.getScale(), 1.2));
                    }
                }
            }
        });    
        
        this.vSyncText = vsyncText;
        
        //bloom
        final Text bloomText = new Text(Game.getInstance().getConfiguration().getEngineSettings().bloom?"Bloom Effects: ON":"Bloom Effects: OFF",LeadCrystalTextType.MENUBUTTONS);
        bloomText.setScale(1.2f);
        bloomText.setPosition(center - bloomText.getWidth()/2, 475);
        final Button bloomButton = new Button(new Image("blank.png"), center - bloomText.getWidth()/2, bloomText.getPosition().y, bloomText.getWidth(), bloomText.getHeight());
        this.add(bloomText,Layer.MAIN);
        this.add(bloomButton,Layer.MAIN);
        bloomButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    if(Game.getInstance().getConfiguration().getEngineSettings().bloom == false)
                    {
                        Game.getInstance().getConfiguration().getEngineSettings().bloom = true;
                        bloomText.setText("Bloom Effects: ON");   
                    }
                    else
                    {
                        Game.getInstance().getConfiguration().getEngineSettings().bloom = false;
                        bloomText.setText("Bloom Effects: Off");
                    }
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(bloomText.hasTextEffect("small"))
                          bloomText.removeTextEffect("small");
                      
                       bloomText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, bloomText.getScale(), 1.5));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(bloomText.hasTextEffect("big"))
                           bloomText.removeTextEffect("big");
                        
                        bloomText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, bloomText.getScale(), 1.2));
                }
            }
        });
        
        //lighting
        final Text lightingText = new Text(Game.getInstance().getConfiguration().getEngineSettings().lighting?"Lighting Effects: ON":"Lighting Effects: OFF",LeadCrystalTextType.MENUBUTTONS);
        lightingText.setScale(1.2f);
        lightingText.setPosition(center - lightingText.getWidth()/2, 425);
        final Button lightingButton = new Button(new Image("blank.png"), center - lightingText.getWidth()/2, lightingText.getPosition().y, lightingText.getWidth(), lightingText.getHeight());
        this.add(lightingText,Layer.MAIN);
        this.add(lightingButton,Layer.MAIN);
        lightingButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    if(Game.getInstance().getConfiguration().getEngineSettings().lighting == false)
                    {
                        Game.getInstance().getConfiguration().getEngineSettings().lighting = true;
                        lightingText.setText("Lighting Effects: ON");   
                    }
                    else
                    {
                        Game.getInstance().getConfiguration().getEngineSettings().lighting = false;
                        lightingText.setText("Lighting Effects: Off");
                    }
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(lightingText.hasTextEffect("small"))
                          lightingText.removeTextEffect("small");
                      
                       lightingText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, lightingText.getScale(), 1.5));
                    
                   //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(lightingText.hasTextEffect("big"))
                           lightingText.removeTextEffect("big");
                        
                        lightingText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, lightingText.getScale(), 1.2));
                }
            }
        });
        
        //blur
        final Text blurText = new Text(Game.getInstance().getConfiguration().getEngineSettings().gaussianBlur?"Blur Effects: ON":"Blur Effects: OFF",LeadCrystalTextType.MENUBUTTONS);
        blurText.setScale(1.2f);
        blurText.setPosition(center - blurText.getWidth()/2, 375);
        final Button blurButton = new Button(new Image("blank.png"), center - blurText.getWidth()/2, blurText.getPosition().y, blurText.getWidth(), blurText.getHeight());
        this.add(blurText,Layer.MAIN);
        this.add(blurButton,Layer.MAIN);
        blurButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    if(Game.getInstance().getConfiguration().getEngineSettings().gaussianBlur == false)
                    {
                        Game.getInstance().getConfiguration().getEngineSettings().gaussianBlur = true;
                        blurText.setText("Blur Effects: ON");   
                    }
                    else
                    {
                        Game.getInstance().getConfiguration().getEngineSettings().gaussianBlur = false;
                        blurText.setText("Blur Effects: Off");
                    }
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(blurText.hasTextEffect("small"))
                          blurText.removeTextEffect("small");
                      
                       blurText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, blurText.getScale(), 1.5));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(blurText.hasTextEffect("big"))
                           blurText.removeTextEffect("big");
                        
                        blurText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, blurText.getScale(), 1.2));
                }
            }
        });
        
        //particle density
        final Text particleText = new Text("Particle Density:" + Game.getInstance().getConfiguration().getEngineSettings().particleDensity.name(),LeadCrystalTextType.MENUBUTTONS);
        particleText.setScale(1.2f);
        particleText.setPosition(center - particleText.getWidth()/2, 325);
        final Button particleDensityButton =  new Button(new Image("blank.png"), center - particleText.getWidth()/2, particleText.getPosition().y, particleText.getWidth(), particleText.getHeight());
        this.add(particleText,Layer.MAIN);
        this.add(particleDensityButton,Layer.MAIN);
        particleDensityButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    switch(Game.getInstance().getConfiguration().getEngineSettings().particleDensity)
                    {
                        case UBER: Game.getInstance().getConfiguration().getEngineSettings().particleDensity = ParticleDensity.OFF; break;
                        case HIGH: Game.getInstance().getConfiguration().getEngineSettings().particleDensity = ParticleDensity.UBER; break;
                        case MEDIUM: Game.getInstance().getConfiguration().getEngineSettings().particleDensity = ParticleDensity.HIGH; break;
                        case LOW: Game.getInstance().getConfiguration().getEngineSettings().particleDensity = ParticleDensity.MEDIUM; break;
                        case OFF: Game.getInstance().getConfiguration().getEngineSettings().particleDensity = ParticleDensity.LOW; break;
                    }
                    
                    
                    particleText.setText("Particle Density:" + Game.getInstance().getConfiguration().getEngineSettings().particleDensity.name());

                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(particleText.hasTextEffect("small"))
                          particleText.removeTextEffect("small");
                      
                       particleText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, particleText.getScale(), 1.5));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(particleText.hasTextEffect("big"))
                           particleText.removeTextEffect("big");
                        
                        particleText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, particleText.getScale(), 1.2));
                }
            }
        });
        
        //resolution
        final Text resolutionText = new Text("Resolution: " + this.monitorModes.get(newIndex).getSurfaceSize().getResolution() +"  (" + this.asFraction(this.monitorModes.get(newIndex).getSurfaceSize().getResolution().getWidth(),this.monitorModes.get(newIndex).getSurfaceSize().getResolution().getHeight()) + ") ",LeadCrystalTextType.MENUBUTTONS);
        resolutionText.setScale(1.2f);
        resolutionText.setPosition(center - resolutionText.getWidth()/2, 275);
        final Button resolutionButton = new Button(new Image("blank.png"), center - resolutionText.getWidth()/2, resolutionText.getPosition().y, resolutionText.getWidth(), resolutionText.getHeight());
        this.add(resolutionText,Layer.MAIN);
        this.add(resolutionButton,Layer.MAIN);
        resolutionButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    newIndex++;
                    if(newIndex >= monitorModes.size())
                        newIndex = 0;
                    
                    resolutionText.setText("Resolution: " + monitorModes.get(newIndex).getSurfaceSize().getResolution()+"  (" + asFraction(monitorModes.get(newIndex).getSurfaceSize().getResolution().getWidth(),monitorModes.get(newIndex).getSurfaceSize().getResolution().getHeight()) + ") ");
                    
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(resolutionText.hasTextEffect("small"))
                          resolutionText.removeTextEffect("small");
                      
                       resolutionText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, resolutionText.getScale(), 1.5));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(resolutionText.hasTextEffect("big"))
                           resolutionText.removeTextEffect("big");
                        
                        resolutionText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, resolutionText.getScale(), 1.2));
                }
            }
        });
        
        //back
        final Text backText = new Text("Back",LeadCrystalTextType.MENUBUTTONS);
        backText.setScale(1.2f);
        backText.setPosition(center - backText.getWidth()/2, 225);
        final Button backButton = new Button(new Image("blank.png"), center - backText.getWidth()/2, backText.getPosition().y, backText.getWidth(), backText.getHeight());
        this.add(backText,Layer.MAIN);
        this.add(backButton,Layer.MAIN);
        backButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    //change screen resolution first
                    if(newIndex != currentIndex)
                    {
                        Game.getInstance().getGraphicsWindow().setDisplayResolution((Dimension)monitorModes.get(newIndex).getSurfaceSize().getResolution());
                        Game.getInstance().getConfiguration().getEngineSettings().screenResolution = (Dimension)monitorModes.get(newIndex).getSurfaceSize().getResolution();
                    }
                    
                    //change scene
                    Game.getInstance().loadScene(new MainMenuScene());
                    Game.getInstance().changeScene(MainMenuScene.class,null); 
                    
                    
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(backText.hasTextEffect("small"))
                          backText.removeTextEffect("small");
                      
                       backText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, backText.getScale(), 1.5));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(backText.hasTextEffect("big"))
                           backText.removeTextEffect("big");
                        
                        backText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, backText.getScale(), 1.2));
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
        
        this.vSyncText.setText(Game.getInstance().getConfiguration().getEngineSettings().vSync?"VSync: ON":"VSync: OFF"); 
        
        if(!Game.getInstance().getGraphicsWindow().isFullscreen())
           this.vSyncText.setColor(new Color(.66f,.66f,.66f,.6f)); 
        else
            this.vSyncText.setColor(new Color(1f,1f,1f,1f)); 
    }

    public void handleInput() 
    {
        //if escape is pressed        
        if(Game.getInstance().getInputHandler().getInputSnapshot().isKeyReleased(KeyEvent.VK_ESCAPE)) 
        {           
            //change screen resolution first
            if(newIndex != currentIndex)
            {
                Game.getInstance().getGraphicsWindow().setDisplayResolution((Dimension)monitorModes.get(newIndex).getSurfaceSize().getResolution());
                Game.getInstance().getConfiguration().getEngineSettings().screenResolution = (Dimension)monitorModes.get(newIndex).getSurfaceSize().getResolution();
            }

            //change scene
            Game.getInstance().loadScene(new MainMenuScene());
            Game.getInstance().changeScene(MainMenuScene.class,null); 
        }

    }
    
    public void sceneEntered(ArrayList args) 
    {
        //set mouse cursor
        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND));
    }
   
    public void sceneExited()
    {
        Game.getInstance().unloadScene(OptionsMenuScene.class);
    }
    
    private static long gcm(long a, long b) {
    return b == 0 ? a : gcm(b, a % b); // Not bad for one line of code :)
}

    private static String asFraction(long a, long b) {
    long gcm = gcm(a, b);
    return (a / gcm) + ":" + (b / gcm);
}
}
