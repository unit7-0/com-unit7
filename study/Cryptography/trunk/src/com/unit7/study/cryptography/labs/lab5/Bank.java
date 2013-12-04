package com.unit7.study.cryptography.labs.lab5;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import com.unit7.study.cryptography.labs.lab2.CoderInfo;
import com.unit7.study.cryptography.labs.lab2.RSACoder;
import com.unit7.study.cryptography.labs.lab3.RSASignAlgorithm;

public class Bank {
    private Bank() {
        
    }
    
    /**
     * подписывается хэш банкноты
     * @param data
     * @return
     */
    public byte[] sign(byte[] data) {
        byte[] sign = signer.sign(data);
        return sign;
    }
    
    /**
     * Приходит реальная банкнота и подпись хэша
     * @param data
     * @param sign
     * @return
     */
    public boolean verify(byte[] data, byte[] sign) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(data);

            boolean verified = signer.verify(sign, hash);
            int value = ByteBuffer.wrap(data).getInt();
            if (banknotes.contains(value))
                return false;
            
            banknotes.add(value);
            return true;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return false;
    }
    
    public int getN() {
        return signer.getN();
    }
    
    public static Bank getInstance() {
        return instance;
    }
    
    private static final Bank instance = new Bank();
    private Set<Integer> banknotes = new HashSet<Integer>();
    private RSASignAlgorithm signer = new RSASignAlgorithm();
}
