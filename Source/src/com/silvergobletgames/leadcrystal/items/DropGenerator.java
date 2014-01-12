
package com.silvergobletgames.leadcrystal.items;

import java.util.ArrayList;
import java.util.Random;
import com.silvergobletgames.leadcrystal.entities.ItemEntity;
import com.silvergobletgames.sylver.util.SylverRandom;

/**
 *
 * @author Mike
 */
public class DropGenerator {
    
    /**
     * Enum that describes the currency drop chance
     */
    public static enum DropChance
    { 
        //WARNING - Changing the names of this enum will break save data
        NONE(0), LOW(0.20f), NORMAL(0.35f),  HIGH(0.60f), VERYHIGH(0.75f), ALWAYS(1);

        float percentChance;

        DropChance(float c)
        {
            percentChance = c;
        }
    
    }
    
    /**
     * repurposed as the drop chance of a potion
     */
    public static enum DropQuality
    {
        VeryPoor, // 0%
        Poor, //5%
        Good, //20%
        VeryGood; //100%
    }
    
    
    //====================
    // Generation Methods
    //====================
    
    public static ArrayList<ItemEntity> generateDrops(DropQuality quality,DropChance chance)
    {
        //the return list of drops
        ArrayList<ItemEntity> drops = new ArrayList();
        
        //=======================
        // Roll Currency Drops
        //=======================
        //generate the amount of currency that will drop
        int numberOfCurrency = rollAmountOfCurrency(chance.percentChance);
        
        //build the currency
        for(int i = 0; i < numberOfCurrency; i++)
        {
            //build
            ItemEntity drop = new Currency(1);
            
            //add drop
            drops.add(drop);
        }
        
        //=======================
        //roll if we drop a potion
        //=======================
        boolean dropAPot = false;
        float potionRoll = SylverRandom.random.nextFloat();       
        if(quality == DropQuality.VeryPoor)
        {
            if(potionRoll<= 0.00f)
                dropAPot = true;
        }
        else if(quality == DropQuality.Poor)
        {
            if(potionRoll<= 0.05f)
                dropAPot = true;
        }
        else if(quality == DropQuality.Good)
        {
            if(potionRoll<= 0.2f)
                dropAPot = true;
        }
        else if(quality == DropQuality.VeryGood)
        {
            if(potionRoll<= 1f)
                dropAPot = true;
        }
        
        if(dropAPot == true)
        {
            drops.add(new Potion());
        }
        
        
        //return the drops
        return drops;
        
    }
    
    /**
     * Rolls the amount of currency
     * @param chance
     * @return 
     */
    private static int rollAmountOfCurrency(float chance)
    {
        boolean done = false;
        int count = 0;
        Random r = SylverRandom.random;

        while (!done){

           //Loop until we stop adding additional items
           if (r.nextFloat() < chance){
               chance *= .75;
                count++;
           }
           else
               done = true;

        }
        return count;
    }
}
