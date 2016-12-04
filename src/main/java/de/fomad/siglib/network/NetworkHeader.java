package de.fomad.siglib.network;

/**
 *
 * @author boreas
 */
public class NetworkHeader {
    
    private String value;

    private Key key;

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public enum Key {
        CONTENT_LENGTH
    }
    
}
