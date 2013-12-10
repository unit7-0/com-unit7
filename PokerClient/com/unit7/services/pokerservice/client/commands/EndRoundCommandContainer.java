package com.unit7.services.pokerservice.client.commands;

public class EndRoundCommandContainer extends AbstractCommandContainer {
    public double getCurrentWin() {
        return currentWin;
    }

    public void setCurrentWin(double currentWin) {
        this.currentWin = currentWin;
    }

    private double currentWin;
}
