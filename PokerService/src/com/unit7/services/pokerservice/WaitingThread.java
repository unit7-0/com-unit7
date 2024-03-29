package com.unit7.services.pokerservice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

import com.unit7.services.pokerservice.engine.commands.ErrorCommand;
import com.unit7.services.pokerservice.engine.framework.Controller;
import com.unit7.services.pokerservice.res.Resources;

/**
 * Поток ожидает новых подключений для создания игры, если вышел таймаут
 * подключения берется доступное количество игроков и начинаетя игра, если их
 * меньше возвращается результат ошибки. Если подключилось уже максимальное
 * количество игроков - игра начинается.
 * 
 * @author Zajcev
 * 
 */
public class WaitingThread implements Runnable {
    public WaitingThread(ServerSocket socket, Socket clientSocket, Semaphore semaphore) {
        this.socket = socket;
        this.semaphore = semaphore;
        this.clients.add(clientSocket);
        
        if (log.isDebugEnabled()) {
            log.debug(String.format("[\tExecuting: before semaphore.acquire\t]", null));
        }
        
        try {
            semaphore.acquire();
            if (log.isDebugEnabled()) {
                log.debug(String.format("[\tExecuting: semaphore acquired\t]", null));
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Socket clientSocket = null;
        try {
            socket.setSoTimeout(connectionTimeOut);
            try {
                if (log.isDebugEnabled()) {
                    log.debug("[\tListening: start\t]");
                }

                while ((clientSocket = socket.accept()) != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("[\tListening: client connected: " + clientSocket.getInetAddress().getHostAddress()
                                + " : " + clientSocket.getPort() + "\t]");
                    }

                    clients.add(clientSocket);

                    // TODO добавить константу
                    if (clients.size() > 9) {
                        Controller.getInstance().createNewGame(clients);
                        break;
                    }
                }
            } catch (SocketTimeoutException ex) {
                ex.printStackTrace();
                if (clients.size() < 2) {
                    if (log.isDebugEnabled()) {
                        log.debug("[\tListening: timeout. connected clients < 2\t]");
                    }
                    
                    ErrorCommand command = new ErrorCommand();
                    command.setSocket(clients.get(0));
                    command.setMessage(Resources.NOT_ENOUGH_FOR_GAME);
                    command.execute(Controller.getInstance());
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("[\tListening: new game started, clients.size=" + clients.size() + "\t]");
                    }
                    
                    Controller.getInstance().createNewGame(clients);
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                socket.setSoTimeout(0);
            } catch (SocketException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            semaphore.release();
        }
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    private int connectionTimeOut = 15000;
    private ServerSocket socket;
    private Semaphore semaphore;
    private List<Socket> clients = new ArrayList<Socket>();

    private Logger log = Logger.getLogger(WaitingThread.class);
}
