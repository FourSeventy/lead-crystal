package com.silvergobletgames.leadcrystal.entities;


import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.core.SceneObjectManager;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.LightSource;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import com.silvergobletgames.sylver.netcode.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.Body;
import net.phys2d.raw.StaticBody;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectClasses;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.RockChipsEmitter;
import com.silvergobletgames.leadcrystal.entities.EntityTooltip.EntityTooltipField;
import com.silvergobletgames.leadcrystal.scripting.ScriptObject;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.graphics.ShadowCaster;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.ConvexPolygon;
import net.phys2d.raw.shapes.Polygon;



public class WorldObjectEntity extends Entity implements SavableSceneObject, ShadowCaster
{
    
    //if this object blocks viewport movement
    protected boolean viewportBlocker;
    //if this object casts shadows
    protected boolean shadowCaster;
    //if this object is a ladder
    protected boolean ladder;
    //if this object is a jumpthrough platform
    protected boolean jumpthrough;
    //what type of world object this is
    protected WorldObjectType worldObjectType;
    //what material type is this world object
    protected MaterialType materialType;


    //world object type enum
    public enum WorldObjectType 
    {
        //WARNING - Changing the names of this enum will break save data
         TERRAIN,PRIMARYOBJECTIVE,SECONDARYOBJECTIVE,NONE;
    }
    
    public enum MaterialType
    {
        SAND,ROCK,CONCRETE,METAL,NONE
    }
            
    

    //================
    // Constructors
    //================           

    /**
     * Creates a world object that has some standard parameters for a floor or a wall type object in terms of the booleans.
     * Stops viewport, doesn't cast shadows, collides, and is static
     */
    public WorldObjectEntity(Image image, Body body) 
    {
        super(image,body);
        
        viewportBlocker = false;
        shadowCaster = false;
        worldObjectType = WorldObjectType.NONE;
        materialType = MaterialType.NONE;
        

        if(body != null)
        {
            this.body.setRestitution(.3f);
        }
        
        //entity tooltip
        this.entityTooltip = new EntityTooltip(EntityTooltipField.NAME);      
        
    }

    
    //=====================
    // Scene Object Methods
    //=====================    

    public void collidedWith(Entity other, CollisionEvent event) 
    {
        super.collidedWith(other, event);
        
        //=======================
        //Add collision emitters
        //=======================
        if(this.materialType == MaterialType.SAND && (other instanceof CombatEntity || other instanceof HitBox))
        {
            float randomAdjust = 0;
            if(other instanceof HitBox)
                randomAdjust = .15f;
            
            if(SylverRandom.random.nextFloat() < (.25f + randomAdjust) ||(Math.abs(other.getBody().getLastVelocity().getX() - other.getBody().getLastVelocity().getY()) < 10 && other instanceof PlayerEntity))
            {
                AbstractParticleEmitter sand = new LeadCrystalParticleEmitters.SandSpurtEmitter();
                sand.setDuration(5);
                sand.setPosition(event.getPoint().getX(), event.getPoint().getY());
                this.getOwningScene().add(sand, Scene.Layer.MAIN);
            }
        }
        else if(this.materialType == MaterialType.ROCK && other instanceof HitBox)
        {
            if(SylverRandom.random.nextFloat() < .2f)
            {
                AbstractParticleEmitter rock = new RockChipsEmitter();
                rock.setDuration(5);
                rock.setPosition(event.getPoint().getX(), event.getPoint().getY());
                this.getOwningScene().add(rock, Scene.Layer.MAIN);
            }
        }
    }
    
    public void update()           
    {
        //rounds off some body fields so they dont get sent in render data for minute changes    
        float rotation = body.getRotation();
        BigDecimal bd = new BigDecimal(rotation).setScale(5, RoundingMode.HALF_EVEN);
        rotation = bd.floatValue();
        this.body.setRotation(rotation); 
        
        
        super.update();
      
    }

    
    //==================
    // Accessor Methods
    //==================
    
    public WorldObjectType getWorldObjectType()
    {
        return worldObjectType;
    }
    
    public void setWorldObjectType(WorldObjectType type)
    {
        this.worldObjectType = type;
        
        // if terrain
        if(type == WorldObjectType.TERRAIN)      
            this.addToGroup(ExtendedSceneObjectGroups.TERRAIN);     
        else if(this.isInGroup(ExtendedSceneObjectGroups.TERRAIN))     
            this.removeFromGroup(ExtendedSceneObjectGroups.TERRAIN); 
        
        //if primary objective
        if(type == WorldObjectType.PRIMARYOBJECTIVE)      
            this.addToGroup(ExtendedSceneObjectGroups.PRIMARYOBJECTIVE);     
        else if(this.isInGroup(ExtendedSceneObjectGroups.PRIMARYOBJECTIVE))     
            this.removeFromGroup(ExtendedSceneObjectGroups.PRIMARYOBJECTIVE); 
        
        //if secondary objective
        if(type == WorldObjectType.SECONDARYOBJECTIVE)      
            this.addToGroup(ExtendedSceneObjectGroups.SECONDARYOBJECTIVE);     
        else if(this.isInGroup(ExtendedSceneObjectGroups.SECONDARYOBJECTIVE))     
            this.removeFromGroup(ExtendedSceneObjectGroups.SECONDARYOBJECTIVE); 
                  
    }
    
