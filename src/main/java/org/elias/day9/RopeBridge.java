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
        new RopeBridge("/9/input_test.txt").solve(1); // 13
        new RopeBridge("/9/input_test_long.txt").solve(9); // 36

        new RopeBridge("/9/input.txt").solve(1);
        new RopeBridge("/9/input.txt").solve(9);
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

    void solve(int tailSize) {
        // directions, pos -> 0 = y, 1 = x
        int[] head = new int[]{0, 0}; // 15,11 example long
        List<int[]> tails = new ArrayList<>();
        for (int i = 0; i < tailSize; i++) {
            tails.add(new int[]{0, 0});
        }
        Set<String> seenByTail = new LinkedHashSet<>();
        seenByTail.add(Arrays.toString(tails.get(tailSize - 1)));

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

                    tailStickToHead(head, tails.get(0), dir);
                    for (int j = 1; j < tailSize; j++) {
                        tailStickToHead(tails.get(j - 1), tails.get(j), dir);
                    }

                    seenByTail.add(Arrays.toString(tails.get(tailSize - 1)));
                    //print(tailSize, head, tails);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(seenByTail.size());
    }

    // 30x30 input_test_long.txt, challenge 2 long example
    private static void print(int tailSize, int[] head, List<int[]> tails) {
        Map<Integer, Map<Integer, String>> map = new HashMap<>();

        for (int y = 0; y < 30; y++) {
            map.put(y, new HashMap<>());
            for (int x = 0; x < 30; x++) {
                map.get(y).put(x, ".");
            }
        }

        map.get(head[0]).put(head[1], "H");
        for (int j = 0; j < tailSize; j++) {
            int[] tail = tails.get(j);
            map.get(tail[0]).put(tail[1], String.valueOf(j + 1));
        }

        for (int y = 0; y < map.size(); y++) {
            Map<Integer, String> row = map.get(y);
            for (int x = 0; x < row.size() ; x++) {
                System.out.print(row.get(x));
            }
            System.out.println();
        }
    }

    private void tailStickToHead(int[] head, int[] tail, char dir) {
        if (head[0] == tail[0] && head[1] == tail[1]) {
            return; // same pos
        }
        if (Math.abs(head[0] - tail[0]) < 2 && Math.abs(head[1] - tail[1]) < 2) {
            return; // < 2 diff is ok
        }
        if (head[0] != tail[0]) {
            if (head[0] - tail[0] > 0)
                tail[0] = tail[0] + 1;
            else
                tail[0] = tail[0] - 1;
        }
        if (head[1] != tail[1]) {
            if (head[1] - tail[1] > 0)
                tail[1] = tail[1] + 1;
            else
                tail[1] = tail[1] - 1;
        }
    }
}
