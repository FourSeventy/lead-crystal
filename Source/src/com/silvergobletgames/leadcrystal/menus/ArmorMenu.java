package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.Window;
import com.silvergobletgames.sylver.windowsystem.WindowComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager;
import com.silvergobletgames.leadcrystal.items.ArmorManager.Armor;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorID;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.leadcrystal.skills.Skill;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.*;

/**
 *
 * @author Mike
 */
public class ArmorMenu extends Window{
 
    PlayerEntity playerReference;
    
    //item hovered tooltip
    private Button skillTooltipBackground;
    private Label skillTooltipName;
    private Button skillTooltipIcon;
    private TextBlock skillTooltipTextBlock;
    private Label infoTextBox;
    
    private ArrayList<WindowComponent> ranged1LockedComponents = new ArrayList();
    private ArrayList<WindowComponent> ranged2LockedComponents = new ArrayList();
    private ArrayList<WindowComponent> ranged3LockedComponents = new ArrayList();
    private ArrayList<WindowComponent> ranged4LockedComponents = new ArrayList();
    
    private ArrayList<WindowComponent> melee1LockedComponents = new ArrayList();
    private ArrayList<WindowComponent> melee2LockedComponents = new ArrayList();
    private ArrayList<WindowComponent> melee3LockedComponents = new ArrayList();
    private ArrayList<WindowComponent> melee4LockedComponents = new ArrayList();
    
    private ArrayList<WindowComponent> helm1LockedComponents = new ArrayList();
    private ArrayList<WindowComponent> helm2LockedComponents = new ArrayList();
    private ArrayList<WindowComponent> helm3LockedComponents = new ArrayList();
    private ArrayList<WindowComponent> helm4LockedComponents = new ArrayList();
    
    private ArrayList<WindowComponent> chest1LockedComponents = new ArrayList();
    private ArrayList<WindowComponent> chest2LockedComponents = new ArrayList();
    private ArrayList<WindowComponent> chest3LockedComponents = new ArrayList();
    private ArrayList<WindowComponent> chest4LockedComponents = new ArrayList();
    
    private ArrayList<WindowComponent> boots1LockedComponents = new ArrayList();
    private ArrayList<WindowComponent> boots2LockedComponents = new ArrayList();
    private ArrayList<WindowComponent> boots3LockedComponents = new ArrayList();
    private ArrayList<WindowComponent> boots4LockedComponents = new ArrayList();
    
