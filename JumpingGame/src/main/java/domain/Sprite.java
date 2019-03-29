/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author sonja
 */
public class Sprite {
    
    private double positionX;
    private double positionY;    
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
 
    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }
 
    public void render(GraphicsContext gc) {
        gc.fillRect(positionX, positionY, width, height);
    }
 
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX,positionY,width,height);
    }
 
    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects( this.getBoundary() );
    }
    
    public void setPositionX(double x) {
        positionX = x;
    }
    
    public void setPositionY(double y) {
        positionY = y;
    }
    
    public void setWidth(double width) {
        this.width = width;
    }
    
    public void setHeight(double height) {
        this.height = height;
    }
    
    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }
    
    public void changeVelocity(double x, double y) {
        velocityX += x;
        velocityY += y;
    }
    
    public double getVelocityY() {
        return velocityY;
    }
    
    public double getVelocityX() {
        return velocityX;
    }
    
}
