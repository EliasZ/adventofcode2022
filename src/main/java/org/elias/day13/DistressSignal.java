package org.elias.day13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.lang.Character.isDigit;

public class DistressSignal {
    private final String file;

    public static void main(String[] args) {
        new DistressSignal("/13/input_test.txt").solveA();
        new DistressSignal("/13/input.txt").solveA();

        /*new DistressSignal("/13/input_test.txt").solveB();
        new DistressSignal("/13/input.txt").solveB();*/
    }

    public DistressSignal(String s) {
        this.file = s;
    }

    void solveA() {
        int indexSum = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;
            int index = 0;

            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                index++;
                List<Object> left = parse(line);
                List<Object> right = parse(br.readLine());
                if (compareLists(left, right) <= 0) {
                    indexSum += index;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("indexSum = " + indexSum);
    }

    private int compareLists(List<Object> a, List<Object> b) {
        int min = Math.min(a.size(), b.size());
        for (int x = 0; x < min; x++) {
            Object aObj = a.get(x);
            Object bObj = b.get(x);
            Integer compareElement = compareElement(aObj, bObj);
            if (compareElement < 0) {
                return -1;
            } else if (compareElement > 0) {
                return 1;
            }
        }
        return (int) Math.signum(a.size() - b.size());
    }

    private Integer compareElement(Object aObj, Object bObj) {
        if (aObj instanceof List aList) {
            if (bObj instanceof List bList) {
                return compareLists(aList, bList);
            }
            return compareLists(aList, List.of(bObj));
        }
        if (aObj instanceof Integer aInteger) {
            if (bObj instanceof List bList) {
                return compareLists(List.of(aInteger), bList);
            }
            Integer bInteger = (Integer) bObj;
            return (int) Math.signum(aInteger - bInteger);
        }
        throw new IllegalStateException("???");
    }

    private List<Object> parse(String line) {
        char[] chars = line.toCharArray();
        Queue<Character> q = new LinkedList<>();
        for (char aChar : chars) {
            q.add(aChar);
        }
        return parseList(q);
    }

    private List<Object> parseList(Queue<Character> q) {
        List<Object> result = new ArrayList<>();
        q.poll(); // first [
        while (q.peek() != ']') { // not at end of list
            if (q.peek() == ',') {
                q.poll();
            }
            result.add(parseItem(q));
        }
        q.poll(); // last ]
        return result;
    }

    private Object parseItem(Queue<Character> q) {
        char peek = q.peek();
        if (isDigit(peek)) {
            StringBuilder s = new StringBuilder();
            while (isDigit(q.peek())) {
                s.append(q.poll());
            }
            return Integer.parseInt(s.toString());
        } else if (peek == '[') { // new list
            return parseList(q);
        } else {
            throw new IllegalStateException("???");
        }
    }
}
