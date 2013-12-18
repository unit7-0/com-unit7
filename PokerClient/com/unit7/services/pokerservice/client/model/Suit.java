package com.unit7.services.pokerservice.client.model;

import com.unit7.services.pokerservice.client.tools.Utils;

public enum Suit {
    SPADES, CLUBS, DIAMONDS, HEARTS;
    
    public static Suit rand() {
    	return values()[Utils.getRandInt(values().length)];
    }
}
