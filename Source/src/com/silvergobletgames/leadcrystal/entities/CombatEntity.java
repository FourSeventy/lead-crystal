package com.silvergobletgames.leadcrystal.entities;

import com.jogamp.opengl.util.texture.Texture;
import com.silvergobletgames.leadcrystal.combat.*;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.graphics.ImageEffect.ImageEffectType;
import java.util.ArrayList;
import java.util.Random;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Box;
import com.silvergobletgames.leadcrystal.combat.Damage.DamageType;
import com.silvergobletgames.leadcrystal.combat.ProcEffect.ProcType;
import com.silvergobletgames.leadcrystal.combat.StateEffect.StateEffectType;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.CommonCrateAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.FlierAnimationPack;
import com.silvergobletgames.leadcrystal.core.AnimationPackClasses.PlantAnimationPack;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.BloodEmitter;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.GreenBloodEmitter;
import com.silvergobletgames.leadcrystal.entities.EntityEffect.EntityEffectType;
import com.silvergobletgames.leadcrystal.items.DropGenerator;
import com.silvergobletgames.leadcrystal.items.DropGenerator.DropQuality;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.leadcrystal.skills.Skill;
import com.silvergobletgames.leadcrystal.skills.SkillManager;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Game;
import com.silvergobletgames.sylver.graphics.AnimationPack.CoreAnimations;
import com.silvergobletgames.sylver.graphics.AnimationPack.ImageAnimation;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import javax.media.opengl.GL2;


public abstract class CombatEntity extends Entity 
{
    //Full encapsulation of things related to combat
    protected CombatData combatData;
    //Full encapsulation of things related to skills
    protected SkillManager skillManager;
    //Sound pack
    protected SoundPack soundPack;
    
    public int inAirTimer = 0;
    //Last direction that a damage number was thrown
    private int lastDmgDir = 1;
    //Last Damage Information
    private Vector2f lastDmgNormal = new Vector2f();
    //last damage object
    private Damage lastDamage;
    
    //Skill that we are currently casting
    protected Skill castingSkill;
    //attack delay
    protected int attackDelay = 0;
    
    protected boolean feetOnTheGround = true;

    
    //==================
    // Constructors
    //==================
    
    public CombatEntity(Image image, Body body) 
    {
        super(image, body);
        
        //Default combat data
        combatData = new CombatData(this);
        
        //Default skill manager
        skillManager = new SkillManager(this);
        
        //sound pack
        this.soundPack = SoundPackFactory.getInstance().getSoundPack(SoundPack.SoundPackID.None);          
    }

    
    //=====================
    // SceneObject Methods
    //=====================
    
    /**
     * Updates all time-based components of this entity
     */
    public void update()
    {
        super.update();
        
        //update combat data
        combatData.update();
        
        //Update velocity
        body.setSoftMaxVelocity(combatData.xVelocity.getTotalValue(), combatData.yVelocity.getTotalValue());
        
        //update skill manager
        skillManager.update();
        
        if(!this.feetOnTheGround)
            this.inAirTimer++;
        
        
        //===================
        // Handle Attacking
        //===================       
        if(combatData.getState(CombatData.CombatState.ATTACKING))
        {
            attackDelay--;
            if(attackDelay == 0 && castingSkill != null)
                this.finishAttack();
        }
    }

    /**
     * Responsible for drawing healthbar.  In temporarily for testing purposes.
     * @param gl 
     */
    public void draw(GL2 gl) 
    {
        super.draw(gl);
        
    }
   
    public void collidedWith(Entity other, CollisionEvent event)
    {
        super.collidedWith(other, event);

        //if its a hitbox
        if (other instanceof HitBox) 
        {
            HitBox hitBox = (HitBox)other;
            
            //take the damage of the projectile
            if (hitBox.getDamage() != null && hitBox.getDamage().getType() != Damage.DamageType.NODAMAGE && hitBox.getSource() != this)
            {
                this.lastDmgNormal = new Vector2f(event.getNormal());
                this.takeDamage(hitBox.getDamage());
            }
        }
        
        //if we collided with the ground
        if (other instanceof WorldObjectEntity) 
        {  
            //if we landed on the top of a worldObjectEntity
            if(-event.getNormal().getY() > .75)
            {
                this.inAirTimer = 0 ;
            }
             //if we collided with a jumpthrouh block
            if (((WorldObjectEntity)other).isJumpthrough())
            {
                if (event.getNormal().getY() < .3)
                {
                    this.body.addIncludedBody(other.getBody());
                }
            }
        }
    }
    
