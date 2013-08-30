
package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Label;
import com.silvergobletgames.sylver.windowsystem.Menu;
import com.silvergobletgames.sylver.windowsystem.TextBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 *
 * @author mike
 */
public class LevelPropertiesWindow extends Menu 
{

    //level properties
    private String backgroundMusic = "";
    private Float r = 1f;
    private Float g = 1f;
    private Float b = 1f;
    
    //list of text boxes
    private ArrayList<TextBox> textBoxes = new ArrayList<>();
 
    public LevelPropertiesWindow(float x, float y) {
        super("Level Properties", x, y, 275, 500);

 
        this.addComponent(new Label("BGM String:", 20, 400));
        TextBox t = new TextBox(backgroundMusic, 150, 395);
        this.addComponent(t); 
        this.textBoxes.add(t); //0
               
        //Light        
        this.addComponent(new Label("Ambient Light (0-1 scale):", 20,350));
        
        this.addComponent(new Label("R:", 30,310));
        t = new TextBox(r.toString(), 50, 310);
        this.addComponent(t); 
        this.textBoxes.add(t); //1
        
        this.addComponent(new Label("G:", 30,270));
        t = new TextBox(g.toString(), 50, 270);
        this.addComponent(t); 
        this.textBoxes.add(t); //2
        
        this.addComponent(new Label("B:", 30,230));
        t = new TextBox(b.toString(), 50, 230);
        this.addComponent(t); 
        this.textBoxes.add(t); //3
        
        Text text = new Text("OK");
        text.setColor(new Color(Color.gray));
        Button b = new Button("buttonBackground.png", text, 10, 20, 75, 30);
        b.setTextPadding(10, 7);
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) {
                    saveButton_clicked();
                }
            }
        });
        this.addComponent(b);

        text = new Text("Cancel");
        text.setColor(new Color(Color.gray));
        b = new Button("buttonBackground.png", text, 110, 20, 75, 30);
        b.setTextPadding(10, 7);
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) {
                    cancelButton_clicked();
                }
            }
        });
        this.addComponent(b);

    }

    public void saveButton_clicked() 
    {
       this.backgroundMusic = this.textBoxes.get(0).getText();
       this.r = Float.parseFloat(this.textBoxes.get(1).getText());
       this.g = Float.parseFloat(this.textBoxes.get(2).getText());
       this.b = Float.parseFloat(this.textBoxes.get(3).getText());
       
       this.owningScene.getSceneEffectsManager().sceneAmbientLight = new Color(this.r,this.g,this.b,1);
       this.close();
    }
    

    public void cancelButton_clicked()
    {
        this.close();
    }
    
    public void setBackgroundMusic(String music)
    {
        this.backgroundMusic = music;
        this.textBoxes.get(0).setText(music);
    }
    
    public void setAmbientLight(Color col){
        this.r = col.r;
        this.g = col.g;
        this.b = col.b;
        
        this.textBoxes.get(1).setText(Float.toString(this.r));
        this.textBoxes.get(2).setText(Float.toString(this.g));
        this.textBoxes.get(3).setText(Float.toString(this.b));
        
        
    }
    
    
    public String getBackgroundMusic()
    {
        return this.backgroundMusic;
    }
    
    public Color getAmbientLight(){
        return new Color(r, g, b);
    }
    
}
