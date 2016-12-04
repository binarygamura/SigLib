package de.fomad.siglib.network;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author boreas
 */
public class NetworkReaderProcess implements Runnable {
    
    private static final Logger LOGGER = LogManager.getLogger(NetworkReaderProcess.class);
    
    private final NetworkReader networkReader;
    
    private final NetworkReaderCallback callback;
    
    public NetworkReaderProcess(NetworkReader networkReader, NetworkReaderCallback callback){
        this.networkReader = networkReader;
        this.callback = callback;
    }

    @Override
    public void run() {
        try{
            while(!Thread.currentThread().isInterrupted()){
                callback.onNetworkMessage(networkReader.readMessage());
            }
        }
        catch(Exception ex){
            LOGGER.error("error while reading messages.", ex);
        }
        finally {
            callback.onExit();
        }
    }
}
