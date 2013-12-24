package com.unit7.services.pokerservice.engine.framework;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.unit7.services.pokerservice.GameThread;
import com.unit7.services.pokerservice.ServerThread;
import com.unit7.services.pokerservice.client.commands.containers.CardContainer;
import com.unit7.services.pokerservice.client.commands.containers.CommandContainerType;
import com.unit7.services.pokerservice.client.commands.containers.EndRoundCommandContainer;
import com.unit7.services.pokerservice.client.commands.containers.ErrorCommandContainer;
import com.unit7.services.pokerservice.client.commands.containers.GamersInfoCommandContainer;
import com.unit7.services.pokerservice.client.commands.containers.RequestBetContainer;
import com.unit7.services.pokerservice.client.commands.containers.RequestBlindContainer;
import com.unit7.services.pokerservice.client.commands.containers.RequestNameContainer;
import com.unit7.services.pokerservice.client.engine.transfer.PokerRequestListener;
import com.unit7.services.pokerservice.client.engine.transfer.Request;
import com.unit7.services.pokerservice.client.engine.transfer.RequestImpl;
import com.unit7.services.pokerservice.client.engine.transfer.RequestListener;
import com.unit7.services.pokerservice.client.engine.transfer.Response;
import com.unit7.services.pokerservice.client.model.Card;
import com.unit7.services.pokerservice.client.model.CombinationType;
import com.unit7.services.pokerservice.client.model.LightweightGamer;
import com.unit7.services.pokerservice.engine.commands.Command;
import com.unit7.services.pokerservice.engine.commands.CommandType;
import com.unit7.services.pokerservice.engine.commands.EndRoundCommand;
import com.unit7.services.pokerservice.engine.commands.ErrorCommand;
import com.unit7.services.pokerservice.engine.commands.GamerCommand;
import com.unit7.services.pokerservice.engine.commands.RequestNameCommand;
import com.unit7.services.pokerservice.model.PokerGamer;
import com.unit7.services.pokerservice.model.PokerModel;

/**
 * пока большая часть запросов отправляется без подтверждения о получении.
 * Преполагается сделать специальный контейнер, который будет свидетельствовать
 * либо об успешной передаче, либо об ошибке.
 * 
 * @author unit7
 * 
 */
