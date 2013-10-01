package com.unit7.study.cryptography.labs.lab2;

import java.io.IOException;
import java.io.InputStream;

public class CodingInputStream extends InputStream {
    public CodingInputStream(InputStream in, CoderInfo coderInfo) {
        this.in = in;
        this.coderInfo = coderInfo;
    }
    
    @Override
    public int read() throws IOException {
        int ret = -1;
        if (coded == null) {
            int i = in.read();
            if (i == -1)
                return i;
            
            coded = coderInfo.getEncoded(i);
        } else {
            ret = (Integer) ((Object[]) coded)[1];
            coded = null;
        }
        
        if (coded instanceof Object[]) {
            return (Integer) ((Object[]) coded)[0];
        } else if (coded != null) {
            ret = (Integer) coded;
        }
        
        coded = null;
        return ret;
    }
    
    private Object coded;
    private InputStream in;
    private CoderInfo coderInfo;
}
