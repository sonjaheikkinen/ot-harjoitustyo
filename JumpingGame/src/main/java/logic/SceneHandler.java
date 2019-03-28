/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class SceneHandler {
    
    private Stage stage;
    private Scene startScene;
    private Scene game;
    
    public SceneHandler(Stage stage) {
        this.stage = stage;
    }
    
    public void setInitialScene() {
        stage.setScene(startScene);
    }
    
    public void createScenes() {
        Button startGame = new Button("Start game");
        Button stopGame = new Button("Stop game");

        startScene = new Scene(startGame);
        game = new Scene(stopGame);

        startGame.setOnAction((event) -> {
            stage.setScene(game);
        });

        stopGame.setOnAction((event) -> {
            stage.setScene(startScene);
        });
    }
    
    
    
}
