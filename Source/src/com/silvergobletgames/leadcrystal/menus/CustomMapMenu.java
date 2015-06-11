package com.silvergobletgames.leadcrystal.menus;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.silvergobletgames.leadcrystal.combat.LevelProgressionManager.Level;
import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.core.LevelLoader;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.scenes.GameScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.util.SylverVector2f;
import com.silvergobletgames.sylver.windowsystem.*;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.TextBlock;
import com.silvergobletgames.sylver.windowsystem.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.media.opengl.GL2;

/**
 *
 * @author mike
 */
public class CustomMapMenu extends Window{
    
    //Player reference
    private GameScene sceneReference;
    
    //level detail fields
    private Label levelName; 
    private Label mainObjectiveHeader; private Label mainObjectiveCompleted;
    private Label mainObjectiveName;
    private TextBlock mainObjectiveDescription;
    private Label sideObjectiveHeader; private Label sideObjectiveCompleted;
    private Label sideObjectiveName;
    private TextBlock sideObjectiveDescription;
    private Button levelDetailBackground;
    
    //map background fields
    private SylverVector2f panOffset = new SylverVector2f(0,0);
    private Float closePanX = null;
    private Image mapBackground = new Image("customMapMenuBackground.png");
    private Image mapOutline = new Image("mapFrame.png"); 
    private SylverVector2f mapDimensions = new SylverVector2f(1165,850);
    
    //level button list
    private ArrayList<Button> levelButtons = new ArrayList();
    
    private Button closeButton;
    
    //movement buttons
    private Button rightMovementButton;
    private Button leftMovementButton;
    
    
    //==============
    // Constructor
    //==============
    public CustomMapMenu(float x, float y,GameScene scene)
    {
       //super constructor call, setting the background sprite and initial position
       super(new Image("mapFrame.png"),x,y,1200,900);
       
       this.sceneReference = scene;
                   
      
      

    }
    
    
    
    //===============
    // Class Methods
    //===============
    
    private void panMapRight()
    {
        SylverVector2f newOffset = new SylverVector2f();
        
        if(this.panOffset.x < 635)
        {
            this.panOffset.x = this.panOffset.x + 4;
            newOffset.x = newOffset.x + 2f;
        }
        
        
         //=================================
        // Update Button Relative Positions
        //=================================

        for(Button button: this.levelButtons)
        {
            //set button relative position
            button.setWindowRelativePosition(button.getWindowRelativePosition().x - newOffset.x * 2, button.getWindowRelativePosition().y - newOffset.y * 2);

            //hide button if it is out of the screen
            if(button.getWindowRelativePosition().x < 10 || button.getWindowRelativePosition().x > this.getWidth()-60
            ||button.getWindowRelativePosition().y < 10 || button.getWindowRelativePosition().y > this.getHeight())
            {
                button.setDisabled(true);
                button.setHidden(true);
            }
            else
            {
                button.setDisabled(false);
                button.setHidden(false);
                
               //set alpha based on distance 
                button.getImage().getOverlay("interact").getImage().setColor(new Color(1,1,1,1));  
                
                if(button.getWindowRelativePosition().x <100)
                {
                    float alpha = (float)button.getWindowRelativePosition().x/(float)100;
                    button.getImage().getOverlay("interact").getImage().setColor(new Color(1,1,1,alpha));
                }
                if(button.getWindowRelativePosition().x > (this.getWidth() -175))  // x=1040 y=1   x=1140 y =0
                {
                    
                    float alpha =  (-1/(float)100) * button.getWindowRelativePosition().x  +(57/(float)5);
                    button.getImage().getOverlay("interact").getImage().setColor(new Color(1,1,1,alpha));
                }
                
            }

        }
    }
    
