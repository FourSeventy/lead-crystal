package com.silvergobletgames.leadcrystal.core;

import java.util.ArrayList;
import com.jogamp.opengl.util.texture.Texture;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.graphics.AnimationPack;
import com.silvergobletgames.sylver.graphics.TextureLoader;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.HashMap;
import net.phys2d.math.Vector2f;

/**
 * This class is a singleton factory class that builds animations.
 * @author mike
 */
public class AnimationPackClasses {

    
    public static class NPC2AnimationPack extends AnimationPack
    {
        public NPC2AnimationPack()
        {
            //Idle
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc2idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc2idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc2idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc2idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc2idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc2idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc2idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc2idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc2idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc2idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc2idle10.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle); 
        }
    }
    
    public static class NPC3AnimationPack extends AnimationPack
    {
        public NPC3AnimationPack()
        {
            //Idle
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc3idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc3idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc3idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc3idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc3idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc3idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc3idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc3idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc3idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc3idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc3idle10.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle); 
        }
    }
   
    public static class NPC4AnimationPack extends AnimationPack
    {
        public NPC4AnimationPack()
        {
            //Idle
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc4idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc4idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc4idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc4idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc4idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc4idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc4idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc4idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc4idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc4idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc4idle10.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle); 
        }
    }
    
    public static class NPC5AnimationPack extends AnimationPack
    {
        public NPC5AnimationPack()
        {
            //Idle
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc5idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc5idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc5idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc5idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc5idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc5idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc5idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc5idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc5idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc5idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc5idle10.png"));
            this.animationSet.put(AnimationPack.CoreAnimations.IDLE, idle); 
        }
    }
    
    public static class NPC6AnimationPack extends AnimationPack
    {
        public NPC6AnimationPack()
        {
            //Idle
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc6idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc6idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc6idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc6idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc6idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc6idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc6idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc6idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc6idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc6idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc6idle10.png"));
            this.animationSet.put(AnimationPack.CoreAnimations.IDLE, idle); 
        }
    }
    
    public static class NPC7AnimationPack extends AnimationPack
    {
        public NPC7AnimationPack()
        {
            //Idle
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc7idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc7idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc7idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc7idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc7idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc7idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc7idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc7idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc7idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc7idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("npc7idle10.png"));
            this.animationSet.put(AnimationPack.CoreAnimations.IDLE, idle); 
        }
    }
    
    public static class Scout1AnimationPack extends AnimationPack
    {
        public Scout1AnimationPack()
        {
            //Idle
            ArrayList<Texture> idle = new ArrayList();
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-idle0.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-idle1.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-idle2.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-idle3.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-idle4.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-idle5.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-idle6.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-idle7.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-idle8.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-idle9.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-idle10.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Running
            ArrayList<Texture> running = new ArrayList();
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk0.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk1.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk2.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk3.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk4.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk5.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk6.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk7.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk8.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk9.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk10.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk11.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk12.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk13.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk14.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk15.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk16.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk17.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk18.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk19.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk20.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk21.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk22.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk23.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk24.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-walk25.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);

            //Ranged Attack
            ArrayList<Texture> ranged = new ArrayList();
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-ranged0.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-ranged1.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-ranged2.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-ranged3.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-ranged4.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-ranged5.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-ranged6.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-ranged7.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-ranged8.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-ranged9.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1-ranged10.png"));
            this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK ,ranged); 

            //death
            ArrayList<Texture> dying = new ArrayList();
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_rightarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_righthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_leftleg.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_rightfoot.png"));  
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_leftfoot.png"));                    
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);

            //=====================
            // Build Delay Map
            //=====================        
            this.timingMap.put(ExtendedImageAnimations.RANGEDATTACK, 10);    
            
            //========================
            // Build Skill Offset Map
            //========================

            this.positionOffsetMap.put(ExtendedImageAnimations.RANGEDATTACK, new SylverVector2f(50,22));
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_0111_0100_1011_1011_0100_1011_0100),
                                                                                        Float.intBitsToFloat(0b0111_0111_1011_1011_0110_0100_0111_0111)));
                    
                    

            //================
            // Build FPT Map
            //================
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK, 3);
             this.fptMap.put(ExtendedImageAnimations.RUNNING, 5);
              


        }
    }
    
    public static class Scout2AnimationPack extends AnimationPack
    {
        public Scout2AnimationPack()
        {
            //Idle
            ArrayList<Texture> idle = new ArrayList();
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-idle0.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-idle1.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-idle2.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-idle3.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-idle4.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-idle5.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-idle6.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-idle7.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-idle8.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-idle9.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-idle10.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Running
            ArrayList<Texture> running = new ArrayList();
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk0.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk1.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk2.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk3.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk4.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk5.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk6.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk7.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk8.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk9.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk10.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk11.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk12.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk13.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk14.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk15.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk16.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk17.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk18.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk19.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk20.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk21.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk22.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk23.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk24.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-walk25.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);

            //Ranged Attack
            ArrayList<Texture> ranged = new ArrayList();
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-ranged0.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-ranged1.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-ranged2.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-ranged3.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-ranged4.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-ranged5.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-ranged6.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-ranged7.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-ranged8.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-ranged9.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2-ranged10.png"));
            this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK ,ranged); 

            //death
            ArrayList<Texture> dying = new ArrayList();
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_rightarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_righthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_leftleg.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_rightfoot.png"));  
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_leftfoot.png"));                    
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);

            //=====================
            // Build Delay Map
            //=====================        
            this.timingMap.put(ExtendedImageAnimations.RANGEDATTACK, 10);    
            
            //========================
            // Build Skill Offset Map
            //========================

            this.positionOffsetMap.put(ExtendedImageAnimations.RANGEDATTACK, new SylverVector2f(50,22));
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_0111_0100_1011_1011_0100_1011_0100),
                                                                                        Float.intBitsToFloat(0b0111_0111_1011_1011_0110_0100_0111_0111)));
                    
                    

            //================
            // Build FPT Map
            //================
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK, 3);
            this.fptMap.put(ExtendedImageAnimations.RUNNING, 3);
              

        }
    }
    
    public static class Scout3AnimationPack extends AnimationPack
    {
        public Scout3AnimationPack()
        {
            //Idle
            ArrayList<Texture> idle = new ArrayList();
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-idle0.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-idle1.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-idle2.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-idle3.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-idle4.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-idle5.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-idle6.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-idle7.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-idle8.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-idle9.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-idle10.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Running
            ArrayList<Texture> running = new ArrayList();
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk0.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk1.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk2.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk3.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk4.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk5.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk6.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk7.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk8.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk9.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk10.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk11.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk12.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk13.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk14.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk15.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk16.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk17.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk18.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk19.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk20.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk21.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk22.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk23.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk24.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-walk25.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);

            //Ranged Attack
            ArrayList<Texture> ranged = new ArrayList();
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-ranged0.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-ranged1.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-ranged2.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-ranged3.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-ranged4.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-ranged5.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-ranged6.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-ranged7.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-ranged8.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-ranged9.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3-ranged10.png"));
            this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK ,ranged); 

            //death
            ArrayList<Texture> dying = new ArrayList();
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_rightarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_righthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_leftleg.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_rightfoot.png"));  
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_leftfoot.png"));                    
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);

            //=====================
            // Build Delay Map
            //=====================        
            this.timingMap.put(ExtendedImageAnimations.RANGEDATTACK, 10);    
            
            //========================
            // Build Skill Offset Map
            //========================

            this.positionOffsetMap.put(ExtendedImageAnimations.RANGEDATTACK, new SylverVector2f(50,22));
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_0111_0100_1011_1011_0100_1011_0100),
                                                                                        Float.intBitsToFloat(0b0111_0111_1011_1011_0110_0100_0111_0111)));
                    
                    

            //================
            // Build FPT Map
            //================
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK, 3);
             this.fptMap.put(ExtendedImageAnimations.RUNNING, 3);
              

        }
    }
    
    public static class PlantAnimationPack extends AnimationPack
    {
        public PlantAnimationPack()
        {
            //Idle
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_idle10.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_idle11.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_idle12.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_idle13.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Ranged Attack
            ArrayList<Texture> ranged = new ArrayList();
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack0.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack1.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack2.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack3.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack4.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack5.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack6.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack7.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack8.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack9.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack10.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack11.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack12.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack13.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack14.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack15.png"));
                    ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_attack16.png"));
            this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK ,ranged); 

            //death
            ArrayList<Texture> dying = new ArrayList();
                    dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_butt.png"));
                    dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_head.png"));
                    dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_leg_bot.png"));
                    dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_leg_mid.png"));
                    dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_leg_top.png"));
                    dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_torso.png"));
                    dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_wing.png"));
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);

            
            //spawning
            ArrayList<Texture> spawning = new ArrayList();
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn1.png"));    
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn2.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn3.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn4.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn5.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn6.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn7.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn8.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn9.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn10.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn11.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn12.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn13.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn14.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn15.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("spitter_spawn16.png"));
            this.animationSet.put(ExtendedImageAnimations.SPAWN,spawning);


            //=====================
            // Build Delay Map
            //=====================        
            this.timingMap.put(ExtendedImageAnimations.RANGEDATTACK, 40);

            //========================
            // Build Skill Offset Map
            //========================

            this.positionOffsetMap.put(ExtendedImageAnimations.RANGEDATTACK, new SylverVector2f(90,45));
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_0011_0111_0111_0111_0111_0110_0000),
                                                                                        Float.intBitsToFloat(0b0010_1110_0011_0100_0111_1010_1100_0000)));
                  

            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE, 5);
            this.fptMap.put(ExtendedImageAnimations.SPAWN, 5);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK, 5);
        }
    }  
    
    public static class FlierAnimationPack extends AnimationPack
    {
        public FlierAnimationPack()
        {
             //Idle
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle7.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);
            
            //Idle
            idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_idle7.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, idle);
            
            //Spawn
            ArrayList<Texture> spawn = new ArrayList();
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_spawn1.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_spawn2.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_spawn3.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_spawn4.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_spawn5.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_spawn6.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_spawn7.png"));
            this.animationSet.put(ExtendedImageAnimations.SPAWN, spawn);

            //flying
            ArrayList<Texture> running = new ArrayList();
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_attack0.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_attack1.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_attack2.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_attack3.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_attack4.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_attack5.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_attack6.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_attack7.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_attack8.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_attack9.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_attack10.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_attack11.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_attack12.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_attack13.png"));
            this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK, running);
            
            //death
            ArrayList<Texture> dying = new ArrayList();
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_head.png"));
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_torso.png"));
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_torso.png"));
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_blutt.png"));
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_legs.png"));
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_wing.png"));
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_wing.png"));
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);
            
            
             //=====================
            // Build Delay Map
            //=====================        
            this.timingMap.put(ExtendedImageAnimations.RANGEDATTACK, 24);

            //========================
            // Build Skill Offset Map
            //========================
            this.positionOffsetMap.put(ExtendedImageAnimations.RANGEDATTACK, new SylverVector2f(0,-75));
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0111_0110_0111_0011_0111_0111_0110_0000),
                                                                                        Float.intBitsToFloat(0b0011_1000_0111_0011_0100_0110_0111_0000)));
                  

            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE, 5);
            this.fptMap.put(ExtendedImageAnimations.RUNNING, 5);
            this.fptMap.put(ExtendedImageAnimations.SPAWN, 3);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK, 6);
            
        }
    }
    
    public static class MoleAnimationPack extends AnimationPack
    {
        public MoleAnimationPack()
        {
             //Idle
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle10.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle11.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle12.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle13.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle14.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle15.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_idle16.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);
            
            //Idle
            idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk10.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk11.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk12.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk13.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk14.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk15.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_walk16.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, idle);
            
            //Spawn
            ArrayList<Texture> spawn = new ArrayList();
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn1.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn2.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn3.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn4.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn5.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn6.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn7.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn8.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn9.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn10.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn11.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn12.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn13.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn14.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn15.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn16.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn17.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn18.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn19.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn20.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn21.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn22.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn23.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_spawn24.png"));
            this.animationSet.put(ExtendedImageAnimations.SPAWN, spawn);

            //flying
            ArrayList<Texture> running = new ArrayList();
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_attack0.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_attack1.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_attack2.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_attack3.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_attack4.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_attack5.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_attack6.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_attack7.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_attack8.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_attack9.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_attack10.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_attack11.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_attack12.png"));
            this.animationSet.put(ExtendedImageAnimations.MELEEATTACK, running);
            
            //death
            ArrayList<Texture> dying = new ArrayList();
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_head.png"));                
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_upperarm.png"));
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_forearm.png"));               
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_hand.png"));
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_torso.png"));
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("mole_foot.png"));
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);
            
            
            //=====================
            // Build Delay Map
            //=====================        
            this.timingMap.put(ExtendedImageAnimations.MELEEATTACK, 20);

            //========================
            // Build Skill Offset Map
            //========================
            this.positionOffsetMap.put(ExtendedImageAnimations.MELEEATTACK, new SylverVector2f(100,0));
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_1101_1010_0111_0111_0111_0000_0000),
                                                                                        Float.intBitsToFloat(0b0111_1010_1000_0101_0111_0000_0000_0000)));
                  
            
            //================
            // Build FPT Map
            //================
            this.fptMap.put(ExtendedImageAnimations.RUNNING, 5);
            this.fptMap.put(ExtendedImageAnimations.SPAWN, 3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK, 4);
            this.fptMap.put(CoreAnimations.IDLE, 7);
        }
    }
    
    public static class JumperAnimationPack extends AnimationPack
    {
        public JumperAnimationPack()
        {
             //Idle
            ArrayList<Texture> idle = new ArrayList<>();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-idle10.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-idle11.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-idle12.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-idle13.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-idle14.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);
            
            //Idle
            ArrayList<Texture> floating = new ArrayList<>();
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float0.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float1.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float2.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float3.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float4.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float5.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float6.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float7.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float8.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float9.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float10.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float11.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float12.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float13.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float14.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float15.png"));
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float16.png"));  
                floating.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-float17.png"));  
            this.animationSet.put(ExtendedImageAnimations.JUMPING, floating);
            
            idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk10.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk11.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk12.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk13.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk14.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk15.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk16.png"));               
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk17.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk18.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk19.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk20.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk21.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk22.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk23.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper-walk24.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, idle);
            
            //Spawn
            ArrayList<Texture> spawn = new ArrayList();
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_spawn1.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_spawn2.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_spawn3.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_spawn4.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_spawn5.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_spawn6.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_spawn7.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_spawn8.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_spawn9.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_spawn10.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_spawn11.png"));
                spawn.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_spawn12.png"));
            this.animationSet.put(ExtendedImageAnimations.SPAWN, spawn);

            
            //death
            ArrayList<Texture> dying = new ArrayList();
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_head.png"));                
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_upperarm.png"));
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_midleg.png"));               
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_torso.png"));
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("jumper_spike.png"));
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying); 
            
            
            //=====================
            // Build Delay Map
            //=====================        

            this.timingMap.put(ExtendedImageAnimations.RANGEDATTACK, 15);
            
            //========================
            // Build Skill Offset Map
            //========================
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_1101_1010_0111_0111_0111_0000_0000),
                                                                                        Float.intBitsToFloat(0b0111_1010_1000_0101_0111_0000_0000_0000)));
                  
            
            //================
            // Build FPT Map
            //================
            this.fptMap.put(ExtendedImageAnimations.JUMPING, 5);
            this.fptMap.put(ExtendedImageAnimations.RUNNING, 5);
            this.fptMap.put(ExtendedImageAnimations.SPAWN, 3);
            this.fptMap.put(CoreAnimations.IDLE, 7);
        }
    }
    
    
    //==============
    // Bash Brown 
    //==============
     public static class BashBrownBodyAnimationPack extends AnimationPack
     {
        public BashBrownBodyAnimationPack()
        {
            //=====================
            // Build Animation Map
            //=====================

            //idle  fps 21
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-idle10.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-idle11.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-idle12.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-idle13.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-idle14.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Running  fps 21
            ArrayList<Texture> running = new ArrayList();
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk0.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk1.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk2.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk3.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk4.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk5.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk6.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk7.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk8.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk9.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk10.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk11.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk12.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk13.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk14.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk15.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk16.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk17.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk18.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk19.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk20.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk21.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);
            
            //RunningReverse fps 21
            ArrayList<Texture> runningReverse = new ArrayList();
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk21.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk20.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk19.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk18.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk17.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk16.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk15.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk14.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk13.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk12.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk11.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk10.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk9.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk8.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk7.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk6.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk5.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk4.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk3.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk2.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk1.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-walk0.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNINGREVERSE, runningReverse);

            //Jumping fps 21
            ArrayList<Texture> jumping = new ArrayList();
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-jump0.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-jump1.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-jump2.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-jump3.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-jump4.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-jump5.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-jump6.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-jump7.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-jump8.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-jump9.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-jump10.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-jump11.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-jump12.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-jump13.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-jump14.png"));
            this.animationSet.put(ExtendedImageAnimations.JUMPING, jumping);    
            
            //climbing 21fps
            ArrayList<Texture> climb = new ArrayList();
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-climb0.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-climb1.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-climb2.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-climb3.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-climb4.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-climb5.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-climb6.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-body-climb7.png"));
            this.animationSet.put(ExtendedImageAnimations.CLIMBING,climb);

            //death
            ArrayList<Texture> dying = new ArrayList();
            //dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_brown_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_brown_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_brown_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_brown_rightarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_brown_righthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_brown_leftleg.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_brown_rightleg.png"));  
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_brown_leftfoot.png"));  
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);


            //========================
            // Build Skill Offset Map
            //========================
            
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_0111_0100_1011_1011_0100_1011_0100),
                                                                                        Float.intBitsToFloat(0b0111_0111_1011_1011_0110_0100_0111_0111)));
                    
            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RUNNING,2);
            this.fptMap.put(ExtendedImageAnimations.CLIMBING,5);
            this.fptMap.put(ExtendedImageAnimations.RUNNINGREVERSE,2);

        }
    }
        
     public static class BashBrownFrontArmAnimationPack extends AnimationPack
     {
         public BashBrownFrontArmAnimationPack()
         {
             //idle 15fps
             ArrayList<Texture> idle = new ArrayList();
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-idle0.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-idle1.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-idle2.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-idle3.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-idle4.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-idle5.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-idle6.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-idle7.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-idle8.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-idle9.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-idle10.png"));
              this.animationSet.put(CoreAnimations.IDLE, idle);
              
              //melee 25fps
              ArrayList<Texture> melee = new ArrayList();
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee0.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee1.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee2.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee3.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee4.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee5.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee6.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee7.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee8.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee9.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee10.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee11.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee12.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee13.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee14.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-melee15.png"));
              this.animationSet.put(ExtendedImageAnimations.MELEEATTACK,melee);
              
              
              //Ranged 15fps
              ArrayList<Texture> attacking = new ArrayList();
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-shoot0.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-shoot1.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-shoot2.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-shoot3.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-shoot4.png"));             
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-shoot5.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-shoot6.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-shoot7.png")); 
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-shoot8.png"));              
              this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK,attacking);
              
              //Use 15fps
              ArrayList<Texture> use = new ArrayList(); 
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-cast0.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-cast1.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-cast2.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-cast3.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-cast4.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-cast5.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-cast6.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-front-cast7.png"));
              this.animationSet.put(ExtendedImageAnimations.SPELLATTACK,use);
              
            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.SPELLATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK,1);
             
              
              //========================
              // Build Skill Offset Map
              //========================
            
              this.positionOffsetMap.put(ExtendedImageAnimations.RANGEDATTACK, new SylverVector2f(25,10));
              this.positionOffsetMap.put(ExtendedImageAnimations.MELEEATTACK, new SylverVector2f(25,10));
            
         }
     }
     
     public static class BashBrownBackArmAnimationPack extends AnimationPack
     {
         public BashBrownBackArmAnimationPack()
         {
              //idle
              ArrayList<Texture> idle = new ArrayList();
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-idle0.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-idle1.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-idle2.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-idle3.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-idle4.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-idle5.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-idle6.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-idle7.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-idle8.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-idle9.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-idle10.png"));
              this.animationSet.put(CoreAnimations.IDLE, idle);
              
              //melee
              ArrayList<Texture> melee = new ArrayList();
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee0.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee1.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee2.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee3.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee4.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee5.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee6.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee7.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee8.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee9.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee10.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee11.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee12.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee13.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee14.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-melee15.png"));              
              this.animationSet.put(ExtendedImageAnimations.MELEEATTACK,melee);
              
              
              //Ranged
              ArrayList<Texture> attacking = new ArrayList();
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-shoot0.png"));
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-shoot1.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-shoot2.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-shoot3.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-shoot4.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-shoot5.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-shoot6.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-shoot7.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-shoot8.png")); 
              this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK,attacking);
              
              //Use
              ArrayList<Texture> use = new ArrayList();
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-cast0.png"));
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-cast1.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-cast2.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-cast3.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-cast4.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-cast5.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-cast6.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-brown-back-cast7.png")); 
              this.animationSet.put(ExtendedImageAnimations.SPELLATTACK,use);
              
              //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.SPELLATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK,1);
         }
     }
     
     
     
     //==============
    // Bash Black
    //==============
     public static class BashBlackBodyAnimationPack extends AnimationPack
     {
        public BashBlackBodyAnimationPack()
        {
            //=====================
            // Build Animation Map
            //=====================

            //idle  fps 21
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-idle10.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-idle11.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-idle12.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-idle13.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-idle14.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Running  fps 21
            ArrayList<Texture> running = new ArrayList();
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk0.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk1.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk2.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk3.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk4.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk5.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk6.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk7.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk8.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk9.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk10.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk11.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk12.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk13.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk14.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk15.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk16.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk17.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk18.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk19.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk20.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk21.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);
            
            //RunningReverse fps 21
            ArrayList<Texture> runningReverse = new ArrayList();
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk21.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk20.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk19.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk18.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk17.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk16.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk15.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk14.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk13.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk12.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk11.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk10.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk9.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk8.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk7.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk6.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk5.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk4.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk3.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk2.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk1.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-walk0.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNINGREVERSE, runningReverse);

            //Jumping fps 21
            ArrayList<Texture> jumping = new ArrayList();
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-jump0.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-jump1.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-jump2.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-jump3.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-jump4.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-jump5.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-jump6.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-jump7.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-jump8.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-jump9.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-jump10.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-jump11.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-jump12.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-jump13.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-jump14.png"));
            this.animationSet.put(ExtendedImageAnimations.JUMPING, jumping);    
            
            //climbing 21fps
            ArrayList<Texture> climb = new ArrayList();
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-climb0.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-climb1.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-climb2.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-climb3.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-climb4.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-climb5.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-climb6.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-body-climb7.png"));
            this.animationSet.put(ExtendedImageAnimations.CLIMBING,climb);

            //death
            ArrayList<Texture> dying = new ArrayList();
            //dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_black_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_black_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_black_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_black_rightarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_black_righthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_black_leftleg.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_black_rightleg.png"));  
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_black_leftfoot.png"));  
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);


            //========================
            // Build Skill Offset Map
            //========================
            
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_0111_0100_1011_1011_0100_1011_0100),
                                                                                        Float.intBitsToFloat(0b0111_0111_1011_1011_0110_0100_0111_0111)));
                    
            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RUNNING,2);
            this.fptMap.put(ExtendedImageAnimations.CLIMBING,5);
            this.fptMap.put(ExtendedImageAnimations.RUNNINGREVERSE,2);

        }
    }
        
     public static class BashBlackFrontArmAnimationPack extends AnimationPack
     {
         public BashBlackFrontArmAnimationPack()
         {
             //idle 15fps
             ArrayList<Texture> idle = new ArrayList();
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-idle0.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-idle1.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-idle2.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-idle3.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-idle4.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-idle5.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-idle6.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-idle7.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-idle8.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-idle9.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-idle10.png"));
              this.animationSet.put(CoreAnimations.IDLE, idle);
              
              //melee 25fps
              ArrayList<Texture> melee = new ArrayList();
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee0.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee1.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee2.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee3.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee4.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee5.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee6.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee7.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee8.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee9.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee10.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee11.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee12.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee13.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee14.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-melee15.png"));
              this.animationSet.put(ExtendedImageAnimations.MELEEATTACK,melee);
              
              
              //Ranged 15fps
              ArrayList<Texture> attacking = new ArrayList();
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-shoot0.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-shoot1.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-shoot2.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-shoot3.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-shoot4.png"));             
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-shoot5.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-shoot6.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-shoot7.png")); 
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-shoot8.png"));              
              this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK,attacking);
              
              //Use 15fps
              ArrayList<Texture> use = new ArrayList(); 
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-cast0.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-cast1.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-cast2.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-cast3.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-cast4.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-cast5.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-cast6.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-front-cast7.png"));
              this.animationSet.put(ExtendedImageAnimations.SPELLATTACK,use);
              
            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.SPELLATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK,1);
             
              
              //========================
              // Build Skill Offset Map
              //========================
            
              this.positionOffsetMap.put(ExtendedImageAnimations.RANGEDATTACK, new SylverVector2f(25,10));
              this.positionOffsetMap.put(ExtendedImageAnimations.MELEEATTACK, new SylverVector2f(25,10));
            
         }
     }
     
     public static class BashBlackBackArmAnimationPack extends AnimationPack
     {
         public BashBlackBackArmAnimationPack()
         {
              //idle
              ArrayList<Texture> idle = new ArrayList();
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-idle0.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-idle1.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-idle2.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-idle3.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-idle4.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-idle5.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-idle6.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-idle7.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-idle8.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-idle9.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-idle10.png"));
              this.animationSet.put(CoreAnimations.IDLE, idle);
              
              //melee
              ArrayList<Texture> melee = new ArrayList();
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee0.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee1.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee2.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee3.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee4.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee5.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee6.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee7.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee8.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee9.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee10.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee11.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee12.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee13.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee14.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-melee15.png"));              
              this.animationSet.put(ExtendedImageAnimations.MELEEATTACK,melee);
              
              
              //Ranged
              ArrayList<Texture> attacking = new ArrayList();
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-shoot0.png"));
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-shoot1.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-shoot2.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-shoot3.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-shoot4.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-shoot5.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-shoot6.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-shoot7.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-shoot8.png")); 
              this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK,attacking);
              
              //Use
              ArrayList<Texture> use = new ArrayList();
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-cast0.png"));
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-cast1.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-cast2.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-cast3.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-cast4.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-cast5.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-cast6.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-black-back-cast7.png")); 
              this.animationSet.put(ExtendedImageAnimations.SPELLATTACK,use);
              
              //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.SPELLATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK,1);
         }
     }
   
      
     //==============
    // Bash Blue
    //==============
     public static class BashBlueBodyAnimationPack extends AnimationPack
     {
        public BashBlueBodyAnimationPack()
        {
            //=====================
            // Build Animation Map
            //=====================

            //idle  fps 21
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-idle10.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-idle11.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-idle12.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-idle13.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-idle14.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Running  fps 21
            ArrayList<Texture> running = new ArrayList();
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk0.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk1.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk2.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk3.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk4.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk5.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk6.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk7.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk8.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk9.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk10.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk11.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk12.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk13.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk14.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk15.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk16.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk17.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk18.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk19.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk20.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk21.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);
            
            //RunningReverse fps 21
            ArrayList<Texture> runningReverse = new ArrayList();
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk21.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk20.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk19.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk18.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk17.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk16.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk15.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk14.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk13.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk12.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk11.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk10.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk9.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk8.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk7.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk6.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk5.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk4.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk3.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk2.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk1.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-walk0.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNINGREVERSE, runningReverse);

            //Jumping fps 21
            ArrayList<Texture> jumping = new ArrayList();
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-jump0.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-jump1.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-jump2.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-jump3.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-jump4.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-jump5.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-jump6.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-jump7.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-jump8.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-jump9.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-jump10.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-jump11.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-jump12.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-jump13.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-jump14.png"));
            this.animationSet.put(ExtendedImageAnimations.JUMPING, jumping);    
            
            //climbing 21fps
            ArrayList<Texture> climb = new ArrayList();
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-climb0.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-climb1.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-climb2.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-climb3.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-climb4.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-climb5.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-climb6.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-body-climb7.png"));
            this.animationSet.put(ExtendedImageAnimations.CLIMBING,climb);

            //death
            ArrayList<Texture> dying = new ArrayList();
            //dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_blue_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_blue_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_blue_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_blue_rightarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_blue_righthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_blue_leftleg.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_blue_rightleg.png"));  
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_blue_leftfoot.png"));  
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);


            //========================
            // Build Skill Offset Map
            //========================
            
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_0111_0100_1011_1011_0100_1011_0100),
                                                                                        Float.intBitsToFloat(0b0111_0111_1011_1011_0110_0100_0111_0111)));
                    
            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RUNNING,2);
            this.fptMap.put(ExtendedImageAnimations.CLIMBING,5);
            this.fptMap.put(ExtendedImageAnimations.RUNNINGREVERSE,2);

        }
    }
        
     public static class BashBlueFrontArmAnimationPack extends AnimationPack
     {
         public BashBlueFrontArmAnimationPack()
         {
             //idle 15fps
             ArrayList<Texture> idle = new ArrayList();
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-idle0.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-idle1.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-idle2.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-idle3.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-idle4.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-idle5.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-idle6.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-idle7.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-idle8.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-idle9.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-idle10.png"));
              this.animationSet.put(CoreAnimations.IDLE, idle);
              
              //melee 25fps
              ArrayList<Texture> melee = new ArrayList();
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee0.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee1.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee2.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee3.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee4.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee5.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee6.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee7.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee8.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee9.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee10.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee11.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee12.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee13.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee14.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-melee15.png"));
              this.animationSet.put(ExtendedImageAnimations.MELEEATTACK,melee);
              
              
              //Ranged 15fps
              ArrayList<Texture> attacking = new ArrayList();
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-shoot0.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-shoot1.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-shoot2.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-shoot3.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-shoot4.png"));             
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-shoot5.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-shoot6.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-shoot7.png")); 
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-shoot8.png"));              
              this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK,attacking);
              
              //Use 15fps
              ArrayList<Texture> use = new ArrayList(); 
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-cast0.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-cast1.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-cast2.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-cast3.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-cast4.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-cast5.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-cast6.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-front-cast7.png"));
              this.animationSet.put(ExtendedImageAnimations.SPELLATTACK,use);
              
            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.SPELLATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK,1);
             
              
              //========================
              // Build Skill Offset Map
              //========================
            
              this.positionOffsetMap.put(ExtendedImageAnimations.RANGEDATTACK, new SylverVector2f(25,10));
              this.positionOffsetMap.put(ExtendedImageAnimations.MELEEATTACK, new SylverVector2f(25,10));
            
         }
     }
     
     public static class BashBlueBackArmAnimationPack extends AnimationPack
     {
         public BashBlueBackArmAnimationPack()
         {
              //idle
              ArrayList<Texture> idle = new ArrayList();
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-idle0.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-idle1.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-idle2.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-idle3.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-idle4.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-idle5.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-idle6.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-idle7.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-idle8.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-idle9.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-idle10.png"));
              this.animationSet.put(CoreAnimations.IDLE, idle);
              
              //melee
              ArrayList<Texture> melee = new ArrayList();
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee0.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee1.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee2.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee3.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee4.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee5.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee6.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee7.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee8.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee9.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee10.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee11.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee12.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee13.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee14.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-melee15.png"));              
              this.animationSet.put(ExtendedImageAnimations.MELEEATTACK,melee);
              
              
              //Ranged
              ArrayList<Texture> attacking = new ArrayList();
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-shoot0.png"));
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-shoot1.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-shoot2.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-shoot3.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-shoot4.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-shoot5.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-shoot6.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-shoot7.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-shoot8.png")); 
              this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK,attacking);
              
              //Use
              ArrayList<Texture> use = new ArrayList();
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-cast0.png"));
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-cast1.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-cast2.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-cast3.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-cast4.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-cast5.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-cast6.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-blue-back-cast7.png")); 
              this.animationSet.put(ExtendedImageAnimations.SPELLATTACK,use);
              
              //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.SPELLATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK,1);
         }
     }
   
     
     //==============
    // Bash Green
    //==============
     public static class BashGreenBodyAnimationPack extends AnimationPack
     {
        public BashGreenBodyAnimationPack()
        {
            //=====================
            // Build Animation Map
            //=====================

            //idle  fps 21
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-idle10.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-idle11.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-idle12.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-idle13.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-idle14.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Running  fps 21
            ArrayList<Texture> running = new ArrayList();
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk0.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk1.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk2.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk3.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk4.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk5.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk6.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk7.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk8.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk9.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk10.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk11.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk12.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk13.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk14.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk15.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk16.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk17.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk18.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk19.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk20.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk21.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);
            
            //RunningReverse fps 21
            ArrayList<Texture> runningReverse = new ArrayList();
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk21.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk20.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk19.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk18.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk17.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk16.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk15.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk14.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk13.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk12.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk11.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk10.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk9.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk8.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk7.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk6.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk5.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk4.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk3.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk2.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk1.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-walk0.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNINGREVERSE, runningReverse);

            //Jumping fps 21
            ArrayList<Texture> jumping = new ArrayList();
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-jump0.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-jump1.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-jump2.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-jump3.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-jump4.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-jump5.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-jump6.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-jump7.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-jump8.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-jump9.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-jump10.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-jump11.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-jump12.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-jump13.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-jump14.png"));
            this.animationSet.put(ExtendedImageAnimations.JUMPING, jumping);    
            
            //climbing 21fps
            ArrayList<Texture> climb = new ArrayList();
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-climb0.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-climb1.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-climb2.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-climb3.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-climb4.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-climb5.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-climb6.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-body-climb7.png"));
            this.animationSet.put(ExtendedImageAnimations.CLIMBING,climb);

            //death
            ArrayList<Texture> dying = new ArrayList();
           // dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_green_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_green_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_green_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_green_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_green_righthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_green_leftleg.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_green_rightleg.png"));  
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_green_leftfoot.png"));  
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);


            //========================
            // Build Skill Offset Map
            //========================
            
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_0111_0100_1011_1011_0100_1011_0100),
                                                                                        Float.intBitsToFloat(0b0111_0111_1011_1011_0110_0100_0111_0111)));
                    
            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RUNNING,2);
            this.fptMap.put(ExtendedImageAnimations.CLIMBING,5);
            this.fptMap.put(ExtendedImageAnimations.RUNNINGREVERSE,2);

        }
    }
        
     public static class BashGreenFrontArmAnimationPack extends AnimationPack
     {
         public BashGreenFrontArmAnimationPack()
         {
             //idle 15fps
             ArrayList<Texture> idle = new ArrayList();
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-idle0.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-idle1.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-idle2.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-idle3.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-idle4.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-idle5.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-idle6.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-idle7.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-idle8.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-idle9.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-idle10.png"));
              this.animationSet.put(CoreAnimations.IDLE, idle);
              
              //melee 25fps
              ArrayList<Texture> melee = new ArrayList();
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee0.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee1.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee2.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee3.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee4.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee5.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee6.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee7.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee8.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee9.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee10.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee11.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee12.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee13.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee14.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-melee15.png"));
              this.animationSet.put(ExtendedImageAnimations.MELEEATTACK,melee);
              
              
              //Ranged 15fps
              ArrayList<Texture> attacking = new ArrayList();
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-shoot0.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-shoot1.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-shoot2.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-shoot3.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-shoot4.png"));             
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-shoot5.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-shoot6.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-shoot7.png")); 
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-shoot8.png"));              
              this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK,attacking);
              
              //Use 15fps
              ArrayList<Texture> use = new ArrayList(); 
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-cast0.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-cast1.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-cast2.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-cast3.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-cast4.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-cast5.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-cast6.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-front-cast7.png"));
              this.animationSet.put(ExtendedImageAnimations.SPELLATTACK,use);
              
            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.SPELLATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK,1);
             
              
              //========================
              // Build Skill Offset Map
              //========================
            
              this.positionOffsetMap.put(ExtendedImageAnimations.RANGEDATTACK, new SylverVector2f(25,10));
              this.positionOffsetMap.put(ExtendedImageAnimations.MELEEATTACK, new SylverVector2f(25,10));
            
         }
     }
     
     public static class BashGreenBackArmAnimationPack extends AnimationPack
     {
         public BashGreenBackArmAnimationPack()
         {
              //idle
              ArrayList<Texture> idle = new ArrayList();
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-idle0.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-idle1.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-idle2.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-idle3.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-idle4.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-idle5.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-idle6.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-idle7.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-idle8.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-idle9.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-idle10.png"));
              this.animationSet.put(CoreAnimations.IDLE, idle);
              
              //melee
              ArrayList<Texture> melee = new ArrayList();
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee0.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee1.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee2.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee3.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee4.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee5.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee6.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee7.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee8.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee9.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee10.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee11.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee12.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee13.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee14.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-melee15.png"));              
              this.animationSet.put(ExtendedImageAnimations.MELEEATTACK,melee);
              
              
              //Ranged
              ArrayList<Texture> attacking = new ArrayList();
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-shoot0.png"));
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-shoot1.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-shoot2.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-shoot3.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-shoot4.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-shoot5.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-shoot6.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-shoot7.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-shoot8.png")); 
              this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK,attacking);
              
              //Use
              ArrayList<Texture> use = new ArrayList();
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-cast0.png"));
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-cast1.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-cast2.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-cast3.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-cast4.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-cast5.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-cast6.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-green-back-cast7.png")); 
              this.animationSet.put(ExtendedImageAnimations.SPELLATTACK,use);
              
              //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.SPELLATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK,1);
         }
     }
   
        
     //==============
    // Bash Red
    //==============
     public static class BashRedBodyAnimationPack extends AnimationPack
     {
        public BashRedBodyAnimationPack()
        {
            //=====================
            // Build Animation Map
            //=====================

            //idle  fps 21
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-idle10.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-idle11.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-idle12.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-idle13.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-idle14.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Running  fps 21
            ArrayList<Texture> running = new ArrayList();
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk0.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk1.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk2.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk3.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk4.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk5.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk6.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk7.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk8.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk9.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk10.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk11.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk12.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk13.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk14.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk15.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk16.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk17.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk18.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk19.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk20.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk21.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);
            
            //RunningReverse fps 21
            ArrayList<Texture> runningReverse = new ArrayList();
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk21.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk20.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk19.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk18.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk17.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk16.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk15.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk14.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk13.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk12.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk11.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk10.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk9.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk8.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk7.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk6.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk5.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk4.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk3.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk2.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk1.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-walk0.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNINGREVERSE, runningReverse);

            //Jumping fps 21
            ArrayList<Texture> jumping = new ArrayList();
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-jump0.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-jump1.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-jump2.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-jump3.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-jump4.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-jump5.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-jump6.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-jump7.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-jump8.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-jump9.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-jump10.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-jump11.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-jump12.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-jump13.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-jump14.png"));
            this.animationSet.put(ExtendedImageAnimations.JUMPING, jumping);    
            
            //climbing 21fps
            ArrayList<Texture> climb = new ArrayList();
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-climb0.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-climb1.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-climb2.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-climb3.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-climb4.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-climb5.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-climb6.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-body-climb7.png"));
            this.animationSet.put(ExtendedImageAnimations.CLIMBING,climb);

            //death
            ArrayList<Texture> dying = new ArrayList();
          //  dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_red_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_red_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_red_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_red_rightarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_red_righthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_red_leftleg.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_red_rightleg.png"));  
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_red_leftfoot.png"));  
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);


            //========================
            // Build Skill Offset Map
            //========================
            
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_0111_0100_1011_1011_0100_1011_0100),
                                                                                        Float.intBitsToFloat(0b0111_0111_1011_1011_0110_0100_0111_0111)));
                    
            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RUNNING,2);
            this.fptMap.put(ExtendedImageAnimations.CLIMBING,5);
            this.fptMap.put(ExtendedImageAnimations.RUNNINGREVERSE,2);

        }
    }
        
     public static class BashRedFrontArmAnimationPack extends AnimationPack
     {
         public BashRedFrontArmAnimationPack()
         {
             //idle 15fps
             ArrayList<Texture> idle = new ArrayList();
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-idle0.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-idle1.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-idle2.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-idle3.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-idle4.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-idle5.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-idle6.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-idle7.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-idle8.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-idle9.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-idle10.png"));
              this.animationSet.put(CoreAnimations.IDLE, idle);
              
              //melee 25fps
              ArrayList<Texture> melee = new ArrayList();
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee0.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee1.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee2.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee3.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee4.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee5.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee6.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee7.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee8.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee9.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee10.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee11.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee12.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee13.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee14.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-melee15.png"));
              this.animationSet.put(ExtendedImageAnimations.MELEEATTACK,melee);
              
              
              //Ranged 15fps
              ArrayList<Texture> attacking = new ArrayList();
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-shoot0.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-shoot1.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-shoot2.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-shoot3.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-shoot4.png"));             
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-shoot5.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-shoot6.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-shoot7.png")); 
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-shoot8.png"));              
              this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK,attacking);
              
              //Use 15fps
              ArrayList<Texture> use = new ArrayList(); 
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-cast0.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-cast1.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-cast2.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-cast3.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-cast4.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-cast5.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-cast6.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-front-cast7.png"));
              this.animationSet.put(ExtendedImageAnimations.SPELLATTACK,use);
              
            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.SPELLATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK,1);
             
              
              //========================
              // Build Skill Offset Map
              //========================
            
              this.positionOffsetMap.put(ExtendedImageAnimations.RANGEDATTACK, new SylverVector2f(25,10));
              this.positionOffsetMap.put(ExtendedImageAnimations.MELEEATTACK, new SylverVector2f(25,10));
            
         }
     }
     
     public static class BashRedBackArmAnimationPack extends AnimationPack
     {
         public BashRedBackArmAnimationPack()
         {
              //idle
              ArrayList<Texture> idle = new ArrayList();
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-idle0.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-idle1.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-idle2.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-idle3.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-idle4.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-idle5.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-idle6.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-idle7.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-idle8.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-idle9.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-idle10.png"));
              this.animationSet.put(CoreAnimations.IDLE, idle);
              
              //melee
              ArrayList<Texture> melee = new ArrayList();
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee0.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee1.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee2.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee3.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee4.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee5.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee6.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee7.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee8.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee9.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee10.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee11.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee12.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee13.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee14.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-melee15.png"));              
              this.animationSet.put(ExtendedImageAnimations.MELEEATTACK,melee);
              
              
              //Ranged
              ArrayList<Texture> attacking = new ArrayList();
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-shoot0.png"));
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-shoot1.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-shoot2.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-shoot3.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-shoot4.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-shoot5.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-shoot6.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-shoot7.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-shoot8.png")); 
              this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK,attacking);
              
              //Use
              ArrayList<Texture> use = new ArrayList();
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-cast0.png"));
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-cast1.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-cast2.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-cast3.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-cast4.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-cast5.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-cast6.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-red-back-cast7.png")); 
              this.animationSet.put(ExtendedImageAnimations.SPELLATTACK,use);
              
              //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.SPELLATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK,1);
         }
     }
   
     
     //==============
    // Bash White
    //==============
     public static class BashWhiteBodyAnimationPack extends AnimationPack
     {
        public BashWhiteBodyAnimationPack()
        {
            //=====================
            // Build Animation Map
            //=====================

            //idle  fps 21
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-idle10.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-idle11.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-idle12.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-idle13.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-idle14.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Running  fps 21
            ArrayList<Texture> running = new ArrayList();
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk0.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk1.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk2.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk3.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk4.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk5.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk6.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk7.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk8.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk9.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk10.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk11.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk12.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk13.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk14.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk15.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk16.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk17.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk18.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk19.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk20.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk21.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);
            
            //RunningReverse fps 21
            ArrayList<Texture> runningReverse = new ArrayList();
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk21.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk20.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk19.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk18.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk17.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk16.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk15.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk14.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk13.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk12.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk11.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk10.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk9.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk8.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk7.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk6.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk5.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk4.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk3.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk2.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk1.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-walk0.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNINGREVERSE, runningReverse);

            //Jumping fps 21
            ArrayList<Texture> jumping = new ArrayList();
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-jump0.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-jump1.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-jump2.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-jump3.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-jump4.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-jump5.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-jump6.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-jump7.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-jump8.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-jump9.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-jump10.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-jump11.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-jump12.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-jump13.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-jump14.png"));
            this.animationSet.put(ExtendedImageAnimations.JUMPING, jumping);    
            
            //climbing 21fps
            ArrayList<Texture> climb = new ArrayList();
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-climb0.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-climb1.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-climb2.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-climb3.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-climb4.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-climb5.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-climb6.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-body-climb7.png"));
            this.animationSet.put(ExtendedImageAnimations.CLIMBING,climb);

            //death
            ArrayList<Texture> dying = new ArrayList();
           // dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_white_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_white_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_white_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_white_rightarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_white_righthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_white_leftleg.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_white_rightleg.png"));  
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_white_leftfoot.png"));  
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);


            //========================
            // Build Skill Offset Map
            //========================
            
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_0111_0100_1011_1011_0100_1011_0100),
                                                                                        Float.intBitsToFloat(0b0111_0111_1011_1011_0110_0100_0111_0111)));
                    
            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RUNNING,2);
            this.fptMap.put(ExtendedImageAnimations.CLIMBING,5);
            this.fptMap.put(ExtendedImageAnimations.RUNNINGREVERSE,2);

        }
    }
        
     public static class BashWhiteFrontArmAnimationPack extends AnimationPack
     {
         public BashWhiteFrontArmAnimationPack()
         {
             //idle 15fps
             ArrayList<Texture> idle = new ArrayList();
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-idle0.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-idle1.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-idle2.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-idle3.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-idle4.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-idle5.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-idle6.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-idle7.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-idle8.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-idle9.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-idle10.png"));
              this.animationSet.put(CoreAnimations.IDLE, idle);
              
              //melee 25fps
              ArrayList<Texture> melee = new ArrayList();
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee0.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee1.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee2.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee3.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee4.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee5.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee6.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee7.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee8.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee9.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee10.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee11.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee12.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee13.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee14.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-melee15.png"));
              this.animationSet.put(ExtendedImageAnimations.MELEEATTACK,melee);
              
              
              //Ranged 15fps
              ArrayList<Texture> attacking = new ArrayList();
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-shoot0.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-shoot1.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-shoot2.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-shoot3.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-shoot4.png"));             
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-shoot5.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-shoot6.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-shoot7.png")); 
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-shoot8.png"));              
              this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK,attacking);
              
              //Use 15fps
              ArrayList<Texture> use = new ArrayList(); 
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-cast0.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-cast1.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-cast2.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-cast3.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-cast4.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-cast5.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-cast6.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-front-cast7.png"));
              this.animationSet.put(ExtendedImageAnimations.SPELLATTACK,use);
              
            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.SPELLATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK,1);
             
              
              //========================
              // Build Skill Offset Map
              //========================
            
              this.positionOffsetMap.put(ExtendedImageAnimations.RANGEDATTACK, new SylverVector2f(25,10));
              this.positionOffsetMap.put(ExtendedImageAnimations.MELEEATTACK, new SylverVector2f(25,10));
            
         }
     }
     
     public static class BashWhiteBackArmAnimationPack extends AnimationPack
     {
         public BashWhiteBackArmAnimationPack()
         {
              //idle
              ArrayList<Texture> idle = new ArrayList();
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-idle0.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-idle1.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-idle2.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-idle3.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-idle4.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-idle5.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-idle6.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-idle7.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-idle8.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-idle9.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-idle10.png"));
              this.animationSet.put(CoreAnimations.IDLE, idle);
              
              //melee
              ArrayList<Texture> melee = new ArrayList();
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee0.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee1.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee2.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee3.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee4.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee5.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee6.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee7.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee8.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee9.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee10.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee11.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee12.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee13.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee14.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-melee15.png"));              
              this.animationSet.put(ExtendedImageAnimations.MELEEATTACK,melee);
              
              
              //Ranged
              ArrayList<Texture> attacking = new ArrayList();
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-shoot0.png"));
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-shoot1.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-shoot2.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-shoot3.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-shoot4.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-shoot5.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-shoot6.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-shoot7.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-shoot8.png")); 
              this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK,attacking);
              
              //Use
              ArrayList<Texture> use = new ArrayList();
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-cast0.png"));
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-cast1.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-cast2.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-cast3.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-cast4.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-cast5.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-cast6.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-white-back-cast7.png")); 
              this.animationSet.put(ExtendedImageAnimations.SPELLATTACK,use);
              
              //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.SPELLATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK,1);
         }
     }
    
     
     //==============
    // Bash Yellow
    //==============
     public static class BashYellowBodyAnimationPack extends AnimationPack
     {
        public BashYellowBodyAnimationPack()
        {
            //=====================
            // Build Animation Map
            //=====================

            //idle  fps 21
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-idle0.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-idle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-idle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-idle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-idle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-idle5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-idle6.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-idle7.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-idle8.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-idle9.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-idle10.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-idle11.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-idle12.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-idle13.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-idle14.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Running  fps 21
            ArrayList<Texture> running = new ArrayList();
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk0.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk1.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk2.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk3.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk4.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk5.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk6.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk7.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk8.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk9.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk10.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk11.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk12.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk13.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk14.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk15.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk16.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk17.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk18.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk19.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk20.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk21.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);
            
            //RunningReverse fps 21
            ArrayList<Texture> runningReverse = new ArrayList();
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk21.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk20.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk19.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk18.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk17.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk16.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk15.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk14.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk13.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk12.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk11.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk10.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk9.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk8.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk7.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk6.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk5.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk4.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk3.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk2.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk1.png"));
                runningReverse.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-walk0.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNINGREVERSE, runningReverse);

            //Jumping fps 21
            ArrayList<Texture> jumping = new ArrayList();
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-jump0.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-jump1.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-jump2.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-jump3.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-jump4.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-jump5.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-jump6.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-jump7.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-jump8.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-jump9.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-jump10.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-jump11.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-jump12.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-jump13.png"));
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-jump14.png"));
            this.animationSet.put(ExtendedImageAnimations.JUMPING, jumping);    
            
            //climbing 21fps
            ArrayList<Texture> climb = new ArrayList();
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-climb0.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-climb1.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-climb2.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-climb3.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-climb4.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-climb5.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-climb6.png"));
                climb.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-body-climb7.png"));
            this.animationSet.put(ExtendedImageAnimations.CLIMBING,climb);

            //death
            ArrayList<Texture> dying = new ArrayList();
           // dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_yellow_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_yellow_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_yellow_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_yellow_rightarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_yellow_righthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_yellow_leftleg.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_yellow_rightleg.png"));  
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash_yellow_leftfoot.png"));  
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);


            //========================
            // Build Skill Offset Map
            //========================
            
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_0111_0100_1011_1011_0100_1011_0100),
                                                                                        Float.intBitsToFloat(0b0111_0111_1011_1011_0110_0100_0111_0111)));
                    
            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RUNNING,2);
            this.fptMap.put(ExtendedImageAnimations.CLIMBING,5);
            this.fptMap.put(ExtendedImageAnimations.RUNNINGREVERSE,2);

        }
    }
        
     public static class BashYellowFrontArmAnimationPack extends AnimationPack
     {
         public BashYellowFrontArmAnimationPack()
         {
             //idle 15fps
             ArrayList<Texture> idle = new ArrayList();
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-idle0.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-idle1.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-idle2.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-idle3.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-idle4.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-idle5.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-idle6.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-idle7.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-idle8.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-idle9.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-idle10.png"));
              this.animationSet.put(CoreAnimations.IDLE, idle);
              
              //melee 25fps
              ArrayList<Texture> melee = new ArrayList();
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee0.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee1.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee2.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee3.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee4.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee5.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee6.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee7.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee8.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee9.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee10.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee11.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee12.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee13.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee14.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-melee15.png"));
              this.animationSet.put(ExtendedImageAnimations.MELEEATTACK,melee);
              
              
              //Ranged 15fps
              ArrayList<Texture> attacking = new ArrayList();
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-shoot0.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-shoot1.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-shoot2.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-shoot3.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-shoot4.png"));             
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-shoot5.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-shoot6.png"));
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-shoot7.png")); 
              attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-shoot8.png"));              
              this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK,attacking);
              
              //Use 15fps
              ArrayList<Texture> use = new ArrayList(); 
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-cast0.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-cast1.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-cast2.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-cast3.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-cast4.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-cast5.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-cast6.png"));
              use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-front-cast7.png"));
              this.animationSet.put(ExtendedImageAnimations.SPELLATTACK,use);
              
            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.SPELLATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK,1);
             
              
              //========================
              // Build Skill Offset Map
              //========================
            
              this.positionOffsetMap.put(ExtendedImageAnimations.RANGEDATTACK, new SylverVector2f(25,10));
              this.positionOffsetMap.put(ExtendedImageAnimations.MELEEATTACK, new SylverVector2f(25,10));
            
         }
     }
     
     public static class BashYellowBackArmAnimationPack extends AnimationPack
     {
         public BashYellowBackArmAnimationPack()
         {
              //idle
              ArrayList<Texture> idle = new ArrayList();
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-idle0.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-idle1.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-idle2.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-idle3.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-idle4.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-idle5.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-idle6.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-idle7.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-idle8.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-idle9.png"));
              idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-idle10.png"));
              this.animationSet.put(CoreAnimations.IDLE, idle);
              
              //melee
              ArrayList<Texture> melee = new ArrayList();
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee0.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee1.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee2.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee3.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee4.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee5.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee6.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee7.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee8.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee9.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee10.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee11.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee12.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee13.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee14.png"));
              melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-melee15.png"));              
              this.animationSet.put(ExtendedImageAnimations.MELEEATTACK,melee);
              
              
              //Ranged
              ArrayList<Texture> attacking = new ArrayList();
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-shoot0.png"));
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-shoot1.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-shoot2.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-shoot3.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-shoot4.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-shoot5.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-shoot6.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-shoot7.png")); 
               attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-shoot8.png")); 
              this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK,attacking);
              
              //Use
              ArrayList<Texture> use = new ArrayList();
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-cast0.png"));
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-cast1.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-cast2.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-cast3.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-cast4.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-cast5.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-cast6.png")); 
               use.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("bash-yellow-back-cast7.png")); 
              this.animationSet.put(ExtendedImageAnimations.SPELLATTACK,use);
              
              //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,10);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.SPELLATTACK,3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK,1);
         }
     }
   
     
     
            
    
    
 }
