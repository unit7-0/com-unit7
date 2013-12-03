package com.unit7.services.pokerservice.engine.commands;

public enum CommandType {
    REQUEST_NAME("REQUEST_NAME"),
    SMALL_BLIND("SMALL_BLIND"),
    BIG_BLIND("BIG_BLIND"),
    BET("BET"),
    CALL("CALL"),
    RAISE("RAISE"),
    FOLD("FOLD"),
    GET_CARD("GET_CARD"), 
    PREFLOP("PREFLOP"), 
    RIVER("RIVER"), 
    TURN("TURN"), 
    FLOP("FLOP");
    
    private CommandType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    private String value;
}
