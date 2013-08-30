
package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.CheckBox;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.Menu;
import com.silvergobletgames.sylver.windowsystem.RadioSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap;
import java.util.HashMap;
import com.silvergobletgames.leadcrystal.scenes.MapEditorScene;

/**
 *
 * @author mike
 */
public class LayerWindow extends Menu 
{

    //Layer properties
    public RadioSet layerChoices;
    public HashMap<Integer, String> layerNames;
    public CheckBox selectionOption;
    public CheckBox viewOption;
    public CheckBox lockOption;
 
    public MapEditorScene owningScene;
    
    public LayerWindow(float x, float y, MapEditorScene owner) {
        super("Layer Selection:", x, y, 170, 440);

        this.owningScene = owner;
        
        layerChoices = new RadioSet<>(10, 120);
        layerChoices.addElement(new AbstractMap.SimpleEntry("Foreground2", 13));
        layerChoices.addElement(new AbstractMap.SimpleEntry("Foreground1", 12));
        layerChoices.addElement(new AbstractMap.SimpleEntry("World Hud", 9));
        layerChoices.addElement(new AbstractMap.SimpleEntry("Attached FG", 8));
        layerChoices.addElement(new AbstractMap.SimpleEntry("Main", 7));
        layerChoices.addElement(new AbstractMap.SimpleEntry("Attached BG", 6));
        layerChoices.addElement(new AbstractMap.SimpleEntry("Parallax1", 1));
        layerChoices.addElement(new AbstractMap.SimpleEntry("Parallax2", 2));
        layerChoices.addElement(new AbstractMap.SimpleEntry("Parallax3", 3));
        layerChoices.addElement(new AbstractMap.SimpleEntry("Parallax4", 4));
        layerChoices.addElement(new AbstractMap.SimpleEntry("Parallax5", 5));
        layerChoices.addElement(new AbstractMap.SimpleEntry("BackGround", 0));
        this.addComponent(layerChoices);
        layerChoices.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("selectionChanged")) {
                    owningScene.selectedLayer = (Integer)layerChoices.getSelectedValue();
                }
            }
        });
        layerChoices.setDefaultSelection("Main");

        //Selection option
        Text t = new Text("Select by layer:");
        t.setScale(.7f);
        this.addComponent(new Label(t, 10, 80));
        
        selectionOption = new CheckBox(142,75, false);
        this.addComponent(selectionOption);
        
        //Viewing option
        t = new Text("View objs by layer:");
        t.setScale(.7f);
        this.addComponent(new Label(t, 10, 50));
        
        viewOption = new CheckBox(142,45, false);
        this.addComponent(viewOption);
        
        //Layer lock option
        t = new Text("Lock layer selection:");
        t.setScale(.7f);
        this.addComponent(new Label(t, 10, 20));
        
        lockOption = new CheckBox(142,15, false);
        this.addComponent(lockOption);
        
        //Layer name map
        layerNames = new HashMap();
        layerNames.put(9, "World Hud");
        layerNames.put(8, "Attached FG");
        layerNames.put(7, "Main");
        layerNames.put(6, "Attached BG");
        layerNames.put(1, "Parallax1");
        layerNames.put(2, "Parallax2");
        layerNames.put(3, "Parallax3");
        layerNames.put(4, "Parallax4");
        layerNames.put(5, "Parallax5");
        layerNames.put(0, "BackGround");
        layerNames.put(12, "Foreground1");
        layerNames.put(13, "Foreground2");
    }

    public void setSelection(int i){
        this.layerChoices.selectElement(this.layerNames.get(i));
    }
    
}
