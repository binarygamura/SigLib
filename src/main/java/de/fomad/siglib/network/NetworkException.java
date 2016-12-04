package de.fomad.siglib.network;

import java.io.IOException;

/**
 *
 * @author boreas
 */
public class NetworkException extends IOException {
    public NetworkException(String message){
        super(message);
    }
}
