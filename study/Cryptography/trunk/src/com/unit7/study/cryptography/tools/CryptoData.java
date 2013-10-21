package com.unit7.study.cryptography.tools;

import com.unit7.study.cryptography.labs.lab1.MathUtils;

public class CryptoData {
	public CryptoData(int p, int g) {
		this.p = p;
		this.g = g;
		generateKeys();
	}
	
	public void generateKeys() {
        priv = Utils.getRandPrime(p - 1);
        pub = (int) MathUtils.binpow(g, priv, p);
	}
	
	public int getPrivate() {
		return priv;
	}
	
	public void setPrivate(int priv) {
		this.priv = priv;
	}
	
	public int getPublic() {
		return pub;
	}
	
	public void setPublic(int pub) {
		this.pub = pub;
	}
	
	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	private int priv, pub;
	private int g, p;
}
