package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.sylver.netcode.SaveData;
import java.util.*;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillType;

/**
 * Owned by a Combat Entity.  The purpose of the skill manager is to maintain a list of known
 * Skills and information about them.  For the player data regarding what Skills have been upgraded
 * we may have to get a little more complicated.
 */
public class SkillManager 
{

    // The mapping of Skill names (ID's) to the Skill object 
    private HashMap<SkillID, Skill> knownSkills = new HashMap();
    
    // The mapping of skill types to the known skills of that type 
    private HashMap<Skill.SkillType, ArrayList<SkillID>> skillTypeMap = new HashMap();
    
    //owning reference
    private CombatEntity ownerReference;
    
    //skill points
    private int skillPoints;
    
    
    
    //==============
    // Constructors
    //==============
    
    /**
     * Constructor, taking nothing
     */
    public SkillManager(CombatEntity owner) 
    {
        //set owner reference
        this.ownerReference = owner;
    }

    /**
     * Convenient constructor if we already have a set of skills we want to learn
     */
    public SkillManager(CombatEntity owner, ArrayList<SkillID> initSkills)
    {
        //set owner reference
        this.ownerReference = owner;
        
        //learn initial skills
        for (SkillID sName : initSkills)       
            learnSkill(sName);
        
    }

    
    //================
    // Class Methods
    //================
    
    /**
     * Updates any time-dependant things for the manager
     */
    public void update()
    {
        Iterator<SkillID> iter = knownSkills.keySet().iterator();
        while (iter.hasNext()){
            knownSkills.get(iter.next()).update();
        }
    }
    
    /**
     * Returns a reference to the skill object if it is known.
     */
    public Skill getSkill(SkillID skillName)
    {
        if (hasSkill(skillName)) {
            return knownSkills.get(skillName);
        }
        return null;
    }

    /**
     * Attempts to learn the specified Skill, outputting true if succesful
     */
    public final boolean learnSkill(SkillID skillID) 
    {
        if (!knownSkills.containsKey(skillID)) 
        {
            //get the skill from the factory
            Skill skillObj = SkillFactory.getInstance().getSkill(skillID);
            
            //add the skill to the map
            knownSkills.put(skillID, skillObj);
            skillObj.setUser(ownerReference);
            

            //populate the skillTypeMap
            Skill.SkillType type = skillObj.getType();
            if (!skillTypeMap.containsKey(type)){
                //Build the new array list if it doesn't exist
                skillTypeMap.put(type, new ArrayList());
            }
            //Add the skill type to the associated array list
            skillTypeMap.get(type).add(skillID);
            
            return true;
        }
        return false;
    }

    /**
     * Attempts to unlearn the specified Skill, outputting true if successful;
     */
    public boolean forgetSkill(SkillID skillID)
    {
        if (knownSkills.containsKey(skillID))  
        {
            //Undo skill types
            Skill skillObj = knownSkills.get(skillID);

            //For each skill type that this skill has
            Skill.SkillType type = skillObj.getType();
            ArrayList<SkillID> nameList = skillTypeMap.get(type);
            //Unbind from the appropriate arraylist
            nameList.remove(skillID);
            //Unbind the entry from the entire skillTypeMap if no skills left
            if (nameList.isEmpty())
                skillTypeMap.keySet().remove(type);
            
            //Remove skill from map
            knownSkills.remove(skillID);
            return true;
        }
        return false;
    }

    /**
     * Returns the first skill of the given type.  May return null.  More later!
     * @param type
     * @return 
     */
    public ArrayList<Skill> getKnownSkillsOfType(SkillType type)
    {
        if (skillTypeMap.get(type) != null)
        {
            ArrayList<Skill> list = new ArrayList();
            for(SkillID id :skillTypeMap.get(type))
            {
                list.add(this.getSkill(id));
            }
            
            return list;
            
        }
        return new ArrayList();
    }
    
    /**
     * Returns true if this skill is known.
     */
    public boolean hasSkill(SkillID skillName) {
        return knownSkills.containsKey(skillName);
    }
    
    /**
     * Returns true if there is at least skill with the given type that is known.
     * @param type
     * @return 
     */
    public boolean hasSkillOfType(SkillType type){
        return skillTypeMap.containsKey(type);
    }
    
    /**
     * Returns the set of known skills
     * @return 
     */
    public HashSet<SkillID> getKnownSkills()
    {
        return new HashSet(knownSkills.keySet());     
    }
    
    /**
     * Returns the set of known skill types
     * @return 
     */
    public HashSet<SkillType> getKnownSkillTypes(){
        return (HashSet)skillTypeMap.keySet();
    }
    
    public void setOwner(CombatEntity owner)
    {
        this.ownerReference = owner;
        
        //set skill user
        for(Skill skill :knownSkills.values())
        {
            skill.user = owner;
        }
    }
    
    public int getSkillPoints()
    {
        return this.skillPoints;
    }
    
    public void setSkillPoints(int value)
    {
        this.skillPoints = value;
    }
    
    
    
    //===================
    //Saving Methods
    //===================
    
     public SaveData dumpFullData() 
     {
         //=====================================
         // WARNING
         //-Making changes to this method could
         //break saved data
         //======================================
         
         SaveData saveData = new SaveData();
         ArrayList<String> skillNames = new ArrayList();
         
         for(SkillID skill: this.knownSkills.keySet())
         {
             skillNames.add(skill.name());
         }
         saveData.dataMap.put("skillNames",skillNames);
         saveData.dataMap.put("skillPoints", this.skillPoints);

         
         return saveData;
         
     }
     
     public static SkillManager buildFromFullData(SaveData saved) 
     {       
         //=====================================
         // WARNING
         //-Making changes to this method could
         //break saved data
         //======================================
         
         ArrayList<String> skillNames = ( ArrayList<String>)saved.dataMap.get("skillNames");
         ArrayList<SkillID> skillEnums = new ArrayList();
         
         for(String name: skillNames)
         {
             skillEnums.add(SkillID.valueOf(name));
         }
         
         SkillManager manager = new SkillManager(null,skillEnums);
         
         Integer points = (Integer)saved.dataMap.get("skillPoints");
         if( points != null)
             manager.skillPoints = points;
         
         return manager;
     }
    
    
}