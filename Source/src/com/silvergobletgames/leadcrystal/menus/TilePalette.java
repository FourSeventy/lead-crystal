package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.FlierAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.MoleAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.NPC2AnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.NPC3AnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.NPC4AnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.NPC5AnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.NPC6AnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.NPC7AnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.PlantAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.Scout1AnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.Scout2AnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.Scout3AnimationPack;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.netcode.SceneObjectDeserializer;
import com.silvergobletgames.sylver.netcode.SceneObjectSaveData;
import com.silvergobletgames.sylver.windowsystem.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import com.silvergobletgames.leadcrystal.entities.NonPlayerEntity;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.leadcrystal.scenes.MapEditorScene;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import com.silvergobletgames.sylver.graphics.Text.TextType;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author mike
 */
public class TilePalette extends Menu {

    
    //current image selection
    private SceneObject sceneObjectSelection;
    
    //list of image palette buttons and labels
    private ArrayList<Button> imagePaletteButtons = new ArrayList();
    private ArrayList<Label> imagePaletteLabels = new ArrayList<>();
    //list of wo palette buttons and labels
    private ArrayList<Button> woPaletteButtons = new ArrayList();
    private ArrayList<Label> woPaletteLabels = new ArrayList<>();
    //list of npe palette buttons and labels
    private ArrayList<Button> npePaletteButtons = new ArrayList();
    private ArrayList<Label> npePaletteLabels = new ArrayList<>();
    
    //tab pane
    protected TabPane tabPane;
    
    //current image radio set selection
    private String imageRadioSetSelection;
    //lists of image palettes
    private HashMap<String,ArrayList<Image>> imageGroupMap = new HashMap<>();   
    private ArrayList<Image> animatedTerrain = new ArrayList<>();    
    private ArrayList<Image> characters = new ArrayList<>();  
    private ArrayList<Image> savedImages = new ArrayList<>();
    private ArrayList<Image> backgrounds = new ArrayList<>();
    private ArrayList<Image> townDoodads = new ArrayList<>();  
    private ArrayList<Image> desertTiles = new ArrayList<>();
    private ArrayList<Image> desertTiles2 = new ArrayList<>();
    private ArrayList<Image> desertDoodads = new ArrayList<>();
    private ArrayList<Image> specialDoodads = new ArrayList<>();
    
    
    //current world object radio set selection
    private String woRadioSetSelection;
    //lists of world object palettes
    private TreeMap<String,ArrayList<WorldObjectEntity>> woGroupMap = new TreeMap<>();
    private ArrayList<WorldObjectEntity> townWO = new ArrayList<>();
    private ArrayList<WorldObjectEntity> desertTilesWO = new ArrayList<>();
    private ArrayList<WorldObjectEntity> desertTiles2WO = new ArrayList<>();
    private ArrayList<WorldObjectEntity> desertRocksWO = new ArrayList<>();
    
    
    //current npe radio set selection
    private String npeRadioSetSelection;
    //lists of npe palettes
    private HashMap<String,ArrayList<NonPlayerEntity>> npeGroupMap = new HashMap<>();
    private ArrayList<NonPlayerEntity> townEnemies = new ArrayList<>();
    private ArrayList<NonPlayerEntity> desertEnemies = new ArrayList<>();
    
