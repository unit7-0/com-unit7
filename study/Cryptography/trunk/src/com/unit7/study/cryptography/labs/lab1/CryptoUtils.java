package com.unit7.study.cryptography.labs.lab1;

public class CryptoUtils {
    /**
     * Вычи�?ление общего ключа по �?хеме Диффи-Хеллмана.
     * @param priv закрытый ключ �?
     * @param pub открытый ключ Б
     * @param p большое про�?тое, модуль.
     * @return общий ключ.
     */
    public static long DiffieHelmanKey(int priv, int pub, long p) {
        return MathUtils.binpow(pub, priv, p);
    }
}
