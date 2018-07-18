package net.kaoriya.mapmap;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

public class WriterTest {
    private static byte[] toUTF8(String s)
            throws java.io.UnsupportedEncodingException
    {
        return s.getBytes("UTF-8");
    }

    @Test
    public void testWriter1() throws Exception {
        try (Writer w = new Writer(new File("tmp/test1.bin"))) {
        }
    }

    @Test
    public void testWriter2() throws Exception {
        try (Writer w = new Writer(new File("tmp/test2.bin"))) {
            w.put(0x10, 0x11, toUTF8("aaaa"));
            w.put(0x10, 0x12, toUTF8("aabb"));
            w.put(0x10, 0x13, toUTF8("aacc"));
            w.put(0x20, 0x21, toUTF8("bbaa"));
            w.put(0x20, 0x22, toUTF8("bbbb"));
            w.put(0x30, 0x31, toUTF8("ccaa"));
        }
    }
}
