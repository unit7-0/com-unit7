/**
 * Date:	22 дек. 2013 г.
 * File:	PokerClientTests.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.tests;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadPoolExecutor;

import javax.net.SocketFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
//import org.junit.Test;

import com.sun.corba.se.impl.orbutil.threadpool.ThreadPoolImpl;
import com.sun.corba.se.impl.orbutil.threadpool.ThreadPoolManagerImpl;
import com.sun.org.apache.xml.internal.utils.ThreadControllerWrapper.ThreadController;
import com.unit7.services.pokerservice.client.app.gui.DesktopPanel;
import com.unit7.services.pokerservice.client.app.gui.MainForm;
import com.unit7.services.pokerservice.client.commands.CommandType;
import com.unit7.services.pokerservice.client.commands.SimpleCommand;
import com.unit7.services.pokerservice.client.engine.Controller;
import com.unit7.services.pokerservice.client.engine.EndGameProxy;
import com.unit7.services.pokerservice.client.engine.GameThread;
import com.unit7.services.pokerservice.client.engine.RefreshDataProxy;
import com.unit7.services.pokerservice.client.engine.RequestBetProxy;
import com.unit7.services.pokerservice.client.engine.RequestBlindProxy;
import com.unit7.services.pokerservice.client.engine.RequestNameProxy;
import com.unit7.services.pokerservice.client.engine.RoundInfoProxy;
import com.unit7.services.pokerservice.client.resources.Resources;

/**
 * @author unit7
 * 
 */
public class PokerClientTests {
	public void loadResources() {
		try {
			Class.forName(Resources.class.getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		PokerClientTests tests = new PokerClientTests();
		tests.loadResources();
		tests.panel();
	}

	// @Test
	public void panel() {
		JFrame fr = new JFrame();

		JPanel main = new JPanel(new GridLayout(2, 1, 10, 10));
		final JPanel main1 = new JPanel(null);
		main.setSize(10, 10);
		main1.setBackground(Color.RED);
		main1.setSize(10, 10);

		main.add(main1);

		JPanel main2 = new JPanel();
		JButton resize = new JButton("resize");

		resize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main1.setSize(100, 100);
			}
		});

		main2.add(resize);
		main.add(main2);
		main.setBackground(Color.BLUE);

		fr.getContentPane().add(main);
		fr.setSize(10, 10);

		// fr.pack();
		fr.setVisible(true);
	}

//	@Test
	public void testMaxGamers() {
		for (int i = 0; i < 10; ++i) {
			upClient();
		}
	}

	public void upClient() {
		try {
			Socket sock = SocketFactory.getDefault().createSocket(ip, port);
			Controller.getInstance().setServerSocket(sock);
			/*
			 * Controller.getInstance().setRequestNameProxy( new
			 * RequestNameProxy(null));
			 */
			new Thread(new Runnable() {
				@Override
				public void run() {
					SimpleCommand command = new SimpleCommand();
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

					MainFormStub stub = new MainFormStub();
					Thread thread = new Thread(new GameThread.Builder()
							.setBetProxy(new RequestBetProxy(stub))
							.setDataProxy(new RefreshDataProxy(stub))
							.setEndGameProxy(new EndGameProxy(stub))
							.setRoundInfoProxy(new RoundInfoProxy(stub))
							.setRequestBlindProxy(new RequestBlindProxy(stub))
							.build());
					thread.start();
				}
			}).start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static final String ip = "127.0.0.1";
	private static final int port = 7777;
	private static final String name = "asd";

	private static final Logger log = Logger.getLogger(PokerClientTests.class);
}
