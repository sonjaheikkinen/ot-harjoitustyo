/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package construction;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SceneConstructor {
    
    public Scene startScene() {
        
        
        BorderPane layout = new BorderPane();
        VBox buttons = new VBox();
        
        Button startGame = new Button("Start Game");
        Button howToPlay = new Button("How To Play");
        Button highScores = new Button("High Scores");
        
        buttons.getChildren().addAll(startGame, howToPlay, highScores);
        
        layout.setTop(new Label("Jumping Game"));
        layout.setCenter(buttons);              
        
        Scene startScene = new Scene(layout);
        
        return startScene;
        
    }
    
}