    public void separatedFrom(Entity other, CollisionEvent event)
    {
        if(other instanceof WorldObjectEntity)
        {
            //if we seperated from a jumpthrough block
            if (((WorldObjectEntity)other).isJumpthrough())  
            {
                 
                this.body.removeIncludedBody(other.getBody());
            }
        }
    }
      
    //=================
    // Class Methods
    //=================
    
    /**
     * Begins to cast the skill that is passed as a parameter.  The cast will
     * finish after the skills cast time has elapsed.
     * @param skill The skill to be cast.
     */
    public void attack(Skill skill) 
    {
        //start to cast the skill if we aren't already casting , and the skill is usable
        if(skill != null && skill.isUsable() && this.combatData.canAttack() && !this.inAttackAnimation())
        {   
            //set variables
            this.castingSkill = skill;      
            this.attackDelay = this.image.getAnimationPack().getTimingDelay(skill.getImageAnimation());
             
            //change states
            this.combatData.setState(CombatData.CombatState.ATTACKING);

            //change animation, it will play through once
            this.image.setAnimation(this.castingSkill.getImageAnimation());

            //if there is no attack delay call finish attack
            if(this.attackDelay <= 0)
                this.finishAttack();             
           
        }
    }
    
    protected void finishAttack()
    {
        //build the damage object
        Damage damage = new Damage(DamageType.PHYSICAL, 0, this);
        damage.getAmountObject().setPercentModifier(this.getCombatData().baseDamage.getPercentModifier());
        
        //go through on attack procs
        for(CombatEffect effect: this.combatData.getCombatEffects())
        {
            if(effect instanceof ProcEffect && ((ProcEffect)effect).getProcType() == ProcType.ONSKILL)
            {
                boolean procResult = ((ProcEffect)effect).rollProc();
                
                if(procResult == true)
                {
                    damage.addCombatEffect(((ProcEffect)effect).getProccedEffect());
                }
            }
        }
        
        //roll for crit
        double critRoll = Math.random();
        if(critRoll <= this.combatData.critChance.getTotalValue())
        {
            //modify damage
            damage.setCrit(true);
            damage.getAmountObject().adjustPercentModifier(this.combatData.critModifier.getTotalValue());
            
        }
        
        //set damage life leech
        damage.setLifeLeech(this.combatData.lifeLeech.getTotalValue());
        
        //calculate the skill origin point
        SylverVector2f offset = new SylverVector2f(0,0);
        if(this.image.getAnimationPack() != null)
            offset = new SylverVector2f(this.image.getAnimationPack().getPositionOffset(this.castingSkill.getImageAnimation()));
        
        offset.x = offset.x * this.getFacingDirection().value;
        offset.scale(this.image.getScale());
        offset.add(this.getPosition());
        
        //use the skill
        this.castingSkill.use(damage, offset);
        
        //start the cooldown
        this.castingSkill.beginCooldown(); 
        
        //leave attacking state
        this.combatData.removeState(CombatData.CombatState.ATTACKING);
              
    }

    
    /**
     * Stops all activities related to the spell being cast currently.
     */
    public void interruptAttacking() 
    {
        //Combat Data Changes
        this.combatData.removeState(CombatData.CombatState.ATTACKING);
        
        //Skill Changes
        this.castingSkill = null;
        
        //Image Changes
        this.image.setAnimation(CoreAnimations.IDLE);
    }

