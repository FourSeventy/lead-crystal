package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBlackBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBlueBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashBrownBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashGreenBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashRedBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashWhiteBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.BashYellowBodyAnimationPack;
import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorStat;
import com.silvergobletgames.leadcrystal.scenes.GameScene;
import com.silvergobletgames.leadcrystal.skills.Skill;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.InputSnapshot;
import com.silvergobletgames.sylver.graphics.AnimationPack;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Overlay;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.*;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.Window;
import com.silvergobletgames.sylver.windowsystem.WindowComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Mike
 */
public class ArmorMenu extends Window{
 
    PlayerEntity playerReference;
    
    private Label playerGoldLabel;
    
    //weapon
    private Button meleeAttackDamageBonusButton;
    private Button lifeLeechButton;   
    private Button criticalHitDamageButton;
    private Button weaponDamageButton;
    private Button weaponAttackSpeedButton;
    private Button critChanceButton;
    
    private Label meleeAttackDamageBonusLabel;
    private Label lifeLeechLabel;
    private Label criticalHitDamageLabel;
    private Label weaponDamageLabel;
    private Label weaponAttackSpeedLabel;
    private Label critChanceLabel;
    

    //helm upgrades
    private Button seeEnemyHealthButton;
    private Button doubleGoldFindButton;
    private Button upgradeRadarButton;
    private Button potionCooldownResetButton;//
    private Button healingEffectivenessButton;
    
    private Label seeEnemyHealthLabel;
    private Label doubleGoldFindLabel;
    private Label upgradeRadarLabel;
    private Label potionCooldownResetLabel;//
    private Label healingEffectivenessLabel;
    
    //body
    private Button hardToKillButton;
    private Button reducedCriticalHitButton;
    private Button proximityDamageReductionButton;
    private Button healthButton;
    private Button numberOfPotionsButton;
    private Button thornsDamageButton;
    
    private Label hardToKillLabel;
    private Label reducedCriticalHitLabel;
    private Label proximityDamageReductionLabel;
    private Label healthLabel;
    private Label numberOfPotionsLabel;
    private Label thornsDamageLabel;
       

    //boots
    private Button doubleJumpButton;
    private Button jetpackButton;
    private Button ccReductionButton;
    private Button moveSpeedButton;
    
    private Label doubleJumpLabel;
    private Label jetpackLabel;
    private Label ccReductionLabel;
    private Label moveSpeedLabel;
    
        
    
    //item hovered tooltip
    private Button skillTooltipBackground;
    private Label skillTooltipName;
    private Button skillTooltipIcon;
    private TextBlock skillTooltipTextBlock;
    private Label infoTextBox;
    private Label tooltipCost;
    private Button tooltipGold;
    
    
    
    public ArmorMenu( PlayerEntity player, float x, float y)
    {
        super(new Image("bigFrame.png"),x,y,1200,900);
        
        this.playerReference = player;
        
        //text
        Text menuText = new Text("Armor Upgrades",LeadCrystalTextType.HUD34);
        Label menuTextLabel = new Label(menuText,600 - menuText.getWidth()/2,825);
        this.addComponent(menuTextLabel);
        
        //close
        final Image closeImage = new Image("closeButton.png");
        Button closeButton = new Button(closeImage,1154,867,closeImage.getWidth()+1,closeImage.getHeight());
        closeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {

                  closeImage.setBrightness(1.5f);
                  Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND)); 
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
        
        //gold holder
        Image goldHolderImage = new Image("text_holder.png");
        Button goldHolder = new Button(goldHolderImage,1000,803,150,30);
        this.addComponent(goldHolder);
        
