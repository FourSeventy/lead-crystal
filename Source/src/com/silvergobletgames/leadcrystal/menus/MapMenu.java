package com.silvergobletgames.leadcrystal.menus;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
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
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.util.SylverVector2f;
import com.silvergobletgames.sylver.windowsystem.*;
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
    private TextBlock mainObjectiveDescription;
    private Label sideObjectiveHeader; private Label sideObjectiveCompleted;
    private TextBlock sideObjectiveDescription;
    
    //map background fields
    private SylverVector2f panOffset = new SylverVector2f(0,0);
    private Image mapBackground = new Image("mapBig.jpg");
    private Image mapOutline = new Image("mapScreenBack.png");
    
    //level button list
    private ArrayList<Button> levelButtons = new ArrayList();
    
    
    //==============
    // Constructor
    //==============
    public MapMenu(float x, float y,GameClientScene scene)
    {
       //super constructor call, setting the background sprite and initial position
       super(new Image("mapScreenBack.png"),x,y,1200,900);
       
       this.sceneReference = scene;
                   
      
       //=================
       // Level Details
       //================
       
       this.levelName = new Label("", 850, 500);
       levelName.getText().setScale(1.4f);
       levelName.getText().setColor(new Color(Color.black));
       
       this.mainObjectiveHeader = new Label("Main Objective: ",800,450);
       this.mainObjectiveHeader.getText().setColor(new Color(Color.red));
       this.mainObjectiveCompleted = new Label("Complete", 975,450);
       this.mainObjectiveCompleted.getText().setColor(new Color(Color.green));
     
       this.mainObjectiveDescription = new TextBlock(800, 410, 375, new Text(""));

       
       this.sideObjectiveHeader = new Label("Side Objective: ",800,230);
       this.sideObjectiveHeader.getText().setColor(new Color(Color.red));
       this.sideObjectiveCompleted = new Label("Complete", 975,230);
       this.sideObjectiveCompleted.getText().setColor(new Color(Color.green));

       
       sideObjectiveDescription = new TextBlock(800, 200, 375, new Text(""));

    }
    
    
    
    //===============
    // Class Methods
    //===============
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

                if(Game.getInstance().getInputHandler().getInputSnapshot().isKeyPressed(KeyEvent.VK_UP))
                {
                    if(this.panOffset.y < this.mapBackground.getHeight()/2)
                    {
                        this.panOffset.y = this.panOffset.y + 2;
                        newOffset.y = newOffset.y + 2;
                    }
                }
                if(Game.getInstance().getInputHandler().getInputSnapshot().isKeyPressed(KeyEvent.VK_DOWN))
                {
                    if(this.panOffset.y > 0)
                    {
                    this.panOffset.y = this.panOffset.y - 2;
                    newOffset.y = newOffset.y - 2;
                    }
                }
                if(Game.getInstance().getInputHandler().getInputSnapshot().isKeyPressed(KeyEvent.VK_LEFT))
                {
                    if(this.panOffset.x > 0)
                    {
                        this.panOffset.x = this.panOffset.x - 2;
                        newOffset.x = newOffset.x -2 ;
                    }
                }
                if(Game.getInstance().getInputHandler().getInputSnapshot().isKeyPressed(KeyEvent.VK_RIGHT))
                {
                    if(this.panOffset.x < this.mapBackground.getWidth()/2)
                    {
                        this.panOffset.x = this.panOffset.x + 2;
                        newOffset.x = newOffset.x + 2;
                    }
                }


                //=================================
                // Update Button Relative Positions
                //=================================

                for(Button button: this.levelButtons)
                {
                    //set button relative position
                    button.setWindowRelativePosition(button.getWindowRelativePosition().x - newOffset.x * 2, button.getWindowRelativePosition().y - newOffset.y * 2);

                    //hide button if it is out of the screen
                    if(button.getWindowRelativePosition().x < 0 || button.getWindowRelativePosition().x > this.getWidth()
                    ||button.getWindowRelativePosition().y < 0 || button.getWindowRelativePosition().y > this.getHeight())
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
                gl.glVertex2f(this.getPosition().x , this.getPosition().y ); 

                //bottom right
                gl.glTexCoord2d(textureRight, textureBottom);
                gl.glVertex2f(this.getPosition().x + 1200, this.getPosition().y );  

                //top right
                gl.glTexCoord2d(textureRight, textureTop);
                gl.glVertex2f(this.getPosition().x + 1200, this.getPosition().y + 900);  

                //top left    
                gl.glTexCoord2d(textureLeft, textureTop);
                gl.glVertex2f(this.getPosition().x , this.getPosition().y + 900);         
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
            
            
            
             
        }
     }
    
    @Override
    public void open()
    {

        this.buildLevelButtons();
        for(Button button: this.levelButtons)
        {
            //set button relative position
           button.setWindowRelativePosition(button.getWindowRelativePosition().x - this.panOffset.x * 2, button.getWindowRelativePosition().y - this.panOffset.y * 2);

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
            //level name
            this.removeComponent(levelName);
            this.levelName.getText().setText(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(levelNumber).levelName);
            this.addComponent(levelName);
            
            //main objective description
            this.addComponent(mainObjectiveHeader);
            if(newLevel.mainObjective.complete)
                this.addComponent(mainObjectiveCompleted);
                      
            this.removeComponent(mainObjectiveDescription);
            mainObjectiveDescription = new TextBlock(800, 410, 375, new Text(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(levelNumber).mainObjective.objectiveDescription));
            this.addComponent(mainObjectiveDescription);
            
            //side objective description
            this.addComponent(sideObjectiveHeader);
            if(newLevel.sideObjective.complete)
                this.addComponent(sideObjectiveCompleted);
            this.removeComponent(sideObjectiveDescription);
            sideObjectiveDescription = new TextBlock(800, 200, 375, new Text(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(levelNumber).sideObjective.objectiveDescription));
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
        }
        
    }
    
    private void buildLevelButtons()
    {
        
        //remove old components
        this.levelButtons.clear();
        this.removeAllComponents();
        
        //close button
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
       
       //===============
       // Level Buttons
       //===============
       
       //================== desert 0 ======================
       final Button button0 = new Button(new Image("blank.png"),175,225,50,50);
       
       Overlay correctOverlay0 = new Overlay(new Image("arrow.png"));
       correctOverlay0.getImage().setDimensions(50, 50);
       correctOverlay0.useRelativeSize = false;    
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(0).mainObjective.complete)
       {
           correctOverlay0.getImage().setColor(new Color(Color.green));
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
       
       final Button button = new Button(new Image("blank.png"),175,425,50,50);
       
       Overlay correctOverlay = new Overlay(new Image("arrow.png"));
       correctOverlay.getImage().setDimensions(50, 50);
       correctOverlay.useRelativeSize = false;    
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(1).mainObjective.complete)
       {
           correctOverlay.getImage().setColor(new Color(Color.green));
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
       final Button button1 = new Button("blank.png",375,425,50,50);
       
       correctOverlay = new Overlay(new Image("mapLock.png"));
       
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(2).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           correctOverlay.getImage().setColor(new Color(Color.green));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(1).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button1.getImage().addImageEffect(effect);
       }
       
       correctOverlay.getImage().setDimensions(50, 50);
       correctOverlay.useRelativeSize = false;    
       button1.getImage().addOverlay("img",correctOverlay);
       button1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                    
                     if(!button1.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                     {
                        button1.getImage().getOverlay("img").getImage().setScale(1.2f);
                        repaintLevelDetails(2);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                    
                     if(!button1.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                     {
                        button1.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button1.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                     {
                            ((GameClientScene)owningScene).sendChooseLevelPacket(2);
                     }
                }
            }
       });
       this.levelButtons.add(button1);
       this.addComponent(button1);
       
       //================== desert 3========================
       final Button button2 = new Button("blank.png",575,425,50,50);
       
       correctOverlay = new Overlay(new Image("mapLock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(3).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           correctOverlay.getImage().setColor(new Color(Color.green));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(2).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button2.getImage().addImageEffect(effect);
       }
       
       correctOverlay.getImage().setDimensions(50, 50);
       correctOverlay.useRelativeSize = false;  
       button2.getImage().addOverlay("img",correctOverlay);
       button2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                     if(!button2.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                     {
                        button2.getImage().getOverlay("img").getImage().setScale(1.2f);
                        repaintLevelDetails(3);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button2.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                     {
                        button2.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button2.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                        ((GameClientScene)owningScene).sendChooseLevelPacket(3);
                }
            }
       });
       this.levelButtons.add(button2);
       this.addComponent(button2);
       
       //================== desert 4========================
       final Button button3 = new Button("blank.png",500,700,50,50);
       
       correctOverlay = new Overlay(new Image("mapLock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(4).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           correctOverlay.getImage().setColor(new Color(Color.green));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(2).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button3.getImage().addImageEffect(effect);
       }
       
       correctOverlay.getImage().setDimensions(50, 50);
       correctOverlay.useRelativeSize = false;  
       button3.getImage().addOverlay("img",correctOverlay);
       button3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                    
                     if(!button3.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                     {
                        button3.getImage().getOverlay("img").getImage().setScale(1.2f);
                        repaintLevelDetails(4);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button3.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                     {
                        button3.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button3.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                        ((GameClientScene)owningScene).sendChooseLevelPacket(4);
                }
            }
       });
       this.levelButtons.add(button3);
       this.addComponent(button3);
       
        //================== desert 5========================
       final Button button4 = new Button("blank.png",965,425,50,50);
       
       correctOverlay = new Overlay(new Image("mapLock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(5).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           correctOverlay.getImage().setColor(new Color(Color.green));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(3).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button4.getImage().addImageEffect(effect);
       }
       
       correctOverlay.getImage().setDimensions(50, 50);
       correctOverlay.useRelativeSize = false;  
       button4.getImage().addOverlay("img",correctOverlay);
       button4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                     if(!button4.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                     {
                        button4.getImage().getOverlay("img").getImage().setScale(1.2f);
                        repaintLevelDetails(5);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button4.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                     {
                        button4.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button4.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                        ((GameClientScene)owningScene).sendChooseLevelPacket(5);
                }
            }
       });
       this.levelButtons.add(button4);
       this.addComponent(button4);
       
       
       
       //================== cave 1 ========================
  
        final Button button5 = new Button("blank.png",1400,425,50,50);
        
        correctOverlay = new Overlay(new Image("mapLock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(6).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           correctOverlay.getImage().setColor(new Color(Color.green));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(5).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button5.getImage().addImageEffect(effect);
       }
       
       
       correctOverlay.getImage().setDimensions(50, 50);
       correctOverlay.useRelativeSize = false;  
       button5.getImage().addOverlay("img",correctOverlay);
       button5.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                     if(!button5.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png")) 
                     {
                        button5.getImage().getOverlay("img").getImage().setScale(1.2f);
                        repaintLevelDetails(6);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button5.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                     {
                        button5.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button5.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                        ((GameClientScene)owningScene).sendChooseLevelPacket(6);
                }
            }
       });
       this.levelButtons.add(button5);
       this.addComponent(button5);
       
       
       //================== cave 2========================
  
        final Button button6 = new Button("blank.png",1600,625,50,50);
        
        correctOverlay = new Overlay(new Image("mapLock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(7).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           correctOverlay.getImage().setColor(new Color(Color.green));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(6).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button6.getImage().addImageEffect(effect);
       }
       
       
       correctOverlay.getImage().setDimensions(50, 50);
       correctOverlay.useRelativeSize = false;  
       button6.getImage().addOverlay("img",correctOverlay);
       button6.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                     if(!button6.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png")) 
                     {
                        button6.getImage().getOverlay("img").getImage().setScale(1.2f);
                        repaintLevelDetails(7);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button6.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                     {
                        button6.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button6.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                        ((GameClientScene)owningScene).sendChooseLevelPacket(7);
                }
            }
       });
       this.levelButtons.add(button6);
       this.addComponent(button6);
       
       //================== cave 3========================
  
        final Button button7 = new Button("blank.png",1600,325,50,50);
        
        correctOverlay = new Overlay(new Image("mapLock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(8).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           correctOverlay.getImage().setColor(new Color(Color.green));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(6).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button7.getImage().addImageEffect(effect);
       }
       
       
       correctOverlay.getImage().setDimensions(50, 50);
       correctOverlay.useRelativeSize = false;  
       button7.getImage().addOverlay("img",correctOverlay);
       button7.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                     if(!button7.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png")) 
                     {
                        button7.getImage().getOverlay("img").getImage().setScale(1.2f);
                        repaintLevelDetails(8);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button7.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                     {
                        button7.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button7.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                        ((GameClientScene)owningScene).sendChooseLevelPacket(8);
                }
            }
       });
       this.levelButtons.add(button7);
       this.addComponent(button7);
       
       //================== cave 4========================
  
        final Button button8 = new Button("blank.png",1750,325,50,50);
        
        correctOverlay = new Overlay(new Image("mapLock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(9).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           correctOverlay.getImage().setColor(new Color(Color.green));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(7).mainObjective.complete || ((GameClientScene)owningScene).hostLevelProgression.levelMap.get(8).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button8.getImage().addImageEffect(effect);
       }
       
       
       correctOverlay.getImage().setDimensions(50, 50);
       correctOverlay.useRelativeSize = false;  
       button8.getImage().addOverlay("img",correctOverlay);
       button8.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                     if(!button8.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png")) 
                     {
                        button8.getImage().getOverlay("img").getImage().setScale(1.2f);
                        repaintLevelDetails(8);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button8.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                     {
                        button8.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button8.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                        ((GameClientScene)owningScene).sendChooseLevelPacket(9);
                }
            }
       });
       this.levelButtons.add(button8);
       this.addComponent(button8);
       
       //================== cave 5========================
  
        final Button button9 = new Button("blank.png",1950,325,50,50);
        
        correctOverlay = new Overlay(new Image("mapLock.png"));  
       //if this map is complete
       if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(10).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           correctOverlay.getImage().setColor(new Color(Color.green));
       }
       //if the prereqs are complete
       else if(((GameClientScene)owningScene).hostLevelProgression.levelMap.get(9).mainObjective.complete)
       {
           correctOverlay = new Overlay(new Image("arrow.png"));
           Object points[] = {0,.5,0};
           int durations[] = {90,90};
           MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.YOVERLAYTRANSLATE, points, durations);
           effect.setRepeating(true);
           button9.getImage().addImageEffect(effect);
       }
       
       
       correctOverlay.getImage().setDimensions(50, 50);
       correctOverlay.useRelativeSize = false;  
       button9.getImage().addOverlay("img",correctOverlay);
       button9.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("mouseEntered")) {
                     if(!button9.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png")) 
                     {
                        button9.getImage().getOverlay("img").getImage().setScale(1.2f);
                        repaintLevelDetails(8);
                     }
                }
                if (e.getActionCommand().equals("mouseExited")) {
                     if(!button9.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                     {
                        button9.getImage().getOverlay("img").getImage().setScale(1.0f);
                        repaintLevelDetails(-1);
                     }
                }
                if(e.getActionCommand().equals("clicked"))
                {
                     if(!button9.getImage().getOverlay("img").getImage().getTextureReference().equals("mapLock.png"))
                        ((GameClientScene)owningScene).sendChooseLevelPacket(10);
                }
            }
       });
       this.levelButtons.add(button9);
       this.addComponent(button9);
       
    }
    
}
