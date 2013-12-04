package com.unit7.study.cryptography.labs.lab5;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.unit7.study.cryptography.labs.lab1.MathUtils;

public class Buyer {
    public byte[][] getMoney() {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            Bank bank = Bank.getInstance();
            byte[] hash = md5.digest(ByteBuffer.allocate(4).putInt(banknote).array());
            byte[] blindSign = bank.sign(hash);
            
            return new byte[][] { hash, blindSign };
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }
    
    public int getBanknote() {
        return banknote;
    }
    
    public void generateBanknote() {
        banknote = MathUtils.getRandInt(100000);
    }
    
    private int banknote = MathUtils.getRandInt(100000);
}
