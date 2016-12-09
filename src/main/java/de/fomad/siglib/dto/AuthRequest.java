package de.fomad.siglib.dto;

import de.fomad.siglib.entities.Pilot;

/**
 *
 * @author binary
 */
public class AuthRequest {
    
    private String password;
    
    private Pilot character;

    public Pilot getCharacter() {
        return character;
    }

    public void setCharacter(Pilot character) {
        this.character = character;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
