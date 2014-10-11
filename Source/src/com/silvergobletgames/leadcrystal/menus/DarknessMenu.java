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
import com.silvergobletgames.sylver.graphics.DarkSource;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author mike
 */
public class DarknessMenu extends Menu {

    
    
    //list of image palette buttons and labels
    private ArrayList<Button> imagePaletteButtons = new ArrayList();
    private ArrayList<Label> imagePaletteLabels = new ArrayList<>();
    private ArrayList<Image> darknessTextures = new ArrayList();
    private TextBox intensityTextBox;
    private TextBox widthTextBox;
    private TextBox heightTextBox;

    
    
    public DarknessMenu(float x, float y) 
    {
        super("DarkSource Builder", x, y, 1000, 500);

        //Darkness
        darknessTextures.add(new Image("darknessSquare.png"));
        darknessTextures.add(new Image("darknessLeft.png"));
        darknessTextures.add(new Image("darknessRight.png"));
        darknessTextures.add(new Image("darknessTop.png"));
        darknessTextures.add(new Image("darknessBottom.png"));
        
        //add width and height textboxes
        Label l = new Label("Width", 50, 210);
        this.addComponent(l);
        widthTextBox = new TextBox("100",150, 200);
        this.addComponent(widthTextBox);
        
        l = new Label("Height", 50, 160);
        this.addComponent(l);
        heightTextBox = new TextBox("100",150, 150);
        this.addComponent(heightTextBox);
        
        //add intensity text box
        l = new Label("Intensity", 50, 110);
        this.addComponent(l);
        intensityTextBox = new TextBox(".5",150, 100);
        this.addComponent(intensityTextBox);
        
       
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
        for (int i = 0; i < darknessTextures.size(); i++) 
        {
            final Button button = new Button((Image)MapEditorScene.makeCopyOfObject(darknessTextures.get(i)), 50 + (100 * i) % 800, (this.height - 100) - 100 * ((int) i / 8), 75, 75);
            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("clicked"))
                    {
                        Image selection = darknessTextures.get(imagePaletteButtons.indexOf(button));
                        selectDarkness(selection);

                    }
                }
            });
            
            String text = darknessTextures.get(i).getTextureReference();
            if(text == null){ text = darknessTextures.get(i).getAnimationPack().getClass().getSimpleName();
            
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
    
  
    private void selectDarkness(Image darknessImage)
    {
        //build darkness
        DarkSource d = new DarkSource(darknessImage.getTextureReference());
        float dWidth = Float.parseFloat(this.widthTextBox.getText());
        float dHeight = Float.parseFloat(this.heightTextBox.getText());
        float intensity = Float.parseFloat(this.intensityTextBox.getText());
        d.setDimentions(dWidth, dHeight);
        d.setIntensity(intensity);
        
        ((MapEditorScene)owningScene).setHand(d);
        this.close();
    }
    
    
            
}
