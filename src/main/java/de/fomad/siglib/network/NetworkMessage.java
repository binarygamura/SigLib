package de.fomad.siglib.network;

import java.util.List;

/**
 *
 * @author boreas
 */
public class NetworkMessage {
    
    private String resource;
    
    private List<NetworkHeader> headers;
    
    private String content;
    
    public NetworkMessage(){
        
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
    
    public List<NetworkHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<NetworkHeader> headers) {
        this.headers = headers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
