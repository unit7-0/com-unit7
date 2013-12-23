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

    public double getInitialMoney() {
		return initialMoney;
	}

	public void setInitialMoney(double initialMoney) {
		this.initialMoney = initialMoney;
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

    public double getBigBlind() {
		return bigBlind;
	}

	public void setBigBlind(double bigBlind) {
		this.bigBlind = bigBlind;
	}

	private Socket serverSocket;
    private String name;
    private double initialMoney;
    private double bigBlind;
    private List<LightweightGamer> gamers;
    private List<Card> prikup;
    private LightweightGamer gamer = new LightweightGamer();
}
