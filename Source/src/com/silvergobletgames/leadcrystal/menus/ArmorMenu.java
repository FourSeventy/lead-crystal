package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorModifier;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorStat;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.leadcrystal.skills.Skill;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
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
        b = new Button(modifier.image.copy(),200,225,100,100);
        b.addActionListener(new ActionListener(){     
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
        this.tabPane.addComponent(b,0);
        
        ArmorModifier modifier2 = this.playerReference.getArmorManager().doubleGoldFindModifier;
        b = new Button(modifier2.image.copy(),325,225,100,100);
        b.addActionListener(new ActionListener(){     
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
        this.tabPane.addComponent(b,0);
        
        ArmorModifier modifier3 = this.playerReference.getArmorManager().revealSecretAreasModifier;
        b = new Button(modifier3.image.copy(),200,100,100,100);
        b.addActionListener(new ActionListener(){     
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
        this.tabPane.addComponent(b,0);
        
        ArmorModifier modifier4 = this.playerReference.getArmorManager().seeSecondaryObjectivesModifier;
        b = new Button(modifier4.image.copy(),325,100,100,100);
        b.addActionListener(new ActionListener(){     
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
        this.tabPane.addComponent(b,0);
        
        
        //=========================
        //Body Modifier Components
        //=========================
        
        this.tabPane.addComponent(new Button(new Text("Modifiers:"), 200, 400), 1);
        
        ArmorModifier modifier5 = this.playerReference.getArmorManager().doubleCCResistModifier;      
        b = new Button(modifier5.image.copy(),200,225,100,100);
        b.addActionListener(new ActionListener(){     
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
        this.tabPane.addComponent(b,1);
        
        ArmorModifier modifier6 = this.playerReference.getArmorManager().doubleHealingModifier;
        b = new Button(modifier6.image.copy(),325,225,100,100);
        b.addActionListener(new ActionListener(){     
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
        this.tabPane.addComponent(b,1);
        
        ArmorModifier modifier7 = this.playerReference.getArmorManager().tenPotionsModifier;
        b = new Button(modifier7.image.copy(),200,100,100,100);
        b.addActionListener(new ActionListener(){     
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
        this.tabPane.addComponent(b,1);
        
        ArmorModifier modifier8 = this.playerReference.getArmorManager().chanceAbsorbModifier;
        b = new Button(modifier8.image.copy(),325,100,100,100);
        b.addActionListener(new ActionListener(){     
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
        this.tabPane.addComponent(b,1);
        
        //=================
        // Body Components
        //=================
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
        
        //===================
        // Weapon Components
        //===================
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
        
        //===================
        // Boots Components
        //===================
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
