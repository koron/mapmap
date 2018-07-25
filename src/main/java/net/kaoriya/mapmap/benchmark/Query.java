package net.kaoriya.mapmap.benchmark;

import java.io.File;
import net.kaoriya.mapmap.Reader;
import java.util.Random;

public class Query {
    static double rate1 = 1.0;
    static double rate2 = 0.2;
    static int nKey2 = 10;

    public static void main(String[] args) throws Exception {
        File f = new File("tmp/bench01.bin");

        long queryTime = 0;
        long queryHit = 0;
        long queryCount = 0;
        long resultHit = 0;
        long resultCount = 0;

        try (Reader r = new Reader(f)) {
            Random rnd = new Random();
            long[] keys2 = new long[nKey2];
            for (int i = 0; i < 2000000; ++i) {
                // prepare keys.
                long k1 = rnd.nextInt((int)(10000 / rate1));
                for (int j = 0; j < keys2.length; ++j) {
                    keys2[j] = rnd.nextInt((int)(100 / rate2));
                }
                // measure query time.
                long st = System.nanoTime();
                byte[][] vals = r.get(k1, keys2);
                queryTime += System.nanoTime() - st;
                queryCount++;
                if (vals == null) {
                    continue;
                }
                queryHit++;
                // count hit rate.
                for (byte[] b : vals) {
                    ++resultCount;
                    if (b != null) {
                        ++resultHit;
                    }
                }
            }
        }
        System.out.printf("Avg. time: %d nanosec\n", queryTime / queryCount);
        System.out.printf("Hit rate (query): %f (%d/%d)\n", queryHit / (double)(queryCount), queryHit, queryCount);
        System.out.printf("Hit rate (result): %f (%d/%d)\n", resultHit / (double)(resultCount), resultHit, resultCount);
    }
}
