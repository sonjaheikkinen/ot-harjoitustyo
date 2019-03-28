/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 *
 * @author sonja
 */
public class GameObject {

    private Polygon object;
    private Point2D movement;

    public GameObject(Polygon shape, int x, int y) {
        this.object = shape;
        this.object.setTranslateX(x);
        this.object.setTranslateY(y);
        this.movement = new Point2D(0, 0);
    }

    public Polygon getObject() {
        return this.object;
    }
    
    public Point2D getMovement() {
        return this.movement;
    }

    public void moveSideways(double x) {
        this.object.setTranslateX(x);
    }
    
    public void moveVertical(double y) {
        this.object.setTranslateY(y);
    }
    
    public void changeMovement(double x, double y) {
        this.movement = this.movement.add(x, y);
    }
    
    public void fall() {
        moveVertical(this.object.getTranslateY() + this.movement.getY());
    }
    
    public boolean collission(GameObject other) {
        Shape collissionArea = Shape.intersect(this.object, other.object);
        return collissionArea.getBoundsInLocal().getWidth() != -1;
    }


}
