/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package construction;

import logic.SceneHandler;
import domain.*;


import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;

public class SceneConstructor {

    private SceneHandler sceneHandler;
    private int width;
    private int height;

    public SceneConstructor(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
        this.width = 500;
        this.height = 500;
    }

    public Scene startScene() {

        BorderPane layout = new BorderPane();
        layout.setPrefSize(this.width, this.height);
        VBox buttons = new VBox();

        Button startGame = new Button("Start Game");
        Button howToPlay = new Button("How To Play");
        Button highScores = new Button("High Scores");

        startGame.setOnAction((event) -> {
            this.sceneHandler.startGame();
        });

        buttons.getChildren().addAll(startGame, howToPlay, highScores);

        layout.setTop(new Label("Jumping Game"));
        layout.setCenter(buttons);

        Scene startScene = new Scene(layout);

        return startScene;

    }

    public Scene game() {

        //Create game screen
        BorderPane gameScreen = new BorderPane();
        gameScreen.setPrefSize(this.width, this.height);

        //Create objects on the screen
        GameCharacter character = new GameCharacter(250, 400);
        Platform platform = new Platform(240, 0);

        //Add objects to screen
        gameScreen.getChildren().addAll(character.getObject(), platform.getObject());

        //Create buttons
        Button quitGame = new Button("Quit Game");
        quitGame.setOnAction((event) -> {
            this.sceneHandler.quitGame();
        });
        gameScreen.setBottom(quitGame);

        //Create and manipulate scene
        Scene game = new Scene(gameScreen);

        game.setOnMouseMoved((event) -> {
            character.moveSideways(event.getSceneX());
        });

        new AnimationTimer() {

            @Override
            public void handle(long nykyhetki) {
                character.jump();
                platform.fall();
            }
        }.start();

        return game;

    }

}
