package com.silvergobletgames.leadcrystal.entities;


import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.util.LinearInterpolator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
            if ((this.specialDisplay))
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
    
   

}
