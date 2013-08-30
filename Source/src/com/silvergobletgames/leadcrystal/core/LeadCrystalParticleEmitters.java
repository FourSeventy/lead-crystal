package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ParticleEmitter;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;

/**
 *
 * @author Mike
 */
public class LeadCrystalParticleEmitters 
{
    public static class RocketSmokeEmitter extends ParticleEmitter
    {
        public RocketSmokeEmitter()
        {
            super();
            this.setImage(new Image("Smoke.png"));
            this.setParticlesPerFrame(3); 
            this.useRelativePositioning(false);
        }
        
        public Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(position.x+(rand.nextFloat() - .5f)*30 , position.y+(rand.nextFloat() - .5f)*30);
            float magnitude =  rand.nextFloat() + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 90;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            Color color = new Color(Color.randomGray(.6f));
            color.a = .15f;
            int ttl = 100 + (int)(Math.random()*20);
            return new Particle(pos, velocity, acceleration, color, .8f, 1f/ttl,  ttl);
        }      
    }
    
    public static class SmokeEmitter extends ParticleEmitter
    {
        public SmokeEmitter()
        {
            this.setImage(new Image("Smoke.png"));
        }
        
        public ParticleEmitter.Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(position.x+(rand.nextFloat() - .5f)*30 , position.y+(rand.nextFloat() - .5f)*30);
            float magnitude =  rand.nextFloat() + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 90;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            Color color = new Color(Color.randomGray(.6f));
            color.a = .06f;
            int ttl = 100 + (int)(Math.random()*20);
            return new ParticleEmitter.Particle(pos, velocity, acceleration, color, .8f, 1f/ttl, ttl);
        }      
    }
    
    public static class RocketExplosionEmitter extends ParticleEmitter
    {
        public RocketExplosionEmitter()
        {
            super();
            this.setImage(new Image("explosionParticle.png"));
            this.setParticlesPerFrame(2); 
            this.useRelativePositioning(false);
        }
        
        public ParticleEmitter.Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(position.x+(rand.nextFloat() - .5f)*30 , position.y+(rand.nextFloat() - .5f)*30);
            float magnitude =  rand.nextFloat()/3 + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 720;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            Color color = new Color(.8f,.8f,.8f,.75f);
            float ttl = 80 + (int)(Math.random()*20);
            return new ParticleEmitter.Particle(pos, velocity, acceleration, color, .6f, .5f/ttl, (int)ttl);
        }      
    }
    
    public static class BlueLaserBitsEmitter extends ParticleEmitter
    {
        public BlueLaserBitsEmitter()
        {
            super();
        
        }
    
        public ParticleEmitter.Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(position.x, position.y);
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 90;
            SylverVector2f velocity = new SylverVector2f((float)Math.random() *5 * (float)Math.cos(randomedAngle * Math.PI/180) , (float)Math.random()*5 * (float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,-.1f);
            Color color = new Color(.5f,.5f,3f,1f);
            if(SylverRandom.random.nextFloat() < .5){
                color.r += .3f;
            }
            if(SylverRandom.random.nextFloat() < .5){
                color.b += .4f;
            }
            color.a = 1f;
            int ttl = 40 + (int)(Math.random()*20);
            return new ParticleEmitter.Particle( pos, velocity, acceleration, color, .18f, -.1f/ttl, ttl);
        }       
    }
    
    public static class LaserBitsEmitter extends ParticleEmitter
    {
        public LaserBitsEmitter()
        {
            super();

        }
    
        public ParticleEmitter.Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(position.x, position.y);
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 90;
            SylverVector2f velocity = new SylverVector2f((float)Math.random() *5 * (float)Math.cos(randomedAngle * Math.PI/180) , (float)Math.random()*5 * (float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,-.1f);
 
            Color color = new Color(3f,.5f,.5f,1f);
            if(Math.random() < .5){
                color.r += 1f;
            }
            color.a = 1f;
            int ttl = 40 + (int)(Math.random()*20);
            return new ParticleEmitter.Particle( pos, velocity, acceleration, color, .18f, -.1f/ttl, ttl);
        }       
    }
    
    public static class BloodEmitter extends ParticleEmitter
    {
        public BloodEmitter()
        {
            super();
            
            image = new Image("Bleed1.png");
        }
   
        public ParticleEmitter.Particle buildParticle()
        { 
             this.setParticlesPerFrame(Math.max(this.getParticlesPerFrame() * (float)Math.min(this.getRemainingDuration()/ this.getDuration(),1),.30f ));
            
                   Random rand = SylverRandom.random;
                   SylverVector2f pos = new SylverVector2f(position.x, position.y);
                   float magnitude =  rand.nextFloat() + .3f;
                    float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 90;
                    SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
                   SylverVector2f acceleration = new SylverVector2f(0,-.1f);

                   Color color = new Color(1f,.1f,.1f,1f);
                   if(Math.random() < .5){
                       color.r += 1f;
                   }
                   color.a = 1f;
                   int ttl = 45 + (int)(Math.random()*20);
                   return new ParticleEmitter.Particle( pos, velocity, acceleration, color, .24f, -.1f/ttl, ttl);
               }        
        
    }
    
    public static class GreenBloodEmitter extends ParticleEmitter
    {
        public GreenBloodEmitter()
        {
            super();
            
            image = new Image("Bleed1.png");
        }
   
        public ParticleEmitter.Particle buildParticle()
        { 
            this.setParticlesPerFrame(Math.max(this.getParticlesPerFrame() * (float)Math.min(this.getRemainingDuration()/ this.getDuration(),1),.30f ));
            
                   Random rand = SylverRandom.random;
                   SylverVector2f pos = new SylverVector2f(position.x, position.y);
                   float magnitude =  rand.nextFloat() + .3f;
                    float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 90;
                    SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
                   SylverVector2f acceleration = new SylverVector2f(0,-.1f);

                   Color color = new Color(.1f,4f,.1f,1f);
                   if(Math.random() < .5){
                       color.g += 1f;
                   }
                   color.a = 1f;
                   int ttl = 45 + (int)(Math.random()*20);
                   return new ParticleEmitter.Particle( pos, velocity, acceleration, color, .24f, -.1f/ttl, ttl);
               }        
        
    }
    
    public static class GreenGooEmitter extends ParticleEmitter
    {
        public GreenGooEmitter()
        {
            super();
            
            image = new Image("Bleed1.png");
            this.setParticlesPerFrame(4);
        }

        public ParticleEmitter.Particle buildParticle()
        { 
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(position.x+(rand.nextFloat() - .5f)*30 , position.y+(rand.nextFloat() - .5f)*30);
            float magnitude =  rand.nextFloat() + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 90;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,-.1f);
            Color color = new Color(.1f,4f,.1f,1f);
            if(Math.random() < .5){
                color.g += 1f;
            }
            color.a = 1f;
            int ttl = 5 + (int)(Math.random()*10);
            return new ParticleEmitter.Particle( pos, velocity, acceleration, color, .3f, -.1f/ttl, ttl);
        }
                
    }
    
    public static class IceEmitter extends ParticleEmitter
    {
        public IceEmitter()
        {
            super();
            
            image = new Image("circle.png");
            this.setParticlesPerFrame(8);
        }

        public ParticleEmitter.Particle buildParticle()
        { 
            
            Random rand = SylverRandom.random;
            float posMag = (rand.nextFloat() - .5f)*300;
            float posAngle = (rand.nextFloat() - .5f) * 720;
            SylverVector2f pos = new SylverVector2f(position.x + posMag *(float)Math.cos(posAngle * Math.PI/180) , position.y + posMag *(float)Math.sin(posAngle * Math.PI/180));
            float magnitude =  rand.nextFloat() + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 720;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
      
            Color color = new Color(new Color(.8f,.8f,1.5f));
//            if(Math.random() < .5){
//                color.g += 1f;
//            }
            color.a = .7f;
            int ttl = 50 + (int)(Math.random()*10);
            return new ParticleEmitter.Particle( pos, velocity, acceleration,  color, 2f, -.1f/ttl, ttl);
        }
                
    }
    
    public static class SandSpurtEmitter extends ParticleEmitter
    {
        public SandSpurtEmitter()
        {
            super();
            this.setImage(new Image("smoke.png"));
            this.setParticlesPerFrame(10); 
            this.setAngle(90);
        }
        
        public ParticleEmitter.Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(position.x+(rand.nextFloat() - .5f)*30 , position.y);
            float magnitude =  rand.nextFloat()/3 + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 150;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            float angularVelocity = 0;
            Color color = new Color(.67f,.61f,.46f,.7f);
            float ttl = 40 + (int)(Math.random()*20);
            return new ParticleEmitter.Particle(pos, velocity, acceleration, color, .15f, 0, (int)ttl);
        }      
    }
    
    public static class SandSpawnEmitter extends ParticleEmitter
    {
        public SandSpawnEmitter()
        {
            super();
            this.setImage(new Image("smoke.png"));
            this.setParticlesPerFrame(15); 
            this.setAngle(90);
            this.setDuration(30);
        }
        
        public ParticleEmitter.Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(position.x+(rand.nextFloat() - .5f)*70 , position.y);
            float magnitude =  rand.nextFloat() + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 150;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            Color color = new Color(.67f,.61f,.46f,.7f);
            float ttl = 40 + (int)(Math.random()*10);
            return new ParticleEmitter.Particle(pos, velocity, acceleration, color, .70f, 0, (int)ttl);
        }      
    }
    
    public static class RockChipsEmitter extends ParticleEmitter
    {
        public RockChipsEmitter()
        {
            super();
        
            image = new Image("rockParticle.png");
            this.setParticlesPerFrame(.5f); 

        }
    
        public ParticleEmitter.Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(position.x, position.y);
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 90;
            SylverVector2f velocity = new SylverVector2f((float)Math.random() *5 * (float)Math.cos(randomedAngle * Math.PI/180) , (float)Math.random()*5 * (float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,-.1f);
  
            Color color = new Color(.5f,.5f,.5f,1f);
            int ttl = 40 + (int)(Math.random()*20);
            return new ParticleEmitter.Particle( pos, velocity, acceleration, color, .18f, 0, ttl);
        }       
    }
    
    public static class SandWindEmitter extends ParticleEmitter
    {
        public SandWindEmitter()
        {
            super();
            this.setImage(new Image("explosionParticle.png"));
            this.setParticlesPerFrame(2); 
        
        }
        
        public ParticleEmitter.Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(position.x+(rand.nextFloat() - .5f)*30 , position.y+(rand.nextFloat() - .5f)*30);
            float magnitude =  rand.nextFloat()/3 + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 720;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            float angularVelocity = 0;
            Color color = new Color(.8f,.8f,.8f,.75f);
            float ttl = 80 + (int)(Math.random()*20);
            return new ParticleEmitter.Particle(pos, velocity, acceleration, color, .6f, .5f/ttl, (int)ttl);
        }      
    }
    
}
