package com.silvergobletgames.leadcrystal.core;

import com.jogamp.opengl.util.awt.TextRenderer;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.graphics.OpenGLGameWindow;
import com.silvergobletgames.sylver.graphics.OpenGLGameWindow.SylverRenderDelegate;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.Text.TextType;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public enum LeadCrystalTextType implements TextType{
    
    COMBAT,
    COMBAT_CRIT,
    HUD16,
    HUD20,
    HUD22,
    HUD24,
    HUD28,
    HUD30,
    HUD34,
    HUD40,
    MESSAGE38,
    MESSAGE42,
    MESSAGE48,
    MESSAGE54,
    MESSAGE90,
    CHAT,
    MENU23,
    MENU36,
    MENU30,
    MENU40,
    MENU46,
    MENU50,
    MENU54,
    MENU60;

    
    
    public static void loadTextTypes()
    {
         //build a java 2d graphics context to get the font matrics from
        BufferedImage bufferedImage = new BufferedImage ( 2 ,2 ,BufferedImage.TYPE_4BYTE_ABGR_PRE );
        Graphics2D java2d = ( Graphics2D)( bufferedImage.createGraphics());
        
        //load our custom font
        Font customFont;
        try 
        {
//            File rootDirectory = new File("Graphics/fonts/Aller_Rg.ttf");
//            URI textureFolderURI = rootDirectory.toURI().resolve("Graphics/");
             customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Graphics/fonts/Aller_Bd.ttf"));
             
        } 
        catch (IOException|FontFormatException e) 
        {
            Logger.getLogger(LeadCrystalTextType.class.getName()).log(Level.SEVERE,  "Error loading font", e);
            
            //load backup font
            customFont = new Font("CALIBRI", Font.PLAIN, 1);
         
        }

         //COMBAT
        Font font = new Font("IMPACT", Font.BOLD, 32);
        TextRenderer textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate());
        textRenderer.setUseVertexArrays(false);
        FontMetrics metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.COMBAT,textRenderer,metrics);
        
        //COMBAT
        font = new Font("IMPACT", Font.BOLD, 40);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate());
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.COMBAT_CRIT,textRenderer,metrics);
        
        
        //HUD16
        font = new Font("CALIBRI", Font.PLAIN, 16);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate(false,0));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.HUD16,textRenderer,metrics);
        
        
        //HUD20
        font = new Font("CALIBRI", Font.PLAIN, 20);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate(false,0));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.HUD20,textRenderer,metrics);
        
        //HUD22
        font = new Font("CALIBRI", Font.PLAIN, 22);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate(false,0));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.HUD22,textRenderer,metrics);
        
         //HUD24
        font = new Font("CALIBRI", Font.PLAIN, 24);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate(false,0));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.HUD24,textRenderer,metrics);
        
        //HUD28
        font = new Font("CALIBRI", Font.PLAIN, 28);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate(false,0));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.HUD28,textRenderer,metrics);
        
        //HUD30
        font = new Font("CALIBRI", Font.PLAIN, 30);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate(false,0));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.HUD30,textRenderer,metrics);
        
        //HUD34
        font = new Font("CALIBRI", Font.PLAIN, 36);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate(false,0));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.HUD34,textRenderer,metrics);
        
        //HUD40
        font = new Font("CALIBRI", Font.PLAIN, 40);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate(false,0));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.HUD40,textRenderer,metrics);
        
        
        //CHAT
        font = new Font("CALIBRI", Font.BOLD, 28);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate(false, 0));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.CHAT,textRenderer,metrics);
        
        //MESSAGE38
        font = new Font("IMPACT", Font.PLAIN, 38);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate());
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.MESSAGE38,textRenderer,metrics);
        
        //MESSAGE42
        font = new Font("IMPACT", Font.PLAIN, 42);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate());
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.MESSAGE42,textRenderer,metrics);
        
        //MESSAGE48
        font = new Font("IMPACT", Font.PLAIN, 48);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate());
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.MESSAGE48,textRenderer,metrics);
        
        //MESSAGE54
        font = new Font("IMPACT", Font.PLAIN, 54);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate());
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.MESSAGE54,textRenderer,metrics);

        //MESSAGE60
        font = new Font("IMPACT", Font.PLAIN, 90);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate(true, 1));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.MESSAGE90,textRenderer,metrics);
        
        //MENU23
        //font = new Font("CALIBRI", Font.BOLD, 40);
        font = customFont.deriveFont(23f); //aller font
        textRenderer = new TextRenderer(font, true, true, new SylverRenderDelegate(true,.5f));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.MENU23,textRenderer,metrics);

        //MENU30
        //font = new Font("CALIBRI", Font.BOLD, 40);
        font = customFont.deriveFont(30f); //aller font
        textRenderer = new TextRenderer(font, true, true, new SylverRenderDelegate(true,.6f));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.MENU30,textRenderer,metrics);

        
        //MENU36
        //font = new Font("CALIBRI", Font.BOLD, 40);
        font = customFont.deriveFont(36f); //aller font
        textRenderer = new TextRenderer(font, true, true, new SylverRenderDelegate(true,.83f));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.MENU36,textRenderer,metrics);

        
        //MENU40
        //font = new Font("CALIBRI", Font.BOLD, 40);
        font = customFont.deriveFont(40f); //aller font
        textRenderer = new TextRenderer(font, true, true, new SylverRenderDelegate(true,1.1f));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.MENU40,textRenderer,metrics);

        //MENU46
        //font = new Font("CALIBRI", Font.BOLD, 46);
        font = customFont.deriveFont(46f); //aller font
        textRenderer = new TextRenderer(font, true, true, new SylverRenderDelegate(true,1.28f));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.MENU46,textRenderer,metrics);

        //MENU50
        //font = new Font("CALIBRI", Font.BOLD, 46);
        font = customFont.deriveFont(50f); //aller font
        textRenderer = new TextRenderer(font, true, true, new SylverRenderDelegate(true,1.32f));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.MENU50,textRenderer,metrics);

        
        //MENU54
        //font = new Font("CALIBRI", Font.BOLD, 54);
        font = customFont.deriveFont(54f); //aller font
        textRenderer = new TextRenderer(font, true, true, new SylverRenderDelegate(true,1.5f));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.MENU54,textRenderer,metrics);

        
        //MENU60
        //font = new Font("CALIBRI", Font.BOLD, 54);
        font = customFont.deriveFont(95f); //aller font
        textRenderer = new TextRenderer(font, true, true, new SylverRenderDelegate(true,2f));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.MENU60,textRenderer,metrics);
 }

}