        //gold
        Text playerGoldDesc = new Text("Your Gold:",LeadCrystalTextType.HUD24);
        Label playerGoldDescLabel = new Label(playerGoldDesc,886,810);
        this.addComponent(playerGoldDescLabel);
        Text playerGold = new Text(Integer.toString(0),LeadCrystalTextType.HUD24);
        playerGoldLabel = new Label(playerGold,1070,810);
        this.addComponent(playerGoldLabel);
        Button b1 = new Button(new Image("goldCoinSmall.png"),1120,808,20,20);
        this.addComponent(b1);
        
        
        //========================
        // Basic Labels and Image
        //========================
       
        
        //Weapon Label
        Text weaponText = new Text("Weapon Upgrades",LeadCrystalTextType.HUD28);
        Label weaponLabel = new Label(weaponText, 275 - weaponText.getWidth()/2, 705);
        this.addComponent(weaponLabel);
        //Helm Label
        Text helmText = new Text("Helm Upgrades",LeadCrystalTextType.HUD28);
        Label helmLabel = new Label(helmText, 930 -helmText.getWidth()/2, 705);
        this.addComponent(helmLabel);
        //body Label
        Text bodyText = new Text("Body Upgrades",LeadCrystalTextType.HUD28);
        Label bodyLabel = new Label(bodyText, 275 -bodyText.getWidth()/2, 305);
        this.addComponent(bodyLabel);
        //boots Label
        Text bootsText = new Text("Boots Upgrades",LeadCrystalTextType.HUD28);
        Label bootsLabel = new Label(bootsText, 930 - helmText.getWidth()/2, 305);
        this.addComponent(bootsLabel);
        
        
//        //figure out head and body image
//        String bodyImageString = "";
//        AnimationPack playerAnimationPack = this.playerReference.getImage().getAnimationPack();
//        if(playerAnimationPack instanceof BashBrownBodyAnimationPack)
//        {
//            bodyImageString = "bash_brown_pose.png";
//        }
//        if(playerAnimationPack instanceof BashBlackBodyAnimationPack)
//        {
//            bodyImageString = "bash_black_pose.png";
//        }
//        if(playerAnimationPack instanceof BashBlueBodyAnimationPack)
//        {
//            bodyImageString = "bash_blue_pose.png";
//        }
//        if(playerAnimationPack instanceof BashGreenBodyAnimationPack)
//        {
//            bodyImageString = "bash_green_pose.png";
//        }
//        if(playerAnimationPack instanceof BashRedBodyAnimationPack)
//        {
//            bodyImageString = "bash_red_pose.png";
//        }
//        if(playerAnimationPack instanceof BashWhiteBodyAnimationPack)
//        {
//            bodyImageString = "bash_white_pose.png";
//        }
//        if(playerAnimationPack instanceof BashYellowBodyAnimationPack)
//        {
//            bodyImageString = "bash_yellow_pose.png";
//        }
//        
//        
//        //body image
//        Image bodyImg = new Image(bodyImageString);
//        bodyImg.setScale(1.35f);
//        Button bodyImage = new Button(bodyImg, 580 - (bodyImg.getWidth() * 1.35f)/2, 310, bodyImg.getWidth() * 1.35f, bodyImg.getHeight() * 1.35f);
//        this.addComponent(bodyImage);
//        
//        //head image
//        Image headImg = new Image(this.playerReference.getHead().getTextureReference());
//        headImg.setScale(1.35f);
//        Button headImage = new Button(headImg, 574 - (headImg.getWidth() * 1.35f)/2, 520, headImg.getWidth() * 1.35f, headImg.getHeight() * 1.35f);
//        this.addComponent(headImage);
        
        //=================
        // Weapon Upgrades
        //=================

