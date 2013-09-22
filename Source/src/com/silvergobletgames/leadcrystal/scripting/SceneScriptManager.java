
package com.silvergobletgames.leadcrystal.scripting;

import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.EntityEffect.EntityEffectType;
import com.silvergobletgames.leadcrystal.entities.MobSpawner;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.netcode.GobletServer;
import com.silvergobletgames.leadcrystal.netcode.OpenInstructionalTipPacket.InstructionalTip;
import com.silvergobletgames.leadcrystal.netcode.OpenMenuPacket.MenuID;
import com.silvergobletgames.leadcrystal.netcode.SideObjectiveCompletePacket;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.graphics.ImageEffect.ImageEffectType;
import com.silvergobletgames.sylver.graphics.LightEffect;
import com.silvergobletgames.sylver.graphics.LightEffect.LightEffectType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author Mike
 */
public class SceneScriptManager 
{
    
    //owning scene
    private GameServerScene owningScene;
    //world data
    private HashMap<String,Float> worldDataVariables = new HashMap();
    
    //===================
    // Constructor
    //==================
    
    public SceneScriptManager(GameServerScene scene)
    {
        this.owningScene = scene;
    }
    
    //==================
    // Script Functions
    //==================
    
    
    
    public float getWorldData(String key)
    {
        if(worldDataVariables.get(key) == null)
            worldDataVariables.put(key,0f);
        
        return worldDataVariables.get(key);
    }
    
    public void setWorldData(String key, float value)
    {
        this.worldDataVariables.put(key,value);
    }
    
    /**
     * Adds an image effect to the given entity
     * @param entityID entityID to add the image effect to
     * @param effectType ImageEffectType to add
     * @param duration duration for the effect in ticks
     * @param start start value of the effect
     * @param end end value of the effect
     */
    public void addImageEffect(String entityID, String effectType, int duration, float start, float end)
    {       
        //create the image effect
        ImageEffectType type = ImageEffectType.valueOf(effectType);
        ImageEffect effect = new ImageEffect(type,duration,start,end);
        
        //get the entity and apply the image effect
        SceneObject target =this.owningScene.getSceneObjectManager().get(entityID);
        
        if(target instanceof Entity)
          ((Entity)target).getImage().addImageEffect(effect);
        else if(target instanceof Image)
          ((Image)target).addImageEffect(effect);
        
    }
    
    /**
     * Adds a light effect to the given entity
     * @param entityID entityID to add the light effect to
     * @param effectType LightEffectType to add
     * @param duration duration for the effect to last in ticks
     * @param start start value of the effect
     * @param end end value of the effect
     */
    public void addLightEffect(String entityID, String effectType, int duration, float start, float end)
    {
        //create the image effect
        LightEffectType type = LightEffectType.valueOf(effectType);
        LightEffect effect = new LightEffect(type,duration,start,end);
        
        //get the entity and apply the image effect
        ((Entity)this.owningScene.getSceneObjectManager().get(entityID)).getLight().addLightEffect(effect);
    }
    
    /**
     *  Adds an entity effect to the given entity
     * @param entityID entityID to give effect to
     * @param effectType EntityEffectType for the effect
     * @param duration duration of the efect
     * @param start start value
     * @param end end value
     */
    public void addEntityEffect(String entityID, String effectType, int duration, float start, float end)
    {
        //create the image effect
        EntityEffectType type = EntityEffectType.valueOf(effectType);
        EntityEffect effect = new EntityEffect(type,duration,start,end);
       
        
      
        
        //get the entity and apply the image effect
        ((Entity)this.owningScene.getSceneObjectManager().get(entityID)).addEntityEffect(effect);
    }
    
    public void addTextEffect()
    {
        
    }
    
    public void addCombatEffect()
    {
        
    }
    
    /**
     * Opens a menu for the given client
     * @param clientID clientID of the target client
     * @param menuID menuID to open
     */
    public void openClientMenu(String clientID, String menuID)
    {
        MenuID id = MenuID.valueOf(menuID);
        this.owningScene.sendOpenMenu(clientID,id); 
    }
    
    /**
     * Closes a menu for the given client
     * @param clientID clientID of the target client
     * @param menuID menuID to close
     */
    public void closeClientMenu(String clientID, String menuID)
    {
        MenuID id = MenuID.valueOf(menuID);
        this.owningScene.sendCloseMenu(clientID,id); 
    }
    
    public void openClientTip(String clientID, String tipID)
    {
        InstructionalTip tip = InstructionalTip.valueOf(tipID);
        this.owningScene.sendOpenInstructionalTipPacket(UUID.fromString(clientID),tip);
    }
    
    public void closeClientTip(String clientID, String tipID)
    {
        InstructionalTip tip = InstructionalTip.valueOf(tipID);
        this.owningScene.sendCloseInstructionalTipPacket(UUID.fromString(clientID),tip);
    }
    
    /**
     * Tells a particular client to show the following dialogue
     * @param clientID clientID of the target client
     * @param speaker speaker of the dialogue
     * @param text text for the dialogue
     */
    public void openClientDialogue(String clientID, String speaker, String text)
    {
        //send the packet
        this.owningScene.sendOpenDialogue(clientID, speaker, text);
    }
    
    public void openAllClientDialogue(String speaker, String text)
    {
        //for each player in the scene
        ArrayList<PlayerEntity> playerList = new ArrayList(((GameServerScene)this.owningScene).players);
        for(PlayerEntity player :playerList)
        { 
            this.openClientDialogue(player.getID(), speaker, text);
        }
    }
    
