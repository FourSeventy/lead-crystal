
package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.leadcrystal.scripting.ScriptObject;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.OpenGLGameWindow;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.TextArea;
import com.silvergobletgames.sylver.windowsystem.TextBlock;
import com.silvergobletgames.sylver.windowsystem.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author mike
 */
public class DialogueWindow extends Window {
    
    private Label name;
    private TextBlock textArea;
    
    private ArrayList<Label> text = new ArrayList<>();
    
    private boolean opening = false;
    private float targetY;
    
    public DialogueWindow(float x, float y,String speakersName,  String dialogueText)
    {
        super(new Image("dialogueBox.png"), x, y, 768 * 1.2f, 173 * 1.2f);
        
        
        
        //set image
        Image characterImage; 
        switch(speakersName)
        {
            case "Slash": characterImage = new Image("Slash.png"); break;
            case "Dr. Tam": characterImage = new Image("DrTam.png"); break;
            case "Old Man": characterImage = new Image("OldMan.png"); break;
            case "Brice": characterImage = new Image("Brice.png"); break;
            default: characterImage = new Image("blank.png");
        }
        
        Button portrait = new Button(characterImage,25,37,142,142);
        this.addComponent(portrait);
        //set name
        Text t = new Text(speakersName,LeadCrystalTextType.HUD24);
        this.name = new Label(t,90 - t.getWidth()/2,15);
        this.addComponent(this.name);
        
        //OK button
        Button button = new Button(new Text("OK",LeadCrystalTextType.HUD24), 843, 19);
        button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("clicked")) 
                    {                
                        
                        DialogueWindow.this.close();
                        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.RETICLE)); 
                    
                    }
                    if (e.getActionCommand().equals("mouseEntered")) 
                    {
                        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.ACTIVEHAND)); 
                    }
                    if (e.getActionCommand().equals("mouseExited")) 
                    {
                         Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.RETICLE)); 
                    }
                }
            });
        this.addComponent(button);
        
        //set up dialog
        this.textArea = new TextBlock(191, 155, 720, new Text(dialogueText,LeadCrystalTextType.HUD22));  
        this.addComponent(textArea);
        
        this.targetY = y;
 
    }
    
    @Override
    public void open()
    {
        
        
         if(this.owningScene != null)
        {
            Sound openSound = Sound.ambientSound("buffered/menuOpen.ogg", false);
            this.owningScene.add(openSound);
        }
         
         super.open();
        
        this.opening = true;
        this.setPosition(this.getPosition().x, 900);
    }
    
    @Override
    public void close()
    {
        if(this.owningScene != null && this.isOpen)
        {
            Sound closeSound = Sound.ambientSound("buffered/menuClose.ogg", false);
            this.owningScene.add(closeSound);
            
            ((GameClientScene)this.owningScene).sendDialogueClosedPacket();
        }
        
        super.close();
              
    }
    
    @Override
    public void update()
    {
        super.update();
        
        if(opening)
        {
            if(targetY < this.getPosition().y)
            {
                this.setPosition(this.getPosition().x,this.getPosition().y - 15);
            }
            else
                opening = false;
        }
    }

}
