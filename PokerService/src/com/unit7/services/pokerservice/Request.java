package com.unit7.services.pokerservice;

import java.net.Socket;

public interface Request {
    void setSocket(Socket socket);

    Socket getSocket();

    void setData(Object data);

    Object getData();
}
