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
    private Text gameInfo;
    private ProgramLogic pLogic;

    public GameScreenHandler(Canvas canvas, Scene scene, Text gameInfo, StageHandler handler, ProgramLogic pLogic) {
        this.game = scene;
        this.canvas = canvas;
        this.gLogic = new GameLogic();
        this.stageHandler = handler;
        this.gameInfo = gameInfo;
        this.pLogic = pLogic;
    }

    public void updateGame() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GameObject gameCharacter = this.gLogic.createGameCharacter();
        ArrayList<GameObject> platforms = this.gLogic.createPlatforms();
        ArrayList<GameObject> traps = this.gLogic.createTraps();
        GameObject boost = this.gLogic.createBoost();
        handleMouseMovement(game, gameCharacter);
        handleAnimation(gc, gameCharacter, platforms, traps, boost);
    }

    public static void handleMouseMovement(Scene game, GameObject character) {
        game.setOnMouseMoved((event) -> {
            double mouseX = event.getSceneX();
            character.setPositionX(mouseX);
        });
    }

    public void handleAnimation(GraphicsContext gc, GameObject gameCharacter, ArrayList<GameObject> platforms,
            ArrayList<GameObject> traps, GameObject boost) {
        LongValue lastNanoTime = new LongValue(System.nanoTime());
        GameLogic gameLogic = this.gLogic;
        ProgramLogic programLogic = this.pLogic;
        StageHandler handler = this.stageHandler;
        Text info = this.gameInfo;
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (gameCharacter.getPositionY() > 500) {
                    stopGame(programLogic, gameLogic, handler);
                    this.stop();
                } else {
                    updateGameScreen(currentNanoTime, lastNanoTime, gameLogic, gameCharacter, platforms, traps, boost,
                            info, gc);
                }
            }
        }.start();
    }

    public void stopGame(ProgramLogic programLogic, GameLogic gameLogic, StageHandler handler) {
        programLogic.updatePoints(gameLogic);
        programLogic.updateHighScore();
        handler.gameOver();
    }

    public void updateGameScreen(long currentNanoTime, LongValue lastNanoTime, GameLogic gameLogic, GameObject gameCharacter,
            ArrayList<GameObject> platforms, ArrayList<GameObject> traps, GameObject boost, Text gameInfo,
            GraphicsContext gc) {
        double elapsedTimeInSeconds = calculateElapsedTime(lastNanoTime, currentNanoTime);
        moveGameObjects(elapsedTimeInSeconds, gameLogic, gameCharacter, platforms, traps, boost);
        detectCollission(gameLogic, gameCharacter, platforms, traps, boost);
        gameLogic.handleLevel();
        gameInfo.setText("Points: \n" + gameLogic.getPoints() + "\nLives: \n" + gameLogic.getLives());
        render(gc, gameCharacter, platforms, traps, boost);
    }

    public double calculateElapsedTime(LongValue lastNanoTime, long currentNanoTime) {
        double elapsedTime = (currentNanoTime - lastNanoTime.getValue()) / 1000000000.0;
        lastNanoTime.setValue(currentNanoTime);
        return elapsedTime;
    }

    public void moveGameObjects(double elapsedTimeInSeconds, GameLogic gameLogic, GameObject gameCharacter,
            ArrayList<GameObject> platforms, ArrayList<GameObject> traps, GameObject boost) {
        gameLogic.moveCharacter(elapsedTimeInSeconds, gameCharacter);
        gameLogic.movePlatforms(elapsedTimeInSeconds, platforms);
        gameLogic.moveTraps(elapsedTimeInSeconds, traps);
        gameLogic.moveBoost(elapsedTimeInSeconds, boost);
    }

    public void detectCollission(GameLogic gameLogic, GameObject gameCharacter, ArrayList<GameObject> platforms,
            ArrayList<GameObject> traps, GameObject boost) {
        gameLogic.detectCollissionWithPlatforms(gameCharacter, platforms);
        gameLogic.detectDeathOnTrap(gameCharacter, traps);
        gameLogic.detectCollissionWithBoost(gameCharacter, boost);
    }

    public void render(GraphicsContext gc, GameObject gameCharacter, ArrayList<GameObject> platforms,
            ArrayList<GameObject> traps, GameObject boost) {
        gc.setFill(javafx.scene.paint.Color.WHITE);
        gc.clearRect(0, 0, 400, 500);
        renderCharacter(gc, gameCharacter);
        renderPlatforms(gc, platforms);
        renderTraps(gc, traps);
        renderBoost(gc, boost);
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

    public void renderBoost(GraphicsContext gc, GameObject boost) {
        double posX = boost.getPositionX();
        double posY = boost.getPositionY();
        double w = boost.getWidth();
        double h = boost.getHeight();
        gc.fillPolygon(new double[]{posX, posX + w, posX + (w / 2)}, new double[]{posY, posY, posY - h}, 3);
    }

}
