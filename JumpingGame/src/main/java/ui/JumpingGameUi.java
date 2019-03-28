/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import construction.SceneConstructor;
import logic.SceneHandler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class JumpingGameUi extends Application {

    @Override
    public void start(Stage stage) {

        /*
        Button startGame = new Button("Start game");
        Button stopGame = new Button("Stop game");

        Scene startScene = new Scene(startGame);
        Scene game = new Scene(stopGame);

        startGame.setOnAction((event) -> {
            stage.setScene(game);
        });

        stopGame.setOnAction((event) -> {
            stage.setScene(startScene);
        });
         */
        
        SceneHandler sceneHandler = new SceneHandler(stage);
        sceneHandler.createScenes();
        sceneHandler.setInitialScene();

        stage.show();

    }

    public static void main(String args[]) {
        launch(JumpingGameUi.class);
    }

}
