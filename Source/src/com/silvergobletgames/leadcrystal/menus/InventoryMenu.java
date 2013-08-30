package com.silvergobletgames.leadcrystal.menus;


import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.Window;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager;
import com.silvergobletgames.sylver.windowsystem.TextBlock;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.media.opengl.GL2;



public class InventoryMenu extends Window
{

   //local reference to the player
   private PlayerEntity playerReference;   
   
   //scrap text
   private Label scrapText;
   
   //armor buttons
   private Button ranged;
   private Button melee;
   private Button helm;
   private Button chest;
   private Button boots;
   
    //item hovered tooltip
    private Button skillTooltipBackground;
    private Label skillTooltipName;
    private Button skillTooltipIcon;
    private TextBlock skillTooltipTextBlock;

   

    /**
    * inventory menu constructor.
    * @param player reference to the player
    * 
    */
    public InventoryMenu(float x, float y,PlayerEntity player)
    {
       //super constructor call, setting the background sprite and initial position
       super(new Image("inventoryScreen1.png"),x,y,550,800);
       
       //get new player referene
       playerReference =player;
       
     
       
       
       //================
       // Currency Text
       //================
       
       //currency text
       Text te = new Text(Integer.toString(playerReference.getCurrencyManager().getBalence()));
       te.setScale(1.2f);
       this.scrapText = new Label(te, 175, 90);
       this.addComponent(scrapText);
       
       
       
       //==============
       // Armor Area
       //=============
       
       this.melee = new Button(new Image("blank.png"),155,445,50,50);
       melee.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   if(playerReference.getArmorManager().getModifierEquipped() != null)
                   {
                       openTooltip(playerReference.getArmorManager().getModifierEquipped(),-250,445);
                   }
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(melee);
       
       this.ranged = new Button(new Image("blank.png"),370,445,50,50);
       ranged.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   if(playerReference.getArmorManager().getAttachmentEquipped() != null)
                   {
                       openTooltip(playerReference.getArmorManager().getAttachmentEquipped(),-30,445);
                   }
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(ranged);
       
       this.helm = new Button(new Image("blank.png"),245,570,80,80);
       helm.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   if(playerReference.getArmorManager().getHelmEquipped() != null)
                   {
                       openTooltip(playerReference.getArmorManager().getHelmEquipped(),-160,570);
                   }
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(helm);
       
       this.chest = new Button(new Image("blank.png"),240,410,85,100);
       chest.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   if(playerReference.getArmorManager().getChestEquipped() != null)
                   {
                       openTooltip(playerReference.getArmorManager().getChestEquipped(),-160,410);
                   }
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(chest);
       
       this.boots = new Button(new Image("blank.png"),260,270,65,65);
       boots.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("mouseEntered"))
               {
                   if(playerReference.getArmorManager().getBootsEquipped() != null)
                   {
                       openTooltip(playerReference.getArmorManager().getBootsEquipped(),-140,270);
                   }
               }
               if(e.getActionCommand().equals("mouseExited"))
               {
                   closeTooltip();
               }
           }
       });
       this.addComponent(boots);
                      
    }
    
    public void draw(GL2 gl)
    {
        super.draw(gl); 
    }
    
    public void update()
    {
       super.update();
       
       //======================
       // currency text update
       //======================
       this.scrapText.getText().setText(Integer.toString(playerReference.getCurrencyManager().getBalence()));
       
       //================
       // Armor Update
       //================
       
       if(playerReference.getArmorManager().getAttachmentEquipped() != null)
       {
           this.ranged.setImage(playerReference.getArmorManager().getAttachmentEquipped().image.copy());
       }
       if(playerReference.getArmorManager().getModifierEquipped() != null)
       {
           this.melee.setImage(playerReference.getArmorManager().getModifierEquipped().image.copy());
       }
       if(playerReference.getArmorManager().getHelmEquipped() != null)
       {
           this.helm.setImage(playerReference.getArmorManager().getHelmEquipped().image.copy());
       }
       if(playerReference.getArmorManager().getChestEquipped() != null)
       {
           this.chest.setImage(playerReference.getArmorManager().getChestEquipped().image.copy());
       }
       if(playerReference.getArmorManager().getBootsEquipped() != null)
       {
           this.boots.setImage(playerReference.getArmorManager().getBootsEquipped().image.copy());
       }

    }
    
    private void openTooltip(ArmorManager.Armor armor, float x, float y)
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


