

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
    public ArmorModifier seeSecondaryObjectivesModifier;
    public ArmorModifier revealSecretAreasModifier;
    public ArmorStat helmHealthStat;
    public ArmorStat helmDamageReductionStat;
    public ArmorStat ccReductionStat;
    public ArmorStat healingEffectivenessStat;
    
    //body
    public ArmorModifier doubleCCResistModifier;
    public ArmorModifier doubleHealingModifier;
    public ArmorModifier tenPotionsModifier;
    public ArmorModifier chanceAbsorbModifier;
    public ArmorStat bodyHealthStat;
    public ArmorStat bodyDamageReductionStat;
    public ArmorStat lifeLeech;
    public ArmorStat lifeRegen;
    
    //weapon
    public ArmorModifier concecutiveHitsModifier;
    public ArmorModifier meleeAttackDamageModifier;
    public ArmorModifier rangedAttackSlowModifier;
    public ArmorModifier criticalHitDamageModifier;
    public ArmorStat weaponDamageStat;
    public ArmorStat weaponAttackSpeedStat;
    public ArmorStat critChanceStat;
    public ArmorStat critDamageStat; 
    
    //boots
    public ArmorModifier ccBonusModifier;
    public ArmorModifier doubleJumpModifier;
    public ArmorModifier jetpackModifier;
    public ArmorModifier teleportModifier;
    public ArmorStat bootsDamageStat;
    public ArmorStat bootsAttackSpeedStat;
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
       
        this.seeSecondaryObjectivesModifier = new ArmorModifier(ArmorModifierID.SECONDARY_OBJECTIVES, new Image("seeSecondaryObjectiveModifier.jpg"), "See Secondary Objectives");
        this.seeSecondaryObjectivesModifier.description = "Allows secondary objectives to show up on your radar.";
        this.armorModifiers.put(this.seeSecondaryObjectivesModifier.id,this.seeSecondaryObjectivesModifier);
        
        this.revealSecretAreasModifier = new ArmorModifier(ArmorModifierID.REVEAL_SECRETS, new Image("revealSecretModifier.jpg"), "Reveal Secret Objectives");
        this.revealSecretAreasModifier.description = "Allows secret areas to show up on your radar.";
        this.armorModifiers.put(this.revealSecretAreasModifier.id,this.revealSecretAreasModifier);
       
        //===============
        // Helm Stats
        //===============
        this.helmHealthStat = new ArmorStat(ArmorStat.ArmorStatID.HELM_HEALTH, new Image("healthStat.jpg"), "Health", 50);
        this.helmHealthStat.description = "+10 health per point.";
        this.helmHealthStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().maxHealth.adjustBase(10); 
                                                   getPlayerReference().getCombatData().currentHealth+=10;
                                                   }}); 
        this.armorStats.put(this.helmHealthStat.id,this.helmHealthStat);
        
        this.helmDamageReductionStat = new ArmorStat(ArmorStat.ArmorStatID.HELM_DAMAGE_REDUCTION, new Image("armorStat.jpg"), "Damage Reduction", 50);
        this.helmDamageReductionStat.description = "+2% damage reduction per point.";
        this.helmDamageReductionStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().damageResistance.adjustBase(.02f);}}); 
        this.armorStats.put(this.helmDamageReductionStat.id,this.helmDamageReductionStat);
        
        this.ccReductionStat = new ArmorStat(ArmorStat.ArmorStatID.CC_REDUCTION, new Image("CCStat.jpg"), "CC Reduction", 75);
        this.ccReductionStat.description = "+5% CC reduction per point.";
        this.ccReductionStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().ccResistance.adjustBase(.05f);}}); 
        this.armorStats.put(this.ccReductionStat.id,this.ccReductionStat);
        
        this.healingEffectivenessStat = new ArmorStat(ArmorStat.ArmorStatID.HEALING_EFFECTIVENESS, new Image("healingStat.jpg"), "Healing Effectiveness", 75);
        this.healingEffectivenessStat.description = "+5% healing effectiveness per point.";
        this.healingEffectivenessStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().healingModifier.adjustBase(.05f);}}); 
        this.armorStats.put(this.healingEffectivenessStat.id,this.healingEffectivenessStat);
       
        
        //================
        // Body Modifiers
        //================
        this.doubleCCResistModifier = new ArmorModifier(ArmorModifierID.DOUBLE_CC, new Image("ccStat.jpg"), "Double CC Resist");
        this.doubleCCResistModifier.description = "Doubles your CC resistance.";
        this.armorModifiers.put(this.doubleCCResistModifier.id,this.doubleCCResistModifier);
        
        this.doubleHealingModifier = new ArmorModifier(ArmorModifierID.DOUBLE_HEALING, new Image("healthStat.jpg"), "Double Healing Effectiveness");
        this.doubleHealingModifier.description ="Doubles the effectiveness of all healing";
        this.armorModifiers.put(this.doubleHealingModifier.id,this.doubleHealingModifier);
       
        this.tenPotionsModifier = new ArmorModifier(ArmorModifierID.TEN_POTIONS, new Image("healingStat.jpg"), "Additional Potions");
        this.tenPotionsModifier.description = "Allows you to carry up to 10 potions";
        this.armorModifiers.put(this.tenPotionsModifier.id,this.tenPotionsModifier);
        
        this.chanceAbsorbModifier = new ArmorModifier(ArmorModifierID.CHANCE_ABSORB, new Image("armorStat.jpg"), "Chance to Absorb Projectiles");
        this.chanceAbsorbModifier.description = "When hit by an enemy projectile, you have a 15% chance to absorb it.";
        this.armorModifiers.put(this.chanceAbsorbModifier.id,this.chanceAbsorbModifier);
        
        
        //===============
        // Body Stats
        //===============
        this.bodyHealthStat = new ArmorStat(ArmorStat.ArmorStatID.BODY_HEALTH, new Image("healthStat.jpg"), "Health", 75);
        this.bodyHealthStat.description = "+10 health per point.";
        this.bodyHealthStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().maxHealth.adjustBase(10); 
                                                   getPlayerReference().getCombatData().currentHealth+=10;
                                                   }}); 
        this.armorStats.put(this.bodyHealthStat.id,this.bodyHealthStat);
        
        this.bodyDamageReductionStat = new ArmorStat(ArmorStat.ArmorStatID.BODY_DAMAGE_REDUCTION, new Image("armorStat.jpg"), "Damage Reduction", 50);
        this.bodyDamageReductionStat.description = "+2% damage reduction per point.";
        this.bodyDamageReductionStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().damageResistance.adjustBase(.02f);}}); 
        this.armorStats.put(this.bodyDamageReductionStat.id,this.bodyDamageReductionStat);
        
        this.lifeLeech = new ArmorStat(ArmorStat.ArmorStatID.LIFE_LEECH, new Image("lifeLeechStat.jpg"), "Life Leech", 75);
        this.lifeLeech.description = "+5% life leech per point.";
        this.lifeLeech.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().lifeLeech.adjustBase(.05f);}}); 
        this.armorStats.put(this.lifeLeech.id,this.lifeLeech);
        
        this.lifeRegen = new ArmorStat(ArmorStat.ArmorStatID.LIFE_REGEN, new Image("lifeRegenStat.jpg"), "Life Regen", 75);
        this.lifeRegen.description = "+1 health regen/s per point.";
        this.lifeRegen.setAddPointAction(new ArmorAction(){ public void doAction(){System.out.println("health regen up");}}); 
        this.armorStats.put(this.lifeRegen.id,this.lifeRegen);
        
        //=================
        // Weapon Modifiers
        //=================
        this.concecutiveHitsModifier = new ArmorModifier(ArmorModifierID.CONCECUTIVE_HITS, new Image("critChanceStat.jpg"), "Concecutive Hits Bonus");
        this.concecutiveHitsModifier.description = "With each concecutive hit to an enemy you gain 5% crit chance";
        this.armorModifiers.put(this.concecutiveHitsModifier.id,this.concecutiveHitsModifier);
        
        this.meleeAttackDamageModifier = new ArmorModifier(ArmorModifierID.MELEE_ATTACK_DMG, new Image("meleeDamageModifier.jpg"), "Melee Damage Increase");
        this.meleeAttackDamageModifier.description = "Increases the damage of melee attacks by 50%";
        this.armorModifiers.put(this.meleeAttackDamageModifier.id,this.meleeAttackDamageModifier);
       
        this.rangedAttackSlowModifier = new ArmorModifier(ArmorModifierID.RANGED_ATTACK_SLOW, new Image("healthStat.jpg"), "Ranged Attack Slow");
        this.armorModifiers.put(this.rangedAttackSlowModifier.id,this.rangedAttackSlowModifier);
        
        this.criticalHitDamageModifier = new ArmorModifier(ArmorModifierID.CRIT_DMG, new Image("critDamageStat.jpg"), "Critical Hit Damage");
        this.armorModifiers.put(this.criticalHitDamageModifier.id,this.criticalHitDamageModifier);
        

        
        //==============
        // Weapon Stats
        //==============
        this.weaponDamageStat = new ArmorStat(ArmorStat.ArmorStatID.WEAPON_DAMAGE, new Image("damageStat.jpg"), "Damage", 50);
        this.weaponDamageStat.description = "+2% damage per point";
        this.weaponDamageStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().baseDamage.adjustPercentModifier(.02f);}}); 
        this.armorStats.put(this.weaponDamageStat.id,this.weaponDamageStat);
        
        this.weaponAttackSpeedStat = new ArmorStat(ArmorStat.ArmorStatID.WEAPON_ATTACK_SPEED, new Image("attackSpeedStat.jpg"), "Attack Speed", 50);
        this.weaponAttackSpeedStat.description = "+2% attack speed per point";
        this.weaponAttackSpeedStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().cooldownModifier.adjustBase(-.20f);}}); 
        this.armorStats.put(this.weaponAttackSpeedStat.id,this.weaponAttackSpeedStat);
        
        this.critChanceStat = new ArmorStat(ArmorStat.ArmorStatID.CRIT_CHANCE, new Image("critChanceStat.jpg"), "Critical Hit Chance", 75);
        this.critChanceStat.description = "+3% crit chance per point";
        this.critChanceStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().critChance.adjustBase(.03f);}}); 
        this.armorStats.put(this.critChanceStat.id,this.critChanceStat);
        
        this.critDamageStat = new ArmorStat(ArmorStat.ArmorStatID.CRIT_DAMAGE, new Image("critDamageStat.jpg"), "Critical Hit Damage", 75);
        this.critDamageStat.description = "+10% crit damage per point";
        this.critDamageStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().critModifier.adjustBase(.10f);}}); 
        this.armorStats.put(this.critDamageStat.id,this.critDamageStat);
        
        
        //================
        // Boots Modfiers
        //================
        
        this.ccBonusModifier = new ArmorModifier(ArmorModifierID.CC_REDUCTION, new Image("healthStat.jpg"), "+15% CC Reduction");
        this.armorModifiers.put(this.ccBonusModifier.id,this.ccBonusModifier);
        
        this.doubleJumpModifier = new ArmorModifier(ArmorModifierID.DOUBLE_JUMP, new Image("healthStat.jpg"), "Double Jump");
        this.armorModifiers.put(this.doubleJumpModifier.id,this.doubleJumpModifier);
       
        this.jetpackModifier = new ArmorModifier(ArmorModifierID.JETPACK, new Image("healthStat.jpg"), "Jetpack");
        this.armorModifiers.put(this.jetpackModifier.id,this.jetpackModifier);
        
        this.teleportModifier = new ArmorModifier(ArmorModifierID.TELEPORT, new Image("healthStat.jpg"), "Teleport");
        this.armorModifiers.put(this.teleportModifier.id,this.teleportModifier);
        
        //==============
        // Boots Stats
        //==============
        
        this.bootsDamageStat = new ArmorStat(ArmorStat.ArmorStatID.BOOTS_DAMAGE, new Image("damageStat.jpg"), "Damage", 50);
        this.bootsDamageStat.description = "+2% damage per point";
        this.bootsDamageStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().baseDamage.adjustPercentModifier(.02f);}}); 
        this.armorStats.put(this.bootsDamageStat.id,this.bootsDamageStat);
        
        this.bootsAttackSpeedStat = new ArmorStat(ArmorStat.ArmorStatID.BOOTS_ATTACK_SPEED, new Image("attackSpeedStat.jpg"), "Attack Speed", 50);
        this.bootsAttackSpeedStat.description = "+2% attack speed per point";
        this.bootsAttackSpeedStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().cooldownModifier.adjustBase(-.20f);}}); 
        this.armorStats.put(this.bootsAttackSpeedStat.id,this.bootsAttackSpeedStat);
        
        this.moveSpeedStat = new ArmorStat(ArmorStat.ArmorStatID.MOVE_SPEED, new Image("moveSpeedStat.jpg"), "Move Speed", 75);
        this.moveSpeedStat.description = "+5% move speed per point";
        this.moveSpeedStat.setAddPointAction(new ArmorAction(){ public void doAction(){getPlayerReference().getCombatData().xVelocity.adjustPercentModifier(.05f);}}); 
        this.armorStats.put(this.moveSpeedStat.id,this.moveSpeedStat);
        
        this.jumpHeightStat = new ArmorStat(ArmorStat.ArmorStatID.JUMP_HEIGHT, new Image("jumpHeightStat.jpg"), "Jump Height", 75);
        this.jumpHeightStat.description = "2m jump height per point";
        this.jumpHeightStat.setAddPointAction(new ArmorAction(){ public void doAction(){System.out.println("jump height");}}); 
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
        helmModifiers.add(this.seeSecondaryObjectivesModifier);
        helmModifiers.add(this.revealSecretAreasModifier);

        ArrayList<ArmorModifier> bodyModifiers =new ArrayList<>(); 
        bodyModifiers.add(this.doubleCCResistModifier);
        bodyModifiers.add(this.doubleHealingModifier);
        bodyModifiers.add(this.tenPotionsModifier);
        bodyModifiers.add(this.chanceAbsorbModifier);
    
        ArrayList<ArmorModifier> weaponModifiers = new ArrayList<>();
        bodyModifiers.add(this.concecutiveHitsModifier);
        bodyModifiers.add(this.meleeAttackDamageModifier);
        bodyModifiers.add(this.rangedAttackSlowModifier);
        bodyModifiers.add(this.criticalHitDamageModifier);

        ArrayList<ArmorModifier> bootsModifiers = new ArrayList<>();
        bootsModifiers.add(this.ccBonusModifier);
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
            ENEMY_HEALTH, GOLD_FIND, SECONDARY_OBJECTIVES, REVEAL_SECRETS,
            //body modifiers
            DOUBLE_CC, DOUBLE_HEALING, TEN_POTIONS, CHANCE_ABSORB,
            //weapon modifiers
            CONCECUTIVE_HITS,MELEE_ATTACK_DMG,RANGED_ATTACK_SLOW,CRIT_DMG,
            //boot modifiers
            CC_REDUCTION, DOUBLE_JUMP,JETPACK,TELEPORT
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
            this.equipAction.doAction();
        }
        
        public void setUnequipMethod(ArmorAction action)
        {
            this.unequipAction.doAction();
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
            HELM_HEALTH,HELM_DAMAGE_REDUCTION, CC_REDUCTION,HEALING_EFFECTIVENESS,
            //body
            BODY_HEALTH,BODY_DAMAGE_REDUCTION,LIFE_LEECH,LIFE_REGEN,
            //weapon
            WEAPON_DAMAGE, WEAPON_ATTACK_SPEED,CRIT_CHANCE,CRIT_DAMAGE,
            //boots
            BOOTS_DAMAGE,BOOTS_ATTACK_SPEED,MOVE_SPEED, JUMP_HEIGHT
            
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

         renderData.data.add(0,helmHealthStat.dumpRenderData());
         renderData.data.add(1,helmDamageReductionStat.dumpRenderData());
         renderData.data.add(2,ccReductionStat.dumpRenderData());
         renderData.data.add(3,healingEffectivenessStat.dumpRenderData());
         renderData.data.add(4,bodyHealthStat.dumpRenderData());
         renderData.data.add(5,bodyDamageReductionStat.dumpRenderData());
         renderData.data.add(6,lifeLeech.dumpRenderData());
         renderData.data.add(7,lifeRegen.dumpRenderData());        
         renderData.data.add(8,weaponDamageStat.dumpRenderData());
         renderData.data.add(9,weaponAttackSpeedStat.dumpRenderData());
         renderData.data.add(10,critChanceStat.dumpRenderData());
         renderData.data.add(11,critDamageStat.dumpRenderData());        
         renderData.data.add(12,bootsDamageStat.dumpRenderData()); 
         renderData.data.add(13,bootsAttackSpeedStat.dumpRenderData()); 
         renderData.data.add(14,moveSpeedStat.dumpRenderData()); 
         renderData.data.add(15,jumpHeightStat.dumpRenderData()); 
         
         renderData.data.add(16,seeEnemyHealthModifier.dumpRenderData()); 
         renderData.data.add(17,doubleGoldFindModifier.dumpRenderData()); 
         renderData.data.add(18,seeSecondaryObjectivesModifier.dumpRenderData()); 
         renderData.data.add(19,revealSecretAreasModifier.dumpRenderData());    
         
         renderData.data.add(20,doubleCCResistModifier.dumpRenderData()); 
         renderData.data.add(21,doubleHealingModifier.dumpRenderData()); 
         renderData.data.add(22,tenPotionsModifier.dumpRenderData()); 
         renderData.data.add(23,chanceAbsorbModifier.dumpRenderData());    
         
         renderData.data.add(24,concecutiveHitsModifier.dumpRenderData()); 
         renderData.data.add(25,meleeAttackDamageModifier.dumpRenderData()); 
         renderData.data.add(26,rangedAttackSlowModifier.dumpRenderData()); 
         renderData.data.add(27,criticalHitDamageModifier.dumpRenderData()); 
         
         renderData.data.add(28,ccBonusModifier.dumpRenderData()); 
         renderData.data.add(29,doubleJumpModifier.dumpRenderData()); 
         renderData.data.add(30,jetpackModifier.dumpRenderData()); 
         renderData.data.add(31,teleportModifier.dumpRenderData()); 

         return renderData;        
     }
     
     public SceneObjectRenderDataChanges generateRenderDataChanges(RenderData oldData,RenderData newData)
     {
         SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();
        
         int changeMap = 0;
         ArrayList changeList = new ArrayList();
         
         ArmorStat dummyStat = this.armorStats.get(ArmorStatID.CC_REDUCTION);
         
        for(int i=0; i<=15; i++)
        {//0
            SceneObjectRenderDataChanges renderChanges = dummyStat.generateRenderDataChanges((RenderData)oldData.data.get(i), (RenderData)newData.data.get(i));
            if(renderChanges != null)
            {
                changeList.add(renderChanges);
                changeMap += 1L <<i;
            }
        }
       
        ArmorModifier dummyModifier = this.armorModifiers.get(ArmorModifierID.CC_REDUCTION);
        for(int i=16; i<=31; i++)
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
                this.helmHealthStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(0));
            }
            if(changeData.get(1) != null)
            {
                this.helmDamageReductionStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(1));
            }
            if(changeData.get(2) != null)
            {
                this.ccReductionStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(2));
            }
            if(changeData.get(3) != null)
            {
                this.healingEffectivenessStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(3));
            }
            if(changeData.get(4) != null)
            {
                this.bodyHealthStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(4));
            }
            if(changeData.get(5) != null)
            {
                this.bodyDamageReductionStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(5));
            }
            if(changeData.get(6) != null)
            {
                this.lifeLeech.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(6));
            }
            if(changeData.get(7) != null)
            {
                this.lifeRegen.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(7));
            }          
            if(changeData.get(8) != null)
            {
                this.weaponDamageStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(8));
            }
            if(changeData.get(9) != null)
            {
                this.weaponAttackSpeedStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(9));
            }
            if(changeData.get(10) != null)
            {
                this.critChanceStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(10));
            }
            if(changeData.get(11) != null)
            {
                this.critDamageStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(11));
            }
           
            if(changeData.get(12) != null)
            {
                this.bootsDamageStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(12));
            }
            if(changeData.get(13) != null)
            {
                this.bootsAttackSpeedStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(13));
            }
            if(changeData.get(14) != null)
            {
                this.moveSpeedStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(14));
            }
            if(changeData.get(15) != null)
            {
                this.jumpHeightStat.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(15));
            }
            
            if(changeData.get(16) != null)
            {
                this.seeEnemyHealthModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(16));
            }
            if(changeData.get(17) != null)
            {
                this.doubleGoldFindModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(17));
            }
            if(changeData.get(18) != null)
            {
                this.seeSecondaryObjectivesModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(18));
            }
            if(changeData.get(19) != null)
            {
                this.revealSecretAreasModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(19));
            }
            
            if(changeData.get(20) != null)
            {
                this.doubleCCResistModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(20));
            }
            if(changeData.get(21) != null)
            {
                this.doubleHealingModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(21));
            }
            if(changeData.get(22) != null)
            {
                this.tenPotionsModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(22));
            }
            if(changeData.get(23) != null)
            {
                this.chanceAbsorbModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(23));
            }
            
            if(changeData.get(24) != null)
            {
                this.concecutiveHitsModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(24));
            }
            if(changeData.get(25) != null)
            {
                this.meleeAttackDamageModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(25));
            }
            if(changeData.get(26) != null)
            {
                this.rangedAttackSlowModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(26));
            }
            if(changeData.get(27) != null)
            {
                this.criticalHitDamageModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(27));
            }
            
            if(changeData.get(28) != null)
            {
                this.criticalHitDamageModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(28));
            }
            if(changeData.get(29) != null)
            {
                this.criticalHitDamageModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(29));
            }
            if(changeData.get(30) != null)
            {
                this.criticalHitDamageModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(30));
            }
            if(changeData.get(31) != null)
            {
                this.criticalHitDamageModifier.reconcileRenderDataChanges(0, 1, (SceneObjectRenderDataChanges)changeData.get(31));
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
