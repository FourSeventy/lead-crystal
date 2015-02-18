
package com.silvergobletgames.leadcrystal.cutscenes;

import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.InputSnapshot;
import com.silvergobletgames.sylver.graphics.Anchorable;
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
   
    private Image frame2;
    private Image frame1;
    private Image frame3;
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
         float height = Game.getInstance().getGraphicsWindow().getCurrentAspectRatio().y/2;
         //center -500 is max left
        
        frame1 = new Image("opening_frame1.jpg");
        frame1.setAnchor(Anchorable.Anchor.CENTER);
        frame1.setPositionAnchored(this.center, height);
        frame1.setDimensions(1600, 900);
        frame1.setAlphaBrightness(1);
        this.objects.add(frame1);  
        
        
        frame2 = new Image("opening_frame2.jpg");
        frame2.setDimensions(1600, 900);
        frame2.setAnchor(Anchorable.Anchor.CENTER);
        frame2.setPositionAnchored(this.center, height);
        frame2.setAlphaBrightness(0);
        this.objects.add(frame2);  
        
        frame3 = new Image("opening_frame3.jpg");
        frame3.setAnchor(Anchorable.Anchor.CENTER);
        frame3.setPositionAnchored(this.center, height);
        frame3.setAlphaBrightness(0);
        this.objects.add(frame3);  
             
        bashBubble = new Image("speechBubble.png");
        bashBubble.setDimensions(450,400);
        bashBubble.setPosition(center - 550, 475);
        bashBubble.setAlphaBrightness(0);
        this.objects.add(bashBubble);  
        
        slashBubble = new Image("speechBubble.png");
        slashBubble.setDimensions(450,400);
        slashBubble.setPosition(center -200, 500);
        slashBubble.setAlphaBrightness(0);
        this.objects.add(slashBubble);  
        
        bashTextArea = new TextBlock(center - 700, 500, 380, new Text("",LeadCrystalTextType.MENU30));       
        slashTextArea = new TextBlock(center - 300, 700, 380, new Text("",LeadCrystalTextType.MENU30));

    }
    
    public void update(InputSnapshot input)
    {
        super.update(input);
        
        //start fading in bash and slash image at 4 seconds
        if(counter == 60 *4)
        {
            frame2.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.ALPHABRIGHTNESS, 60, 0, 1));
        }
        
        //at 8 seconds Slash: "Hey Bash, where is this settlement we are looking for?"
        if(counter == 60 * 8)
        {
            slashBubble.setAlphaBrightness(1);

            Text t = new Text("Hey Slash, where is the settlement we are looking for?",LeadCrystalTextType.MENU30);
            t.setColor(new Color(Color.black));
            slashTextArea = new TextBlock(center - 150, 800, 380, t); 
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

            Text t = new Text("It should be right up ahead somewhere.",LeadCrystalTextType.MENU30);
            t.setColor(new Color(Color.black));
            bashTextArea = new TextBlock(center - 500, 770, 380, t); 
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

            Text t = new Text("It better be, we've been driving through this desert for two days!",LeadCrystalTextType.MENU30);
            t.setColor(new Color(Color.black));
            slashTextArea = new TextBlock(center - 150, 800, 380, t); 
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

            Text t = new Text("Their distress signal says they are under attack. We've got to help them!", LeadCrystalTextType.MENU30);
            t.setColor(new Color(Color.black));
            bashTextArea = new TextBlock(center - 500, 770, 380, t); 
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

            Text t = new Text("I still think this is a bad idea. There better be a good reward in this.", LeadCrystalTextType.MENU30);
            t.setColor(new Color(Color.black));
            slashTextArea = new TextBlock(center - 150, 800, 380, t); 
            this.objects.add(slashTextArea);
        }
        
        //at 33 seconds fade in settlement
        if(counter == 60 *33)
        {
            //take away old text
            this.objects.remove(slashTextArea);
            slashBubble.setAlphaBrightness(0);
            
            this.frame3.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.ALPHABRIGHTNESS, 60, 0, 1));
        }
        
        // at 37 seconds  Slash: "There it is up ahead! You hop out here and clear this area, I'll meet you at the town!"
        if(counter == 60 * 37)
        {
            
            //new text
            slashBubble.setAlphaBrightness(1);
            slashBubble.setHorizontalFlip(true);
            slashBubble.setVerticalFlip(true);
            slashBubble.setPosition(center -150, 150);

            Text t = new Text( "There it is up ahead! I'll jump out here and clear this area then meet you at the town!",LeadCrystalTextType.MENU30);
            t.setColor(new Color(Color.black));
            slashTextArea = new TextBlock(center - 100, 400, 380, t); 
            this.objects.add(slashTextArea);
        }
        
        
    }
}
