package org.elias.day9;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RopeBridge {
    private final String file;

    public static void main(String[] args) {
        new RopeBridge("/9/input_test.txt").solve(); // 13
        new RopeBridge("/9/input.txt").solve();
    }

    private final Map<Character, int[]> DIRECTIONS = Map.of(  // d -> [y,x]
            'U', new int[] {-1, 0}, // up
            'D', new int[] {1, 0}, // down
            'L', new int[] {0, -1}, // left
            'R', new int[] {0, 1} // right
    );

    public RopeBridge(String s) {
        this.file = s;
    }

    void solve() {
        // directions, pos -> 0 = y, 1 = x
        int[] head = new int[]{0, 0};
        int[] tail = new int[]{0, 0};
        Set<String> seenByTail = new LinkedHashSet<>();
        seenByTail.add(Arrays.toString(tail));

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                char dir = s[0].charAt(0);
                int steps = Integer.parseInt(s[1]);
                int[] action = DIRECTIONS.get(dir);

                for (int i = 0; i < steps; i++) {
                    head[0] += action[0];
                    head[1] += action[1];
                    tailStickToHead(head, tail, dir);
                    seenByTail.add(Arrays.toString(tail));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(seenByTail.size());
    }

    private void tailStickToHead(int[] head, int[] tail, char dir) {
        if (head[0] == tail[0] && head[1] == tail[1]) {
            return; // same pos
        }
        if (Math.abs(head[0] -tail[0]) < 2 && Math.abs(head[1] - tail[1]) < 2) {
            return; // < 2 diff is ok
        }
        switch (dir) {
            case 'U' -> { // put under
                tail[0] = head[0] + 1;
                tail[1] = head[1];
            }
            case 'D' -> { // put above
                tail[0] = head[0] - 1;
                tail[1] = head[1];
            }
            case 'L' -> { // put right of
                tail[0] = head[0];
                tail[1] = head[1] + 1;
            }
            case 'R' -> { // put left of
                tail[0] = head[0];
                tail[1] = head[1] - 1;
            }
        }
    }
}
