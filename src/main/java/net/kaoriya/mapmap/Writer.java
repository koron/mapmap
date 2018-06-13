package net.kaoriya.mapmap;

import java.io.IOException;
import java.io.File;
import java.io.RandomAccessFile;

public class Writer {

    RandomAccessFile dataFile;
    File mmxFile;
    RandomAccessFile indexFile;

    /**
     * Creates a new mapmap Writer instance.
     */
    public Writer(File file) throws IOException {
        dataFile = new RandomAccessFile(file, "rw");
        // write header.
        dataFile.write(new byte[]{'M', 'M', '0', '1'});
        dataFile.writeInt(2);  // number of hunks: 2 hunks followed
        dataFile.writeLong(0); // length of 1st hunk: place holder
        dataFile.writeLong(0); // length of 2nd hunk: place holder

        mmxFile = new File(file.getPath() + ".mmx");
        indexFile = new RandomAccessFile(mmxFile, "rw");
    }

    /**
     * Puts a value with key pair.
     */
    public void put(long key1, long key2, byte[] value) throws IOException {
        // TODO:
    }

    /**
     * Close and finish mapmap data file.
     */
    public void close() throws IOException {
        indexFile.close();
        // TODO:
        dataFile.close();
        mmxFile.delete();
    }
}
