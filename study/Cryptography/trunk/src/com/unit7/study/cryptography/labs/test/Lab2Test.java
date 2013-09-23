package com.unit7.study.cryptography.labs.test;

import java.io.PrintWriter;

import com.unit7.study.cryptography.labs.lab1.MathUtils;
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
            writer.println("Symbol:\t" + (int) ch);
            
            int coded = first.getCoded(ch);
            writer.println("Step 1: " + coded);
            
            coded = second.getCoded(coded);
            writer.println("Step 2: " + coded);
            
            coded = first.getCoded(coded);
            writer.println("Step 3: " + coded);
            
            coded = second.getCoded(coded);
            writer.println("Step 4: " + coded + "\n");
        }
        
        writer.println("\ntestShamirSending() [finished]");
        writer.flush();
    }
    
    private PrintWriter writer = new PrintWriter(System.out);
}
