package com.unit7.services.pokerservice;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.unit7.services.pokerservice.engine.PokerGameInterface;
import com.unit7.services.pokerservice.engine.PokerGameInterfaceImpl;
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
        List<PokerGamer> gamers = new ArrayList<PokerGamer>();
        for (Socket sock : clients) {
            PokerGamer gamer = new PokerGamer();
            gamer.setSocket(sock);
            gamers.add(gamer);
        }
        
        gameInterface = new PokerGameInterfaceImpl(gamers);
    }
    
    @Override
    public void run() {
        gameInterface.game();
    }

    private PokerGameInterface gameInterface;
    
    private static final Logger log = Logger.getLogger(GameThread.class);
}
