package com.unit7.study.cryptography.labs.lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CryptoUtils {
    /**
     * Алгоритм вычисления общего ключа для шифрования Диффи-Хеллмана.
     * @param priv Закрытый ключ А
     * @param pub Открытый ключ Б
     * @param p модуль
     * @return ключ для шифрования
     */
    public static long DiffieHellmanKey(long priv, long pub, long p) {
        return MathUtils.binpow(pub, priv, p);
    }
    
    /**
     * Алгоритм Шенкса - шаг младенца, шаг великана.
     * Предполагается, что модуль p не больше 10<sup>10</sup> из-за ограничений памяти.
     * @param a число
     * @param key число после возведения в степень
     * @param p модуль
     * @return Список значений x, где x = log<sub>a</sub>(key). a<sup>x</sup> mod p = key; 
     */
    public static List<Long> babyStepGiantStep(long a, long key, long p) {
        Map<Long, Integer> babySteps = new HashMap<Long, Integer>();
        int m = (int) Math.sqrt(p) + 1;
        for (int i = m - 1; i >= 0; --i) {
            babySteps.put(MathUtils.binpow(a, i, p) * key % p, i);
        } 

        List<Long> result = new ArrayList<Long>();
        for (long t = m, i = 1; t <= m * m; ++i, t += m) {
            long b = MathUtils.binpow(a, t, p);
            if (babySteps.containsKey(b)) {
                long x = i * m - babySteps.get(b);
                result.add(x);
            }
        }

        if (result.size() == 0)
                throw new IllegalArgumentException("May be something wrong in arguments, because the solution must be works for all samples");
        return result;
    }
}