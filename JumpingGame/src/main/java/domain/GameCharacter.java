/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author sonja
 */
public class GameCharacter extends Sprite{
    
    private boolean jump;
    
    public void setJump(boolean value) {
        this.jump = value;
    }
    
    public boolean getJump() {
        return this.jump;
    }
    
}
