package main;

import java.io.IOException;
import java.net.Socket;

import interfaces.IReadSocketListener;
import interfaces.IReadThread;
import interfaces.IWriteSocketListener;
import interfaces.IWriteThread;

public class Client implements IWriteSocketListener, IReadSocketListener, IGuiListener {
	private final String server;
	private final int port;
	private IReadThread readThread;
	private IWriteThread writeThread;
	private Socket socket;
	private gui gui;
	
	//private Client client;
	
	/*
	private static final Client instance = new Client(server, port);
	
	//private static final Client instance = new Client(String server, int portNumber)
	
	public static Client getInstance() {
		return Client 
		//return instance;
	}
	*/
	
	public Client(String server, int portNumber) {
		this.server = server;
		this.port = portNumber;
		this.socket = null;
	}

	public void run() throws IOException {	
		try {
			socket = new Socket(server, port);
			readThread = new ReadSocketThread(socket, this);
			writeThread = new WriteSocketThread(socket, this);
			readThread.begin();
			
			//load gui

			gui = new gui(this);	
			//System.out.println("gui is working");
			
		} catch (IOException e) {
			e.printStackTrace();
			stop();
		}
	}
	
	public void stop() throws IOException{
		socket.close();
		readThread.end();
		readThread.end();
	}

	@Override
	public void printToScreen(String message) {
		if (!Util.isNullOrEmpty(message)){
		System.out.println(message);
		gui.displayMessage(message);
		}
	}

	@Override
	public void sendText(String text) {
		writeThread.sendToSocket(text);
	}

}
