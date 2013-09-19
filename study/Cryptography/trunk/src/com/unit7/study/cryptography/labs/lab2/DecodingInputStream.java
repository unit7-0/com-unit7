package com.unit7.study.cryptography.labs.lab2;

import java.io.IOException;
import java.io.InputStream;

public class DecodingInputStream extends InputStream {
    public DecodingInputStream(InputStream in) {
        this.in = in;
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

    public CoderInfo getCoderInfo() {
        return coderInfo;
    }

    public void setCoderInfo(CoderInfo coderInfo) {
        this.coderInfo = coderInfo;
    }

    private InputStream in;
    private CoderInfo coderInfo;
}