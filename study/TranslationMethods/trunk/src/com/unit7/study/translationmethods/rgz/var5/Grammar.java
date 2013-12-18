/**
 * Date:	15 дек. 2013 г.
 * File:	Grammar.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.rgz.var5;

import java.util.Map;

/**
 * @author unit7
 * 
 */
public class Grammar {
    public Grammar(Map<String, String> rules, String terminals, String notTerminals, String target, int min, int max) {
        this.rules = rules;
        this.terminals = terminals;
        this.notTerminals = notTerminals;
        this.target = target;
        this.minLen = min;
        this.maxLen = max;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getMinLen() {
        return minLen;
    }

    public void setMinLen(int minLen) {
        this.minLen = minLen;
    }

    public int getMaxLen() {
        return maxLen;
    }

    public void setMaxLen(int maxLen) {
        this.maxLen = maxLen;
    }

    public Map<String, String> getRules() {
        return rules;
    }

    public void setRules(Map<String, String> rules) {
        this.rules = rules;
    }

    public String getTerminals() {
        return terminals;
    }

    public void setTerminals(String terminals) {
        this.terminals = terminals;
    }

    public String getNotTerminals() {
        return notTerminals;
    }

    public void setNotTerminals(String notTerminals) {
        this.notTerminals = notTerminals;
    }

    private Map<String, String> rules;
    private String terminals;
    private String notTerminals;
    private String target;
    
    private int minLen;
    private int maxLen;
}
