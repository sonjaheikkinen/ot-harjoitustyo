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
    private GameObject testCharacter;
    private ArrayList<GameObject> platforms;
    private ArrayList<GameObject> testPlatform;
    private ArrayList<GameObject> traps;
    private GameObject boost;

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
        this.character = this.gameLogic.createGameCharacter();
        this.testCharacter = new GameObject(0, 0, 40, 40, 0, 0);
        this.platforms = this.gameLogic.createPlatforms();
        GameObject platform = new GameObject(0, 0, 100, 10, 0, 0);
        this.testPlatform = new ArrayList<>();
        this.testPlatform.add(platform);
        this.traps = this.gameLogic.createTraps();
        this.boost = this.gameLogic.createBoost();
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
        this.gameLogic.moveTraps(100, traps);
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
        this.gameLogic.moveTraps(100, traps);
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
        this.gameLogic.moveTraps(100, traps);
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
        this.gameLogic.moveCharacter(0, character);
        assertTrue(this.character.getVelocityY() == 500);
    }

    @Test
    public void moveCharacterChangesVelocityDowndWardsCorrectlyIfCharacterJumpStateTrue() {
        this.character.setAction(true);
        this.character.setVelocityY(-500);
        this.gameLogic.moveCharacter(0, character);
        assertTrue(this.character.getVelocityY() == -480);
    }

    @Test
    public void moveCharacterSetsCharacterJumpStateFalseWhenCharacterReachesFallingSpeed() {
        this.character.setAction(true);
        this.character.setVelocityY(480);
        this.gameLogic.moveCharacter(0, character);
        assertFalse(this.character.getAction());
    }

    @Test
    public void moveCharacterSetsCharacterVelocityYFallingSpeedIfCharacterFallsTooFastInJump() {
        this.character.setAction(true);
        this.character.setVelocityY(490);
        this.gameLogic.moveCharacter(0, character);
        assertTrue(this.character.getVelocityY() == 500);
    }

    @Test
    public void movePlatformDropsPlatformRigthAmountPerSecond() {
        this.gameLogic.movePlatforms(1, testPlatform);
        assertTrue(this.testPlatform.get(0).getPositionY() == 100);
    }

    @Test
    public void movePlatformLiftsPlatformUpWhenPlatformReachesBottom() {
        this.testPlatform.get(0).setPositionY(500);
        this.gameLogic.movePlatforms(0, testPlatform);
        assertTrue(this.testPlatform.get(0).getPositionY() == -10);
    }

    @Test
    public void detectCollissionSetsCharacterJumpStateTrueIfCharacterTouchesPlatform() {
        this.testPlatform.get(0).setPositionY(39);
        this.gameLogic.detectCollissionWithPlatforms(testCharacter, testPlatform);
        assertTrue(this.testCharacter.getAction());
    }

    @Test
    public void detectCollissionSetsCharacterVelocityYCorrecltyIfCharacterTouchesPlatform() {
        this.testPlatform.get(0).setPositionY(39);
        this.gameLogic.detectCollissionWithPlatforms(testCharacter, testPlatform);
        assertTrue(this.testCharacter.getVelocityY() == -500);
    }

    @Test
    public void detectCollissionDoesNotMakeCharacterJumpIfCharacterAlreadyGoingUp() {
        this.testPlatform.get(0).setPositionY(39);
        this.testCharacter.setVelocityY(-10);
        this.gameLogic.detectCollissionWithPlatforms(testCharacter, testPlatform);
        assertTrue(testCharacter.getVelocityY() == -10);
    }
    
    @Test
    public void boostInitialActionStateFalse() {
        assertFalse(this.boost.getAction());
    }
    
    @Test
    public void boostActionStateTrueAfterCollidingWithCharacter() {
        this.testCharacter.setPositionX(100);
        this.testCharacter.setPositionY(100);
        this.boost.setPositionX(101);
        this.boost.setPositionY(101);
        this.gameLogic.detectCollissionWithBoost(this.testCharacter, this.boost);
        assertTrue(this.boost.getAction());
    }
    
    @Test
    public void moveBoostMovesBoostOffScreenWhenActionStateTrue() {
        this.boost.setAction(true);
        this.boost.setPositionX(100);
        this.boost.setPositionY(100);
        this.gameLogic.moveBoost(100, boost);
        boolean offScreen = false;
        if (this.boost.getPositionX() < 0 || this.boost.getPositionX() > 400
                || this.boost.getPositionY() < 0 || this.boost.getPositionY() > 500) {
            offScreen = true;
        }
        assertTrue(offScreen);
    }

}
