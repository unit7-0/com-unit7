package com.unit7.study.cryptography.labs.lab5;

public class Shop {
    /**
     * 
     * @param money реальная банкнота
     * @param sign подпись хэша от банкноты
     * @return
     */
    public boolean buy(byte[] money, byte[] sign) {
        return Bank.getInstance().verify(money, sign);
    }
}