    /**
     * Give the entity a "Status" object that is a better version of CombatData. 
     * Status will handle incoming damage, combat "status", and combat effects(?).
     * 
     * Wrap this functionality into the combat data?
     */
    public void takeDamage(Damage dmg)
    {
        //if we are immune or dead we wont take any damage
        if (combatData.getState(CombatData.CombatState.IMMUNE) || combatData.isDead())  
        { 
            return;
        }
                       
        //go through on damage procs
        for(CombatEffect effect: this.combatData.getCombatEffects())
        {
            if(effect instanceof ProcEffect && ((ProcEffect)effect).getProcType() == ProcType.TAKEDAMAGE)
            {
                boolean procResult = ((ProcEffect)effect).rollProc();

                if(procResult == true)
                {
                    this.combatData.addCombatEffect(((ProcEffect)effect).getProccedEffect());
                }
            }
        }


        //get a copy of our incoming damage
        Damage incomingDamage = new Damage(dmg);
        this.lastDamage = incomingDamage;
        
        //flatten damage amount object so we can adjust its percentage correctly
        incomingDamage.getAmountObject().setBase(incomingDamage.getAmount());
        incomingDamage.getAmountObject().setAbsoluteModifier(0);
        incomingDamage.getAmountObject().setPercentModifier(1f);
        
        //handle adjustment of incoming damage from resistances
        if(incomingDamage.getType() == DamageType.HEAL)
        {
            float healingModifier = combatData.healingModifier.getTotalValue();
            incomingDamage.getAmountObject().setPercentModifier(healingModifier); 
        }
        else
        {
            float resistance = this.combatData.damageResistance.getTotalValue(); 
            incomingDamage.getAmountObject().adjustPercentModifier( -resistance);
        }

        //Handle modification of  health   
        if(incomingDamage.getType() != DamageType.HEAL)
        {   
            this.combatData.currentHealth -= incomingDamage.getAmount();
        }
        else
        {
            this.combatData.currentHealth += incomingDamage.getAmount();
        }

        //add damage text to the world
        if (incomingDamage.getType() != DamageType.NODAMAGE && !(this.getImage().getAnimationPack() instanceof CommonCrateAnimationPack))
            owningScene.add(new CombatText(incomingDamage, this, owningScene), Layer.WORLD_HUD);

        //Apply the effects and overlays of the damage to this entity
        for(CombatEffect combatEffect: incomingDamage.getCombatEffects())
        {
            //reduce the duration of cc's with ccreduction
            if(combatEffect instanceof StateEffect)
            {
                if(((StateEffect)combatEffect).getStateEffectType() == StateEffectType.STUN ||
                   ((StateEffect)combatEffect).getStateEffectType() == StateEffectType.SLOW)
                {
                    ((StateEffect)combatEffect).setDuration((int)(combatEffect.getDuration() * (1 - combatData.ccResistance.getTotalValue()))); 
                }
            }
            this.combatData.addCombatEffect(combatEffect);
        }

        for(ImageEffect renderEffect: incomingDamage.getImageEffects())
            this.image.addImageEffect(renderEffect);

        for(Overlay overlay: incomingDamage.getOverlays())
            this.image.addOverlay(overlay);                   

        //TODO - interrupt casting

        //handle life leech
        float leechAmount = incomingDamage.getAmount() * incomingDamage.getLifeLeech();
        if(leechAmount > 0)
        {
            Damage leechHeal = new Damage(DamageType.HEAL, leechAmount, this);
            if(incomingDamage.getSource() != null)
            {
                incomingDamage.getSource().takeDamage(leechHeal);
            }
        }
        
        //handle thorns damage
        float thornsAmount = incomingDamage.getAmount() * this.getCombatData().thornsDamage.getTotalValue();
        if(thornsAmount > 0)
        {
            Damage thornsDamage = new Damage(DamageType.PHYSICAL,thornsAmount,this);
            if(incomingDamage.getSource() != null)
            {              
                incomingDamage.getSource().takeDamage(thornsDamage);
            }
        }
        
    }

    /**
     * Cool helper method to make damage numbers awesome by finding the right theta, 
     * to try to keep them from stacking up.
     *
     * Figure out a way we can do this inside CombatText.
     * @return 
     */
    public float nextDamageDirection() {
        float theta = 0;

        switch (lastDmgDir) {
            case 1: {
                theta = (float) (Math.random() * 32 + 5);
                lastDmgDir = 0;
                break;
            }
            case 0: {
                if (Math.random() < .5) {
                    theta = (float) (90 - Math.random() * 18);
                } else {
                    theta = (float) (-90 + Math.random() * 18);
                }
                lastDmgDir = -1;
                break;
            }
            case -1: {
                theta = -(float) (Math.random() * 32 + 5);
                lastDmgDir = 1;
                break;
            }
        }
        return theta;
    }

    /**
     * Calls functionality related to death
     */
    public void die() 
    {
        //Essential status flag when dead
        this.combatData.setState(CombatData.CombatState.DEAD);

        //Remove all effects
        this.combatData.removeAllCombatEffects();
        this.getImage().removeAllImageEffects();

        //Stop casting
        this.interruptAttacking();
        
        //remove emitters
        for(AbstractParticleEmitter emitter: this.emitters)
            emitter.stopEmittingThenRemove(); 
        
        //Playing of death sounds
        Sound sound =this.soundPack.playRandom(SoundPack.SoundPackType.DEATH, this.getPosition().x, this.getPosition().y);
        this.owningScene.add(sound);
        
        //Chunk Bucket
        this.emitDeathChunks();
        
        //Drop items
        if(!(this instanceof PlayerEntity))
        {
            ArrayList<ItemEntity> items = DropGenerator.generateDrops(this.combatData.dropPotionChance, this.combatData.dropGoldChance);
            for (ItemEntity item : items) 
            {                
                //set the items position and give it some initial velocity
                item.setPosition(this.getPosition().x, this.getPosition().y);
                Random r = SylverRandom.random;
                short sign = 1;
                if (Math.random()<.5)
                    sign = -1;
                Vector2f force = new Vector2f( 500 + sign*(r.nextFloat()*1500), 2000 + r.nextFloat()*5000 );
                item.getBody().addForce(force);

                owningScene.add(item, Layer.MAIN);
            }      
        }
        
        //remove from scene
        this.removeFromOwningScene();
        
    }
    
