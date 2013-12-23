/**
 * Date:	22 дек. 2013 г.
 * File:	GamerCardCommand.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.commands;

import java.util.List;

import com.unit7.services.pokerservice.client.model.Card;

/**
 * @author unit7
 *
 */
public class GamerCardCommand extends SimpleCommand {
    public GamerCardCommand() {
        setType(CommandType.GAMER_CARD);
    }
    
    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    private List<Card> cards;
}
