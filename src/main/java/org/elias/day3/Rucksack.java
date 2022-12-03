package org.elias.day3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

public class Rucksack {
    private final String file;

    public static void main(String[] args) {
        System.out.println(new Rucksack("/3/input_test.txt").getPrioritySum());
        System.out.println(new Rucksack("/3/input_test.txt").getBadgePrioritySum());

        System.out.println(new Rucksack("/3/input.txt").getPrioritySum());
        System.out.println(new Rucksack("/3/input.txt").getBadgePrioritySum());
    }

    public Rucksack(String s) {
        this.file = s;
    }

    // part one
    int getPrioritySum() {
        int prioritiesSum = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                Set<Character> compartmentA = line.substring(0, line.length() / 2)
                        .chars()
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.toSet());
                OptionalInt charInCompartmentBAndA = line.substring(line.length() / 2)
                        .chars()
                        .filter(c -> compartmentA.contains((char) c))
                        .findFirst();
                prioritiesSum += getPrio(charInCompartmentBAndA);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return prioritiesSum;
    }

    // part two
    int getBadgePrioritySum() {
        int prioritiesSum = 0;
        List<String> perThree = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                perThree.add(line);
                if (perThree.size() != 3) {
                    continue;
                }

                Set<Character> rucksackA = perThree.get(0)
                        .chars()
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.toSet());
                Set<Character> rucksackB = perThree.get(1)
                        .chars()
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.toSet());
                OptionalInt charInABC = perThree.get(2)
                        .chars()
                        .filter(c -> rucksackA.contains((char) c) && rucksackB.contains((char) c))
                        .findFirst();
                prioritiesSum += getPrio(charInABC);
                perThree.clear();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return prioritiesSum;
    }

    private int getPrio(OptionalInt charInABC) {
        if (charInABC.isEmpty()) {
            throw new IllegalStateException();
        }
        char chr = (char) charInABC.getAsInt();
        int priority = 0;
        if (chr >= 'A' && chr <= 'Z')
            priority += ((int) chr - 'A' + 1) + 26;
        if (chr >= 'a' && chr <= 'z')
            priority += ((int) chr - 'a' + 1);

        return priority;
    }
}