    /**
     * Completes the given side objective for every client in the scene
     * @param levelNumber level number
     * @param objectiveNumber objective number
     */
    public void completeSideObjective(int levelNumber)
    {
        
        ArrayList<PlayerEntity> playerList = new ArrayList(((GameServerScene)this.owningScene).players);
        for(PlayerEntity player :playerList)
        {   
            //figure out if it is newly completed
            boolean sideObjectiveNewlyCompleted = false;
            if(player.getLevelProgressionManager().levelMap.get(levelNumber).sideObjective.complete == false)
                sideObjectiveNewlyCompleted = true;

            //complete side objective
            player.getLevelProgressionManager().completeSideObjective(levelNumber);
            
            //send side objective complete packet
            short currencyReward =0;
            if(sideObjectiveNewlyCompleted)
            {
                currencyReward = (short)player.getLevelProgressionManager().levelMap.get(levelNumber).sideObjective.currencyAward;
            }
            owningScene.sendSideObjectiveCompletePacket(player.getID(), currencyReward);
            
           
        
        }
        
        this.setSideQuestStatusText("complete");
    }
    
    public void setSideQuestStatusText( String text)
    {
        //send the packet
        this.owningScene.sendSetSideQuestStatus(text);
    }
    
    /**
     * complete the main objective for every player in the scene. Also moves all players back to town
     * @param levelNumber level to complete
     */
    public void completeMainObjective(int levelNumber)
    {
        //for each player in the scene
        ArrayList<PlayerEntity> playerList = new ArrayList(((GameServerScene)this.owningScene).players);
        for(PlayerEntity player :playerList)
        {                       
            //flags for completion packet
            boolean mainObjectiveNewleyCompleted = false;
            
            //completes the main objective
            if(player.getLevelProgressionManager().levelMap.get(levelNumber).mainObjective.complete == false)
                mainObjectiveNewleyCompleted = true;
            
            //calculate currency reward 
            short currencyAward = 0;
            if(mainObjectiveNewleyCompleted)
            {
                currencyAward = (short)player.getLevelProgressionManager().levelMap.get(levelNumber).mainObjective.currencyAward;
            }
            
            //send main objective complete packet
            owningScene.sendMainObjectiveCompletePacket(player.getID(), currencyAward); 
            
            //complete main objective
            player.getLevelProgressionManager().completeMainObjective(levelNumber);          
            
            //heals players
            player.getCombatData().fullHeal();    
         
        }
       
    }
    
    public void completeLevel()
    {
        //for each player in the scene
        ArrayList<PlayerEntity> playerList = new ArrayList(((GameServerScene)this.owningScene).players);
        for(PlayerEntity player :playerList)
        {  
            //moves players back to town
            ((GobletServer)Game.getInstance().getRunnable("Goblet Server")).queueMovePlayerToLevel(player.getID(), "town.lv", "checkpoint1");          
        }  
    }
    
    /**
     * Moves given player to given level and entity
     * @param clientID clientID to move
     * @param levelName levelName to move to
     * @param toEntityID entity to spawn at
     */
    public void movePlayer(String clientID, String levelName, String toEntityID)
    {
           ((GobletServer)Game.getInstance().getRunnable("Goblet Server")).queueMovePlayerToLevel(clientID, levelName, toEntityID);      
    }
    
    public void enablePVP(String clientID)
    {
        ((GameServerScene)this.owningScene).clientsInScene.get(UUID.fromString(clientID)).player.getBody().setOverlapMask(Entity.OverlapMasks.PVP_PLAYER.value);
    }
    
    public void disablePVP(String clientID)
    {
        ((GameServerScene)this.owningScene).clientsInScene.get(UUID.fromString(clientID)).player.getBody().setOverlapMask(Entity.OverlapMasks.PLAYER.value);
    }
    
    public void respawnPlayer(String clientID)
    {
        this.owningScene.clientsInScene.get(UUID.fromString(clientID)).player.respawn();
    }
    
    /**
     * Adds a sound into the scene
     * @param soundRef sound to add
     */
    public void playSound(String soundRef)
    {
        Sound soundData = Sound.ambientSound(soundRef, false);
        this.owningScene.add(soundData);
    }
    
    public void changeBGM(String soundRef)
    {
        Sound sound =Sound.changeBGM(soundRef, 500, 500);   
        this.owningScene.add(sound);
        sound = Sound.adjustSourceVolume("BGM", .3f);
        this.owningScene.add(sound);
    }
    
    /**
     * Tells given clientID to save
     * @param clientID 
     */
    public void tellClientToSave(String clientID)
    {
        this.owningScene.sendSaveGamePacket(UUID.fromString(clientID));
    }
    
    /**
     * Begins the mob spawning process for the given MobSpawner id
     * @param entityID MobSpawner id
     */
    public void spawnMobs(String entityID)
    {
        //Get the spawner
        MobSpawner spawnerEntity = (MobSpawner)this.owningScene.getSceneObjectManager().get(entityID);
 
        //Begin
        spawnerEntity.beginSpawning();
    }
    
    /**
     * Rearmes the spawner for the given MobSpawner id
     * @param entityID 
     */
    public void rearmSpawner(String entityID)
    {
        //Get the spawner
        MobSpawner spawnerEntity = (MobSpawner)this.owningScene.getSceneObjectManager().get(entityID);
 
        //Begin
        spawnerEntity.setArmed(true);
    }
    
    public SceneObject getSceneObject(String id)
    {
        return this.owningScene.getSceneObjectManager().get(id);
    }
    
    /**
     * Triggers the script of a given Entity
     * @param entityID 
     */
    public void triggerScript(String entityID)
    {
        //Get the script object
        ScriptObject scriptObject = ((Entity)this.owningScene.getSceneObjectManager().get(entityID)).getScriptObject();
 
        //trigger the script
        if(scriptObject != null)
            scriptObject.runScript(null); 
    }
    
    
    
}
