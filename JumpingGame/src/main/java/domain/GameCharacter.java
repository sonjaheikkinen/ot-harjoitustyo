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
public class GameCharacter extends GameObject {

    /*
    private Polygon character;
    private Point2D movement;
     */
    public GameCharacter(int x, int y) {
        super(new Polygon(20, 0, 40, 40, 0, 40), x, y);
        super.changeMovement(0, 3);
    }

    public void jump(double jumpGround) {
        super.changeMovement(0, -6);
    }

}
