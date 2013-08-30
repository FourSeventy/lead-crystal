package com.silvergobletgames.leadcrystal.skills;

import java.util.HashSet;
import java.util.Set;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;

/**
 * Contains a system for building a Skill object and spitting it back to us, much in the same way
 * that we use AnimationSpriteFactory to get an appropriate list of textures.  
 */
public class SkillFactory
{
    //private instance
    private static SkillFactory instance = new SkillFactory();
    
  
    
    //===============
    // Constructors
    //===============
    
    private SkillFactory()
    {
     
    }
    
    public static SkillFactory getInstance()
    {
        return instance;
    }
    
    
    //===============
    // Class Methods
    //===============
    
    /**
     * Given the correct ID, will get the appropriate skill from the map.  If the skill does not
     * exist in the map, null will be returned
     */
    public  Skill getSkill(SkillID id) 
    {    
        switch(id)
        {
            case PlayerLaser: return new PlayerLaserShot();
            case PlayerSnipe: return new PlayerSnipe();
            case EnemyLaser: return new EnemyLaserShot();
            case EnemyMeleeBash: return new EnemyMeleeBash();
            case EnemySmallMelee: return new EnemySmallMelee();
            case EnemyGooShot: return new EnemyGooShot();
            case EnemyAntThrow: return new EnemyAntThrow();
            case PlayerDash: return new PlayerDashAttack();
            case PlayerGuard: return new PlayerGuard();
            case PlayerFreezeAttack: return new PlayerFreezeAttack();
            case PlayerWard: return new PlayerWard();
            case PlayerBuckshot: return new PlayerBuckshot();
            case PlayerAttackDrone: return new PlayerAttackDrone();
            case PlayerDestructionDisk: return new PlayerDestructionDisk();
            case PlayerBashAttack: return new PlayerBashAttack();
            case PlayerFlashbang: return new PlayerFlashbang();
            case PlayerStimpack: return new PlayerStimpack();
            case PlayerRicochet: return new PlayerRicochet();
            case PlayerSoulLeech: return new PlayerSoulLeech();
            case PlayerStomp: return new PlayerStompAttack();
            case PlayerRocket: return new PlayerRocket();
            default: return null;
        }
    }

    /**
     * Returns a sorted Set of the different types of Brains that is used for building
     * NPEs in the map editor.
     */
    public  Set<String> getAvailableEnemySkills() 
    {
        HashSet<String> set = new HashSet();
        
        for(SkillID type :SkillID.values())
        {
            if(type.name().startsWith("Enemy"))
                set.add(type.name());
        }
        
        return set;
    }
    
    public  Set<String> getAvailableSkills() 
    {
        HashSet<String> set = new HashSet();
        
        for(SkillID type :SkillID.values())
        {           
            set.add(type.name());
        }
        
        return set;
    }
}
