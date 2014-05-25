package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.TextBlock;
import com.silvergobletgames.sylver.windowsystem.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.silvergobletgames.leadcrystal.combat.LevelProgressionManager.Level;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;

/**
 *
 * @author mike
 */
public class QuestMenu extends Window{
    
    //Player reference
    private PlayerEntity playerReference;
    
    //level detail fields
    private Label levelName; 
    private Label mainObjectiveHeader; private Label mainObjectiveCompleted;
    private TextBlock mainObjectiveDescription;
    private Label sideObjective1Header; private Label sideObjective1Completed; private Label sideObjectiveStatus; private String statusText = "";
    private TextBlock sideObjective1Description;
    
    
    
    //==============
    // Constructor
    //==============
    public QuestMenu(float x, float y,PlayerEntity player)
    {
       //super constructor call, setting the background sprite and initial position
       super(new Image("tallFrame.png"),x,y,500,900);
       
       this.playerReference = player;
       
       Button closeButton = new Button("deleteButton.png",500,850,50,50);
       closeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {

                  
                }
                if (e.getActionCommand().equals("mouseExited")) {

                    
                }
                if(e.getActionCommand().equals("clicked"))
                {
                    close();
                }
            }
       });
       this.addComponent(closeButton);
                    
  
       

    }
    
    
    
    //===============
    // Class Methods
    //===============
    
    /**
     * Repaints the level details that display in the bottom right corner of the map screen. A 0 clears the fields.
     * @param levelNumber 
     */
    public void repaintLevelDetails(Level currentLevel)
    {
        //clear old stuff
        this.removeComponent(levelName);
        this.removeComponent(mainObjectiveDescription);
        this.removeComponent(mainObjectiveHeader);
        this.removeComponent(mainObjectiveCompleted);
        this.removeComponent(sideObjective1Description);
        this.removeComponent(sideObjective1Header);
        this.removeComponent(sideObjective1Completed);
        this.removeComponent(sideObjectiveStatus); 
        
        if(currentLevel != null)
        {
            //level name
            this.levelName = new Label(currentLevel.levelName, 50, 800);
            levelName.getText().setScale(1.4f);
            levelName.getText().setColor(new Color(Color.white));
            this.addComponent(levelName);
       
            //main objective
            this.mainObjectiveHeader = new Label("Main Objective: ",50,700);
            this.mainObjectiveHeader.getText().setColor(new Color(Color.red));
            this.addComponent(mainObjectiveHeader);      
            if(currentLevel.mainObjective.complete)
            {
                this.mainObjectiveCompleted = new Label("Complete", 250,700);
                this.mainObjectiveCompleted.getText().setColor(new Color(Color.green));
                this.addComponent(mainObjectiveCompleted);
            }    
            this.mainObjectiveDescription = new TextBlock(50, 675, 420, new Text(currentLevel.mainObjective.objectiveDescription));
            this.addComponent(mainObjectiveDescription);

            //side objective 1
            if(currentLevel.sideObjective != null)
            {          
                //header
                this.sideObjective1Header = new Label("Side Objective: ",50,675 - mainObjectiveDescription.getHeight() - 50);
                this.sideObjective1Header.getText().setColor(new Color(Color.red));
                this.addComponent(sideObjective1Header);
                
                //complete
                if(currentLevel.sideObjective.complete || this.statusText.equals("complete"))
                {
                    this.sideObjective1Completed = new Label("Complete", 250,675 - mainObjectiveDescription.getHeight() - 50);
                    this.sideObjective1Completed.getText().setColor(new Color(Color.green)); 
                    this.addComponent(sideObjective1Completed);
                }
                
                //status
                if(!statusText.equals("complete"))
                {
                    this.sideObjectiveStatus = new Label(statusText, 250,675 - mainObjectiveDescription.getHeight() - 50);
                    this.sideObjectiveStatus.getText().setColor(new Color(Color.red)); 
                    this.addComponent(sideObjectiveStatus);
                }
                else
                {
                    this.removeComponent(sideObjectiveStatus); 
                }
                
                //description
                sideObjective1Description = new TextBlock(50, 675 - mainObjectiveDescription.getHeight() - 50 - 25, 420, new Text(currentLevel.sideObjective.objectiveDescription));
                this.addComponent(sideObjective1Description);
            }
            
        }
        else
        {
            
        }
        
    }
    public void setSideObjectiveStatus(String string)
    {
        this.statusText = string;
        
        //repaint
        String playerLevelName = ((GameClientScene)this.owningScene).activeLevelData.filename;
        int levelNumber = this.playerReference.getLevelProgressionManager().getLevelNumberFromDataName(playerLevelName);
        Level currentLevel = this.playerReference.getLevelProgressionManager().levelMap.get(levelNumber);      
        this.repaintLevelDetails(currentLevel); 
    }
    
    @Override
    public void open()
    {
        super.open();
        
       //=================
       // Level Details
       //================
        
        
        
        //build new stuff
        String playerLevelName = ((GameClientScene)this.owningScene).activeLevelData.filename;
        int levelNumber = this.playerReference.getLevelProgressionManager().getLevelNumberFromDataName(playerLevelName);
        Level currentLevel = this.playerReference.getLevelProgressionManager().levelMap.get(levelNumber);
       
        this.repaintLevelDetails(currentLevel); 
       
       
       
    }
    
}
