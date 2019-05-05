
package fi.sonjaheikkinen.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Luokka huolehtii näkymien vaihtamisesta ikkunassa stage
 */
public class StageHandler {
    
    private final Stage stage;
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
    
    /**
     * Metodi luo uuden gameOver-scenen ja asettaa sen näkyväksi stageen.
     */
    public void gameOver() {
        this.scenes.createGameOverScene();
        this.stage.setScene(scenes.getGameOverScene());
    }
    
}
