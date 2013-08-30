
package com.silvergobletgames.leadcrystal.cutscenes;

import com.silvergobletgames.sylver.core.InputSnapshot;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.windowsystem.Label;

/**
 *
 * @author Mike
 */
public class Desert0CompletedCutscene extends Cutscene{
    
    
    
    public Desert0CompletedCutscene()
    {
        super( 300);
        
        Image playerImage = new Image("bastionCut2.png");
        playerImage.setDimensions(1600, 900);
        playerImage.setPosition(0, 0);
        this.objects.add(playerImage);    
        
        Label l = new Label("Desert 0 Completed", 400 , 400);
        this.objects.add(l);
        
        
    }
    
    public void update(InputSnapshot input)
    {
        super.update(input);
        
        
    }
    
}
