package com.unit7.services.pokerservice.app;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.unit7.services.pokerservice.client.tools.Settings;
import com.unit7.services.pokerservice.engine.framework.Controller;

public class App {
	public static void main(String[] args) {
//	    System.setProperty("log4j.configuration", "config/log4j.properties");
//	    Logger.getRootLogger().setLevel(Level.DEBUG);
		Controller.getInstance().upServer(Settings.SERVER_PORT);
	}
}
