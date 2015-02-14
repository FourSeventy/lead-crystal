
package com.silvergobletgames.leadcrystal.items;

import com.silvergobletgames.sylver.netcode.SaveData;

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
