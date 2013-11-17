package com.unit7.study.translationmethods.labs.chains;

import java.util.Map;

import com.unit7.study.translationmethods.labs.lab1.GrammarRules;
import com.unit7.study.translationmethods.labs.lab1.GrammarRules.State;

/**
 * Контейнер для передачи параметров классу Builder и возвращения результатов
 * работы
 * 
 * @author unit7
 * 
 */
public class Container {
    private Container(Builder builder) {
        this.result = builder.result;
        this.chain = builder.chain;
        this.chainPos = builder.chainPos;
        this.cType = builder.cType;
        this.current = builder.current;
        this.rule = builder.rule;
        this.rulePos = builder.rulePos;
        this.rules = builder.rules;
    }
    
    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getChainPos() {
        return chainPos;
    }

    public void setChainPos(int chainPos) {
        this.chainPos = chainPos;
    }

    public int getRulePos() {
        return rulePos;
    }

    public void setRulePos(int rulePos) {
        this.rulePos = rulePos;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public State getcType() {
        return cType;
    }

    public void setcType(State cType) {
        this.cType = cType;
    }

    public Vertex getCurrent() {
        return current;
    }

    public void setCurrent(Vertex current) {
        this.current = current;
    }
    
    public Map<String, String> getRules() {
        return rules;
    }

    public void setRules(Map<String, String> rules) {
        this.rules = rules;
    }

    public static class Builder {
        private boolean result;
        private int chainPos;
        private int rulePos;
        private String chain;
        private String rule;
        private GrammarRules.State cType;
        private Vertex current; 
        private Map<String, String> rules;
        
        public Builder setResult(boolean result) {
            this.result = result;
            return this;
        }

        public Builder setChainPos(int chainPos) {
            this.chainPos = chainPos;
            return this;
        }

        public Builder setRulePos(int rulePos) {
            this.rulePos = rulePos;
            return this;
        }

        public Builder setChain(String chain) {
            this.chain = chain;
            return this;
        }

        public Builder setRule(String rule) {
            this.rule = rule;
            return this;
        }

        public Builder setcType(GrammarRules.State cType) {
            this.cType = cType;
            return this;
        }

        public Builder setCurrent(Vertex current) {
            this.current = current;
            return this;
        }
        
        public Builder setRules(Map<String, String> rules) {
            this.rules = rules;
            return this;
        }

        public Container build() {
            return new Container(this);
        }
    }

    private boolean result;
    private int chainPos;
    private int rulePos;
    private String chain;
    private String rule;
    private Map<String, String> rules;
    private GrammarRules.State cType;
    private Vertex current;
}
