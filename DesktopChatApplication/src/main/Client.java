package main;

import java.io.IOException;
import java.net.Socket;

import interfaces.IReadSocketListener;
import interfaces.IReadThread;
import interfaces.IWriteSocketListener;
import interfaces.IWriteThread;

public class Client implements IWriteSocketListener, IReadSocketListener {
	private final String server;
	private final int port;
	private IReadThread readThread;
	private IWriteThread writeThread;
	
	public Client(String server, int portNumber) {
		// TODO Auto-generated constructor stub
		this.server = server;
		this.port = portNumber;
	}

	public void run() {
		
		try {
			Socket socket = new Socket(server, port);
			readThread = new ReadSocketThread(socket, this);
			writeThread = new WriteSocketThread(socket, this);
			readThread.startThread();
			writeThread.startThread();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void printToScreen(String message) {
		System.out.println(message);
		
	}

}
