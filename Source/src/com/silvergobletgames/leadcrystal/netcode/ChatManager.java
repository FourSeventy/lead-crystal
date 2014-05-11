
package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.leadcrystal.scenes.GameClientScene;
import com.silvergobletgames.sylver.core.InputHandler;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.Text;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import com.jogamp.newt.event.KeyEvent;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.sylver.core.InputSnapshot;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL2;
import javax.media.opengl.GL3bc;


/**
 * Manages the collection and display of chat messages on the client.
 * @author Justin Capalbo
 */
public class ChatManager {
    
    //owning scene
    private GameClientScene owningScene;
    //If we are currently displaying text.  The text should hide itself after a few seconds,
    //And if a new message is received or we send a message, it should show for 10-ish seconds.
    private int visibleFrames = 0;
    
    //If we are currently accepting input.
    public boolean chatPromptOpen = false;
    
    //List of past sent messages (capped to 20)
    private ArrayList<String> pastMessages;
    private int pastMsgIndex = 0;
    
    //List of chat messages (capped to 5)
    private ArrayList<ChatText> messages; 
        
    //Outgoing message
    private Text outgoingMessage;
    
    //Prompt
    private Text promptText;
    
    //Cursor variables
    private Image cursor;
    private long cursorBlink = 0;
    private boolean cursorDraw = false;
    
    //paste
    private boolean paste = false;
    
    //BG variable
    private Image bg;
    
    //Constants
    private final int BASE_X = 50;
    private final int BASE_Y = 50;
    private final int HEIGHT_OFFSET = 25;
    private final Color PROMPT_COLOR = Color.white;
    private final Color CHAT_COLOR = Color.white;
    
    
    //==============
    // Constructor
    //==============
    
    public ChatManager(GameClientScene scene)
    {
        this.owningScene = scene;
        
        messages = new ArrayList();     
        pastMessages = new ArrayList();
        pastMessages.add("");
        
        promptText = new Text("To All Players: ",LeadCrystalTextType.HUD20);
        promptText.setScale(.9f);
        promptText.setColor(new Color(PROMPT_COLOR));
        promptText.setPosition(BASE_X , BASE_Y+5);
        
        outgoingMessage = new Text("",LeadCrystalTextType.HUD20);
        outgoingMessage.setScale(.9f);
        outgoingMessage.setColor(new Color(PROMPT_COLOR));
        outgoingMessage.setPosition(BASE_X + promptText.getWidth(), BASE_Y+5);
                
        bg = new Image("black.png");
        bg.setPosition(BASE_X - 2, BASE_Y);
        bg.setDimensions(promptText.getWidth(), promptText.getHeight());
        bg.setColor(new Color(.8f,.8f,.8f,.5f));
        
        cursor = new Image("textBoxCursor.png");
        cursor.setPosition(BASE_X + 1 + promptText.getWidth(), BASE_Y);
        cursor.setDimensions(2,30);        
    }
    
    //================
    // Class Methods
    //================
    
    public void draw(GL2 gl)
    {
        //draw chat promt and current typed message
        if(chatPromptOpen)
        {
            bg.draw(gl);
            outgoingMessage.draw(gl);
            promptText.draw(gl);          
            if (cursorDraw)
                cursor.draw(gl);
        }
        
        //draw old messages
        if (visibleFrames > 0 && messages.size() > 0)
        {
            for (ChatText t : messages)
            {
                t.draw(gl);
            }            
        }
    }
    
    /**
     * Receives input
     */
    public void update()
    {
        //update received messages
        for (int i=0; i<messages.size(); i++)
        {
            ChatText text = messages.get(i);
            text.update();
            if (text.lifetime == 0)
            {
                messages.remove(text);
                i--;
            }
        }
        
        if (visibleFrames > 0){
            visibleFrames--;
        }
        

        //handle cursor blinking
        cursorBlink++;
        if(cursorBlink % 50 == 0)
            cursorDraw = !cursorDraw;
    }
    
