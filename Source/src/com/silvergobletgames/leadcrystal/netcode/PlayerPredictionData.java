package com.silvergobletgames.leadcrystal.netcode;

/**
 *
 * @author Mike
 */
public class PlayerPredictionData {
    
    public float positionX;
    public float positionY;
    public float velocityX;
    public float velocityY;
    
    public PlayerPredictionData()
    {
        
    }
    
//    @Override
//    public boolean equals(Object other)
//    {
//        if (other == null)
//            return false;
//        if (other == this)
//            return true;
//        if (other.getClass() != getClass())
//            return false;
//    }
    

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 83 * hash + Float.floatToIntBits(this.positionX);
        hash = 83 * hash + Float.floatToIntBits(this.positionY);
        hash = 83 * hash + Float.floatToIntBits(this.velocityX);
        hash = 83 * hash + Float.floatToIntBits(this.velocityY);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final PlayerPredictionData other = (PlayerPredictionData) obj;
        if (Float.floatToIntBits(this.positionX) != Float.floatToIntBits(other.positionX))
            return false;
        if (Float.floatToIntBits(this.positionY) != Float.floatToIntBits(other.positionY))
            return false;
        if (Float.floatToIntBits(this.velocityX) != Float.floatToIntBits(other.velocityX))
            return false;
        if (Float.floatToIntBits(this.velocityY) != Float.floatToIntBits(other.velocityY))
            return false;
        return true;
    }
}
