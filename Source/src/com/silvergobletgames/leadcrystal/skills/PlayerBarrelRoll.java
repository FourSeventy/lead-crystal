package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.combat.CombatEffect;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.ImageEffect.ImageEffectType;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Box;
import com.silvergobletgames.leadcrystal.combat.Damage.DamageType;
import com.silvergobletgames.leadcrystal.combat.DotEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.combat.StateEffect.StateEffectType;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.GroundFireEmitter1;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.GroundFireSmokeEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.LaserBitsEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.SmokeEmitter;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Circle;



/**
 * Description: Shoots a laser that damages enemies when struck.
 * Damage: 5-10
 * Cooldown: .5 seconds
 * @author Mike
 */
public class PlayerBarrelRoll extends PlayerSkill{
        
    
    public PlayerBarrelRoll()
    {
        super(SkillID.PlayerBarrelRoll,SkillType.OFFENSIVE,ExtendedImageAnimations.SPELLATTACK,600,Integer.MAX_VALUE);
        
        //set the skillID and the name
        this.icon = new Image("barrelRollIcon.png");
        this.skillName = "Barrel Roll";
        this.skillDescription = "Rolls an explosive barrel that explodes into shrapnal and leaves damaging fire on the ground.";
        this.skillDamageDescription = "Damage: 15-17";
        this.unlockCost = 2;
    }
    

    public void use(Damage damage, SylverVector2f origin)
    {
        
        Random r = SylverRandom.random;
        
        //get targeting data
        TargetingData targetingData = this.getTargetingData(origin);
        SylverVector2f vectorToTarget = targetingData.vectorToTarget;
        float theta = targetingData.theta;
                        
        //set damage
        int min = 15; 
        int max = 17;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.NODAMAGE);       
        damage.addImageEffect(this.getDamageBrightnessEffect());
        
        //build body of the barrel
        Body body = new Body(new Circle(32), 4);
        Image img = new Image("barrel.png");
        img.setAnchor(Anchorable.Anchor.CENTER);
        img.setDimensions(65, 65);
        img.setColor(new Color(1f,1f,1f,1f)); 
        BarrelHitBox barrel = new BarrelHitBox(damage, body, img, user); 
        
           
        //calculate force for the bullet
        float xforce = 4000*vectorToTarget.x;
        float yforce = 4000*vectorToTarget.y;
        
