
package com.silvergobletgames.leadcrystal.scenes;

import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.sylver.audio.AudioRenderer;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.*;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.graphics.MultiImageEffect;
import com.silvergobletgames.sylver.graphics.MultiTextEffect;
import com.silvergobletgames.sylver.graphics.RenderingPipelineGL2;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import com.silvergobletgames.sylver.graphics.TextEffect;
import com.silvergobletgames.sylver.graphics.TextEffect.TextEffectType;
import com.silvergobletgames.sylver.windowsystem.Label;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 *
 * @author Mike
 */
public class CreditsScene extends Scene{
        
    
    private long timer = 0;
    
   
    //==============
    // Constructor
    //==============
    
    public CreditsScene()
    {
        //center and right layout coordinates
        int right = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x;
        int center = right/2;
        
        Image img = new Image("silver-goblet.png");
        img.setScale(.47f);
        img.setPosition(center - img.getWidth()/2 - 150, 550); 
        this.add(img, Layer.MAIN); 
       
        Text text = new Text("Credits",LeadCrystalTextType.MENU60);
        text.setPosition(center - text.getWidth()/2, 620);       
        this.add(text,Layer.MAIN);
        
        img = new Image("darknessSquare.png");
        img.setDimensions(750, 8);
        img.setColor(new Color(Color.white));
        img.setPosition(center - img.getWidth()/2 + 170, 600);
        this.add(img, Layer.MAIN);
        
        text = new Text("Programming",LeadCrystalTextType.MENU40);
        text.setColor(new Color(1,1,1,.75f));
        text.setPosition(center - text.getWidth() - 25, 450);
        this.add(text,Layer.MAIN);
        text = new Text("Michael Signorella",LeadCrystalTextType.MENU40);
        text.setPosition(center , 450);
        this.add(text,Layer.MAIN);
        
        text = new Text("Art",LeadCrystalTextType.MENU40);
        text.setPosition(center - text.getWidth() - 25, 370);
        text.setColor(new Color(1,1,1,.75f));
        this.add(text,Layer.MAIN);
        text = new Text("George Lubinski",LeadCrystalTextType.MENU40);
        text.setPosition(center, 370);
        this.add(text,Layer.MAIN);
        
        text = new Text("Testing",LeadCrystalTextType.MENU40);
        text.setPosition(center - text.getWidth() - 25, 290);  
        text.setColor(new Color(1,1,1,.75f));
        this.add(text,Layer.MAIN);
        text = new Text("Justin Capalbo",LeadCrystalTextType.MENU40);
        text.setPosition(center , 290);       
        this.add(text,Layer.MAIN);
        
        
       
    }
    
    //===============
    // Scene Methods
    //===============
    
    public void update()
    {
        super.update();
        
        this.timer++;
        
        
        if( timer > (60 *  10))
        {
            Game.getInstance().loadScene(new MainMenuScene());
            Game.getInstance().changeScene(MainMenuScene.class,new ArrayList(){{add(true);}});
            Game.getInstance().unloadScene(CreditsScene.class);
        }
        
       
        
       
 
    }

    
    public void handleInput() 
    {

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
    
}
