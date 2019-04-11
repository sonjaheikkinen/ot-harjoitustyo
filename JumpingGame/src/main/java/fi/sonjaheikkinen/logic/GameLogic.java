/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.logic;

import fi.sonjaheikkinen.domain.GameObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author sonja
 */
public class GameLogic {
    
    private long points;
    
    public GameLogic() {
        this.points = 0;
    }

    public GameObject createGameCharacter() {
        GameObject gameCharacter = new GameObject(360, 0, 15, 15, 0, 0);
        return gameCharacter;
    }

    public ArrayList<GameObject> createPlatforms() {

        ArrayList<GameObject> platforms = new ArrayList<>();

        GameObject groundPlatform = new GameObject(0, 200, 400, 2, 0, 0);
        platforms.add(groundPlatform);

        for (int i = 0; i < 4; i++) {
            int platformY = i * 100;
            if (platformY >= 200) {
                platformY = i * 100 + 100;
            }
            GameObject platform = new GameObject((3 * i * 10), platformY, 70, 2, 0, 0);
            platforms.add(platform);
        }

        return platforms;

    }

    public void moveCharacter(double elapsedTimeInSeconds, GameObject gameCharacter) {
        if (gameCharacter.getAction()) {
            gameCharacter.changeVelocity(0, 20);
            if (gameCharacter.getVelocityY() >= 500) {
                gameCharacter.setAction(false);
                gameCharacter.setVelocityY(500);
            }
        } else {
            gameCharacter.setVelocityY(500);
        }

        gameCharacter.update(elapsedTimeInSeconds);
    }
    
    public void movePlatforms(double elapsedTime, ArrayList<GameObject> platforms) {
        Random random = new Random();
        for (GameObject platform : platforms) {
            platform.setVelocityY(100);
            if (platform.getPositionY() >= 500) {
                platform.setWidth(70);
                platform.setPositionY(-10);
                platform.setPositionX(random.nextInt(300));
                platform.setAction(false);
            }
            platform.update(elapsedTime);
        }      
    }

    public void detectCollission(GameObject gameCharacter, ArrayList<GameObject> platforms) {
        Iterator<GameObject> platformIterator = platforms.iterator();
        while (platformIterator.hasNext()) {
            GameObject platform = platformIterator.next();
            if (gameCharacter.intersects(platform) && gameCharacter.getVelocityY() >= 0) {
                gameCharacter.setVelocityY(-500);
                gameCharacter.setAction(true);
                if (!platform.getAction()) {
                    //character has not yet been given points from this platform
                    platform.setAction(true);
                    this.points += 10;
                }
            }
        }
    }
    
    public long getPoints() {
        return this.points;
    }

}
