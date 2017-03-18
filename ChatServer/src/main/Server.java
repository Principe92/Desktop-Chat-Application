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

import model.Client;
import model.IClientListener;
import model.IClientThread;
import type.ILogger;

public class Server implements IClientListener {

	private final int port;
	private final ILogger logger;
	int counter;
	private Map<Integer, IClientThread> clients;
	private ServerSocket socket;

	public Server(int portNumber, ILogger logger) {
		this.port = portNumber;
		this.clients = new HashMap<>();
		this.logger = logger;
	}

	public void run() throws IOException {
		
			socket = new ServerSocket(port);
			
			while (!socket.isClosed()){
				IClientThread client = new ClientThread(socket.accept(), counter++, this, logger);
				client.startThread();
				clients.put(client.getClientId(), client);
				
				Client who = client.getClient();
				String probe = String.format("%s has connected", who.getName());
				sendMessage(probe.getBytes(Util.getEncoding()), who.getId());
			}
	}

	@Override
	public void sendMessage(byte[] message, int id) {
		Iterator<Entry<Integer, IClientThread>> it = clients.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<Integer, IClientThread> entry = it.next();
			
			if (entry.getKey() != id)
				entry.getValue().sendMessageToSocket(message);
			else
				logger.logInfo(new String(message, Util.getEncoding()));
		}
	}

	@Override
	public void removeClient(int id) {
		clients.remove(id);
	}

	public void close() throws IOException {
		if (socket != null) socket.close();
	}

}
