package com.unit7.services.pokerservice.client.engine.transfer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import com.unit7.services.pokerservice.client.tools.Utils;

public class PokerRequestListener implements RequestListener {
    @Override
    public Response executeRequest(Request request) {
        Socket socket = request.getSocket();
        Object data = request.getData();
        sendData(socket, data);
        
        Response response = new ResponseImpl();
        data = receiveData(socket);
        response.setData(data);
        
        return response;
    }
    
    @Override
    public Object waitMessage(Socket sock) {
        Object data = receiveData(sock);
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
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    protected Object receiveData(Socket socket) {
        Object result = null;
        
        try {
            InputStream in = socket.getInputStream();
            ByteBuffer buffer = ByteBuffer.allocate(1);
            int readed = -1;
            while ((readed = in.read()) != -1) {
                byte readedByte = (byte) readed;
                if (buffer.position() == buffer.capacity()) {
                    ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() << 1);
                    newBuffer.put(buffer.array());
                    buffer = newBuffer;
                }
                
                buffer.put(readedByte);
            }
            
            byte[] data = buffer.array();
            result = Utils.deserializerObject(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return result;
    }
}
