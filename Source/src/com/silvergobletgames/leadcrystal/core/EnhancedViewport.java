package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.Viewport;
import java.util.ArrayList;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.MobSpawner;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.util.SylverVector2f;
import net.phys2d.raw.shapes.Line;


public class EnhancedViewport extends Viewport{
    
    
    //collision lines
    public Line topLine;
    public Line bottomLine;
    public Line leftLine;
    public Line rightLine;
    
    public EnhancedViewport()
    {       
        
        //lines
        topLine = new Line(new Vector2f(getCenterCoordinates().x,yPos + height - 200),new Vector2f(getCenterCoordinates().x, getCenterCoordinates().y));
        bottomLine = new Line(new Vector2f(getCenterCoordinates().x, getCenterCoordinates().y),new Vector2f(getCenterCoordinates().x,yPos +200));
        leftLine = new Line(new Vector2f(getCenterCoordinates().x, getCenterCoordinates().y),new Vector2f(xPos + 150,getCenterCoordinates().y));
        rightLine = new Line(new Vector2f(xPos + width - 150,getCenterCoordinates().y),new Vector2f(getCenterCoordinates().x, getCenterCoordinates().y));
    }
    
    protected void setPosition(float x, float y)
    {
        super.setPosition(x,y);
        
        //lines
        topLine.set(new Vector2f(getCenterCoordinates().x,yPos + height - 200), new Vector2f(getCenterCoordinates().x, getCenterCoordinates().y));
        bottomLine.set(new Vector2f(getCenterCoordinates().x, getCenterCoordinates().y),new Vector2f(getCenterCoordinates().x,yPos +200));
        leftLine.set(new Vector2f(getCenterCoordinates().x, getCenterCoordinates().y),new Vector2f(xPos + 150,getCenterCoordinates().y));
        rightLine.set(new Vector2f(xPos + width - 150,getCenterCoordinates().y),new Vector2f(getCenterCoordinates().x, getCenterCoordinates().y));
    }

    
    public void snapToCoordinateWithCorrection(float x, float y, ArrayList<Entity> viewportBlockers)
    {
         //initially set position to given coordinates
        this.setPosition(x - this.width/2, y - this.height/2);
        
        //for all viewport blockers
        for(Entity blocker: viewportBlockers)
        {
            //while there is an intersection with the topline, move down
            while(determineIntersectionWithBlocker(topLine,blocker))
            {
                this.setPosition(xPos, yPos -1); 
            }
            //while there is an intersection with the bottom line, move up
            while(determineIntersectionWithBlocker(bottomLine,blocker))
            {
                this.setPosition(xPos, yPos +1); 
            }
            //while there is an intersection with the leftLine, move right
            while(determineIntersectionWithBlocker(leftLine,blocker))
            {
                this.setPosition(xPos + 1, yPos ); 
            }
            //while there is an intersection with the rightLine, move left
            while(determineIntersectionWithBlocker(rightLine,blocker))
            {
                this.setPosition(xPos - 1, yPos ); 
            }
        }
        
       
    }
    
