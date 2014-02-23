/**
 * Date:	22 февр. 2014 г.
 * File:	Utils.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.tools;

import java.awt.Toolkit;
import java.awt.Window;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.unit7.pathfinder.graphs.Pair;
import com.unit7.pathfinder.gui.QueryData;
import com.unit7.pathfinder.gui.UserInputFrame;

/**
 * @author unit7
 * 
 */
public class Utils {
    public static void centreFrame(Window main, Window depend) {
        int x = 0, y = 0;
        if (main == null) {
            java.awt.Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            x = d.width / 2 - depend.getWidth() / 2;
            y = d.height / 2 - depend.getHeight() / 2;
        } else {
            x = main.getX() + main.getWidth() / 2 - depend.getWidth() / 2;
            y = main.getY() + main.getHeight() / 2 - depend.getHeight() / 2;
        }

        depend.setLocation(x, y);
    }

    public static boolean isIntersect(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2, Integer r) {
        if (p1 == null || p2 == null)
            return false;

        Integer x = p1.getFirst(), y = p1.getSecond(), x1 = p2.getFirst(), y1 = p2.getSecond();
        if (x >= x1 - r && x <= x1 + r && y >= y1 - r && y <= y1 + r)
            return true;

        return false;
    }

    public static Object getUserInput(UserInputFrame obj) {
        final AtomicBoolean quit = new AtomicBoolean();
        quit.set(false);

        obj.setQuit(quit);
        obj.createGUI();
        obj.setVisible(true);

        while (!quit.get()) {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        Object res = obj.getData();
        obj.dispose();
        return res;
    }
}
