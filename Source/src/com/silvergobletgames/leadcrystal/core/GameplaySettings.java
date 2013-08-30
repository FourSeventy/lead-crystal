package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.sylver.core.Game;
import java.io.*;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Mike
 */
public class GameplaySettings{
    
    private static GameplaySettings instance;
    
    private GameplaySettings()
    {
        
    }
    
    public static GameplaySettings getInstance()
    {
        if(instance == null)
            instance = new GameplaySettings();
        
        return instance;
    }
    
    //=====================
    // Debug Variables
    //=====================
    public boolean bodyWireframe = false;
    public boolean viewportFeelers = false;
    public boolean drawPlayerServerTime = false;
    public boolean networkDebugging = false;
    public boolean packetSizeDebugging  = false;
    public boolean drawNetworkingStats = true;
    public boolean debugEnemies = false;
    
    
    //===================
    // Gameplay Settings
    //=================== 
    public boolean showHealthBars = false;
    public boolean showSpawnLocations = false;
    public boolean showCooldownTimers = false;
    
    
    public void loadSettingsFromFile()
    {
              
        Document dom = this.getDOM();
        
        if (dom == null)
        {
            this.dumpSettingsToFile();
            dom = this.getDOM();
        }
        
        //get the root element
        Element docEle = dom.getDocumentElement();

        parseGameplayFields(docEle);
        parseDebugFields(docEle);
    
    }
    
    private Document getDOM()
    {
        File directory = new File ("");
        String path = directory.getAbsolutePath() + "\\System\\";
        
        try
        { 
            //Get the factory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder(); 
            //parse using builder to get DOM representation of the XML file
            
            File file = new File(path + "gameplaySettings.xml");
            return db.parse(file);
        }
        catch(ParserConfigurationException | SAXException | IOException e){
            return null;
        }
    }
    
    
    private void parseGameplayFields(Element docEle){
        //get a nodelist of elements
        NodeList nl = docEle.getElementsByTagName("gameplay");
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0 ; i < nl.getLength();i++) {

                Element gameplayElement = (Element)nl.item(i);
                
                //declare the nodelist that will hold each element
                NodeList n2;
                
                n2 = gameplayElement.getElementsByTagName("drawNetworkingStats");
                if(n2 != null && n2.getLength() > 0) 
                    this.drawNetworkingStats = getBooleanValue((Element)n2.item(0));
                
                n2 = gameplayElement.getElementsByTagName("showCooldownTimers");
                if(n2 != null && n2.getLength() > 0) 
                    this.showCooldownTimers = getBooleanValue((Element)n2.item(0));
            }
        }
    }
    
    private void parseDebugFields(Element docEle){
        //get a nodelist of elements
        NodeList nl = docEle.getElementsByTagName("debug");
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0 ; i < nl.getLength();i++) {

                Element debugElement = (Element)nl.item(i);
                
                //declare the nodelist that will hold each element
                NodeList n2;
                
                n2 = debugElement.getElementsByTagName("bodyWireframe");
                if(n2 != null && n2.getLength() > 0) 
                    this.bodyWireframe = getBooleanValue((Element)n2.item(0));
		
                n2 = debugElement.getElementsByTagName("viewportFeelers");
                if(n2 != null && n2.getLength() > 0) 
                    this.viewportFeelers = getBooleanValue((Element)n2.item(0));
                
                n2 = debugElement.getElementsByTagName("drawPlayerServerTime");
                if(n2 != null && n2.getLength() > 0) 
                    this.drawPlayerServerTime = getBooleanValue((Element)n2.item(0));
                
                n2 = debugElement.getElementsByTagName("networkDebugging");
                if(n2 != null && n2.getLength() > 0) 
                    this.networkDebugging = getBooleanValue((Element)n2.item(0));
                
                n2 = debugElement.getElementsByTagName("packetSizeDebugging");
                if(n2 != null && n2.getLength() > 0) 
                    this.packetSizeDebugging = getBooleanValue((Element)n2.item(0));
                
                n2 = debugElement.getElementsByTagName("debugEnemies");
                if(n2 != null && n2.getLength() > 0) 
                    this.debugEnemies = getBooleanValue((Element)n2.item(0));
            }
        }
    }
    
    public void dumpSettingsToFile() 
    {
        try
        {
            DocumentBuilderFactory dbfac =DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            //==================
            //Creating XML tree
            //==================
            
            Element usersettings = doc.createElement("gamesettings");
            doc.appendChild(usersettings);
            
            //Element used for each value, re-used.
            Element elem;
            
             //======
            //DEBUG
            //======
            Element debug = doc.createElement("debug");
            
                elem = doc.createElement("bodyWireframe");
                elem.setAttribute("value", Boolean.toString(this.bodyWireframe));
                debug.appendChild(elem);

                elem = doc.createElement("viewportFeelers");
                elem.setAttribute("value", Boolean.toString(this.viewportFeelers));
                debug.appendChild(elem);

                elem = doc.createElement("drawPlayerServerTime");
                elem.setAttribute("value", Boolean.toString(this.drawPlayerServerTime));
                debug.appendChild(elem);

                elem = doc.createElement("networkDebugging");
                elem.setAttribute("value", Boolean.toString(this.networkDebugging));
                debug.appendChild(elem);

                elem = doc.createElement("packetSizeDebugging");
                elem.setAttribute("value", Boolean.toString(this.packetSizeDebugging));
                debug.appendChild(elem);
                
                elem = doc.createElement("debugEnemies");
                elem.setAttribute("value", Boolean.toString(this.debugEnemies));
                debug.appendChild(elem);
                
                //=========
                //GAMEPLAY
                //=========
                Element gameplay = doc.createElement("gameplay");


                elem = doc.createElement("drawNetworkingStats");
                elem.setAttribute("value", Boolean.toString(this.drawNetworkingStats));
                gameplay.appendChild(elem);
                
                elem = doc.createElement("showCooldownTimers");
                elem.setAttribute("value", Boolean.toString(this.showCooldownTimers));
                gameplay.appendChild(elem);
                
                usersettings.appendChild(gameplay);
                usersettings.appendChild(debug);
                
                //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty(OutputKeys.METHOD, "xml");
         

            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            String xmlString = sw.toString();

            File directory = new File ("");
             String path = directory.getAbsolutePath() + "\\System\\";

            //write that raw data to the disk
            try (FileOutputStream f = new FileOutputStream(path + "gameplaySettings.xml"); 
                 OutputStreamWriter writer = new OutputStreamWriter(f);)
            {           
                writer.write(xmlString);
                writer.close();                
            }     
        }
        catch(ParserConfigurationException | DOMException | TransformerFactoryConfigurationError | IllegalArgumentException | TransformerException | IOException e)
        {
            
            System.out.println(e);
        }
    }
    
    private static boolean getBooleanValue(Element ele)
    {
        return Boolean.parseBoolean(ele.getAttribute("value"));
    }
    
    
}
