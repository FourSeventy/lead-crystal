/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.silvergobletgames.leadcrystal.ai;

import com.silvergobletgames.leadcrystal.skills.Skill;
import com.silvergobletgames.sylver.util.SylverRandom;
import java.util.ArrayList;
import java.util.Random;

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
    }
    
     public void selectSkill()
    {        

        //get all offensive skills
        ArrayList<Skill> skillPool = self.getSkillManager().getKnownSkillsOfType(Skill.SkillType.OFFENSIVE);
  
        if(r.nextFloat() < .33) //33% chance
        {
            this.selectedSkill = skillPool.get(0);
        }
        else if(r.nextFloat() < .66) //33%  change
        {
            this.selectedSkill = skillPool.get(1);
        }
        else //33% chance
        {
            this.selectedSkill = skillPool.get(2);
        }
    }
    
}
