package com.unit7.services.pokerservice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Поток ожидает новых подключений для создания игры, если вышел таймаут
 * подключения берется доступное количество игроков и начинаетя игра, если их
 * меньше возвращается результат ошибки. Если подключилось уже максимальное
 * количество игроков - игра начинается.
 * 
 * @author Zajcev
 * 
 */
public class WaitingThread implements Runnable {
	public WaitingThread(ServerSocket socket, Socket clientSocket, Semaphore semaphore) {
		this.socket = socket;
		this.semaphore = semaphore;
		this.clients.add(clientSocket);
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Socket clientSocket = null;
		try {
			socket.setSoTimeout(connectionTimeOut);
			try {
				while ((clientSocket = socket.accept()) != null) {
					clients.add(clientSocket);
				}
			} catch (SocketTimeoutException ex) {
				// проверить игроков, начать игру
			}
			
			socket.setSoTimeout(0);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
	}

	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	private int connectionTimeOut = 900;
	private ServerSocket socket;
	private Semaphore semaphore;
	private List<Socket> clients = new ArrayList<Socket>();
}
