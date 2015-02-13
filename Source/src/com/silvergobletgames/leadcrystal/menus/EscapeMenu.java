package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.scenes.GameScene;
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
    private final Text optionsText;
    private final Text returnToGameText;
    private final Text quitText;
    
    
    public EscapeMenu(float x, float y, final Hud hudReference)
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
        
        //return to game
        this.returnToGameText = new Text("Return to Game",LeadCrystalTextType.HUD34);
        returnToGameText.setPosition(x +200 - returnToGameText.getWidth()/2, y + 285 );
        final Label returnToGameLabel = new Label(this.returnToGameText, 200 -returnToGameText.getWidth()/2, 285,true);
        this.addComponent(returnToGameLabel);
        final Button singlePlayerButton = new Button(new Image("blank.png"), 200 - returnToGameText.getWidth()/2, 285, returnToGameText.getWidth(), returnToGameText.getHeight());    
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
        
        //options
        this.optionsText = new Text("Options",LeadCrystalTextType.HUD34);
        optionsText.setPosition(x +200 - optionsText.getWidth()/2, y + 185 );
        final Label optionsLabel = new Label(this.optionsText, 200 -optionsText.getWidth()/2, 185,true);
        this.addComponent(optionsLabel);
        final Button optionsButton = new Button(new Image("blank.png"), 200 - optionsText.getWidth()/2, 185, optionsText.getWidth(), optionsText.getHeight());    
        this.addComponent(optionsButton);
        optionsButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
                   close();
                   hudReference.optionsMenu.open();
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(optionsText.hasTextEffect("small"))
                          optionsText.removeTextEffect("small");
                      
                       optionsText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, optionsText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    getOwningScene().add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(optionsText.hasTextEffect("big"))
                           optionsText.removeTextEffect("big");
                        
                        optionsText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, optionsText.getScale(), 1));
                }
            }
        });
        
         //save and quit
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
                    ((GameScene)owningScene).saveAndQuit();
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
        
        ((GameScene)this.getOwningScene()).getHud().closeDialogue();
               
    }
}
