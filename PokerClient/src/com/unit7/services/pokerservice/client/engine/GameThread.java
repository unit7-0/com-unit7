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
            if (!(message instanceof CommandContainer)) {
                log.error(String.format("[\tReceive not a command container from server\r\n message: [ %s ]\t]",
                        message));
                continue;
            }

            CommandContainer container = (CommandContainer) message;
            CommandContainerType type = container.getType();
            if (CommandContainerType.SMALL_BLIND.equals(type)) {
                betProxy.request(Resources.REQUEST_SMALL_BLIND_TITLE);
            } else if (CommandContainerType.BIG_BLIND.equals(type)) {
                betProxy.request(Resources.REQUEST_BIG_BLIND_TITLE);
            } else if (CommandContainerType.END_ROUND.equals(type)) {

            } else if (CommandContainerType.REQUEST_BET.equals(type)) {
                betProxy.request(Resources.REQUEST_BET_TITLE);
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

    private RequestBetProxy betProxy;
    private RefreshDataProxy dataProxy;
    private RoundInfoProxy roundInfoProxy;
    private EndGameProxy endGameProxy;

    private Logger log = Logger.getLogger(GameThread.class);
}
