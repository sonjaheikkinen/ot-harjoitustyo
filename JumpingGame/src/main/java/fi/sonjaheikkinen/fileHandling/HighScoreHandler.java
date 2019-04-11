/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.fileHandling;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author sonja
 */
public class HighScoreHandler {

    public ArrayList<String> readHighScore() {
        
        ArrayList<String> scoreInfo = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File("highScores.txt"))) {
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                scoreInfo.add(row);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        
        return scoreInfo;

    }

    public void writeHighScore(ArrayList<String> scoreInfo) {
        
        try (PrintWriter writer = new PrintWriter(new File("highScores.txt"))) {
            for (int i = 0; i < scoreInfo.size(); i++) {
                writer.println(scoreInfo.get(i));
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

    }

}