public class Controller {
	public void execute(final Command command) {
		if (command.getCommandType().equals(CommandType.REQUEST_NAME)) {
			if (log.isDebugEnabled()) {
				log.debug("[ Executing: Request name command ]");
			}

			/*
			 * new Thread(new Runnable() {
			 * 
			 * @Override public void run() {
			 */
			Request request = new RequestImpl();
			RequestNameContainer requestContainer = new RequestNameContainer();
			GamerCommand gamerCommand = (RequestNameCommand) command;
			request.setData(requestContainer);
			request.setSocket(gamerCommand.getGamer().getSocket());

			if (log.isDebugEnabled()) {
				log.debug("[ Executing: before execute request ]");
			}

			Response response = requestListener.executeRequest(request);
			Object data = response.getData();

			if (log.isDebugEnabled()) {
				log.debug("[ Executing: after execute request ]");
			}

			try {
				requestContainer = (RequestNameContainer) data;
				String name = requestContainer.getName();

				if (log.isDebugEnabled()) {
					log.debug("[ Executing: received name: " + name + " ]");
				}

				gamerCommand.getGamer().setName(name);
			} catch (Exception e) {
				// TODO handle exception
			}
			/*
			 * } }).start();
			 */

			if (log.isDebugEnabled()) {
				log.debug("[ Executing: Request name executing started ]");
			}
		} else if (CommandType.GAMERS_INFO.equals(command.getCommandType())) {
			if (log.isDebugEnabled()) {
				log.debug(String.format(
						"[\tExecuting: Gamers info command started\t]", null));
			}

			// get all gamers
			List<PokerGamer> gamers = model.getGamers();
			List<LightweightGamer> lightweightGamers = new ArrayList<LightweightGamer>();
			for (PokerGamer pokerGamer : gamers) {
				LightweightGamer gamer = new LightweightGamer();
				gamer.setName(pokerGamer.getName());
				gamer.setId(pokerGamer.getId());
				lightweightGamers.add(gamer);
			}

			GamersInfoCommandContainer container = new GamersInfoCommandContainer();
			container.setGamers(lightweightGamers);

			// start params
			container.setBigBlind(model.getBetValue());
			container.setInitialMoney(model.getInitialMoney());

			// send to each gamer
			for (int i = 0; i < gamers.size(); ++i) {
				PokerGamer pokerGamer = gamers.get(i);
				LightweightGamer gamer = lightweightGamers.get(i);
				try {
					gamer.setCards(pokerGamer.getCards());
					requestListener.sendMessage(pokerGamer.getSocket(),
							container);

					if (log.isDebugEnabled()) {
						log.debug(String
								.format("[\tExecuting: received gamers info response from gamer: %s\t]",
										gamer.getName()));
					}
				} finally {
					gamer.setCards(null);
				}
			}
		} else if (CommandType.SMALL_BLIND.equals(command.getCommandType())
				|| CommandType.BIG_BLIND.equals(command.getCommandType())) {

			Request request = new RequestImpl();
			RequestBlindContainer container = new RequestBlindContainer();
			container.setType(CommandType.SMALL_BLIND.equals(command
					.getCommandType()) ? CommandContainerType.SMALL_BLIND
					: CommandContainerType.BIG_BLIND);
			request.setData(container);
			GamerCommand gamerCommand = (GamerCommand) command;
			request.setSocket(gamerCommand.getGamer().getSocket());

			Response response = requestListener.executeRequest(request);
			Object data = response.getData();

			if (log.isDebugEnabled()) {
				boolean isBlind = data instanceof RequestBetContainer;
				log.debug(String
						.format("[\tReceived container object: %s, is RequestBetContainer: %s, type: %s\t]",
								data, isBlind,
								isBlind ? ((RequestBetContainer) data)
										.getType().name() : "none"));
			}

			try {
				RequestBetContainer betCont = (RequestBetContainer) data;
				// check container is blind
				double blind = betCont.getBet();
				PokerGamer gamer = gamerCommand.getGamer();
				gamer.setBet(blind);
				model.addToBank(blind);
			} catch (Exception e) {
				// TODO handle exception
			}
		} else if (CommandType.GET_CARD.equals(command.getCommandType())) {
			CardContainer container = generateCardContainer(2);
			GamerCommand gamerCommand = (GamerCommand) command;
			PokerGamer gamer = gamerCommand.getGamer();
			container.setType(CommandContainerType.GET_CARD);

			container.setGamerId(gamer.getId());
			requestListener.sendMessage(gamer.getSocket(), container);

			Iterator<Card> it = container.getCards().iterator();
			while (it.hasNext()) {
				gamer.addCard(it.next());
			}

			// nothing todo
		} else if (CommandType.FLOP.equals(command.getCommandType())) {
			CardContainer container = new CardContainer();
			List<Card> cards = new ArrayList<Card>();

			Card[] prikup = model.getPrikup();
			for (int i = 0; i < 3; ++i) {
				cards.add(prikup[i]);
			}

			container.setCards(cards);
			container.setType(CommandContainerType.FLOP);

			List<PokerGamer> gamers = model.getGamers();
			for (int i = 0; i < gamers.size(); ++i) {
				PokerGamer gamer = gamers.get(i);
				requestListener.sendMessage(gamer.getSocket(), container);
			}
			// nothing todo
		} else if (CommandType.TURN.equals(command.getCommandType())) {
			CardContainer container = new CardContainer();
			List<Card> cards = new ArrayList<Card>();

			Card[] prikup = model.getPrikup();
			cards.add(prikup[3]);

			container.setCards(cards);
			container.setType(CommandContainerType.TURN);

			List<PokerGamer> gamers = model.getGamers();
			for (int i = 0; i < gamers.size(); ++i) {
				PokerGamer gamer = gamers.get(i);
				requestListener.sendMessage(gamer.getSocket(), container);
			}

			// nothing todo
		} else if (CommandType.RIVER.equals(command.getCommandType())) {
			CardContainer container = new CardContainer();
			List<Card> cards = new ArrayList<Card>();

			Card[] prikup = model.getPrikup();
			cards.add(prikup[4]);

			container.setCards(cards);
			container.setType(CommandContainerType.RIVER);

			List<PokerGamer> gamers = model.getGamers();
			for (int i = 0; i < gamers.size(); ++i) {
				PokerGamer gamer = gamers.get(i);
				requestListener.sendMessage(gamer.getSocket(), container);
			}

			// nothing todo
		} else if (CommandType.BET.equals(command.getCommandType())) {
			GamerCommand gamerCommand = (GamerCommand) command;
			Request request = new RequestImpl();
			RequestBetContainer container = new RequestBetContainer();
			container.setType(CommandContainerType.REQUEST_BET);

			request.setData(container);
			request.setSocket(gamerCommand.getGamer().getSocket());
			Response response = requestListener.executeRequest(request);

			Object data = response.getData();
			PokerGamer gamer = gamerCommand.getGamer();

			try {
				container = (RequestBetContainer) data;
				CommandContainerType type = container.getType();
				if (CommandContainerType.CALL.equals(type)) {
					double val = container.getBet();
					gamer.setBet(gamer.getBet() + val);
					model.addToBank(val);
					gamerCommand.setCommandType(CommandType.CALL);
				} else if (CommandContainerType.RAISE.equals(type)) {
					double bet = container.getBet();
					// model.setBetValue(bet);
					model.addToBank(bet);
					gamer.setBet(gamer.getBet() + bet);
					gamerCommand.setCommandType(CommandType.RAISE);
				} else if (CommandContainerType.FOLD.equals(type)) {
					gamer.setInGame(false);
					gamerCommand.setCommandType(CommandType.FOLD);
				} else if (CommandContainerType.CHECK.equals(type)) {
					// TODO
				}

				Map<PokerGamer, GamerCommand> comm = new HashMap<PokerGamer, GamerCommand>();
				comm.put(gamer, gamerCommand);

				for (int i = 0; i < listeners.size(); ++i) {
					EventListener listener = listeners.get(i);
					listener.update(comm);
				}
			} catch (Exception e) {
				// TODO handle exception
			}
		} else if (CommandType.END_ROUND.equals(command.getCommandType())) {
			// TODO refactor command
			
			EndRoundCommandContainer container = new EndRoundCommandContainer();
			container.setType(CommandContainerType.END_ROUND);
			EndRoundCommand endRound = (EndRoundCommand) command;
			
			// сформировать контейнер
			Map<Integer, CombinationType> types = endRound.getCombinations();
			container.setCombinations(types);
			
			// послать комбинации и выигрыш всем игрокам
			
			List<PokerGamer> winners = endRound.getWinners();
			double win = model.getBank() / winners.size();
			for (PokerGamer gamer : model.getGamers()) {
				if (winners.contains(gamer)) {
					gamer.addMoney(win);
					container.setMoney(gamer.getMoney());
					gamer.setBet(0);
				} else {
					container.setMoney(0);
				}
				
				requestListener.sendMessage(gamer.getSocket(), container);
			}
			
			model.resetBank();

			// снять деньги - ставку
			for (PokerGamer gamer : model.getGamers()) {
				gamer.setMoney(gamer.getMoney() - gamer.getBet());
				gamer.setBet(0);
			}
		} else if (CommandType.ERROR.equals(command.getCommandType())) {
			if (log.isDebugEnabled()) {
				log.debug("[\tExecuting error command\t]");
			}

			ErrorCommand errorCommand = (ErrorCommand) command;
			ErrorCommandContainer container = new ErrorCommandContainer();
			container.setMessage(errorCommand.getMessage());

			requestListener.sendMessage(errorCommand.getSocket(), container);

			if (log.isDebugEnabled()) {
				log.debug("[\tError command executed\t]");
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
		new Thread(new GameThread(clients)).start();
	}

	public List<PokerGamer> initGame(List<Socket> clients) {
		model = new PokerModel(clients);
		return model.getGamers();
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

	private static final Logger log = Logger.getLogger(Controller.class);
}