        final int weaponBasePositionX = 128;
        final int weaponBasePositionY = 600;
        
        
        final ArmorStat stat1 = this.playerReference.getArmorManager().weaponDamage;
        Button b = new Button(stat1.image.copy(), weaponBasePositionX, weaponBasePositionY, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat1.id);
                    ArmorMenu.this.handleSoundAndErrors(stat1.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(weaponBasePositionX +95,weaponBasePositionY ,stat1.name,stat1.image,stat1.description, stat1.cost);
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND)); 
                   
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat1.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
               }
           }
       });
        this.addComponent(b);
        weaponDamageButton = b;
        this.weaponDamageLabel = new Label(new Text(Byte.toString(stat1.points) + "/" +Integer.toString(stat1.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y+8); 
        this.addComponent(weaponDamageLabel); 

        
        final ArmorStat stat2 = this.playerReference.getArmorManager().weaponAttackSpeed;
        b = new Button(stat2.image.copy(), weaponBasePositionX + 110, weaponBasePositionY, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat2.id);
                   ArmorMenu.this.handleSoundAndErrors(stat2.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND)); 
                   
                   openTooltip(weaponBasePositionX +195,weaponBasePositionY,stat2.name,stat2.image,stat2.description, stat2.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND)); 
                   
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat2.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        weaponAttackSpeedButton = b;
        this.weaponAttackSpeedLabel = new Label(new Text(Byte.toString(stat2.points) + "/" +Integer.toString(stat2.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y+8); 
        this.addComponent(weaponAttackSpeedLabel);
        
        final ArmorStat stat3 = this.playerReference.getArmorManager().critChance;
        b = new Button(stat3.image.copy(), weaponBasePositionX + 220, weaponBasePositionY, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat3.id);
                   ArmorMenu.this.handleSoundAndErrors(stat3.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND)); 
                   
                   openTooltip(weaponBasePositionX +295,weaponBasePositionY,stat3.name,stat3.image,stat3.description, stat3.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND)); 
                   
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat3.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        critChanceButton = b;
        this.critChanceLabel = new Label(new Text(Byte.toString(stat3.points) + "/" +Integer.toString(stat3.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y+8); 
        this.addComponent(critChanceLabel);
        
        final ArmorStat stat4 = this.playerReference.getArmorManager().meleeAttackDamageBonus;
        b = new Button(stat4.image.copy(), weaponBasePositionX, weaponBasePositionY - 110, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat4.id);
                   ArmorMenu.this.handleSoundAndErrors(stat4.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND)); 
                   
                   openTooltip(weaponBasePositionX +95,weaponBasePositionY - 100,stat4.name,stat4.image,stat4.description, stat4.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND)); 
                   
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat4.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        meleeAttackDamageBonusButton = b;
        this.meleeAttackDamageBonusLabel = new Label(new Text(Byte.toString(stat4.points) + "/" +Integer.toString(stat4.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y+8 ); 
        this.addComponent(meleeAttackDamageBonusLabel); 
        
        final ArmorStat stat8 = this.playerReference.getArmorManager().lifeLeech;
        b = new Button(stat8.image.copy(), weaponBasePositionX + 110, weaponBasePositionY - 110, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat8.id);
                   ArmorMenu.this.handleSoundAndErrors(stat8.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(weaponBasePositionX +205,weaponBasePositionY - 100,stat8.name,stat8.image,stat8.description, stat8.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat8.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        lifeLeechButton =b;
        this.lifeLeechLabel = new Label(new Text(Byte.toString(stat8.points) + "/" +Integer.toString(stat8.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y + 8); 
        this.addComponent(lifeLeechLabel);
        
        final ArmorStat stat6 = this.playerReference.getArmorManager().criticalHitDamage;
        b = new Button(stat6.image.copy(), weaponBasePositionX + 220, weaponBasePositionY - 110, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat6.id);
                   ArmorMenu.this.handleSoundAndErrors(stat6.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(weaponBasePositionX +295,weaponBasePositionY -100,stat6.name,stat6.image,stat6.description, stat6.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat6.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        this.criticalHitDamageButton = b;
        this.criticalHitDamageLabel = new Label(new Text(Byte.toString(stat6.points) + "/" +Integer.toString(stat6.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y+8 ); 
        this.addComponent(criticalHitDamageLabel); 
        
        
        
        //================
        // Helm Upgrades
        //================
        
        final int helmBasePositionX = 787;
        final int helmBasePositionY = 600;
       
        
        final ArmorStat stat7 = this.playerReference.getArmorManager().seeEnemyHealth;
        b = new Button(stat7.image.copy(), helmBasePositionX, helmBasePositionY, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat7.id);
                   ArmorMenu.this.handleSoundAndErrors(stat7.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(helmBasePositionX -425,helmBasePositionY ,stat7.name,stat7.image,stat7.description, stat7.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat7.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        seeEnemyHealthButton =b;
        this.seeEnemyHealthLabel = new Label(new Text(Byte.toString(stat7.points) + "/" +Integer.toString(stat7.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y + 8); 
        this.addComponent(seeEnemyHealthLabel); 
        
        
        ///// cooldown
        final ArmorStat stat5 = this.playerReference.getArmorManager().potionCooldownReset;
        b = new Button(stat5.image.copy(), helmBasePositionX + 110, helmBasePositionY, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat5.id);
                   ArmorMenu.this.handleSoundAndErrors(stat5.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(helmBasePositionX -325, helmBasePositionY ,stat5.name,stat5.image,stat5.description, stat5.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat5.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        potionCooldownResetButton = b;
        this.potionCooldownResetLabel = new Label(new Text(Byte.toString(stat5.points) + "/" +Integer.toString(stat5.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y+8 ); 
        this.addComponent(potionCooldownResetLabel); 
        
        
        
        final ArmorStat stat9 = this.playerReference.getArmorManager().upgradeRadar;
        b = new Button(stat9.image.copy(), helmBasePositionX + 220, helmBasePositionY, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat9.id);
                   ArmorMenu.this.handleSoundAndErrors(stat9.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(helmBasePositionX -225,helmBasePositionY,stat9.name,stat9.image,stat9.description, stat9.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat9.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        upgradeRadarButton = b;
        this.upgradeRadarLabel = new Label(new Text(Byte.toString(stat9.points) + "/" +Integer.toString(stat9.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y+ 8); 
        this.addComponent(upgradeRadarLabel);
        
        final ArmorStat stat10 = this.playerReference.getArmorManager().doubleGoldFind;
        b = new Button(stat10.image.copy(), helmBasePositionX, helmBasePositionY - 110, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat10.id);
                   ArmorMenu.this.handleSoundAndErrors(stat10.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(helmBasePositionX -425,helmBasePositionY-100,stat10.name,stat10.image,stat10.description, stat10.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat10.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        doubleGoldFindButton = b;
        this.doubleGoldFindLabel = new Label(new Text(Byte.toString(stat10.points) + "/" +Integer.toString(stat10.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y + 8); 
        this.addComponent(doubleGoldFindLabel); 
        
        final ArmorStat stat11 = this.playerReference.getArmorManager().healingEffectiveness;
        b = new Button(stat11.image.copy(), helmBasePositionX + 110, helmBasePositionY - 110, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat11.id);
                   ArmorMenu.this.handleSoundAndErrors(stat11.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(helmBasePositionX -325,helmBasePositionY-100,stat11.name,stat11.image,stat11.description, stat11.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat11.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        healingEffectivenessButton = b;
        this.healingEffectivenessLabel = new Label(new Text(Byte.toString(stat11.points) + "/" +Integer.toString(stat11.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y + 8); 
        this.addComponent(healingEffectivenessLabel); 

        
        //================
        // Armor Upgrades
        //================
        
        final int armorBasePositionX = 128;
        final int armorBasePositionY = 200;
               
        
        final ArmorStat stat12 = this.playerReference.getArmorManager().health;
        b = new Button(stat12.image.copy(), armorBasePositionX, armorBasePositionY, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat12.id);
                   ArmorMenu.this.handleSoundAndErrors(stat12.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(armorBasePositionX +95,armorBasePositionY,stat12.name,stat12.image,stat12.description, stat12.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat12.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        healthButton = b;
        this.healthLabel = new Label(new Text(Byte.toString(stat12.points) + "/" +Integer.toString(stat12.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 24,b.getWindowRelativePosition().y + 8); 
        this.addComponent(healthLabel); 
        
        final ArmorStat stat13 = this.playerReference.getArmorManager().numberOfPotions;
        b = new Button(stat13.image.copy(), armorBasePositionX + 110, armorBasePositionY, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat13.id);
                   ArmorMenu.this.handleSoundAndErrors(stat13.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(armorBasePositionX +195,armorBasePositionY,stat13.name,stat13.image,stat13.description, stat13.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat13.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        numberOfPotionsButton = b;
        this.numberOfPotionsLabel = new Label(new Text(Byte.toString(stat13.points) + "/" +Integer.toString(stat13.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y + 8); 
        this.addComponent(numberOfPotionsLabel);
        
        final ArmorStat stat14 = this.playerReference.getArmorManager().reducedCriticalHit;
        b = new Button(stat14.image.copy(), armorBasePositionX + 220, armorBasePositionY, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat14.id);
                   ArmorMenu.this.handleSoundAndErrors(stat14.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(armorBasePositionX +295,armorBasePositionY,stat14.name,stat14.image,stat14.description, stat14.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat14.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        reducedCriticalHitButton = b;
        this.reducedCriticalHitLabel = new Label(new Text(Byte.toString(stat14.points) + "/" +Integer.toString(stat14.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y + 8); 
        this.addComponent(reducedCriticalHitLabel);
        
        final ArmorStat stat15 = this.playerReference.getArmorManager().hardToKill;
        b = new Button(stat15.image.copy(), armorBasePositionX, armorBasePositionY - 110, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat15.id);
                   ArmorMenu.this.handleSoundAndErrors(stat15.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(armorBasePositionX +95,armorBasePositionY-100,stat15.name,stat15.image,stat15.description, stat15.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat15.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        hardToKillButton = b;
        this.hardToKillLabel = new Label(new Text(Byte.toString(stat15.points) + "/" +Integer.toString(stat15.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y + 8 ); 
        this.addComponent(hardToKillLabel); 
        
        final ArmorStat stat16 = this.playerReference.getArmorManager().proximityDamageReduction;
        b = new Button(stat16.image.copy(), armorBasePositionX + 110, armorBasePositionY - 110, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat16.id);
                   ArmorMenu.this.handleSoundAndErrors(stat16.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(armorBasePositionX +195,armorBasePositionY-100,stat16.name,stat16.image,stat16.description, stat16.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat16.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        proximityDamageReductionButton = b;
        this.proximityDamageReductionLabel = new Label(new Text(Byte.toString(stat16.points) + "/" +Integer.toString(stat16.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y + 8 ); 
        this.addComponent(proximityDamageReductionLabel); 
        
        final ArmorStat stat17 = this.playerReference.getArmorManager().thornsDamage;
        b = new Button(stat17.image.copy(), armorBasePositionX + 220, armorBasePositionY - 110, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat17.id);
                   ArmorMenu.this.handleSoundAndErrors(stat17.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(armorBasePositionX +295,armorBasePositionY-100,stat17.name,stat17.image,stat17.description, stat17.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat17.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        thornsDamageButton = b;
        this.thornsDamageLabel = new Label(new Text(Byte.toString(stat17.points) + "/" +Integer.toString(stat17.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y + 8); 
        this.addComponent(thornsDamageLabel); 
        
        
        //================
        // Boot Upgrades
        //================
              
        final int bootsBasePositionX = 787;
        final int bootsBasePositionY = 200;
               
        
        final ArmorStat stat18 = this.playerReference.getArmorManager().doubleJump;
        b = new Button(stat18.image.copy(), bootsBasePositionX, bootsBasePositionY, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat18.id);
                   ArmorMenu.this.handleSoundAndErrors(stat18.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(bootsBasePositionX -425,bootsBasePositionY ,stat18.name,stat18.image,stat18.description, stat18.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat18.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        doubleJumpButton = b;
        this.doubleJumpLabel = new Label(new Text(Byte.toString(stat18.points) + "/" +Integer.toString(stat18.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y+ 8); 
        this.addComponent(doubleJumpLabel); 
        
        final ArmorStat stat19 = this.playerReference.getArmorManager().ccReduction;
        b = new Button(stat19.image.copy(), bootsBasePositionX + 110, bootsBasePositionY, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat19.id);
                   ArmorMenu.this.handleSoundAndErrors(stat19.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(bootsBasePositionX -325,bootsBasePositionY ,stat19.name,stat19.image,stat19.description, stat19.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat19.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        ccReductionButton = b;
        this.ccReductionLabel = new Label(new Text(Byte.toString(stat19.points) + "/" +Integer.toString(stat19.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y+ 8); 
        this.addComponent(ccReductionLabel);
        
        final ArmorStat stat20 = this.playerReference.getArmorManager().jetpack;
        b = new Button(stat20.image.copy(), bootsBasePositionX + 220, bootsBasePositionY, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat20.id);
                   ArmorMenu.this.handleSoundAndErrors(stat20.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(bootsBasePositionX -225,bootsBasePositionY ,stat20.name,stat20.image,stat20.description, stat20.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat20.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        jetpackButton = b;
        this.jetpackLabel = new Label(new Text(Byte.toString(stat20.points) + "/" +Integer.toString(stat20.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y+ 8); 
        this.addComponent(jetpackLabel);
        
        final ArmorStat stat21 = this.playerReference.getArmorManager().moveSpeed;
        b = new Button(stat21.image.copy(), bootsBasePositionX, bootsBasePositionY - 110, 85, 85);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameScene)owningScene).buyStat(stat21.id);
                   ArmorMenu.this.handleSoundAndErrors(stat21.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND));
                   openTooltip(bootsBasePositionX -425,bootsBasePositionY -100,stat21.name,stat21.image,stat21.description, stat21.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND));
                   if(ArmorMenu.this.skillTooltipIcon.getImage().getTextureReference().equals(stat21.image.getTextureReference()))
                   {
                      closeTooltip();
                   }
               }
           }
       });
        this.addComponent(b);
        moveSpeedButton = b;
        this.moveSpeedLabel = new Label(new Text(Byte.toString(stat21.points) + "/" +Integer.toString(stat21.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y + 8 ); 
        this.addComponent(moveSpeedLabel); 

       
    }
    
    @Override
    public void update()
    {
        super.update();
        
        //update gold
        this.playerGoldLabel.getText().setText(Integer.toString(this.playerReference.getCurrencyManager().getBalence()));
        
        InputSnapshot input = Game.getInstance().getInputHandler().getInputSnapshot();
        
        
        //================================
        // Check for Weapon Button Changes
        //=================================

        if(this.playerReference.getArmorManager().meleeAttackDamageBonus.unlocked == false)
        {
            if(!meleeAttackDamageBonusButton.getImage().hasOverlay("lock"))
            {
               meleeAttackDamageBonusButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            meleeAttackDamageBonusButton.setDisabled(true);
            meleeAttackDamageBonusLabel.setHidden(true);
        }
        else
        {

            meleeAttackDamageBonusButton.getImage().removeAllOverlays();
            meleeAttackDamageBonusButton.setDisabled(false);
            meleeAttackDamageBonusLabel.setHidden(false);        
            meleeAttackDamageBonusLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().meleeAttackDamageBonus.points) + "/" +Integer.toString(this.playerReference.getArmorManager().meleeAttackDamageBonus.maxPoints)); 

        }

        if(this.playerReference.getArmorManager().potionCooldownReset.unlocked == false)
        {
            if(!potionCooldownResetButton.getImage().hasOverlay("lock"))
            {
               potionCooldownResetButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            potionCooldownResetButton.setDisabled(true);
            potionCooldownResetLabel.setHidden(true);
        }
        else
        {
            potionCooldownResetButton.getImage().removeAllOverlays();
            potionCooldownResetButton.setDisabled(false);
            potionCooldownResetLabel.setHidden(false);
            potionCooldownResetLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().potionCooldownReset.points) + "/" +Integer.toString(this.playerReference.getArmorManager().potionCooldownReset.maxPoints)); 

        }


        if(this.playerReference.getArmorManager().criticalHitDamage.unlocked == false)
        {
            if(!criticalHitDamageButton.getImage().hasOverlay("lock"))
            {
               criticalHitDamageButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            criticalHitDamageButton.setDisabled(true);
            criticalHitDamageLabel.setHidden(true);
        }
        else
        {
            criticalHitDamageButton.getImage().removeAllOverlays();
            criticalHitDamageButton.setDisabled(false);
            criticalHitDamageLabel.setHidden(false);
            criticalHitDamageLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().criticalHitDamage.points) + "/" +Integer.toString(this.playerReference.getArmorManager().criticalHitDamage.maxPoints)); 

        }

        if(this.playerReference.getArmorManager().weaponDamage.unlocked == false)
        {
            if(!weaponDamageButton.getImage().hasOverlay("lock"))
            {
               weaponDamageButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            weaponDamageButton.setDisabled(true);
            weaponDamageLabel.setHidden(true);
        }
        else
        {
            weaponDamageButton.getImage().removeAllOverlays();
            weaponDamageButton.setDisabled(false);
            weaponDamageLabel.setHidden(false);
            weaponDamageLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().weaponDamage.points) + "/" +Integer.toString(this.playerReference.getArmorManager().weaponDamage.maxPoints)); 

        }

        if(this.playerReference.getArmorManager().weaponAttackSpeed.unlocked == false)
        {
            if(!weaponAttackSpeedButton.getImage().hasOverlay("lock"))
            {
               weaponAttackSpeedButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            weaponAttackSpeedButton.setDisabled(true);
            weaponAttackSpeedLabel.setHidden(true);
        }
        else
        {
            weaponAttackSpeedButton.getImage().removeAllOverlays();
            weaponAttackSpeedButton.setDisabled(false);
            weaponAttackSpeedLabel.setHidden(false);
            weaponAttackSpeedLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().weaponAttackSpeed.points) + "/" +Integer.toString(this.playerReference.getArmorManager().weaponAttackSpeed.maxPoints)); 

        }

        if(this.playerReference.getArmorManager().critChance.unlocked == false)
        {
            if(!critChanceButton.getImage().hasOverlay("lock"))
            {
               critChanceButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            critChanceButton.setDisabled(true);
            critChanceLabel.setHidden(true);
        }
        else
        {
            critChanceButton.getImage().removeAllOverlays();
            critChanceButton.setDisabled(false);
            critChanceLabel.setHidden(false);
            critChanceLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().critChance.points) + "/" +Integer.toString(this.playerReference.getArmorManager().critChance.maxPoints)); 

        }

          //================================
          // Check for Helm Button Changes
          //=================================

        if(this.playerReference.getArmorManager().seeEnemyHealth.unlocked == false)
        {
            if(!seeEnemyHealthButton.getImage().hasOverlay("lock"))
            {
               seeEnemyHealthButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            seeEnemyHealthButton.setDisabled(true);
            seeEnemyHealthLabel.setHidden(true);
        }
        else 
        {
            seeEnemyHealthButton.getImage().removeAllOverlays();
            seeEnemyHealthButton.setDisabled(false);
            seeEnemyHealthLabel.setHidden(false);        
            seeEnemyHealthLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().seeEnemyHealth.points) + "/" +Integer.toString(this.playerReference.getArmorManager().seeEnemyHealth.maxPoints)); 

        }

        if(this.playerReference.getArmorManager().doubleGoldFind.unlocked == false)
        {
            if(!doubleGoldFindButton.getImage().hasOverlay("lock"))
            {
               doubleGoldFindButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            doubleGoldFindButton.setDisabled(true);
            doubleGoldFindLabel.setHidden(true);
        }
        else
        {
            doubleGoldFindButton.getImage().removeAllOverlays();
            doubleGoldFindButton.setDisabled(false);
            doubleGoldFindLabel.setHidden(false);
            doubleGoldFindLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().doubleGoldFind.points) + "/" +Integer.toString(this.playerReference.getArmorManager().doubleGoldFind.maxPoints)); 

        }


        if(this.playerReference.getArmorManager().upgradeRadar.unlocked == false)
        {
            if(!upgradeRadarButton.getImage().hasOverlay("lock"))
            {
               upgradeRadarButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            upgradeRadarButton.setDisabled(true);
            upgradeRadarLabel.setHidden(true);
        }
        else
        {
            upgradeRadarButton.getImage().removeAllOverlays();
            upgradeRadarButton.setDisabled(false);
            upgradeRadarLabel.setHidden(false);
            upgradeRadarLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().upgradeRadar.points) + "/" +Integer.toString(this.playerReference.getArmorManager().upgradeRadar.maxPoints)); 

        }

        if(this.playerReference.getArmorManager().lifeLeech.unlocked == false)
        {
            if(!lifeLeechButton.getImage().hasOverlay("lock"))
            {
               lifeLeechButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            lifeLeechButton.setDisabled(true);
            lifeLeechLabel.setHidden(true);
        }
        else
        {
            lifeLeechButton.getImage().removeAllOverlays();
            lifeLeechButton.setDisabled(false);
            lifeLeechLabel.setHidden(false);
            lifeLeechLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().lifeLeech.points) + "/" +Integer.toString(this.playerReference.getArmorManager().lifeLeech.maxPoints)); 

        }

        if(this.playerReference.getArmorManager().healingEffectiveness.unlocked == false)
        {
            if(!healingEffectivenessButton.getImage().hasOverlay("lock"))
            {
               healingEffectivenessButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            healingEffectivenessButton.setDisabled(true);
            healingEffectivenessLabel.setHidden(true);
        }
        else
        {
            healingEffectivenessButton.getImage().removeAllOverlays();
            healingEffectivenessButton.setDisabled(false);
            healingEffectivenessLabel.setHidden(false);
            healingEffectivenessLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().healingEffectiveness.points) + "/" +Integer.toString(this.playerReference.getArmorManager().healingEffectiveness.maxPoints)); 

        }

           //================================
          // Check for Body Button Changes
          //=================================

        if(this.playerReference.getArmorManager().hardToKill.unlocked == false)
        {
            if(!hardToKillButton.getImage().hasOverlay("lock"))
            {
               hardToKillButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            hardToKillButton.setDisabled(true);
            hardToKillLabel.setHidden(true);
        }
        else
        {
            hardToKillButton.getImage().removeAllOverlays();
            hardToKillButton.setDisabled(false);
            hardToKillLabel.setHidden(false);        
            hardToKillLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().hardToKill.points) + "/" +Integer.toString(this.playerReference.getArmorManager().hardToKill.maxPoints)); 

        }

        if(this.playerReference.getArmorManager().reducedCriticalHit.unlocked == false)
        {       
            if(!reducedCriticalHitButton.getImage().hasOverlay("lock"))
            {
               reducedCriticalHitButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            reducedCriticalHitButton.setDisabled(true);
            reducedCriticalHitLabel.setHidden(true);
        }
        else
        {
            reducedCriticalHitButton.getImage().removeAllOverlays();
            reducedCriticalHitButton.setDisabled(false);
            reducedCriticalHitLabel.setHidden(false);
            reducedCriticalHitLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().reducedCriticalHit.points) + "/" +Integer.toString(this.playerReference.getArmorManager().reducedCriticalHit.maxPoints)); 

        }


        if(this.playerReference.getArmorManager().proximityDamageReduction.unlocked == false)
        {
            if(!proximityDamageReductionButton.getImage().hasOverlay("lock"))
            {
               proximityDamageReductionButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            proximityDamageReductionButton.setDisabled(true);
            proximityDamageReductionLabel.setHidden(true);
        }
        else
        {
            proximityDamageReductionButton.getImage().removeAllOverlays();
            proximityDamageReductionButton.setDisabled(false);
            proximityDamageReductionLabel.setHidden(false);
            proximityDamageReductionLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().proximityDamageReduction.points) + "/" +Integer.toString(this.playerReference.getArmorManager().proximityDamageReduction.maxPoints)); 

        }

        if(this.playerReference.getArmorManager().health.unlocked == false)
        {
            if(!healthButton.getImage().hasOverlay("lock"))
            {
               healthButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            healthButton.setDisabled(true);
            healthLabel.setHidden(true);
        }
        else
        {
            healthButton.getImage().removeAllOverlays();
            healthButton.setDisabled(false);
            healthLabel.setHidden(false);
            healthLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().health.points) + "/" +Integer.toString(this.playerReference.getArmorManager().health.maxPoints)); 

        }

        if(this.playerReference.getArmorManager().numberOfPotions.unlocked == false)
        {
            if(!numberOfPotionsButton.getImage().hasOverlay("lock"))
            {
               numberOfPotionsButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            numberOfPotionsButton.setDisabled(true);
            numberOfPotionsLabel.setHidden(true);
        }
        else
        {
            numberOfPotionsButton.getImage().removeAllOverlays();
            numberOfPotionsButton.setDisabled(false);
            numberOfPotionsLabel.setHidden(false);
            numberOfPotionsLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().numberOfPotions.points) + "/" +Integer.toString(this.playerReference.getArmorManager().numberOfPotions.maxPoints)); 

        }

        if(this.playerReference.getArmorManager().thornsDamage.unlocked == false)
        {
            if(!thornsDamageButton.getImage().hasOverlay("lock"))
            {
               thornsDamageButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            thornsDamageButton.setDisabled(true);
            thornsDamageLabel.setHidden(true);
        }
        else
        {
            thornsDamageButton.getImage().removeAllOverlays();
            thornsDamageButton.setDisabled(false);
            thornsDamageLabel.setHidden(false);
            thornsDamageLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().thornsDamage.points) + "/" +Integer.toString(this.playerReference.getArmorManager().thornsDamage.maxPoints)); 

        }

          //================================
          // Check for Boots Button Changes
          //=================================

        if(this.playerReference.getArmorManager().doubleJump.unlocked == false)
        {
            if(!doubleJumpButton.getImage().hasOverlay("lock"))
            {
               doubleJumpButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            doubleJumpButton.setDisabled(true);
            doubleJumpLabel.setHidden(true);
        }
        else 
        {
            doubleJumpButton.getImage().removeAllOverlays();
            doubleJumpButton.setDisabled(false);
            doubleJumpLabel.setHidden(false);        
            doubleJumpLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().doubleJump.points) + "/" +Integer.toString(this.playerReference.getArmorManager().doubleJump.maxPoints)); 

        }

        if(this.playerReference.getArmorManager().jetpack.unlocked == false)
        {
            if(!jetpackButton.getImage().hasOverlay("lock"))
            {
               jetpackButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            jetpackButton.setDisabled(true);
            jetpackLabel.setHidden(true);
        }
        else
        {
            jetpackButton.getImage().removeAllOverlays();
            jetpackButton.setDisabled(false);
            jetpackLabel.setHidden(false);
            jetpackLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().jetpack.points) + "/" +Integer.toString(this.playerReference.getArmorManager().jetpack.maxPoints)); 

        }


        if(this.playerReference.getArmorManager().ccReduction.unlocked == false)
        {
            if(!ccReductionButton.getImage().hasOverlay("lock"))
            {
               ccReductionButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            ccReductionButton.setDisabled(true);
            ccReductionLabel.setHidden(true);
        }
        else
        {
            ccReductionButton.getImage().removeAllOverlays();
            ccReductionButton.setDisabled(false);
            ccReductionLabel.setHidden(false);
            ccReductionLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().ccReduction.points) + "/" +Integer.toString(this.playerReference.getArmorManager().ccReduction.maxPoints)); 

        }

        if(this.playerReference.getArmorManager().moveSpeed.unlocked == false)
        {
            if(!moveSpeedButton.getImage().hasOverlay("lock"))
            {
               moveSpeedButton.getImage().addOverlay("lock",new Overlay(new Image("armor_lock.png")));
            }
            moveSpeedButton.setDisabled(true);
            moveSpeedLabel.setHidden(true);
        }
        else
        {
            moveSpeedButton.getImage().removeAllOverlays();
            moveSpeedButton.setDisabled(false);
            moveSpeedLabel.setHidden(false);
            moveSpeedLabel.getText().setText(Byte.toString(this.playerReference.getArmorManager().moveSpeed.points) + "/" +Integer.toString(this.playerReference.getArmorManager().moveSpeed.maxPoints)); 

        }

         
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
        
        ((GameScene)this.getOwningScene()).getHud().closeDialogue();
       
    }
    
    public void handleSoundAndErrors(ArmorStat.ArmorStatID id)
    {
        ArmorStat stat = this.playerReference.getArmorManager().armorStatLookup(id);
        int currentGold = this.playerReference.getCurrencyManager().getBalence();
        
      
        //check for max points
        if(!stat.unlocked || stat.isMaxPoints())
        {
            //play error sound
            Sound errorSound = Sound.ambientSound("buffered/error.ogg",false);               
            ArmorMenu.this.getOwningScene().add(errorSound);
            
            //show error message
            return;
        }
        
        //check for enough gold
        if(currentGold < stat.cost)
        {
            //play error sound
            Sound errorSound = Sound.ambientSound("buffered/error.ogg",false);               
            ArmorMenu.this.getOwningScene().add(errorSound);
            
            //show error message
            
            return;
        }

        
        //play purchase sound
        Sound goldSound = Sound.ambientSound("buffered/coins.ogg",false);               
        ArmorMenu.this.getOwningScene().add(goldSound);
        
          
    }
    
    
    private void openTooltip( float x, float y, String name, Image image, String description, Integer cost)
    {
        //remove old components
        this.removeComponent(skillTooltipBackground);
        this.removeComponent(skillTooltipName);
        this.removeComponent(skillTooltipIcon);
        this.removeComponent(skillTooltipTextBlock);
        this.removeComponent(this.infoTextBox);
        this.removeComponent(this.tooltipCost);
        this.removeComponent(this.tooltipGold);
        
        //===============
        //build tooltip
        //===============
        
        //background
        Image i = new Image("tooltip.png");
        i.setColor(new Color(.6f,.6f,.6f)); 
        skillTooltipBackground = new Button(i, x, y, 400, 300);
        this.addComponent(skillTooltipBackground);
        
        //name
        Text text = new Text(name,LeadCrystalTextType.HUD34);
        skillTooltipName = new Label(text, x + 200 - text.getWidth()/2, y + 250);
        this.addComponent(skillTooltipName);
        
        //icon
        skillTooltipIcon = new Button(image.copy(),x + 25, y + 140,75,75);
        this.addComponent(skillTooltipIcon);
        
        //description
        text = new Text(description,LeadCrystalTextType.HUD24);
        skillTooltipTextBlock = new TextBlock(x + 110, y + 190, 280, text);
        this.addComponent(skillTooltipTextBlock);    
        
        //cost
        if(cost != null)
        {
            text = new Text("Cost: " + cost.toString(),LeadCrystalTextType.HUD24);
            tooltipCost = new Label(text, x + 195 - text.getWidth()/2, y + 40);
            this.addComponent(tooltipCost);
            
            tooltipGold = new Button(new Image("goldCoinWikidSmall.png"),x + 195 + text.getWidth()/2 + 5,y + 38,20,20);
            this.addComponent(tooltipGold);
        }
        
    }
    
    private void closeTooltip()
    {
        //remove old components
        this.removeComponent(skillTooltipBackground);
        this.removeComponent(skillTooltipName);
        this.removeComponent(skillTooltipIcon);
        this.removeComponent(skillTooltipTextBlock);
        this.removeComponent(infoTextBox);
        this.removeComponent(this.tooltipCost);
        this.removeComponent(this.tooltipGold);
    }
}
