package com.silvergobletgames.leadcrystal.items;

import com.silvergobletgames.leadcrystal.combat.*;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorStat.ArmorStatID;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.netcode.SaveData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderDataChanges;
import com.silvergobletgames.sylver.util.SerializableEntry;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author mike
 */
public class ArmorManager {

    //player reference
    private PlayerEntity playerReference;
    
    public HashMap<ArmorStatID,ArmorStat> armorStats = new HashMap<>();
    
    
    //weapon
    public ArmorStat meleeAttackDamageBonus;
    public ArmorStat potionCooldownReset;
    public ArmorStat criticalHitDamage;
    public ArmorStat weaponDamage;
    public ArmorStat weaponAttackSpeed;
    public ArmorStat critChance;
    
    //helm
    public ArmorStat seeEnemyHealth;
    public ArmorStat doubleGoldFind;
    public ArmorStat upgradeRadar;
    public ArmorStat lifeLeech;
    public ArmorStat healingEffectiveness;
    
    //body
    public ArmorStat hardToKill;
    public ArmorStat reducedCriticalHit;
    public ArmorStat proximityDamageReduction;
    public ArmorStat health;
    public ArmorStat numberOfPotions;
    public ArmorStat thornsDamage;
    
    
    //boots
    public ArmorStat doubleJump;
    public ArmorStat jetpack;
    public ArmorStat ccReduction;
    public ArmorStat moveSpeed;
    

    
    