    private void panMapLeft()
    {
        SylverVector2f newOffset = new SylverVector2f();
        
        if(this.panOffset.x > 0)
        {
            this.panOffset.x = this.panOffset.x - 4;
            newOffset.x = newOffset.x - 2f ;
        }
        
         //=================================
        // Update Button Relative Positions
        //=================================

        for(Button button: this.levelButtons)
        {
            //set button relative position
            button.setWindowRelativePosition(button.getWindowRelativePosition().x - newOffset.x * 2, button.getWindowRelativePosition().y - newOffset.y * 2);

            //hide button if it is out of the screen
            if(button.getWindowRelativePosition().x < 10 || button.getWindowRelativePosition().x > this.getWidth()-60
            ||button.getWindowRelativePosition().y < 10 || button.getWindowRelativePosition().y > this.getHeight())
            {
                button.setDisabled(true);
                button.setHidden(true);
            }
            else
            {
                button.setDisabled(false);
                button.setHidden(false);
                
                //set alpha based on distance 
                button.getImage().getOverlay("interact").getImage().setColor(new Color(1,1,1,1));  
                
                if(button.getWindowRelativePosition().x <100)
                {
                    float alpha = (float)button.getWindowRelativePosition().x/(float)100;
                    button.getImage().getOverlay("interact").getImage().setColor(new Color(1,1,1,alpha));
                }
                if(button.getWindowRelativePosition().x > (this.getWidth() -175))
                {
                    float alpha =  (-1/(float)100) * button.getWindowRelativePosition().x  +(57/(float)5);
                    button.getImage().getOverlay("interact").getImage().setColor(new Color(1,1,1,alpha));
                }

            }

        }
    }
    
    
    public void update()
    {
        
        if(this.isOpen)
        {
            if(owningScene != null)
            {           

                super.update();

            }
        }
    }
    
    public void draw(GL2 gl)
    {
         if(this.isOpen())
         {
            
            //=====================
            // Draw Background Map
            //=====================
            
             //set blending functions
            gl.glEnable(GL2.GL_BLEND);
            gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
            gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);

            //enable texturing
            gl.glEnable(GL2.GL_TEXTURE_2D);
            
            //bind texture
            Texture texture = this.mapBackground.getTexture();
            texture.bind(gl);
            //texture flip
            TextureCoords coords = texture.getSubImageTexCoords((int)this.panOffset.x, (int)this.panOffset.y,(int)this.panOffset.x + 1165, (int)this.panOffset.y + 850);
            float textureBottom = coords.bottom();
            float textureTop = coords.top();
            float textureLeft = coords.left();
            float textureRight = coords.right();

            //draw the image
            gl.glBegin(GL2.GL_QUADS);
            {
                //bottom left
                gl.glTexCoord2d(textureLeft, textureBottom);
                gl.glVertex2f(this.getPosition().x + 15 , this.getPosition().y + 15 ); 

                //bottom right
                gl.glTexCoord2d(textureRight, textureBottom);
                gl.glVertex2f(this.getPosition().x + this.mapDimensions.x + 15, this.getPosition().y + 15 );  

                //top right
                gl.glTexCoord2d(textureRight, textureTop);
                gl.glVertex2f(this.getPosition().x + this.mapDimensions.x + 15, this.getPosition().y + this.mapDimensions.y + 15);  

                //top left    
                gl.glTexCoord2d(textureLeft, textureTop);
                gl.glVertex2f(this.getPosition().x + 15 , this.getPosition().y + this.mapDimensions.y + 15);         
            }
            gl.glEnd(); 
            
            //================
            // Draw Components
            //================
            //draw components
            for(WindowComponent wc: windowComponents)       
                wc.draw(gl); 
            
            //=====================
            // Draw Map outline
            //=====================
            
            //set blending functions
            gl.glEnable(GL2.GL_BLEND);
            gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
            gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);

            //enable texturing
            gl.glEnable(GL2.GL_TEXTURE_2D);
            
            //bind texture
            texture = this.mapOutline.getTexture();
            texture.bind(gl);

            //draw the image
            gl.glBegin(GL2.GL_QUADS);
            {
                //bottom left
                gl.glTexCoord2d(0, 0);
                gl.glVertex2f(this.getPosition().x , this.getPosition().y ); 

                //bottom right
                gl.glTexCoord2d(1, 0);
                gl.glVertex2f(this.getPosition().x + 1200, this.getPosition().y);  

                //top right
                gl.glTexCoord2d(1, 1);
                gl.glVertex2f(this.getPosition().x + 1200, this.getPosition().y + 900);  

                //top left    
                gl.glTexCoord2d(0, 1);
                gl.glVertex2f(this.getPosition().x , this.getPosition().y + 900);         
            }
            gl.glEnd(); 
            
