package net.kaoriya.mapmap;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

public class WriterTest {
    @Test
    public void testWriter() {
		try (Writer w = new Writer(new File("test1.bin"))) {
		}
    }
}
