package com.unit7.services.pokerservice.client.engine.transfer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import com.unit7.services.pokerservice.client.tools.Utils;

public class PokerRequestListener implements RequestListener {
    @Override
    public Response executeRequest(Request request) {
        Socket socket = request.getSocket();
        Object data = request.getData();

        if (log.isDebugEnabled()) {
            log.debug(String.format("[\tExecuting: host: %s, port: %d, data: %s\t]", socket.getInetAddress()
                    .getHostAddress(), socket.getPort(), data));
        }

        sendData(socket, data);

        Response response = new ResponseImpl();
        data = receiveData(socket);
        response.setData(data);

        if (log.isDebugEnabled()) {
            log.debug("[\tExecuting: received data: " + data + "\t]");
        }

        return response;
    }

    @Override
    public Object waitMessage(Socket sock) {
        if (log.isDebugEnabled()) {
            log.debug(String.format("[\tExecuting: host: %s, port: %d\t]", sock.getInetAddress().getHostAddress(),
                    sock.getPort()));
        }

        Object data = receiveData(sock);

        if (log.isDebugEnabled()) {
            log.debug(String.format("[\tExecuting: received data: %s from host: %s:%d\t]", data, sock.getInetAddress()
                    .getHostAddress(), sock.getPort()));
        }

        return data;
    }

    @Override
    public void sendMessage(Socket sock, Object data) {
        sendData(sock, data);
    }

    protected void sendData(Socket socket, Object data) {
        try {
            OutputStream out = socket.getOutputStream();
            byte[] binaryData = Utils.serializeObject(data);
            out.write(binaryData);
            out.write(-1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected Object receiveData(Socket socket) {
        Object result = null;

        if (log.isDebugEnabled()) {
            log.debug(String.format("[\tExecuting: receiving data from host: %s:%d\t]", socket.getInetAddress()
                    .getHostAddress(), socket.getPort()));
        }

        try {
            InputStream in = socket.getInputStream();
            ByteBuffer buffer = ByteBuffer.allocate(1);
            int readed = -1;
            while ((readed = in.read()) != -1) {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("[\tExecuting: readed: %d\t]", readed));
                }
                byte readedByte = (byte) readed;
                if (buffer.position() == buffer.capacity()) {
                    ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() << 1);
                    newBuffer.put(buffer.array());
                    buffer = newBuffer;
                }

                buffer.put(readedByte);
            }

            byte[] data = new byte[buffer.position()];
            buffer.get(data);
            result = Utils.deserializerObject(data);
            
            if (log.isDebugEnabled()) {
                log.debug(String.format("[\tExecuting: data received and deserialized from host: %s:%d, data: %s\t]", socket.getInetAddress().getHostAddress(), socket.getPort(), result));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    private Logger log = Logger.getLogger(PokerRequestListener.class);
}
