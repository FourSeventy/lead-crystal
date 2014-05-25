package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.leadcrystal.scenes.MainMenuScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.TextEffect;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author mike
 */
public class EscapeMenu extends Window
{
    private final Text returnToGameText;
    private final Text quitText;
    
    public EscapeMenu(float x, float y)
    {
        super(new Image("smallFrame.png"), x, y, 400, 300);
        
       
        //single player
        this.returnToGameText = new Text("Return to Game",LeadCrystalTextType.HUD34);
        returnToGameText.setPosition(x +200 - returnToGameText.getWidth()/2, y + 185 );
        final Label returnToGameLabel = new Label(this.returnToGameText, 200 -returnToGameText.getWidth()/2, 185,true);
        this.addComponent(returnToGameLabel);
        final Button singlePlayerButton = new Button(new Image("blank.png"), 200 - returnToGameText.getWidth()/2, 185, returnToGameText.getWidth(), returnToGameText.getHeight());    
        this.addComponent(singlePlayerButton);
        singlePlayerButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    close();
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(returnToGameText.hasTextEffect("small"))
                          returnToGameText.removeTextEffect("small");
                      
                       returnToGameText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, returnToGameText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    getOwningScene().add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(returnToGameText.hasTextEffect("big"))
                           returnToGameText.removeTextEffect("big");
                        
                        returnToGameText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, returnToGameText.getScale(), 1));
                }
            }
        });
        
         //single player  
        this.quitText = new Text("Save and Quit",LeadCrystalTextType.HUD34);
        quitText.setPosition(x +200 - quitText.getWidth()/2,y+ 85);
        final Label quitLabel = new Label(this.quitText, 200 -quitText.getWidth()/2, 85,true);
        this.addComponent(quitLabel);
        final Button quitButton = new Button(new Image("blank.png"), 200 - quitText.getWidth()/2, 85, quitText.getWidth(), quitText.getHeight());     
        this.addComponent(quitButton);
        quitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                    ((GameClientScene)owningScene).sendDisconnectRequest();
                    ((GameClientScene)owningScene).saveGameToDisk();
                    Game.getInstance().unloadScene(GameClientScene.class);
                    Game.getInstance().loadScene(new MainMenuScene());
                    Game.getInstance().changeScene(MainMenuScene.class,new ArrayList(){{add(true);}});  
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(quitText.hasTextEffect("small"))
                          quitText.removeTextEffect("small");
                      
                       quitText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, quitText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    getOwningScene().add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(quitText.hasTextEffect("big"))
                           quitText.removeTextEffect("big");
                        
                        quitText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, quitText.getScale(), 1));
                }
            }
        });
        
        
    }
}
