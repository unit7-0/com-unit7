package com.unit7.services.pokerservice.client.commands.containers;

public class RequestBlindContainer extends AbstractCommandContainer {
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    private double value;
}