    /**
     * 
     * @param x
     * @param y 
     */
    public TilePalette(float x, float y) 
    {
        super("Object Palette", x, y, 1000, 750);

        //============
        // fill groups
        //============
        
        //characters
        characters.add(new Image(new Scout1AnimationPack()));
        characters.add(new Image(new Scout2AnimationPack()));
        characters.add(new Image(new Scout3AnimationPack()));
        characters.add(new Image(new PlantAnimationPack()));
        characters.add(new Image(new NPC2AnimationPack()));
        characters.add(new Image(new NPC3AnimationPack()));
        characters.add(new Image(new NPC4AnimationPack()));
        characters.add(new Image(new NPC5AnimationPack()));
        characters.add(new Image(new NPC6AnimationPack()));
        characters.add(new Image(new NPC7AnimationPack()));
        characters.add(new Image(new FlierAnimationPack()));
        characters.add(new Image(new MoleAnimationPack()));
        
        //animated images
        
        //backgrounds
        try
        {
            ArrayList<String> backgroundStrings = this.getTexturesInFolder(Game.getInstance().getConfiguration().getTextureRootFolder().resolve( "terrain/backgrounds"));
            for(String string: backgroundStrings)
            {
                backgrounds.add(new Image(string));
            }
        }
        catch(Exception e){ System.err.println("Error getting Backgrounds folder");}
        
        //desert tiles
        try
        {
            ArrayList<String> desertStrings = this.getTexturesInFolder(Game.getInstance().getConfiguration().getTextureRootFolder().resolve("terrain/desertTiles"));
            for(String string: desertStrings)
            {
                desertTiles.add(new Image(string));
            }
        }
        catch(Exception e){ System.err.println("Error getting Desert Tiles folder");}
        
        //desert tiles2
        try
        {
            ArrayList<String> desertStrings2 = this.getTexturesInFolder(Game.getInstance().getConfiguration().getTextureRootFolder().resolve("terrain/desertTiles2"));
            for(String string: desertStrings2)
            {
                desertTiles2.add(new Image(string));
            }
        }
        catch(Exception e){ System.err.println("Error getting Desert Tiles2 folder");}
        
        //town doodads
        try
        {
            ArrayList<String> townDoodadStrings = this.getTexturesInFolder(Game.getInstance().getConfiguration().getTextureRootFolder().resolve( "terrain/townDoodads"));
            for(String string: townDoodadStrings)
            {
                townDoodads.add(new Image(string));
            }
        }
        catch(Exception e){ System.err.println("Error getting Town Doodads folder");}
        
        //desert Doodads
        try
        {
            ArrayList<String> desertDoodadStrings = this.getTexturesInFolder(Game.getInstance().getConfiguration().getTextureRootFolder().resolve( "terrain/desertDoodads"));
            for(String string: desertDoodadStrings)
            {
                desertDoodads.add(new Image(string));
            }
        }
        catch(Exception e){ System.err.println("Error getting Desert Doodads folder");}
        
        //special doodads
        try
        {
            ArrayList<String> specialDoodadStrings = this.getTexturesInFolder(Game.getInstance().getConfiguration().getTextureRootFolder().resolve("terrain/specialDoodads"));
            for(String string: specialDoodadStrings)
            {
                specialDoodads.add(new Image(string));
            }
        }
        catch(Exception e){ System.err.println("Error getting Special Doodads folder");}


        //build the image group mappings
        imageGroupMap.put("specialDoodads",specialDoodads);
        imageGroupMap.put("savedImages",savedImages);
        imageGroupMap.put("desertDoodads", desertDoodads);
        imageGroupMap.put("desertTiles", desertTiles);
        imageGroupMap.put("desertTiles2",desertTiles2);
        imageGroupMap.put("townDoodads", townDoodads); 
        imageGroupMap.put("backgrounds", backgrounds);
        imageGroupMap.put("animatedImages", animatedTerrain);
        imageGroupMap.put("characters", characters); 
               
        
        //build world object group mapping
        woGroupMap.put("deserttiles", desertTilesWO); 
        woGroupMap.put("deserttiles2", desertTiles2WO); 
        woGroupMap.put("town", townWO);       
        woGroupMap.put("desertdoodads", desertRocksWO); 
        
        //build npe group mapping
        npeGroupMap.put("town", townEnemies);
        npeGroupMap.put("desert", desertEnemies);
        
        
        
        //read in saved items and populate lists
        loadSavedItems();

        //build tab pane
        tabPane = new TabPane( 10,10,980,680);
        tabPane.addTab("Images");
        tabPane.addTab("World Obj"); 
        tabPane.addTab("NPE's");
        this.addComponent(tabPane);
        tabPane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("tabChanged")) {
                                    
                }
            }
        });
        
        //build radio set for images
        final RadioSet<String> ra = new RadioSet<>(10, 430);
        ra.addElement(new SimpleEntry("Saved","savedImages" )); 
        ra.addElement(new SimpleEntry("Spcial Doodads","specialDoodads" ));     
        ra.addElement(new SimpleEntry("Desert Doodads","desertDoodads" ));
        ra.addElement(new SimpleEntry("Desert Tiles2","desertTiles2" )); 
        ra.addElement(new SimpleEntry("Desert Tiles","desertTiles" )); 
        ra.addElement(new SimpleEntry("Town Doodads","townDoodads" ));
        ra.addElement(new SimpleEntry("Backgrounds","backgrounds" ));
        ra.addElement(new SimpleEntry("Animated","animatedImages" ));
        ra.addElement(new SimpleEntry("Characters","characters" ));

        
      
        ra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("selectionChanged")) {
                    imageRadioSetSelection = ra.getSelectedValue();
                    imageRadioSet_Changed();
                }
            }
        });
        ra.setDefaultSelection("Characters");
        tabPane.addComponent(ra, 0);
        
        //build radio set for wo's
        final RadioSet<String> ra2 = new RadioSet<>(10, 550);
        Iterator iter = this.woGroupMap.keySet().iterator();
        while(iter.hasNext())
        {
            String key = (String)iter.next();
            ra2.addElement(new SimpleEntry(key,key ));
        }    
        ra2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("selectionChanged")) {
                    woRadioSetSelection =  ra2.getSelectedValue();
                    woRadioSet_Changed();
                }
            }
        });
        ra2.setDefaultSelection("town");
        tabPane.addComponent(ra2, 1);
        
        
        //build radio set for npes
        final RadioSet<String> ra3 = new RadioSet<>(10, 600);
        iter = this.npeGroupMap.keySet().iterator();
        while(iter.hasNext())
        {
            String key = (String)iter.next();
            ra3.addElement(new SimpleEntry(key,key ));
        }    
        ra3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("selectionChanged")) {
                    npeRadioSetSelection =  ra3.getSelectedValue();
                    npeRadioSet_Changed();
                }
            }
        });
        ra3.setDefaultSelection("town");
        tabPane.addComponent(ra3, 2);
          
    }

    private void imageRadioSet_Changed()
    {
        //clear old buttons 
        for (WindowComponent c : imagePaletteButtons) {
            tabPane.removeComponent(c,0);
        }
        imagePaletteButtons.clear();
        
        //clear old labels
        for(WindowComponent c: imagePaletteLabels)
        {
            tabPane.removeComponent(c, 0);
        }
        imagePaletteLabels.clear();
        
        //get the correct group
        final ArrayList<Image> list = imageGroupMap.get(imageRadioSetSelection);
        
         //build palette buttons
        for (int i = 0; i < list.size(); i++) 
        {
            final Button button = new Button((Image)MapEditorScene.makeCopyOfObject(list.get(i)), 140 + (100 * i) % 800, (this.height - 160) - 100 * ((int) i / 8), 75, 75);
            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("clicked")) {
                        sceneObjectSelection = list.get(imagePaletteButtons.indexOf(button));
                        fireAction(this, "selectionChanged");
                    }
                }
            });
            
            String text = list.get(i).getTextureReference();
            if(text != null && text.contains("."))
               text =text.substring(0, text.indexOf("."));
            if(text == null)
            { 
                text = list.get(i).getAnimationPack().getClass().getSimpleName();
                text =text.substring(0, text.indexOf("AnimationPack"));
            
            }
            Label label = new Label(text,140 + (100 * i) % 800, (this.height - 180) - 100 * ((int) i / 8));
            label.getText().setColor(new Color(Color.orange));
            label.getText().setScale(1f);
            label.getText().setTextType(CoreTextType.CODE);
            tabPane.addComponent(label, 0);
            tabPane.addComponent(button, 0);
            this.imagePaletteButtons.add(button);
            this.imagePaletteLabels.add(label);
        }
        
        super.setPosition(this.getPosition().x, this.getPosition().y); 


    }
    
    private void woRadioSet_Changed()
    {
        //clear old buttons
        for (WindowComponent c : woPaletteButtons) {
            tabPane.removeComponent(c,1);
        }
        woPaletteButtons.clear();
        
        //clear old labels
        for(WindowComponent c: woPaletteLabels)
        {
            tabPane.removeComponent(c, 1);
        }
        woPaletteLabels.clear();
        
        //get the correct group
        final ArrayList<WorldObjectEntity> list = woGroupMap.get(woRadioSetSelection); 
        
        //build new palette buttons from the group
        for (int i = 0; i < list.size(); i++) 
        {
            final Button button = new Button(new Image("blank.png"), 140 + (100 * i) % 700, (this.height - 160) - 100 * ((int) i / 7), 75, 75);
            button.setImage((Image)MapEditorScene.makeCopyOfObject(list.get(i).getImage()));
            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("clicked")) {
                        sceneObjectSelection = list.get(woPaletteButtons.indexOf(button));
                        fireAction(this, "selectionChanged");
                    }
                }
            });
            
            Label label = new Label(list.get(i).getName(),140 + (100 * i) % 700, (this.height - 180) - 100 * ((int) i / 7));
            label.getText().setColor(new Color(Color.orange));
            label.getText().setScale(.6f);
            tabPane.addComponent(label, 1);
            tabPane.addComponent(button, 1);
            this.woPaletteButtons.add(button);
            this.woPaletteLabels.add(label);
        }
        
        super.setPosition(this.getPosition().x, this.getPosition().y); 
        
    }
    
    private void npeRadioSet_Changed()
    {
         //clear old buttons
        for (WindowComponent c : npePaletteButtons) {
            tabPane.removeComponent(c,2);
        }
        npePaletteButtons.clear();
        
        //clear old labels
        for(WindowComponent c: npePaletteLabels)
        {
            tabPane.removeComponent(c, 2);
        }
        npePaletteLabels.clear();
        
        //get the correct group
        final ArrayList<NonPlayerEntity> list = npeGroupMap.get(npeRadioSetSelection); 
        
        //build new palette buttons from the group
        for (int i = 0; i < list.size(); i++) 
        {
            final Button button = new Button(new Image("blank.png"), 140 + (100 * i) % 700, (this.height - 160) - 100 * ((int) i / 7), 75, 75);
            button.setImage((Image)MapEditorScene.makeCopyOfObject(list.get(i).getImage()));
            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("clicked")) {
                        sceneObjectSelection = list.get(npePaletteButtons.indexOf(button));
                        fireAction(this, "selectionChanged");
                    }
                }
            });
            
            Label label = new Label(list.get(i).getName(),140 + (100 * i) % 700, (this.height - 180) - 100 * ((int) i / 7));
            label.getText().setColor(new Color(Color.orange));
            label.getText().setScale(.6f);
            tabPane.addComponent(label, 2);
            tabPane.addComponent(button, 2);
            this.npePaletteButtons.add(button);
            this.npePaletteLabels.add(label);
        }
        
        super.setPosition(this.getPosition().x, this.getPosition().y); 
    }

    public SceneObject getSelectedObject() {
        return MapEditorScene.makeCopyOfObject(this.sceneObjectSelection);
    }
    
    private void loadSavedItems()
    {
        //load in saved items
        File directory = new File ("");
        String path = directory.getAbsolutePath() + "\\LevelEditorFiles\\";
        String fileName;

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) 
        {
            if (listOfFiles[i].isFile()) 
            {
                fileName = listOfFiles[i].getName().toLowerCase();
                if (fileName.endsWith(".soi") || fileName.endsWith(".sow")|| fileName.endsWith(".son"))
                {
                    try(FileInputStream fileInputStream = new FileInputStream(path+fileName);
                        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);)
                    {
                        
                        //get save data from input stream
                        SceneObjectSaveData saveData = (SceneObjectSaveData)objectInputStream.readObject();

                        //build sceneobject from save data
                        SceneObject obj = SceneObjectDeserializer.buildSceneObjectFromSaveData(saveData);

                        if (fileName.endsWith(".soi") )
                        {                           
                            savedImages.add((Image)obj);
                        }
                        else if(fileName.endsWith(".sow"))
                        {

                            String workingName = fileName;
                            workingName = workingName.substring(0, workingName.length() - 4); //cut off extension

                            //split the name into two parts, the first has the name and the second has the group
                            String[] nameSplit = workingName.split("_"); 

                            woGroupMap.get(nameSplit[1]).add((WorldObjectEntity)obj);

                        }
                        else if(fileName.endsWith(".son"))
                        {
                            String workingName = fileName;
                            workingName = workingName.substring(0, workingName.length() - 4); //cut off extension

                            //split the name into two parts, the first has the name and the second has the group
                            String[] nameSplit = workingName.split("_"); 

                            npeGroupMap.get(nameSplit[1]).add((NonPlayerEntity)obj);
                        }

                    }
                    catch(Exception e){System.err.println("Load Saved Palette Item Fail");}
                
                }
                             
            }
        }
    }
    
    public void refreshSavedItems()
    {
        
        //clear old saved world objects
        Iterator iter = woGroupMap.keySet().iterator();
        while(iter.hasNext())
        {
            woGroupMap.get((String)iter.next()).clear();
        }
        
        //clear old saved npe's
        iter = npeGroupMap.keySet().iterator();
        while(iter.hasNext())
        {
            npeGroupMap.get((String)iter.next()).clear();
        }
        
        //clear saved images
        savedImages.clear();
        
        //load in the saved items again
        loadSavedItems();
        
        //rebuild buttons
        imageRadioSet_Changed();
        woRadioSet_Changed();
        npeRadioSet_Changed();
        
    }
    
    /**
     * returns the keys of all the world object groups
     * @return 
     */
    public Set<String> getWoGroups()
    {
       return woGroupMap.keySet();
    }
    
    /**
     * returns the keys of all the npe groups
     * @return 
     */
    public Set<String> getNpeGroups()
    {
        return npeGroupMap.keySet();
    }
    
    private ArrayList<String> getTexturesInFolder(URI resourceURL)
    {
        
        final ArrayList<String> returnList = new ArrayList();
        
        //get the Path
        Path resourcePath = Paths.get(resourceURL);

        //walk the file tree
        try
        {
            Files.walkFileTree(resourcePath, new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attr)
            {
                //get file name
                String fileName = file.getFileName().toString().toLowerCase();

                returnList.add(fileName);
                
                return FileVisitResult.CONTINUE;
            }

        });
        }
        catch(Exception e)
        {
            System.err.println("Error getting textures in folder");
        }

        return returnList;
    }
            
}
