package com.unit7.services.pokerservice.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.unit7.services.pokerservice.client.model.Card;
import com.unit7.services.pokerservice.client.model.CombinationType;
import com.unit7.services.pokerservice.client.tools.Utils;
import com.unit7.services.pokerservice.engine.commands.BetCommand;
import com.unit7.services.pokerservice.engine.commands.BigBlindCommand;
import com.unit7.services.pokerservice.engine.commands.Command;
import com.unit7.services.pokerservice.engine.commands.CommandType;
import com.unit7.services.pokerservice.engine.commands.EndRoundCommand;
import com.unit7.services.pokerservice.engine.commands.FlopCommand;
import com.unit7.services.pokerservice.engine.commands.GamerCommand;
import com.unit7.services.pokerservice.engine.commands.GamersInfoCommand;
import com.unit7.services.pokerservice.engine.commands.GetCardCommand;
import com.unit7.services.pokerservice.engine.commands.PreflopCommand;
import com.unit7.services.pokerservice.engine.commands.RequestNameCommand;
import com.unit7.services.pokerservice.engine.commands.RiverCommand;
import com.unit7.services.pokerservice.engine.commands.SmallBlindCommand;
import com.unit7.services.pokerservice.engine.commands.TurnCommand;
import com.unit7.services.pokerservice.engine.framework.CommandExecutor;
import com.unit7.services.pokerservice.engine.framework.Controller;
import com.unit7.services.pokerservice.engine.framework.Executor;
import com.unit7.services.pokerservice.engine.framework.GamerCommandListener;
import com.unit7.services.pokerservice.engine.framework.GamersChangeListener;
import com.unit7.services.pokerservice.model.PokerGamer;

public class PokerGameInterfaceImpl implements PokerGameInterface {
    public PokerGameInterfaceImpl(List<PokerGamer> gamers) {
        this.gamers = gamers;
        Controller.getInstance().addListener(new GamerCommandListener().add(this));
        Controller.getInstance().addListener(new GamersChangeListener().add(this));
    }

    @Override
    public void distributeCards() {
        Command[] commands = new Command[gamers.size()];
        for (int i = 0; i < gamers.size(); ++i) {
            Card first = Card.getRandCard(cards);
            Card second = Card.getRandCard(cards);

            PokerGamer gamer = gamers.get(i);
            gamer.addCard(first);
            gamer.addCard(second);

            GetCardCommand command = new GetCardCommand();
            command.setGamer(gamer);
            commands[i] = command;
        }

        executor.execute(commands);
    }

    @Override
    public void requestSmallBlind(PokerGamer gamer) {
        GamerCommand command = new SmallBlindCommand();
        command.setGamer(gamer);
        executor.execute(new Command[] { command });
    }

    @Override
    public void requestBigBlind(PokerGamer gamer) {
        GamerCommand command = new BigBlindCommand();
        command.setGamer(gamer);
        executor.execute(new Command[] { command });
    }

    @Override
    public void game() {
        // step one - request usernames
        requestNames();

        sendGamersInfo();

        // step two - select button
        PokerGamer button = selectButton();

        // step three - get small blind
        PokerGamer gamer = gamers.get((lastButton + 1) % gamers.size());
        requestSmallBlind(gamer);

        // step four - get big blind
        gamer = gamers.get((lastButton + 2) % gamers.size());
        requestBigBlind(gamer);

        betRound();

        flop();

        betRound();

        turn();

        betRound();

        river();
        
        determineWinner();
    }

    @Override
    public PokerGamer selectButton() {
        if (lastButton == -1) {
            lastButton = Utils.getRandInt(gamers.size());
        } else {
            lastButton = ++lastButton % gamers.size();
        }

        return gamers.get(lastButton);
    }

