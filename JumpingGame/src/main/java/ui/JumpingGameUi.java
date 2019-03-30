/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JumpingGameUi extends Application {

    @Override
    public void start(Stage stage) {
        
        SceneConstructor sc = new SceneConstructor();

        sc.createGameScene();
        Scene game = sc.getGameScene();

        stage.setTitle("Jumping Game");
        stage.setScene(game);
        stage.show();
    }

    public static void main(String args[]) {
        launch(JumpingGameUi.class);
    }



}
