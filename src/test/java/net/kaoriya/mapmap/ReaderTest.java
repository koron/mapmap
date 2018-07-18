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

    @Test
    public void testReader2c() throws Exception {
        try (Reader r = new Reader(new File("src/test/resource/read2.bin"))) {
            byte[][] v = r.get(0x10, new long[]{0x11, 0x13, 0x14, 0x12});
            assertEquals("aaaa", fromUTF8(v[0]));
            assertEquals("aacc", fromUTF8(v[1]));
            assertNull(v[2]);
            assertEquals("aabb", fromUTF8(v[3]));
        }
    }

    @Test
    public void testReader2d() throws Exception {
        try (Reader r = new Reader(new File("src/test/resource/read2.bin"))) {
            Reader.Ptr p1 = r.get(0x10);
            assertNotNull(p1);
            assertEquals("aaaa", fromUTF8(r.get(p1, 0x11)));
            assertEquals("aabb", fromUTF8(r.get(p1, 0x12)));
            assertEquals("aacc", fromUTF8(r.get(p1, 0x13)));
            assertNull(r.get(p1, 0x14));

            Reader.Ptr p2 = r.get(0x20);
            assertNotNull(p2);
            assertEquals("bbaa", fromUTF8(r.get(p2, 0x21)));
            assertEquals("bbbb", fromUTF8(r.get(p2, 0x22)));
            assertNull(r.get(p2, 0x23));

            Reader.Ptr p3 = r.get(0x30);
            assertNotNull(p3);
            assertEquals("ccaa", fromUTF8(r.get(p3, 0x31)));
            assertNull(r.get(p3, 0x32));

            Reader.Ptr p4 = r.get(0x40);
            assertNull(p4);
        }
    }
}
