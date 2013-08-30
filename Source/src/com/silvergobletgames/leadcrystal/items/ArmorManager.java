

package com.silvergobletgames.leadcrystal.items;

import com.silvergobletgames.leadcrystal.combat.*;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
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
    
    //equipped items
    private ArmorID equippedAttachment;
    private ArmorID equippedModifier;
    private ArmorID equippedChest;
    private ArmorID equippedBoots;
    private ArmorID equippedHelm;
    
    //map of all armorID's to their armors
    private HashMap<ArmorID, Armor> armorMap = new HashMap();
    
    //map of armorID's and if they are locked or unlocked (true == unlocked)
    private HashMap<ArmorID, Boolean> isLockedMap = new HashMap();
    
    
    public static enum ArmorID
    {
        ATTACHMENT1,ATTACHMENT2,ATTACHMENT3,ATTACHMENT4,
        MODIFIER1,MODIFIER2,MODIFIER3,MODIFIER4,
        CHEST1,CHEST2,CHEST3,CHEST4,
        BOOTS1,BOOTS2,BOOTS3,BOOTS4,
        HELM1,HELM2,HELM3,HELM4;
    }
    
    
    //==============
    // Constructor
    //==============
    public ArmorManager()
    {
        
        //===================
        // Weapon Attachment
        //===================
        
        //attachment 1
        Armor armor = new Armor()
        {
            public void onEquip(PlayerEntity player)
            {
                StateEffect effect = new StateEffect(StateEffect.StateEffectType.BASEDAMAGE, 10, .2f, true);
                effect.setInfinite();
                player.getCombatData().addCombatEffect("attachment1", effect);
            }
            
            public void onUnequip(PlayerEntity player)
            {
                player.getCombatData().removeCombatEffect("attachment1");
            }
        };
        armor.image = new Image("ranged.png");
        armor.name = "Weapon Attachment: Damage";
        armor.description = "20% increased damage.";
        armor.currencyCost = 40;
        this.armorMap.put(ArmorID.ATTACHMENT1, armor);
        
        //attachment 2
        armor = new Armor(){
            public void onEquip(PlayerEntity player)
            {
                StateEffect effect = new StateEffect(StateEffect.StateEffectType.COOLDOWNMODIFIER, 10, -.30f, false);
                effect.setInfinite();
                player.getCombatData().addCombatEffect("attachment2", effect);
            }
            
            public void onUnequip(PlayerEntity player)
            {
                player.getCombatData().removeCombatEffect("attachment2");
            }
        };
        armor.image = new Image("ranged.png");
        armor.name = "Weapon Attachment: Speed";
        armor.description = "30% cooldown reduction.";
        armor.currencyCost = 60;
        this.armorMap.put(ArmorID.ATTACHMENT2, armor);
        
        //attachment 3
        armor = new Armor(){
            public void onEquip(PlayerEntity player)
            {
                StateEffect effect = new StateEffect(StateEffect.StateEffectType.CRITCHANCE, 10, .1f, false);
                effect.setInfinite();
                player.getCombatData().addCombatEffect("attachment3", effect);
            }
            
            public void onUnequip(PlayerEntity player)
            {
                player.getCombatData().removeCombatEffect("attachment3");
            }
        };
        armor.image = new Image("ranged.png");
        armor.name = "Weapon Attachment: Crit";
        armor.description = "+10% chance to crit.";
        armor.currencyCost = 80;
        this.armorMap.put(ArmorID.ATTACHMENT3, armor);
        
        //attachment 4
        armor = new Armor(){
            public void onEquip(PlayerEntity player)
            {
               
                StateEffect effect = new StateEffect(StateEffect.StateEffectType.LIFELEECH, 10, .1f, false);
                effect.setInfinite();
                player.getCombatData().addCombatEffect("attachment4", effect);
                
            }
            
            public void onUnequip(PlayerEntity player)
            {
                player.getCombatData().removeCombatEffect("attachment4");
            }
        };
        armor.image = new Image("ranged.png");
        armor.name = "Weapon Attachment: Leech";
        armor.description = "10% life leech";
        armor.currencyCost = 100;
        this.armorMap.put(ArmorID.ATTACHMENT4, armor);
        
        
        //================
        // Weapon Modifier
        //=================
        
        //weapon modifier 1
        armor = new Armor()
        {
            public void onEquip(PlayerEntity player)
            {
                //has a 10% chance to slow 75% for 6 seconds
                ProcEffect effect = new ProcEffect(ProcEffect.ProcType.ONSKILL, 60, new StateEffect(StateEffect.StateEffectType.SLOW, 360, .75f, true), 0.10f); 
                effect.setInfinite();
                player.getCombatData().addCombatEffect("modifier1", effect);
            }
            
            public void onUnequip(PlayerEntity player)
            {
                player.getCombatData().removeCombatEffect("modifier1");
            }
        };
        armor.image = new Image("melee1.png");
        armor.name = "Weapon Modifier: Slow";
        armor.description = "10% chance to slow enemies by 75% for 6 seconds.";
        armor.currencyCost = 40;
        this.armorMap.put(ArmorID.MODIFIER1, armor);
        
        //weapon modifier 2
        armor = new Armor(){
            public void onEquip(PlayerEntity player)
            {
                //has a 3% chance to stun for 3 seconds
                ProcEffect effect = new ProcEffect(ProcEffect.ProcType.ONSKILL, 60, new StateEffect(StateEffect.StateEffectType.STUN, 180, .75f, true), 0.05f); 
                effect.setInfinite();
                player.getCombatData().addCombatEffect("modifier2", effect);
            }
            
            public void onUnequip(PlayerEntity player)
            {
                player.getCombatData().removeCombatEffect("modifier2");
            }
        };
        armor.image = new Image("melee2.png");
        armor.name = "Weapon Modifier: Stun";
        armor.description = "5% chance for attacks to stun enemies for 3 seconds.";
        armor.currencyCost = 60;
        this.armorMap.put(ArmorID.MODIFIER2, armor);
        
        //weapon modifier 3
        armor = new Armor(){
             public void onEquip(PlayerEntity player)
            {
                //has a 10% chance to bleed for 10 damage
                ProcEffect effect = new ProcEffect(ProcEffect.ProcType.ONSKILL, 60, new DotEffect(150, 30, new Damage(Damage.DamageType.PHYSICAL, 2)), 0.15f); 
                effect.setInfinite();
                player.getCombatData().addCombatEffect("modifier3", effect);
            }
            
            public void onUnequip(PlayerEntity player)
            {
                player.getCombatData().removeCombatEffect("modifier3");
            }
        };
        armor.image = new Image("melee3.png");
        armor.name = "Weapon Modifier: Bleed";
        armor.description = "15% chance for attacks to bleed for 10 additional damage.";
        armor.currencyCost = 80;
        this.armorMap.put(ArmorID.MODIFIER3, armor);
        
        //weapon modifier 4
        armor = new Armor(){
            public void onEquip(PlayerEntity player)
            {
                //has a 10% chance to bleed for 10 damage
                StateEffect effect = new StateEffect(StateEffect.StateEffectType.CRITMODIFIER, 10, 1, false); 
                effect.setInfinite(); 
                player.getCombatData().addCombatEffect("modifier4", effect);
            }
            
            public void onUnequip(PlayerEntity player)
            {
                player.getCombatData().removeCombatEffect("modifier4");
            }
        };
        armor.image = new Image("melee4.png");
        armor.name = "Weapon Modifier: Critical";
        armor.description = "Critical hits do 100% more damage.";
        armor.currencyCost = 100;
        this.armorMap.put(ArmorID.MODIFIER4, armor);
        
        
        //===========
        // Body Armor
        //===========
        
        //chest 1
        armor = new Armor(){
            public void onEquip(PlayerEntity player)
            {
               
                StateEffect effect = new StateEffect(StateEffect.StateEffectType.DAMAGEREDUCTION, 10, .2f, false);
                effect.setInfinite();
                player.getCombatData().addCombatEffect("chest1", effect);
                
            }
            
            public void onUnequip(PlayerEntity player)
            {
                player.getCombatData().removeCombatEffect("chest1");
            }
        };
        armor.image = new Image("body1.png");
        armor.name = "Body Armor: Mitigation";
        armor.description = "Take 20% less damage";
        armor.currencyCost = 40;
        this.armorMap.put(ArmorID.CHEST1, armor);
        
        //chest 2
        armor = new Armor(){
             public void onEquip(PlayerEntity player)
            {
               
                StateEffect effect = new StateEffect(StateEffect.StateEffectType.HEALINGMODIFIER, 10, 1f, false);
                effect.setInfinite();
                player.getCombatData().addCombatEffect("chest2", effect);
                
            }
            
            public void onUnequip(PlayerEntity player)
            {
                player.getCombatData().removeCombatEffect("chest2");
            }
        };
        armor.image = new Image("body2.png");
        armor.name = "Body Armor: Recovery";
        armor.description = "All healing is 100% as effective.";
        armor.currencyCost = 60;
        this.armorMap.put(ArmorID.CHEST2, armor);
        
        //chest 3
        armor = new Armor(){
            public void onEquip(PlayerEntity player)
            {
               
                StateEffect effect = new StateEffect(StateEffect.StateEffectType.CCREDUCTION, 10, .75f, true);
                effect.setInfinite();
                player.getCombatData().addCombatEffect("chest3", effect);
                
            }
            
            public void onUnequip(PlayerEntity player)
            {
                player.getCombatData().removeCombatEffect("chest3");
            }
        };
        armor.image = new Image("body3.png");
        armor.name = "Body Armor: Resistance";
        armor.description = "75% CC reduction";
        armor.currencyCost = 80;
        this.armorMap.put(ArmorID.CHEST3, armor);
        
        //chest 4 
        armor = new Armor(){
             public void onEquip(PlayerEntity player)
            {
               
                StateEffect proccedEffect = new StateEffect(StateEffect.StateEffectType.IMMUNE, 300);
                ProcEffect effect = new ProcEffect(ProcEffect.ProcType.TAKEDAMAGE, 10, proccedEffect, .03f);
                effect.setInfinite();
                player.getCombatData().addCombatEffect("chest4", effect);
                
            }
            
            public void onUnequip(PlayerEntity player)
            {
                player.getCombatData().removeCombatEffect("chest4");
            }
        }; 
        armor.image = new Image("body4.png");
        armor.name = "Body Armor: Last Will";
        armor.description = "Damage taken has a %3 chance to make you immune for 5 seconds.";
        armor.currencyCost = 100;
        this.armorMap.put(ArmorID.CHEST4, armor);
        
        
        //========
        // Boots
        //========
        
        //boots 1
        armor = new Armor(){
             public void onEquip(PlayerEntity player)
            {
               
                StateEffect effect = new StateEffect(StateEffect.StateEffectType.SLOW, 10, -.20f, true);
                effect.setInfinite();
                player.getCombatData().addCombatEffect("boots1", effect);
                
            }
            
            public void onUnequip(PlayerEntity player)
            {
                player.getCombatData().removeCombatEffect("boots1");
            }
        };
        armor.image = new Image("boots1.png");
        armor.name = "Boots: Mobility";
        armor.description = "20% faster movement speed.";
        armor.currencyCost = 40;
        this.armorMap.put(ArmorID.BOOTS1, armor);
        
        //chest 2
        armor = new Armor(){
            public void onEquip(){}
            
            public void onUnequip(){}
        };
        armor.image = new Image("boots2.png");
        armor.name = "Boots: Double Jump";
        armor.description = "Press space again at the peak of your first jump to do a double jump.";
        armor.currencyCost = 60;
        this.armorMap.put(ArmorID.BOOTS2, armor);
        
        //chest 3
        armor = new Armor(){
            public void onEquip(){}
            
            public void onUnequip(){}
        };
        armor.image = new Image("boots3.png");
        armor.name = "Boots: Jetpack";
        armor.description = "Press space again at the peak of your first jump to use the jetpack.";
        armor.currencyCost = 80;
        this.armorMap.put(ArmorID.BOOTS3, armor);
        
        //chest 4
        armor = new Armor(){
            public void onEquip(){}
            
            public void onUnequip(){}
        };
        armor.image = new Image("boots4.png");
        armor.name = "Boots: Teleport";
        armor.description = "Press space again at the peak of your first jump to teleport to your mouse.";
        armor.currencyCost = 100;
        this.armorMap.put(ArmorID.BOOTS4, armor);
        
        
        //========
        // Helm
        //========
        
        //helm 1
        armor = new Armor(){
            public void onEquip(PlayerEntity player)
            {
                GameplaySettings.getInstance().showHealthBars = true;
            }
            
            public void onUnequip(PlayerEntity player)
            {
                GameplaySettings.getInstance().showHealthBars = false;
            }
        };
        armor.image = new Image("helm1.png");
        armor.name = "Eyepiece: Weakness";
        armor.description = "See enemy health";
        armor.currencyCost = 40;
        this.armorMap.put(ArmorID.HELM1, armor);
        
        //helm 2
        armor = new Armor(){
            public void onEquip(){}
            
            public void onUnequip(){}
        };
        armor.image = new Image("helm2.png");
        armor.name = "Eyepiece: Secondary";
        armor.description = "See secondary objectives on the radar.";
        armor.currencyCost = 60;
        this.armorMap.put(ArmorID.HELM2, armor);
        
        //helm 3 //TODO work on this one
        armor = new Armor(){
            public void onEquip(PlayerEntity player)
            {
               
            }
            
            public void onUnequip(PlayerEntity player)
            {
                
            }
        };
        armor.image = new Image("helm3.png");
        armor.name = "Eyepiece: Secrets";
        armor.description = "See secret locations on the radar.";
        armor.currencyCost = 80;
        this.armorMap.put(ArmorID.HELM3, armor);
        
        //helm 4
        armor = new Armor(){
            public void onEquip(PlayerEntity player)
            {
                GameplaySettings.getInstance().showSpawnLocations = true;
            }
            
            public void onUnequip(PlayerEntity player)
            {
                 GameplaySettings.getInstance().showSpawnLocations = false;
            }
        };
        armor.image = new Image("helm4.png");
        armor.name = "Eyepiece: Spidey Sense";
        armor.description = "Can see where enemies will spawn.";
        armor.currencyCost = 100;
        this.armorMap.put(ArmorID.HELM4, armor);
        
        
        
        
        //====================
        // Populate Lock List
        //====================
        
        for(ArmorID id: ArmorID.values())
        {
            this.isLockedMap.put(id, Boolean.FALSE);
        }     
        
        
    }
    
    
    //==========
    // Methods
    //==========
    public Armor getArmor(ArmorID armor)
    {
        return this.armorMap.get(armor);
    }
    
    public Armor getAttachmentEquipped()
    {
        if(equippedAttachment != null)
            return armorMap.get(equippedAttachment);
        else
            return null;
    }
    
    public ArmorID getAttachmentEquippedID()
    {
        return this.equippedAttachment;
    }
    
    public Armor getModifierEquipped()
    {
        if(equippedModifier != null)
            return armorMap.get(equippedModifier);
        else
            return null;
    }
    
    public ArmorID getModifierEquippedID()
    {
        return this.equippedModifier;
    }
    
    public Armor getChestEquipped()
    {
        if(equippedChest != null)
            return armorMap.get(equippedChest);
        else 
            return null;
    }
    
    public ArmorID getChestEquippedID()
    {
        return this.equippedChest;
    }
    
    public Armor getBootsEquipped()
    {
        if(equippedBoots != null)
            return armorMap.get(equippedBoots);
        else
            return null;
    }
    
    public ArmorID getBootsEquippedID()
    {
        return this.equippedBoots;
    }
    
    public Armor getHelmEquipped()
    {
        if(equippedHelm != null)
            return armorMap.get(equippedHelm);
        else
            return null;
    }
    
    public ArmorID getHelmEquippedID()
    {
        return this.equippedHelm;
    }
    
    public boolean isUnlocked(ArmorID armor)
    {
        return this.isLockedMap.get(armor);
    }
    
    public void equipRanged(ArmorID armor)
    {
        //unequp old armor
        if(equippedAttachment != null)
        {
            armorMap.get(equippedAttachment).onUnequip(this.playerReference);
        }
        
        //equip new armor
        equippedAttachment = armor;
        if(equippedAttachment != null)
        {
            armorMap.get(equippedAttachment).onEquip(this.playerReference);
        }
    }
    
    public void equipMelee(ArmorID armor)
    {
        //unequp old armor
        if(equippedModifier != null)
        {
            armorMap.get(equippedModifier).onUnequip(this.playerReference);
        }
        
        //equip new armor
        equippedModifier = armor;
        if(equippedModifier != null)
        {
            armorMap.get(equippedModifier).onEquip(this.playerReference);
        }
    }
    
    public void equipChest(ArmorID armor)
    {
        //unequp old armor
        if(equippedChest != null)
        {
            armorMap.get(equippedChest).onUnequip(this.playerReference);
        }
        
        //equip new armor
        equippedChest = armor;
        if(equippedChest != null)
        {
            armorMap.get(equippedChest).onEquip(this.playerReference);
        }
    }
    
    public void equipBoots(ArmorID armor)
    {
        //unequp old armor
        if(equippedBoots != null)
        {
            armorMap.get(equippedBoots).onUnequip(this.playerReference);
        }
        
        //equip new armor
        equippedBoots = armor;
        if(equippedBoots != null)
        {
            armorMap.get(equippedBoots).onEquip(this.playerReference);
        }
    }
    
    public void equipHelm(ArmorID armor)
    {
        //unequp old armor
        if(equippedHelm != null)
        {
            armorMap.get(equippedHelm).onUnequip(this.playerReference);
        }
        
        //equip new armor
        equippedHelm = armor;
        if(equippedHelm != null)
        {
            armorMap.get(equippedHelm).onEquip(this.playerReference);
        }
    }
    
    public void unlock(ArmorID armor)
    {
        this.isLockedMap.put(armor, Boolean.TRUE);
    }
    
    public void setPlayerReference(PlayerEntity player)
    {
        this.playerReference = player;
        
        // paply combat stuff
        if(this.equippedBoots != null)
        {
            armorMap.get(this.equippedBoots).onEquip(player);
        }
        if(this.equippedChest != null)
        {
            armorMap.get(this.equippedChest).onEquip(player);
        }
        if(this.equippedHelm != null)
        {
            armorMap.get(this.equippedHelm).onEquip(player);
        }
        if(this.equippedModifier != null)
        {
            armorMap.get(this.equippedModifier).onEquip(player);
        }
        if(this.equippedAttachment != null)
        {
            armorMap.get(this.equippedAttachment).onEquip(player);
        }
        
    }
    
    public PlayerEntity getPlayerReference()
    {
        return this.playerReference;
    }
    
        
    //=====================
    // Private Armor Class
    //=====================
    
    public static class Armor
    {
        public Image image;
        public String name;
        public String description;
        public int currencyCost;
        
        public void onEquip(PlayerEntity player)
        {
            
        }
        
        public void onUnequip(PlayerEntity player)
        {
            
        }
    }
    
  
    
    
    
    
    //====================
    // RenderData Methods
    //====================
     public RenderData dumpRenderData()
     {
         RenderData renderData = new RenderData();
         
         renderData.data.add(0,equippedAttachment!= null?equippedAttachment:1);
         renderData.data.add(1,equippedModifier!=null?equippedModifier:1);
         renderData.data.add(2,equippedHelm!=null?equippedHelm:1);
         renderData.data.add(3,equippedChest!=null?equippedChest:1);
         renderData.data.add(4,equippedBoots!=null?equippedBoots:1);
         
         //build arraylist of unlocked items
         ArrayList<ArmorID> unlockedList = new ArrayList();      
         for(Entry<ArmorID,Boolean> entry:this.isLockedMap.entrySet())
         {
             //if the armorID is unlocked, add it to the list of unlocked items
             if(entry.getValue() == true) 
                 unlockedList.add(entry.getKey());
         }
         
         renderData.data.add(5,unlockedList);

         
         return renderData;        
     }
     
     public SceneObjectRenderDataChanges generateRenderDataChanges(RenderData oldData,RenderData newData)
     {
         SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();
        
         int changeMap = 0;
         ArrayList changeList = new ArrayList();
         
         for(int i = 0; i <5; i++)
         {                  
             if( !oldData.data.get(i).equals( newData.data.get(i)))
             {                 
                 changeList.add(newData.data.get(i));
                 changeMap += 1L << i;
             }
         }
         
         //compare arrays of unlocked
         ArrayList<ArmorID> oldList = (ArrayList<ArmorID>)oldData.data.get(5); 
         ArrayList<ArmorID> newList = (ArrayList<ArmorID>)newData.data.get(5);         
         ArrayList<ArmorID> newUnlocks = new ArrayList();
         
         for(ArmorID id: newList)
         {
             if(!oldList.contains(id))
                 newUnlocks.add(id);
         }        
         if(!newUnlocks.isEmpty())
         {
            changeList.add(newUnlocks);
            changeMap += 1L << 5;
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
            for(byte i = 0; i <8; i ++)
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
            {
                this.equippedAttachment = (ArmorID)changeData.get(0);
            }
            if(changeData.get(1) != null)         
            {
                this.equippedModifier = (ArmorID)changeData.get(1);
            }
            if(changeData.get(2) != null)         
            {
                this.equippedHelm = (ArmorID)changeData.get(2);
            }
            if(changeData.get(3) != null)         
            {
                this.equippedChest = (ArmorID)changeData.get(3);
            }
            if(changeData.get(4) != null)         
            {
                this.equippedBoots = (ArmorID)changeData.get(4);
            }
            
            if(changeData.get(5) != null)
            {
                for(ArmorID id: (ArrayList<ArmorID>)changeData.get(5))
                    this.unlock(id);
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
       saveData.dataMap.put("ranged", equippedAttachment != null? equippedAttachment.name(): null);
       saveData.dataMap.put("melee", equippedModifier != null? equippedModifier.name(): null);
       saveData.dataMap.put("helm", equippedHelm != null? equippedHelm.name(): null);
       saveData.dataMap.put("chest", equippedChest != null? equippedChest.name(): null);
       saveData.dataMap.put("boots", equippedBoots != null? equippedBoots.name(): null);
       
       //build arraylist of unlocked items
       ArrayList<ArmorID> unlockedList = new ArrayList();      
       for(Entry<ArmorID,Boolean> entry:this.isLockedMap.entrySet())
       {
           //if the armorID is unlocked, add it to the list of unlocked items
           if(entry.getValue() == true) 
               unlockedList.add(entry.getKey());
       }
       
       saveData.dataMap.put("unlockedList", unlockedList);
        
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
       
       armorManager.equippedAttachment = saveData.dataMap.get("ranged") != null? ArmorID.valueOf((String)saveData.dataMap.get("ranged")) : null;
       armorManager.equippedModifier = saveData.dataMap.get("melee") != null? ArmorID.valueOf((String)saveData.dataMap.get("melee")) : null;
       armorManager.equippedHelm = saveData.dataMap.get("helm") != null? ArmorID.valueOf((String)saveData.dataMap.get("helm")) : null;
       armorManager.equippedChest = saveData.dataMap.get("chest") != null? ArmorID.valueOf((String)saveData.dataMap.get("chest")) : null;
       armorManager.equippedBoots = saveData.dataMap.get("boots") != null? ArmorID.valueOf((String)saveData.dataMap.get("boots")) : null;
       
       //unlock items
       ArrayList<ArmorID> unlockedList = (ArrayList<ArmorID>)saveData.dataMap.get("unlockedList");
       for(ArmorID id: unlockedList)     
           armorManager.unlock(id);
       
          
       return armorManager;
       
   }
}
