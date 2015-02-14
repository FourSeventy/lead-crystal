package com.silvergobletgames.leadcrystal.combat;

import com.silvergobletgames.leadcrystal.cutscenes.CutsceneManager.Cutscenes;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorStat.ArmorStatID;
import com.silvergobletgames.sylver.netcode.SaveData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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
        level0.levelName = "Find Titanis";
        //main objective
        LevelObjective objective = new LevelObjective();
        objective.objectiveName = "Locate Town";
        objective.objectiveDescription = "You recieved a distress signal from a town called Titanis. Find the town and defeat any enemies in your path.";
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
        objective.objectiveName = "Kill Creatures Near Antenna ";
        objective.objectiveDescription = "The town's antenna has been overrun by creatures called Motaccos. Clear the antenna off to get it functioning again.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        level1.mainObjective = objective;
        //side objective 
        objective = new LevelObjective();
        objective.objectiveName = "Collect Supplies";
        objective.objectiveDescription = "The townsfolk lost some supplies in that area. See if you can find 3 boxes of supplies.";
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
        objective.objectiveDescription = "The blacksmith lost a power supply out in the desert somewhere. Find this supply for him and he will be able to upgrade your gear.";
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
        level3.levelName = "Dabat Nest";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Destroy Large Dabat Nest";
        objective.objectiveDescription = "The townsfolk have noticed a lot of Dabats flying around. Find and destroy the Dabat nest.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        level3.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Kill Giant Dabat";
        objective.objectiveDescription = "There is rumored to be a giant Dabat in this area. Destroy it.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.MELEE_DAMAGE;
        level3.sideObjective = objective;
        //adding to map
        this.levelMap.put(3, level3);
        
        //desert 4
        Level level4 = new Level();
        level4.levelDataName = "desert4.lv";
        level4.levelName = "Power Generator";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Repair the Power Generator";
        objective.objectiveDescription = "The power generation station nearby has been overrun by creatures and become inoperable. Clear the generator of the creatures and restart it.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        level4.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Find Weapon Cache";
        objective.objectiveDescription = "There is a crate of lost weapons in this area, see if you can find it.";
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
        objective.objectiveName = "Find Missing Caravan";
        objective.objectiveDescription = "We lost contact with a caravan carrying supplies to the town, go investigate.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        objective.statReward = ArmorStatID.THORNS_DAMAGE;
        level5.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Search For Survivors";
        objective.objectiveDescription = "Search for any survivors of the caravan.";
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
        objective.objectiveDescription = "The town's underground water pumps seem to be malfunctioning. Find and activate the 3 water pumps.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        cave1.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Find Power Crystal";
        objective.objectiveDescription = "Rumor has it a rare crystal can be found in this cave system. Brice would love to study it if you can find it.";
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
        objective.objectiveDescription = "Several giant motoccos are dwelling in this cave system. Exterminate all 5 of them.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        cave2.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Kill Jumpers";
        objective.objectiveDescription = "Countless Jumpers are roaming these caves. Thin their numbers by at least 10.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.LIFE_LEECH;
        cave2.sideObjective = objective;
        //adding to map
        this.levelMap.put(7, cave2);
        
        //cave 3
        Level cave3 = new Level(); 
        cave3.levelDataName = "cave3.lv";
        cave3.levelName = "Sewage Pipe";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Flush Enemies From Sewer";
        objective.objectiveDescription = "Motoccos and Jumpers have made a haven out of the sewer system. Get down there and clear them out.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        cave3.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Find Talisman";
        objective.objectiveDescription = "Some townsfolk speak about a rare talisman that might be found in this area. Find the missing artifact.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.DOUBLE_GOLD;
        cave3.sideObjective = objective;
        //adding to map
        this.levelMap.put(8, cave3);
        
        //cave 4
        Level cave4 = new Level(); 
        cave4.levelDataName = "cave4.lv";
        cave4.levelName = "Investigate Ruins";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Investigate Ruins";
        objective.objectiveDescription = "There is an ancient underground ruin in this cavern. It is probably worth checking out.";
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
        cave5.levelName = "Recon Commandos";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Investigate Commando Presence";
        objective.objectiveDescription = "Commandos have been spotted entering the underground ruins, see what they are up to.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        objective.statReward = ArmorStatID.MOVE_SPEED;
        cave5.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Find Map";
        objective.objectiveDescription = "Find a map to lead us to the commando base.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.CRIT_DAMAGE;
        cave5.sideObjective = objective;
        //adding to map
        this.levelMap.put(10, cave5);
        
        //temple1
        Level temple1 = new Level(); 
        temple1.levelDataName = "temple1.lv";
        temple1.levelName = "Commando Outposts";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Clear 3 Outposts";
        objective.objectiveDescription = "The commandos have set up 3 guard outposts in the ruins. Wipe them out.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        temple1.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Hack mainframe";
        objective.objectiveDescription = "Find and hack the commando mainframe hidden in the ruins.";
        objective.currencyAward = 25;
        objective.statReward = ArmorStatID.HEALING_EFFECTIVENESS;
        temple1.sideObjective = objective;
        //adding to map
        this.levelMap.put(11,temple1);
        
        //temple2
        Level temple2 = new Level(); 
        temple2.levelDataName = "temple2.lv";
        temple2.levelName = "Weapon Caches";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Destroy Commando Weapon Cache";
        objective.objectiveDescription = "Vexx's forces have stored a weapon cache in the ruins somewhere. Find it and destroy it.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        temple2.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Destroy Grenade Cache";
        objective.objectiveDescription = "Find and destroy the crates of grenades.";
        objective.currencyAward = 25;
        temple2.sideObjective = objective;
        //adding to map
        this.levelMap.put(12,temple2);
        
        //temple3
        Level temple3 = new Level(); 
        temple3.levelDataName = "temple3.lv";
        temple3.levelName = "Commando Officers";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Kill 3 Commando Officers";
        objective.objectiveDescription = "Kill the 3 commando officers in this area. Be careful, these commandos are extremely dangerous.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        temple3.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Salvage Technology";
        objective.objectiveDescription = "Salvage some technology from one of the commando tanks.";
        objective.currencyAward = 25;
        temple3.sideObjective = objective;
        //adding to map
        this.levelMap.put(13,temple3);
        
        //temple4
        Level temple4 = new Level(); 
        temple4.levelDataName = "temple4.lv";
        temple4.levelName = "Rescue Mission";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Rescue Captured Townsfolk";
        objective.objectiveDescription = "Several members of the town were captured by Vexx's forces. Find and rescue the captured townsfolk";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        temple4.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Rare Blade";
        objective.objectiveDescription = "Rumour has it one of the commandos is carrying a rare blade weapon, find it for Slash.";
        objective.currencyAward = 25;
        temple4.sideObjective = objective;
        //adding to map
        this.levelMap.put(14,temple4);
        
        //temple5
        Level temple5 = new Level(); 
        temple5.levelDataName = "temple5.lv";
        temple5.levelName = "Tank Commander";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Kill Tank Commander";
        objective.objectiveDescription = "An elite tank commander is guarding this section of the ruins. Show him who's boss.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        temple5.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Disable Uplink";
        objective.objectiveDescription = "There is a satellite uplink installed in the ruins somewhere. If you knock it out Vexx will lose communication with this forces.";
        objective.currencyAward = 25;
        temple5.sideObjective = objective;
        //adding to map
        this.levelMap.put(15,temple5);
        
        //temple6
        Level temple6 = new Level(); 
        temple6.levelDataName = "temple6.lv";
        temple6.levelName = "Turret Commander";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Kill Turret Commander";
        objective.objectiveDescription = "Kill the elite turret commander. Watch that crossfire.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        temple6.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "Gold Stockpile";
        objective.objectiveDescription = "Find the commando gold stockpile.";
        objective.currencyAward = 25;
        temple6.sideObjective = objective;
        //adding to map
        this.levelMap.put(16,temple6);
        
        //temple7
        Level temple7 = new Level(); 
        temple7.levelDataName = "temple7.lv";
        temple7.levelName = "Final Duel";
        //main objective
        objective = new LevelObjective();
        objective.objectiveName = "Kill Vexx";
        objective.objectiveDescription = "Face Vexx in one on one combat. Be prepared, Vexx is extremely dangerous.";
        objective.skillPointAward = true;
        objective.currencyAward = 50;
        temple7.mainObjective = objective;
        //side objective 1
        objective = new LevelObjective();
        objective.objectiveName = "null";
        objective.objectiveDescription = "null";
        objective.currencyAward = 25;
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
