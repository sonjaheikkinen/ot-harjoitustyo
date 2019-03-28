/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import javafx.scene.shape.Polygon;

/**
 *
 * @author sonja
 */
public class Platform extends GameObject {
    
    public Platform (int x, int y) {
        super(new Polygon(0, 0, 100, 0, 100, 5, 0, 5), x, y);
        super.changeMovement(0, 1);
    }
    
    public void fall() {
        super.moveVertical(super.getObject().getTranslateY() + super.getMovement().getY());
    }
    
}
