package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.silvergobletgames.leadcrystal.items.Potion;
import com.silvergobletgames.leadcrystal.items.PotionManager;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;

/**
 *
 * @author Mike
 */
public class PotionsMenu extends Window{
 
    public PotionsMenu( float x, float y)
    {
        super(new Image("potionsMenu.png"),x,y,550,800);
        
        
        
       //small potion
       final Potion potion = new Potion();
       Button b = new Button(potion.getImage(),25,650,60,70);
       b.getImage().setScale(.6f);
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
       Label label = new Label(" Healing Potion",95,713);
       label.getText().setScale(.8f);
       this.addComponent(label);
       label = new Label("- Heals 100 health.",95,680);
       label.getText().setScale(.65f);
       this.addComponent(label);
       //price
       b = new Button(new Image("currency2.png"),95,640,20,20);
       this.addComponent(b);
       label = new Label(Integer.toString(PotionManager.POTION_PRICE),120,642);
       label.getText().setScale(.9f);
       this.addComponent(label);
       
      
    }
}
