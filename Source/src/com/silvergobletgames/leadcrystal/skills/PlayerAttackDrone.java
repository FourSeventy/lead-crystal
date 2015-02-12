package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.EntityEffect;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Anchorable;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageEffect;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Circle;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.StateEffect;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.graphics.*;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.ArrayList;
import java.util.Random;
import net.phys2d.raw.*;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Polygon;

/**
 * 
 * Name: Guard
 * Cost:
 * Cooldown: 30 seconds
 * Description: While active, you take 50% damage.  For the first second that Guard is active, you take no damage.
 */
public class PlayerAttackDrone extends Skill
{
    
    public PlayerAttackDrone()
    {
        //super constructor
        super(SkillID.PlayerAttackDrone,SkillType.OFFENSIVE,ExtendedImageAnimations.SPELLATTACK,1800,Integer.MAX_VALUE);

        //set the name description image and unlock cost
        this.skillName = "Attack Droid";
        this.skillDescription = "Spawns a droid that flies around with you shooting at enemies.";
        this.skillDamageDescription = "Damage: 3-5";
        this.icon = new Image("attackDroidIcon.png");      
        this.unlockCost = 2;
        
    }
    
    public void use(Damage damage, SylverVector2f origin) 
    {
      
        //create drone and put it in the world
        Image img = new Image("droid.png");
        img.setScale(1f);
        Drone drone = new Drone(img,new StaticBody(new Circle(20)),(PlayerEntity)this.user,damage);
        user.getOwningScene().add(drone,Layer.MAIN);
        
        //play sound
        Sound sound = Sound.locationSound("buffered/jump.ogg", user.getPosition().x, user.getPosition().y, false, .8f, 1.4f);
        user.getOwningScene().add(sound);
        
    }
    
    
    
    
    
    private class Drone extends Entity
    {
        private PlayerEntity playerReference;
        private Damage passthroughDamage;
               
        //wander x and y offsets
        private float wanderX = 0; boolean right = true;
        private float wanderY = 5; boolean up = true;
        
        //skill cooldown
        int skillCooldown = 30;
        
        //target
        CombatEntity target;
        
        //timer
        private int lifeTime =1440;
        
        public Drone(Image image, Body body, PlayerEntity playerReference,Damage passthroughDamage)
        {
            super(image,body);        
            this.playerReference = playerReference;
            
            // no collision
            body.setBitmask(Entity.BitMasks.NO_COLLISION.value);
            
            //initial position
            this.setPosition(playerReference.getPosition().x, playerReference.getPosition().y + 100);
            
            this.passthroughDamage = passthroughDamage;
                      
        }
        
        public void update()
        {
            super.update();
                       

            // x wandering 
            if(right)
            {
                wanderX += .125;
                if(wanderX > 9 || Math.random() > .995f)
                    right = false;
            }
            else
            {
                wanderX -= .125;
                if(wanderX < -9  ||  Math.random() > .995f)
                    right = true;
            }

            //y wandering
            if(up)
            {
                wanderY += .125;
                if(wanderY > 9 || Math.random() > .995f)
                    up = false;
            }
            else
            {
                wanderY -= .125;
                if(wanderY < -9 || Math.random() > .995f)
                    up = true;
            }                           
            
            //follow player           
            this.setPosition(playerReference.getPosition().x + wanderX, playerReference.getPosition().y + 100 + wanderY);

            
            
            //============
            // Targeting
            //============
            
            //if we dont have a target aquire one
            //get all the FIGHTER's in the players scene
            ArrayList<SceneObject> fighters = playerReference.getOwningScene().getSceneObjectManager().get(ExtendedSceneObjectGroups.FIGHTER);

            //get the closest one and target it
            float distance = Float.MAX_VALUE;
            NonPlayerEntity closestNpe = null;
            for(SceneObject so: fighters)
            {
                NonPlayerEntity npe = (NonPlayerEntity)so;
                if(playerReference.distanceAbs(npe) < distance)
                {
                    distance = playerReference.distanceAbs(npe);
                    closestNpe = npe;
                }
            }
            this.target = closestNpe;

            
            //if our target is too far away or goes out of LOS set our target to null
            if(this.target != null)
            {
                if(playerReference.distanceAbs(target) > 1000 || target.getCombatData().isDead())
                    this.target = null;
            }
            
            //if skill is off cooldown, shoot at target
            skillCooldown--;
            if(skillCooldown <= 0)
            {
                if(this.target != null && this.checkLOS())
                    this.pew();
                
                skillCooldown = 30;
            }
            
            //lifetime
            this.lifeTime--;
            if(lifeTime <= 0)
                this.removeFromOwningScene();
        }
        
        public void pew()
        {
            Random r = SylverRandom.random;
            //set damage
            Damage damage = new Damage(this.passthroughDamage);
            //set damage
            int min = 3; 
            int max = 5;
            float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
            damage.getAmountObject().adjustBase(damageAmout);
            damage.setType(Damage.DamageType.PHYSICAL);    
            
            //add brightness effect to damage
            Float[] points = {1f,2f,1f};
            int[] durations = {10,5};
            ImageEffect brightnessEffect = new MultiImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, points,durations);
            damage.addImageEffect(brightnessEffect);

