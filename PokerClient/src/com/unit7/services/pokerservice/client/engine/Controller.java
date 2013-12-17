/**
 * Date:	17 дек. 2013 г.
 * File:	Controller.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.engine;

import java.net.Socket;
import java.util.List;

import org.apache.log4j.Logger;
import org.jcp.xml.dsig.internal.dom.ApacheCanonicalizer;

import com.unit7.services.pokerservice.client.commands.Command;
import com.unit7.services.pokerservice.client.commands.CommandType;
import com.unit7.services.pokerservice.client.commands.containers.GamersInfoCommandContainer;
import com.unit7.services.pokerservice.client.commands.containers.RequestNameContainer;
import com.unit7.services.pokerservice.client.engine.transfer.PokerRequestListener;
import com.unit7.services.pokerservice.client.engine.transfer.RequestListener;
import com.unit7.services.pokerservice.client.model.LightweightGamer;
import com.unit7.services.pokerservice.client.model.Model;

/**
 * @author unit7
 * 
 */
public class Controller {
    public static Controller getInstance() {
        return instance;
    }

    public void execute(Command command) {
        CommandType type = command.getType();
        if (CommandType.REQUEST_NAME.equals(type)) {
            Object message = requestListener.waitMessage(model.getServerSocket());
            if (message instanceof RequestNameContainer) {
                RequestNameContainer container = (RequestNameContainer) message;
                String name = requestNameProxy.request(null);
                container.setName(name);
                model.setName(name);
                requestListener.sendMessage(model.getServerSocket(), container);

                if (log.isDebugEnabled()) {
                    log.debug("REQUEST_NAME_COMMAND: name[ " + name + " ]");
                }
            } else {
                System.err.println("receive not a request name container");
                throw new RuntimeException();
            }
        } else if (CommandType.GAMERS_INFO.equals(type)) {
            Object message = requestListener.waitMessage(model.getServerSocket());
            if (message instanceof GamersInfoCommandContainer) {
                GamersInfoCommandContainer container = (GamersInfoCommandContainer) message;
                List<LightweightGamer> gamers = container.getGamers();
                model.setGamers(gamers);

                if (log.isDebugEnabled()) {
                    log.debug("GAMER_INFO_COMMAND: gamers.size[ " + gamers != null ? gamers.size() : "0" + " ]");
                }
            } else {
                System.err.println("receive not a gamers info container");
                throw new RuntimeException();
            }
        } else if (CommandType.INIT_GAME.equals(type)) {

        }
    }

    public void setServerSocket(Socket sock) {
        model.setServerSocket(sock);
    }
    
    public Socket getServerSocket() {
        return model.getServerSocket();
    }
    
    public void setRequestNameProxy(RequestNameProxy proxy) {
        this.requestNameProxy = proxy;
    }

    private Model model = new Model();
    private RequestNameProxy requestNameProxy;
    private RequestListener requestListener = new PokerRequestListener();

    private static final Controller instance = new Controller();

    private static final Logger log = Logger.getLogger(Controller.class);
}
