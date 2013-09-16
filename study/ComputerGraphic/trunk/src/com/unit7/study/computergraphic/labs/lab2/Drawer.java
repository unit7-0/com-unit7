package com.unit7.study.computergraphic.labs.lab2;

import javax.media.opengl.GL2;

/**
 * Рисовальщик.
 * Передает управление реальному алгоритму.
 * @author unit7
 *
 */
public class Drawer {
    public Drawer() {
        this(new DrawerStrategy() {
            @Override
            public void display(GL2 gl) {
                // TODO Auto-generated method stub
            }

            @Override
            public void clear(GL2 gl) {
                // TODO Auto-generated method stub
            }
        });
    }

    public Drawer(DrawerStrategy strategy) {
        this.strategy = strategy;
    }

    public void clear(GL2 gl) {
        strategy.clear(gl);
    }

    public void display(GL2 gl) {
        strategy.display(gl);
    }
    
    public DrawerStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(DrawerStrategy strategy) {
        this.strategy = strategy;
    }

    private DrawerStrategy strategy;
}
