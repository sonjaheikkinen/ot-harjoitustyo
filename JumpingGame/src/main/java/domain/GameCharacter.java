/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

/**
 *
 * @author sonja
 */
public class GameCharacter {
    
    private Polygon character;
    private Point2D movement;
    
    public GameCharacter(int x, int y) {
        this.character = new Polygon(20, 0, 40, 40, 0, 40);
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.movement = new Point2D(0, -1);
    }
    
    public Polygon getCharacter() {
        return this.character;
    }
    
    public void moveSideways(double x) {
        this.character.setTranslateX(x);
    }
    
    public void moveUpwards() {
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());
    }
    
}
