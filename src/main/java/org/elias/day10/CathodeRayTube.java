package org.elias.day10;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CathodeRayTube {
    private static final String NOOP = "noop";
    private final String file;

    public static void main(String[] args) {
        new CathodeRayTube("/10/input_test.txt").solve();
        new CathodeRayTube("/10/input.txt").solve();
    }

    public CathodeRayTube(String s) {
        this.file = s;
    }

    void solve() {
        int x = 1; // sprite @ 0 1 2
        int cycle = 0;
        int signalStrength = 0;
        int cycleCheck = 20;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;

            while ((line = br.readLine()) != null) {
                int cycleInc = 1;
                int xInc = 0;

                String[] s = line.split(" ");
                if (! s[0].equals(NOOP)) { //addx
                    cycleInc = 2;
                    xInc = Integer.parseInt(s[1]);
                }

                for (int i = 0; i < cycleInc; i++) {
                    int c = (cycle - (40 * (int) Math.floor(cycle / 40))); // adjust for new row
                    if (c == x || c == x - 1 || c == x + 1) {
                        System.out.print("#");
                    } else {
                        System.out.print(".");
                    }
                    cycle++;
                    if (cycle == cycleCheck) {
                        signalStrength += (cycle * x);
                        cycleCheck += 40;
                    }
                    if (cycle % 40 == 0) {
                        System.out.println();
                    }
                }

                x += xInc;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("signalStrength = " + signalStrength);
    }
}
