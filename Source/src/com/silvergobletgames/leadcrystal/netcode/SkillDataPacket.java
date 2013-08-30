package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.netcode.ClientPacket;
import java.util.UUID;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;


/**
 *
 * @author mike
 */
public class SkillDataPacket extends ClientPacket
{  
    
    //skill assignments
    public SkillID skill1;
    public SkillID skill2;
    public SkillID skill3;
    public SkillID skill4;
    
    
    public SkillDataPacket()
    {
        
    }
}
