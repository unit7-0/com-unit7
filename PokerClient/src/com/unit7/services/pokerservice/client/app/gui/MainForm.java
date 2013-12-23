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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.unit7.services.pokerservice.client.commands.Command;
import com.unit7.services.pokerservice.client.commands.CommandType;
import com.unit7.services.pokerservice.client.commands.SimpleCommand;
import com.unit7.services.pokerservice.client.engine.Controller;
import com.unit7.services.pokerservice.client.engine.EndGameProxy;
import com.unit7.services.pokerservice.client.engine.GameThread;
import com.unit7.services.pokerservice.client.engine.RefreshDataProxy;
import com.unit7.services.pokerservice.client.engine.RequestBetProxy;
import com.unit7.services.pokerservice.client.engine.RequestNameProxy;
import com.unit7.services.pokerservice.client.engine.RoundInfoProxy;
import com.unit7.services.pokerservice.client.resources.Resources;
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

        final JPanel main = new JPanel();
        add(main);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String address = "127.0.0.1:7777";/*JOptionPane.showInputDialog(MainForm.this,
                        "Введите адрес сервера[xxx.xxx.xxx.xxx:port]");*/
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
                JDialog dialog = null;
                try {
                    // блокируем родительское окно
                    dialog = lockAndShow(MainForm.this, "Пожалуйста подождите...");

                    // пытаемся коннектиться
                    Socket sock = null;
                    try {
                        sock = SocketFactory.getDefault().createSocket(address, port);
                        Controller.getInstance().setServerSocket(sock);
                        if (log.isDebugEnabled()) {
                            log.debug("connected to: " + sock.getInetAddress().getHostAddress() + " : "
                                    + sock.getPort());
                        }
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

                    if (log.isDebugEnabled()) {
                        log.debug("name setted");
                    }

                    command.setType(CommandType.GAMERS_INFO);
                    command.execute(Controller.getInstance());

                    if (log.isDebugEnabled()) {
                        log.debug("gamers received");
                    }

                    command.setType(CommandType.INIT_GAME);
                    command.execute(Controller.getInstance());

                    desktop = new DesktopPanel(Controller.getInstance().getGamers());
                    main.add(desktop);
                    main.revalidate();
                    main.repaint();

                    new Thread(new GameThread.Builder().setBetProxy(new RequestBetProxy(MainForm.this))
                            .setDataProxy(new RefreshDataProxy(MainForm.this))
                            .setEndGameProxy(new EndGameProxy(MainForm.this))
                            .setRoundInfoProxy(new RoundInfoProxy(MainForm.this)).build()).start();

                    if (log.isDebugEnabled()) {
                        log.debug("game inited");
                    }
                } finally {
                    unlock(MainForm.this, dialog);
                }
            }
        });

        setSize(400, 300);
        Utils.centreFrame(null, this);
        setTitle("Texas Holdem");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        return this;
    }

    public JDialog lockAndShow(JFrame frame, String message) {
        frame.setEnabled(false);
        JDialog dialog = new JDialog(frame);
        dialog.setUndecorated(true);
        JLabel label = new JLabel("Пожалуйста, подождите...");
        JPanel panel = new JPanel();
        panel.add(label);
        dialog.getContentPane().add(panel);
        dialog.pack();
        Utils.centreFrame(frame, dialog);
        dialog.setVisible(true);

        return dialog;
    }

    public void unlock(JFrame frame, JDialog dialog) {
        dialog.dispose();
        frame.setEnabled(true);
    }

    public double getBet(String message) {
        double val = 0;

        String input = null;
        Pattern pattern = Pattern.compile(Resources.REGEX_DOUBLE);
        Matcher matcher = null;
        do {
            input = JOptionPane.showInputDialog(message);
            matcher = pattern.matcher(input);
        } while (!matcher.matches());

        val = Double.parseDouble(input);

        return val;
    }

    public void refresh() {
        desktop.setPrikup(Controller.getInstance().getPrikup());
        revalidate();
        repaint();
    }

    public void roundInfo(String info) {
        JOptionPane.showMessageDialog(this, info);
    }

    public void endGame() {

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

    private DesktopPanel desktop;

    private static final Logger log = Logger.getLogger(Logger.class);
}
