
package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.Window;
import java.util.ArrayList;
import com.silvergobletgames.leadcrystal.scripting.ScriptObject;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.graphics.OpenGLGameWindow;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import com.silvergobletgames.sylver.windowsystem.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author mike
 */
public class DialogueWindow extends Window {
    
    private Label name;
    
    private ArrayList<Label> text = new ArrayList<>();
    
    private boolean opening = false;;
    private float targetY;
    
    public DialogueWindow(float x, float y,String speakersName,  String dialogueText)
    {
        super(new Image("dialogueBox.png"),x,y, 1000,210);
        
        //set name
        Text t = new Text(speakersName);
        t.setTextType(CoreTextType.DEFAULT);  
        this.name = new Label(t,50,170);
        this.addComponent(this.name);
        
        //OK button
        Button button = new Button(new Text("OK"), 800, 35);
        button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("clicked")) 
                    {                        
                        ((GameClientScene)owningScene).hud.closeDialogue();
                    }
                    if (e.getActionCommand().equals("mouseEntered")) 
                    {
                        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND)); 
                    }
                    if (e.getActionCommand().equals("mouseExited")) 
                    {
                         Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.RETICULE)); 
                    }
                }
            });
        this.addComponent(button);
        
        //set up dialog
        ArrayList<String> list = new ArrayList<>();
        
        //break up the input into an array seperateed by spaces
        String[] spaces = dialogueText.split(" ");
        
        //concatinate these words together untill they are length 90
        String line = "";
        for(String part: spaces)
        {
            line += part + " ";
            
            if(line.length() > 95)
            {
               int index = line.lastIndexOf(part);
               line = line.substring(0, index);
               list.add(line);
               line = part + " ";
            }
            
        }
        list.add(line);
        
        for(String s: list)
        {
            text.add(new Label(s,0,0));
        }
        
        //position the lines of text correctly
        for(int i = 0; i <text.size(); i++)
        {
            text.get(i).setWindowRelativePosition( 20,  this.height - 75 - (i * text.get(i).getText().getHeight()) );
            this.addComponent(text.get(i));
        }
        
        this.targetY = y;
 
    }
    
    @Override
    public void open()
    {
        super.open();
        
        this.opening = true;
        this.setPosition(this.getPosition().x, 900);
    }
    
    @Override
    public void update()
    {
        super.update();
        
        if(opening)
        {
            if(targetY < this.getPosition().y)
            {
                this.setPosition(this.getPosition().x,this.getPosition().y - 7);
            }
            else
                opening = false;
        }
    }

}
