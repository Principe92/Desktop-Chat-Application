package main;

import java.io.IOException;
import java.net.Socket;

import interfaces.IGuiListener;
import interfaces.IReadSocketListener;
import interfaces.IReadThread;
import interfaces.IWriteSocketListener;
import interfaces.IWriteThread;
import type.ILogger;
import type.IMessage;
import type.ISocketProtocol;

public class Client implements IWriteSocketListener, IReadSocketListener, IGuiListener {
	private final String server;
	private final int port;
	private IReadThread readThread;
	private IWriteThread writeThread;
	private Socket socket;
	private gui gui;
	private final ILogger logger;
	private final ISocketProtocol protocol;
	
	
	public Client(String server, int portNumber, ILogger logger, ISocketProtocol protocol) {
		this.server = server;
		this.port = portNumber;
		this.logger = logger;
		this.protocol = protocol;
		this.socket = null;
	}

	public void run() throws IOException {	
			socket = new Socket(server, port);
			readThread = new ReadSocketThread(socket, this, logger, protocol);
			writeThread = new WriteSocketThread(socket, logger, protocol);
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
	public void printToScreen(IMessage msg) {
		if (msg != null){
			gui.displayMessage(msg);
		}
	}

	@Override
	public void sendText(IMessage msg) throws IOException {
		writeThread.sendToSocket(msg);
	}

	@Override
	public void close() {
		gui.close();
		
	}

}
