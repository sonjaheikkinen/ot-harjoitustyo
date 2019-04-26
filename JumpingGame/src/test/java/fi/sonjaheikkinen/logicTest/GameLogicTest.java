/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.logicTest;

import fi.sonjaheikkinen.domain.GameObject;
import fi.sonjaheikkinen.logic.GameLogic;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sonja
 */
public class GameLogicTest {

    private GameLogic gameLogic;
    private GameObject character;
    private ArrayList<GameObject> platforms;
    private ArrayList<GameObject> traps;
    private GameObject boost;
    private GameObject testPlatform;

    public GameLogicTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.gameLogic = new GameLogic();
        this.gameLogic.createGameObjects();
        this.character = this.gameLogic.getGameCharacter();
        this.platforms = this.gameLogic.getPlatforms();
        this.traps = this.gameLogic.getTraps();
        this.boost = this.gameLogic.getBoost();
        this.testPlatform = this.platforms.get(0);
        this.testPlatform.setWidth(70);
        this.testPlatform.setPositionX(0);
        this.testPlatform.setPositionY(50);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void characterInitialPositionXSetCorrectly() {
        assertTrue(this.character.getPositionX() == 360);
    }

    @Test
    public void characterInitialPositionYSetCorrectly() {
        assertTrue(this.character.getPositionY() == 0);
    }

    @Test
    public void characterInitialWidthSetCorrectly() {
        assertTrue(this.character.getWidth() == 15);
    }

    @Test
    public void characterInitialHeigthSetCorrectly() {
        assertTrue(this.character.getHeight() == 15);
    }

    @Test
    public void characterInitialVelocityXSetCorrectly() {
        assertTrue(this.character.getVelocityX() == 0);
    }

    @Test
    public void characterInitialVelocityYSetCorrectly() {
        assertTrue(this.character.getVelocityY() == 0);
    }

    @Test
    public void createPlatformsCreatesFivePlatforms() {
        assertTrue(this.platforms.size() == 5);
    }

    @Test
    public void createTrapsCreatesTenTraps() {
        assertTrue(this.traps.size() == 10);
    }

    @Test
    public void moveTrapsDoesNotMoveTrapsIfLevelIsZero() {
        this.gameLogic.setLevel(0);
        ArrayList<Double> trapXPositions1 = getTrapPositions("x");
        ArrayList<Double> trapYPositions1 = getTrapPositions("y");
        this.gameLogic.setElapsedTimeInSeconds(100);
        this.gameLogic.moveTraps();
        ArrayList<Double> trapXPositions2 = getTrapPositions("x");
        ArrayList<Double> trapYPositions2 = getTrapPositions("y");
        boolean moved = false;
        for (int i = 0; i < this.traps.size(); i++) {
            if (!Objects.equals(trapXPositions1.get(i), trapXPositions2.get(i))
                    || !Objects.equals(trapYPositions1.get(i), trapYPositions2.get(i))) {
                moved = true;
            }
        }
        assertFalse(moved);
    }

    @Test
    public void amountOfMovingTrapsMatchesLevelIfLevelIsTenOrSmaller() {
        Random random = new Random();
        int level = random.nextInt(10) + 1;
        this.gameLogic.setLevel(level);
        ArrayList<Double> trapXPositions1 = getTrapPositions("x");
        ArrayList<Double> trapYPositions1 = getTrapPositions("y");
        this.gameLogic.setElapsedTimeInSeconds(100);
        this.gameLogic.moveTraps();
        ArrayList<Double> trapXPositions2 = getTrapPositions("x");
        ArrayList<Double> trapYPositions2 = getTrapPositions("y");
        int moved = 0;
        for (int i = 0; i < this.traps.size(); i++) {
            if (!Objects.equals(trapXPositions1.get(i), trapXPositions2.get(i))
                    || !Objects.equals(trapYPositions1.get(i), trapYPositions2.get(i))) {
                moved++;
            }
        }
        assertEquals(moved, level);
    }

    @Test
    public void amountOfMovingTrapsIsTenIfLevelOverTen() {
        this.gameLogic.setLevel(17);
        ArrayList<Double> trapXPositions1 = getTrapPositions("x");
        ArrayList<Double> trapYPositions1 = getTrapPositions("y");
        this.gameLogic.setElapsedTimeInSeconds(100);
        this.gameLogic.moveTraps();
        ArrayList<Double> trapXPositions2 = getTrapPositions("x");
        ArrayList<Double> trapYPositions2 = getTrapPositions("y");
        int moved = 0;
        for (int i = 0; i < this.traps.size(); i++) {
            if (!Objects.equals(trapXPositions1.get(i), trapXPositions2.get(i))
                    || !Objects.equals(trapYPositions1.get(i), trapYPositions2.get(i))) {
                moved++;
            }
        }
        assertEquals(moved, 10);
    }

