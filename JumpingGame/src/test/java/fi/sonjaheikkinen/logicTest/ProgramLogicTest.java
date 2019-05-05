/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.logicTest;

import fi.sonjaheikkinen.logic.GameLogic;
import fi.sonjaheikkinen.logic.ProgramLogic;
import java.sql.DriverManager;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;
import java.util.Random;

/**
 *
 * @author sonja
 */
public class ProgramLogicTest {

    private ProgramLogic pLogic;
    private GameLogic gLogic;

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
        this.pLogic = new ProgramLogic("jdbc:h2:./test");
        this.gLogic = new GameLogic();
        this.gLogic.setPoints(10);
        this.pLogic.setCurrentPlayer("player1");
        this.pLogic.getDatabaseHandler().createTables();
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void updatePointsAddsPointsToDatabase() {
        this.pLogic.updatePoints(this.gLogic);
        ArrayList<String> list = readScores();
        assertFalse(list.isEmpty());
    }
    
    @Test
    public void updatePointsAddsRigthPointsToDatabase() {
        this.pLogic.updatePoints(this.gLogic);
        ArrayList<String> list = readScores();
        assertTrue(list.contains("player1:10"));
    }
    
    @Test
    public void updateHighScoresFetchesGlobalHighScoreWhenParameterAll() {
       addToScores();
       this.pLogic.updateHighScore("all");
       assertTrue(moreThanOneNameFound(this.pLogic.getScoreInfo()));
    }
    
    @Test
    public void updateHighScoresFetchesPersonalHighScoreWhenParameterPersonal() {
        addToScores();
        this.pLogic.updateHighScore("personal");
        assertFalse(moreThanOneNameFound(this.pLogic.getScoreInfo()));
    }
    
    @Test
    public void highScoreStringInRightForm() {
        this.pLogic.updatePoints(this.gLogic);
        this.pLogic.updateHighScore("all");
        assertTrue(this.pLogic.getHighScoreString().equals("1. player1, 10 points\n"));
    }
    
    @Test
    public void createNewUserWorks() {
        this.pLogic.createNewUser("player1", "test1");
        assertTrue(this.pLogic.getDatabaseHandler().searchUser("player1", "test1"));
    }
   
    
    @Test
    public void checkUserInfoNoticesWrongInfo() {
        this.pLogic.createNewUser("player1", "test1");
        String text = this.pLogic.checkUserInfo("player1", "test2");
        assertTrue(text.equals("Wrong username or password"));
    }
    
    @Test
    public void checkUserInfoReturnsTrueOnRigthInfo() {
        this.pLogic.createNewUser("player1", "test1");
        assertTrue(this.pLogic.checkUserInfo("player1", "test1").equals("true"));
    }
    
    @Test
    public void checkRegisterInfoNoticesIfUsernameTaken() {
        this.pLogic.createNewUser("player1", "test1");
        assertTrue(this.pLogic.checkRegisterInfo("player1", "test2").equals("Name is already taken"));
    }
    
    @Test
    public void checkRegisterInfoNoticesIfNameEmpty() {
        assertTrue(this.pLogic.checkRegisterInfo("", "test1").equals("Name cannot be empty"));
    }
    
    @Test
    public void checkRegisterInfoNoticesIfNameContainsSpecialCharacter() {
        assertTrue(this.pLogic.checkRegisterInfo("player:", "test1").equals("Name should only contain numbers and letters from A to Ö"));
    }
    
    @Test
    public void checkRegisterInfoNameLengthCheckWorksWhenNameLength32() {
        assertTrue(this.pLogic.checkRegisterInfo("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "test1").equals("true"));
    }
    
    @Test
    public void checkRegisterInfoNameLengthCheckWorksWhenNameLengthOver32() {
        assertTrue(this.pLogic.checkRegisterInfo("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "test1").equals("Maximum length of name is 32 characters"));
    }
    
    
    
    
    public boolean moreThanOneNameFound(ArrayList<String> scoreInfo) {
        int names = 1;
        for (int i = 1; i < scoreInfo.size(); i++) {
            String formerName = scoreInfo.get(i - 1).split(":")[0];
            String name = scoreInfo.get(i).split(":")[0];
            if (!name.equals(formerName)) {
                names++;
                break;
            }
        }
        return names > 1;
    }
    
    public void addToScores() {
            Random random = new Random();
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./test", "sa", "")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Scores (name, points) VALUES (?, ?)");
            statement.setString(1, "player1");
            for (int i = 0; i < 5; i++) {
                statement.setInt(2, random.nextInt(100));
                statement.executeUpdate();
            }
            statement.setString(1, "player2");
            for (int i = 0; i < 7; i++) {
                statement.setInt(2, random.nextInt(100));
                statement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public ArrayList<String> readScores() {
        ArrayList<String> scores = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./test", "sa", "")) {
            PreparedStatement statement = connection.prepareStatement("SELECT name, points FROM Scores");
            ResultSet rs = statement.executeQuery();
            rs.next();
            scores.add(rs.getString("name") + ":" + rs.getInt("points"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return scores;
    }

}
