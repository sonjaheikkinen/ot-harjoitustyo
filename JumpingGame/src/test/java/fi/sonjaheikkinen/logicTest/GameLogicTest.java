/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.logicTest;

import fi.sonjaheikkinen.domain.GameCharacter;
import fi.sonjaheikkinen.domain.GameObject;
import fi.sonjaheikkinen.logic.GameLogic;
import java.util.ArrayList;
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
    private GameCharacter character;
    private GameCharacter testCharacter;
    private ArrayList<GameObject> platforms;
    private ArrayList<GameObject> testPlatform;
    
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
        this.testCharacter = new GameCharacter(0, 0, 40, 40, 0, 0);
        this.platforms = this.gameLogic.createPlatforms();
        GameObject platform = new GameObject(0, 0, 100, 10, 0, 0);
        this.testPlatform = new ArrayList<>();
        this.testPlatform.add(platform);
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
        assertTrue(this.character.getHeigth() == 15);
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
    public void createPlatformCreatesFivePlatforms() {
        assertTrue(this.platforms.size() == 5);
    }
    
    @Test
    public void moveCharacterSetsCharacterVelocityYCorrecltyIfCharacterJumpStateFalse() {
        this.gameLogic.moveCharacter(0, character);
        assertTrue(this.character.getVelocityY() == 500);
    }
    
    @Test
    public void moveCharacterChangesVelocityDowndWardsCorrectlyIfCharacterJumpStateTrue() {
        this.character.setJump(true);
        this.character.setVelocityY(0, -500);
        this.gameLogic.moveCharacter(0, character);
        assertTrue(this.character.getVelocityY() == -480);
    }
    
    @Test
    public void moveCharacterSetsCharacterJumpStateFalseWhenCharacterReachesFallingSpeed() {
        this.character.setJump(true);
        this.character.setVelocityY(0, 480);
        this.gameLogic.moveCharacter(0, character);
        assertFalse(this.character.getJump());
    }
    
    @Test
    public void moveCharacterSetsCharacterVelocityYFallingSpeedIfCharacterFallsTooFastInJump() {
        this.character.setJump(true);
        this.character.setVelocityY(0, 490);
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
        this.gameLogic.detectCollission(testCharacter, testPlatform);
        assertTrue(this.testCharacter.getJump());
    }
    
    @Test
    public void detectCollissionSetsCharacterVelocityYCorrecltyIfCharacterTouchesPlatform() {
        this.testPlatform.get(0).setPositionY(39);
        this.gameLogic.detectCollission(testCharacter, testPlatform);
        assertTrue(this.testCharacter.getVelocityY() == -500);
    }
    
    @Test
    public void detectCollissionDoesNotMakeCharacterJumpIfCharacterAlreadyGoingUp() {
        this.testPlatform.get(0).setPositionY(39);
        this.testCharacter.setVelocityY(0, -10);
        this.gameLogic.detectCollission(testCharacter, testPlatform);
        assertTrue(testCharacter.getVelocityY() == -10);
    }
    
}
