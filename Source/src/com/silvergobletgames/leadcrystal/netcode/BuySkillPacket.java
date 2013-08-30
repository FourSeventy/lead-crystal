package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.netcode.ClientPacket;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;

/**
 *
 * @author Mike
 */
public class BuySkillPacket extends ClientPacket{
    
    public SkillID skill;
    
    public BuySkillPacket()
    {
        
    }
}
