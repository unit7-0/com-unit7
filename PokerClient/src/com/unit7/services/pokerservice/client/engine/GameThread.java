/**
 * Date:	22 дек. 2013 г.
 * File:	GameThread.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.engine;

import java.util.List;

import org.apache.log4j.Logger;

import com.unit7.services.pokerservice.client.commands.GamerCardCommand;
import com.unit7.services.pokerservice.client.commands.PrikupCardCommand;
import com.unit7.services.pokerservice.client.commands.containers.CardContainer;
import com.unit7.services.pokerservice.client.commands.containers.CommandContainer;
import com.unit7.services.pokerservice.client.commands.containers.CommandContainerType;
import com.unit7.services.pokerservice.client.commands.containers.RequestBetContainer;
import com.unit7.services.pokerservice.client.commands.containers.RequestBlindContainer;
import com.unit7.services.pokerservice.client.model.Card;
import com.unit7.services.pokerservice.client.resources.Resources;

/**
 * @author unit7
 * 
 */
public class GameThread implements Runnable {
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        while (true) {
            Object message = Controller.getInstance().waitCommand();
            if (log.isDebugEnabled()) {
                log.debug(String.format("[\tReceive message from server: %s\t]", message));
            }

            if (!(message instanceof CommandContainer)) {
                log.error(String.format("[\tReceive not a command container from server\r\n message: [ %s ]\t]",
                        message));
                continue;
            }

            CommandContainer container = (CommandContainer) message;
            CommandContainerType type = container.getType();

            if (log.isDebugEnabled()) {
                log.debug(String.format("[\tContainer type: %s\t]", type.name()));
            }

            if (CommandContainerType.SMALL_BLIND.equals(type)) {
                double val = betProxy.request(Resources.REQUEST_SMALL_BLIND_TITLE);
                RequestBlindContainer cont = (RequestBlindContainer) container;
                cont.setValue(val);
                Controller.getInstance().sendMessage(cont);
            } else if (CommandContainerType.BIG_BLIND.equals(type)) {
                double val = betProxy.request(Resources.REQUEST_BIG_BLIND_TITLE);
                RequestBlindContainer cont = (RequestBlindContainer) container;
                cont.setValue(val);
                Controller.getInstance().sendMessage(cont);
            } else if (CommandContainerType.END_ROUND.equals(type)) {

            } else if (CommandContainerType.REQUEST_BET.equals(type)) {
                double val = betProxy.request(Resources.REQUEST_BET_TITLE);
                RequestBlindContainer cont = (RequestBlindContainer) container;
                cont.setValue(val);
                Controller.getInstance().sendMessage(cont);
            } else if (CommandContainerType.SHOWDOWN.equals(type)) {

            } else if (CommandContainerType.FLOP.equals(type) || CommandContainerType.TURN.equals(type)
                    || CommandContainerType.RIVER.equals(type)) {
                PrikupCardCommand command = new PrikupCardCommand();
                List<Card> cards = ((CardContainer) container).getCards();
                command.setCards(cards);
                command.execute(Controller.getInstance());
                // обновим данные
                dataProxy.request(null);
            } else if (CommandContainerType.GET_CARD.equals(type)) {
                CardContainer cardContainer = (CardContainer) container;
                List<Card> cards = cardContainer.getCards();
                GamerCardCommand command = new GamerCardCommand();
                command.setCards(cards);
                command.setGamerId(cardContainer.getGamerId());
                command.execute(Controller.getInstance());
                dataProxy.request(null);
            } else if (CommandContainerType.ERROR.equals(type)) {
                break;
            }
        }
    }

    public RequestBetProxy getBetProxy() {
        return betProxy;
    }

    public void setBetProxy(RequestBetProxy betProxy) {
        this.betProxy = betProxy;
    }

    public RefreshDataProxy getDataProxy() {
        return dataProxy;
    }

    public void setDataProxy(RefreshDataProxy dataProxy) {
        this.dataProxy = dataProxy;
    }

    public RoundInfoProxy getRoundInfoProxy() {
        return roundInfoProxy;
    }

    public void setRoundInfoProxy(RoundInfoProxy roundInfoProxy) {
        this.roundInfoProxy = roundInfoProxy;
    }

    public EndGameProxy getEndGameProxy() {
        return endGameProxy;
    }

    public void setEndGameProxy(EndGameProxy endGameProxy) {
        this.endGameProxy = endGameProxy;
    }

    public static class Builder {
        public GameThread build() {
            GameThread thread = new GameThread();
            thread.setBetProxy(betProxy);
            thread.setDataProxy(dataProxy);
            thread.setEndGameProxy(endGameProxy);
            thread.setRoundInfoProxy(roundInfoProxy);
            return thread;
        }

        public RequestBetProxy getBetProxy() {
            return betProxy;
        }

        public Builder setBetProxy(RequestBetProxy betProxy) {
            this.betProxy = betProxy;
            return this;
        }

        public Builder setDataProxy(RefreshDataProxy dataProxy) {
            this.dataProxy = dataProxy;
            return this;
        }

        public Builder setRoundInfoProxy(RoundInfoProxy roundInfoProxy) {
            this.roundInfoProxy = roundInfoProxy;
            return this;
        }

        public Builder setEndGameProxy(EndGameProxy endGameProxy) {
            this.endGameProxy = endGameProxy;
            return this;
        }

        private RequestBetProxy betProxy;
        private RefreshDataProxy dataProxy;
        private RoundInfoProxy roundInfoProxy;
        private EndGameProxy endGameProxy;
    }

    private RequestBetProxy betProxy;
    private RefreshDataProxy dataProxy;
    private RoundInfoProxy roundInfoProxy;
    private EndGameProxy endGameProxy;

    private Logger log = Logger.getLogger(GameThread.class);
}
