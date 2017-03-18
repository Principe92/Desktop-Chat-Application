package main;

import java.io.IOException;

import factory.AbstractFactory;
import type.ILogger;
import type.ISocketProtocol;

public class Main {

	public static void main(String[] args) throws IOException {
		ILogger logger = AbstractFactory.getLogger();
		ISocketProtocol protocol = AbstractFactory.getProtocol();
		
		if (args == null || args.length != 2){
			logger.logInfo("Port number is missing");
			System.exit(0);
		}
		
		
		int portNumber = Integer.parseInt(args[1]);
		String server = args[0]; 
		
		
		Client client = new Client(server, portNumber, logger, protocol);
		try {
			client.run();
		} catch (IOException e) {
			logger.logError(e);
			client.stop();
		}

	}

}
