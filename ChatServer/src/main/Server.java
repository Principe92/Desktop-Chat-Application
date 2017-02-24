package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class Server implements IClientListener {

	private int port;
	int counter;
	private Map<Integer, IClientThread> clients;

	public Server(int portNumber) {
		this.port = portNumber;
		this.clients = new HashMap<>();
	}

	public void run() {
		try {
			ServerSocket socket = new ServerSocket(port);
			
			while (true){
				IClientThread client = new ClientThread(socket.accept(), counter++, this);
				client.startThread();
				clients.put(client.getClientId(), client);
			}
			
		} catch (IOException e) {
			
			System.out.printf(Locale.getDefault(), "An exception has occurred: %s", e.getMessage());
		}
		
	}

	@Override
	public void sendMessage(String message, int id) {
		Iterator<Entry<Integer, IClientThread>> it = clients.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<Integer, IClientThread> entry = it.next();
			
			if (entry.getKey() != id)
			entry.getValue().sendMessageToSocket(message);
		}
		
	}

	@Override
	public void removeClient(int id) {
		clients.remove(id);
		//TODO: Change data storage structure
	}

}
