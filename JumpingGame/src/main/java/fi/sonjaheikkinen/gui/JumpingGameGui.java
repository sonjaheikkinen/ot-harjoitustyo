/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.gui;

import fi.sonjaheikkinen.logic.ProgramLogic;
import fi.sonjaheikkinen.fileHandling.HighScoreHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JumpingGameGui extends Application {

    ProgramLogic pLogic;
    HighScoreHandler highScoreHandler;

    @Override
    public void init() {
        this.pLogic = new ProgramLogic();
        this.highScoreHandler = new HighScoreHandler();
        this.pLogic.setScoreInfo(this.highScoreHandler.readHighScore("highScores.txt"));
    }

    @Override
    public void start(Stage stage) {

        stage.setWidth(400);
        stage.setHeight(500);

        StageHandler handler = new StageHandler(stage);
        SceneConstructor sc = new SceneConstructor(handler, pLogic);
        handler.setScenes(sc);

        sc.createScenes();

        stage.setTitle("Jumping Game");
        stage.setScene(sc.getStartScene());
        stage.show();
    }
    
    @Override
    public void stop() {
        this.highScoreHandler.writeHighScore(this.pLogic.getScoreInfo(), "highScores.txt");
    }

    public static void main(String args[]) {
        launch(JumpingGameGui.class);
    }

}
