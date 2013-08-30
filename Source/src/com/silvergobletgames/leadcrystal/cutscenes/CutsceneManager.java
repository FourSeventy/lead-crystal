
package com.silvergobletgames.leadcrystal.cutscenes;

import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.sylver.core.Game;

/**
 *
 * @author Mike
 */
public class CutsceneManager {
    
    //owning scene
    public GameClientScene owningScene;
    
    //cutscene
    private Cutscene cutscene;
    
    public static enum Cutscenes{
        OpeningCutscene, Desert1Completed, Desert0Completed;
    }
    
    //==============
    // Constructor
    //==============
    public CutsceneManager(GameClientScene scene)
    {
        this.owningScene = scene;
    }
    
    
    //=====================
    // Class Methods
    //===================
    
    public void update()
    {
        //update cutscene
        if(this.cutscene != null)
        {
            this.cutscene.update(Game.getInstance().getInputHandler().getInputSnapshot());
            
            if(this.cutscene.done == true)
                this.cutscene = null;
        }
        
        
        //===========================
        //Test for Running Cutscenes
        //===========================
        
        //opening cutscene
        if(owningScene.player.getLevelProgressionManager().cutsceneCompleteMap.get(Cutscenes.OpeningCutscene) == false)
        {
            owningScene.player.getLevelProgressionManager().cutsceneCompleteMap.put(Cutscenes.OpeningCutscene, true);
            this.playCutscene(new OpeningCutscene()); 
        }
        //level 0 completion cutscene
        if(owningScene.player.getLevelProgressionManager().cutsceneCompleteMap.get(Cutscenes.Desert0Completed) == false
           && owningScene.player.getLevelProgressionManager().levelMap.get(0).mainObjective.complete == true)
        {
            owningScene.player.getLevelProgressionManager().cutsceneCompleteMap.put(Cutscenes.Desert0Completed, true);
             this.playCutscene(new Desert0CompletedCutscene()); 
        }
        //level 1 completion cutscene
        if(owningScene.player.getLevelProgressionManager().cutsceneCompleteMap.get(Cutscenes.Desert1Completed) == false
           && owningScene.player.getLevelProgressionManager().levelMap.get(1).mainObjective.complete == true)
        {
            owningScene.player.getLevelProgressionManager().cutsceneCompleteMap.put(Cutscenes.Desert1Completed, true);
             this.playCutscene(new Desert1CompletedCutscene()); 
        }
    }
    
    public void playCutscene(Cutscene cutscene)
    {
        this.cutscene = cutscene;
        this.cutscene.setOwningScene(this.owningScene);
        this.owningScene.lockInput.set(true);
    }
    
    public Cutscene getCutscene()
    {
        return this.cutscene;
    }
}
