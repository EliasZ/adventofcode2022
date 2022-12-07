package org.elias.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class ReadFile {
    public static void read(String file, Consumer<String> consumer) {
        new ReadFile(file).readFile(consumer);
    }

    private final String file;

    public ReadFile(String file) {
        this.file = file;
    }

    public void readFile(Consumer<String> consume) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                consume.accept(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
