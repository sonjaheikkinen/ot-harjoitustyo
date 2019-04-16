/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.gui;

import fi.sonjaheikkinen.logic.ProgramLogic;
import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author sonja
 */
public class SceneConstructor {

    private Scene startScene;
    private Scene newGame;
    private Scene game;
    private Scene gameOver;
    private Scene highScores;
    private Scene instructionScene;
    private StageHandler handler;
    private ProgramLogic pLogic;
    private File f;

    public SceneConstructor(StageHandler handler, ProgramLogic pInfo) {
        this.handler = handler;
        this.pLogic = pInfo;
        this.f = new File("stylesheet.css");
    }

    public void createScenes() {
        createStartScene();
        createInstructionScene();
    }

    public void createStartScene() {

        BorderPane layout = new BorderPane();

        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);

        Label heading = new Label("Jumping Game");
        heading.setId("mainHeading");
        layout.setTop(heading);
        layout.setCenter(buttons);

        Button startGame = new Button("New Game");
        Button howToPlay = new Button("How To Play");
        Button highScore = new Button("High Score");

        startGame.setOnAction((event) -> {
            createNewGameScene();
            this.handler.setScene(this.newGame);
        });
        howToPlay.setOnAction((event) -> {
            this.handler.setScene(this.instructionScene);
        });
        highScore.setOnAction((event) -> {
            createHighScoreScene();
            this.handler.setScene(this.highScores);
        });

        buttons.getChildren().addAll(startGame, howToPlay, highScore);

        Scene start = new Scene(layout);

        start.getStylesheets().clear();
        start.getStylesheets().add("file:///" + this.f.getAbsolutePath().replace("\\", "/"));

        this.startScene = start;

    }

    public void createNewGameScene() {

        BorderPane layout = new BorderPane();

        layout.setTop(new Label("New Game"));

        VBox things = new VBox();

        Text nameInstruction = new Text();
        HBox nameField = new HBox();
        Text name = new Text("Name: ");
        TextField playerName = new TextField();
        Button startGame = new Button("Start Game");

        startGame.setOnAction((event) -> {
            boolean nameMatchesFormat = this.pLogic.checkName(nameInstruction, playerName);
            if (nameMatchesFormat) {
                this.pLogic.setCurrentPlayer(playerName.getText());
                this.pLogic.setPoints(0);
                createGameScene();
                this.handler.setScene(this.game);
            }
        });

        nameField.getChildren().addAll(name, playerName);
        things.getChildren().addAll(nameInstruction, nameField, startGame);
        nameField.setAlignment(Pos.CENTER);
        things.setAlignment(Pos.CENTER);

        HBox buttons = new HBox();
        Button back = new Button("Back");
        back.setOnAction((event) -> {
            this.handler.setScene(this.startScene);
        });
        buttons.getChildren().add(back);

        layout.setCenter(things);
        layout.setBottom(buttons);

        Scene newGameScene = new Scene(layout);

        newGameScene.getStylesheets().clear();
        newGameScene.getStylesheets().add("file:///" + this.f.getAbsolutePath().replace("\\", "/"));

        this.newGame = newGameScene;

    }

    public void createGameScene() {

        BorderPane layout = new BorderPane();
        Scene gameScene = new Scene(layout);

        Canvas canvas = new Canvas(400, 500);
        layout.getChildren().add(canvas);

        Text pointText = new Text("Points: \n0");

        layout.setRight(pointText);

        GameScreenHandler gch = new GameScreenHandler(canvas, gameScene, pointText, this.handler, this.pLogic);

        gch.updateGame();

        gameScene.getStylesheets().clear();
        gameScene.getStylesheets().add("file:///" + this.f.getAbsolutePath().replace("\\", "/"));

        this.game = gameScene;

    }

    public void createGameOverScene() {

        BorderPane layout = new BorderPane();

        layout.setTop(new Label("Game Over"));

        VBox buttonsEtc = new VBox();
        buttonsEtc.setAlignment(Pos.CENTER);

        Text player = new Text("Player: " + this.pLogic.getCurrentPlayer());
        Text points = new Text("Points: " + this.pLogic.getPoints());

        Button tryAgain = new Button("Try Again");
        tryAgain.setOnAction((event) -> {
            createGameScene();
            this.pLogic.setPoints(0);
            this.handler.setScene(this.game);
        });

        Button quit = new Button("Quit");
        quit.setOnAction((event) -> {
            this.handler.setScene(this.startScene);
        });

        buttonsEtc.getChildren().addAll(player, points, tryAgain, quit);
        layout.setCenter(buttonsEtc);

        Scene gameOverScene = new Scene(layout);

        gameOverScene.getStylesheets().clear();
        gameOverScene.getStylesheets().add("file:///" + this.f.getAbsolutePath().replace("\\", "/"));

        this.gameOver = gameOverScene;

    }

    public void createInstructionScene() {

        BorderPane layout = new BorderPane();

        layout.setTop(new Label("How To Play"));

        Text instruction = new Text();
        instruction.setText("Move sideways by moving your mouse. \n"
                + "Character jumps automatically on platform. \n"
                + "Do not fall off screen. ");

        Button back = new Button("Back");
        back.setOnAction((event) -> {
            this.handler.setScene(this.startScene);
        });
        Button newGame = new Button("New Game");
        newGame.setOnAction((event) -> {
            createNewGameScene();
            this.handler.setScene(this.newGame);
        });

        HBox buttons = new HBox();

        buttons.getChildren().addAll(back, newGame);

        layout.setCenter(instruction);
        layout.setBottom(buttons);

        Scene howToPlay = new Scene(layout);

        howToPlay.getStylesheets().clear();
        howToPlay.getStylesheets().add("file:///" + this.f.getAbsolutePath().replace("\\", "/"));

        this.instructionScene = howToPlay;

    }

    public void createHighScoreScene() {

        BorderPane layout = new BorderPane();

        layout.setTop(new Label("High Score"));

        Text highScore = new Text();
        highScore.setText(this.pLogic.getHighScoreString());
        layout.setCenter(highScore);

        HBox buttons = new HBox();
        Button back = new Button("Back");
        back.setOnAction((event) -> {
            this.handler.setScene(this.startScene);
        });
        buttons.getChildren().add(back);
        layout.setBottom(buttons);

        Scene highScoreScene = new Scene(layout);

        highScoreScene.getStylesheets().clear();
        highScoreScene.getStylesheets().add("file:///" + this.f.getAbsolutePath().replace("\\", "/"));

        this.highScores = highScoreScene;

    }

    public Scene getStartScene() {
        return this.startScene;
    }  

    public Scene getGameOverScene() {
        return this.gameOver;
    }

}
