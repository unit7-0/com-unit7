package com.unit7.study.cryptography.labs.lab1;

public class CryptoUtils {
    /**
     * Алгоритм вычисления общего ключа для шифрования Диффи-Хеллмана.
     * @param priv Закрытый ключ А
     * @param pub Открытый ключ Б
     * @param p модуль
     * @return ключ для шифрования
     */
    public static long DiffieHelmanKey(int priv, int pub, long p) {
        return MathUtils.binpow(pub, priv, p);
    }
    
    /**
     * Алгоритм Шенкса - шаг младенца, шаг великана.
     * @param a число
     * @param key число после возведения в степень
     * @param p модуль
     * @return x, where x = log<sub>a</sub>(key). a<sup>x</sup> mod p = key;
     */
    public static long babyStepGiantStep(int a, long key, long p) {
    	// TODO realise
    	return 0;
    }
}
