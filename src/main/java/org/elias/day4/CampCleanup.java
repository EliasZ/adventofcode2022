package org.elias.day4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

public class CampCleanup {
    private final String file;

    public static void main(String[] args) {
        System.out.println(new CampCleanup("/4/input_test.txt").getFullyContainsCount());
        System.out.println(new CampCleanup("/4/input_test.txt").getOverlappingCount());

        System.out.println(new CampCleanup("/4/input.txt").getFullyContainsCount());
        System.out.println(new CampCleanup("/4/input.txt").getOverlappingCount());
    }

    public CampCleanup(String s) {
        this.file = s;
    }

    // part one
    int getFullyContainsCount() {
        int count = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] pairs = line.split(",");
                String[] a = pairs[0].split("-");
                String[] b = pairs[1].split("-");

                int a0 = Integer.parseInt(a[0]);
                int a1 = Integer.parseInt(a[1]);
                int b0 = Integer.parseInt(b[0]);
                int b1 = Integer.parseInt(b[1]);

                if ((a0 >= b0 && a1 <= b1) || (b0 >= a0 && b1 <= a1)) {
                    count++;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    // part two
    int getOverlappingCount() {
        int count = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] pairs = line.split(",");
                String[] a = pairs[0].split("-");
                String[] b = pairs[1].split("-");

                int a0 = Integer.parseInt(a[0]);
                int a1 = Integer.parseInt(a[1]);
                int b0 = Integer.parseInt(b[0]);
                int b1 = Integer.parseInt(b[1]);

                if (a0 <= b1 && b0 <= a1) {
                    count++;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return count;
    }
}
