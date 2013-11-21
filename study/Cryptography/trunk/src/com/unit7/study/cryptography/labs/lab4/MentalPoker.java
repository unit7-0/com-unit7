package com.unit7.study.cryptography.labs.lab4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
        cypherCards();

        // shuffle
        // раздаем каждому по userCardCount
        for (int i = 0; i < userCardCount; ++i) {
            // берем случайную карту
            cardMap.keySet().
            // и ее кодированное значение
            
            // каждый пользователь декодирует
            for (Gamer gamer : getGamers()) {
                
            }
        }
    }

    /**
     * Ширфует карты от каждого пользователя
     */
    protected void cypherCards() {
        for (Integer value : cardMap.keySet()) {
            Card card = cardMap.remove(value);
            for (Map.Entry<Gamer, CoderInfo> gamerEntry : gamerDigits.entrySet()) {
                int start = value;
                value = (int) gamerEntry.getValue().getEncoded(start);
                log.info(String.format("Gamer %s coded card %s with value %d to vaue %d", gamerEntry.getKey(), card,
                        start, value));
            }
            
            cardMap.put(value, card);
        }
    }

    /**
     * Дешифрует карту от каждого пользователя
     * 
     * @return
     */
    protected Card decypherCard(Card card) {

    }

    /**
     * генерирует колоду и пользователей
     */
    protected void generate(int n) {
        generateUsers(n);
        generateCards(n * userCardCount + remainCardCount);
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
            cardMap.put(initial, card);
        }
    }

    /**
     * возвращает оставщиеся карты - прикуп, расшифрованные
     * 
     * @return
     */
    public List<Card> getRemainCards() {
        // just a stub yet
        return cards;
    }

    public int getUserCardCount() {
        return userCardCount;
    }

    public void setUserCardCount(int userCardCount) {
        this.userCardCount = userCardCount;
    }

    public int getRemainCardCount() {
        return remainCardCount;
    }

    public void setRemainCardCount(int remainCardCount) {
        this.remainCardCount = remainCardCount;
    }

    private List<Card> cards = new ArrayList<Card>();

    private Map<Gamer, CoderInfo> gamerDigits = new HashMap<Gamer, CoderInfo>();
    private Map<Integer, Card> cardMap = new HashMap<Integer, Card>();
    private UserFactory gamerFactory = new GamerFactory();
    private CoderInfoFactory coderInfoFactory = new CoderInfoFactoryImpl();

    private static final Logger log = Logger.getLogger(MentalPoker.class.getName());

    private int userCardCount = 2;
    private int remainCardCount = 5;
}
