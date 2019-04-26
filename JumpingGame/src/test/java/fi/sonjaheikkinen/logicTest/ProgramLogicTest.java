/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.logicTest;

import fi.sonjaheikkinen.logic.ProgramLogic;
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
public class ProgramLogicTest {
    
    private ProgramLogic pLogic;
    
    public ProgramLogicTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.pLogic = new ProgramLogic();
        this.pLogic.setPoints(0);
        this.pLogic.setCurrentPlayer("test");        
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void highScoreListGrowsWhenUpdatingIfSizeUnder10() {
        int size1 = this.pLogic.getScoreInfo().size();
        this.pLogic.updateHighScore();
        int size2 = this.pLogic.getScoreInfo().size();
        assertTrue(size2 > size1);
    }
    
    @Test
    public void highScreListDoesNotGrowWhenUpdatingIfSizeIs10() {
        for (int i = 0; i < 11; i++) {
            this.pLogic.updateHighScore();
        }
        assertTrue(this.pLogic.getScoreInfo().size() == 10);        
    }
    
    @Test
    public void highScoreListPrintsCorrectly() {
        this.pLogic.updateHighScore();
        assertEquals("1. test, 0 points\n", this.pLogic.getHighScoreString());
    }

     
}
