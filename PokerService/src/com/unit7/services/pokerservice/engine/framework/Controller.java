package com.unit7.services.pokerservice.engine.framework;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.unit7.services.pokerservice.GameThread;
import com.unit7.services.pokerservice.PokerRequestListener;
import com.unit7.services.pokerservice.Request;
import com.unit7.services.pokerservice.RequestImpl;
import com.unit7.services.pokerservice.RequestListener;
import com.unit7.services.pokerservice.Response;
import com.unit7.services.pokerservice.ServerThread;
import com.unit7.services.pokerservice.client.commands.CardContainer;
import com.unit7.services.pokerservice.client.commands.CommandContainer;
import com.unit7.services.pokerservice.client.commands.CommandContainerType;
import com.unit7.services.pokerservice.client.commands.EndRoundCommandContainer;
import com.unit7.services.pokerservice.client.commands.RequestBetContainer;
import com.unit7.services.pokerservice.client.commands.RequestBlindContainer;
import com.unit7.services.pokerservice.client.commands.RequestNameContainer;
import com.unit7.services.pokerservice.client.commands.ShowdownCommandContainer;
import com.unit7.services.pokerservice.client.model.Card;
import com.unit7.services.pokerservice.engine.commands.BetCommand;
import com.unit7.services.pokerservice.engine.commands.Command;
import com.unit7.services.pokerservice.engine.commands.CommandType;
import com.unit7.services.pokerservice.engine.commands.EndRoundCommand;
import com.unit7.services.pokerservice.engine.commands.GamerCommand;
import com.unit7.services.pokerservice.engine.commands.RequestNameCommand;
import com.unit7.services.pokerservice.model.PokerGamer;
import com.unit7.services.pokerservice.model.PokerModel;

