package com.unit7.services.pokerservice.client.model;

import com.unit7.services.pokerservice.client.tools.Utils;

public enum Suit {
    SPADES("spades"), CLUBS("clubs"), DIAMONDS("diamonds"), HEARTS("hearts");
    
    private Suit(String name) {
        this.name = name;
    }
    
    public static Suit rand() {
    	return values()[Utils.getRandInt(values().length)];
    }
    
    public String getName() {
        return name;
    }

    private String name;
}
