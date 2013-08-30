
package com.silvergobletgames.leadcrystal.skills;

import com.silvergobletgames.leadcrystal.entities.CombatEntity;
import com.silvergobletgames.leadcrystal.entities.HitBox;
import com.silvergobletgames.leadcrystal.entities.Entity;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity;
import com.silvergobletgames.leadcrystal.entities.PlayerEntity;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.*;
import java.util.Random;
import java.util.UUID;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Box;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectGroups;
import com.silvergobletgames.leadcrystal.core.LeadCrystalParticleEmitters.SmokeEmitter;
import com.silvergobletgames.leadcrystal.entities.*;
import com.silvergobletgames.leadcrystal.scenes.GameServerScene;
import com.silvergobletgames.sylver.core.SceneObject;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.ArrayList;
import net.phys2d.raw.*;
import net.phys2d.raw.shapes.Line;
import net.phys2d.raw.shapes.Polygon;


/**
 * Description: Shoots a laser that damages enemies when struck. Always crits. Does more damage the farther away
 *              the target is.
 * Damage: 10-15
 * Cooldown: 10 seconds
 * @author Mike
 */
public class PlayerSnipe extends Skill 
{

    
    /**
     * Here we define the skill types and set up other initial params if any
     */
    public PlayerSnipe() 
    {
        //super constructor
        super(SkillID.PlayerSnipe,SkillType.OFFENSIVE,ExtendedImageAnimations.RANGEDATTACK,300, Integer.MAX_VALUE);

        //set the skillID and the name
        this.icon = new Image("snipeIcon.jpg");
        this.skillName = "Snipe";
        
        this.skillDescription = "Shoots a long range powerful beam of energy that does more damage the farther away"
                                 + " the target is. Snipe will always cause a critical hit.";
        this.unlockCost = 1;

    }