public class Controller {
    public void execute(final Command command) {
        if (command.getCommandType().equals(CommandType.REQUEST_NAME)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Request request = new RequestImpl();
                    RequestNameContainer requestContainer = new RequestNameContainer();
                    GamerCommand gamerCommand = (RequestNameCommand) command;
                    request.setData(requestContainer);
                    request.setSocket(gamerCommand.getGamer().getSocket());

                    Response response = requestListener.executeRequest(request);
                    Object data = response.getData();

                    try {
                        requestContainer = (RequestNameContainer) data;
                        String name = requestContainer.getName();
                        gamerCommand.getGamer().setName(name);
                    } catch (Exception e) {
                        // TODO handle exception
                    }
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
            GamerCommand gamerCommand = (GamerCommand) command;
            request.setSocket(gamerCommand.getGamer().getSocket());

            Response response = requestListener.executeRequest(request);
            Object data = response.getData();

            try {
                container = (RequestBlindContainer) data;
                double blind = container.getValue();
                PokerGamer gamer = gamerCommand.getGamer();
                gamer.setBet(blind);
                model.addToBank(blind);
            } catch (Exception e) {
                // TODO handle exception
            }
        } else if (CommandType.GET_CARD.equals(command.getCommandType())) {
            Request request = new RequestImpl();
            CardContainer container = generateCardContainer(2);
            GamerCommand gamerCommand = (GamerCommand) command;
            container.setType(CommandContainerType.GET_CARD);

            request.setData(container);
            request.setSocket(gamerCommand.getGamer().getSocket());

            requestListener.executeRequest(request);

            PokerGamer gamer = gamerCommand.getGamer();
            Iterator<Card> it = container.getCards().iterator();
            while (it.hasNext()) {
                gamer.addCard(it.next());
            }

            // nothing todo
        } else if (CommandType.FLOP.equals(command.getCommandType())) {
            Request request = new RequestImpl();
            CardContainer container = generateCardContainer(3);
            container.setType(CommandContainerType.FLOP);

            request.setData(container);
            request.setSocket(((GamerCommand) command).getGamer().getSocket());

            Response response = requestListener.executeRequest(request);

            // nothing todo
        } else if (CommandType.TURN.equals(command.getCommandType())) {
            Request request = new RequestImpl();
            CardContainer container = generateCardContainer(1);
            container.setType(CommandContainerType.TURN);

            request.setData(container);
            request.setSocket(((GamerCommand) command).getGamer().getSocket());

            Response response = requestListener.executeRequest(request);
            Object data = response.getData();

            // nothing todo
        } else if (CommandType.RIVER.equals(command.getCommandType())) {
            Request request = new RequestImpl();
            CardContainer container = generateCardContainer(1);
            container.setType(CommandContainerType.RIVER);

            request.setData(container);
            request.setSocket(((GamerCommand) command).getGamer().getSocket());

            Response response = requestListener.executeRequest(request);
            Object data = response.getData();

            // nothing todo
        } else if (CommandType.BET.equals(command.getCommandType())) {
            GamerCommand gamerCommand = (GamerCommand) command;
            Request request = new RequestImpl();
            RequestBetContainer container = new RequestBetContainer();

            request.setData(container);
            request.setSocket(gamerCommand.getGamer().getSocket());
            Response response = requestListener.executeRequest(request);

            Object data = response.getData();
            PokerGamer gamer = gamerCommand.getGamer();
            
            try {
                container = (RequestBetContainer) data;
                CommandContainerType type = container.getType();
                if (CommandContainerType.CALL.equals(type)) {
                    double bet = model.getBetValue();
                    gamer.setBet(gamer.getBet() + bet);
                    model.addToBank(bet);
                } else if (CommandContainerType.RAISE.equals(type)) {
                    double bet = model.getBetValue();
                    bet *= 2;
                    model.setBetValue(bet);
                    gamer.setBet(gamer.getBet() + bet);
                } else if (CommandContainerType.FOLD.equals(type)) {
                    gamer.setInGame(false);
                } else if (CommandContainerType.CHECK.equals(type)) {
                	// TODO
                }
            } catch (Exception e) {
                // TODO handle exception
            }
        } else if (CommandType.SHOWDOWN.equals(command.getCommandType())) {
            CommandContainer container = new ShowdownCommandContainer();
            container.setType(CommandContainerType.SHOWDOWN);
            
            Request request = new RequestImpl();
            request.setData(container);
            
            for (PokerGamer gamer : model.getGamers()) {
                request.setSocket(gamer.getSocket());
                Response response = requestListener.executeRequest(request);
                // TODO log response
            }
            
        } else if (CommandType.END_ROUND.equals(command.getCommandType())) {
            EndRoundCommandContainer container = new EndRoundCommandContainer();
            container.setType(CommandContainerType.END_ROUND);
            
            Request request = new RequestImpl();
            List<PokerGamer> winners = ((EndRoundCommand) command).getGamers();
            double win = model.getBank() / winners.size();
            model.resetBank();	
            for (PokerGamer winner : winners) {
                winner.addMoney(win);
                container.setCurrentWin(winner.getMoney());
                request.setSocket(winner.getSocket());
                Response response = requestListener.executeRequest(request);
                // TODO log response
            }
        }
    }

    public void upServer(int port) {
    	new Thread(new ServerThread(port)).start();
    }
    
    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    /**
     * генерирует контейнер с картами для указанного количества TODO добавить
     * проверку доступности карт
     * 
     * @param count
     * @return
     */
    private CardContainer generateCardContainer(int count) {
        Request request = new RequestImpl();
        CardContainer container = new CardContainer();
        List<Card> cards = new ArrayList<Card>();
        Card card = null;

        for (int i = 0; i < count; ++i) {
            card = Card.getRandCard(model.getDeck());
            model.addCard(card);

            cards.add(card);
        }

        container.setCards(cards);
        return container;
    }
    
    public void createNewGame(List<Socket> clients) {
    	model = new PokerModel(clients);
    	new Thread(new GameThread(model.getGamers())).start();
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

    private PokerModel model;
    private RequestListener requestListener = new PokerRequestListener();
    private List<EventListener> listeners = new ArrayList<EventListener>();
}
