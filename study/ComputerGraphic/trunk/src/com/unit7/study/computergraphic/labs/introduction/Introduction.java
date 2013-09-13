package com.unit7.study.computergraphic.labs.introduction;

public class Introduction implements Movable {
    public Introduction(int w, int h) {
        x = y = 1;
        dx = dy = 1;
        this.w = w;
        this.h = h;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void move() {
        if (x + dx  + 1 >= h || x + dx - 1 < 0) {
            dx = -dx;
        }
        
        if (y + dy + 1 >= w || y + dy - 1 < 0) {
            dy = -dy;
        }
        
        x += dx; y += dy;
    }
    
    private int dx, dy, x, y, w, h;
    private char symbol;
}
