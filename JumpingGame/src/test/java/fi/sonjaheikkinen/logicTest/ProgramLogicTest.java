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
    
    private ProgramLogic pInfo;
    
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
        this.pInfo = new ProgramLogic();
        this.pInfo.setPoints(0);
        this.pInfo.setCurrentPlayer("test");
        this.pInfo.setScoreInfo(new ArrayList<>());
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void highScoreListGrowsWhenUpdatingIfSizeUnder10() {
        int size1 = this.pInfo.getScoreInfo().size();
        this.pInfo.updateHighScore();
        int size2 = this.pInfo.getScoreInfo().size();
        assertTrue(size2 > size1);
    }
    
    @Test
    public void highScreListDoesNotGrowWhenUpdatingIfSizeIs10() {
        for (int i = 0; i < 11; i++) {
            this.pInfo.updateHighScore();
        }
        assertTrue(this.pInfo.getScoreInfo().size() == 10);        
    }
    
    @Test
    public void highScoreListPrintsCorrectly() {
        this.pInfo.updateHighScore();
        assertEquals("1. test, 0 points\n", this.pInfo.getHighScoreString());
    }

     
}
