/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.silvergobletgames.leadcrystal.ai;

import com.jogamp.opengl.util.texture.Texture;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.Boss2DeathAnimationPack;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.BloodEmitter;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.EntityEffect.EntityEffectType;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.items.LootSpewer;
import com.silvergobletgames.leadcrystal.skills.Skill;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.graphics.ImageEffect.ImageEffectType;
import com.silvergobletgames.sylver.graphics.Overlay;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.ArrayList;
import java.util.Random;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Circle;

/**
 *
 * @author Mike
 */
public class BrainFinalBoss2 extends BrainFighter
{
    
    Random r = SylverRandom.random;
    
     /**
     * Fighter brain.
     */
    public BrainFinalBoss2() 
    {
        super();
        ID = BrainFactory.BrainID.FinalBoss2;
        
        relevantGroups.add(ExtendedSceneObjectGroups.FIGHTER);
    }
    
     public void selectSkill()
    {       
        float roll = r.nextFloat();

        //get all offensive skills
        ArrayList<Skill> skillPool = self.getSkillManager().getKnownSkillsOfType(Skill.SkillType.OFFENSIVE);
  
        if(roll < .33) //33% chance
        {
            this.selectedSkill = skillPool.get(0);
        }
        else if(roll < .66) //33%  change
        {
            this.selectedSkill = skillPool.get(1);
        }
        else //33% chance
        {
            this.selectedSkill = skillPool.get(2);
        }
    }
     
    public void deadEnter()
    {
        
       //add animation handler
        self.getOwningScene().add( new DeathImage(self.getPosition()),Scene.Layer.MAIN);
        
       //add explosions
       self.getOwningScene().add(new ExplosionProducer(self.getPosition()),Scene.Layer.MAIN);
//        //if we are a gold chest
//        if(self.getCombatData().dropGoldChance == DropGenerator.DropChance.ALWAYS)
//        {
//            //add spewer to the world
//            LootSpewer spew = new LootSpewer(60);
//            spew.setPosition(self.getPosition().x, self.getPosition().y);
//            self.getOwningScene().add(spew, Scene.Layer.MAIN);
//        }
    }
    
    protected void spawningExecute()
    {
        if(self.getImage().getAnimation() != ExtendedImageAnimations.SPAWN)
        {
            this.getStateMachine().changeState(AIState.StateID.AGGRESSIVE);
        }
    }
    
    

    
    
    private class ExplosionProducer extends Entity
    {
        private long ticks = 0;
        private SylverVector2f origin;

        
        
        public ExplosionProducer(SylverVector2f origin)
        { 
           super( new Image("blank.png"),new Body(new Box(10,10), 1));

           this.origin = origin;
        }
        
        @Override
        public void update()
        {
            this.ticks++;
            
            
            
            for( int i = 0; i < (5 * 60) ; i += 15)
            {
                if(ticks == i)
                {
                    Random rand = SylverRandom.random;
                    float posMag = (rand.nextFloat() - .5f)*400;
                    float posAngle = (rand.nextFloat() - .5f) * 720;
                    SylverVector2f pos = new SylverVector2f( posMag *(float)Math.cos(posAngle * Math.PI/180) ,  posMag *(float)Math.sin(posAngle * Math.PI/180));
                    this.insertExplosion(self.getPosition().x + pos.x, self.getPosition().y + pos.y);
                }
            }
            if(this.ticks > (5 * 60))
            {
                this.removeFromOwningScene();
            }
            
            
        }
        
        public void insertExplosion(float x, float y)
        {
         //===============================
        //add explosion hitbox and effects 
        //================================

        //explosion hitbox
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

        Damage damage = new Damage(Damage.DamageType.NODAMAGE, 0);

           Body beh = new StaticBody(new Circle(180));
           beh.setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);
           beh.setBitmask(Entity.BitMasks.NO_COLLISION.value);
           damage.setType(Damage.DamageType.PHYSICAL);   

           //add stun and slow to damage
           damage.addCombatEffect(new StateEffect(StateEffect.StateEffectType.STUN, 240));

