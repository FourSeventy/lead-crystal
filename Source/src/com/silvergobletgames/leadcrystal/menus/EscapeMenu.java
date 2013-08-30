package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.leadcrystal.scenes.MainMenuScene;
import com.silvergobletgames.sylver.audio.Sound;

/**
 *
 * @author mike
 */
public class EscapeMenu extends Window
{
    
    public EscapeMenu(float x, float y)
    {
        super(new Image("EscapeMenu.png"), x, y, 400f, 400f);
        
        
        Text buttonText = new Text("Return to Game");
        buttonText.setScale(1.5f);
        final Button returnToGameButton = new Button(buttonText, 50, 300);
        this.addComponent(returnToGameButton);
        returnToGameButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) {
                    close();
                }
                if (e.getActionCommand().equals("mouseEntered")) {
                    returnToGameButton.text.setScale(2);
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    owningScene.add(sound);
                }
                if (e.getActionCommand().equals("mouseExited")) {
                    returnToGameButton.text.setScale(1.5f);
                }
            }
        });
        
        buttonText = new Text("Options");
        buttonText.setScale(1.5f);
        final Button optionsButton = new Button(buttonText, 50, 200);
        this.addComponent(optionsButton);
        optionsButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) {
                    //do options menu stuff
                }
                if (e.getActionCommand().equals("mouseEntered")) {
                    optionsButton.text.setScale(2);
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    owningScene.add(sound);
                }
                if (e.getActionCommand().equals("mouseExited")) {
                    optionsButton.text.setScale(1.5f);
                }
            }
        });
        
        buttonText = new Text("Save and Quit");
        buttonText.setScale(1.5f);
        final Button quitButton = new Button(buttonText, 50, 100);
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
                if (e.getActionCommand().equals("mouseEntered")) {
                    quitButton.text.setScale(2);
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true );
                    owningScene.add(sound);
                }
                if (e.getActionCommand().equals("mouseExited")) {
                    quitButton.text.setScale(1.5f);
                }
            }
        });
        
    }
}