    public MaterialType getMaterialType()
    {
        return this.materialType;
    }
    
    public void setMaterialType(MaterialType mat)
    {
        this.materialType = mat;
    }

    public boolean isShadowCaster()
    {
        return this.shadowCaster;
    }

    public void setShadowCaster(boolean shadowc) 
    {
        shadowCaster = shadowc;
        
        if (shadowCaster) 
            this.addToGroup(CoreGroups.SHADOWCASTER);
        else if(this.isInGroup(CoreGroups.SHADOWCASTER))
            this.removeFromGroup(CoreGroups.SHADOWCASTER); 
        
    }

    public boolean isViewportBlocker()
    {
        return this.viewportBlocker;
    }
    
    public void setViewportBlocker(boolean viewportb) 
    {
        viewportBlocker = viewportb;
        
        if(viewportb == true)
            this.addToGroup(ExtendedSceneObjectGroups.VIEWPORTBLOCKER);
        else if(this.isInGroup(ExtendedSceneObjectGroups.VIEWPORTBLOCKER))
            this.removeFromGroup(ExtendedSceneObjectGroups.VIEWPORTBLOCKER);
    }
    
    public boolean isLadder()
    {
        return this.ladder;
    }
    
    public void setLadder(boolean value)
    {
        this.ladder = value;
    }
    
    public boolean isJumpthrough()
    {
        return this.jumpthrough;
    }
    
    public void setJumpthrough(boolean value)
    {
        this.jumpthrough = value;
    }
    
    public SylverVector2f[] getCorners()
    {
              
        Vector2f[] physCorners;
        if (this.getBody().getShape() instanceof Polygon) 
        {
            SylverVector2f position = this.getPosition();
            physCorners = ((Polygon) this.getBody().getShape()).getVertices(new Vector2f(position.x,position.y), this.getBody().getRotation());
        } 
        else //(entity.getBody().getShape() instanceof Box)
        {
            SylverVector2f position = this.getPosition();
            physCorners = ((Box) this.getBody().getShape()).getPoints(new Vector2f(position.x,position.y), this.getBody().getRotation());
        }
        
        SylverVector2f[] returnCorners = new SylverVector2f[physCorners.length];
        
        for(int i = 0; i < physCorners.length; i++)
        {
            returnCorners[i] = new SylverVector2f(physCorners[i].getX(),physCorners[i].getY());
        }
        
        return returnCorners;
    }
    
    
    //====================
    // Render Data Methods
    //====================     
    
    public SceneObjectRenderData dumpRenderData() 
    {
        SceneObjectRenderData renderData = new SceneObjectRenderData(ExtendedSceneObjectClasses.WORLDOBJECTENTITY,this.ID);
               
        renderData.data.add(0,this.image.dumpRenderData());
        renderData.data.add(1, this.light != null ? this.light.dumpRenderData() : null);
        renderData.data.add(2,this.getBody().getPosition().getX());
        renderData.data.add(3,this.getBody().getPosition().getY());
        
        
        return renderData;
    }
    
    public static WorldObjectEntity buildFromRenderData(SceneObjectRenderData renderData)
    {
        Body body = Entity.buildBodyFromRenderData((RenderData)renderData.data.get(2));
        Image image = Image.buildFromRenderData((SceneObjectRenderData)renderData.data.get(0));
        WorldObjectEntity worldObject = new WorldObjectEntity(image,body);
        
        if(renderData.data.get(1) != null)
            worldObject.setLight(LightSource.buildFromRenderData((SceneObjectRenderData)renderData.data.get(1)));
        
        worldObject.setID(renderData.getID());              
        
        return worldObject;
    }
    