        //Dispense barrel into the world
        barrel.setPosition(origin.x + vectorToTarget.x * 25, origin.y + vectorToTarget.y * 25);
        barrel.getBody().addForce(new Vector2f(xforce ,yforce));
        barrel.getBody().setRotation((float)theta);
        barrel.getImage().setAngle((float)(theta * (180f/Math.PI))); 
        user.getOwningScene().add(barrel,Layer.MAIN);      
        
 
        //play sound
        Sound sound = Sound.locationSound("buffered/jump.ogg", user.getPosition().x, user.getPosition().y, false, .8f, 1.4f);
        user.getOwningScene().add(sound);


    }
    
    public static class BarrelHitBox extends HitBox
    {

        public boolean landed= false;
        
         public BarrelHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
            
            this.getBody().setGravityEffected(true);
            this.getBody().setRotatable(true);
            
         }
         
         private void explode()
         {
             
             //explosion images
             Image img = new Image("flashParticle.png");  
             img.setDimensions(300, 300);
             img.setColor(new Color(3,3,1,1));
             img.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.SCALE, 60, 1, 0f));
             img.setHorizontalFlip(SylverRandom.random.nextBoolean());
             img.setVerticalFlip(SylverRandom.random.nextBoolean());

             String particleImageToUse = SylverRandom.random.nextBoolean()?"flashParticle2.png":"flashParticle3.png";
             Image img2 = new Image(particleImageToUse);  
             img2.setDimensions(300, 300);
             img2.setColor(new Color(3,3,1,1));
             img2.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.SCALE, 60, 1, 0f));
             img2.setHorizontalFlip(SylverRandom.random.nextBoolean());
             img2.setVerticalFlip(SylverRandom.random.nextBoolean()); 
             Overlay ehhovhh = new Overlay(img2);
             img.addOverlay(ehhovhh); 
             
             
             Body beh = new StaticBody(new Circle(200));
             beh.setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);
             beh.setBitmask(Entity.BitMasks.NO_COLLISION.value);
             damage.setType(Damage.DamageType.BURN);   

             //explosion hitbox
             HitBox explosionHitbox = new HitBox(damage, beh, img, sourceEntity);
             explosionHitbox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 10, 0, 0));               
             explosionHitbox.setPosition(this.getPosition().x, this.getPosition().y);
             LeadCrystalParticleEmitters.BombExplosionEmitter emitter = new LeadCrystalParticleEmitters.BombExplosionEmitter();
             emitter.setDuration(15);
             AbstractParticleEmitter fire1 = new LeadCrystalParticleEmitters.GroundFireEmitter1(); 
             fire1.setParticlesPerFrame(.250f);
             fire1.setDuration(360);
             explosionHitbox.addEmitter(fire1);
             explosionHitbox.addEmitter(emitter);
             this.getOwningScene().add(explosionHitbox, Layer.MAIN); 
                
             //shoot out debris
             for(int i = 0; i < SylverRandom.random.nextInt(2) + 2; i ++)
             {
                 this.shootdebris();
             }
                
             //lay down damaging fire
             this.placeFire(this.getPosition().x - 100, this.getPosition().y);
             this.placeFire(this.getPosition().x - 50, this.getPosition().y);
             this.placeFire(this.getPosition().x, this.getPosition().y);
             this.placeFire(this.getPosition().x + 50, this.getPosition().y);            
             this.placeFire(this.getPosition().x + 100, this.getPosition().y);
             
             Sound sound = Sound.locationSound("buffered/explosion.ogg", this.getPosition().x, this.getPosition().y, false, 1f, 1f);
                this.getOwningScene().add(sound);
                
             //remove this hitbox from the world
            this.getBody().setVelocity(new Vector2f(0,0));
            this.removeFromOwningScene();
         }
         
         private void placeFire(float x, float y)
         {
             //lay down damaging fire
             Body body = new Body(new Box(50,20), 1);
             Image image = new Image("blank.png"); 
             BarrelFireHitbox fireHitbox = new BarrelFireHitbox(body,image,this.sourceEntity);
             fireHitbox.setPosition(x,y);
             fireHitbox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 360, 0, 0));
             
             //add fire effects
             AbstractParticleEmitter fire1 = new GroundFireEmitter1(); 
             fire1.setParticlesPerFrame(.250f);
             fire1.setDuration(360);
             fireHitbox.addEmitter(fire1);             
             AbstractParticleEmitter smoke = new GroundFireSmokeEmitter(); 
             smoke.setDuration(360);
             fireHitbox.addEmitter(smoke); 
             this.getOwningScene().add(fireHitbox, Layer.MAIN); 
         }
         
         private void shootdebris()
         {
             int variance =SylverRandom.random.nextInt(10);
             Body body = new Body(new Box(50 -variance ,50 - variance), 2);
             Image image = new Image("barrel_chunk1.png"); 
             image.setDimensions(50 - variance, 50 - variance);
             BarrelDebrisHitbox fireHitbox = new BarrelDebrisHitbox(new Damage(DamageType.BURN, 5),body,image,this.sourceEntity);
             fireHitbox.setPosition(this.getPosition().x, this.getPosition().y);
             fireHitbox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 420, 0, 0));
             
             int sign = SylverRandom.random.nextBoolean()?1:-1;
             fireHitbox.getBody().addForce(new Vector2f(sign *(1500 + (int)(SylverRandom.random.nextDouble() * 1000)),2500 + (int)(SylverRandom.random.nextDouble() * 1400)));
             this.getOwningScene().add(fireHitbox, Layer.MAIN); 
         }

         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event);
             
             //see if we collided with a player primary skill
             if(other instanceof HitBox)
             {                
                 if(!((HitBox)other).getDamage().getCombatEffects().isEmpty())
                 {
                     CombatEffect labelEffect = ((HitBox)other).getDamage().getCombatEffects().get(0);
                     
                     if(labelEffect instanceof StateEffect)
                     {
                         if (((StateEffect)labelEffect).getStateEffectType() == StateEffectType.LABEL)
                         {
                             if(((StateEffect)labelEffect).getName().equals("playerLaser"))
                             {
                                
                                //explode 
                                 this.explode();
                                 
                             }
                         }
                     }
                 }
             }
             
             //remove if we hit a world object
             if( !landed && other instanceof WorldObjectEntity && -event.getNormal().getY() > .65 )
             {
                 //if we landed on the top of a worldObjectEntity
                 landed = true;
             
                 Sound sound = Sound.locationSound("buffered/bodyFall.ogg", this.getPosition().x, this.getPosition().y, false,.40f,.6f);
                 this.getOwningScene().add(sound);
                
              }
             

                         
             //remove if we hit a world object, or an enemy
             if(other instanceof CombatEntity)
             {
                //explode
                this.explode();
               
             }
         }
         
    }
    
    
    
    public static class BarrelFireHitbox extends HitBox
    {
        
        public BarrelFireHitbox(Body b, Image i, Entity user)
        {
            super(new Damage(Damage.DamageType.NODAMAGE,0),b,i,user);
            
            this.getBody().setBitmask(BitMasks.COLLIDE_WORLD.value);
            this.getBody().setOverlapMask(OverlapMasks.OVERLAP_ALL.value);
            this.getBody().setGravityEffected(true);
        }
        
        
        public void collidedWith(Entity other, CollisionEvent event)
        {
            super.collidedWith(other, event);
            
            if(other instanceof WorldObjectEntity)
            {
                body.setDamping(1);
            }
            
            //if we collided with a player add a heal dot
            if(other instanceof NonPlayerEntity)
            {
                
                //apply image effect
                //((CombatEntity)other).getImage().setColor(new Color(1.3f,.5f,.5f)); 
                
                //apply damage dot
                DotEffect fireDot = new DotEffect(360, 30, new Damage(DamageType.BURN, 5));               
                ((CombatEntity)other).getCombatData().addCombatEffect("fireDot", fireDot);
                ((CombatEntity)other).getImage().addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.COLOR, 6 * 60, new Color(255,40,5), new Color(1f,1f,1f)));
            }
            
        }
        
        public void separatedFrom(Entity other, CollisionEvent event)
        {
            if(other instanceof NonPlayerEntity)
            {
                //remove infinite effects 
//                ((CombatEntity)other).getCombatData().removeCombatEffect("fireDot");
//                ((CombatEntity)other).getImage().setColor(new Color(1,1,1,1));
                
            }
        }
        
        
    }
    
    public static class BarrelDebrisHitbox extends HitBox
    {
        private int fire = 3;
        
        public BarrelDebrisHitbox(Damage d, Body b, Image i, Entity user)
        {
            super(d, b, i, user);
            this.getBody().setGravityEffected(true);
            
             //add fire effects
             AbstractParticleEmitter fire1 = new GroundFireEmitter1(); 
             fire1.setParticlesPerFrame(.25f);
             fire1.setDuration(360);
           
             AbstractParticleEmitter smoke = new GroundFireSmokeEmitter(); 
             smoke.setDuration(360);
            this.addEmitter(smoke);
            this.addEmitter(fire1);
        }
        
        private void placeFire(float x, float y)
         {
             fire--;
             
             if(fire >0)
             {
               
               //lay down damaging fire
             Body body = new Body(new Box(50,20), 1);
             Image image = new Image("blank.png"); 
             BarrelFireHitbox fireHitbox = new BarrelFireHitbox(body,image,this.sourceEntity);
             fireHitbox.setPosition(x,y);
             fireHitbox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 360, 0, 0));
             
             //add fire effects
             AbstractParticleEmitter fire1 = new GroundFireEmitter1(); 
             fire1.setParticlesPerFrame(.25f);
             fire1.setDuration(360);
             fireHitbox.addEmitter(fire1);             
             AbstractParticleEmitter smoke = new GroundFireSmokeEmitter(); 
             smoke.setDuration(360);
             fireHitbox.addEmitter(smoke); 
             this.getOwningScene().add(fireHitbox, Layer.MAIN); 
             }
         }
        
        public void collidedWith(Entity other, CollisionEvent event)
        {
            super.collidedWith(other, event);
            
            if(other instanceof WorldObjectEntity)
            {          
                this.placeFire(this.getPosition().x, this.getPosition().y);
            }
        }
    }
    
}  

