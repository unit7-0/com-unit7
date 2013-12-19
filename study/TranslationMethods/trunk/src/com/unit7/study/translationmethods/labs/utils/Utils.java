package com.unit7.study.translationmethods.labs.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JFrame;

import com.unit7.study.translationmethods.labs.chains.Container;

public class Utils {
    public static void centreFrame(JFrame main, JFrame child) {
        int x = 0;
        int y = 0;
        
        if (main == null) {
        	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        	x = d.width / 2 - child.getWidth() / 2;
        	y = d.height / 2 - child.getHeight() / 2;
        } else {
        	x = main.getX() + main.getWidth() / 2 - child.getWidth() / 2;
        	y = main.getY() + main.getHeight() / 2 - child.getHeight() / 2;
        }
        
        child.setLocation(x, y);
    }

    public static boolean validateContainer(Container container) {
        return container.getChainPos() >= container.getChain().length()
                && container.getRulePos() >= container.getRule().length();
    }

    public static boolean validateContainers(List<Container> containers) {
        if (containers == null)
            return true;

        for (Container cont : containers) {
            if (!validateContainer(cont))
                return false;
        }

        return true;
    }
}
