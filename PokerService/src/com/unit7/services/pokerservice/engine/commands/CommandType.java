package com.unit7.services.pokerservice.engine.commands;

public enum CommandType {
    REQUEST_NAME("REQUEST_NAME"),
    REQUEST_SMALL_BLIND("REQUEST_SMALL_BLIND"),
    REQUEST_BIG_BLIND("REQUEST_BIG_BLIND"),
    GET_CARD("GET_CARD");
    
    private CommandType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    private String value;
}
