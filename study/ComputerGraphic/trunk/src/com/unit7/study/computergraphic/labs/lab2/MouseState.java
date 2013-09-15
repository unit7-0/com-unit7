package com.unit7.study.computergraphic.labs.lab2;

public interface MouseState extends State {
    int getX();
    int getY();
    void setX(int x);
    void setY(int y);
    void setXY(int x, int y);
}
