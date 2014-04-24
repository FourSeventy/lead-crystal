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
    
    //The entity that I belong to.
    protected CombatEntity owningEntity;
    
    /**
     * Takes just text.
     * @param txt 
     */
    public CombatText(Damage damage, CombatEntity owningEntity, Scene owningScene)
    {
        super("", LeadCrystalTextType.COMBAT);
        this.owningScene = owningScene;
        this.amount = Math.abs(damage.getAmount());
        
        String damageString = Float.toString(this.amount);
//        if(this.amount >= 1)
//        {
//            damageString = new DecimalFormat("#").format(this.amount); 
//        }
//        else
//        {
//            damageString = new DecimalFormat(".##").format(this.amount); 
//        }
        
        
        
        
        this.setText(damageString);
        this.setAnchor(Anchor.CENTER);
        this.damage = damage;
        this.lifetime = 50;
        this.owningEntity = owningEntity;
        this.crit = damage.isCrit();
        this.assignColor(damage);
        this.assignPosition(owningEntity);
        
        float theta = owningEntity.nextDamageDirection();
        float vx = 65*(float)Math.cos(theta * Math.PI / 180);
        if (theta < 0)
            vx = -vx;
        //Initial velocities and fade times.
        if (damage.getType() == Damage.DamageType.BURN){
            this.setVelocity(new Vector2f(0, -40 + owningEntity.getBody().getVelocity().getY())); 
        }
        else{               
            this.setVelocity(new Vector2f((int)vx + owningEntity.getBody().getVelocity().getX(), 78 + owningEntity.getBody().getVelocity().getY()));
            if (!damage.isCrit())
                this.setAngularVelocity((float)(-vx/1.2));
        }
        if (damage.isCrit())
            this.setFadeTime(25);
        else
            this.setFadeTime(20);
    }
    
    /**
     * Initializes the position.
     * @param c 
     */
    private void assignPosition(CombatEntity c){
        this.setPositionAnchored(c.getPosition().x, c.getPosition().y + (int)c.getBody().getShape().getBounds().getHeight()/3) ;
    }
    
    /**
     * Determines and assigns a color to this text based on the damage type.
     * @param d
     * @return 
     */
    private void assignColor(Damage d)
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
        else if (d.getAmount() < 0) //ABSORB
        {
            this.setText("Absorb! " + text);
            this.setScale(.7f);
            this.setLifetime(30);
            this.setFadeTime(15);
            c = new Color(0,.7f,.2f,1f);
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
        setColor(c);
    }
    
    /** 
     * Sets when this text will begin to fade, and it will fade over the remainder of its life.
     * @param fadeTime 
     */
    public void setFadeTime(int fadeTime){
        this.fadeAt = fadeTime;
    }
    
    public void setVelocity(Vector2f v){
        this.velocity = v;
    }
    
    public void setAngularVelocity(float av){
        this.angularVelocity = av;
    }
    
    public final void setLifetime(int life){
        this.lifetime = life;
    }
    
    public void update(){
        if (lifetime <= 0 || scale <= 0)        
            owningScene.remove(this);
        else
        {
            //Size control
            if (crit)
            {
                this.color = new Color(Color.yellow);
                
                if (lifetime >= fadeAt)
                    scaleVelocity = 4f;
                else
                    scaleVelocity = -3f;                
            }    
            else
            {
                if (lifetime <= fadeAt)
                    scaleVelocity = -.667f;                               
            }
            
            //Angle and color
            if (lifetime <= fadeAt){
                if (fadeAmt == 0)
                    fadeAmt = color.a/fadeAt;
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
}
