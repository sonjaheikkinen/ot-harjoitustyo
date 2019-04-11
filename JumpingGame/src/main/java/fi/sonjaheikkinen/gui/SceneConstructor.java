/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.gui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Scene game;
    private Scene gameOver;
    private Scene instructionScene;
    private StageHandler handler;
    
    public SceneConstructor(StageHandler handler) {
        this.handler = handler;
    }
    
    public Scene getStartScene() {
        return this.startScene;
    }
    
    public Scene getGameScene() {
        return this.game;
    }
    
    public Scene getGameOverScene() {
        return this.gameOver;
    }
    
    public void createScenes() {
        createStartScene();
        createGameOverScene();
        createInstructionScene();
    }
    
    public void createStartScene() {
        
        BorderPane layout = new BorderPane();
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
        
        layout.setTop(new Label("Jumping Game"));
        layout.setCenter(buttons);
        
        Button newGame = new Button("New Game");
        Button howToPlay = new Button("How To Play");
        Button highScore = new Button("High Score");
        
        newGame.setOnAction((event) -> {
            createGameScene();
            handler.setScene(this.game);
        });
        howToPlay.setOnAction((event) -> {
            handler.setScene(this.instructionScene);
        });
        
        buttons.getChildren().addAll(newGame, howToPlay, highScore);
        
        Scene start = new Scene(layout);        
        this.startScene = start;
        
    }
    
    public void createGameScene() {
        
        BorderPane layout  = new BorderPane();
        Scene gameScene = new Scene(layout);
        
        Canvas canvas = new Canvas(400, 500);
        layout.getChildren().add(canvas); 
        
        VBox pointCalculator = new VBox();
        pointCalculator.setAlignment(Pos.TOP_RIGHT);
        Label pointText = new Label("Points: ");
        Label pointAmount = new Label("0");
        pointCalculator.getChildren().addAll(pointText, pointAmount);
        
        layout.setRight(pointCalculator);
               
        GameScreenHandler gch = new GameScreenHandler(canvas, gameScene, pointAmount, this.handler);
        
        gch.updateGame();
        
        this.game = gameScene;
        
    }
    
    public void createGameOverScene() {
        
        BorderPane layout = new BorderPane();
        
        layout.setTop(new Label("Game Over"));
        
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
        
        Button tryAgain = new Button("Try Again"); 
        tryAgain.setOnAction((event) -> {
            createGameScene();
            this.handler.setScene(this.game);
        });
        
        Button quit = new Button("Quit");
        quit.setOnAction((event) -> {
            this.handler.setScene(this.startScene);
        });
        
        buttons.getChildren().addAll(tryAgain, quit);
        layout.setCenter(buttons);
        
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
    
}
