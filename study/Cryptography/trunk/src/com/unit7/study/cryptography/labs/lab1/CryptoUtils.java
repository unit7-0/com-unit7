package com.unit7.study.cryptography.labs.lab1;

public class CryptoUtils {
    /**
     * �������� ���������� ������ ����� ��� ���������� �����-��������.
     * @param priv �������� ���� �
     * @param pub �������� ���� �
     * @param p ������
     * @return ���� ��� ����������
     */
    public static long DiffieHelmanKey(int priv, int pub, long p) {
        return MathUtils.binpow(pub, priv, p);
    }
    
    /**
     * �������� ������ - ��� ��������, ��� ��������.
     * @param a �����
     * @param key ����� ����� ���������� � �������
     * @param p ������
     * @return x, where x = log<sub>a</sub>(key). a<sup>x</sup> mod p = key;
     */
    public static long babyStepGiantStep(int a, long key, long p) {
    	// TODO realise
    	return 0;
    }
}
