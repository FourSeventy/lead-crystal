package com.silvergobletgames.leadcrystal.combat;


import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.netcode.SaveData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderDataChanges;
import com.silvergobletgames.sylver.util.SerializableEntry;
import java.util.*;
import net.phys2d.math.Vector2f;
import com.silvergobletgames.leadcrystal.combat.Damage.DamageType;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.items.DropGenerator;
import com.silvergobletgames.leadcrystal.items.DropGenerator.DropChance;
import com.silvergobletgames.leadcrystal.items.DropGenerator.DropQuality;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;


/**
 * Struct type class that contains information relevant to a CombatEntity's current combat state.
 * @author Goku
 */
public class CombatData 
{
    //combat entity reference
    private CombatEntity self;   
    
    //health 
    public Stat maxHealth = new Stat(1);
    public float currentHealth = 1;   
    
    //base damage
    public Stat baseDamage = new Stat(0);
    
    //secondary stats
    public Stat critChance = new Stat(0);
    public Stat critModifier = new Stat(0);
    public Stat cooldownModifier = new Stat(1);
    public Stat lifeLeech = new Stat(0);
    
    //movement speed
    public Stat xVelocity = new Stat(15);
    public Stat yVelocity = new Stat(15);
       
    //Resistances
    public Stat damageResistance = new Stat(0);
    public Stat ccResistance = new Stat(0);
    public Stat healingModifier = new Stat(1);
    
    //The list of combat states that we are in.
    private HashMap<CombatState, Boolean> combatStates = new HashMap(); 
    
    //combat effects
    private HashMap<String,CombatEffect> combatEffects = new HashMap();
    
    
    //misc
    public int timeOfDeath;
    public float overkill;
    public DropGenerator.DropChance dropChance = DropGenerator.DropChance.NONE;
    public DropGenerator.DropQuality dropQuality = DropGenerator.DropQuality.VeryPoor;
    
     
    /**
     * Definition of combat "states" that we can be in one or more of at a time.
     * This includes, but is not restricted to, CC.
     */
    public enum CombatState
    {
        STUN, SLOW, IMMOBILIZE, DEAD, ATTACKING, IMMUNE;
    }
    
    //===============
    // Constructor 
    //===============
    
    public CombatData(CombatEntity owner)
    {
        //owner
        this.self = owner;
        
        //init states     
        for(CombatState state: CombatState.values())
            this.combatStates.put(state, false);
        
    }
    
    
    //==============
    // Class Methods
    //==============
    
    /**
     * Performs time-based updates
     */
    public void update()
    { 
        //check for death
        if(currentHealth <= 0 && !this.getState(CombatState.DEAD))
           self.die(); 
       
        //if we are not dead, do stuff
        if (!this.getState(CombatState.DEAD)) 
        {          

            if (currentHealth > maxHealth.getTotalValue()) 
            {
                currentHealth = maxHealth.getTotalValue();
            }

            //update combat effects
            HashMap<String,CombatEffect> updateList = new HashMap(this.combatEffects);
            for (CombatEffect effect: updateList.values())
                effect.update();
        }
    }           
 
    /**
     * Adds a CombatEffect without specifying a key
     * @param e CombatEffect to add
     */
    public void addCombatEffect(CombatEffect e) 
    {
        //uese the name as they key unless the name is null, in that case it generates a random key
        String key;
        if(e.getName() == null)
            key =Integer.toString(e.hashCode());
        else
            key = e.getName();
        
        this.addCombatEffect(key,e);
    }
    
    /**
     * Adds a CombatEffect with a specific key
     * @param key The key for this combat effect
     * @param effect The effect to add
     */
    public void addCombatEffect(String key, CombatEffect effect)
    {
        effect.owningEntity = this.self;
        if (effect.canBeApplied())
        {
            effect.onApply();
            combatEffects.put(key, effect);
        }
    }
    
