
package com.silvergobletgames.leadcrystal.items;

import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import com.silvergobletgames.sylver.netcode.RenderData;
import com.silvergobletgames.sylver.netcode.SaveData;
import com.silvergobletgames.sylver.netcode.SceneObjectRenderDataChanges;
import java.util.ArrayList;
import java.util.Arrays;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;

/**
 *
 * @author Mike
 */
public class PotionManager 
{
    public PlayerEntity playerReference;
    private int numberOfPotions;
    private int maxPotions = 3;
    
    public static final int POTION_PRICE = 15;
    
    //============
    // Constructor
    //============
    
    public PotionManager(PlayerEntity playerReference)
    {
        this.playerReference = playerReference;
    }
    
    
    //===============
    // Class Methods
    //===============
    
    public void addPotion(int amount)
    {
        this.numberOfPotions += amount;
    }
    
    public void increaseMaxPotions(int amount)
    {
        this.maxPotions += amount;
    }
    
    public int getNumberOfPotions()
    {
        return this.numberOfPotions;
    }
    
    public int getMaxPotions()
    {
        return this.maxPotions;
    }
    
    public void usePotion()
    {
        if(this.numberOfPotions > 0)
        {
            this.numberOfPotions--;
            
            int healAmount = 50;
            
            //handle potion effectiveness upgrade
            if(this.playerReference.getArmorManager().healingEffectiveness.points == 1)
            {
                healAmount = 100;
            }
            //do the heal
            Damage heal = new Damage(Damage.DamageType.HEAL, healAmount);
            this.playerReference.takeDamage(heal);
            
            Image potionImage = new Image("healthPot3.png");
           
            potionImage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.SCALE, 60, 1, 2));
            potionImage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.ALPHABRIGHTNESS, 60, 1, 0));
            
            Body body = new Body( new Box(10,10),1);
            body.setGravityEffected(false);
            body.setOverlapMask(Entity.OverlapMasks.NO_OVERLAP.value);
            body.setBitmask(Entity.BitMasks.NO_COLLISION.value);
            Entity potionEntity = new Entity(potionImage, body);
             potionEntity.setPosition(this.playerReference.getPosition().x, this.playerReference.getPosition().y + 150);
            potionEntity.addEntityEffect(new EntityEffect(EntityEffect.EntityEffectType.DURATION, 61, 0, 1));
            this.playerReference.getOwningScene().add(potionEntity, Scene.Layer.MAIN);
            
            
            //if they have the modifier
            if(this.playerReference.getArmorManager().potionCooldownReset.isMaxPoints())
            {
                this.playerReference.getSkillManager().getSkill(this.playerReference.getSkillAssignment(1)).setCooldownRemaining(0);
                this.playerReference.getSkillManager().getSkill(this.playerReference.getSkillAssignment(2)).setCooldownRemaining(0);
                if(this.playerReference.getSkillAssignment(3) != null )
                {
                    this.playerReference.getSkillManager().getSkill(this.playerReference.getSkillAssignment(3)).setCooldownRemaining(0);
                }
                if(this.playerReference.getSkillAssignment(4) != null)
                {
                    this.playerReference.getSkillManager().getSkill(this.playerReference.getSkillAssignment(4)).setCooldownRemaining(0);
                }
            }
            
            
            //play sound
            if(this.playerReference != null && this.playerReference.getOwningScene() != null)
            {
                //sound
                Sound sound = Sound.locationSound("buffered/usePotion.ogg", this.playerReference.getPosition().x, this.playerReference.getPosition().y, false,.8f);  
                this.playerReference.getOwningScene().add(sound);
            }
        }
    }
    
    
   //==================
   //Save Data Methods
   //==================
   
   public SaveData dumpFullData() 
   {
        //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
       
       SaveData saveData = new SaveData();
       saveData.dataMap.put("number", numberOfPotions);
        
        return saveData;
   }
   
   
   public static PotionManager buildFromFullData(SaveData saveData)  
   {
       //=====================================
        // WARNING
        //-Making changes to this method could
        //break saved data
        //======================================
       
       PotionManager currencyManager = new PotionManager(null);
       
       currencyManager.numberOfPotions = (int)saveData.dataMap.get("number");
       
       return currencyManager;
       
   }
}
