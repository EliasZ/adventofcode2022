package org.elias.day7;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class NoSpaceLeftOnDevice {
    private final String file;

    public static void main(String[] args) {
        new NoSpaceLeftOnDevice("/7/input_test.txt").solve(); // 95437
        new NoSpaceLeftOnDevice("/7/input.txt").solve();
    }

    public NoSpaceLeftOnDevice(String s) {
        this.file = s;
    }

    // part one
    void solve() {
        Map<String, Integer> dirSizes = new HashMap<>(); // cumulative

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;
            Stack<String> path = new Stack<>();

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("$")) {
                    if (!line.startsWith("dir")) {
                        String[] s = line.split(" ");
                        String pathForSize = "";
                        for (String pathPart : path) {
                            if (!pathForSize.isEmpty()) {
                                pathForSize = pathForSize + ",";
                            }
                            pathForSize = pathForSize + pathPart;
                            dirSizes.compute(pathForSize, (k, v) -> v == null ? Integer.parseInt(s[0]) : Integer.parseInt(s[0]) + v);
                        }
                    }
                } else if (line.startsWith("$ cd")) {
                    String dir = line.substring(5);
                    if (dir.equals("..")) {
                        path.pop();
                    } else {
                        path.push(dir);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("getSumOfTotalSizeAtMost100k: " + dirSizes.values().stream().filter(i -> i < 100000).reduce(0, Integer::sum));

        int max = 70000000;
        int unused = (max - dirSizes.get("/"));
        int toBeDeleted = 30000000 - unused;
        System.out.println(unused + " unused");
        System.out.println(toBeDeleted + " to be deleted");
        System.out.println(dirSizes.values().stream().filter(i -> i >= toBeDeleted).sorted().findFirst().get());
    }
}
