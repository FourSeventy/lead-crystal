package com.silvergobletgames.leadcrystal.scenes;

import com.jogamp.newt.event.KeyEvent;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBlackBackArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBlackBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBlackFrontArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBlueBackArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBlueBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBlueFrontArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBrownBackArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBrownBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBrownFrontArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashGreenBackArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashGreenBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashGreenFrontArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashRedBackArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashRedBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashRedFrontArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashWhiteBackArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashWhiteBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashWhiteFrontArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashYellowBackArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashYellowBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashYellowFrontArmAnimationPack;
import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.core.Main;
import com.silvergobletgames.leadcrystal.core.SaveGame;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorStat;
import com.silvergobletgames.sylver.audio.AudioRenderer;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.*;
import com.silvergobletgames.sylver.graphics.AnimationPack;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.OpenGLGameWindow;
import com.silvergobletgames.sylver.graphics.RenderingPipelineGL2;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import com.silvergobletgames.sylver.graphics.TextEffect;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.TextBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL2;

public class NewCharacterScene extends Scene
{
    
    private TextBox nameTextBox;
    private final Text headText, bodyText;
    
    private Image headImage,bodyImage;
    
    //list of heads
    private ArrayList<String> headList = new ArrayList<>();
    private int currentHeadSelection = 0;
    
    //list of bodies
    private ArrayList<String> bodyList= new ArrayList<>();
    private int currentBodySelection = 0;
    
    
    //we have to pass this back to char select screen
    private String actionArg;
    
 
    
    //================
    //Constructors
    //================
    
