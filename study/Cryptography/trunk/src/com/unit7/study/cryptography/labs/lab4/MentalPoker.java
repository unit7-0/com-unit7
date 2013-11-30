package com.unit7.study.cryptography.labs.lab4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import com.unit7.study.cryptography.labs.lab1.MathUtils;
import com.unit7.study.cryptography.labs.lab2.CoderInfo;
import com.unit7.study.cryptography.labs.lab2.CoderInfoFactoryImpl;
import com.unit7.study.cryptography.labs.lab3.Algorithm;
import com.unit7.study.cryptography.tools.CoderInfoFactory;
import com.unit7.study.cryptography.tools.Pair;

/**
 * Абстракция сервера, у которого есть карты и подключенные клиенты. Каждый
 * пользователь имеет пару чисел, сервер ставит в соответствие каждой карте
 * число, это число шифруется всеми игроками, таким образом карты до снятия
 * шифра никому неизвестны и можно свободно их пересылать, не опасаясь перехвата
 * третьей стороной.
 * 
 * @author unit7
 * 
 */
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

        // для каждого пользователя
        for (Gamer target : getGamers()) {
            // раздаем каждому по userCardCount
            for (int i = 0; i < userCardCount; ++i) {
                // берем случайную число
                Integer[] arr = cypheredCards.toArray(new Integer[cypheredCards.size()]);
                int num = MathUtils.getRandInt(arr.length);
                int selected = arr[num];
                log.info("selected value: " + selected);

                // удаляем его из колоды
                cypheredCards.remove(selected);

                // каждый пользователь декодирует
                selected = decodeValue(selected);

                // исходное число, получаем карту
                Card card = sourceMap.get(selected);
                if (card == null)
                    log.info("Error while decoding, card == null, decoded value == " + selected);
                else
                    log.info("selected card: " + card);

                // отдаем карту
                target.addCard(card);
            }
        }
    }

    /**
     * порядок важен = расшифруют в обратном порядке
     * @param value
     * @return
     */
    protected int decodeValue(int value) {
        List<Gamer> gamers = getGamers();
        for (int i = gamers.size() - 1; i >= 0; --i) {
            Gamer gamer = gamers.get(i);
            String msg = "value: " + value;
            value = gamer.getDecoded(value);
            msg += " decoded to: " + value + " by gamer: " + gamer.getName();
            log.info(msg);
        }

        return value;
    }

    /**
     * Ширфует карты от каждого пользователя
     * порядок важен = по часовой шифруют
     */
    protected void cypherCards() {
        for (Integer value : sourceMap.keySet()) {
            Card card = sourceMap.get(value);
            for (Gamer gamer : getGamers()) {
                int start = value;
                value = (int) gamer.getEncoded(start);
                log.info(String.format("Gamer %s coded card %s with value %d to vaue %d", gamer, card,
                        start, value));
            }

            cypheredCards.add(value);
        }
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
            Gamer gamer = new Gamer(p);
            gamer.setName("name" + i);
            addGamer(gamer);
        }
    }

    /**
     * Генерирует карты
     */
    protected void generateCards(int n) {
        int start = 100;
        int initial = MathUtils.getRandInt(start);
        Set<Card> cards = new HashSet<Card>();
        for (int i = 0; i < n; ++i) {
            Card card = Card.randCard();
            while (cards.contains(card)) {
                card = Card.randCard();
            }
            
            cards.add(card);
            initial = initial + MathUtils.getRandInt(100) + 1;
            sourceMap.put(initial, card);
        }
    }

    /**
     * возвращает оставщиеся карты - прикуп, расшифрованные
     * 
     * @return
     */
    public List<Card> getRemainCards() {
        List<Card> result = new ArrayList<Card>();
        for (int value : cypheredCards) {
            log.info("value: " + value);
            int selected = decodeValue(value);
            // исходное число, получаем карту
            Card card = sourceMap.get(selected);
            if (card == null)
                log.info("Error while decoding, card == null, decoded value == " + selected);
            else {
                log.info("selected card: " + card);
                result.add(card);
            }
        }

        return result;
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

    private Map<Integer, Card> sourceMap = new HashMap<Integer, Card>();
    private Set<Integer> cypheredCards = new HashSet<Integer>();
    private CoderInfoFactory coderInfoFactory = new CoderInfoFactoryImpl();

    private static final Logger log = Logger.getLogger(MentalPoker.class.getName());

    private int userCardCount = 2;
    private int remainCardCount = 5;
    private Random rnd = new Random();
    private int p = new BigInteger(30, 100, rnd).intValue();
}
