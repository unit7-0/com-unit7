package com.unit7.study.cryptography.labs.lab2;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class IntOutputStream extends OutputStream {
	public IntOutputStream(OutputStream out) {
		this.out = out;
	}
	
	@Override
	public void write(int b) throws IOException {
	    writeInt(b);
	}

	private void writeInt(int d) throws IOException {
	    ByteBuffer buffer = ByteBuffer.allocate(4).putInt(d);
	    out.write(buffer.array());
	}
	
	private OutputStream out;
}
