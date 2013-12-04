package com.unit7.study.cryptography.labs.lab3;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.unit7.study.cryptography.labs.lab1.MathUtils;
import com.unit7.study.cryptography.tools.Pair;

public class RSASignAlgorithm implements SignAlgorithm {
    public RSASignAlgorithm() {
        generate();
    }

    public void generate() {
        int p = MathUtils.getRandPrime(13377);
        int q = MathUtils.getRandPrime(13377);
        n = p * q;
        int gcd = 0;
        int f = (p - 1) * (q - 1);
        Pair<Integer, Integer> xy = new Pair<Integer, Integer>();
        do {
            d = MathUtils.getRandInt(f);
            gcd = MathUtils.gcd(d, f, xy);
        } while (gcd != 1);

        c = xy.getFirst();
        if (c < 0)
            c += f;
    }

    /**
     * encode each byte to int and write to encoded as 4 bytes
     */
    @Override
    public byte[] sign(byte[] data) {
        byte[] encoded = new byte[data.length * 4];
        ByteBuffer buffer = ByteBuffer.allocate(4);
        for (int i = 0; i < data.length; ++i) {
            int buf = (int) MathUtils.binpow(data[i], c, n);
            buffer.position(0);
            buffer.putInt(buf);
            byte[] bytes = buffer.array();
            for (int j = i * 4; j < i * 4 + 4; ++j) {
                encoded[j] = bytes[j - i * 4];
            }
        }

        return encoded;
    }

    /**
     * decode each 4 bytes of data to 1 byte decoded
     */
    @Override
    public boolean verify(byte[] data, byte[] hash) {
        byte[] decoded = new byte[data.length / 4];
        ByteBuffer buffer = ByteBuffer.allocate(4);
        for (int i = 0; i + 4 <= data.length; i += 4) {
            buffer.position(0);
            buffer.put(data, i, 4);
            buffer.position(0);
            int encoded = buffer.getInt();
            decoded[i / 4] = (byte) MathUtils.binpow(encoded, d, n);
        }
        
        return Arrays.equals(decoded, hash);
    }

    @Override
    public String getName() {
        return Algorithm.RSA.getValue();
    }

    public int getN() {
        return n;
    }
    
    private int c, d, n;
}