    /**
     * Removes a specific CombatEffect from the list.
     * 
     */
    public void removeCombatEffect(CombatEffect e) 
    {
        for(String key: combatEffects.keySet())
        {
            CombatEffect effect = combatEffects.get(key);
            if(effect == e)
            {
                this.removeCombatEffect(key);
                return;
            }
        }
        
    }
    
    public void removeCombatEffect(String key)
    {
        CombatEffect e = combatEffects.remove(key);
        if(e != null)
           e.onRemove();
    }
        
    public boolean containsEffect(String effectName)
    {
        for (CombatEffect e : combatEffects.values()) 
        {
            if (e.getName() != null && e.getName().equals(effectName))           
               return true;               
        }
        
        return false;
    } 
    
    public Collection<CombatEffect> getCombatEffects()
    {
        return this.combatEffects.values();
    }
    
    /**
     * Removes every effect from the list.  Used for things like death or dispose
     * or changing scenes.
     */
    public void removeAllCombatEffects() 
    {
        for (CombatEffect effect: combatEffects.values())
            effect.onRemove();
        
        combatEffects.clear(); //Need for some reason?
    }    
    
    /**
     * Fully heals this entity
     */
    public void fullHeal()
    {
        this.currentHealth = this.maxHealth.getTotalValue();      
    }
    
    public void setOwner(CombatEntity owner)
    {
        this.self = owner;
    }  
    
    
    public void setState(CombatState state)
    {
        this.combatStates.put(state, true);
    }
    
    public void removeState(CombatState state)
    {
        if(this.getState(state))
            this.combatStates.put(state, false);
    }
    
    public boolean getState(CombatState state)
    {
        return this.combatStates.get(state);
    }   
    
    
    //=================
    // Query Methods
    //=================
    
    /**
     * AI check as to whether I can currently move.
     * Refactor once combat is done.
     * @return 
     */
    public boolean canMove()
    {
        return (!this.getState(CombatState.IMMOBILIZE) && !this.getState(CombatState.STUN));
    }
    
    /**
     * Check to see whether I can act. 
     * @return 
     */
    public boolean canAttack()
    {
        return (!this.getState(CombatState.DEAD) && !this.getState(CombatState.STUN)&& !this.getState(CombatState.ATTACKING)); 
    }
    
    public boolean isDead()
    {
        return this.combatStates.get(CombatState.DEAD);
    }
    
    public float percentHealth() {
        return  currentHealth /  this.maxHealth.getTotalValue();
    }
    
         
    //====================
    // RenderData Methods
    //==================== 
    
    public RenderData dumpRenderData()
    {
        
        RenderData renderData = new RenderData();
        renderData.data.add(0,new Stat(maxHealth));
        renderData.data.add(1,currentHealth);
        renderData.data.add(2,new Stat(this.baseDamage));
        renderData.data.add(3,new Stat(this.critChance));
        renderData.data.add(4,new Stat(this.critModifier));
        renderData.data.add(5,new Stat(this.lifeLeech));
        renderData.data.add(6,new Stat(this.cooldownModifier));
        renderData.data.add(7,this.combatStates.get(CombatState.IMMOBILIZE));
        renderData.data.add(8,this.combatStates.get(CombatState.STUN));
        renderData.data.add(9,this.combatStates.get(CombatState.DEAD));
        renderData.data.add(10,new Stat(this.xVelocity));
        renderData.data.add(11,new Stat(this.yVelocity));
        renderData.data.add(12,new Stat(this.damageResistance));
        renderData.data.add(13,new Stat(this.ccResistance));
        renderData.data.add(14,new Stat(this.healingModifier));
        
        //combat effects
         ArrayList<SerializableEntry> combatEffectData = new ArrayList();
         for(String key: this.combatEffects.keySet()){
             combatEffectData.add(new SerializableEntry(key,this.combatEffects.get(key).dumpRenderData()));
         }
         renderData.data.add(15,combatEffectData);

        
        return renderData;     
    }
    
