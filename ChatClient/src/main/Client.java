package main;

import java.io.IOException;
import java.net.Socket;

import interfaces.IGuiListener;
import interfaces.IReadSocketListener;
import interfaces.IReadThread;
import interfaces.IWriteSocketListener;
import interfaces.IWriteThread;
import model.ILogger;

public class Client implements IWriteSocketListener, IReadSocketListener, IGuiListener {
	private final String server;
	private final int port;
	private IReadThread readThread;
	private IWriteThread writeThread;
	private Socket socket;
	private gui gui;
	private final ILogger logger;
	
	
	public Client(String server, int portNumber, ILogger logger) {
		this.server = server;
		this.port = portNumber;
		this.logger = logger;
		this.socket = null;
	}

	public void run() throws IOException {	
			socket = new Socket(server, port);
			readThread = new ReadSocketThread(socket, this, logger);
			writeThread = new WriteSocketThread(socket, logger);
			readThread.begin();
			
			//load GUI
			gui = new gui(this, logger);
	}
	
	public void stop() throws IOException{
		socket.close();
		readThread.end();
		readThread.end();
	}

	//TODO: Account for different types of messages
	@Override
	public void printToScreen(byte[] msg) {
		if (msg != null){
		gui.displayMessage(new String(msg, Util.getEncoding()));
		}
	}

	@Override
	public void sendText(byte[] msg) throws IOException {
		writeThread.sendToSocket(msg);
	}

	@Override
	public void close() {
		gui.close();
		
	}

}
