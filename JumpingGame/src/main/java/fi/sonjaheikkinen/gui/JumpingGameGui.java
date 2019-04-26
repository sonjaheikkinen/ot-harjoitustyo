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
 * Luokka käynnistää koko ohjelman, ja hoitaa myös high score -tietojen lukemisen tiedostosta ohjelman suorituksen alussa,  
 * ja tietojen päivittämiseen kirjastoon suorituksen lopuksi.
 */
public class JumpingGameGui extends Application {

    ProgramLogic pLogic;

    /**
     * Metodi luo ohjelman toimintaa hoitavan olion pLogic, ja kutsuu sen metodia setScoreInfo, joka hakee high score 
     * -tiedot tiedostosta.
     */
    @Override
    public void init() {
        this.pLogic = new ProgramLogic();
        this.pLogic.setScoreInfo("highScores.txt");
    }
    
    /**
     * Metodi hoitaa varsinaisen ohjelman suorituksen. Se asettaa ikkunan kooksi 400x500, luo näkymiä hallitsevan 
     * StageHandler-olion ja näkymiä rakentavan SceneConstructor-olion, ja rakentaa niiden avulla ohjelman eri näkymät 
     * ja niiden väliset suhteet. Lopuksi se asettaa ikkunaan aloitusnäkymän ja asettaa ikkunan näkyväksi käyttäjälle. 
     * @param stage ikkuna
     */
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
    
    /**
     * Metodi kutsuu ohjelmalogiikan metodia saveScoreInfo(), joka tallentaa high score -tiedot tiedostoon. 
     */
    @Override
    public void stop() {
        this.pLogic.saveScoreInfo("highScores.txt");
    }

    /**
     * Metodi suorittaa ohjelman kutsumalla ensin metodia init(), sitten metodia start(), ja lopuksi metodia stop().
     * @param args String args
     */
    public static void main(String args[]) {
        launch(JumpingGameGui.class);
    }

}
