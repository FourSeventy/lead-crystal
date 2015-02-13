
package com.silvergobletgames.leadcrystal.cutscenes;

import com.silvergobletgames.leadcrystal.scenes.GameScene;
import com.silvergobletgames.sylver.core.Game;

/**
 *
 * @author Mike
 */
public class CutsceneManager {
    
    //owning scene
    public GameScene owningScene;
    
    //cutscene
    private Cutscene cutscene;
    
    public static enum Cutscenes{
        OpeningCutscene, ClosingCutscene;
    }
    
    //==============
    // Constructor
    //==============
    public CutsceneManager(GameScene scene)
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
        if(owningScene.getPlayer().getLevelProgressionManager().cutsceneCompleteMap.get(Cutscenes.OpeningCutscene) == false)
        {
            owningScene.getPlayer().getLevelProgressionManager().cutsceneCompleteMap.put(Cutscenes.OpeningCutscene, true);
            this.playCutscene(new OpeningCutscene()); 
        }
        
        //closing cutscene
        if(owningScene.getPlayer().getLevelProgressionManager().cutsceneCompleteMap.get(Cutscenes.ClosingCutscene) == false
           && owningScene.getPlayer().getLevelProgressionManager().levelMap.get(17).mainObjective.complete == true
                && owningScene.getActiveLevel() != null && owningScene.getActiveLevel().filename.equals("town.lv"))
        {
            owningScene.getPlayer().getLevelProgressionManager().cutsceneCompleteMap.put(Cutscenes.ClosingCutscene, true);
             this.playCutscene(new ClosingCutscene()); 
        }
    }
    
    public void playCutscene(Cutscene cutscene)
    {
        this.cutscene = cutscene;
        this.cutscene.setOwningScene(this.owningScene);
        this.owningScene.getInputLock().set(true);
    }
    
    public Cutscene getCutscene()
    {
        return this.cutscene;
    }
}
