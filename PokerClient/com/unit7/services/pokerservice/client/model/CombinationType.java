package com.unit7.services.pokerservice.client.model;

import java.util.List;

/**
 * Тип комбинации карт. Константами заданы только базовые комбинации. Для
 * определения типа комбинации служит поле rank, чтобы получить комбинацию нужно
 * вызвать метод getCombination. Равенство комбинаций определяется по названию.
 * Для каждой комбинации зарезервировано несколько значений rank, что позволяет
 * однозначно определить как старшинство комбинации так и старшинство в разрезе
 * комбинации.
 * 
 * @author unit7
 * 
 */
public class CombinationType implements Comparable<CombinationType> {
    public static final CombinationType STREET_FLASH = new CombinationType(0, "Street flush");
    public static final CombinationType FOUR_OF_A_KIND = new CombinationType(9, "Four of a kind");
    public static final CombinationType FULL_HOUSE = new CombinationType(57, "Full house");
    public static final CombinationType FLUSH = new CombinationType(70, "Flush");
    public static final CombinationType STREET = new CombinationType(80, "Street");
    public static final CombinationType THREE_OF_A_KIND = new CombinationType(90, "Three of a kind");
    public static final CombinationType TWO_PAIRS = new CombinationType(103, "Two pairs");
    public static final CombinationType PAIR = new CombinationType(129, "Pair");
    public static final CombinationType OTHER = new CombinationType(777, "Other");

    private CombinationType(int rank, String desk) {
        this.rank = rank;
        this.desk = desk;
    }

    public String getDesk() {
        return desk;
    }

    /**
     * stub Возвращает тип комбинации из пяти карт
     * 
     * @param cards
     * @return
     */
    public static CombinationType getCombination(List<Card> cards) {
        return new CombinationType(1, "");
    }

    /**
     * stub Возвращает лучший тип комбинации среди семи карт
     * 
     * @param cards
     * @return
     */
    public static CombinationType getPerfectCombintaionType(List<Card> cards) {
        return new CombinationType(1, "");
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CombinationType))
            return false;

        CombinationType type = (CombinationType) other;
        return desk.equals(type.desk);
    }

    @Override
    public int compareTo(CombinationType type) {
        return rank - type.rank;
    }

    private int rank;
    private String desk;
}
