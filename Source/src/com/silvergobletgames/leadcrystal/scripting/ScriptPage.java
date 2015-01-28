package com.silvergobletgames.leadcrystal.scripting;

import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.netcode.SaveData;
import com.silvergobletgames.sylver.util.Log;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.*;

public class ScriptPage 
{
    //owning script object
    private ScriptObject owningScriptObject;
    //the executed script
    private String mainScript;
    
    //script engine
    private ScriptEngine engine;
    //compiled script
    private CompiledScript compiledScript;
    
    //===============
    // Constructor
    //===============
    
    public ScriptPage()
    {       
        //default scripts
        mainScript = "";
        
        //build engine
        ScriptEngineManager factory = new ScriptEngineManager();
        engine = factory.getEngineByName("JavaScript");
    }
    
    //===============
    // Class Methods
    //===============
    
    public void runScript(Entity triggeringEntity)
    {
        //gets self and sceneScriptManager references
        Entity entity =this.owningScriptObject.getOwningEntity();
        SceneScriptManager manager =((GameServerScene)this.owningScriptObject.getOwningEntity().getOwningScene()).getScriptManager();
        
        try
        {
            //builds bindings
            Bindings scriptBindings  = engine.createBindings();
            scriptBindings.put("self",entity);
            scriptBindings.put("scriptManager", manager);
            scriptBindings.put("invoker",triggeringEntity);
            
            //run the script
            compiledScript.eval(scriptBindings);
        }
        catch (ScriptException ex)
        {
            Log.error( "Script Error", ex);
        }
    }
    
    
    //===========
    // Accessors
    //===========
    
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
     * @return The owning ScriptObject
     */
    public ScriptObject getOwningScriptObject()
    {
        return this.owningScriptObject;
    }
    
    public void setScript(String script)
    {
        //set strings
        this.mainScript = script;
        
        //get compiling engine
        Compilable compilingEngine = (Compilable)engine;
        try
        {
            //compile it
            CompiledScript newlyCompiledScript = compilingEngine.compile(script);
            this.compiledScript = newlyCompiledScript;
        }
        catch (ScriptException ex)
        {
            Log.error( "Script Error", ex);
        }
    }
    
    public String getScript()
    {
        return this.mainScript;
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
        
        saveData.dataMap.put("mainScript",this.mainScript);
        
        return saveData;
        
    }
    
    public static ScriptPage buildFromFullData(SaveData saved) 
    {
        ScriptPage page = new ScriptPage();
        page.setScript( (String)saved.dataMap.get("mainScript"));
        
        return page;
    }
}
