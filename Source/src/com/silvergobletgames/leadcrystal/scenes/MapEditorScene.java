package com.silvergobletgames.leadcrystal.scenes;

import com.jogamp.newt.event.KeyEvent;
import com.silvergobletgames.leadcrystal.core.EnhancedViewport;
import com.silvergobletgames.leadcrystal.menus.LayerWindow;
import com.silvergobletgames.leadcrystal.menus.PropertiesWindow;
import com.silvergobletgames.leadcrystal.menus.TilePalette;
import com.silvergobletgames.leadcrystal.menus.LevelPropertiesWindow;
import com.silvergobletgames.leadcrystal.menus.ScriptWindow;
import com.silvergobletgames.leadcrystal.menus.FileMenu;
import com.silvergobletgames.leadcrystal.core.LevelData;
import com.silvergobletgames.leadcrystal.core.SaveGame;
import com.silvergobletgames.leadcrystal.core.*;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.FlierAnimationPack;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.sylver.core.*;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.netcode.SavableSceneObject;
import com.silvergobletgames.sylver.netcode.SceneObjectDeserializer;
import com.silvergobletgames.sylver.netcode.SceneObjectSaveData;
import com.silvergobletgames.sylver.windowsystem.*;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.UUID;
import javax.media.opengl.GL3bc;
import javax.media.opengl.glu.gl2.GLUgl2;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Polygon;
import net.phys2d.raw.shapes.Shape;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.MobSpawner;
import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.items.DropGenerator;
import com.silvergobletgames.leadcrystal.items.DropGenerator.DropQuality;
import com.silvergobletgames.leadcrystal.menus.*;
import com.silvergobletgames.leadcrystal.netcode.ConnectionException;
import com.silvergobletgames.leadcrystal.netcode.GobletServer;
import com.silvergobletgames.sylver.audio.AudioRenderer;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import com.silvergobletgames.sylver.util.SylverVector2f;
import javax.media.opengl.GL2;
import net.phys2d.raw.StaticBody;

/**
 *
 * @author mike
 */
public class MapEditorScene extends Scene {


    //World mouse coordinate
    public static Point worldMouseLocation = new Point(0, 0);
    
    //==============================
    // Map editor specific variables
    //==============================
    //the currently selected layerObjects
    public int selectedLayer = 7;
    //current item to place
    private SceneObject hand;
    //currently selected item
    public SceneObject selectedItem;
    //selected items location
    public ItemLocation selectedItemLocation;
    //selected item layerObjects
    private int selectedItemLayer;
    //grid selectioin
    private int gridSelection = 0;
    //mouse position text
    private Text mousePositionText;
    //viewport location
    public SylverVector2f viewportLocation = new SylverVector2f(0,0); 
    //zoom amounts
    public int zoom = 0;
    //jump into level variables
    public boolean jumpIntoLevel = false;
    public boolean placingStartingPosition = false;
    
    
    public enum ItemLocation {

        PALETTE, WORLD,HAND;
    }
    
    //menus
    public LayerWindow layerMenu;
    public PropertiesWindow propertiesMenu;
    public TilePalette paletteMenu;
    public LevelPropertiesWindow levelProperties;
    public Menu gridMenu;
    public FileMenu fileMenu;
    public Menu loadLevelMenu;
    public Menu saveLevelMenu;
    public ScriptWindow scriptWindow;
    public BackgroundMenu backgroundMenu;
    public DarknessMenu darknessMenu;
    public ArrayList<Window> menus = new ArrayList<>();
    
    
    //Polygon builder
    public PolygonBuilder polygonBuilder = new PolygonBuilder();
    
    //current path
    public LevelData loadedLevelData = new LevelData(null,null);
    
    //================
    // Constructors
    //================
     
