package com.unit7.study.cryptography.labs.lab4;

public class GamerFactory implements UserFactory {

    @Override
    public User createUser() {
        return new Gamer();
    }

}
