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
import com.silvergobletgames.leadcrystal.scenes.GameScene;


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
    public Stat thornsDamage = new Stat(0);
    
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
    public DropGenerator.DropChance dropGoldChance = DropGenerator.DropChance.NONE; //$$ chance
    public DropGenerator.DropQuality dropPotionChance = DropGenerator.DropQuality.VeryPoor; //potion chance
    
     
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
        {
           currentHealth = 0;
           self.die(); 
        }
       
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
        if (effect.canBeApplied() && !combatEffects.containsKey(key)) //cant stack effects of the same name
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
    
    public float getPercentHealth() {
        return  currentHealth /  this.maxHealth.getTotalValue();
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
        saveData.dataMap.put("dropChance",dropGoldChance.name());
        saveData.dataMap.put("dropQuality",dropPotionChance.name());
        saveData.dataMap.put("damageResistance",damageResistance.dumpFullData());
        saveData.dataMap.put("ccResistance",ccResistance.dumpFullData());
        saveData.dataMap.put("xVelocity",xVelocity.dumpFullData());
        saveData.dataMap.put("yVelocity",yVelocity.dumpFullData());
        saveData.dataMap.put("lifeLeech",this.lifeLeech.dumpFullData());
        saveData.dataMap.put("healing",this.healingModifier.dumpFullData());
        saveData.dataMap.put("thorns", this.thornsDamage.dumpFullData());
        
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
         combatData.dropGoldChance = DropChance.valueOf((String)saved.dataMap.get("dropChance"));      
         combatData.dropPotionChance = DropQuality.valueOf((String)saved.dataMap.get("dropQuality")); 
         combatData.damageResistance = Stat.buildFromFullData((SaveData)saved.dataMap.get("damageResistance"));
         combatData.ccResistance = Stat.buildFromFullData((SaveData)saved.dataMap.get("ccResistance"));
         combatData.xVelocity = Stat.buildFromFullData((SaveData)saved.dataMap.get("xVelocity"));
         combatData.yVelocity = Stat.buildFromFullData((SaveData)saved.dataMap.get("yVelocity"));
         combatData.lifeLeech = Stat.buildFromFullData((SaveData)saved.dataMap.get("lifeLeech"));
         combatData.healingModifier = Stat.buildFromFullData((SaveData)saved.dataMap.get("healing"));
         
         if((SaveData)saved.dataMap.get("thorns") != null)
             combatData.thornsDamage = Stat.buildFromFullData((SaveData)saved.dataMap.get("thorns")); 
         
         return combatData;
     }
    
     
}
