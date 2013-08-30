
package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.core.GameplaySettings;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.InputHandler;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.OpenGLGameWindow;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.Button;
import com.silvergobletgames.sylver.windowsystem.Window;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.silvergobletgames.leadcrystal.scenes.MapEditorScene;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.graphics.*;
import java.util.ArrayList;


/**
 *
 * @author mike
 */
public class FileMenu extends Window{
    
    //windows
    private Window fileWindow;
    private Window editWindow;
    private Window windowWindow;
    private Window renderWindow;
    private Window placeSpecialWindow;
    
    //current open window
    private Window openWindow;
    
    private boolean justOpened= false;
    
    public FileMenu()
    {
        super(new Image("menuBlank.png"),0,870,1600,30);
        
        //add buttons
        Text t = new Text("File");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        Button b =new Button(t,10,0);
        b.setTextPadding(0, 5);
        b.addActionListener(new ActionListener(){
                
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {
                   if(openWindow != fileWindow)
                   {
                       //remove old open window
                       if(openWindow != null)
                           owningScene.remove(openWindow);
                       
                       //add new window
                       owningScene.add(fileWindow,Layer.MENU);
                       openWindow = fileWindow;
                       justOpened = true;
                   }
                }
                
            }
        
        });
        this.addComponent(b);
        
        
        t = new Text("Edit");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        b = new Button(t,70,0);
        b.setTextPadding(0, 5);
        b.addActionListener(new ActionListener(){
                
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {         
                   if(openWindow != editWindow)
                   {
                       //remove old open window
                       if(openWindow != null)
                           owningScene.remove(openWindow);
                       
                       owningScene.add(editWindow,Layer.MENU);
                       openWindow = editWindow;
                       justOpened = true;
                   }
                }
            }       
        });
        this.addComponent(b);
        
        t = new Text("Window");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        b = new Button(t,130,0);
        b.setTextPadding(0, 5);
        b.addActionListener(new ActionListener(){
                
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {         
                   if(openWindow != windowWindow)
                   {
                       //remove old open window
                       if(openWindow != null)
                           owningScene.remove(openWindow);
                       
                       owningScene.add(windowWindow,Layer.MENU);
                       openWindow = windowWindow;
                       justOpened = true;
                   }
                }
            }       
        });
        this.addComponent(b);
        
        t = new Text("Rendering");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        b = new Button(t,230,0);
        b.setTextPadding(0, 5);
        b.addActionListener(new ActionListener(){
                
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {         
                   if(openWindow != renderWindow)
                   {
                       //remove old open window
                       if(openWindow != null)
                           owningScene.remove(openWindow);
                       
                       owningScene.add(renderWindow,Layer.MENU);
                       openWindow = renderWindow;
                       justOpened = true;
                   }
                }
            }       
        });
        this.addComponent(b);
        
        t = new Text("Place Special");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        b = new Button(t,350,0);
        b.setTextPadding(0, 5);
        b.addActionListener(new ActionListener(){
                
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {         
                   if(openWindow != placeSpecialWindow)
                   {
                       //remove old open window
                       if(openWindow != null)
                           owningScene.remove(openWindow);
                       
                       owningScene.add(placeSpecialWindow,Layer.MENU);
                       openWindow = placeSpecialWindow;
                       justOpened = true;
                   }
                }
            }       
        });
        this.addComponent(b);
        
        
        
        //===============
        // Build Windows
        //===============
        
        //file window
        fileWindow = new Window(new Image("menuBlank.png"),10,740,150,130);
        
        t = new Text("New Level");
        t.setColor(new Color(Color.white));
        b = new Button(t,5,100);    
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) 
            {
                if(e.getActionCommand().equals("clicked"))
                {                  
                   ((MapEditorScene)owningScene).newLevel();
                    owningScene.remove(fileWindow);
                    openWindow = null;
                    Game.getInstance().getInputHandler().getInputSnapshot().killMouseClick();
                   
                }
            }           
        }); 
        fileWindow.addComponent(b);
        
        t = new Text("Open Level");
        t.setColor(new Color(Color.white));
        b = new Button(t,5,70);
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) 
            {
                if(e.getActionCommand().equals("clicked"))
                {                  
                   ((MapEditorScene)owningScene).loadLevelMenu.open();
                    owningScene.remove(fileWindow);
                    openWindow = null;
                    Game.getInstance().getInputHandler().getInputSnapshot().killMouseClick();
                   
                }
            }           
        });  
        fileWindow.addComponent(b);
        
        t = new Text("Save       ctl + s");
        t.setColor(new Color(Color.white));
        b = new Button(t,5,40);
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) 
            {
                if(e.getActionCommand().equals("clicked"))
                {                  
                   ((MapEditorScene)owningScene).saveLevel(((MapEditorScene)owningScene).loadedLevelData.filename);
                    owningScene.remove(fileWindow);
                    openWindow = null;
                    Game.getInstance().getInputHandler().getInputSnapshot().killMouseClick();
                   
                }
            }           
        });  
        fileWindow.addComponent(b);
        
        
        t = new Text("Save As");
        t.setColor(new Color(Color.white));
        b = new Button(t,5,10);
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) 
            {
                if(e.getActionCommand().equals("clicked"))
                {                  
                   ((MapEditorScene)owningScene).saveLevelMenu.open();
                    owningScene.remove(fileWindow);
                    openWindow = null;
                    Game.getInstance().getInputHandler().getInputSnapshot().killMouseClick();
                   
                }
            }           
        });  
        fileWindow.addComponent(b);
        
       
        
             
        //edit window
        editWindow = new Window(new Image("menuBlank.png"),60,740,100,130);
        
        t = new Text("Clear Background");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
         b = new Button(t,5,100);
         b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) 
            {
                if(e.getActionCommand().equals("clicked"))
                {                  
                    //remove background stuff
                    ArrayList<SceneObject> temp = new ArrayList(owningScene.getSceneObjectManager().get(Layer.BACKGROUND));
                    for(SceneObject sceneObject: temp )
                        owningScene.remove(sceneObject);
                    
                    owningScene.remove(editWindow);
                    openWindow = null;
                    Game.getInstance().getInputHandler().getInputSnapshot().killMouseClick();
                   
                }
            }           
        });
        editWindow.addComponent(b);
        
        t = new Text("Clear Darkness");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        b = new Button(t,5,70);
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) 
            {
                if(e.getActionCommand().equals("clicked"))
                {                  
                    //remove background stuff
                    for(Layer layer :Layer.values())
                    {
                        ArrayList<SceneObject> temp = new ArrayList(owningScene.getSceneObjectManager().get(layer));
                        for(SceneObject sceneObject: temp )
                        {
                            if(sceneObject instanceof DarkSource)
                                owningScene.remove(sceneObject);
                        }
                    }
                    
                    owningScene.remove(editWindow);
                    openWindow = null;
                    Game.getInstance().getInputHandler().getInputSnapshot().killMouseClick();
                   
                }
            }           
        });
        editWindow.addComponent(b);
       
        
        
        //window window
        windowWindow = new Window(new Image("menuBlank.png"),110,710,175,160);
        
        t = new Text("Grid Settings");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        b = new Button(t,5,130);
        b.addActionListener(new ActionListener(){            
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {         
                  ((MapEditorScene)owningScene).gridMenu.open();
                   owningScene.remove(windowWindow);
                   openWindow = null;
                }
            }
        });
        windowWindow.addComponent(b);
        
        t = new Text("Level Properties");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        b = new Button(t,5,100);
        b.addActionListener(new ActionListener(){            
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {         
                  ((MapEditorScene)owningScene).levelProperties.open();
                   owningScene.remove(windowWindow);
                   openWindow = null;
                   Game.getInstance().getInputHandler().getInputSnapshot().killMouseClick();
                }
            }
        });
        windowWindow.addComponent(b);
        
        t = new Text("Layer Selector");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        b = new Button(t,5,70);
        b.addActionListener(new ActionListener(){            
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {         
                  ((MapEditorScene)owningScene).layerMenu.open();
                   owningScene.remove(windowWindow);
                   openWindow = null;
                   Game.getInstance().getInputHandler().getInputSnapshot().killMouseClick();
                }
            }
        });
        windowWindow.addComponent(b);
        
        t = new Text("Item Properties");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        b = new Button(t,5,40);
        b.addActionListener(new ActionListener(){            
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {         
                  ((MapEditorScene)owningScene).propertiesMenu.open();
                   owningScene.remove(windowWindow);
                   openWindow = null;
                   Game.getInstance().getInputHandler().getInputSnapshot().killMouseClick();
                }
            }
        });
        windowWindow.addComponent(b);
        
        t = new Text("Tile Palette");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        b = new Button(t,5,10);
        b.addActionListener(new ActionListener(){            
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {         
                  ((MapEditorScene)owningScene).paletteMenu.open();
                   owningScene.remove(windowWindow);
                   openWindow = null;
                   Game.getInstance().getInputHandler().getInputSnapshot().killMouseClick();
                }
            }
        });
        windowWindow.addComponent(b);
        
        
        //rendering
        renderWindow = new Window(new Image("menuBlank.png"),200,710,170,160);
        t = new Text("Body Wireframe");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        b = new Button(t,5,130);
        b.addActionListener(new ActionListener(){            
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {         
                  GameplaySettings.getInstance().bodyWireframe = !GameplaySettings.getInstance().bodyWireframe;
                  
                   owningScene.remove(renderWindow);
                   openWindow = null;
                }
            }
        });
        renderWindow.addComponent(b);
        
        t = new Text("Viewport Feelers");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        b = new Button(t,5,100);
        b.addActionListener(new ActionListener(){            
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {         
                  GameplaySettings.getInstance().viewportFeelers = !GameplaySettings.getInstance().viewportFeelers;
                  
                   owningScene.remove(renderWindow);
                   openWindow = null;
                }
            }
        });
        renderWindow.addComponent(b);
        
        t = new Text("Reset Zoom");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        b = new Button(t,5,70);
        b.addActionListener(new ActionListener(){            
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {         
                  ((MapEditorScene)owningScene).zoom = 0;
                  
                   owningScene.remove(renderWindow);
                   openWindow = null;
                }
            }
        });
        renderWindow.addComponent(b);
        
        
        
        //place special
        placeSpecialWindow = new Window(new Image("menuBlank.png"),300,710,170,160);
        
        t = new Text("Background");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        b = new Button(t,5,130);
        b.addActionListener(new ActionListener(){            
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {         
                   //open background menu
                    ((MapEditorScene)owningScene).backgroundMenu.open();
                   owningScene.remove(placeSpecialWindow);
                   openWindow = null;
                }
            }
        });
        placeSpecialWindow.addComponent(b);
        
        t = new Text("Dark Source");
        t.setColor(new Color(Color.white));
        t.setScale(.9f);
        b = new Button(t,5,100);
        b.addActionListener(new ActionListener(){            
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("clicked"))
                {         
                   //open background menu
                    ((MapEditorScene)owningScene).darknessMenu.open();
                   owningScene.remove(placeSpecialWindow);
                   openWindow = null;
                }
            }
        });
        placeSpecialWindow.addComponent(b);
        
        
        
   
        
        
    }
    
    
    public void update()
    {
        super.update();
        
        if(owningScene != null && Game.getInstance().getInputHandler().getInputSnapshot() != null)
        {
            Point mouseLocation = Game.getInstance().getInputHandler().getInputSnapshot().getScreenMouseLocation(); 
            if(Game.getInstance().getInputHandler().getInputSnapshot().isMouseDown())
            {           
                if(justOpened != true)
                {
                    if( openWindow != null &&  !(mouseLocation.x >= openWindow.getPosition().x && mouseLocation.x <= openWindow.getPosition().y + openWindow.getWidth() && mouseLocation.y >= openWindow.getPosition().y && mouseLocation.y <= openWindow.getPosition().y + openWindow.getHeight()))
                    {
                        owningScene.remove(openWindow);
                        openWindow = null;
                    }
                }
                else
                    justOpened = false;
            }
        }
    }

}
