package net.kaoriya.mapmap.benchmark.sparkey;

import java.io.File;
import java.util.Random;

import com.spotify.sparkey.Sparkey;
import com.spotify.sparkey.SparkeyReader;

public class Query {
    static double rate1 = 0.2;
    static double rate2 = 0.2;
    static int nKey2 = 10;

    public static void main(String[] args) throws Exception {
        File f = new File("tmp/bench-sparkey");

        long queryTime = 0;
        long queryHit = 0;
        long queryCount = 0;
        long resultHit = 0;
        long resultCount = 0;

        try (SparkeyReader r = Sparkey.open(f)) {
            Random rnd = new Random();
            byte[] keyData = new byte[16];
            long[] keys2 = new long[nKey2];
            for (int i = 0; i < 2000000; ++i) {
                // prepare keys.
                long k1 = rnd.nextInt((int)(10000 / rate1));
                Utils.putKey1(k1, keyData);
                for (int j = 0; j < keys2.length; ++j) {
                    keys2[j] = rnd.nextInt((int)(100 / rate2));
                }

                // measure query time.
                long st = System.nanoTime();
                long hitCount = 0;
                for (int j = 0; j < keys2.length; ++j) {
                    Utils.putKey2(keys2[j], keyData);
                    byte[] v = r.getAsByteArray(keyData);
                    if (v != null) {
                        ++hitCount;
                    }
                }
                queryTime += System.nanoTime() - st;
                queryCount++;
                if (hitCount > 0) {
                    queryHit++;
                    resultCount += keys2.length;
                    resultHit += hitCount;
                }
            }
        }
        System.out.printf("Avg. time: %d nanosec\n", queryTime / queryCount);
        System.out.printf("Hit rate (query): %f (%d/%d)\n", queryHit / (double)(queryCount), queryHit, queryCount);
        System.out.printf("Hit rate (result): %f (%d/%d)\n", resultHit / (double)(resultCount), resultHit, resultCount);
    }
}
