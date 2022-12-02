package org.elias.day2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RockPaperScissors {
    private final String file;

    public static void main(String[] args) {
        System.out.println(new RockPaperScissors("/2/input_test.txt").getTotalScore());
        System.out.println(new RockPaperScissors("/2/input_test.txt").getTotalScoreTwo());

        System.out.println(new RockPaperScissors("/2/input.txt").getTotalScore());
        System.out.println(new RockPaperScissors("/2/input.txt").getTotalScoreTwo());
    }

    public RockPaperScissors(String s) {
        this.file = s;
    }

    // part one
    int getTotalScore() {
        Map<String, Map<String, Integer>> scores = Map.of(
                "A", Map.of(    "X", 4,
                                "Y", 8,
                                "Z", 3),
                "B", Map.of(    "X", 1,
                                "Y", 5,
                                "Z", 9),
                "C", Map.of(    "X", 7,
                                "Y", 2,
                                "Z", 6)
        );
        int score = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                score += scores.get(s[0]).get(s[1]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return score;
    }

    // part two
    int getTotalScoreTwo() {
        Map<String, Map<String, Integer>> scores = Map.of(
                "A", Map.of(    "X", 3,
                                "Y", 4,
                                "Z", 8),
                "B", Map.of(    "X", 1,
                                "Y", 5,
                                "Z", 9),
                "C", Map.of(    "X", 2,
                                "Y", 6,
                                "Z", 7)
        );
        int score = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                score += scores.get(s[0]).get(s[1]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return score;
    }
}
