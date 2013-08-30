package com.silvergobletgames.leadcrystal.cutscenes;

import com.jogamp.newt.event.KeyEvent;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.sylver.core.InputSnapshot;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Viewport;
import java.util.ArrayList;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.gl2.GLUgl2;

/**
 *
 * @author Mike
 */
public abstract class Cutscene 
{
    protected ArrayList<SceneObject> objects = new ArrayList();
    protected GameClientScene owningScene;
    
    public boolean done = false;
    
    //variables for scene fading
    private Image blackImage;
    private boolean fadingToBlack = false;
    private boolean fadingFromBlack = true;
    
    //timing counter
    protected long counter = 0;
    //end time
    private long endTime = 0;
    
    //=============
    // Constructor
    //=============
    
    public Cutscene(long endtime)
    {
        //end time
        this.endTime = endtime;
        
        //set fade to black image
        blackImage = new Image("black.png");       
        blackImage.setColor(new Color(1,1,1,1));
        blackImage.setDimensions(1600, 900); //largest aspect ratio that the game supports
    }
    
    
    //===============
    // Class Methods
    //===============
    
    public void update(InputSnapshot input)
    {      
        //update objects
        for(SceneObject so: objects)
            so.update();
        
        if(this.fadingToBlack)
        {
            blackImage.setColor(new Color(1,1,1,blackImage.getColor().a + .015f));
            if(blackImage.getColor().a >= 1)
            {
                blackImage.setColor(new Color(1,1,1,1));
                this.fadingToBlack = false;
            }
        }
        
        if(this.fadingFromBlack)
        {
            blackImage.setColor(new Color(1,1,1,blackImage.getColor().a - .015f));
            if(blackImage.getColor().a <= 0)
            {
                blackImage.setColor(new Color(1,1,1,0));
                this.fadingFromBlack = false;
            }
        }
        
        //lock input
        this.owningScene.lockInput.set(true);
        
        //handle input
        if(input.isKeyReleased(KeyEvent.VK_ENTER) || input.isKeyReleased(KeyEvent.VK_ESCAPE) || input.isKeyReleased(KeyEvent.VK_SPACE))
        {
            this.counter = this.endTime -1 ;
        }
        
        //time
        this.counter++;
        
        //end time
        if(this.counter == this.endTime)
            this.fadeToBlack();
        if(this.counter == this.endTime + 150)
            this.endCutscene();
    }
    
    public void draw(GL2 gl, Viewport viewport)
    {
        //clear the framebuffer
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        
        //set up projection matrix
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        GLUgl2 glu = new GLUgl2();
        glu.gluOrtho2D(0.0, viewport.getWidth(), 0.0, viewport.getHeight());
        
        for(SceneObject so: objects)
            so.draw(gl);
        
        this.blackImage.draw(gl);
    }
    
    public final void setOwningScene(GameClientScene scene)
    {
        this.owningScene = scene;
    }
    
    protected void endCutscene()
    {
        this.done = true;
        owningScene.getSceneEffectsManager().fadeFromBlack(null);
        this.owningScene.lockInput.set(false);
    }
    
    public final void fadeFromBlack()
    {
        blackImage.setColor(new Color(1,1,1,1));
        this.fadingFromBlack = true;
    }
    
    public final void fadeToBlack()
    {
        blackImage.setColor(new Color(1,1,1,0));
        this.fadingToBlack = true;
    }
    
    
}
