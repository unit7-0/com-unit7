package com.unit7.services.pokerservice.client.commands.containers;

import java.io.Serializable;

public interface CommandContainer extends Serializable {
    void setType(CommandContainerType type);
    CommandContainerType getType();
}
