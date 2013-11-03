package com.unit7.study.cryptography.labs.lab3;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.unit7.study.cryptography.labs.lab1.MathUtils;

public class Gost94SignAlgorithm implements SignAlgorithm {
    public Gost94SignAlgorithm() {
        generate();
    }

    public void generate() {
        BigInteger[] pq = B();
        p = pq[0];
        q = pq[1];
        a = C();
    }

    /**
     * returns p and q by procedure A'
     * 
     * @return
     */
    public BigInteger[] A(int t, Integer defaultY) {
        /**
         * 0 < x<sub>0</sub> < 2<sup>32</sup> 0 < c < 2<sup>32</sup> and
         * <b>c</b> not even
         */
        int x0 = MathUtils.getRandIntWithoutZero(Integer.MAX_VALUE);
        int c = MathUtils.getRandIntWithoutZero(Integer.MAX_VALUE);
        while ((c & 1) == 0) {
            c = MathUtils.getRandIntWithoutZero(Integer.MAX_VALUE);
        }

        int[] y = new int[(int) Math.ceil(t / 32)];
        y[0] = defaultY == null ? x0 : defaultY;
        List<Integer> tList = new ArrayList<Integer>();
        tList.add(t);
        for (int i = 0; tList.get(i) >= 33; ++i) {
            tList.add(tList.get(i) / 2);
        }

        int s = tList.size();
        int ts = tList.get(s);
        int n = (int) MathUtils.binpow(2, ts - 1, Integer.MAX_VALUE);
        int m = s - 1;
        /**
         * <b>p<sub>s</sub></b> min probable prime of length t<sub>s</sub>
         */
        BigInteger[] p = new BigInteger[s + 1];
        p[s] = BigInteger.valueOf(n).nextProbablePrime();

        long maxInt = MathUtils.binpow(2, 32, Long.MAX_VALUE);
        // step 5
        stepFive: while (true) {
            int r = (int) Math.ceil(tList.get(m) / 32);
            // step 6
            stepSix: while (true) {
                for (int i = 0; i < r; ++i) {
                    y[i + 1] = BigInteger.valueOf(97781173).multiply(BigInteger.valueOf(y[i]))
                            .add(BigInteger.valueOf(c)).mod(BigInteger.valueOf(maxInt)).intValue();
                }

                BigInteger Ym = BigInteger.ZERO;
                for (int i = 0; i < r; ++i) {
                    Ym = Ym.add(BigInteger.valueOf(2).pow(i * 32).multiply(BigInteger.valueOf(y[i])));
                }

                y[0] = y[r];
                BigDecimal twoPow = BigDecimal.valueOf(2).pow(tList.get(m) - 1);
                BigInteger first = twoPow.divide(new BigDecimal(p[m + 1]), BigDecimal.ROUND_CEILING).toBigInteger();
                BigInteger second = twoPow
                        .multiply(new BigDecimal(Ym))
                        .divide(new BigDecimal(p[m + 1].multiply(BigInteger.valueOf(2).pow(32 * r))),
                                BigDecimal.ROUND_FLOOR).toBigInteger();
                BigInteger N = first.add(second);
                if (N.testBit(0))
                    N = N.add(BigInteger.ONE);
                int k = 0;
                // step 11
                while (true) {
                    p[m] = p[m + 1].multiply(N.add(BigInteger.valueOf(k))).add(BigInteger.ONE);
                    if (p[m].compareTo(BigInteger.valueOf(2).pow(tList.get(m))) > 0)
                        continue stepSix;

                    BigInteger firstCondition = pow(BigInteger.valueOf(2), p[m + 1].add(N.add(BigInteger.valueOf(k))))
                            .mod(p[m]);
                    BigInteger secondCondition = pow(BigInteger.valueOf(2), N.add(BigInteger.valueOf(k))).mod(p[m]);
                    if (firstCondition.compareTo(BigInteger.ONE) == 0 && secondCondition.compareTo(BigInteger.ONE) != 1) {
                        m -= 1;
                    } else {
                        k += 2;
                        continue;
                    }
                    if (m >= 0)
                        continue stepFive;
                    else {
                        return new BigInteger[] { p[0], p[1], BigInteger.valueOf(y[0]) };
                    }
                }
            }
        }
    }

