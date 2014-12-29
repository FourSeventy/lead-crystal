package com.silvergobletgames.leadcrystal.menus;

import com.jogamp.newt.MonitorDevice;
import com.jogamp.newt.MonitorMode;
import com.jogamp.newt.util.MonitorModeUtil;
import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.Potion;
import com.silvergobletgames.leadcrystal.items.PotionManager;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.TextEffect;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.media.nativewindow.util.Dimension;

/**
 *
 * @author Mike
 */
public class OptionsMenu extends Window{
    
    //screen resolution vars
    private ArrayList<MonitorMode> monitorModes;
    private int currentIndex;
    private int newIndex;
    
    private Text vSyncText;
    
    public OptionsMenu(float x, float y)
    {
        super(new Image("tallFrame.png"),x,y,550,600);
        
      
        //text
        Text menuText = new Text("Options",LeadCrystalTextType.HUD30);
        Label menuTextLabel = new Label(menuText,275 - menuText.getWidth()/2,this.getHeight() -60);
        this.addComponent(menuTextLabel);
        
        //close
        final Image closeImage = new Image("closeButton.png");
        Button closeButton = new Button(closeImage,504,this.getHeight() -32,closeImage.getWidth()+1,closeImage.getHeight());
        closeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {

                  closeImage.setBrightness(1.5f);
                  Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.HAND)); 
                }
                if (e.getActionCommand().equals("mouseExited")) {

                    closeImage.setBrightness(1f);
                }
                if(e.getActionCommand().equals("clicked"))
                {
                    close();
                }
            }
       });
        this.addComponent(closeButton);
        
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
        
        //fullscreen
        final Text fullScreenText = new Text(Game.getInstance().getConfiguration().getEngineSettings().fullScreen?"Full Screen: ON":"Full Screen: OFF",LeadCrystalTextType.HUD34);
        fullScreenText.setPosition(x +275 - fullScreenText.getWidth()/2, y + 400 );
        final Label fullScreenlabel = new Label(fullScreenText, 275 -fullScreenText.getWidth()/2, 400,true);
        this.addComponent(fullScreenlabel);
        final Button fullScreenButton = new Button(new Image("blank.png"), 275 - fullScreenText.getWidth()/2, 400, fullScreenText.getWidth(), fullScreenText.getHeight());    
        this.addComponent(fullScreenButton);
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
                      
                       fullScreenText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, fullScreenText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    getOwningScene().add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(fullScreenText.hasTextEffect("big"))
                           fullScreenText.removeTextEffect("big");
                        
                        fullScreenText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, fullScreenText.getScale(), 1));
                }
            }
        });
        
        //vsync
        final Text vsyncText = new Text(Game.getInstance().getConfiguration().getEngineSettings().vSync?"VSync: ON":"VSync: OFF",LeadCrystalTextType.HUD34);       
        vsyncText.setPosition(x +275 - vsyncText.getWidth()/2, y + 320 );
        final Label vsyncLabel = new Label(vsyncText, 275 -vsyncText.getWidth()/2, 320,true);
        this.addComponent(vsyncLabel);
        final Button vsyncButton = new Button(new Image("blank.png"), 275 - vsyncText.getWidth()/2, 320, vsyncText.getWidth(), vsyncText.getHeight());    
        this.addComponent(vsyncButton);      
        vsyncButton.addActionListener(new ActionListener() {

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
                      
                       vsyncText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, vsyncText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    getOwningScene().add(sound);
                    }
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                    if(Game.getInstance().getGraphicsWindow().isFullscreen())
                    {  
                        if(vsyncText.hasTextEffect("big"))
                           vsyncText.removeTextEffect("big");
                        
                        vsyncText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, vsyncText.getScale(), 1));
                    }
                }
            }
        });         
        this.vSyncText = vsyncText;
        
        //bloom
        final Text bloomText = new Text(Game.getInstance().getConfiguration().getEngineSettings().bloom?"Bloom Effects: ON":"Bloom Effects: OFF",LeadCrystalTextType.HUD34);
        bloomText.setPosition(x +275 - bloomText.getWidth()/2, y + 240 );
        final Label bloomLabel = new Label(bloomText, 275 -bloomText.getWidth()/2, 240,true);
        this.addComponent(bloomLabel);
        final Button bloomButton = new Button(new Image("blank.png"), 275 - bloomText.getWidth()/2, 240, bloomText.getWidth(), bloomText.getHeight());    
        this.addComponent(bloomButton);
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
                      
                       bloomText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, bloomText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    getOwningScene().add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(bloomText.hasTextEffect("big"))
                           bloomText.removeTextEffect("big");
                        
                        bloomText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, bloomText.getScale(), 1));
                }
            }
        });
        
        //lighting
        final Text lightingText = new Text(Game.getInstance().getConfiguration().getEngineSettings().lighting?"Lighting Effects: ON":"Lighting Effects: OFF",LeadCrystalTextType.HUD34);
        lightingText.setPosition(x +275 - lightingText.getWidth()/2, y + 160 );
        final Label lightingLabel = new Label(lightingText, 275 -lightingText.getWidth()/2, 160,true);
        this.addComponent(lightingLabel);
        final Button lightingButton = new Button(new Image("blank.png"), 275 - lightingText.getWidth()/2, 160, lightingText.getWidth(), lightingText.getHeight());    
        this.addComponent(lightingButton);    
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
                      
                       lightingText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, lightingText.getScale(), 1.2));
                    
                   //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    getOwningScene().add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(lightingText.hasTextEffect("big"))
                           lightingText.removeTextEffect("big");
                        
                        lightingText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, lightingText.getScale(), 1));
                }
            }
        });
        
        
    }
    
    @Override
    public void update()
    {
        super.update();
        
                this.vSyncText.setText(Game.getInstance().getConfiguration().getEngineSettings().vSync?"VSync: ON":"VSync: OFF"); 
        
        if(!Game.getInstance().getGraphicsWindow().isFullscreen())
           this.vSyncText.setColor(new Color(.66f,.66f,.66f,.6f)); 
        else
            this.vSyncText.setColor(new Color(1f,1f,1f,1f)); 
        
    }
    
     @Override
    public void close()
    {
        if(this.owningScene != null && this.isOpen)
        {
            Sound closeSound = Sound.ambientSound("buffered/menuClose.ogg", false);
            this.owningScene.add(closeSound);
        }
        
        super.close();
              
    }
    
    @Override
    public void open()
    {
        if(this.owningScene != null && !this.isOpen)
        {
            Sound openSound = Sound.ambientSound("buffered/menuOpen.ogg", false);
            this.owningScene.add(openSound);
        }
        
        super.open();      
        
    }
}
