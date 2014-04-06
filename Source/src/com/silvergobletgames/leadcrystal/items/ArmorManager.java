

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

    
    
    //==============
    // Constructor
    //==============
    public ArmorManager()
    {
        //================
        // Helm Modifiers
        //================
        this.seeEnemyHealthModifier = new ArmorModifier(ArmorModifierID.ENEMY_HEALTH, new Image("blank.png"), "See Enemy Health", 150);
        this.armorModifiers.put(this.seeEnemyHealthModifier.id,this.seeEnemyHealthModifier);
        
        this.doubleGoldFindModifier = new ArmorModifier(ArmorModifierID.GOLD_FIND, new Image("blank.png"), "Double Gold Find", 300);
        this.armorModifiers.put(this.doubleGoldFindModifier.id,this.doubleGoldFindModifier);
       
        this.seeSecondaryObjectivesModifier = new ArmorModifier(ArmorModifierID.SECONDARY_OBJECTIVES, new Image("blank.png"), "See Secondary Objectives", 500);
        this.armorModifiers.put(this.seeSecondaryObjectivesModifier.id,this.seeSecondaryObjectivesModifier);
        
        this.revealSecretAreasModifier = new ArmorModifier(ArmorModifierID.REVEAL_SECRETS, new Image("blank.png"), "Reveal Secret Objectives", 1000);
        this.armorModifiers.put(this.revealSecretAreasModifier.id,this.revealSecretAreasModifier);
       
        //===============
        // Helm Stats
        //===============
        this.helmHealthStat = new ArmorStat(ArmorStat.ArmorStatID.HELM_HEALTH, new Image("healthStat.jpg"), "Health", 50);
        this.helmHealthStat.description = "+10 health per point.";
        this.helmHealthStat.setAddPointAction(()->{this.getPlayerReference().getCombatData().maxHealth.adjustBase(10); 
                                                   this.getPlayerReference().getCombatData().currentHealth+=10;
                                                   }); 
        this.armorStats.put(this.helmHealthStat.id,this.helmHealthStat);
        
        this.helmDamageReductionStat = new ArmorStat(ArmorStat.ArmorStatID.HELM_DAMAGE_REDUCTION, new Image("armorStat.jpg"), "Damage Reduction", 50);
        this.helmDamageReductionStat.description = "+2% damage reduction per point.";
        this.helmDamageReductionStat.setAddPointAction(()->{this.getPlayerReference().getCombatData().damageResistance.adjustBase(.02f);}); 
        this.armorStats.put(this.helmDamageReductionStat.id,this.helmDamageReductionStat);
        
        this.ccReductionStat = new ArmorStat(ArmorStat.ArmorStatID.CC_REDUCTION, new Image("CCStat.jpg"), "CC Reduction", 75);
        this.ccReductionStat.description = "+5% CC reduction per point.";
        this.ccReductionStat.setAddPointAction(()->{this.getPlayerReference().getCombatData().ccResistance.adjustBase(.05f);}); 
        this.armorStats.put(this.ccReductionStat.id,this.ccReductionStat);
        
        this.healingEffectivenessStat = new ArmorStat(ArmorStat.ArmorStatID.HEALING_EFFECTIVENESS, new Image("healingStat.jpg"), "Healing Effectiveness", 75);
        this.healingEffectivenessStat.description = "+5% healing effectiveness per point.";
        this.healingEffectivenessStat.setAddPointAction(()->{this.getPlayerReference().getCombatData().healingModifier.adjustBase(.05f);}); 
        this.armorStats.put(this.healingEffectivenessStat.id,this.healingEffectivenessStat);
    }
    
    
    //==========
    // Methods
    //==========
   
    
    public void setPlayerReference(PlayerEntity player)
    {
        this.playerReference = player;
        
    }
    
    public PlayerEntity getPlayerReference()
    {
        return this.playerReference;
    }
    
        
    //=====================
    // Inner Classes
    //=====================
    
    public static class ArmorModifier
    {
        protected ArmorModifierID id;
        protected Image image;
        protected String name;
        protected String description;
        protected int cost;
        protected boolean unlocked;
        protected boolean equipped;
        private ArmorAction equipAction;
        private ArmorAction unequipAction;
        
        public interface ArmorAction
        {
            public void doAction();
        }
        
        public static enum ArmorModifierID
        {   
            //helm modifiers
            ENEMY_HEALTH, GOLD_FIND, SECONDARY_OBJECTIVES, REVEAL_SECRETS,
            //body modifiers
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
        public ArmorModifier(ArmorModifierID id,Image image, String name, int cost)
        {
            this.id = id;
            this.image = image;
            this.name = name;
            this.cost = cost;
            
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
          this.equipAction.doAction();
       }
       
        /**
        * Unequips modifier and removes all effects
        */
        public void unequip()
        {
           this.equipped = false;
           this.unequipAction.doAction();
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
        private ArmorAction addPointAction;
        
        public interface ArmorAction
        {
            public void doAction();
        }
        
        public static enum ArmorStatID
        {
            //helm
            HELM_HEALTH,HELM_DAMAGE_REDUCTION, CC_REDUCTION,HEALING_EFFECTIVENESS,
            //body
            
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

         return renderData;        
     }
     
     public SceneObjectRenderDataChanges generateRenderDataChanges(RenderData oldData,RenderData newData)
     {
         SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();
        
         int changeMap = 0;
         ArrayList changeList = new ArrayList();
         
         ArmorStat dummyStat = this.armorStats.get(ArmorStatID.CC_REDUCTION);
         
        //0
        SceneObjectRenderDataChanges renderChanges = dummyStat.generateRenderDataChanges((RenderData)oldData.data.get(0), (RenderData)newData.data.get(0));
        if(renderChanges != null)
        {
            changeList.add(renderChanges);
            changeMap += 1L <<0;
        }
        //1
        renderChanges = dummyStat.generateRenderDataChanges((RenderData)oldData.data.get(1), (RenderData)newData.data.get(1));
        if(renderChanges != null)
        {
            changeList.add(renderChanges);
            changeMap += 1L <<1;
        }
        //2
        renderChanges = dummyStat.generateRenderDataChanges((RenderData)oldData.data.get(2), (RenderData)newData.data.get(2));
        if(renderChanges != null)
        {
            changeList.add(renderChanges);
            changeMap += 1L <<2;
        }
        //3
        renderChanges = dummyStat.generateRenderDataChanges((RenderData)oldData.data.get(3), (RenderData)newData.data.get(3));
        if(renderChanges != null)
        {
            changeList.add(renderChanges);
            changeMap += 1L <<3;
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
            for(byte i = 0; i <10; i ++)
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
       
     
          
       return armorManager;
       
   }
}