    //==============
    // Constructor
    //==============
    public ArmorManager()
    {
        
        //=================
        // Weapon
        //=================

        this.weaponDamage = new ArmorStat(ArmorStatID.WEAPON_DAMAGE, new Image("damageIcon.png"), "Tempered Bullets", 50,5);
        this.weaponDamage.description = "+5% damage per point.";
        this.weaponDamage.unlocked = true;
        this.weaponDamage.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().baseDamage.adjustPercentModifier(.02f);}}); 
        this.armorStats.put(this.weaponDamage.id,this.weaponDamage);
        
        this.weaponAttackSpeed = new ArmorStat(ArmorStatID.WEAPON_ATTACK_SPEED, new Image("attackSpeedIcon.png"), "Recoil Dampener", 50,5);
        this.weaponAttackSpeed.description = "+5% attack speed per point.";
        this.weaponAttackSpeed.unlocked = true;
        this.weaponAttackSpeed.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().cooldownModifier.adjustBase(-.05f);}}); 
        this.armorStats.put(this.weaponAttackSpeed.id,this.weaponAttackSpeed);
        
        this.critChance = new ArmorStat(ArmorStatID.CRIT_CHANCE, new Image("critChanceIcon.png"), "Laser Targeting", 50,3);
        this.critChance.description = "+10% critical hit chance per point.";
        this.critChance.unlocked = true;
        this.critChance.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().critChance.adjustBase(.1f);}}); 
        this.armorStats.put(this.critChance.id,this.critChance);
        
        
        this.meleeAttackDamageBonus = new ArmorStat(ArmorStatID.MELEE_DAMAGE, new Image("meleeDamageIcon.png"), "Brass Knuckles",100,2);
        this.meleeAttackDamageBonus.description = "+25% melee damage per point.";
        this.meleeAttackDamageBonus.unlocked = false;
        this.armorStats.put(this.meleeAttackDamageBonus.id,this.meleeAttackDamageBonus);
        
        this.lifeLeech = new ArmorStat(ArmorStatID.LIFE_LEECH, new Image("lifeLeechIcon.png"), "Leeching Ammo", 150,1);
        this.lifeLeech.description = "+1 life on hit per attack.";
        this.lifeLeech.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().lifeLeech.adjustBase(1f);}}); 
        this.lifeLeech.unlocked = false;
        this.armorStats.put(this.lifeLeech.id,this.lifeLeech); 
       
           
        this.criticalHitDamage = new ArmorStat(ArmorStatID.CRIT_DAMAGE, new Image("critDamageIcon.png"), "Explosive Bullets",100,2);
        this.criticalHitDamage.description = "+50% critical hit damage per point.";
        this.criticalHitDamage.unlocked = false;
        this.criticalHitDamage.setAddPointAction(new ArmorAction(){
           public void doAction()
           {
               getPlayerReference().getCombatData().critModifier.adjustBase(.5f); 
           }
        });
        this.armorStats.put(this.criticalHitDamage.id,this.criticalHitDamage);
              
        
        //===============
        // Helm 
        //===============
          
        this.seeEnemyHealth = new ArmorStat(ArmorStatID.SEE_HEALTH, new Image("seeEnemyHealthIcon.png"), "Retinal Implant",75,1);
        this.seeEnemyHealth.description = "Shows health bars above enemies.";
        this.seeEnemyHealth.unlocked = true;
        this.armorStats.put(this.seeEnemyHealth.id,this.seeEnemyHealth);
       
        this.doubleGoldFind = new ArmorStat(ArmorStatID.DOUBLE_GOLD, new Image("goldFindIcon.png"), "Gold Find",75,1);
        this.doubleGoldFind.description = "Doubles the value of each gold picked up.";
        this.doubleGoldFind.unlocked= false;
        this.armorStats.put(this.doubleGoldFind.id,this.doubleGoldFind);
       
        this.upgradeRadar = new ArmorStat(ArmorStatID.IMPROVED_RADAR, new Image("radarUpgradeIcon.png"), "Improved Radar",75,1);
        this.upgradeRadar.description = "Shows the position of enemies on the radar.";
        this.upgradeRadar.unlocked = true;
        this.armorStats.put(this.upgradeRadar.id,this.upgradeRadar);  
 
        this.potionCooldownReset = new ArmorStat(ArmorStatID.POTION_COOLDOWN_RESET, new Image("steroidPotionIcon.png"), "Steroid Potions",150,1);
        this.potionCooldownReset.description = "Using a potion will instantly reset all cooldowns.";
        this.potionCooldownReset.unlocked = true;
        this.armorStats.put(this.potionCooldownReset.id,this.potionCooldownReset);
        
        this.healingEffectiveness = new ArmorStat(ArmorStatID.HEALING_EFFECTIVENESS, new Image("healingEffectivenessIcon.png"), "Improved Potions", 150,1);
        this.healingEffectiveness.description = "Doubles the healing amount of potions.";
        this.healingEffectiveness.unlocked= false;
        this.armorStats.put(this.healingEffectiveness.id,this.healingEffectiveness);
       
        
        //================
        // Body
        //================
        this.hardToKill = new ArmorStat(ArmorStatID.HARD_TO_KILL, new Image("specialArmorIcon.png"), "Hard to Kill",150,1);
        this.hardToKill.description = "When below 33% health all incoming damage is reduced by 50%.";
        this.hardToKill.unlocked = false;
        this.armorStats.put(this.hardToKill.id,this.hardToKill);
        
        this.reducedCriticalHit = new ArmorStat(ArmorStatID.REDUCED_CRIT, new Image("hardenedArmorIcon.png"), "Hardened Armor",150,1);
        this.reducedCriticalHit.description ="Enemies can no longer critical hit you.";
        this.reducedCriticalHit.unlocked = true;
        this.armorStats.put(this.reducedCriticalHit.id,this.reducedCriticalHit);
       
        this.proximityDamageReduction = new ArmorStat(ArmorStatID.PROXIMITY_DAMAGE_REDUCTION, new Image("superArmorIcon.png"), "Proximity Shield",100,2);
        this.proximityDamageReduction.description = "Incoming damage is reduced by 15% when at close range.";
        this.proximityDamageReduction.unlocked = false;
        this.armorStats.put(this.proximityDamageReduction.id,this.proximityDamageReduction);

        
        this.health = new ArmorStat(ArmorStatID.HEALTH, new Image("healthIcon.png"), "Health", 50,10);
        this.health.description = "+10 health per point.";
        this.health.unlocked = true;
        this.health.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().maxHealth.adjustBase(10); 
                                                   getPlayerReference().getCombatData().currentHealth+=10;
                                                   }}); 
        this.armorStats.put(this.health.id,this.health);

        this.thornsDamage = new ArmorStat(ArmorStatID.THORNS_DAMAGE, new Image("thornsIcon.png"), "Thorns Armor", 50,3);
        this.thornsDamage.description = "+25% thorns damage per point.";
        this.thornsDamage.unlocked = false;
        this.thornsDamage.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().thornsDamage.adjustBase(.25f);}}); 
        this.armorStats.put(this.thornsDamage.id,this.thornsDamage); 
        
        this.numberOfPotions = new ArmorStat(ArmorStatID.NUMBER_POTIONS, new Image("numberOfPotionsIcon.png"), "Potion Slots", 50,5);
        this.numberOfPotions.description = "Can carry +1 additional potion per point.";
        this.numberOfPotions.unlocked = true;
        this.numberOfPotions.setAddPointAction(new ArmorAction(){ public void doAction(){playerReference.getPotionManager().increaseMaxPotions(1);}}); 
        this.armorStats.put(this.numberOfPotions.id,this.numberOfPotions);
      
        
       
        
        
        //================
        // Boots
        //================
        
        this.doubleJump = new ArmorStat(ArmorStatID.DOUBLE_JUMP, new Image("doubleJumpIcon.png"), "Double Jump Boots",75,1);
        this.doubleJump.description = "At the peak of a jump, press jump again to get an additional boost.";
        this.doubleJump.unlocked = true;
        this.armorStats.put(this.doubleJump.id,this.doubleJump);
       
        this.jetpack = new ArmorStat(ArmorStatID.JETPACK, new Image("rocketBootsIcon.png"), "Jetpack Boots",150,1);
        this.jetpack.description = "At the peak of a jump, press jump again to hover briefly.";
        this.jetpack.unlocked = false;
        this.armorStats.put(this.jetpack.id,this.jetpack);   
                      
        this.ccReduction = new ArmorStat(ArmorStat.ArmorStatID.CC_REDUCTION, new Image("ccReductionIcon.png"), "Traction Spikes", 50,3);
        this.ccReduction.description = "Reduces slow and stun effects by 20% per point.";
        this.ccReduction.unlocked = true;
        this.ccReduction.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().ccResistance.adjustBase(.05f);}}); 
        this.armorStats.put(this.ccReduction.id,this.ccReduction);
        
        this.moveSpeed = new ArmorStat(ArmorStat.ArmorStatID.MOVE_SPEED, new Image("moveSPeedIcon.png"), "Swiftness", 50,5);
        this.moveSpeed.description = "+5% move speed per point.";
        this.moveSpeed.unlocked = false;
        this.moveSpeed.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().xVelocity.adjustPercentModifier(.05f);}}); 
        this.armorStats.put(this.moveSpeed.id,this.moveSpeed);
        
    }
    
    
    //==========
    // Methods
    //==========
   
    
    public void setPlayerReference(PlayerEntity player)
    {
        this.playerReference = player;
        
        
        //go through each stat and add the points
        for(ArmorStat stat: this.armorStats.values())
        {
            
            for( int i = 0; i < stat.points; i++)
            {
                if(stat.addPointAction != null)
                {
                   stat.addPointAction.doAction();
                }
            }
        }
        
        
    }
    
    public PlayerEntity getPlayerReference()
    {            
        return this.playerReference;  
    }

    public ArmorStat armorStatLookup(ArmorStatID id)
    {
        switch(id)
        {
           case SEE_HEALTH: return this.seeEnemyHealth;
           case DOUBLE_GOLD: return this.doubleGoldFind;
           case IMPROVED_RADAR: return this.upgradeRadar;
           case LIFE_LEECH: return this.lifeLeech;
           case HEALING_EFFECTIVENESS: return this.healingEffectiveness;

           case HARD_TO_KILL: return this.hardToKill;
           case REDUCED_CRIT: return this.reducedCriticalHit;
           case PROXIMITY_DAMAGE_REDUCTION: return this.proximityDamageReduction;
           case HEALTH: return this.health;
           case THORNS_DAMAGE: return this.thornsDamage;
           case NUMBER_POTIONS: return this.numberOfPotions;
       
           case MELEE_DAMAGE: return this.meleeAttackDamageBonus;
           case POTION_COOLDOWN_RESET: return this.potionCooldownReset;
           case CRIT_DAMAGE: return this.criticalHitDamage;
           case WEAPON_DAMAGE: return this.weaponDamage;
           case WEAPON_ATTACK_SPEED: return this.weaponAttackSpeed;
           case CRIT_CHANCE: return this.critChance;
       
           case DOUBLE_JUMP: return this.doubleJump;
           case JETPACK: return this.jetpack;
           case CC_REDUCTION: return this.ccReduction;
           case MOVE_SPEED: return this.moveSpeed;
               
           default: return null;
        }
    }
  
        
    //=====================
    // Inner Classes
    //=====================
    
    public static abstract class ArmorAction
    {
            public abstract void doAction();
        }
    
    public static class ArmorStat
    {
        public ArmorStatID id;
        public Image image;
        public String name;
        public String description;
        public int cost;
        public byte points;
        public int maxPoints;
        protected ArmorAction addPointAction;
        public boolean unlocked;
        
        
        public static enum ArmorStatID
        {
            //helm
            SEE_HEALTH,DOUBLE_GOLD, IMPROVED_RADAR,LIFE_LEECH,HEALING_EFFECTIVENESS,
            //body
            HARD_TO_KILL, REDUCED_CRIT, PROXIMITY_DAMAGE_REDUCTION,HEALTH,THORNS_DAMAGE, NUMBER_POTIONS,
            //weapon
            MELEE_DAMAGE, POTION_COOLDOWN_RESET,CRIT_DAMAGE,WEAPON_DAMAGE, WEAPON_ATTACK_SPEED,CRIT_CHANCE,
            //boots
            DOUBLE_JUMP,JETPACK, CC_REDUCTION,MOVE_SPEED
            
        }
        
        //=============
        // Constructor
        //=============
        
        public ArmorStat(ArmorStatID id, Image image, String name, int cost,int maxPoints)            
        {
            this.id = id;
            this.image = image;
            this.name = name;
            this.cost = cost;
            
            this.points = 0;
            this.maxPoints = maxPoints;
            this.description = ""; 
            this.unlocked = false;
        }
        
        
        //=============
        // Methods
        //=============
        
        public void setAddPointAction(ArmorAction action)
        {
            this.addPointAction = action;
        }
        
        public void addPoint(int points)
        {
            for( int i = 0; i < points; i++)
            {
                if(this.addPointAction != null)
                {
                   this.addPointAction.doAction();
                }
            }
            
            this.points += points;
        }
        
        public boolean isMaxPoints()
        {
            return this.points == this.maxPoints;
        }
        
        
        //=======================
        // Seralization Methods
        //=======================
        
        public RenderData dumpRenderData()
        {
            RenderData renderData = new RenderData();
            renderData.data.add(0,this.points);
            renderData.data.add(1,this.unlocked);
            


            return renderData;        
        }

        public SceneObjectRenderDataChanges generateRenderDataChanges(RenderData oldData,RenderData newData)
        {
            SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();

            int changeMap = 0;
            ArrayList changeList = new ArrayList();

            for(int i = 0; i <2; i++)
            {                  
                if( !oldData.data.get(i).equals( newData.data.get(i)))
                {                 
                    changeList.add(newData.data.get(i));
                    changeMap += 1L << i;
                }
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
            for(byte i = 0; i <2; i ++)
            {
                // The bit was set
                if ((fieldMap & (1L << i)) != 0)
                {
                    changeData.add(rawData.get(0));
                    rawData.remove(0);
                }
                else
                {
                    changeData.add(null); 
                }
            }
            
            if(changeData.get(0) != null)
            {
                this.points = (byte)changeData.get(0);
            }
            if(changeData.get(1) != null)
            {
                this.unlocked = (boolean)changeData.get(1);
            }

     }
    }
    
 

    
    //====================
    // RenderData Methods
    //====================
     public RenderData dumpRenderData()
     {
         RenderData renderData = new RenderData();

         renderData.data.add(0,numberOfPotions.dumpRenderData());
         renderData.data.add(1,ccReduction.dumpRenderData());
         renderData.data.add(2,healingEffectiveness.dumpRenderData());
         renderData.data.add(3,health.dumpRenderData());
         renderData.data.add(4,lifeLeech.dumpRenderData());
         renderData.data.add(5,thornsDamage.dumpRenderData());        
         renderData.data.add(6,weaponDamage.dumpRenderData());
         renderData.data.add(7,weaponAttackSpeed.dumpRenderData());
         renderData.data.add(8,critChance.dumpRenderData());
         renderData.data.add(9,moveSpeed.dumpRenderData());         
         renderData.data.add(10,seeEnemyHealth.dumpRenderData()); 
         renderData.data.add(11,doubleGoldFind.dumpRenderData()); 
         renderData.data.add(12,upgradeRadar.dumpRenderData());         
         renderData.data.add(13,hardToKill.dumpRenderData()); 
         renderData.data.add(14,reducedCriticalHit.dumpRenderData()); 
         renderData.data.add(15,proximityDamageReduction.dumpRenderData()); 
         renderData.data.add(16,meleeAttackDamageBonus.dumpRenderData()); 
         renderData.data.add(17,potionCooldownReset.dumpRenderData()); 
         renderData.data.add(18,criticalHitDamage.dumpRenderData());        
         renderData.data.add(19,doubleJump.dumpRenderData()); 
         renderData.data.add(20,jetpack.dumpRenderData()); 

         return renderData;        
     }
     
     public SceneObjectRenderDataChanges generateRenderDataChanges(RenderData oldData,RenderData newData)
     {
         SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();
        
         int changeMap = 0;
         ArrayList changeList = new ArrayList();
         
         ArmorStat dummyStat = this.armorStats.get(ArmorStatID.CC_REDUCTION);
         
        for(int i=0; i<=20; i++)
        {//0
            SceneObjectRenderDataChanges renderChanges = dummyStat.generateRenderDataChanges((RenderData)oldData.data.get(i), (RenderData)newData.data.get(i));
            if(renderChanges != null)
            {
                changeList.add(renderChanges);
                changeMap += 1L <<i;
            }
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
            for(byte i = 0; i <32; i ++)
            {
                // The bit was set
                if ((fieldMap & (1L << i)) != 0)
                {
                    changeData.add(rawData.get(0));
                    rawData.remove(0);
                }
                else
                {
                    changeData.add(null); 
                }
            }
            
            if(changeData.get(0) != null)
            {
                this.numberOfPotions.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(0));
            }
            if(changeData.get(1) != null)
            {
                this.ccReduction.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(1));
            }
            if(changeData.get(2) != null)
            {
                this.healingEffectiveness.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(2));
            }
            if(changeData.get(3) != null)
            {
                this.health.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(3));
            }
            if(changeData.get(4) != null)
            {
                this.lifeLeech.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(4));
            }
            if(changeData.get(5) != null)
            {
                this.thornsDamage.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(5));
            }          
            if(changeData.get(6) != null)
            {
                this.weaponDamage.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(6));
            }
            if(changeData.get(7) != null)
            {
                this.weaponAttackSpeed.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(7));
            }
            if(changeData.get(8) != null)
            {
                this.critChance.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(8));
            }
            if(changeData.get(9) != null)
            {
                this.moveSpeed.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(9));
            }
            if(changeData.get(10) != null)
            {
                this.seeEnemyHealth.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(10));
            }
            if(changeData.get(11) != null)
            {
                this.doubleGoldFind.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(11));
            }
            if(changeData.get(12) != null)
            {
                this.upgradeRadar.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(12));
            }
            
            if(changeData.get(13) != null)
            {
                this.hardToKill.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(13));
            }
            if(changeData.get(14) != null)
            {
                this.reducedCriticalHit.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(14));
            }
            if(changeData.get(15) != null)
            {
                this.proximityDamageReduction.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(15));
            }
            if(changeData.get(16) != null)
            {
                this.meleeAttackDamageBonus.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(16));
            }
            if(changeData.get(17) != null)
            {
                this.potionCooldownReset.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(17));
            }
            if(changeData.get(18) != null)
            {
                this.criticalHitDamage.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(18));
            }
            
            if(changeData.get(19) != null)
            {
                this.doubleJump.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(19));
            }
            if(changeData.get(20) != null)
            {
                this.jetpack.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(20));
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
       
       //save all stat data
       for(Entry<ArmorStatID,ArmorStat> entry: this.armorStats.entrySet())
       {          
            saveData.dataMap.put(entry.getKey().name(), new SerializableEntry(entry.getValue().unlocked,entry.getValue().points));
       }
      
        return saveData;
   }
   
   public static ArmorManager buildFromFullData(SaveData saveData)  
   {
       //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
       
       ArmorManager armorManager = new ArmorManager();
       
       //go through all stats setting points
       for(Entry<ArmorStatID,ArmorStat> entry: armorManager.armorStats.entrySet())
       {       
           SerializableEntry savedEntry = (SerializableEntry)saveData.dataMap.get(((ArmorStatID)entry.getKey()).name()); 
           byte points = (byte)savedEntry.getValue();
           boolean unlocked = (boolean)savedEntry.getKey();
           entry.getValue().points = points;
           entry.getValue().unlocked = unlocked;

       }     
          
       return armorManager;
       
   }
}
