package main;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		if (args == null || args.length != 2){
			System.out.println("Port number is missing");
			System.exit(0);
		}
		
		
		int portNumber = Integer.parseInt(args[1]);
		String server = args[0]; 
		
		
		Client client = new Client(server, portNumber);
		try {
			client.run();
		} catch (IOException e) {
			e.printStackTrace();
			client.stop();
		}

	}

}
