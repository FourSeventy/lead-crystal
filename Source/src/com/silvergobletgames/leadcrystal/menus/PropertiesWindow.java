 package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.core.SceneObject;
 import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.netcode.SavableSceneObject;
import com.silvergobletgames.sylver.netcode.SceneObjectSaveData;
import com.silvergobletgames.sylver.windowsystem.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.*;
import com.silvergobletgames.leadcrystal.ai.BrainFactory;
import com.silvergobletgames.leadcrystal.ai.BrainFactory.BrainID;
import com.silvergobletgames.leadcrystal.combat.CombatData;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.MobSpawner;
import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.leadcrystal.items.DropGenerator.DropChance;
import com.silvergobletgames.leadcrystal.items.DropGenerator.DropQuality;
import com.silvergobletgames.leadcrystal.scripting.ScriptObject;
import com.silvergobletgames.leadcrystal.scenes.MapEditorScene;
import com.silvergobletgames.leadcrystal.scenes.MapEditorScene.ItemLocation;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;
import com.silvergobletgames.leadcrystal.skills.SkillFactory;
import com.silvergobletgames.leadcrystal.combat.SoundPack.SoundPackID;
import com.silvergobletgames.leadcrystal.combat.SoundPackFactory;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.BloodEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.GreenGooEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.LaserBitsEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.SmokeEmitter;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity.MaterialType;

public class PropertiesWindow extends Menu 
{

    //selected item
    private Image selectedItemsImage;
    //arraylistof properties components
    private ArrayList<WindowComponent> propertiesComponents = new ArrayList<>();    
    //arraylist of the textbox components so we can get their values
    private ArrayList<WindowComponent> imageFields = new ArrayList<>();
    private ArrayList<WindowComponent> bodyFields = new ArrayList<>();
    private ArrayList<WindowComponent> worldObjectFields = new ArrayList<>();
    private ArrayList<WindowComponent> lightFields = new ArrayList<>();
    private ArrayList<WindowComponent> combatFields = new ArrayList<>();
    private ArrayList<WindowComponent> skillFields = new ArrayList<>();
    private ArrayList<WindowComponent> npeFields = new ArrayList<>();
    private ArrayList<WindowComponent> emitterFields = new ArrayList<>();
    private ArrayList<WindowComponent> spawnerFields = new ArrayList<>();
    
    private TabPane tabPane;
    //type selection for the current item
    private String typeSelection;
    private TextBox idTextBox;
    private Button polygonCreate;
        
    ArrayList<Vector2f> polygonVertices = new ArrayList();
    
    //save menu for objects
    Menu saveMenu;
    Button okButton;
    
    public PropertiesWindow(float x, float y) 
    {
        super("Selected Item Properties", x, y, 310, 850);
    }

    public void update()
    {
        super.update();
        
        if(this.owningScene != null && ((MapEditorScene)owningScene).selectedItem != null)
        {
            //if the the type selection is different from the type of the object
            //of the object is not located in the world, disable the ok button
            if(((MapEditorScene)this.owningScene).selectedItemLocation == ItemLocation.WORLD)
            {
                SceneObject selectedItem = ((MapEditorScene)owningScene).selectedItem;
                switch(this.typeSelection)
                {
                    case "Image": 
                        if(!(selectedItem instanceof Image))
                            this.okButton.setDisabled(true);
                        else
                            this.okButton.setDisabled(false);
                        break;

                    case "World Object": 
                        if(!(selectedItem instanceof WorldObjectEntity))
                            this.okButton.setDisabled(true);
                        else
                            this.okButton.setDisabled(false);
                        break;

                    case "NPE": 
                        if(!(selectedItem instanceof NonPlayerEntity))
                            this.okButton.setDisabled(true);
                        else
                            this.okButton.setDisabled(false);
                        break; 

                    case "Spawner":
                        if(!(selectedItem instanceof MobSpawner))
                            this.okButton.setDisabled(true);
                        else
                            this.okButton.setDisabled(false);
                        break; 
                }
                
                //enable polygon button
                if(this.polygonCreate != null)
                    this.polygonCreate.setDisabled(false);
            }
            else
            {
                this.okButton.setDisabled(true);
                //disable polygon button
                if(this.polygonCreate != null)
                    this.polygonCreate.setDisabled(true);
            }
        }
        
        
            
    }
    
