package org.elias.day8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class TreetopTreeHouse {
    private final String file;

    public static void main(String[] args) {
        new TreetopTreeHouse("/8/input_test.txt").solve(); // 21
        new TreetopTreeHouse("/8/input.txt").solve(); // 95437
    }

    public TreetopTreeHouse(String s) {
        this.file = s;
    }

    void solve() {
        Map<Integer, List<Integer>> grid = new HashMap<>(); // y => [x]

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;

            while ((line = br.readLine()) != null) {
                grid.put(grid.size(), line.chars().mapToObj(Character::getNumericValue).toList());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int visible = 0;
        int highestScenicScore = 0;
        int height = grid.size();
        for (int y = 0; y < height; y++) {
            List<Integer> row = grid.get(y);
            int length = row.size();
            for (int x = 0; x < length; x++) {
                if (y == 0 || y == height - 1 || x == 0 || x == length - 1) { // border
                    visible++;
                } else if (isVisible(y, x, grid)) {
                    visible++;
                }

                int scenicScore = getScenicScore(y, x, grid);
                if (scenicScore > highestScenicScore) {
                    highestScenicScore = scenicScore;
                }
            }
        }

        System.out.println("visible: " + visible);
        System.out.println("best score: " + highestScenicScore);
    }

    private final List<int[]> DIRECTIONS = List.of(  // [y,x]
            new int[] {-1, 0}, // up
            new int[] {1, 0}, // down
            new int[] {0, -1}, // left
            new int[] {0, 1} // right
    );
    private boolean isVisible(int startY, int startX, Map<Integer, List<Integer>> grid) {
        int height = grid.size();
        int length = grid.get(0).size();
        int tree = grid.get(startY).get(startX);

        for (int[] direction : DIRECTIONS) {
            boolean reachedEndOfDirecton = false;
            boolean foundABlockingTree = false;
            int y = startY;
            int x = startX;
            do {
                y += direction[0];
                x += direction[1];

                if (y < 0 || y == height || x < 0 || x == length) {
                    reachedEndOfDirecton = true;
                } else {
                    int treeB = grid.get(y).get(x);
                    if (treeB >= tree) {
                        foundABlockingTree = true;
                        break;
                    }
                }
            } while (!reachedEndOfDirecton);
            if (!foundABlockingTree) {
                return true;
            }
        }

        return false;
    }

    private int getScenicScore(int startY, int startX, Map<Integer, List<Integer>> grid) {
        int height = grid.size();
        int length = grid.get(0).size();
        int tree = grid.get(startY).get(startX);

        List<Integer> scores = new ArrayList<>();

        for (int[] direction : DIRECTIONS) {
            boolean reachedEndOfDirecton = false;
            int directionScore = 0;

            int y = startY;
            int x = startX;
            do {
                y += direction[0];
                x += direction[1];

                if (y < 0 || y == height || x < 0 || x == length) {
                    reachedEndOfDirecton = true;
                } else {
                    int treeB = grid.get(y).get(x);
                    directionScore++;

                    if (treeB >= tree) {
                        // blocking tree
                        break;
                    }
                }
            } while (!reachedEndOfDirecton);
            scores.add(directionScore);
        }

        return scores.stream().reduce(1, (a, b) -> a * b);
    }
}
