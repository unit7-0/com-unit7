package com.unit7.study.cryptography.labs.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.unit7.study.cryptography.labs.lab1.MathUtils;
import com.unit7.study.cryptography.labs.lab1.tools.CryptoData;
import com.unit7.study.cryptography.labs.lab1.tools.Pair;
import com.unit7.study.cryptography.labs.lab1.tools.Utils;
import com.unit7.study.cryptography.labs.lab2.CoderInfo;
import com.unit7.study.cryptography.labs.lab2.CodingInputStream;
import com.unit7.study.cryptography.labs.lab2.DecodingInputStream;
import com.unit7.study.cryptography.labs.lab2.ElGamalCoder;
import com.unit7.study.cryptography.labs.lab2.IntOutputStream;
import com.unit7.study.cryptography.labs.lab2.RSACoder;
import com.unit7.study.cryptography.labs.lab2.Rewriter;
import com.unit7.study.cryptography.labs.lab2.ShamirCoder;
import com.unit7.study.cryptography.labs.lab2.VernamCoder;

import junit.framework.TestCase;

public class Lab2Tests extends TestCase {
    public void testShamirSending() {
        int p = MathUtils.getRandPrime(3377651);
        ShamirCoder first = new ShamirCoder(p);
        ShamirCoder second = new ShamirCoder(p);
        
        String message = "Message";
        writer.println("testShamirSending() [started]\n\nSending message: " + message + "\n");
        
        for (int i = 0; i < message.length(); ++i) {
            char ch = message.charAt(i);
            writer.println("Symbol:\t" + ch);
            
            int coded = first.getCoded(ch);
            writer.println("Step 1: " + coded);
            
            coded = second.getCoded(coded);
            writer.println("Step 2: " + coded);
            
            coded = first.getCoded(coded);
            writer.println("Step 3: " + coded);
            
            coded = second.getCoded(coded);
            writer.println("Step 4: " + (char) coded + "\n");
        }
        
        writer.println("\ntestShamirSending() [finished]");
        writer.flush();
    }
    
    public void testElGamal() {
        writer.println("testElGamal() [started]");
        
        Pair<Integer, Integer> pg = Utils.generatePG(13335);
        int p = pg.getFirst();
        int g = pg.getSecond();
        
        CryptoData cryptoData = new CryptoData(p, g);
        int privA = cryptoData.getPrivate();
        int pubA = cryptoData.getPublic();
        
        cryptoData.generateKeys();
        int privB = cryptoData.getPrivate();
        int pubB = cryptoData.getPublic();
        
        CoderInfo coderInfoA = new ElGamalCoder(p, g, privA, pubB);
        CoderInfo coderInfoB = new ElGamalCoder(p, g, privB, pubA);
        
        // encoding
        String fileIn = "file.txt";
        String fileOut = "codedElGamal.txt";
        String decodedFile = "decodedElGamal.txt";
        
        FileInputStream fin = null;
        OutputStream fout = null;
        try {
            fin = new FileInputStream(fileIn);
            fout = new IntOutputStream(new FileOutputStream(fileOut));
        } catch (FileNotFoundException e) {
            fail("can't create filestream");
        }
        
        CodingInputStream codedIn = new CodingInputStream(fin, coderInfoA);
        Rewriter rewriter = new Rewriter(codedIn, fout);
        
        try {
            rewriter.rewrite();
        } catch (IOException e) {
            fail("can't write file");
        }
        
        // decoding
        try {
            fin = new FileInputStream(fileOut);
            fout = new FileOutputStream(decodedFile);
        } catch (FileNotFoundException e) {
            fail("can't open coded filestream");
        }
        
        DecodingInputStream decodedIn = new DecodingInputStream(fin, coderInfoB);
        rewriter.setIn(decodedIn);
        rewriter.setOut(fout);
        
        try {
            rewriter.rewrite();
        } catch (IOException e) {
            fail("can't write decoded file");
        }
        
        writer.println("\ntestElGamal() [finished]\n");
        writer.flush();
    }
    
    public void testRSA() {
        writer.println("\ntestRSA() [started]\n");
        RSACoder coderA = new RSACoder();
        RSACoder coderB = new RSACoder();
        
        coderA.setDb(coderB.getD());
        coderA.setNb(coderB.getN());
        coderB.setDb(coderA.getD());
        coderB.setNb(coderA.getN());
        
        String fileIn = "file.txt";
        String codedFile = "codedRSA.txt";
        String decodedFile = "decodedRSA.txt";
        
        InputStream in = null;
        OutputStream out = null;
        
        try {
            in = new FileInputStream(fileIn);
            out = new IntOutputStream(new FileOutputStream(codedFile));
        } catch (FileNotFoundException e) {
            fail("can't create files");
        }
        
        // coding
        CodingInputStream coding = new CodingInputStream(in, coderA);
        Rewriter rewriter = new Rewriter(coding, out);
        try {
            rewriter.rewrite();
        } catch (IOException e) {
            fail("can't rewrite");
        }
        
        try {
            in = new FileInputStream(codedFile);
            out = new FileOutputStream(decodedFile);
        } catch (FileNotFoundException e) {
            fail("can't create files");
        }
        
        // decoding
        DecodingInputStream decoding = new DecodingInputStream(in, coderB);
        rewriter.setIn(decoding);
        rewriter.setOut(out);
        try {
            rewriter.rewrite();
        } catch (IOException e) {
            fail("can't rewrite, decode");
        }
        
        writer.println("\ntestRSA [finished]\n");
        writer.flush();
    }
    
    public void testVernam() {
        writer.println("\ntestVernam() [started]\n");
        
        int k = MathUtils.getRandInt(1513035135);
        VernamCoder coderA = new VernamCoder(k);
        VernamCoder coderB = new VernamCoder(k);
        
        String fileIn = "file.txt";
        String codedFile = "codedVernam.txt";
        String decodedFile = "decodedVernam.txt";
        
        InputStream in = null;
        OutputStream out = null;
        
        try {
            in = new FileInputStream(fileIn);
            out = new IntOutputStream(new FileOutputStream(codedFile));
        } catch (FileNotFoundException e) {
            fail("can't create files");
        }
        
        // coding
        CodingInputStream coding = new CodingInputStream(in, coderA);
        Rewriter rewriter = new Rewriter(coding, out);
        try {
            rewriter.rewrite();
        } catch (IOException e) {
            fail("can't rewrite");
        }
        
        try {
            in = new FileInputStream(codedFile);
            out = new FileOutputStream(decodedFile);
        } catch (FileNotFoundException e) {
            fail("can't create files");
        }
        
        // decoding
        DecodingInputStream decoding = new DecodingInputStream(in, coderB);
        rewriter.setIn(decoding);
        rewriter.setOut(out);
        try {
            rewriter.rewrite();
        } catch (IOException e) {
            fail("can't rewrite, decode");
        }
        
        writer.println("\ntestVernam() [finished]\n");
        writer.flush();
    }
    
    private PrintWriter writer = new PrintWriter(System.out);
}
