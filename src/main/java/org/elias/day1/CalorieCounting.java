package org.elias.day1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalorieCounting {
    private final String file;

    public static void main(String[] args) {
        System.out.println(new CalorieCounting("/1/input_test.txt").getMostCalories());
        System.out.println(new CalorieCounting("/1/input_test.txt").getMostCaloriesTopThree());

        System.out.println(new CalorieCounting("/1/input.txt").getMostCalories());
        System.out.println(new CalorieCounting("/1/input.txt").getMostCaloriesTopThree());
    }

    public CalorieCounting(String s) {
        this.file = s;
    }

    // part one
    int getMostCalories() {
        int mostCalories = 0;
        int countingCalories = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) {
                    countingCalories += Integer.parseInt(line);
                } else {
                    if (countingCalories > mostCalories) {
                        mostCalories = countingCalories;
                    }
                    countingCalories = 0;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mostCalories;
    }

    // part two
    int getMostCaloriesTopThree() {
        List<Integer> totals = new ArrayList<>();
        int countingCalories = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) {
                    countingCalories += Integer.parseInt(line);
                } else {
                    totals.add(countingCalories);
                    countingCalories = 0;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        totals.sort(Collections.reverseOrder());
        int total = 0;
        for (int i = 0; i < 3; i++) {
            total += totals.get(i);
        }
        return total;
    }
}
