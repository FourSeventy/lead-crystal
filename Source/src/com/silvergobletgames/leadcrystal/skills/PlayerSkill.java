package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.AnimationPack;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.graphics.MultiImageEffect;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.UUID;
import net.phys2d.math.Vector2f;

/**
 *
 * @author Mike
 */
public abstract class PlayerSkill extends Skill
{
    
    //============
    // Constructor
    //============
    public PlayerSkill(SkillID skillID, SkillType type, AnimationPack.ImageAnimation animation, int cooldown, int range)
    {
        super(skillID,type,animation,cooldown,range);
    }
    
    
    //================
    // Helper Methods
    //================
    protected ImageEffect getDamageBrightnessEffect()
    {
        Float[] points = {1f,2f,1f};
        int[] durations = {10,5};
        ImageEffect brightnessEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points,durations);
        
        return brightnessEffect;
    }
    
    protected TargetingData getTargetingData(SylverVector2f origin)
    {
        PlayerEntity player = (PlayerEntity) user;
        
        //Get target X and Y
        float targetX = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationX;
        float targetY = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationY;
        
        //Get user X and Y
        float userX = origin.x;
        float userY = origin.y;
        
        //get vector to target
        SylverVector2f vectorToTarget = new SylverVector2f(targetX - userX, targetY - userY);
        vectorToTarget.normalise();
        
        
        float theta = (float)Math.acos(vectorToTarget.dot(new SylverVector2f(1,0)));
        if(targetY < userY)
            theta = (float)(2* Math.PI - theta);
        
        return new TargetingData(vectorToTarget,theta);
    }
    
    protected Image getMuzzleFlash(TargetingData targetingData, SylverVector2f origin)
    {
        Image flash = new Image("flash.png");
        flash.setScale(.14f);
        flash.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 7, 1, 1));
        flash.setRotationPoint(0, .5f);
        flash.setAnchor(Anchorable.Anchor.LEFTCENTER);
        flash.setColor(new Color(3f,2f,2f,.45f));
        flash.setAngle((float)(targetingData.theta * (180f/Math.PI))); 
        flash.setPositionAnchored(origin.x - targetingData.vectorToTarget.x * 15, origin.y - targetingData.vectorToTarget.y * 15); 
        
        return flash;
       
    }
    
    protected AbstractParticleEmitter getMuzzleEffect(SylverVector2f origin)
    {
        AbstractParticleEmitter smokeEmitter = new LeadCrystalParticleEmitters.SmokeEmitter();
        smokeEmitter.setPosition(origin.x, origin.y);
        smokeEmitter.setDuration(8);
        smokeEmitter.setParticlesPerFrame(10);
        
        return smokeEmitter;
    }

    
    
    public static class TargetingData
    {
        
        public SylverVector2f vectorToTarget;
        public float theta;
        
        protected TargetingData(SylverVector2f vec, float theta)
        {
            this.vectorToTarget = vec;
            this.theta = theta;
        }
    }
    
}
