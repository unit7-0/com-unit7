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
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}

		// 128 bit MD5-hash -> 16 bytes -> coded 16 * 4 = 64 first bytes of file
		// is signature
		
		try {
			out = new FileOutputStream(signedFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ? ?? ? connect
		rewriter.setOut(out);
		
		try {
			rewriter.rewrite();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			in = new FileInputStream(signedFile);
			out = new FileOutputStream(designedFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// здесь отрезать от файла 64 байта наших
		// потом прогнать через RSA получить хэш
		// посчитать хэш от сообщения, сравнить == - ок.
	}

	@Test
	public void ElGamalSignature() {

	}

	@Test
	public void gostSignature() {

	}
}
