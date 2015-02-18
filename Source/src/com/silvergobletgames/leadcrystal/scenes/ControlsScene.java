package com.silvergobletgames.leadcrystal.scenes;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.MonitorDevice;
import com.jogamp.newt.MonitorMode;
import com.jogamp.newt.Screen;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.util.MonitorModeUtil;
import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.sylver.audio.AudioRenderer;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.*;
import com.silvergobletgames.sylver.core.EngineSettings.ParticleDensity;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.OpenGLGameWindow;
import com.silvergobletgames.sylver.graphics.RenderingPipelineGL2;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import com.silvergobletgames.sylver.graphics.TextEffect;
import com.silvergobletgames.sylver.windowsystem.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.media.nativewindow.util.Dimension;
import javax.media.opengl.GL2;
import javax.media.opengl.GL3bc;
import javax.media.opengl.glu.GLU;

public class ControlsScene extends Scene
{
   
    
    private GameplaySettings settings = null;
    
    private String currentlyUpdating = null;
    
    private Text upText, downText, leftText, rightText, jumpText, sprintText, flashlightText, potionText, skill3Text, skill4Text;
    
    
    //================
    //Constructors
    //================
    
    public ControlsScene()
    {
        
        this.settings = GameplaySettings.getInstance();
                 
        
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
        Text title = new Text("Controls",LeadCrystalTextType.MENU60);      
        title.setPosition(center - title.getWidth()/2, 740);       
        this.add(title,Layer.MAIN);
        
         
        
        // up
        this.upText = new Text("Up             " +  this.getKeyText(settings.up)  ,LeadCrystalTextType.MENU36);
        upText.setPosition(center - 100, 630);
        final Button upButton = new Button(new Image("blank.png"), center - upText.getWidth()/2, upText.getPosition().y, upText.getWidth(), upText.getHeight());
        this.add(upText,Layer.MAIN);
        this.add(upButton,Layer.MAIN);
        upButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                   ControlsScene.this.refreshButtonText();
                   upText.setText("Up             _");
                   ControlsScene.this.currentlyUpdating ="up";
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(upText.hasTextEffect("small"))
                          upText.removeTextEffect("small");
                      
                       upText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, upText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(upText.hasTextEffect("big"))
                           upText.removeTextEffect("big");
                        
                        upText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, upText.getScale(), 1));
                }
            }
        });
        
        // down
        this.downText = new Text("Down             " +   this.getKeyText(settings.down),LeadCrystalTextType.MENU36);
        downText.setPosition(center - 100, 580);
        final Button downButton = new Button(new Image("blank.png"), center - downText.getWidth()/2, downText.getPosition().y, downText.getWidth(), downText.getHeight());
        this.add(downText,Layer.MAIN);
        this.add(downButton,Layer.MAIN);
        downButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                   ControlsScene.this.refreshButtonText();
                   downText.setText("Down             _");
                   ControlsScene.this.currentlyUpdating ="down";
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(downText.hasTextEffect("small"))
                          downText.removeTextEffect("small");
                      
                       downText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, downText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(downText.hasTextEffect("big"))
                           downText.removeTextEffect("big");
                        
                        downText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, downText.getScale(), 1));
                }
            }
        });
        
        // Left
        this.leftText = new Text("Left             " +   this.getKeyText(settings.left),LeadCrystalTextType.MENU36);
        leftText.setPosition(center - 100, 530);
        final Button leftButton = new Button(new Image("blank.png"), center - leftText.getWidth()/2, leftText.getPosition().y, leftText.getWidth(), leftText.getHeight());
        this.add(leftText,Layer.MAIN);
        this.add(leftButton,Layer.MAIN);
        leftButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                   ControlsScene.this.refreshButtonText();
                   leftText.setText("Left             _");
                   ControlsScene.this.currentlyUpdating ="left";
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(leftText.hasTextEffect("small"))
                          leftText.removeTextEffect("small");
                      
                       leftText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, leftText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(leftText.hasTextEffect("big"))
                           leftText.removeTextEffect("big");
                        
                        leftText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, leftText.getScale(), 1));
                }
            }
        });
        
        // Right
        this.rightText = new Text("Right             " +   this.getKeyText(settings.right),LeadCrystalTextType.MENU36);
        rightText.setPosition(center - 100, 480);
        final Button rightButton = new Button(new Image("blank.png"), center - rightText.getWidth()/2, rightText.getPosition().y, rightText.getWidth(), rightText.getHeight());
        this.add(rightText,Layer.MAIN);
        this.add(rightButton,Layer.MAIN);
        rightButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                   ControlsScene.this.refreshButtonText();
                   rightText.setText("Right             _");
                   ControlsScene.this.currentlyUpdating ="right";
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(rightText.hasTextEffect("small"))
                          rightText.removeTextEffect("small");
                      
                       rightText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, rightText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(rightText.hasTextEffect("big"))
                           rightText.removeTextEffect("big");
                        
                        rightText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, rightText.getScale(), 1));
                }
            }
        });
        
        // Jump
        this.jumpText = new Text("Jump             " +   this.getKeyText(settings.jump),LeadCrystalTextType.MENU36);
        jumpText.setPosition(center - 100, 430);
        final Button jumpButton = new Button(new Image("blank.png"), center - jumpText.getWidth()/2, jumpText.getPosition().y, jumpText.getWidth(), jumpText.getHeight());
        this.add(jumpText,Layer.MAIN);
        this.add(jumpButton,Layer.MAIN);
        jumpButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                   ControlsScene.this.refreshButtonText();
                   jumpText.setText("Jump             _");
                   ControlsScene.this.currentlyUpdating ="jump";
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(jumpText.hasTextEffect("small"))
                          jumpText.removeTextEffect("small");
                      
                       jumpText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, jumpText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(jumpText.hasTextEffect("big"))
                           jumpText.removeTextEffect("big");
                        
                        jumpText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, jumpText.getScale(), 1));
                }
            }
        });
        
        // Sprint
        this.sprintText = new Text("Sprint             "+   this.getKeyText(settings.sprint),LeadCrystalTextType.MENU36);
        sprintText.setPosition(center - 100, 380);
        final Button sprintButton = new Button(new Image("blank.png"), center - sprintText.getWidth()/2, sprintText.getPosition().y, sprintText.getWidth(), sprintText.getHeight());
        this.add(sprintText,Layer.MAIN);
        this.add(sprintButton,Layer.MAIN);
        sprintButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                   ControlsScene.this.refreshButtonText();
                   sprintText.setText("Sprint             _");
                   ControlsScene.this.currentlyUpdating ="sprint";
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(sprintText.hasTextEffect("small"))
                          sprintText.removeTextEffect("small");
                      
                       sprintText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, sprintText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(sprintText.hasTextEffect("big"))
                           sprintText.removeTextEffect("big");
                        
                        sprintText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, sprintText.getScale(), 1));
                }
            }
        });
        
        // Flashlight
        this.flashlightText = new Text("Flashlight             " +   this.getKeyText(settings.flashlight),LeadCrystalTextType.MENU36);
        flashlightText.setPosition(center - 100, 330);
        final Button flashlightButton = new Button(new Image("blank.png"), center - flashlightText.getWidth()/2, flashlightText.getPosition().y, flashlightText.getWidth(), flashlightText.getHeight());
        this.add(flashlightText,Layer.MAIN);
        this.add(flashlightButton,Layer.MAIN);
        flashlightButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                   ControlsScene.this.refreshButtonText();
                   flashlightText.setText("Flashlight             _");
                   ControlsScene.this.currentlyUpdating ="flashlight";
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(flashlightText.hasTextEffect("small"))
                          flashlightText.removeTextEffect("small");
                      
                       flashlightText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, flashlightText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(flashlightText.hasTextEffect("big"))
                           flashlightText.removeTextEffect("big");
                        
                        flashlightText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, flashlightText.getScale(), 1));
                }
            }
        });
        
        // Potion
        this.potionText = new Text("Potion             " +   this.getKeyText(settings.potion),LeadCrystalTextType.MENU36);
        potionText.setPosition(center - 100, 280);
        final Button potionButton = new Button(new Image("blank.png"), center - potionText.getWidth()/2, potionText.getPosition().y, potionText.getWidth(), potionText.getHeight());
        this.add(potionText,Layer.MAIN);
        this.add(potionButton,Layer.MAIN);
        potionButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                   ControlsScene.this.refreshButtonText();
                   potionText.setText("Potion             _");
                   ControlsScene.this.currentlyUpdating ="potion";
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(potionText.hasTextEffect("small"))
                          potionText.removeTextEffect("small");
                      
                       potionText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, potionText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(potionText.hasTextEffect("big"))
                           potionText.removeTextEffect("big");
                        
                        potionText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, potionText.getScale(), 1));
                }
            }
        });
        
        // Skill3
        this.skill3Text = new Text("Skill3             " +   this.getKeyText(settings.skill3),LeadCrystalTextType.MENU36);
        skill3Text.setPosition(center - 100, 230);
        final Button skill3Button = new Button(new Image("blank.png"), center - skill3Text.getWidth()/2, skill3Text.getPosition().y, skill3Text.getWidth(), skill3Text.getHeight());
        this.add(skill3Text,Layer.MAIN);
        this.add(skill3Button,Layer.MAIN);
        skill3Button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                  ControlsScene.this.refreshButtonText();
                   skill3Text.setText("Skill3             _");
                   ControlsScene.this.currentlyUpdating ="skill3"; 
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(skill3Text.hasTextEffect("small"))
                          skill3Text.removeTextEffect("small");
                      
                       skill3Text.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, skill3Text.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(skill3Text.hasTextEffect("big"))
                           skill3Text.removeTextEffect("big");
                        
                        skill3Text.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, skill3Text.getScale(), 1));
                }
            }
        });
        
        // skill4
        this.skill4Text = new Text("Skill4             " +   this.getKeyText(settings.skill4),LeadCrystalTextType.MENU36);
        skill4Text.setPosition(center - 100, 180);
        final Button skill4Button = new Button(new Image("blank.png"), center - skill4Text.getWidth()/2, skill4Text.getPosition().y, skill4Text.getWidth(), skill4Text.getHeight());
        this.add(skill4Text,Layer.MAIN);
        this.add(skill4Button,Layer.MAIN);
        skill4Button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                   ControlsScene.this.refreshButtonText();
                   skill4Text.setText("Skill4             _");
                   ControlsScene.this.currentlyUpdating ="skill4"; 
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(skill4Text.hasTextEffect("small"))
                          skill4Text.removeTextEffect("small");
                      
                       skill4Text.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, skill4Text.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(skill4Text.hasTextEffect("big"))
                           skill4Text.removeTextEffect("big");
                        
                        skill4Text.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, skill4Text.getScale(), 1));
                }
            }
        });
        
        
        
       //reset
        final Text resetDefaults = new Text("Reset Defaults",LeadCrystalTextType.MENU46);
        resetDefaults.setPosition(center - resetDefaults.getWidth()/2, 100);
        final Button resetDefaultsButton = new Button(new Image("blank.png"), center - resetDefaults.getWidth()/2, resetDefaults.getPosition().y, resetDefaults.getWidth(), resetDefaults.getHeight());
        this.add(resetDefaults,Layer.MAIN);
        this.add(resetDefaultsButton,Layer.MAIN);
        resetDefaultsButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                   
                    GameplaySettings.getInstance().up = 87;
                    GameplaySettings.getInstance().down = 83;
                    GameplaySettings.getInstance().left = 65;
                    GameplaySettings.getInstance().right = 68;
                    GameplaySettings.getInstance().jump = 32;
                    GameplaySettings.getInstance().sprint = 15;
                    GameplaySettings.getInstance().flashlight = 84;
                    GameplaySettings.getInstance().potion = 70;
                    GameplaySettings.getInstance().skill3 = 81;
                    GameplaySettings.getInstance().skill4 = 69;
                    ControlsScene.this.refreshButtonText();
                    
                    
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(resetDefaults.hasTextEffect("small"))
                          resetDefaults.removeTextEffect("small");
                      
                       resetDefaults.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, resetDefaults.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(resetDefaults.hasTextEffect("big"))
                           resetDefaults.removeTextEffect("big");
                        
                        resetDefaults.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, resetDefaults.getScale(), 1));
                }
            }
        });     
        
        //back
        final Text backText = new Text("Back",LeadCrystalTextType.MENU46);
        backText.setPosition(center - backText.getWidth()/2, 30);
        final Button backButton = new Button(new Image("blank.png"), center - backText.getWidth()/2, backText.getPosition().y, backText.getWidth(), backText.getHeight());
        this.add(backText,Layer.MAIN);
        this.add(backButton,Layer.MAIN);
        backButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                   
                    //change scene
                    Game.getInstance().loadScene(new OptionsMenuScene());
                    Game.getInstance().changeScene(OptionsMenuScene.class,null); 
                    
                    
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
        InputSnapshot inputSnapshot = Game.getInstance().getInputHandler().getInputSnapshot();
        
        //if escape is pressed        
        if(inputSnapshot.isKeyReleased(KeyEvent.VK_ESCAPE)) 
        {           
            //change scene
            Game.getInstance().loadScene(new OptionsMenuScene());
            Game.getInstance().changeScene(OptionsMenuScene.class,null); 
        }
        
        
        if(this.currentlyUpdating != null)
        {
            switch(this.currentlyUpdating)
            {
                case "up": 
                    if( inputSnapshot.getReleasedMap().keySet().iterator().hasNext())
                    {
                        this.settings.up = inputSnapshot.getReleasedMap().keySet().iterator().next(); 
                        this.refreshButtonText();  
                        this.currentlyUpdating = null;
                    }
                    break;
                case "down": 
                    if( inputSnapshot.getReleasedMap().keySet().iterator().hasNext())
                    {
                        this.settings.down = inputSnapshot.getReleasedMap().keySet().iterator().next(); 
                        this.refreshButtonText();   
                        this.currentlyUpdating = null;
                    }
                    break;
                case "left": 
                    if( inputSnapshot.getReleasedMap().keySet().iterator().hasNext())
                    {
                        this.settings.left = inputSnapshot.getReleasedMap().keySet().iterator().next(); 
                        this.refreshButtonText();
                        this.currentlyUpdating = null;
                    }
                    break;
                case "right": 
                    if( inputSnapshot.getReleasedMap().keySet().iterator().hasNext())
                    {
                        this.settings.right = inputSnapshot.getReleasedMap().keySet().iterator().next(); 
                        this.refreshButtonText();  
                        this.currentlyUpdating = null;
                    }
                    break;
                case "jump":  
                    if( inputSnapshot.getReleasedMap().keySet().iterator().hasNext())
                    {
                        this.settings.jump = inputSnapshot.getReleasedMap().keySet().iterator().next(); 
                        this.refreshButtonText(); 
                        this.currentlyUpdating = null;
                    }
                    break;
                case "sprint":  
                    if( inputSnapshot.getReleasedMap().keySet().iterator().hasNext())
                    {
                        this.settings.sprint = inputSnapshot.getReleasedMap().keySet().iterator().next(); 
                        this.refreshButtonText(); 
                        this.currentlyUpdating = null;
                    }
                    break;
                case "flashlight":  
                    if( inputSnapshot.getReleasedMap().keySet().iterator().hasNext())
                    {
                        this.settings.flashlight = inputSnapshot.getReleasedMap().keySet().iterator().next(); 
                        this.refreshButtonText();   
                        this.currentlyUpdating = null;
                    }
                    break;
                case "potion":  
                    if( inputSnapshot.getReleasedMap().keySet().iterator().hasNext())
                    {
                        this.settings.potion = inputSnapshot.getReleasedMap().keySet().iterator().next(); 
                        this.refreshButtonText();  
                        this.currentlyUpdating = null;
                    }
                    break;
                case "skill3":  
                    if( inputSnapshot.getReleasedMap().keySet().iterator().hasNext())
                    {
                        this.settings.skill3 = inputSnapshot.getReleasedMap().keySet().iterator().next(); 
                        this.refreshButtonText(); 
                        this.currentlyUpdating = null;
                    }
                    break;
                case "skill4":  
                    if( inputSnapshot.getReleasedMap().keySet().iterator().hasNext())
                    {
                        this.settings.skill4 = inputSnapshot.getReleasedMap().keySet().iterator().next(); 
                        this.refreshButtonText(); 
                        this.currentlyUpdating = null;
                    }
                    break;
            }
        }

    }
    
    public void sceneEntered(ArrayList args) 
    {
        //set mouse cursor
        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.POINTERHAND));
    }
   
    public void sceneExited()
    {
        Game.getInstance().unloadScene(ControlsScene.class);
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
    // Class Methods
    //===============
    
    
   private void refreshButtonText()
   {
       final int right = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x;
        final int center = right/2;
        
       this.upText.setText("Up             " +  ControlsScene.getKeyText(settings.up));   
       //upText.setPosition(center - upText.getWidth()/2, 630);
       this.downText.setText("Down             " +  ControlsScene.getKeyText(settings.down)); 
       //downText.setPosition(center - downText.getWidth()/2, 580);
       this.leftText.setText("Left             " +  ControlsScene.getKeyText(settings.left)); 
      // leftText.setPosition(center - leftText.getWidth()/2, 530);
       this.rightText.setText("Right             " +  ControlsScene.getKeyText(settings.right)); 
      // rightText.setPosition(center - rightText.getWidth()/2, 480);
       this.jumpText.setText("Jump             " +  ControlsScene.getKeyText(settings.jump)); 
      // jumpText.setPosition(center - jumpText.getWidth()/2, 430);
       this.sprintText.setText("Sprint             " +  ControlsScene.getKeyText(settings.sprint));
      // sprintText.setPosition(center - sprintText.getWidth()/2, 380);
       this.flashlightText.setText("Flashlight             " +  ControlsScene.getKeyText(settings.flashlight)); 
      // flashlightText.setPosition(center - flashlightText.getWidth()/2, 330);
       this.potionText.setText("Potion             " +  ControlsScene.getKeyText(settings.potion)); 
      // potionText.setPosition(center - potionText.getWidth()/2, 280);
       this.skill3Text.setText("Skill3             " +  ControlsScene.getKeyText(settings.skill3));
      // skill3Text.setPosition(center - skill3Text.getWidth()/2, 230);
       this.skill4Text.setText("Skill4             " +  ControlsScene.getKeyText(settings.skill4));
      // skill4Text.setPosition(center - skill4Text.getWidth()/2, 180);

    
   }
   
    public static String getKeyText(short keyCode)
    {
            switch(keyCode)
            {
                case  0 : return  "UNDEFINED" ;
                case  1 : return  "FREE01" ;
                case  2 : return  "HOME" ;
                case  3 : return  "END" ;
                case  4 : return  "FINAL" ;
                case  5 : return  "PRINTSCREEN" ;
                case  6 : return  "FREE06" ;
                case  7 : return  "FREE07" ;
                case  8 : return  "BACK_SPACE" ;
                case  9 : return  "TAB" ;
                case  10 : return  "FREE0A" ;
                case  11 : return  "PAGE_DOWN" ;
                case  12 : return  "CLEAR" ;
                case  13 : return  "ENTER" ;
                case  14 : return  "FREE0E" ;
                case  15 : return  "SHIFT" ;
                case  16 : return  "PAGE_UP" ;
                case  17 : return  "CONTROL" ;
                case  18 : return  "ALT" ;
                case  19 : return  "ALT_GRAPH" ;
                case  20 : return  "CAPS_LOCK" ;
                case  21 : return  "FREE15" ;
                case  22 : return  "PAUSE" ;
                case  23 : return  "SCROLL_LOCK" ;
                case  24 : return  "CANCEL" ;
                case  25 : return  "FREE19" ;
                case  26 : return  "INSERT" ;
                case  27 : return  "ESCAPE" ;
                case  28 : return  "CONVERT" ;
                case  29 : return  "NONCONVERT" ;
                case  30 : return  "ACCEPT" ;
                case  31 : return  "MODECHANGE" ;
                case  32 : return  "SPACE" ;
                case  33 : return  "EXCLAMATION_MARK" ;
                case  34 : return  "QUOTEDBL" ;
                case  35 : return  "NUMBER_SIGN" ;
                case  36 : return  "DOLLAR" ;
                case  37 : return  "PERCENT" ;
                case  38 : return  "AMPERSAND" ;
                case  39 : return  "QUOTE" ;
                case  40 : return  "LEFT_PARENTHESIS" ;
                case  41 : return  "RIGHT_PARENTHESIS" ;
                case  42 : return  "ASTERISK" ;
                case  43 : return  "PLUS" ;
                case  44 : return  "COMMA" ;
                case  45 : return  "MINUS" ;
                case  46 : return  "PERIOD" ;
               case  47 : return  "SLASH" ;
                case  48 : return  "0" ;
                case  49 : return  "1" ;
                case  50 : return  "2" ;
                case  51 : return  "3" ;
                case  52 : return  "4" ;
                case  53 : return  "5" ;
                case  54 : return  "6" ;
                case  55 : return  "7" ;
                case  56 : return  "8" ;
                case  57 : return  "9" ;
                case  58 : return  "COLON" ;
                case  59 : return  "SEMICOLON" ;
                case  60 : return  "LESS" ;
                case  61 : return  "EQUALS" ;
                case  62 : return  "GREATER" ;
                case  63 : return  "QUESTIONMARK" ;
                case  64 : return  "AT" ;
                case  65 : return  "A" ;
                case  66 : return  "B" ;
                case  67 : return  "C" ;
                case  68 : return  "D" ;
                case  69 : return  "E" ;
                case  70 : return  "F" ;
                case  71 : return  "G" ;
                case  72 : return  "H" ;
                case  73 : return  "I" ;
                case  74 : return  "J" ;
                case  75 : return  "K" ;
                case  76 : return  "L" ;
                case  77 : return  "M" ;
                case  78 : return  "N" ;
                case  79 : return  "O" ;
                case  80 : return  "P" ;
                case  81 : return  "Q" ;
                case  82 : return  "R" ;
                case  83 : return  "S" ;
                case  84 : return  "T" ;
                case  85 : return  "U" ;
                case  86 : return  "V" ;
                case  87 : return  "W" ;
                case  88 : return  "X" ;
                case  89 : return  "Y" ;
                case  90 : return  "Z" ;
                case  91 : return  "OPEN_BRACKET" ;
                case  92 : return  "BACK_SLASH" ;
                case  93 : return  "CLOSE_BRACKET" ;
                case  94 : return  "CIRCUMFLEX" ;
                case  95 : return  "UNDERSCORE" ;
                case  96 : return  "BACK_QUOTE" ;
                case  97 : return  "F1" ;
                case  98 : return  "F2" ;
                case  99 : return  "F3" ;
                case  100 : return  "F4" ;
                case  101 : return  "F5" ;
                case  102 : return  "F6" ;
                case  103 : return  "F7" ;
                case  104 : return  "F8" ;
                case  105 : return  "F9" ;
                case  106 : return  "F10" ;
                case  107 : return  "F11" ;
                case  108 : return  "F12" ;
                case  109 : return  "F13" ;
                case  110 : return  "F14" ;
                case  111 : return  "F15" ;
                case  112 : return  "F16" ;
                case  113 : return  "F17" ;
                case  114 : return  "F18" ;
                case  115 : return  "F19" ;
                case  116 : return  "F20" ;
                case  117 : return  "F21" ;
                case  118 : return  "F22" ;
                case  119 : return  "F23" ;
                case  120 : return  "F24" ;
                case  123 : return  "LEFT_BRACE" ;
                case  124 : return  "PIPE" ;
                case  125 : return  "RIGHT_BRACE" ;
                case  126 : return  "TILDE" ;
                case  127 : return  "SEPARATOR" ;
                case  128 : return  "NUMPAD0" ;
                case  129 : return  "NUMPAD1" ;
                case  130 : return  "NUMPAD2" ;
                case  131 : return  "NUMPAD3" ;
                case  132 : return  "NUMPAD4" ;
                case  133 : return  "NUMPAD5" ;
                case  134 : return  "NUMPAD6" ;
                case  135 : return  "NUMPAD7" ;
                case  136 : return  "NUMPAD8" ;
                case  137 : return  "NUMPAD9" ;
                case  138 : return  "DECIMAL" ;
                case  139 : return  "ADD" ;
                case  140 : return  "SUBTRACT" ;
                case  141 : return  "MULTIPLY" ;
                case  142 : return  "DIVIDE" ;
                case  147 : return  "DELETE" ;
                case  148 : return  "NUM_LOCK" ;
                case  149 : return  "LEFT" ;
                case  150 : return  "UP" ;
                case  151 : return  "RIGHT" ;
               case  152 : return  " DOWN" ;
                case  153 : return  "CONTEXT_MENU" ;
                case  154 : return  "WINDOWS" ;
                case  155 : return  "META" ;
                case  156 : return  "HELP" ;
                case  157 : return  "COMPOSE" ;
                case  158 : return  "BEGIN" ;
                case  159 : return  "STOP" ;
                case  161 : return  "INVERTED_EXCLAMATION_MARK" ;
                case  8364 : return  "EURO_SIGN" ;
                case  -1927 : return  "CUT" ;
                case  -1926 : return  "COPY" ;
                case  -1925 : return  "PASTE" ;
                case  -1924 : return  "UNDO" ;
                case  -1923 : return  "AGAIN" ;
                case  -1922 : return  "FIND" ;
                case  -1921 : return  "PROPS" ;
                case  -1904 : return  "INPUT_METHOD_ON_OFF" ;
                case  -1903 : return  "CODE_INPUT" ;
                case  -1902 : return  "ROMAN_CHARACTERS" ;
                case  -1901 : return  "ALL_CANDIDATES" ;
                case  -1900 : return  "PREVIOUS_CANDIDATE" ;
                case  -1899 : return  "ALPHANUMERIC" ;
                case  -1898 : return  "KATAKANA" ;
                case  -1897 : return  "HIRAGANA" ;
                case  -1896 : return  "FULL_WIDTH" ;
                case  -1894 : return  "HALF_WIDTH" ;
                case  -1893 : return  "JAPANESE_KATAKANA" ;
                case  -1892 : return  "JAPANESE_HIRAGANA" ;
                case  -1891 : return  "JAPANESE_ROMAN" ;
                case  -1889 : return  "KANA_LOCK" ;
                case  -1793 : return  "KEYBOARD_INVISIBLE" ;
                default: return "";
            }
        }
    
    

}