    public NewCharacterScene()
    {

        //===================
        // Build Image Lists
        //===================
        
        this.headList.add("bash-head1.png");
        this.headList.add("bash-head0.png");
        this.headList.add("bash-head2.png");
        this.headList.add("bash-head3.png");
        this.headList.add("bash-head4.png");
        
        this.bodyList.add("bash_brown.png"); 
        this.bodyList.add("bash_black.png");
        this.bodyList.add("bash_blue.png");
        this.bodyList.add("bash_green.png");
        this.bodyList.add("bash_red.png");
        this.bodyList.add("bash_white.png");
        this.bodyList.add("bash_yellow.png");  
        
        
        
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
        Text title = new Text("New Character",LeadCrystalTextType.MENU60);
        title.setPosition(center - title.getWidth()/2, 740);       
        this.add(title,Layer.MAIN);
        
        setImages();
        
        //name label
        Text nameLabel = new Text("Name:",LeadCrystalTextType.MENU46);
        nameLabel.setPosition(center + 30, 560);
        this.add(nameLabel, Layer.MAIN);
        
        //name textbox
        nameTextBox = new TextBox(new Text("Bash",LeadCrystalTextType.MENU40), center + 180, 550);  
        nameTextBox.setHideBackground(true);
        nameTextBox.setCursorScale(1.85f);
        nameTextBox.setAlphaNumericRestriction(true);
        nameTextBox.setMaxCharacters(20);
        nameTextBox.setDimensions(400, 50);
        this.add(nameTextBox,Layer.MAIN);
        
        // head
        headText = new Text("Head: " + (this.currentHeadSelection + 1),LeadCrystalTextType.MENU46);
        headText.setPosition(center + 30, 505);
        final Button headButton = new Button(new Image("blank.png"), headText.getPosition().x, headText.getPosition().y, headText.getWidth(), headText.getHeight());
        this.add(headText,Layer.MAIN);
        this.add(headButton,Layer.MAIN);
        headButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                        currentHeadSelection++;
                        currentHeadSelection %= 5;
                        headText.setText("Head: " + (currentHeadSelection + 1));
                        setImages();

                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(headText.hasTextEffect("small"))
                          headText.removeTextEffect("small");
                      
                       headText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, headText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(headText.hasTextEffect("big"))
                           headText.removeTextEffect("big");
                        
                        headText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, headText.getScale(), 1));
                }
            }
        });
        
        //body
        bodyText = new Text("Body: " + (this.currentBodySelection + 1),LeadCrystalTextType.MENU46);
        bodyText.setPosition(center + 30, 450);
        final Button bodyButton = new Button(new Image("blank.png"), bodyText.getPosition().x, bodyText.getPosition().y, bodyText.getWidth(), bodyText.getHeight());
        this.add(bodyText,Layer.MAIN);
        this.add(bodyButton,Layer.MAIN);
        bodyButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                        currentBodySelection++;
                        currentBodySelection%=7;
                        bodyText.setText("Body: " + (currentBodySelection + 1));
                        setImages();

                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(bodyText.hasTextEffect("small"))
                          bodyText.removeTextEffect("small");
                      
                       bodyText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, bodyText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(bodyText.hasTextEffect("big"))
                           bodyText.removeTextEffect("big");
                        
                        bodyText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, bodyText.getScale(), 1));
                }
            }
        });
    
        
        //ok
        final Text okText = new Text("OK",LeadCrystalTextType.MENU50);
        okText.setPosition(center  - okText.getWidth()/2, 325);
        final Button okButton = new Button(new Image("blank.png"), okText.getPosition().x, okText.getPosition().y, okText.getWidth(), okText.getHeight());
        this.add(okText,Layer.MAIN);
        this.add(okButton,Layer.MAIN);
        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    //=============== 
                    //make the player
                    //===============
                    
                    PlayerMock playerMock = new PlayerMock(getBodyAnimationPack(bodyList.get(currentBodySelection)),headList.get(currentHeadSelection),getBackArmAnimationPack(bodyList.get(currentBodySelection)),getFrontArmAnimationPack(bodyList.get(currentBodySelection)),nameTextBox.getText());
                    
                    
                    //change scene
                    //if we are in multiplayer mode, go to the multiplayer menu and pass it your character
                    if(actionArg.equals("Multiplayer"))
                    {
                        Game.getInstance().loadScene(new MultiplayerMenuScene());

                        ArrayList args = new ArrayList();
                        args.add(playerMock);
                        args.add(actionArg);
                        Game.getInstance().changeScene(MultiplayerMenuScene.class,args);
                    }
                    //else if we are in single player mode, start up a game
                    else if(actionArg.equals("Singleplayer"))
                    {          
                        //stop music
                        Sound sound = Sound.newBGM("");
                        add(sound);

                        //start a single player game
                        MainMenuScene.startSinglePlayerGame(playerMock);

                    }                 
                  
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(okText.hasTextEffect("small"))
                          okText.removeTextEffect("small");
                      
                       okText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, okText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(okText.hasTextEffect("big"))
                           okText.removeTextEffect("big");
                        
                        okText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, okText.getScale(), 1));
                }
            }
        });
        
        //back
        final Text backText = new Text("Back",LeadCrystalTextType.MENU50);
        backText.setPosition(center - backText.getWidth()/2, 267);
        final Button backButton = new Button(new Image("blank.png"), okText.getPosition().x, backText.getPosition().y, backText.getWidth(), backText.getHeight());
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
        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.POINTERHAND));
    }
   
    public void sceneExited()
    {
        Game.getInstance().unloadScene(NewCharacterScene.class);
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
    
    
    private void setImages()
    {
        final int right = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x;
        final int center = right/2;
       
        //body image
        if(bodyImage != null)
           this.remove(bodyImage);
        bodyImage = new Image(this.bodyList.get(this.currentBodySelection));
        bodyImage.setScale(1.45f);
        bodyImage.setPosition(center - 377, 363);
        this.add(bodyImage, Layer.MAIN);
        
        //head image
        if(headImage != null)
           this.remove(headImage);
        headImage = new Image(this.headList.get(this.currentHeadSelection));
        headImage.setScale(1.45f);
        headImage.setPosition(center - 130, 585);
        this.add(headImage, Layer.MAIN);

    }
    
    
    private Class getBodyAnimationPack(String selection)
    {
        switch(selection)
        {
            case "bash_brown.png": return  BashBrownBodyAnimationPack.class;
            case "bash_blue.png": return  BashBlueBodyAnimationPack.class;
            case "bash_green.png": return  BashGreenBodyAnimationPack.class;
            case "bash_red.png": return  BashRedBodyAnimationPack.class;
            case "bash_white.png": return  BashWhiteBodyAnimationPack.class;
            case "bash_yellow.png": return  BashYellowBodyAnimationPack.class;
            case "bash_black.png": return  BashBlackBodyAnimationPack.class;
        }
        return null;
    }
    
    private Class getFrontArmAnimationPack(String selection)
    {
        switch(selection)
        {
            case "bash_brown.png": return  BashBrownFrontArmAnimationPack.class;
            case "bash_blue.png": return  BashBlueFrontArmAnimationPack.class;
            case "bash_green.png": return  BashGreenFrontArmAnimationPack.class;
            case "bash_red.png": return  BashRedFrontArmAnimationPack.class;
            case "bash_white.png": return  BashWhiteFrontArmAnimationPack.class;
            case "bash_yellow.png": return  BashYellowFrontArmAnimationPack.class;
            case "bash_black.png": return  BashBlackFrontArmAnimationPack.class;
        }
        return null;
    }
    
    private Class getBackArmAnimationPack(String selection)
    {
        switch(selection)
        {
            case "bash_brown.png": return  BashBrownBackArmAnimationPack.class;
            case "bash_blue.png": return  BashBlueBackArmAnimationPack.class;
            case "bash_green.png": return  BashGreenBackArmAnimationPack.class;
            case "bash_red.png": return  BashRedBackArmAnimationPack.class;
            case "bash_white.png": return  BashWhiteBackArmAnimationPack.class;
            case "bash_yellow.png": return  BashYellowBackArmAnimationPack.class;
            case "bash_black.png": return  BashBlackBackArmAnimationPack.class;
        }
        return null;
    }
    
    
    public static class PlayerMock //body head back front
    {
        public Class bodyAnimationPack, frontArmAnimationPack,backArmAnimationPack;
        public String headString, name;
        public String saveDataFileName = null;
        
        public PlayerMock(String saveDataFileName)
        {
            this.saveDataFileName = saveDataFileName;
        }
        
        public PlayerMock(Class body, String head, Class back, Class front, String name)
        {
            this.bodyAnimationPack = body;
            this.frontArmAnimationPack = front;
            this.backArmAnimationPack = back;
            this.headString = head;
            this.name = name;
        }
        
        
        public SaveGame buildSaveGameData()
        {
            if(this.saveDataFileName == null)
            {
                AnimationPack body;
                AnimationPack front;
                AnimationPack back;

                try{
                    body = (AnimationPack)this.bodyAnimationPack.newInstance();
                    front = (AnimationPack)this.frontArmAnimationPack.newInstance();
                    back = (AnimationPack)this.backArmAnimationPack.newInstance();
                }
                catch(IllegalAccessException | InstantiationException e)
                {
                    //log error to console
                    Logger logger =Logger.getLogger(NewCharacterScene.class.getName());
                    logger.log(Level.SEVERE, "Error building player data", e.toString());

                    body = new BashBrownBodyAnimationPack();
                    front= new BashBrownFrontArmAnimationPack();
                    back = new BashBrownBackArmAnimationPack();

                }

                PlayerEntity player = new PlayerEntity(new Image(body),new Image(this.headString),new Image(back),new Image(front));
                player.setName(this.name); 

                //dev settings
                if(player.getName().equals("devtest"))
                {

                    player.getCurrencyManager().addCurrency(5000); 
                    player.getLevelProgressionManager().completeMainObjective(0); 
                    player.getLevelProgressionManager().completeSideObjective(0);
                    player.getLevelProgressionManager().completeMainObjective(1); 
                    player.getLevelProgressionManager().completeSideObjective(1);
                    player.getLevelProgressionManager().completeMainObjective(2); 
                    player.getLevelProgressionManager().completeSideObjective(2);
                    player.getLevelProgressionManager().completeMainObjective(3);
                    player.getLevelProgressionManager().completeSideObjective(3);
                    player.getLevelProgressionManager().completeMainObjective(4);
                    player.getLevelProgressionManager().completeSideObjective(4);
                    player.getLevelProgressionManager().completeMainObjective(5);
                    player.getLevelProgressionManager().completeSideObjective(5);
                    player.getLevelProgressionManager().completeMainObjective(6); 
                    player.getLevelProgressionManager().completeSideObjective(6);
                    player.getLevelProgressionManager().completeMainObjective(7);
                    player.getLevelProgressionManager().completeSideObjective(7);
                    player.getLevelProgressionManager().completeMainObjective(8);
                    player.getLevelProgressionManager().completeSideObjective(8);
                    player.getLevelProgressionManager().completeMainObjective(9);
                    player.getLevelProgressionManager().completeSideObjective(9);
                    player.getLevelProgressionManager().completeMainObjective(10);
                    player.getLevelProgressionManager().completeSideObjective(10);
                    player.getLevelProgressionManager().completeMainObjective(11);
                    player.getLevelProgressionManager().completeSideObjective(11);
                    player.getLevelProgressionManager().completeMainObjective(12);
                    player.getLevelProgressionManager().completeSideObjective(12);
                    player.getLevelProgressionManager().completeMainObjective(13);
                    player.getLevelProgressionManager().completeSideObjective(13);
                    player.getLevelProgressionManager().completeMainObjective(14);
                    player.getLevelProgressionManager().completeSideObjective(14);
                    player.getLevelProgressionManager().completeMainObjective(15);
                    player.getLevelProgressionManager().completeSideObjective(15);
                    player.getLevelProgressionManager().completeMainObjective(16);
                    player.getLevelProgressionManager().completeSideObjective(16);
  

                }
                if(player.getName().equals("cavetest"))
                {

                    player.getCurrencyManager().addCurrency(220); 
                    player.getLevelProgressionManager().completeMainObjective(0); 
                    player.getLevelProgressionManager().completeSideObjective(0);
                    player.getLevelProgressionManager().completeMainObjective(1);
                    player.getLevelProgressionManager().completeSideObjective(1);
                    player.getLevelProgressionManager().completeMainObjective(2);
                    player.getLevelProgressionManager().completeSideObjective(2);
                    player.getLevelProgressionManager().completeMainObjective(3);
                    player.getLevelProgressionManager().completeSideObjective(3);
                    player.getLevelProgressionManager().completeMainObjective(4);
                    player.getLevelProgressionManager().completeSideObjective(4);
                    player.getLevelProgressionManager().completeMainObjective(5); 
                    player.getLevelProgressionManager().completeSideObjective(5);
                    
                    player.getSkillManager().setSkillPoints(6);


                }
                if(player.getName().equals("ruintest"))
                {

                    player.getCurrencyManager().addCurrency(440); 
                    player.getLevelProgressionManager().completeMainObjective(0); 
                    player.getLevelProgressionManager().completeSideObjective(0);
                    player.getLevelProgressionManager().completeMainObjective(1);
                    player.getLevelProgressionManager().completeSideObjective(1);
                    player.getLevelProgressionManager().completeMainObjective(2);
                    player.getLevelProgressionManager().completeSideObjective(2);
                    player.getLevelProgressionManager().completeMainObjective(3);
                    player.getLevelProgressionManager().completeSideObjective(3);
                    player.getLevelProgressionManager().completeMainObjective(4);
                    player.getLevelProgressionManager().completeSideObjective(4);
                    player.getLevelProgressionManager().completeMainObjective(5); 
                    player.getLevelProgressionManager().completeSideObjective(5);
                    player.getLevelProgressionManager().completeMainObjective(6);
                    player.getLevelProgressionManager().completeSideObjective(6);
                    player.getLevelProgressionManager().completeMainObjective(7); 
                    player.getLevelProgressionManager().completeSideObjective(7);
                    player.getLevelProgressionManager().completeMainObjective(8); 
                    player.getLevelProgressionManager().completeSideObjective(8);
                    player.getLevelProgressionManager().completeMainObjective(9);
                    player.getLevelProgressionManager().completeSideObjective(9);
                    player.getLevelProgressionManager().completeMainObjective(10);
                    player.getLevelProgressionManager().completeSideObjective(10);

                }
                if(player.getName().equals("bosstest"))
                {

                    player.getCurrencyManager().addCurrency(1000); 
                    player.getLevelProgressionManager().completeMainObjective(0); 
                    player.getLevelProgressionManager().completeSideObjective(0);
                    player.getLevelProgressionManager().completeMainObjective(1);
                    player.getLevelProgressionManager().completeSideObjective(1);
                    player.getLevelProgressionManager().completeMainObjective(2);
                    player.getLevelProgressionManager().completeSideObjective(2);
                    player.getLevelProgressionManager().completeMainObjective(3);
                    player.getLevelProgressionManager().completeSideObjective(3);
                    player.getLevelProgressionManager().completeMainObjective(4);
                    player.getLevelProgressionManager().completeSideObjective(4);
                    player.getLevelProgressionManager().completeMainObjective(5); 
                    player.getLevelProgressionManager().completeSideObjective(5);
                    player.getLevelProgressionManager().completeMainObjective(6);
                    player.getLevelProgressionManager().completeSideObjective(6);
                    player.getLevelProgressionManager().completeMainObjective(7); 
                    player.getLevelProgressionManager().completeSideObjective(7);
                    player.getLevelProgressionManager().completeMainObjective(8); 
                    player.getLevelProgressionManager().completeSideObjective(8);
                    player.getLevelProgressionManager().completeMainObjective(9);
                    player.getLevelProgressionManager().completeSideObjective(9);
                    player.getLevelProgressionManager().completeMainObjective(10);
                    player.getLevelProgressionManager().completeSideObjective(10);                  
                    player.getLevelProgressionManager().completeMainObjective(11);
                    player.getLevelProgressionManager().completeSideObjective(11);
                    player.getLevelProgressionManager().completeMainObjective(12);
                    player.getLevelProgressionManager().completeSideObjective(12);
                    player.getLevelProgressionManager().completeMainObjective(13);
                    player.getLevelProgressionManager().completeSideObjective(13);
                    player.getLevelProgressionManager().completeMainObjective(14);
                    player.getLevelProgressionManager().completeSideObjective(14);
                    player.getLevelProgressionManager().completeMainObjective(15);
                    player.getLevelProgressionManager().completeSideObjective(15);
                    player.getLevelProgressionManager().completeMainObjective(16);
                    player.getLevelProgressionManager().completeSideObjective(16);

                }

                SaveGame save = new SaveGame();
                save.setPlayer(player);
                save.save(player.getName() + ".save");

                return save;
            }  
            else
            {
                try
                {
                   return SaveGame.loadSaveFromFile(this.saveDataFileName);
                }
                catch(Exception e)
                {
                   System.err.println("Load Saved Game Fail: "); e.printStackTrace(System.err);
                   return null;
                }
            }
        }
        
    }

}
