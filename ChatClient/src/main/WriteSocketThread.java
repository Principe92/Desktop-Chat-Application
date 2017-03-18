package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import interfaces.IWriteSocketListener;
import interfaces.IWriteThread;
import type.ILogger;
import type.IMessage;
import type.ISocketProtocol;

public class WriteSocketThread implements IWriteThread {
	private final Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private final ILogger logger;
	private final ISocketProtocol protocol;
	
	public WriteSocketThread(Socket socket, ILogger logger, ISocketProtocol protocol){
		this.socket = socket;
		this.logger = logger;
		this.protocol = protocol;
		setUp();
	}
	
	public void setUp(){
			try {
				
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(System.in));
				
			} catch (IOException e) {
				logger.logError(e);
				logger.logInfo("The server has shutdown unexpectedly");
				try {
					exitChat();
				} catch (IOException f) {
					logger.logError(e);
				}
			}
		}

		private void exitChat() throws IOException {
			if (in != null) in.close();
			if (out != null) out.flush();
			if (out != null) out.close();
			if (socket != null) socket.close();
		}
		
		@Override
		public void end() throws IOException{
			exitChat();
		}

		@Override
		public void sendToSocket(IMessage msg) throws IOException {
			if (msg != null){
				byte[] handshake = protocol.getHandShake(msg);
				
				if (handshake != null){
					socket.getOutputStream().write(handshake);
					socket.getOutputStream().write(msg.getData());
				}
			}
		}
}
