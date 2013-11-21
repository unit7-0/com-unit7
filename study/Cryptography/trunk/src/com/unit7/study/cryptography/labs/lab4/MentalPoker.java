package com.unit7.study.cryptography.labs.lab4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            addGamer(new Gamer());
        }
    }
    
    /**
     * Генерирует карты
     */
    protected void generateCards(int n) {
        // генерация
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
    
    private Map<Gamer, Pair<Integer, Integer>> gamerDigits = new HashMap<Gamer, Pair<Integer, Integer>>();
    private Map<Card, Integer> cardMap = new HashMap<Card, Integer>();
}

