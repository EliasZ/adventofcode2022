package org.elias.day11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MonkeyInTheMiddle {
    private final String file;

    public static void main(String[] args) {
        new MonkeyInTheMiddle("/11/input_test.txt").solve();
        new MonkeyInTheMiddle("/11/input.txt").solve();
        //new MonkeyInTheMiddle("/10/input.txt").solve();
    }

    public MonkeyInTheMiddle(String s) {
        this.file = s;
    }

    private final static String OP_MUL = "old *";
    private final static String OP_PLUS = "old +";
    private final static String OP_MULMUL = "old * old";

    Map<String, BiFunction<Integer, Integer, Integer>> operations = Map.of(
            OP_MUL, (old, input) -> old * input,
            OP_PLUS, (old, input) -> old + input,
            OP_MULMUL, (oldA, oldB) -> oldA * oldB
    );

    void solve() {
        int monkeyIndex = 0;
        Map<Integer, List<Integer>> items = new HashMap<>();
        Map<Integer, Function<Integer, Integer>> ops = new HashMap<>();
        Map<Integer, Integer> check = new HashMap<>();
        Map<Integer, Map<Boolean, Integer>> target = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    monkeyIndex++;
                    continue;
                }
                if (line.charAt(0) == 'M') continue; // newline index increase
                if (line.charAt(2) == 'S') {
                    String[] split = line.substring(18).split(", ");
                    items.put(monkeyIndex, Arrays.stream(split).map(Integer::valueOf).collect(Collectors.toList()));
                }
                if (line.charAt(2) == 'O') {
                    String operation = line.substring(19);
                    if (operation.equals("old * old")) {
                        ops.put(monkeyIndex, (old) -> old * old);
                    } else if (operation.charAt(4) == '*') { // old *
                        ops.put(monkeyIndex, (old) -> Integer.parseInt(operation.substring(6)) * old);
                    } else if (operation.charAt(4) == '+') { // old +
                        ops.put(monkeyIndex, (old) -> Integer.parseInt(operation.substring(6)) + old);
                    }
                }
                if (line.charAt(2) == 'T') {
                    check.put(monkeyIndex, Integer.parseInt(line.substring(21)));
                    target.computeIfAbsent(monkeyIndex, (k) -> new HashMap<>())
                            .put(Boolean.TRUE, Integer.parseInt(br.readLine().substring(29)));
                    target.computeIfAbsent(monkeyIndex, (k) -> new HashMap<>())
                            .put(Boolean.FALSE, Integer.parseInt(br.readLine().substring(30)));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int rounds = 20;
        Map<Integer, Integer> inspects = new HashMap<>();
        for (int i = 0; i < rounds; i++) {
            for (int monkey = 0; monkey < monkeyIndex + 1; monkey++) {
                List<Integer> value = new ArrayList(items.get(monkey));
                for (Integer item : value) {
                    inspects.compute(monkey, (k, v) -> v == null ? 1 : v + 1);
                    Integer apply = ops.get(monkey).apply(item);
                    apply = (int) Math.floor(apply / 3.0);
                    Integer targetMonkey = target.get(monkey).get((apply % check.get(monkey)) == 0);
                    items.get(targetMonkey).add(apply);
                }
                items.get(monkey).clear();
            }
        }

        List<Integer> integers = inspects.values().stream().sorted(Comparator.reverseOrder()).toList();
        System.out.println(integers.get(0) * integers.get(1));
    }
}
