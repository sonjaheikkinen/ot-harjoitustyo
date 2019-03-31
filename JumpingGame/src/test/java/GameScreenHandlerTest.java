/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import domain.GameCharacter;
import domain.GameObject;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import logic.GameScreenHandler;
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
public class GameScreenHandlerTest {

    public GameScreenHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void moveCharacterSetsVelocityY500ifCharacterJumpValueFalse() {
        GameCharacter character = new GameCharacter(0, 0, 0, 0, 0, 0);
        GameScreenHandler.moveCharacter(10, character);
        assertTrue(character.getVelocityY() == 500);
    }
    
    @Test
    public void GameCharacterXisAlwaysSameAsMouseX() {
        GameCharacter character = new GameCharacter(0, 0, 40, 40, 0, 0);
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.setOnMouseMoved((event) -> {
            double mouseX = event.getSceneX();
            GameScreenHandler.handleMouseMovement(scene, character);
            assertTrue(character.getPositionX() == mouseX);
        });
        
    }

    @Test
    public void detectCollissionMakesCharacterJumpIfCharacterTouchesPlatform() {
        GameCharacter character = new GameCharacter(0, 0, 40, 40, 0, 0);
        GameObject platform = new GameObject(0, 39, 10, 100, 0, 0);
        ArrayList<GameObject> platforms = new ArrayList<>();
        platforms.add(platform);
        GameScreenHandler.detectCollission(character, platforms);
        assertTrue(character.getJump() == true);
    }

    @Test
    public void detectCollissionSetsCharacterVelocityYNegative500IfCharacterTouchesPlatform() {
        GameCharacter character = new GameCharacter(0, 0, 40, 40, 0, 0);
        GameObject platform = new GameObject(0, 39, 10, 100, 0, 0);
        ArrayList<GameObject> platforms = new ArrayList<>();
        platforms.add(platform);
        GameScreenHandler.detectCollission(character, platforms);
        assertTrue(character.getVelocityY() == -500);
    }

}
