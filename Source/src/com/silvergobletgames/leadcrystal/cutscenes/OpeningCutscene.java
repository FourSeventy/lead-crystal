
package com.silvergobletgames.leadcrystal.cutscenes;

import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.InputSnapshot;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.windowsystem.TextArea;
import com.silvergobletgames.sylver.windowsystem.TextBlock;

/**
 *
 * @author Mike
 */
public class OpeningCutscene extends Cutscene 
{
   
    private Image bashSlashImage;
    private Image dangerCart;
    private Image settlement;
    private Image bashBubble;
    private Image slashBubble;
    private TextBlock slashTextArea;
    private TextBlock bashTextArea;
    private int center;
    
    public OpeningCutscene()
    {       
        super(60 * 42);
        
        // first image is the danger cart driving through the desert
        // fades into bash and slash sitting in the vehicle
        // Slash: "Hey Bash, where is this settlement we are looking for?"
        // Bash: "It should be right up ahead somewhere."
        // Slash: "It better be,  we've been driving through this damn desert for two days."
        // Bash: "Their distress signal says they are under attack. We've got to help them."
        // Slash: "I still think this is a bad idea. There better be a good reward in this"
        // Image of settlement fades in
        // Slash: "There it is up ahead! You hop out here and clear this area, I'll meet you at the town!"       
        // fade in to level
        
         this.center = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().x/2;
         //center -500 is max left
        
        dangerCart = new Image("openingCutscene1.jpg");
        dangerCart.setScale(1.7f);
        dangerCart.setPosition(center - 400, 650);
        dangerCart.setAlphaBrightness(1);
        this.objects.add(dangerCart);  
        
        
        bashSlashImage = new Image("openingCutscene2.jpg");
        bashSlashImage.setScale(3.8f);
        bashSlashImage.setPosition(center - (bashSlashImage.getWidth() * bashSlashImage.getScale())/2, 150);
        bashSlashImage.setAlphaBrightness(0);
        this.objects.add(bashSlashImage);  
        
        settlement = new Image("openingCutscene3.jpg");
        settlement.setScale(1f);
        settlement.setPosition(center + 300, 25);
        settlement.setAlphaBrightness(0);
        this.objects.add(settlement);  
             
        bashBubble = new Image("speechBubble.png");
        bashBubble.setScale(2f);
        bashBubble.setPosition(center - 300, 400);
        bashBubble.setAlphaBrightness(0);
        this.objects.add(bashBubble);  
        
        slashBubble = new Image("speechBubble.png");
        slashBubble.setScale(2f);
        slashBubble.setHorizontalFlip(true);
        slashBubble.setPosition(center -200, 400);
        slashBubble.setAlphaBrightness(0);
        this.objects.add(slashBubble);  
        
        bashTextArea = new TextBlock(center - 400, 600, 400, new Text(""));       
        slashTextArea = new TextBlock(center - 100, 600, 400, new Text(""));

    }
    
    public void update(InputSnapshot input)
    {
        super.update(input);
        
        //start fading in bash and slash image at 4 seconds
        if(counter == 60 *4)
        {
            bashSlashImage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.ALPHABRIGHTNESS, 60, 0, 1));
        }
        
        //at 8 seconds Slash: "Hey Bash, where is this settlement we are looking for?"
        if(counter == 60 * 8)
        {
            slashBubble.setAlphaBrightness(1);

            Text t = new Text("Hey Bash, where is this settlement we are looking for?");
            t.setColor(new Color(Color.black));
            slashTextArea = new TextBlock(center - 100, 600, 400, t); 
            this.objects.add(slashTextArea);
        }
        //at 13 seconds  Bash: "It should be right up ahead somewhere."
        if(counter == 60 * 13)
        {
            //take away old text
            this.objects.remove(slashTextArea);
            slashBubble.setAlphaBrightness(0);
            
            //new text
            bashBubble.setAlphaBrightness(1);

            Text t = new Text("It should be right up ahead somewhere.");
            t.setColor(new Color(Color.black));
            bashTextArea = new TextBlock(center - 200, 600, 400, t); 
            this.objects.add(bashTextArea);
        }
        //at 18 seconds Slash: "It better be,  we've been driving through this damn desert for two days."
        if(counter == 60 * 18)
        {
            //take away old text
            this.objects.remove(bashTextArea);
            bashBubble.setAlphaBrightness(0);
            
            //new text
            slashBubble.setAlphaBrightness(1);

            Text t = new Text("It better be,  we've been driving through this damn desert for two days.");
            t.setColor(new Color(Color.black));
            slashTextArea = new TextBlock(center - 100, 600, 400, t); 
            this.objects.add(slashTextArea);
        }
        //at 23 seconds Bash: "Their distress signal says they are under attack. We've got to help them."
        if(counter == 60 * 23)
        {
            //take away old text
            this.objects.remove(slashTextArea);
            slashBubble.setAlphaBrightness(0);
            
            //new text
            bashBubble.setAlphaBrightness(1);

            Text t = new Text("Their distress signal says they are under attack. We've got to help them.");
            t.setColor(new Color(Color.black));
            bashTextArea = new TextBlock(center - 200, 600, 400, t); 
            this.objects.add(bashTextArea);
        }
        // at 28 seconds "I still think this is a bad idea. There better be a good reward in this"
        if(counter == 60 * 28)
        {
            //take away old text
            this.objects.remove(bashTextArea);
            bashBubble.setAlphaBrightness(0);
            
            //new text
            slashBubble.setAlphaBrightness(1);

            Text t = new Text("I still think this is a bad idea. There better be a good reward in this");
            t.setColor(new Color(Color.black));
            slashTextArea = new TextBlock(center - 100, 600, 400, t); 
            this.objects.add(slashTextArea);
        }
        
        //at 33 seconds fade in settlement
        if(counter == 60 *33)
        {
            //take away old text
            this.objects.remove(slashTextArea);
            slashBubble.setAlphaBrightness(0);
            
            this.settlement.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.ALPHABRIGHTNESS, 60, 0, 1));
        }
        
        // at 37 seconds  Slash: "There it is up ahead! You hop out here and clear this area, I'll meet you at the town!"
        if(counter == 60 * 37)
        {
            
            //new text
            slashBubble.setAlphaBrightness(1);

            Text t = new Text( "There it is up ahead! You hop out here and clear this area, I'll meet you at the town!");
            t.setColor(new Color(Color.black));
            slashTextArea = new TextBlock(center - 200, 600, 400, t); 
            this.objects.add(slashTextArea);
        }
        
        
    }
}
