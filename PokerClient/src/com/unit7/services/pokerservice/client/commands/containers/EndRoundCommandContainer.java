package com.unit7.services.pokerservice.client.commands.containers;

public class EndRoundCommandContainer extends AbstractCommandContainer {
    private static final long serialVersionUID = -8636035520396595290L;

    public double getCurrentWin() {
        return currentWin;
    }

    public void setCurrentWin(double currentWin) {
        this.currentWin = currentWin;
    }

    private double currentWin;
}
