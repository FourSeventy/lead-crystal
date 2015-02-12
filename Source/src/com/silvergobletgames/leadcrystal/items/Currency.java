package com.silvergobletgames.leadcrystal.items;

import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderDataChanges;
import com.silvergobletgames.sylver.netcode.SceneObjectSaveData;
import java.util.ArrayList;
import java.util.Arrays;
import com.silvergobletgames.leadcrystal.entities.ItemEntity;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectClasses;
import com.silvergobletgames.leadcrystal.entities.EntityTooltip;
import net.phys2d.raw.shapes.Box;

/**
 *
 * @author Mike
 */
public final class Currency extends ItemEntity
{
    //amount
    private int amount;
    
    //================
    // Constructor
    //================
    
    protected Currency(int amount)
    {
        super(new Image("goldCoinSmall.png"));
       
        
        //set amount
        this.amount = amount;
        
        //adjust image
        this.image.setDimensions(32, 32);
        //adjust body
        this.body.set(new Box(27,27),3f); 
        this.body.setRestitution(.5f);
        
    }
    
    //================
    // Class Methods
    //================
    
    public int getAmount()
    {
        return amount;
    }
    
    public void setAmount(int amount)
    {
        this.amount = amount;
    }
    
    //===================
    //Render Data Methods
    //===================
    
    public SceneObjectRenderData dumpRenderData() 
    {
        SceneObjectRenderData renderData = new SceneObjectRenderData(ExtendedSceneObjectClasses.CURRENCY,this.ID);
        
        renderData.data.add(0,this.image.dumpRenderData());
        renderData.data.add(1,0);
        renderData.data.add(2,amount);
        renderData.data.add(3,this.entityTooltip.dumpRenderData());
        
        return renderData;
    }
    
    public static Currency buildFromRenderData(SceneObjectRenderData renderData)
    {
        Image image = Image.buildFromRenderData((SceneObjectRenderData)renderData.data.get(0));
        Currency currency = new Currency((int)renderData.data.get(2));
        EntityTooltip tooltip = EntityTooltip.buildFromRenderData((RenderData)renderData.data.get(3));
        currency.setTooltip(tooltip);
        currency.setID(renderData.getID());
        currency.setImage(image);
        
        return currency;
    }
    
    public SceneObjectRenderDataChanges generateRenderDataChanges(SceneObjectRenderData oldData,SceneObjectRenderData newData)
    {
        SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();
        
        int changeMap = 0;
        changes.ID = this.ID;
        ArrayList changeList = new ArrayList();
        
        
        SceneObjectRenderDataChanges imageChanges = this.image.generateRenderDataChanges((SceneObjectRenderData)oldData.data.get(0), (SceneObjectRenderData)newData.data.get(0));
        if(imageChanges != null)
        {
            changeList.add(imageChanges);
            changeMap += 1;
        }
        
        if(oldData.data.get(2) != newData.data.get(2))
        {
            changeList.add(newData.data.get(2));
            changeMap += 1L << 2;
        }
        
        if(entityTooltip != null)
        {
            SceneObjectRenderDataChanges tooltipChanges = this.entityTooltip.generateRenderDataChanges((RenderData)oldData.data.get(3), (RenderData)newData.data.get(3));
            if(tooltipChanges != null)
            {
                changeList.add(tooltipChanges);
                changeMap += 1<< 3;
            }
        }
                      
        changes.fields = changeMap;
        changes.data = changeList.toArray();
        
        if(changeList.size() > 0)
            return changes;
        else
            return null;
    }
    
    public void reconcileRenderDataChanges(long lastTime, long futureTime, SceneObjectRenderDataChanges renderDataChanges)
    {
         
        int fieldMap = renderDataChanges.fields;
        ArrayList rawData = new ArrayList();
        rawData.addAll(Arrays.asList(renderDataChanges.data)); 
        
        //construct an arraylist of data that we got, nulls will go where we didnt get any data
        ArrayList changeData = new ArrayList();
        for(int i = 0; i <4; i ++)
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
        
        if( this.image != null && changeData.get(0) != null)
            this.image.reconcileRenderDataChanges(lastTime,futureTime,(SceneObjectRenderDataChanges)changeData.get(0));
        
        if(this.entityTooltip != null && changeData.get(3) != null)
            this.entityTooltip.reconcileRenderDataChanges(lastTime, futureTime, (SceneObjectRenderDataChanges)changeData.get(3));
        
        if(changeData.get(2) != null)
            this.amount = (int)changeData.get(2);
    }
    
     public void interpolate(long currentTime)
    {

        if(image != null)
            image.interpolate(currentTime);

        if(entityTooltip != null)
            entityTooltip.interpolate(currentTime);
        
    }
    
    //==================
    //Save Data Methods
    //==================
    
    public SceneObjectSaveData dumpFullData() 
    {
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
        
        SceneObjectSaveData saveData = new SceneObjectSaveData(ExtendedSceneObjectClasses.CURRENCY,this.getID());
        
        saveData.dataMap.put("amount",amount);
        
        return saveData;
    }
    
    public static Currency buildFromFullData(SceneObjectSaveData data) 
    {
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
        
        Currency curency = new Currency((int)data.dataMap.get("amount"));
        
        return curency;
    }
    
}
