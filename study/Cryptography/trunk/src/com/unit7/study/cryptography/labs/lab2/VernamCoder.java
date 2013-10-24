package com.unit7.study.cryptography.labs.lab2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.unit7.study.cryptography.labs.lab3.Algorithm;
import com.unit7.study.cryptography.labs.lab3.Signer;

/**
 * enc и dec индексы по массиву ключа соответственно для кодирования и
 * декодирования. Единичный вызов getEncoded или getDecoded сдвигает индекс на
 * единицу, чтобы для последующего вызова использовался другой ключ
 * 
 * @author unit7
 * 
 */
public class VernamCoder implements CoderInfo {
    public VernamCoder(File key) {
        try {
            keyEnc = new FileInputStream(key);
            keyDec = new FileInputStream(key);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("Cannot read file with key, Check it out");
        }
    }

    @Override
    public Object getEncoded(int m) {
        try {
            return m ^ keyEnc.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("Cannot read file with key, Check it out");
        }
    }

    @Override
    public int getDecoded(Object coded) {
        if (!(coded instanceof Integer))
            throw new IllegalArgumentException("coded parameter must be int");

        try {
            return (Integer) coded ^ keyDec.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("Cannot read file with key, Check it out");
        }
    }

    private InputStream keyEnc, keyDec;

    @Override
    public String getAlgorithm() {
        return Algorithm.VERNAM.getValue();
    }
}