    public SceneObjectRenderDataChanges generateRenderDataChanges(RenderData oldData,RenderData newData)
    {
        SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();

        int changeMap = 0;
        ArrayList changeList = new ArrayList();

        for(int i = 0; i <= 14; i++)
        {

            if(!oldData.data.get(i).equals( newData.data.get(i)))
            {                 
                changeList.add(newData.data.get(i));
                changeMap += 1L << i;
            }
        }
        
        //====================================
        //generate changes for Combat Effects 
        //====================================
        
        ArrayList<SerializableEntry<String,SceneObjectRenderData>> combatAdds = new ArrayList();
        ArrayList<String> combatRemoves = new ArrayList();
        ArrayList<SerializableEntry> oldEffectData = (ArrayList)oldData.data.get(15);
        ArrayList<SerializableEntry> newEffectData = (ArrayList)newData.data.get(15);
        
        HashMap<String, SceneObjectRenderData> oldEffectMap = new HashMap();
        for (SerializableEntry<String, SceneObjectRenderData> entry : oldEffectData)
            oldEffectMap.put(entry.getKey(), entry.getValue());
        
        HashMap<String, SceneObjectRenderData> newEffectMap = new HashMap();
        for (SerializableEntry<String, SceneObjectRenderData> entry : newEffectData)
            newEffectMap.put(entry.getKey(), entry.getValue());
        
        
        //check for additions
        for (String newKey: newEffectMap.keySet())
        {
            if(!oldEffectMap.containsKey(newKey))
                combatAdds.add(new SerializableEntry(newKey,(RenderData)newEffectMap.get(newKey)));
        }
        
        //check for subtractions
        for(String oldKey: oldEffectMap.keySet())
        {
            if(!newEffectMap.containsKey(oldKey))
                combatRemoves.add(oldKey);
        }
        
         if(!combatAdds.isEmpty())
        {
            changeList.add(combatAdds);
            changeMap += 1L << 15;
        }
        if(!combatRemoves.isEmpty())
        {
            changeList.add(combatRemoves);
            changeMap += 1L << 16;
        }
        
        
            
        changes.fields = changeMap;
        changes.data = changeList.toArray();
        
        if(changeList.size() > 0)
            return changes;
        else
            return null;
    }
    
    public void reconcileRenderDataChanges(long lastTime,long futureTime,SceneObjectRenderDataChanges renderDataChanges )
    {
        int fieldMap = renderDataChanges.fields;
        ArrayList rawData = new ArrayList();
        rawData.addAll(Arrays.asList(renderDataChanges.data)); 
        
        //construct an arraylist of data that we got, nulls will go where we didnt get any data
        ArrayList changeData = new ArrayList();
        for(byte i = 0; i <=16; i ++)
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
            this.maxHealth = (Stat)changeData.get(0);   
        if(changeData.get(1) != null)
            this.currentHealth = (float)changeData.get(1);       
        if(changeData.get(2) != null)
            this.baseDamage= (Stat)changeData.get(2);
        if(changeData.get(3) != null)
            this.critChance= (Stat)changeData.get(3);
        if(changeData.get(4) != null)
            this.critModifier= (Stat)changeData.get(4);
        if(changeData.get(5) != null)
            this.lifeLeech = (Stat)changeData.get(5);
        if(changeData.get(6) != null)
            this.cooldownModifier = (Stat)changeData.get(6);
        if(changeData.get(7) != null)
            this.combatStates.put(CombatState.IMMOBILIZE, (boolean)changeData.get(7));       
        if(changeData.get(8) != null)
            this.combatStates.put(CombatState.STUN, (boolean)changeData.get(8));
        if(changeData.get(9) != null)
            this.combatStates.put(CombatState.DEAD, (boolean)changeData.get(9));
        if(changeData.get(10) != null)
            this.xVelocity= (Stat)changeData.get(10);
        if(changeData.get(11) != null)
            this.yVelocity= (Stat)changeData.get(11);
        if(changeData.get(12) != null)
            this.damageResistance = (Stat)changeData.get(12);
        if(changeData.get(13) != null)
            this.ccResistance = (Stat)changeData.get(13);
        if(changeData.get(14) != null)
            this.healingModifier = (Stat)changeData.get(14);

        
        //added renderEffects
        if(changeData.get(15) != null)
        {
             ArrayList<SerializableEntry<String, SceneObjectRenderData>> renderAdds = (ArrayList)changeData.get(15);
            for (SerializableEntry<String, SceneObjectRenderData> entry : renderAdds)
            {
                CombatEffect effect;
                String key = entry.getKey();
                
                effect = CombatEffect.buildFromRenderData(entry.getValue());
                
                this.combatEffects.put(key, effect);
                effect.owningEntity = this.self;
                              
            }           
        }
        
        //removed renderEffects
        if(changeData.get(16) != null)
        {
            ArrayList<String> removeList = (ArrayList<String>)changeData.get(16);
            
            for(String key: removeList)
                this.combatEffects.remove(key);
        }


    }
    
