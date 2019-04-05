/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.domain;

/**
 *
 * @author sonja
 */
public class GameCharacter extends GameObject {

    private boolean jump;

    public GameCharacter(double x, double y, double width, double height, double velocityX, double velocityY) {
        super(x, y, width, height, velocityX, velocityY);
    }

    public void setJump(boolean value) {
        this.jump = value;
    }

    public boolean getJump() {
        return this.jump;
    }

    

}
