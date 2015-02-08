
package com.silvergobletgames.leadcrystal.scenes;

import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.sylver.audio.AudioRenderer;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.*;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
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
        
        Random r = new Random();
        
        //build tip
        switch(r.nextInt(6))
        {
            case 0:

                Text tt = new Text("Tip: Pressing T will activate your flashlight.",LeadCrystalTextType.HUD34);
                tt.setPosition( center - tt.getWidth()/2, 147);
                this.add(tt,Layer.MAIN);             
             break;
                
            case 1:
                tt = new Text("Tip: By pressing     you can interact with objects and people.",LeadCrystalTextType.HUD34);
                tt.setPosition( center - tt.getWidth()/2, 147);
                this.add(tt,Layer.MAIN);
                Button b = new Button(new Image("leftClickIcon.png"){{setHorizontalFlip(true);}}, center - 200, 135, 27 , 45);
                b.dontKillClick = true;
                this.add(b,Layer.MAIN);              
             break;
                
            case 2: 
                tt = new Text("Tip: The       icon indicates an enemy is stunned.",LeadCrystalTextType.HUD34);
                tt.setPosition( center - tt.getWidth()/2, 147);
                this.add(tt,Layer.MAIN);
                b = new Button(new Image("stunspiral.png"){{setHorizontalFlip(true);}}, center - 210, 135, 35 , 35);
                b.dontKillClick = true;
                this.add(b,Layer.MAIN);  
             break;
            case 3: 
                tt = new Text("Tip: The       icon indicates an enemy is slowed.",LeadCrystalTextType.HUD34);
                tt.setPosition( center - tt.getWidth()/2, 147);
                this.add(tt,Layer.MAIN);
                b = new Button(new Image("slow.png"){{setHorizontalFlip(true);}}, center - 210, 135, 35 , 35);
                b.dontKillClick = true;
                this.add(b,Layer.MAIN); 
              break;
            case 4: 
                tt = new Text("Tip: The       icon indicates an enemy is vulnerable.", LeadCrystalTextType.HUD34);
                tt.setPosition( center - tt.getWidth()/2, 147);
                this.add(tt,Layer.MAIN);
                b = new Button(new Image("teleport2.png"){{setHorizontalFlip(true);}}, center - 225, 135, 35 , 35);
                b.dontKillClick = true;
                this.add(b,Layer.MAIN); 
            break;
                
            case 5:
                tt = new Text("Tip: Pressing F will use a potion.", LeadCrystalTextType.HUD34);
                tt.setPosition( center - tt.getWidth()/2, 147);
                this.add(tt,Layer.MAIN);             
             break;
            case 6:
                tt = new Text("Tip: Resurrecting a player in co-op requires a potion.", LeadCrystalTextType.HUD34);
                tt.setPosition( center - tt.getWidth()/2, 147);
                this.add(tt,Layer.MAIN);             
            break;
        }
        
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
