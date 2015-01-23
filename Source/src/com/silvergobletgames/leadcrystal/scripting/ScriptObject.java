package com.silvergobletgames.leadcrystal.scripting;

import com.silvergobletgames.leadcrystal.combat.CombatData;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.sylver.netcode.SaveData;
import com.silvergobletgames.sylver.util.SerializableEntry;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashSet;


public class ScriptObject 
{
    //owning entity
    private Entity owningEntity;
    //script trigger
    private ScriptTrigger scriptTrigger = ScriptTrigger.NONE;
    //script delay
    private int scriptDelay = 0;
    private int delayRemaining = 0;
    private Entity delayEntity = null;
    //pages
    private ArrayList<SimpleEntry<ScriptPage,PageCondition>> scriptPages = new ArrayList();
    //active page
    private ScriptPage activePage;
    
    //list of id's for keeping track of players
    public HashSet<String> playerCollisionList = new HashSet<>();
    
    //script trigger enum
    public static enum ScriptTrigger
    {
        RIGHTCLICK, DEATH, SEPERATE, COLLIDE, AUTO, ALL_PLAYERS_HERE,COLLIDE_EACH_ONCE, NONE, DIALOGUECLOSED;
    }
    
    //===============
    // Constructors
    //===============
    
    public ScriptObject()
    {
        
    }
    
    
    //===============
    // Class Methods
    //===============
    
    /**
     * Updates the script object. Checks conditions to see what script should be active,
     * and activates it.
     */
    public void update()
    {
        if(scriptDelay != 0 && delayRemaining == 0)
        {
            this.runScript(this.delayEntity);
            delayRemaining = scriptDelay;
            this.delayEntity = null;
        }
        
        if(delayRemaining < scriptDelay)
            delayRemaining--;
    
    }
    
    /**
     * Checks the page conditions to see what script should be active, and runs that script
     * @param triggeringEntity 
     */
    public void runScript(Entity triggeringEntity)
    {
        if(delayRemaining == 0)
        {
            //checks conditions to see what page should be active, and activates it
            for(SimpleEntry<ScriptPage,PageCondition> entry: this.scriptPages)
            {
                ScriptPage page = entry.getKey();
                PageCondition condition = entry.getValue();

                //if condition evaluates to true, set active page and break
                if(condition.evaluateCondition() == true)
                {
                    this.activePage = page;
                    break;
                }
            }

            //if active page is null, set the first page to be active
            if(this.activePage == null && this.scriptPages.get(0) != null)
            {
                this.activePage = this.scriptPages.get(0).getKey();
            }

            //if the active page isnt null run its script
            if(this.activePage != null)
            {
                this.activePage.runScript(triggeringEntity);
            }
        }
        else
        {
            if(delayRemaining == this.scriptDelay)
            {
                this.delayEntity = triggeringEntity;
                delayRemaining--;
            }
        }
    }
    
    /**
     * Adds a page/condition pair to the list of script pages.
     * @param page ScriptPage to add
     * @param condition  ScriptCondition that coorelates to the page
     */
    public void addPage(ScriptPage page, PageCondition condition)
    {
        //sets owning script objects
        page.setOwningScriptObject(this);
        condition.setOwningScriptObject(this);
        
        //adds the page and condition to list
        SimpleEntry entry = new SimpleEntry(page,condition);
        this.scriptPages.add(entry);
                    
    }
    
    public void switchToPage(int pageNumber)
    {
        if(this.scriptPages.get(pageNumber) != null)
            this.activePage = this.scriptPages.get(pageNumber).getKey();
    } 
    
    
    //===========
    // Accessors
    //===========
    
    public ScriptTrigger getTrigger()
    {
        return this.scriptTrigger;
    }
    
    public void setTrigger(ScriptTrigger tigger)
    {
        this.scriptTrigger = tigger;
    }
    
    public Entity getOwningEntity()
    {
        return this.owningEntity;
    }
    
    public void setOwningEnity(Entity entity)
    {
        this.owningEntity = entity;
    }
    
    public ArrayList<SimpleEntry<ScriptPage,PageCondition>> getPages()
    {
        return this.scriptPages;
    }
    
    public int getDelay()
    {
        return this.scriptDelay;
    }
    
    public void setDelay(int delay)
    {
        this.scriptDelay = delay;
        this.delayRemaining = delay;
    }
    
    
    //================
    // Saving Methods
    //================
    
    public SaveData dumpFullData()
    {
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
         
        SaveData saveData = new SaveData();
        
        saveData.dataMap.put("trigger", this.scriptTrigger.name());
        
        //script page data
        ArrayList<SerializableEntry<SaveData,SaveData>> pageData = new ArrayList();
        for(SimpleEntry<ScriptPage,PageCondition> entry: this.scriptPages)
        {
            ScriptPage page = entry.getKey();
            PageCondition condition = entry.getValue();
            
            SerializableEntry sEntry = new SerializableEntry(page.dumpFullData(),condition.dumpFullData());
            pageData.add(sEntry);
        }
        
        saveData.dataMap.put("pageData", pageData);
        saveData.dataMap.put("delay",this.scriptDelay);
        
        return saveData;
        
    }
    
    public static ScriptObject buildFromFullData(SaveData saved) 
    {
        ScriptObject scriptObject = new ScriptObject();
        
        //set trigger
        scriptObject.scriptTrigger = ScriptTrigger.valueOf((String)saved.dataMap.get("trigger"));
        
        //page data
        ArrayList<SerializableEntry<SaveData,SaveData>> pageSaveData= (ArrayList<SerializableEntry<SaveData,SaveData>>)saved.dataMap.get("pageData");
        for(SerializableEntry<SaveData,SaveData> entry: pageSaveData)
        {
            ScriptPage pageData = ScriptPage.buildFromFullData(entry.getKey());
            PageCondition condition = PageCondition.buildFromFullData(entry.getValue());
            
            scriptObject.addPage(pageData, condition);
        }
        
        //delay
        if(saved.dataMap.containsKey("delay"))
        {
            scriptObject.setDelay((int)saved.dataMap.get("delay"));
        }
        
        return scriptObject;
    }
}
