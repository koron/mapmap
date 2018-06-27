package net.kaoriya.mapmap;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Reader {

    static final byte[] header1 = new byte[]{'M', 'M', '0', '1'};

    final RandomAccessFile dataFile;
    final MappedByteBuffer buffer;

    final long dataOffset;
    final long indexOffset;

    final long size1;
    final long indexOffset1;
    final long indexOffset2;

    public Reader(File file) throws IOException {
        dataFile = new RandomAccessFile(file, "r");
        byte[] head = new byte[4];
        dataFile.readFully(head);
        if (!Arrays.equals(head, header1)) {
            throw new IOException("unknown header");
        }
        int hunks = dataFile.readInt();
        if (hunks != 2) {
            throw new IOException("format violation: two hunks required");
        }
        dataOffset = dataFile.readLong();
        indexOffset = dataFile.readLong();

        FileChannel ch = dataFile.getChannel();
        buffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, ch.size());
        size1 = buffer.getLong((int)indexOffset);

        indexOffset1 = indexOffset + 8;
        indexOffset2 = indexOffset1 + size1 * 24;
    }

    static class Item {
        long key;
        long num;
        long off;

        Item(long key, long num, long off) {
            this.key = key;
            this.num = num;
            this.off = off;
        }
    }

    Item findKey1(long key, long start, long end) throws IOException {
        if (start > end) {
            return null;
        }
        long mid = (start + end) / 2;
        long curr = indexOffset1 + mid * 24;
        long key1 = buffer.getLong((int)curr);
        if (key < key1) {
            return findKey1(key, start, mid - 1);
        } else if (key > key1) {
            return findKey1(key, mid + 1, end);
        }
        long num = buffer.getLong((int)(curr + 8));
        long off = buffer.getLong((int)(curr + 16));
        return new Item(key, num, off);
    }

    Item findKey2(long key, long base, long start, long end) throws IOException {
        if (start > end) {
            return null;
        }
        long mid = (start + end) / 2;
        long curr = base + mid * 16;
        long key2 = buffer.getLong((int)curr);
        if (key < key2) {
            return findKey2(key, base, start, mid - 1);
        } else if (key > key2) {
            return findKey2(key, base, mid + 1, end);
        }
        long off = buffer.getLong((int)(curr + 8));
        long num = buffer.getLong((int)off);
        return new Item(key, num, off + 8);
    }

    public byte[] get(long key1, long key2) throws IOException {
        Item v1 = findKey1(key1, 0, size1-1);
        if (v1 == null) {
            return null;
        }
        Item v2 = findKey2(key2, v1.off, 0, v1.num - 1);
        if (v2 == null) {
            return null;
        }
        byte[] b = new byte[(int)v2.num];
        if (v2.num > 0) {
            buffer.get(b, (int)v2.off, (int)v2.num);
        }
        return b;
    }

    public void close() throws IOException {
        dataFile.close();
    }
}
