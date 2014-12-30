
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
        //load texture we need
        URI textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("ui/silver-goblet.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "silver-goblet.png");
        URI textureURI2 = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("ui/silver-goblet-text.png"); 
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI2, "silver-goblet-text.png");
                     
        
        //center and right layout coordinates
        int right = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x;
        int center = right/2;
        
        
       
        
        
      Text leadCrystal = new Text("Michael Signorella -- Programming / Design",LeadCrystalTextType.MENU40);
        leadCrystal.setPosition(center - leadCrystal.getWidth()/2, 700);       
        this.add(leadCrystal,Layer.MAIN);
        
        leadCrystal = new Text("George Lubinski  -- Art",LeadCrystalTextType.MENU40);
        leadCrystal.setPosition(center - leadCrystal.getWidth()/2, 500);       
        this.add(leadCrystal,Layer.MAIN);
        
        leadCrystal = new Text("Justin Capalbo  -- QA",LeadCrystalTextType.MENU40);
        leadCrystal.setPosition(center - leadCrystal.getWidth()/2, 300);       
        this.add(leadCrystal,Layer.MAIN);
        
        
       
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
