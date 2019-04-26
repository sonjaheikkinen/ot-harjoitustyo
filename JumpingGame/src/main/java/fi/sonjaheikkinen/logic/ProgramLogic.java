/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.logic;

import fi.sonjaheikkinen.filehandling.HighScoreHandler;
import java.util.ArrayList;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Luokka ohjaa koko peliohjelman toimintaa. Se pitää kirjaa siitä, minkä niminen pelaaja on milläkin hetkellä 
 * pelaamassa käynnissä olevaa peliä, muistaa viimeksi päättyneen pelin pisteet, sekä päivittää highScore-listausta.
 * Luokka ei kuitenkaan ohjaa varsinaista peliä.
 * 
 */
public class ProgramLogic {

    private HighScoreHandler hch;
    private String currentPlayer;
    private long points;
    private ArrayList<String> scoreInfo;

    public ProgramLogic() {
        this.hch = new HighScoreHandler();
        this.scoreInfo = new ArrayList<>();
    }
    
    /**
     * Metodi hakee tämänhetkisten pisteiden lukumäärän GameLogic-oliolta, ja päivittää sen omaan kirjanpitoonsa
     * 
     * @param currentGame käynnissä olevaa peli-instassia ohjaava GameLogic-olio
     */
    public void updatePoints(GameLogic currentGame) {
        this.points = currentGame.getPoints();
    }

    /**
     * Metodi lisää uuden pelituloksen listaan, mikäli listan koko on pienempi kuin kymmenen, tai uusin tulos on
     * suurempi kuin jokin listalla jo oleva tulos. Jos listan koko on lopuksi suurempi kuin kymmenen, pudotetaan pienin
     * tulos pois, jotta listan koko palautuu oikeaksi. Apuna käytetään metodia addScoreToList()
     * 
     * @see this.addScoreToList()
     */
    public void updateHighScore() {
        boolean added;
        ArrayList<String> newHighScoreList = new ArrayList<>();
        added = addScoreToList(newHighScoreList);
        if (this.scoreInfo.size() < 10 && !added) {
            newHighScoreList.add(this.currentPlayer + ":" + this.points);
        }
        if (newHighScoreList.size() > 10) {
            newHighScoreList.remove(newHighScoreList.size() - 1);
        }
        this.scoreInfo = newHighScoreList;
    }

    /**
     * Metodi käy läpi tämänhetkisen high score -listauksen, ja sijoittaa uusimman tuloksen oikeaan kohtaan parametrina
     * saatavaa tuloslistausta, mikäli uusin tulos on suurempi kuin jokin listalla jo oleva tulos. 
     * @param newHighScoreList String-olioita sisältävä ArrayList, johon uusi tulos mahdollisesti lisätääns
     * @return palautetaan true, jos uusin tulos lisättiin listaan, muuten false
     */
    public boolean addScoreToList(ArrayList<String> newHighScoreList) {
        boolean added = false;
        for (int i = 0; i < Math.min(scoreInfo.size(), 10); i++) {
            String score = scoreInfo.get(i);
            String[] info = score.split(":");
            int scorePoints = Integer.parseInt(info[1]);
            if (this.points >= scorePoints && !added) {
                newHighScoreList.add(this.currentPlayer + ":" + this.points);
                added = true;
            }
            newHighScoreList.add(score);
        }
        return added;
    }

    /**
     * Metodi muokkaa high score -listauksen käyttäjäystävälliseksi merkkijonoksi. Jokainen tulos on muotoa: 
     * (Järjestysnumero). pelaajanNimi, (pistemäärä) points
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
     * Metodi tarkistaa, onko pelaajan itselleen määrittelemä nimimerkki sääntöjen mukainen. Jos kenttä on tyhjä, 
     * syötetään parametrina annettuun infokenttään teksti "Name cannot be Empty". Jos kenttä sisältää erikoismerkkejä, 
     * syötetään samaan kenttään teksti "Name should only contain numbers and letters from A to Ö". 
     * 
     * @param nameInstruction Infokenttä, joka tarvittaessa kertoo pelaajalle, miten nimimerkkiä pitää korjata, jotta se
     * olisi hyväksytty
     * @param playerName tekstikenttä, johon pelaaja on syöttänyt valitsemansa nimimerkin
     * 
     * @return palautetaan true, jos nimi on sääntöjen mukainen, muuten false
     */
    public boolean checkName(Text nameInstruction, TextField playerName) {
        if (playerName.getText().equals("") || playerName.getText() == null) {
            nameInstruction.setText("Name cannot be empty");
            return false;
        } else if (!playerName.getText().matches("[a-öA-Ö0-9]+")) {
            nameInstruction.setText("Name should only contain numbers and letters from A to Ö");
            return false;
        } else {
            nameInstruction.setText("");
            return true;
        }
    }
    
    /**
     * Metodi asettaa muuttujan scoreInfo arvoksi highScoreHandlerin metodin readHighScore palauttaman merkkijonon.
     * 
     * @param filename luettavan tiedoston nimi
     */
    public void setScoreInfo(String filename) {
        this.scoreInfo = this.hch.readHighScore(filename);
    }
    
    /**
     * Metodi kutsuu highScoreHandlerin metodia writeHighScore, ja antaa sille parametriksi pistetiedot sisältävän 
     * merkkijonon scoreInfo.
     * 
     * @param filename kirjoitettavan tiedoston nimi
     */
    public void saveScoreInfo(String filename) {
        this.hch.writeHighScore(this.scoreInfo, "filename");
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

}
