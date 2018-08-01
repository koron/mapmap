package net.kaoriya.mapmap.benchmark.sparkey;

class Utils {
    static void putLong(long v, byte[] buf, int offset) {
        buf[offset+0] = (byte)(v >>> 56);
        buf[offset+1] = (byte)(v >>> 48);
        buf[offset+2] = (byte)(v >>> 40);
        buf[offset+3] = (byte)(v >>> 32);
        buf[offset+4] = (byte)(v >>> 24);
        buf[offset+5] = (byte)(v >>> 16);
        buf[offset+6] = (byte)(v >>> 8);
        buf[offset+7] = (byte)(v >>> 0);
    }

    static void putKey1(long k1, byte[]buf) {
        putLong(k1, buf, 0);
    }

    static void putKey2(long k2, byte[]buf) {
        putLong(k2, buf, 8);
    }
}
