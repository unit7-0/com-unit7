package com.unit7.services.pokerservice.client.commands;

public interface CommandContainer {
    void setType(CommandContainerType type);
    CommandContainerType getType();
}
