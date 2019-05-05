package fi.sonjaheikkinen.dbhandling;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Luokka vastaa ohjelman ja tietokannan välisestä kommunikoinnista
 */
public class DatabaseHandler {
    
    private final String database;
    
    public DatabaseHandler(String database) {
        this.database = database;
    }

    /**
     * Metodi lukee kymmenen parasta pelitulosta tietokantataulusta Scores, ja tallentaa ne Stringejä 
     * sisältävään ArrayListiin muodossa käyttäjätunnus:pisteet. 
     * 
     * @return Tulokset ArrayListina
     */
    public ArrayList<String> readHighScore() {
        ArrayList<String> scoreInfo = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.database, "sa", "")) {
            PreparedStatement statement = connection.prepareStatement("SELECT TOP 10 name, points FROM Scores"
                    + " ORDER BY points DESC");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String pointInfo = rs.getString("name") + ":" + rs.getInt("points");
                scoreInfo.add(pointInfo);
            }
        } catch (Exception e) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
        }
        return scoreInfo;
    }

    /**
     * Metodi hakee parametrina annetun pelaajan pelituloksista kymmenen parasta tietokantataulusta Scores, 
     * ja tallentaa ne Stringejä sisältävään ArrayListiin muodossa käyttäjätunnus:pisteet.
     * 
     * @param playerName pelaajan käyttäjätunnus String-muuttujana
     * 
     * @return pelitulokset ArrayListina
     */
    public ArrayList<String> readPersonalHighScore(String playerName) {
        ArrayList<String> scoreInfo = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.database, "sa", "")) {
            PreparedStatement statement = connection.prepareStatement("SELECT TOP 10 name, points FROM Scores"
                    + " WHERE name = ?"
                    + " ORDER BY points DESC");
            statement.setString(1, playerName);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String pointInfo = rs.getString("name") + ":" + rs.getInt("points");
                scoreInfo.add(pointInfo);
            }
        } catch (Exception e) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
        }
        return scoreInfo;
    }

    /**
     * Metodi lisää pistetuloksen tietokantaan tauluun Scores.
     * 
     * @param player pelajaan käyttäjätunnus String-muuttujana
     * @param points lisättävä pistemäärä integerinä
     */
    public void addPoints(String player, int points) {
        try (Connection connection = DriverManager.getConnection(this.database, "sa", "")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Scores (name, points) VALUES (?, ?)");
            statement.setString(1, player);
            statement.setInt(2, points);
            statement.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Metodi tarkistaa, löytyykö tietokannan Users-taulusta käyttäjää joka täsmää annettuihin parametreihin
     * 
     * @param name pelaajan käyttäjtunnus String-muuttujana
     * @param password pelaajan salasana String-muuttujana
     * 
     * @return boolean-arvo, joka kertoo löytyikö etsittyä käyttäjää
     */
    public boolean searchUser(String name, String password) {
        boolean returnValue = false;
        try (Connection connection = DriverManager.getConnection(this.database, "sa", "")) {
            PreparedStatement statement = connection.prepareStatement("SELECT name, password FROM Users"
                    + " WHERE name = ? AND password = ?");
            statement.setString(1, name);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            returnValue = rs.next();
        } catch (Exception e) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnValue;
    }

    /**
     * Metodi tarkastaa, onko parametrina annettu käyttäjänimi jo käytössä jollan toisella käyttäjällä.
     * 
     * @param name tarkistettava käyttäjänimi
     * 
     * @return true, jos nimi on käytössä, muuten false
     */
    public boolean nameTaken(String name) {
        boolean returnValue = false;
        try (Connection connection = DriverManager.getConnection(this.database, "sa", "")) {
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM Users"
                    + " WHERE name = ?");
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            returnValue = rs.next();
        } catch (Exception e) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnValue;
    }

    /**
     * Metodi lisää uuden käyttäjän tietokantatauluun Users.
     * 
     * @param name käyttäjätunnus
     * @param password salasana
     */
    public void addUser(String name, String password) {
        try (Connection connection = DriverManager.getConnection(this.database, "sa", "")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Users (name, password) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Metodi luo uudet tyhjät tietokantataulut. Metodia ei käytetä tavallisesti ohjelman aikana, mutta se on jätetty 
     * ylläpitoa ja testausta varten.
     */
    public void createTables() {

        try (Connection conn = DriverManager.getConnection(this.database, "sa", "")) {

            conn.prepareStatement("DROP TABLE Scores IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE Scores(id serial, name varchar(32), points integer);").executeUpdate();

            conn.prepareStatement("DROP TABLE Users IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE Users(id serial, name varchar(32), password varchar(64));").executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
        }

    }

}
