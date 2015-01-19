package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.TextBlock;
import com.silvergobletgames.sylver.windowsystem.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author mike
 */
public class ErrorMenu extends Window
{
    public TextBlock errorText;
    
    public ErrorMenu(String text, float x, float y)
    {
        super(new Image("smallFrame.png"), x, y, 400, 400);
        
        //close
        final Image closeImage = new Image("closeButton.png");
        Button closeButton = new Button(closeImage,357,366,closeImage.getWidth()+1,closeImage.getHeight());
        closeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {

                  closeImage.setBrightness(1.5f);
                  Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.POINTERHAND)); 
                }
                if (e.getActionCommand().equals("mouseExited")) {

                    closeImage.setBrightness(1f);
                }
                if(e.getActionCommand().equals("clicked"))
                {
                    System.err.println("WTFF");
                    clicked_handler();
                }
            }
       });
        this.addComponent(closeButton);
        
        //error label
        Text t = new Text("Error",LeadCrystalTextType.MENU54);
        t.setColor(new Color(156,19,19)); 
        Label errorLabel = new Label(t, 200-t.getWidth()/2, 300);
        this.addComponent(errorLabel);
        
        
        //text
        this.errorText = new TextBlock( 50, 230, 300, new Text(text,LeadCrystalTextType.MENU30));
        this.addComponent(errorText);
              
        
    }
    
    private void clicked_handler()
    {
        this.getOwningScene().remove(this);
    }
}
