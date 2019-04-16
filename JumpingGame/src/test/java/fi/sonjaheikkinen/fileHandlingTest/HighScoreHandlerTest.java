/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.fileHandlingTest;

import fi.sonjaheikkinen.fileHandling.HighScoreHandler;
import java.io.File;
import java.io.PrintWriter;
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
public class HighScoreHandlerTest {

    private HighScoreHandler hsh;

    public HighScoreHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.hsh = new HighScoreHandler();
        try (PrintWriter writer = new PrintWriter(new File("test.txt"))) {
            writer.println("");
            writer.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
    }

    
    @Test
    public void HighScoreHandlerWritingWorks() {
        ArrayList<String> test = new ArrayList<>();
        test.add("player:10");
        this.hsh.writeHighScore(test, "test.txt");
        ArrayList<String> content = this.hsh.readHighScore("test.txt");
        assertTrue(content.contains("player:10"));
    }
    
    @Test
    public void HighScoreHandlerWritesAllRows() {
        ArrayList<String> test = new ArrayList<>();
        test.add("player1:10");
        test.add("player2:20");
        test.add("player3:30");
        this.hsh.writeHighScore(test, "test.txt");
        ArrayList<String> content = this.hsh.readHighScore("test.txt");
        assertTrue(content.size() == 3);
    }
    
    
}
