package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.Potion;
import com.silvergobletgames.leadcrystal.items.PotionManager;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
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
 
    public PotionsMenu( PlayerEntity playerReference,float x, float y)
    {
        super(new Image("tallFrame.png"),x,y,550,900);
        
        this.playerReference = playerReference;
        
        //text
        Text menuText = new Text("Potions",LeadCrystalTextType.HUD24);
        Label menuTextLabel = new Label(menuText,275 - menuText.getWidth()/2,840);
        this.addComponent(menuTextLabel);
        
        //close
        final Image closeImage = new Image("closeButton.png");
        Button closeButton = new Button(closeImage,504,867,closeImage.getWidth()+1,closeImage.getHeight());
        closeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {

                  closeImage.setBrightness(1.5f);
                  Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.HAND)); 
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
       
        //section1
        final Image section = new Image("section1.png");
        Button sectionButton = new Button(section,275 - section.getWidth()/2,300,section.getWidth(),section.getHeight());
        sectionButton.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if (e.getActionCommand().equals("mouseEntered")) 
               {
                   section.setBrightness(1.8f);
                   potionImage.setBrightness(1.8f);
               }
               if (e.getActionCommand().equals("mouseExited")) 
               {
                   section.setBrightness(1f);
                   potionImage.setBrightness(1f);
               }
               if(e.getActionCommand().equals("clicked"))
               {
                   ((GameClientScene)owningScene).sendBuyPotionPacket(); 
               }
           }
       });
        this.addComponent(sectionButton);
        
        //section2
        final Image section2 = new Image("section2.png");
        Button sectionButton2 = new Button(section2,330,50,section2.getWidth(),section2.getHeight()+6);
        this.addComponent(sectionButton2);
        
        //gold
        Text playerGoldDesc = new Text("Your Gold:",LeadCrystalTextType.HUD24);
        Label playerGoldDescLabel = new Label(playerGoldDesc,226,57);
        this.addComponent(playerGoldDescLabel);
        Text playerGold = new Text(Integer.toString(0),LeadCrystalTextType.HUD24);
        playerGoldLabel = new Label(playerGold,390,57);
        this.addComponent(playerGoldLabel);
        Button b1 = new Button(new Image("currency2.png"),460,55,20,20);
        this.addComponent(b1);
        
      
       Button b = new Button(potion.getImage(),200,450,potion.getImage().getWidth() * 2,potion.getImage().getHeight() * 2);
       b.dontKillClick = true;
       this.addComponent(b);
       Text potionText = new Text("Healing Potion",LeadCrystalTextType.HUD34);
       Label label = new Label(potionText,275 - potionText.getWidth()/2,400);
       this.addComponent(label);
       //price
       b = new Button(new Image("currency2.png"),230,350,30,30);
       this.addComponent(b);
       Text potionPrice =new Text(Integer.toString(PotionManager.POTION_PRICE),LeadCrystalTextType.HUD28);
       label = new Label(potionPrice,260,350);
       this.addComponent(label);
       
      
    }
    
    @Override
    public void update()
    {
        super.update();
        
        this.playerGoldLabel.getText().setText(Integer.toString(this.playerReference.getCurrencyManager().getBalence()));
    }
}
