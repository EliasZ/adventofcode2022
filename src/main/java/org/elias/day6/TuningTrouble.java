package org.elias.day6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class TuningTrouble {
    private final String file;

    public static void main(String[] args) {
        new TuningTrouble("/6/input_test.txt").afterHowManyDistinct(4);
        new TuningTrouble("/6/input_test.txt").afterHowManyDistinct(14);

        System.out.println("---");
        new TuningTrouble("/6/input.txt").afterHowManyDistinct(4);
        new TuningTrouble("/6/input.txt").afterHowManyDistinct(14);
    }

    public TuningTrouble(String s) {
        this.file = s;
    }

    // part one
    void afterHowManyDistinct(int distinct) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    Set<Character> chars = new HashSet<>();
                    char[] asChars = line.substring(i, Math.min(line.length(), distinct + i)).toCharArray();
                    for (char c : asChars) {
                        chars.add(c);
                    }
                    if (chars.size() == distinct) {
                        System.out.println(distinct + i);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
