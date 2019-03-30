/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import logic.GameScreenHandler;

/**
 *
 * @author sonja
 */
public class SceneConstructor {
    
    private Scene game;
    
    public Scene getGameScene() {
        return this.game;
    }
    
    public void createGameScene() {
        
        Group root = new Group();
        Scene game = new Scene(root);
        Canvas canvas = new Canvas(400, 500);
        root.getChildren().add(canvas); 
        GameScreenHandler gch = new GameScreenHandler(canvas, game);
        gch.updateGame();
        
        this.game = game;
        
    }
    
}
