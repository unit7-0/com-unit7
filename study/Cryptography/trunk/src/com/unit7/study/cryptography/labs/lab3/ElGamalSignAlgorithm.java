package com.unit7.study.cryptography.labs.lab3;

import java.nio.ByteBuffer;

import com.unit7.study.cryptography.labs.lab1.MathUtils;
import com.unit7.study.cryptography.tools.Pair;
import com.unit7.study.cryptography.tools.Utils;

public class ElGamalSignAlgorithm implements SignAlgorithm {
    public ElGamalSignAlgorithm() {
        generate();
    }

    public void generate() {
        Pair<Integer, Integer> pg = Utils.generatePG(13335);
        p = pg.getFirst();
        g = pg.getSecond();

        x = MathUtils.getRandPrime(p - 2);
        y = (int) MathUtils.binpow(g, x, p);
    }

    /**
     * each bytes of data encode to @s array. first 4 bytes of encoded array is
     * number r
     */
    @Override
    public byte[] sign(byte[] data) {
        byte[] encoded = new byte[data.length * 4 + 4];

        int k = 0;
        Pair<Integer, Integer> tmp = new Pair<Integer, Integer>();
        do {
            k = MathUtils.getRandPrime(p - 2) + 1;
        } while (MathUtils.gcd(k, p - 1, tmp) != 1);

        int gcd = 0;
        int r = (int) MathUtils.binpow(g, k, p);
        do {
            gcd = MathUtils.gcd(k, p - 1, tmp);
        } while (gcd != 1);

        int invK = tmp.getFirst();
        if (invK < 0)
            invK += p - 1;
        
        ByteBuffer buffer = ByteBuffer.allocate(4);
        for (int i = 0; i < data.length; ++i) {
            int u = (data[i] - x * r) % (p - 1);
            while (u < 0)
                u += p - 1;
            
            int s = invK * u % (p - 1);
            buffer.position(0);
            buffer.putInt(s);
            byte[] bytes = buffer.array();
            for (int j = i * 4 + 4; j < i * 4 + 8; ++j) {
                encoded[j] = bytes[j - (i * 4 + 4)];
            }
        }

        buffer.position(0);
        buffer.putInt(r);
        byte[] bytes = buffer.array();
        for (int i = 0; i < bytes.length; ++i) {
            encoded[i] = bytes[i];
        }
        
        return encoded;
    }

    /**
     * get first 4 bytes from data as r number and other is s array
     */
    @Override
    public byte[] verify(byte[] data, byte[] hash) {
        byte[] decoded = new byte[(data.length - 4) / 4];
        
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(data, 0, 4);
        buffer.position(0);
        int r = buffer.getInt();
        
        for (int i = 4; i + 4 <= data.length; i += 4) {
            buffer.position(0);
            buffer.put(data, i, 4);
            buffer.position(0);
            int s = buffer.getInt();
            decoded[(i - 4) / 4] = (byte) (MathUtils.binpow(y, r, p) * MathUtils.binpow(r, s, p) % p);
        }
        
        return decoded;
    }

    @Override
    public String getName() {
        return Algorithm.EL_GAMAL.getValue();
    }

    private int p, g, x, y;
}
