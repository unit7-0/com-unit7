package com.unit7.study.computergraphic.labs.lab2;

import javax.media.opengl.GL2;

public interface DrawerStrategy {
    void display(GL2 gl);
    void clear(GL2 gl);
}
