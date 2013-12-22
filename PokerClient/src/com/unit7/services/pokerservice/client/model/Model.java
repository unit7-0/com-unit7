/**
 * Date:	17 дек. 2013 г.
 * File:	Model.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.model;

import java.net.Socket;
import java.util.List;

/**
 * @author unit7
 *
 */
public class Model {    
    public Socket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public List<LightweightGamer> getGamers() {
        return gamers;
    }

    public void setGamers(List<LightweightGamer> gamers) {
        this.gamers = gamers;
    }

    public LightweightGamer getGamer() {
        return gamer;
    }

    public void setGamer(LightweightGamer gamer) {
        this.gamer = gamer;
    }

    public List<Card> getPrikup() {
        return prikup;
    }

    public void setPrikup(List<Card> prikup) {
        this.prikup = prikup;
    }

    private Socket serverSocket;
    private String name;
    private double bet;
    private double money;
    private List<LightweightGamer> gamers;
    private List<Card> prikup;
    private LightweightGamer gamer;
}
