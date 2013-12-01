package com.unit7.services.pokerservice.model;

import java.net.Socket;

public interface Net {
    void setSocket(Socket socket);

    Socket getSocket();
}
