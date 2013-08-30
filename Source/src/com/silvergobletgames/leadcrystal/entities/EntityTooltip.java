package com.silvergobletgames.leadcrystal.entities;


import com.jogamp.opengl.util.texture.Texture;
import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderDataChanges;
import com.silvergobletgames.sylver.util.LinearInterpolator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import javax.media.opengl.GL3bc;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.Entity;
import javax.media.opengl.GL2;


/**
 * Class Tooltip information about the Entity that it is hovered over.
 */
public class EntityTooltip 
{
    //Name field
    private Text name;
    
    //position
    private float x;
    private float y;
    protected ArrayList<LinearInterpolator> interpolators = new ArrayList<>();
    
    //Health field
    private Image healthFill;   
    private Image healthOutline;
    private float percentHealth;   
       
    //the set of fields this tooltip has
    private HashSet<EntityTooltipField> tooltipFields;
    
    //special display flag
    public boolean specialDisplay =  false;
    public boolean specialDoNotDisplay = false;
    
    //tooltip fields
    public static enum EntityTooltipField{
        NAME,HEALTH
    }
    
    
    //=============
    // Constructor
    //=============
    
    /**
     * Constructs a new EntityToolTIp with the given fields
     */
    public EntityTooltip(EntityTooltipField... fields)
    {
        //set what fields this tooltip has
        tooltipFields = new HashSet(Arrays.asList(fields));
        
        if(tooltipFields.contains((EntityTooltipField.NAME)))
        {
            name = new Text();
            name.setAnchor(Anchorable.Anchor.CENTER);
            name.setScale(.8f);
        }       
        
        if(tooltipFields.contains(EntityTooltipField.HEALTH))
        {
            healthFill = new Image("statbar.png");
            healthFill.setColor(new Color(Color.red));
            healthOutline = new Image("healthoutline.png");
            healthOutline.setDimensions(52, 5);
        }

    }
    
    /**
     * 
     */
    public void update()
    {
        //health fill
        if(tooltipFields.contains(EntityTooltipField.HEALTH))
        {
            float width = 52 * this.percentHealth;
            float height = 6;      
            healthFill.setDimensions(width, height); 
            healthFill.update();
        }
        

    }
      
    /**
     * Positions and draws the various components of the HUD.
     * @param gl 
     */
    public void draw(GL2 gl)
    {    
        if(!specialDoNotDisplay)
        {
            if ((this.specialDisplay && GameplaySettings.getInstance().showHealthBars))
            {
                //draw name component
                if(tooltipFields.contains((EntityTooltipField.NAME))&& (!tooltipFields.contains(EntityTooltipField.HEALTH) || this.percentHealth > 0)) 
                {
                    name.draw(gl);
                }

                //draw health component
                if(tooltipFields.contains(EntityTooltipField.HEALTH) && this.percentHealth > 0)
                {                
                    healthFill.draw(gl);
                    healthOutline.draw(gl);
                }     
            }
        }
        
    }
    
    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
        
        if(tooltipFields.contains((EntityTooltipField.NAME))) 
        {
            if(tooltipFields.contains(EntityTooltipField.HEALTH))
                name.setPositionAnchored(x, y + 30);
            else
                name.setPositionAnchored(x,y+10);
        }
        
        if(tooltipFields.contains(EntityTooltipField.HEALTH))
        {
            healthFill.setPosition(x-healthOutline.getWidth()/2,y );
            healthOutline.setPosition(x-healthOutline.getWidth()/2,y );
        }
        
        
        
    }
    
    public void setNameField(String name)
    {
        this.name.setText(name);
    }
    
    public void setHealthField(float percent)
    {
        this.percentHealth = percent;
    }
    
    
    //====================
    // RenderData Methods
    //====================
     public RenderData dumpRenderData()
     {
         RenderData renderData = new RenderData();
         
         renderData.data.add(0,this.name.toString());
         renderData.data.add(1,this.percentHealth);
         renderData.data.add(2,1);
         renderData.data.add(3,this.x);
         renderData.data.add(4,this.y);
         renderData.data.add(5,new ArrayList(Arrays.asList(this.tooltipFields.toArray())));
         renderData.data.add(6,specialDisplay);
         renderData.data.add(7,specialDoNotDisplay);
         
         return renderData;        
     }
     
     public static EntityTooltip buildFromRenderData(RenderData renderData)
     {
         ArrayList fields = (ArrayList)renderData.data.get(5);
         EntityTooltipField[] array = new EntityTooltipField[fields.size()];
         for(int i = 0; i < fields.size(); i ++)
             array[i] = (EntityTooltipField)fields.get(i);

         
         EntityTooltip tooltip = new EntityTooltip(array);
         
         tooltip.setNameField((String)renderData.data.get(0));
         tooltip.setHealthField((float)renderData.data.get(1));
         tooltip.setPosition((float)renderData.data.get(3),(float)renderData.data.get(4));
         tooltip.specialDisplay = (boolean)renderData.data.get(6);
         tooltip.specialDoNotDisplay = (boolean)renderData.data.get(7);
         
         return tooltip;
     }
     
     public SceneObjectRenderDataChanges generateRenderDataChanges(RenderData oldData,RenderData newData)
     {
         SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();
        
         int changeMap = 0;
         ArrayList changeList = new ArrayList();
         
         for(int i = 0; i <5; i++)
         {                  
             if(!oldData.data.get(i).equals( newData.data.get(i)))
             {                 
                 changeList.add(newData.data.get(i));
                 changeMap += 1L << i;
             }
         }
         
        if(!oldData.data.get(6).equals( newData.data.get(6)))
        {                 
            changeList.add(newData.data.get(6));
            changeMap += 1L << 6;
        }
        if(!oldData.data.get(7).equals( newData.data.get(7)))
        {                 
            changeList.add(newData.data.get(7));
            changeMap += 1L << 7;
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
                this.setNameField((String)changeData.get(0));

            if(changeData.get(1) != null)        
                this.setHealthField((float)changeData.get(1));
            
            this.update();
            
            //clear old interpolators
            this.interpolators.clear();

            //x position interpolator
            if(changeData.get(3) != null)
            {
                LinearInterpolator lerp1= new LinearInterpolator(this.x,(float)changeData.get(3),lastTime,futureTime);
                this.interpolators.add(lerp1);
            }
            else
                this.interpolators.add(null);

            //y position interpolator
            if(changeData.get(4) != null)
            {
                LinearInterpolator lerp2= new LinearInterpolator(this.y,(float)changeData.get(4),lastTime,futureTime);
                this.interpolators.add(lerp2);
            }
            else
                this.interpolators.add(null);
            
            
            if(changeData.get(6) != null)
                this.specialDisplay = (boolean)changeData.get(6);
            
            if(changeData.get(7) != null)
                this.specialDoNotDisplay = (boolean)changeData.get(7);
          

     }
     
     public void interpolate(long currentTime)
     {
         if(!this.interpolators.isEmpty())
         {
            float x= this.x;
            float y = this.y; 

            if(this.interpolators.get(0) != null&& currentTime <= this.interpolators.get(0).getb())
                 x = (float)this.interpolators.get(0).interp(currentTime);
        
            if(this.interpolators.get(1) != null&& currentTime <= this.interpolators.get(1).getb())
                y = (float)this.interpolators.get(1).interp(currentTime); 

            this.setPosition(x, y);
         }
     }
    
    
    
    

}
