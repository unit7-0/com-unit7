package com.unit7.study.cryptography.labs.lab2;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Читает по 4 байта(то есть int), так как
 * CondingInputStream вормирует 32х битные коды
 * и записывает именно так
 * @author Zajcev
 *
 */
public class DecodingInputStream extends InputStream {
    public DecodingInputStream(InputStream in, CoderInfo coderInfo) {
        this.in = in;
        this.coderInfo = coderInfo;
    }
    
    @Override
    public int read() throws IOException {
        int readed = readInt();
        if (readed == -1)
            return -1;
        
        int ret = coderInfo.getDecoded(readed);
        if (ret == -1)
            ret = coderInfo.getDecoded(new Integer[] { readed, readInt() });
        
        return ret;
    }

    private int readInt() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        byte[] data = new byte[4];
        int count = in.read(data);
        if (count == -1)
            return -1;
        
        buffer.put(data);
        buffer.position(0);
        
        return buffer.getInt();
    }
    
    private InputStream in;
    private CoderInfo coderInfo;
}