    /**
     * returns
     * 
     * @return
     */
    public BigInteger[] B() {
        /**
         * 0 < x<sub>0</sub> < 2<sup>32</sup> 0 < c < 2<sup>32</sup> and
         * <b>c</b> not even
         */
        int x0 = MathUtils.getRandIntWithoutZero(Integer.MAX_VALUE);
        int c = MathUtils.getRandIntWithoutZero(Integer.MAX_VALUE);
        int tp = 1024;
        while ((c & 1) == 0) {
            c = MathUtils.getRandIntWithoutZero(Integer.MAX_VALUE);
        }

        BigInteger[] res = A(256, null);
        BigInteger q = res[0];
        res = A(512, res[2].intValue());
        BigInteger Q = res[0];

        // step 3
        int[] y = new int[33];
        long maxInt = MathUtils.binpow(2, 32, Long.MAX_VALUE);
        BigDecimal qQ = new BigDecimal(q.multiply(Q));
        BigDecimal two1024 = BigDecimal.valueOf(2).pow(1024);
        stepThree: while (true) {
            for (int i = 0; i < y.length - 1; ++i) {
                y[i + 1] = BigInteger.valueOf(97781173).multiply(BigInteger.valueOf(y[i])).add(BigInteger.valueOf(c))
                        .mod(BigInteger.valueOf(maxInt)).intValue();
            }

            BigInteger Y = BigInteger.ZERO;
            for (int i = 0; i < 32; ++i) {
                Y = Y.add(BigInteger.valueOf(y[i]).pow(32 * i));
            }

            y[0] = y[32];
            BigDecimal twoPow = BigDecimal.valueOf(2).pow(tp - 1);
            BigInteger first = twoPow.divide(qQ, BigDecimal.ROUND_CEILING).toBigInteger();
            BigInteger second = twoPow.multiply(new BigDecimal(Y)).divide(qQ.multiply(two1024), BigDecimal.ROUND_FLOOR)
                    .toBigInteger();
            BigInteger N = first.add(second);
            if (N.testBit(0))
                N = N.add(BigInteger.ONE);
            int k = 0;
            // step eight
            stepEight: while (true) {
                BigInteger p = qQ.toBigInteger().multiply(N.add(BigInteger.valueOf(k))).add(BigInteger.ONE);
                if (p.compareTo(BigInteger.valueOf(2).pow(tp)) > 0)
                    continue stepThree;

                first = pow(BigInteger.valueOf(2), qQ.toBigInteger().multiply(N.add(BigInteger.valueOf(k)))).mod(p);
                second = pow(BigInteger.valueOf(2), q.multiply(N.add(BigInteger.valueOf(k)))).mod(p);
                if (first.compareTo(BigInteger.ONE) == 0 && second.compareTo(BigInteger.ONE) != 1) {
                    return new BigInteger[] { p, q };
                } else {
                    k += 2;
                    continue stepEight;
                }
            }
        }
    }

    /**
     * returns a by given p and q
     * 
     * @return
     */
    public BigInteger C() {
        /**
         * 1 < d
         * < p
         * - 1
         */
        BigInteger d = null;
        while (true) {
            while ((d = new BigInteger(p.bitLength() - 1, rnd))
                    .compareTo(BigInteger.ZERO) == 0) {
            }
            
            BigInteger f = pow(d, p.subtract(BigInteger.ONE).divide(q));
            if (f.compareTo(BigInteger.ONE) == 0)
                continue;
            
            return f;
        }
    }

    BigInteger pow(BigInteger base, BigInteger exponent) {
        BigInteger result = BigInteger.ONE;
        while (exponent.signum() > 0) {
            if (exponent.testBit(0))
                result = result.multiply(base);
            base = base.multiply(base);
            exponent = exponent.shiftRight(1);
        }
        return result;
    }

    /**
     * first four bytes is an integer whose represent bytes count of r number
     * and next parts of data array is a r and s numbers
     */
    @Override
    public byte[] sign(byte[] data) {
        BigInteger hash = new BigInteger(data);

        BigInteger k;
        BigInteger r;

        do {
            k = new BigInteger(q.bitLength() - 2, rnd).add(BigInteger.ONE);
            r = a.modPow(k, p).mod(q);
        } while (r.compareTo(BigInteger.ZERO) == 0);

        BigInteger s = k.multiply(hash).add(x.multiply(r)).mod(q);

        byte[] rBytes = r.toByteArray();
        byte[] sBytes = s.toByteArray();
        ByteBuffer buffer = ByteBuffer.allocate(rBytes.length + sBytes.length + 4).putInt(rBytes.length).put(rBytes)
                .put(sBytes);

        return buffer.array();
    }

    @Override
    public boolean verify(byte[] data, byte[] hash) {
        BigInteger h = new BigInteger(hash);
        ByteBuffer buffer = (ByteBuffer) ByteBuffer.allocate(data.length).put(data).position(0);
        int rLength = buffer.getInt();
        byte[] bytes = new byte[rLength];
        buffer.get(bytes, 0, rLength);
        BigInteger r = new BigInteger(bytes);
        bytes = new byte[data.length - 4 - rLength];
        buffer.position(rLength + 4);
        buffer.get(bytes, 0, bytes.length);
        BigInteger s = new BigInteger(bytes);

        if (r.compareTo(BigInteger.ONE) < 0 || r.compareTo(q) >= 0 || s.compareTo(BigInteger.ONE) < 0
                || s.compareTo(q) >= 0) {
            return false;
        }

        BigInteger invH = h.modInverse(q);
        BigInteger u1 = s.multiply(invH).mod(q);
        BigInteger u2 = r.negate().multiply(invH).mod(q);
        BigInteger v = a.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);

        return v.compareTo(r) == 0;
    }

    @Override
    public String getName() {
        return Algorithm.GOST_94.getValue();
    }

    private BigInteger p, q, a, x, y;
    private Random rnd = new Random(System.currentTimeMillis());
}
