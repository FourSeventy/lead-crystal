package com.silvergobletgames.leadcrystal.combat;

import com.silvergobletgames.leadcrystal.cutscenes.CutsceneManager.Cutscenes;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorStat.ArmorStatID;
import com.silvergobletgames.leadcrystal.items.Currency;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.netcode.SaveData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderDataChanges;
import com.silvergobletgames.sylver.util.SerializableEntry;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

/**
 *
 * @author Mike
 */
public class LevelProgressionManager 
{
    //player reference
    public PlayerEntity playerReference;
    
    //map of all levels
    public HashMap<Integer,Level> levelMap = new HashMap();
    
    //map of cutscene completeness
    public HashMap<Cutscenes,Boolean> cutsceneCompleteMap = new HashMap();
    
    //=============
    // Constructor
    //=============
    
    public LevelProgressionManager(PlayerEntity playerReference)
    {
        //player reference
        this.playerReference = playerReference;
        
        //fill in cutscene map
        for(Cutscenes cutscene:Cutscenes.values())
        {
            this.cutsceneCompleteMap.put(cutscene, Boolean.FALSE);
        }
        
        //=================
        // Desert Levels
        //=================
        
        //desert 0
        Level level0 = new Level();
        level0.levelDataName = "desert0.lv";
        level0.levelName = "Reach Town";
        //main objective
        LevelObjective objective = new LevelObjective();
        objective.objectiveName = "Get To Town";
        objective.objectiveDescription = "Clear the outskirts of town of enemies and locate the town";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        level0.mainObjective = objective;
        //side objective 
        objective = new LevelObjective();
        objective.objectiveName = "null";
        objective.objectiveDescription = "";
        objective.currencyAward = 0;
        level0.sideObjective = objective;
        //adding to map
        this.levelMap.put(0, level0);
        
        //desert 1
        Level level1 = new Level();
        level1.levelDataName = "desert1.lv";
        level1.levelName = "Clear Antenna";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Clear Antenna";
        objective.objectiveDescription = "Since the attack on the town our antenna has become overrun by scorpions. Clear the antenna of"
                                          + " scorpions and get it working again.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        level1.mainObjective = objective;
        //side objective 
        objective = new LevelObjective();
        objective.objectiveName = "Collect Supplies";
        objective.objectiveDescription = "We lost some supplies in that area. See if you can find 3 boxes of supplies";
        objective.currencyAward = 25;
        level1.sideObjective = objective;
        //adding to map
        this.levelMap.put(1, level1);
        
        //desert 2
        Level level2 = new Level();
        level2.levelDataName = "desert2.lv";
        level2.levelName = "Missing Power Supply";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Find Power Supply";
        objective.objectiveDescription = "The blacksmith has lost a power supply outside the city walls. Find this supply for him and he will be able to make you gear.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        level2.mainObjective = objective;
        //side objective 
        objective = new LevelObjective();
        objective.objectiveName = "Find Mysterious Artifact";
        objective.objectiveDescription = "Keep your eyes open for any mysterious artifacts you may find out in the desert.";
        objective.currencyAward = 25;
        level2.sideObjective = objective;
        //adding to map
        this.levelMap.put(2, level2);
        
        //desert 3
        Level level3 = new Level();
        level3.levelDataName = "desert3.lv";
        level3.levelName = "Destroy Large Dabat Nest";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Destroy Nest";
        objective.objectiveDescription = "The townsfolk have noticed a lot of Dabats around, find and destroy the Dabat nest.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        level3.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Giant Dabat";
        objective.objectiveDescription = "There is rumored to be a giant Dabat in this area. Destroy it.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.MELEE_DAMAGE;
        level3.sideObjective = objective;
        //adding to map
        this.levelMap.put(3, level3);
        
        //desert 4
        Level level4 = new Level();
        level4.levelDataName = "desert4.lv";
        level4.levelName = "Restart Power Generator";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Fix Power Generator";
        objective.objectiveDescription = "The defenses of the power generation station nearby have been ravaged by Dahla, and now the generator  has been taken over by the deadly monsters. Clear the generator of the monsters and restart it.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        level4.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Lost Weapons";
        objective.objectiveDescription = " Find the lost weapons cache.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.IMPROVED_RADAR;
        level4.sideObjective = objective;
        //adding to map
        this.levelMap.put(4, level4);
        
        //desert 5
        Level level5 = new Level();
        level5.levelDataName = "desert5.lv";
        level5.levelName = "Search and Rescue";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Search For Caravan";
        objective.objectiveDescription = "We lost contact with a caravan carrying supplies to the town, go investigate";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        level5.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Survivors";
        objective.objectiveDescription = "Search for any survivors of the caravan";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.HARD_TO_KILL;
        level5.sideObjective = objective;
        //adding to map
        this.levelMap.put(5, level5);
        
        //================
        // Cave Levels
        //================
        //cave 1
        Level cave1 = new Level(); 
        cave1.levelDataName = "cave1.lv";
        cave1.levelName = "Water Pumps";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Reactivate Water Pumps";
        objective.objectiveDescription = "Find and activate the three water pumps.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        cave1.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Lost Treasure";
        objective.objectiveDescription = "Find the hidden artifact.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.JETPACK;
        cave1.sideObjective = objective;
        //adding to map
        this.levelMap.put(6, cave1);
        
        //cave 2
        Level cave2 = new Level(); 
        cave2.levelDataName = "cave2.lv";
        cave2.levelName = "Clear Caves";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Kill Giant Motoccos";
        objective.objectiveDescription = "Kill all 5 giant motoccos in the cave.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        cave2.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Kill Jumpers";
        objective.objectiveDescription = "Kill 10 jumpers";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.POTION_COOLDOWN_RESET;
        cave2.sideObjective = objective;
        //adding to map
        this.levelMap.put(7, cave2);
        
        //cave 3
        Level cave3 = new Level(); 
        cave3.levelDataName = "cave3.lv";
        cave3.levelName = "Sewage Pipe";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Close off leaking sewage pipe";
        objective.objectiveDescription = "Find and close off leaking sewage pipe";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        cave3.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Missing artifact";
        objective.objectiveDescription = "Find the missing artifact";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.DOUBLE_GOLD;
        cave3.sideObjective = objective;
        //adding to map
        this.levelMap.put(8, cave3);
        
        //cave 4
        Level cave4 = new Level(); 
        cave4.levelDataName = "cave4.lv";
        cave4.levelName = "Investigate ruins";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Investigate Ruins";
        objective.objectiveDescription = "Investigate the underground ruins";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        cave4.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Kill Mucker";
        objective.objectiveDescription = "Kill the giant Mucker lurking in the ruins.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.PROXIMITY_DAMAGE_REDUCTION;
        cave4.sideObjective = objective;
        //adding to map
        this.levelMap.put(9, cave4);
        
        //cave 5
        Level cave5 = new Level(); 
        cave5.levelDataName = "cave5.lv";
        cave5.levelName = "Track Down Relic";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Find the Ancient Relic";
        objective.objectiveDescription = "Head into the temple and find the ancient relic.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        objective.statReward = ArmorStatID.MOVE_SPEED;
        cave5.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "null";
        objective.objectiveDescription = "Do something.";
        objective.currencyAward = 25;
        cave5.sideObjective = objective;
        //adding to map
        this.levelMap.put(10, cave5);
        
        //temple1
        Level temple1 = new Level(); 
        temple1.levelDataName = "temple1.lv";
        temple1.levelName = "Temple1";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do Something";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        temple1.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do something.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.MOVE_SPEED;
        temple1.sideObjective = objective;
        //adding to map
        this.levelMap.put(11,temple1);
        
        //temple2
        Level temple2 = new Level(); 
        temple2.levelDataName = "temple21.lv";
        temple2.levelName = "Temple2";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do Something";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        temple2.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do something.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.MOVE_SPEED;
        temple2.sideObjective = objective;
        //adding to map
        this.levelMap.put(12,temple2);
        
        //temple3
        Level temple3 = new Level(); 
        temple3.levelDataName = "temple3.lv";
        temple3.levelName = "Temple3";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do Something";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        temple3.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do something.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.MOVE_SPEED;
        temple3.sideObjective = objective;
        //adding to map
        this.levelMap.put(13,temple3);
        
        //temple4
        Level temple4 = new Level(); 
        temple4.levelDataName = "temple4.lv";
        temple4.levelName = "Temple4";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do Something";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        temple4.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do something.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.MOVE_SPEED;
        temple4.sideObjective = objective;
        //adding to map
        this.levelMap.put(14,temple4);
        
        //temple5
        Level temple5 = new Level(); 
        temple5.levelDataName = "temple5.lv";
        temple5.levelName = "Temple5";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do Something";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        temple5.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do something.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.MOVE_SPEED;
        temple5.sideObjective = objective;
        //adding to map
        this.levelMap.put(15,temple5);
        
        //temple6
        Level temple6 = new Level(); 
        temple6.levelDataName = "temple6.lv";
        temple6.levelName = "Temple6";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do Something";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        temple6.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do something.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.MOVE_SPEED;
        temple6.sideObjective = objective;
        //adding to map
        this.levelMap.put(16,temple6);
        
        //temple7
        Level temple7 = new Level(); 
        temple7.levelDataName = "temple7.lv";
        temple7.levelName = "Temple7";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do Something";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        temple7.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do something.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.MOVE_SPEED;
        temple7.sideObjective = objective;
        //adding to map
        this.levelMap.put(17,temple7);
        
        //test1
        Level test1 = new Level(); 
        test1.levelDataName = "test1.lv";
        test1.levelName = "Test1";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do Something";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        test1.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do something.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.MOVE_SPEED;
        test1.sideObjective = objective;
        //adding to map
        this.levelMap.put(18,test1);
        
        //test2
        Level test2 = new Level(); 
        test2.levelDataName = "test2.lv";
        test2.levelName = "Test2";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do Something";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        test2.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Do something";
        objective.objectiveDescription = "Do something.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.MOVE_SPEED;
        test2.sideObjective = objective;
        //adding to map
        this.levelMap.put(19,test2);
        
        
        
        
    }
    