           HitBox explosionHitbox = new HitBox(damage, beh, img, null);
           explosionHitbox.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 10, 0, 0));               
           explosionHitbox.setPosition(x, y);
           LeadCrystalParticleEmitters.BombExplosionEmitter emitter = new LeadCrystalParticleEmitters.BombExplosionEmitter();
           emitter.setDuration(15);
           AbstractParticleEmitter fire1 = new LeadCrystalParticleEmitters.GroundFireEmitter1(); 
           fire1.setParticlesPerFrame(.75f);
           fire1.setDuration(360);
           explosionHitbox.addEmitter(fire1);
           explosionHitbox.addEmitter(emitter);

           this.getOwningScene().add(explosionHitbox, Scene.Layer.MAIN);

           //play sound
           Sound sound = Sound.locationSound("buffered/explosion.ogg",x, y, false, 1f, 1f);
           this.getOwningScene().add(sound);

    }
    
        
        
    }
    
    private class DeathImage extends Entity
    {
        private long ticks = 0;
        private SylverVector2f origin;

        
        
        public DeathImage(SylverVector2f origin)
        { 
           super( new Image(new Boss2DeathAnimationPack()),new Body(new Box(10,10), 1));

           this.origin = origin;
           this.setPosition(origin.x, origin.y + 35);
        }
        
        @Override
        public void update()
        {
            this.ticks++;
            
                      
            if(this.ticks > (5 * 60))
            {
                this.emitDeathChunks();
                this.removeFromOwningScene();
            }
            
            
        }
        
        protected void emitDeathChunks()
        {
       
        
        
        //figure out chunk offsets
        ArrayList<SylverVector2f> chunkOffsets = new ArrayList();
        
        int xBitmask = Float.floatToIntBits(this.getImage().getAnimationPack().getPositionOffset(ExtendedImageAnimations.DEATH).x);
        int yBitmask = Float.floatToIntBits(this.getImage().getAnimationPack().getPositionOffset(ExtendedImageAnimations.DEATH).y);
        
        float xByteOffset1 = ((xBitmask & 0xf0000000) >> 28) /7f; //HUEHUEHUEHUEHUEHUEHUEHUE????????
        float yByteOffset1 = ((yBitmask & 0xf0000000) >> 28) /7f;
        chunkOffsets.add(new SylverVector2f(xByteOffset1,yByteOffset1)); 
        
        float xByteOffset2 = ((xBitmask & 0x0f000000) >> 24) /15f; 
        float yByteOffset2 = ((yBitmask & 0x0f000000) >> 24) /15f;
        chunkOffsets.add(new SylverVector2f(xByteOffset2,yByteOffset2)); 
        
        float xByteOffset3 = ((xBitmask & 0x00f00000) >> 20) /15f; 
        float yByteOffset3 = ((yBitmask & 0x00f00000) >> 20) /15f;
        chunkOffsets.add(new SylverVector2f(xByteOffset3,yByteOffset3)); 
        
        float xByteOffset4 = ((xBitmask & 0x000f0000) >> 16) /15f; 
        float yByteOffset4 = ((yBitmask & 0x000f0000) >> 16) /15f;
        chunkOffsets.add(new SylverVector2f(xByteOffset4,yByteOffset4)); 
        
        float xByteOffset5 = ((xBitmask & 0x0000f000) >> 12) /15f; 
        float yByteOffset5 = ((yBitmask & 0x0000f000) >> 12) /15f;
        chunkOffsets.add(new SylverVector2f(xByteOffset5,yByteOffset5)); 
        
        float xByteOffset6 = ((xBitmask & 0x00000f00) >> 8) /15f; 
        float yByteOffset6 = ((yBitmask & 0x00000f00) >> 8) /15f;
        chunkOffsets.add(new SylverVector2f(xByteOffset6,yByteOffset6));
        
        float xByteOffset7 = ((xBitmask & 0x000000f0) >> 4) /15f; 
        float yByteOffset7 = ((yBitmask & 0x000000f0) >> 4) /15f;
        chunkOffsets.add(new SylverVector2f(xByteOffset7,yByteOffset7));
        
        float xByteOffset8 = ((xBitmask & 0x0000000f)) /15f; 
        float yByteOffset8 = ((yBitmask & 0x0000000f)) /15f;
        chunkOffsets.add(new SylverVector2f(xByteOffset8,yByteOffset8));

            
        //for each texture in the death animation set
        for(int i = 0; i <this.getImage().getAnimationPack().animationSet.get(ExtendedImageAnimations.DEATH).size(); i++)
        {
            //get texture
            Texture texture = this.getImage().getAnimationPack().animationSet.get(ExtendedImageAnimations.DEATH).get(i);
            
            //make image out of texture
            Image chunkImage = new Image(Game.getInstance().getAssetManager().getTextureLoader().reverseLookup(texture));
            chunkImage.setScale(this.getImage().getScale());
            chunkImage.setHorizontalFlip(this.getImage().isFlippedHorizontal());
            chunkImage.setVerticalFlip(this.getImage().isFlippedVertical());
                       
            //set up body
            Box shape = new Box(chunkImage.getWidth() * .8f,chunkImage.getHeight() * .8f);
            float mass = chunkImage.getWidth() * chunkImage.getHeight() /1000; //calculating mass based on side of image
            mass = Math.max(.6f, mass); //mass cant be lower than .6f
            Body chunkBody = new Body(shape, mass);
            chunkBody.setRestitution(.8f);           
            chunkBody.setBitmask(Entity.BitMasks.COLLIDE_WORLD.value);
            chunkBody.setRotDamping(3f);
                     
            //add some x and y force
            Vector2f direction = new Vector2f((float)Math.random(),(float)Math.random());
            Vector2f chunkForce = new Vector2f(((float)Math.random()*10_000+10_000),((float)Math.random()*13_000+13_000)); 
            chunkBody.addForce(new Vector2f(((Math.random() >=.5f)? 1: -1) * chunkForce.x, chunkForce.y));
            
            //add some rotation
            int rotationDirection = Math.random() > .5f? 1: -1;
            float rt = ((float)Math.random()*3 *rotationDirection);
            
            chunkBody.adjustAngularVelocity(rt);
            
            //set up entity                 
            Entity chunk = new Entity(chunkImage, chunkBody)
            {
                private LootSpewer loot = new LootSpewer(7 * 60);
                
                @Override
                public void addedToScene()
                {
                    super.addedToScene();
                    
                    if(this.getImage().getTextureReference().equals("boss2-torso.png"))
                    {
                        this.loot.setPosition(this.getPosition().x, this.getPosition().y);
                        this.getOwningScene().add(loot, Layer.MAIN);
                    }
                }
                
                @Override
                public void update()
                {
                    super.update();
                    if(this.getImage().getTextureReference().equals("boss2-torso.png"))
                    {
                        this.loot.setPosition(this.getPosition().x, this.getPosition().y);
                    }
                }
                
                @Override
                 public void collidedWith(Entity other, CollisionEvent event)
                {
                    super.collidedWith(other, event);
                    
                    this.body.setRestitution(0);
                    
                   
                }
            };
            chunk.getBody().setDamping(0);
            chunk.getBody().setFriction(2);
            
            if(i >=3)
            {
                ImageEffect fadeEffect =new ImageEffect(ImageEffectType.COLOR, 200,new Color(Color.white),new Color(Color.transparent));
                fadeEffect.setDelay(((int)(SylverRandom.random.nextFloat() * 500)) +1000);
                chunk.getImage().addImageEffect(fadeEffect);           
                chunk.addEntityEffect(new EntityEffect(EntityEffectType.DURATION,1699,1,1));
            }
            else
            {
                chunk.addEntityEffect(new EntityEffect(EntityEffectType.REMOVEBODY,1699,1,1));
            }
            
            //add blood emitter TODO add more blood with more mass
            if(SylverRandom.random.nextFloat() > .30f)
            {
                
                {
                    AbstractParticleEmitter emitter = new BloodEmitter();
                    emitter.setDuration(250);
                    emitter.setPosition(this.getPosition().x,this.getPosition().y);
                    chunk.addEmitter(emitter); 
                }
            }
            
            chunk.setPosition(this.getPosition().x + (chunkOffsets.get(i).x * this.getWidth() - this.getWidth())/2, this.getPosition().y + (chunkOffsets.get(i).y * this.getHeight() - this.getHeight()/2));
            
            owningScene.add(chunk,Layer.MAIN);
        } 
    }
        
       
    
        
        
    }
}
