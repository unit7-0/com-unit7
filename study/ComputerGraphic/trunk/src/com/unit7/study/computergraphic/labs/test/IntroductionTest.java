package com.unit7.study.computergraphic.labs.test;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import com.unit7.study.computergraphic.labs.introduction.Introduction;

import junit.framework.TestCase;

public class IntroductionTest extends TestCase {
    static {
        System.setProperty("java.library.path", "C:/users/unit7/sources/workspace/Eclipse/ComputerGraphic/bin");
        System.loadLibrary("com_unit7_study_computergraphic_labs_test_IntroductionTest");
    }
    
    public void testConsoleFly() {
        final int n = getHeight() - 1, m = getWidth() - 1;
        final int delay = 300;
        Introduction app = new Introduction(n, m);
        PrintWriter writer = new PrintWriter(System.out);

        while (true) {
            String[] canvas = app.getCanvas();
            clearConsole();
            for (int i = 0; i < canvas.length; ++i) {
                for (int j = 0; j < canvas[i].length(); ++j) {
                    writer.print(canvas[i].charAt(j));
                }
                writer.println();
            }
            
            writer.flush();
            app.move();
            try {
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            if (false)
                break;
        }
        
        writer.close();
    }
   
    public native static int getWidth();
    public native static int getHeight();
    public native static void clearConsole();
}
