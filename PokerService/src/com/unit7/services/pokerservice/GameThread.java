package com.unit7.services.pokerservice;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.unit7.services.pokerservice.engine.PokerGameInterface;
import com.unit7.services.pokerservice.engine.PokerGameInterfaceImpl;
import com.unit7.services.pokerservice.engine.framework.Controller;
import com.unit7.services.pokerservice.model.PokerGamer;

/**
 * Обрабатывает внешние соединения от пользователей, передает запросы
 * пользователей ReqeustListener'у и возвращает ответ
 * 
 * @author Zajcev
 * 
 */
public class GameThread implements Runnable {
    public GameThread(List<Socket> clients) {        
    	this.clients = clients;
    }
    
    @Override
    public void run() {
    	List<PokerGamer> gamers = Controller.getInstance().initGame(clients);
        gameInterface = new PokerGameInterfaceImpl(gamers);
        gameInterface.game();
    }

    private PokerGameInterface gameInterface;
    private List<Socket> clients;
    
    private static final Logger log = Logger.getLogger(GameThread.class);
}
