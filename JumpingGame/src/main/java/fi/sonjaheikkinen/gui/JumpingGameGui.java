/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.gui;

import fi.sonjaheikkinen.domain.ProgramInformation;
import fi.sonjaheikkinen.fileHandling.HighScoreHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JumpingGameGui extends Application {

    ProgramInformation pInfo;
    HighScoreHandler highScoreHandler;

    @Override
    public void init() {
        this.pInfo = new ProgramInformation();
        this.highScoreHandler = new HighScoreHandler();
        this.pInfo.setScoreInfo(this.highScoreHandler.readHighScore());
    }

    @Override
    public void start(Stage stage) {

        stage.setWidth(400);
        stage.setHeight(500);

        StageHandler handler = new StageHandler(stage);
        SceneConstructor sc = new SceneConstructor(handler, pInfo);
        handler.setScenes(sc);

        sc.createScenes();

        stage.setTitle("Jumping Game");
        stage.setScene(sc.getStartScene());
        stage.show();
    }
    
    @Override
    public void stop() {
        this.highScoreHandler.writeHighScore(this.pInfo.getScoreInfo());
    }

    public static void main(String args[]) {
        launch(JumpingGameGui.class);
    }

}
