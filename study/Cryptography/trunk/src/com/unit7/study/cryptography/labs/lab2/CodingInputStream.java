package com.unit7.study.cryptography.labs.lab2;

import java.io.IOException;
import java.io.InputStream;

public class CodingInputStream extends InputStream {
    public CodingInputStream(InputStream in) {
        this.in = in;
    }
    
    @Override
    public int read() throws IOException {
        int ret = -1;
        if (coded == null) {
            int i = in.read();
            if (i == -1)
                return i;
            
            coded = coderInfo.getCoded(i);
        } else {
            ret = (Integer) ((Object[]) coded)[1];
            coded = null;
        }
        
        if (coded instanceof Object[]) {
            return (Integer) ((Object[]) coded)[0];
        } else {
            ret = (Integer) coded;
        }
        
        coded = null;
        return ret;
    }

    public CoderInfo getCoderInfo() {
        return coderInfo;
    }

    public void setCoderInfo(CoderInfo coderInfo) {
        this.coderInfo = coderInfo;
    }

    private Object coded;
    private InputStream in;
    private CoderInfo coderInfo;
}
