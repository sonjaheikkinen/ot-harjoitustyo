/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.GameCharacter;
import domain.Sprite;
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
        ArrayList<Sprite> platforms = createPlatforms();
        handleMouseMovement(game, gameCharacter);
        handleAnimation(gc, gameCharacter, platforms);
    }

    public static GameCharacter createGameCharacter() {
        GameCharacter gameCharacter = new GameCharacter();
        gameCharacter.setPositionX(180);
        gameCharacter.setPositionY(400);
        gameCharacter.setWidth(40);
        gameCharacter.setHeight(40);
        return gameCharacter;
    }

    public static ArrayList<Sprite> createPlatforms() {

        ArrayList<Sprite> platforms = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            Sprite platform = new Sprite();
            platform.setPositionX(3 * i * 10);
            platform.setPositionY(7 * i * 10);
            platform.setWidth(100);
            platform.setHeight(10);
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

    public static void handleAnimation(GraphicsContext gc, GameCharacter gameCharacter, ArrayList<Sprite> platforms) {
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

    public static void detectCollission(GameCharacter gameCharacter, ArrayList<Sprite> platforms) {
        Iterator<Sprite> platformIterator = platforms.iterator();
        while (platformIterator.hasNext()) {
            Sprite platform = platformIterator.next();
            if (gameCharacter.intersects(platform)) {
                gameCharacter.setVelocity(0, -100);
                gameCharacter.setJump(true);
            }
        }
    }

    public static void render(GraphicsContext gc, GameCharacter gameCharacter, ArrayList<Sprite> platforms) {
        gc.clearRect(0, 0, 400, 500);
        gameCharacter.render(gc);

        for (Sprite platform : platforms) {
            platform.render(gc);
        }
    }

}
