package com.unit7.services.pokerservice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * Основной поток сервера, сюда приходят конекшены
 * 
 * @author Zajcev
 * 
 */
public class ServerThread implements Runnable {
    public ServerThread(int port) {
        this.port = port;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error(e.getLocalizedMessage());
            }

            e.printStackTrace();
        }

        if (log.isDebugEnabled()) {
            log.debug("server socket up");
        }
    }

    @Override
    public void run() {
        Socket clientSocket = null;
        try {
            Semaphore semaphore = new Semaphore(1);
            semaphore.acquire();
            while ((clientSocket = socket.accept()) != null) {
                if (log.isDebugEnabled()) {
                    log.debug("[\tListening: client connected: " + clientSocket.getInetAddress().getHostAddress()
                            + " : " + clientSocket.getPort() + "\t]");
                }

                semaphore.release();
                WaitingThread thread = new WaitingThread(socket, clientSocket, semaphore);
                new Thread(thread).start();

                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                semaphore.acquire();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private ServerSocket socket;
    private int port;

    private static final Logger log = Logger.getLogger(ServerThread.class);
}
