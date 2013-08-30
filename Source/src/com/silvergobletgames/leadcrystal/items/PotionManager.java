
package com.silvergobletgames.leadcrystal.items;

import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.netcode.SaveData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderDataChanges;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Mike
 */
public class PotionManager 
{
    public PlayerEntity playerReference;
    private int numberOfPotions;
    
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
    
    public int getNumberOfPotions()
    {
        return this.numberOfPotions;
    }
    
    public void usePotion()
    {
        if(this.numberOfPotions > 0)
        {
            this.numberOfPotions--;
            
            //do the heal
            Damage heal = new Damage(Damage.DamageType.HEAL, 50);
            this.playerReference.takeDamage(heal);
        }
    }
    
    
    //====================
    // RenderData Methods
    //====================
     public RenderData dumpRenderData()
     {
         RenderData renderData = new RenderData();
         
         renderData.data.add(0,this.numberOfPotions);

         
         return renderData;        
     }
     
     public static PotionManager buildFromRenderData(RenderData renderData)
     {
         PotionManager currencyManager = new PotionManager(null);
         currencyManager.numberOfPotions = (int)renderData.data.get(0);
         
         return currencyManager;
     }
     
     public SceneObjectRenderDataChanges generateRenderDataChanges(RenderData oldData,RenderData newData)
     {
         SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();
        
         int changeMap = 0;
         ArrayList changeList = new ArrayList();
         
         for(int i = 0; i <1; i++)
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
            for(byte i = 0; i <1; i ++)
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
