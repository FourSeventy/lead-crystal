package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.leadcrystal.netcode.JoinResponse.ReasonCode;
import java.io.IOException;

/**
 *
 * @author Mike
 */
public class ConnectionException extends Exception {
    
    public ReasonCode reason;
    public IOException exception;
    
    public ConnectionException(ReasonCode reason)
    {
        this.reason = reason;
    }
}
