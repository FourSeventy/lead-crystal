
package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.skills.PlayerBuckshot;
import com.silvergobletgames.leadcrystal.skills.PlayerDashAttack;
import com.silvergobletgames.leadcrystal.skills.PlayerSnipe;
import com.silvergobletgames.leadcrystal.skills.PlayerAttackDrone;
import com.silvergobletgames.leadcrystal.skills.PlayerGravityShield;
import com.silvergobletgames.leadcrystal.skills.PlayerWard;
import com.silvergobletgames.sylver.core.InputHandler;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.media.opengl.GL3bc;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.leadcrystal.skills.*;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.*;
import java.util.ArrayList;
import javax.media.opengl.GL2;



public class SkillMenu extends Window {

   //local reference to the player
   private PlayerEntity playerReference;
   
   //item hovered tooltip
   private Button skillTooltipBackground;
   private Label skillTooltipName;
   private Button skillTooltipIcon;
   private TextBlock skillTooltipTextBlock;
   
   //skill points
   private Label skillPoints;
   
   //skill hand
   protected Image hand;
   protected SkillID handSkillID;
   
   //skill slots
   private Skill[][] skillSlots = new Skill[4][4];
   
   //is locked boolenas
   private boolean[] primaryLock= {true,true,true,true};
   private boolean[] secondaryLock= {true,true,true,true};
   private boolean[] powerLock= {true,true,true,true};
   private boolean[] techLock= {true,true,true,true};
   
   //locked componenets
   private ArrayList<WindowComponent> primary1LockedComponents = new ArrayList();
   private ArrayList<WindowComponent> primary2LockedComponents = new ArrayList();
   private ArrayList<WindowComponent> primary3LockedComponents = new ArrayList();
   private ArrayList<WindowComponent> primary4LockedComponents = new ArrayList();
   
   private ArrayList<WindowComponent> secondary1LockedComponents = new ArrayList();
   private ArrayList<WindowComponent> secondary2LockedComponents = new ArrayList();
   private ArrayList<WindowComponent> secondary3LockedComponents = new ArrayList();
   private ArrayList<WindowComponent> secondary4LockedComponents = new ArrayList();
   
   private ArrayList<WindowComponent> power1LockedComponents = new ArrayList();
   private ArrayList<WindowComponent> power2LockedComponents = new ArrayList();
   private ArrayList<WindowComponent> power3LockedComponents = new ArrayList();
   private ArrayList<WindowComponent> power4LockedComponents = new ArrayList();
   
   private ArrayList<WindowComponent> tech1LockedComponents = new ArrayList();
   private ArrayList<WindowComponent> tech2LockedComponents = new ArrayList();
   private ArrayList<WindowComponent> tech3LockedComponents = new ArrayList();
   private ArrayList<WindowComponent> tech4LockedComponents = new ArrayList();

   


