/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.logic;

import fi.sonjaheikkinen.dbhandling.DatabaseHandler;
import java.util.ArrayList;

/**
 * Luokka ohjaa koko peliohjelman toimintaa. Se pitää kirjaa siitä, minkä
 * niminen pelaaja on milläkin hetkellä pelaamassa käynnissä olevaa peliä,
 * muistaa viimeksi päättyneen pelin pisteet, sekä päivittää
 * highScore-listausta. Luokka ei kuitenkaan ohjaa varsinaista peliä.
 *
 */
public class ProgramLogic {

    private final DatabaseHandler dbh;
    private String currentPlayer;
    private long points;
    private ArrayList<String> scoreInfo;

    public ProgramLogic(String database) {
        this.dbh = new DatabaseHandler(database);
        this.scoreInfo = new ArrayList<>();
    }

    /**
     * Metodi hakee tämänhetkisten pisteiden lukumäärän GameLogic-oliolta, ja
     * kutsuu sitten highScoreHandleria, joka tallettaa pisteet tietokantaan.
     *
     * @param currentGame käynnissä olevaa peli-instassia ohjaava GameLogic-olio
     */
    public void updatePoints(GameLogic currentGame) {
        this.points = currentGame.getPoints();
        this.dbh.addPoints(this.currentPlayer, currentGame.getPoints());
    }

    /**
     * Metodi kutsuu parametrin perusteella databaseHandleria lukemaan joko
     * yleiset tai henkilökohtaiset parhaat tulokset.
     *
     * @param highScoreType String-muuttuja, arvo "all" tai "personal"
     */
    public void updateHighScore(String highScoreType) {
        //this.hch.createTables();
        if (highScoreType.equals("all")) {
            this.scoreInfo = this.dbh.readHighScore();
        } else {
            this.scoreInfo = this.dbh.readPersonalHighScore(this.currentPlayer);
        }
    }

    /**
     * Metodi muokkaa high score -listauksen käyttäjäystävälliseksi
     * merkkijonoksi. Jokainen tulos on muotoa: (Järjestysnumero). pelaajanNimi,
     * (pistemäärä) points
     *
     * @return high score -listaus merkkijonona
     */
    public String getHighScoreString() {
        String highScore = "";
        for (int i = 0; i < Math.min(this.scoreInfo.size(), 10); i++) {
            String nextRow = this.scoreInfo.get(i);
            String[] rowInPieces = nextRow.split(":");
            String newRow = (i + 1) + ". " + rowInPieces[0] + ", " + rowInPieces[1] + " points";
            highScore = highScore + newRow + "\n";
        }
        return highScore;
    }

    /**
     * Metodi kutsuu databaseHandleria, joka tarkistaa, löytyykö tietokannasta
     * käyttäjätunnusta annetuilla tiedoilla, ja tarvittaessa tiedottaa
     * käyttäjää vääristä tiedoista kirjoittamalla infotekstiin merkkijonon
     * "Wrong username or password"
     *
     * @param name käyttäjätunnus
     * @param password salasana
     *
     * @return String muuttuja, true jos käyttäjä löytyy, muuten viesti "Wrong username or password"
     */
    public String checkUserInfo(String name, String password) {
        if (!this.dbh.searchUser(name, password)) {
            return "Wrong username or password";
        }
        return "true";
    }

    /**
     * Metodi tarkistaa rekisteröintitietojen kelvollisuuden, ja tarvittaessa
     * kirjoittaa korjausohjeita muuttujaan infoText.
     *
     * @param name käyttäjätunnus
     * @param password salasana
     * 
     * @return Merkkijono true, jos rekisteröintitiedot ok. Muussa tapauksessa palautetaan virheen spesifioiva merkkijono
     */
    public String checkRegisterInfo(String name, String password) {
        if (name.equals("")) {
            return "Name cannot be empty";
        } else if (!name.matches("[a-öA-Ö0-9]+")) {
            return "Name should only contain numbers and letters from A to Ö";
        } else if (name.length() > 32) {
            return "Maximum length of name is 32 characters";
        } else if (this.dbh.nameTaken(name)) {
            return "Name is already taken";
        } else if (password.length() > 64) {
            return "Maximum length of password is 64 characters";
        } else if (password.equals("")) {
            return "Password cannot be empty";
        } else {
            return "true";
        }
    }

    /**
     * Metodi luo uuden käyttäjätunnuksen annetuilla parametreilla.
     *
     * @param name käyttäjänimi
     * @param password salasana
     */
    public void createNewUser(String name, String password) {
        this.currentPlayer = name;
        this.dbh.addUser(name, password);
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public ArrayList<String> getScoreInfo() {
        return scoreInfo;
    }

    public DatabaseHandler getDatabaseHandler() {
        return this.dbh;
    }

}
