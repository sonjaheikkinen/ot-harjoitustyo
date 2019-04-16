/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.gui;

import fi.sonjaheikkinen.domain.GameObject;
import fi.sonjaheikkinen.logic.GameLogic;
import fi.sonjaheikkinen.logic.ProgramLogic;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import fi.sonjaheikkinen.other.LongValue;
import java.awt.Color;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 *
 * @author sonja
 */
public class GameScreenHandler {

    private Scene game;
    private Canvas canvas;
    private GameLogic gLogic;
    private StageHandler stageHandler;
    private Text points;
    private ProgramLogic pLogic;

    public GameScreenHandler(Canvas canvas, Scene scene, Text points, StageHandler handler, ProgramLogic pLogic) {
        this.game = scene;
        this.canvas = canvas;
        this.gLogic = new GameLogic();
        this.stageHandler = handler;
        this.points = points;
        this.pLogic = pLogic;
    }

    public void updateGame() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GameObject gameCharacter = this.gLogic.createGameCharacter();
        ArrayList<GameObject> platforms = this.gLogic.createPlatforms();
        handleMouseMovement(game, gameCharacter);
        handleAnimation(gc, gameCharacter, platforms);
    }

    public static void handleMouseMovement(Scene game, GameObject character) {

        game.setOnMouseMoved((event) -> {

            double mouseX = event.getSceneX();
            character.setPositionX(mouseX);

        });
    }

    public void handleAnimation(GraphicsContext gc, GameObject gameCharacter, ArrayList<GameObject> platforms) {
        LongValue lastNanoTime = new LongValue(System.nanoTime());
        GameLogic gameLogic = this.gLogic;
        ProgramLogic programLogic = this.pLogic;
        StageHandler handler = this.stageHandler;
        Text pointAmount = this.points;
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (gameCharacter.getPositionY() > 500) {
                    programLogic.updatePoints(gameLogic);
                    programLogic.updateHighScore();
                    handler.gameOver();
                    this.stop();
                }
                double elapsedTimeInSeconds = calculateElapsedTime(lastNanoTime, currentNanoTime);
                gameLogic.moveCharacter(elapsedTimeInSeconds, gameCharacter);
                gameLogic.movePlatforms(elapsedTimeInSeconds, platforms);
                gameLogic.detectCollission(gameCharacter, platforms);
                pointAmount.setText("Points: \n" + gameLogic.getPoints());
                render(gc, gameCharacter, platforms);
            }
        }.start();
    }

    public static double calculateElapsedTime(LongValue lastNanoTime, long currentNanoTime) {
        double elapsedTime = (currentNanoTime - lastNanoTime.getValue()) / 1000000000.0;
        lastNanoTime.setValue(currentNanoTime);
        return elapsedTime;
    }

    public static void render(GraphicsContext gc, GameObject gameCharacter, ArrayList<GameObject> platforms) {
        
        gc.setFill(javafx.scene.paint.Color.WHITE);
        
        gc.clearRect(0, 0, 400, 500);

        double characterX = gameCharacter.getPositionX();
        if (characterX < 0) {
            characterX = 0;
        } else if (characterX > 360) {
            characterX = 360;
        }

        gc.fillRect(characterX, gameCharacter.getPositionY(), gameCharacter.getWidth(), gameCharacter.getHeigth());

        for (GameObject platform : platforms) {
            gc.fillRect(platform.getPositionX(), platform.getPositionY(), platform.getWidth(), platform.getHeigth());
        }
    }

}
