package com.unit7.services.pokerservice.engine.commands;

import java.util.List;
import java.util.Map;

import com.unit7.services.pokerservice.client.model.CombinationType;
import com.unit7.services.pokerservice.engine.framework.Controller;
import com.unit7.services.pokerservice.model.PokerGamer;

/**
 * Выдает игрокам выигрыш, а также определяет кто остался в игре
 * 
 * @author unit7
 * 
 */
public class EndRoundCommand extends AbstractCommand {
    public EndRoundCommand() {
        setCommandType(CommandType.END_ROUND);
    }

    @Override
    public void execute(Controller controller) {
        controller.execute(this);
    }

	public Map<Integer, CombinationType> getCombinations() {
		return combinations;
	}

	public void setCombinations(Map<Integer, CombinationType> combinations) {
		this.combinations = combinations;
	}

	public List<PokerGamer> getWinners() {
		return winners;
	}

	public void setWinners(List<PokerGamer> winners) {
		this.winners = winners;
	}

	private Map<Integer, CombinationType> combinations;
	private List<PokerGamer> winners;
}
