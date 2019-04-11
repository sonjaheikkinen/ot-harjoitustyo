/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.logic;

/**
 *
 * @author sonja
 */
public class ProgramLogic {
    
    private String currentPlayer;
    private long points;

    public void updatePoints(GameLogic currentGame) {
        this.points = currentGame.getPoints();
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
    
    
    
}
