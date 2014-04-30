
package com.silvergobletgames.leadcrystal.items;

import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.netcode.SaveData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderDataChanges;
import java.util.ArrayList;
import java.util.Arrays;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;

/**
 *
 * @author Mike
 */
public class PotionManager 
{
    public PlayerEntity playerReference;
    private int numberOfPotions;
    private int maxPotions = 5;
    
    public static final int POTION_PRICE = 15;
    
    //============
    // Constructor
    //============
    
    public PotionManager(PlayerEntity playerReference)
    {
        this.playerReference = playerReference;
    }
    
    
    //===============
    // Class Methods
    //===============
    
    public void addPotion(int amount)
    {
        this.numberOfPotions += amount;
    }
    
    public void increaseMaxPotions(int amount)
    {
        this.maxPotions += amount;
    }
    
    public int getNumberOfPotions()
    {
        return this.numberOfPotions;
    }
    
    public int getMaxPotions()
    {
        return this.maxPotions;
    }
    
    public void usePotion()
    {
        if(this.numberOfPotions > 0)
        {
            this.numberOfPotions--;
            
            //do the heal
            Damage heal = new Damage(Damage.DamageType.HEAL, 50);
            this.playerReference.takeDamage(heal);
            
            Image potionImage = new Image("healthPot3.png");
           
            potionImage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.SCALE, 60, 1, 2));
            potionImage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.ALPHABRIGHTNESS, 60, 1, 0));
            
            Body body = new Body( new Box(10,10),1);
            body.setGravityEffected(false);
            body.setOverlapMask(Entity.OverlapMasks.NO_OVERLAP.value);
            body.setBitmask(Entity.BitMasks.NO_COLLISION.value);
            Entity potionEntity = new Entity(potionImage, body);
             potionEntity.setPosition(this.playerReference.getPosition().x, this.playerReference.getPosition().y + 150);
            potionEntity.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 61, 0, 1));
            this.playerReference.getOwningScene().add(potionEntity, Scene.Layer.MAIN);
            
            
            //if they have the modifier
            if(this.playerReference.getArmorManager().potionCooldownResetModifier.equipped == true)
            {
                this.playerReference.getSkillManager().getSkill(this.playerReference.getSkillAssignment(1)).setCooldownRemaining(0);
                this.playerReference.getSkillManager().getSkill(this.playerReference.getSkillAssignment(2)).setCooldownRemaining(0);
                if(this.playerReference.getSkillAssignment(3) != null )
                {
                    this.playerReference.getSkillManager().getSkill(this.playerReference.getSkillAssignment(3)).setCooldownRemaining(0);
                }
                if(this.playerReference.getSkillAssignment(4) != null)
                {
                    this.playerReference.getSkillManager().getSkill(this.playerReference.getSkillAssignment(4)).setCooldownRemaining(0);
                }
            }
        }
    }
    
    
    //====================
    // RenderData Methods
    //====================
     public RenderData dumpRenderData()
     {
         RenderData renderData = new RenderData();
         
         renderData.data.add(0,this.numberOfPotions);
         renderData.data.add(1,this.maxPotions);

         
         return renderData;        
     }
     
     public static PotionManager buildFromRenderData(RenderData renderData)
     {
         PotionManager currencyManager = new PotionManager(null);
         currencyManager.numberOfPotions = (int)renderData.data.get(0);
         currencyManager.maxPotions = (int)renderData.data.get(1);
         
         return currencyManager;
     }
     
     public SceneObjectRenderDataChanges generateRenderDataChanges(RenderData oldData,RenderData newData)
     {
         SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();
        
         int changeMap = 0;
         ArrayList changeList = new ArrayList();
         
         for(int i = 0; i <2; i++)
         {                  
             if(!oldData.data.get(i).equals( newData.data.get(i)))
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
                    changeData.add(null);          
            }

            if(changeData.get(0) != null)         
                this.numberOfPotions = (int)changeData.get(0);
            if(changeData.get(1) != null)
                this.maxPotions = (int)changeData.get(1);

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
       saveData.dataMap.put("number", numberOfPotions);
        
        return saveData;
   }
   
   
   public static PotionManager buildFromFullData(SaveData saveData)  
   {
       //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
       
       PotionManager currencyManager = new PotionManager(null);
       
       currencyManager.numberOfPotions = (int)saveData.dataMap.get("number");
       
       return currencyManager;
       
   }
}
