package com.silvergobletgames.leadcrystal.scenes;

import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.core.SaveGame;
import com.silvergobletgames.leadcrystal.scenes.NewCharacterScene.PlayerMock;
import com.silvergobletgames.sylver.audio.AudioRenderer;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.*;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect.ImageEffectType;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import com.silvergobletgames.sylver.windowsystem.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.media.opengl.GL2;

/**
 *
 * @author mike
 */
public class CharacterSelectionScene extends Scene
{
    
    //list of save games we find
    private HashMap<String, SaveGame> saveGames = new HashMap<>();
    private HashMap<String, ArrayList<SceneObject>> saveGameButtonMap = new HashMap<>();
    
    
    //selected save
    private Image selectionArrow;
    private String selectedSave;
    
    
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
        Text title = new Text("Select Character",LeadCrystalTextType.MENU60);      
        title.setPosition(center - title.getWidth()/2, 740);       
        this.add(title,Layer.MAIN);
        
        //selection box
        Image selectionBox = new Image("tallFrameMenu.png");
        selectionBox.setDimensions(selectionBox.getWidth() + 25, selectionBox.getHeight() + 150); 
        selectionBox.setAnchor(Anchorable.Anchor.TOPLEFT);
        selectionBox.setPositionAnchored(center - selectionBox.getWidth()/2 - 70, 680);
        this.add(selectionBox,Layer.MAIN);

        //select
        final Text selectText = new Text("Select",LeadCrystalTextType.MENU46);
        selectText.setPosition(center + 280 - 70, 645); 
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
                      
                       selectText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, selectText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(selectText.hasTextEffect("big"))
                           selectText.removeTextEffect("big");
                        
                        selectText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, selectText.getScale(), 1));
                }
            }
        });
        
        //delete
        final Text deleteText = new Text("Delete",LeadCrystalTextType.MENU46);
        deleteText.setPosition(center + 280 - 70, 590);
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
                      
                       deleteText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, deleteText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(deleteText.hasTextEffect("big"))
                           deleteText.removeTextEffect("big");
                        
                        deleteText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, deleteText.getScale(), 1));
                }
            }
        });
        
              
        //back
        final Text backText = new Text("Back",LeadCrystalTextType.MENU46);
        backText.setPosition(center + 280 - 70, 535); 
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
                      
                       backText.addTextEffect("big",new TextEffect(TextEffect.TextEffectType.SCALE, 15, backText.getScale(), 1.2));
                    
                    //play sound
                    Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true);
                    add(sound);
                }
                if (e.getActionCommand().equals("mouseExited"))
                {
                        if(backText.hasTextEffect("big"))
                           backText.removeTextEffect("big");
                        
                        backText.addTextEffect("small",new TextEffect(TextEffect.TextEffectType.SCALE, 15, backText.getScale(), 1));
                }
            }
        });
     
        //selection arrow
        this.selectionArrow = new Image("map_arrow.png");
        this.selectionArrow.setHorizontalFlip(true);
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
        //set mouse cursor
        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.POINTERHAND));
    }

    public void sceneExited() 
    {
        Game.getInstance().unloadScene(CharacterSelectionScene.class);
    }
    
    /**
     * Renders everything in the scene using either the GL2 or GL3 renderer, based on the GlCapabilities
     * @param gl 
     */
    public void render(GL2 gl)
    {
        //set viewport size
        getViewport().setDimensions(Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x, Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().y);
                  
       
        //===============
        // GL2 rendering
        //===============
        RenderingPipelineGL2.render(gl, getViewport(), getSceneObjectManager(), getSceneEffectsManager()); 
                
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
        
        for(ArrayList<SceneObject> saveObjectList: this.saveGameButtonMap.values())
        {
            
            for(SceneObject objects: saveObjectList)
            {
                this.remove(objects);
            }
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

        for(int i = 0; i < listOfFiles.length; i++) 
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
            //if they have more than 10 characters they wont see them all
            if(number>10)
            {
                break;
            }
            
            ArrayList<SceneObject> saveGameObjects =  new ArrayList<>();
            
            Text saveGameText = new Text(key.substring(0, key.length()-5),LeadCrystalTextType.MENU36);
            final Button savegameButton = new Button(saveGameText, center - 225 - 70, 630 - 55 * number);           
            this.add(savegameButton,Layer.MAIN);
            saveGameObjects.add(savegameButton);
            
            Image coinIcon = new Image("goldCoinSmall.png");
            coinIcon.setDimensions(24, 24);
            coinIcon.setPosition(center + 200 - 70, 627 - 55 * number);
            this.add(coinIcon,Layer.MAIN);
            saveGameObjects.add(coinIcon);
            
            Text goldText = new Text(Integer.toString(this.saveGames.get(key).getPlayer().getCurrencyManager().getBalence()), LeadCrystalTextType.MENU23);
            goldText.setPosition(center + 200 - goldText.getWidth() - 7 - 70, 630 - 55 * number);
            this.add(goldText,Layer.MAIN);
            saveGameObjects.add(goldText);
            
            this.saveGameButtonMap.put(saveGameText.toString(), saveGameObjects);
            
            savegameButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    if (e.getActionCommand().equals("clicked")) 
                    {
                        selectedSave = savegameButton.getText().toString() + ".save";
                        selectionArrow.setPosition(savegameButton.getPosition().x - 125, savegameButton.getPosition().y - selectionArrow.getHeight()/2);
                        selectionArrow.setAlphaBrightness(1);
                        Float[] points = {1.0f,1.3f,1.0f};
                        int[] durations = {10,10};
                        selectionArrow.addImageEffect(new MultiImageEffect(ImageEffectType.SCALE, points, durations));
                    }
                    if (e.getActionCommand().equals("mouseEntered")) 
                    {
                        if(selectedSave == null)
                        {
                            selectionArrow.setPosition(savegameButton.getPosition().x - 125, savegameButton.getPosition().y - selectionArrow.getHeight()/2);
                            selectionArrow.setAlphaBrightness(1);
                        }
                        //play sound
                        Sound sound = Sound.ambientSound("buffered/buttonBoop.ogg", true );
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
        //else if we are in single player mode, start up a game
        if( this.selectedSave != null)
        {          
            //stop music
            Sound sound = Sound.newBGM("");
            add(sound);
         
            PlayerMock playerMock = new PlayerMock(this.saveGames.get(this.selectedSave).fileName);
            //start a single player game
            MainMenuScene.startGame(playerMock);                        
        }
       
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
