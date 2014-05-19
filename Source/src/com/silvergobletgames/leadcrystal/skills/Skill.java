package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.sylver.core.InputHandler;
import com.silvergobletgames.sylver.graphics.Image;
import java.util.HashSet;
import net.phys2d.math.Vector2f;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.sylver.graphics.AnimationPack.ImageAnimation;
import com.silvergobletgames.sylver.util.SylverVector2f;

public abstract class Skill 
{
    
    //the skillID
    protected SkillID skillID;
    //skill types that this skill is
    protected SkillType skillType;
    //reference to the user of this skill   
    protected CombatEntity user;
    //skill icon
    protected Image icon;
    //skill name 
    protected String skillName = "";
    //skill description
    protected String skillDescription = "";
    //unlock cost
    protected int unlockCost = 0;
    
    // Max range (If further from target than this, can't use) 
    protected int range = Integer.MAX_VALUE;
    // Cooldown, if any 
    protected int cooldown = 0;
    // Cooldown time remaining 
    protected int cooldownRemaining = 0;

    //Image animation to play
    protected ImageAnimation imageAnimation;
    
    public static enum SkillID{
        //WARNING - Changing the names of this enum will break save data
        PlayerLaser,PlayerBuckshot, PlayerRicochet, PlayerRocket, //primary
        PlayerBashAttack, PlayerDash, PlayerBoomerang, PlayerSnipe,  //secondary
        PlayerPoisonBomb, PlayerBarrelRoll,PlayerClusterbomb, PlayerCrushingStrike, //power
        PlayerAttackDrone,PlayerGravityShield,PlayerWard,PlayerLeechingBlades,  //tech
        EnemyMeleeBash,EnemyGooShot,EnemySmallMelee, EnemyAntThrow, EnemyFlierGooBomb,
        EnemyBossSwipe, EnemyJumperSpikes,EnemyRangedSwipe,EnemyTriShot,EnemyGooHeal,  
        EnemyRockThrow;
        
    }
    
    public static enum SkillType{
        OFFENSIVE, DEFENSIVE, HEAL, BUFF, DEBUFF;
    }
    
    
    //=============
    // Constructor
    //=============
    
    public Skill(SkillID skillID, SkillType type, ImageAnimation animation, int cooldown, int range)
    {
        //set skillID
        this.skillID = skillID;
        
        //add the skill type
        this.skillType = type;
        
        //set image animation
        this.imageAnimation = animation;
        
        //set cooldown
        this.cooldown = cooldown;

        //set range
        this.range = range;
    }
    
    
    //===============
    // Class Methods
    //================
    
    /**
     * Basic command all skills will share.  Passes the using CombatEntity.  This Entity contains
     * a reference to all relevant stats, equipment, and combat effects that might influence the skill,
     * as well as the Entity's target, if needed.  It also contains a reference to its owning scene, 
     * which allows the mouse cursor position to be obtained, and for entities/particles/images to be
     * added to the scene for the effect of the skill.
     * 
     * @param c The user of the skill, used to obtain any references needed.
     */
    public abstract void use(Damage damage, SylverVector2f originPoint);
       
    /**
     * Updates the state of this skill, important for skills with cooldowns and for channeled skills
     */
    public void update()
    {
        if (cooldownRemaining > 0)
                 cooldownRemaining--;
    }
    
    /**
     * Returns whether the skill is usable by the entity, depends on cooldown, silenced, etc.
     * @return 
     */
    public boolean isUsable()
    {
        return cooldownRemaining <= 0;
    }
    
    /**
     * Starts the cooldown
     */
    public void beginCooldown()
    {     
        //cooldown remaining is modified by the cooldown regen
        cooldownRemaining = (int)(this.cooldown * user.getCombatData().cooldownModifier.getTotalValue());
    }
       
    /**
     * If a skill requires the InputHandler to make additional logic decisions after
     * the skill has been used (or even before it's used), this method will contain
     * that logic.  Implement when necessary.
     * @param input 
     */
    public void handleInput(InputHandler input){
        
    }
    
    public static Vector2f getVectorToTarget(Entity self, Entity target)
    {
        Vector2f vector;
        
        float selfX = self.getPosition().x;
        float selfY = self.getPosition().y;
        float targetX = target.getPosition().x;
        float targetY = target.getPosition().y;
        
        //math
        float theta = (float)Math.atan((targetY - selfY) / (targetX - selfX) );
        int sign = (int)((targetX - selfX) / Math.abs(targetX - selfX));
        
        vector = new Vector2f( sign *(float)Math.cos(theta), sign * (float)Math.sin(theta));
        
        return vector;
    }
        
    
    //==================
    // Accessor Methods
    //===================
    
    /**
     * Returns the skill type
     * @return 
     */
    public SkillType getType()
    {
        return skillType;
    }
    
    /**
     * Sets the skill type
     * @param type 
     */
    public void setType(SkillType type)
    {
        this.skillType = type;
    }
    
    /**
     * Returns the image animation that corresponds to the
     * @return 
     */
    public ImageAnimation getImageAnimation()
    {
        return this.imageAnimation;
    }
    
    /**
     * Returns the skill users base damage to be manipulated by the skill
     * @return 
     */
    protected final float getBaseDamage()
    {
        return this.user.getCombatData().baseDamage.getTotalValue();
    } 
    
    /**
     * Returns the cooldown of the skill
     * @return 
     */
    public int getCooldown()
    {
        return cooldown;
    }
    
    /**
     * returns the cooldown remaining
     * @return 
     */
    public int getCooldownRemaining()
    {
        return this.cooldownRemaining;
    }
    
    public void setCooldownRemaining(int value)
    {
        this.cooldownRemaining = value;
    }
    
    /**
     * Returns the max range necessary to use the skill
     */
    public int getRange()
    {
        return range;
    }
    
    /**
     * returns this skills icon
     * @return 
     */
    public Image getIcon()
    {
        return new Image(this.icon.getTextureReference());
    }
    
    public void setUser(CombatEntity user)
    {
        this.user = user;
    }
    
    public SkillID getSkillID()
    {
        return this.skillID;
    }
    
    /**
     * 
     * @return the skills name
     */
    public String getSkillName()
    {
        return this.skillName;
    }
    
    /**
     * 
     * @return the description of this skill
     */
    public String getSkillDescription()
    {
        return this.skillDescription;
    }
    
    /**
     * 
     * @return the unlock cost of this skill
     */
    public int getUnlockCost()
    {
        return this.unlockCost;
    }
}
