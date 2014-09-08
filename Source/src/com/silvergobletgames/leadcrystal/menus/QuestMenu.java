package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.TextBlock;
import com.silvergobletgames.sylver.windowsystem.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.silvergobletgames.leadcrystal.combat.LevelProgressionManager.Level;
import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Game;

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
    private Label mainObjectiveTitle;
    private Label mainObjectiveStatus; private String mainObjectiveStatusText = "";
    private TextBlock mainObjectiveDescription;
    
    private Label sideObjectiveTitle;
    private Label sideObjectiveHeader; private Label sideObjectiveCompleted; 
    private Label sideObjectiveStatus; private String sideObjectiveStatusText = "";
    private TextBlock sideObjective1Description;
    
    
    
    //==============
    // Constructor
    //==============
    public QuestMenu(float x, float y,PlayerEntity player)
    {
       //super constructor call, setting the background sprite and initial position
       super(new Image("tallFrame.png"),x,y,550,900);
       
       this.playerReference = player;
       
         //text
        Text menuText = new Text("Objectives",LeadCrystalTextType.HUD24);
        Label menuTextLabel = new Label(menuText,275 - menuText.getWidth()/2,840);
        this.addComponent(menuTextLabel);
        
        //close
        final Image closeImage = new Image("closeButton.png");
        Button closeButton = new Button(closeImage,504,867,closeImage.getWidth()+1,closeImage.getHeight());
        closeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {

                  closeImage.setBrightness(1.5f);
                  Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.HAND)); 
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
  
    }
    
    
    
    //===============
    // Class Methods
    //===============
    
    /**
     * Repains level details based on current level
     */
    public void repaintLevelDetails()           
    {
        //get current level 
        String playerLevelName = ((GameClientScene)this.owningScene).activeLevelData.filename; 
        Level currentLevel= null;
        try
        {
            int levelNumber= this.playerReference.getLevelProgressionManager().getLevelNumberFromDataName(playerLevelName);
            currentLevel = this.playerReference.getLevelProgressionManager().levelMap.get(levelNumber);
        }
        catch(Exception e)
        {
            //expected if level is town
        }    
        
        //clear old stuff
        this.removeComponent(levelName);
        this.removeComponent(mainObjectiveDescription);
        this.removeComponent(mainObjectiveHeader);
        this.removeComponent(mainObjectiveCompleted);
        this.removeComponent(sideObjective1Description);
        this.removeComponent(sideObjectiveHeader);
        this.removeComponent(sideObjectiveCompleted);
        this.removeComponent(sideObjectiveStatus); 
        this.removeComponent(mainObjectiveTitle);
        this.removeComponent(sideObjectiveTitle);
        
        if(currentLevel == null)
        {
            return;
        }
           
        //================
        //main objective  
        //=================

        //header
        Text tex = new Text("Main Objective:",LeadCrystalTextType.HUD28);
        this.mainObjectiveHeader = new Label(tex,50,700);
        this.mainObjectiveHeader.getText().setColor(new Color(200,100,8));
        this.addComponent(mainObjectiveHeader);     

        //complete
        if(currentLevel.mainObjective.complete)
        {
            tex = new Text("Complete",LeadCrystalTextType.HUD28);
            this.mainObjectiveCompleted = new Label(tex, 250,700);
            this.mainObjectiveCompleted.getText().setColor(new Color(Color.green));
            this.addComponent(mainObjectiveCompleted);
        }    
        
        //status
        if(!mainObjectiveStatusText.equals("complete"))
        {
            tex =new Text(mainObjectiveStatusText,LeadCrystalTextType.HUD24);
            this.mainObjectiveStatus = new Label(tex, 250,700);
            this.mainObjectiveStatus.getText().setColor(new Color(Color.red)); 
            this.addComponent(mainObjectiveStatus);
        }
        else
        {
            this.removeComponent(mainObjectiveStatus); 
        }

        //title and description
        tex = new Text(currentLevel.mainObjective.objectiveName + "-",LeadCrystalTextType.HUD28);
        this.mainObjectiveTitle = new Label(tex,75,660);
        this.addComponent(mainObjectiveTitle);
        this.mainObjectiveDescription = new TextBlock(75, 630, 450, new Text(currentLevel.mainObjective.objectiveDescription,LeadCrystalTextType.HUD24));
        this.addComponent(mainObjectiveDescription);


        //==============
        //side objective
        //===============
        if(currentLevel.sideObjective != null && !currentLevel.sideObjective.objectiveName.equals("null"))
        {          
            //header
            tex =new Text("Side Objective:",LeadCrystalTextType.HUD28);
            this.sideObjectiveHeader = new Label(tex,50,600 - mainObjectiveDescription.getHeight() - 50);
            this.sideObjectiveHeader.getText().setColor(new Color(200,100,8));
            this.addComponent(sideObjectiveHeader);

            //complete
            if(currentLevel.sideObjective.complete || this.sideObjectiveStatusText.equals("complete"))
            {
                tex =new Text("Complete",LeadCrystalTextType.HUD28);
                this.sideObjectiveCompleted = new Label(tex, 250,600 - mainObjectiveDescription.getHeight() - 50);
                this.sideObjectiveCompleted.getText().setColor(new Color(Color.green)); 
                this.addComponent(sideObjectiveCompleted);
            }

            //status
            if(!sideObjectiveStatusText.equals("complete"))
            {
                tex =new Text(sideObjectiveStatusText,LeadCrystalTextType.HUD24);
                this.sideObjectiveStatus = new Label(tex, 250,600 - mainObjectiveDescription.getHeight() - 50);
                this.sideObjectiveStatus.getText().setColor(new Color(Color.red)); 
                this.addComponent(sideObjectiveStatus);
            }
            else
            {
                this.removeComponent(sideObjectiveStatus); 
            }

            tex = new Text(currentLevel.sideObjective.objectiveName + "-",LeadCrystalTextType.HUD28);
            this.sideObjectiveTitle = new Label(tex,75,600 - mainObjectiveDescription.getHeight() - 50 -40);
            this.addComponent(sideObjectiveTitle);
            //description
            sideObjective1Description = new TextBlock(75, 600 - mainObjectiveDescription.getHeight() - 50 - 70, 450, new Text(currentLevel.sideObjective.objectiveDescription,LeadCrystalTextType.HUD24));
            this.addComponent(sideObjective1Description);
        }

    }
    
     
    public void setSideObjectiveStatus(String string)
    {
        this.sideObjectiveStatusText = string;
        
        //repaint
        this.repaintLevelDetails(); 
    }
    
    public void setMainObjectiveStatus(String string)
    {
        this.mainObjectiveStatusText = string;
        
        //repaint
        this.repaintLevelDetails(); 
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
        
        //repaint
        this.repaintLevelDetails();
    }
    
}
