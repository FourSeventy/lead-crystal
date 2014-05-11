package com.silvergobletgames.leadcrystal.menus;

import com.jogamp.newt.event.KeyEvent;
import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.core.InputHandler;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.media.opengl.GL3bc;
import com.silvergobletgames.leadcrystal.items.Potion;
import com.silvergobletgames.leadcrystal.entities.ItemEntity;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager;
import com.silvergobletgames.leadcrystal.netcode.ChatManager;
import com.silvergobletgames.leadcrystal.netcode.OpenInstructionalTipPacket.InstructionalTip;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;
import com.silvergobletgames.leadcrystal.skills.SkillFactory;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import com.silvergobletgames.sylver.util.SylverVector2f;
import com.silvergobletgames.sylver.windowsystem.Menu;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import jogamp.opengl.gl4.GL4bcImpl;

/**
 *
 * @author mike
 */
public class Hud extends Window 
{
    //scene and player references
    private final GameClientScene sceneReference;   
    private final PlayerEntity playerReference;
    
    //dialogue
    private DialogueWindow activeDialogue;
    
    //active level name
    public String activeLevelName;
    
    private Button healthFrame;

    //health bar
    private Button healthFill;
    private float healthFillSize = 536;
    private Label healthMaxText;
    private Label healthCurrentText;
    private Button healthAreaTransparencyFeeler;
    
    //potion counter
    private Button potionImage;
    private Label potionText;
    
    private Button skillFrame;
    private Button skillButton1;  private Label skillHotkey1; private Label skillCooldown1; private SkillCooldownGraphic skillCooldownBlack1;
    private Button skillButton2;  private Label skillHotkey2; private Label skillCooldown2; private SkillCooldownGraphic skillCooldownBlack2;
    private Button skillButton3;  private Label skillHotkey3; private Label skillCooldown3; private SkillCooldownGraphic skillCooldownBlack3;
    private Button skillButton4;  private Label skillHotkey4; private Label skillCooldown4; private SkillCooldownGraphic skillCooldownBlack4;
    private Button questButton;
    private Button escMenu;
    
    //radar stuffs
    private Button radarFrame;
    private ArrayList<Button> primaryObjectiveArrows = new ArrayList<>();
    private ArrayList<Button> secondaryObjectiveArrows = new ArrayList<>();
    private ArrayList<Button> playerArrows = new ArrayList<>();
    
    //credit element
    private Label creditLabel;
    private Button creditIcon;
    
    //pain overlay
    private Button painOverlay;
    
    //you have died text and button
    private Label youHaveDied;
    private Button youDiedButton;
    private Text youHaveDiedText;
    
    
    //==================
    // networking stats
    //==================
    //ping
    public Label pingText;   
    //packet loss
    public Label packetLossText;   
    //server ip and port
    public Label serverIpText;
    public Label serverPortText;
    //Framerate
    public Label fpsText;
    
    //=======
    //Menus
    //=======
    private ArrayList<Window> menuList = new ArrayList<>();
    public SkillMenu skillMenu;  
    public EscapeMenu escapeMenu;
    public PotionsMenu potionsMenu;
    public ArmorMenu armorMenu;
    public MapMenu mapMenu;
    public QuestMenu questMenu;
    
    //Chat manager
    public ChatManager chatManager;  
    
    
    //================
    // Help  Tooltips
    //================
    private Button jumpTip;
    private Button leftClick;
    private Button rightClick;
    private Button sprint;
    private Button usePotion;
    private Button useLadder;
    private Button jumpThrough;
    private Button rightClickInteract;
    
    
    
    

    
    public Hud(GameClientScene scene)
    {
        super(new Image("blank.png"),0,0,1440,900);
        
        //scene reference
        this.sceneReference = scene;
        
        //player reference
        this.playerReference = scene.player;
        
        //position variables 
        float center = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x/2;
        float right = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x;
        
        //===============
        // Menus
        //==============
        
        //initialize potions menu
        potionsMenu = new PotionsMenu(2,100);
        potionsMenu.addActionListener(new ActionListener(){     
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("mouseEntered"))
                {
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND)); 
                }
                else if(e.getActionCommand().equals("mouseExited"))
                {
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.RETICULE)); 
                }
            }
        });
        potionsMenu.update();
        potionsMenu.close();
        potionsMenu.setOwningScene(scene);
        menuList.add(potionsMenu);
        
        //initializing armor menu
        armorMenu = new ArmorMenu(playerReference,center - 600,0);
        armorMenu.setPosition(center - armorMenu.getWidth()/2, 0);
        armorMenu.addActionListener(new ActionListener(){     
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("mouseEntered"))
                {
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND)); 
                }
                else if(e.getActionCommand().equals("mouseExited"))
                {
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.RETICULE)); 
                }
            }
        });
        armorMenu.update();
        armorMenu.close();
        armorMenu.setOwningScene(scene);
        menuList.add(armorMenu);
        
        //initializing the skill menu
        skillMenu = new SkillMenu(right - 560,100,playerReference);
        skillMenu.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("mouseEntered"))
                {
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND)); 
                }
                else if(e.getActionCommand().equals("mouseExited"))
                {
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.RETICULE)); 
                }
            }
        
        });
        skillMenu.update();
        skillMenu.close();
        skillMenu.setOwningScene(scene);
        menuList.add(skillMenu);
        
        //map menu
        mapMenu = new MapMenu(center - 600,0,(GameClientScene)scene);
        mapMenu.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("mouseEntered"))
                {
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND)); 
                }
                else if(e.getActionCommand().equals("mouseExited"))
                {
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.RETICULE)); 
                }
            }
        
        });
        mapMenu.update();
        mapMenu.close();
        mapMenu.setOwningScene(scene);
        menuList.add(mapMenu);
        
        //quest menu
        questMenu = new QuestMenu(10,0,playerReference);
        questMenu.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("mouseEntered"))
                {
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND)); 
                }
                else if(e.getActionCommand().equals("mouseExited"))
                {
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.RETICULE)); 
                }
            }
        
        });
        questMenu.update();
        questMenu.close();
        questMenu.setOwningScene(scene);
        menuList.add(questMenu);
        
        //escape menu
        this.escapeMenu = new EscapeMenu(center-200,250);
        this.escapeMenu.close();
        escapeMenu.setOwningScene(scene);
        menuList.add(escapeMenu);
        
        //pain overlay     
        this.painOverlay = new Button(new Image("painOverlay1.png"), 0, 0, Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x, Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().y);
        this.painOverlay.dontKillClick = true;
        this.painOverlay.getImage().setAlphaBrightness(0);
        this.painOverlay.getImage().setAlphaCulling(false);
        this.addComponent(painOverlay);
        
        //maxes the health and shield area go transparent if you mouse over it
