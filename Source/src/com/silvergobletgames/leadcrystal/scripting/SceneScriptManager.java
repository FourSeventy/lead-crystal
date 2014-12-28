
package com.silvergobletgames.leadcrystal.scripting;

import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.Damage.DamageType;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.TeleporterEmitter;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.EntityEffect.EntityEffectType;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.MobSpawner;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorStat.ArmorStatID;
import com.silvergobletgames.leadcrystal.items.LootSpewer;
import com.silvergobletgames.leadcrystal.netcode.GobletServer;
import com.silvergobletgames.leadcrystal.netcode.OpenInstructionalTipPacket.InstructionalTip;
import com.silvergobletgames.leadcrystal.netcode.OpenMenuPacket.MenuID;
import com.silvergobletgames.leadcrystal.netcode.SideObjectiveCompletePacket;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.graphics.ImageEffect.ImageEffectType;
import com.silvergobletgames.sylver.graphics.LightEffect;
import com.silvergobletgames.sylver.graphics.LightEffect.LightEffectType;
import com.silvergobletgames.sylver.graphics.Overlay;
import com.silvergobletgames.sylver.util.SylverRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Circle;

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
    
    
    /**
     * Returns persisted world data that was saved with setWorldData().
     * @param key Key that the data was saved under.
     * @return Returns the saved world data of type float.
     */
    public float getWorldData(String key)
    {
        if(worldDataVariables.get(key) == null)
            worldDataVariables.put(key,0f);
        
        return worldDataVariables.get(key);
    }
    
    /**
     * Persists data as a key/value pair. This data only persists for the current level.
     * @param key Key to save the data under
     * @param value Value of the data, must be a float
     */
    public void setWorldData(String key, float value)
    {
        this.worldDataVariables.put(key,value);
    }
    
    /**
     * Adds an image effect to the given entity
     * @param entityID entityID to add the image effect to
     * @param effectType ImageEffectType to add (BRIGHTNESS, ALPHABRIGHTNESS, COLOR, DURATION, ROTATION, XTRANSLATE, YTRANSLATE, SCALE, WIDTH, HEIGHT, XOVERLAYTRANSLATE, YOVERLAYTRANSLATE)
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
     * @param effectType LightEffectType to add (SIZE, COLOR, DIRECTION, INTENSITY, RADIUS, DURATION)
     * @param duration duration for the effect to last in ticks
     * @param start start value of the effect
     * @param end end value of the effect
     */
    public void addLightEffect(String entityID, String effectType, int duration, float start, float end, boolean repeating)
    {
        //create the image effect
        LightEffectType type = LightEffectType.valueOf(effectType);
        LightEffect effect = new LightEffect(type,duration,start,end);
        effect.setRepeating(repeating);
        
        //get the entity and apply the image effect
        ((Entity)this.owningScene.getSceneObjectManager().get(entityID)).getLight().addLightEffect(effect);
    }
    
    /**
     *  Adds an entity effect to the given entity
     * @param entityID entityID to give effect to( MASS,DURATION,ROTATION,XTRANSLATE,YTRANSLATE,VFORCE,HFORCE,REMOVEBODY)
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
    
    /**
     * NOOP
     */
    public void addTextEffect()
    {
        
    }
    
    /**
     * NOOP
     */
    public void addCombatEffect()
    {
        
    }
    
    /**
     * Opens a menu for the given client
     * @param clientID clientID of the target client (get with invoker.getID())
     * @param menuID menuID to open (POTION,ARMOR,SKILL,MAP)
     */
    public void openClientMenu(String clientID, String menuID)
    {
        MenuID id = MenuID.valueOf(menuID);
        this.owningScene.sendOpenMenu(clientID,id); 
    }
    
    /**
     * Closes a menu for the given client
     * @param clientID clientID of the target client (get with invoker.getID())
     * @param menuID menuID to close (POTION,ARMOR,SKILL,MAP)
     */
    public void closeClientMenu(String clientID, String menuID)
    {
        MenuID id = MenuID.valueOf(menuID);
        this.owningScene.sendCloseMenu(clientID,id); 
    }
    
    /**
     * Opens helper tooltip for given client
     * @param clientID clientID of the target client (get with invoker.getID())
     * @param tipID InstructionalTip type to open (Jump,PrimarySkill,SecondarySkill,Sprint,UsePotion,RightClick,Ladder,Jumpthrough,OpenMap)
     */
    public void openClientTip(String clientID, String tipID)
    {
        InstructionalTip tip = InstructionalTip.valueOf(tipID);
        this.owningScene.sendOpenInstructionalTipPacket(UUID.fromString(clientID),tip);
    }
    
    /**
     * closes helper tooltip for given client
     * @param clientID clientID of the target client (get with invoker.getID())
     * @param tipID InstructionalTip type to close (Jump,PrimarySkill,SecondarySkill,Sprint,UsePotion,RightClick,Ladder,Jumpthrough,OpenMap)
     */
    public void closeClientTip(String clientID, String tipID)
    {
        InstructionalTip tip = InstructionalTip.valueOf(tipID);
        this.owningScene.sendCloseInstructionalTipPacket(UUID.fromString(clientID),tip);
    }
    
    /**
     * Tells a particular client to show the following dialogue
     * @param clientID clientID of the target client  (get with invoker.getID())
     * @param speaker speaker of the dialogue (Slash, Brice, drTam, oldMan)
     * @param text text for the dialogue
     */
    public void openClientDialogue(String clientID, String speaker, String text)
    {
        //send the packet
        this.owningScene.sendOpenDialogue(clientID, speaker, text);
    }
    
    /**
     * Opens a dialoge for each player in the scene.
     * @param speaker speaker of the dialogue (Slash, Brice, drTam, oldMan)
     * @param text text for the dialogue
     */
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
     * Completes the side objective for every client in the scene
     * @param levelNumber level number
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
            ArmorStatID id = null;
            if(sideObjectiveNewlyCompleted)
            {
                currencyReward = (short)player.getLevelProgressionManager().levelMap.get(levelNumber).sideObjective.currencyAward;           
                id = player.getLevelProgressionManager().levelMap.get(levelNumber).sideObjective.statReward;
                
            }
            
            owningScene.sendSideObjectiveCompletePacket(player.getID(), currencyReward,id);
            
            
            //play sound
            Sound sound = Sound.ambientSound("buffered/complete.ogg",true);  
            this.owningScene.add(sound);
           
        
        }
        
        this.setSideQuestStatusText("complete");
    }
    
    /**
     * Sets the side objective status text. This will flash on the screen a well as go in to the quest menu.
     * @param text Text to show
     */
    public void setSideQuestStatusText( String text)
    {
        //send the packet
        this.owningScene.sendSetSideQuestStatus(text);
    }
    
    /**
     * complete the main objective for every player in the scene. 
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
            ArmorStatID id = null;
            if(mainObjectiveNewleyCompleted)
            {
                currencyAward = (short)player.getLevelProgressionManager().levelMap.get(levelNumber).mainObjective.currencyAward;
               id = player.getLevelProgressionManager().levelMap.get(levelNumber).mainObjective.statReward;
                
           
            }
            
            //send main objective complete packet
            owningScene.sendMainObjectiveCompletePacket(player.getID(), currencyAward,id); 
            
            //complete main objective
            player.getLevelProgressionManager().completeMainObjective(levelNumber);          
            
            //play sound
            Sound sound = Sound.ambientSound("buffered/complete.ogg",true);  
            this.owningScene.add(sound);
         
        }
       
    }
    
    /**
     * Sets the Main objective status text. This will flash on the screen a well as go in to the quest menu.
     * @param text Text to show
     */
    public void setMainObjectiveStatusText(String text)
    {
        //send the packet
        this.owningScene.sendSetMainQuestStatus(text);
    }
    
    public void insertExplosion(int x, int y)
    {
         //===============================
        //add explosion hitbox and effects 
        //================================

        //explosion hitbox
        Image img = new Image("flashParticle.png");  
        img.setDimensions(300, 300);
        img.setColor(new Color(3,3,1,1));
        img.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.SCALE, 60, 1, 0f));
        img.setHorizontalFlip(SylverRandom.random.nextBoolean());
        img.setVerticalFlip(SylverRandom.random.nextBoolean());

        String particleImageToUse = SylverRandom.random.nextBoolean()?"flashParticle2.png":"flashParticle3.png";
        Image img2 = new Image(particleImageToUse);  
        img2.setDimensions(300, 300);
        img2.setColor(new Color(3,3,1,1));
        img2.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.SCALE, 60, 1, 0f));
        img2.setHorizontalFlip(SylverRandom.random.nextBoolean());
        img2.setVerticalFlip(SylverRandom.random.nextBoolean()); 
        Overlay ehhovhh = new Overlay(img2);
        img.addOverlay(ehhovhh); 

        Damage damage = new Damage(DamageType.NODAMAGE, 0);

           Body beh = new StaticBody(new Circle(180));
           beh.setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);
           beh.setBitmask(Entity.BitMasks.NO_COLLISION.value);
           damage.setType(Damage.DamageType.PHYSICAL);   

           //add stun and slow to damage
           damage.addCombatEffect(new StateEffect(StateEffect.StateEffectType.STUN, 240));

           HitBox explosionHitbox = new HitBox(damage, beh, img, null);
           explosionHitbox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 10, 0, 0));               
           explosionHitbox.setPosition(x, y);
           LeadCrystalParticleEmitters.BombExplosionEmitter emitter = new LeadCrystalParticleEmitters.BombExplosionEmitter();
           emitter.setDuration(15);
           AbstractParticleEmitter fire1 = new LeadCrystalParticleEmitters.GroundFireEmitter1(); 
           fire1.setParticlesPerFrame(.75f);
           fire1.setDuration(360);
           explosionHitbox.addEmitter(fire1);
           explosionHitbox.addEmitter(emitter);

           this.owningScene.add(explosionHitbox, Scene.Layer.MAIN);

           //play sound
           Sound sound = Sound.locationSound("buffered/explosion.ogg",x, y, false, 1f, 1f);
            this.owningScene.add(sound);

    }
    
    public void dropTechnology(int x, int y)
    {
        
        Body technologyBody = new Body(new Box(75,75),1 );
        technologyBody.setBitmask(Entity.BitMasks.COLLIDE_WORLD.value);
        technologyBody.setOverlapMask(Entity.OverlapMasks.NO_OVERLAP.value);
        
        Entity technology = new Entity(new Image("doodad4.png"), technologyBody);
        technology.getImage().setDimensions(75, 75);
       // technology.getImage().setScale(1f);
        
        
        //building respawn script
        ScriptPage page = new ScriptPage();
        page.setScript("scriptManager.completeSideObjective(13);"); 
        
        PageCondition condition = new PageCondition();
        condition.setConditionScript("conditionValue = true;");
        
        ScriptObject obj = new ScriptObject();
        obj.addPage(page, condition);
        obj.setTrigger(ScriptObject.ScriptTrigger.RIGHTCLICK);
        technology.setScriptObject(obj); 
        technology.setPosition(x, y);
        
        this.owningScene.add(technology, Scene.Layer.MAIN);
    }
    
    /**
     * Completes the current level and moves all players back to town.
     */
    public void completeLevel()
    {
       this.completeLevel("checkpoint1");
    }
    
    /**
     * Completes the current level and moves all players back to given checkpoint in town.
     * @param destination checkpoint to move players to in town
     */
    public void completeLevel(String destination)
    {
        //for each player in the scene
        ArrayList<PlayerEntity> playerList = new ArrayList(((GameServerScene)this.owningScene).players);
        for(PlayerEntity player :playerList)
        {  
            //heals players
            player.getCombatData().fullHeal();  
            
            //moves players back to town
            ((GobletServer)Game.getInstance().getRunnable("Goblet Server")).queueMovePlayerToLevel(player.getID(), "town.lv", destination);          
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
    
    /**
     * Enables pvp mode for given client id
     * @param clientID  clientID of the target client (get with invoker.getID())
     */
    public void enablePVP(String clientID)
    {
        ((GameServerScene)this.owningScene).clientsInScene.get(UUID.fromString(clientID)).player.getBody().setOverlapMask(Entity.OverlapMasks.PVP_PLAYER.value);
    }
    
    /**
     * disables pvp mode for given client id
     * @param clientID  clientID of the target client (get with invoker.getID())
     */
    public void disablePVP(String clientID)
    {
        ((GameServerScene)this.owningScene).clientsInScene.get(UUID.fromString(clientID)).player.getBody().setOverlapMask(Entity.OverlapMasks.PLAYER.value);
    }
    
    /**
     * Respawns target player
     * @param clientID clientID of the target client to respawn
     * @param invokerID  clientID of the invoking client (get with invoker.getID())
     */
    public void respawnPlayer(String clientID,String invokerID)
    {
        //get invoker
        PlayerEntity invoker =this.owningScene.clientsInScene.get(UUID.fromString(invokerID)).player;
        
        //if invoker has a potion respawn the player
        if(invoker.getPotionManager().getNumberOfPotions() >0)
        {
            //respawn player
            PlayerEntity respawnedPlayer = this.owningScene.clientsInScene.get(UUID.fromString(clientID)).player;     
            respawnedPlayer.respawn();
            
            
            //remove potion anda add potion graphic
            invoker.getPotionManager().addPotion(-1);
            Image potionImage = new Image("healthPot3.png");
           
            potionImage.addImageEffect(new ImageEffect(ImageEffectType.SCALE, 60, 1, 2));
            potionImage.addImageEffect(new ImageEffect(ImageEffectType.ALPHABRIGHTNESS, 60, 1, 0));
            
            Body body = new Body( new Box(10,10),1);
            body.setGravityEffected(false);
            body.setOverlapMask(Entity.OverlapMasks.NO_OVERLAP.value);
            body.setBitmask(Entity.BitMasks.NO_COLLISION.value);
            Entity potionEntity = new Entity(potionImage, body);
             potionEntity.setPosition(respawnedPlayer.getPosition().x, respawnedPlayer.getPosition().y + 150);
            potionEntity.addEntityEffect(new EntityEffect(EntityEffectType.DURATION, 61, 0, 1));
            this.owningScene.add(potionEntity, Scene.Layer.MAIN);
        }
        
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
    
    /**
     * Adds a sound into the scene at given position
     * @param x x coordinate
     * @param y y coordinate
     * @param soundRef sound to add
     */
    public void playSoundAtPosition(float x, float y, String soundRef)
    {
        Sound soundData = Sound.locationSound(soundRef, x, y, false);
        this.owningScene.add(soundData);
    }
    
    
    /**
     * Adds item pickup sound into the scene at given position
     * @param x x coordinate
     * @param y y coordinate
     */
    public void playPickUpSound(float x, float y)
    {
        //add sound
        Sound goldSound = Sound.locationSound("buffered/jump.ogg", x, y, false, .6f,2f);               
        this.owningScene.add(goldSound);
    }
    
    /**
     * Fades out old background music and fades  new music in
     * @param soundRef 
     */
    public void changeBGM(String soundRef)
    {
        Sound sound =Sound.changeBGM(soundRef, 400, 400);   
        this.owningScene.add(sound);
        sound = Sound.adjustSourceVolume("BGM", .2f);
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
     * @param entityID MobSpawner id to rearm
     */
    public void rearmSpawner(String entityID)
    {
        //Get the spawner
        MobSpawner spawnerEntity = (MobSpawner)this.owningScene.getSceneObjectManager().get(entityID);
 
        //Begin
        spawnerEntity.setArmed(true);
    }
    
    /**
     * Returns scene object with given id
     * @param id id of the scene object to return
     * @return 
     */
    public SceneObject getSceneObject(String id)
    {
        return this.owningScene.getSceneObjectManager().get(id);
    }
    
    /**
     * Triggers the script of a given Entity
     * @param entityID id of entity with script to trigger
     */
    public void triggerScript(String entityID)
    {
        //Get the script object
        ScriptObject scriptObject = ((Entity)this.owningScene.getSceneObjectManager().get(entityID)).getScriptObject();
 
        //trigger the script
        if(scriptObject != null)
            scriptObject.runScript(null); 
    }
    
    /**
     * Adds a loot spawner to given location
     * @param spewTime spew time in frames
     * @param x x coordinate
     * @param y y coordinate
     */
    public void addLootSpewer(float spewTime,float x, float y)
    {
        //add spewer to the world
        LootSpewer spew = new LootSpewer((int)spewTime);
        spew.setPosition(x, y);
        this.owningScene.add(spew, Scene.Layer.MAIN);
    }
    
    public void fadeInTeleporter(String entityId)
    {
        WorldObjectEntity teleporter =  (WorldObjectEntity)this.owningScene.getSceneObjectManager().get(entityId);
       teleporter.getImage().addImageEffect(new ImageEffect(ImageEffectType.COLOR, 120 , new Color(1,1,1,0), new Color(1,1,1,1)));
    }
    public void enableTeleporter(String entityId)
    {
       WorldObjectEntity teleporter =  (WorldObjectEntity)this.owningScene.getSceneObjectManager().get(entityId);
       teleporter.addEmitter(new TeleporterEmitter());
       teleporter.getScriptObject().setTrigger(ScriptObject.ScriptTrigger.RIGHTCLICK);
       teleporter.addToGroup(ExtendedSceneObjectGroups.CLICKABLE); 
    }
    
    public void finishGame()
    {
        
    }
    
    
    
}
