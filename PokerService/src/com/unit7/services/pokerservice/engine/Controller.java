package com.unit7.services.pokerservice.engine;

import com.unit7.services.pokerservice.PokerRequestListener;
import com.unit7.services.pokerservice.Request;
import com.unit7.services.pokerservice.RequestImpl;
import com.unit7.services.pokerservice.RequestListener;
import com.unit7.services.pokerservice.Response;
import com.unit7.services.pokerservice.client.commands.RequestNameContainer;
import com.unit7.services.pokerservice.engine.commands.Command;
import com.unit7.services.pokerservice.engine.commands.CommandType;
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
        }
    }

    public void addListener(EventListener listener) {

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
}
