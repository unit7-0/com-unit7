/**
 * Date:	22 дек. 2013 г.
 * File:	CardCommand.java
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
public class PrikupCardCommand extends SimpleCommand {
    public PrikupCardCommand() {
        setType(CommandType.PRIKUP_CARDS);
    }
    
    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    private List<Card> cards;
}
