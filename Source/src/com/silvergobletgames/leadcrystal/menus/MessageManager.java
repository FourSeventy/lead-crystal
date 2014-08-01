package com.silvergobletgames.leadcrystal.menus;

import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.graphics.MultiImageEffect;
import com.silvergobletgames.sylver.graphics.MultiTextEffect;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.TextEffect;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map.Entry;

/**
 *
 * @author Mike
 */
public class MessageManager 
{
    
    private LinkedList<SimpleEntry<MessageType,ArrayList<String>>> messageQueue = new LinkedList<>();
    private Hud hudReference;
    private long messageTimer = 0;
    private boolean readyToLaunchMessage = true;
    
    public static enum MessageType{
        OBJECTIVE_STATUS,OBJECTIVE_COMPLETE,MODIFIER_UNLOCKED;
    }
    
    //================
    // Constructor
    //================
    public MessageManager(Hud hud)
    {
        this.hudReference = hud;
    }
    
    
    //=============
    // Methods
    //=============
    
    public void queueMessage(MessageType type, ArrayList<String> messages)
    {     
        //queue up the message
        SimpleEntry<MessageType,ArrayList<String>> messageEntry = new SimpleEntry<>(type,messages);
        messageQueue.add(messageEntry);
    }
    
    
    public void update()
    {
        //update timer
        this.messageTimer++;
        
        //every 7 seconds put next message into scene
        if(this.messageTimer % (60 *7) == 0) 
        {
            this.readyToLaunchMessage = true;                  
        }
        
        //if we are ready to launch the message and there is a message in the queue
        if(this.readyToLaunchMessage && !this.messageQueue.isEmpty())
        {
            //put message into scene
            SimpleEntry<MessageType,ArrayList<String>> message = this.messageQueue.poll();               
            switch(message.getKey())
            {
                case OBJECTIVE_STATUS: putObjectiveStatusMessage(message); break;
                case OBJECTIVE_COMPLETE: putObjectiveCompleteMessage(message);break;
                case MODIFIER_UNLOCKED: putModifierUnlockedMessage(message);break;
            }      
            
            //not ready anymore
            this.readyToLaunchMessage = false;
            this.messageTimer = 0;
        }  
    }
    
    private void putObjectiveCompleteMessage(SimpleEntry<MessageType,ArrayList<String>> message)
    {    
        ArrayList<String> messageText = message.getValue();
        String currencyReward = messageText.get(0);
        String modifierString = null;
        if(messageText.size() > 1)
        {
            modifierString = messageText.get(1);
        }
        
        //complete text
        Text completeText = new Text("Main Objective Complete!", LeadCrystalTextType.MESSAGE);
        completeText.setColor(new Color(Color.green));
        completeText.setScale(1.3f);
        float center = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x/2;
        completeText.setPosition(center- completeText.getWidth()/2, 650);
        completeText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 240, 0, 0));
        
        //currency text
        String currencyString = "+" + currencyReward;
        Text currencyText = new Text(currencyString,LeadCrystalTextType.MESSAGE);
        currencyText.setScale(1f);
        currencyText.setPosition(center- currencyText.getWidth()/2 - 20, 600);
        currencyText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 240, 0, 0));
             
        //currency image
        Image currencyImage = new Image("goldCoin.png");
        currencyImage.setScale(1f);
        currencyImage.setPosition(center- currencyText.getWidth()/2 + 75, 600);
        currencyImage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 240, 0, 0));
        
        //skill text
        Text skillText = new Text("+1 Skill Point",LeadCrystalTextType.MESSAGE);
        skillText.setScale(1f);
        skillText.setPosition(center- skillText.getWidth()/2 - 20, 540);
        skillText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 240, 0, 0));
        
        //modifier text
        Text modifierText = null;
        if(modifierString != null)
        {
            //modifier text
            modifierText = new Text(modifierString,LeadCrystalTextType.MESSAGE);
            modifierText.setScale(.5f);
            modifierText.setPosition(center- modifierText.getWidth()/2 - 20, 550);
            modifierText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 240, 0, 0));
        }
        
        
        //add fade effects
        TextEffect fade = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(Color.green), new Color(Color.green,0));
        fade.setDelay(210);
        completeText.addTextEffect(fade);
        fade = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(Color.white), new Color(Color.white,0));
        fade.setDelay(210);
        currencyText.addTextEffect(fade);
        fade = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(Color.white), new Color(Color.white,0));
        fade.setDelay(210);
        skillText.addTextEffect(fade);
        
        //add scale effects
        Float[] points ={1.3f,1.5f,1.3f};
        int[] durations = {45,45};
        completeText.addTextEffect(new MultiTextEffect(TextEffect.TextEffectType.SCALE, points, durations));
        Float[] points3 ={1f,1.2f,1f};
        int[] durations3 = {45,45};
        currencyText.addTextEffect(new MultiTextEffect(TextEffect.TextEffectType.SCALE, points3, durations3));
        skillText.addTextEffect(new MultiTextEffect(TextEffect.TextEffectType.SCALE, points3, durations3));
        
        Float[] points2 ={1f,1.2f,1f};
        int[] durations2 = {45,45};
        currencyImage.addImageEffect(new MultiImageEffect(ImageEffect.ImageEffectType.SCALE,points2,durations2));
       
        //add text to scene
        this.hudReference.getOwningScene().add(completeText, Scene.Layer.HUD);
        
        if(!currencyReward.equals("0"))
        {
            this.hudReference.getOwningScene().add(currencyText, Scene.Layer.HUD);
            this.hudReference.getOwningScene().add(currencyImage, Scene.Layer.HUD);
            this.hudReference.getOwningScene().add(skillText, Scene.Layer.HUD);
        }
    }
    
    private void putObjectiveStatusMessage(SimpleEntry<MessageType,ArrayList<String>> message)
    {
        
    }
    
    private void putModifierUnlockedMessage(SimpleEntry<MessageType,ArrayList<String>> message)
    {
        
    }
    
}
