package com.unit7.study.cryptography.labs.lab4;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {
    public List<Gamer> getGamers() {
        return gamers;
    }

    public void setGamers(List<Gamer> gamers) {
        this.gamers = gamers;
    }

    public void addGamer(Gamer gamer) {
        gamers.add(gamer);
    }
    
    private List<Gamer> gamers = new ArrayList<Gamer>();
}
