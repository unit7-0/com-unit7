package com.unit7.services.pokerservice.client.commands.containers;

public class RequestNameContainer extends AbstractCommandContainer {
    private static final long serialVersionUID = 2954099509336375407L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
