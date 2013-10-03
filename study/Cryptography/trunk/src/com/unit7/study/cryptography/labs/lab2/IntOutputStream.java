package com.unit7.study.cryptography.labs.lab2;

import java.io.IOException;
import java.io.OutputStream;

public class IntOutputStream extends OutputStream {
	public IntOutputStream(OutputStream out) {
		this.out = out;
	}
	
	@Override
	public void write(int b) throws IOException {
		int writed = (1 << 8) - 1;
		out.write(b & writed);
		writed = (1 << 16) - 1 - writed;
		out.write(b & writed);
		writed = (1 << 24) - 1 - writed;
		out.write(b & writed);
		writed = (int) ((1L << 32) - 1 - writed);
		out.write(b & writed);
	}

	private OutputStream out;
}
