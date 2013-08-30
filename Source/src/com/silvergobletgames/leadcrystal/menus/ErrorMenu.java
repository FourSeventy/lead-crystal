package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.silvergobletgames.sylver.windowsystem.TextBlock;

/**
 *
 * @author mike
 */
public class ErrorMenu extends Window
{
    public TextBlock errorText;
    
    public ErrorMenu(String text, float x, float y)
    {
        super(new Image("ErrorMenu.png"), x, y, 600, 400);
        
        //text
        this.errorText = new TextBlock( 150, 300, 300, new Text(text));
        this.addComponent(errorText);
        
        //ok button
        Button button = new Button(new Text("OK"), 300, 50);
        button.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent event)
         {
             if(event.getActionCommand().equals("clicked"))
             {
                 clicked_handler();
             }
         }
        }); 
        this.addComponent(button);
              
        
    }
    
    private void clicked_handler()
    {
        this.getOwningScene().remove(this);
    }
}
