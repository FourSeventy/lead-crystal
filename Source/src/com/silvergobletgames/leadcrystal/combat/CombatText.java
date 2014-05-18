package com.silvergobletgames.leadcrystal.combat;

import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.sylver.core.Scene;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Text;
import java.text.DecimalFormat;
import net.phys2d.math.Vector2f;

/**
 * Text object that we can give "velocities" to for each of its different properties.
 * 
 * @author Justin Capalbo
 */
public class CombatText extends Text
{
    protected Vector2f velocity = new Vector2f(0,0);
    protected float angularVelocity = 0;
    protected float scaleVelocity = 0;
    protected int fadeAt = 0;
    protected int lifetime = 0;
    protected float fadeAmt;
    
    protected Damage damage;
    protected float amount;
    protected boolean crit;
    
    protected CombatEntity owningEntity;
    
    /**
     * 
     * @param damage
     * @param owningEntity
     * @param owningScene 
     */
    public CombatText(Damage damage, CombatEntity owningEntity, Scene owningScene)
    {
        super("", LeadCrystalTextType.COMBAT);
        this.owningScene = owningScene;

        //setting amount
        this.amount = Math.abs(damage.getAmount());        
        String damageString = Float.toString(this.amount);
        if(this.amount >= 1)
        {
            damageString = new DecimalFormat("#").format(this.amount); 
        }
        else
        {
            damageString = new DecimalFormat(".##").format(this.amount); 
        }  
        this.setText(damageString);
        
        
        this.setAnchor(Anchor.CENTER);
        this.damage = damage;
        this.lifetime = 50;
        this.owningEntity = owningEntity;
        this.crit = damage.isCrit();
        this.determineColor(damage);
        this.fadeAt = 22;
        
        if(damage.isCrit())
        {
            this.setTextType(LeadCrystalTextType.COMBAT_CRIT);
            this.setScale(.5f); 
        }
        
        //set position
        this.setPositionAnchored(owningEntity.getPosition().x, owningEntity.getPosition().y + (int)owningEntity.getBody().getShape().getBounds().getHeight()/3) ;
    
        
        float theta = owningEntity.nextDamageDirection();
        float vx = 65*(float)Math.cos(theta * Math.PI / 180);
        if (theta < 0)
            vx = -vx;
        //Initial velocities and fade times.
        if (damage.getType() == Damage.DamageType.BURN)
        {
            this.velocity =new Vector2f(0, -40 + owningEntity.getBody().getVelocity().getY()); 
        }
        else
        {               
            this.velocity =new Vector2f((int)vx + owningEntity.getBody().getVelocity().getX(), 78 + owningEntity.getBody().getVelocity().getY());
            if (!damage.isCrit())
            {
                this.angularVelocity =(float)(-vx/1.2);
            }
        }
    }

    
    /**
     * Determines and assigns a color to this text based on the damage type.
     * @param d
     * @return 
     */
    private void determineColor(Damage d)
    {
        Color c;
        
        
        if(d.getType() == Damage.DamageType.PHYSICAL)
        {
            c = new Color(1f,1f,1f,1f);
        }
        else if (d.getType() == Damage.DamageType.HEAL) 
        {
            c = new Color(0,1f,0,1f);
        }     
        else if (d.getType() == Damage.DamageType.BURN)
        {
            c = new Color(1f,.60f,0,1f);
        }
        else if(d.getType() == Damage.DamageType.FROST)
        {
            c = new Color(Color.blue);
        }
        else if(d.getType() == Damage.DamageType.SHOCK)
        {
             c = new Color(Color.yellow);
        }
        else if(d.getType() == Damage.DamageType.POISON)
        {
            c = new Color(181,47,177);
        }
        else
        {
            c = new Color(1f,1f,1f,1f);
        }
        
        if(d.isCrit())
        {
            c = new Color(Color.yellow);
        }
        setColor(c);
    }

    
    public void update()
    {
        if (lifetime <= 0 || scale <= 0) 
        {
            owningScene.remove(this);
            return;
        }
        

        //Size control
        if (crit)
        {
            if (lifetime >= fadeAt)
                scaleVelocity = 3.5f;
            else
                scaleVelocity = -2.5f;                
        }    
        else
        {
            if (lifetime <= fadeAt)
                scaleVelocity = -.667f;                               
        }

        //Angle and color
        if (lifetime <= fadeAt)
        {
            if (fadeAmt == 0)
            {
                fadeAmt = color.a/fadeAt;
            }
            angularVelocity /= 1.1;
            setColor(new Color(this.color, Math.max(this.color.a-fadeAmt, 0)));
        }

        //Directional velocity control
        if (damage.getType() == Damage.DamageType.BURN)
        {                
            velocity.y -= 1;                
            velocity.x = (float)Math.sin(lifetime/2)*70;
        }
        else
        {                                
            velocity.y -= 2.1;
        }

        //Step
        this.position.x += velocity.x / 60.0;
        this.position.y += velocity.y / 60.0;
        angle += angularVelocity / 60.0;
        scale += scaleVelocity / 60.0;

        //Decrement
        lifetime--;

    }
}
