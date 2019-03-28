/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import construction.SceneConstructor;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class SceneHandler {
    
    private SceneConstructor sceneConstructor;
    private Stage stage;
    private Scene startScene;
    private Scene game;
    
    public SceneHandler(Stage stage) {
        this.stage = stage;
        this.sceneConstructor = new SceneConstructor(this);
    }
    
    public void setInitialScene() {
        stage.setScene(startScene);
    }
    
    public void startGame() {
        stage.setScene(game);
    }
    
    public void quitGame() {
        //this method will change later to do something else that this.setInitialScene()
        stage.setScene(startScene);
    }
    
    public void createScenes() {
        startScene = this.sceneConstructor.startScene();
        game = this.sceneConstructor.game();
    }
    
    
    
}
