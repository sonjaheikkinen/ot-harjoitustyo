/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import domain.GameCharacter;
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
}
