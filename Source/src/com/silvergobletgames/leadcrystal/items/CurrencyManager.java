
package com.silvergobletgames.leadcrystal.items;

import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.netcode.SaveData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderDataChanges;
import java.util.ArrayList;
import java.util.Arrays;
import com.silvergobletgames.leadcrystal.entities.EntityTooltip;

/**
 *
 * @author Mike
 */
public class CurrencyManager {
    
    private int balence;
    
    
    //============
    // Constructor
    //============
    
    public CurrencyManager()
    {
        
    }
    
    
    
    //===============
    // Class Methods
    //===============
    
    public void addCurrency(int amount)
    {
        this.balence += amount;
    }
    
    
    public boolean subtractCurrency(int amount)
    {
        if(this.balence >= amount)
        {
            this.balence -= amount;
            return true;
        }
        else
            return false;
    }
    
    public int getBalence()
    {
        return this.balence;
    }
    
    
    //====================
    // RenderData Methods
    //====================
     public RenderData dumpRenderData()
     {
         RenderData renderData = new RenderData();
         
         renderData.data.add(0,this.balence);

         
         return renderData;        
     }
     
     public static CurrencyManager buildFromRenderData(RenderData renderData)
     {
         CurrencyManager currencyManager = new CurrencyManager();
         currencyManager.balence = (int)renderData.data.get(0);
         
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
                this.balence = (int)changeData.get(0);

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
       saveData.dataMap.put("balence", balence);
        
        return saveData;
   }
   
   
   public static CurrencyManager buildFromFullData(SaveData saveData)  
   {
       //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
       
       CurrencyManager currencyManager = new CurrencyManager();
       
       currencyManager.balence = (int)saveData.dataMap.get("balence");
       
       return currencyManager;
       
   }
    
}
