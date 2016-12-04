package de.fomad.siglib.network;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 * @author boreas
 */
public class NetworkWriter {
    
    private final OutputStream stream;
    
    private static final byte NEWLINE = '\n';
    
    public NetworkWriter(OutputStream stream){
        this.stream = stream;
    }
    
    private void writeHeaders(List<NetworkHeader> headers) throws IOException {
        if(headers == null){
            throw new NetworkException("unable to send null headers.");
        }
        for(NetworkHeader header : headers){
            stream.write(header.getKey().toString().getBytes(StandardCharsets.UTF_8));
            stream.write(':');
            stream.write(header.getValue().getBytes(StandardCharsets.UTF_8));
            stream.write(NEWLINE);
        }
        stream.write(NEWLINE);
        stream.flush();
    }
    
    private void writeBody(String body) throws IOException{
        if(body != null && !body.isEmpty()){
            stream.write(body.getBytes(StandardCharsets.UTF_8));
            stream.flush();
        }
    }
    
    public synchronized void writeMessage(NetworkMessage message) throws NetworkException, IOException{
        if(message == null){
            throw new NetworkException("unable to write null message.");
        }
        stream.write(message.getResource().getBytes(StandardCharsets.UTF_8));
        stream.write(NEWLINE);
        writeHeaders(message.getHeaders());
        writeBody(message.getContent());
    }
}
