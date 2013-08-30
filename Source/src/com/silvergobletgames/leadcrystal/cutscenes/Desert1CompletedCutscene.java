
package com.silvergobletgames.leadcrystal.cutscenes;

import com.silvergobletgames.sylver.core.InputSnapshot;
import com.silvergobletgames.sylver.graphics.Image;

/**
 *
 * @author Mike
 */
public class Desert1CompletedCutscene extends Cutscene{
    
    
    
    public Desert1CompletedCutscene()
    {
        super( 300);
        
        Image playerImage = new Image("bastionCut2.png");
        playerImage.setDimensions(1600, 900);
        playerImage.setPosition(0, 0);
        this.objects.add(playerImage);      
        
        
    }
    
    public void update(InputSnapshot input)
    {
        super.update(input);
        
        
    }
    
}
