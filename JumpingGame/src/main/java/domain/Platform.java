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
    
    public Platform (int x, int y, int width) {
        super(new Polygon(0, 0, width, 0, width, 5, 0, 5), x, y);
        super.changeMovement(0, 1);
    }
   
    
}
