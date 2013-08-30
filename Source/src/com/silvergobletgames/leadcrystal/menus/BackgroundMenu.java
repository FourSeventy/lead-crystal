package com.silvergobletgames.leadcrystal.menus;


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
import com.silvergobletgames.sylver.core.Scene;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author mike
 */
public class BackgroundMenu extends Menu {

    
    
    //list of image palette buttons and labels
    private ArrayList<Button> imagePaletteButtons = new ArrayList();
    private ArrayList<Label> imagePaletteLabels = new ArrayList<>();
    private ArrayList<Image> backgrounds = new ArrayList();

    
    
    public BackgroundMenu(float x, float y) 
    {
        super("Background Selection", x, y, 1000, 500);

        //backgrounds
        try
        {
            ArrayList<String> backgroundStrings = this.getTexturesInFolder(Game.getInstance().getConfiguration().getTextureRootFolder().resolve("terrain/backgrounds"));
            for(String string: backgroundStrings)
            {
                backgrounds.add(new Image(string));
            }
        }
        catch(Exception e){ System.err.println("Error getting Backgrounds folder");}
        
       
        this.refresh();
    }

    private void refresh()
    {
        //clear old buttons 
        for(Button button: imagePaletteButtons)
            this.removeComponent(button);
        
        for(Label label: imagePaletteLabels)
            this.removeComponent(label);
        
        imagePaletteButtons.clear();
        imagePaletteLabels.clear();
        
        //build palette buttons
        for (int i = 0; i < backgrounds.size(); i++) 
        {
            final Button button = new Button((Image)MapEditorScene.makeCopyOfObject(backgrounds.get(i)), 50 + (100 * i) % 800, (this.height - 100) - 100 * ((int) i / 8), 75, 75);
            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("clicked"))
                    {
                        Image selection = backgrounds.get(imagePaletteButtons.indexOf(button));
                        setBackground(selection);

                    }
                }
            });
            
            String text = backgrounds.get(i).getTextureReference();
            if(text == null){ text = backgrounds.get(i).getAnimationPack().getClass().getSimpleName();
            
            }
            Label label = new Label(text,50 + (100 * i) % 800, (this.height - 120) - 100 * ((int) i / 8));
            label.getText().setColor(new Color(Color.orange));
            label.getText().setScale(.55f);
            this.addComponent(label);
            this.addComponent(button);
            this.imagePaletteButtons.add(button);
            this.imagePaletteLabels.add(label);
        }
        
        super.setPosition(this.getPosition().x, this.getPosition().y); 


    }   
    
  
    private void setBackground(Image background)
    {
        background.setDimensions(1600, 900);
        background.setPosition(0, 0);
        this.owningScene.add(background, Scene.Layer.BACKGROUND);
        this.close();
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
