/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.sonjaheikkinen.filehandling;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author sonja
 */
public class HighScoreHandler {

    public ArrayList<String> readHighScore(String fileName) {

        ArrayList<String> scoreInfo = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                if (row.matches("([a-öA-Ö0-9])+:[0-9]+")) {
                    scoreInfo.add(row);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return scoreInfo;

    }

    public void writeHighScore(ArrayList<String> scoreInfo, String fileName) {

        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            for (int i = 0; i < scoreInfo.size(); i++) {
                writer.println(scoreInfo.get(i));
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

    }

}
