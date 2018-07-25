package net.kaoriya.mapmap.benchmark;

import java.util.HashSet;
import java.util.Random;

class Generator {
    Random r = new Random();
    HashSet<Long> m = new HashSet<Long>();
    public long next() {
        while (true) {
            long v = r.nextLong();
            if (!m.contains(v)) {
                m.add(v);
                return v;
            }
        }
    }
}
