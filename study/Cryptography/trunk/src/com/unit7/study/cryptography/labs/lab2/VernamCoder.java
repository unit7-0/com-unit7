package com.unit7.study.cryptography.labs.lab2;

/**
 * enc и dec индексы по массиву ключа соответственно для кодирования и
 * декодирования. Единичный вызов getEncoded или getDecoded сдвигает индекс на
 * единицу, чтобы для последующего вызова использовался другой ключ
 * 
 * @author unit7
 * 
 */
public class VernamCoder implements CoderInfo {
    public VernamCoder(int[] k) {
        this.k = k;
    }

    @Override
    public Object getEncoded(int m) {
        return m ^ k[enc++];
    }

    @Override
    public int getDecoded(Object coded) {
        if (!(coded instanceof Integer))
            throw new IllegalArgumentException("coded parameter must be int");

        return (Integer) coded ^ k[dec++];
    }

    public int[] getK() {
        return k;
    }

    public void setK(int[] k) {
        this.k = k;
    }

    private int[] k;
    private int enc, dec;
}
