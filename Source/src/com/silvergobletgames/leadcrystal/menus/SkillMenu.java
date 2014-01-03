
package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.skills.PlayerFreezeAttack;
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
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.*;
import java.util.ArrayList;
import javax.media.opengl.GL2;


/**
 * Justin edit:
 *
 * When an item in the inventory grid is clicked on, it becomes 'attached'
 * to the cursor. When the item becomes attached to the cursor it is no longer associated
 * in the grid space it use to occupy. Whether the item is
 * 'attached' to the cursor or not determines whether or not it will be drawn
 * in its grid location, or at the cursor coordinates.
 *
 * When an item is held, clicking an empty slot in the grid will cause the swap
 * method to be called, and null will be swapped with an item.
 *
 * Clicking a spot with an item will swap the items
 *
 * Clicking the spot that the item originally came from will put it back.
 *
 * Clicking outside the menu will cause the item to be dropped.
 *
 * Clicking inside an equipment slot will cause a check as to whether the item
 * can be equipped, and if so, it will be swapped with whatever is in that
 * equipment slot.
 *
 * @author mike
 */
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
       super(new Image("SkillScreen.png"),x,y,550,800);
       
       //get new player referene
       playerReference =player;
        
       //skill points
       String points = Integer.toString(player.getSkillManager().getSkillPoints());
       this.skillPoints = new Label(points,165,745);
       this.addComponent(this.skillPoints);
       
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
       
       //================== Primary ========================//
        
       //laser shot
       final Skill skill = this.skillSlots[0][0];
       Button b = new Button(skill.getIcon(),100,575,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill,-50,275);
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
       b = new Button(skill5.getIcon(),200,575,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill5,50,275);
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
       b = new Button(skill7.getIcon(),300,575,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill7,150,275);
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
       b = new Button(blade.getIcon(),400,575,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(blade,170,275);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(b);
       
       // =================== Secondary ========================//
       //bash
       final Skill skill1 = this.skillSlots[1][0];
       b = new Button(skill1.getIcon(),102,393,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill1,-50,90);
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
       b = new Button(skill3.getIcon(),202,393,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill3,50,90);
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
       b = new Button(freezeSkill.getIcon(),302,393,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(freezeSkill,150,90);
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
       b = new Button(snipeSkill.getIcon(),402,393,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(snipeSkill,150,90);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(b);
       
       //=================== Power ==========================//
       
       //stomp attack
       final Skill skill6 = this.skillSlots[2][0];
       b = new Button(skill6.getIcon(),100,220,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill6,-50,300);
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
       b = new Button(flashbangSkill.getIcon(),200,220,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(flashbangSkill,50,300);
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
       b = new Button(stimSkill.getIcon(),300,220,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(stimSkill,150,300);
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
       b = new Button(gravitySkill.getIcon(),400,220,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(gravitySkill,150,300);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
           
       });
       this.addComponent(b);
       
       //=================== Tech =========================
       
       //attack drone
       final Skill skill2 = this.skillSlots[3][0];
       b = new Button(skill2.getIcon(),100,53,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill2,-50,140);
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
       b = new Button(skill4.getIcon(),200,53,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(skill4,50,140);
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
       b = new Button(playerGuardSkill.getIcon(),300,53,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(playerGuardSkill,150,140);
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
       b = new Button(playerFlashbangSkill.getIcon(),400,53,60,70);
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
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(playerFlashbangSkill,150,140);
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
        Image i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,100,575,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        primary1LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),110,575,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        primary1LockedComponents.add(b);
        Label l = new Label(Integer.toString(this.skillSlots[0][0].getUnlockCost()),135,555);
        this.addComponent(l);
        primary1LockedComponents.add(l);
        
        // primary2
        i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,200,575,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        primary2LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),210,575,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        primary2LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[0][1].getUnlockCost()),235,575);
        this.addComponent(l);
        primary2LockedComponents.add(l);
        
        // parimary3
        i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,300,575,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        primary3LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),310,575,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        primary3LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[0][2].getUnlockCost()),335,575);
        this.addComponent(l);
        primary3LockedComponents.add(l);
        
        // primary4
        i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,400,575,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        primary4LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),410,575,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        primary4LockedComponents.add(b);
        l = new Label(Integer.toString(this.skillSlots[0][3].getUnlockCost()),435,575);
        this.addComponent(l);
        primary4LockedComponents.add(l);
        
        
        // secondary1
        i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,100,393,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        secondary1LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),110,393,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        secondary1LockedComponents.add(b);
         l = new Label(Integer.toString(this.skillSlots[1][0].getUnlockCost()),135,393);
        this.addComponent(l);
        secondary1LockedComponents.add(l);
        
        // secondary2
        i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,200,393,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        secondary2LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),210,393,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        secondary2LockedComponents.add(b);
         l = new Label(Integer.toString(this.skillSlots[1][1].getUnlockCost()),235,393);
        this.addComponent(l);
        secondary2LockedComponents.add(l);
        
        // secondary3
        i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,300,393,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        secondary3LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),310,393,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        secondary3LockedComponents.add(b);
         l = new Label(Integer.toString(this.skillSlots[1][2].getUnlockCost()),335,393);
        this.addComponent(l);
        secondary3LockedComponents.add(l);
        
        // secondary4
        i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,400,393,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        secondary4LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),410,393,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        secondary4LockedComponents.add(b);
         l = new Label(Integer.toString(this.skillSlots[1][3].getUnlockCost()),435,393);
        this.addComponent(l);
        secondary4LockedComponents.add(l);
        
        
        // power1
        i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,100,220,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        power1LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),110,220,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        power1LockedComponents.add(b);
         l = new Label(Integer.toString(this.skillSlots[2][0].getUnlockCost()),135,220);
        this.addComponent(l);
        power1LockedComponents.add(l);
        
        // power2
        i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,200,220,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        power2LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),210,220,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        power2LockedComponents.add(b);
         l = new Label(Integer.toString(this.skillSlots[2][1].getUnlockCost()),235,220);
        this.addComponent(l);
        power2LockedComponents.add(l);
        
        // power3
        i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,300,220,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        power3LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),310,220,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        power3LockedComponents.add(b);
         l = new Label(Integer.toString(this.skillSlots[2][2].getUnlockCost()),335,220);
        this.addComponent(l);
        power3LockedComponents.add(l);
        
        // tech4
        i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,400,220,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        power4LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),410,220,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        power4LockedComponents.add(b);
         l = new Label(Integer.toString(this.skillSlots[2][3].getUnlockCost()),435,220);
        this.addComponent(l);
        power4LockedComponents.add(l);
        
        
        // tech1
        i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,100,53,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        tech1LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),110,53,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        tech1LockedComponents.add(b);
         l = new Label(Integer.toString(this.skillSlots[3][0].getUnlockCost()),135,53);
        this.addComponent(l);
        tech1LockedComponents.add(l);
        
        // tech2
        i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,200,53,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        tech2LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),210,53,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        tech2LockedComponents.add(b);
         l = new Label(Integer.toString(this.skillSlots[3][1].getUnlockCost()),235,53);
        this.addComponent(l);
        tech2LockedComponents.add(l);
        
        // tech3
        i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,300,53,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        tech3LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),310,53,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        tech3LockedComponents.add(b);
         l = new Label(Integer.toString(this.skillSlots[3][2].getUnlockCost()),335,53);
        this.addComponent(l);
        tech3LockedComponents.add(l);
        
        // tech4
        i = new Image("black.png");
        i.setColor(new Color(1,1,1,.8f));
        b = new Button(i,400,53,60,70);  
        b.dontKillClick = true;
        this.addComponent(b);
        tech4LockedComponents.add(b);
        b = new Button(new Image("skillPoint.png"),410,53,20,20);
        b.dontKillClick = true;
        this.addComponent(b);
        tech4LockedComponents.add(b);
         l = new Label(Integer.toString(this.skillSlots[3][3].getUnlockCost()),435,53);
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
            hand.setDimensions(40, 40);
            hand.setPositionAnchored(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);
        }

    }
    
    public void close()
    {
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
        skillTooltipBackground = new Button(new Image("tooltip.jpg"), x, y, 400, 300);
        this.addComponent(skillTooltipBackground);
        
        //name
        Text text = new Text(skill.getSkillName());
        text.setScale(1.3f);
        skillTooltipName = new Label(text, x + 200 - text.getWidth()/2, y + 250);
        this.addComponent(skillTooltipName);
        
        //icon
        skillTooltipIcon = new Button(skill.getIcon().copy(),x + 25, y + 160,50,50);
        this.addComponent(skillTooltipIcon);
        
        //description
        text = new Text(skill.getSkillDescription());
        skillTooltipTextBlock = new TextBlock(x + 85, y + 190, 300, text);
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

}


