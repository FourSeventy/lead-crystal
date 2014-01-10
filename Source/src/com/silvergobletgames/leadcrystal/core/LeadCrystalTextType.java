
package com.silvergobletgames.leadcrystal.core;

import com.jogamp.opengl.util.awt.TextRenderer;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.graphics.OpenGLGameWindow;
import com.silvergobletgames.sylver.graphics.OpenGLGameWindow.SylverRenderDelegate;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.graphics.Text.TextType;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Mike
 */
public enum LeadCrystalTextType implements TextType{
    
    COMBAT,
    MESSAGE,
    CHAT,
    MENUBUTTONS;

    
    
    public static void loadTextTypes()
    {
         //build a java 2d graphics context to get the font matrics from
        BufferedImage bufferedImage = new BufferedImage ( 2 ,2 ,BufferedImage.TYPE_4BYTE_ABGR_PRE );
        Graphics2D java2d = ( Graphics2D)( bufferedImage.createGraphics());
        
         //COMBAT
        Font font = new Font("IMPACT", Font.BOLD, 30);
        TextRenderer textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate());
        textRenderer.setUseVertexArrays(false);
        FontMetrics metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.COMBAT,textRenderer,metrics);
        
        //CHAT
        font = new Font("Ebrima", Font.BOLD, 16);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate(true, .7f));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.CHAT,textRenderer,metrics);
        
        //MESSAGE
        font = new Font("CALIBRI", Font.BOLD, 48);
        textRenderer = new TextRenderer(font, true, true,new SylverRenderDelegate());
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.MESSAGE,textRenderer,metrics);

        //MENUBUTTONS
        font = new Font("CALIBRI", Font.BOLD, 32);
        textRenderer = new TextRenderer(font, true, true, new SylverRenderDelegate(true,1));
        textRenderer.setUseVertexArrays(false);
        metrics = java2d.getFontMetrics(font);
        Game.getInstance().getGraphicsWindow().registerTextRenderer(LeadCrystalTextType.MENUBUTTONS,textRenderer,metrics);

    }

}