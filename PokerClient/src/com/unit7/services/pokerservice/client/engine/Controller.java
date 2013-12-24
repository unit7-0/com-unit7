/**
 * Date:	17 дек. 2013 г.
 * File:	Controller.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.engine;

import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.unit7.services.pokerservice.client.commands.BetCommand;
import com.unit7.services.pokerservice.client.commands.Command;
import com.unit7.services.pokerservice.client.commands.CommandType;
import com.unit7.services.pokerservice.client.commands.GamerCardCommand;
import com.unit7.services.pokerservice.client.commands.PrikupCardCommand;
import com.unit7.services.pokerservice.client.commands.containers.CommandContainerType;
import com.unit7.services.pokerservice.client.commands.containers.ErrorCommandContainer;
import com.unit7.services.pokerservice.client.commands.containers.GamersInfoCommandContainer;
import com.unit7.services.pokerservice.client.commands.containers.RequestBetContainer;
import com.unit7.services.pokerservice.client.commands.containers.RequestNameContainer;
import com.unit7.services.pokerservice.client.engine.transfer.PokerRequestListener;
import com.unit7.services.pokerservice.client.engine.transfer.RequestListener;
import com.unit7.services.pokerservice.client.exceptions.ErrorException;
import com.unit7.services.pokerservice.client.model.Card;
import com.unit7.services.pokerservice.client.model.LightweightGamer;
import com.unit7.services.pokerservice.client.model.Model;
import com.unit7.services.pokerservice.client.resources.Resources;

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
			Object message = requestListener.waitMessage(model
					.getServerSocket());
			if (message instanceof RequestNameContainer) {
				RequestNameContainer container = (RequestNameContainer) message;
				String name = "asd";//requestNameProxy.request(null);
				container.setName(name);
				model.setName(name);
				requestListener.sendMessage(model.getServerSocket(), container);

				if (log.isDebugEnabled()) {
					log.debug("REQUEST_NAME_COMMAND: name[ " + name + " ]");
				}
			} else {
				log.error("receive not a request name container");
				errorHandler.handleError(message);
			}
		} else if (CommandType.GAMERS_INFO.equals(type)) {
			// выполняется при старте программы, инциирует модель
			Object message = requestListener.waitMessage(model
					.getServerSocket());
			if (message instanceof GamersInfoCommandContainer) {
				GamersInfoCommandContainer container = (GamersInfoCommandContainer) message;
				List<LightweightGamer> gamers = container.getGamers();
				model.setGamers(gamers);
				model.setInitialMoney(container.getInitialMoney());
				model.setBigBlind(container.getBigBlind());
				model.setMaxBet(model.getBigBlind());

				for (LightweightGamer gamer : gamers) {
					gamer.setMoney(model.getInitialMoney());
					if (gamer.getCards() != null) {
						model.setGamer(gamer);
					}
				}

				if (log.isDebugEnabled()) {
					log.debug("GAMER_INFO_COMMAND: gamers.size[ " + gamers != null ? gamers
							.size() : "0" + " ]");
				}
			} else {
				log.error("receive not a gamers info container");
				errorHandler.handleError(message);
			}
		} else if (CommandType.INIT_GAME.equals(type)) {
			// now nothing to do
		} else if (CommandType.GAMER_CARD.equals(type)) {
			List<Card> cards = ((GamerCardCommand) command).getCards();

			LightweightGamer gamer = model.getGamer();
			gamer.setCards(cards);
			gamer.setBet(0);
			// если игрок проиграется до последней копейки - для него игра
			// окончена, но будет предложено получить начальную сумму заново,
			// таким образом в этой части кода money никогда != 0, если игрок в
			// игре
			if (gamer.getMoney() == 0)
				gamer.setMoney(model.getInitialMoney());
		} else if (CommandType.PRIKUP_CARDS.equals(type)) {
			PrikupCardCommand prikupCommand = (PrikupCardCommand) command;
			List<Card> cards = prikupCommand.getCards();
			if (model.getPrikup() != null)
				cards.addAll(model.getPrikup());
			model.setPrikup(cards);
		} else if (CommandType.BET.equals(type)) {
			BetCommand betCommand = (BetCommand) command;
			CommandContainerType containerType = betCommand.getBetType();
			RequestBetContainer container = new RequestBetContainer();
			container.setType(containerType);

			LightweightGamer gamer = model.getGamer();
			double bet = gamer.getBet();
			double money = gamer.getMoney();
			double maxBet = model.getMaxBet();

			switch (containerType) {
			case CALL:
				double to = maxBet - bet;
				if (to > money) {
					// TODO refactor
					errorHandler.handleException(new RuntimeException(Resources.MONEY_NOT_ENOUGH));
				} else if (to != 0) {
					gamer.setBet(maxBet);
					gamer.setMoney(money - to);
					container.setBet(to);
				} else {
					errorHandler.handleException(new RuntimeException(Resources.WRONG_COMMAND));
				}
				break;
			case RAISE:
				to = maxBet * 2 - bet;
				if (to > money) {
					// TODO refactor
					errorHandler.handleException(new RuntimeException(Resources.MONEY_NOT_ENOUGH));
				} else if (to != 0) {
					gamer.setBet(maxBet);
					gamer.setMoney(money - to);
					container.setBet(to);
				} else {
					errorHandler.handleException(new RuntimeException(Resources.WRONG_COMMAND));
				}
				break;
			case SMALL_BLIND:
				gamer.setBet(model.getBigBlind() / 2);
				gamer.setMoney(gamer.getMoney() - gamer.getBet());
				container.setBet(gamer.getBet());
				break;
			case BIG_BLIND:
				gamer.setBet(model.getBigBlind());
				gamer.setMoney(gamer.getMoney() - gamer.getBet());
				container.setBet(gamer.getBet());
				break;
			case FOLD:
				gamer.setInGame(false);
				break;
			case CHECK:
				break;
			default:
				throw new RuntimeException();
			}

			sendMessage(container);
		} else if (CommandType.END_ROUND.equals(type)) {
			model.setMaxBet(model.getBigBlind());
			// возвращаем в игру fold'ов
			for (LightweightGamer gamer : model.getGamers()) {
				if (gamer.getMoney() > 0) { 
					gamer.setInGame(true);
					gamer.setBet(0);
				}
			}
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

	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}

	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	public List<LightweightGamer> getGamers() {
		return model.getGamers();
	}

	public Object waitCommand() {
		return requestListener.waitMessage(model.getServerSocket());
	}

	public void sendMessage(Object mess) {
		requestListener.sendMessage(model.getServerSocket(), mess);
	}

	public List<Card> getPrikup() {
		return model.getPrikup();
	}

	private Model model = new Model();
	private RequestNameProxy requestNameProxy;
	private RequestListener requestListener = new PokerRequestListener();
	private ErrorHandler errorHandler = new GUIErrorHandler();

	private static final Controller instance = new Controller();

	private static final Logger log = Logger.getLogger(Controller.class);
}