    /**
     * When the selected world object changes in the mapEditorScene
     * this method gets called. This method is responsible for refreshing
     * all of the information in the window and calling helper methods to
     * build all the tabs and fields
     */
    public void selectedItemChanged()
    {
        //setting local selected item variable
        SceneObject selectedItem = ((MapEditorScene)owningScene).selectedItem;

        //remove old components
        for (WindowComponent wc : propertiesComponents) {
            this.removeComponent(wc);
        }
        propertiesComponents.clear();
        imageFields.clear();
        worldObjectFields.clear();
        bodyFields.clear();
        combatFields.clear();
        skillFields.clear();
        lightFields.clear();
        npeFields.clear();
        emitterFields.clear();
        spawnerFields.clear();

        if(selectedItem != null)
        {
           
            //case statement to determine what type of object we have
            //build propreties based on that
            if (selectedItem instanceof Image) 
            {
                //set the selectedItems image
                this.selectedItemsImage = (Image)selectedItem;
                this.typeSelection = "Image";
            } 
            else if (selectedItem instanceof WorldObjectEntity ) 
            {          
                //set selected image
                this.selectedItemsImage = ((WorldObjectEntity)selectedItem).getImage();
                this.typeSelection = "World Object";
            }
            else if (selectedItem instanceof NonPlayerEntity ) 
            {          
                //set selected image
                this.selectedItemsImage = ((NonPlayerEntity)selectedItem).getImage();
                this.typeSelection = "NPE";
            }
            else if (selectedItem instanceof MobSpawner)
            {
                //set selected image
                this.selectedItemsImage = ((MobSpawner)selectedItem).getImage();
                this.typeSelection = "Spawner";
            }
            
            
            //clear script window
            ((MapEditorScene)owningScene).scriptWindow.clear();

            //build the tab pane and add it
            tabPane = this.buildTabPane();
            propertiesComponents.add(tabPane); 

            //draw selectedItems image
            Button selectedImageButton = new Button((Image)MapEditorScene.makeCopyOfObject(this.selectedItemsImage),112.5f,750,70,70);
            propertiesComponents.add(selectedImageButton);

            //draw buttons
            Text t = new Text("Ok");
            t.setColor(new Color(Color.gray));
            okButton = new Button("buttonBackground.png", t, 10, 20, 75, 30);
            okButton.setTextPadding(20, 7);
            okButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("clicked")) {
                        okButton_clicked();
                    }
                }
            });
            propertiesComponents.add(okButton);
            if(((MapEditorScene)this.owningScene).selectedItemLocation != ItemLocation.WORLD)
                okButton.setDisabled(true);
            else
                okButton.setDisabled(false);

            t = new Text("Build New");
            t.setColor(new Color(Color.gray));
            t.setScale(.7f);
            Button b = new Button("buttonBackground.png", t, 110, 20, 75, 30);
            b.setTextPadding(3, 7);
            b.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("clicked")) {
                        buildButton_clicked();
                    }
                }
            });
            propertiesComponents.add(b);

            t = new Text("Save As");
            t.setColor(new Color(Color.gray));
            t.setScale(.7f);
            b = new Button("buttonBackground.png", t, 210, 20, 75, 30);
            b.setTextPadding(10, 7);
            b.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("clicked")) {
                        saveButton_clicked();
                    }
                }
            });
            propertiesComponents.add(b);
            
            //ID field
            this.propertiesComponents.add(new Label("Object ID:",20,670));
            String selectedID; 
            selectedID = (selectedItem.getID() != null)? selectedItem.getID():"";
            idTextBox = new TextBox(selectedID, 150, 665);
            this.propertiesComponents.add(idTextBox);
            
            
             //draw the label of the object type
            this.propertiesComponents.add(new Label("Object Type:", 20, 705));
            //draw object type selector
            final DropDown<String> dd = new DropDown(150,700);
            dd.addElement(new SimpleEntry("NPE", "NPE"));
            dd.addElement(new SimpleEntry("World Object", "World Object"));
            dd.addElement(new SimpleEntry("Image","Image"));
            dd.addElement(new SimpleEntry("Spawner","Spawner"));
            
            //set current type selection
            if(selectedItem instanceof Image)
            {
                dd.setDefaultSelection("Image");
            }
            else if(selectedItem instanceof WorldObjectEntity)
            {
                dd.setDefaultSelection("World Object");
            }
            else if(selectedItem instanceof NonPlayerEntity)
            {
                dd.setDefaultSelection("NPE");
            }
            else if(selectedItem instanceof MobSpawner)
            {
                dd.setDefaultSelection("Spawner");
            }

            dd.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("selectionChanged")) {
                        typeSelection = dd.getSelectedElement();
                        typeSelection_changed();
                    }
                }
            });   
            this.propertiesComponents.add(dd);
            
            
             
                   
        }

        //add all the components we just built
        for (WindowComponent wc : propertiesComponents) 
        {
            this.addComponent(wc);
        }
       
    }

    /**
     * Begins the polygon creation process.
     */
    private void createPolygonButton_clicked() 
    {
        TextBox offsetX = (TextBox)this.imageFields.get(10);
        TextBox offsetY = (TextBox)this.imageFields.get(11);
        offsetX.setText("0");
        offsetY.setText("0");
        ((MapEditorScene)this.owningScene).polygonBuilder.setActive(true);
    }
        
    /**
     * Called after polygon creation has ceased.
     * @param indices 
     */
    public void postPolygonCreate(ArrayList<Vector2f> indices)
    {
        if (indices.size() >= 3)
        {
            //Normalize the indices so that 0,0 is the origin
            Vector2f[] verts = new Vector2f[indices.size()];
            verts = indices.toArray(verts);

            //Initial min and max
            float xMin = verts[0].x;
            float yMin = verts[0].y;
            float xMax = verts[0].x;
            float yMax = verts[0].y;

            //FInd min and max
            for (Vector2f vertex : verts)
            {
                xMin = Math.min(vertex.x, xMin);
                yMin = Math.min(vertex.y, yMin);
                xMax = Math.max(vertex.x, xMax);
                yMax = Math.max(vertex.y, yMax);
            }

            //Center vector of the vertices we placed
            Vector2f center = new Vector2f( xMin + ((xMax - xMin)/2f) , yMin + ((yMax - yMin)/2f));

            //Subtract the center coordinate from each vertex
            for (Vector2f vertex : indices){
                vertex.sub(center);
            }

            this.polygonVertices = new ArrayList(indices);

            //Reverse the vertices if we placed them clockwise
            Polygon poly = new Polygon(polygonVertices.toArray(new Vector2f[this.polygonVertices.size()]));
            if (poly.getTrueArea() < 0)
                Collections.reverse(this.polygonVertices);

            
            //Centering the new polygon on the image.
            SceneObject selected = ((MapEditorScene) owningScene).selectedItem ;
            
            if(selected != null) 
            {
                TextBox offsetX = (TextBox)this.imageFields.get(10);
                TextBox offsetY = (TextBox)this.imageFields.get(11);
                
                Entity selectedEntity = (Entity)selected;
                Image img = selectedEntity.getImage();
                
                Vector2f imgCenter = new Vector2f(img.getPosition().x + img.getWidth() / 2, img.getPosition().y + img.getHeight() / 2);
                float dx = imgCenter.x - center.x;
                float dy = imgCenter.y - center.y;
                
                offsetX.setText(Float.toString(dx));
                offsetY.setText(Float.toString(dy));         
                
                selected.setPosition(center.x, center.y);     
            }
            
            okButton_clicked();
        }
       
    }
    
    /**
     * Clicking the save button allows you to save the current Item
     * into your pallette for future use
     */
    private void saveButton_clicked() 
    {
        
        //*********************
        // Build save menu
        //*********************
        if(saveMenu != null)
            this.owningScene.remove(saveMenu);
        
        saveMenu = new Menu("Save Object",570,350,300,160);
        
        Label l = new Label("Group: ",50,100);
        saveMenu.addComponent(l);
        
        final DropDown<String> dd;
        if(this.typeSelection.equals("Image"))
        {
            dd = new DropDown(120,90);
            dd.addElement(new SimpleEntry("saved","saved"));
            dd.setDefaultSelection("saved");
            
        }
        else if(this.typeSelection.equals("World Object"))
        {
            dd = new DropDown(120,90);
            Set<String> groups = ((MapEditorScene)owningScene).paletteMenu.getWoGroups();
            for(String g : groups)
            {
               dd.addElement(new SimpleEntry(g,g));
            }
            dd.setDefaultSelection("town");
        }
        else //(this.typeSelection.equals("NPE"))
        {
            dd = new DropDown(120,90);
            Set<String> groups = ((MapEditorScene)owningScene).paletteMenu.getNpeGroups();
            for(String g : groups)
            {
               dd.addElement(new SimpleEntry(g,g));
            }
            dd.setDefaultSelection("town");
            
        }
        
        
        
        
        //save button
        Text t = new Text("Save");
        t.setColor(new Color(Color.gray));
        Button b = new Button("buttonBackground.png",t,30,20,90,30);
        b.setTextPadding(20, 5);
        b.addActionListener(new ActionListener(){       
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("clicked"))
                {
                    //***************
                    // * Build the item
                    //****************
                    SceneObject sceneObj = buildSceneObject();
                    String extension = "";
                    //modify selected item based on fields
                    if (typeSelection.equals("Image")) 
                    {
                        extension = ".soi";
                    } 
                    else if (typeSelection.equals("World Object")) 
                    {
                        extension= ".sow";
                    }
                    else if (typeSelection.equals("NPE")) 
                    {
                        extension = ".son";
                    }

                    //*************
                    //Save it
                    //************
                    try
                    {
                        File directory = new File("");
                        String path = directory.getAbsolutePath() + "\\LevelEditorFiles";
                        
                        SceneObjectSaveData save = ((SavableSceneObject)sceneObj).dumpFullData();
                        FileOutputStream f = new FileOutputStream(path +"\\" +sceneObj.hashCode() +"_" + dd.getSelectedElement() + extension);
                        ObjectOutputStream oos = new ObjectOutputStream(f);
                        oos.writeObject(save);
                        oos.close();
                    }
                    catch (Exception ex){System.err.println("save to palette failed: " + ex.getMessage());}
                    
                    //refresh palette
                    ((MapEditorScene)owningScene).paletteMenu.refreshSavedItems();
                    
                    //close menu
                    owningScene.remove(saveMenu);
                    
                }
            }   
        });
        saveMenu.addComponent(b);
        
        
        
        //cancel button
        t = new Text("Cancel");
        t.setColor(new Color(Color.gray));
        b = new Button("buttonBackground.png",t,170,20,90,30);
        b.setTextPadding(10, 5);
        b.addActionListener(new ActionListener(){       
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("clicked"))
                {
                    owningScene.remove(saveMenu);
                }
            }   
        });
        saveMenu.addComponent(b);
        
        saveMenu.addComponent(dd);
        this.owningScene.add(saveMenu,Layer.MENU); 
        
        
     
    }

    /**
     * Clicking the build button constructs a new object from the data you
     * entered and puts it into your hand. the existing selected item is not modified
     */
    private void buildButton_clicked() 
    {
        //build the object
        SceneObject obj = this.buildSceneObject();
        if(!(obj instanceof NonPlayerEntity))
           obj.update();

        //put copy into your hand
        ((MapEditorScene) owningScene).setHand(obj);
    }

    /**
     * Clicking the ok button will save the modifications you made to the selected item.
     * If you selected an item from a palette this button will be grayed out. This will work
     * by modifying the old sceneObject with the appropriate changed
     */
    private void okButton_clicked()
    {
        Layer sceneObjectsLayer = owningScene.getSceneObjectManager().getLayerOfSceneObject(((MapEditorScene) owningScene).selectedItem);
        owningScene.getSceneObjectManager().remove(((MapEditorScene) owningScene).selectedItem);
               
        if (((MapEditorScene) owningScene).selectedItem instanceof Image) 
        {
            Image selectedItem = (Image)((MapEditorScene) owningScene).selectedItem;
            
            Image newImage = this.parseImageFields();
            
            selectedItem.setDimensions(newImage.getWidth(), newImage.getHeight());
            selectedItem.setAngle(newImage.getAngle());
            selectedItem.setScale(newImage.getScale());
            selectedItem.setHorizontalFlip(newImage.isFlippedHorizontal());
            selectedItem.setVerticalFlip(newImage.isFlippedVertical());
            selectedItem.setColor(newImage.getColor()); 
            
            //set the id
            selectedItem.setID(idTextBox.getText());
            
            selectedItem.update();
            
            //re register the selected item with its new ID
           owningScene.getSceneObjectManager().add(((MapEditorScene) owningScene).selectedItem,sceneObjectsLayer);
            
            
        } 
        else if (((MapEditorScene) owningScene).selectedItem instanceof WorldObjectEntity) 
        {
            WorldObjectEntity selectedItem = (WorldObjectEntity)((MapEditorScene) owningScene).selectedItem;
            
            selectedItem.setImage(this.parseImageFields());
            Body tempBody = this.parseBodyFields();
            tempBody.setPosition(selectedItem.getPosition().x,selectedItem.getPosition().y);
            selectedItem.setBody(tempBody);
            selectedItem.setLight(this.parseLightFields());
            
            WorldObjectEntity newWO = this.parseWOFields();
            selectedItem.setScriptObject(newWO.getScriptObject());
            
            
            selectedItem.setShadowCaster(newWO.isShadowCaster());
            selectedItem.setViewportBlocker(newWO.isViewportBlocker()); 
            selectedItem.setLadder(newWO.isLadder());
            selectedItem.setJumpthrough(newWO.isJumpthrough());
            selectedItem.setID(newWO.getID());
            selectedItem.setName(newWO.getName());
            selectedItem.setWorldObjectType(newWO.getWorldObjectType()); 
            selectedItem.setImageOffset(new Vector2f(newWO.getImageOffset()));
            selectedItem.setMaterialType(newWO.getMaterialType());
            
            //set the id
            selectedItem.setID(idTextBox.getText());
            
            //re register the selected item with its new ID
            owningScene.getSceneObjectManager().add(((MapEditorScene) owningScene).selectedItem,sceneObjectsLayer);
            
            if(!newWO.getEmitters().isEmpty())
            {
                for(ParticleEmitter emitter :selectedItem.getEmitters())
                {
                    owningScene.remove(emitter);
                }
                selectedItem.getEmitters().clear();
                selectedItem.addEmitter(newWO.getEmitters().get(0)); 
            }
            else if(newWO.getEmitters().isEmpty())
            {
                for(ParticleEmitter emitter :selectedItem.getEmitters())
                {
                    owningScene.remove(emitter);
                }
                selectedItem.getEmitters().clear();
            }
            
            selectedItem.update();
            
       
        }
        else if (((MapEditorScene) owningScene).selectedItem instanceof NonPlayerEntity) 
        {
            
            NonPlayerEntity selectedItem =(NonPlayerEntity)((MapEditorScene) owningScene).selectedItem;
            
            selectedItem.setImage(this.parseImageFields());
            Body tempBody = this.parseBodyFields();
            tempBody.setPosition(selectedItem.getPosition().x,selectedItem.getPosition().y);
            selectedItem.setBody(tempBody);
            
            NonPlayerEntity newNPE = this.parseNPEFields();
            selectedItem.setScriptObject(newNPE.getScriptObject());
            selectedItem.setBrain(newNPE.getBrain());
            selectedItem.setCombatData(newNPE.getCombatData());
            selectedItem.setSkillManager(newNPE.getSkillManager());
            selectedItem.setID(newNPE.getID());
            selectedItem.setName(newNPE.getName());
            selectedItem.setSoundPack(newNPE.getSoundPack());
            selectedItem.setImageOffset(new Vector2f(newNPE.getImageOffset())); 
            selectedItem.setLocateDistance(newNPE.getLocateDistance());
            selectedItem.setWanderDistance(newNPE.getWanderDistance());
            
            //set the id
            selectedItem.setID(idTextBox.getText()); 
            //update 
            if(!(selectedItem instanceof NonPlayerEntity))
                selectedItem.update();
            else
            {
                ((Entity)selectedItem).getImage().update();
            }
            
            //re register the selected item with its new ID
            owningScene.getSceneObjectManager().add(((MapEditorScene) owningScene).selectedItem,sceneObjectsLayer);
      
        }
        else if (((MapEditorScene) owningScene).selectedItem instanceof MobSpawner) 
        {
            MobSpawner selectedItem =(MobSpawner)((MapEditorScene) owningScene).selectedItem;
            
            NonPlayerEntity newNPE = this.parseNPEFields();
            Body tempBody = this.parseBodyFields();
            Image tempImage = this.parseImageFields();
            newNPE.setBody(tempBody);
            newNPE.setImage(tempImage);
            
            selectedItem.mobToSpawn = newNPE;
            
            MobSpawner tempSpawner = this.parseSpawnerFields(newNPE);
            selectedItem.numberToSpawn = tempSpawner.numberToSpawn;
            selectedItem.spawnTime = tempSpawner.spawnTime;
            selectedItem.spawnX = tempSpawner.spawnX;
            selectedItem.spawnY = tempSpawner.spawnY; 
            selectedItem.spawnOnPlayerCollide = tempSpawner.spawnOnPlayerCollide;          
            tempBody = tempSpawner.getBody();
            tempBody.setPosition(selectedItem.getPosition().x, selectedItem.getPosition().y);
            selectedItem.setBody(tempBody);
            
            //set the id
            selectedItem.setID(idTextBox.getText());
            
            selectedItem.update();
            //re register the selected item with its new ID
            owningScene.getSceneObjectManager().add(((MapEditorScene) owningScene).selectedItem,sceneObjectsLayer);
        }
        
        
        
    }
    
    /**
     * When the radioset that controls the type of the object changes this method will be called.
     * This method is responsible for resetting and building the appropriate
     */
    private void typeSelection_changed()
    {  
        //remove old tab pane
        this.removeComponent(tabPane);
        propertiesComponents.remove(tabPane); 
        imageFields.clear();
        worldObjectFields.clear();
        bodyFields.clear();
        combatFields.clear();
        skillFields.clear();
        lightFields.clear();
        npeFields.clear();
        emitterFields.clear();
        spawnerFields.clear();
        
        //rebuild the tab pane with the correct fields
        tabPane = buildTabPane();
        this.addComponent(0,tabPane);
        propertiesComponents.add(tabPane);
        
        
   
    }
    
    /**
     * Calls the appropriate helper methods to build the sceneObject based on
     * all of our fields
     * @return A scene object built from all of the input fields
     */
    private SceneObject buildSceneObject()
    {
        SceneObject returnCopy = null;
               
        //modify selected item based on fields
        switch (typeSelection) 
        {
            case "Image":
            {
                returnCopy = this.parseImageFields();
                break;
            }
            case "World Object":
            {
                Image newImage = this.parseImageFields();
                Body body = this.parseBodyFields();
                WorldObjectEntity newWO = this.parseWOFields();
                LightSource light = this.parseLightFields();
                newWO.setImage(newImage);
                newWO.setBody(body);
                newWO.setLight(light);
                returnCopy = newWO;
                break;
            }
            case "NPE":
            {
                Image newImage = this.parseImageFields();
                Body body = this.parseBodyFields();
                NonPlayerEntity NPE = this.parseNPEFields();
                NPE.setImage(newImage);
                NPE.setBody(body);
                
              
                returnCopy = NPE;
                break;
            }
            case "Spawner":
            {
                Image newImage = this.parseImageFields();
                Body body = this.parseBodyFields();
                NonPlayerEntity NPE = this.parseNPEFields();
                NPE.setImage(newImage);
                NPE.setBody(body);
                
                MobSpawner spawner = this.parseSpawnerFields(NPE);
                returnCopy = spawner;
                break;
                              
            }
        }
        
        //set the id
        returnCopy.setID(idTextBox.getText());
        
        
        return returnCopy;
    }
     
    
    /**
     * Constructs the tab pane with the appropriate tabs for the object selected as well as
     * calls helper methods to populate those tabs with the appropriate fields
     * @return 
     */
    private TabPane buildTabPane()
    {     
        //build the tab pane
        TabPane tp = new TabPane(10,60,290,550);

        switch(this.typeSelection)
        {
            case "Image":
            {
                tp.addTab("Image");
                addImageFields(tp,0);
                break;
            }
            case "World Object":
            {
                tp.addTab("Image");
                tp.addTab("Body");
                tp.addTab("WO");
                tp.addTab("Emitter");
                tp.addTab("Light");

                addImageFields(tp,0);
                addBodyFields(tp,1);
                addWorldObjectFields(tp,2);
                addEmitterFields(tp,3);
                addLightFields(tp,4);
                break;
            }
            case "NPE":
            {
                tp.addTab("Image");
                tp.addTab("Body");
                tp.addTab("NPE");
                tp.addTab("Combat");
                addImageFields(tp,0);
                addBodyFields(tp,1);
                addNPEFields(tp,2);
                addCombatFields(tp,3);              
                break;
            }
            case "Spawner":
            {
                Image tempImage = this.selectedItemsImage;
                SceneObject tempSO = ((MapEditorScene)owningScene).selectedItem;
                
                if(tempSO instanceof MobSpawner)
                {
                    this.selectedItemsImage =((MobSpawner)((MapEditorScene)owningScene).selectedItem).mobToSpawn.getImage();
                    ((MapEditorScene)owningScene).selectedItem = ((MobSpawner)((MapEditorScene)owningScene).selectedItem).mobToSpawn;
                }
                tp.addTab("Image");
                tp.addTab("Body");
                tp.addTab("NPE");
                tp.addTab("Combat");             
                tp.addTab("Spawn");
                addImageFields(tp,0);
                addBodyFields(tp,1);
                addNPEFields(tp,2);
                addCombatFields(tp,3);
                
                this.selectedItemsImage = tempImage;
                ((MapEditorScene)owningScene).selectedItem = tempSO;
                
                addSpawnerFields(tp,4);
                
                
            }
        }
            
            return tp;
    }
    
    
    //==========================
    // Tab Building and Parsing
    //==========================
    
    /**
     * Adds all of the fields that the image tab requires
     * @param tp TabPane the fields should be added to
     * @param tab The tab that the fields should be added to
     */
    private void addImageFields(TabPane tp, int tab)
    {
        
        Image selectedImage = this.selectedItemsImage;
        
        String reference;  
        if(selectedImage.getTextureReference() != null)
            reference = selectedImage.getTextureReference();
        else if (selectedImage.getAnimationPack() != null)
            reference = selectedImage.getAnimationPack().getClass().getSimpleName();
        else
            reference = selectedImage.getVertexReference() + ", " + selectedImage.getFragReference();
        
        Label la = new Label(reference, 140, 510);
        la.getText().setColor(new Color(Color.orange));
        la.getText().setScale(.8f);
        tp.addComponent(new Label("Ref String:", 20, 510),tab);
        tp.addComponent(la,tab);

        tp.addComponent(new Label("Width:", 20, 470),tab);
        TextBox tb = new TextBox(Float.toString(selectedImage.getWidth()), 100, 460);
        tp.addComponent(tb,tab); 
        imageFields.add(tb); //0

        tp.addComponent(new Label("Height:", 20, 430),tab);
        tb = new TextBox(Float.toString(selectedImage.getHeight()), 100, 420);
        tp.addComponent(tb,tab); 
        imageFields.add(tb); //1

        tp.addComponent(new Label("Angle:", 20, 390),tab);
        tb = new TextBox(Float.toString(selectedImage.getAngle()), 100, 380);
        tp.addComponent(tb,tab); 
        imageFields.add(tb); //2

        tp.addComponent(new Label("Scale:", 20, 350),tab);
        tb =new TextBox(Float.toString(selectedImage.getScale()), 100, 340);
        tp.addComponent(tb,tab); 
        imageFields.add(tb); //3

            
        tp.addComponent(new Label("Color Filter", 20, 140),tab);
        tp.addComponent(new Label("R:", 55, 110),tab);
        tp.addComponent(new Label("G:", 55, 80),tab);
        tp.addComponent(new Label("B:", 55, 50),tab);
        tp.addComponent(new Label("A:", 55, 20),tab);

        tb = new TextBox(Float.toString(selectedImage.getColor().r), 90, 100);
        tp.addComponent(tb,tab); 
        imageFields.add(tb); //4

        tb = new TextBox(Float.toString(selectedImage.getColor().g), 90, 70);
        tp.addComponent(tb,tab); 
        imageFields.add(tb); //5

        tb = new TextBox(Float.toString(selectedImage.getColor().b), 90, 40);
        tp.addComponent(tb,tab);
        imageFields.add(tb); //6

        tb = new TextBox(Float.toString(selectedImage.getColor().a), 90, 10);
        tp.addComponent(tb,tab); 
        imageFields.add(tb); //7
        
        tp.addComponent(new Label("Horizontal Flip: ",20,220), tab);
        CheckBox cb = new CheckBox(180,215,selectedImage.isFlippedHorizontal());
        tp.addComponent(cb,tab);
        imageFields.add(cb); //8
        
        tp.addComponent(new Label("Vertical Flip: ",20,180), tab);
        cb = new CheckBox(160,175,selectedImage.isFlippedVertical());
        tp.addComponent(cb,tab);
        imageFields.add(cb); //9
        
        float xOff = 0;
        if(((MapEditorScene)owningScene).selectedItem instanceof Entity)
            xOff = ((Entity)((MapEditorScene)owningScene).selectedItem).getImageOffset().x;
            
        tp.addComponent(new Label("Offset X:", 20, 310),tab);
        final TextBox xOffset = new TextBox(Float.toString(xOff), 145, 300);
        tp.addComponent(xOffset,tab); 
        imageFields.add(xOffset); //10
        
        Image arrow = new Image("rightarrow.png");
        arrow.setHorizontalFlip(true);
        Button nudgeButton = new Button(arrow, 110, 295, 30, 35);
        nudgeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (e.getActionCommand().equals("clicked"))
                {
                    float newAmt = Float.parseFloat(xOffset.getText()) - 1;
                    xOffset.setText(Float.toString(newAmt));
                    okButton_clicked();
                }
            }
        });
        tp.addComponent(nudgeButton, tab);
        
        arrow = new Image("rightarrow.png");
        nudgeButton = new Button(arrow, 250, 295, 30, 35);
        nudgeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (e.getActionCommand().equals("clicked"))
                {
                    float newAmt = Float.parseFloat(xOffset.getText()) + 1;
                    xOffset.setText(Float.toString(newAmt));
                    okButton_clicked();
                }
            }
        });
        tp.addComponent(nudgeButton, tab);
        
        float yOff = 0;
        if(((MapEditorScene)owningScene).selectedItem instanceof Entity)
            yOff = ((Entity)((MapEditorScene)owningScene).selectedItem).getImageOffset().y;
        
        tp.addComponent(new Label("Offset Y:", 20, 270),tab);
        final TextBox yOffset = new TextBox(Float.toString(yOff), 145, 260);
        tp.addComponent(yOffset,tab); 
        imageFields.add(yOffset); //11
        
        arrow = new Image("rightarrow.png");
        arrow.setHorizontalFlip(true);
        nudgeButton = new Button(arrow, 110, 255, 30, 35);
        nudgeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (e.getActionCommand().equals("clicked"))
                {
                    float newAmt = Float.parseFloat(yOffset.getText()) - 1;
                    yOffset.setText(Float.toString(newAmt));
                    okButton_clicked();
                }
            }
        });
        tp.addComponent(nudgeButton, tab);
        
        arrow = new Image("rightarrow.png");
        nudgeButton = new Button(arrow, 250, 255, 30, 35);
        nudgeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (e.getActionCommand().equals("clicked"))
                {
                    float newAmt = Float.parseFloat(yOffset.getText()) + 1;
                    yOffset.setText(Float.toString(newAmt));
                    okButton_clicked();
                }
            }
        });
        tp.addComponent(nudgeButton, tab);
        
    }
    
    private Image parseImageFields()
    {
        float width, height, angle, scale, r, g, b, a, xOff, yOff;
        Image newImage = (Image)MapEditorScene.makeCopyOfObject(selectedItemsImage);
        boolean hFlip, vFlip;
            
        try 
        {
            width = Float.parseFloat(((TextBox) imageFields.get(0)).getText());
            height = Float.parseFloat(((TextBox) imageFields.get(1)).getText());
            angle = Float.parseFloat(((TextBox) imageFields.get(2)).getText());
            scale = Float.parseFloat(((TextBox) imageFields.get(3)).getText());
            r = Float.parseFloat(((TextBox) imageFields.get(4)).getText());
            g = Float.parseFloat(((TextBox) imageFields.get(5)).getText());
            b = Float.parseFloat(((TextBox) imageFields.get(6)).getText());
            a = Float.parseFloat(((TextBox) imageFields.get(7)).getText());
            hFlip = ((CheckBox)imageFields.get(8)).isChecked();
            vFlip = ((CheckBox)imageFields.get(9)).isChecked();           

            newImage.setDimensions(width, height);
            newImage.setAngle(angle);
            newImage.setScale(scale);
            newImage.setColor(new Color(r, g, b, a));
            newImage.setHorizontalFlip(hFlip);
            newImage.setVerticalFlip(vFlip);
            
        }
        catch (Exception e) {
            System.err.println("Parse Text Fields error: " + e.getMessage());
        }

        return newImage;
    }
    
    
    private void addBodyFields(TabPane tp, int tab)
    {
        
        //default settings
        String shape = "Box";
        String width = Float.toString(this.selectedItemsImage.getWidth());
        String height = Float.toString(this.selectedItemsImage.getHeight());
        String radius = Float.toString(this.selectedItemsImage.getHeight()/2);
        boolean dynamic = false;
        String collisionMask = "WORLD_OBJ";
        String specialCollisionMask = "NO_OVERLAP";
        String friction = "1";
        String mass = "1";
        boolean gravityEffected = false;
        String angle = "0";
        boolean rotatable = false;
        polygonVertices.clear();
        polygonVertices.add(new Vector2f(-5,-5));
        polygonVertices.add(new Vector2f(-5, 5));
        polygonVertices.add(new Vector2f( 5, 5));
        polygonVertices.add(new Vector2f( 5,-5));
        
        //override defaults if the selected item has a body
        if(((MapEditorScene)owningScene).selectedItem instanceof Entity)
        {
            Entity selectedItem = (Entity)((MapEditorScene)owningScene).selectedItem;
            Body body = selectedItem.getBody();
            
            if(body.getShape() instanceof Circle)
            {
                shape = "Circle";
                radius= Float.toString(((Circle)body.getShape()).getRadius());
                width = "0";
                height = "0";
            }
            else if(body.getShape() instanceof Box)
            {
                shape = "Box";
                width = Float.toString(selectedItem.getWidth());
                height = Float.toString(selectedItem.getHeight());
                radius = "0";
            }
            else if(body.getShape() instanceof Polygon)
            {
                shape = "Polygon";
                polygonVertices = new ArrayList(Arrays.asList(((Polygon)body.getShape()).getVertices()));
            }
            
            if(body instanceof StaticBody)
            {
                dynamic = false;
            }
            else
                dynamic = true;
            
            collisionMask = Entity.BitMasks.valueToEnum(body.getBitmask()).toString();
            specialCollisionMask =  Entity.OverlapMasks.valueToEnum(body.getOverlapMask()).toString();
            
            friction = Float.toString(body.getFriction());
            mass = Float.toString(body.getMass());
            gravityEffected = body.getGravityEffected();
            angle = Float.toString((float)(body.getRotation() * 180 / Math.PI));
            rotatable = body.isRotatable();
            
        }
        
        
         final Label radiusLabel = new Label("Radius: ",10,390);
         tp.addComponent(radiusLabel, tab);
         final TextBox radiusTb = new TextBox(radius,120,380);
         tp.addComponent(radiusTb,tab);
         this.bodyFields.add(radiusTb); //0
         
         final Label widthLabel = new Label("Width: ",10,470);
         tp.addComponent(widthLabel, tab);
         final TextBox widthTb = new TextBox(width,120,460);
         tp.addComponent(widthTb,tab);
         this.bodyFields.add(widthTb); //1
         
         final Label heightLabel = new Label("Height: ",10,430);
         tp.addComponent(heightLabel, tab);
         final TextBox heightTb = new TextBox(height,120,420);
         tp.addComponent(heightTb,tab);
         this.bodyFields.add(heightTb); //2

         Text t = new Text("Create New");
         t.setColor(new Color(Color.gray));
         t.setScale(.7f);
         polygonCreate = new Button("buttonBackground.png", t, 30, 470, 85, 30);
         polygonCreate.setTextPadding(3, 7);
         polygonCreate.addActionListener(new ActionListener() {
 
              public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) {
                    createPolygonButton_clicked();
                }
            }
         });
         tp.addComponent(polygonCreate, tab);
         
         tp.addComponent(new Label("Dynamic: ",10,360), tab);
         final CheckBox cb1 = new CheckBox(120,355,dynamic);
         cb1.addActionListener(new ActionListener(){
             
             //if the checkbox is clicked we have to reset the mass
             //otherwise the thing has infinite mass
             public void actionPerformed(ActionEvent e)
             {
                 if(e.getActionCommand().equals("clicked") && cb1.isChecked())
                 {
                     ((TextBox)bodyFields.get(5)).setText("1");
                 }
             }
             
         });
         tp.addComponent(cb1,tab);
         this.bodyFields.add(cb1); //3
            
         tp.addComponent(new Label("Friction: ",10,150), tab);
         TextBox tb = new TextBox(friction,120,140);
         tp.addComponent(tb,tab);
         this.bodyFields.add(tb); //4
         
         tp.addComponent(new Label("Mass: ",10,105), tab);
         tb = new TextBox(mass,120,95);
         tp.addComponent(tb,tab);
         this.bodyFields.add(tb); //5
         
         tp.addComponent(new Label("Gravity Effected: ",10,320), tab);
         CheckBox cb = new CheckBox(190,315,gravityEffected);
         tp.addComponent(cb,tab);
         this.bodyFields.add(cb); //6
         
         tp.addComponent(new Label("Angle: ",10,65), tab);
         tb = new TextBox(angle,120,55);
         tp.addComponent(tb,tab);
         this.bodyFields.add(tb); //7
         
         tp.addComponent(new Label("Rotatable: ",10,280), tab);
         cb = new CheckBox(120,275,rotatable);
         tp.addComponent(cb,tab);
         this.bodyFields.add(cb); //8
         
         
         tp.addComponent(new Label("Shape:", 10, 510),tab);     
         final DropDown<String> rs = new DropDown(100,500);
         rs.addElement(new SimpleEntry("Box", "box"));
         rs.addElement(new SimpleEntry("Circle", "circle"));
         rs.addElement(new SimpleEntry("Polygon","polygon"));
         rs.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("selectionChanged")) 
                    {
                        if(rs.getSelectedElement().equals("box"))
                        {
                            //Hide polygon button
                            polygonCreate.setHidden(true);
                            
                            //hide radius
                            radiusLabel.setHidden(true);
                            radiusTb.setHidden(true);    
                            
                            //unhide width and height
                            heightLabel.setHidden(false);
                            heightTb.setHidden(false);
                            widthLabel.setHidden(false);
                            widthTb.setHidden(false);
                            
                            //move radius down
                            radiusLabel.setWindowRelativePosition(10, 330);
                            radiusTb.setWindowRelativePosition(120, 320);
                        }
                        else if (rs.getSelectedElement().equals("polygon"))
                        {
                            //Show polygon button
                            polygonCreate.setHidden(false);
                            
                            //hide radius
                            radiusLabel.setHidden(true);
                            radiusTb.setHidden(true);
                            
                            //hide width and height
                            heightLabel.setHidden(true);
                            heightTb.setHidden(true);
                            widthLabel.setHidden(true);
                            widthTb.setHidden(true);
                        }
                        else //circle is selected
                        {                            
                            //Hide polygon button
                            polygonCreate.setHidden(true);
                            
                            //hide height and width
                            heightLabel.setHidden(true);
                            heightTb.setHidden(true);
                            widthLabel.setHidden(true);
                            widthTb.setHidden(true);
                            
                            //unhide radius
                            radiusLabel.setHidden(false);
                            radiusTb.setHidden(false); 
                            
                            //move radius up
                            radiusLabel.setWindowRelativePosition(10, 420);
                            radiusTb.setWindowRelativePosition(120, 410);
                        }
                    }
                }
           });   
         rs.setDefaultSelection(shape);
         tp.addComponent(rs, tab);
         this.bodyFields.add(rs); //9
         
         t = new Text ("Overlap Mask: ");
         t.setScale(.9f);
         tp.addComponent(new Label(t,10,195), tab);
         DropDown<Entity.OverlapMasks> de = new DropDown<>(150,185);        
         de.addElement(new SimpleEntry("PLAYER_TOUCH",Entity.OverlapMasks.PLAYER_TOUCH));
         de.addElement(new SimpleEntry("NPE_TOUCH",Entity.OverlapMasks.NPE_TOUCH));
         de.addElement(new SimpleEntry("NPE",Entity.OverlapMasks.NPE));
         de.addElement(new SimpleEntry("OVERLAP_ALL",Entity.OverlapMasks.OVERLAP_ALL));
         de.addElement(new SimpleEntry("NO_OVERLAP",Entity.OverlapMasks.NO_OVERLAP));
         
         de.setDefaultSelection(specialCollisionMask);
         tp.addComponent(de ,tab);
         this.bodyFields.add(de); //10
         
         t = new Text ("Collision Mask: ");
         t.setScale(.9f);
         tp.addComponent(new Label(t,10,240), tab);
         DropDown<Entity.BitMasks> dd = new DropDown<>(150,230);
         dd.addElement(new SimpleEntry("WORLD_OBJ",Entity.BitMasks.WORLD_OBJ));
         dd.addElement(new SimpleEntry("COLLIDE_WORLD",Entity.BitMasks.COLLIDE_WORLD));
         dd.addElement(new SimpleEntry("NPE",Entity.BitMasks.NPE));
         dd.addElement(new SimpleEntry("COLLIDE_ALL",Entity.BitMasks.COLLIDE_ALL));
         dd.addElement(new SimpleEntry("NO_COLLISION",Entity.BitMasks.NO_COLLISION));
         dd.setDefaultSelection(collisionMask);
         tp.addComponent(dd ,tab);
         this.bodyFields.add(dd); //11   
    }
    
    private Body parseBodyFields()
    {
        
        float width, height,radius, mass, friction, angle;
        String shape;
        boolean gravityEffected,dynamic, rotatable;
        long collisionMask,overlapMask;
        Body body;
        ArrayList vertices;

        try
        {
            shape = (String)((DropDown)this.bodyFields.get(9)).getSelectedElement();
            
            width = 0;
            height = 0;
            if(shape.equals("box"))
            {
                width = Float.parseFloat(((TextBox)this.bodyFields.get(1)).getText());
                height = Float.parseFloat(((TextBox)this.bodyFields.get(2)).getText());
            }
            radius = 0;
            if(shape.equals("circle"))
                radius = Float.parseFloat(((TextBox)this.bodyFields.get(0)).getText()) ;
            
            vertices = new ArrayList(this.polygonVertices); //Sorry, body fields only took window components
            
            dynamic = ((CheckBox)this.bodyFields.get(3)).isChecked();          
            friction = Float.parseFloat(((TextBox)this.bodyFields.get(4)).getText());
            mass = Float.parseFloat(((TextBox)this.bodyFields.get(5)).getText());
            gravityEffected = ((CheckBox)this.bodyFields.get(6)).isChecked();
            angle =(float)( Float.parseFloat(((TextBox)this.bodyFields.get(7)).getText() )/180 * Math.PI);
            
            collisionMask =((Entity.BitMasks)((DropDown)this.bodyFields.get(11)).getSelectedElement()).value;          
            overlapMask =((Entity.OverlapMasks)((DropDown)this.bodyFields.get(10)).getSelectedElement()).value;
            rotatable = ((CheckBox)this.bodyFields.get(8)).isChecked();
             //if its static or dynamic
            if(dynamic == false)
            {
                if(shape.equals("polygon"))
                {
                    Vector2f[] polygonVerts = new Vector2f[vertices.size()];
                    vertices.toArray(polygonVerts);                   
                    Polygon poly = new Polygon(polygonVerts);                    
                    body = new StaticBody(poly);
                }
                else if(shape.equals("box"))
                    body = new StaticBody(new Box(width,height));
                else // its a circle
                    body = new StaticBody(new Circle(radius));        
            }
            else //its dynamic
            {
                if(shape.equals("polygon"))
                {
                    Vector2f[] polygonVerts = new Vector2f[vertices.size()];
                    vertices.toArray(polygonVerts);                   
                    Polygon poly = new Polygon(polygonVerts);                    
                    body = new Body(poly,mass);
                }
                else if(shape.equals("box"))
                    body = new Body(new Box(width,height),mass);
                else//its a circle
                    body = new Body(new Circle(radius),mass);
            }
                       

            //set angle
            body.setRotation(angle);       
            //set collision mask
            body.setBitmask(collisionMask);
            //set special tactic mask
            body.setOverlapMask(overlapMask);
            //set friction
            body.setFriction(friction);
            //set gravity effected
            body.setGravityEffected(gravityEffected);
            //set rotatable
            body.setRotatable(rotatable);
            
            return body;
        }
        catch(Exception e){System.err.println("Parse WorldObject Fields error: " + e.getMessage());}

        //if we failed return null
        return null;
    }
    
    
    private void addWorldObjectFields(TabPane tp, int tab)
    {       
        //default values
        boolean viewportBlock = false;
        boolean shadowCast = false;
        WorldObjectEntity.WorldObjectType terrain = WorldObjectEntity.WorldObjectType.NONE;
        MaterialType materialType = MaterialType.NONE;
        String name = "";
        String ID = "";
        ScriptObject scriptObject = null;
        boolean useScript = false;
        boolean ladder = false;
        boolean jumpthrough = false;
        
        //if the selected item is a world object use its current values instead of defaults
        if(((MapEditorScene)owningScene).selectedItem instanceof WorldObjectEntity)
        {
             WorldObjectEntity selectedItem = (WorldObjectEntity)((MapEditorScene)owningScene).selectedItem;
             viewportBlock = selectedItem.isViewportBlocker();
             shadowCast = selectedItem.isShadowCaster();
             terrain = selectedItem.getWorldObjectType();
             ladder = selectedItem.isLadder();
             jumpthrough =selectedItem.isJumpthrough();
             materialType =selectedItem.getMaterialType();
            
        }
        if(((MapEditorScene)owningScene).selectedItem instanceof Entity)
        {
            Entity selectedItem = (Entity)((MapEditorScene)owningScene).selectedItem;
            
            name = selectedItem.getName();
            if(name == null)
                name = "";
            
            //get script object
            scriptObject = selectedItem.getScriptObject();
            if(scriptObject != null)
            {
                useScript = true;
                ((MapEditorScene)owningScene).scriptWindow.populateFromScriptObject(scriptObject);
            }
                
        }

        tp.addComponent(new Label("Viewport Block:", 20, 420),tab);
        CheckBox cb =new CheckBox( 185, 415,viewportBlock);
        tp.addComponent(cb,tab);
        worldObjectFields.add(cb); //0

        tp.addComponent(new Label("Shadow Cast:", 20, 380),tab);
        cb = new CheckBox( 170, 375,shadowCast);
        tp.addComponent(cb,tab);
        worldObjectFields.add(cb); //1
    
        tp.addComponent(new Label("Name:", 20, 500),tab);
        TextBox tb = new TextBox(name, 170, 490);
        tp.addComponent(tb,tab);
        worldObjectFields.add(tb); //2       
        
        Text t = new Text("Scripting...");
        t.setColor(new Color(Color.red));
        tp.addComponent(new Label(t, 20, 285),tab);
        
        tp.addComponent(new Label("Use Script:", 20, 250),tab);
        cb = new CheckBox( 170, 245,useScript);
        tp.addComponent(cb,tab);
        worldObjectFields.add(cb); //3
        
        t = new Text("Script");
        t.setScale(.7f);
        t.setColor(new Color(Color.black));
        Button b = new Button("buttonBackground.png",t,210,240,60,30);
        b.addActionListener(new ActionListener(){
         
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("clicked"))
                {
                   ((MapEditorScene)owningScene).scriptWindow.open();
                   ((CheckBox)worldObjectFields.get(3)).setChecked(true);
                }
            }   
        });
        
        
        b.setTextPadding(10, 7);
        tp.addComponent(b,tab);
        
        tp.addComponent(new Label("Terrain Type:", 20, 340),tab);
        DropDown<WorldObjectEntity.WorldObjectType> dd = new DropDown( 170, 330);
        for(WorldObjectEntity.WorldObjectType c:WorldObjectEntity.WorldObjectType.values())
        {
            dd.addElement(new SimpleEntry(c.toString(),c)); 
        }
        dd.setDefaultSelection(terrain.name());     
        tp.addComponent(dd,tab);
        worldObjectFields.add(dd); //4
        
        tp.addComponent(new Label("Ladder:", 20, 200),tab);
        cb = new CheckBox( 170, 190,ladder);
        tp.addComponent(cb,tab);
        worldObjectFields.add(cb); //5
        
        tp.addComponent(new Label("Jumpthrough:", 20, 150),tab);
        cb = new CheckBox( 170, 140,jumpthrough);
        tp.addComponent(cb,tab);
        worldObjectFields.add(cb); //6
        
        tp.addComponent(new Label("Material Type:", 20, 100),tab);
        DropDown<WorldObjectEntity.WorldObjectType> dropDown = new DropDown( 170, 90);
        for(MaterialType c:MaterialType.values())
        {
            dropDown.addElement(new SimpleEntry(c.toString(),c)); 
        }
        dropDown.setDefaultSelection(materialType.name());     
        tp.addComponent(dropDown,tab);
        worldObjectFields.add(dropDown); //7
        
        

    }
    
    private void addEmitterFields(TabPane tp, int tab)
    {
        Class emitterClass = null;
        String particles = "1";
        String emitterAngle = "90";
        
        if(((MapEditorScene)owningScene).selectedItem instanceof WorldObjectEntity)
        {
             WorldObjectEntity selectedItem = (WorldObjectEntity)((MapEditorScene)owningScene).selectedItem;
            
             //if this wo has an emitter set its emitter properties
             if(!selectedItem.getEmitters().isEmpty())
             {
                 emitterClass = selectedItem.getEmitters().get(0).getClass();
                 particles = Float.toString(selectedItem.getEmitters().get(0).getParticlesPerFrame());
                 emitterAngle = Float.toString(selectedItem.getEmitters().get(0).getAngle());
             }
        }
          
        tp.addComponent(new Label("Particles/Frame: ",10,460),tab);
        TextBox tb = new TextBox(particles,175,450);
        tp.addComponent(tb,tab);
        emitterFields.add(tb); //0
        
        //emitter type dropdown
        tp.addComponent(new Label("Emitter Type: ",10,500),tab);
        DropDown<Class> emitterDD = new DropDown<>(150,490);     
        emitterDD.addElement(new SimpleEntry("None",null));
        emitterDD.addElement(new SimpleEntry("Green",GreenGooEmitter.class));
        emitterDD.addElement(new SimpleEntry("Blood",BloodEmitter.class));
        emitterDD.addElement(new SimpleEntry("Laser",LaserBitsEmitter.class));
        emitterDD.addElement(new SimpleEntry("Smoke",SmokeEmitter.class));
        
        if(emitterClass == null)
           emitterDD.setDefaultSelection("None");
        else if(emitterClass == GreenGooEmitter.class)
            emitterDD.setDefaultSelection("Green");
        else if(emitterClass == BloodEmitter.class)
            emitterDD.setDefaultSelection("Blood");
        else if(emitterClass == LaserBitsEmitter.class)
            emitterDD.setDefaultSelection("Laser");
        else if(emitterClass == SmokeEmitter.class)
            emitterDD.setDefaultSelection("Smoke");
        
        emitterFields.add(emitterDD); //1
        
        tp.addComponent(new Label("Angle: ",10,380),tab);
        tb = new TextBox(emitterAngle,175,380);
        tp.addComponent(tb,tab); 
        emitterFields.add(tb); //2
        
        //drop down componenet
        tp.addComponent(emitterDD,tab);
        
    }
    
    private WorldObjectEntity parseWOFields()
    {      
        boolean viewportInhibitor, shadowCaster, useScript,ladder,jumpthrough;
        WorldObjectEntity.WorldObjectType terrain;
        MaterialType materialType;
        String  name;
        Class emitterClass;
        float particlesFrame;
        ScriptObject scriptObject;
        float xOff, yOff, emitterAngle;
        
        
        try
        {
            viewportInhibitor= ((CheckBox)this.worldObjectFields.get(0)).isChecked();
            shadowCaster= ((CheckBox)this.worldObjectFields.get(1)).isChecked();
            emitterAngle = Float.parseFloat(((TextBox)this.emitterFields.get(2)).getText());
            emitterClass =(Class)((DropDown)this.emitterFields.get(1)).getSelectedElement();
            particlesFrame = Float.parseFloat(((TextBox)this.emitterFields.get(0)).getText());
            useScript = ((CheckBox)this.worldObjectFields.get(3)).isChecked(); 
            ladder = ((CheckBox)this.worldObjectFields.get(5)).isChecked(); 
            jumpthrough = ((CheckBox)this.worldObjectFields.get(6)).isChecked(); 
            terrain = (WorldObjectEntity.WorldObjectType)((DropDown)this.worldObjectFields.get(4)).getSelectedElement();
            materialType = (WorldObjectEntity.MaterialType)((DropDown)this.worldObjectFields.get(7)).getSelectedElement();
            WorldObjectEntity newWorldObject = new WorldObjectEntity(new Image("blank.png"),new StaticBody( new Box(1,1)));
            newWorldObject.setViewportBlocker(viewportInhibitor);
            newWorldObject.setShadowCaster(shadowCaster);
            newWorldObject.setWorldObjectType(terrain);
            newWorldObject.setLadder(ladder);
            newWorldObject.setJumpthrough(jumpthrough);
            newWorldObject.setMaterialType(materialType);
            xOff =Float.parseFloat(((TextBox) imageFields.get(10)).getText());
            yOff =Float.parseFloat(((TextBox) imageFields.get(11)).getText());
            newWorldObject.setImageOffset(new Vector2f(xOff,yOff));
            
            //if emitter type is not none build the emitter
            if(emitterClass != null)
            {
                ParticleEmitter emitter = (ParticleEmitter)emitterClass.newInstance();
                emitter.setDuration(-1);
                emitter.setParticlesPerFrame(particlesFrame); 
                emitter.setAngle(emitterAngle);
                newWorldObject.getEmitters().clear();
                newWorldObject.addEmitter(emitter);
                
            }           
            else
            {
                newWorldObject.getEmitters().clear();
            }
            
            //if there should be a script assign it
            if(useScript)
            {
                scriptObject = ((MapEditorScene)owningScene).scriptWindow.getScriptObject();
                newWorldObject.setScriptObject(scriptObject);
            }
            
            name = ((TextBox)this.worldObjectFields.get(2)).getText();           
            newWorldObject.setName(name);
                
            return newWorldObject;
        }
        catch(Exception e){System.err.println("Parse WorldObject Fields error: " + e.getMessage());}

        //if we failed return null
        return null;                  
    }
    
    
    private void addLightFields(TabPane tp, int tab)
    {
        //default values
        String size = "10";
        String conicalRadius = "360";
        String direction = "0";
        String intensity = "1";
        String r = "1";
        String g = "1";
        String b = "1";
        String a = "1";
        boolean useLight =false;
        
        //if the selected item has a light, fill in those values instead of the defaults
        if(((MapEditorScene)owningScene).selectedItem instanceof WorldObjectEntity)
        {
             WorldObjectEntity selectedItem = (WorldObjectEntity)((MapEditorScene)owningScene).selectedItem;
             
             if(selectedItem.getLight() != null)
             {
                 useLight = true;
                 size = Float.toString(selectedItem.getLight().getSize());
                 conicalRadius = Float.toString(selectedItem.getLight().getConicalRadius());
                 direction = Float.toString(selectedItem.getLight().getDirection());
                 intensity = Float.toString(selectedItem.getLight().getIntensity());
                 r = Float.toString(selectedItem.getLight().getColor().r);
                 b = Float.toString(selectedItem.getLight().getColor().g);
                 g = Float.toString(selectedItem.getLight().getColor().b);
                 a = Float.toString(selectedItem.getLight().getColor().a);
             }
        }
        
        //build all of the fields
        tp.addComponent(new Label("Size:", 20, 450),tab);
        TextBox tb = new TextBox(size, 170, 440);
        tp.addComponent(tb,tab);
        this.lightFields.add(tb); //0
        
        tp.addComponent(new Label("Conical Radius:", 20, 410),tab);
        tb = new TextBox(conicalRadius, 170, 400);
        tp.addComponent(tb,tab);
        this.lightFields.add(tb); //1
        
        tp.addComponent(new Label("Direction:", 20, 370),tab);
        tb = new TextBox(direction, 170, 360);
        tp.addComponent(tb,tab);
        this.lightFields.add(tb); //2
        
        tp.addComponent(new Label("Intensity:", 20, 330),tab);
        tb = new TextBox(intensity, 170, 320);
        tp.addComponent(tb,tab);
        this.lightFields.add(tb); //3
        
        tp.addComponent(new Label("Color", 20, 290),tab);
        tp.addComponent(new Label("R:", 55, 260),tab);
        tp.addComponent(new Label("G:", 55, 230),tab);
        tp.addComponent(new Label("B:", 55, 200),tab);
        tp.addComponent(new Label("A:", 55, 170),tab);

        tb = new TextBox( r, 90, 250);
        tp.addComponent(tb,tab); 
        this.lightFields.add(tb); //4

        tb = new TextBox(g, 90, 220);
        tp.addComponent(tb,tab); 
        this.lightFields.add(tb); //5

        tb = new TextBox(b, 90, 190);
        tp.addComponent(tb,tab);
        this.lightFields.add(tb); //6

        tb = new TextBox(a, 90, 160);
        tp.addComponent(tb,tab); 
        this.lightFields.add(tb); //7
        
        tp.addComponent(new Label("Use Light:", 20, 500),tab);
        CheckBox cb = new CheckBox(170,495,useLight);
        tp.addComponent(cb,tab);
        this.lightFields.add(cb); //8
        
    }
    
    private LightSource parseLightFields()
    {
        float size, conicalRadius, direction, intensity, r,b,g,a;
        
        try
        {
            if(!((CheckBox)lightFields.get(8)).isChecked())
            {
                return null;
            }
            else
            {
                size = Float.parseFloat(((TextBox)lightFields.get(0)).getText());
                conicalRadius = Float.parseFloat(((TextBox)lightFields.get(1)).getText());
                direction = Float.parseFloat(((TextBox)lightFields.get(2)).getText());
                intensity = Float.parseFloat(((TextBox)lightFields.get(3)).getText());

                r = Float.parseFloat(((TextBox)lightFields.get(4)).getText());
                g = Float.parseFloat(((TextBox)lightFields.get(5)).getText());
                b = Float.parseFloat(((TextBox)lightFields.get(6)).getText());
                a = Float.parseFloat(((TextBox)lightFields.get(7)).getText());

                LightSource light = new LightSource(); 
                light.setSize(size);
                light.setConicalRadius(conicalRadius);
                light.setDirection(direction);
                light.setIntensity(intensity);
                light.setColor(new Color(r,g,b,a));
                light.setPosition(0, 0);

                return light;   
            }
        }
        
        catch(Exception e){System.err.println("Parse Light Fields error: " + e.getMessage());}
        
        return null;
    }
    
    
    private void addCombatFields(TabPane tp, int tab)
    {
        
        
        //default settings
        String brain = "Fighter";
        String health = "20";
        String baseDamage = "5";
        String xBase = Float.toString(15);
        String yBase = Float.toString(15);
        String ccResistance = "0";
        String locateDistance = "500";
        String wanderDistance = "400";
        
        //if the selected item is already an NPE override default values
        if(((MapEditorScene)owningScene).selectedItem instanceof NonPlayerEntity)
        {
            NonPlayerEntity selectedItem = (NonPlayerEntity)((MapEditorScene)owningScene).selectedItem;
            CombatData selectedCombatData = selectedItem.getCombatData();
            
            brain = selectedItem.getBrain().getID().name();
            health = Float.toString(selectedCombatData.maxHealth.getBase());
            baseDamage = Float.toString(selectedCombatData.baseDamage.getBase());
            xBase = Float.toString(selectedCombatData.xVelocity.getTotalValue());
            yBase = Float.toString(selectedCombatData.yVelocity.getTotalValue());
            ccResistance = Float.toString(selectedCombatData.ccResistance.getTotalValue());
            locateDistance = Float.toString(selectedItem.getLocateDistance());
            wanderDistance = Float.toString(selectedItem.getWanderDistance());
        }

        
        tp.addComponent(new Label("Health:", 20, 450),tab);
        TextBox tb = new TextBox(health, 170, 440);
        tp.addComponent(tb,tab);
        this.combatFields.add(tb); //0 - Health    
        
        tp.addComponent(new Label("Base Damage:", 20, 410),tab);
        tb = new TextBox(baseDamage, 170, 400);
        tp.addComponent(tb,tab);
        this.combatFields.add(tb); //1 - damage
        
        tp.addComponent(new Label("Velocity (x,y):", 20, 370),tab);
        tb = new TextBox(xBase, 185, 360);
        tb.setDimensions(40,30);
        tp.addComponent(tb,tab);
        this.combatFields.add(tb); //2 - X velocity
        tb = new TextBox(yBase, 230, 360);
        tb.setDimensions(40,30);
        tp.addComponent(tb,tab);
        this.combatFields.add(tb); //3 - Y velocity
        
        tp.addComponent(new Label("CC reduction:", 20, 330),tab);
        tb = new TextBox(ccResistance, 170, 320);
        tp.addComponent(tb,tab);
        this.combatFields.add(tb); //4 - damage
        
        tp.addComponent(new Label("Locate Distance:", 20, 290),tab);
        tb = new TextBox(locateDistance, 170, 280);
        tp.addComponent(tb,tab);
        this.combatFields.add(tb); //5 - locate distance
        
        tp.addComponent(new Label("Wander Distance:", 20, 250),tab);
        tb = new TextBox(wanderDistance, 170, 240);
        tp.addComponent(tb,tab);
        this.combatFields.add(tb); //6 - locate distance
        
       
        
        tp.addComponent(new Label("Brain:", 20, 500),tab);
        DropDown<String> dd = new DropDown<>( 150, 490);  
        Set<String> types = BrainFactory.getInstance().getAvailableBrainTypes();
        for(String s: types)
        {
            dd.addElement(new SimpleEntry(s,s));
        }
        dd.setDefaultSelection(brain);
        tp.addComponent(dd,tab);
        this.combatFields.add(dd); //7 - Brain
 
    }
    
    
    private void addNPEFields(TabPane tp, int tab)
    {
        String name = "";
        ScriptObject scriptObject;
        boolean useScript = false;
        DropChance chance = DropChance.NORMAL;
        SoundPackID soundPack = SoundPackID.None;
        DropQuality quality = DropQuality.VeryPoor;
        
        if(((MapEditorScene)owningScene).selectedItem instanceof Entity)
        {
            Entity selectedItem = (Entity)((MapEditorScene)owningScene).selectedItem;
            
            name = selectedItem.getName();
            if(name == null)
                name = "";
            
            //get script object
            scriptObject = selectedItem.getScriptObject();
            if(scriptObject != null)
            {
                useScript = true;
                ((MapEditorScene)owningScene).scriptWindow.populateFromScriptObject(scriptObject);
            }
                                        
        }
        
        
        if(((MapEditorScene)owningScene).selectedItem instanceof NonPlayerEntity)
        {
            
            NonPlayerEntity selectedItem = (NonPlayerEntity)((MapEditorScene)owningScene).selectedItem;
            
            chance = selectedItem.getCombatData().dropChance;
            quality = selectedItem.getCombatData().dropQuality;
            if(selectedItem.getSoundPack() != null)
            soundPack = selectedItem.getSoundPack().getID();
        }
        
        tp.addComponent(new Label("Name:", 20, 500),tab);
        TextBox tb = new TextBox(name, 100, 490);
        tp.addComponent(tb,tab); 
        npeFields.add(tb); //0
        
        //drop quality
        tp.addComponent(new Label("Pot Chance:", 20, 420),tab);
        DropDown<DropQuality> dropQuality = new DropDown(155,410);
        for(DropQuality c: DropQuality.values())
        {
            dropQuality.addElement(new SimpleEntry(c.toString(),c)); 
        }
        dropQuality.setDefaultSelection(quality.name());
        npeFields.add(dropQuality); //1
        
        
        //sound pack
        tp.addComponent(new Label("Sound Pack:", 20, 370),tab);
        Set<String> soundPacks = SoundPackFactory.getInstance().getAvailableSoundPacks();
        DropDown dd = new DropDown<>(140,360);      
        for(String s:soundPacks)
        {
            dd.addElement(new SimpleEntry(s,s));
        }
        dd.setDefaultSelection(soundPack.name());
        
        npeFields.add(dd); //2
        
                
        Text t = new Text("Scripting...");
        t.setColor(new Color(Color.red));
        tp.addComponent(new Label(t, 20, 330),tab);
        
        tp.addComponent(new Label("Use Script:", 20, 290),tab);
        CheckBox cb = new CheckBox( 170, 285,useScript);
        tp.addComponent(cb,tab);
        npeFields.add(cb); //3
        
        t = new Text("Script");
        t.setScale(.7f);
        t.setColor(new Color(Color.black));
        Button b = new Button("buttonBackground.png",t,210,285,60,30);
        b.addActionListener(new ActionListener(){
         
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("clicked"))
                {
                   ((MapEditorScene)owningScene).scriptWindow.open();
                   ((CheckBox)npeFields.get(3)).setChecked(true);
                }
            }   
        });        
        b.setTextPadding(10, 7);
        tp.addComponent(b,tab);
        
        //add sound pack and drop chance drop downs
        tp.addComponent(dd,tab);
        tp.addComponent(dropQuality, tab);
        
        //drop rate dropdown
        tp.addComponent(new Label("$$$ Chance:", 20, 460),tab);
        DropDown<DropChance> drops = new DropDown(155,450);
        for(DropChance c: DropChance.values())
        {
            drops.addElement(new SimpleEntry(c.toString(),c)); 
        }
        drops.setDefaultSelection(chance.name());
        npeFields.add(drops); //4
        tp.addComponent(drops, tab);   
        
        
        
       
        
        //==========
        // Skills
        //==========
        t = new Text("Skills...");
        t.setColor(new Color(Color.red));
        tp.addComponent(new Label(t, 20, 240),tab);
        
        ArrayList<String> knownSkillNames = new ArrayList<>();
             
        if(((MapEditorScene)owningScene).selectedItem instanceof NonPlayerEntity)
        {
            //get known skills from player
            NonPlayerEntity selectedItem = (NonPlayerEntity)((MapEditorScene)owningScene).selectedItem;         
            HashSet<SkillID> skillz = selectedItem.getSkillManager().getKnownSkills();
           
            //add them to a list of skills
            for(SkillID s: skillz)
            {
               knownSkillNames.add(s.name());   
               
            }
        }
        //fill the rest of the known skill names with blank entries
        while(knownSkillNames.size() <5)
        {
            knownSkillNames.add("");
        }
        
        tp.addComponent(new Label("Skill 1:", 20, 150),tab);
        final Label l1 =new Label(knownSkillNames.get(0), 100, 150);
        tp.addComponent(l1,tab);
        t = new Text("X");
        t.setColor(new Color(Color.red));
        final Button b1 = new Button(t,255,147);
        b1.setTextPadding(5, 5);
        tp.addComponent(b1,tab);
        b1.addActionListener(new ActionListener(){
        
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {
                    b1.setHidden(true);
                    l1.setText(new Text(""));                  
                }
            }      
        });
        
      
        if(knownSkillNames.get(0).equals(""))    
            b1.setHidden(true);       
           
        this.skillFields.add(l1);
        
        
        tp.addComponent(new Label("Skill 2:", 20, 120),tab);
        final Label l2 =new Label(knownSkillNames.get(1), 100, 120);
        tp.addComponent(l2,tab);
        t = new Text("X");
        t.setColor(new Color(Color.red));
        final Button b2 = new Button(t,255,117);
        b2.setTextPadding(5, 5);
        tp.addComponent(b2,tab);
        b2.addActionListener(new ActionListener(){
        
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {
                    b2.setHidden(true);
                    l2.setText(new Text(""));                  
                }
            }      
        });
        
        if(knownSkillNames.get(1).equals(""))
             b2.setHidden(true);
        
        this.skillFields.add(l2);
        
        
        
        
        tp.addComponent(new Label("Skill 3:", 20, 90),tab);
        final Label l3 = new Label(knownSkillNames.get(2), 100, 90);
        tp.addComponent(l3,tab);
        t = new Text("X");
        t.setColor(new Color(Color.red));
        final Button b3 = new Button(t,255,87);
        b3.setTextPadding(5, 5);
        tp.addComponent(b3,tab);
        b3.addActionListener(new ActionListener(){
        
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {
                    b3.setHidden(true);
                    l3.setText(new Text(""));                  
                }
            }      
        });
        if(knownSkillNames.get(2).equals(""))
            b3.setHidden(true);
        
        this.skillFields.add(l3);
        
        
        tp.addComponent(new Label("Skill 4:", 20, 60),tab);
        final Label l4 = new Label(knownSkillNames.get(3), 100, 60);
        tp.addComponent(l4,tab);
        t = new Text("X");
        t.setColor(new Color(Color.red));
        final Button b4 = new Button(t,255,57);
        b4.setTextPadding(5, 5);
        tp.addComponent(b4,tab);
        b4.addActionListener(new ActionListener(){
        
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {
                    b4.setHidden(true);
                    l4.setText(new Text(""));                  
                }
            }      
        });
        
        if(knownSkillNames.get(3).equals(""))
             b4.setHidden(true);
        
        this.skillFields.add(l4);
        
        
        tp.addComponent(new Label("Skill 5:", 20, 30),tab);
        final Label l5 = new Label(knownSkillNames.get(4), 100, 30);
        tp.addComponent(l5,tab);
        t = new Text("X");
        t.setColor(new Color(Color.red));
        final Button b5 = new Button(t,255,27);
        b5.setTextPadding(5, 5);
        tp.addComponent(b5,tab);
        b5.addActionListener(new ActionListener(){
        
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {
                    b5.setHidden(true);
                    l5.setText(new Text(""));                  
                }
            }      
        });
        if(knownSkillNames.get(4).equals(""))
              b5.setHidden(true);
        
        this.skillFields.add(l5);
        
        
        tp.addComponent(new Label("Skill:", 20, 200),tab);
        final DropDown<String> ddq = new DropDown<>( 70, 190);  
        Set<String> types = SkillFactory.getInstance().getAvailableEnemySkills();
        String first = "";
        for(String s: types)
        {
            if(first.equals(""))
                first = s;
            
            ddq.addElement(new SimpleEntry(s,s));
        }
        ddq.setDefaultSelection(first);
        tp.addComponent(ddq,tab);
        
        t = new Text("Add");
        t.setScale(.7f);
        t.setColor(new Color(Color.black));
        b = new Button("buttonBackground.png",t,200,190,50,30);
        b.addActionListener(new ActionListener(){
         
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("clicked"))
                {
                    if(l1.getText().toString().equals(""))
                    {
                        l1.setText(new Text(ddq.getSelectedElement())); skillFields.add(l1); b1.setHidden(false); 
                    }
                    else if(l2.getText().toString().equals(""))
                    {
                        l2.setText(new Text(ddq.getSelectedElement())); skillFields.add(l2); b2.setHidden(false); 
                    }
                    else if(l3.getText().toString().equals(""))
                    {
                        l3.setText(new Text(ddq.getSelectedElement())); skillFields.add(l3); b3.setHidden(false); 
                    }
                    else if(l4.getText().toString().equals(""))
                    {
                        l4.setText(new Text(ddq.getSelectedElement())); skillFields.add(l4); b4.setHidden(false);
                    }
                    else if(l5.getText().toString().equals(""))
                    {
                        l5.setText(new Text(ddq.getSelectedElement())); skillFields.add(l5); b5.setHidden(false); 
                    }               
                }
            }   
        });
        
        
        b.setTextPadding(10, 7);
        tp.addComponent(b,tab);
              
        
    }
       
    private NonPlayerEntity parseNPEFields()
    {
        
        NonPlayerEntity NPE;
        
        try
        {         
            //Level, Heatlhmod, Forcemod, Expmod, Resil, xVel, yVel, resistances, Brain
            String brain = (String)((DropDown)this.combatFields.get(7)).getSelectedElement();   
            float health = Float.parseFloat(((TextBox)this.combatFields.get(0)).getText());
            float baseDamage = Float.parseFloat(((TextBox)this.combatFields.get(1)).getText());
            float baseX = Float.parseFloat(((TextBox)this.combatFields.get(2)).getText());
            float baseY = Float.parseFloat(((TextBox)this.combatFields.get(3)).getText());
            float ccResistance = Float.parseFloat(((TextBox)this.combatFields.get(4)).getText());
            float locateDistance =  Float.parseFloat(((TextBox)this.combatFields.get(5)).getText());
            float wanderDistance =  Float.parseFloat(((TextBox)this.combatFields.get(6)).getText());
            
            DropQuality quality = (DropQuality)((DropDown)this.npeFields.get(1)).getSelectedElement();
            boolean useScript = ((CheckBox)this.npeFields.get(3)).isChecked();
            DropChance chance = (DropChance)((DropDown)this.npeFields.get(4)).getSelectedElement();
            String soundPackIDString = (String)((DropDown)this.npeFields.get(2)).getSelectedElement();
            SoundPackID soundPackID = SoundPackID.valueOf(soundPackIDString);
            
            String name = ((TextBox)this.npeFields.get(0)).getText();
            
            NPE = new NonPlayerEntity(new Image("blank.png"),new StaticBody(new Box(1,1)));
            NPE.setBrain(BrainFactory.getInstance().getBrain(BrainID.valueOf(brain)));
            NPE.getCombatData().maxHealth.setBase(health);
            NPE.getCombatData().baseDamage.setBase(baseDamage);
            NPE.getCombatData().xVelocity.setBase(baseX);
            NPE.getCombatData().yVelocity.setBase(baseY);
            NPE.getCombatData().dropChance = chance;
            NPE.getCombatData().dropQuality = quality;
            NPE.getCombatData().ccResistance.setBase(ccResistance);
            NPE.setLocateDistance(locateDistance);
            NPE.setWanderDistance(wanderDistance);
            NPE.setName(name);
            NPE.setSoundPack(SoundPackFactory.getInstance().getSoundPack(soundPackID));
            
            float xOff =Float.parseFloat(((TextBox) imageFields.get(10)).getText());
            float yOff =Float.parseFloat(((TextBox) imageFields.get(11)).getText());
            NPE.setImageOffset(new Vector2f(xOff,yOff));
            
            //learn all of the skills!
            for(WindowComponent wc: this.skillFields)
            {
                Label label = (Label)wc;
                //if the skill isnt blank learn it
                if(!label.getText().toString().equals(""))
                   NPE.getSkillManager().learnSkill(SkillID.valueOf(label.getText().toString()));        
            }
            
            //assign the script
            if(useScript)
            {
                ScriptObject scriptObject = ((MapEditorScene)owningScene).scriptWindow.getScriptObject();
                NPE.setScriptObject(scriptObject);
            }
           

            return NPE;
        }
        catch(Exception e){System.err.println("Parse NPE Fields error: " + e.getMessage());}
        
        return null;
        
    }
    
    private void addSpawnerFields(TabPane tp, int tab)
    {
        //default values
        int spawnTime = 180;
        int numberToSpawn = 3;
        float spawnX = 0;
        float spawnY = 0;
        float spawnerWidth = 300;
        float spawnerHeight = 300;
        boolean spawnOnCollide = true;
        //script
        
        //if the selected item is a MobSpawner use its current values instead of defaults
        if(((MapEditorScene)owningScene).selectedItem instanceof MobSpawner)
        {
            MobSpawner spawner = (MobSpawner)((MapEditorScene)owningScene).selectedItem;
            spawnTime = spawner.spawnTime;
            numberToSpawn = spawner.numberToSpawn;
            spawnX = spawner.spawnX;
            spawnY = spawner.spawnY;
            spawnerWidth = spawner.getWidth();
            spawnerHeight = spawner.getHeight();
            spawnOnCollide = spawner.spawnOnPlayerCollide;
                      
        }
        
        tp.addComponent(new Label("Spawn Time:", 20, 510),tab);
        TextBox tb = new TextBox(Integer.toString(spawnTime), 160, 500); 
        tp.addComponent(tb,tab); 
        spawnerFields.add(tb); //0
        
        tp.addComponent(new Label("Spawn #:", 20, 470),tab);
        tb = new TextBox(Integer.toString(numberToSpawn), 160, 460);
        tp.addComponent(tb,tab); 
        spawnerFields.add(tb); //1
        
        tp.addComponent(new Label("Spawn x:", 20, 430),tab);
        tb = new TextBox(Float.toString(spawnX), 160, 420);
        tp.addComponent(tb,tab); 
        spawnerFields.add(tb); //2
        
        tp.addComponent(new Label("Spawn Y:", 20, 400),tab);
        tb = new TextBox(Float.toString(spawnY), 160, 390);
        tp.addComponent(tb,tab); 
        spawnerFields.add(tb); //3
        
        Label l = new Label("Spawner Height:", 20, 360); 
        l.getText().setScale(.85f);
        tp.addComponent(l,tab);
        tb = new TextBox(Float.toString(spawnerHeight), 160, 350);
        tp.addComponent(tb,tab); 
        spawnerFields.add(tb); //4
        
        l = new Label("Spawner Width:", 20, 330);
        l.getText().setScale(.85f);
        tp.addComponent(l,tab);
        tb = new TextBox(Float.toString(spawnerWidth), 160, 320);
        tp.addComponent(tb,tab); 
        spawnerFields.add(tb); //5
        
        l = new Label("Spawn On Collide:", 20, 300);
        l.getText().setScale(.85f);
        tp.addComponent(l,tab);
        CheckBox cb = new CheckBox(200, 300, spawnOnCollide);
        tp.addComponent(cb,tab); 
        spawnerFields.add(cb); //6
        
        
        
    }
    
    private MobSpawner parseSpawnerFields(NonPlayerEntity mob)
    {
        
        int spawnTime = Integer.parseInt(((TextBox)spawnerFields.get(0)).getText());
        int spawnNumber = Integer.parseInt(((TextBox)spawnerFields.get(1)).getText());
        float spawnX = Float.parseFloat(((TextBox)spawnerFields.get(2)).getText());
        float spawnY = Float.parseFloat(((TextBox)spawnerFields.get(3)).getText());
        float spawnerH = Float.parseFloat(((TextBox)spawnerFields.get(4)).getText());
        float spawnerW = Float.parseFloat(((TextBox)spawnerFields.get(5)).getText());
        boolean spawnCollide = ((CheckBox)spawnerFields.get(6)).isChecked();
        
        Body body = new StaticBody(new Box(spawnerW,spawnerH)); 
        body.setBitmask(Entity.BitMasks.NO_COLLISION.value);
        body.setOverlapMask(Entity.OverlapMasks.PLAYER_TOUCH.value);
        MobSpawner spawner= new MobSpawner(mob,body);
        spawner.spawnX = spawnX;
        spawner.spawnY = spawnY;
        spawner.spawnTime = spawnTime;
        spawner.numberToSpawn = spawnNumber;
        spawner.spawnOnPlayerCollide = spawnCollide;
        
        return spawner;
    }
    
    
       
}
