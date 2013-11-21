package com.unit7.study.cryptography.labs.lab4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unit7.study.cryptography.labs.lab1.MathUtils;
import com.unit7.study.cryptography.labs.lab2.CoderInfo;
import com.unit7.study.cryptography.labs.lab2.CoderInfoFactoryImpl;
import com.unit7.study.cryptography.labs.lab3.Algorithm;
import com.unit7.study.cryptography.tools.CoderInfoFactory;
import com.unit7.study.cryptography.tools.Pair;


public class MentalPoker extends Game {
    public MentalPoker(int n) {
        distributeCards(n);
    }
    
    /**
     * раздает карты игрокам
     */
    public void distributeCards(int n) {
        generate(n);
        
        // раздача
    }
    
    /**
     * генерирует колоду и пользователей
     */
    protected void generate(int n) {
        generateUsers(n);
        generateCards(n);
    }
    
    /**
     * Генерирует пользователей
     */
    protected void generateUsers(int n) {
        for (int i = 0; i < n; ++i) {
            Gamer gamer = (Gamer) gamerFactory.createUser();
            addGamer(gamer);
            gamerDigits.put(gamer, coderInfoFactory.createCoderInfo(Algorithm.RSA, null));
        }
    }
    
    /**
     * Генерирует карты
     */
    protected void generateCards(int n) {
        int start = 100;
        int initial = MathUtils.getRandInt(start);
        for (int i = 0; i < n; ++i) {
            Card card = new Card();
            cards.add(card);
            initial = initial + MathUtils.getRandInt(100) + 1;
            cardMap.put(card, initial);
        }
    }
    
    /**
     * возвращает оставщиеся карты - прикуп, расшифрованные
     * @return
     */
    public List<Card> getRemainCards() {
        // just a stub yet
        return cards;
    }

    private List<Card> cards = new ArrayList<Card>(); 
    
    private Map<Gamer, CoderInfo> gamerDigits = new HashMap<Gamer, CoderInfo>();
    private Map<Card, Integer> cardMap = new HashMap<Card, Integer>();
    private UserFactory gamerFactory = new GamerFactory();
    private CoderInfoFactory coderInfoFactory = new CoderInfoFactoryImpl();
}