    //===========
    // Methods 
    //===========
    
    public void completeMainObjective(int levelNumber)
    {
        //get level
        Level level = this.levelMap.get(levelNumber);
        
        //give rewards
        if(level.mainObjective.complete == false)
        {           
            //add skill point
            if(level.mainObjective.skillPointAward == true)
                playerReference.getSkillManager().setSkillPoints(playerReference.getSkillManager().getSkillPoints() + 1);

            //add currency rewards
            playerReference.getCurrencyManager().addCurrency(level.mainObjective.currencyAward);
            
            //add armor modifier reward
            if(level.mainObjective.statReward != null)
                playerReference.getArmorManager().armorStats.get(level.mainObjective.statReward).unlocked = true;
        
        }
                
        //mark complete
        level.mainObjective.complete = true;
    }
    
    public void completeSideObjective(int levelNumber)
    {
        //get level
        Level level = this.levelMap.get(levelNumber);
        
        //give rewards
        if(level.sideObjective.complete == false)
        {           
            //add skill point
            if(level.sideObjective.skillPointAward == true)
                playerReference.getSkillManager().setSkillPoints(playerReference.getSkillManager().getSkillPoints() + 1);

            //add currency rewards
            playerReference.getCurrencyManager().addCurrency(level.sideObjective.currencyAward);
            
            //add armor modifier reward
            if(level.sideObjective.statReward != null)
                playerReference.getArmorManager().armorStats.get(level.sideObjective.statReward).unlocked = true;
        }
                
        //mark complete
        level.sideObjective.complete = true;
    }

    
    public int getLevelNumberFromDataName(String levelDataName) throws Exception
    {
        for(Entry<Integer,Level> entry: this.levelMap.entrySet())
        {
            int levelNumber = entry.getKey();
            Level level = entry.getValue();
            
            if(level.levelDataName.equals(levelDataName))
                return levelNumber;
        }
        
        throw new Exception("Level Not Found");
    }
    
      
    
