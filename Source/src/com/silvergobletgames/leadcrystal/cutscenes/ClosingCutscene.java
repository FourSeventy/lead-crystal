
package com.silvergobletgames.leadcrystal.cutscenes;

import com.silvergobletgames.leadcrystal.scenes.CreditsScene;
import com.silvergobletgames.leadcrystal.scenes.GameScene;
import com.silvergobletgames.leadcrystal.scenes.LoadingScene;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.InputSnapshot;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.windowsystem.Label;

/**
 *
 * @author Mike
 */
public class ClosingCutscene extends Cutscene{
    
    
    
    public ClosingCutscene()
    {
        super( 60 * 5);
        
         float center = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x/2;
         float height = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().y/2;
         //center -500 is max left
        
        Image frame1 = new Image("finalscene.jpg");
        frame1.setAnchor(Anchorable.Anchor.CENTER);
        frame1.setPositionAnchored(center, height);
        frame1.setDimensions(1600, 900);
        frame1.setAlphaBrightness(1);
        this.objects.add(frame1); 
        
        
    }
    
    public void update(InputSnapshot input)
    {
        super.update(input);
        
        
    }
    

    @Override
    protected void endCutscene()
    {
        Game.getInstance().loadScene(new CreditsScene());
        Game.getInstance().changeScene(CreditsScene.class, null);
       ((GameScene)owningScene).saveGameToDisk();
    }

    
}
