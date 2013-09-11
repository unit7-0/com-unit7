package com.unit7.study.cryptography.labs.lab1;

import java.util.HashSet;
import java.util.Set;

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
     * ��������������, ��� ������ p �� ������ 10<sup>10</sup> ��-�� ����������� ������.
     * @param a �����
     * @param key ����� ����� ���������� � �������
     * @param p ������
     * @return x, where x = log<sub>a</sub>(key). a<sup>x</sup> mod p = key;
     */
    public static long babyStepGiantStep(int a, long key, long p) {
    	Set<Long> first = new HashSet<Long>(), second = new HashSet<Long>();
    	// TODO realise
    	return 0;
    }
}
