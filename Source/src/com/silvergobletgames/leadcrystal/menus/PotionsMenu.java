package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.Potion;
import com.silvergobletgames.leadcrystal.items.PotionManager;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Mike
 */
public class PotionsMenu extends Window{
    
    private PlayerEntity playerReference;
    private Label playerGoldLabel;
 
    public PotionsMenu( final PlayerEntity playerReference,float x, float y)
    {
        super(new Image("tallFrame.png"),x,y,550,900);
        
        this.playerReference = playerReference;
        
        //text
        Text menuText = new Text("Health Potions",LeadCrystalTextType.HUD34);
        Label menuTextLabel = new Label(menuText,275 - menuText.getWidth()/2,820);
        this.addComponent(menuTextLabel);
        
        //close
        final Image closeImage = new Image("closeButton.png");
        Button closeButton = new Button(closeImage,504,867,closeImage.getWidth()+1,closeImage.getHeight());
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
        
        //potion
        final Potion potion = new Potion();
        potion.getImage().removeAllImageEffects();
        final Image potionImage = potion.getImage();
       
        //potion button
        final Image section = new Image("big_potion_button.png");
        Button sectionButton = new Button(section,275 - section.getWidth()/2,300,section.getWidth(),section.getHeight());
        sectionButton.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if (e.getActionCommand().equals("mouseEntered")) 
               {
                   section.setBrightness(1.65f);
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.PURCHASEHAND)); 
               
               }
               if (e.getActionCommand().equals("mouseExited")) 
               {
                   section.setBrightness(1f);
                   potionImage.setBrightness(1f);
                   Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND)); 
               
               }
               if(e.getActionCommand().equals("clicked"))
               {
                   ((GameClientScene)owningScene).sendBuyPotionPacket(); 

                   //check if the buy will succeed or fail
                   int currency = PotionsMenu.this.playerReference.getCurrencyManager().getBalence();
                   int currentPots = PotionsMenu.this.playerReference.getPotionManager().getNumberOfPotions();
                   int maxPots = PotionsMenu.this.playerReference.getPotionManager().getMaxPotions();
                   if(currency >= PotionManager.POTION_PRICE && currentPots < maxPots)
                   {
                       //play success noise
                       Sound goldSound = Sound.ambientSound("buffered/coins.ogg",false);               
                       PotionsMenu.this.getOwningScene().add(goldSound);
                       
                   }
                   else
                   {
                       //play error sound
                        Sound errorSound = Sound.ambientSound("buffered/error.ogg",false);               
                        PotionsMenu.this.getOwningScene().add(errorSound);
                       //show error message
                   }
               }
           }
       });
        this.addComponent(sectionButton);
                
        //Cost:
        Text buyText = new Text("Cost:",LeadCrystalTextType.HUD34);
        Label buyLabel = new Label(buyText,200,340);
        this.addComponent(buyLabel);
        
        //price     
        Text potionPrice =new Text(Integer.toString(PotionManager.POTION_PRICE),LeadCrystalTextType.HUD30);
        Label label = new Label(potionPrice,290,340);
        this.addComponent(label);
        Button b = new Button(new Image("goldCoin.png"),326,336,26,26);
        this.addComponent(b);
     
        
        //gold holder
        Image goldHolderImage = new Image("text_holder.png");
        Button goldHolder = new Button(goldHolderImage,340,50,150,30);
        this.addComponent(goldHolder);
        
        //gold
        Text playerGoldDesc = new Text("Your Gold:",LeadCrystalTextType.HUD24);
        Label playerGoldDescLabel = new Label(playerGoldDesc,226,57);
        this.addComponent(playerGoldDescLabel);
        Text playerGold = new Text(Integer.toString(0),LeadCrystalTextType.HUD24);
        playerGoldLabel = new Label(playerGold,390,57);
        this.addComponent(playerGoldLabel);
        Button b1 = new Button(new Image("goldCoin.png"),460,55,20,20);
        this.addComponent(b1);
       
      
    }
    
    @Override
    public void update()
    {
        super.update();
        
        this.playerGoldLabel.getText().setText(Integer.toString(this.playerReference.getCurrencyManager().getBalence()));
        this.playerGoldLabel.setWindowRelativePosition(455 -this.playerGoldLabel.getText().getWidth(), 57); //390,57);
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
        
        ((GameClientScene)this.getOwningScene()).hud.closeDialogue();
       
    }
}
