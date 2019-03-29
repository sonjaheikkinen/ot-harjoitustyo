/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import domain.GameCharacter;
import domain.PracticeGameCharacter;
import domain.Sprite;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import other.LongValue;

public class JumpingGameUi extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("Jumping Game");

        Group root = new Group();
        Scene game = new Scene(root);
        stage.setScene(game);

        Canvas canvas = new Canvas(400, 500);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        //GameCharacter character = new PracticeGameCharacter();
        ArrayList<Double> mouseMovementInput = new ArrayList<>();

        GameCharacter gameCharacter = new GameCharacter();
        gameCharacter.setPositionX(180);
        gameCharacter.setPositionY(400);
        gameCharacter.setWidth(40);
        gameCharacter.setHeight(40);

        game.setOnMouseMoved((event) -> {

            double mouseX = event.getSceneX();
            gameCharacter.setPositionX(mouseX);

            /*
            double mouseX = event.getSceneX();
            double characterX;
            if (mouseX < 0) {
                characterX = 0;
            } else if (mouseX > 400) {
                characterX = 400;
            } else {
                characterX = mouseX;
            }
            character.setX(characterX);
             */
        });

        game.setOnMouseClicked((event) -> {
            /*
            character.setMovement(0, -5);
            character.setJump(true);
             */
        });

        ArrayList<Sprite> platforms = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            Sprite platform = new Sprite();
            platform.setPositionX(3 * i * 10);
            platform.setPositionY(7 * i * 10);
            platform.setWidth(100);
            platform.setHeight(10);
            platforms.add(platform);
        }

        LongValue lastNanoTime = new LongValue(System.nanoTime());

        new AnimationTimer() {

            @Override
            public void handle(long currentNanoTime) {

                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.getValue()) / 1000000000.0;
                lastNanoTime.setValue(currentNanoTime);

                // game logic
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

                // collision detection
                Iterator<Sprite> platformIterator = platforms.iterator();
                while (platformIterator.hasNext()) {
                    Sprite platform = platformIterator.next();
                    if (gameCharacter.intersects(platform)) {
                        gameCharacter.setVelocity(0, -100);
                        gameCharacter.setJump(true);
                    }
                }

                // render
                gc.clearRect(0, 0, 400, 500);
                gameCharacter.render(gc);

                for (Sprite platform : platforms) {
                    platform.render(gc);
                }

                /*
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                //System.out.println(t);
                System.out.println(character.getY());
                
                if (character.getJump() == true) {
                    character.changeMovement(0, 0.1);
                    if (character.getMovement().getY() >= 0) {
                        character.setJump(false);
                        character.setMovement(0, 1);
                    }
                } else if (character.getY() >= 460) {
                    character.setMovement(0, 0);
                }
                double characterX = character.getX();
                Point2D characterMovement = character.getMovement();
                character.setY(character.getY() + characterMovement.getY());
                double characterY = character.getY();
                
                gc.clearRect(0, 0, 400, 600);
                gc.fillRect(characterX, characterY, 40, 40);
                 */
            }

        }.start();

        stage.show();
    }

    public static void main(String args[]) {
        launch(JumpingGameUi.class);
    }

}
