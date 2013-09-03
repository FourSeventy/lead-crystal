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

    
    public static class PlayerAnimationPack extends AnimationPack
    {
        public PlayerAnimationPack()
        {
            //=====================
            // Build Animation Map
            //=====================

            //idle
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerIdle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerIdle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerIdle3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerIdle4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerIdle6.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Running
            ArrayList<Texture> running = new ArrayList();
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerWalking1.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerWalking2.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerWalking3.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerWalking4.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);

            //Jumping
            ArrayList<Texture> jumping = new ArrayList();
                jumping.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerJumping1.png"));
            this.animationSet.put(ExtendedImageAnimations.JUMPING, jumping);            

            //death
            ArrayList<Texture> dying = new ArrayList();
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_rightarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_righthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_leftleg.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_rightleg.png"));  
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_leftfoot.png"));  
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);

            //Ranged
            ArrayList<Texture> attacking = new ArrayList();
            // attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerRanged1.png"));
            //  attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerRanged2.png"));
                attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerRanged3.png"));
                attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerRanged4.png"));
                attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerRanged5.png"));
                attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerRanged6.png"));
            //  attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerRanged7.png"));
            // attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerRanged8.png"));
            //  attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerRanged9.png"));
            this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK,attacking);

            //Ranged
            ArrayList<Texture> melee = new ArrayList();
                melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerMelee1.png"));
                melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerMelee2.png"));
                melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerMelee3.png"));
                melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerMelee4.png"));
                melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerMelee5.png"));
                melee.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("playerMelee6.png"));
            this.animationSet.put(ExtendedImageAnimations.MELEEATTACK,melee);
            
            
            //grave
            ArrayList<Texture> grave = new ArrayList();
                grave.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("gravestone.png"));
            this.animationSet.put(ExtendedImageAnimations.SPAWN,grave);


            //=====================
            // Build Delay Map
            //=====================        
            this.timingMap.put(ExtendedImageAnimations.MELEEATTACK, 0);
            this.timingMap.put(ExtendedImageAnimations.RANGEDATTACK, 0);

            //========================
            // Build Skill Offset Map
            //========================
            this.positionOffsetMap.put(ExtendedImageAnimations.RANGEDATTACK, new SylverVector2f(25,10));
            this.positionOffsetMap.put(ExtendedImageAnimations.MELEEATTACK, new SylverVector2f(25,10));
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_0111_0100_1011_1011_0100_1011_0100),
                                                                                        Float.intBitsToFloat(0b0111_0111_1011_1011_0110_0100_0111_0111)));
                    
                

            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE,20);
            this.fptMap.put(ExtendedImageAnimations.RANGEDATTACK,5);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK,5);

        }
    }
    
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
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_idle0.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_idle1.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_idle2.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_idle3.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_idle4.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_idle5.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_idle6.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_idle7.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_idle8.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_idle9.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_idle10.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Running
            ArrayList<Texture> running = new ArrayList();
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking0.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking1.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking2.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking3.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking4.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking5.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking6.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking7.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking8.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking9.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking10.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking11.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking12.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking13.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking14.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_walking15.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);

            //Ranged Attack
            ArrayList<Texture> ranged = new ArrayList();
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_ranged0.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_ranged1.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_ranged2.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_ranged3.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_ranged4.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_ranged5.png"));                              
            this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK ,ranged); 

            //death
            ArrayList<Texture> dying = new ArrayList();
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_rightarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_righthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_leftleg.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_rightleg.png"));  
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
             this.fptMap.put(ExtendedImageAnimations.RUNNING, 3);
              

        }
    }
    
    public static class Scout2AnimationPack extends AnimationPack
    {
        public Scout2AnimationPack()
        {
            //Idle
            ArrayList<Texture> idle = new ArrayList();
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_idle0.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_idle1.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_idle2.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_idle3.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_idle4.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_idle5.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_idle6.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_idle7.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_idle8.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_idle9.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_idle10.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Running
            ArrayList<Texture> running = new ArrayList();
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking0.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking1.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking2.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking3.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking4.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking5.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking6.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking7.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking8.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking9.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking10.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking11.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking12.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking13.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking14.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_walking15.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);

            //Ranged Attack
            ArrayList<Texture> ranged = new ArrayList();
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_ranged0.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_ranged1.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_ranged2.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_ranged3.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_ranged4.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_ranged5.png"));                              
            this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK ,ranged); 

            //death
            ArrayList<Texture> dying = new ArrayList();
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_rightarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_righthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_leftleg.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout2_rightleg.png"));  
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
             this.fptMap.put(ExtendedImageAnimations.RUNNING, 3);
              

        }
    }
    
    public static class Scout3AnimationPack extends AnimationPack
    {
        public Scout3AnimationPack()
        {
            //Idle
            ArrayList<Texture> idle = new ArrayList();
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_idle0.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_idle1.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_idle2.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_idle3.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_idle4.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_idle5.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_idle6.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_idle7.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_idle8.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_idle9.png"));
            idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_idle10.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Running
            ArrayList<Texture> running = new ArrayList();
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking0.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking1.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking2.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking3.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking4.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking5.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking6.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking7.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking8.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking9.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking10.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking11.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking12.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking13.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking14.png"));
            running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_walking15.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);

            //Ranged Attack
            ArrayList<Texture> ranged = new ArrayList();
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_ranged0.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_ranged1.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_ranged2.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_ranged3.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_ranged4.png"));
            ranged.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_ranged5.png"));                              
            this.animationSet.put(ExtendedImageAnimations.RANGEDATTACK ,ranged); 

            //death
            ArrayList<Texture> dying = new ArrayList();
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_rightarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_righthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_leftleg.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout3_rightleg.png"));  
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
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_blutt.png"));
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_legs.png"));
                dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("flier_wing.png"));
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);
            
            
             //=====================
            // Build Delay Map
            //=====================        
            this.timingMap.put(ExtendedImageAnimations.RANGEDATTACK, 50);

            //========================
            // Build Skill Offset Map
            //========================
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_1101_1010_0111_0111_0111_0000_0000),
                                                                                        Float.intBitsToFloat(0b0111_1010_1000_0101_0111_0000_0000_0000)));
                  

            //================
            // Build FPT Map
            //================
            this.fptMap.put(CoreAnimations.IDLE, 5);
            this.fptMap.put(ExtendedImageAnimations.RUNNING, 5);
            this.fptMap.put(ExtendedImageAnimations.SPAWN, 3);
           // this.fptMap.put(ExtendedImageAnimations.MELEEATTACK, 5);
            
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
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0011_1101_1010_0111_0111_0111_0000_0000),
                                                                                        Float.intBitsToFloat(0b0111_1010_1000_0101_0111_0000_0000_0000)));
                  

            //================
            // Build FPT Map
            //================
            this.fptMap.put(ExtendedImageAnimations.RUNNING, 5);
            this.fptMap.put(ExtendedImageAnimations.SPAWN, 3);
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK, 5);
            this.fptMap.put(CoreAnimations.IDLE, 7);
        }
    }
    
    
    
    
    
    
    
    public static class TikiGuyAnimationPack extends AnimationPack
    {
        public TikiGuyAnimationPack()
        {
             //Idle
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiIdle1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiIdle2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiIdle3.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle);

            //Running
            ArrayList<Texture> running = new ArrayList();
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiRunning1.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiRunning2.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiRunning3.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiRunning4.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);

            //Melee Attack
            ArrayList<Texture> attacking = new ArrayList();
                    attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiMelee1.png"));
                    attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiMelee2.png"));
                    attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiMelee3.png"));
                    attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiMelee4.png"));
                    attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiMelee5.png"));
                    attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiMelee6.png"));
                    attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiMelee7.png"));
                    attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiMelee8.png"));
                    attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiMelee9.png"));
                    attacking.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiMelee10.png"));               
            this.animationSet.put(ExtendedImageAnimations.MELEEATTACK ,attacking); 
            
            //spawning
            ArrayList<Texture> spawning = new ArrayList();
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiDying7.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiDying6.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiDying5.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiDying4.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiDying3.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiDying2.png"));
                    spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("TikiDying1.png"));                              
            this.animationSet.put(ExtendedImageAnimations.SPAWN,spawning);

            //death
            ArrayList<Texture> dying = new ArrayList();
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_rightarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_lefthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_righthand.png"));
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);

            //=====================
            // Build Delay Map
            //=====================        
            this.timingMap.put(ExtendedImageAnimations.MELEEATTACK, 15);

            //========================
            // Build Skill Offset Map
            //========================
            this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0000_0000_0000_0000_0000_0000_0000_0000),
                                                                                        Float.intBitsToFloat(0b1110_0000_0000_0000_0000_0000_0000_0000)));
                  

            //================
            // Build FPT Map
            //================
            this.fptMap.put(ExtendedImageAnimations.MELEEATTACK, 3);
        }
    }
    
    
    
    
    
    
    
    
    
    public static class BleedAnimationPack extends AnimationPack
    {
        public BleedAnimationPack()
        {
            ArrayList<Texture> def = new ArrayList();
                    def.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("blood1.png"));
                    def.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("blood2.png"));
                    def.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("blood3.png"));
                    def.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("blood4.png"));
                this.animationSet.put(CoreAnimations.IDLE, def);
        }
    }
    
    public static class ExclaimAnimationPack extends AnimationPack
    {
        public ExclaimAnimationPack()
        {
            ArrayList<Texture> def = new ArrayList();
                    def.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("exclaimbubble01.png"));
                    def.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("exclaimbubble02.png"));
                    def.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("exclaimbubble03.png"));
                    def.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("exclaimbubble04.png"));
                    def.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("exclaimbubble05.png"));
                    def.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("exclaimbubble06.png"));
                    def.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("exclaimbubble07.png"));
                this.animationSet.put(CoreAnimations.IDLE, def);
        }
    }
    
    public static class BatAnimationPack extends AnimationPack
    {
        public BatAnimationPack()
        {
            //Idle
            ArrayList<Texture> idle = new ArrayList();
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batIdle-1.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batIdle-2.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batIdle-3.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batIdle-4.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batIdle-5.png"));
                idle.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batIdle-6.png"));
            this.animationSet.put(CoreAnimations.IDLE, idle); 

            //Running
            ArrayList<Texture> running = new ArrayList();
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batIdle-1.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batIdle-2.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batIdle-3.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batIdle-4.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batIdle-5.png"));
                running.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batIdle-6.png"));
            this.animationSet.put(ExtendedImageAnimations.RUNNING, running);
            
            //death
            ArrayList<Texture> dying = new ArrayList();
                       dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_head.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_torso.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_leftarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_rightarm.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_lefthand.png"));
            dying.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("scout1_righthand.png"));
            this.animationSet.put(ExtendedImageAnimations.DEATH, dying);
             
             ArrayList<Texture> spawning = new ArrayList();
                 spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batDeath-4.png"));
                 spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batDeath-3.png"));
                 spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batDeath-2.png"));
                 spawning.add(Game.getInstance().getAssetManager().getTextureLoader().getTexture("batDeath-1.png"));
             this.animationSet.put(ExtendedImageAnimations.SPAWN,spawning);
             
             this.positionOffsetMap.put(ExtendedImageAnimations.DEATH,new SylverVector2f(Float.intBitsToFloat(0b0000_0000_0000_0000_0000_0000_0000_0000),
                                                                                        Float.intBitsToFloat(0b1110_0000_0000_0000_0000_0000_0000_0000)));
                  

        }
    }
    
    
    
    
            
    
    
 }
