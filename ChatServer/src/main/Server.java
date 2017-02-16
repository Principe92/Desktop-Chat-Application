package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Server implements IClientListener {

	private int port;
	private List<IClientThread> clients;

	public Server(int portNumber) {
		this.port = portNumber;
		this.clients = new ArrayList<>();
	}

	public void run() {
		try {
			ServerSocket socket = new ServerSocket(port);
			
			while (true){
				IClientThread client = new ClientThread(socket.accept(), this);
				client.startThread();
				clients.add(client);
			}
			
		} catch (IOException e) {
			
			System.out.printf(Locale.getDefault(), "An exception has occurred: %s", e.getMessage());
		}
		
	}

	@Override
	public void sendMessage(String message) {
		
		for(IClientThread client : clients){
			client.sendMessageToSocket(message);
		}
		
	}

}
