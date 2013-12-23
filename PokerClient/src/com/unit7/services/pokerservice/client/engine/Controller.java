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
import com.unit7.services.pokerservice.client.commands.containers.ErrorCommandContainer;
import com.unit7.services.pokerservice.client.commands.containers.GamersInfoCommandContainer;
import com.unit7.services.pokerservice.client.commands.containers.RequestNameContainer;
import com.unit7.services.pokerservice.client.engine.transfer.PokerRequestListener;
import com.unit7.services.pokerservice.client.engine.transfer.RequestListener;
import com.unit7.services.pokerservice.client.exceptions.ErrorException;
import com.unit7.services.pokerservice.client.model.Card;
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
			Object message = requestListener.waitMessage(model
					.getServerSocket());
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

				for (LightweightGamer gamer : gamers) {
					if (gamer.getCards() != null) {
						model.setGamer(gamer);
						gamer.setMoney(model.getInitialMoney());
						break;
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
			model.getGamer().setBet(
					model.getGamer().getBet() + betCommand.getBet());
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
	private ErrorHandler errorHandler = new ErrorHandlerStub();

	private static final Controller instance = new Controller();

	private static final Logger log = Logger.getLogger(Controller.class);
}
