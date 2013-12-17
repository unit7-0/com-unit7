package com.unit7.services.pokerservice.client.commands.containers;

public interface CommandContainer {
    void setType(CommandContainerType type);
    CommandContainerType getType();
}