            //build body of the lazer
            Body body = new Body(new Box(10,4f), 1);
            Image img = new Image("dashparticle.png");
            img.setAnchor(Anchorable.Anchor.LEFTCENTER);
            img.setDimensions(60, 10f);
            img.setColor(new Color(3f,3f,.5f,1f)); 
            LaserHitbox laser = new LaserHitbox(damage, body, img, user);        
            laser.setImageOffset(new Vector2f(-10,0));

            //Get target X and Y
            float targetX = this.target.getPosition().x;
            float targetY = this.target.getPosition().y;

            //Get user X and Y
            float userX = this.getPosition().x ;
            float userY = this.getPosition().y;

            //get vector to target
            Vector2f vectorToTarget = new Vector2f(targetX - userX, targetY - userY);
            vectorToTarget.normalise();

            //calculate force for the bullet
            float xforce = 4000*vectorToTarget.x;
            float yforce = 4000*vectorToTarget.y;

            //determine angle for the image
            float theta = (float)Math.acos(vectorToTarget.dot(new Vector2f(1,0)));
            if(targetY < userY)
                theta = (float)(2* Math.PI - theta);

            //Dispense laser into the world
            laser.setPosition(this.getPosition().x + 10 + vectorToTarget.x * 25, this.getPosition().y + vectorToTarget.y * 25);
            laser.getBody().addForce(new Vector2f(xforce ,yforce));
            laser.getBody().setRotation((float)theta);
            laser.getImage().setAngle((float)(theta * (180f/Math.PI))); 
            user.getOwningScene().add(laser,Layer.MAIN);
            
            //play sound
            Sound sound = Sound.locationSound("buffered/smallLaser.ogg", this.getPosition().x, this.getPosition().y, false, 1f,1f);
            this.getOwningScene().add(sound);
        }
        
        public boolean checkLOS()
        {
            //==========LOS check=================
            
            //make line from self to target
            Vector2f point1 = new Vector2f(this.getPosition().x, this.getPosition().y);
            Vector2f point2 = new Vector2f(this.getPosition().x +5, this.getPosition().y +5);
            Vector2f point3 = new Vector2f(target.getPosition().x +5, target.getPosition().y +5);
            Vector2f point4 = new Vector2f(target.getPosition().x , target.getPosition().y);
            Vector2f[] vectorArray = {point1,point2,point3,point4};
            Polygon line = new Polygon(vectorArray);   
            Body lineBody = new StaticBody(line);
            lineBody.setBitmask(Entity.BitMasks.COLLIDE_WORLD.value);
            
            
            //get list of LOS blocking terrain
            ArrayList<SceneObject> terrain = this.getOwningScene().getSceneObjectManager().get(ExtendedSceneObjectGroups.TERRAIN);
            
            //collide everything
            for(SceneObject object: terrain)
            {
                WorldObjectEntity blocker = (WorldObjectEntity)object;
                
                Contact[] contacts = new Contact[10];
                for (int i=0;i<10;i++) {
			contacts[i] = new Contact();
		}
                int numContacts = Collide.collide(contacts,blocker.getBody() , lineBody, 1);
                
                if(numContacts > 0)
                {
                    return false;
                }
            }
            

            return true;
        }
        
    }

    
    public static class LaserHitbox extends HitBox
    {

        
         public LaserHitbox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
            
         }

         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 
             
             //laser bits
             if(!(other instanceof HitBox))
             {
                 //get angle of impact
                 Vector2f normalVector = (Vector2f)event.getNormal();
                 normalVector =normalVector.negate();
                 Vector2f xVector = new Vector2f(1,0);
                 float angle = (float)(Math.acos(normalVector.dot(xVector) ) * 180 / Math.PI);
                 if(normalVector.getY() < 0)
                     angle += 180;
                 
                 //make emitter
                 PointParticleEmitter emitter = new LeadCrystalParticleEmitters.LaserBitsEmitter();
                 emitter.setPosition(event.getPoint().getX(), event.getPoint().getY());
                 emitter.setDuration(1);
                 emitter.setAngle(angle);
                 emitter.setParticlesPerFrame(5);
                 emitter.setColor(new Color((61f/255f) * 4f,(40f/255f) * 4f,(86f/255f) * 4f));
                 emitter.setSize(3);                
                 owningScene.add(emitter,Layer.MAIN);
                 
             }

                         
             //remove if we hit a world object, or an enemy
             if(other instanceof WorldObjectEntity || other instanceof CombatEntity)
             {
                 //play sound
                Sound sound = Sound.locationSound("buffered/smallLaser.ogg", this.getPosition().x, this.getPosition().y, false, .5f,2);
                this.getOwningScene().add(sound);
        
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
         }
         
    }
}
