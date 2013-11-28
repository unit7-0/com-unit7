package com.unit7.services.pokerservice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Основной поток сервера, сюда приходят конекшены
 * @author Zajcev
 *
 */
public class ServerThread implements Runnable {
	public ServerThread(int port) {
		this.port = port;
		try {
			socket = new  ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		Socket clientSocket = null;
		try {
			Semaphore semaphore = new Semaphore(1);
			while ((clientSocket = socket.accept()) != null) {
				WaitingThread thread = new WaitingThread(socket, clientSocket, semaphore);
				new Thread(thread).start();
				
				boolean tryAc = false;
				do {
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					tryAc = semaphore.tryAcquire();					
				} while (!tryAc);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ServerSocket socket;
	private int port;
}
