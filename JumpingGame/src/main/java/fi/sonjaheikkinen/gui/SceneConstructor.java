/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.gui;

import fi.sonjaheikkinen.domain.ProgramInformation;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
    private ProgramInformation pInfo;

    public SceneConstructor(StageHandler handler, ProgramInformation pInfo) {
        this.handler = handler;
        this.pInfo = pInfo;
    }

    public void createScenes() {
        createStartScene();
        createInstructionScene();
    }

    public void createStartScene() {

        BorderPane layout = new BorderPane();
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);

        layout.setTop(new Label("Jumping Game"));
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
        this.startScene = start;

    }

    public void createNewGameScene() {

        BorderPane layout = new BorderPane();

        layout.setTop(new Label("New Game"));

        VBox things = new VBox();

        HBox nameField = new HBox();
        Label name = new Label("Name: ");
        TextField playerName = new TextField();
        Button startGame = new Button("Start Game");

        startGame.setOnAction((event) -> {
            createGameScene();
            this.pInfo.setCurrentPlayer(playerName.getText());
            this.pInfo.setPoints(0);
            this.handler.setScene(this.game);
        });

        nameField.getChildren().addAll(name, playerName);
        things.getChildren().addAll(nameField, startGame);
        nameField.setAlignment(Pos.CENTER);
        things.setAlignment(Pos.CENTER);

        Button back = new Button("Back");

        back.setOnAction((event) -> {
            this.handler.setScene(this.startScene);
        });
        
        layout.setCenter(things);
        layout.setBottom(back);

        Scene newGameScene = new Scene(layout);
        this.newGame = newGameScene;

    }

    public void createGameScene() {

        BorderPane layout = new BorderPane();
        Scene gameScene = new Scene(layout);

        Canvas canvas = new Canvas(400, 500);
        layout.getChildren().add(canvas);

        VBox pointCalculator = new VBox();
        pointCalculator.setAlignment(Pos.TOP_RIGHT);
        Label pointText = new Label("Points: ");
        Label pointAmount = new Label("0");
        pointCalculator.getChildren().addAll(pointText, pointAmount);

        layout.setRight(pointCalculator);

        GameScreenHandler gch = new GameScreenHandler(canvas, gameScene, pointAmount, this.handler, this.pInfo);

        gch.updateGame();

        this.game = gameScene;

    }

    public void createGameOverScene() {

        BorderPane layout = new BorderPane();

        layout.setTop(new Label("Game Over"));

        VBox buttonsEtc = new VBox();
        buttonsEtc.setAlignment(Pos.CENTER);
        
        Label player = new Label("Player: " + this.pInfo.getCurrentPlayer());
        Label points = new Label("Points: " + this.pInfo.getPoints());

        Button tryAgain = new Button("Try Again");
        tryAgain.setOnAction((event) -> {
            createGameScene();
            this.pInfo.setPoints(0);
            this.handler.setScene(this.game);
        });

        Button quit = new Button("Quit");
        quit.setOnAction((event) -> {
            this.handler.setScene(this.startScene);
        });

        buttonsEtc.getChildren().addAll(player, points, tryAgain, quit);
        layout.setCenter(buttonsEtc);

        Scene gameOverScene = new Scene(layout);
        this.gameOver = gameOverScene;

    }

    public void createInstructionScene() {

        BorderPane layout = new BorderPane();

        layout.setTop(new Label("How To Play"));

        Text instruction = new Text();
        instruction.setText("Move sideways by moving your mouse. \n "
                + "Character jumps automatically on platform. \n "
                + "Do not fall off screen. ");

        Button back = new Button("Back");
        back.setOnAction((event) -> {
            this.handler.setScene(this.startScene);
        });
        Button newGame = new Button("New Game");
        newGame.setOnAction((event) -> {
            createGameScene();
            this.handler.setScene(this.game);
        });

        HBox buttons = new HBox();

        buttons.getChildren().addAll(back, newGame);

        layout.setCenter(instruction);
        layout.setBottom(buttons);

        Scene howToPlay = new Scene(layout);
        this.instructionScene = howToPlay;

    }
    
    public void createHighScoreScene() {
        
        BorderPane layout = new BorderPane();
       
        layout.setTop(new Label("High Score"));
        
        Text highScore = new Text();
        highScore.setText(this.pInfo.getHighScoreString());
        layout.setCenter(highScore);
        
        Button back = new Button("Back");  
        back.setOnAction((event) -> {
            this.handler.setScene(this.startScene);
        });
        layout.setBottom(back);
        
        Scene highScoreScene = new Scene(layout);
        this.highScores = highScoreScene;
        
    }

    public Scene getStartScene() {
        return this.startScene;
    }

    public Scene getNewGameScene() {
        return this.newGame;
    }

    public Scene getGameScene() {
        return this.game;
    }

    public Scene getGameOverScene() {
        return this.gameOver;
    }

}