    public void moveToCoordinateWithCorrection(float x, float y, ArrayList<Entity> viewportBlockers)
    {
         //initially set position to given coordinates
        super.centerAroundPoint(new SylverVector2f(x,y));
          
        
        //for all viewport blockers
        for(Entity blocker: viewportBlockers)
        {
            //while there is an intersection with the topline, move down
            while(determineIntersectionWithBlocker(topLine,blocker))
            {
                this.setPosition(xPos, yPos -1); 
            }
            //while there is an intersection with the bottom line, move up
            while(determineIntersectionWithBlocker(bottomLine,blocker))
            {
                this.setPosition(xPos, yPos +1); 
            }
            //while there is an intersection with the leftLine, move right
            while(determineIntersectionWithBlocker(leftLine,blocker))
            {
                this.setPosition(xPos + 1, yPos ); 
            }
            //while there is an intersection with the rightLine, move left
            while(determineIntersectionWithBlocker(rightLine,blocker))
            {
                this.setPosition(xPos - 1, yPos ); 
            }
        }
        
       
    }
    

    
    private boolean determineIntersectionWithBlocker(Line line, Entity viewportBlocker)
    {
       SylverVector2f blockerPosition = viewportBlocker.getPosition();
       Vector2f[] blockerPoints = ((Box)viewportBlocker.getBody().getShape()).getPoints(new Vector2f(blockerPosition.x,blockerPosition.y), viewportBlocker.getBody().getRotation());
       
       Line blockerBottom = new Line(blockerPoints[0],blockerPoints[1]);
       Line blockerRight = new Line(blockerPoints[1],blockerPoints[2]);
       Line blockerTop =new Line(blockerPoints[2],blockerPoints[3]);
       Line blockerLeft = new Line(blockerPoints[3],blockerPoints[0]);
       
       Vector2f bottomIntersection =line.intersect(blockerBottom);
       if(bottomIntersection != null && bottomIntersection.x > blockerBottom.getStart().getX() && bottomIntersection.x < blockerBottom.getEnd().getX() && bottomIntersection.y < line.getStart().getY() && bottomIntersection.y > line.getEnd().getY())
           return true; 
       
       Vector2f topIntersection =line.intersect(blockerTop);
       if(topIntersection != null && topIntersection.x < blockerTop.getStart().getX() && topIntersection.x > blockerTop.getEnd().getX() && topIntersection.y < line.getStart().getY() && topIntersection.y > line.getEnd().getY())
           return true;
       
       Vector2f rightIntersection = line.intersect(blockerRight);
       if(rightIntersection != null && rightIntersection.y > blockerRight.getStart().getY() && rightIntersection.y < blockerRight.getEnd().getY() && rightIntersection.x < line.getStart().getX() && rightIntersection.x > line.getEnd().getX())
           return true;
      
       Vector2f leftIntersection = line.intersect(blockerLeft);
       if(leftIntersection != null && leftIntersection.y < blockerLeft.getStart().getY() && leftIntersection.y > blockerLeft.getEnd().getY() && leftIntersection.x < line.getStart().getX() && leftIntersection.x > line.getEnd().getX())
           return true;
       
       return false;
    }
    
    public boolean isSceneObjectVisible(SceneObject object, Layer layer)
    {
        
        SylverVector2f positionOfObj = new SylverVector2f(0,0);
        float widthOfObj =0;
        float heightOfObj =0;
        float maxDimension = 0;
        
        
        if(object instanceof Entity)
        {
            if(object instanceof MobSpawner)
            {
                return true;
            }
            
            positionOfObj = new SylverVector2f(((Entity)object).getImage().getPosition());
            widthOfObj = ((Entity)object).getImage().getWidth() * ((Entity)object).getImage().getScale();
            heightOfObj = ((Entity)object).getImage().getHeight() * ((Entity)object).getImage().getScale();        
        }       
        else if(object instanceof Image)
        {
            positionOfObj = new SylverVector2f(((Image)object).getPosition());
            widthOfObj = ((Image)object).getWidth() * ((Image)object).getScale();
            heightOfObj = ((Image)object).getHeight() * ((Image)object).getScale();
        }
        else if(object instanceof Text)
        {
            positionOfObj = new SylverVector2f(object.getPosition());
            widthOfObj = ((Text)object).getWidth() * ((Text)object).getScale();
            heightOfObj = ((Text)object).getHeight() * ((Text)object).getScale();
        }
        else if(object instanceof AbstractParticleEmitter)
        {
            positionOfObj = new SylverVector2f(object.getPosition());
            widthOfObj = 2000;
            heightOfObj = 2000; //TODO - more accurately figure this out
        }
        else if(object instanceof LightSource)
        {
            positionOfObj = new SylverVector2f(object.getPosition());           
            widthOfObj = ((LightSource)object).getSize();
            heightOfObj = widthOfObj;     
        }
        else if(object instanceof DarkSource)
        {
            positionOfObj = new SylverVector2f(object.getPosition());
            widthOfObj = ((DarkSource)object).getWidth();
            heightOfObj = ((DarkSource)object).getHeight();
        }
        
        
        
        //determine max dimension of obj
        maxDimension =(float)Math.sqrt(Math.pow(widthOfObj, 2) + Math.pow(heightOfObj, 2));
        
        //coordinate transform of position based on layer      
        SylverVector2f adjustedViewportPos = new SylverVector2f(this.xPos,this.yPos);
        adjustedViewportPos.scale(Layer.getLayerConversionFactor(Layer.MAIN, layer));
        SylverVector2f adjustedViewportDimensions = new SylverVector2f(this.getWidth(),this.getHeight());
        
        if(positionOfObj.x + maxDimension >= adjustedViewportPos.x && positionOfObj.x - maxDimension  <= adjustedViewportPos.x + adjustedViewportDimensions.x&&
           positionOfObj.y + maxDimension >= adjustedViewportPos.y && positionOfObj.y - maxDimension  <= adjustedViewportPos.y + adjustedViewportDimensions.y)
            return true;
        else
            return false;
        
    }
    
    
}
