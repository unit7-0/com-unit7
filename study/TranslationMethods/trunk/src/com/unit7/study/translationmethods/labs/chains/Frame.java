package com.unit7.study.translationmethods.labs.chains;

import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import com.unit7.study.translationmethods.labs.lab2.TreeBuilder;

/**
 * Делегирует построение контента билдеру и отображает результаты. Реализация
 * отображения достается наследникам
 * 
 * @author unit7
 * 
 * @param <T>
 */
public abstract class Frame<T> extends JFrame {
    public Frame(Map<String, String> rules, String target) {
        this.rules = rules;
        this.target = target;
    }
    
    public abstract void showContent();

    protected List<T> build(Container container) {
        return builder.build(container);
    }

    public Builder<T> getBuilder() {
        return builder;
    }

    public void setBuilder(Builder<T> builder) {
        this.builder = builder;
    }

    private Builder<T> builder;
    protected Map<String, String> rules;
    protected String target;
}
