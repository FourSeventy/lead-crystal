
package com.silvergobletgames.leadcrystal.scenes;

import com.silvergobletgames.sylver.audio.AudioRenderer;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.*;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import com.silvergobletgames.sylver.windowsystem.Label;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 *
 * @author Mike
 */
public class LoadingScene extends Scene
{
    
    //loading text
    private Text loading;
    private long updateTicks;
    
    private Text currentProgress;
    
    private AtomicBoolean doneLoading = new AtomicBoolean(false);
    
    //==============
    // Constructor
    //==============
    
    public LoadingScene()
    {
        //center and right layout coordinates
        int right = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x;
        int center = right/2;
        
        //loading text
        loading = new Text("Loading",CoreTextType.MENU);
        Label leadCrystalButton = new Label(loading, center - loading.getWidth()/2, 600);
        this.add(leadCrystalButton,Layer.MAIN);
        
        //progress text
        currentProgress = new Text("",CoreTextType.DEFAULT);
        Label progressLabel = new Label(currentProgress, center - loading.getWidth()/2 + 5, 555);
        this.add(progressLabel,Layer.MAIN);
    }
    
    //===============
    // Scene Methods
    //===============
    
    public void update()
    {
        super.update();
        
        this.updateTicks++;
        
        if(this.updateTicks % 30 ==0)
        {
            switch(loading.toString().length())
            {
                case 7: loading.setText("Loading."); break;
                case 8: loading.setText("Loading.."); break;
                case 9: loading.setText("Loading..."); break;
                case 10: loading.setText("Loading"); break;
            }
        }
        
        
    }
    
    @Override
    public synchronized void render(GL2 gl)
    {
        super.render(gl);
    }
    
    public synchronized void setCurrentProgressText(String s)
    {
        currentProgress.setText(s);
    }
    
    public void setFinishedLoading(boolean done)
    {
        this.doneLoading.set(done);
    }
    
    public void handleInput() 
    {

    }  
    
}
