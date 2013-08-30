package com.silvergobletgames.leadcrystal.scenes;

import com.jogamp.newt.event.KeyEvent;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.PlayerAnimationPack;
import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.core.SaveGame;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.sylver.audio.AudioRenderer;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.*;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.OpenGLGameWindow;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import com.silvergobletgames.sylver.graphics.TextEffect;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.TextBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NewCharacterScene extends Scene
{
    
    private TextBox nameTextBox;
    private final Text headText,colorText;
    
    //we have to pass this back to char select screen
    private String actionArg;
    
    
    //================
    //Constructors
    //================
    
    public NewCharacterScene()
    {

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
        Text title = new Text("New Character",CoreTextType.MENU);      
        title.setScale(1.2f);
        title.setPosition(center - title.getWidth()/2, 700);       
        this.add(title,Layer.MAIN);
        
        //player image
        Image player = new Image(new PlayerAnimationPack());
        player.setScale(1.3f);
        player.setPosition(center - player.getWidth()/2, 535);
        this.add(player, Layer.MAIN);
        
        //name label
        Text nameLabel = new Text("Name:",LeadCrystalTextType.MENUBUTTONS);
        nameLabel.setScale(1.2f);
        nameLabel.setPosition(center - 200, 475);
        this.add(nameLabel, Layer.MAIN);
        
        //name textbox
        nameTextBox = new TextBox("Player1", center, 475);        
        this.add(nameTextBox,Layer.MAIN);
        
        // head
        headText = new Text("Head: Normal",LeadCrystalTextType.MENUBUTTONS);
        headText.setScale(1.2f);
        headText.setPosition(center - headText.getWidth()/2, 425);
        final Button headButton = new Button(new Image("blank.png"), center - headText.getWidth()/2, headText.getPosition().y, headText.getWidth(), headText.getHeight());
        this.add(headText,Layer.MAIN);
        this.add(headButton,Layer.MAIN);
        headButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    
                        headText.setText("Head: Helmet");

                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(headText.hasTextEffect("small"))
                          headText.removeTextEffect("small");
                      
                       headText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, headText.getScale(), 1.5));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(headText.hasTextEffect("big"))
                           headText.removeTextEffect("big");
                        
                        headText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, headText.getScale(), 1.2));
                }
            }
        });
        
        //color
        colorText = new Text("Color: Normal",LeadCrystalTextType.MENUBUTTONS);
        colorText.setScale(1.2f);
        colorText.setPosition(center - colorText.getWidth()/2, 375);
        final Button colorButton = new Button(new Image("blank.png"), center - colorText.getWidth()/2, colorText.getPosition().y, colorText.getWidth(), colorText.getHeight());
        this.add(colorText,Layer.MAIN);
        this.add(colorButton,Layer.MAIN);
        colorButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                 
                    colorText.setText("Color: Red");   

                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(colorText.hasTextEffect("small"))
                          colorText.removeTextEffect("small");
                      
                       colorText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, colorText.getScale(), 1.5));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(colorText.hasTextEffect("big"))
                           colorText.removeTextEffect("big");
                        
                        colorText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, colorText.getScale(), 1.2));
                }
            }
        });      
        
        
        //ok
        final Text okText = new Text("OK",LeadCrystalTextType.MENUBUTTONS);
        okText.setScale(1.2f);
        okText.setPosition(center - okText.getWidth()/2, 275);
        final Button okButton = new Button(new Image("blank.png"), center - okText.getWidth()/2, okText.getPosition().y, okText.getWidth(), okText.getHeight());
        this.add(okText,Layer.MAIN);
        this.add(okButton,Layer.MAIN);
        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    //===============
                    //make the player
                    //===============
                    PlayerEntity player = new PlayerEntity();
                    player.setName(nameTextBox.getText()); 

                    //dev settings
                    if(player.getName().equals("devtest"))
                    {
                        player.getSkillManager().setSkillPoints(40);
                        player.getCurrencyManager().addCurrency(1000); 
                    }

                    SaveGame save = new SaveGame();
                    save.setPlayer(player);
                    save.save(player.getName() + ".save");
                    
                    //change scene
                    Game.getInstance().loadScene(new CharacterSelectionScene());
                    ArrayList<String> args = new ArrayList();
                    args.add(actionArg);
                    Game.getInstance().changeScene(CharacterSelectionScene.class,args);                    
                  
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(okText.hasTextEffect("small"))
                          okText.removeTextEffect("small");
                      
                       okText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, okText.getScale(), 1.5));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(okText.hasTextEffect("big"))
                           okText.removeTextEffect("big");
                        
                        okText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, okText.getScale(), 1.2));
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
                    //change scene
                    Game.getInstance().loadScene(new CharacterSelectionScene());
                    ArrayList<String> args = new ArrayList();
                    args.add(actionArg);
                    Game.getInstance().changeScene(CharacterSelectionScene.class,args);                     
                    
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

    public void handleInput() 
    {
        //if escape is pressed        
        if(Game.getInstance().getInputHandler().getInputSnapshot().isKeyReleased(KeyEvent.VK_ESCAPE)) 
        {           

            //change scene
            Game.getInstance().loadScene(new CharacterSelectionScene());
            Game.getInstance().changeScene(CharacterSelectionScene.class,null); 
        }

    }
    
   
    
    public void sceneEntered(ArrayList args) 
    {
        //set our args
        if(args != null && args.get(0) != null)
            this.actionArg = (String)args.get(0);
        
        //set mouse cursor
        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND));
    }
   
    public void sceneExited()
    {
        Game.getInstance().unloadScene(NewCharacterScene.class);
    }
}
