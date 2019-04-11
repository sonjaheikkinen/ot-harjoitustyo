/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.domain;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author sonja
 */
public class GameObject {

    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private boolean action;

    public GameObject(double x, double y, double width, double heigth, double velocityX, double velocityY) {
        this.positionX = x;
        this.positionY = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.width = width;
        this.height = heigth;
        this.action = false;
    }

    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(GameObject s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public void changeVelocity(double x, double y) {
        velocityX += x;
        velocityY += y;
    }

    public double getPositionX() {
        return this.positionX;
    }

    public void setPositionX(double x) {
        positionX = x;
    }

    public double getPositionY() {
        return this.positionY;
    }

    public void setPositionY(double y) {
        positionY = y;
    }

    public double getVelocityX() {
        return this.velocityX;
    }

    public void setVelocityX(double x) {
        this.velocityX = x;
    }

    public double getVelocityY() {
        return this.velocityY;
    }

    public void setVelocityY(double y) {
        this.velocityY = y;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeigth() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean getAction() {
        return this.action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

}
