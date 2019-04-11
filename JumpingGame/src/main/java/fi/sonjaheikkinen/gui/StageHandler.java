/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author sonja
 */
public class StageHandler {
    
    private Stage stage;
    private SceneConstructor scenes;
    
    public StageHandler(Stage stage) {
        this.stage = stage;
    }
    
    public void setScenes(SceneConstructor scenes) {
        this.scenes = scenes;
    }
    
    public void setScene(Scene scene) {
        this.stage.setScene(scene);
    }
    
    public void gameOver() {
        this.scenes.createGameOverScene();
        this.stage.setScene(scenes.getGameOverScene());
    }
    
}
