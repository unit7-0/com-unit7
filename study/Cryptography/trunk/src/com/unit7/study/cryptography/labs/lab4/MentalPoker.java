package com.unit7.study.cryptography.labs.lab4;

import java.util.ArrayList;
import java.util.List;


public class MentalPoker extends Game {
    public MentalPoker(int n) {
        distributeCards();
    }
    
    /**
     * раздает карты игрокам
     */
    public void distributeCards() {
        generate();
        
        // раздача
    }
    
    /**
     * генерирует колоду и пользователей
     */
    protected void generate() {
        generateUsers();
        generateCards();
    }
    
    /**
     * Генерирует пользователей
     */
    protected void generateUsers() {
        // генерация
    }
    
    /**
     * Генерирует карты
     */
    protected void generateCards() {
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
}

