package com.unit7.study.cryptography.labs.lab3;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

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

		while ((x = new BigInteger(q.bitLength(), rnd))
				.compareTo(BigInteger.ZERO) == 0)
			;

		y = a.modPow(x, p);
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

		long[] y = new long[(int) Math.ceil(t / 32) + 1];
		y[0] = defaultY == null ? x0 : defaultY;
		List<Integer> tList = new ArrayList<Integer>();
		tList.add(t);
		for (int i = 0; tList.get(i) >= 33; ++i) {
			tList.add(tList.get(i) / 2);
		}

		int s = tList.size() - 1;
		int ts = tList.get(s);
		long n = MathUtils.binpow(2, ts, Long.MAX_VALUE);
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
					y[i + 1] = BigInteger.valueOf(97781173)
							.multiply(BigInteger.valueOf(y[i]))
							.add(BigInteger.valueOf(c))
							.mod(BigInteger.valueOf(maxInt)).longValue();
					// mod = 2^32. Инта не хватит, берем лонг
				}

				BigInteger Ym = BigInteger.ZERO;
				for (int i = 0; i < r; ++i) {
					Ym = Ym.add(BigInteger.valueOf(2).pow(i * 32)
							.multiply(BigInteger.valueOf(y[i])));
				}

				y[0] = y[r];
				BigDecimal twoPow = BigDecimal.valueOf(2).pow(tList.get(m) - 1);
				BigInteger first = twoPow.divide(new BigDecimal(p[m + 1]),
						BigDecimal.ROUND_CEILING).toBigInteger();
				BigInteger second = twoPow
						.multiply(new BigDecimal(Ym))
						.divide(new BigDecimal(p[m + 1].multiply(BigInteger
								.valueOf(2).pow(32 * r))),
								BigDecimal.ROUND_FLOOR).toBigInteger();
				BigInteger N = first.add(second);
				if (N.testBit(0))
					N = N.add(BigInteger.ONE);
				int k = 0;
				// step 11
				while (true) {
					p[m] = p[m + 1].multiply(N.add(BigInteger.valueOf(k))).add(
							BigInteger.ONE);
					if (p[m].compareTo(BigInteger.valueOf(2).pow(tList.get(m))) > 0)
						continue stepSix;

					BigInteger firstCondition = BigInteger.valueOf(2).modPow(
							p[m + 1].multiply(N.add(BigInteger.valueOf(k))),
							p[m]);
					BigInteger secondCondition = BigInteger.valueOf(2).modPow(
							N.add(BigInteger.valueOf(k)), p[m]);
					if (firstCondition.compareTo(BigInteger.ONE) == 0
							&& secondCondition.compareTo(BigInteger.ONE) != 0) {
						m -= 1;
					} else {
						k += 2;
						continue;
					}
					if (m >= 0)
						continue stepFive;
					else {
						log.info(String
								.format("results:\n\tp: %s, \n\tq: %s, \n\t(p - 1) / q = %s, \n\t(p - 1) %% q = %s"
										+ "\n\tp is probable prime: %s, q is probable prime: %s,\n\tp bit count: %d, q bit count: %d",
										p[0],
										p[1],
										p[0].subtract(BigInteger.ONE).divide(
												p[1]),
										p[0].subtract(BigInteger.ONE)
												.divideAndRemainder(p[1])[1], p[0]
												.isProbablePrime(100), p[1]
												.isProbablePrime(100), p[0]
												.bitLength(), p[1].bitLength()));
						return new BigInteger[] { p[0], p[1],
								BigInteger.valueOf(y[0]) };
					}
				}
			}
		}
	}

	/**
	 * returns p(1024 bits) and q(256 bits) <b>q</b> is a dividor <b>p - 1</b>
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

		// получаем q длины 256. его битность равна половине битности числа p
		BigInteger[] res = A(tp / 2, null);
		BigInteger q = res[1];
		 res = A(tp, null/*res[2].intValue()*/);    /// ????
		// получаем Q длины 512. Его битность равна половине длины p
		BigInteger Q = res[0].subtract(BigInteger.ONE).divide(res[1]);// res[0];
		log.info("Q: " + Q + " \r\nQ.bitLength: " + Q.bitLength());

		// step 3
		long[] y = new long[33];
		y[0] = res[2].intValue();
		long maxInt = MathUtils.binpow(2, 32, Long.MAX_VALUE);
		BigDecimal qQ = new BigDecimal(q.multiply(Q));
		BigDecimal two1024 = BigDecimal.valueOf(2).pow(1024);
		int step3IterCount = 1;
		BigInteger twoTp = BigInteger.valueOf(2).pow(tp);
		stepThree: while (true) {
			for (int i = 0; i < y.length - 1; ++i) {
				y[i + 1] = BigInteger.valueOf(97781173)
						.multiply(BigInteger.valueOf(y[i]))
						.add(BigInteger.valueOf(c))
						.mod(BigInteger.valueOf(maxInt)).longValue();
			}

			BigInteger Y = BigInteger.valueOf(0);
			for (int i = 0; i < 32; ++i) {
				Y = Y.add(BigInteger.valueOf(y[i]).pow(32 * i)).mod(res[0]);   //// ??????
				log.info(String.format("step %d: Y = %d", i, Y));
			}

			y[0] = y[32];
			BigDecimal twoPow = BigDecimal.valueOf(2).pow(tp - 1);
			BigInteger first = twoPow.divide(qQ, BigDecimal.ROUND_CEILING)
					.toBigInteger();
			BigInteger second = twoPow.multiply(new BigDecimal(Y))
					.divide(qQ.multiply(two1024), BigDecimal.ROUND_FLOOR)
					.toBigInteger();
			BigInteger N = first.add(second);
			if (N.testBit(0))
				N = N.add(BigInteger.ONE);
			int k = 0;
			// step eight
			int step8Iter = 1;
			stepEight: while (true) {
				BigInteger p = qQ.toBigInteger()
						.multiply(N.add(BigInteger.valueOf(k)))
						.add(BigInteger.ONE);
				if (p.compareTo(twoTp) > 0) {
					log.info("step three iter: " + step3IterCount++ + String.format(" p: %s\r\n2^%d: %s", p, tp, twoTp));
					continue stepThree;
				}

				first = BigInteger.valueOf(2).modPow(
						qQ.toBigInteger()
								.multiply(N.add(BigInteger.valueOf(k))), p);
				second = BigInteger.valueOf(2).modPow(
						q.multiply(N.add(BigInteger.valueOf(k))), p);
				if (first.compareTo(BigInteger.ONE) == 0
						&& second.compareTo(BigInteger.ONE) != 0) {
					return new BigInteger[] { p, q };
				} else {
					k += 2;
					log.info(String.format("step eight iter: %d\r\nfirst: %s\r\nsecond: %s\r\nk: %d", step8Iter++, first, second, k));
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

			BigInteger f = d.modPow(p.subtract(BigInteger.ONE).divide(q), p);
			if (f.compareTo(BigInteger.ONE) == 0)
				continue;

			return f;
		}
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
		ByteBuffer buffer = ByteBuffer
				.allocate(rBytes.length + sBytes.length + 4)
				.putInt(rBytes.length).put(rBytes).put(sBytes);

		return buffer.array();
	}

	@Override
	public boolean verify(byte[] data, byte[] hash) {
		BigInteger h = new BigInteger(hash);
		ByteBuffer buffer = (ByteBuffer) ByteBuffer.allocate(data.length)
				.put(data).position(0);
		int rLength = buffer.getInt();
		byte[] bytes = new byte[rLength];
		buffer.get(bytes, 0, rLength);
		BigInteger r = new BigInteger(bytes);
		bytes = new byte[data.length - 4 - rLength];
		buffer.position(rLength + 4);
		buffer.get(bytes, 0, bytes.length);
		BigInteger s = new BigInteger(bytes);

		if (r.compareTo(BigInteger.ONE) < 0 || r.compareTo(q) >= 0
				|| s.compareTo(BigInteger.ONE) < 0 || s.compareTo(q) >= 0) {
			return false;
		}

		BigInteger invH = h.modInverse(q);
		BigInteger u1 = s.multiply(invH).mod(q);
		BigInteger u2 = r.negate().multiply(invH).mod(q);
		BigInteger v = a.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);

		return v.compareTo(r) == 0;
	}

	public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getQ() {
        return q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public BigInteger getA() {
        return a;
    }

    public void setA(BigInteger a) {
        this.a = a;
    }

    public BigInteger getX() {
        return x;
    }

    public void setX(BigInteger x) {
        this.x = x;
    }

    public BigInteger getY() {
        return y;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }

    @Override
	public String getName() {
		return Algorithm.GOST_94.getValue();
	}

	private BigInteger p, q, a, x, y;
	protected Random rnd = new Random(System.currentTimeMillis());

	private static final Logger log = Logger
			.getLogger(Gost94SignAlgorithm.class.getName());
}
