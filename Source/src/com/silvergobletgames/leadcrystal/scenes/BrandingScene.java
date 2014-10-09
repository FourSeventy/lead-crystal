
package com.silvergobletgames.leadcrystal.scenes;

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
public class BrandingScene extends Scene{
        
    
    private long timer = 0;
    
   
    //==============
    // Constructor
    //==============
    
    public BrandingScene()
    {
        //load texture we need
        URI textureURI = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("ui/silver-goblet.png");  
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI, "silver-goblet.png");
        URI textureURI2 = Game.getInstance().getConfiguration().getTextureRootFolder().resolve("ui/silver-goblet-text.png"); 
        Game.getInstance().getAssetManager().getTextureLoader().loadTexture(textureURI2, "silver-goblet-text.png");
                     
        
        //center and right layout coordinates
        int right = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x;
        int center = right/2;
        
        
        //silver goblet image
        Image img = new Image("silver-goblet.png");
        img.setPosition(center - img.getWidth()/2, 180); 
        img.setColor(new Color(1f,1f,1f,0f));
             
        int[] durations = {120, 150, 60};
        Object[] points = {new Color(1f,1f,1f,0f),new Color(1f,1f,1f,1f), new Color(1f,1f,1f,1f),new Color(1f,1f,1f,0f)};       
        MultiImageEffect effect = new MultiImageEffect(ImageEffect.ImageEffectType.COLOR, points, durations);
        img.addImageEffect(effect);
        
        this.add(img,Layer.MAIN);
        
        
        
        //branding text
        Image img2 = new Image("silver-goblet-text.png");
        img2.setScale(.9f);
        img2.setPosition(center - (img2.getWidth() * .9f)/2, 75); 
        img2.setColor(new Color(1f,1f,1f,0f));
             
        int[] durations2 = {120, 150, 60};
        Object[] points2 = {new Color(1f,1f,1f,0f),new Color(1f,1f,1f,1f), new Color(1f,1f,1f,1f),new Color(1f,1f,1f,0f)};       
        MultiImageEffect effect2 = new MultiImageEffect(ImageEffect.ImageEffectType.COLOR, points2, durations2);
        img2.addImageEffect(effect2);
        
        this.add(img2,Layer.MAIN);
        
        
       
    }
    
    //===============
    // Scene Methods
    //===============
    
    public void update()
    {
        super.update();
        
        this.timer++;
        
        if(timer == 20){
            Sound sound = Sound.ambientSound("streaming/silvergoblet_intro.ogg", true);
            this.add(sound); 
        }
        
        
        if(this.timer > 350) // 5 ish seconds
        {
            //switch to loading scene
            Game.getInstance().changeScene(LoadingScene.class, null);
            Game.getInstance().unloadScene(BrandingScene.class);
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
