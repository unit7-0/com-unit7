package com.unit7.study.cryptography.labs.lab5;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import org.junit.Test;

public class Lab5Tests {
    @Test
    public void generateMoney() {
        Buyer buyer = new Buyer();
        Shop shop = new Shop();

        for (int i = 0; i < 10; ++i) {
            buyer.generateBanknote();
            byte[][] money = buyer.getMoney();
            byte[] realMoney = money[0];
            byte[] sign = money[1];

            long moneyValue = ByteBuffer.wrap(realMoney).getLong();
            writer.println("\r\n\r\ngenerated money: " + buyer.getBanknote() + "\r\nhash: "
                    + javax.xml.bind.DatatypeConverter.printHexBinary(realMoney) + "\r\nmoney sign: "
                    + javax.xml.bind.DatatypeConverter.printHexBinary(sign));

            boolean canBuy = shop.buy(ByteBuffer.allocate(4).putInt(buyer.getBanknote()).array(), sign);
            writer.println("can buy: " + canBuy);
        }
    }

    private PrintWriter writer = new PrintWriter(System.out, true);
}
