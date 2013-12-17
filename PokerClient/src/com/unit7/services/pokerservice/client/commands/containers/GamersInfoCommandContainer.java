/**
 * Date:	17 дек. 2013 г.
 * File:	GamersInfoCommandContainer.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.commands.containers;

import java.util.List;

import com.unit7.services.pokerservice.client.model.LightweightGamer;

/**
 * @author unit7
 *
 */
public class GamersInfoCommandContainer extends AbstractCommandContainer {

    public List<LightweightGamer> getGamers() {
        return gamers;
    }

    public void setGamers(List<LightweightGamer> gamers) {
        this.gamers = gamers;
    }

    private List<LightweightGamer> gamers;
}
