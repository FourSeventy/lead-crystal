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
import com.silvergobletgames.leadcrystal.netcode.LevelCompletePacket;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;

/**
 *
 * @author mike
 */
public class LevelCompleteMenu extends Window{
    
    //Player reference
    private PlayerEntity playerReference;
    
    //level detail fields
    private Label levelCompleteText;
    private Label levelName;
    private Label mainObjectiveHeader,  mainObjectiveCompleted; 
    private Label sideObjectiveHeader, sideObjectiveCompleted;
    //rewards fields
    private Button skillPointButton, currencyButton, timeButton;
    private Label skillPoints, currency, completionTime;

    
    
    
    //==============
    // Constructor
    //==============
    public LevelCompleteMenu(float x, float y,PlayerEntity player)
    {
       //super constructor call, setting the background sprite and initial position
       super(new Image("questMenu.png"),x,y,1200,900);
       
       this.playerReference = player;
       
       Button closeButton = new Button("deleteButton.png",1100,800,50,50);
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
    public void repaintLevelDetails(LevelCompletePacket packet)
    {
//        private Label levelCompleteText;
//    private Label levelName;
//    private Label mainObjectiveHeader,  mainObjectiveCompleted; 
//    private Label sideObjectiveHeader, sideObjectiveCompleted;
//    //rewards fields
//    private Button skillPointButton, currencyButton, timeButton;
//    private Label skillPoints, currency, completionTime;
    
        //=================
        // remove old stuff
        //=================
        this.removeComponent(levelCompleteText);
        this.removeComponent(levelName);
        this.removeComponent(mainObjectiveHeader);
        this.removeComponent(mainObjectiveCompleted);
        this.removeComponent(sideObjectiveHeader);
        this.removeComponent(sideObjectiveCompleted);
        this.removeComponent(skillPointButton);
        this.removeComponent(currencyButton);
        this.removeComponent(timeButton);
        this.removeComponent(skillPoints);
        this.removeComponent(currency);
        this.removeComponent(completionTime);

        
        //================
        // Build Screen
        //================
        
       // level we just finished
       Level completedLevel = this.playerReference.getLevelProgressionManager().levelMap.get((int)packet.levelNumber);    
       
       
       //level complete text
       levelCompleteText = new Label("Level Complete!",50,825);
       levelCompleteText.getText().setScale(1.7f);
       levelCompleteText.getText().setColor(new Color(Color.white));
       this.addComponent(levelCompleteText);

       //level name
       this.levelName = new Label(completedLevel.levelName, 50, 775);
       levelName.getText().setScale(1.2f);
       levelName.getText().setColor(new Color(Color.white));
       this.addComponent(levelName);
       
       //main objective
       this.mainObjectiveHeader = new Label("Main Objective: ",50,600);
       this.mainObjectiveHeader.getText().setColor(new Color(Color.white));
       this.addComponent(mainObjectiveHeader);
       this.addComponent(mainObjectiveHeader);
                  
       //main objective status
       if(completedLevel.mainObjective.complete || packet.mainObjective)
       {
           this.mainObjectiveCompleted = new Label("Complete", 250,600);
           this.mainObjectiveCompleted.getText().setColor(new Color(Color.green));
       }
       else
       {
           this.mainObjectiveCompleted = new Label("Not Complete", 250 ,600);
           this.mainObjectiveCompleted.getText().setColor(new Color(Color.red));
       }
       this.addComponent(mainObjectiveCompleted);
       
      
        //side objective
        this.sideObjectiveHeader = new Label("Side Objective 1: ",50,500);
        this.sideObjectiveHeader.getText().setColor(new Color(Color.white));
        this.addComponent(sideObjectiveHeader);
        
        //side objective status
       if(completedLevel.sideObjective.complete || packet.sideObjective)
       {
           this.sideObjectiveCompleted = new Label("Complete", 250,500);
           this.sideObjectiveCompleted.getText().setColor(new Color(Color.green));
       }
       else
       {
           this.sideObjectiveCompleted = new Label("Not Complete", 250 ,500);
           this.sideObjectiveCompleted.getText().setColor(new Color(Color.red));
       }
       this.addComponent(sideObjectiveCompleted);
       
       // skill points
       this.skillPointButton = new Button(new Image("skillPoint.png"), 50, 400, 40, 40);
       this.addComponent(skillPointButton);
       
       float points = 0;
       if(packet.mainObjective && completedLevel.mainObjective.skillPointAward)
           points += 1;
       if(packet.sideObjective && completedLevel.sideObjective.skillPointAward)
           points += 1;
       this.skillPoints = new Label(Float.toString(points), 200, 400);
       this.addComponent(skillPoints);
       
       
       // currency
       this.currencyButton = new Button(new Image("currency2.png"), 50, 300, 40, 40);
       this.addComponent(currencyButton);
       
       float currency = 0;
       if(packet.mainObjective)
           currency += completedLevel.mainObjective.currencyAward;
       if(packet.sideObjective)
           currency +=completedLevel.sideObjective.currencyAward;
       this.currency = new Label(Float.toString(currency), 200, 300);
       this.addComponent(this.currency);
       
       // completion time


       
   }
                  
        
    
}
