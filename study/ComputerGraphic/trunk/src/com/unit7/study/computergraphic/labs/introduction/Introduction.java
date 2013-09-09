package com.unit7.study.computergraphic.labs.introduction;

public class Introduction implements Movable {
    public Introduction(int n, int m) {
        this(n, m, '@');
    }

    public Introduction(int n, int m, char symbol) {
        this.symbol = symbol;
        console = new StringBuilder[n];
        for (int i = 0; i < n; ++i) {
            console[i] = new StringBuilder();
            console[i].setLength(m);
            
            for (int j = 0; j < m; ++j) {
                console[i].setCharAt(j, ' ');
                if (i + 1 == n || i == 0) {
                    console[i].setCharAt(j, '#');
                }
            }
            
            console[i].setCharAt(0, '#');
            console[i].setCharAt(m - 1, '#');
        }
        
        x = y = 1;
        dx = dy = 1;
        console[x].setCharAt(y, symbol);
    }
    
    public String[] getCanvas() {
        String[] canvas = new String[console.length];
        for (int i = 0; i < console.length; ++i) {
            canvas[i] = console[i].toString();
        }
        
        return canvas;
    }

    @Override
    public void move() {
        console[x].setCharAt(y, ' ');
        if (x + dx  + 1 >= console.length || x + dx - 1 < 0) {
            dx = -dx;
        }
        
        if (y + dy + 1 >= console[0].length() || y + dy < 0) {
            dy = -dy;
        }
        
        x += dx; y += dy;
        console[x].setCharAt(y, symbol);
    }
    
    private StringBuilder[] console;
    private int dx, dy, x, y;
    private char symbol;
}
