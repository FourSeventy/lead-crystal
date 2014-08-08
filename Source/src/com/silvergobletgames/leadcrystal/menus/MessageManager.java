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
        MAIN_OBJECTIVE_STATUS,SIDE_OBJECTIVE_STATUS,OBJECTIVE_COMPLETE,MODIFIER_UNLOCKED,OBJECTIVE_REWARD;
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
        
        //every 5 seconds put next message into scene
        if(this.messageTimer % (60 *5 ) == 0) 
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
                case MAIN_OBJECTIVE_STATUS: handleMainObjectiveStatusMessage(message); break;
                case SIDE_OBJECTIVE_STATUS: handleSideObjectiveStatusMessage(message); break;
                case OBJECTIVE_COMPLETE: handleObjectiveCompleteMessage(message);break;
                case MODIFIER_UNLOCKED: handleModifierUnlockedMessage(message);break;
                case OBJECTIVE_REWARD: handleObjectiveRewardMessage(message); break;
            }      
            
            //not ready anymore
            this.readyToLaunchMessage = false;
            this.messageTimer = 0;
        }  
    }
    
    
    
    private void handleMainObjectiveStatusMessage(SimpleEntry<MessageType,ArrayList<String>> message)
    {
        ArrayList<String> messageText = message.getValue();
        
        //objective status text
        Text completeText = new Text(messageText.get(0), LeadCrystalTextType.MESSAGE48);
        completeText.setColor(new Color(1f,1f,1f));
        completeText.setScale(.9f);
        float center = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x/2;
        completeText.setPosition(center- completeText.getWidth()/2, 700);
        completeText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 240, 0, 0));
        
        //add fade effect
        TextEffect fade = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(1f,1f,1f), new Color(1f,1f,1f,0));
        fade.setDelay(210);
        completeText.addTextEffect(fade);
        
        //add scale effects
        Float[] points ={.9f,1f,.9f};
        int[] durations = {45,45};
        completeText.addTextEffect(new MultiTextEffect(TextEffect.TextEffectType.SCALE, points, durations));
        
         //add text to scene
        this.hudReference.getOwningScene().add(completeText, Scene.Layer.HUD);
    }
    
    private void handleSideObjectiveStatusMessage(SimpleEntry<MessageType,ArrayList<String>> message)
    {
        ArrayList<String> messageText = message.getValue();
        
        //objective status text
        Text completeText = new Text(messageText.get(0), LeadCrystalTextType.MESSAGE48);
        completeText.setColor(new Color(1f,1f,1f));
        completeText.setScale(.9f);
        float center = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x/2;
        completeText.setPosition(center- completeText.getWidth()/2, 700);
        completeText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 240, 0, 0));
        
        //add fade effect
        TextEffect fade = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(1f,1f,1f), new Color(1f,1f,1f,0));
        fade.setDelay(210);
        completeText.addTextEffect(fade);
        
        //add scale effects
        Float[] points ={.9f,1f,.9f};
        int[] durations = {45,45};
        completeText.addTextEffect(new MultiTextEffect(TextEffect.TextEffectType.SCALE, points, durations));
        
         //add text to scene
        this.hudReference.getOwningScene().add(completeText, Scene.Layer.HUD);
    }
        
    
    
    private void handleObjectiveCompleteMessage(SimpleEntry<MessageType,ArrayList<String>> message)
    {    
        ArrayList<String> messageText = message.getValue();
        String completeMessage = messageText.get(0);
        
        //complete text
        Text completeText = new Text(completeMessage, LeadCrystalTextType.MESSAGE54);
        completeText.setColor(new Color(Color.green));
        completeText.setScale(1.3f);
        float center = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x/2;
        completeText.setPosition(center- completeText.getWidth()/2, 700);
        completeText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 270, 0, 0));
            
        //add fade effect
        TextEffect fade = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(Color.green), new Color(Color.green,0));
        fade.setDelay(240);
        completeText.addTextEffect(fade);
   
        //add scale effect
        Float[] points ={1.3f,1.5f,1.3f};
        int[] durations = {45,45};
        completeText.addTextEffect(new MultiTextEffect(TextEffect.TextEffectType.SCALE, points, durations));
  
        //add text to scene
        this.hudReference.getOwningScene().add(completeText, Scene.Layer.HUD);
        
    }
    

    
    private void handleModifierUnlockedMessage(SimpleEntry<MessageType,ArrayList<String>> message)
    {
        float center = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x/2;
        ArrayList<String> messageText = message.getValue();
        String modifierString = messageText.get(0);
        String modifierImageString = messageText.get(1);
                   
        //reward text
        Text rewardText = new Text("Modifier Unlocked:",LeadCrystalTextType.MESSAGE54);
        rewardText.setColor(new Color(240,194,12)); 
        rewardText.setPosition(center- rewardText.getWidth()/2, 700);
        rewardText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 270, 0, 0));
        
        //modifier text
        Text modifierText = new Text(modifierString,LeadCrystalTextType.MESSAGE42);
        modifierText.setPosition(center- modifierText.getWidth()/2, 629);
        modifierText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 270, 0, 0));
        
        
        //modifier image
        Image modifierImage = new Image(modifierImageString);
        modifierImage.setScale(.8f);
        modifierImage.setPosition(center - 40, 600);
        modifierImage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 270, 0, 0));
        
        //fade effect
        TextEffect fade = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(Color.white), new Color(Color.white,0));
        fade.setDelay(240);
        modifierText.addTextEffect(fade); 
        
        ImageEffect imageFade2 = new ImageEffect(ImageEffect.ImageEffectType.ALPHABRIGHTNESS,30, 1, 0);
        imageFade2.setDelay(250);
        modifierImage.addImageEffect(imageFade2);
        
        //add to scene
        this.hudReference.getOwningScene().add(rewardText, Scene.Layer.HUD);
       // this.hudReference.getOwningScene().add(modifierText, Scene.Layer.HUD);
        this.hudReference.getOwningScene().add(modifierImage, Scene.Layer.HUD);
        
        
        
    }
    
    private void handleObjectiveRewardMessage(SimpleEntry<MessageType,ArrayList<String>> message)
    {
        float center = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x/2;
        ArrayList<String> messageText = message.getValue();
        String currencyString = messageText.get(0);
        String skillReward = messageText.get(1);
        
        //reward text
        Text rewardText = new Text("Objective Reward:",LeadCrystalTextType.MESSAGE54);
        rewardText.setColor(new Color(240,194,12)); 
        rewardText.setPosition(center- rewardText.getWidth()/2, 700);
        rewardText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 270, 0, 0));
        
        //currency text
        Text currencyText = new Text(currencyString,LeadCrystalTextType.MESSAGE42);
        currencyText.setPosition(center- currencyText.getWidth() - 20, 629);
        currencyText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 270, 0, 0));
        
        //currency image
        Image currencyImage = new Image("goldCoin.png");
        currencyImage.setScale(.5f);
        currencyImage.setPosition(center- currencyText.getWidth() - 70, 625);
        currencyImage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 270, 0, 0));
        
        
        //skill text
        Text skillText = new Text(skillReward,LeadCrystalTextType.MESSAGE42);
        skillText.setPosition(center+ 65, 629);
        skillText.addTextEffect(new TextEffect(TextEffect.TextEffectType.DURATION, 270, 0, 0));
        
        //skill image
        Image skillImage = new Image("skillPoint.png");
        skillImage.setScale(1.3f);
        skillImage.setPosition(center + 20, 630);
        skillImage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 270, 0, 0));
        
        //fade effect
        TextEffect fade = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(Color.white), new Color(Color.white,0));
        fade.setDelay(240);
        currencyText.addTextEffect(fade);       
        fade = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(Color.white), new Color(Color.white,0));
        fade.setDelay(240);
        skillText.addTextEffect(fade);
        fade = new TextEffect(TextEffect.TextEffectType.COLOR, 30, new Color(new Color(240,194,12),1), new Color(new Color(240,194,12),0));
        fade.setDelay(240);
        rewardText.addTextEffect(fade);
        
        ImageEffect imageFade = new ImageEffect(ImageEffect.ImageEffectType.ALPHABRIGHTNESS,30, 1, 0);
        imageFade.setDelay(240);
        ImageEffect imageFade2 = new ImageEffect(ImageEffect.ImageEffectType.ALPHABRIGHTNESS,30, 1, 0);
        imageFade2.setDelay(240);
        currencyImage.addImageEffect(imageFade);
        skillImage.addImageEffect(imageFade2);
        
        //scale effect
//        Float[] points3 ={1f,1.2f,1f};
//        int[] durations3 = {45,45};
//        currencyText.addTextEffect(new MultiTextEffect(TextEffect.TextEffectType.SCALE, points3, durations3));
//        skillText.addTextEffect(new MultiTextEffect(TextEffect.TextEffectType.SCALE, points3, durations3));
//        Float[] points2 ={1f,1.2f,1f};
//        int[] durations2 = {45,45};
//        skillImage.addImageEffect(new MultiImageEffect(ImageEffect.ImageEffectType.SCALE,points2,durations2));
//        currencyImage.addImageEffect(new MultiImageEffect(ImageEffect.ImageEffectType.SCALE,points2,durations2));
        
        //add to scene
        this.hudReference.getOwningScene().add(currencyText, Scene.Layer.HUD);
        this.hudReference.getOwningScene().add(skillImage, Scene.Layer.HUD);
        this.hudReference.getOwningScene().add(currencyImage, Scene.Layer.HUD);
        this.hudReference.getOwningScene().add(skillText, Scene.Layer.HUD);
        this.hudReference.getOwningScene().add(rewardText,Scene.Layer.HUD);
    }
    
}
