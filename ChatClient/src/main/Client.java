package main;

import interfaces.*;
import type.ILogger;
import type.IMessage;
import type.ISocketProtocol;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class Client implements IWriteSocketListener, IReadSocketListener, IGuiListener {
	private final String server;
	private final int port;
	private final ILogger logger;
	private final ISocketProtocol protocol;
	private IReadThread readThread;
	private IWriteThread writeThread;
	private Socket socket;
	private gui gui;
	
	
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

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}

				gui = new gui(Client.this, logger);
			}
		});
		//load GUI
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
