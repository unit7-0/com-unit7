/**
 * Date:	15 дек. 2013 г.
 * File:	RegExp.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.rgz.var7;

/**
 * @author unit7
 *
 */
public class RegExp {
    public RegExp(String expr, String terminals, int min, int max) {
        this.expr = expr;
        this.min = min;
        this.max = max;
        this.terminals = terminals;
    }
    
    public String getExpr() {
        return expr;
    }

    public String getTerminals() {
        return terminals;
    }

    public void setTerminals(String terminals) {
        this.terminals = terminals;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    private String expr;
    private int min;
    private int max;
    private String terminals;
}
