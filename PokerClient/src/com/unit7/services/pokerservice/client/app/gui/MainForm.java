/**
 * Date:	17 дек. 2013 г.
 * File:	MainForm.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.app.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.SocketFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.unit7.services.pokerservice.client.commands.Command;
import com.unit7.services.pokerservice.client.commands.CommandType;
import com.unit7.services.pokerservice.client.commands.SimpleCommand;
import com.unit7.services.pokerservice.client.engine.Controller;
import com.unit7.services.pokerservice.client.engine.RequestNameProxy;
import com.unit7.services.pokerservice.client.tools.Utils;

/**
 * @author unit7
 * 
 */
public class MainForm extends AbstractForm {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.unit7.services.pokerservice.client.app.gui.AbstractForm#createGUI()
     */
    @Override
    public JFrame createGUI() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Файл");
        JMenuItem connect = new JMenuItem("Подключиться");
        JMenuItem exit = new JMenuItem("Выход");

        menu.add(connect);
        menu.add(exit);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String address = JOptionPane.showInputDialog(MainForm.this,
                        "Введите адрес сервера[xxx.xxx.xxx.xxx:port]");
                Pattern pattern = Pattern.compile("((\\d+\\.){3}\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(address);

                // получаем адрес
                int port = 0;
                if (matcher.matches()) {
                    address = matcher.group();
                    int del = address.indexOf(':');
                    String part = address.substring(0, del);
                    String portStr = address.substring(del + 1, address.length());
                    port = Integer.parseInt(portStr);
                    address = part;
                } else {
                    JOptionPane.showMessageDialog(MainForm.this, "Адрес введен неверно!");
                    return;
                }

                JFrame frame = new JFrame();
                try {
                    // блокируем родительское окно
                    MainForm.this.setEnabled(false);
                    frame.setUndecorated(true);
                    JLabel label = new JLabel("Пожалуйста, подождите...");
                    JPanel panel = new JPanel();
                    panel.add(label);
                    frame.getContentPane().add(panel);
                    frame.pack();
                    Utils.centreFrame(MainForm.this, frame);
                    frame.setVisible(true);

                    // пытаемся коннектиться
                    Socket sock = null;
                    try {
                        sock = SocketFactory.getDefault().createSocket(address, port);
                    } catch (UnknownHostException e1) {
                        JOptionPane.showMessageDialog(MainForm.this, e1.getLocalizedMessage());
                        return;
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(MainForm.this, e1.getLocalizedMessage());
                        return;
                    }
                    
                    // все ок
                    JOptionPane.showMessageDialog(MainForm.this, "Соединение установлено.");
                    Command command = new SimpleCommand();
                    
                    Controller.getInstance().setRequestNameProxy(new RequestNameProxy(MainForm.this));
                    
                    command.setType(CommandType.REQUEST_NAME);
                    command.execute(Controller.getInstance());
                    
                    command.setType(CommandType.GAMERS_INFO);
                    command.execute(Controller.getInstance());
                    
                    command.setType(CommandType.INIT_GAME);
                    command.execute(Controller.getInstance());
                } finally {
                    frame.dispose();
                    MainForm.this.setEnabled(true);
                }
            }
        });

        setSize(400, 300);
        Utils.centreFrame(null, this);
        setTitle("Texas Holdem");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        return this;
    }
    
    public String getUserName() {
        this.setEnabled(false);
        String name = null;
        do {
            name = JOptionPane.showInputDialog(this, "Введите имя пользователя[3-15 знаков]:");
        } while (!(name != null && (name = name.trim()).length() <= 15 && name.length() >= 3));
        
        this.setEnabled(true);
        return name;
    }
}
