
package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.scripting.PageCondition;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import com.silvergobletgames.leadcrystal.scripting.ScriptObject;
import com.silvergobletgames.leadcrystal.scripting.ScriptObject.ScriptTrigger;
import com.silvergobletgames.leadcrystal.scripting.ScriptPage;

/**
 *
 * @author mike
 */
public class ScriptWindow extends Menu {
    
    //tab pane
    TabPane tabPane;
    
    //text areas
    private ArrayList<TextArea> textAreas = new ArrayList<>();
    
    //conditions
    private ArrayList<TextBox> pageConditions = new ArrayList<>();
    
    //trigger
    private DropDown<ScriptTrigger> trigger;
    
    //delay
    private TextBox scriptDelay;
    
    //=============
    // Constructor
    //=============
    
    public ScriptWindow(float x, float y )
    {
        super("Script Editor", x, y, 900, 800);
                
        //build tab pane
        tabPane= new TabPane(10,10,880,680);
        //Need to add one tab page
        this.addPage();
        this.addComponent(tabPane);
        
        //build trigger condition drop down
        this.addComponent(new Label("Script Trigger:",10,740));
        trigger = new DropDown<>(200,730);
        
        //Add
        Button btnAdd = new Button(new Image("addButton.png"), 350, 740, 32, 32);
        btnAdd.setTextPadding(20, 7);
        btnAdd.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) {
                    addButton_clicked();
                }
            }
        });
        this.addComponent(btnAdd);
        Text txtAdd = new Text("Add");
        txtAdd.setScale(.75f);
        this.addComponent(new Label(txtAdd, 348, 728));
        
        //Delete
        Button btnDelete = new Button(new Image("deleteButton.png"), 400, 740, 32, 32);
        btnDelete.setTextPadding(20, 7);
        btnDelete.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clicked")) {
                    deleteButton_clicked();
                }
            }
        });
        this.addComponent(btnDelete);
        Text txtDel = new Text("Delete");
        txtDel.setScale(.75f);
        this.addComponent(new Label(txtDel, 391, 728));
        
        for(ScriptTrigger value: ScriptTrigger.values())
        {
            trigger.addElement(new SimpleEntry(value.toString(),value)); 
        }
        trigger.setDefaultSelection("NONE");
        this.addComponent(trigger);      
        
        //delay
        Label l = new Label("Delay:", 475, 740);
        this.addComponent(l);
        this.scriptDelay = new TextBox(570, 735);
        this.scriptDelay.setText("0");
        this.addComponent(scriptDelay);
    }
    
    
    //================
    // Class Methods
    //================
    
    /**
     * Adds a Script Page
     */
    private void addPage()
    {
        //Get the index of the next tab ("size" variable of current tabPane state)
        int index = tabPane.size();
        
        //Adds the next tab page
        tabPane.addTab("Page " + tabPane.size());
        
        //add text area
        TextArea ta = new TextArea(10,10,860,580);
        tabPane.addComponent(ta,index);
        textAreas.add(ta);

        //add page conditions
        tabPane.addComponent(new Label("Page Conditions:",10,630), index); 
        TextBox condition = new TextBox(175, 620);
        condition.setDimensions(600, 30);
        tabPane.addComponent(condition, index);

        pageConditions.add(condition);
    }
    
    /**
     * Deletes the current page
     */
    private void deletePage()
    {
        //Get the index of the page to delete
        int index = tabPane.getSelectedTab();
        
        //Can't have 0 pages
        if (tabPane.size() > 1){
            pageConditions.remove(index);
            textAreas.remove(index);
            tabPane.removeTab(index);            
        }
        
        //Reresh page names
        tabPane.refreshTabNames("Page");
    }
    
    /**
     * Clears the script window of all components and settings.
     */
    public void clear()
    {
        clearComponents();
        
        //Add a blank page
        addPage();
        
        //reset the trigger
        trigger.setDefaultSelection("NONE");
    }
    
    /**
     * Clear the window components associated with scripts
     */
    private void clearComponents()
    {
        textAreas.clear();
        pageConditions.clear();
        tabPane.clear();
    }
    
    /**
     * Populates the window with the contents of the given script object
     * @param scriptObject object to populate the window with
     */
    public void populateFromScriptObject(ScriptObject scriptObject)
    {
        //clear everything first
        this.clearComponents();
        
        //set the trigger
        trigger.setDefaultSelection(scriptObject.getTrigger().toString());
        
        //set delay
        this.scriptDelay.setText(Integer.toString(scriptObject.getDelay()));
        
        //populate the pages and conditions
        ArrayList<ScriptPage> pages = new ArrayList();
        ArrayList<PageCondition> conditions = new ArrayList();
        for(SimpleEntry<ScriptPage,PageCondition> entry: scriptObject.getPages())
        {
            pages.add(entry.getKey());
            conditions.add(entry.getValue());
        }
        
        if (pages.size() > 0)
        {
            //Add each page
            for(int i = 0; i< pages.size(); i ++)
            { 
                //Add a tabpage
                addPage();

                //Set the text of the newly created textArea
                this.textAreas.get(i).setText( pages.get(i).getScript());  

                //populate conditions
                TextBox scriptBox = this.pageConditions.get(i);
                scriptBox.setText(conditions.get(i).getConditionScript());
            }
        }
        
        //Switch to the 0th tab
        tabPane.switchToTab(0);
        
        this.update();
    }
    
    /**
     * Returns a script object built from the populated fields
     * @return Script object built from the populated fields
     */
    public ScriptObject getScriptObject()
    {
        ScriptObject returnScript = new ScriptObject();
        
        //set the trigger
        returnScript.setTrigger(trigger.getSelectedElement());
        
        //set the delay
        returnScript.setDelay(Integer.parseInt(this.scriptDelay.getText()));
        
        //build pages
        for(int i = 0; i < this.textAreas.size(); i++)
        {
             //make page
             ScriptPage page = new ScriptPage();
             page.setScript(textAreas.get(i).getText());

             //make condition
             TextBox conditionBox = this.pageConditions.get(i);
             PageCondition condition = new PageCondition();
             condition.setConditionScript(conditionBox.getText());
             
             //add page to script
             returnScript.addPage(page,condition); 
             
        }
        
        return returnScript;
    }
    
    /**
     * Adds a script page
     */
    public void addButton_clicked(){
        addPage();
    }   
    
    /**
     * Adds a script page
     */
    public void deleteButton_clicked(){
        deletePage();
    }  
}