    public ArmorMenu( PlayerEntity player, float x, float y)
    {
        super(new Image("armorMenu.png"),x,y,1000,800);
        
        this.playerReference = player;
        
        //================
        // Ranged Row
        //================
        
        //ranged1
        final Armor armor = player.getArmorManager().getArmor(ArmorID.ATTACHMENT1);
        Button b = new Button(armor.image.copy(),85,640,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.ATTACHMENT1))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.ATTACHMENT1); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.ATTACHMENT1); 
                   
                   //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.ATTACHMENT1).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor,ArmorID.ATTACHMENT1,220,450);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
        });
        this.addComponent(b);           
        //locked components
        b = new Button(new Image("black.png"),20,600,230,130);  
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        ranged1LockedComponents.add(b);
        b = new Button(new Image("currency2.png"),85,610,20,20);
        this.addComponent(b);
        ranged1LockedComponents.add(b);
        Label label = new Label(Integer.toString(armor.currencyCost),110,612);
        label.getText().setScale(.9f);
        this.addComponent(label);
        ranged1LockedComponents.add(label);
             
        
        //ranged2
        final Armor armor2 = player.getArmorManager().getArmor(ArmorID.ATTACHMENT2);
        b = new Button(armor2.image.copy(),320,640,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.ATTACHMENT2))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.ATTACHMENT2); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.ATTACHMENT2); 
                   
                   //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.ATTACHMENT2).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor2,ArmorID.ATTACHMENT2,455,450);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);      
        //locked components
        b = new Button(new Image("black.png"),255,600,230,130); 
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        ranged2LockedComponents.add(b);     
        b = new Button(new Image("currency2.png"),325,610,20,20);
        this.addComponent(b);
        ranged2LockedComponents.add(b);
        label = new Label(Integer.toString(armor2.currencyCost),350,612);
        label.getText().setScale(.9f);
        this.addComponent(label);
        ranged2LockedComponents.add(label);
       
        //ranged3
        final Armor armor3 = player.getArmorManager().getArmor(ArmorID.ATTACHMENT3);
        b = new Button(armor3.image.copy(),560,640,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.ATTACHMENT3))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.ATTACHMENT3); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.ATTACHMENT3); 
                   
                   //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.ATTACHMENT3).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor3,ArmorID.ATTACHMENT3,700,450);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);        
        //locked components
        b = new Button(new Image("black.png"),495,600,230,130);   
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        ranged3LockedComponents.add(b);      
        b = new Button(new Image("currency2.png"),565,610,20,20);
        this.addComponent(b);
        ranged3LockedComponents.add(b);
        label = new Label(Integer.toString(armor3.currencyCost),590,612);
        label.getText().setScale(.9f);
        this.addComponent(label);
        ranged3LockedComponents.add(label);

        
        //ranged4
        final Armor armor4 = player.getArmorManager().getArmor(ArmorID.ATTACHMENT4);
        b = new Button(armor4.image.copy(),800,640,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.ATTACHMENT4))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.ATTACHMENT4); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.ATTACHMENT4);
                   
                   //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.ATTACHMENT4).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor4,ArmorID.ATTACHMENT4,935,450);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);                      
        //locked componenets 
        b = new Button(new Image("black.png"),735,600,230,130);  
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        ranged4LockedComponents.add(b); 
        b = new Button(new Image("currency2.png"),805,610,20,20);
        this.addComponent(b);
        ranged4LockedComponents.add(b);
        label = new Label(Integer.toString(armor4.currencyCost),830,612);
        label.getText().setScale(.9f);
        this.addComponent(label);
        ranged4LockedComponents.add(label);

        
        
        
        //================
        // Melee Row
        //================
        
        //melee1
        final Armor armor5 = player.getArmorManager().getArmor(ArmorID.MODIFIER1);
        b = new Button(armor5.image.copy(),80,490,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.MODIFIER1))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.MODIFIER1); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.MODIFIER1); 
                   
                   //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.MODIFIER1).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor5,ArmorID.MODIFIER1,220,300);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);     
        //locked components
        b = new Button(new Image("black.png"),20,450,230,130);  
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        melee1LockedComponents.add(b);      
        b = new Button(new Image("currency2.png"),85,460,20,20);
        this.addComponent(b);
        melee1LockedComponents.add(b);
        label = new Label(Integer.toString(armor5.currencyCost),110,462);
        label.getText().setScale(.9f);
        this.addComponent(label);
        melee1LockedComponents.add(label);


        
        //melee2
        final Armor armor6 = player.getArmorManager().getArmor(ArmorID.MODIFIER2);
        b = new Button(armor6.image.copy(),320,490,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.MODIFIER2))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.MODIFIER2); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.MODIFIER2); 
                   
                   //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.MODIFIER2).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor6,ArmorID.MODIFIER2,460,300);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        //locked componenets
        b = new Button(new Image("black.png"),260,450,230,130); 
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        melee2LockedComponents.add(b);     
        b = new Button(new Image("currency2.png"),325,460,20,20);
        this.addComponent(b);
        melee2LockedComponents.add(b);
        label = new Label(Integer.toString(armor6.currencyCost),350,462);
        label.getText().setScale(.9f);
        this.addComponent(label);
        melee2LockedComponents.add(label);

        
        //melee3
        final Armor armor7 = player.getArmorManager().getArmor(ArmorID.MODIFIER3);
        b = new Button(armor7.image.copy(),560,490,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.MODIFIER3))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.MODIFIER3); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.MODIFIER3);
                   
                    //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.MODIFIER3).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor7,ArmorID.MODIFIER3,700,300);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        //locked components
        b = new Button(new Image("black.png"),495,450,230,130); 
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        melee3LockedComponents.add(b);     
        b = new Button(new Image("currency2.png"),565,460,20,20);
        this.addComponent(b);
        melee3LockedComponents.add(b);
        label = new Label(Integer.toString(armor7.currencyCost),590,462);
        label.getText().setScale(.9f);
        this.addComponent(label);
        melee3LockedComponents.add(label);
        
        
        //melee4
        final Armor armor8 = player.getArmorManager().getArmor(ArmorID.MODIFIER4);
        b = new Button(armor8.image.copy(),800,490,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.MODIFIER4))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.MODIFIER4); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.MODIFIER4); 
                   
                    //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.MODIFIER4).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor8,ArmorID.MODIFIER4,935,300);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        //locked components
        b = new Button(new Image("black.png"),735,450,230,130);
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        melee4LockedComponents.add(b);   
        b = new Button(new Image("currency2.png"),805,460,20,20);
        this.addComponent(b);
        melee4LockedComponents.add(b);
        label = new Label(Integer.toString(armor8.currencyCost),830,462);
        label.getText().setScale(.9f);
        this.addComponent(label);
        melee4LockedComponents.add(label);
        

        
        
        //================
        // Helm Row
        //================
        
        //helm1
        final Armor armor9 = player.getArmorManager().getArmor(ArmorID.HELM1);
        b = new Button(armor9.image.copy(),80,350,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.HELM1))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.HELM1); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.HELM1); 
                   
                    //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.HELM1).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor9,ArmorID.HELM1,220,160);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        //locked components
        b = new Button(new Image("black.png"),20,310,230,130); 
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        helm1LockedComponents.add(b);      
        b = new Button(new Image("currency2.png"),85,320,20,20);
        this.addComponent(b);
        helm1LockedComponents.add(b);
        label = new Label(Integer.toString(armor9.currencyCost),110,322);
        label.getText().setScale(.9f);
        this.addComponent(label);
        helm1LockedComponents.add(label);
        


        
        //helm2
        final Armor  armor10 = player.getArmorManager().getArmor(ArmorID.HELM2);
        b = new Button(armor10.image.copy(),320,350,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.HELM2))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.HELM2); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.HELM2); 
                   
                    //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.HELM2).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor10,ArmorID.HELM2,455,160);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        //locked components
        b = new Button(new Image("black.png"),255,310,230,130);  
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        helm2LockedComponents.add(b);     
        b = new Button(new Image("currency2.png"),325,320,20,20);
        this.addComponent(b);
        helm2LockedComponents.add(b);
        label = new Label(Integer.toString(armor10.currencyCost),350,322);
        label.getText().setScale(.9f);
        this.addComponent(label);
        helm2LockedComponents.add(label);
        

        
        //helm3
        final Armor armor11 = player.getArmorManager().getArmor(ArmorID.HELM3);
        b = new Button(armor11.image.copy(),560,350,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.HELM3))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.HELM3); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.HELM3); 
                   
                    //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.HELM3).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor11,ArmorID.HELM3,700,160);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        //locked components
        b = new Button(new Image("black.png"),495,310,230,130); 
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        helm3LockedComponents.add(b);      
        b = new Button(new Image("currency2.png"),565,320,20,20);
        this.addComponent(b);
        helm3LockedComponents.add(b);
        label = new Label(Integer.toString(armor11.currencyCost),590,322);
        label.getText().setScale(.9f);
        this.addComponent(label);
        helm3LockedComponents.add(label);
        

        
        //helm4
        final Armor armor12 = player.getArmorManager().getArmor(ArmorID.HELM4);
        b = new Button(armor12.image.copy(),800,350,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.HELM4))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.HELM4); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.HELM4); 
                   
                    //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.HELM4).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor12,ArmorID.HELM4,935,160);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        //locked components
        b = new Button(new Image("black.png"),735,310,230,130);
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        helm4LockedComponents.add(b);
        b = new Button(new Image("currency2.png"),805,320,20,20);
        this.addComponent(b);
        helm4LockedComponents.add(b);
        label = new Label(Integer.toString(armor12.currencyCost),830,322);
        label.getText().setScale(.9f);
        this.addComponent(label);
        helm4LockedComponents.add(label);
        

        
        
        //================
        // Chest Row
        //================
        
        //chest1
        final Armor armor13 = player.getArmorManager().getArmor(ArmorID.CHEST1);
        b = new Button(armor13.image.copy(),80,200,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.CHEST1))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.CHEST1); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.CHEST1); 
                   
                    //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.CHEST1).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor13,ArmorID.CHEST1,220,80);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        //locked components
        b = new Button(new Image("black.png"),20,150,230,130);
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        chest1LockedComponents.add(b);   
        b = new Button(new Image("currency2.png"),85,170,20,20);
        this.addComponent(b);
        chest1LockedComponents.add(b);
        label = new Label(Integer.toString(armor13.currencyCost),110,172);
        label.getText().setScale(.9f);
        this.addComponent(label);
        chest1LockedComponents.add(label);


        
        //chest2
        final Armor armor14 = player.getArmorManager().getArmor(ArmorID.CHEST2);
        b = new Button(armor14.image.copy(),320,200,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.CHEST2))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.CHEST2); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.CHEST2); 
                   
                     //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.CHEST2).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor14,ArmorID.CHEST2,455,80);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        //locked components
        b = new Button(new Image("black.png"),255,150,230,130);
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        chest2LockedComponents.add(b);
        b = new Button(new Image("currency2.png"),325,170,20,20);
        this.addComponent(b);
        chest2LockedComponents.add(b);
        label = new Label(Integer.toString(armor14.currencyCost),350,172);
        label.getText().setScale(.9f);
        this.addComponent(label);
        chest2LockedComponents.add(label);

        
        //chest3
        final Armor armor15 = player.getArmorManager().getArmor(ArmorID.CHEST3);
        b = new Button(armor15.image.copy(),560,200,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.CHEST3))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.CHEST3); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.CHEST3); 
                   
                      //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.CHEST3).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor15,ArmorID.CHEST3,700,80);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        //locked components
        b = new Button(new Image("black.png"),495,150,230,130);
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        chest3LockedComponents.add(b);
        b = new Button(new Image("currency2.png"),565,170,20,20);
        this.addComponent(b);
        chest3LockedComponents.add(b);
        label = new Label(Integer.toString(armor15.currencyCost),590,172);
        label.getText().setScale(.9f);
        this.addComponent(label);
        chest3LockedComponents.add(label);
        

        
        //chest4
        final Armor armor16 = player.getArmorManager().getArmor(ArmorID.CHEST4);
        b = new Button(armor16.image.copy(),800,200,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.CHEST4))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.CHEST4); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.CHEST4);  
                   
                       //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.CHEST4).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor16,ArmorID.CHEST4,935,80);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        //locked components
        b = new Button(new Image("black.png"),735,150,230,130);
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        chest4LockedComponents.add(b);
        b = new Button(new Image("currency2.png"),805,170,20,20);
        this.addComponent(b);
        chest4LockedComponents.add(b);
        label = new Label(Integer.toString(armor16.currencyCost),830,172);
        label.getText().setScale(.9f);
        this.addComponent(label);
        chest4LockedComponents.add(label);
        

        //================
        // Boots Row
        //================
        
        //boots1
        final Armor armor17 = player.getArmorManager().getArmor(ArmorID.BOOTS1);
        b = new Button(armor17.image.copy(),80,50,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                  //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.BOOTS1))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.BOOTS1); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.BOOTS1); 
                   
                    //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.BOOTS1).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor17,ArmorID.BOOTS1,220,5);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        //locked components
        b = new Button(new Image("black.png"),20,5,230,130);
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        boots1LockedComponents.add(b);    
        b = new Button(new Image("currency2.png"),85,20,20,20);
        this.addComponent(b);
        boots1LockedComponents.add(b);
        label = new Label(Integer.toString(armor17.currencyCost),110,22);
        label.getText().setScale(.9f);
        this.addComponent(label);
        boots1LockedComponents.add(label);
        
        
        //boots2
        final Armor armor18 = player.getArmorManager().getArmor(ArmorID.BOOTS2);
        b = new Button(armor18.image.copy(),320,50,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.BOOTS2))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.BOOTS2); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.BOOTS2);
                   
                    //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.BOOTS2).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor18,ArmorID.BOOTS2,450,5);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        //locked components
        b = new Button(new Image("black.png"),255,5,230,130);
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        boots2LockedComponents.add(b);
        b = new Button(new Image("currency2.png"),325,20,20,20);
        this.addComponent(b);
        boots2LockedComponents.add(b);
        label = new Label(Integer.toString(armor18.currencyCost),350,22);
        label.getText().setScale(.9f);
        this.addComponent(label);
        boots2LockedComponents.add(label);
        

        
        //boots3
        final Armor armor19 = player.getArmorManager().getArmor(ArmorID.BOOTS3);
        b = new Button(armor19.image.copy(),560,50,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.BOOTS3))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.BOOTS3); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.BOOTS3); 
                   
                     //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.BOOTS3).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armor19,ArmorID.BOOTS3,700,5);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        //locked components
        b = new Button(new Image("black.png"),495,5,230,130);
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        boots3LockedComponents.add(b); 
        b = new Button(new Image("currency2.png"),565,20,20,20);
        this.addComponent(b);
        boots3LockedComponents.add(b);
        label = new Label(Integer.toString(armor19.currencyCost),590,22);
        label.getText().setScale(.9f);
        this.addComponent(label);
        boots3LockedComponents.add(label);
       
        
        //boots4
        final Armor armo20 = player.getArmorManager().getArmor(ArmorID.BOOTS4);
        b = new Button(armo20.image.copy(),800,50,60,70);
        b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   //if the armor is not unlocked, buy it, else equip it
                   if(!playerReference.getArmorManager().isUnlocked(ArmorID.BOOTS4))
                      ((GameClientScene)owningScene).sendBuyArmorPacket(ArmorID.BOOTS4); 
                   else
                      ((GameClientScene)owningScene).sendArmorAdjustPacket(ArmorID.BOOTS4);  
                   
                        //client side purchace check for error messages and info box text
                   if(playerReference.getArmorManager().getArmor(ArmorID.BOOTS4).currencyCost <= playerReference.getCurrencyManager().getBalence())
                   {
                      infoTextBox.getText().setText("Click to equip this item"); 
                   }
                   else
                   {
                       //error message
                   }
               }
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   openTooltip(armo20,ArmorID.BOOTS4,935,5);
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
        this.addComponent(b);
        //locked components
        b = new Button(new Image("black.png"),735,5,230,130);
        b.getImage().setColor(new Color(1,1,1,.6f));
        b.dontKillClick = true;
        this.addComponent(b);
        boots4LockedComponents.add(b);
        b = new Button(new Image("currency2.png"),805,20,20,20);
        this.addComponent(b);
        boots4LockedComponents.add(b);
        label = new Label(Integer.toString(armo20.currencyCost),830,22);
        label.getText().setScale(.9f);
        this.addComponent(label);
        boots4LockedComponents.add(label);

        
        
        
    }
    
    @Override
    public void update()
    {
        super.update();
        
        //=========================
        // Ranged Locked Components
        //==========================
        if(!ranged1LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.ATTACHMENT1))
        {
            for(WindowComponent wc: ranged1LockedComponents)
                this.removeComponent(wc);
            
            ranged1LockedComponents.clear();
        }
        if(!ranged2LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.ATTACHMENT2))
        {
            for(WindowComponent wc: ranged2LockedComponents)
                this.removeComponent(wc);
            
            ranged2LockedComponents.clear();
        }
        if(!ranged3LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.ATTACHMENT3))
        {
            for(WindowComponent wc: ranged3LockedComponents)
                this.removeComponent(wc);
            
            ranged3LockedComponents.clear();
        }
        if(!ranged4LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.ATTACHMENT4))
        {
            for(WindowComponent wc: ranged4LockedComponents)
                this.removeComponent(wc);
            
            ranged4LockedComponents.clear();
        }
        
        //=========================
        // Melee Locked Components
        //==========================
        if(!melee1LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.MODIFIER1))
        {
            for(WindowComponent wc: melee1LockedComponents)
                this.removeComponent(wc);
            
            melee1LockedComponents.clear();
        }
        if(!melee2LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.MODIFIER2))
        {
            for(WindowComponent wc: melee2LockedComponents)
                this.removeComponent(wc);
            
            melee2LockedComponents.clear();
        }
        if(!melee3LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.MODIFIER3))
        {
            for(WindowComponent wc: melee3LockedComponents)
                this.removeComponent(wc);
            
            melee3LockedComponents.clear();
        }
        if(!melee4LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.MODIFIER4))
        {
            for(WindowComponent wc: melee4LockedComponents)
                this.removeComponent(wc);
            
            melee4LockedComponents.clear();
        }
        
        
        //=========================
        // Helm Locked Components
        //==========================
        if(!helm1LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.HELM1))
        {
            for(WindowComponent wc: helm1LockedComponents)
                this.removeComponent(wc);
            
            helm1LockedComponents.clear();
        }
        if(!helm2LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.HELM2))
        {
            for(WindowComponent wc: helm2LockedComponents)
                this.removeComponent(wc);
            
            helm2LockedComponents.clear();
        }
        if(!helm3LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.HELM3))
        {
            for(WindowComponent wc: helm3LockedComponents)
                this.removeComponent(wc);
            
            helm3LockedComponents.clear();
        }
        if(!helm4LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.HELM4))
        {
            for(WindowComponent wc: helm4LockedComponents)
                this.removeComponent(wc);
            
            helm4LockedComponents.clear();
        }
        
        //=========================
        // Helm Locked Components
        //==========================
        if(!chest1LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.CHEST1))
        {
            for(WindowComponent wc: chest1LockedComponents)
                this.removeComponent(wc);
            
            chest1LockedComponents.clear();
        }
        if(!chest2LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.CHEST2))
        {
            for(WindowComponent wc: chest2LockedComponents)
                this.removeComponent(wc);
            
            chest2LockedComponents.clear();
        }
        if(!chest3LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.CHEST3))
        {
            for(WindowComponent wc: chest3LockedComponents)
                this.removeComponent(wc);
            
            chest3LockedComponents.clear();
        }
        if(!chest4LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.CHEST4))
        {
            for(WindowComponent wc: chest4LockedComponents)
                this.removeComponent(wc);
            
            chest4LockedComponents.clear();
        }
        
        //=========================
        // Helm Locked Components
        //==========================
        if(!boots1LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.BOOTS1))
        {
            for(WindowComponent wc: boots1LockedComponents)
                this.removeComponent(wc);
            
            boots1LockedComponents.clear();
        }
        if(!boots2LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.BOOTS2))
        {
            for(WindowComponent wc: boots2LockedComponents)
                this.removeComponent(wc);
            
            boots2LockedComponents.clear();
        }
        if(!boots3LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.BOOTS3))
        {
            for(WindowComponent wc: boots3LockedComponents)
                this.removeComponent(wc);
            
            boots3LockedComponents.clear();
        }
        if(!boots4LockedComponents.isEmpty() && playerReference.getArmorManager().isUnlocked(ArmorID.BOOTS4))
        {
            for(WindowComponent wc: boots4LockedComponents)
                this.removeComponent(wc);
            
            boots4LockedComponents.clear();
        }
        
    }
    
    private void openTooltip(Armor armor, ArmorID id, float x, float y)
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
        Text text = new Text(armor.name);
        text.setScale(1.3f);
        skillTooltipName = new Label(text, x + 200 - text.getWidth()/2, y + 250);
        this.addComponent(skillTooltipName);
        
        //icon
        skillTooltipIcon = new Button(armor.image.copy(),x + 25, y + 160,50,50);
        this.addComponent(skillTooltipIcon);
        
        //description
        text = new Text(armor.description);
        skillTooltipTextBlock = new TextBlock(x + 85, y + 190, 300, text);
        this.addComponent(skillTooltipTextBlock);
        
        if(!playerReference.getArmorManager().isUnlocked(id))
        {
            text = new Text("Click to purchase this item");
            infoTextBox = new Label( text,x + 50, y + 20  );
            this.addComponent(infoTextBox);
        }
        else
        {
            text = new Text("Click to equip this item");
            infoTextBox = new Label( text,x + 50, y + 20 );
            this.addComponent(infoTextBox);
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
    }
}
