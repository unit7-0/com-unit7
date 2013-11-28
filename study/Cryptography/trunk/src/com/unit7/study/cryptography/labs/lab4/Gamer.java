package com.unit7.study.cryptography.labs.lab4;

import java.util.ArrayList;
import java.util.List;

import com.unit7.study.cryptography.labs.lab1.MathUtils;
import com.unit7.study.cryptography.tools.Pair;

public class Gamer extends User {
    public Gamer(int p) {
        this.p = p;
        generate();
    }

    public void generate() {
        c = MathUtils.getRandPrime(p - 1);
        Pair<Integer, Integer> xy = new Pair<Integer, Integer>();
        int gcd;
        
        do {
            gcd = MathUtils.gcd(c, p - 1, xy); 
        } while (gcd != 1);
        
        d = xy.getFirst();
        if (d < 0)
            d += p - 1;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName();
    }

    public int getEncoded(int m) {
        return (int) MathUtils.binpow(m, c, p);
    }
    
    public int getDecoded(int m) {
        return (int) MathUtils.binpow(m, d, p);
    }
    
    private int p;
    private int c;
    private int d;
    private List<Card> cards = new ArrayList<Card>();
}
