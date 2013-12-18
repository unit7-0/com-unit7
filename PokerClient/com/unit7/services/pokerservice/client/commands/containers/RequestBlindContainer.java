package com.unit7.services.pokerservice.client.commands.containers;

public class RequestBlindContainer extends AbstractCommandContainer {
    private static final long serialVersionUID = -3047449276361395219L;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    private double value;
}
