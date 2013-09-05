package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Circle;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.DotEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.IceEmitter;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.UUID;

/**
 * 
 * Name: Guard
 * Cost:
 * Cooldown: 30 seconds
 * Description: While active, you take 50% damage.  For the first second that Guard is active, you take no damage.
 */
public class PlayerWard extends Skill{
    
    public PlayerWard()
    {
        //super constructor
        super(SkillID.PlayerWard,SkillType.OFFENSIVE,ExtendedImageAnimations.SPELLATTACK,1800, Integer.MAX_VALUE);
        
        //set the skillID and the name
        this.icon = new Image("icetrapIcon.jpg");
        this.skillName = "Ice Trap";
        this.skillDescription = "Places a ward on the ground damaging and slowing all enemies in its radius.";
        
        this.unlockCost = 1;

    }
    
    public void use(Damage damage, SylverVector2f origin) 
    {
        PlayerEntity player = (PlayerEntity) user;
        
         //Get target X and Y
        float targetX = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationX;
        float targetY = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationY;
        
        //Get user X and Y
        float userX = origin.x;
        float userY = origin.y;
        
        //get vector to target
        Vector2f vectorToTarget = new Vector2f(targetX - userX, targetY - userY);
        vectorToTarget.normalise();
        
        //place ice trap hitbox in the world
        HealingWardHitbox hitbox = new HealingWardHitbox(user);
        hitbox.setPosition(user.getPosition().x + 150 *vectorToTarget.getX(), user.getPosition().y + 150 * vectorToTarget.getY());
        hitbox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 900, 1, 1));
        user.getOwningScene().add(hitbox,Layer.MAIN);
        
        //add image
        Image img = new Image("wardSprite.png");
        img.setScale(.35f);
        img.setAnchor(Anchorable.Anchor.CENTER);
        img.setPositionAnchored(hitbox.getPosition().x, hitbox.getPosition().y);
        img.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 900, 0, 0));
        user.getOwningScene().add(img, Layer.ATTACHED_FG);
        
        //add its praticle effects
        ParticleEmitter emitter = new IceEmitter();
        emitter.setDuration(900);
        hitbox.addEmitter(emitter);
        
       
    }
    
    private class HealingWardHitbox extends HitBox
    {
        
        public HealingWardHitbox(CombatEntity user)
        {
            super(new Damage(Damage.DamageType.NODAMAGE, 0), new Body(new Circle(200), 1), new Image("blank.png"),user); 
            
            this.body.setGravityEffected(false);
            this.body.removeExcludedBody(user.getBody());
            this.getBody().setBitmask(BitMasks.NO_COLLISION.value);
            this.getBody().setOverlapMask(OverlapMasks.OVERLAP_ALL.value);
        }
        
        public void collidedWith(Entity other, CollisionEvent event)
        {
            //if we collided with a player add a heal dot
            if(other instanceof NonPlayerEntity)
            {
                //apply slow
                StateEffect slow = new StateEffect(StateEffect.StateEffectType.SLOW, 10, .75f, true);
                slow.setInfinite();
                ((CombatEntity)other).getCombatData().addCombatEffect("iceSlow", slow);
                
                //apply image effect
                ((CombatEntity)other).getImage().setColor(new Color(.5f,.5f,2)); 
                
                //apply damage dot
                Damage healDamage = new Damage(Damage.DamageType.FROST, 1);
                DotEffect heal = new DotEffect(1, 30, healDamage);
                heal.setInfinite();                
                ((CombatEntity)other).getCombatData().addCombatEffect("iceDot", heal);
            }
            
        }
        
        public void separatedFrom(Entity other, CollisionEvent event)
        {
            if(other instanceof NonPlayerEntity)
            {
                //remove infinite effects 
                ((CombatEntity)other).getCombatData().removeCombatEffect("iceDot");
                ((CombatEntity)other).getCombatData().removeCombatEffect("iceSlow");
                ((CombatEntity)other).getImage().setColor(new Color(1,1,1,1));
                
                //apply finite effects
                
                //apply slow
                StateEffect slow = new StateEffect(StateEffect.StateEffectType.SLOW, 180, .75f, true);
                ((CombatEntity)other).getCombatData().addCombatEffect("iceSlow", slow);
                
                //apply image effect
                ((CombatEntity)other).getImage().addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.COLOR, 180, new Color(.5f,.5f,2), new Color(1f,1f,1f)));
                
                //apply damage dot
                Damage healDamage = new Damage(Damage.DamageType.FROST, 1);
                DotEffect heal = new DotEffect(180, 30, healDamage);             
                ((CombatEntity)other).getCombatData().addCombatEffect("iceDot", heal);
            }
        }
        

    }
     
    
     

}
