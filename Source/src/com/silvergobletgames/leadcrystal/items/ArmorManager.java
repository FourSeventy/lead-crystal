

package com.silvergobletgames.leadcrystal.items;

import com.silvergobletgames.leadcrystal.combat.*;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorModifier.ArmorModifierID;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorStat.ArmorStatID;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.netcode.SaveData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderDataChanges;
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
    public HashMap<ArmorModifierID, ArmorModifier> armorModifiers = new HashMap<>();
    
    
    //helm
    public ArmorModifier seeEnemyHealthModifier;
    public ArmorModifier doubleGoldFindModifier;
    public ArmorModifier upgradedRadarModifier;
    public ArmorStat lifeLeech;
    public ArmorStat numberOfPotionsStat;
    public ArmorStat healingEffectivenessStat;
    
    //body
    public ArmorModifier damageReductionBonusModifier;
    public ArmorModifier reducedCriticalHitModifier;
    public ArmorModifier bonusThornsDamageModifier;
    public ArmorStat healthStat;
    public ArmorStat damageReductionStat;
    public ArmorStat thornsDamage;
    
       
    //weapon
    public ArmorModifier meleeAttackDamageModifier;
    public ArmorModifier potionCooldownResetModifier;
    public ArmorModifier criticalHitDamageModifier;
    public ArmorStat weaponDamageStat;
    public ArmorStat weaponAttackSpeedStat;
    public ArmorStat critChanceStat;
    
    //boots
    public ArmorModifier doubleJumpModifier;
    public ArmorModifier jetpackModifier;
    public ArmorModifier teleportModifier;
    public ArmorStat ccReductionStat;
    public ArmorStat moveSpeedStat;
    public ArmorStat jumpHeightStat;
    

    
    
    //==============
    // Constructor
    //==============
    public ArmorManager()
    {
        //================
        // Helm Modifiers
        //================
        this.seeEnemyHealthModifier = new ArmorModifier(ArmorModifierID.ENEMY_HEALTH, new Image("enemyHealthModifier.jpg"), "See Enemy Health");
        this.seeEnemyHealthModifier.description = "Gives the ability to see enemy health bars.";
        this.armorModifiers.put(this.seeEnemyHealthModifier.id,this.seeEnemyHealthModifier);
       
        this.doubleGoldFindModifier = new ArmorModifier(ArmorModifierID.GOLD_FIND, new Image("doubleGoldFindModifier.jpg"), "Double Gold Find");
        this.doubleGoldFindModifier.description = "Doubles the value of each gold you pick up.";
        this.armorModifiers.put(this.doubleGoldFindModifier.id,this.doubleGoldFindModifier);
       
        this.upgradedRadarModifier = new ArmorModifier(ArmorModifierID.UPGRADED_RADAR, new Image("seeSecondaryObjectiveModifier.jpg"), "Improved radar");
        this.upgradedRadarModifier.description = "Allows secondary objectives to show up on your radar.";
        this.armorModifiers.put(this.upgradedRadarModifier.id,this.upgradedRadarModifier);

       
        //===============
        // Helm Stats
        //===============
          
        this.lifeLeech = new ArmorStat(ArmorStat.ArmorStatID.LIFE_LEECH, new Image("lifeLeechStat.jpg"), "Life Leech", 50);
        this.lifeLeech.description = "+5% life leech per point.";
        this.lifeLeech.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().lifeLeech.adjustBase(.05f);}}); 
        this.armorStats.put(this.lifeLeech.id,this.lifeLeech);
        
        this.numberOfPotionsStat = new ArmorStat(ArmorStat.ArmorStatID.NUMBER_POTIONS, new Image("armorStat.jpg"), "Number of Potions", 50);
        this.numberOfPotionsStat.description = "+1 additional potion per point.";
        this.numberOfPotionsStat.setAddPointAction(new ArmorAction(){ public void doAction(){playerReference.getPotionManager().increaseMaxPotions(1);}}); 
        this.armorStats.put(this.numberOfPotionsStat.id,this.numberOfPotionsStat);
        
        this.healingEffectivenessStat = new ArmorStat(ArmorStat.ArmorStatID.HEALING_EFFECTIVENESS, new Image("healingStat.jpg"), "Healing Effectiveness", 50);
        this.healingEffectivenessStat.description = "+5% healing effectiveness per point.";
        this.healingEffectivenessStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().healingModifier.adjustBase(.05f);}}); 
        this.armorStats.put(this.healingEffectivenessStat.id,this.healingEffectivenessStat);
       
        
        //================
        // Body Modifiers
        //================
        this.damageReductionBonusModifier = new ArmorModifier(ArmorModifierID.DR_BONUS, new Image("ccStat.jpg"), "Hard To Kill");
        this.damageReductionBonusModifier.description = "When below 33% health, DR is incresed +50%";
        this.damageReductionBonusModifier.setUnequipMethod(new ArmorAction(){
           public void doAction()
           {
               getPlayerReference().getCombatData().removeCombatEffect("DRModifier"); 
           }
        });
        this.armorModifiers.put(this.damageReductionBonusModifier.id,this.damageReductionBonusModifier);
        
        this.reducedCriticalHitModifier = new ArmorModifier(ArmorModifierID.NO_CRITS, new Image("healthStat.jpg"), "Hardened Armor");
        this.reducedCriticalHitModifier.description ="Enemies can no longer critical hit you.";
        this.armorModifiers.put(this.reducedCriticalHitModifier.id,this.reducedCriticalHitModifier);
       
        this.bonusThornsDamageModifier = new ArmorModifier(ArmorModifierID.INCREASED_THORNS, new Image("healingStat.jpg"), "Additional Thorns");
        this.bonusThornsDamageModifier.description = "Thorns damage is doubled.";
        this.bonusThornsDamageModifier.setEquipMethod(new ArmorAction(){
           public void doAction()
           {
               getPlayerReference().getCombatData().thornsDamage.setPercentModifier(2f); 
           }
        });
        this.bonusThornsDamageModifier.setUnequipMethod(new ArmorAction(){
           public void doAction()
           {
               getPlayerReference().getCombatData().thornsDamage.setPercentModifier(1f);
           }
        });
        this.armorModifiers.put(this.bonusThornsDamageModifier.id,this.bonusThornsDamageModifier);

        
        
        //===============
        // Body Stats
        //===============
        this.healthStat = new ArmorStat(ArmorStat.ArmorStatID.BODY_HEALTH, new Image("healthStat.jpg"), "Health", 50);
        this.healthStat.description = "+10 health per point.";
        this.healthStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().maxHealth.adjustBase(10); 
                                                   getPlayerReference().getCombatData().currentHealth+=10;
                                                   }}); 
        this.armorStats.put(this.healthStat.id,this.healthStat);
        
        this.damageReductionStat = new ArmorStat(ArmorStat.ArmorStatID.BODY_DAMAGE_REDUCTION, new Image("armorStat.jpg"), "Damage Reduction", 50);
        this.damageReductionStat.description = "+2% damage reduction per point.";
        this.damageReductionStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().damageResistance.adjustBase(.02f);}}); 
        this.armorStats.put(this.damageReductionStat.id,this.damageReductionStat);
        
        this.thornsDamage = new ArmorStat(ArmorStat.ArmorStatID.THORNS_DAMAGE, new Image("lifeRegenStat.jpg"), "Thorns", 50);
        this.thornsDamage.description = "+5% thorns damage per point.";
        this.thornsDamage.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().thornsDamage.adjustBase(.05f);}}); 
        this.armorStats.put(this.thornsDamage.id,this.thornsDamage);
      
        
        //=================
        // Weapon Modifiers
        //=================

        this.meleeAttackDamageModifier = new ArmorModifier(ArmorModifierID.MELEE_ATTACK_DMG, new Image("meleeDamageModifier.jpg"), "Melee Damage Increase");
        this.meleeAttackDamageModifier.description = "Increases the damage of melee attacks by 50%";
        this.armorModifiers.put(this.meleeAttackDamageModifier.id,this.meleeAttackDamageModifier);
       
        this.potionCooldownResetModifier = new ArmorModifier(ArmorModifierID.POTION_RESET, new Image("healthStat.jpg"), "Potion Cooldown Reset");
        this.potionCooldownResetModifier.description = "Using a potion will instantly reset all cooldowns.";
        this.armorModifiers.put(this.potionCooldownResetModifier.id,this.potionCooldownResetModifier);
        
        this.criticalHitDamageModifier = new ArmorModifier(ArmorModifierID.CRIT_DMG, new Image("critDamageStat.jpg"), "Critical Hit Damage");
        this.criticalHitDamageModifier.description = "Critical hit damaged increased by 100%.";
        this.criticalHitDamageModifier.setEquipMethod(new ArmorAction(){
           public void doAction()
           {
               getPlayerReference().getCombatData().critModifier.adjustBase(1f); 
           }
        });
        this.criticalHitDamageModifier.setUnequipMethod(new ArmorAction(){
           public void doAction()
           {
               getPlayerReference().getCombatData().critModifier.adjustBase(-1f); 
           }
        });
        this.armorModifiers.put(this.criticalHitDamageModifier.id,this.criticalHitDamageModifier);
        

        
        //==============
        // Weapon Stats
        //==============
        this.weaponDamageStat = new ArmorStat(ArmorStat.ArmorStatID.WEAPON_DAMAGE, new Image("damageStat.jpg"), "Damage", 50);
        this.weaponDamageStat.description = "+2% damage per point";
        this.weaponDamageStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().baseDamage.adjustPercentModifier(.02f);}}); 
        this.armorStats.put(this.weaponDamageStat.id,this.weaponDamageStat);
        
        this.weaponAttackSpeedStat = new ArmorStat(ArmorStat.ArmorStatID.WEAPON_ATTACK_SPEED, new Image("attackSpeedStat.jpg"), "Attack Speed", 50);
        this.weaponAttackSpeedStat.description = "+5% attack speed per point";
        this.weaponAttackSpeedStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().cooldownModifier.adjustBase(-.05f);}}); 
        this.armorStats.put(this.weaponAttackSpeedStat.id,this.weaponAttackSpeedStat);
        
        this.critChanceStat = new ArmorStat(ArmorStat.ArmorStatID.CRIT_CHANCE, new Image("critChanceStat.jpg"), "Critical Hit Chance", 50);
        this.critChanceStat.description = "+3% crit chance per point";
        this.critChanceStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().critChance.adjustBase(.03f);}}); 
        this.armorStats.put(this.critChanceStat.id,this.critChanceStat);
        
        
        //================
        // Boots Modfiers
        //================
        
        this.doubleJumpModifier = new ArmorModifier(ArmorModifierID.DOUBLE_JUMP, new Image("healthStat.jpg"), "Double Jump");
        this.armorModifiers.put(this.doubleJumpModifier.id,this.doubleJumpModifier);
       
        this.jetpackModifier = new ArmorModifier(ArmorModifierID.JETPACK, new Image("healthStat.jpg"), "Jetpack");
        this.armorModifiers.put(this.jetpackModifier.id,this.jetpackModifier);
        
        this.teleportModifier = new ArmorModifier(ArmorModifierID.TELEPORT, new Image("healthStat.jpg"), "Teleport");
        this.armorModifiers.put(this.teleportModifier.id,this.teleportModifier);
        
        //==============
        // Boots Stats
        //==============
                      
        this.ccReductionStat = new ArmorStat(ArmorStat.ArmorStatID.CC_REDUCTION, new Image("CCStat.jpg"), "CC Reduction", 50);
        this.ccReductionStat.description = "+5% CC reduction per point.";
        this.ccReductionStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().ccResistance.adjustBase(.05f);}}); 
        this.armorStats.put(this.ccReductionStat.id,this.ccReductionStat);
        
        this.moveSpeedStat = new ArmorStat(ArmorStat.ArmorStatID.MOVE_SPEED, new Image("moveSpeedStat.jpg"), "Move Speed", 50);
        this.moveSpeedStat.description = "+5% move speed per point";
        this.moveSpeedStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().xVelocity.adjustPercentModifier(.05f);}}); 
        this.armorStats.put(this.moveSpeedStat.id,this.moveSpeedStat);
        
        this.jumpHeightStat = new ArmorStat(ArmorStat.ArmorStatID.JUMP_HEIGHT, new Image("jumpHeightStat.jpg"), "Jump Height", 50);
        this.jumpHeightStat.description = "2m jump height per point";
        this.jumpHeightStat.setAddPointAction(new ArmorAction(){ public void doAction(){playerReference.setMaxJumpEnergy(playerReference.getMaxJumpEnergy() + 20);}});  
        this.armorStats.put(this.jumpHeightStat.id,this.jumpHeightStat);
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
                stat.addPointAction.doAction();
            }
        }
        
        //go through modifiers and apply equipped ones
        for(ArmorModifier modifier: this.armorModifiers.values())
        {
            if(modifier.equipped == true)
            {
                modifier.equip();
            }
        }
        
    }
    
    public PlayerEntity getPlayerReference()
    {            
        return this.playerReference;  
    }
    
    public void equipModifier(ArmorModifierID id)
    {      
        
        //modifier must be unlocked to equip it
        if(!this.armorModifiers.get(id).unlocked)
        {
            return;
        }
        
        // hardcode groupings of modifiers
        ArrayList<ArmorModifier> helmModifiers =new ArrayList<>();   
        helmModifiers.add(this.seeEnemyHealthModifier);
        helmModifiers.add(this.doubleGoldFindModifier);
        helmModifiers.add(this.upgradedRadarModifier);

        ArrayList<ArmorModifier> bodyModifiers =new ArrayList<>(); 
        bodyModifiers.add(this.damageReductionBonusModifier);
        bodyModifiers.add(this.reducedCriticalHitModifier);
        bodyModifiers.add(this.bonusThornsDamageModifier);

    
        ArrayList<ArmorModifier> weaponModifiers = new ArrayList<>();
        bodyModifiers.add(this.meleeAttackDamageModifier);
        bodyModifiers.add(this.potionCooldownResetModifier);
        bodyModifiers.add(this.criticalHitDamageModifier);

        ArrayList<ArmorModifier> bootsModifiers = new ArrayList<>();
        bootsModifiers.add(this.doubleJumpModifier);
        bootsModifiers.add(this.jetpackModifier);
        bootsModifiers.add(this.teleportModifier);
    
        
        ArrayList<ArmorModifier> correctGrouping;
        
        ArmorModifier parameterMod = this.armorModifiers.get(id);
         //find out which grouping this one is in
        if(helmModifiers.contains(parameterMod))
            correctGrouping = helmModifiers;
        else if(bodyModifiers.contains(parameterMod))
            correctGrouping = bodyModifiers;
        else if(weaponModifiers.contains(parameterMod))
            correctGrouping = weaponModifiers;
        else 
            correctGrouping = bootsModifiers;
       
        //iterate over group
        for(ArmorModifier modifier: correctGrouping)
        {
            //if equipped unequip
            if(modifier.equipped)
                modifier.unequip();
        }
                   
        //equip this modifier
        parameterMod.equip();
    }
    
        
    //=====================
    // Inner Classes
    //=====================
    
    public static abstract class ArmorAction
    {
            public abstract void doAction();
        }
    
    public static class ArmorModifier
    {
        public ArmorModifierID id;
        public Image image;
        public String name;
        public String description;
        public boolean unlocked;
        public boolean equipped;
        private ArmorAction equipAction;
        private ArmorAction unequipAction;
        
        
        
        public static enum ArmorModifierID
        {   
            //helm modifiers
            ENEMY_HEALTH, GOLD_FIND, UPGRADED_RADAR,
            //body modifiers
            DR_BONUS, NO_CRITS, INCREASED_THORNS, 
            //weapon modifiers
            MELEE_ATTACK_DMG,POTION_RESET,CRIT_DMG,
            //boot modifiers
            DOUBLE_JUMP,JETPACK,TELEPORT
        }
        
        
        //=============
        // Constructor
        //=============
        
        /**
         * 
         * @param id
         * @param image
         * @param name
         * @param cost 
         */
        public ArmorModifier(ArmorModifierID id,Image image, String name)
        {
            this.id = id;
            this.image = image;
            this.name = name;
            
            this.description = "";
            this.unlocked= false;
            this.equipped = false;
            
        }
        
        
        //==========
        // Methods
        //==========
       
        public void setEquipMethod(ArmorAction action)
        {
            this.equipAction = action;
        }
        
        public void setUnequipMethod(ArmorAction action)
        {
            this.unequipAction = action;
        }
        
        /**
         * Equips modifier and applies all effects
         */
        public void equip()
        {
          this.equipped = true;
          if(this.equipAction!= null)
              this.equipAction.doAction();
       }
       
        /**
        * Unequips modifier and removes all effects
        */
        public void unequip()
        {
           this.equipped = false;
           if(this.unequipAction != null)
               this.unequipAction.doAction();
       }
        
        //=======================
        // Seralization Methods
        //=======================
        
        public RenderData dumpRenderData()
        {
            RenderData renderData = new RenderData();
            renderData.data.add(0,this.equipped);
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
                this.equipped = (boolean)changeData.get(0);
            }
            if(changeData.get(1) != null)
            {
                this.unlocked = (boolean)changeData.get(1);
            }

     }
        
             
    }
    
    
    public static class ArmorStat
    {
        public ArmorStatID id;
        public Image image;
        public String name;
        public String description;
        public int cost;
        public byte points;
        public static final int MAX_POINTS = 5;
        protected ArmorAction addPointAction;
        
        
        public static enum ArmorStatID
        {
            //helm
            LIFE_LEECH,NUMBER_POTIONS,HEALING_EFFECTIVENESS,
            //body
            BODY_HEALTH,BODY_DAMAGE_REDUCTION,THORNS_DAMAGE,
            //weapon
            WEAPON_DAMAGE, WEAPON_ATTACK_SPEED,CRIT_CHANCE,
            //boots
            CC_REDUCTION,MOVE_SPEED, JUMP_HEIGHT
            
        }
        
        //=============
        // Constructor
        //=============
        
        public ArmorStat(ArmorStatID id, Image image, String name, int cost)            
        {
            this.id = id;
            this.image = image;
            this.name = name;
            this.cost = cost;
            
            this.points = 0;
            this.description = "";         
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
                this.addPointAction.doAction();
            }
            
            this.points += points;
        }
        
        
        //=======================
        // Seralization Methods
        //=======================
        
        public RenderData dumpRenderData()
        {
            RenderData renderData = new RenderData();
            renderData.data.add(0,this.points);


            return renderData;        
        }

        public SceneObjectRenderDataChanges generateRenderDataChanges(RenderData oldData,RenderData newData)
        {
            SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();

            int changeMap = 0;
            ArrayList changeList = new ArrayList();

            for(int i = 0; i <1; i++)
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
            for(byte i = 0; i <1; i ++)
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

     }
    }
    
 

    
    //====================
    // RenderData Methods
    //====================
     public RenderData dumpRenderData()
     {
         RenderData renderData = new RenderData();

         renderData.data.add(0,numberOfPotionsStat.dumpRenderData());
         renderData.data.add(1,ccReductionStat.dumpRenderData());
         renderData.data.add(2,healingEffectivenessStat.dumpRenderData());
         renderData.data.add(3,healthStat.dumpRenderData());
         renderData.data.add(4,damageReductionStat.dumpRenderData());
         renderData.data.add(5,lifeLeech.dumpRenderData());
         renderData.data.add(6,thornsDamage.dumpRenderData());        
         renderData.data.add(7,weaponDamageStat.dumpRenderData());
         renderData.data.add(8,weaponAttackSpeedStat.dumpRenderData());
         renderData.data.add(9,critChanceStat.dumpRenderData());
         renderData.data.add(10,moveSpeedStat.dumpRenderData()); 
         renderData.data.add(11,jumpHeightStat.dumpRenderData()); 
         
         renderData.data.add(12,seeEnemyHealthModifier.dumpRenderData()); 
         renderData.data.add(13,doubleGoldFindModifier.dumpRenderData()); 
         renderData.data.add(14,upgradedRadarModifier.dumpRenderData());         
         renderData.data.add(15,damageReductionBonusModifier.dumpRenderData()); 
         renderData.data.add(16,reducedCriticalHitModifier.dumpRenderData()); 
         renderData.data.add(17,bonusThornsDamageModifier.dumpRenderData()); 
         renderData.data.add(18,meleeAttackDamageModifier.dumpRenderData()); 
         renderData.data.add(19,potionCooldownResetModifier.dumpRenderData()); 
         renderData.data.add(20,criticalHitDamageModifier.dumpRenderData());        
         renderData.data.add(21,doubleJumpModifier.dumpRenderData()); 
         renderData.data.add(22,jetpackModifier.dumpRenderData()); 
         renderData.data.add(23,teleportModifier.dumpRenderData()); 

         return renderData;        
     }
     
     public SceneObjectRenderDataChanges generateRenderDataChanges(RenderData oldData,RenderData newData)
     {
         SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();
        
         int changeMap = 0;
         ArrayList changeList = new ArrayList();
         
         ArmorStat dummyStat = this.armorStats.get(ArmorStatID.CC_REDUCTION);
         
        for(int i=0; i<=11; i++)
        {//0
            SceneObjectRenderDataChanges renderChanges = dummyStat.generateRenderDataChanges((RenderData)oldData.data.get(i), (RenderData)newData.data.get(i));
            if(renderChanges != null)
            {
                changeList.add(renderChanges);
                changeMap += 1L <<i;
            }
        }
       
        ArmorModifier dummyModifier = this.armorModifiers.get(ArmorModifierID.DOUBLE_JUMP);
        for(int i=12; i<=23; i++)
        {//0
            SceneObjectRenderDataChanges renderChanges = dummyModifier.generateRenderDataChanges((RenderData)oldData.data.get(i), (RenderData)newData.data.get(i));
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
                this.numberOfPotionsStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(0));
            }
            if(changeData.get(1) != null)
            {
                this.ccReductionStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(1));
            }
            if(changeData.get(2) != null)
            {
                this.healingEffectivenessStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(2));
            }
            if(changeData.get(3) != null)
            {
                this.healthStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(3));
            }
            if(changeData.get(4) != null)
            {
                this.damageReductionStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(4));
            }
            if(changeData.get(5) != null)
            {
                this.lifeLeech.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(5));
            }
            if(changeData.get(6) != null)
            {
                this.thornsDamage.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(6));
            }          
            if(changeData.get(7) != null)
            {
                this.weaponDamageStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(7));
            }
            if(changeData.get(8) != null)
            {
                this.weaponAttackSpeedStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(8));
            }
            if(changeData.get(9) != null)
            {
                this.critChanceStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(9));
            }
            if(changeData.get(10) != null)
            {
                this.moveSpeedStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(10));
            }
            if(changeData.get(11) != null)
            {
                this.jumpHeightStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(11));
            }
            
            
            
            
            
            if(changeData.get(12) != null)
            {
                this.seeEnemyHealthModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(12));
            }
            if(changeData.get(13) != null)
            {
                this.doubleGoldFindModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(13));
            }
            if(changeData.get(14) != null)
            {
                this.upgradedRadarModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(14));
            }
            
            if(changeData.get(15) != null)
            {
                this.damageReductionBonusModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(15));
            }
            if(changeData.get(16) != null)
            {
                this.reducedCriticalHitModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(16));
            }
            if(changeData.get(17) != null)
            {
                this.bonusThornsDamageModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(17));
            }
            if(changeData.get(18) != null)
            {
                this.meleeAttackDamageModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(18));
            }
            if(changeData.get(19) != null)
            {
                this.potionCooldownResetModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(19));
            }
            if(changeData.get(20) != null)
            {
                this.criticalHitDamageModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(20));
            }
            
            if(changeData.get(21) != null)
            {
                this.doubleJumpModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(21));
            }
            if(changeData.get(22) != null)
            {
                this.jetpackModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(22));
            }
            if(changeData.get(23) != null)
            {
                this.teleportModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(23));
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
            saveData.dataMap.put(entry.getKey().name(), entry.getValue().points);
       }
      
       //save all armor modifier data
       for(Entry<ArmorModifierID,ArmorModifier> entry: this.armorModifiers.entrySet())
       {          
            saveData.dataMap.put((entry.getKey()).name() + "-a", (entry.getValue()).unlocked);
            saveData.dataMap.put((entry.getKey()).name() + "-b", (entry.getValue()).equipped);
       
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
           byte points = (byte)saveData.dataMap.get(((ArmorStatID)entry.getKey()).name()); 
           entry.getValue().points = points;

       }
       
       //go through all modifieres setting equipped, unlocked
       for(Entry<ArmorModifierID,ArmorModifier> entry: armorManager.armorModifiers.entrySet())
       {          
           boolean unlocked = (boolean)saveData.dataMap.get((entry.getKey()).name() + "-a");
           boolean equipped = (boolean)saveData.dataMap.get((entry.getKey()).name() + "-b");
           entry.getValue().equipped = equipped;
           entry.getValue().unlocked = unlocked;
       
       }
     
          
       return armorManager;
       
   }
}
