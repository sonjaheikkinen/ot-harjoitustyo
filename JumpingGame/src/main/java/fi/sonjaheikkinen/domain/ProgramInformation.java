/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.domain;

import fi.sonjaheikkinen.logic.GameLogic;
import java.util.ArrayList;

/**
 *
 * @author sonja
 */
public class ProgramInformation {

    private String currentPlayer;
    private long points;
    private ArrayList<String> scoreInfo;

    public void updatePoints(GameLogic currentGame) {
        this.points = currentGame.getPoints();
    }

    public void updateHighScore() {
        boolean added = false;
        ArrayList<String> newHighScoreList = new ArrayList<>();
        for (int i = 0; i < Math.min(scoreInfo.size(), 20); i++) {
            String score = scoreInfo.get(i);
            String[] info = score.split(":");
            int scorePoints = Integer.parseInt(info[1]);
            if (this.points > scorePoints && !added) {
                newHighScoreList.add(this.currentPlayer + ":" + this.points);
                added = true;
            }
            newHighScoreList.add(score);
        }
        if (this.scoreInfo.size() < 20 && !added) {
            newHighScoreList.add(this.currentPlayer + ":" + this.points);
        }
        this.scoreInfo = newHighScoreList;
    }

    public String getHighScoreString() {
        String highScore = "";
        for (int i = 0; i < Math.min(this.scoreInfo.size(), 20); i++) {
            String nextRow = this.scoreInfo.get(i);
            String[] rowInPieces = nextRow.split(":");
            String newRow = (i + 1) + ". " + rowInPieces[0] + ", " + rowInPieces[1] + " points";
            highScore = highScore + newRow + "\n";
        }
        return highScore;
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

    public void setScoreInfo(ArrayList<String> scoreInfo) {
        this.scoreInfo = scoreInfo;
    }

}
