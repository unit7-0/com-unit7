package com.unit7.services.pokerservice.client.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public static final CombinationType STRAIGHT_FLASH = new CombinationType(0,
			"Straight flash");
	public static final CombinationType FOUR_OF_A_KIND = new CombinationType(9,
			"Four of a kind");
	public static final CombinationType FULL_HOUSE = new CombinationType(57,
			"Full house");
	public static final CombinationType FLUSH = new CombinationType(70, "Flush");
	public static final CombinationType STRAIGHT = new CombinationType(80,
			"Straight");
	public static final CombinationType THREE_OF_A_KIND = new CombinationType(
			90, "Three of a kind");
	public static final CombinationType TWO_PAIRS = new CombinationType(103,
			"Two pairs");
	public static final CombinationType PAIR = new CombinationType(129, "Pair");
	public static final CombinationType HIGH_CARD = new CombinationType(777,
			"Other");

	private CombinationType(int rank, String desk) {
		this.rank = rank;
		this.desk = desk;
	}

	public String getDesk() {
		return desk;
	}

	/**
	 * Определить тип комбинации из пяти карт.
	 * 
	 * @param cards
	 * @return
	 */
	private static CombinationType determine(List<Card> cards) {
		Map<Integer, Integer> same = new HashMap<Integer, Integer>();
		for (Iterator<Card> it = cards.iterator(); it.hasNext();) {
			Card card = it.next();
			int ordinal = card.getType().ordinal();
			if (same.containsKey(ordinal)) {
				same.put(ordinal, same.get(ordinal) + 1);
			} else {
				same.put(ordinal, 1);
			}
		}

		int maxSame = 0;
		int prevMax = 0;
		for (Map.Entry<Integer, Integer> entry : same.entrySet()) {
			if (entry.getValue() >= maxSame) {
				prevMax = maxSame;
				maxSame = entry.getValue();
			}
		}

		if (maxSame == 4) {
			return FOUR_OF_A_KIND;
		} else if (maxSame == 3 && prevMax == 2) {
			return FULL_HOUSE;
		} else if (maxSame == 3) {
			return THREE_OF_A_KIND;
		} else if (maxSame == 2 && prevMax == 2) {
			return TWO_PAIRS;
		} else if (maxSame == 2) {
			return PAIR;
		} else {
			// straight or straight flash or high card
			if (same.size() == 5) {
				Set<Suit> suits = new HashSet<Suit>();
				for (Card card : cards) {
					suits.add(card.getType().getSuit());
				} 
				
				if (suits.size() == 5)
					return STRAIGHT_FLASH;
				else
					return STRAIGHT;
			} else {
				return HIGH_CARD;
			}
		}
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
