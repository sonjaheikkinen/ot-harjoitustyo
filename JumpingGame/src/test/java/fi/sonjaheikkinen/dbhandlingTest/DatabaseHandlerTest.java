/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.dbhandlingTest;

import fi.sonjaheikkinen.dbhandling.DatabaseHandler;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author sonja
 */
public class DatabaseHandlerTest {

    private DatabaseHandler dbh;

    public DatabaseHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.dbh = new DatabaseHandler("jdbc:h2:./test");
        this.dbh.createTables();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void readHighScoreReturnsEmptyListIfScoresTableEmpty() {
        ArrayList<String> list = this.dbh.readHighScore();
        assertTrue(list.isEmpty());
    }

    @Test
    public void readHighScoreReturnsRightAmountOfStuff1() {
        addToScores(7);
        ArrayList<String> list = this.dbh.readHighScore();
        assertTrue(list.size() == 7);
    }

    @Test
    public void readHighScoreReturnsRightAmountOfStuff2() {
        addToScores(10);
        ArrayList<String> list = this.dbh.readHighScore();
        assertTrue(list.size() == 10);
    }

    @Test
    public void readHighScoreReturnsRightAmountOfStuff3() {
        addToScores(20);
        ArrayList<String> list = this.dbh.readHighScore();
        assertTrue(list.size() == 10);
    }

    @Test
    public void readHighScoreReturnsScoresInOrder1() {
        addToScores(20);
        ArrayList<String> list = this.dbh.readHighScore();
        assertTrue(checkOrder(list));
    }

    @Test
    public void readHighScoreReturnsScoresInOrder2() {
        addPersonalScores();
        ArrayList<String> list = this.dbh.readHighScore();
        assertTrue(checkOrder(list));
    }

    @Test
    public void readPersonalHighScoreReturnsOnlyAskedPersonsScores() {
        addPersonalScores();
        ArrayList<String> list = this.dbh.readPersonalHighScore("player1");
        boolean value = true;
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).split(":")[0].equals("player1")) {
                value = false;
            }
        }
        assertTrue(value);
    }

    @Test
    public void readPersonalHighScoreReturnsRightAmountOfStuff() {
        addPersonalScores();
        ArrayList<String> list = this.dbh.readPersonalHighScore("player1");
        assertTrue(list.size() == 10);
    }

    @Test
    public void addPointsWorks() {
        this.dbh.addPoints("player1", 10);
        ArrayList<String> list = this.dbh.readHighScore();
        assertTrue(list.contains("player1:10"));
    }

    @Test
    public void searchUserReturnsTrueWhenUserInTableUsers() {
        addUser("player1", "test1");
        assertTrue(this.dbh.searchUser("player1", "test1"));
    }

    @Test
    public void searchUserReturnsFalseWhenUserNotInTableUsers() {
        addUser("player1", "test1");
        assertFalse(this.dbh.searchUser("player2", "test1"));
    }

    @Test
    public void searchUserReturnsFalseWhenPasswordDoesNotMatchUser() {
        addUser("player1", "test1");
        addUser("player2", "test2");
        assertFalse(this.dbh.searchUser("player1", "test2"));
    }

    @Test
    public void nameTakenReturnsTrueWhenNameTaken() {
        addUser("player1", "test1");
        assertTrue(this.dbh.nameTaken("player1"));
    }

    @Test
    public void nameTakenReturnsFalseWhenNameNotTaken() {
        addUser("player1", "test1");
        assertFalse(this.dbh.nameTaken("player2"));
    }

    @Test
    public void addUserWorks() {
        this.dbh.addUser("player1", "test1");
        String user = getUser();
        assertTrue(user.equals("player1:test1"));
    }

    public String getUser() {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./test", "sa", "")) {
            PreparedStatement statement = connection.prepareStatement("SELECT name, password FROM Users");
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getString("name") + ":" + rs.getString("password");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "";
    }

    public void addUser(String name, String password) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./test", "sa", "")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Users (name, password) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean checkOrder(ArrayList<String> list) {
        for (int i = 1; i < list.size(); i++) {
            int formerPoints = Integer.parseInt(list.get(i - 1).split(":")[1]);
            int points = Integer.parseInt(list.get(i).split(":")[1]);
            if (formerPoints < points) {
                return false;
            }
        }
        return true;
    }

    public void addToScores(int amount) {
        Random random = new Random();
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./test", "sa", "")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Scores (name, points) VALUES (?, ?)");
            statement.setString(1, "player");
            for (int i = 0; i < amount; i++) {
                statement.setInt(2, random.nextInt(100));
                statement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addPersonalScores() {
        Random random = new Random();
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./test", "sa", "")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Scores (name, points) VALUES (?, ?)");
            statement.setString(1, "player1");
            for (int i = 0; i < 20; i++) {
                statement.setInt(2, random.nextInt(100));
                statement.executeUpdate();
            }
            statement.setString(1, "player2");
            for (int i = 0; i < 17; i++) {
                statement.setInt(2, random.nextInt(100));
                statement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
