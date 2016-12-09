package de.fomad.siglib.network;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

/**
 *
 * @author boreas
 */
public class NetworkReader implements AutoCloseable{
    
    private InputStream stream;
    
    private static final int MAX_LINE_LENGTH = 256;
    
    private static final int MAX_CONTENT_LENGTH = 12000;
    
    private static final Logger LOGGER = Logger.getLogger(NetworkReader.class);
    
    public NetworkReader(InputStream stream){
        this.stream = stream;
    }
    
    public NetworkMessage readMessage() throws IOException{
        String resource = readLine();
        List<NetworkHeader> headers = readHeaders();
        Optional<NetworkHeader> firstMatch = headers.stream().filter(e -> e.getKey() == NetworkHeader.Key.CONTENT_LENGTH).findFirst();
        int contentLength = firstMatch.isPresent() ? Integer.parseInt(firstMatch.get().getValue()) : 0;
        if(contentLength < 0 || contentLength > MAX_CONTENT_LENGTH){
            throw new NetworkException("invalid content-length detected!");
        }
        NetworkMessage message = new NetworkMessage();
        message.setResource(resource);
        message.setHeaders(headers);
        if(contentLength > 0){
            message.setContent(readBodyAsString(contentLength));
        }
        return message;
        
    }
    
    private String readBodyAsString(int length) throws IOException{
        int bytesRead = 0, temp;
        byte[] content = new byte[length];
        while((temp = stream.read(content, bytesRead, length - bytesRead)) != -1){
            bytesRead += temp;
        }
        if(temp == -1){
            throw new EOFException();
        }
        
        return new String(content, StandardCharsets.UTF_8);
    }
    
    private String readLine() throws IOException{
        StringBuilder builder = new StringBuilder();
        int value;
        while((value = stream.read()) != -1 && ((char) value) != '\n' && builder.length() < MAX_LINE_LENGTH){
            builder.append((char) value);
        }
        if(value == -1){
            throw new EOFException("unexpected end of stream");
        }
        return builder.toString();
    }
    
    private List<NetworkHeader> readHeaders() throws IOException{
        String line;
        NetworkHeader header;
        String[] splitted;
        List<NetworkHeader> headers = new ArrayList<>();
        while(!(line = readLine()).isEmpty()){
            splitted = line.split(";", 2);
            header = new NetworkHeader();
            header.setKey(NetworkHeader.Key.valueOf(splitted[0].toUpperCase()));
            header.setValue(splitted[1]);
            headers.add(header);
        }
        return headers;
    }

    @Override
    public void close() throws IOException {
        try{
            if(stream != null){
                stream.close();
            }
        }
        catch(Exception ex){
            LOGGER.warn("error while closing the input stream.", ex);
        }
        finally{
            stream = null;
        }
    }
}