            this.closeButton.draw(gl);
            
             
        }
     }
    
    @Override
    public void open()
    {

        if(this.closePanX != null)
        {
            this.panOffset.x = closePanX;
            closePanX = null;
        }
        
        this.buildLevelButtons();
        for(Button button: this.levelButtons)
        {
            //set button relative position
           button.setWindowRelativePosition(button.getWindowRelativePosition().x - this.panOffset.x *1f , button.getWindowRelativePosition().y - this.panOffset.y * 1);

            //hide button if it is out of the screen
            if(button.getWindowRelativePosition().x < 10 || button.getWindowRelativePosition().x > this.getWidth()-60
            ||button.getWindowRelativePosition().y < 10 || button.getWindowRelativePosition().y > this.getHeight())
            {
                button.setDisabled(true);
                button.setHidden(true);
            }
            else
            {
                button.setDisabled(false);
                button.setHidden(false);
            }
        }
        
        if(this.owningScene != null && !this.isOpen)
        {
            Sound openSound = Sound.ambientSound("buffered/menuOpen.ogg", false);
            this.owningScene.add(openSound);
        }
        
        super.open();
        
        ((GameScene)this.getOwningScene()).getHud().closeDialogue();
        
        Game.getInstance().getInputHandler().getInputSnapshot().killMouseClick();
        this.update();
       
    }
    
    /**
     * Repaints the level details that display in the bottom right corner of the map screen. A 0 clears the fields.
     * @param levelNumber 
     */
    private void repaintLevelDetails(int levelNumber, Button button)
    {
        final int LEFT_POSITION = 15;
        final int RIGHT_POSITION = 725;
        
        //decide position of detail window
        int xPosition;
        if(button.getWindowRelativePosition().x > 632)
        {
            xPosition = LEFT_POSITION;
        }
        else
        {
             xPosition = RIGHT_POSITION;
        }
        
        this.removeComponent(levelName);
        this.removeComponent(mainObjectiveDescription);
        this.removeComponent(mainObjectiveHeader);
        this.removeComponent(mainObjectiveCompleted);
        this.removeComponent(sideObjectiveDescription);
        this.removeComponent(sideObjectiveHeader);
        this.removeComponent(sideObjectiveCompleted);
        this.removeComponent(levelDetailBackground);
        this.removeComponent(sideObjectiveName);
        this.removeComponent(mainObjectiveName);
            
        if(levelNumber != -1)
        {
            Level newLevel = sceneReference.getPlayer().getLevelProgressionManager().levelMap.get(levelNumber);
            
            //detail black
            this.levelDetailBackground = new Button(new Image("section1.png"), xPosition, 25, 450, 550);
            levelDetailBackground.dontKillClick=true;
            this.addComponent(levelDetailBackground);
            
             //=================
            // Level Details
            //================

            this.levelName = new Label("", xPosition + 125, 500);

            //main objective header
            Text objectiveText = new Text("Main Objective:",LeadCrystalTextType.HUD24);
            this.mainObjectiveHeader = new Label(objectiveText,xPosition + 35,450);
            this.mainObjectiveHeader.getText().setColor(new Color(200,100,8));

            //main objective completed
            Text completedText = new Text("Complete",LeadCrystalTextType.HUD24);
            this.mainObjectiveCompleted = new Label(completedText, xPosition + 210,450);
            this.mainObjectiveCompleted.getText().setColor(new Color(Color.green));

            //main objective name
            Text mainObjectiveNameText = new Text("",LeadCrystalTextType.HUD24);
            this.mainObjectiveName = new Label(mainObjectiveNameText, xPosition + 65,420);

            //main objective description
            this.mainObjectiveDescription = new TextBlock(xPosition + 65, 370, 375, new Text("",LeadCrystalTextType.HUD16));


            //side objective header
            Text sideObjectiveText = new Text("Side Objective:",LeadCrystalTextType.HUD24);
            this.sideObjectiveHeader = new Label(sideObjectiveText,xPosition + 35,230);
            this.sideObjectiveHeader.getText().setColor(new Color(200,100,8));

            //side objective complete
            completedText = new Text("Complete",LeadCrystalTextType.HUD24);
            this.sideObjectiveCompleted = new Label(completedText, xPosition + 210,230);
            this.sideObjectiveCompleted.getText().setColor(new Color(Color.green));

            //side objective name
            Text sideObjectiveNameText = new Text("",LeadCrystalTextType.HUD24);
            this.sideObjectiveName = new Label(sideObjectiveNameText, xPosition + 65,200);


            //side objective description
            sideObjectiveDescription = new TextBlock(xPosition + 65, 150, 375, new Text("",LeadCrystalTextType.HUD16));
            
            //level name
            this.removeComponent(levelName);
            Text newText = new Text(sceneReference.getPlayer().getLevelProgressionManager().levelMap.get(levelNumber).levelName,LeadCrystalTextType.HUD34);
            this.levelName = new Label(newText, xPosition + 220 - newText.getWidth()/2, 500);
            this.addComponent(levelName);
            
            //main objective description
            this.addComponent(mainObjectiveHeader);

            if(newLevel.mainObjective.complete)
            {
                this.addComponent(mainObjectiveCompleted);
            }
             
            //main objective name
            this.removeComponent(mainObjectiveName);
            this.mainObjectiveName.getText().setText(sceneReference.getPlayer().getLevelProgressionManager().levelMap.get(levelNumber).mainObjective.objectiveName + "-");
            this.addComponent(mainObjectiveName);
            
            this.removeComponent(mainObjectiveDescription);
            mainObjectiveDescription = new TextBlock(xPosition + 65, 390, 375, new Text(sceneReference.getPlayer().getLevelProgressionManager().levelMap.get(levelNumber).mainObjective.objectiveDescription,LeadCrystalTextType.HUD22));
            this.addComponent(mainObjectiveDescription);
            
            if(!newLevel.sideObjective.objectiveName.equals( "null"))
            {
                //side objective description
                this.addComponent(sideObjectiveHeader);
                if(newLevel.sideObjective.complete)
                {
                    this.addComponent(sideObjectiveCompleted);
                }

                //side objective name
                this.removeComponent(sideObjectiveName);
                this.sideObjectiveName.getText().setText(sceneReference.getPlayer().getLevelProgressionManager().levelMap.get(levelNumber).sideObjective.objectiveName + "-");
                this.addComponent(sideObjectiveName);


                this.removeComponent(sideObjectiveDescription);
                sideObjectiveDescription = new TextBlock(xPosition + 65, 170, 375, new Text(sceneReference.getPlayer().getLevelProgressionManager().levelMap.get(levelNumber).sideObjective.objectiveDescription,LeadCrystalTextType.HUD22));
                this.addComponent(sideObjectiveDescription);
            }
            else
            {
                this.removeComponent(sideObjectiveName);
                this.removeComponent(sideObjectiveDescription);
            }
            
        }
           
    }
    
    private void buildLevelButtons()
    {
        
        //remove old components
        this.levelButtons.clear();
        this.removeAllComponents();
        
        //close
        final Image closeImage = new Image("closeButton.png");
        this.closeButton = new Button(closeImage,1154,867,closeImage.getWidth()+1,closeImage.getHeight());
        this.closeButton.addActionListener(new ActionListener(){
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
        this.addComponent(this.closeButton);
        

       
       //===============
       // Level Buttons
       //===============
       
       
 
       //================== Custom 1========================
       final Button button1 = new Button("blank.png",100,300,75,75);
       
       Overlay correctOverlay = new Overlay(new Image("map_lock.png"));
       

       //if the level exists
       if(LevelLoader.getInstance().isLevelLoaded("customLevel1.lv"))
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.25,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button1.getImage().addImageEffect(effect);
       }
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;    
       button1.getImage().addOverlay("interact",correctOverlay);
       button1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                    
                     if(!button1.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button1.getImage().getOverlay("interact").getImage().setScale(1.2f);
                        playLevelHoverSound();
                       // repaintLevelDetails(2,button1);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                    
                     if(!button1.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button1.getImage().getOverlay("interact").getImage().setScale(1.0f);
                        //repaintLevelDetails(-1,button1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button1.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                            sceneReference.changeLevel("customLevel1.lv", "checkpoint1");
                     }
                }
            }
       });
       this.levelButtons.add(button1);
       this.addComponent(button1);
       
       
       //================== Custom 2========================
       final Button button2 = new Button("blank.png",400,500,75,75);
       
       correctOverlay = new Overlay(new Image("map_lock.png"));
       

       //if the level exists
       if(LevelLoader.getInstance().isLevelLoaded("customLevel2.lv"))
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.25,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button2.getImage().addImageEffect(effect);
       }
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;    
       button2.getImage().addOverlay("interact",correctOverlay);
       button2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                    
                     if(!button2.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button2.getImage().getOverlay("interact").getImage().setScale(1.2f);
                        playLevelHoverSound();
                       // repaintLevelDetails(2,button1);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                    
                     if(!button2.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button2.getImage().getOverlay("interact").getImage().setScale(1.0f);
                        //repaintLevelDetails(-1,button1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button2.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                            sceneReference.changeLevel("customLevel2.lv", "checkpoint1");
                     }
                }
            }
       });
       this.levelButtons.add(button2);
       this.addComponent(button2);
       
       
       //================== Custom 3========================
       final Button button3 = new Button("blank.png",700,300,75,75);
       
       correctOverlay = new Overlay(new Image("map_lock.png"));
       

       //if the level exists
       if(LevelLoader.getInstance().isLevelLoaded("customLevel3.lv"))
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.25,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button3.getImage().addImageEffect(effect);
       }
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;    
       button3.getImage().addOverlay("interact",correctOverlay);
       button3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                    
                     if(!button3.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button3.getImage().getOverlay("interact").getImage().setScale(1.2f);
                        playLevelHoverSound();
                       // repaintLevelDetails(2,button1);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                    
                     if(!button3.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button3.getImage().getOverlay("interact").getImage().setScale(1.0f);
                        //repaintLevelDetails(-1,button1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button3.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                            sceneReference.changeLevel("customLevel3.lv", "checkpoint1");
                     }
                }
            }
       });
       this.levelButtons.add(button3);
       this.addComponent(button3);
       
       //================== Custom 4========================
       final Button button4 = new Button("blank.png",1000,500,75,75);
       
       correctOverlay = new Overlay(new Image("map_lock.png"));
       

       //if the level exists
       if(LevelLoader.getInstance().isLevelLoaded("customLevel4.lv"))
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.25,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button4.getImage().addImageEffect(effect);
       }
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;    
       button4.getImage().addOverlay("interact",correctOverlay);
       button4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                    
                     if(!button4.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button4.getImage().getOverlay("interact").getImage().setScale(1.2f);
                        playLevelHoverSound();
                       // repaintLevelDetails(2,button1);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                    
                     if(!button4.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button4.getImage().getOverlay("interact").getImage().setScale(1.0f);
                        //repaintLevelDetails(-1,button1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button4.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                            sceneReference.changeLevel("customLevel4.lv", "checkpoint1");
                     }
                }
            }
       });
       this.levelButtons.add(button4);
       this.addComponent(button4);
       
       
       //================== Custom 5========================
       final Button button5 = new Button("blank.png",1300,300,75,75);
       
       correctOverlay = new Overlay(new Image("map_lock.png"));
       

       //if the level exists
       if(LevelLoader.getInstance().isLevelLoaded("customLevel5.lv"))
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.25,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button5.getImage().addImageEffect(effect);
       }
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;    
       button5.getImage().addOverlay("interact",correctOverlay);
       button5.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                    
                     if(!button5.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button5.getImage().getOverlay("interact").getImage().setScale(1.2f);
                        playLevelHoverSound();
                       // repaintLevelDetails(2,button1);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                    
                     if(!button5.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button5.getImage().getOverlay("interact").getImage().setScale(1.0f);
                        //repaintLevelDetails(-1,button1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button5.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                            sceneReference.changeLevel("customLevel5.lv", "checkpoint1");
                     }
                }
            }
       });
       this.levelButtons.add(button5);
       this.addComponent(button5);
       
       
       //================== Custom 6========================
       final Button button6 = new Button("blank.png",1600,500,75,75);
       
       correctOverlay = new Overlay(new Image("map_lock.png"));
       

       //if the level exists
       if(LevelLoader.getInstance().isLevelLoaded("customLevel6.lv"))
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.25,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button6.getImage().addImageEffect(effect);
       }
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;    
       button6.getImage().addOverlay("interact",correctOverlay);
       button6.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                    
                     if(!button6.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button6.getImage().getOverlay("interact").getImage().setScale(1.2f);
                        playLevelHoverSound();
                       // repaintLevelDetails(2,button1);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                    
                     if(!button6.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button6.getImage().getOverlay("interact").getImage().setScale(1.0f);
                        //repaintLevelDetails(-1,button1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button6.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                            sceneReference.changeLevel("customLevel6.lv", "checkpoint1");
                     }
                }
            }
       });
       this.levelButtons.add(button6);
       this.addComponent(button6);
       
       
       //================== Custom 7========================
       final Button button7 = new Button("blank.png",1900,300,75,75);
       
       correctOverlay = new Overlay(new Image("map_lock.png"));
       

       //if the level exists
       if(LevelLoader.getInstance().isLevelLoaded("customLevel7.lv"))
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.25,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button7.getImage().addImageEffect(effect);
       }
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;    
       button7.getImage().addOverlay("interact",correctOverlay);
       button7.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                    
                     if(!button7.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button7.getImage().getOverlay("interact").getImage().setScale(1.2f);
                        playLevelHoverSound();
                       // repaintLevelDetails(2,button1);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                    
                     if(!button7.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button7.getImage().getOverlay("interact").getImage().setScale(1.0f);
                        //repaintLevelDetails(-1,button1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button7.getImage().getOverlay("interact").getImage().getTextureReference().equals("map_lock.png"))
                     {
                            sceneReference.changeLevel("customLevel7.lv", "checkpoint1");
                     }
                }
            }
       });
       this.levelButtons.add(button7);
       this.addComponent(button7);
       
       
       
       
       
     
       
       
       
        //==================
        // Movement Buttons
        //==================
        
        Image i = new Image("map_arrow.png");
        i.setHorizontalFlip(true);
        rightMovementButton = new Button(i, 620 , 760, 80, 80);
        rightMovementButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered"))
                {
                    rightMovementButton.getImage().setBrightness(1.5f);
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND)); 
                
                }
                if (e.getActionCommand().equals("mouseExited")) 
                {
                    rightMovementButton.getImage().setBrightness(1f);
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND)); 
                
                }
                if(e.getActionCommand().equals("mouseDown"))
                {
                    panMapRight();
                }
            }
        
        });
        this.addComponent(rightMovementButton);
        
        
        leftMovementButton = new Button(new Image("map_arrow.png"), 600  -i.getWidth() - 20, 760, 80, 80);
        leftMovementButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered"))
                {
                    leftMovementButton.getImage().setBrightness(1.5f);
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND)); 
                
                }
                if (e.getActionCommand().equals("mouseExited")) 
                {
                    leftMovementButton.getImage().setBrightness(1f);
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.ACTIVEHAND)); 
                }
                if(e.getActionCommand().equals("mouseDown"))
                {
                    panMapLeft();

                }
            }
        
        });
        this.addComponent(leftMovementButton);
       
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
    
    private void playLevelHoverSound()
    {
        //add sound
        Sound goldSound = Sound.ambientSound("buffered/buttonBoop.ogg", false);
        CustomMapMenu.this.getOwningScene().add(goldSound);
    }
    

    
    
}