    /**
     * Sets up the the force vector, spends the resource.
     */
    public void use(Damage damage, SylverVector2f origin) 
    {
        Random r = SylverRandom.random;
        
        PlayerEntity player = (PlayerEntity) user;
        
        //set damage
        int min = 19; 
        int max = 22;
        float damageAmout =  min + r.nextInt(max+1 -min); // roll at number from min to max;
        damage.getAmountObject().adjustBase(damageAmout);
        damage.setType(Damage.DamageType.PHYSICAL);      
        damage.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.BRIGHTNESS, 10, 0.0f, 1f));
        
        //make the damage always be a crit
        if(!damage.isCrit())
        {
            damage.setCrit(true);
            damage.getAmountObject().adjustPercentModifier(user.getCombatData().critModifier.getTotalValue());
        }
        
        //======================
        // Get Vector To Target
        //======================
        
        //Get target X and Y
        float targetX = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationX;
        float targetY = ((GameServerScene)player.getOwningScene()).clientsInScene.get(UUID.fromString(player.getID())).currentInputPacket.mouseLocationY;
        
        //Get user X and Y
        float userX = origin.x;
        float userY = origin.y;
        
        //get vector to target
        Vector2f vectorToTarget = new Vector2f(targetX - userX, targetY - userY);
        vectorToTarget.normalise();
        
        //===============================
        // Determine Rail Beam End Point
        //===============================
        
        SylverVector2f endPoint = new SylverVector2f((vectorToTarget.x * 2000) + userX , (vectorToTarget.y * 2000) + userY);
        ArrayList<SylverVector2f> potentialEnds = new ArrayList();
        //collision body
        //make line from self to target
        Vector2f h = new Vector2f(vectorToTarget );
        h.scale(2000);
        Line line = new Line(new Vector2f(0, 0),h);
 
        Body lineBody = new StaticBody(line);
        lineBody.setPosition(userX, userY);
        lineBody.setBitmask(Entity.BitMasks.COLLIDE_WORLD.value);
        lineBody.setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value);        
        
        //get list terrain
        ArrayList<SceneObject> terrain = user.getOwningScene().getSceneObjectManager().get(ExtendedSceneObjectGroups.TERRAIN);

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
                for(int i = 0; i < numContacts; i++)
                    potentialEnds.add( new SylverVector2f(contacts[i].getPosition().getX(),contacts[i].getPosition().getY()));
            }
            
        }  
        
        //get list terrain
        ArrayList<SceneObject> NPEs = user.getOwningScene().getSceneObjectManager().get(ExtendedSceneObjectGroups.NPE);

        //collide everything
        for(SceneObject object: NPEs)
        {
            NonPlayerEntity enemy = (NonPlayerEntity)object;

            Contact[] contacts = new Contact[10];
            for (int i=0;i<10;i++) {
                    contacts[i] = new Contact();
            }
            int numContacts = Collide.collide(contacts,enemy.getBody() , lineBody, 1);

            if(numContacts > 0)
            {
                for(int i = 0; i < numContacts; i++)
                    potentialEnds.add( new SylverVector2f(contacts[i].getPosition().getX(),contacts[i].getPosition().getY()));
            }
        }  
        
        //find closest end and make that the endPoint
        for(SylverVector2f end: potentialEnds)
        { 
//            //debug
//            Image hh = new Image("black.png");
//            hh.setDimensions(10, 10);
//            hh.setPosition(end.getX(), end.getY());
//            user.getOwningScene().add(hh, Layer.MAIN);
                               
            if(end.distance(user.getPosition())< endPoint.distance(user.getPosition()))
                endPoint = end;
        }
            
        
        //=======================
        // Construct Rail Hitbox
        //=======================
                
        //build body of the snipe 
        Body railBody = new StaticBody(new Box(20,20));
        railBody.setBitmask(Entity.BitMasks.NO_COLLISION.value);
        railBody.setOverlapMask(Entity.OverlapMasks.NPE_TOUCH.value); 
        SnipeHitBox snipe = new SnipeHitBox(damage, railBody, new Image("blank.png"), user);  
        
        //Dispense snipe hitbox into the world
        snipe.setPosition(endPoint.getX(), endPoint.getY());
        user.getOwningScene().add(snipe,Layer.MAIN);
        
        //determine angle for the image
        float theta = (float)Math.acos(vectorToTarget.dot(new Vector2f(1,0)));
        if(targetY < userY)
            theta = (float)(2* Math.PI - theta);
        
        //rail image
        Image img = new Image("rail.png");
        img.setDimensions((float)Math.sqrt(Math.pow(endPoint.x - userX,2) + Math.pow(endPoint.y - userY,2)), 15f);
        img.setRotationPoint(0, .5f);
        img.setPosition(userX,userY);
        img.setAngle((float)(theta * (180f/Math.PI)));
        img.addImageEffect(new ImageEffect(ImageEffect.ImageEffectType.DURATION, 60, 0, 0));
        
        int[] durations = {30,30};
        Color[] colors = { new Color(1,3,1,1),new Color(1,1,1,1), new Color(1,1,1,0) };
        img.addImageEffect(new MultiImageEffect(ImageEffect.ImageEffectType.COLOR, colors, durations));
        user.getOwningScene().add(img,Layer.MAIN);
        
        
         //play sound
        Sound sound = Sound.locationSound("buffered/bigLaser.ogg", user.getPosition().x, user.getPosition().y, false, 1);
        user.getOwningScene().add(sound);



        
    }    
    
    private class SnipeHitBox extends HitBox
    {

         public SnipeHitBox(Damage d, Body b, Image i, Entity user)
         { 
            super(d, b, i, user); 
         }
         
         public void collidedWith(Entity other, CollisionEvent event)
         {
             super.collidedWith(other,event); 
             
             //remove if we hit a world object
             if(other instanceof WorldObjectEntity || other instanceof CombatEntity)
             {
                this.getBody().setVelocity(new Vector2f(0,0));
                this.removeFromOwningScene();
             }
         }
    }
}
