package com.unit7.services.pokerservice.client.tools;

import java.awt.Toolkit;
import java.awt.Window;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import javax.swing.JFrame;

public class Utils {
    public static byte[] serializeObject(Object obj) {        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        byte[] data = out.toByteArray();
        
        try {
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return data;
    }
    
    public static Object deserializerObject(byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        Object result = null;
        try {
            ObjectInputStream objIn = new ObjectInputStream(in);
            result = objIn.readObject();
            objIn.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return result;
    }
    
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
    
    public static int getRandInt(int n) {
        return rnd.nextInt(n);
    }
    
    private static final Random rnd = new Random();
}
