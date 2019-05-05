/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.gui;

import fi.sonjaheikkinen.logic.ProgramLogic;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Luokka käynnistää ohjelman graafisen käyttöliittymän, ja luo sen toimintaa ohjaavan 
 * ProgramLogic-olion.
 */
public class JumpingGameGui extends Application {

    /**
     * Metodi hoitaa varsinaisen ohjelman suorituksen. Se asettaa ikkunan kooksi
     * 400x500 ja luo ohjelman toimintaa ohjaavan ProgramLogic-olion. Sitten se luo 
     * näkymiä vaihtavan StageHandlerin ja näkymän muodostavan SceneConstructorin, jotka yhdessä ProgramLogicin
     * kanssa muodostavat ja näyttävät aina oikean näkymän avautuvassa ikkunassa stage.
     *
     * @param stage ikkuna
     */
    @Override
    public void start(Stage stage) {

        ProgramLogic pLogic = new ProgramLogic("jdbc:h2:./highScores");

        stage.setWidth(400);
        stage.setHeight(500);

        StageHandler handler = new StageHandler(stage);
        SceneConstructor sc = new SceneConstructor(handler, pLogic);
        handler.setScenes(sc);

        sc.createScenes();

        stage.setTitle("Jumping Game");
        stage.setScene(sc.getLoginOrRegister());
        stage.show();
    }

    /**
     * Metodi suorittaa ohjelman kutsumalla ensin metodia init(), sitten metodia
     * start(), ja lopuksi metodia stop().
     *
     * @param args String args
     */
    public static void main(String args[]) {
        launch(JumpingGameGui.class);
    }

}
