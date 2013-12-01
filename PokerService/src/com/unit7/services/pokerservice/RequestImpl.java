package com.unit7.services.pokerservice;

import java.net.Socket;

public class RequestImpl implements Request {
    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    private Socket socket;
    private Object data;
}
