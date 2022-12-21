package org.elias.day14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import static java.lang.Character.isDigit;

public class RegolithReservoir {
    public static final char ROCK = '#';
    public static final char AIR = '.';
    public static final char SAND = 'o';
    private final String file;

    public static void main(String[] args) {
        new RegolithReservoir("/14/input_test.txt").solve();
        new RegolithReservoir("/14/input.txt").solve();
    }

    public RegolithReservoir(String s) {
        this.file = s;
    }

    private final Map<Character, int[]> DIRECTIONS = Map.of(  // [y,x]
            'U', new int[] {-1, 0}, // up
            'D', new int[] {1, 0}, // down
            'L', new int[] {0, -1}, // left
            'R', new int[] {0, 1} // right
    );
    private final Map<Integer, Map<Integer, Character>> map = new HashMap<>(); // y => x

    void solve() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] split = line.split(" -> "); // x,y !
                int[] prevPos = null;
                for (String s : split) {
                    String[] pos  = s.split(",");
                    int[] curPos = new int[] {Integer.parseInt(pos[0]), Integer.parseInt(pos[1])};
                    if (prevPos != null) {
                        int[] direction = new int[] {(int) Math.signum(curPos[0] - prevPos[0]), (int) Math.signum(curPos[1] - prevPos[1])};
                        while (prevPos[0] != curPos[0] || prevPos[1] != curPos[1]) {
                            prevPos[0] += direction[0];
                            prevPos[1] += direction[1];
                            put(prevPos[1], prevPos[0], ROCK);
                        }
                    } else {
                        put(curPos[1], curPos[0], ROCK);
                        prevPos = curPos;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("--a--");
        a();
        System.out.println("--b--");
        b();
        System.out.println();
    }

    private void a() {
        int maxY = map.keySet().stream().max(Comparator.naturalOrder()).orElseThrow();
        System.out.println("maxY = " + maxY);
        boolean sandCanStillFall = true;
        int fallen = 0;
        while (sandCanStillFall) {
            int sandX = 500;
            int sandY = 0;
            boolean sandIsFalling = true;
            while (sandIsFalling) {
                if (get(sandY + 1, sandX) == AIR) {
                    sandY += 1;
                } else if (get(sandY + 1, sandX - 1) == AIR) {
                    sandY += 1;
                    sandX -= 1;
                } else if (get(sandY + 1, sandX + 1) == AIR) {
                    sandY += 1;
                    sandX += 1;
                } else {
                    sandIsFalling = false;
                    put(sandY, sandX, SAND);
                    fallen++;
                }
                if (sandY >= maxY) {
                    sandCanStillFall = false;
                    break;
                }
            }
        }
        System.out.println("fallen = " + fallen);
    }

    private void b() {
        int maxY = map.keySet().stream().max(Comparator.naturalOrder()).orElseThrow();
        int maxX = map.values().stream().flatMap(e -> e.keySet().stream()).max(Comparator.naturalOrder()).orElseThrow();
        for (int x = 0; x < maxX; x++) {
            put(maxY + 2, x, ROCK);
        }
        int fallen = 0;
        while (get(0, 500) == AIR) {
            int sandX = 500;
            int sandY = 0;
            boolean sandIsFalling = true;
            while (sandIsFalling) {
                if (sandY == maxY + 1) {
                    put(sandY, sandX, SAND);
                    fallen++;
                    break;
                }

                if (get(sandY + 1, sandX) == AIR) {
                    sandY += 1;
                } else if (get(sandY + 1, sandX - 1) == AIR) {
                    sandY += 1;
                    sandX -= 1;
                } else if (get(sandY + 1, sandX + 1) == AIR) {
                    sandY += 1;
                    sandX += 1;
                } else {
                    sandIsFalling = false;
                    put(sandY, sandX, SAND);
                    fallen++;
                }
            }
        }
        System.out.println("fallen = " + fallen);
    }

    private void put(int y, int x, char what) {
        //System.out.println(x + " " + y +  " -> " + what);
        map.computeIfAbsent(y, (k) -> new HashMap<>()).put(x, what);
    }

    private char get(int y, int x) {
        return map.getOrDefault(y, Collections.emptyMap()).getOrDefault(x, AIR);
    }

    private void print(int maxY, int maxX) {
        System.out.println();
        for (int y = 0; y < maxY + 3; y++) {
            for (int x = 480; x < maxX + 50; x++) {
                System.out.print(get(y, x));
            }
            System.out.println();
        }
        System.out.println();
    }
}
