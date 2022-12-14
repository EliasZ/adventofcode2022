package org.elias.day12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class HillClimbingAlgo {
    private final String file;

    public static void main(String[] args) {
        new HillClimbingAlgo("/12/input_test.txt").solve();
        new HillClimbingAlgo("/12/input.txt").solve();
    }

    public HillClimbingAlgo(String s) {
        this.file = s;
    }

    void solve() {
        Map<Integer, List<Position>> map = new HashMap<>(); // y => x
        Position startPos = null; // y,x
        Position endPos = null; // y,x

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;
            int y = 0;

            while ((line = br.readLine()) != null) {
                List<Position> row = new ArrayList<>();
                char[] chars = line.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    if (chars[i] == 'S') {
                        startPos = new Position(y, i, 0, 'a'); // start at a height
                        row.add(startPos);
                    } else if (chars[i] == 'E') {
                        endPos = new Position(y, i, Integer.MAX_VALUE, 'z');  // end on z
                        row.add(endPos);
                    } else {
                        row.add(new Position(y, i, Integer.MAX_VALUE, chars[i]));
                    }
                }
                map.put(y, row);
                y++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // part a
        System.out.println("part a: " + getShortest(map, startPos, endPos));

        // part b
        List<Position> aOpts = map.values().stream().flatMap(Collection::stream)
                .filter(p -> p.what == 'a')
                .toList();

        int shortest = Integer.MAX_VALUE;

        for (Position aOpt : aOpts) {
            int optShortest = getShortest(map, aOpt, endPos);
            if (optShortest < shortest) {
                shortest = optShortest;
            }
        }

        System.out.println("part b: " + shortest);
    }

    private int getShortest(Map<Integer, List<Position>> map, Position startPos, Position endPos) {
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                Position position = map.get(y).get(x);
                int score = Integer.MAX_VALUE;
                if (y == startPos.y && x == startPos.x) {
                    score = 0;
                }
                map.get(y).set(x, new Position(position.y, position.x, score, position.what));
            }
        }

        Queue<Position> thingsToDo = new LinkedList<>();
        thingsToDo.add(map.get(startPos.y).get(startPos.x)); // after reset... shouldn't have used immutable records :-)

        int shortest = Integer.MAX_VALUE;
        while (thingsToDo.size() > 0) {
            Position remove = thingsToDo.remove();
            if (remove.y == endPos.y && remove.x == endPos.x) {
                if (remove.score < shortest) {
                    shortest = remove.score;
                }
                break;
            }
            List<Position> arounds = lookAround(map, remove);
            for (Position around : arounds) {
                if (remove.score + 1 < around.score) {
                    thingsToDo.remove(around);
                    Position newAround = new Position(around.y, around.x, remove.score + 1, around.what);
                    map.get(around.y).set(around.x, newAround);
                    thingsToDo.add(newAround);
                }
            }
        }
        return shortest;
    }

    private final List<int[]> DIRECTIONS = List.of(  // [y,x]
            new int[] {-1, 0}, // up
            new int[] {1, 0}, // down
            new int[] {0, -1}, // left
            new int[] {0, 1} // right
    );

    private List<Position> lookAround(Map<Integer, List<Position>> map, Position myPos) {
        List<Position> options = new ArrayList<>();
        int lookY;
        int lookX;
        int boundY = map.size() - 1;
        int boundX = map.get(0).size() - 1;
        for (int[] direction : DIRECTIONS) {
            lookY = myPos.y + direction[0];
            lookX = myPos.x + direction[1];
            if (lookY < 0 || lookX < 0 || lookY > boundY || lookX > boundX) { // out of map
                continue;
            }
            Position aroundMe = map.get(lookY).get(lookX);
            if (myPos.what - aroundMe.what < -1) { // m - n = -1, m - o = - 2, can't ascend so not an option
                continue;
            }
            options.add(aroundMe);
        }
        return options;
    }

    public record Position(int y, int x, int score, char what) {};
}
