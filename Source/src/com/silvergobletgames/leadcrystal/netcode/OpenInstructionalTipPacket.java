package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.sylver.netcode.Packet;


/**
 *
 * @author Mike
 */
public class OpenInstructionalTipPacket extends Packet{
    
    public InstructionalTip tipID;
    
    public static enum InstructionalTip{
        Jump,PrimarySkill,SecondarySkill,Sprint,UsePotion,RightClick,Ladder,Jumpthrough,OpenMap,AssignSkills,BuyPotions
    }
    
    public OpenInstructionalTipPacket()
    {
        
    }
    
}
