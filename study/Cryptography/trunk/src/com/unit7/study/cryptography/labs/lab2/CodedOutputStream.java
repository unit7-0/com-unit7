package com.unit7.study.cryptography.labs.lab2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CodedOutputStream extends OutputStream {
    public CodedOutputStream(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        int readed = -1;
        while ((readed = in.read()) != -1) {
            out.write(readed);
        }
    }
    
    private InputStream in;
    private OutputStream out;
}