    /** 
     * Update from key input
     */
    public void handleInput(InputSnapshot input)
    {

        if(input != null && this.chatPromptOpen)
        {
            String builder = "";
            
            
            paste = false;
            if (input.isKeyReleased(KeyEvent.VK_UP))
            {
                if (this.pastMsgIndex < pastMessages.size())
                {
                    pastMsgIndex = Math.min(pastMsgIndex + 1, pastMessages.size()-1);
                    this.outgoingMessage.setText(pastMessages.get(pastMsgIndex));
                }
            }

            if (input.isKeyReleased(KeyEvent.VK_DOWN))
            {
                if (this.pastMsgIndex > 0)
                {
                    pastMsgIndex = Math.max(pastMsgIndex - 1 , 0);
                    this.outgoingMessage.setText(pastMessages.get(pastMsgIndex));
                }                
            }

            if (input.isKeyReleased(KeyEvent.VK_ESCAPE))
            {
                this.closePrompt();
                pastMsgIndex = 0;
            }

            for(Character chara: input.getTypedCharacters())
            {
                char character = chara;

                if(character == '\b')
                {
                    if(builder.length()> 0)
                        builder = builder.substring(0,builder.length() -1);
                    if(outgoingMessage.toString().length() > 0)
                    outgoingMessage.setText(outgoingMessage.toString().substring(0,outgoingMessage.toString().length() -1));
                }
                else
                {
                    if (character != '\r' && character != '\n')
                        builder += character;
                }
            }

            

            outgoingMessage.setText(outgoingMessage.toString() + builder);
            pastMessages.set(0, outgoingMessage.toString() + builder);
            if (chatPromptOpen)
                this.cursor.setPosition(BASE_X + 1 + promptText.getWidth() + outgoingMessage.getWidth(), BASE_Y); 
            bg.setDimensions(promptText.getWidth() + outgoingMessage.getWidth() + 5, promptText.getHeight());  

        }
        
    }
    
    /**
     * Open the prompt
     */
    public void openPrompt()
    {
        chatPromptOpen = true;
        cursorBlink = 0;
        cursorDraw = true;
        this.cursor.setPosition(BASE_X + 1 + promptText.getWidth(), BASE_Y);  
        this.bg.setDimensions(promptText.getWidth() + outgoingMessage.getWidth() + 5, promptText.getHeight());    
    }
     
    /**
     * Close the prompt
     */
    public void closePrompt()
    {
        chatPromptOpen = false;
        outgoingMessage.setText("");
        pastMsgIndex = 0;
    }
    
    
    /**
     * Sends the currently buffered message to the server
     */
    public void sendMessage()
    {
        if(this.outgoingMessage.toString().length() > 0)
        {
            this.pastMessages.add(1, outgoingMessage.toString());
            this.pastMessages.set(0,"");
            if (this.pastMessages.size() > 20)
                this.pastMessages.remove(20);
            pastMsgIndex = 0;
            
            //send chat packet
            ClientChatPacket packet = new ClientChatPacket();
            packet.chatMessage = outgoingMessage.toString();
            this.owningScene.sendPacket(packet);
        }
    }
    
    /**
     * Append a message to the chat log
     * @param s 
     */
    public void receiveMessage(String s)
    {
        ChatText text = new ChatText(s);
        text.setTextType(LeadCrystalTextType.HUD20);
        text.setColor(new Color(CHAT_COLOR));
        messages.add(0, text);
        if (messages.size() > 10){
            messages.remove(10); //Remove the 10th message if we go too high
        }
        for (int i=0; i<messages.size(); i++){
            Text tmpMsg = messages.get(i);
            tmpMsg.setPosition(BASE_X, BASE_Y + 5 + HEIGHT_OFFSET + i * tmpMsg.getHeight());
        }
        visibleFrames = 1000;
    }
    
    
    //=================
    // Private Class
    //================
    private class ChatText extends Text{
        
        public float lifetime;
        
        public ChatText(String s){
            super(s, LeadCrystalTextType.HUD20);
            lifetime = 500f;
        }        
        
        public void update(){
            super.update();
            lifetime--;
            if (lifetime <= 75f){
                float a = lifetime/75;
                this.color.a = a;
            }
        }
    }
}
