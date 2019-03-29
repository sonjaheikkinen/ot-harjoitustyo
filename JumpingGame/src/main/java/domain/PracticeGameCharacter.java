/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import javafx.geometry.Point2D;

/**
 *
 * @author sonja
 */
public class PracticeGameCharacter {

    private double x;
    private double y;
    private boolean jump;
    private Point2D movement;

    public PracticeGameCharacter() {
        this.movement = new Point2D(0, 1);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point2D getMovement() {
        return this.movement;
    }
    
    public void setMovement(double x, double y) {
        this.movement = new Point2D(x, y);
    }

    public void changeMovement(double changeX, double changeY) {
        this.movement = this.movement.add(changeX, changeY);
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
    
    public boolean getJump() {
        return this.jump;
    }


}