    //==================
    // Saving Methods
    //==================
    
     public SaveData dumpFullData() 
     {
         
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
         
        SaveData saveData = new SaveData();
        
        //remove all combat effects
        this.removeAllCombatEffects();
        
        saveData.dataMap.put("weaponDamage",baseDamage.dumpFullData());
        saveData.dataMap.put("maxHealth",maxHealth.dumpFullData());
        saveData.dataMap.put("resourceRegen",cooldownModifier.dumpFullData());
        saveData.dataMap.put("critChance",critChance.dumpFullData());
        saveData.dataMap.put("critModifier",critModifier.dumpFullData());
        saveData.dataMap.put("dropChance",dropChance.name());
        saveData.dataMap.put("dropQuality",dropQuality.name());
        saveData.dataMap.put("damageResistance",damageResistance.dumpFullData());
        saveData.dataMap.put("ccResistance",ccResistance.dumpFullData());
        saveData.dataMap.put("xVelocity",xVelocity.dumpFullData());
        saveData.dataMap.put("yVelocity",yVelocity.dumpFullData());
        saveData.dataMap.put("lifeLeech",this.lifeLeech.dumpFullData());
        saveData.dataMap.put("healing",this.healingModifier.dumpFullData());
        
        return saveData;
     }
     
     public static CombatData buildFromFullData(SaveData saved) 
     {
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
         
         CombatData combatData = new CombatData(null);
                 
         combatData.baseDamage = Stat.buildFromFullData((SaveData)saved.dataMap.get("weaponDamage"));
         combatData.maxHealth = Stat.buildFromFullData((SaveData)saved.dataMap.get("maxHealth"));
         combatData.currentHealth = combatData.maxHealth.getTotalValue();
         combatData.critChance = Stat.buildFromFullData((SaveData)saved.dataMap.get("critChance"));
         combatData.critModifier = Stat.buildFromFullData((SaveData)saved.dataMap.get("critModifier"));
         combatData.dropChance = DropChance.valueOf((String)saved.dataMap.get("dropChance"));      
         combatData.dropQuality = DropQuality.valueOf((String)saved.dataMap.get("dropQuality")); 
         combatData.damageResistance = Stat.buildFromFullData((SaveData)saved.dataMap.get("damageResistance"));
         combatData.ccResistance = Stat.buildFromFullData((SaveData)saved.dataMap.get("ccResistance"));
         combatData.xVelocity = Stat.buildFromFullData((SaveData)saved.dataMap.get("xVelocity"));
         combatData.yVelocity = Stat.buildFromFullData((SaveData)saved.dataMap.get("yVelocity"));
         combatData.lifeLeech = Stat.buildFromFullData((SaveData)saved.dataMap.get("lifeLeech"));
         combatData.healingModifier = Stat.buildFromFullData((SaveData)saved.dataMap.get("healing"));
         
         return combatData;
     }
    
     
}