    //==============
    // Level Class
    //==============
    
    public static class Level
    {
        
        //the name of the file containing the level data
        public String levelDataName;       
        //level name
        public String levelName;
        
        //main objective
        public LevelObjective mainObjective;       
        //side objectives
        public LevelObjective sideObjective;
        
        
        //==============
        // Constructor
        //==============
        public Level()
        {
            
        }
               
    }
    
    public static class LevelObjective
    {
        //objective name
        public String objectiveName;
        //objective description
        public String objectiveDescription;
        //completed
        public boolean complete = false;
        
        //objective rewards
        public boolean skillPointAward = false;
        public int currencyAward;
        public ArmorStatID statReward = null;
        
    }
    
    //===================
    // Class Methods
    //===================
    
    public Level getLevel(int levelNumber)
    {
        return this.levelMap.get(levelNumber); 
    }
    
    public boolean isLevelComplete(int levelNumber)
    {
        return this.levelMap.get(levelNumber).mainObjective.complete;
    }
   
    //====================
    // RenderData Methods
    //====================
     public RenderData dumpRenderData()
     {
         RenderData renderData = new RenderData();
         
         //for each level
         for(int i = 0; i < this.levelMap.size() ; i++)
         {
             ArrayList<Boolean> objectiveCompleteList = new ArrayList();
             Level level= this.levelMap.get(i);
             objectiveCompleteList.add(level.mainObjective.complete);
             objectiveCompleteList.add(level.sideObjective.complete);
             renderData.data.add(i, objectiveCompleteList);        
         }

         
         return renderData;        
     }
     
