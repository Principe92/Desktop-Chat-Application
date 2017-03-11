package main;

import java.io.IOException;

import factory.AbstractFactory;
import model.ILogger;

public class Main {
	
	public static void main(String[] args) {
		ILogger logger = AbstractFactory.getLogger();
	
		if (args == null || args.length != 1){
			logger.logInfo("Port number is missing");
			System.exit(0);
		}
		
		
		int portNumber = Integer.parseInt(args[0]);
		Server main = new Server(portNumber, logger);
		try {
			main.run();
		} catch (IOException e) {
			try {
				main.close();
			} catch (IOException e1) {
				logger.logError(e1);
			}
			logger.logError(e);
		}

	}

}	