    protected void emitDeathChunks()
    {
        lastDmgNormal.normalise();
        Vector2f direction = new Vector2f(-lastDmgNormal.x,-lastDmgNormal.y);
        
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
            Vector2f chunkForce = new Vector2f(((float)Math.random()*600+400),((float)Math.random()*1200+600)); 
            chunkBody.addForce(new Vector2f(((direction.x >= 0)? 1: -1) * chunkForce.x, chunkForce.y));
            
            //add some rotation
            int rotationDirection = Math.random() > .5f? 1: -1;
            float rt = ((float)Math.random()*3 *rotationDirection);
            
            chunkBody.adjustAngularVelocity(rt);
            
            //set up entity                 
            Entity chunk = new Entity(chunkImage, chunkBody)
            {
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
                if(this.getImage().getAnimationPack() instanceof PlantAnimationPack
                   ||this.getImage().getAnimationPack() instanceof FlierAnimationPack)
                {
                    AbstractParticleEmitter emitter = new GreenBloodEmitter();
                    emitter.setDuration(250);
                    emitter.setPosition(this.getPosition().x,this.getPosition().y);
                    chunk.addEmitter(emitter); 
                }
                else if(this.getImage().getAnimationPack() instanceof CommonCrateAnimationPack)
                {
                    //dont add emitter
                }
                else
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
    
    /**
     * Base definition for finishedAnimating.
     * @param animation 
     */
    public void finishedAnimating(Image image,ImageAnimation animation) 
    {       
        super.finishedAnimating(image, animation);       
        
        if(animation == ExtendedImageAnimations.MELEEATTACK || animation == ExtendedImageAnimations.RANGEDATTACK || animation == ExtendedImageAnimations.SPELLATTACK)
        {            
            this.image.setAnimation(CoreAnimations.IDLE);   // TODO - revert to previous animation       
        }
    }
    
    /**
     * Moves this entity in the direction of the supplied vector. Changes animation accordingly
     * @param vector Direction to move
     */
    public void move(SylverVector2f vector)
    {
        if (combatData.canMove())
        {          
            //add force to the body
            SylverVector2f vectorCopy = new SylverVector2f(vector);
            vectorCopy.normalise();
            
//            int moveForce = 3000;
//            if(!this.feetOnTheGround)
//                moveForce = 500;
//                System.err.println(moveForce);
            
            this.body.addSoftForce(new Vector2f(3000 * vectorCopy.x, 3000 * vectorCopy.y));
            
            //set the correct animation            
            if(Math.abs(this.body.getVelocity().getX()) > 2 && !this.combatData.getState(CombatData.CombatState.ATTACKING) &&  !this.inAttackAnimation())
               image.setAnimation(ExtendedImageAnimations.RUNNING);
        }
    }
    
    public void move(FacingDirection direction)
    {
        this.move(new SylverVector2f(direction.value,0));
    }
    
    
    
    //=====================
    // Accessor Methods
    //=====================
        
    public boolean inAttackAnimation()
    {
        return (this.image.getAnimation() == ExtendedImageAnimations.MELEEATTACK || this.image.getAnimation() == ExtendedImageAnimations.RANGEDATTACK || this.image.getAnimation() == ExtendedImageAnimations.SPELLATTACK);
    }
    
    /**
     * Assigns this entity a combat data.
     * @param s 
     */
    public void setCombatData(CombatData s)
    {
        this.combatData = s;
    }

    /**
     * Returns this entity's combat data
     * @return 
     */
    public CombatData getCombatData() 
    {
        return combatData;
    }

    /**
     * Accessor for skill manager
     * @return 
     */
    public SkillManager getSkillManager()
    {
        return skillManager;
    }
    
    public void setSkillManager(SkillManager bisu)
    {
        this.skillManager = bisu;
    }
    
    public void setSoundPack(SoundPack soundPack)
    {
        this.soundPack = soundPack;
    }
    
    public SoundPack getSoundPack()
    {
        return this.soundPack;
    }
}
