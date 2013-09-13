package com.unit7.study.computergraphic.labs.test;

import java.util.concurrent.TimeUnit;

import com.unit7.study.computergraphic.labs.introduction.Introduction;

import junit.framework.TestCase;

public class IntroductionTest extends TestCase {
    static {
        System.setProperty("java.library.path", "C:/Users/unit7/sources/Eclipse/workspace/ComputerGraphic/bin");
        System.loadLibrary("com_unit7_study_computergraphic_labs_test_IntroductionTest");
    }
    
    public void testConsoleFly() {
        final int h = getHeight() - 1, w = getWidth() - 1;
        final int delay = 100;
        Introduction app = new Introduction(w, h);

        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                printCharXY(" ", j, i);
                if (i == 0 || i == h - 1) {
                    printCharXY("#", j, i);
                }
            }
            
            printCharXY("#", 0, i);
            printCharXY("#", w - 1, i);
        }
        
        while (true) {
            printCharXY(" ", app.getY(), app.getX());
            app.move();
            printCharXY("@", app.getY(), app.getX());
            try {
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            if (false)
                break;
        }
    }
   
    public native static int getWidth();
    public native static int getHeight();
    public native static void clearConsole();
    public native static void printCharXY(String str, int x, int y);
}
