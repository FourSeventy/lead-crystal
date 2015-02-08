
package com.silvergobletgames.leadcrystal.cutscenes;

import com.silvergobletgames.leadcrystal.scenes.CreditsScene;
import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.leadcrystal.scenes.LoadingScene;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.InputSnapshot;
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
        
        Image playerImage = new Image("blank.png");
        playerImage.setDimensions(1600, 900);
        playerImage.setPosition(0, 0);
        this.objects.add(playerImage);    
        
        Label l = new Label("Closing Cutscene", 400 , 400);
        this.objects.add(l);
        
        
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
        ((GameClientScene)owningScene).disconnectFromServer();
        ((GameClientScene)owningScene).saveGameToDisk();
        Game.getInstance().unloadScene(GameClientScene.class);
    }

    
}
