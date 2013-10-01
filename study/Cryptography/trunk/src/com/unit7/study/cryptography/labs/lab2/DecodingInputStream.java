package com.unit7.study.cryptography.labs.lab2;

import java.io.IOException;
import java.io.InputStream;

public class DecodingInputStream extends InputStream {
    public DecodingInputStream(InputStream in, CoderInfo coderInfo) {
        this.in = in;
        this.coderInfo = coderInfo;
    }
    
    @Override
    public int read() throws IOException {
        int readed = in.read();
        if (readed == -1)
            return -1;
        
        int ret = coderInfo.getDecoded(readed);
        if (ret == -1)
            ret = coderInfo.getDecoded(new Integer[] { readed, in.read() });
        
        return ret;
    }

    private InputStream in;
    private CoderInfo coderInfo;
}