package com.silvergobletgames.leadcrystal.scripting;

import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.scenes.GameScene;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.netcode.SaveData;
import com.silvergobletgames.sylver.util.Log;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.*;


public class PageCondition 
{
    
    //owning script object
    private ScriptObject owningScriptObject;
    //the condition script
    private String conditionScript;
    
    //script engine
    private ScriptEngine engine;
    //compiled script
    private CompiledScript compiledScript;
    //script bindings
    private Bindings scriptBindings;
    
    //===============
    // Constructor
    //===============
    public PageCondition()
    {       
        //build engine
        ScriptEngineManager factory = new ScriptEngineManager();
        engine = factory.getEngineByName("JavaScript");
        
    }
    
    //===============
    // Class methods
    //===============
    
    public boolean evaluateCondition()
    {
        //gets self, sceneScriptManager, and host references
        Entity entity =this.owningScriptObject.getOwningEntity();
        SceneScriptManager manager =((GameScene)this.owningScriptObject.getOwningEntity().getOwningScene()).getScriptManager();
        PlayerEntity player = ((GameScene)this.owningScriptObject.getOwningEntity().getOwningScene()).getPlayer();
        
        //initializes our return value to false
        boolean conditionReturnValue = false;
      
        try
        {    
            //build the script bindings, if we dont already have them
            if(this.scriptBindings == null)
            {
                this.scriptBindings  = engine.createBindings();
                this.scriptBindings.put("self",entity);
                this.scriptBindings.put("scriptManager", manager);
                this.scriptBindings.put("host", player);    
            }
            
            //run the script
            compiledScript.eval(scriptBindings);
            
            //check the condition value
            conditionReturnValue = (Boolean)scriptBindings.get("conditionValue");
            
        }
        catch (ScriptException ex)
        {
            Log.error( "Script Error", ex);
        }
        
        return conditionReturnValue;
    }
    
    
    //============
    // Accessors
    //============
    
    /**
     * 
     * @param object owning ScriptObject to set
     */
    public void setOwningScriptObject(ScriptObject object)
    {
        this.owningScriptObject = object;
    }
    
    /**
     * 
     * @return the owning ScriptObject
     */
    public ScriptObject getOwningScriptObject()
    {
        return this.owningScriptObject;
    }
    
    public void setConditionScript(String script)
    {
        //sets the string
        this.conditionScript = script;
        
        //get compiling engine
        Compilable compilingEngine = (Compilable)engine;
        try
        {
            CompiledScript newlyCompiledScript = compilingEngine.compile(conditionScript);
            this.compiledScript = newlyCompiledScript;
        }
        catch (ScriptException ex)
        {
            Log.error( "Script Error", ex);
        }
    }
    
    public String getConditionScript()
    {
        return this.conditionScript;
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
        
        saveData.dataMap.put("conditionScript", this.conditionScript);
        
        return saveData;
        
    }
    
    public static PageCondition buildFromFullData(SaveData saved) 
    {
        PageCondition condition = new PageCondition();
        
        condition.setConditionScript((String)saved.dataMap.get("conditionScript"));
        
        return condition;
    }
    
    
}
