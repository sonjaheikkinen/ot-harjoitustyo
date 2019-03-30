/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.GameCharacter;
import domain.GameObject;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import other.LongValue;

/**
 *
 * @author sonja
 */
public class GameScreenHandler {

    private Scene game;
    private Canvas canvas;

    public GameScreenHandler(Canvas canvas, Scene scene) {
        this.game = scene;
        this.canvas = canvas;
    }

    public void updateGame() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GameCharacter gameCharacter = createGameCharacter();
        ArrayList<GameObject> platforms = createPlatforms();
        handleMouseMovement(game, gameCharacter);
        handleAnimation(gc, gameCharacter, platforms);
    }

    public static GameCharacter createGameCharacter() {
        GameCharacter gameCharacter = new GameCharacter(400, 0, 40, 40, 0, 50);
        return gameCharacter;
    }

    public static ArrayList<GameObject> createPlatforms() {

        ArrayList<GameObject> platforms = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            GameObject platform = new GameObject((3*i*10), (7*i*10), 100, 10, 0 , 0);
            platforms.add(platform);
        }

        return platforms;

    }

    public static void handleMouseMovement(Scene game, GameCharacter character) {

        game.setOnMouseMoved((event) -> {

            double mouseX = event.getSceneX();
            character.setPositionX(mouseX);

        });
    }

    public static void handleAnimation(GraphicsContext gc, GameCharacter gameCharacter, ArrayList<GameObject> platforms) {
        LongValue lastNanoTime = new LongValue(System.nanoTime());
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double elapsedTime = calculateElapsedTime(lastNanoTime, currentNanoTime);
                moveCharacter(elapsedTime, gameCharacter);
                detectCollission(gameCharacter, platforms);
                render(gc, gameCharacter, platforms);
            }
        }.start();
    }

    public static double calculateElapsedTime(LongValue lastNanoTime, long currentNanoTime) {
        double elapsedTime = (currentNanoTime - lastNanoTime.getValue()) / 1000000000.0;
        lastNanoTime.setValue(currentNanoTime);
        return elapsedTime;
    }

    public static void moveCharacter(double elapsedTime, GameCharacter gameCharacter) {
        if (gameCharacter.getJump()) {
            gameCharacter.changeVelocity(0, 1);
            if (gameCharacter.getVelocityY() >= 0) {
                gameCharacter.setJump(false);
                gameCharacter.setVelocity(0, 100);
            }
        } else {
            gameCharacter.setVelocity(0, 100);
        }

        gameCharacter.update(elapsedTime);
    }

    public static void detectCollission(GameCharacter gameCharacter, ArrayList<GameObject> platforms) {
        Iterator<GameObject> platformIterator = platforms.iterator();
        while (platformIterator.hasNext()) {
            GameObject platform = platformIterator.next();
            if (gameCharacter.intersects(platform)) {
                gameCharacter.setVelocity(0, -100);
                gameCharacter.setJump(true);
            }
        }
    }

    public static void render(GraphicsContext gc, GameCharacter gameCharacter, ArrayList<GameObject> platforms) {
        gc.clearRect(0, 0, 400, 500);
        gameCharacter.render(gc);

        for (GameObject platform : platforms) {
            platform.render(gc);
        }
    }

}
