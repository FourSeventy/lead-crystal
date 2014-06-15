package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.Potion;
import com.silvergobletgames.leadcrystal.items.PotionManager;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
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
        
        
        //gold
        Text playerGoldDesc = new Text("Your Gold:",LeadCrystalTextType.HUD24);
        Label playerGoldDescLabel = new Label(playerGoldDesc,285,800);
        this.addComponent(playerGoldDescLabel);
        Text playerGold = new Text(Integer.toString(0),LeadCrystalTextType.HUD24);
        playerGoldLabel = new Label(playerGold,410,800);
        this.addComponent(playerGoldLabel);
        Button b1 = new Button(new Image("currency2.png"),480,800,20,20);
        this.addComponent(b1);
        
       //potion
       final Potion potion = new Potion();
       Button b = new Button(potion.getImage(),200,450,potion.getImage().getWidth() * 2,potion.getImage().getHeight() * 2);
       b.addActionListener(new ActionListener(){
       
           public void actionPerformed(ActionEvent e)
           {
               if(e.getActionCommand().equals("clicked"))
               {
                   ((GameClientScene)owningScene).sendBuyPotionPacket(); 
               }
           }
       });
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