    public MapEditorScene() 
    {

        //initializing vewport
        this.setViewport(new EnhancedViewport());
        getViewport().quickMoveToCoordinate(0, 0);
        getViewport().setPanSpeed(20);

        //initialize mouse position text
        this.mousePositionText = new Text("", CoreTextType.DEFAULT);
        this.mousePositionText.setPosition( 3, 3);
        //this.mousePositionText.setScale(.75f);

        //================
        //build menus
        //================     

        //file menu
        fileMenu = new FileMenu();
        this.menus.add(fileMenu);
        this.add(fileMenu, Layer.MENU);

        // build layerObjects selector     
        layerMenu = new LayerWindow(500, 350, this);
        layerMenu.update();
        layerMenu.close();
        this.add(layerMenu, Layer.MENU);
        this.menus.add(layerMenu);

        //build grid snap menu
        gridMenu = new Menu("Grid Settings", 325, 650, 125, 202);
        final RadioSet<Integer> rd = new RadioSet<>(10, 20);
        rd.addElement(new SimpleEntry("128", 128));
        rd.addElement(new SimpleEntry("64", 64));
        rd.addElement(new SimpleEntry("32", 32));
        rd.addElement(new SimpleEntry("16", 16));
        rd.addElement(new SimpleEntry("8", 8));
        rd.addElement(new SimpleEntry("None", 0));
        gridMenu.addComponent(rd);
        gridMenu.update();
        gridMenu.close();
        this.menus.add(gridMenu);
        this.add(gridMenu, Layer.MENU);
        rd.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                gridSelection = rd.getSelectedValue();
            }
        });
        rd.setDefaultSelection("None");

        //properties window
        propertiesMenu = new PropertiesWindow(1100, 10);
        propertiesMenu.update();
        propertiesMenu.close();
        this.add(propertiesMenu, Layer.MENU);
        this.menus.add(propertiesMenu);
        
        //adding script window
        scriptWindow = new ScriptWindow(100,25);
        scriptWindow.update();
        scriptWindow.close();
        this.add(scriptWindow, Layer.MENU);
        this.menus.add(scriptWindow);

        //level info window
        levelProperties = new LevelPropertiesWindow(1000, 100);
        levelProperties.update();
        levelProperties.close();
        this.add(levelProperties, Layer.MENU);
        this.menus.add(levelProperties);

        //build image palette
        paletteMenu = new TilePalette(100, 100);
        paletteMenu.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("selectionChanged")) {
                    selectedItem = paletteMenu.getSelectedObject();
                    selectedItemLocation = ItemLocation.PALETTE;
                    propertiesMenu.selectedItemChanged();
                    propertiesMenu.open();
                    paletteMenu.close();
                    Game.getInstance().getInputHandler().getInputSnapshot().killMouseClick();
                }
            }
        });
        paletteMenu.update();
        paletteMenu.close();
        this.menus.add(paletteMenu);
        this.add(paletteMenu, Layer.MENU);
        
        backgroundMenu = new BackgroundMenu(400,100);
        backgroundMenu.update();
        backgroundMenu.close();
        this.add(backgroundMenu, Layer.MENU);
        this.menus.add(backgroundMenu);
        
        darknessMenu = new DarknessMenu(400,100);
        darknessMenu.update();
        darknessMenu.close();
        this.add(darknessMenu,Layer.MENU);
        this.menus.add(darknessMenu);
        
        

        //load level menu
        loadLevelMenu = new Menu("Load Level", 720, 450, 300, 200);
        loadLevelMenu.addComponent(new Label("Level Path: ", 30, 150));
        final TextBox levelName = new TextBox("town.lv", 155, 140);
        loadLevelMenu.addComponent(levelName);

        Button b = new Button("buttonBackground.png", new Text("Load"), 20, 20, 75, 30);
        b.setTextPadding(10, 5);
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) 
                {
        
                    loadLevel(levelName.getText());

                    loadLevelMenu.close();
                }
            }
        });
        loadLevelMenu.addComponent(b);

        b = new Button("buttonBackground.png", new Text("Close"), 120, 20, 75, 30);
        b.setTextPadding(10, 5);
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) {
                    loadLevelMenu.close();
                }
            }
        });
        loadLevelMenu.addComponent(b);

        this.menus.add(loadLevelMenu);
        this.add(loadLevelMenu, Layer.MENU);
        loadLevelMenu.update();
        loadLevelMenu.close();
          
        //save level menu
        saveLevelMenu = new Menu("Save Level", 720, 450, 300, 200);
        saveLevelMenu.addComponent(new Label("Level Path: ", 30, 150));
        final TextBox saveLevelName = new TextBox("", 155, 140);
        saveLevelMenu.addComponent(saveLevelName);

        b = new Button("buttonBackground.png", new Text("Save"), 20, 20, 75, 30);
        b.setTextPadding(10, 5);
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) {
                 
                    saveLevel(saveLevelName.getText());

                    saveLevelMenu.close();
                }
            }
        });
        saveLevelMenu.addComponent(b);

        b = new Button("buttonBackground.png", new Text("Close"), 120, 20, 75, 30);
        b.setTextPadding(10, 5);
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) {
                    saveLevelMenu.close();
                }
            }
        });
        saveLevelMenu.addComponent(b);

        this.menus.add(saveLevelMenu);
        this.add(saveLevelMenu, Layer.MENU);
        saveLevelMenu.update();
        saveLevelMenu.close();
        

        //add mouse position text
        this.add(mousePositionText, Layer.MENU);     
        
        //new level
        this.newLevel();
        
        this.update();
    }

    
    //================
    // Scene Methods
    //================
     
    public void update()
    {

        //upate everything in the scene
        ArrayList<SceneObject> parallax;
        for (Layer layer: Layer.values()) 
        {
            parallax = new ArrayList(this.getSceneObjectManager().get(layer));
            for (int j = 0; j < parallax.size(); j++) 
            {
                //if its not an NPE update it ( so AI doesnt run our guys around)
                if(!(parallax.get(j) instanceof NonPlayerEntity))
                   parallax.get(j).update();
                else //if it is an NPE just update its image
                    ((Entity)parallax.get(j)).getImage().update(); 
            }
        }
        
        //checks if an entity has a script object and gives it the correct overlay
        for (Layer layer: Layer.values()) 
        {
            if(layer != Layer.ATTACHED_BG && layer != Layer.MAIN && layer != Layer.ATTACHED_FG)
                continue;
            
            parallax = new ArrayList(this.getSceneObjectManager().get(layer));
            for (int j = 0; j < parallax.size(); j++) 
            {
                if(parallax.get(j) instanceof Entity)
                {
                    Entity sceneObject = (Entity)parallax.get(j);
                    
                    if(sceneObject.getScriptObject() != null && !sceneObject.getImage().hasOverlay("script"))
                    {
                         //script image
                        Image scriptImage = new Image("scripticonsmall.png");
                        scriptImage.setColor(new Color(1,1,1,.75f));
                        scriptImage.setScale(.6f);
                        scriptImage.setPosition(0,0);
                        scriptImage.setAnchor(Anchorable.Anchor.CENTER); 
                        Overlay scriptOverlay = new Overlay(scriptImage);
                        scriptOverlay.setRelativePosition(.5f, .5f);
                        scriptOverlay.setUseRelativeSize(false);
                        
                        sceneObject.getImage().addOverlay("script",scriptOverlay);
                    }
                    else if(sceneObject.getScriptObject() == null &&sceneObject.getImage().hasOverlay("script"))
                    {
                        sceneObject.getImage().removeOverlay("script");
                    }
                }
            }
        }

        //moves whats in our hand
        if (hand != null && this.gridSelection != 0) //moves the thing along the grid snap
        {
            if (hand instanceof Entity) 
                hand.setPosition(MapEditorScene.worldMouseLocation.x - MapEditorScene.worldMouseLocation.x % this.gridSelection + ((Entity) hand).getWidth() / 2, MapEditorScene.worldMouseLocation.y - MapEditorScene.worldMouseLocation.y % this.gridSelection + ((Entity) hand).getHeight() / 2);
            else if (hand instanceof Image) 
                hand.setPosition(MapEditorScene.worldMouseLocation.x - MapEditorScene.worldMouseLocation.x % this.gridSelection, MapEditorScene.worldMouseLocation.y - MapEditorScene.worldMouseLocation.y % this.gridSelection );          
        }
        else if (hand != null) //if there is no grid snap       
            hand.setPosition(MapEditorScene.worldMouseLocation.x, MapEditorScene.worldMouseLocation.y);
        
        


        //updates mouse position text
        this.mousePositionText.setText("X:" + MapEditorScene.worldMouseLocation.x + ", " + "Y:" + MapEditorScene.worldMouseLocation.y);
        
        //moves viewport
        this.getViewport().centerAroundPoint(viewportLocation);        
        
        //Update the polygon builder
        if (polygonBuilder.active)
            polygonBuilder.update();
    }

    public void render(GL2 gl)
    {
        //set viewport size
        Point aspectRatio = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio();
        float ratio = ((float) aspectRatio.x) / aspectRatio.y;
        getViewport().setDimensions(aspectRatio.x + this.zoom*ratio, aspectRatio.y + this.zoom);
        
        //go through all the scene objects, checks if they are hidden and color them accordingly
        ArrayList<SceneObject> parallax;
        for (Layer layer: Layer.values()) 
        {
            parallax = new ArrayList(this.getSceneObjectManager().get(layer));
            for (int j = 0; j < parallax.size(); j++) 
            {
                this.checkForLayerHidden(layer,parallax.get(j));
            }
        }
                

        //render scene with openGL3 rendering pipeling
        if(gl.isGL3bc())
           RenderingPipelineGL3.render((GL3bc)gl, getViewport(), getSceneObjectManager(),getSceneEffectsManager(),Layer.MENU); 
        else
           RenderingPipelineGL2.render(gl, getViewport(), getSceneObjectManager(),getSceneEffectsManager(),Layer.MENU);   
        
        //hand
        GLUgl2 glu = new GLUgl2();
        gl.glMatrixMode(GL3bc.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(this.getViewport().getBottomLeftCoordinate().x * Layer.MAIN.coordinateScalingFactor, this.getViewport().getBottomLeftCoordinate().y * Layer.MAIN.coordinateScalingFactor, 1, this.getViewport().getBottomLeftCoordinate().x * Layer.MAIN.coordinateScalingFactor, this.getViewport().getBottomLeftCoordinate().y * Layer.MAIN.coordinateScalingFactor, 0, 0, 1, 0);
        if(this.hand != null)
            this.hand.draw(gl);
      
        //=================
        //Draw Grid lines
        //=================

        gl.glDisable(GL3bc.GL_TEXTURE_2D);
        gl.glMatrixMode(GL3bc.GL_MODELVIEW);
        gl.glLoadIdentity();
        //GLUgl2 glu = new GLUgl2();
        glu.gluLookAt(getViewport().getBottomLeftCoordinate().x, getViewport().getBottomLeftCoordinate().y, 1, getViewport().getBottomLeftCoordinate().x, getViewport().getBottomLeftCoordinate().y, 0, 0, 1, 0);
        gl.glEnable(GL3bc.GL_BLEND);
        gl.glBlendFunc(GL3bc.GL_SRC_ALPHA, GL3bc.GL_ONE_MINUS_SRC_ALPHA);
        gl.glColor4f(20, 20, 20, .2f);
        gl.glLineWidth(.5f);
        gl.glBegin(GL3bc.GL_LINES);
        {
            if (this.gridSelection != 0) {
                //horizontal lines
                for (int i = -80_000; i < 80_000; i += this.gridSelection) {
                    gl.glVertex2f(-80_000, i);
                    gl.glVertex2f(80_000, i);
                }
                //vertical lines
                for (int i = -80_000; i < 80_000; i += this.gridSelection) {
                    gl.glVertex2f(i, -80_000);
                    gl.glVertex2f(i, 80_000);
                }

            }
        }
        gl.glEnd();
        gl.glDisable(GL3bc.GL_BLEND);

        
        //=====================
        //Draw polygon builder
        //=====================
        if (this.polygonBuilder.active)
        {
            gl.glMatrixMode(GL3bc.GL_MODELVIEW);
            gl.glLoadIdentity();
            glu.gluLookAt(getViewport().getBottomLeftCoordinate().x, getViewport().getBottomLeftCoordinate().y, 1, getViewport().getBottomLeftCoordinate().x, getViewport().getBottomLeftCoordinate().y, 0, 0, 1, 0);
            polygonBuilder.draw(gl);
        }
        
        
        //======================
        //Draw Item Highlight
        //======================

        if (this.selectedItem != null && this.selectedItemLocation == ItemLocation.WORLD) //if the item is in the world
        {
            //set up correct coordinate axis
            gl.glMatrixMode(GL3bc.GL_MODELVIEW);
            gl.glLoadIdentity();
            glu.gluLookAt(getViewport().getBottomLeftCoordinate().x * (MapEditorScene.getLayerConversionFactor(Layer.MAIN.saveInt, selectedItemLayer)), getViewport().getBottomLeftCoordinate().y * (MapEditorScene.getLayerConversionFactor(Layer.MAIN.saveInt, selectedItemLayer)), 1, getViewport().getBottomLeftCoordinate().x * (MapEditorScene.getLayerConversionFactor(Layer.MAIN.saveInt, selectedItemLayer)), getViewport().getBottomLeftCoordinate().y * (MapEditorScene.getLayerConversionFactor(Layer.MAIN.saveInt, selectedItemLayer)), 0, 0, 1, 0);

            float x = 0;
            float y = 0;
            float w = 0;
            float h = 0;
            float angle = 0;

            //set position and dimention variables correctly based on the type of the item selected
            if (this.selectedItem instanceof Entity) {
                Entity item = (Entity) selectedItem;

                w = item.getImage().getWidth() * item.getImage().getScale();
                h = item.getImage().getHeight() * item.getImage().getScale();
                x = (item.getPosition().x - w / 2);
                y = (item.getPosition().y - h / 2);
                angle = item.getImage().getAngle();
            }
            if (selectedItem instanceof Image) {
                Image item = (Image) selectedItem;

                w = item.getWidth() * item.getScale();
                h = item.getHeight() * item.getScale();
                x = item.getPosition().x;
                y = item.getPosition().y;
                angle = item.getAngle();
            }
            
            //move the highlight out a bit
            w += 6;
            h += 6;
            x -=3;
            y -=3;

            //handle the rotation of the highlight
            gl.glTranslatef(x + w / 2, y + h / 2, 0.0f);
            gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
            gl.glTranslatef(-(x + w / 2), -(y + h / 2), 0.0f);

            //draw the actual highlighting box
            gl.glLineWidth(2f);
            gl.glColor4f(0, 255, 0, 1f);
            gl.glBegin(GL3bc.GL_LINES);
            {
                gl.glVertex2f(x, y);
                gl.glVertex2f(x + w, y);

                gl.glVertex2f(x + w, y);
                gl.glVertex2f(x + w, y + h);

                gl.glVertex2f(x + w, y + h);
                gl.glVertex2f(x, y + h);

                gl.glVertex2f(x, y + h);
                gl.glVertex2f(x, y);
            }
            gl.glEnd();
        }
               
        //set viewport size
        aspectRatio = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio();     
       // getViewport().setDimensions(aspectRatio.x , aspectRatio.y);
        gl.glMatrixMode(GL3bc.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0.0, aspectRatio.x, 0.0,  aspectRatio.y);


        //=================
        //Draw  HUD Layer
        //=================

        gl.glMatrixMode(GL3bc.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(0, 0, 1, 0, 0, 0, 0, 1, 0);
        ArrayList<SceneObject> Hud = this.getSceneObjectManager().get(Layer.HUD);
        for (int i = 0; i < Hud.size(); i++) {
            this.checkForLayerHidden(Layer.HUD, Hud.get(i));
            Hud.get(i).draw(gl);
        }


        //==================
        //Draw Menu Layer
        //==================

        gl.glMatrixMode(GL3bc.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(0, 0, 1, 0, 0, 0, 0, 1, 0);
        ArrayList<SceneObject> menu = this.getSceneObjectManager().get(Layer.MENU);
        for (int i = 0; i < menu.size(); i++) {
            menu.get(i).draw(gl);
        }
        
        //==================
        // Viewport Feelers
        //==================
        if(GameplaySettings.getInstance().viewportFeelers)
        {
            EnhancedViewport viewport = (EnhancedViewport)getViewport();
            gl.glMatrixMode(GL3bc.GL_MODELVIEW);
            gl.glDisable(GL3bc.GL_BLEND);
            gl.glDisable(GL3bc.GL_TEXTURE_2D); 
            gl.glLoadIdentity();
            glu.gluLookAt(viewport.getBottomLeftCoordinate().x * Layer.MAIN.coordinateScalingFactor, viewport.getBottomLeftCoordinate().y * Layer.MAIN.coordinateScalingFactor, 1, viewport.getBottomLeftCoordinate().x * Layer.MAIN.coordinateScalingFactor, viewport.getBottomLeftCoordinate().y * Layer.MAIN.coordinateScalingFactor, 0, 0, 1, 0);

            gl.glColor4f(1,0,0,1);
            gl.glLineWidth(2);
            gl.glBegin(GL3bc.GL_LINES);
                gl.glVertex2f(viewport.topLine.getStart().getX(),viewport.topLine.getStart().getY());
                gl.glVertex2f(viewport.topLine.getEnd().getX(),viewport.topLine.getEnd().getY());
            gl.glEnd();

            gl.glColor4f(0,1,0,1);
            gl.glBegin(GL3bc.GL_LINES);
                gl.glVertex2f(viewport.bottomLine.getStart().getX(),viewport.bottomLine.getStart().getY());
                gl.glVertex2f(viewport.bottomLine.getEnd().getX(),viewport.bottomLine.getEnd().getY());
            gl.glEnd();

            gl.glColor4f(0,0,1,1);
            gl.glBegin(GL3bc.GL_LINES);
                gl.glVertex2f(viewport.leftLine.getStart().getX(),viewport.leftLine.getStart().getY());
                gl.glVertex2f(viewport.leftLine.getEnd().getX(),viewport.leftLine.getEnd().getY());
            gl.glEnd();

            gl.glColor4f(1,1,0,1);
            gl.glBegin(GL3bc.GL_LINES);
                gl.glVertex2f(viewport.rightLine.getStart().getX(),viewport.rightLine.getStart().getY());
                gl.glVertex2f(viewport.rightLine.getEnd().getX(),viewport.rightLine.getEnd().getY());
            gl.glEnd();
        }
     
    }
  
    public void add(Sound sound)
    {
        AudioRenderer.playSound(sound);
    }

    public void handleInput() 
    {
        //input snapshot
        InputSnapshot inputSnapshot = Game.getInstance().getInputHandler().getInputSnapshot();
        
        //set the world mouse location 
        // world mouse location is going to be the viewport postion + how far into the viewport the cursor is poportional to the current viewport size
        int mouseX = (int)(getViewport().getBottomLeftCoordinate().x + (((float)inputSnapshot.getScreenMouseLocation().x / (float)Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x)  *  getViewport().getWidth()));     
        int mouseY = (int)(getViewport().getBottomLeftCoordinate().y + ((float)(inputSnapshot.getScreenMouseLocation().y / (float)Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().y)  *  getViewport().getHeight())); 
        MapEditorScene.worldMouseLocation = new Point(mouseX, mouseY);

        //exit
        if (inputSnapshot.isKeyReleased(KeyEvent.VK_ESCAPE)) 
        {
            Game.getInstance().unloadScene(MapEditorScene.class); 
            Game.getInstance().loadScene(new MainMenuScene());
            Game.getInstance().changeScene(MainMenuScene.class,new ArrayList(){{add(true);}});
        }

        //test
        if (inputSnapshot.isKeyReleased(KeyEvent.VK_F5))
        {
            this.jumpIntoLevel = true;
            this.placingStartingPosition = true;
            Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.WRENCH));
        }
        
        if (this.polygonBuilder.active)
        {
            polygonBuilder.handleInput(inputSnapshot);
        }
        
        //mouse clicked
        if (inputSnapshot.isMouseClicked() && !isMouseOverMenu() && !this.polygonBuilder.active) 
        {
           
            if(!this.placingStartingPosition)
            {
                //if we have something in our hand place it
                if ((hand != null) && inputSnapshot.buttonClicked() == 1) 
                {
                    this.placeItem();
                } 
                //if we right clicked, empty the hand
                else if ((hand != null) && inputSnapshot.buttonClicked() == 3) 
                {
                    
                    this.setHand(null);

                    if (this.selectedItemLocation == ItemLocation.WORLD) {
                        this.selectedItem = null;
                        this.propertiesMenu.selectedItemChanged();
                    }
                } 
                //else attempt to do in level selection
                else if (hand == null)
                {
                    //iterate over everything in the level
                    ArrayList<SceneObject> layerObjects;
                    float x = 0;
                    float y = 0;
                    float w = 0;
                    float h = 0;
                    SceneObject tempSelection = null;
                    ItemLocation tempItemLocation = null;
                    int tempLayer = 0;
                    boolean clickedObject = false;

                    //Perform check for layer selection option
                    int fromLayer = Layer.PARALLAX1.saveInt; 
                    int toLayer = Layer.WORLD_HUD.saveInt;                    
                    if (layerMenu.selectionOption.isChecked())
                    {
                        fromLayer = this.selectedLayer;
                        toLayer = this.selectedLayer;
                    }
                    
                    for (int i = fromLayer; i <= toLayer; i++)
                    {
                        if (hand == null)
                        {
                            float correctedMouseX = this.getCorrectedLayerPlacementCoordinates(i).x;
                            float correctedMouseY = this.getCorrectedLayerPlacementCoordinates(i).y;

                            layerObjects = this.getSceneObjectManager().get(Layer.fromIntToLayer(i));
                            for (int j = 0; j < layerObjects.size(); j++)
                            {
                                if (hand == null)
                                {
                                    boolean mouseOn = false;
                                    
                                    if (layerObjects.get(j) instanceof Entity)
                                    {
                                        Entity entity = (Entity)layerObjects.get(j);
                                        Shape shape = entity.getBody().getShape();
                                        
                                        if(shape.contains(new Vector2f(correctedMouseX,correctedMouseY), new Vector2f(entity.getPosition().x,entity.getPosition().y), entity.getBody().getRotation()))
                                            mouseOn = true;                                       

                                    }
                                    else if (layerObjects.get(j) instanceof Image)
                                    {
                                        Image item = (Image) layerObjects.get(j);
                                        Shape shape = new Box(item.getWidth()*item.getScale(), item.getHeight()*item.getScale());
                                        
                                        SylverVector2f centerPos = new SylverVector2f(item.getPosition());
                                        centerPos.add(new SylverVector2f(item.getWidth()/2f,item.getHeight()/2f));
                                        
                                        if(shape.contains(new Vector2f(correctedMouseX,correctedMouseY), new Vector2f(centerPos.x,centerPos.y), item.getAngle()))
                                            mouseOn = true;   
                                  
                                    }
                                    else
                                        continue;

                                    //if our mouse is on the object
                                    if (mouseOn == true)
                                    {
                                        clickedObject = true;
                                        this.propertiesMenu.open();
                                        //if this item is selected already put it in your hand
                                        // == operator works here because it literally is the same guy.
                                        if (((SceneObject) layerObjects.get(j)) == this.selectedItem)
                                        {
                                            this.remove(this.selectedItem);
                                            this.setHand(this.selectedItem);
                                            //Set the layer
                                            if (!this.layerMenu.lockOption.isChecked())
                                                this.layerMenu.setSelection(i);
                                            //Should set the layer to the layer that the object was in so we can place it in the same one easily
                                            //this.selectedLayer = i;
                                            //But this doesn't update the layer selector.
                                        }
                                        else
                                        {
                                            tempSelection = (SceneObject) layerObjects.get(j);
                                            tempItemLocation = ItemLocation.WORLD;
                                            tempLayer = i;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (clickedObject && hand == null) 
                    {
                        this.selectedItem = tempSelection;
                        this.selectedItemLocation = tempItemLocation;
                        this.selectedItemLayer = tempLayer;
                        this.propertiesMenu.selectedItemChanged();
                    }

                }
            }
            else // place starting position
            {
                this.placingStartingPosition  = false;
                
                Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND));
                if (this.jumpIntoLevel)
                {
                    this.jumpIntoLevel = false;
                    this.testCurrentLevel(MapEditorScene.worldMouseLocation.x,MapEditorScene.worldMouseLocation.y);                    
                }
            }
        }

        if (!scriptWindow.isOpen())
        {

            //open grid menu
            if (inputSnapshot.isKeyReleasedCtrlModifier(KeyEvent.VK_G)) 
            {
                if (gridMenu.isOpen()) {
                    gridMenu.close();
                } else {
                    this.gridMenu.open();
                }
            }

            //open properties menu
            if (inputSnapshot.isKeyReleasedCtrlModifier(KeyEvent.VK_P)) 
            {
                if (propertiesMenu.isOpen()) {
                    propertiesMenu.close();
                } else {
                    this.propertiesMenu.open();
                }
            }
            //open palette menu
            if (inputSnapshot.isKeyReleasedCtrlModifier(KeyEvent.VK_A)) 
            {
                if (this.paletteMenu.isOpen()) {
                    paletteMenu.close();
                } else {
                    this.paletteMenu.open();
                }
            }
            //open layerObjects menu
            if (inputSnapshot.isKeyReleasedCtrlModifier(KeyEvent.VK_L)) 
            {
                if (layerMenu.isOpen()) {
                    layerMenu.close();
                } else {
                    this.layerMenu.open();
                }
            }
            //open level properties menu
            if (inputSnapshot.isKeyReleasedCtrlModifier(KeyEvent.VK_E)) 
            {
                if (levelProperties.isOpen()) {
                    levelProperties.close();
                } else {
                    this.levelProperties.open();
                }
            }

             //camera zoom
            if (inputSnapshot.isKeyPressedCtrlModifier(KeyEvent.VK_EQUALS))
            {
                this.zoom += 20;
            }
            if (inputSnapshot.isKeyPressedCtrlModifier(KeyEvent.VK_MINUS)) 
            {
                this.zoom -= 20;
            }

            //delete is pressed
            if (inputSnapshot.isKeyReleased(KeyEvent.VK_DELETE)) 
            {
                if (this.selectedItem != null && this.selectedItemLocation == ItemLocation.WORLD && this.hand == null) 
                {
                    this.remove(selectedItem);
                    this.selectedItem = null;
                    this.propertiesMenu.selectedItemChanged();
                }
            }

            //camera movement
            if(inputSnapshot.isKeyPressedAltModifier(KeyEvent.VK_LEFT))
            {
                this.viewportLocation.x = this.viewportLocation.x - 20;
            }
            else if (inputSnapshot.isKeyPressed(KeyEvent.VK_LEFT)) {
                this.viewportLocation.x = this.viewportLocation.x - 10;
            }
            
            if (inputSnapshot.isKeyPressedAltModifier(KeyEvent.VK_RIGHT)) {
                this.viewportLocation.x = this.viewportLocation.x + 20;
            }
            else if (inputSnapshot.isKeyPressed(KeyEvent.VK_RIGHT)) {
                this.viewportLocation.x = this.viewportLocation.x + 10;
            }
            
            if (inputSnapshot.isKeyPressedAltModifier(KeyEvent.VK_UP)) {
                this.viewportLocation.y = this.viewportLocation.y + 20;
            }
            else if (inputSnapshot.isKeyPressed(KeyEvent.VK_UP)) {
                this.viewportLocation.y = this.viewportLocation.y + 10;
            }
            
            if (inputSnapshot.isKeyPressedAltModifier(KeyEvent.VK_DOWN)) {
                this.viewportLocation.y = this.viewportLocation.y - 20;
            }
            else if (inputSnapshot.isKeyPressed(KeyEvent.VK_DOWN)) {
                this.viewportLocation.y = this.viewportLocation.y - 10;
            }
            
            
            if (inputSnapshot.isKeyReleasedCtrlModifier(KeyEvent.VK_M))
            {
                
              
                
//                for(SceneObject so: this.getSceneObjectManager().get(Layer.MAIN))
//                {
//                    if(so instanceof NonPlayerEntity)
//                    {
//                        if(((NonPlayerEntity)so).getImage().getAnimationPack() instanceof FlierAnimationPack)
//                        {
//                            ((NonPlayerEntity)so).getCombatData().baseDamage.setBase(10);
//                        }
//                    }
//                    else if(so instanceof MobSpawner)
//                    {
//                        if(((MobSpawner)so).mobToSpawn.getImage().getAnimationPack() instanceof FlierAnimationPack)
//                        {
//                            ((MobSpawner)so).mobToSpawn.getCombatData().baseDamage.setBase(10);
//                        }
//                            
//                    }
//                }
            }
            
            
            
        }
    }

    public void sceneEntered(ArrayList args) 
    {
        Game.getInstance().getGraphicsWindow().setCursor(CursorFactory.getInstance().getCursor(CursorType.HAND));
        
        //stop music
        Sound sound = Sound.newBGM("");
        add(sound);
            
        if (args != null && args.size() > 1)
        {
            String path = (String)args.get(0);
            loadLevel(path);
            File directory = new File ("");
            
            path = directory.getAbsolutePath() + "\\Save Games\\";
            String fileName;

            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();

            String selectedSave = ((SaveGame)args.get(1)).fileName;
            
            for (int i = 0; i < listOfFiles.length; i++) 
            {
                fileName = listOfFiles[i].getName().toLowerCase();
                if (listOfFiles[i].isFile() && fileName.endsWith(".save")) 
                {
                    //if it exists, delete
                    if (selectedSave.equals(fileName))
                    {
                        boolean success = (new File(path + selectedSave)).delete();            
                    }           
                }            
            }
            
        }
    }

    public void sceneExited() 
    {
    }

    
    ///===============
    // Class methods
    //================
    
    /**Checks if the view option in layer menu is set, and colors this scene object with .1 alpha
    *if so.
    */
    private void checkForLayerHidden(Layer layerNum, SceneObject obj)
    {
        if (this.layerMenu.viewOption.isChecked() && this.selectedLayer != layerNum.saveInt)
        {
            if (obj instanceof Image)
            {
                Image img = (Image)obj;
                float a = img.getColor().a;
                img.setAlphaBrightness(.2f);
            }
            else if (obj instanceof Entity)
            {               
                 ((Entity)obj).getImage().setAlphaBrightness(.2f);
            }
        }
        else
        {
            if (obj instanceof Image)
            {
                ((Image)obj).setAlphaBrightness(1);
            }
            else if (obj instanceof Entity)
            {               
                 ((Entity)obj).getImage().setAlphaBrightness(1f);
            } 
        }
    }
    
    /**
     * Replaces whatever is in your hand with the given item
     * @param newHandItem Object to be placed into hand
     */
    public void setHand(SceneObject newHandItem)
    {
        //set old hand to null
        if (this.hand != null)
        {
            if(this.hand instanceof DarkSource)
                this.getSceneObjectManager().remove(hand);
            
            this.hand = null;         
        }
               

        //set the hand
        hand = newHandItem;
        
        if(hand != null)
        {
            
            hand.setPosition(Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x, Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y);

            //add some alpha to the hand item
            if (hand instanceof Image) 
            {
                if(((Image)hand).getColor().a > .6)
                    ((Image) hand).setAlphaBrightness(.7f);
            }
            if (hand instanceof WorldObjectEntity)
            {
                if(((WorldObjectEntity) hand).getImage().getColor().a > .6)
                    ((WorldObjectEntity) hand).getImage().setAlphaBrightness(.7f);
            }

            //moves whats in our hand
            if (hand != null && this.gridSelection != 0) //moves the thing along the grid snap
            {
                if (hand instanceof Entity) 
                {
                    hand.setPosition(MapEditorScene.worldMouseLocation.x - MapEditorScene.worldMouseLocation.x % this.gridSelection + ((Entity) hand).getWidth() / 2, MapEditorScene.worldMouseLocation.y - MapEditorScene.worldMouseLocation.y % this.gridSelection + ((Entity) hand).getHeight() / 2);
                } 
                else if (hand instanceof Image)
                {
                    hand.setPosition(MapEditorScene.worldMouseLocation.x - MapEditorScene.worldMouseLocation.x % this.gridSelection, MapEditorScene.worldMouseLocation.y - MapEditorScene.worldMouseLocation.y % this.gridSelection);
                }
            } 
            else if (hand != null) //if there is no grid snap
            {
                hand.setPosition(MapEditorScene.worldMouseLocation.x, MapEditorScene.worldMouseLocation.y);
            }

            //handle lighting
            if(hand instanceof DarkSource)
                this.getSceneObjectManager().add(hand, Layer.MAIN);

            //selected item location
            this.selectedItemLocation = ItemLocation.HAND; 
        
        }
        

    }

    /**
     * This makes a deep copy of the given SavableSceneObject. This is required because not all of our
     * objects have copy constructors. This workaround uses the serialization method that we employ to save our games
     * as a means of making the copy
     * @param oldObj The object to be copied
     * @return New deep copy of old object
     */
    public static SceneObject makeCopyOfObject(SceneObject oldObj) 
    {
        SceneObjectSaveData saveData = ((SavableSceneObject)oldObj).dumpFullData();
        SceneObject obj = null;
        try
        {
            obj = SceneObjectDeserializer.buildSceneObjectFromSaveData(saveData);
            if( obj.getID() != null && obj.getID().startsWith("$")) 
            {
                obj.setID( "$" + UUID.randomUUID().toString().substring(0, 7));
            }
        } 
        catch (Exception e){System.out.println("Make Copy of Object failed"); return new Image("textureMissing.jpg");}        
        return obj;
    }

    /**
     * places an item from your hand into the world at the layerObjects selected in the layerObjects menu
     */
    public void placeItem() 
    {
        //make copy of the item 
        SceneObject copy = MapEditorScene.makeCopyOfObject(hand);

        Vector2f correctedLocation = getCorrectedLayerPlacementCoordinates(this.selectedLayer);
        //set its position according to the grid snap and layerObjects conversion
        if (this.gridSelection != 0) 
        {
            if (copy instanceof WorldObjectEntity) 
                copy.setPosition((correctedLocation.x - correctedLocation.x % this.gridSelection + ((WorldObjectEntity) copy).getWidth() / 2), (correctedLocation.y - correctedLocation.y % this.gridSelection + ((WorldObjectEntity) copy).getHeight() / 2));
             else if (copy instanceof Image) 
                copy.setPosition((correctedLocation.x - correctedLocation.x % this.gridSelection), (correctedLocation.y - correctedLocation.y % this.gridSelection));
            
        } 
        else //if there is no grid snap
        {
            copy.setPosition(correctedLocation.x, correctedLocation.y);
        }
        
        //if the thing is an entity
        //check to see if the current id is in the entity manager, if so increment the number at the end and check again
           
                   
        String workingID = copy.getID();

        if( workingID != null && !workingID.startsWith("$"))
        {
            //if the entity manager contains an entity with this id already
            while(this.getSceneObjectManager().get(workingID) != null)
            {
                //find the index of the dash
                int index =workingID.lastIndexOf('-'); 

                //if there is no dash add a '-1' and check again
                if( index == -1)
                {
                    workingID += "-1";
                }
                else
                {
                    int number = Integer.parseInt(workingID.substring(index + 1));
                    number++;
                    String stringNumber = Integer.toString(number);
                    workingID = workingID.substring(0, index+1) + stringNumber;
                }

            }

            copy.setID(workingID);
        }
        

        //add the copy to the scene
        this.add(copy, Layer.fromIntToLayer(this.selectedLayer));

        //Confirmation sound
//        Sound sound= Sound.playAmbient("buttonBoop.wav", true);
//        add(sound);

        //set the alpha back
        if (copy instanceof Image) {
            Image image = (Image) copy;
            image.setAlphaBrightness(1);
        }
        if (copy instanceof WorldObjectEntity) {
            WorldObjectEntity entity = (WorldObjectEntity) copy;
            entity.getImage().setAlphaBrightness(1);
        }
    }

    /**
     * This function is used to determine if the mouse is currently over an open menu
     * @return True if the mouse is over an open menu
     */
    private boolean isMouseOverMenu() {
        for (Window m : menus) {
            if (m.isOpen() && Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x >= m.getPosition().x && Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().x <= m.getPosition().x + m.getWidth() && Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y >= m.getPosition().y && Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation().y <= m.getPosition().y + m.getHeight()) {
                return true;
            }

        }
        return false;
    }

    /**
     * Returns the conversion factor between any two layers. You can use this ratio to convert from the coordinate plane of one layerObjects to another
     * @param fromLayer The layerObjects you are converting from
     * @param toLayer The layerObjects you are converting too
     * @return  The ratio that must be multiplied with the first layers coordinate to get the second.
     */
    public static float getLayerConversionFactor(int fromLayer, int toLayer) {
        ArrayList<Float> factors = new ArrayList<>();
        factors.add(0f);  //background
        factors.add(.9f); //parallax 1
        factors.add(.8f); //parallax 2
        factors.add(.7f); //parallax 3
        factors.add(.6f); //parallax 4
        factors.add(.501f); //parallax 5
        factors.add(1f);  //attached bg
        factors.add(1f); //main
        factors.add(1f); //attached fg
        factors.add(1f); //world hud layer
        factors.add(0f); // null
        factors.add(0f); //null
        factors.add(1.1f); //foreground 1
        factors.add(1.2f); //foreground 2

        return factors.get(toLayer) / factors.get(fromLayer);
    }

    /**
     * Returns the corrected coordinates so that you can place items on other layers and they will appear right behind your mouse
     * cursor. The coordinate correction is required because the layerObjects layers are all on different coordinate planes.
     * @param layerObjects The layerObjects that you are going to place your item into
     * @return The corrected coordinate pair
     */
    private Vector2f getCorrectedLayerPlacementCoordinates(int layer) {

        float correctedX = (1 - getLayerConversionFactor(7, layer)) * (MapEditorScene.worldMouseLocation.x - this.getViewport().getBottomLeftCoordinate().x) + MapEditorScene.worldMouseLocation.x * getLayerConversionFactor(7, layer);
        float correctedY = (1 - getLayerConversionFactor(7, layer)) * (MapEditorScene.worldMouseLocation.y - this.getViewport().getBottomLeftCoordinate().y) + MapEditorScene.worldMouseLocation.y * getLayerConversionFactor(7, layer);
        return new Vector2f(correctedX, correctedY);
    }

    public void loadLevel(String level)
    {
        //Empty the scene
        for (Layer layer: Layer.values()) 
        {
            if(layer == Layer.MENU)
                continue;
            
            ArrayList<SceneObject> layerObjs = (ArrayList<SceneObject>) this.getSceneObjectManager().get(layer);
            for (int i = 0; i < layerObjs.size(); i++) {
                this.remove(layerObjs.get(i));
                i--;
            }
        }
        //Build the new level
        LevelData loaded;
        try 
        {
            loaded = LevelLoader.getInstance().getLevelData(level);
            ArrayList<SimpleEntry<SceneObject,Layer>> sceneObjectList = loaded.getSceneObjects();
            for(SimpleEntry<SceneObject,Layer> entry:sceneObjectList)
            {
                this.add(entry.getKey(), entry.getValue());
            }
            this.loadedLevelData = loaded;
            this.levelProperties.setAmbientLight(loaded.getAmbientLight());
            this.getSceneEffectsManager().sceneAmbientLight = loaded.getAmbientLight();
      

        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        
        
    }

    public void saveLevel(String levelName) 
    {
        
        /**
         * Populate the scene object data list.
         * 
         * IMPORTANT: after dumping the savabledata from the object,
         * make THIS CALL:
         * 
         * e.g.
         * SceneObjectSaveData sceneObjectData = ((Savable)obj).dumpFullData();
         * sceneObjectData.set(0, layer);
         * 
         * This is because the second element of the scene object is reserved
         * for layerObjects metadata and must be included.
         */
        ArrayList<SceneObjectSaveData> sceneObjectData = new ArrayList();
        for (Layer layer: Layer.values())
        {
            if(layer == Layer.HUD || layer == Layer.MENU)
                continue;
            
            ArrayList<SceneObject> list = (ArrayList<SceneObject>) this.getSceneObjectManager().get(layer);
            for (int i = 0; i < list.size(); i++)
            {

                SceneObject obj = (SceneObject) list.get(i);
                if(!(obj instanceof AbstractParticleEmitter) && !(obj instanceof LightSource)) //particle emitters are already saved in the entity we dont need to save them again
                {
                    SceneObjectSaveData savedObj = ((SavableSceneObject)obj).dumpFullData();
                    //Adding layer to the data
                    savedObj.dataMap.put("layer", layer.saveInt);
                    sceneObjectData.add(savedObj);
                }
            }
        }
        
        LevelData level = new LevelData(sceneObjectData,this.levelProperties.getAmbientLight());
        
        //tell the level to save
        level.writeLevelDataToDisk(levelName); 
        
        //reload level into level loader
        LevelLoader.getInstance().reloadLevel(levelName);

        //play a noise so we know we saved
        Sound sound= Sound.ambientSound("buffered/bigLaser.ogg", true);
        add(sound);
               
    }
    
    public void newLevel()
    {
        //Empty the scene
        for (Layer layer: Layer.values()) 
        {
            if(layer == Layer.MENU)
                continue;
            
            ArrayList<SceneObject> layerObjs = (ArrayList<SceneObject>) this.getSceneObjectManager().get(layer);
            for (int i = 0; i < layerObjs.size(); i++) {
                this.remove(layerObjs.get(i));
                i--;
            }
        }
        
        //set default level properties
        this.levelProperties.setAmbientLight(new Color(1f,1f,1f,1f));
        
        //add spawner entity
        StaticBody body = new StaticBody(new Box(100,200));
        body.setBitmask(Entity.BitMasks.NO_COLLISION.value);
        body.setOverlapMask(Entity.OverlapMasks.NO_OVERLAP.value);      
        Image img = new Image("textureMissing.jpg");
        img.setDimensions(100, 200);
        WorldObjectEntity entity = new WorldObjectEntity(img, body);
        entity.setPosition(0, 0);
        entity.setID("checkpoint1");
        this.add(entity, Layer.MAIN);
        
        //level data
        this.loadedLevelData.filename = "newLevel.lv";
        
        
    }
   
    public void testCurrentLevel(final float x,final float y)
    {
        //Build a point to load at        
        
        //Dummy save game
        final PlayerEntity player = new PlayerEntity(new Image(new AnimationPackClasses.BashBrownBodyAnimationPack()),new Image("bash-head1.png"),new Image(new AnimationPackClasses.BashBrownFrontArmAnimationPack()),new Image(new AnimationPackClasses.BashBrownFrontArmAnimationPack()));
                   
        player.setName("testplayer");
        final SaveGame save = new SaveGame();
        save.setPlayer(player);
        save.save(player.getName() + ".save");        

        //save level
        this.saveLevel(this.loadedLevelData.filename);
        
        Game.getInstance().loadScene(new LoadingScene());
        Game.getInstance().changeScene(LoadingScene.class, null);

        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                //if game client is loaded for some reason unload it
                if(Game.getInstance().isLoaded(GameClientScene.class))
                    Game.getInstance().unloadScene(GameClientScene.class); 

                //start a server
                GobletServer.ServerConfiguration config = new GobletServer.ServerConfiguration();
                Game.getInstance().addRunnable("Goblet Server", new GobletServer(config));

                //load game client scene
                Game.getInstance().loadScene(new GameClientScene(save)); 

                //connect to server
                try
                {
                    ((GameClientScene)Game.getInstance().getScene(GameClientScene.class)).connectToServer( "127.0.0.1", 50501);                   
                }
                catch(ConnectionException e){System.err.println("Couldnt connect: " + e.reason); return;}

                try{
                    Thread.sleep(1000);
                }
                catch(Exception e){}
                
                //switch to GameClientScene
                ArrayList args = new ArrayList();
                args.add(loadedLevelData.filename);
                Game.getInstance().changeScene(GameClientScene.class,args); 

                //unload loading scene
                Game.getInstance().unloadScene(LoadingScene.class);
                
                 //tell the server to move the player to the right place
                ((GobletServer)Game.getInstance().getRunnable("Goblet Server")).queueMovePlayerToLevel(((GameClientScene)Game.getInstance().getScene(GameClientScene.class)).clientID.toString(), loadedLevelData.filename, new SylverVector2f(x,y));
                
                
                
                
                
            }
        };

        thread.start();
            
         
    }
    
    /**
     * A class that will let us construct a polygon when we are building that particular body type. 
     */
    public class PolygonBuilder{
        
        public boolean active = false;
        
        //The vertices for this polygon
        private ArrayList<Vector2f> vertices; 
        
        private float nextX, nextY;
        
        public PolygonBuilder(){
            vertices = new ArrayList();
        }
        
        public void handleInput(InputSnapshot snapshot)
        {
            if (snapshot.isMouseClicked() && !isMouseOverMenu())
            {
                //Left click
                if (snapshot.buttonClicked() == 1) 
                {
                    this.addVertex(new Vector2f(nextX, nextY));
                } 
                //Right click
                else if (snapshot.buttonClicked() == 3) 
                {
                    if (!vertices.isEmpty())
                        this.removeLast();
                }
            }
            
            if (!scriptWindow.isOpen())
            {
                //Backspace (cancel)
                if(snapshot.isKeyReleased(KeyEvent.VK_BACK_SPACE))
                {
                    propertiesMenu.postPolygonCreate(null);
                    this.vertices.clear();
                    this.setActive(false);
                }

                //Confirm
                if(snapshot.isKeyReleased(KeyEvent.VK_SPACE) || snapshot.isKeyReleased(KeyEvent.VK_ENTER))
                {
                    propertiesMenu.postPolygonCreate(vertices);
                    this.vertices.clear();
                    this.setActive(false);
                }
            }
        }
        
        public void update()
        {
            nextX = MapEditorScene.worldMouseLocation.x; 
            nextY = MapEditorScene.worldMouseLocation.y; 
            if (gridSelection != 0)
            {
                nextX -= MapEditorScene.worldMouseLocation.x % gridSelection; 
                nextY -= MapEditorScene.worldMouseLocation.y % gridSelection;
            }
        }
        
        public void draw(GL2 gl)
        {
            gl.glColor3f(0, .5f, 0);
            gl.glBegin(GL3bc.GL_QUADS);
            {
                gl.glVertex2f(nextX-2f, nextY-2f);
                gl.glVertex2f(nextX-2f, nextY+2f);
                gl.glVertex2f(nextX+2f, nextY+2f);
                gl.glVertex2f(nextX+2f, nextY-2f);
            }
            gl.glEnd();
            
            if (!vertices.isEmpty())
            {
                gl.glColor3f(0, .5f, 1);
                gl.glBegin(GL3bc.GL_LINES);
                {
                    int size = vertices.size();
                    Vector2f p1; 
                    Vector2f p2;
                    for(int i = 0; i < vertices.size(); i ++)
                    {
                        p1 = vertices.get(i);
                        p2 = vertices.get( (i+1) % size);

                        gl.glVertex2f(p1.x, p1.y);
                        gl.glVertex2f(p2.x, p2.y);                                 
                    }
                    
                    gl.glColor3f(0, .5f, 0);
                    Vector2f last = vertices.get(size-1);
                    Vector2f first = vertices.get(0);
                    
                    //Draw connection between last and next
                    gl.glVertex2f(last.x, last.y);
                    gl.glVertex2f(nextX, nextY);
                    
                    //First and next
                    gl.glVertex2f(nextX, nextY);
                    gl.glVertex2f(first.x, first.y);

                }
                gl.glEnd();
                
                gl.glColor3f(1, 1, 1);    
            }
        }
        
        public void setActive(boolean active){
            this.active = active;
        }
        
        /**
         * Adds a vertex to the builder.
         * @param vertex 
         */
        public void addVertex(Vector2f vertex){
            vertices.add(vertex);
        }
        
        /**
         * Removes the last vertex that was added to the builder.
         */
        public void removeLast(){
            vertices.remove(vertices.size()-1);
        }

        /**
         * Constructs a polygon with the array of vertices currently contained within the builder.
         * @return 
         */
        public Polygon getPolygonFromVertices(){
            Vector2f[] vertexArray = new Vector2f[vertices.size()];
            vertexArray = vertices.toArray(vertexArray);
            return new Polygon(vertexArray);
        }
    }
    
}