    public SceneObjectRenderDataChanges generateRenderDataChanges(SceneObjectRenderData oldData,SceneObjectRenderData newData)
    {
        SceneObjectRenderDataChanges changes = new SceneObjectRenderDataChanges();
        
        int changeMap = 0;
        changes.ID = this.ID;
        ArrayList changeList = new ArrayList();
        
        if(this.image != null)
        {
            SceneObjectRenderDataChanges imageChanges = this.image.generateRenderDataChanges((SceneObjectRenderData)oldData.data.get(0), (SceneObjectRenderData)newData.data.get(0));
            if(imageChanges != null)
            {
                changeList.add(imageChanges);
                changeMap += 1;
            }
        }
        
        if(this.light != null)
        {
            SceneObjectRenderDataChanges lightChanges = this.light.generateRenderDataChanges((SceneObjectRenderData)oldData.data.get(1), (SceneObjectRenderData)newData.data.get(1));
            if(lightChanges != null)
            {
                changeList.add(lightChanges);
                changeMap += 2;
            }
        }
           
        
        for(int i = 2; i <=3; i++)
        {
            if(!oldData.data.get(i).equals( newData.data.get(i)))
            {
                changeList.add(newData.data.get(i));
                changeMap += 1 << i;
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
        for(int i = 0; i <=3; i ++)
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
        
        
        if(this.light != null && changeData.get(1) != null)
            this.light.reconcileRenderDataChanges(lastTime,futureTime,(SceneObjectRenderDataChanges)changeData.get(1)); 
        
        float x = this.getPosition().getX();
        float y = this.getPosition().getY();
        if(changeData.get(2) != null) 
        {
            x = (float)changeData.get(2);
        }
        if(changeData.get(3) != null) 
        {
            y = (float)changeData.get(3);
        }
        
        this.setPosition(x, y);
                
    }
    
    public void interpolate(long currentTime)
    {
        
        if(image != null)
            image.interpolate(currentTime);

        if(light != null)
            light.interpolate(currentTime);
    }
    
    

    //====================
    // Saving Methods
    //==================== 
    
    
    public SceneObjectSaveData dumpFullData() 
    {       
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
        SceneObjectSaveData saveData = new SceneObjectSaveData(ExtendedSceneObjectClasses.WORLDOBJECTENTITY,this.ID);
        
        saveData.dataMap.put("image", this.image.dumpFullData());
        saveData.dataMap.put("body", Entity.dumpBodySavable(this.body));
        saveData.dataMap.put("viewport", this.viewportBlocker);
        saveData.dataMap.put("shadow", this.shadowCaster);
        saveData.dataMap.put("type", this.worldObjectType.name());
        saveData.dataMap.put("script", this.scriptObject != null ? this.scriptObject.dumpFullData() : null);
        saveData.dataMap.put("emitters", !this.emitters.isEmpty() ? this.emitters.get(0).dumpFullData() : null);
        saveData.dataMap.put("light", this.light != null ? this.light.dumpFullData() : null);
        saveData.dataMap.put("name",this.getName());
        saveData.dataMap.put("ladder",this.ladder);
        saveData.dataMap.put("jumpthrough",this.jumpthrough);
        saveData.dataMap.put("imageOffsetX",this.imageOffset.x);
        saveData.dataMap.put("imageOffsetY",this.imageOffset.y);
        saveData.dataMap.put("material", this.materialType.name());

        return saveData;
    }
    
    
    public static SceneObject buildFromFullData(SceneObjectSaveData saveData) 
    {
        
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
        
        SceneObjectSaveData imgData = (SceneObjectSaveData) saveData.dataMap.get("image"); 
        SaveData bodyData = (SaveData) saveData.dataMap.get("body");
        SceneObjectSaveData emitterData = (SceneObjectSaveData) saveData.dataMap.get("emitters");
        SceneObjectSaveData lightData = (SceneObjectSaveData) saveData.dataMap.get("light");
        boolean viewportInhibitor = (Boolean) saveData.dataMap.get("viewport");
        boolean shadowCaster = (Boolean) saveData.dataMap.get("shadow");
        WorldObjectType terrainType = WorldObjectType.valueOf((String) saveData.dataMap.get("type"));
        SaveData scriptObjData = (SaveData)saveData.dataMap.get("script");
        String name = (String)saveData.dataMap.get("name");        
        boolean ladder = (Boolean)saveData.dataMap.get("ladder");   
        boolean jumpthrough= (Boolean)saveData.dataMap.get("jumpthrough");
        
        MaterialType material = MaterialType.NONE;
        if(saveData.dataMap.containsKey("material"))
            material = MaterialType.valueOf((String)saveData.dataMap.get("material"));
        
        Float imageOffsetX = (Float)saveData.dataMap.get("imageOffsetX");
        Float imageOffsetY = (Float)saveData.dataMap.get("imageOffsetY");

        
        //set image and body
        Image image = (Image) Image.buildFromFullData(imgData);
        Body body = (Body) Entity.buildBodyFromSavedData(bodyData);
        WorldObjectEntity built = new WorldObjectEntity(image, body);
        built.setViewportBlocker(viewportInhibitor);      
        
        //set world object properties
        built.setShadowCaster(shadowCaster);
        built.setWorldObjectType(terrainType);
        built.setLadder(ladder);
        built.setJumpthrough(jumpthrough);
        built.setMaterialType(material);
        
        //set offset
        if(imageOffsetX != null)       
            built.imageOffset.set(imageOffsetX, imageOffsetY);
        else
            built.imageOffset.set(0, 0);
        
        
        //set emitter and light data
        if (emitterData != null) {
            try{
                AbstractParticleEmitter emitter = (AbstractParticleEmitter) SceneObjectDeserializer.buildSceneObjectFromSaveData(emitterData);
                built.addEmitter(emitter);
            }
            catch(Exception e)
            {
                
            }
            
        }
        if (lightData != null) {
            LightSource light = (LightSource) LightSource.buildFromFullData(lightData);
            built.setLight(light);
        }

        //Script object data
        if (scriptObjData !=  null)
        {
            ScriptObject sObj = ScriptObject.buildFromFullData(scriptObjData);
            built.setScriptObject(sObj);
        }
		
        
        built.ID =(String)saveData.dataMap.get("id");
        built.setName(name);
        built.setPosition(body.getPosition().getX(), body.getPosition().getY());
        
        return built;
    }

    
    
  
    
}
