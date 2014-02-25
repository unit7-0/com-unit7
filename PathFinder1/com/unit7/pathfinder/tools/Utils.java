/**
 * Date:	22 февр. 2014 г.
 * File:	Utils.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.tools;

import java.awt.Toolkit;
import java.awt.Window;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.unit7.pathfinder.engine.ImageMap;
import com.unit7.pathfinder.engine.NamedHolder;
import com.unit7.pathfinder.engine.QueryData;
import com.unit7.pathfinder.graphs.Pair;
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

    /**
     * @param mapHolder
     */
    public static <T extends NamedHolder & Serializable> void saveObject(T obj, String dir) {
        OutputStream out = null;
        ObjectOutputStream objOut = null;

        try {
            out = new FileOutputStream(dir + obj.getName());
            objOut = new ObjectOutputStream(out);
            objOut.writeObject(obj);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (objOut != null)
                    objOut.close();
                if (out != null)
                    out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public static <T extends NamedHolder & Serializable> T readObject(String dir, String name) throws ClassNotFoundException {
        T obj = null;
        InputStream in = null;
        ObjectInputStream objIn = null;
        try {
            in = new FileInputStream(dir + name);
            objIn = new ObjectInputStream(in);
            obj = (T) objIn.readObject();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (objIn != null) {
                    objIn.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return obj;
    }
}