    @Override
    public void betRound() {
        // next to big blind
        int gamerIndex = (lastButton + 3) % gamers.size();
        int bigBlindIndex_1 = (lastButton + 2) % gamers.size();
        double maxBet = gamers.get(bigBlindIndex_1).getBet();
        boolean wasChanged = true;

        if (log.isDebugEnabled()) {
            log.debug(String.format("[\tgamerIndex: %d, bigBlind: %d, maxBet: %.2f\t]", gamerIndex,
                    bigBlindIndex_1, maxBet));
        }

        BetCommand command = new BetCommand();
        while (wasChanged) {
            wasChanged = false;
            while (gamerIndex != bigBlindIndex_1) {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("[\tCurrent gamerIndex: %d\t]", gamerIndex));
                }
                
                PokerGamer gamer = gamers.get((gamerIndex = (gamerIndex + 1) % gamers.size()));
                command.setGamer(gamer);
                // комманда выполняется не в отдельном потоке, значит результат
                // у нас уже будет
                executor.execute(new Command[] { command });

                GamerCommand selectedCommand = gamerChoice.get(gamer);
                // не должно быть
                assert selectedCommand != null : "selectedCommand == null";

                // игрок повысил ставку - продолжаем круг торгов
                if (CommandType.RAISE.equals(selectedCommand.getCommandType())) {
                    wasChanged = true;
                }
            }
        }
    }

    @Override
    public void preFlop() {
        executor.execute(new Command[] { new PreflopCommand() });
    }

    @Override
    public void flop() {
        executor.execute(new Command[] { new FlopCommand() });
    }

    @Override
    public void turn() {
        executor.execute(new Command[] { new TurnCommand() });
    }

    @Override
    public void river() {
        executor.execute(new Command[] { new RiverCommand() });
    }

    protected void requestNames() {
        for (PokerGamer gamer : gamers) {
            RequestNameCommand command = new RequestNameCommand();
            command.setGamer(gamer);
            executor.execute(new Command[] { command });
        }
    }

    public void update(Map<PokerGamer, GamerCommand> data) {
        if (data == null)
            throw new IllegalArgumentException("method=PokerGameInterfaceImpl.update: data == null");

        gamerChoice.putAll(data);
    }

    /*
     * @Override public void update(Object gamers) { if (gamers == null) throw
     * new IllegalArgumentException(
     * "method=PokerGameInterfaceImpl.update: gamers == null");
     * 
     * if (!(gamers instanceof List)) throw new IllegalArgumentException(
     * "method=PokerGameInterfaceImpl.update: gamers != List");
     * 
     * this.gamers.clear(); List<PokerGamer> g = (List<PokerGamer>) gamers; for
     * (PokerGamer gamer : g) { this.gamers.add(gamer); } }
     */
    @Override
    public void determineWinner() {
        PokerGamer one = null;
        for (PokerGamer gamer : gamers) {
            if (gamer.isInGame()) {
                if (one != null) {
                    one = null;
                    break;
                } else
                    one = gamer;
            }
        }

        // combinations
        Map<Integer, CombinationType> result = new HashMap<Integer, CombinationType>();
        // one gamer in game - winner
        Map<CombinationType, List<PokerGamer>> winners = new TreeMap<CombinationType, List<PokerGamer>>();
        if (one != null) {
            List<PokerGamer> wins = new ArrayList<PokerGamer>();
            wins.add(one);

            // TODO winners.put(key, wins);
        } else {
            // open cards...
            for (PokerGamer gamer : gamers) {
                if (gamer.isInGame()) {
                    // TODO определить комбинацию
                    CombinationType type = null;
                    List<PokerGamer> wins;
                    result.put(gamer.getId(), type);
                    if (winners.containsKey(type)) {
                        wins = winners.get(type);
                    } else {
                        wins = new ArrayList<PokerGamer>();
                    }

                    wins.add(gamer);
                    winners.put(type, wins);
                }
            }
        }

        List<PokerGamer> realWinners = winners.get(winners.keySet().iterator().next());
        EndRoundCommand command = new EndRoundCommand();
        command.setWinners(realWinners);
        command.setCombinations(result);
        command.execute(Controller.getInstance());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.unit7.services.pokerservice.engine.PokerGameInterface#sendGamersInfo
     * ()
     */
    @Override
    public void sendGamersInfo() {
        Command command = new GamersInfoCommand();
        executor.execute(new Command[] { command });
    }

    private List<PokerGamer> gamers;
    private Set<Card> cards = new HashSet<Card>();
    private Executor executor = new CommandExecutor();
    private int lastButton = -1;

    private Map<PokerGamer, GamerCommand> gamerChoice = new HashMap<PokerGamer, GamerCommand>();

    private static final Logger log = Logger.getLogger(PokerGameInterfaceImpl.class);
}
