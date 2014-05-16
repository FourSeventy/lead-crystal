package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager;
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
    

    
     //weapon
    private Button meleeAttackDamageBonusButton;
    private Button potionCooldownResetButton;
    private Button criticalHitDamageButton;
    private Button weaponDamageButton;
    private Button weaponAttackSpeedButton;
    private Button critChanceButton;
    
    private Label meleeAttackDamageBonusLabel;
    private Label potionCooldownResetLabel;
    private Label criticalHitDamageLabel;
    private Label weaponDamageLabel;
    private Label weaponAttackSpeedLabel;
    private Label critChanceLabel;
    

    //helm upgrades
    private Button seeEnemyHealthButton;
    private Button doubleGoldFindButton;
    private Button upgradeRadarButton;
    private Button lifeLeechButton;
    private Button healingEffectivenessButton;
    
    private Label seeEnemyHealthLabel;
    private Label doubleGoldFindLabel;
    private Label upgradeRadarLabel;
    private Label lifeLeechLabel;
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
  
    
    
    
    public ArmorMenu( PlayerEntity player, float x, float y)
    {
        super(new Image("armorMenu.png"),x,y,1200,900);
        
        this.playerReference = player;
        
        
        //========================
        // Basic Labels and Image
        //========================
        
        //body image
        Image bodyImg = new Image("bash_black.png");
        bodyImg.setScale(1.5f);
        Button bodyImage = new Button(bodyImg, 250, 250, bodyImg.getWidth() * 1.5f, bodyImg.getHeight() * 1.5f);
        this.addComponent(bodyImage);
        
        //Weapon Label
        Text weaponText = new Text("Weapon Upgrades",LeadCrystalTextType.HUD28);
        Label weaponLabel = new Label(weaponText, 150, 700);
        this.addComponent(weaponLabel);
        //Helm Label
        Text helmText = new Text("Helm Upgrades",LeadCrystalTextType.HUD28);
        Label helmLabel = new Label(helmText, 800, 700);
        this.addComponent(helmLabel);
        //body Label
        Text bodyText = new Text("Body Upgrades",LeadCrystalTextType.HUD28);
        Label bodyLabel = new Label(bodyText, 150, 300);
        this.addComponent(bodyLabel);
        //boots Label
        Text bootsText = new Text("Boots Upgrades",LeadCrystalTextType.HUD28);
        Label bootsLabel = new Label(bootsText, 800, 300);
        this.addComponent(bootsLabel);
        
        
        //=================
        // Weapon Upgrades
        //=================

        int weaponBasePositionX = 150;
        int weaponBasePositionY = 600;
        
        
        final ArmorStat stat1 = this.playerReference.getArmorManager().weaponDamage;
        Button b = new Button(stat1.image.copy(), weaponBasePositionX, weaponBasePositionY, 75, 75);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //buy stat
                   ((GameClientScene)owningScene).sendBuyStatPacket(stat1.id);
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(700,450,stat1.name,stat1.image,stat1.description, stat1.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        this.weaponDamageLabel = new Label(new Text(Byte.toString(stat1.points) + "/" +Integer.toString(stat1.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y); 
        this.addComponent(weaponDamageLabel); 
        
        final ArmorStat stat2 = this.playerReference.getArmorManager().weaponAttackSpeed;
        b = new Button(stat2.image.copy(), weaponBasePositionX + 100, weaponBasePositionY, 75, 75);
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
                   openTooltip(700,450,stat2.name,stat2.image,stat2.description, stat2.cost);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        this.weaponAttackSpeedLabel = new Label(new Text(Byte.toString(stat2.points) + "/" +Integer.toString(stat2.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y); 
        this.addComponent(weaponAttackSpeedLabel);
        
        final ArmorStat stat3 = this.playerReference.getArmorManager().critChance;
        b = new Button(stat3.image.copy(), weaponBasePositionX + 200, weaponBasePositionY, 75, 75);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
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
        this.addComponent(b);
        this.critChanceLabel = new Label(new Text(Byte.toString(stat3.points) + "/" +Integer.toString(stat3.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y); 
        this.addComponent(critChanceLabel);
        
        final ArmorStat stat4 = this.playerReference.getArmorManager().meleeAttackDamageBonus;
        b = new Button(stat4.image.copy(), weaponBasePositionX, weaponBasePositionY - 100, 75, 75);
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
        this.addComponent(b);
        this.meleeAttackDamageBonusLabel = new Label(new Text(Byte.toString(stat4.points) + "/" +Integer.toString(stat4.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y ); 
        this.addComponent(meleeAttackDamageBonusLabel); 
        
        final ArmorStat stat5 = this.playerReference.getArmorManager().potionCooldownReset;
        b = new Button(stat5.image.copy(), weaponBasePositionX + 100, weaponBasePositionY - 100, 75, 75);
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
        this.addComponent(b);
        this.potionCooldownResetLabel = new Label(new Text(Byte.toString(stat5.points) + "/" +Integer.toString(stat5.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y ); 
        this.addComponent(potionCooldownResetLabel); 
        
        final ArmorStat stat6 = this.playerReference.getArmorManager().criticalHitDamage;
        b = new Button(stat6.image.copy(), weaponBasePositionX + 200, weaponBasePositionY - 100, 75, 75);
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
        this.addComponent(b);
        this.criticalHitDamageLabel = new Label(new Text(Byte.toString(stat6.points) + "/" +Integer.toString(stat6.maxPoints),LeadCrystalTextType.HUD20),b.getWindowRelativePosition().x + 30,b.getWindowRelativePosition().y ); 
        this.addComponent(criticalHitDamageLabel); 
        
        

       
    }
    
    @Override
    public void update()
    {
        super.update();
         
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
