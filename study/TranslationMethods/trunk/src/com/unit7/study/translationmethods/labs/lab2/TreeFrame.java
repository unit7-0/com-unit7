package com.unit7.study.translationmethods.labs.lab2;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.unit7.study.translationmethods.labs.chains.Container;
import com.unit7.study.translationmethods.labs.chains.Frame;
import com.unit7.study.translationmethods.labs.chains.Vertex;
import com.unit7.study.translationmethods.labs.lab1.GrammarRules;

public class TreeFrame extends Frame<Container> {
    public TreeFrame(Map<String, String> rules, String target, String chain) {
        super(rules, target);
        this.chain = chain;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void show() {
        String[] vars = rules.get(target).split(GrammarRules.GRAMMAR_DELIMETER);
        for (String rule : vars) {
            GrammarRules.State cType;
            // первый символ правила
            if (GrammarRules.isTerminal(rule.charAt(0))) {
                cType = GrammarRules.State.TERMINAL;
            } else {
                cType = GrammarRules.State.NOT_TERMINAL;
            }

            Container container = new Container.Builder().setChain(chain).setChainPos(0).setcType(cType)
                    .setCurrent(new Vertex(target)).setRule(rule).setRulePos(0).build();

            List<Container> toShow = build(container);

            // показываем
            for (Container cont : toShow) {
                if (cont.isResult()) {
                    // show
                }
            }
        }
    }

    private String chain;
    private static final Logger log = Logger.getLogger(TreeFrame.class.getName());
}
