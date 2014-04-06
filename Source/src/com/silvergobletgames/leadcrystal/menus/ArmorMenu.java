package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager;
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
        
        //add helm components
        this.tabPane.addComponent(new Button(new Text("Upgrades"), 600, 500), 0);
        
        final ArmorStat stat = this.playerReference.getArmorManager().helmHealthStat;
        Button b = new Button(stat.image.copy(), 600, 350, 100, 100);
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
        this.tabPane.addComponent(new Label(this.helmHealthStatText,625,350), 0);
        
        final ArmorStat stat2 = this.playerReference.getArmorManager().helmDamageReductionStat;
        b = new Button(stat2.image.copy(), 725, 350, 100, 100);
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
        this.tabPane.addComponent(new Label(this.helmDamageReductionStatText,750,350), 0);
        
        ArmorStat stat3 = this.playerReference.getArmorManager().ccReductionStat;
        b = new Button(stat3.image.copy(), 600, 225, 100, 100);
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
        this.tabPane.addComponent(new Label(this.ccReductionStatText,625,250), 0);
        
        ArmorStat stat4 = this.playerReference.getArmorManager().healingEffectivenessStat;
        b = new Button(stat4.image.copy(), 725, 225, 100, 100);
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
        this.tabPane.addComponent(new Label(this.healingEffectivenessStatText,750,250), 0);
        
        
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
