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

    private int points;
    private int gameScreenWidth;
    private int gameScreenHeight;
    private int pointIncrease;
    private int level;
    private int fallingSpeed;
    private int nextGap;
    private int lives;
    private Random random;

    public GameLogic() {
        this.points = 0;
        this.gameScreenWidth = 400;
        this.gameScreenHeight = 500;
        this.pointIncrease = 1;
        this.level = 0;
        this.fallingSpeed = 100;
        this.nextGap = 20;
        this.lives = 3;
        this.random = new Random();
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

    public ArrayList<GameObject> createTraps() {
        ArrayList<GameObject> traps = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GameObject trap = new GameObject(0, 0, 0, 0, 0, 0);
            trap.setAction(true);
            traps.add(trap);
        }
        return traps;
    }

    public GameObject createBoost() {
        GameObject boost = new GameObject(this.random.nextInt(this.gameScreenWidth) - 10, -500, 10, 10, 0, this.fallingSpeed);
        return boost;
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
        for (GameObject platform : platforms) {
            platform.setVelocityY(this.fallingSpeed);
            if (platform.getPositionY() >= this.gameScreenHeight) {
                platform.setWidth(70);
                platform.setPositionY(-10);
                platform.setPositionX(this.random.nextInt(300));
                platform.setAction(false);
            }
            platform.update(elapsedTime);
        }
    }

    public void moveTraps(double elapsedTime, ArrayList<GameObject> traps) {
        for (int i = 0; i < Math.min(traps.size(), this.level); i++) {
            if (this.level != 0) {
                GameObject trap = traps.get(i);
                if (trapGoesOffScreen(trap) || trap.getAction() || (trap.getVelocityY() == 0 && trap.getVelocityX() == 0)) {
                    //trap gone over side or just created or does not move
                    rebootTrap(trap);
                }
                trap.update(elapsedTime);
            }
        }
    }

    public boolean trapGoesOffScreen(GameObject trap) {
        if (trap.getPositionX() < 0 - trap.getWidth() && trap.getVelocityX() < 0
                || trap.getPositionX() > this.gameScreenWidth && trap.getVelocityX() > 0
                || trap.getPositionY() < 0 - trap.getHeight() && trap.getVelocityY() < 0
                || trap.getPositionY() > this.gameScreenHeight && trap.getVelocityY() > 0) {
            return true;
        }
        return false;
    }

    public void rebootTrap(GameObject trap) {
        trap.setAction(false);
        int numberY = this.random.nextInt(2);
        if (numberY == 0) {
            trap.setPositionY(-100);
        } else {
            trap.setPositionY(600);
        }
        trap.setWidth(this.random.nextInt(40) + 5);
        trap.setHeight(trap.getWidth());
        trap.setPositionX(this.random.nextInt(1200) - 400);
        trap.setVelocityX(this.random.nextInt(200));
        if (trap.getVelocityX() % 2 == 0) {
            trap.setVelocityX(trap.getVelocityX() * -1);
        }
        trap.setVelocityY(this.random.nextInt(200));
        if (trap.getVelocityY() % 2 == 0) {
            trap.setVelocityY(trap.getVelocityY() * -1);
        }
    }

    public void moveBoost(double elapsedTime, GameObject boost) {
        if (boost.getAction()) {
            //player has hit boost
            boost.setVelocityY(0);
            boost.setPositionY(600);
            tryReboot(boost);
        } else {
            boost.update(elapsedTime);
            if (boost.getPositionY() > 600 && !boost.getAction()) {
                tryReboot(boost);
            }
        }
    }

    public void tryReboot(GameObject boost) {
        int randomNumber = this.random.nextInt(10);
        if (randomNumber == 5) {
            rebootBoost(boost);
        }
    }

    public void rebootBoost(GameObject boost) {
        boost.setAction(false);
        boost.setPositionY(-500);
        boost.setPositionX(this.random.nextInt(this.gameScreenWidth - (int) boost.getWidth()));
        boost.setVelocityY(this.fallingSpeed);
    }

    public void detectCollissionWithPlatforms(GameObject gameCharacter, ArrayList<GameObject> platforms) {
        Iterator<GameObject> platformIterator = platforms.iterator();
        while (platformIterator.hasNext()) {
            GameObject platform = platformIterator.next();
            if (gameCharacter.intersects(platform) && gameCharacter.getVelocityY() >= 0) {
                gameCharacter.setVelocityY(-500);
                gameCharacter.setAction(true);
                if (!platform.getAction()) {
                    //character has not yet been given points from this platform
                    platform.setAction(true);
                    this.points += this.pointIncrease;
                }
            }
        }
    }

    public void detectDeathOnTrap(GameObject gameCharacter, ArrayList<GameObject> traps) {
        Iterator<GameObject> trapIterator = traps.iterator();
        while (trapIterator.hasNext()) {
            GameObject trap = trapIterator.next();
            if (gameCharacter.intersects(trap)) {
                if (this.lives == 0) {
                    gameCharacter.setPositionY(this.gameScreenHeight + 1);
                } else {
                    this.lives--;
                    rebootTrap(trap);
                }
            }
        }
    }

    public void detectCollissionWithBoost(GameObject gameCharacter, GameObject boost) {
        if (gameCharacter.intersects(boost)) {
            this.lives++;
            boost.setAction(true);
        }
    }

    public void handleLevel() {
        System.out.println("taso = " + this.level);
        if (this.points >= this.nextGap) {
            this.level++;
            this.pointIncrease = this.pointIncrease + this.level * 5;
            this.nextGap = this.nextGap + this.pointIncrease * 50;
        }

    }

    public int getPoints() {
        return this.points;
    }

    public int getLives() {
        return this.lives;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }

}
