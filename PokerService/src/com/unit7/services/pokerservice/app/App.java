package com.unit7.services.pokerservice.app;

import com.unit7.services.pokerservice.client.tools.Settings;
import com.unit7.services.pokerservice.engine.framework.Controller;

public class App {
	public static void main(String[] args) {
		Controller.getInstance().upServer(Settings.SERVER_PORT);
	}
}