    /**
    * inventory menu constructor.
    * @param player reference to the player
    * 
    */
    public SkillMenu(float x, float y,PlayerEntity player)
    {
       //super constructor call, setting the background sprite and initial position
       super(new Image("skillFrame.png"),x,y,1200,900);
       
        //text
        Text menuText = new Text("Skill Menu",LeadCrystalTextType.HUD34);
        Label menuTextLabel = new Label(menuText,600 - menuText.getWidth()/2,830);
        this.addComponent(menuTextLabel);
        
        //close
        final Image closeImage = new Image("closeButton.png");
        Button closeButton = new Button(closeImage,1154,867,closeImage.getWidth()+1,closeImage.getHeight());
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
       
       //get new player referene
       playerReference =player;
        
        //section2
        final Image section2 = new Image("text_holder.png");
        Button sectionButton2 = new Button(section2,1000,800,150,30);
        this.addComponent(sectionButton2);
        
        //skill points
        Text playerGoldDesc = new Text("Skill Points:",LeadCrystalTextType.HUD24);
        Label playerGoldDescLabel = new Label(playerGoldDesc,879,809);
        this.addComponent(playerGoldDescLabel);
        
       //skill points
       String points = Integer.toString(player.getSkillManager().getSkillPoints());
       this.skillPoints = new Label(new Text(points,LeadCrystalTextType.HUD24),1095,806);
       this.addComponent(this.skillPoints);
       
       Button butt = new Button(new Image("skillPoint.png"),1125,805,20,20);
       butt.dontKillClick = true;
       this.addComponent(butt);
       
       //==========================
       // Assigning Skills to Slots
       //==========================
       
       //primary
       this.skillSlots[0][0] = new PlayerLaserShot();
       this.skillSlots[0][1] = new PlayerBuckshot();
       this.skillSlots[0][2] = new PlayerRicochet();
       this.skillSlots[0][3] = new PlayerRocket();
       
       //secondary
       this.skillSlots[1][0] = new PlayerBashAttack();
       this.skillSlots[1][1] = new PlayerDashAttack();
       this.skillSlots[1][2] = new PlayerBoomerang();
       this.skillSlots[1][3] = new PlayerSnipe();
       
       //power
       this.skillSlots[2][0] = new PlayerPoisonBomb();
       this.skillSlots[2][1] = new PlayerCrushingStrike();
       this.skillSlots[2][2] = new PlayerClusterbomb();
       this.skillSlots[2][3] = new PlayerBarrelRoll();
       
       //tech
       this.skillSlots[3][0] = new PlayerWard();
       this.skillSlots[3][1] = new PlayerLeechingBlades();
       this.skillSlots[3][2] = new PlayerGravityShield();
       this.skillSlots[3][3] = new PlayerAttackDrone();
       
       
       //=========================
       // Building skill buttons
       //=========================
       
       //Weapon Label
        Text weaponText = new Text("Primary Skills",LeadCrystalTextType.HUD28);
        Label weaponLabel = new Label(weaponText, 310 - weaponText.getWidth()/2, 690);
        this.addComponent(weaponLabel);
        //Helm Label
        Text helmText = new Text("Power Skills",LeadCrystalTextType.HUD28);
        Label helmLabel = new Label(helmText, 910 -helmText.getWidth()/2, 690);
        this.addComponent(helmLabel);
        //body Label
        Text bodyText = new Text("Secondary Skills",LeadCrystalTextType.HUD28);
        Label bodyLabel = new Label(bodyText, 310 -bodyText.getWidth()/2, 390);
        this.addComponent(bodyLabel);
        //boots Label
        Text bootsText = new Text("Tech Skills",LeadCrystalTextType.HUD28);
        Label bootsLabel = new Label(bootsText, 910 - helmText.getWidth()/2, 390);
        this.addComponent(bootsLabel);
       
       //================== Primary ========================//
       final int primaryOffsetX = 100;
       final int primaryOffsetY = 550;
       //laser shot
       final Skill skill = this.skillSlots[0][0];
       Button b = new Button(skill.getIcon(),primaryOffsetX,primaryOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(primaryLock[0] == false)
                   {
                        hand = skill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(skill.getSkillID());
                   }
               }
               if(e.getActionCommand().equals("mouseDown"))
               {
                  if(primaryLock[0] == false && hand == null)
                   {
                        hand = skill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill.getSkillID();
                   } 
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill,primaryOffsetX + 100 + 15,primaryOffsetY +30 );
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(b);
       
       //buckshot
       final Skill skill5 = this.skillSlots[0][1];
       b = new Button(skill5.getIcon(),primaryOffsetX + 110,primaryOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(primaryLock[1] == false)
                   {
                        hand = skill5.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill5.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(skill5.getSkillID());
                   }
               }
               if(e.getActionCommand().equals("mouseDown"))
               {
                   if(primaryLock[1] == false && hand == null)
                   {
                        hand = skill5.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill5.getSkillID();
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill5,primaryOffsetX + 210 + 15,primaryOffsetY +30 );
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(b);
       
       //ricochet
       final Skill skill7 = this.skillSlots[0][2];
       b = new Button(skill7.getIcon(),primaryOffsetX + 220,primaryOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(primaryLock[2] == false)
                   {
                        hand = skill7.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill7.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(skill7.getSkillID());
                   }
               }
               if(e.getActionCommand().equals("mouseDown"))
               {
                   if(primaryLock[2] == false && hand == null)
                   {
                        hand = skill7.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill7.getSkillID();
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill7,primaryOffsetX + 320 + 15,primaryOffsetY +30 );
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(b);
       
       //rocket launcher
       final Skill blade = this.skillSlots[0][3];
       b = new Button(blade.getIcon(),primaryOffsetX + 330,primaryOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(primaryLock[3] == false)
                   {
                        hand = blade.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = blade.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(blade.getSkillID());
                   }
               }
               if(e.getActionCommand().equals("mouseDown"))
               {
                   if(primaryLock[3] == false && hand == null)
                   {
                        hand = blade.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = blade.getSkillID();
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(blade,primaryOffsetX + 430 + 15,primaryOffsetY +30 );
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(b);
       
       // =================== Secondary ========================//
       final int secondaryOffsetX = 100;
       final int secondaryOffsetY = 250;
       //bash
       final Skill skill1 = this.skillSlots[1][0];
       b = new Button(skill1.getIcon(),secondaryOffsetX,secondaryOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(secondaryLock[0] == false)
                   {
                        hand = skill1.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill1.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(skill1.getSkillID());
                   }
               }
               if(e.getActionCommand().equals("mouseDown"))
               {
                   if(secondaryLock[0] == false && hand == null)
                   {
                        hand = skill1.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill1.getSkillID();
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill1,secondaryOffsetX + 100 + 15,secondaryOffsetY +30 );
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(b);       
       
       //dash
       final Skill skill3 = this.skillSlots[1][1];
       b = new Button(skill3.getIcon(),secondaryOffsetX + 110,secondaryOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(secondaryLock[1] == false)
                   {
                        hand = skill3.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill3.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(skill3.getSkillID());
                   }
               }
               if(e.getActionCommand().equals("mouseDown"))
               {
                   if(secondaryLock[1] == false && hand == null)
                   {
                        hand = skill3.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill3.getSkillID();
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill3,secondaryOffsetX + 210 + 15,secondaryOffsetY +30 );
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(b);
       
       //boomerang
       final Skill freezeSkill = this.skillSlots[1][2];
       b = new Button(freezeSkill.getIcon(),secondaryOffsetX + 220,secondaryOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(secondaryLock[2] == false)
                   {
                        hand = freezeSkill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = freezeSkill.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(freezeSkill.getSkillID());
                   }
               }
               if(e.getActionCommand().equals("mouseDown"))
               {
                   if(secondaryLock[2] == false && hand == null)
                   {
                        hand = freezeSkill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = freezeSkill.getSkillID();
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(freezeSkill,secondaryOffsetX + 320 + 15,secondaryOffsetY +30 );
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(b);
       
       //snipe
       final Skill snipeSkill = this.skillSlots[1][3];
       b = new Button(snipeSkill.getIcon(),secondaryOffsetX + 330,secondaryOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(secondaryLock[3] == false)
                   {
                        hand = snipeSkill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = snipeSkill.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(snipeSkill.getSkillID());
                   }
               }             
               if(e.getActionCommand().equals("mouseDown"))
               {
                   if(secondaryLock[3] == false && hand == null)
                   {
                        hand = snipeSkill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = snipeSkill.getSkillID();
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(snipeSkill,secondaryOffsetX + 430 + 15,secondaryOffsetY +30 );
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(b);
       
       //=================== Power ==========================//
       
       final int powerOffsetX = 700;
       final int powerOffsetY = 550;
       //stomp attack
       final Skill skill6 = this.skillSlots[2][0];
       b = new Button(skill6.getIcon(),powerOffsetX,powerOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(powerLock[0] == false)
                   {
                        hand = skill6.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill6.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(skill6.getSkillID());
                   }
               }
               if(e.getActionCommand().equals("mouseDown"))
               {
                   if(powerLock[0] == false && hand == null)
                   {
                        hand = skill6.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill6.getSkillID();
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill6,powerOffsetX - 425 ,powerOffsetY + 30);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
           
       });
       this.addComponent(b);
       
       //dash attack
       final Skill flashbangSkill =  this.skillSlots[2][1];
       b = new Button(flashbangSkill.getIcon(),powerOffsetX + 110,powerOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(powerLock[1] == false)
                   {
                        hand = flashbangSkill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = flashbangSkill.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(flashbangSkill.getSkillID());
                   }
               }
               if(e.getActionCommand().equals("mouseDown"))
               {
                   if(powerLock[1] == false && hand == null)
                   {
                        hand = flashbangSkill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = flashbangSkill.getSkillID();
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(flashbangSkill,powerOffsetX - 315 ,powerOffsetY + 30);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
           
       });
       this.addComponent(b);
       
       //stimpack
       final Skill stimSkill = this.skillSlots[2][2];
       b = new Button(stimSkill.getIcon(),powerOffsetX + 220,powerOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(powerLock[2] == false)
                   {
                        hand = stimSkill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = stimSkill.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(stimSkill.getSkillID());
                   }
               }
               if(e.getActionCommand().equals("mouseDown"))
               {
                   if(powerLock[2] == false && hand == null)
                   {
                        hand = stimSkill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = stimSkill.getSkillID();
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(stimSkill,powerOffsetX - 205 ,powerOffsetY + 30);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
           
       });
       this.addComponent(b);
       
       //life leech
       final Skill gravitySkill = this.skillSlots[2][3];
       b = new Button(gravitySkill.getIcon(),powerOffsetX + 330,powerOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(powerLock[3] == false)
                   {
                        hand = gravitySkill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = gravitySkill.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(gravitySkill.getSkillID());
                   }
               }
               if(e.getActionCommand().equals("mouseDown"))
               {
                  if(powerLock[3] == false && hand == null)
                   {
                        hand = gravitySkill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = gravitySkill.getSkillID();
                   } 
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(gravitySkill,powerOffsetX - 95 ,powerOffsetY + 30);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
           
       });
       this.addComponent(b);
       
       //=================== Tech =========================
       
       final int techOffsetX = 700;
       final int techOffsetY = 250;
       //attack drone
       final Skill skill2 = this.skillSlots[3][0];
       b = new Button(skill2.getIcon(),techOffsetX,techOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(techLock[0] == false)
                   {         
                        hand = skill2.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill2.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(skill2.getSkillID());
                   }
               }
               if(e.getActionCommand().equals("mouseDown"))
               {
                   if(techLock[0] == false && hand == null)
                   {         
                        hand = skill2.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill2.getSkillID();
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill2,techOffsetX - 425 ,techOffsetY + 30);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(b);
       
       //damage ward
       final Skill skill4 = this.skillSlots[3][1];
       b = new Button(skill4.getIcon(),techOffsetX + 110,techOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(techLock[1] == false)
                   {
                        hand = skill4.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill4.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(skill4.getSkillID());
                   }
               }
               if(e.getActionCommand().equals("mouseDown"))
               {
                   if(techLock[1] == false &&  hand == null)
                   {
                        hand = skill4.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = skill4.getSkillID();
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill4,techOffsetX - 315 ,techOffsetY + 30);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(b);
       
       //defensive shield
       final Skill playerGuardSkill = this.skillSlots[3][2];
       b = new Button(playerGuardSkill.getIcon(),techOffsetX + 220,techOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(techLock[2] == false)
                   {
                        hand = playerGuardSkill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = playerGuardSkill.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(playerGuardSkill.getSkillID());
                   }
               }
               if(e.getActionCommand().equals("mouseDown"))
               {
                   if(techLock[2] == false && hand == null)
                   {
                        hand = playerGuardSkill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = playerGuardSkill.getSkillID();
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(playerGuardSkill,techOffsetX - 205 ,techOffsetY + 30);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(b);
       
       //flashbang
       final Skill playerFlashbangSkill = this.skillSlots[3][3];
       b = new Button(playerFlashbangSkill.getIcon(),techOffsetX + 330,techOffsetY,95,95);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   if(techLock[3] == false)
                   {
                        hand = playerFlashbangSkill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = playerFlashbangSkill.getSkillID();
                   }
                   else //buy the skill
                   {
                       ((GameClientScene)owningScene).sendBuySkillPacket(playerFlashbangSkill.getSkillID());
                   }
               }
               if(e.getActionCommand().equals("mouseDown"))
               {
                  if(techLock[3] == false && hand == null)
                   {
                        hand = playerFlashbangSkill.getIcon();
                        hand.setAnchor(Anchorable.Anchor.CENTER);
                        hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
                        hand.setDimensions(40, 40);
                        handSkillID = playerFlashbangSkill.getSkillID();
                   } 
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(playerFlashbangSkill,techOffsetX - 95 ,techOffsetY + 30);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(b);
       
       //=========================
       // Build Locked Components
       //=========================
       
        // primary1
        Image i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,primaryOffsetX ,primaryOffsetY,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        primary1LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),primaryOffsetX + 27,primaryOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        primary1LockedComponents.add(b);
        Label l = new Label(Integer.toString(this.skillSlots[0][0].getUnlockCost()),primaryOffsetX + 8,primaryOffsetY+8);
        this.addComponent(l);
        primary1LockedComponents.add(l);
        
        // primary2
        i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,primaryOffsetX + 110 ,primaryOffsetY ,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        primary2LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),primaryOffsetX + 137,primaryOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        primary2LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[0][1].getUnlockCost()),primaryOffsetX + 118,primaryOffsetY+8);
        this.addComponent(l);
        primary2LockedComponents.add(l);
        
        // parimary3
        i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,primaryOffsetX + 220,primaryOffsetY,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        primary3LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),primaryOffsetX + 247,primaryOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        primary3LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[0][2].getUnlockCost()),primaryOffsetX + 228,primaryOffsetY+8);
        this.addComponent(l);
        primary3LockedComponents.add(l);
        
        // primary4
        i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,primaryOffsetX + 330,primaryOffsetY,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        primary4LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),primaryOffsetX + 357,primaryOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        primary4LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[0][3].getUnlockCost()),primaryOffsetX + 338,primaryOffsetY +8);
        this.addComponent(l);
        primary4LockedComponents.add(l);
        
        
        // secondary1
        i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,secondaryOffsetX ,secondaryOffsetY,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        secondary1LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),secondaryOffsetX + 27,secondaryOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        secondary1LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[1][0].getUnlockCost()),secondaryOffsetX + 8,secondaryOffsetY+8);
        this.addComponent(l);
        secondary1LockedComponents.add(l);
        
