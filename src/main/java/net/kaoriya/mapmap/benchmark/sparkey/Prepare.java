package net.kaoriya.mapmap.benchmark.sparkey;

import java.io.File;

import com.spotify.sparkey.Sparkey;
import com.spotify.sparkey.SparkeyWriter;

public class Prepare {
    public static void main(String[] args) throws Exception {
        File f = new File("tmp/bench-sparkey");
        byte[] keyData = new byte[16];
        byte[] emptyData = new byte[0];
        try (SparkeyWriter w = Sparkey.createNew(f)) {
            for (int i = 0; i < 10000; ++i) {
                Utils.putKey1(i, keyData);
                for (int j = 0; j < 100; ++j) {
                    Utils.putKey2(j, keyData);
                    w.put(keyData, emptyData);
                }
            }
            w.writeHash();
        }
    }
}
