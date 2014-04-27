package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorModifier;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorStat;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.leadcrystal.skills.Skill;
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
    
    private TabPane tabPane;
    
    //item hovered tooltip
    private Button skillTooltipBackground;
    private Label skillTooltipName;
    private Button skillTooltipIcon;
    private TextBlock skillTooltipTextBlock;
    private Label infoTextBox;
    private Label tooltipCost;
    
    //==============
    // stat labels
    //==============
    
    private Text lifeLeechText;
    private Text numberOfPotionsText;
    private Text healingEffectivenessStatText;
    
    private Text bodyHealthStatText;
    private Text bodyDamageReductionStatText;
    private Text thornsStatText;

    private Text weaponDamageStatText;
    private Text weaponAttackSpeedStatText;
    private Text critChanceStatText;
    
    private Text ccReductionStatText;
    private Text moveSpeedStatText;
    private Text jumpHeightStatText;
    
    
    //================
    // Detail Section
    //================
    

    private Text lifeLeechDetailText;
    private Text lifeRegenDetailText;
    private Text healingEffectivenessDetailText;
    private Text helmModifierDetailText;
    
    private Text bodyHealthDetailText;
    private Text bodyDamageReductionDetailText;
    private Text bodyThornsDamageDetailText;
    private Text bodyModifierDetailText;
    
    private Text weaponDamageDetailText;
    private Text weaponAttackSpeedDetailText;
    private Text critChanceDetailText;
    private Text weaponModifierDetailText;
     
    private Text ccReductionDetailText;
    private Text moveSpeedDetailText;
    private Text jumpHeightDetailText;
    private Text bootsModifierDetailText;
    
    
    //==============
    // Modifier
    //==============
    private Button seeEnemyHealthModifier;
    private Button doubleGoldFindModifier;
    private Button upgradedRadarModifier;
    
    private Button toughToKillModifier;
    private Button noCritsModifier;
    private Button increasedThornsModifier; 
    
    private Button meleeAttackDamageModifier;
    private Button potionResetModifier;
    private Button criticalHitDamageModifier;

    private Button doubleJumpModifier;
    private Button jetpackModifier;
    private Button teleportModifier;
    
    
  
    
    
    
    public ArmorMenu( PlayerEntity player, float x, float y)
    {
        super(new Image("armorMenu.png"),x,y,1200,900);
        
        this.playerReference = player;
        
        this.tabPane = new TabPane(50,75,1100,700);
        this.tabPane.addTab("Helm");
        this.tabPane.addTab("Body");
        this.tabPane.addTab("Weapon");
        this.tabPane.addTab("Boots");
        this.addComponent(tabPane);
        
        
        int statBaseX = 600;
        int statBaseY = 150;
        
        int modifierBaseX = 200;
        int modifierBaseY = 150;
        
        //======================
        // Helm Stat Components
        //======================
        this.tabPane.addComponent(new Button(new Text("Upgrades:"), statBaseX, statBaseY + 120), 0);
        
        final ArmorStat stat7 = this.playerReference.getArmorManager().lifeLeech;
        Button b = new Button(stat7.image.copy(), statBaseX, statBaseY, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat7.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat7.name,stat7.image,stat7.description, stat7.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b, 0);
        this.lifeLeechText = new Text(Byte.toString(stat7.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.lifeLeechText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.lifeLeechText,statBaseX+25,statBaseY), 0);
        
        final ArmorStat stat8 = this.playerReference.getArmorManager().numberOfPotionsStat;
        b = new Button(stat8.image.copy(), statBaseX+125, statBaseY, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat8.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat8.name,stat8.image,stat8.description, stat8.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b, 0);
        this.numberOfPotionsText= new Text(Byte.toString(stat8.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.numberOfPotionsText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.numberOfPotionsText,statBaseX+150,statBaseY ), 0);

        
        final ArmorStat stat4 = this.playerReference.getArmorManager().healingEffectivenessStat;
        b = new Button(stat4.image.copy(), statBaseX, statBaseY -125, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat4.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat4.name,stat4.image,stat4.description, stat4.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b, 0);
        this.healingEffectivenessStatText= new Text(Byte.toString(stat4.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.healingEffectivenessStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.healingEffectivenessStatText,statBaseX+25,statBaseY-125), 0);
        
        //=========================
        // Helm Modifier Components
        //=========================
        
        this.tabPane.addComponent(new Button(new Text("Modifiers:"), modifierBaseX, modifierBaseY+120), 0);
        
        final ArmorModifier modifier = this.playerReference.getArmorManager().seeEnemyHealthModifier;      
        this.seeEnemyHealthModifier = new Button(modifier.image.copy(),modifierBaseX,modifierBaseY,100,100);
        this.seeEnemyHealthModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
                   ((GameClientScene)owningScene).sendEquipModifierPacket(modifier.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier.name,modifier.image,modifier.description, null);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.seeEnemyHealthModifier,0);
        
        final ArmorModifier modifier2 = this.playerReference.getArmorManager().doubleGoldFindModifier;
        this.doubleGoldFindModifier = new Button(modifier2.image.copy(),modifierBaseX+125,modifierBaseY,100,100);
        this.doubleGoldFindModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
                   ((GameClientScene)owningScene).sendEquipModifierPacket(modifier2.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier2.name,modifier2.image,modifier2.description, null);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.doubleGoldFindModifier,0);
        
        final ArmorModifier modifier4 = this.playerReference.getArmorManager().upgradedRadarModifier;
        this.upgradedRadarModifier = new Button(modifier4.image.copy(),modifierBaseX+125,modifierBaseY-125,100,100);
        this.upgradedRadarModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
                   ((GameClientScene)owningScene).sendEquipModifierPacket(modifier4.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier4.name,modifier4.image,modifier4.description, null);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.upgradedRadarModifier,0);
        
        
        //=========================
        //Body Modifier Components
        //=========================
        
        this.tabPane.addComponent(new Button(new Text("Modifiers:"), modifierBaseX, modifierBaseY+125), 1);
        
        final ArmorModifier modifier6 = this.playerReference.getArmorManager().damageReductionBonusModifier;
        this.toughToKillModifier = new Button(modifier6.image.copy(),modifierBaseX+125,modifierBaseY,100,100);
        this.toughToKillModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
                   ((GameClientScene)owningScene).sendEquipModifierPacket(modifier6.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier6.name,modifier6.image,modifier6.description, null);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.toughToKillModifier,1);
        
        final ArmorModifier modifier7 = this.playerReference.getArmorManager().reducedCriticalHitModifier;
        this.noCritsModifier = new Button(modifier7.image.copy(),modifierBaseX,modifierBaseY-125,100,100);
        this.noCritsModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
                   ((GameClientScene)owningScene).sendEquipModifierPacket(modifier7.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier7.name,modifier7.image,modifier7.description, null);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.noCritsModifier,1);
        
       final ArmorModifier modifier8 = this.playerReference.getArmorManager().bonusThornsDamageModifier;
        this.increasedThornsModifier = new Button(modifier8.image.copy(),modifierBaseX+125,modifierBaseY-125,100,100);
        this.increasedThornsModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
                   ((GameClientScene)owningScene).sendEquipModifierPacket(modifier8.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier8.name,modifier8.image,modifier8.description, null);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.increasedThornsModifier,1);
        
        //======================
        // Body Stat Components
        //======================
        this.tabPane.addComponent(new Button(new Text("Upgrades:"), statBaseX, statBaseY + 120), 1);
        
        final ArmorStat stat5 = this.playerReference.getArmorManager().healthStat;
        b = new Button(stat5.image.copy(), statBaseX, statBaseY, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat5.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat5.name,stat5.image,stat5.description, stat5.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b, 1);
        this.bodyHealthStatText = new Text(Byte.toString(stat5.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.bodyHealthStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.bodyHealthStatText,statBaseX+25,statBaseY), 1);
        
        final ArmorStat stat6 = this.playerReference.getArmorManager().damageReductionStat;
        b = new Button(stat6.image.copy(), statBaseX+125, statBaseY, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat6.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat6.name,stat6.image,stat6.description, stat6.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           } 
       });
        this.tabPane.addComponent(b, 1);
        this.bodyDamageReductionStatText = new Text(Byte.toString(stat6.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.bodyDamageReductionStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.bodyDamageReductionStatText,statBaseX+150,statBaseY), 1);
        
        final ArmorStat stat99 = this.playerReference.getArmorManager().thornsDamage;
        b = new Button(stat99.image.copy(), statBaseX, statBaseY-125, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat99.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat99.name,stat99.image,stat99.description, stat99.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           } 
       });
        this.tabPane.addComponent(b, 1);
        this.thornsStatText = new Text(Byte.toString(stat99.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.thornsStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.thornsStatText,statBaseX+25,statBaseY-125), 1);
        

        
        //============================
        // Weapon Modifier Components
        //============================
        
        this.tabPane.addComponent(new Button(new Text("Modifiers:"), modifierBaseX, modifierBaseY+125), 2);
        
       final ArmorModifier modifier10 = this.playerReference.getArmorManager().meleeAttackDamageModifier;
        this.meleeAttackDamageModifier = new Button(modifier10.image.copy(),modifierBaseX+125,modifierBaseY,100,100);
        this.meleeAttackDamageModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
                   ((GameClientScene)owningScene).sendEquipModifierPacket(modifier10.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier10.name,modifier10.image,modifier10.description, null);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.meleeAttackDamageModifier,2);
        
       final ArmorModifier modifier11 = this.playerReference.getArmorManager().potionCooldownResetModifier;
        this.potionResetModifier = new Button(modifier11.image.copy(),modifierBaseX,modifierBaseY-125,100,100);
        this.potionResetModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
                   ((GameClientScene)owningScene).sendEquipModifierPacket(modifier11.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier11.name,modifier11.image,modifier11.description, null);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.potionResetModifier,2);
        
       final ArmorModifier modifier12 = this.playerReference.getArmorManager().criticalHitDamageModifier;
        this.criticalHitDamageModifier = new Button(modifier12.image.copy(),modifierBaseX+125,modifierBaseY-125,100,100);
        this.criticalHitDamageModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
                   ((GameClientScene)owningScene).sendEquipModifierPacket(modifier12.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier12.name,modifier12.image,modifier12.description, null);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.criticalHitDamageModifier,2);
        
        //========================
        // Weapon Stat Components
        //========================
        this.tabPane.addComponent(new Button(new Text("Upgrades:"), statBaseX, statBaseY + 120), 2);
        
        final ArmorStat stat9 = this.playerReference.getArmorManager().weaponDamageStat;
        b = new Button(stat9.image.copy(), statBaseX, statBaseY, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat9.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat9.name,stat9.image,stat9.description, stat9.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b, 2);
        this.weaponDamageStatText = new Text(Byte.toString(stat9.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.weaponDamageStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.weaponDamageStatText,statBaseX +25,statBaseY), 2);
        
        final ArmorStat stat10 = this.playerReference.getArmorManager().weaponAttackSpeedStat;
        b = new Button(stat10.image.copy(), statBaseX+125, statBaseY, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat10.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat10.name,stat10.image,stat10.description, stat10.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           } 
       });
        this.tabPane.addComponent(b, 2);
        this.weaponAttackSpeedStatText = new Text(Byte.toString(stat10.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.weaponAttackSpeedStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.weaponAttackSpeedStatText,statBaseX +150,statBaseY), 2);
        
       final ArmorStat stat11 = this.playerReference.getArmorManager().critChanceStat;
        b = new Button(stat11.image.copy(), statBaseX, statBaseY-125, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat11.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat11.name,stat11.image,stat11.description, stat11.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b, 2);
        this.critChanceStatText = new Text(Byte.toString(stat11.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.critChanceStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.critChanceStatText,statBaseX +25,statBaseY-125), 2);
        
        
        //==================
        // Boots Modifiers
        //==================
        
        this.tabPane.addComponent(new Button(new Text("Modifiers:"), modifierBaseX, modifierBaseY+125), 3);
        
       final ArmorModifier modifier14 = this.playerReference.getArmorManager().doubleJumpModifier;
        this.doubleJumpModifier = new Button(modifier14.image.copy(),modifierBaseX+125,modifierBaseY,100,100);
        this.doubleJumpModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
                   ((GameClientScene)owningScene).sendEquipModifierPacket(modifier14.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier14.name,modifier14.image,modifier14.description, null);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.doubleJumpModifier,3);
        
       final ArmorModifier modifier15 = this.playerReference.getArmorManager().jetpackModifier;
        this.jetpackModifier = new Button(modifier15.image.copy(),modifierBaseX,modifierBaseY-125,100,100);
        this.jetpackModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
                   ((GameClientScene)owningScene).sendEquipModifierPacket(modifier15.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier15.name,modifier15.image,modifier15.description, null);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.jetpackModifier,3);
        
       final ArmorModifier modifier16 = this.playerReference.getArmorManager().teleportModifier;
        this.teleportModifier = new Button(modifier16.image.copy(),modifierBaseX+125,modifierBaseY-125,100,100);
        this.teleportModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
                   ((GameClientScene)owningScene).sendEquipModifierPacket(modifier16.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier16.name,modifier16.image,modifier16.description, null);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.teleportModifier,3);
        
        //========================
        // Boots Stats Components
        //========================
        this.tabPane.addComponent(new Button(new Text("Upgrades:"), statBaseX, statBaseY + 120), 3);
        
        final ArmorStat stat3 = this.playerReference.getArmorManager().ccReductionStat;
        b = new Button(stat3.image.copy(), statBaseX, statBaseY, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat3.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat3.name,stat3.image,stat3.description, stat3.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b,3);
        this.ccReductionStatText = new Text(Byte.toString(stat3.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.ccReductionStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.ccReductionStatText,statBaseX+25,statBaseY ), 3);
        
      final  ArmorStat stat15 = this.playerReference.getArmorManager().moveSpeedStat;
        b = new Button(stat15.image.copy(), statBaseX+125, statBaseY, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat15.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat15.name,stat15.image,stat15.description, stat15.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b, 3);
        this.moveSpeedStatText = new Text(Byte.toString(stat15.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.moveSpeedStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.moveSpeedStatText,statBaseX +150,statBaseY), 3);
        
       final ArmorStat stat16 = this.playerReference.getArmorManager().jumpHeightStat;
        b = new Button(stat16.image.copy(), statBaseX, statBaseY-125, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat16.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat16.name,stat16.image,stat16.description, stat16.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b, 3);
        this.jumpHeightStatText= new Text(Byte.toString(stat16.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.jumpHeightStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.jumpHeightStatText,statBaseX +25,statBaseY-125), 3);
        
        
        //==================
        // Detail Text
        //===================
        

       this.lifeLeechDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.lifeLeechDetailText,400,400),0);
       this.lifeRegenDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.lifeRegenDetailText,400,375),0);
       this.healingEffectivenessDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.healingEffectivenessDetailText,400,350),0);    
       this.helmModifierDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.helmModifierDetailText,400,325),0);
       
       this.bodyHealthDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.bodyHealthDetailText,400,400),1);
       this.bodyDamageReductionDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.bodyDamageReductionDetailText,400,375),1);  
       this.bodyThornsDamageDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.bodyThornsDamageDetailText,400,350),1);
       this.bodyModifierDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.bodyModifierDetailText,400,325),1);
       
       this.weaponDamageDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.weaponDamageDetailText,400,400),2);
       this.weaponAttackSpeedDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.weaponAttackSpeedDetailText,400,375),2);
       this.critChanceDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.critChanceDetailText,400,350),2);
       this.weaponModifierDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.weaponModifierDetailText,400,325),2);
       
       this.ccReductionDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.ccReductionDetailText,400,400),3);
       this.moveSpeedDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.moveSpeedDetailText,400,375),3);
       this.jumpHeightDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.jumpHeightDetailText,400,350),3);
       this.bootsModifierDetailText = new Text("");
       this.tabPane.addComponent(new Label(this.bootsModifierDetailText,400,325),3);
 
    }
    
    @Override
    public void update()
    {
        super.update();
        
        //update helm stat points
        this.lifeLeechText.setText(Byte.toString(this.playerReference.getArmorManager().lifeLeech.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.numberOfPotionsText.setText(Byte.toString(this.playerReference.getArmorManager().numberOfPotionsStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.healingEffectivenessStatText.setText(Byte.toString(this.playerReference.getArmorManager().healingEffectivenessStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));

        //update body stat points
        this.bodyHealthStatText.setText(Byte.toString(this.playerReference.getArmorManager().healthStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.bodyDamageReductionStatText.setText(Byte.toString(this.playerReference.getArmorManager().damageReductionStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.thornsStatText.setText(Byte.toString(this.playerReference.getArmorManager().thornsDamage.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
      
        //weapon
        this.weaponDamageStatText.setText(Byte.toString(this.playerReference.getArmorManager().weaponDamageStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.weaponAttackSpeedStatText.setText(Byte.toString(this.playerReference.getArmorManager().weaponAttackSpeedStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.critChanceStatText.setText(Byte.toString(this.playerReference.getArmorManager().critChanceStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));

        //boots
        this.ccReductionStatText.setText(Byte.toString(this.playerReference.getArmorManager().ccReductionStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));      
        this.moveSpeedStatText.setText(Byte.toString(this.playerReference.getArmorManager().moveSpeedStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.jumpHeightStatText.setText(Byte.toString(this.playerReference.getArmorManager().jumpHeightStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));    

        
        
        //=============
        // Detail Text
        //=============
        
      //helm
     this.lifeLeechDetailText.setText("+" + Integer.toString(this.playerReference.getArmorManager().lifeLeech.points * 5) + "% life leech.");
      this.lifeRegenDetailText.setText("+" + Integer.toString(this.playerReference.getArmorManager().numberOfPotionsStat.points* 1) + " potions.");
      this.healingEffectivenessDetailText.setText("+" + Integer.toString(this.playerReference.getArmorManager().healingEffectivenessStat.points* 5) + "% bonus to healing.");

      //body
      this.bodyThornsDamageDetailText.setText("+" + Integer.toString(this.playerReference.getArmorManager().thornsDamage.points * 5 )  + "% thorns damage.");
      this.bodyHealthDetailText.setText("+" + Integer.toString(this.playerReference.getArmorManager().healthStat.points * 10)  + " health.");
      this.bodyDamageReductionDetailText.setText("+" + Integer.toString(this.playerReference.getArmorManager().damageReductionStat.points * 2) + "% damage reduction.");
      
      //weapon
      this.weaponDamageDetailText.setText("+" + Integer.toString(this.playerReference.getArmorManager().weaponDamageStat.points * 2)  + "% damage.");
      this.weaponAttackSpeedDetailText.setText("+" + Integer.toString(this.playerReference.getArmorManager().weaponAttackSpeedStat.points * 5) + "% attack speed.");
      this.critChanceDetailText.setText("+" + Integer.toString(this.playerReference.getArmorManager().critChanceStat.points * 3) + "% critical hit chance.");
     
      //boots
      this.ccReductionDetailText.setText("+" + Integer.toString(this.playerReference.getArmorManager().ccReductionStat.points * 5) + "% CC reduction.");
      this.moveSpeedDetailText.setText("+" + Integer.toString(this.playerReference.getArmorManager().moveSpeedStat.points * 5) + "% movement speed.");
      this.jumpHeightDetailText.setText("+" + Integer.toString(this.playerReference.getArmorManager().jumpHeightStat.points* 2) + "m jump height.");

      
      //====================
      // Show/Hide Modifiers
      //=====================
            
         if(this.playerReference.getArmorManager().seeEnemyHealthModifier.unlocked == false)
         {
             this.seeEnemyHealthModifier.setDisabled(true);
             this.seeEnemyHealthModifier.setHidden(true);
         }
         else
         {
             this.seeEnemyHealthModifier.setDisabled(false);
             this.seeEnemyHealthModifier.setHidden(false);
             
         } 
         if(this.playerReference.getArmorManager().doubleGoldFindModifier.unlocked== false)
         {
             this.doubleGoldFindModifier.setDisabled(true);
             this.doubleGoldFindModifier.setHidden(true);
         }
         else
         {
             this.doubleGoldFindModifier.setDisabled(false);
             this.doubleGoldFindModifier.setHidden(false);
             
         }
        if(this.playerReference.getArmorManager().upgradedRadarModifier.unlocked== false)
         {
             this.upgradedRadarModifier.setDisabled(true);
             this.upgradedRadarModifier.setHidden(true);
         }
         else
         {
             this.upgradedRadarModifier.setDisabled(false);
             this.upgradedRadarModifier.setHidden(false);
            
         }

        
        

         if(this.playerReference.getArmorManager().damageReductionBonusModifier.unlocked== false)
         {
             this.toughToKillModifier.setDisabled(true);
             this.toughToKillModifier.setHidden(true);
         }
         else
         {
             this.toughToKillModifier.setDisabled(false);
             this.toughToKillModifier.setHidden(false);
             
         }
         if(this.playerReference.getArmorManager().reducedCriticalHitModifier.unlocked== false)
         {
             this.noCritsModifier.setDisabled(true);
             this.noCritsModifier.setHidden(true);
         }
         else
         {
             this.noCritsModifier.setDisabled(false);
             this.noCritsModifier.setHidden(false);
             
         }
        if(this.playerReference.getArmorManager().bonusThornsDamageModifier.unlocked== false)
         {
             this.increasedThornsModifier.setDisabled(true);
             this.increasedThornsModifier.setHidden(true);
         }
         else
         {
             this.increasedThornsModifier.setDisabled(false);
             this.increasedThornsModifier.setHidden(false);
             
         }

        
        
        
        if(this.playerReference.getArmorManager().meleeAttackDamageModifier.unlocked== false)
         {
             this.meleeAttackDamageModifier.setDisabled(true);
             this.meleeAttackDamageModifier.setHidden(true);
         }
         else
         {
             this.meleeAttackDamageModifier.setDisabled(false);
             this.meleeAttackDamageModifier.setHidden(false);
            
         }
        if(this.playerReference.getArmorManager().potionCooldownResetModifier.unlocked== false)
         {
             this.potionResetModifier.setDisabled(true);
             this.potionResetModifier.setHidden(true);
         }
         else
         {
             this.potionResetModifier.setDisabled(false);
             this.potionResetModifier.setHidden(false);
             
         }
         if(this.playerReference.getArmorManager().criticalHitDamageModifier.unlocked== false)
         {
             this.criticalHitDamageModifier.setDisabled(true);
             this.criticalHitDamageModifier.setHidden(true);
         }
         else
         {
             this.criticalHitDamageModifier.setDisabled(false);
             this.criticalHitDamageModifier.setHidden(false);
             
         }


         
         if(this.playerReference.getArmorManager().doubleJumpModifier.unlocked== false)
         {
             this.doubleJumpModifier.setDisabled(true);
             this.doubleJumpModifier.setHidden(true);
         }
         else
         {
             this.doubleJumpModifier.setDisabled(false);
             this.doubleJumpModifier.setHidden(false);
             
         }
         if(this.playerReference.getArmorManager().jetpackModifier.unlocked== false)
         {
             this.jetpackModifier.setDisabled(true);
             this.jetpackModifier.setHidden(true);
         }
         else
         {
             this.jetpackModifier.setDisabled(false);
             this.jetpackModifier.setHidden(false);
             
         }
         if(this.playerReference.getArmorManager().teleportModifier.unlocked== false)
         {
             this.teleportModifier.setDisabled(true);
             this.teleportModifier.setHidden(true);
         }
         else
         {
             this.teleportModifier.setDisabled(false);
             this.teleportModifier.setHidden(false);
             
         }
         
          
         //toggle equipped highlight
         if(this.playerReference.getArmorManager().seeEnemyHealthModifier.equipped == true)
         {
             this.seeEnemyHealthModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
             this.helmModifierDetailText.setText("Equipped Modifier: See Enemy Health");
         }
         else
         {
             this.seeEnemyHealthModifier.getImage().removeAllOverlays();
         }
        
    
         if(this.playerReference.getArmorManager().doubleGoldFindModifier.equipped== true)
         {
             this.doubleGoldFindModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
             this.helmModifierDetailText.setText("Equipped Modifier: Double Gold Find");
         }
         else
         {
             this.doubleGoldFindModifier.getImage().removeAllOverlays();
         }
        if(this.playerReference.getArmorManager().upgradedRadarModifier.equipped== true)
         {
             this.upgradedRadarModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
              this.helmModifierDetailText.setText("Equipped Modifier: Upgraded Radar");
         }
         else
         {
             this.upgradedRadarModifier.getImage().removeAllOverlays();
         }
        
        
        
        if(this.playerReference.getArmorManager().damageReductionBonusModifier.equipped== true)
         {
             this.toughToKillModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
             this.bodyModifierDetailText.setText("Equipped Modifier: Tough To kill");
         }
         else
         {
             this.toughToKillModifier.getImage().removeAllOverlays();
         }
         if(this.playerReference.getArmorManager().reducedCriticalHitModifier.equipped== true)
         {
             this.noCritsModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
             this.bodyModifierDetailText.setText("Equipped Modifier: Hardened Armor");
         }
         else
         {
             this.noCritsModifier.getImage().removeAllOverlays();
         }
         if(this.playerReference.getArmorManager().bonusThornsDamageModifier.equipped== true)
         {
             this.increasedThornsModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
             this.bodyModifierDetailText.setText("Equipped Modifier: Increased Thorns Damage");
         }
         else
         {
             this.increasedThornsModifier.getImage().removeAllOverlays();
         }


        if(this.playerReference.getArmorManager().meleeAttackDamageModifier.equipped== true)
         {
             this.meleeAttackDamageModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
              this.weaponModifierDetailText.setText("Equipped Modifier: Melee Damage Increase");
         }
         else
         {
             this.meleeAttackDamageModifier.getImage().removeAllOverlays();
         }
        if(this.playerReference.getArmorManager().potionCooldownResetModifier.equipped== true)
         {
             this.potionResetModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
             this.weaponModifierDetailText.setText("Equipped Modifier: Potion Cooldown Reset");
         }
         else
         {
             this.potionResetModifier.getImage().removeAllOverlays();
         }
         if(this.playerReference.getArmorManager().criticalHitDamageModifier.equipped== true)
         {
             this.criticalHitDamageModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png")));
             this.weaponModifierDetailText.setText("Equipped Modifier: Critical Hit Damagee");
         }
         else
         {
             this.criticalHitDamageModifier.getImage().removeAllOverlays();
         }


         if(this.playerReference.getArmorManager().doubleJumpModifier.equipped== true)
         {
             this.doubleJumpModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
             this.bootsModifierDetailText.setText("Equipped Modifier: Double Jump");
         }
         else
         {
             this.doubleJumpModifier.getImage().removeAllOverlays();
         }
         if(this.playerReference.getArmorManager().jetpackModifier.equipped== true)
         {
             this.jetpackModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png")));
             this.bootsModifierDetailText.setText("Equipped Modifier: Jetpack");
         }
         else
         {
             this.jetpackModifier.getImage().removeAllOverlays();
         }
         if(this.playerReference.getArmorManager().teleportModifier.equipped== true)
         {
             this.teleportModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
             this.bootsModifierDetailText.setText("Equipped Modifier: Teleport");
         }
         else
         {
             this.teleportModifier.getImage().removeAllOverlays();
         }
        
        
    
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
        
        //===============
        //build tooltip
        //===============
        
        //background
        skillTooltipBackground = new Button(new Image("tooltip.jpg"), x, y, 400, 300);
        this.addComponent(skillTooltipBackground);
        
        //name
        Text text = new Text(name);
        text.setScale(1.3f);
        skillTooltipName = new Label(text, x + 200 - text.getWidth()/2, y + 250);
        this.addComponent(skillTooltipName);
        
        //icon
        skillTooltipIcon = new Button(image.copy(),x + 25, y + 160,50,50);
        this.addComponent(skillTooltipIcon);
        
        //description
        text = new Text(description);
        skillTooltipTextBlock = new TextBlock(x + 85, y + 190, 300, text);
        this.addComponent(skillTooltipTextBlock);    
        
        //cost
        if(cost != null)
        {
            text = new Text("Cost: " + cost.toString());
            tooltipCost = new Label(text, x + 200 - text.getWidth()/2, y + 50);
            this.addComponent(tooltipCost);
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
    }
}
