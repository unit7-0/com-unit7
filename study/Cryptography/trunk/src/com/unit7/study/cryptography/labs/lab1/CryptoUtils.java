package com.unit7.study.cryptography.labs.lab1;

import java.util.HashMap;
import java.util.Map;

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
     * Предполагается, что модуль p не больше 10<sup>10</sup> из-за ограничений памяти.
     * @param a число
     * @param key число после возведения в степень
     * @param p модуль
     * @return x, where x = log<sub>a</sub>(key). a<sup>x</sup> mod p = key;
     */
    public static long babyStepGiantStep(int a, long key, long p) {
    	Map<Long, Integer> babySteps = new HashMap<Long, Integer>();
        int m = (int) Math.sqrt(p) + 1;
        for (int i = m - 1; i >= 0; --i) {
            babySteps.put(MathUtils.binpow(a, i, p) * key % p, i);
        } 

        for (long t = m, i = 1; t <= m * m; ++i, t *= i) {
            long b = MathUtils.binpow(a, t, p);
            if (babySteps.containsKey(b)) {
                long x = i * m - babySteps.get(b);
                return x;
            }
        }

    	// TODO check it
    	throw new IllegalArgumentException("May be something wrong in arguments, because the solution must be works for all samples.");
    }
}
