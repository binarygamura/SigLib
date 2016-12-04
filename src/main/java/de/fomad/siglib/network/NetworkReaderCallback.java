package de.fomad.siglib.network;

/**
 *
 * @author boreas
 */
public interface NetworkReaderCallback {
    public void onNetworkMessage(NetworkMessage message);
    
    public void onExit();
}
