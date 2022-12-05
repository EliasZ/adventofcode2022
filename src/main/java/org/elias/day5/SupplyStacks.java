package org.elias.day5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class SupplyStacks {
    private final String file;

    public static void main(String[] args) {
        System.out.println(new SupplyStacks("/5/input_test.txt").getTopOfStack());
        System.out.println(new SupplyStacks("/5/input_test.txt").getTopOfStack2());

        System.out.println(new SupplyStacks("/5/input.txt").getTopOfStack());
        System.out.println(new SupplyStacks("/5/input.txt").getTopOfStack2());
    }

    public SupplyStacks(String s) {
        this.file = s;
    }

    // part one
    String getTopOfStack() {
        Map<Integer, Stack<String>> stacks = new HashMap();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;
            boolean readingStacks = true;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    readingStacks = false;

                    stacks.values().forEach(stack -> { stack.pop(); Collections.reverse(stack);});
                    continue;
                }

                if (readingStacks) {
                    int length = line.length();
                    int stackIndex = 1;

                    for (int i = 0; i < length; i += 4) {
                        Stack<String> strings = stacks.computeIfAbsent(stackIndex, k -> new Stack<>());
                        String value = line.substring(i, Math.min(length, i + 4))
                                .replace("[", "")
                                .replace("]", "")
                                .trim();
                        if (!value.isEmpty()) {
                            strings.add(value);
                        }
                        stackIndex++;
                    }
                } else {
                    //move 1 from 2 to 1
                    String[] s = line.split(" ");
                    for (int i = 0; i < Integer.parseInt(s[1]); i++) {
                        stacks.get(Integer.parseInt(s[5])).push(stacks.get(Integer.parseInt(s[3])).pop());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String collect = stacks.keySet().stream().sorted().map(i -> stacks.get(i).pop()).collect(Collectors.joining());
        return collect;
    }

    // part two
    String getTopOfStack2() {
        Map<Integer, Stack<String>> stacks = new HashMap();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;
            boolean readingStacks = true;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    readingStacks = false;

                    stacks.values().forEach(stack -> { stack.pop(); Collections.reverse(stack);});
                    continue;
                }

                if (readingStacks) {
                    int length = line.length();
                    int stackIndex = 1;

                    for (int i = 0; i < length; i += 4) {
                        Stack<String> strings = stacks.computeIfAbsent(stackIndex, k -> new Stack<>());
                        String value = line.substring(i, Math.min(length, i + 4))
                                .replace("[", "")
                                .replace("]", "")
                                .trim();
                        if (!value.isEmpty()) {
                            strings.add(value);
                        }
                        stackIndex++;
                    }
                } else {
                    String[] s = line.split(" ");
                    List<String> toMove = new ArrayList<>();
                    for (int i = 0; i < Integer.parseInt(s[1]); i++) {
                        toMove.add(stacks.get(Integer.parseInt(s[3])).pop());
                    }
                    Collections.reverse(toMove);
                    //System.out.println(toMove + " naar " + Integer.parseInt(s[5]));
                    stacks.get(Integer.parseInt(s[5])).addAll(toMove);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String collect = stacks.keySet().stream().sorted().map(i -> stacks.get(i).pop()).collect(Collectors.joining());
        return collect;
    }
}
