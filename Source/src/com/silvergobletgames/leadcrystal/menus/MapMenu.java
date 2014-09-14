package com.silvergobletgames.leadcrystal.menus;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.silvergobletgames.leadcrystal.combat.LevelProgressionManager.Level;
import com.silvergobletgames.leadcrystal.core.CursorFactory;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
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
public class MapMenu extends Window{
    
    //Player reference
    private GameClientScene sceneReference;
    
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
    private Image mapBackground = new Image("mapBig.jpg");
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
    public MapMenu(float x, float y,GameClientScene scene)
    {
       //super constructor call, setting the background sprite and initial position
       super(new Image("mapFrame.png"),x,y,1200,900);
       
       this.sceneReference = scene;
                   
      
       //=================
       // Level Details
       //================
       
       this.levelName = new Label("", 850, 500);
       
       //main objective header
       Text objectiveText = new Text("Main Objective:",LeadCrystalTextType.HUD24);
       this.mainObjectiveHeader = new Label(objectiveText,760,450);
       this.mainObjectiveHeader.getText().setColor(new Color(200,100,8));
       
       //main objective completed
       Text completedText = new Text("Complete",LeadCrystalTextType.HUD24);
       this.mainObjectiveCompleted = new Label(completedText, 935,450);
       this.mainObjectiveCompleted.getText().setColor(new Color(Color.green));
       
       //main objective name
       Text mainObjectiveNameText = new Text("",LeadCrystalTextType.HUD24);
       this.mainObjectiveName = new Label(mainObjectiveNameText, 790,420);
     
       //main objective description
       this.mainObjectiveDescription = new TextBlock(790, 370, 375, new Text("",LeadCrystalTextType.HUD16));

       
       //side objective header
       Text sideObjectiveText = new Text("Side Objective:",LeadCrystalTextType.HUD24);
       this.sideObjectiveHeader = new Label(sideObjectiveText,760,230);
       this.sideObjectiveHeader.getText().setColor(new Color(200,100,8));
       
       //side objective complete
       completedText = new Text("Complete",LeadCrystalTextType.HUD24);
       this.sideObjectiveCompleted = new Label(completedText, 935,230);
       this.sideObjectiveCompleted.getText().setColor(new Color(Color.green));
       
       //side objective name
       Text sideObjectiveNameText = new Text("",LeadCrystalTextType.HUD24);
       this.sideObjectiveName = new Label(sideObjectiveNameText, 790,200);
       

       //side objective description
       sideObjectiveDescription = new TextBlock(790, 150, 375, new Text("",LeadCrystalTextType.HUD16));

    }
    
    
    
    //===============
    // Class Methods
    //===============
    
