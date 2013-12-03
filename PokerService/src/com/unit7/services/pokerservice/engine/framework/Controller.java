package com.unit7.services.pokerservice.engine.framework;

import java.util.ArrayList;
import java.util.List;

import com.unit7.services.pokerservice.PokerRequestListener;
import com.unit7.services.pokerservice.Request;
import com.unit7.services.pokerservice.RequestImpl;
import com.unit7.services.pokerservice.RequestListener;
import com.unit7.services.pokerservice.Response;
import com.unit7.services.pokerservice.client.commands.CommandContainerType;
import com.unit7.services.pokerservice.client.commands.RequestBlindContainer;
import com.unit7.services.pokerservice.client.commands.RequestNameContainer;
import com.unit7.services.pokerservice.engine.commands.Command;
import com.unit7.services.pokerservice.engine.commands.CommandType;
import com.unit7.services.pokerservice.engine.commands.GamerCommand;
import com.unit7.services.pokerservice.engine.commands.RequestNameCommand;

public class Controller {
    public void execute(final Command command) {
        if (command.getCommandType().equals(CommandType.REQUEST_NAME)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Request request = new RequestImpl();
                    RequestNameContainer requestContainer = new RequestNameContainer();
                    request.setData(requestContainer);
                    request.setSocket(((RequestNameCommand) command).getGamer().getSocket());

                    Response response = requestListener.executeRequest(request);
                    Object data = response.getData();

                    // TODO try to cast
                }
            }).start();
        } else if (CommandType.SMALL_BLIND.equals(command.getCommandType())
                || CommandType.BIG_BLIND.equals(command.getCommandType())) {
            Request request = new RequestImpl();
            RequestBlindContainer container = new RequestBlindContainer();
            container
                    .setType(CommandType.SMALL_BLIND.equals(command.getCommandType()) ? CommandContainerType.SMALL_BLIND
                            : CommandContainerType.BIG_BLIND);
            request.setData(container);
            request.setSocket(((GamerCommand) command).getGamer().getSocket());

            Response response = requestListener.executeRequest(request);
            Object data = response.getData();

            // TODO try to cast
        } else if (CommandType.GET_CARD.equals(command.getCommandType())) {
            
        }
    }

    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    /*
     * TODO верно оформить
     */
    public void createNewGame() {

    }

    public static Controller getInstance() {
        Controller controller = instance.get();
        if (controller == null) {
            controller = new Controller();
            instance.set(controller);
        }

        return controller;
    }

    private static final ThreadLocal<Controller> instance = new ThreadLocal<Controller>();

    static {
        instance.set(new Controller());
    }

    private RequestListener requestListener = new PokerRequestListener();
    private List<EventListener> listeners = new ArrayList<EventListener>();
}
