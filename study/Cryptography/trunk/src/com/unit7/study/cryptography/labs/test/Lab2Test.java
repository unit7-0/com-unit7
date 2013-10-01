package com.unit7.study.cryptography.labs.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import com.unit7.study.cryptography.labs.lab1.MathUtils;
import com.unit7.study.cryptography.labs.lab1.tools.CryptoData;
import com.unit7.study.cryptography.labs.lab1.tools.Pair;
import com.unit7.study.cryptography.labs.lab1.tools.Utils;
import com.unit7.study.cryptography.labs.lab2.CodedOutputStream;
import com.unit7.study.cryptography.labs.lab2.CoderInfo;
import com.unit7.study.cryptography.labs.lab2.CodingInputStream;
import com.unit7.study.cryptography.labs.lab2.DecodingInputStream;
import com.unit7.study.cryptography.labs.lab2.ElGamalCoder;
import com.unit7.study.cryptography.labs.lab2.ShamirCoder;

import junit.framework.TestCase;

public class Lab2Test extends TestCase {
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
        String fileOut = "codedFile.txt";
        String decodedFile = "decoded.txt";
        
        FileInputStream fin = null;
        FileOutputStream fout = null;
        try {
            fin = new FileInputStream(fileIn);
            fout = new FileOutputStream(fileOut);
        } catch (FileNotFoundException e) {
            fail("can't create filestream");
        }
        
        CodingInputStream codedIn = new CodingInputStream(fin, coderInfoA);
        CodedOutputStream rewriter = new CodedOutputStream(codedIn, fout);
        
        try {
            rewriter.write();
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
            rewriter.write();
        } catch (IOException e) {
            fail("can't write decoded file");
        }
        
        writer.println("\ntestElGamal() [finished]\n");
        writer.flush();
    }
    
    private PrintWriter writer = new PrintWriter(System.out);
}
