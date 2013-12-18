package com.unit7.services.pokerservice.client.engine.transfer;

import java.net.Socket;

public interface RequestListener {
    Response executeRequest(Request request);
    Object waitMessage(Socket sock);
    void sendMessage(Socket sock, Object data);
}
