/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.logic;

import fi.sonjaheikkinen.domain.GameCharacter;
import fi.sonjaheikkinen.domain.GameObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author sonja
 */
public class GameLogic {

    public GameCharacter createGameCharacter() {
        GameCharacter gameCharacter = new GameCharacter(360, 0, 40, 40, 0, 0);
        return gameCharacter;
    }

    public ArrayList<GameObject> createPlatforms() {

        ArrayList<GameObject> platforms = new ArrayList<>();

        GameObject groundPlatform = new GameObject(0, 400, 400, 10, 0, 0);
        platforms.add(groundPlatform);

        for (int i = 0; i < 4; i++) {
            GameObject platform = new GameObject((3 * i * 10), (i * 100), 100, 10, 0, 0);
            platforms.add(platform);
        }

        return platforms;

    }

    public void moveCharacter(double elapsedTimeInSeconds, GameCharacter gameCharacter) {
        if (gameCharacter.getJump()) {
            gameCharacter.changeVelocity(0, 20);
            if (gameCharacter.getVelocityY() >= 500) {
                gameCharacter.setJump(false);
                gameCharacter.setVelocity(0, 500);
            }
        } else {
            gameCharacter.setVelocity(0, 500);
        }

        gameCharacter.update(elapsedTimeInSeconds);
    }
    
    public void movePlatforms(double elapsedTime, ArrayList<GameObject> platforms) {
        Random random = new Random();
        for (GameObject platform : platforms) {
            platform.setVelocity(0, 100);
            if (platform.getPositionY() >= 500) {
                platform.setWidth(100);
                platform.setPositionY(-10);
                platform.setPositionX(random.nextInt(300));
            }
            platform.update(elapsedTime);
        }      
    }

    public void detectCollission(GameCharacter gameCharacter, ArrayList<GameObject> platforms) {
        Iterator<GameObject> platformIterator = platforms.iterator();
        while (platformIterator.hasNext()) {
            GameObject platform = platformIterator.next();
            if (gameCharacter.intersects(platform)) {
                gameCharacter.setVelocity(0, -500);
                gameCharacter.setJump(true);
            }
        }
    }

}
