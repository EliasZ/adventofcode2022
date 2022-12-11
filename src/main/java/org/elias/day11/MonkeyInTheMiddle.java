package org.elias.day11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MonkeyInTheMiddle {
    private final String file;

    public static void main(String[] args) {
        new MonkeyInTheMiddle("/11/input_test.txt").solve(20); // 10605
        new MonkeyInTheMiddle("/11/input_test.txt").solve(10000); // 2713310158
        new MonkeyInTheMiddle("/11/input.txt").solve(20);
        new MonkeyInTheMiddle("/11/input.txt").solve(10000);
    }

    public MonkeyInTheMiddle(String s) {
        this.file = s;
    }

    void solve(int rounds) {
        long monkeyIndex = 0;
        Map<Long, List<Long>> items = new HashMap<>();
        Map<Long, Function<Long, Long>> ops = new HashMap<>();
        Map<Long, Long> check = new HashMap<>();
        Map<Long, Map<Boolean, Long>> target = new HashMap<>();

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
                    items.put(monkeyIndex, Arrays.stream(split).map(Long::valueOf).collect(Collectors.toList()));
                }
                if (line.charAt(2) == 'O') {
                    String operation = line.substring(19);
                    if (operation.equals("old * old")) {
                        ops.put(monkeyIndex, (old) -> old * old);
                    } else if (operation.charAt(4) == '*') { // old *
                        ops.put(monkeyIndex, (old) -> Long.parseLong(operation.substring(6)) * old);
                    } else if (operation.charAt(4) == '+') { // old +
                        ops.put(monkeyIndex, (old) -> Long.parseLong(operation.substring(6)) + old);
                    }
                }
                if (line.charAt(2) == 'T') {
                    check.put(monkeyIndex, Long.parseLong(line.substring(21)));
                    target.computeIfAbsent(monkeyIndex, (k) -> new HashMap<>())
                            .put(Boolean.TRUE, Long.parseLong(br.readLine().substring(29)));
                    target.computeIfAbsent(monkeyIndex, (k) -> new HashMap<>())
                            .put(Boolean.FALSE, Long.parseLong(br.readLine().substring(30)));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        long mod = check.values().stream().reduce(1L, (a, b) -> a * b); // use product of modulo to limit item size
        Map<Long, Long> inspects = new HashMap<>();

        for (int i = 0; i < rounds; i++) {
            for (long monkey = 0; monkey < monkeyIndex + 1; monkey++) {
                List<Long> value = new ArrayList(items.get(monkey));
                for (Long item : value) {
                    inspects.compute(monkey, (k, v) -> v == null ? 1 : v + 1);
                    Long apply = item % mod;
                    apply = ops.get(monkey).apply(apply);
                    if (rounds == 20) { // challenge A
                        apply = (long) Math.floor(apply / 3.0);
                    }
                    Long targetMonkey = target.get(monkey).get((apply % check.get(monkey)) == 0);
                    items.get(targetMonkey).add(apply);
                }
                items.get(monkey).clear();
            }
        }

        List<Long> integers = inspects.values().stream().sorted(Comparator.reverseOrder()).toList();
        System.out.println(integers.get(0) * integers.get(1));
    }
}
