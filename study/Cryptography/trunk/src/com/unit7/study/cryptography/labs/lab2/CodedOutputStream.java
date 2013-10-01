package com.unit7.study.cryptography.labs.lab2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CodedOutputStream {
    public CodedOutputStream(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void write() throws IOException {
        int readed = -1;
        while ((readed = in.read()) != -1) {
            out.write(readed);
        }
        
        in.close();
        out.close();
    }
    
    public InputStream getIn() {
        return in;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }

    public OutputStream getOut() {
        return out;
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }

    private InputStream in;
    private OutputStream out;
}
