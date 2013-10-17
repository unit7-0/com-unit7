package com.unit7.study.cryptography.labs.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;

import com.unit7.study.cryptography.labs.lab2.CodingInputStream;
import com.unit7.study.cryptography.labs.lab2.DecodingInputStream;
import com.unit7.study.cryptography.labs.lab2.IntOutputStream;
import com.unit7.study.cryptography.labs.lab2.RSACoder;
import com.unit7.study.cryptography.labs.lab2.Rewriter;

/**
 * Необходимо подписать файл. Записать файл и подпись либо вместе, либо отдельно
 * Затем считать файл и подпись и убедиться в достоверности данных
 */
public class Lab3Tests {
	@Test
	public void rsaSignature() {
		String fileIn = "file.txt";
		String signedFile = "signed";
		String designedFile = "designed.txt";

		RSACoder coder = new RSACoder();
		coder.setDb(coder.getD());
		coder.setNb(coder.getN());

		MessageDigest digester = null;
		InputStream in = null;
		byte[] data = null;

		try {
			digester = MessageDigest.getInstance("MD5");
			in = new FileInputStream(fileIn);
			data = new byte[in.available()];
			in.read(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			Assert.fail();
		}

		// signing
		byte[] digest = digester.digest(data);
		in = new ByteArrayInputStream(digest);

		CodingInputStream codingIn = new CodingInputStream(in, coder);
		OutputStream out = new ByteArrayOutputStream();

		OutputStream output = new IntOutputStream(out);
		Rewriter rewriter = new Rewriter(codingIn, output);

		try {
			rewriter.rewrite();
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}

		// 128 bit MD5-hash -> 16 bytes -> coded 16 * 4 = 64 first bytes of file
		// is signature
		
		try {
			out = new FileOutputStream(signedFile);
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
            Assert.fail();
		}
		
		// first we write to file the signature
		in = new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
		rewriter.setIn(in);
		rewriter.setOut(out);
		
		try {
            rewriter.rewrite();
        } catch (IOException e1) {
            e1.printStackTrace();
            Assert.fail();
        }
		
		// and second we write the our file
		try {
            in = new FileInputStream(fileIn);
            out = new FileOutputStream(signedFile, true);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            Assert.fail();
        }
		
		in = new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
        rewriter.setIn(in);
        rewriter.setOut(out);
        
        try {
            rewriter.rewrite();
        } catch (IOException e1) {
            e1.printStackTrace();
            Assert.fail();
        }
		
		// read sign from file and file contained data
        // then check hash data == decoded sign
        try {
            in = new FileInputStream(signedFile);
            digest = new byte[16 * 4];
            data = new byte[in.available() - digest.length];
            in.read(digest);
            in.read(data);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
        in = new ByteArrayInputStream(digest);
        out = new ByteArrayOutputStream();
        
        // decoding hash
        DecodingInputStream decIn = new DecodingInputStream(in, coder);
        rewriter.setIn(decIn);
        rewriter.setOut(out);
        
        try {
            rewriter.rewrite();
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        digest = ((ByteArrayOutputStream) out).toByteArray();
        byte[] newDigest = digester.digest(data);
        
        // check equals
        Assert.assertArrayEquals(digest, newDigest);
	}

	@Test
	public void ElGamalSignature() {

	}

	@Test
	public void gostSignature() {

	}
}
