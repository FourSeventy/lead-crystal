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
    
    
    //helm stat labels
    private Text helmHealthStatText;
    private Text helmDamageReductionStatText;
    private Text ccReductionStatText;
    private Text healingEffectivenessStatText;
    
    private Text bodyHealthStatText;
    private Text bodyDamageReductionStatText;
    private Text lifeLeechText;
    private Text lifeRegenText;
    
    private Text weaponDamageStatText;
    private Text weaponAttackSpeedStatText;
    private Text critChanceStatText;
    private Text critDamageStatText;
    
    private Text bootsDamageStatText;
    private Text bootsAttackSpeedStatText;
    private Text moveSpeedStatText;
    private Text jumpHeightStatText;
    
    
    private Button seeEnemyHealthModifier;
    private Button doubleGoldFindModifier;
    private Button seeSecondaryObjectivesModifier;
    private Button revealSecretAreasModifier;
    
    private Button doubleCCResistModifier;
    private Button doubleHealingModifier;
    private Button tenPotionsModifier;
    private Button chanceAbsorbModifier;
    
    private Button concecutiveHitsModifier;
    private Button meleeAttackDamageModifier;
    private Button rangedAttackSlowModifier;
    private Button criticalHitDamageModifier;

    private Button ccBonusModifier;
    private Button doubleJumpModifier;
    private Button jetpackModifier;
    private Button teleportModifier;
  
    
    
    
    public ArmorMenu( PlayerEntity player, float x, float y)
    {
        super(new Image("armorMenu.png"),x,y,1200,900);
        
        this.playerReference = player;
        
        this.tabPane = new TabPane(50,50,1100,800);
        this.tabPane.addTab("Helm");
        this.tabPane.addTab("Body");
        this.tabPane.addTab("Weapon");
        this.tabPane.addTab("Boots");
        this.addComponent(tabPane);
        
        //======================
        // Helm Stat Components
        //======================
        this.tabPane.addComponent(new Button(new Text("Upgrades:"), 600, 400), 0);
        
        final ArmorStat stat = this.playerReference.getArmorManager().helmHealthStat;
        Button b = new Button(stat.image.copy(), 600, 250, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat.name,stat.image,stat.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b, 0);
        this.helmHealthStatText = new Text(Byte.toString(stat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.helmHealthStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.helmHealthStatText,625,250), 0);
        
        final ArmorStat stat2 = this.playerReference.getArmorManager().helmDamageReductionStat;
        b = new Button(stat2.image.copy(), 725, 250, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat2.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat2.name,stat2.image,stat2.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b, 0);
        this.helmDamageReductionStatText = new Text(Byte.toString(stat2.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.helmDamageReductionStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.helmDamageReductionStatText,750,250), 0);
        
        ArmorStat stat3 = this.playerReference.getArmorManager().ccReductionStat;
        b = new Button(stat3.image.copy(), 600, 125, 100, 100);
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
                   openTooltip(700,450,stat3.name,stat3.image,stat3.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b, 0);
        this.ccReductionStatText = new Text(Byte.toString(stat3.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.ccReductionStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.ccReductionStatText,625,125), 0);
        
        ArmorStat stat4 = this.playerReference.getArmorManager().healingEffectivenessStat;
        b = new Button(stat4.image.copy(), 725, 125, 100, 100);
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
                   openTooltip(700,450,stat4.name,stat4.image,stat4.description);
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
        this.tabPane.addComponent(new Label(this.healingEffectivenessStatText,750,125), 0);
        
        //=========================
        // Helm Modifier Components
        //=========================
        
        this.tabPane.addComponent(new Button(new Text("Modifiers:"), 200, 400), 0);
        
        ArmorModifier modifier = this.playerReference.getArmorManager().seeEnemyHealthModifier;      
        this.seeEnemyHealthModifier = new Button(modifier.image.copy(),200,225,100,100);
        this.seeEnemyHealthModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier.name,modifier.image,modifier.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.seeEnemyHealthModifier,0);
        
        ArmorModifier modifier2 = this.playerReference.getArmorManager().doubleGoldFindModifier;
        this.doubleGoldFindModifier = new Button(modifier2.image.copy(),325,225,100,100);
        this.doubleGoldFindModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier2.name,modifier2.image,modifier2.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.doubleGoldFindModifier,0);
        
        ArmorModifier modifier3 = this.playerReference.getArmorManager().revealSecretAreasModifier;
        this.revealSecretAreasModifier = new Button(modifier3.image.copy(),200,100,100,100);
        this.revealSecretAreasModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier3.name,modifier3.image,modifier3.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.revealSecretAreasModifier,0);
        
        ArmorModifier modifier4 = this.playerReference.getArmorManager().seeSecondaryObjectivesModifier;
        this.seeSecondaryObjectivesModifier = new Button(modifier4.image.copy(),325,100,100,100);
        this.seeSecondaryObjectivesModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier4.name,modifier4.image,modifier4.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.seeSecondaryObjectivesModifier,0);
        
        
        //=========================
        //Body Modifier Components
        //=========================
        
        this.tabPane.addComponent(new Button(new Text("Modifiers:"), 200, 400), 1);
        
        ArmorModifier modifier5 = this.playerReference.getArmorManager().doubleCCResistModifier;      
        this.doubleCCResistModifier = new Button(modifier5.image.copy(),200,225,100,100);
        this.doubleCCResistModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier5.name,modifier5.image,modifier5.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.doubleCCResistModifier,1);
        
        ArmorModifier modifier6 = this.playerReference.getArmorManager().doubleHealingModifier;
        this.doubleHealingModifier = new Button(modifier6.image.copy(),325,225,100,100);
        this.doubleHealingModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier6.name,modifier6.image,modifier6.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.doubleHealingModifier,1);
        
        ArmorModifier modifier7 = this.playerReference.getArmorManager().tenPotionsModifier;
        this.tenPotionsModifier = new Button(modifier7.image.copy(),200,100,100,100);
        this.tenPotionsModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier7.name,modifier7.image,modifier7.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.tenPotionsModifier,1);
        
        ArmorModifier modifier8 = this.playerReference.getArmorManager().chanceAbsorbModifier;
        this.chanceAbsorbModifier = new Button(modifier8.image.copy(),325,100,100,100);
        this.chanceAbsorbModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier8.name,modifier8.image,modifier8.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.chanceAbsorbModifier,1);
        
        //======================
        // Body Stat Components
        //======================
        this.tabPane.addComponent(new Button(new Text("Upgrades:"), 600, 400), 1);
        
        final ArmorStat stat5 = this.playerReference.getArmorManager().bodyHealthStat;
        b = new Button(stat5.image.copy(), 600, 250, 100, 100);
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
                   openTooltip(700,450,stat5.name,stat5.image,stat5.description);
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
        this.tabPane.addComponent(new Label(this.bodyHealthStatText,625,250), 1);
        
        final ArmorStat stat6 = this.playerReference.getArmorManager().bodyDamageReductionStat;
        b = new Button(stat6.image.copy(), 725, 250, 100, 100);
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
                   openTooltip(700,450,stat6.name,stat6.image,stat6.description);
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
        this.tabPane.addComponent(new Label(this.bodyDamageReductionStatText,750,250), 1);
        
        ArmorStat stat7 = this.playerReference.getArmorManager().lifeLeech;
        b = new Button(stat7.image.copy(), 600, 125, 100, 100);
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
                   openTooltip(700,450,stat7.name,stat7.image,stat7.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b, 1);
        this.lifeLeechText = new Text(Byte.toString(stat7.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.lifeLeechText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.lifeLeechText,625,125), 1);
        
        ArmorStat stat8 = this.playerReference.getArmorManager().lifeRegen;
        b = new Button(stat8.image.copy(), 725, 125, 100, 100);
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
                   openTooltip(700,450,stat8.name,stat8.image,stat8.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b, 1);
        this.lifeRegenText= new Text(Byte.toString(stat8.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.lifeRegenText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.lifeRegenText,750,125), 1);
        
        //============================
        // Weapon Modifier Components
        //============================
        
        this.tabPane.addComponent(new Button(new Text("Modifiers:"), 200, 400), 2);
        
        ArmorModifier modifier9 = this.playerReference.getArmorManager().concecutiveHitsModifier;      
        this.concecutiveHitsModifier = new Button(modifier9.image.copy(),200,225,100,100);
        this.concecutiveHitsModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier9.name,modifier9.image,modifier9.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.concecutiveHitsModifier,2);
        
        ArmorModifier modifier10 = this.playerReference.getArmorManager().meleeAttackDamageModifier;
        this.meleeAttackDamageModifier = new Button(modifier10.image.copy(),325,225,100,100);
        this.meleeAttackDamageModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier10.name,modifier10.image,modifier10.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.meleeAttackDamageModifier,2);
        
        ArmorModifier modifier11 = this.playerReference.getArmorManager().rangedAttackSlowModifier;
        this.rangedAttackSlowModifier = new Button(modifier11.image.copy(),200,100,100,100);
        this.rangedAttackSlowModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier11.name,modifier11.image,modifier11.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.rangedAttackSlowModifier,2);
        
        ArmorModifier modifier12 = this.playerReference.getArmorManager().criticalHitDamageModifier;
        this.criticalHitDamageModifier = new Button(modifier12.image.copy(),325,100,100,100);
        this.criticalHitDamageModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier12.name,modifier12.image,modifier12.description);
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
        this.tabPane.addComponent(new Button(new Text("Upgrades:"), 600, 400), 2);
        
        final ArmorStat stat9 = this.playerReference.getArmorManager().weaponDamageStat;
        b = new Button(stat9.image.copy(), 600, 250, 100, 100);
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
                   openTooltip(700,450,stat9.name,stat9.image,stat9.description);
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
        this.tabPane.addComponent(new Label(this.weaponDamageStatText,625,250), 2);
        
        final ArmorStat stat10 = this.playerReference.getArmorManager().weaponAttackSpeedStat;
        b = new Button(stat10.image.copy(), 725, 250, 100, 100);
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
                   openTooltip(700,450,stat10.name,stat10.image,stat10.description);
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
        this.tabPane.addComponent(new Label(this.weaponAttackSpeedStatText,750,250), 2);
        
        ArmorStat stat11 = this.playerReference.getArmorManager().critChanceStat;
        b = new Button(stat11.image.copy(), 600, 125, 100, 100);
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
                   openTooltip(700,450,stat11.name,stat11.image,stat11.description);
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
        this.tabPane.addComponent(new Label(this.critChanceStatText,625,125), 2);
        
        ArmorStat stat12 = this.playerReference.getArmorManager().critDamageStat;
        b = new Button(stat12.image.copy(), 725, 125, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat12.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat12.name,stat12.image,stat12.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b, 2);
        this.critDamageStatText= new Text(Byte.toString(stat12.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.critDamageStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.critDamageStatText,750,125), 2);
        
        //==================
        // Boots Modifiers
        //==================
        
        this.tabPane.addComponent(new Button(new Text("Modifiers:"), 200, 400), 3);
        
        ArmorModifier modifier13 = this.playerReference.getArmorManager().ccBonusModifier;      
        this.ccBonusModifier = new Button(modifier13.image.copy(),200,225,100,100);
        this.ccBonusModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier13.name,modifier13.image,modifier13.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.ccBonusModifier,3);
        
        ArmorModifier modifier14 = this.playerReference.getArmorManager().doubleJumpModifier;
        this.doubleJumpModifier = new Button(modifier14.image.copy(),325,225,100,100);
        this.doubleJumpModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier14.name,modifier14.image,modifier14.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.doubleJumpModifier,3);
        
        ArmorModifier modifier15 = this.playerReference.getArmorManager().jetpackModifier;
        this.jetpackModifier = new Button(modifier15.image.copy(),200,100,100,100);
        this.jetpackModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier15.name,modifier15.image,modifier15.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(this.jetpackModifier,3);
        
        ArmorModifier modifier16 = this.playerReference.getArmorManager().teleportModifier;
        this.teleportModifier = new Button(modifier16.image.copy(),325,100,100,100);
        this.teleportModifier.addActionListener(new ActionListener(){     
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                 //do stuff
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(400,450,modifier16.name,modifier16.image,modifier16.description);
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
        this.tabPane.addComponent(new Button(new Text("Upgrades:"), 600, 400), 3);
        
        final ArmorStat stat13 = this.playerReference.getArmorManager().bootsDamageStat;
        b = new Button(stat13.image.copy(), 600, 250, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat13.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat13.name,stat13.image,stat13.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.tabPane.addComponent(b, 3);
        this.bootsDamageStatText = new Text(Byte.toString(stat13.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.bootsDamageStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.bootsDamageStatText,625,250), 3);
        
        final ArmorStat stat14 = this.playerReference.getArmorManager().bootsAttackSpeedStat;
        b = new Button(stat14.image.copy(), 725, 250, 100, 100);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat14.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat14.name,stat14.image,stat14.description);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           } 
       });
        this.tabPane.addComponent(b, 3);
        this.bootsAttackSpeedStatText = new Text(Byte.toString(stat14.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.bootsAttackSpeedStatText.setScale(1.2f);
        this.tabPane.addComponent(new Label(this.bootsAttackSpeedStatText,750,250), 3);
        
        ArmorStat stat15 = this.playerReference.getArmorManager().moveSpeedStat;
        b = new Button(stat15.image.copy(), 600, 125, 100, 100);
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
                   openTooltip(700,450,stat15.name,stat15.image,stat15.description);
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
        this.tabPane.addComponent(new Label(this.moveSpeedStatText,625,125), 3);
        
        ArmorStat stat16 = this.playerReference.getArmorManager().jumpHeightStat;
        b = new Button(stat16.image.copy(), 725, 125, 100, 100);
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
                   openTooltip(700,450,stat16.name,stat16.image,stat16.description);
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
        this.tabPane.addComponent(new Label(this.jumpHeightStatText,750,125), 3);
    }
    
    @Override
    public void update()
    {
        super.update();
        
        //update helm stat points
        this.helmHealthStatText.setText(Byte.toString(this.playerReference.getArmorManager().helmHealthStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS)); 
        this.helmDamageReductionStatText.setText(Byte.toString(this.playerReference.getArmorManager().helmDamageReductionStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.ccReductionStatText.setText(Byte.toString(this.playerReference.getArmorManager().ccReductionStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.healingEffectivenessStatText.setText(Byte.toString(this.playerReference.getArmorManager().healingEffectivenessStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
   
        //update body stat points
        this.bodyHealthStatText.setText(Byte.toString(this.playerReference.getArmorManager().bodyHealthStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.bodyDamageReductionStatText.setText(Byte.toString(this.playerReference.getArmorManager().bodyDamageReductionStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.lifeLeechText.setText(Byte.toString(this.playerReference.getArmorManager().lifeLeech.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.lifeRegenText.setText(Byte.toString(this.playerReference.getArmorManager().lifeRegen.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
   
        //weapon
        this.weaponDamageStatText.setText(Byte.toString(this.playerReference.getArmorManager().weaponDamageStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.weaponAttackSpeedStatText.setText(Byte.toString(this.playerReference.getArmorManager().weaponAttackSpeedStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.critChanceStatText.setText(Byte.toString(this.playerReference.getArmorManager().critChanceStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.critDamageStatText.setText(Byte.toString(this.playerReference.getArmorManager().critDamageStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));    

        //boots
        this.bootsDamageStatText.setText(Byte.toString(this.playerReference.getArmorManager().bootsDamageStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.bootsAttackSpeedStatText.setText(Byte.toString(this.playerReference.getArmorManager().bootsAttackSpeedStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.moveSpeedStatText.setText(Byte.toString(this.playerReference.getArmorManager().moveSpeedStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));
        this.jumpHeightStatText.setText(Byte.toString(this.playerReference.getArmorManager().jumpHeightStat.points) + "/" +Integer.toString(ArmorManager.ArmorStat.MAX_POINTS));    

        
        
         //show/hide modifiers       
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
        if(this.playerReference.getArmorManager().seeSecondaryObjectivesModifier.unlocked== false)
         {
             this.seeSecondaryObjectivesModifier.setDisabled(true);
             this.seeSecondaryObjectivesModifier.setHidden(true);
         }
         else
         {
             this.seeSecondaryObjectivesModifier.setDisabled(false);
             this.seeSecondaryObjectivesModifier.setHidden(false);
         }
         if(this.playerReference.getArmorManager().revealSecretAreasModifier.unlocked== false)
         {
             this.revealSecretAreasModifier.setDisabled(true);
             this.revealSecretAreasModifier.setHidden(true);
         }
         else
         {
             this.revealSecretAreasModifier.setDisabled(false);
             this.revealSecretAreasModifier.setHidden(false);
         }
        if(this.playerReference.getArmorManager().doubleCCResistModifier.unlocked== false)
         {
             this.doubleCCResistModifier.setDisabled(true);
             this.doubleCCResistModifier.setHidden(true);
         }
         else
         {
             this.doubleCCResistModifier.setDisabled(false);
             this.doubleCCResistModifier.setHidden(false);
         }
         if(this.playerReference.getArmorManager().doubleHealingModifier.unlocked== false)
         {
             this.doubleHealingModifier.setDisabled(true);
             this.doubleHealingModifier.setHidden(true);
         }
         else
         {
             this.doubleHealingModifier.setDisabled(false);
             this.doubleHealingModifier.setHidden(false);
         }
         if(this.playerReference.getArmorManager().tenPotionsModifier.unlocked== false)
         {
             this.tenPotionsModifier.setDisabled(true);
             this.tenPotionsModifier.setHidden(true);
         }
         else
         {
             this.tenPotionsModifier.setDisabled(false);
             this.tenPotionsModifier.setHidden(false);
         }
        if(this.playerReference.getArmorManager().chanceAbsorbModifier.unlocked== false)
         {
             this.chanceAbsorbModifier.setDisabled(true);
             this.chanceAbsorbModifier.setHidden(true);
         }
         else
         {
             this.chanceAbsorbModifier.setDisabled(false);
             this.chanceAbsorbModifier.setHidden(false);
         }

         if(this.playerReference.getArmorManager().concecutiveHitsModifier.unlocked== false)
         {
             this.concecutiveHitsModifier.setDisabled(true);
             this.concecutiveHitsModifier.setHidden(true);
         }
         else
         {
             this.concecutiveHitsModifier.setDisabled(false);
             this.concecutiveHitsModifier.setHidden(false);
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
        if(this.playerReference.getArmorManager().rangedAttackSlowModifier.unlocked== false)
         {
             this.rangedAttackSlowModifier.setDisabled(true);
             this.rangedAttackSlowModifier.setHidden(true);
         }
         else
         {
             this.rangedAttackSlowModifier.setDisabled(false);
             this.rangedAttackSlowModifier.setHidden(false);
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

        if(this.playerReference.getArmorManager().ccBonusModifier.unlocked== false)
         {
             this.ccBonusModifier.setDisabled(true);
             this.ccBonusModifier.setHidden(true);
         }
         else
         {
             this.ccBonusModifier.setDisabled(false);
             this.ccBonusModifier.setHidden(false);
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
         }
         else
         {
             this.seeEnemyHealthModifier.getImage().removeAllOverlays();
         }
        
    
         if(this.playerReference.getArmorManager().doubleGoldFindModifier.equipped== true)
         {
             this.doubleGoldFindModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
         }
         else
         {
             this.doubleGoldFindModifier.getImage().removeAllOverlays();
         }
        if(this.playerReference.getArmorManager().seeSecondaryObjectivesModifier.equipped== true)
         {
             this.seeSecondaryObjectivesModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
         }
         else
         {
             this.seeSecondaryObjectivesModifier.getImage().removeAllOverlays();
         }
         if(this.playerReference.getArmorManager().revealSecretAreasModifier.equipped== true)
         {
             this.revealSecretAreasModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
         }
         else
         {
             this.revealSecretAreasModifier.getImage().removeAllOverlays();
         }
        if(this.playerReference.getArmorManager().doubleCCResistModifier.equipped== true)
         {
             this.doubleCCResistModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
         }
         else
         {
             this.doubleCCResistModifier.getImage().removeAllOverlays();
         }
         if(this.playerReference.getArmorManager().doubleHealingModifier.equipped== true)
         {
             this.doubleHealingModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
         }
         else
         {
             this.doubleHealingModifier.getImage().removeAllOverlays();
         }
         if(this.playerReference.getArmorManager().tenPotionsModifier.equipped== true)
         {
             this.tenPotionsModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
         }
         else
         {
             this.tenPotionsModifier.getImage().removeAllOverlays();
         }
        if(this.playerReference.getArmorManager().chanceAbsorbModifier.equipped== true)
         {
             this.chanceAbsorbModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
         }
         else
         {
             this.chanceAbsorbModifier.getImage().removeAllOverlays();
         }

         if(this.playerReference.getArmorManager().concecutiveHitsModifier.equipped== true)
         {
             this.concecutiveHitsModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
         }
         else
         {
             this.concecutiveHitsModifier.getImage().removeAllOverlays();
         }
        if(this.playerReference.getArmorManager().meleeAttackDamageModifier.equipped== true)
         {
             this.meleeAttackDamageModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
         }
         else
         {
             this.meleeAttackDamageModifier.getImage().removeAllOverlays();
         }
        if(this.playerReference.getArmorManager().rangedAttackSlowModifier.equipped== true)
         {
             this.rangedAttackSlowModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
         }
         else
         {
             this.rangedAttackSlowModifier.getImage().removeAllOverlays();
         }
         if(this.playerReference.getArmorManager().criticalHitDamageModifier.equipped== true)
         {
             this.criticalHitDamageModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
         }
         else
         {
             this.criticalHitDamageModifier.getImage().removeAllOverlays();
         }

        if(this.playerReference.getArmorManager().ccBonusModifier.equipped== true)
         {
             this.ccBonusModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
         }
         else
         {
             this.ccBonusModifier.getImage().removeAllOverlays();
         }
         if(this.playerReference.getArmorManager().doubleJumpModifier.equipped== true)
         {
             this.doubleJumpModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
         }
         else
         {
             this.doubleJumpModifier.getImage().removeAllOverlays();
         }
         if(this.playerReference.getArmorManager().jetpackModifier.equipped== true)
         {
             this.jetpackModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
         }
         else
         {
             this.jetpackModifier.getImage().removeAllOverlays();
         }
         if(this.playerReference.getArmorManager().teleportModifier.equipped== true)
         {
             this.teleportModifier.getImage().addOverlay("equipped",new Overlay(new Image("painOverlay1.png"))); 
         }
         else
         {
             this.teleportModifier.getImage().removeAllOverlays();
         }
        
        
    
    }
    
    
    
    
    
    
    
    
    private void openTooltip( float x, float y, String name, Image image, String description)
    {
        //remove old components
        this.removeComponent(skillTooltipBackground);
        this.removeComponent(skillTooltipName);
        this.removeComponent(skillTooltipIcon);
        this.removeComponent(skillTooltipTextBlock);
        this.removeComponent(this.infoTextBox);
        
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
        
    }
    
    private void closeTooltip()
    {
        //remove old components
        this.removeComponent(skillTooltipBackground);
        this.removeComponent(skillTooltipName);
        this.removeComponent(skillTooltipIcon);
        this.removeComponent(skillTooltipTextBlock);
        this.removeComponent(infoTextBox);
    }
}
