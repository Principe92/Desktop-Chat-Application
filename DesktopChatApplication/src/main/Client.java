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
	private Socket socket;
	
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
			writeThread.begin();
			
		} catch (IOException e) {
			stop();
		}
	}
	
	public void stop() throws IOException{
		socket.close();
		readThread.end();
		readThread.end();
	}

	@Override
	public void printToScreen(String message) { /*This is what we would want displayed on our GUI???*/
		if (!Util.isNullOrEmpty(message))
		System.out.println(message);
	}

}
