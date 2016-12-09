package de.fomad.siglib.network;

import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author boreas
 */
public class NetworkWriterQueue implements Runnable, AutoCloseable {
    
    private static final Logger LOGGER = LogManager.getLogger(NetworkWriterQueue.class);
    
    private LinkedBlockingQueue<NetworkMessage> messageQueue;
    
    private final NetworkWriter writer;
    
    private static final int DEFAULT_MAX_QUEUE_SIZE = 10;
    
    public NetworkWriterQueue(NetworkWriter writer){
        this(writer, DEFAULT_MAX_QUEUE_SIZE);
    }
    
    public NetworkWriterQueue(NetworkWriter writer, int maxQueueSize){
        this.writer = writer;
        messageQueue = new LinkedBlockingQueue<>(maxQueueSize);
    }

    public boolean addToQueue(NetworkMessage networkMessage) throws NetworkException{
        if(networkMessage == null){
            throw new NetworkException("unable to enqueue null message.");
        }
        return messageQueue.offer(networkMessage);
    }
    
    @Override
    public void run() {
        try{
            while(!Thread.currentThread().isInterrupted()){
                writer.writeMessage(messageQueue.take());
            }
        }
        catch(Exception ex){
            LOGGER.error("error while processing message queue.", ex);
        }
        finally {
            LOGGER.info("message queue terminated.");
        }
    }

    @Override
    public void close() throws Exception {
        if(writer != null){
            writer.close();
        }
    }
}
