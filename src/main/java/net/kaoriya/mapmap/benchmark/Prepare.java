package net.kaoriya.mapmap.benchmark;

import java.io.File;
import net.kaoriya.mapmap.Writer;

public class Prepare {
    public static void main(String[] args) throws Exception {
        File f = new File("tmp/bench.bin");
        byte[] emptyData = new byte[0];
        try (Writer w = new Writer(f)) {
            for (int i = 0; i < 10000; ++i) {
                long k1 = i;
                for (int j = 0; j < 100; ++j) {
                    long k2 = j;
                    w.put(k1, k2, emptyData);
                }
            }
        }
    }
}