        // secondary2
        i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,secondaryOffsetX + 110 ,secondaryOffsetY ,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        secondary2LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),secondaryOffsetX + 137,secondaryOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        secondary2LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[1][1].getUnlockCost()),secondaryOffsetX + 118,secondaryOffsetY+8);
        this.addComponent(l);
        secondary2LockedComponents.add(l);
        
        // secondary3
        i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,secondaryOffsetX + 220,secondaryOffsetY,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        secondary3LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),secondaryOffsetX + 247,secondaryOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        secondary3LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[1][2].getUnlockCost()),secondaryOffsetX + 228,secondaryOffsetY+8);
        this.addComponent(l);
        secondary3LockedComponents.add(l);
        
        // secondary4
        i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,secondaryOffsetX + 330,secondaryOffsetY,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        secondary4LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),secondaryOffsetX + 357,secondaryOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        secondary4LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[1][3].getUnlockCost()),secondaryOffsetX + 338,secondaryOffsetY +8);
        this.addComponent(l);
        secondary4LockedComponents.add(l);
        
        
        // power1
        i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,powerOffsetX ,powerOffsetY,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        power1LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),powerOffsetX + 27,powerOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        power1LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[2][0].getUnlockCost()),powerOffsetX + 8,powerOffsetY+8);
        this.addComponent(l);
        power1LockedComponents.add(l);
        
        // power2
        i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,powerOffsetX + 110 ,powerOffsetY ,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        power2LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),powerOffsetX + 137,powerOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        power2LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[2][1].getUnlockCost()),powerOffsetX + 118,powerOffsetY+8);
        this.addComponent(l);
        power2LockedComponents.add(l);
        
        // parimary3
        i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,powerOffsetX + 220,powerOffsetY,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        power3LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),powerOffsetX + 247,powerOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        power3LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[2][2].getUnlockCost()),powerOffsetX + 228,powerOffsetY+8);
        this.addComponent(l);
        power3LockedComponents.add(l);
        
        // power4
        i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,powerOffsetX + 330,powerOffsetY,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        power4LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),powerOffsetX + 357,powerOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        power4LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[2][3].getUnlockCost()),powerOffsetX + 338,powerOffsetY +8);
        this.addComponent(l);
        power4LockedComponents.add(l);
        
        
        // tech1
        i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,techOffsetX ,techOffsetY,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        tech1LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),techOffsetX + 27,techOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        tech1LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[3][0].getUnlockCost()),techOffsetX + 8,techOffsetY+8);
        this.addComponent(l);
        tech1LockedComponents.add(l);
        
        // tech2
        i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,techOffsetX + 110 ,techOffsetY ,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        tech2LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),techOffsetX + 137,techOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        tech2LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[3][1].getUnlockCost()),techOffsetX + 118,techOffsetY+8);
        this.addComponent(l);
        tech2LockedComponents.add(l);
        
        // parimary3
        i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,techOffsetX + 220,techOffsetY,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        tech3LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),techOffsetX + 247,techOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        tech3LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[3][2].getUnlockCost()),techOffsetX + 228,techOffsetY+8);
        this.addComponent(l);
        tech3LockedComponents.add(l);
        
        // tech4
        i = new Image("dashIcon.png");
        i.setColor(new Color(0,0,0,.8f));
        b = new Button(i,techOffsetX + 330,techOffsetY,95,95);  
        b.dontKillClick = true;
        this.addComponent(b);
        tech4LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),techOffsetX + 357,techOffsetY+7,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        tech4LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[3][3].getUnlockCost()),techOffsetX + 338,techOffsetY +8);
        this.addComponent(l);
        tech4LockedComponents.add(l);
             
     
    }
    
    public void draw(GL2 gl)
    {
        super.draw(gl); 
        
        if(hand != null)
            hand.draw(gl);
      
    }
    
    public void update()
    {
        super.update();
        
        //===============
        //update locked
        //===============
        
        this.primaryLock[0] = !playerReference.getSkillManager().hasSkill(this.skillSlots[0][0].getSkillID());
        this.primaryLock[1] = !playerReference.getSkillManager().hasSkill(this.skillSlots[0][1].getSkillID());
        this.primaryLock[2] = !playerReference.getSkillManager().hasSkill(this.skillSlots[0][2].getSkillID());
        this.primaryLock[3] = !playerReference.getSkillManager().hasSkill(this.skillSlots[0][3].getSkillID());
        
        this.secondaryLock[0] = !playerReference.getSkillManager().hasSkill(this.skillSlots[1][0].getSkillID());
        this.secondaryLock[1] = !playerReference.getSkillManager().hasSkill(this.skillSlots[1][1].getSkillID());
        this.secondaryLock[2] = !playerReference.getSkillManager().hasSkill(this.skillSlots[1][2].getSkillID());
        this.secondaryLock[3] = !playerReference.getSkillManager().hasSkill(this.skillSlots[1][3].getSkillID());
        
        this.powerLock[0] = !playerReference.getSkillManager().hasSkill(this.skillSlots[2][0].getSkillID());
        this.powerLock[1] = !playerReference.getSkillManager().hasSkill(this.skillSlots[2][1].getSkillID());
        this.powerLock[2] = !playerReference.getSkillManager().hasSkill(this.skillSlots[2][2].getSkillID());
        this.powerLock[3] = !playerReference.getSkillManager().hasSkill(this.skillSlots[2][3].getSkillID());
        
        this.techLock[0] = !playerReference.getSkillManager().hasSkill(this.skillSlots[3][0].getSkillID());
        this.techLock[1] = !playerReference.getSkillManager().hasSkill(this.skillSlots[3][1].getSkillID());
        this.techLock[2] = !playerReference.getSkillManager().hasSkill(this.skillSlots[3][2].getSkillID());
        this.techLock[3] = !playerReference.getSkillManager().hasSkill(this.skillSlots[3][3].getSkillID());
        
        //==========================
        // Remove Locked Componenets
        //==========================
        
        if(this.primaryLock[0] == false)
        {
            for(WindowComponent componenet: this.primary1LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.primary1LockedComponents.clear();
        }
        if(this.primaryLock[1] == false)
        {
            for(WindowComponent componenet: this.primary2LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.primary2LockedComponents.clear();
        }
        if(this.primaryLock[2] == false)
        {
            for(WindowComponent componenet: this.primary3LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.primary3LockedComponents.clear();
        }
        if(this.primaryLock[3] == false)
        {
            for(WindowComponent componenet: this.primary4LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.primary4LockedComponents.clear();
        }
        
        if(this.secondaryLock[0] == false)
        {
            for(WindowComponent componenet: this.secondary1LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.secondary1LockedComponents.clear();
        }
        if(this.secondaryLock[1] == false)
        {
            for(WindowComponent componenet: this.secondary2LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.secondary2LockedComponents.clear();
        }
        if(this.secondaryLock[2] == false)
        {
            for(WindowComponent componenet: this.secondary3LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.secondary3LockedComponents.clear();
        }
        if(this.secondaryLock[3] == false)
        {
            for(WindowComponent componenet: this.secondary4LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.secondary4LockedComponents.clear();
        }
        
        if(this.powerLock[0] == false)
        {
            for(WindowComponent componenet: this.power1LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.power1LockedComponents.clear();
        }
        if(this.powerLock[1] == false)
        {
            for(WindowComponent componenet: this.power2LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.power2LockedComponents.clear();
        }
        if(this.powerLock[2] == false)
        {
            for(WindowComponent componenet: this.power3LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.power3LockedComponents.clear();
        }
        if(this.powerLock[3] == false)
        {
            for(WindowComponent componenet: this.power4LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.power4LockedComponents.clear();
        }
        
        if(this.techLock[0] == false)
        {
            for(WindowComponent componenet: this.tech1LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.tech1LockedComponents.clear();
        }
        if(this.techLock[1] == false)
        {
            for(WindowComponent componenet: this.tech2LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.tech2LockedComponents.clear();
        }
        if(this.techLock[2] == false)
        {
            for(WindowComponent componenet: this.tech3LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.tech3LockedComponents.clear();
        }
        if(this.techLock[3] == false)
        {
            for(WindowComponent componenet: this.tech4LockedComponents)
            {
                this.removeComponent(componenet);
            }
            this.tech4LockedComponents.clear();
        }
        
        
        
        //skill points
        this.skillPoints.getText().setText(Integer.toString(playerReference.getSkillManager().getSkillPoints()));
        
        if (hand != null)
        {
            hand.setDimensions(54, 54);
            hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
        }

    }
    
    public void close()
    {
         if(this.owningScene != null && this.isOpen)
        {
            Sound closeSound = Sound.ambientSound("buffered/menuClose.ogg", false);
            this.owningScene.add(closeSound);
        }
         
        super.close();
        
        this.hand = null;
        this.handSkillID = null;
       
    }
    
    
    private void openTooltip(Skill skill, float x, float y)
    {
        //remove old components
        this.removeComponent(skillTooltipBackground);
        this.removeComponent(skillTooltipName);
        this.removeComponent(skillTooltipIcon);
        this.removeComponent(skillTooltipTextBlock);
        
        //===============
        //build tooltip
        //===============
        
        //background
        Image i = new Image("tooltip.png");
        i.setColor(new Color(.6f,.6f,.6f)); 
        skillTooltipBackground = new Button(i, x, y, 400, 300);
        this.addComponent(skillTooltipBackground);
        
        //name
        Text text = new Text(skill.getSkillName(),LeadCrystalTextType.HUD34);
        skillTooltipName = new Label(text, x + 200 - text.getWidth()/2, y + 250);
        this.addComponent(skillTooltipName);
        
        //icon
        skillTooltipIcon = new Button(skill.getIcon().copy(),x + 25, y + 140,75,75);
        this.addComponent(skillTooltipIcon);
        
        //description
        text = new Text(skill.getSkillDescription());
        skillTooltipTextBlock = new TextBlock(x + 110, y + 190, 280, text);
        this.addComponent(skillTooltipTextBlock);
        
        
    }
    
    private void closeTooltip()
    {
        //remove old components
        this.removeComponent(skillTooltipBackground);
        this.removeComponent(skillTooltipName);
        this.removeComponent(skillTooltipIcon);
        this.removeComponent(skillTooltipTextBlock);
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