    public ArrayList<Double> getTrapPositions(String axis) {
        ArrayList<Double> positions = new ArrayList<>();
        for (int i = 0; i < this.traps.size(); i++) {
            GameObject trap = this.traps.get(i);
            double position;
            if (axis.equals("x")) {
                position = trap.getPositionX();
            } else {
                position = trap.getPositionY();
            }
            positions.add(position);
        }
        return positions;
    }

    @Test
    public void moveCharacterSetsCharacterVelocityYCorrecltyIfCharacterJumpStateFalse() {
        this.gameLogic.setElapsedTimeInSeconds(0);
        this.gameLogic.moveCharacter();
        assertTrue(this.character.getVelocityY() == 500);
    }

    @Test
    public void moveCharacterChangesVelocityDowndWardsCorrectlyIfCharacterJumpStateTrue() {
        this.character.setAction(true);
        this.character.setVelocityY(-500);
        this.gameLogic.setElapsedTimeInSeconds(0);
        this.gameLogic.moveCharacter();
        assertTrue(this.character.getVelocityY() == -480);
    }

    @Test
    public void moveCharacterSetsCharacterJumpStateFalseWhenCharacterReachesFallingSpeed() {
        this.character.setAction(true);
        this.character.setVelocityY(480);
        this.gameLogic.setElapsedTimeInSeconds(0);
        this.gameLogic.moveCharacter();
        assertFalse(this.character.getAction());
    }

    @Test
    public void moveCharacterSetsCharacterVelocityYFallingSpeedIfCharacterFallsTooFastInJump() {
        this.character.setAction(true);
        this.character.setVelocityY(490);
        this.gameLogic.setElapsedTimeInSeconds(0);
        this.gameLogic.moveCharacter();
        assertTrue(this.character.getVelocityY() == 500);
    }

    @Test
    public void movePlatformDropsPlatformRigthAmountPerSecond() {
        this.gameLogic.setElapsedTimeInSeconds(1);
        this.gameLogic.movePlatforms();
        assertTrue(this.testPlatform.getPositionY() == 150);
    }

    @Test
    public void movePlatformLiftsPlatformUpWhenPlatformReachesBottom() {
        this.testPlatform.setPositionY(600);
        this.gameLogic.setElapsedTimeInSeconds(0);
        this.gameLogic.movePlatforms();
        assertTrue(this.testPlatform.getPositionY() == -10);
    }

    @Test
    public void detectCollissionSetsCharacterJumpStateTrueIfCharacterTouchesPlatform() {
        this.character.setPositionY(50);
        this.character.setPositionX(0);
        this.gameLogic.detectCollissionWithPlatforms();
        assertTrue(this.character.getAction());
    }

    @Test
    public void detectCollissionSetsCharacterVelocityYCorrecltyIfCharacterTouchesPlatform() {
        this.character.setPositionY(50);
        this.character.setPositionX(0);
        this.gameLogic.detectCollissionWithPlatforms();
        assertTrue(this.character.getVelocityY() == -500);
    }

    @Test
    public void detectCollissionDoesNotMakeCharacterJumpIfCharacterAlreadyGoingUp() {
        this.character.setPositionY(50);
        this.character.setPositionX(0);
        this.character.setVelocityY(-10);
        this.gameLogic.detectCollissionWithPlatforms();
        assertTrue(this.character.getVelocityY() == -10);
    }

    @Test
    public void boostInitialActionStateFalse() {
        assertFalse(this.boost.getAction());
    }

    @Test
    public void boostActionStateTrueAfterCollidingWithCharacter() {
        this.character.setPositionX(100);
        this.character.setPositionY(100);
        this.boost.setPositionX(101);
        this.boost.setPositionY(101);
        this.gameLogic.detectCollissionWithBoost();
        assertTrue(this.boost.getAction());
    }

    @Test
    public void moveBoostMovesBoostOffScreenWhenActionStateTrue() {
        this.boost.setAction(true);
        this.boost.setPositionX(100);
        this.boost.setPositionY(100);
        this.gameLogic.setElapsedTimeInSeconds(100);
        this.gameLogic.moveBoost();
        boolean offScreen = false;
        if (this.boost.getPositionX() < 0 || this.boost.getPositionX() > 400
                || this.boost.getPositionY() < 0 || this.boost.getPositionY() > 500) {
            offScreen = true;
        }
        assertTrue(offScreen);
    }

}
