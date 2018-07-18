package net.kaoriya.mapmap;

import java.io.IOException;
import java.io.File;
import java.lang.AutoCloseable;
import java.io.RandomAccessFile;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;

public class Writer implements AutoCloseable {

    RandomAccessFile dataFile;

    TreeMap<Long, TreeMap<Long, Long>> offsetMap = new TreeMap<>();

    long valueOffset = 0;

    final long hunkOffset1;
    final long hunkOffset2;
    final long dataOffset;

    /**
     * Creates a new mapmap Writer instance.
     */
    public Writer(File file) throws IOException {
        dataFile = new RandomAccessFile(file, "rw");
        // write header.
        dataFile.write(new byte[]{'M', 'M', '0', '1'});
        dataFile.writeInt(2);  // number of hunks: 2 hunks followed

        // length of 1st hunk: place holder
        hunkOffset1 = dataFile.getFilePointer();
        dataFile.writeLong(0);

        // length of 2nd hunk: place holder
        hunkOffset2 = dataFile.getFilePointer();
        dataFile.writeLong(0);

        dataOffset = dataFile.getFilePointer();
    }

    /**
     * Puts a value with key pair.
     */
    public void put(long key1, long key2, byte[] value) throws IOException {
        Long k1 = Long.valueOf(key1);
        TreeMap<Long, Long> v = offsetMap.get(k1);
        if (v == null) {
            v = new TreeMap<>();
            offsetMap.put(k1, v);
        }
        v.put(Long.valueOf(key2), valueOffset);
        dataFile.writeInt(value.length);
        dataFile.write(value);
        valueOffset += 4 + value.length;
    }

    void writeIndex() throws IOException {
        final long baseOffset = dataFile.getFilePointer();

        dataFile.writeLong(offsetMap.size());

        final long indexOffset1 = dataFile.getFilePointer();
        final long indexOffset2 = indexOffset1 + offsetMap.size() * 24;

        // write first indexes
        long count = 0;
        for (Map.Entry<Long, TreeMap<Long, Long>> e : offsetMap.entrySet()) {
            dataFile.writeLong(e.getKey());
            dataFile.writeLong(e.getValue().size());
            dataFile.writeLong(indexOffset2 + count * 16);
            count += e.getValue().size();
        }

        // write second indexes
        for (Map.Entry<Long, TreeMap<Long, Long>> e1 : offsetMap.entrySet()) {
            for (Map.Entry<Long, Long> e2 : e1.getValue().entrySet()) {
                dataFile.writeLong(e2.getKey());
                dataFile.writeLong(e2.getValue() + dataOffset);
            }
        }

        // close hunks
        dataFile.seek(hunkOffset1);
        dataFile.writeLong(dataOffset);
        dataFile.seek(hunkOffset2);
        dataFile.writeLong(baseOffset);
    }

    /**
     * Close and finish mapmap data file.
     */
    public void close() throws IOException {
        writeIndex();
        dataFile.close();
        offsetMap.clear();
    }
}
