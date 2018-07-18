package net.kaoriya.mapmap;

import java.io.File;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReaderTest {

    private static String fromUTF8(byte[] b) throws IOException {
        return new String(b, "UTF-8");
    }

    @Test
    public void testReader() {
        assertTrue("dummy", true);
    }

    @Test
    public void testReader2() throws Exception {
        try (Reader r = new Reader(new File("src/test/resource/read2.bin"))) {
            assertEquals("aaaa", fromUTF8(r.get(0x10, 0x11)));
            assertEquals("aabb", fromUTF8(r.get(0x10, 0x12)));
            assertEquals("aacc", fromUTF8(r.get(0x10, 0x13)));
            assertEquals("bbaa", fromUTF8(r.get(0x20, 0x21)));
            assertEquals("bbbb", fromUTF8(r.get(0x20, 0x22)));
            assertEquals("ccaa", fromUTF8(r.get(0x30, 0x31)));
        }
    }

    @Test
    public void testReader2b() throws Exception {
        try (Reader r = new Reader(new File("src/test/resource/read2.bin"))) {
            assertEquals("ccaa", fromUTF8(r.get(0x30, 0x31)));
            assertEquals("bbbb", fromUTF8(r.get(0x20, 0x22)));
            assertEquals("aabb", fromUTF8(r.get(0x10, 0x12)));
            assertEquals("aaaa", fromUTF8(r.get(0x10, 0x11)));
            assertEquals("bbaa", fromUTF8(r.get(0x20, 0x21)));
            assertEquals("aacc", fromUTF8(r.get(0x10, 0x13)));
        }
    }
}
