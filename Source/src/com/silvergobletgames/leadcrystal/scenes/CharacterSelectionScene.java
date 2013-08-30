package com.silvergobletgames.leadcrystal.scenes;

import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.sylver.core.*;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.leadcrystal.core.SaveGame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import com.silvergobletgames.sylver.audio.AudioRenderer;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.ImageEffect.ImageEffectType;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;

/**
 *
 * @author mike
 */
public class CharacterSelectionScene extends Scene
{
    
    //list of save games we find
    private HashMap<String, SaveGame> saveGames = new HashMap();
    private HashMap<String, Button> saveGameButtonMap = new HashMap();
    
    
    //selected save
    private Image selectionArrow;
    private String selectedSave;
    
    //should we execute single player actions, or multiplayer actions
    private String actionArg;
    
    //==============
    // Constructor
    //==============
    
    public CharacterSelectionScene()
    {
        //center and right
        final int right = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x;
        final int center = right/2;
        
        //build background image
        Image back = new Image("mainMenuBackground.png");
        back.setPosition(0, 0);
        back.setDimensions(1600, 900);
        this.add(back,Layer.BACKGROUND);
        
        //options title
        Text title = new Text("Select Character",CoreTextType.MENU);      
        title.setScale(1f);
        title.setPosition(center - title.getWidth()/2, 730);       
        this.add(title,Layer.MAIN);
        
        //selection box
        Image selectionBox = new Image("menuSquare.png");
        selectionBox.setDimensions(selectionBox.getHeight(), selectionBox.getWidth() + 200);
        selectionBox.setAnchor(Anchorable.Anchor.TOPLEFT);
        selectionBox.setPositionAnchored(center - selectionBox.getWidth()/2, 680);
        this.add(selectionBox,Layer.MAIN);

        //select
        final Text selectText = new Text("Select",LeadCrystalTextType.MENUBUTTONS);
        selectText.setScale(1.5f);
        selectText.setPosition(center + 300, 650); 
        final Button selectButton = new Button(new Image("blank.png"), selectText.getPosition().x, selectText.getPosition().y, selectText.getWidth(), selectText.getHeight());
        this.add(selectText,Layer.MAIN);
        this.add(selectButton,Layer.MAIN);
        selectButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) {
                    selectButton_click();
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(selectText.hasTextEffect("small"))
                          selectText.removeTextEffect("small");
                      
                       selectText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, selectText.getScale(), 1.8));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(selectText.hasTextEffect("big"))
                           selectText.removeTextEffect("big");
                        
                        selectText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, selectText.getScale(), 1.5));
                }
            }
        });
        
        //delete
        final Text deleteText = new Text("Delete",LeadCrystalTextType.MENUBUTTONS);
        deleteText.setScale(1.5f);
        deleteText.setPosition(center + 300, 600);
        final Button deleteButton = new Button(new Image("blank.png"), deleteText.getPosition().x, deleteText.getPosition().y, deleteText.getWidth(), deleteText.getHeight());
        this.add(deleteText,Layer.MAIN);
        this.add(deleteButton,Layer.MAIN);
        deleteButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) {
                    deleteButton_click();
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(deleteText.hasTextEffect("small"))
                          deleteText.removeTextEffect("small");
                      
                       deleteText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, deleteText.getScale(), 1.8));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(deleteText.hasTextEffect("big"))
                           deleteText.removeTextEffect("big");
                        
                        deleteText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, deleteText.getScale(), 1.5));
                }
            }
        });
        
        //new character
        final Text newCharacterText = new Text("New Character",LeadCrystalTextType.MENUBUTTONS);
        newCharacterText.setScale(1.5f);
        newCharacterText.setPosition(center + 300, 550);
        final Button newCharacterButton = new Button(new Image("blank.png"), newCharacterText.getPosition().x, newCharacterText.getPosition().y, newCharacterText.getWidth(), newCharacterText.getHeight());
        this.add(newCharacterText,Layer.MAIN);
        this.add(newCharacterButton,Layer.MAIN);
        newCharacterButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) {
                    newCharacterButton_click();
                }
               if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(newCharacterText.hasTextEffect("small"))
                          newCharacterText.removeTextEffect("small");
                      
                       newCharacterText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, newCharacterText.getScale(), 1.8));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(newCharacterText.hasTextEffect("big"))
                           newCharacterText.removeTextEffect("big");
                        
                        newCharacterText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, newCharacterText.getScale(), 1.5));
                }
            }
        });  
        
        //back
        final Text backText = new Text("Back",LeadCrystalTextType.MENUBUTTONS);
        backText.setScale(1.5f);
        backText.setPosition(center + 300, 500); 
        final Button backButton = new Button(new Image("blank.png"), backText.getPosition().x, backText.getPosition().y, backText.getWidth(), backText.getHeight());      
        this.add(backText,Layer.MAIN);
        this.add(backButton,Layer.MAIN);
        backButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) {
                    backButton_click();
                }
                if (e.getActionCommand().equals("mouseEntered")) 
                {
                    
                      if(backText.hasTextEffect("small"))
                          backText.removeTextEffect("small");
                      
                       backText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, backText.getScale(), 1.8));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(backText.hasTextEffect("big"))
                           backText.removeTextEffect("big");
                        
                        backText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, backText.getScale(), 1.5));
                }
            }
        });
     
        //selection arrow
        this.selectionArrow = new Image("arrow.png");
        this.selectionArrow.setAngle(90);
        this.selectionArrow.setAlphaBrightness(0);
        this.add(selectionArrow,Layer.MAIN);
        
        //populate save list
        this.populateSaveList();
        
       
    }
    
    
    //===============
    // Scene Methods
    //===============
   

    public void handleInput() 
    {
        
    }

    public void sceneEntered(ArrayList args) 
    {
        //set our args
        if(args != null && args.get(0) != null)
            this.actionArg = (String)args.get(0);   
        
        //set mouse cursor
        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND));
    }

    public void sceneExited() 
    {
        Game.getInstance().unloadScene(CharacterSelectionScene.class);
    }
    
    
    //===============
    // Handle Clicks
    //===============
    private void populateSaveList()
    {
        //center and right
        final int right = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x;
        final int center = right/2;
        
        //=========================
        // Clear out old buttons
        //=========================
        
        for(Button bu: this.saveGameButtonMap.values())
        {
            this.remove(bu);
        }
        this.saveGameButtonMap.clear();
        this.saveGames.clear();
        
        this.selectionArrow.setAlphaBrightness(0);
        
        //=========================
        // Scan For Save Games
        //=========================
        File directory = new File ("");
        String path = directory.getAbsolutePath() + "\\Save Games\\";
        String fileName;

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) 
        {
            fileName = listOfFiles[i].getName().toLowerCase();
            if (listOfFiles[i].isFile() && fileName.endsWith(".save")) 
            {
                try
                {                  
                    //build the save and add it to the list
                    SaveGame save = SaveGame.loadSaveFromFile(fileName);
                    this.saveGames.put(save.fileName, save);
                }
                catch(Exception e)
                {
                    System.err.println("Load Saved Game Fail: "); e.printStackTrace(System.err);}                
            }            
        }
        
        //===================
        // Build The Buttons 
        //===================
        int number = 0;
        for(String key: this.saveGames.keySet())
        {
            Text saveGameText = new Text(key);
            saveGameText.setScale(1.3f);
            final Button savegameButton = new Button(saveGameText, center - 250, 650 - 50 * number);
            this.add(savegameButton,Layer.MAIN);
            this.saveGameButtonMap.put(saveGameText.toString(), savegameButton);
            savegameButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    if (e.getActionCommand().equals("clicked")) 
                    {
                        selectedSave = savegameButton.getText().toString();
                        selectionArrow.setPosition(savegameButton.getPosition().x - 150, savegameButton.getPosition().y - selectionArrow.getHeight()/2);
                        selectionArrow.setAlphaBrightness(1);
                        Float[] points = {1.0f,1.3f,1.0f};
                        int[] durations = {10,10};
                        selectionArrow.addImageEffect(new MultiImageEffect(ImageEffectType.SCALE, points, durations));
                    }
                    if (e.getActionCommand().equals("mouseEntered")) 
                    {
                        if(selectedSave == null)
                        {
                            selectionArrow.setPosition(savegameButton.getPosition().x - 150, savegameButton.getPosition().y - selectionArrow.getHeight()/2);
                            selectionArrow.setAlphaBrightness(1);
                        }
                        //play sound
                        Sound sound = Sound.ambientSound("buffered/buttonBoop.wav", true );
                        add(sound);
                    }
                    
                    if (e.getActionCommand().equals("mouseExited")) 
                    {
                        if(selectedSave == null)
                           selectionArrow.setAlphaBrightness(0);

                    }
                }
          
            });
            
            number++;
        }
    }
    
    private void selectButton_click()
    {
        //if we are in multiplayer mode, go to the multiplayer menu and pass it your character
        if(this.actionArg.equals("Multiplayer") && this.selectedSave != null)
        {
            Game.getInstance().loadScene(new MultiplayerMenuScene());
            
            ArrayList args = new ArrayList();
            args.add(this.saveGames.get(this.selectedSave));
            args.add(actionArg);
            Game.getInstance().changeScene(MultiplayerMenuScene.class,args);
        }
        //else if we are in single player mode, start up a game
        else if(this.actionArg.equals("Singleplayer") && this.selectedSave != null)
        {          
            //stop music
            Sound sound = Sound.newBGM("");
            add(sound);
         
            //start a single player game
            MainMenuScene.startSinglePlayerGame(this.saveGames.get(this.selectedSave));
                         
        }
        
        
    }
    
    private void newCharacterButton_click()
    {
        
        Game.getInstance().loadScene(new NewCharacterScene());
        ArrayList<String> args = new ArrayList();
        args.add(actionArg);
        Game.getInstance().changeScene(NewCharacterScene.class, args);
        
    }
    
    private void deleteButton_click()
    {
        File directory = new File ("");
        String path = directory.getAbsolutePath() + "\\Save Games\\";
        String fileName;

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) 
        {
            fileName = listOfFiles[i].getName().toLowerCase();
            if (listOfFiles[i].isFile() && fileName.endsWith(".save")) 
            {
                //if it exists, delete
                if (this.selectedSave != null && this.selectedSave.equals(fileName))
                {
                    //delete the file
                    (new File(path + this.selectedSave)).delete();
                    
                    this.selectedSave = null;
                    
                    //repopulate save games
                    this.populateSaveList();
                }           
            }            
        }
    }
    
    private void backButton_click()
    {
        //switch back to the main menu    
        Game.getInstance().loadScene(new MainMenuScene());
        Game.getInstance().changeScene(MainMenuScene.class, null);       
    }
}