//        healthAreaTransparencyFeeler = new Button("blank.png",0,769,788 * .73f,164 * .73f);
//        healthAreaTransparencyFeeler.addActionListener(new ActionListener(){       
//            public void actionPerformed(ActionEvent e)
//            {
//                if(e.getActionCommand().equals("mouseEntered"))
//                {
//                    healthFrame.getImage().setColor(new Color(1,1,1,.3f)); 
//                    healthFill.getImage().setColor(new Color(1,1,1,.3f)); 
//                    healthCurrentText.getText().setColor(new Color(1,1,1,.3f));
//                    healthMaxText.getText().setColor(new Color(1,1,1,.3f));
//                    potionText.getText().setColor(new Color(1,1,1,.3f));
//                    potionImage.getImage().setColor(new Color(1,1,1,.3f));
//                    
//                }
//                if(e.getActionCommand().equals("mouseExited"))
//                {
//                    healthFrame.getImage().setColor(new Color(1,1,1,1f)); 
//                    healthFill.getImage().setColor(new Color(1,1,1,1f)); 
//                    healthCurrentText.getText().setColor(new Color(1,1,1,1f));
//                    healthMaxText.getText().setColor(new Color(1,1,1,1f));
//                    potionText.getText().setColor(new Color(1,1,1,1f));
//                    potionImage.getImage().setColor(new Color(1,1,1,1f));
//                }
//            }        
//        });
//        healthAreaTransparencyFeeler.dontKillClick = true;       
//        this.addComponent(healthAreaTransparencyFeeler); 
        
        
        //Health     
        this.healthFrame = new Button("healthbarFrame.png",5, 756,788 * .70f, 164 * .70f);
        this.healthFrame.dontKillClick = true;
        this.addComponent(healthFrame);             
        
        this.healthFill = new Button("healthFill.png",13, 818,healthFillSize,28);
        healthFill.dontKillClick = true;
        this.addComponent(healthFill);               

       
        //health numbers
        Text text = new Text(Integer.toString((int)playerReference.getCombatData().currentHealth) + "/",LeadCrystalTextType.HUD22);
        this.healthCurrentText = new Label(text,248,825);
        this.addComponent(healthCurrentText); 
        
        text = new Text(Integer.toString((int)playerReference.getCombatData().maxHealth.getTotalValue()),LeadCrystalTextType.HUD22);
        this.healthMaxText = new Label(text,248 + healthCurrentText.getText().getWidth(),825);
        this.addComponent(healthMaxText);  
        
        
        //potion counter
        this.potionImage = new Button("healthPot3.png", 229,762,34,34); 
        this.potionImage.dontKillClick = true;
        this.addComponent(potionImage);
        
        Text te = new Text("0",LeadCrystalTextType.HUD28);
        this.potionText = new Label(te, 268, 771);
        this.addComponent(potionText);
      
        
        //skill bar stuff
        this.skillFrame = new Button("skillbar.png",center- (456 * .7f)/2,0,456 * .7f,111 * .7f);
        this.addComponent(this.skillFrame);
        this.skillButton1 = new Button("blank.png",center - 131,-1,66,67);
        this.addComponent(skillButton1);
        this.skillButton1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
               GameClientScene gs = ((GameClientScene)owningScene);
                //if we click it when assigning skills
                if(e.getActionCommand().equals("clicked") && skillMenu.isOpen() && skillMenu.hand != null)
                {
                    //clear any old overlays
                    skillButton1.getImage().removeAllOverlays();
                    //add new overlay       
                    Image image = new Image(skillMenu.hand.getTextureReference());
                    image.setDimensions(skillButton1.getWidth(), skillButton1.getHeight());
                    image.setScale(.85f);
                    Overlay overlay = new Overlay(image);
                    overlay.useRelativeSize = false;
                    overlay.setRelativePosition(.1f, .1f);
                    skillButton1.getImage().addOverlay(overlay);
                    //set skill name
                    playerReference.setSkillAssignment(skillMenu.handSkillID, 1) ;
                    
                    //clear hand
                    skillMenu.hand = null;
                    skillMenu.handSkillID = null;
                    
                    //tell the server we changed our skill
                    gs.sendSkillPacket();
                }
                //if we clicked it when playing the game
                else if(e.getActionCommand().equals("clicked") && !skillMenu.isOpen())
                {
                }
            }        
        });              
        this.skillButton2 = new Button("blank.png",center - 67,-1,66,67);
        this.addComponent(skillButton2);
        this.skillButton2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                 GameClientScene gs = ((GameClientScene)owningScene);
                 
                //if we click it when assigning skills
                if(e.getActionCommand().equals("clicked") && skillMenu.isOpen() && skillMenu.hand != null)
                {
                    //clear any old overlays
                    skillButton2.getImage().removeAllOverlays();
                    //add new overlay                    
                    Image image = new Image(skillMenu.hand.getTextureReference());
                    image.setDimensions(skillButton1.getWidth(), skillButton1.getHeight());
                    image.setScale(.85f);
                    Overlay overlay = new Overlay(image);
                    overlay.useRelativeSize = false;
                    overlay.setRelativePosition(.1f, .1f);
                    skillButton2.getImage().addOverlay(overlay);
                    //set skill name
                    playerReference.setSkillAssignment(skillMenu.handSkillID, 2);
                    
                    //clear hand
                    skillMenu.hand = null;
                    skillMenu.handSkillID = null;
                    
                    //tell the server we changed our skill
                    gs.sendSkillPacket();
                }
                //if we clicked it when playing the game
                else if(e.getActionCommand().equals("clicked") && !skillMenu.isOpen())
                {
                }
            }        
        });
        this.skillButton3 = new Button("blank.png",center -2,-1,66,67);
        this.addComponent(skillButton3);
        this.skillButton3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                GameClientScene gs = ((GameClientScene)owningScene);
                
                //if we click it when assigning skills
                if(e.getActionCommand().equals("clicked") && skillMenu.isOpen() && skillMenu.hand != null)
                {
                    //clear any old overlays
                    skillButton3.getImage().removeAllOverlays();
                    //add new overlay                    
                    Image image = new Image(skillMenu.hand.getTextureReference());
                    image.setDimensions(skillButton1.getWidth(), skillButton1.getHeight());
                    image.setScale(.85f);
                    Overlay overlay = new Overlay(image);
                    overlay.useRelativeSize = false;
                    overlay.setRelativePosition(.1f, .1f);
                    skillButton3.getImage().addOverlay(overlay);
                    //set skill name
                    playerReference.setSkillAssignment(skillMenu.handSkillID, 3);
                    
                    //clear hand
                    skillMenu.hand = null;
                    skillMenu.handSkillID = null;
                    
                    //tell the server we changed our skill
                    gs.sendSkillPacket();
                }
                //if we clicked it when playing the game
                else if(e.getActionCommand().equals("clicked") && !skillMenu.isOpen())
                {
                }
            }        
        });
        this.skillButton4 = new Button("blank.png",center + 64,-1,66,67);
        this.addComponent(skillButton4);
        this.skillButton4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                GameClientScene gs = ((GameClientScene)owningScene);
                
                //if we click it when assigning skills
                if(e.getActionCommand().equals("clicked") && skillMenu.isOpen() && skillMenu.hand != null)
                {
                    //clear any old overlays
                    skillButton4.getImage().removeAllOverlays();
                    //add new overlay                    
                   Image image = new Image(skillMenu.hand.getTextureReference());
                    image.setDimensions(skillButton1.getWidth(), skillButton1.getHeight());
                    image.setScale(.85f);
                    Overlay overlay = new Overlay(image);
                    overlay.useRelativeSize = false;
                    overlay.setRelativePosition(.1f, .1f);
                    skillButton4.getImage().addOverlay(overlay);
                    //set skill name
                    playerReference.setSkillAssignment(skillMenu.handSkillID, 4);
                    
                    //clear hand
                    skillMenu.hand = null;
                    skillMenu.handSkillID = null;
                    
                    //tell the server we changed our skill
                    gs.sendSkillPacket();
                }
                //if we clicked it when playing the game
                else if(e.getActionCommand().equals("clicked") && !skillMenu.isOpen())
                {
                }
            }        
        });
        
        //skill hotkey text
        Text t = new Text("Left",LeadCrystalTextType.HUD20);
        this.skillHotkey1 = new Label(t,center - 115,10) ;      
        this.addComponent(skillHotkey1);
        
        t = new Text("Right",LeadCrystalTextType.HUD20);
        this.skillHotkey2 = new Label(t,center - 55,10) ;      
        this.addComponent(skillHotkey2);
        
        t = new Text("Q",LeadCrystalTextType.HUD20);
        this.skillHotkey3 = new Label(t,center + 5,10) ;      
        this.addComponent(skillHotkey3);
        
        t = new Text("E",LeadCrystalTextType.HUD20);
        this.skillHotkey4 = new Label(t,center + 71,10) ;      
        this.addComponent(skillHotkey4);
        
        //skill cooldown black
        Image black = new Image("black.png");
        black.setColor(new Color(1,1,1,.8f));
        this.skillCooldownBlack1  = new SkillCooldownGraphic( this.skillButton1.getPosition().x  + this.skillButton1.getWidth()/2, this.skillButton1.getPosition().y  + this.skillButton1.getHeight()/2, this.skillButton1.getWidth() -5, this.skillButton1.getHeight() -5);
        this.addComponent(skillCooldownBlack1);
        
        black = new Image("black.png");
        black.setColor(new Color(1,1,1,.8f));
        this.skillCooldownBlack2  = new SkillCooldownGraphic( this.skillButton2.getPosition().x + this.skillButton2.getWidth()/2, this.skillButton2.getPosition().y  + this.skillButton2.getHeight()/2, this.skillButton2.getWidth() -5, this.skillButton2.getHeight() -5);
        this.addComponent(skillCooldownBlack2);
        
        black = new Image("black.png");
        black.setColor(new Color(1,1,1,.8f));
        this.skillCooldownBlack3  = new SkillCooldownGraphic( this.skillButton3.getPosition().x  + this.skillButton3.getWidth()/2, this.skillButton3.getPosition().y  + this.skillButton3.getHeight()/2, this.skillButton3.getWidth() -5, this.skillButton3.getHeight() -5);
        this.addComponent(skillCooldownBlack3);
        
        black = new Image("black.png");
        black.setColor(new Color(1,1,1,.8f));
        this.skillCooldownBlack4  = new SkillCooldownGraphic( this.skillButton4.getPosition().x  + this.skillButton4.getWidth()/2, this.skillButton4.getPosition().y  + this.skillButton4.getHeight()/2, this.skillButton4.getWidth() -5, this.skillButton4.getHeight() -5);
        this.addComponent(skillCooldownBlack4);
        
        //skill cooldown text
        t = new Text("0");
        t.setScale(1.3f);
        this.skillCooldown1 = new Label(t,center - 105, 20);
        this.addComponent(skillCooldown1);
        
        t = new Text("0");
        t.setScale(1.3f);
        this.skillCooldown2 = new Label(t,center - 45, 20);
        this.addComponent(skillCooldown2);
        
        t = new Text("0");
        t.setScale(1.3f);
        this.skillCooldown3 = new Label(t,center + 10, 20);
        this.addComponent(skillCooldown3);
        
        t = new Text("0");
        t.setScale(1.3f);
        this.skillCooldown4 = new Label(t,center + 70, 20);
        this.addComponent(skillCooldown4);   
              
        
        
        //radar stuff       
        this.radarFrame = new Button("radar.png",right - 233,648,340 * .69f,363 * .69f);
        this.radarFrame.setHidden(false);
        radarFrame.dontKillClick = true;
        this.addComponent(radarFrame);
        
        //credits hud element
        t = new Text(Integer.toString(this.playerReference.getCurrencyManager().getBalence()),LeadCrystalTextType.HUD22);
        this.creditLabel = new Label(t, right - 114, 669);
        this.addComponent(creditLabel);
        this.creditIcon = new Button("currency2.png", right - 133,667,17,17);
        this.creditIcon.dontKillClick = true;
        this.addComponent(creditIcon);
        
        //Menu Buttons      
        this.questButton = new Button("radar_button.png",right - 224,770,42 * .70f,61  * .70f);
        questButton.addActionListener(new ActionListener()  {           
            public void actionPerformed(ActionEvent e) 
            {
                if(e.getActionCommand().equals("clicked"))
                {
               
                  questMenu.toggle();
                }    
                if(e.getActionCommand().equals("mouseEntered"))
                {
                    questButton.getImage().setBrightness(1.5f);
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND)); 
                }
                if(e.getActionCommand().equals("mouseExited"))
                {
                    questButton.getImage().setBrightness(1f);
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.RETICULE)); 
                }
            }      
        });      
        this.addComponent(questButton);  
        
        //Menu Buttons      
        this.escMenu = new Button("radar_button.png",right - 224,723,42  * .70f,61  * .70f);
        escMenu.addActionListener(new ActionListener()  {           
            public void actionPerformed(ActionEvent e) 
            {
                if(e.getActionCommand().equals("mouseEntered"))
                {
                    escMenu.getImage().setBrightness(1.5f);
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND)); 
                }
                if(e.getActionCommand().equals("mouseExited"))
                {
                    escMenu.getImage().setBrightness(1f);
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.RETICULE)); 
                }
               
                if(e.getActionCommand().equals("clicked"))
                {
               
                  escapeMenu.toggle();
                }                
            }      
        });      
        this.addComponent(escMenu);  

        //networking stats 
        pingText = new Label("0ms",right - 140, 70);     
        packetLossText= new Label("0% pl",right - 140,50);        
        serverIpText = new Label("1.1.1.1",right - 140, 30);       
        serverPortText = new Label("1234",right - 140, 10);     
        fpsText = new Label("9000",right - 140, 90);
        
        //initializing chat manager
        this.chatManager = new ChatManager(scene);
        this.chatManager.receiveMessage("[Message] Welcome to the game!");
        
        this.leftClick= new Button(new Image("leftClick.png"), center - 300, 600, 600 , 150);
        this.leftClick.setHidden(true);
        this.leftClick.dontKillClick = true;
        this.addComponent(leftClick); 
        this.rightClick = new Button(new Image("rightClick.png"), center - 300, 600, 600, 150);
        this.rightClick.setHidden(true);
        this.rightClick.dontKillClick = true;
        this.addComponent(rightClick);
        this.usePotion = new Button(new Image("usePotion.png"), center - 300, 600, 600, 150);
        this.usePotion.setHidden(true);
        this.usePotion.dontKillClick = true;
        this.addComponent(usePotion);
        this.jumpTip = new Button(new Image("jumpTip.png"), center - 300, 600, 600, 150);
        this.jumpTip.setHidden(true);
        this.jumpTip.dontKillClick = true;
        this.addComponent(jumpTip);
        this.useLadder= new Button(new Image("useLadders.png"), center - 300, 600, 600 , 150);
        this.useLadder.setHidden(true);
        this.useLadder.dontKillClick = true;
        this.addComponent(useLadder); 
        this.jumpThrough= new Button(new Image("jumpThrough.png"), center - 300, 600, 600 , 150);
        this.jumpThrough.setHidden(true);
        this.jumpThrough.dontKillClick = true;
        this.addComponent(jumpThrough); 
        this.sprint= new Button(new Image("sprint.png"), center - 300, 600, 600 , 150);
        this.sprint.setHidden(true);
        this.sprint.dontKillClick = true;
        this.addComponent(sprint); 
        this.rightClickInteract= new Button(new Image("rightClickInteract.png"), center - 300, 600, 600 , 150);
        this.rightClickInteract.setHidden(true);
        this.rightClickInteract.dontKillClick = true;
        this.addComponent(rightClickInteract); 
        
        
        //set up skills
        this.setUpSkillBar();
       
        
         
    }
    
    public void update()
    {
        super.update();
               
        //position variables 
        float center = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x/2;
        float right = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x;
        
        //adjust health  fill
        float ratio = (float)playerReference.getCombatData().currentHealth / (float)playerReference.getCombatData().maxHealth.getTotalValue();       
        healthFill.setWidth( healthFillSize * ratio); 
        healthFill.getImage().setDimensions(healthFillSize * ratio, 28);
          
        //adjust health numbers
        this.healthCurrentText.getText().setText(Integer.toString((int)playerReference.getCombatData().currentHealth) + "/");
        this.healthMaxText.getText().setText(Integer.toString((int)playerReference.getCombatData().maxHealth.getTotalValue()));
        this.healthMaxText.setWindowRelativePosition(248 + healthCurrentText.getText().getWidth(),825);
        
        
        //potion counter
        this.potionText.getText().setText(Integer.toString(this.playerReference.getPotionManager().getNumberOfPotions()));
        
        //=================
        // Skill Cooldowns
        //=================
        
        if(playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(1)) != null)
            this.skillCooldown1.getText().setText(Integer.toString((playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(1)).getCooldownRemaining() + 60)/60));
        if(playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(2)) != null)
            this.skillCooldown2.getText().setText(Integer.toString((playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(2)).getCooldownRemaining()+ 60)/60));
        if(playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(3)) != null)
            this.skillCooldown3.getText().setText(Integer.toString((playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(3)).getCooldownRemaining()+ 60)/60));
        if(playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(4)) != null)
            this.skillCooldown4.getText().setText(Integer.toString((playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(4)).getCooldownRemaining()+ 60)/60));
        
        if(playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(1)) == null || playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(1)).getCooldownRemaining() <=0)
        {
            this.skillCooldown1.setHidden(true);
            this.skillCooldownBlack1.setHidden(true);
        }
        else
        {
            if(GameplaySettings.getInstance().showCooldownTimers)
                this.skillCooldown1.setHidden(false);
            
            this.skillCooldownBlack1.setHidden(false);
            this.skillCooldownBlack1.percentCutout = 1 -(float)playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(1)).getCooldownRemaining()/ (float)playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(1)).getCooldown();
        }
        
        if(playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(2)) == null || playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(2)).getCooldownRemaining() <=0)
        {
            this.skillCooldown2.setHidden(true);
            this.skillCooldownBlack2.setHidden(true);
           
        }
        else
        {
            if(GameplaySettings.getInstance().showCooldownTimers)
               this.skillCooldown2.setHidden(false);
            
            this.skillCooldownBlack2.setHidden(false);
            this.skillCooldownBlack2.percentCutout = 1- (float)playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(2)).getCooldownRemaining()/(float)playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(2)).getCooldown();
            
        }
        
        if(playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(3)) == null || playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(3)).getCooldownRemaining() <=0)
        {
            this.skillCooldown3.setHidden(true);
             this.skillCooldownBlack3.setHidden(true);
           
        }
        else
        {
            if(GameplaySettings.getInstance().showCooldownTimers)
                this.skillCooldown3.setHidden(false);   
            
             this.skillCooldownBlack3.setHidden(false);
             this.skillCooldownBlack3.percentCutout = 1 -(float)playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(3)).getCooldownRemaining()/ (float)playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(3)).getCooldown();
             
        }
        
        if(playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(4)) == null || playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(4)).getCooldownRemaining() <=0)
        {
            this.skillCooldown4.setHidden(true);
            this.skillCooldownBlack4.setHidden(true);
            
        }
        else
        {
            if(GameplaySettings.getInstance().showCooldownTimers)
                this.skillCooldown4.setHidden(false);
            
            this.skillCooldownBlack4.setHidden(false);
            this.skillCooldownBlack4.percentCutout = 1- (float)playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(4)).getCooldownRemaining() / playerReference.getSkillManager().getSkill(playerReference.getSkillAssignment(4)).getCooldown();
        }
        
        //update menus
        ArrayList<Window> updateList = new ArrayList(menuList);
        for(Window menu: updateList)
            menu.update();
        
        //update dialogue window
        if(this.activeDialogue != null)
            this.activeDialogue.update();
        
        //update chat manager
        this.chatManager.update();
        this.chatManager.handleInput(Game.getInstance().getInputHandler().getInputSnapshot());
        
        
        //==============
        // Radar Arrows
        //==============
        if(this.playerReference.getOwningScene() != null)
        {
            //position and radius of radar
            float radarRadius = this.radarFrame.getWidth()/2 - 40;
            SylverVector2f centerOfRadar = new SylverVector2f(this.radarFrame.getPosition().x + radarRadius , this.radarFrame.getPosition().y + radarRadius );
           
            //remove all old arrows
            for(Button b :this.primaryObjectiveArrows)
                this.removeComponent(b);
            this.primaryObjectiveArrows.clear();
            
            for(Button b :this.secondaryObjectiveArrows)
                this.removeComponent(b);
            this.secondaryObjectiveArrows.clear();
            
            for(Button b :this.playerArrows)
                this.removeComponent(b);
            this.playerArrows.clear();
            
            //get primary objectives and build their arrows
            ArrayList<SceneObject> primaryObjectiveEntities = this.playerReference.getOwningScene().getSceneObjectManager().get(ExtendedSceneObjectGroups.PRIMARYOBJECTIVE);
            for(SceneObject primaryObjectiveSO: primaryObjectiveEntities)
            {
                Entity primaryObjectiveEnt = (Entity)primaryObjectiveSO;
                
                SylverVector2f vectorToObjective = this.playerReference.distanceVector(primaryObjectiveEnt);
                vectorToObjective.normalise();
                
                SylverVector2f arrowPosition =new SylverVector2f(centerOfRadar.x + vectorToObjective.x * radarRadius, centerOfRadar.y + vectorToObjective.y * radarRadius);
                Button button = new Button(new Image("primaryArrow.png"), arrowPosition.x, arrowPosition.y, 40, 40);
                this.addComponent(button);
                this.primaryObjectiveArrows.add(button);
                
                
                //set image angle
                float angle = (float)Math.toDegrees(Math.acos(vectorToObjective.dot(new SylverVector2f(-1,0))));
                if(vectorToObjective.y > 0)
                    angle = -angle;
                
                button.getImage().setAngle(angle + 90);
            }
            
            //get secondary objectives and build their arrows
            if(playerReference.getCombatData().containsEffect("showSecondary"))
            {
                ArrayList<SceneObject> secondaryObjectiveEntities = this.playerReference.getOwningScene().getSceneObjectManager().get(ExtendedSceneObjectGroups.SECONDARYOBJECTIVE);
                for(SceneObject secondaryObjectiveSO: secondaryObjectiveEntities)
                {
                    Entity secondaryObjectiveEnt = (Entity)secondaryObjectiveSO;

                    SylverVector2f vectorToObjective = this.playerReference.distanceVector(secondaryObjectiveEnt);
                    vectorToObjective.normalise();

                    SylverVector2f arrowPosition =new SylverVector2f(centerOfRadar.x + vectorToObjective.x * radarRadius, centerOfRadar.y + vectorToObjective.y * radarRadius);
                    Button button = new Button(new Image("newSecondaryArrow.png"), arrowPosition.x, arrowPosition.y, 30, 30);
                    this.addComponent(button);
                    this.secondaryObjectiveArrows.add(button);


                    //set image angle
                    float angle = (float)Math.toDegrees(Math.acos(vectorToObjective.dot(new SylverVector2f(-1,0))));
                    if(vectorToObjective.y > 0)
                        angle = -angle;

                    button.getImage().setAngle(angle + 90);
                }
            }
            
            //get players and build their arrows
            ArrayList<SceneObject> playerObjects = this.playerReference.getOwningScene().getSceneObjectManager().get(ExtendedSceneObjectGroups.PLAYER);
            for(SceneObject playerSO: playerObjects)
            {
                if(playerSO == this.playerReference)
                    continue;
                
                Entity playerEnt = (Entity)playerSO;
                
                //calculate vectorto player
                float dx = this.playerReference.getImage().getPosition().getX() - playerEnt.getImage().getPosition().getX();
                float dy = this.playerReference.getImage().getPosition().getY() - playerEnt.getImage().getPosition().getY();
        
                SylverVector2f vectorToPlayer = new SylverVector2f(dx,dy);
                vectorToPlayer.normalise();
                
                SylverVector2f arrowPosition =new SylverVector2f(centerOfRadar.x + vectorToPlayer.x * radarRadius, centerOfRadar.y + vectorToPlayer.y * radarRadius);
                Button button = new Button(new Image("playerArrow.png"), arrowPosition.x, arrowPosition.y, 30, 30);
                this.addComponent(button);
                this.secondaryObjectiveArrows.add(button);
                
                
                //set image angle
                float angle = (float)Math.toDegrees(Math.acos(vectorToPlayer.dot(new SylverVector2f(-1,0))));
                if(vectorToPlayer.y > 0)
                    angle = -angle;
                
                button.getImage().setAngle(angle - 90);
            }
       
        }
        
        //credit counter
        this.creditLabel.getText().setText(Integer.toString(this.playerReference.getCurrencyManager().getBalence()));
        
        //set pain alpha 
        this.painOverlay.getImage().setColor(new Color(1,1,1,1 - this.playerReference.getCombatData().getPercentHealth())); 
      
        
        //check for death
        if(playerReference.getCombatData().isDead())
        {
            if(youHaveDied == null)
            {
                Text dead= new Text("You Have Died", CoreTextType.MENU);
                dead.setScale(1f);
                this.youHaveDied = new Label(dead, center - dead.getWidth()/2, 600);
                youHaveDied.getText().setColor(new Color(1,1,1,0)); 
                
                Color[] points2 = {new Color(1,1,1,0),new Color(1,1,1,0),new Color(1,1,1,1)};
                int[] durations2 = {150,60};
                MultiTextEffect fadeEffect = new MultiTextEffect(TextEffect.TextEffectType.COLOR, points2,durations2);   
                youHaveDied.getText().addTextEffect(fadeEffect);
                
                this.addComponent(youHaveDied);
                
                
                
                final Text ehh = new Text("Respawn In Town",LeadCrystalTextType.MESSAGE);
                ehh.setPosition(center - 200, 500);
                this.youHaveDiedText = ehh;
                this.owningScene.add(ehh, Layer.HUD);
                this.youDiedButton = new Button(new Image("blank.png"), center - ehh.getWidth()/2, ehh.getPosition().y, ehh.getWidth(), ehh.getHeight());
                youDiedButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    ((GameClientScene)owningScene).sendRespawnRequestPacket();
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(ehh.hasTextEffect("small"))
                          ehh.removeTextEffect("small");
                      
                       ehh.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, ehh.getScale(), 1.3));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    owningScene.add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(ehh.hasTextEffect("big"))
                           ehh.removeTextEffect("big");
                        
                        ehh.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, ehh.getScale(), 1));
                }
            }
        });
                
                
                
                ehh.setColor(new Color(1,1,1,0)); 
                Color[] points3 = {new Color(1,1,1,0),new Color(1,1,1,0),new Color(1,1,1,1)};
                int[] durations3 = {150,60};
                MultiTextEffect fadeEffect2 = new MultiTextEffect(TextEffect.TextEffectType.COLOR, points3,durations3);   
                
                ehh.addTextEffect(fadeEffect2);
                
                this.addComponent(youDiedButton);
                
            }
        }
        else
        {
            if(youHaveDied != null)
            {
                this.removeComponent(youHaveDied);
                this.removeComponent(youDiedButton);
                this.owningScene.remove(youHaveDiedText); 
                
                youHaveDied = null;
                youDiedButton = null;
            }
        }
        
        //disable menu buttons
         if(skillMenu.isOpen() )
         {
             this.questButton.setDisabled(true);

         }
         else
         {
             this.questButton.setDisabled(false);
         }
         

    
    }
        
    public void draw(GL2 gl)
    {
        super.draw(gl);
        
        //draw menus
        for(Window menu: this.menuList)
        {
            menu.draw(gl);
        }
        
        //draw chat
        this.chatManager.draw(gl);
        
        
    }
    
    public void setUpSkillBar()
    {

            if(playerReference.getSkillAssignment(1) != null)
            {                
                //set the icon
                skillButton1.getImage().removeAllOverlays();
                //add new overlay       
                Image image = SkillFactory.getInstance().getSkill(playerReference.getSkillAssignment(1)).getIcon();
                image.setDimensions(skillButton1.getWidth(), skillButton1.getHeight());
                image.setScale(.85f);
                Overlay overlay =new Overlay(image);
                overlay.setRelativePosition(.1f, .1f);
                overlay.useRelativeSize = false;
                skillButton1.getImage().addOverlay(overlay);
            }
            if(playerReference.getSkillAssignment(2) != null)
            {
                
                //set the icon
                skillButton2.getImage().removeAllOverlays();
                //add new overlay       
                Image image = SkillFactory.getInstance().getSkill(playerReference.getSkillAssignment(2)).getIcon();
                image.setDimensions(skillButton2.getWidth(), skillButton2.getHeight());
                image.setScale(.85f);
                Overlay overlay =new Overlay(image);
                overlay.setRelativePosition(.1f, .1f);
                overlay.useRelativeSize = false;
                skillButton2.getImage().addOverlay(overlay);
                
            }
            if(playerReference.getSkillAssignment(3) != null)
            {               
                //set the icon
                skillButton3.getImage().removeAllOverlays();
                //add new overlay       
                Image image = SkillFactory.getInstance().getSkill(playerReference.getSkillAssignment(3)).getIcon();
                image.setDimensions(skillButton3.getWidth(), skillButton3.getHeight());
                image.setScale(.85f);
                Overlay overlay =new Overlay(image);
                overlay.useRelativeSize = false;
                overlay.setRelativePosition(.1f, .1f);
                skillButton3.getImage().addOverlay(overlay);
            }
            if(playerReference.getSkillAssignment(4) != null)
            {
                
                //set the icon
                skillButton4.getImage().removeAllOverlays();
                //add new overlay       
                Image image = SkillFactory.getInstance().getSkill(playerReference.getSkillAssignment(4)).getIcon();
                image.setDimensions(skillButton4.getWidth(), skillButton4.getHeight());
                image.setScale(.85f);
                Overlay overlay =new Overlay(image);
                overlay.useRelativeSize = false;
                overlay.setRelativePosition(.1f, .1f);
                skillButton4.getImage().addOverlay(overlay);
            }           
        
    }
    
    /**
     * Set the active dialogue window
     * @param w 
     */
    public void openDialogue(String speaker, String text) 
    {
        //remove old dialogue
        this.closeDialogue();
        
        //Create and set the window
        float center = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x/2;
        DialogueWindow window = new DialogueWindow(center - 460,575, speaker, text);
        window.open();
        this.sceneReference.add(window, Layer.MENU);
        window.update();
        this.activeDialogue = window;
    }
    
    /**
     * Close active dialogue window
     */
    public void closeDialogue()
    {
        this.sceneReference.remove(activeDialogue);
        this.activeDialogue = null;
    }
    
    public boolean hasOpenDialogue()
    {
        return this.activeDialogue != null;
    }
    
    public void drawNetworkingStats(boolean value)
    {
        if(value == true)
        {
            this.removeComponent(pingText);
            this.removeComponent(packetLossText);
            this.removeComponent(serverIpText);
            this.removeComponent(serverPortText);
            this.removeComponent(fpsText);
            
            this.addComponent(pingText);
            this.addComponent(packetLossText);
            this.addComponent(serverIpText);
            this.addComponent(serverPortText);
            this.addComponent(fpsText);
        }
        else
        {
            this.removeComponent(pingText);
            this.removeComponent(packetLossText);
            this.removeComponent(serverIpText);
            this.removeComponent(serverPortText);
            this.removeComponent(fpsText);
        }
    }
    
    /**
     * This function is used to determine if the mouse is currently over an open menu
     * @return True if the mouse is over an open menu
     */
    public boolean isMouseOverMenu() 
    {
        for (Window m : menuList) 
        {
            if (m.isOpen() && Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x >= m.getPosition().x && Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x <= m.getPosition().x + m.getWidth() && Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y >= m.getPosition().y && Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y <= m.getPosition().y + m.getHeight()) 
            {
                return true;
            }
        }
        return false;
    } 
    
    public void openTooltip(InstructionalTip tip)
    { 
        switch(tip)
        {
            case PrimarySkill: this.leftClick.setHidden(false); break;
            case SecondarySkill: this.rightClick.setHidden(false); break;
            case UsePotion: this.usePotion.setHidden(false); break;
            case Jump: this.jumpTip.setHidden(false); break;
            case Ladder: this.useLadder.setHidden(false); break;
            case Jumpthrough: this.jumpThrough.setHidden(false); break;
            case Sprint: this.sprint.setHidden(false); break;
            case RightClick: this.rightClickInteract.setHidden(false); break;
        }
    }
    
    public void closeTooltip(InstructionalTip tip)
    {
        switch(tip)
        {
            case PrimarySkill: this.leftClick.setHidden(true); break;
            case SecondarySkill: this.rightClick.setHidden(true); break;
            case UsePotion: this.usePotion.setHidden(true); break;
            case Jump: this.jumpTip.setHidden(true); break;
            case Ladder: this.useLadder.setHidden(true); break;
            case Jumpthrough: this.jumpThrough.setHidden(true); break;
            case Sprint: this.sprint.setHidden(true); break;
            case RightClick: this.rightClickInteract.setHidden(true); break;
        }
    }
    
    
    
    
    
    //skill cooldown spiral graphic
    public static class SkillCooldownGraphic extends Button
    {
        public float percentCutout = 0;
        
        public SkillCooldownGraphic(float xPos, float yPos, float width, float height)
        {
            super(new Image("blank.png"),xPos,yPos,width,height);
        }
        
        @Override
        public void draw(GL2 gl)
        {           
            if(!this.hidden)
            {
                //set blending functions
                gl.glEnable(GL2.GL_BLEND);
                gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
                gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);

                //draw
                gl.glColor4f(0, 0, 0, .9f);           
                gl.glBegin(GL.GL_TRIANGLE_FAN);
                {
                    //middle point
                    gl.glVertex2f(this.getPosition().x,this.getPosition().y);

                    //edge points
                    for (float angle = 90; angle <=  450 - (360 * this.percentCutout) ; angle += 2) 
                    {

                        float drawRadius = this.getLengthForDeg(angle) * this.width/2;

                        gl.glVertex2f(drawRadius * (float) Math.cos(Math.toRadians(angle)) + this.getPosition().x,
                                    drawRadius * (float) Math.sin(Math.toRadians(angle)) + this.getPosition().y );                                 
                    }      
                }
                gl.glEnd();
                gl.glColor4f(1,1,1,1);


                //disable texture and blend modes
                gl.glDisable(GL2.GL_BLEND);  
            }
        }
        
        private float getLengthForDeg(float phi)
        {
            
            double angle = ((phi+45)%90-45)/180*Math.PI;
            return (float)(1/Math.cos(angle));
        }
    }
   
}
