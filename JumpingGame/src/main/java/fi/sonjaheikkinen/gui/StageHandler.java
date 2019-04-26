
package fi.sonjaheikkinen.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

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
