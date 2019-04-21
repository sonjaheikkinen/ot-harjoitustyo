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
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import fi.sonjaheikkinen.other.LongValue;
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
        ArrayList<GameObject> traps = this.gLogic.createTraps();
        ArrayList<GameObject> boosts = this.gLogic.createBoosts();
        handleMouseMovement(game, gameCharacter);
        handleAnimation(gc, gameCharacter, platforms, traps, boosts);
    }

    public static void handleMouseMovement(Scene game, GameObject character) {

        game.setOnMouseMoved((event) -> {

            double mouseX = event.getSceneX();
            character.setPositionX(mouseX);

        });
    }

    public void handleAnimation(GraphicsContext gc, GameObject gameCharacter, ArrayList<GameObject> platforms,
            ArrayList<GameObject> traps, ArrayList<GameObject> boosts) {
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
                moveGameObjects(elapsedTimeInSeconds, gameLogic, gameCharacter, platforms, traps, boosts);
                detectCollission(gameLogic, gameCharacter, platforms, traps);
                gameLogic.handleLevel();
                pointAmount.setText("Points: \n" + gameLogic.getPoints() + "\nLives: \n" + gameLogic.getLives());
                render(gc, gameCharacter, platforms, traps);
            }

        }.start();
    }

    public void moveGameObjects(double elapsedTimeInSeconds, GameLogic gameLogic, GameObject gameCharacter,
            ArrayList<GameObject> platforms, ArrayList<GameObject> traps, ArrayList<GameObject> boosts) {
        gameLogic.moveCharacter(elapsedTimeInSeconds, gameCharacter);
        gameLogic.movePlatforms(elapsedTimeInSeconds, platforms);
        gameLogic.moveTraps(elapsedTimeInSeconds, traps);
        gameLogic.moveBoosts(elapsedTimeInSeconds, boosts);
    }

    public void detectCollission(GameLogic gameLogic, GameObject gameCharacter, ArrayList<GameObject> platforms, 
            ArrayList<GameObject> traps) {
        gameLogic.detectCollissionWithPlatforms(gameCharacter, platforms);
        gameLogic.detectDeathOnTrap(gameCharacter, traps);
    }

    public double calculateElapsedTime(LongValue lastNanoTime, long currentNanoTime) {
        double elapsedTime = (currentNanoTime - lastNanoTime.getValue()) / 1000000000.0;
        lastNanoTime.setValue(currentNanoTime);
        return elapsedTime;
    }

    public void render(GraphicsContext gc, GameObject gameCharacter, ArrayList<GameObject> platforms,
            ArrayList<GameObject> traps) {

        gc.setFill(javafx.scene.paint.Color.WHITE);

        gc.clearRect(0, 0, 400, 500);

        renderCharacter(gc, gameCharacter);
        renderPlatforms(gc, platforms);
        renderTraps(gc, traps);

    }

    public void renderCharacter(GraphicsContext gc, GameObject gameCharacter) {
        double characterX = gameCharacter.getPositionX();
        if (characterX < 0) {
            characterX = 0;
        } else if (characterX > 360) {
            characterX = 360;
        }

        gc.fillRect(characterX, gameCharacter.getPositionY(), gameCharacter.getWidth(), gameCharacter.getHeight());
    }

    public void renderPlatforms(GraphicsContext gc, ArrayList<GameObject> platforms) {
        for (GameObject platform : platforms) {
            gc.fillRect(platform.getPositionX(), platform.getPositionY(), platform.getWidth(), platform.getHeight());
        }
    }

    public void renderTraps(GraphicsContext gc, ArrayList<GameObject> traps) {
        for (GameObject trap : traps) {
            gc.fillOval(trap.getPositionX(), trap.getPositionY(), trap.getWidth(), trap.getHeight());
        }
    }

}
