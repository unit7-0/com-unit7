package com.unit7.services.pokerservice.client.commands;

public class RequestNameContainer extends AbstractCommandContainer {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
