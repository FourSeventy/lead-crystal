package com.silvergobletgames.leadcrystal.core;

import com.silvergobletgames.sylver.graphics.AbstractParticleEmitter;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.Image;
import com.silvergobletgames.sylver.graphics.ImageParticleEmitter;
import com.silvergobletgames.sylver.graphics.PointParticleEmitter;
import com.silvergobletgames.sylver.util.SylverRandom;
import com.silvergobletgames.sylver.util.SylverVector2f;
import java.util.Random;

/**
 *
 * @author Mike
 */
public class LeadCrystalParticleEmitters 
{
    
   
    public static class JetpackEmitter extends ImageParticleEmitter
    {
        public JetpackEmitter()
        {
            super(new Image("explosionParticle.png"));
            this.setParticlesPerFrame(2); 
            this.setRelativePositioning(false);
        }
        
        public AbstractParticleEmitter.Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x+(rand.nextFloat() - .5f)*50 , this.getPosition().y - 100+(rand.nextFloat() - .5f)*30);
            float magnitude =  rand.nextFloat()/3 + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 720;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            Color color = new Color(.8f,.8f,.8f,.75f);
            float ttl = 80 + (int)(Math.random()*20);
            return new AbstractParticleEmitter.Particle(pos, velocity, acceleration, color, .6f, .5f/ttl, (int)ttl);
        }      
    }
    
    
        
    
    
    public static class BloodEmitter extends ImageParticleEmitter
    {
        public BloodEmitter()
        {
            super(new Image("Bleed1.png"));
        }
   
        public Particle buildParticle()
        { 
             this.setParticlesPerFrame(Math.max(this.getParticlesPerFrame() * (float)Math.min(this.getRemainingDuration()/ this.getDuration(),1),.30f ));
            
                   Random rand = SylverRandom.random;
                   SylverVector2f pos = new SylverVector2f(this.getPosition().x, this.getPosition().y);
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
                   return new  Particle( pos, velocity, acceleration, color, .24f, -.1f/ttl, ttl);
               }        
        
    }
    
    public static class GreenBloodEmitter extends ImageParticleEmitter
    {
        public GreenBloodEmitter()
        {
            super(new Image("Bleed1.png"));

        }
   
        public  Particle buildParticle()
        { 
            this.setParticlesPerFrame(Math.max(this.getParticlesPerFrame() * (float)Math.min(this.getRemainingDuration()/ this.getDuration(),1),.30f ));
            
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x, this.getPosition().y);
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
            return new  Particle( pos, velocity, acceleration, color, .24f, -.1f/ttl, ttl);
        }        
        
    }
    
    public static class GreenGooEmitter extends ImageParticleEmitter
    {
        public GreenGooEmitter()
        {
            super(new Image("Bleed1.png"));
 
            this.setParticlesPerFrame(4);
            
        }

        public  Particle buildParticle()
        { 
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x+(rand.nextFloat() - .5f)*30 , this.getPosition().y+(rand.nextFloat() - .5f)*30);
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
            return new  Particle( pos, velocity, acceleration, color, .3f, -.1f/ttl, ttl);
        }
                
    }
    
    
    public static class SandSpurtEmitter extends ImageParticleEmitter
    {
        public SandSpurtEmitter()
        {
            super(new Image("smallSmoke.png"));
            this.setParticlesPerFrame(10); 
            this.setAngle(90);
        }
        
        public  Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x+(rand.nextFloat() - .5f)*30 , this.getPosition().y);
            float magnitude =  rand.nextFloat()/3 + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 150;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            float angularVelocity = 0;
            Color color = new Color(.67f,.61f,.46f,.7f);
            float ttl = 40 + (int)(Math.random()*20);
            return new  Particle(pos, velocity, acceleration, color, .15f, 0, (int)ttl);
        }      
    }
    
    public static class SandSpawnEmitter extends ImageParticleEmitter
    {
        public SandSpawnEmitter()
        {
            super(new Image("smallSmoke.png"));
            this.setParticlesPerFrame(15); 
            this.setAngle(90);
            this.setDuration(30);
        }
        
        public  Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x+(rand.nextFloat() - .5f)*70 , this.getPosition().y);
            float magnitude =  rand.nextFloat() + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 150;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            Color color = new Color(.67f,.61f,.46f,.7f);
            float ttl = 40 + (int)(Math.random()*10);
            return new  Particle(pos, velocity, acceleration, color, .70f, 0, (int)ttl);
        }      
    }
    
    public static class RockChipsEmitter extends ImageParticleEmitter
    {
        public RockChipsEmitter()
        {
            super(new Image("rockParticle.png"));
        
            this.setParticlesPerFrame(.5f); 

        }
    
        public  Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x, this.getPosition().y);
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 90;
            SylverVector2f velocity = new SylverVector2f((float)Math.random() *5 * (float)Math.cos(randomedAngle * Math.PI/180) , (float)Math.random()*5 * (float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,-.1f);
  
            Color color = new Color(.5f,.5f,.5f,1f);
            int ttl = 40 + (int)(Math.random()*20);
            return new  Particle( pos, velocity, acceleration, color, .18f, 0, ttl);
        }       
    }
    
    public static class SandWindEmitter extends ImageParticleEmitter
    {
        public SandWindEmitter()
        {
            super(new Image("explosionParticle.png"));
            this.setParticlesPerFrame(2); 
        
        }
        
        public  Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x+(rand.nextFloat() - .5f)*30 , this.getPosition().y+(rand.nextFloat() - .5f)*30);
            float magnitude =  rand.nextFloat()/3 + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 720;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            float angularVelocity = 0;
            Color color = new Color(.8f,.8f,.8f,.75f);
            float ttl = 80 + (int)(Math.random()*20);
            return new  Particle(pos, velocity, acceleration, color, .6f, .5f/ttl, (int)ttl);
        }      
    }  
    
    public static class GroundFireEmitter1 extends ImageParticleEmitter
    {
        public GroundFireEmitter1()
        {
            super(new Image("groundFire2.png"));
            this.setParticlesPerFrame(.25f);
        }
        
        public Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x+(rand.nextFloat() - .5f)*60 , this.getPosition().y - 5 +(rand.nextFloat() - .5f)*8);
            float magnitude =  rand.nextFloat() + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 90;
            SylverVector2f velocity =new SylverVector2f(0,0);//new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            Color color = new Color(new Color(1f + (float)(Math.random() * 1.5f) ,1f+ (float)(Math.random() * 1.5f),1f));
            color.a = .2f;
            int ttl = 70 + (int)(Math.random()*15);
            return new Particle(pos, velocity, acceleration, color, .9f, .2f/ttl, ttl);
        }      
    }
    
    
    
    
    //=========
    // Good
    //=======
    public static class DashParticleEmitter extends ImageParticleEmitter
    {
        public DashParticleEmitter()
        {
            super(new Image("dashParticle.png"));
            this.setParticlesPerFrame(.5f);
        }
        
        
        public  Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x+(rand.nextFloat() - .5f)*100, this.getPosition().y+(rand.nextFloat() - .5f)*200);
            float randomedAngle = getAngle() - 180 + (rand.nextFloat() - .5f) * 15;
            SylverVector2f velocity = new SylverVector2f((float)Math.random() *5 * (float)Math.cos(randomedAngle * Math.PI/180) , (float)Math.random()*5 * (float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0f);
  
            Color color = new Color(1.2f,1.2f,1.8f,1f);
            int ttl = 20 + (int)(Math.random()*10);
            return new  Particle( pos, velocity, acceleration, color, .6f, 0, ttl);
        } 
        
    }
    
    public static class FlameStrikeParticleEmitter extends ImageParticleEmitter
    {
        public FlameStrikeParticleEmitter()
        {
            super(new Image("flamepuff.png"));
            this.setParticlesPerFrame(4);
        }
        
        
        public  Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x+(rand.nextFloat() - .5f)*100, this.getPosition().y+(rand.nextFloat() - .5f)*200);
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 15;
            SylverVector2f velocity = new SylverVector2f((float)Math.random() *5 * (float)Math.cos(randomedAngle * Math.PI/180) , (float)Math.random()*5 * (float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0f);
  
            Color color = new Color(1.8f,1.2f,1.2f,.5f);
            int ttl = 20 + (int)(Math.random()*10);
            return new  Particle( pos, velocity, acceleration, color, 1f, .05f/ttl, ttl);
        } 
        
    }
    
    public static class PoisonGasEmitter extends ImageParticleEmitter
    {
        public PoisonGasEmitter()
        {
            super(new Image("fireParticle.png"));
            this.setParticlesPerFrame(2); 
            this.setRelativePositioning(false);
        }
        
        public Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x+(rand.nextFloat() - .5f)*150 , this.getPosition().y+(rand.nextFloat() - .5f)*70);
            float magnitude =  rand.nextFloat()/3 + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 720;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            Color color = new Color(.1f,1f,.1f,.75f);
            float ttl = 420 + (int)(Math.random()*20);
            return new Particle(pos, velocity, acceleration, color, .8f, .20f/ttl, (int)ttl);
        }   
    }
    
    public static class LaserBitsEmitter extends PointParticleEmitter
    {
        public LaserBitsEmitter()
        {
            super(new Color(3f,.5f,.5f,1f),3);

        }
    
        public Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x, this.getPosition().y);
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 90;
            SylverVector2f velocity = new SylverVector2f((float)Math.random() *5 * (float)Math.cos(randomedAngle * Math.PI/180) , (float)Math.random()*5 * (float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,-.1f);
 
            Color color = new Color(this.getColor());
            if(rand.nextDouble() <= .166f)
            {
               float sign = rand.nextBoolean()? 1: -1;
               color.r = (float)(1+  sign* (.30f * rand.nextDouble())) * color.r;
            }
            if(rand.nextDouble() <= .166f)
            {
               float sign = rand.nextBoolean()? 1: -1;
               color.g = (float)(1+  sign *(.30f * rand.nextDouble())) * color.g;
            }
            if(rand.nextDouble() <= .166f)
            {
               float sign = rand.nextBoolean()? 1: -1;
               color.b = (float)(1+ sign *(.30f * rand.nextDouble())) * color.b;
            }
            
            color.a = 1f;
            int ttl = 40 + (int)(Math.random()*20);
            return new Particle( pos, velocity, acceleration, color, .18f, -.1f/ttl, ttl);
        }       
    }
    
    public static class RocketSmokeEmitter extends ImageParticleEmitter
    {
        public RocketSmokeEmitter()
        {
            super(new Image("smallSmoke.png"));
            this.setParticlesPerFrame(3); 
            this.setRelativePositioning(false);
        }
        
        public Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x+(rand.nextFloat() - .5f)*30 , this.getPosition().y+(rand.nextFloat() - .5f)*30);
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
    
    public static class SmokeEmitter extends ImageParticleEmitter
    {
        public SmokeEmitter()
        {
            super(new Image("smallSmoke.png"));
        }
        
        public Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x+(rand.nextFloat() - .5f)*30 , this.getPosition().y+(rand.nextFloat() - .5f)*30);
            float magnitude =  rand.nextFloat() + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 90;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            Color color = new Color(Color.randomGray(.6f));
            color.a = .06f;
            int ttl = 100 + (int)(Math.random()*20);
            return new Particle(pos, velocity, acceleration, color, .8f, 1f/ttl, ttl);
        }      
    }
    
    public static class RocketExplosionEmitter extends ImageParticleEmitter
    {
        public RocketExplosionEmitter()
        {
            super(new Image("fireParticle.png"));
            this.setParticlesPerFrame(2); 
            this.setRelativePositioning(false);
        }
        
        public Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x+(rand.nextFloat() - .5f)*30 , this.getPosition().y+(rand.nextFloat() - .5f)*30);
            float magnitude =  rand.nextFloat()/3 + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 720;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            Color color = new Color(.8f,.8f,.8f,.50f);
            float ttl = 80 + (int)(Math.random()*20);
            return new Particle(pos, velocity, acceleration, color, .25f, .15f/ttl, (int)ttl);
        }      
    }
    
    public static class BombExplosionEmitter extends ImageParticleEmitter
    {
        public BombExplosionEmitter()
        {
            super(new Image("fireParticle.png"));
            this.setParticlesPerFrame(5); 
            this.setRelativePositioning(false);
        }
        
        public Particle buildParticle()
        {
            if(this.particles.size() > 50)
            {
                this.setParticlesPerFrame(1);
            }
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x+(rand.nextFloat() - .5f)*45 , this.getPosition().y+(rand.nextFloat() - .5f)*55);
            float magnitude =  rand.nextFloat()/3 + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 720;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude * 2.5f *(float)Math.sin((rand.nextInt(65) +25) * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            Color color = new Color(.8f,.8f,.8f,1);
            float ttl = 80 + (int)(Math.random()*20);
            return new Particle(pos, velocity, acceleration, color, 1f, .15f/ttl, (int)ttl);
        }      
    }
    
    public static class GroundFireSmokeEmitter extends ImageParticleEmitter
    {
        public GroundFireSmokeEmitter()
        {
            super(new Image("bigSmoke.png"));
            this.setParticlesPerFrame(.5f);
        }
        
        public Particle buildParticle()
        {
            Random rand = SylverRandom.random;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x+(rand.nextFloat() - .5f)*30 , this.getPosition().y+(rand.nextFloat() - .5f)*30);
            float magnitude =  rand.nextFloat() + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 90;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
            Color color = new Color(Color.randomGray(.6f));
            color.a = .06f;
            int ttl = 100 + (int)(Math.random()*20);
            return new Particle(pos, velocity, acceleration, color, .6f, .8f/ttl, ttl);
        }   
    }
    
    public static class IceEmitter extends ImageParticleEmitter
    {
        public IceEmitter()
        {
            super(new Image("icepuff.png"));
            this.setParticlesPerFrame(3);
        }

        public  Particle buildParticle()
        { 
            
            Random rand = SylverRandom.random;
            float posMag = (rand.nextFloat() - .5f)*400;
            float posAngle = (rand.nextFloat() - .5f) * 720;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x + posMag *(float)Math.cos(posAngle * Math.PI/180) , this.getPosition().y + posMag *(float)Math.sin(posAngle * Math.PI/180));
            float magnitude =  rand.nextFloat() + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 720;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
      
            Color color = new Color(.8f,.8f,1.5f,1f);
//            if(Math.random() < .5){
//                color.g += 1f;
//            }
            int ttl = 50 + (int)(Math.random()*10);
            return new  Particle( pos, velocity, acceleration,  color, 1.2f, -.2f/ttl, ttl);
        }
                
    }
    
    public static class IceEmitter2 extends ImageParticleEmitter
    {
        public IceEmitter2()
        {
            super(new Image("iceball.png"));
            this.setParticlesPerFrame(3);
        }

        public  Particle buildParticle()
        { 
            
            Random rand = SylverRandom.random;
            float posMag = (rand.nextFloat() - .5f)*400;
            float posAngle = (rand.nextFloat() - .5f) * 720;
            SylverVector2f pos = new SylverVector2f(this.getPosition().x + posMag *(float)Math.cos(posAngle * Math.PI/180) , this.getPosition().y + posMag *(float)Math.sin(posAngle * Math.PI/180));
            float magnitude =  rand.nextFloat() + .3f;
            float randomedAngle = getAngle() + (rand.nextFloat() - .5f) * 720;
            SylverVector2f velocity =new SylverVector2f(magnitude *(float)Math.cos(randomedAngle * Math.PI/180) , magnitude *(float)Math.sin(randomedAngle * Math.PI/180));
            SylverVector2f acceleration = new SylverVector2f(0,0);
      
            Color color = new Color(.8f,.8f,1.5f,.75f);
//            if(Math.random() < .5){
//                color.g += 1f;
//            }
            int ttl = 50 + (int)(Math.random()*10);
            return new  Particle( pos, velocity, acceleration,  color, .6f, .2f/ttl, ttl);
        }
                
    }
   
   
    
}
