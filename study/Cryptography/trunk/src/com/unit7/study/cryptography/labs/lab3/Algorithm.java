package com.unit7.study.cryptography.labs.lab3;

public enum Algorithm {
    RSA("RSA"),
    VERNAM("VERNAM"),
    SHAMIR("SHAMIR"),
    EL_GAMAL("EL_GAMAL"),
    GOST_94("GOST_94");
    
    private Algorithm(String name) {
        this.name = name;
    }
    
    public String getValue() {
        return name;
    }
    
    private String name;
}