     public static LevelProgressionManager buildFromRenderData(RenderData renderData)
     {
         LevelProgressionManager manager = new LevelProgressionManager(null);
         
         for(int i = 0; i < manager.levelMap.size(); i++)
        {
            ArrayList<Boolean> objectiveCompleteList;
            objectiveCompleteList = (ArrayList<Boolean>)renderData.data.get(i);

            Level level = manager.levelMap.get(i);
            level.mainObjective.complete = objectiveCompleteList.get(0);
            if(objectiveCompleteList.get(1) != null)
                level.sideObjective.complete = objectiveCompleteList.get(1);
        }
         
         return manager;
         
     }
     
     public SceneObjectRenderDataChanges generateRenderDataChanges(RenderData oldData,RenderData newData)
     {
         SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();
        
         int changeMap = 0;
         ArrayList changeList = new ArrayList();
         
         //generate a list of newly completed objectives
         ArrayList<SerializableEntry> newlyCompletedObjectives = new ArrayList();        
         for(int i = 0; i < oldData.data.size(); i++)
         {
             ArrayList<Boolean> oldLevel = (ArrayList<Boolean>)oldData.data.get(i);
             ArrayList<Boolean> newLevel = (ArrayList<Boolean>)newData.data.get(i);
             
             for(int j = 0; j < oldLevel.size(); j ++)
             {
                 if( oldLevel.get(j) != null && !oldLevel.get(j).equals(newLevel.get(j)))
                 {
                     newlyCompletedObjectives.add(new SerializableEntry(i,j));
                 }
             }
         }
         
         if(!newlyCompletedObjectives.isEmpty())
         {
             changeList.add(newlyCompletedObjectives);
             changeMap += 1L;
         }
         
         changes.fields = changeMap;
         changes.data = changeList.toArray();
         
         if(changeList.size() > 0)
            return changes;
        else
            return null;
        
     }
     
     public void reconcileRenderDataChanges(long lastTime,long futureTime,SceneObjectRenderDataChanges renderDataChanges)
     {
            //construct an arraylist of data that we got, nulls will go where we didnt get any data    
            int fieldMap = renderDataChanges.fields;
            ArrayList rawData = new ArrayList();
            rawData.addAll(Arrays.asList(renderDataChanges.data));           
            ArrayList changeData = new ArrayList();
            for(byte i = 0; i <8; i ++)
            {
                // The bit was set
                if ((fieldMap & (1L << i)) != 0)
                {
                    changeData.add(rawData.get(0));
                    rawData.remove(0);
                }
                else
                    changeData.add(null);          
            }

            if(changeData.get(0) != null)
            {
                for(SerializableEntry entry: (ArrayList<SerializableEntry>)changeData.get(0))
                {
                    Level level = this.levelMap.get((Integer)entry.getKey());
                    switch((Integer)entry.getValue())
                    {
                        case 0: level.mainObjective.complete = true; break;
                        case 1: level.sideObjective.complete = true; break;
                    }
                }
            }

     }
    
   //==================
   //Save Data Methods
   //==================
   
   public SaveData dumpFullData() 
   {
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
       
       SaveData saveData = new SaveData();
       
       //for each level
       for(int i = 0; i < this.levelMap.size(); i++)
       {
           ArrayList<Boolean> objectiveCompleteList = new ArrayList();
           Level level= this.levelMap.get(i);
           objectiveCompleteList.add(level.mainObjective.complete);
           objectiveCompleteList.add(level.sideObjective.complete);
           saveData.dataMap.put(Integer.toString(i), objectiveCompleteList);
       }
        
       //cutscenes
       saveData.dataMap.put("cutscenes", this.cutsceneCompleteMap);
        return saveData;
   }
   
   
   public static LevelProgressionManager buildFromFullData(SaveData saveData)  
   {
       //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
       
       LevelProgressionManager levelManager = new LevelProgressionManager(null);
       for(int i = 0; i < levelManager.levelMap.size(); i++)
       {
          ArrayList<Boolean> objectiveCompleteList;
          objectiveCompleteList = (ArrayList<Boolean>)saveData.dataMap.get(Integer.toString(i));
          
          Level level = levelManager.levelMap.get(i);
          level.mainObjective.complete = objectiveCompleteList.get(0);
          if(objectiveCompleteList.get(1) != null)
              level.sideObjective.complete = objectiveCompleteList.get(1);
       }
       
       //cutscenes
       HashMap<Cutscenes,Boolean> map = (HashMap<Cutscenes,Boolean>)saveData.dataMap.get("cutscenes");
       if(map != null)
       {
            for(Cutscenes cutscene:map.keySet())
            {
                levelManager.cutsceneCompleteMap.put(cutscene, map.get(cutscene));
            }
       }
       
       
       
       return levelManager;
       
   }
}