    private void panMapRight()
    {
        SylverVector2f newOffset = new SylverVector2f();
        
        if(this.panOffset.x < this.mapBackground.getWidth()/2)
        {
            this.panOffset.x = this.panOffset.x + 2;
            newOffset.x = newOffset.x + 1.94f;
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
                button.getImage().getOverlay("img").getImage().setColor(new Color(1,1,1,1));  
                
                if(button.getWindowRelativePosition().x <100)
                {
                    float alpha = (float)button.getWindowRelativePosition().x/(float)100;
                    button.getImage().getOverlay("img").getImage().setColor(new Color(1,1,1,alpha));
                }
                if(button.getWindowRelativePosition().x > (this.getWidth() -175))  // x=1040 y=1   x=1140 y =0
                {
                    
                    float alpha =  (-1/(float)100) * button.getWindowRelativePosition().x  +(57/(float)5);
                    button.getImage().getOverlay("img").getImage().setColor(new Color(1,1,1,alpha));
                }
                
            }

        }
    }
    
    private void panMapLeft()
    {
        SylverVector2f newOffset = new SylverVector2f();
        
        if(this.panOffset.x > 0)
        {
            this.panOffset.x = this.panOffset.x - 2;
            newOffset.x = newOffset.x - 1.94f ;
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
                button.getImage().getOverlay("img").getImage().setColor(new Color(1,1,1,1));  
                
                if(button.getWindowRelativePosition().x <100)
                {
                    float alpha = (float)button.getWindowRelativePosition().x/(float)100;
                    button.getImage().getOverlay("img").getImage().setColor(new Color(1,1,1,alpha));
                }
                if(button.getWindowRelativePosition().x > (this.getWidth() -175))
                {
                    float alpha =  (-1/(float)100) * button.getWindowRelativePosition().x  +(57/(float)5);
                    button.getImage().getOverlay("img").getImage().setColor(new Color(1,1,1,alpha));
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

              
                //===============
                // Handle Input
                //===============
                SylverVector2f newOffset = new SylverVector2f();

                if(Game.getInstance().getInputHandler().getInputSnapshot().isKeyPressed(KeyEvent.VK_LEFT))
                {
                    this.panMapLeft();
                }
                if(Game.getInstance().getInputHandler().getInputSnapshot().isKeyPressed(KeyEvent.VK_RIGHT))
                {
                    this.panMapRight();
                }

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
            TextureCoords coords = texture.getSubImageTexCoords((int)this.panOffset.x, (int)this.panOffset.y,(int)this.panOffset.x + texture.getWidth()/2, (int)this.panOffset.y + texture.getHeight()/2);
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
           button.setWindowRelativePosition(button.getWindowRelativePosition().x - this.panOffset.x * 2, button.getWindowRelativePosition().y - this.panOffset.y * 2);

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
       
    }
    
    /**
     * Repaints the level details that display in the bottom right corner of the map screen. A 0 clears the fields.
     * @param levelNumber 
     */
    private void repaintLevelDetails(int levelNumber)
    {
        if(levelNumber != -1)
        {
            Level newLevel = ((GameClientScene)owningScene).hostLevelProgression.levelMap.get(levelNumber);
            
            //detail black
            this.levelDetailBackground = new Button(new Image("section1.png"), 725, 25, 450, 550);
            levelDetailBackground.dontKillClick=true;
            this.addComponent(levelDetailBackground);
            
            //level name
            this.removeComponent(levelName);
            Text newText = new Text(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(levelNumber).levelName,LeadCrystalTextType.HUD34);
            this.levelName = new Label(newText, 945 - newText.getWidth()/2, 500);
            this.addComponent(levelName);
            
            //main objective description
            this.addComponent(mainObjectiveHeader);
            if(newLevel.mainObjective.complete)
            {
                this.addComponent(mainObjectiveCompleted);
            }
             
            //main objective name
            this.removeComponent(mainObjectiveName);
            this.mainObjectiveName.getText().setText(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(levelNumber).mainObjective.objectiveName + "-");
            this.addComponent(mainObjectiveName);
            
            this.removeComponent(mainObjectiveDescription);
            mainObjectiveDescription = new TextBlock(790, 390, 375, new Text(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(levelNumber).mainObjective.objectiveDescription,LeadCrystalTextType.HUD22));
            this.addComponent(mainObjectiveDescription);
            
            //side objective description
            this.addComponent(sideObjectiveHeader);
            if(newLevel.sideObjective.complete)
            {
                this.addComponent(sideObjectiveCompleted);
            }
            
            //side objective name
            this.removeComponent(sideObjectiveName);
            this.sideObjectiveName.getText().setText(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(levelNumber).sideObjective.objectiveName + "-");
            this.addComponent(sideObjectiveName);
            
            
            this.removeComponent(sideObjectiveDescription);
            sideObjectiveDescription = new TextBlock(790, 170, 375, new Text(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(levelNumber).sideObjective.objectiveDescription,LeadCrystalTextType.HUD22));
            this.addComponent(sideObjectiveDescription);
            
        }
        else
        {
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
        this.addComponent(this.closeButton);
        

       
       //===============
       // Level Buttons
       //===============
       
       //================== desert 0 ======================
       final Button button0 = new Button(new Image("blank.png"),175,225,75,75);
       
       Overlay correctOverlay0 = new Overlay(new Image("map_questionmark.png"));
       correctOverlay0.getImage().setDimensions(75, 75);
       correctOverlay0.useRelativeSize = false;    
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(0).mainObjective.complete)
       {
           correctOverlay0.getImage().setTextureReference("map_checkmark.png"); 
           correctOverlay0.getImage().setDimensions(75, 75);
       }
       else
       {
            Object points[] = {0,.5,0};
            int durations[] = {90,90};
            MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
            effect.setRepeating(true);
            button0.getImage().addImageEffect(effect);
       }
       button0.getImage().addOverlay("img",correctOverlay0);
       button0.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {            
                   button0.getImage().getOverlay("img").getImage().setScale(1.2f); 
                   playLevelHoverSound();
                   repaintLevelDetails(0);
                }
                if (e.getActionCommand().equals("mouseExited")) {
                   button0.getImage().getOverlay("img").getImage().setScale(1.0f); 
                    repaintLevelDetails(-1);
                }
                if(e.getActionCommand().equals("clicked"))
                {
                    ((GameClientScene)owningScene).sendChooseLevelPacket(0);
                }
            }
       });
       this.levelButtons.add(button0);
       this.addComponent(button0);
       
       //================== desert 1========================
       
       final Button button = new Button(new Image("blank.png"),175,425,75,75);
       
       Overlay correctOverlay = new Overlay(new Image("map_questionmark.png"));
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;    
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(1).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_checkmark.png")); 
           correctOverlay.getImage().setDimensions(75, 75);
       }
       else
       {
            Object points[] = {0,.5,0};
            int durations[] = {90,90};
            MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
            effect.setRepeating(true);
            button.getImage().addImageEffect(effect);
       }
       button.getImage().addOverlay("img",correctOverlay);
       button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {            
                   button.getImage().getOverlay("img").getImage().setScale(1.2f); 
                   playLevelHoverSound();
                   repaintLevelDetails(1);
                }
                if (e.getActionCommand().equals("mouseExited")) {
                   button.getImage().getOverlay("img").getImage().setScale(1.0f); 
                    repaintLevelDetails(-1);
                }
                if(e.getActionCommand().equals("clicked"))
                {
                    ((GameClientScene)owningScene).sendChooseLevelPacket(1);
                }
            }
       });
       this.levelButtons.add(button);
       this.addComponent(button);
       
       //================== desert 2========================
       final Button button1 = new Button("blank.png",375,425,75,75);
       
       correctOverlay = new Overlay(new Image("map_lock.png"));
       
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(2).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_checkmark.png"));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(1).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button1.getImage().addImageEffect(effect);
       }
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;    
       button1.getImage().addOverlay("img",correctOverlay);
       button1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                    
                     if(!button1.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button1.getImage().getOverlay("img").getImage().setScale(1.2f);
                        playLevelHoverSound();
                        repaintLevelDetails(2);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                    
                     if(!button1.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button1.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button1.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                     {
                            ((GameClientScene)owningScene).sendChooseLevelPacket(2);
                     }
                }
            }
       });
       this.levelButtons.add(button1);
       this.addComponent(button1);
       
       //================== desert 3========================
       final Button button2 = new Button("blank.png",575,425,75,75);
       
       correctOverlay = new Overlay(new Image("map_lock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(3).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_checkmark.png"));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(2).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button2.getImage().addImageEffect(effect);
       }
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;  
       button2.getImage().addOverlay("img",correctOverlay);
       button2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                     if(!button2.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button2.getImage().getOverlay("img").getImage().setScale(1.2f);
                        playLevelHoverSound();
                        repaintLevelDetails(3);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button2.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button2.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button2.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                        ((GameClientScene)owningScene).sendChooseLevelPacket(3);
                }
            }
       });
       this.levelButtons.add(button2);
       this.addComponent(button2);
       
       //================== desert 4========================
       final Button button3 = new Button("blank.png",500,700,75,75);
       
       correctOverlay = new Overlay(new Image("map_lock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(4).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_checkmark.png"));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(2).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button3.getImage().addImageEffect(effect);
       }
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;  
       button3.getImage().addOverlay("img",correctOverlay);
       button3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                    
                     if(!button3.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button3.getImage().getOverlay("img").getImage().setScale(1.2f);
                        playLevelHoverSound();
                        repaintLevelDetails(4);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button3.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button3.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button3.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                        ((GameClientScene)owningScene).sendChooseLevelPacket(4);
                }
            }
       });
       this.levelButtons.add(button3);
       this.addComponent(button3);
       
        //================== desert 5========================
       final Button button4 = new Button("blank.png",965,425,75,75);
       
       correctOverlay = new Overlay(new Image("map_lock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(5).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_checkmark.png"));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(3).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button4.getImage().addImageEffect(effect);
       }
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;  
       button4.getImage().addOverlay("img",correctOverlay);
       button4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                     if(!button4.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button4.getImage().getOverlay("img").getImage().setScale(1.2f);
                        playLevelHoverSound();
                        repaintLevelDetails(5);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button4.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button4.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {                       
                     if(!button4.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        ((GameClientScene)owningScene).sendChooseLevelPacket(5);
                        closePanX = new Float(150);
                     }
                }
            }
       });
       this.levelButtons.add(button4);
       this.addComponent(button4);
       
       
       
       //================== cave 1 ========================
  
        final Button button5 = new Button("blank.png",1400,425,75,75);
        
        correctOverlay = new Overlay(new Image("map_lock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(6).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_checkmark.png"));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(5).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button5.getImage().addImageEffect(effect);
       }
       
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;  
       button5.getImage().addOverlay("img",correctOverlay);
       button5.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                     if(!button5.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png")) 
                     {
                        button5.getImage().getOverlay("img").getImage().setScale(1.2f);
                        playLevelHoverSound();
                        repaintLevelDetails(6);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button5.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button5.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button5.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                        ((GameClientScene)owningScene).sendChooseLevelPacket(6);
                }
            }
       });
       this.levelButtons.add(button5);
       this.addComponent(button5);
       
       
       //================== cave 2========================
  
        final Button button6 = new Button("blank.png",1600,625,75,75);
        
        correctOverlay = new Overlay(new Image("map_lock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(7).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_checkmark.png"));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(6).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button6.getImage().addImageEffect(effect);
       }
       
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;  
       button6.getImage().addOverlay("img",correctOverlay);
       button6.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                     if(!button6.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png")) 
                     {
                        button6.getImage().getOverlay("img").getImage().setScale(1.2f);
                        playLevelHoverSound();
                        repaintLevelDetails(7);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button6.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button6.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button6.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                        ((GameClientScene)owningScene).sendChooseLevelPacket(7);
                }
            }
       });
       this.levelButtons.add(button6);
       this.addComponent(button6);
       
       //================== cave 3========================
  
        final Button button7 = new Button("blank.png",1600,325,75,75);
        
        correctOverlay = new Overlay(new Image("map_lock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(8).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_checkmark.png"));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(6).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button7.getImage().addImageEffect(effect);
       }
       
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;  
       button7.getImage().addOverlay("img",correctOverlay);
       button7.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                     if(!button7.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png")) 
                     {
                        button7.getImage().getOverlay("img").getImage().setScale(1.2f);
                        playLevelHoverSound();
                        repaintLevelDetails(8);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button7.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button7.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button7.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                        ((GameClientScene)owningScene).sendChooseLevelPacket(8);
                }
            }
       });
       this.levelButtons.add(button7);
       this.addComponent(button7);
       
       //================== cave 4========================
  
        final Button button8 = new Button("blank.png",1750,325,75,75);
        
        correctOverlay = new Overlay(new Image("map_lock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(9).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_checkmark.png"));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(7).mainObjective.complete || ((GameClientScene)owningScene).hostLevelProgression.levelMap.get(8).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button8.getImage().addImageEffect(effect);
       }
       
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;  
       button8.getImage().addOverlay("img",correctOverlay);
       button8.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                     if(!button8.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png")) 
                     {
                        button8.getImage().getOverlay("img").getImage().setScale(1.2f);
                        playLevelHoverSound();
                        repaintLevelDetails(9);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button8.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button8.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button8.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                        ((GameClientScene)owningScene).sendChooseLevelPacket(9);
                }
            }
       });
       this.levelButtons.add(button8);
       this.addComponent(button8);
       
       //================== cave 5========================
  
        final Button button9 = new Button("blank.png",1950,325,75,75);
        
        correctOverlay = new Overlay(new Image("map_lock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(10).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_checkmark.png"));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(9).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("map_questionmark.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button9.getImage().addImageEffect(effect);
       }
       
       
       correctOverlay.getImage().setDimensions(75, 75);
       correctOverlay.useRelativeSize = false;  
       button9.getImage().addOverlay("img",correctOverlay);
       button9.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                     if(!button9.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png")) 
                     {
                        button9.getImage().getOverlay("img").getImage().setScale(1.2f);
                        playLevelHoverSound();
                        repaintLevelDetails(10);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button9.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                     {
                        button9.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button9.getImage().getOverlay("img").getImage().getTextureReference().equals("map_lock.png"))
                        ((GameClientScene)owningScene).sendChooseLevelPacket(10);
                }
            }
       });
       this.levelButtons.add(button9);
       this.addComponent(button9);
       
       
       
       
       
        //==================
        // Movement Buttons
        //==================
        
        Image i = new Image("map_arrow.png");
        i.setHorizontalFlip(true);
        rightMovementButton = new Button(i, 1025, 700, 100, 100);
        rightMovementButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered"))
                {
                    rightMovementButton.getImage().setBrightness(1.5f);
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.HAND)); 
                
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
        
        
        leftMovementButton = new Button(new Image("map_arrow.png"), 900, 700, 100, 100);
        leftMovementButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered"))
                {
                    leftMovementButton.getImage().setBrightness(1.5f);
                    Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorFactory.CursorType.HAND)); 
                
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
        MapMenu.this.getOwningScene().add(goldSound);
    }

    
}
