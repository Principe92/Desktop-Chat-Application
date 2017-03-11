package main;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import model.Client;
import model.IClientListener;
import model.IClientThread;
import model.ILogger;

public class ClientThread extends Thread implements IClientThread {
	private final Socket socket;
	private final Client who;
	private IClientListener listener;
	private ILogger logger;
	private PrintWriter out;
	private BufferedReader in;
	
	public ClientThread (Socket socket, int id, IClientListener listener, ILogger logger){
		this.socket = socket;
		this.listener = listener;
		this.logger = logger;
		this.who = new Client(id);
		this.who.setName(String.format("User: %d", id));
	}
	
	@Override
	public void run(){
		
		try {
			
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			byte[] data = new byte[4096];
			
			while(!socket.isClosed()){
				
			//	String message = in.readLine();
				int off = 0;
				
				while(true){
					int bytes = socket.getInputStream().read(data);
					if (bytes < 0) break;
					bs.write(data, off, bytes);
					off = bytes;
					
					if (bytes < 4096) break;
				}
				
				listener.sendMessage(bs.toByteArray(), who.getId());
				bs.reset();
			}
			
		} catch (IOException e) {
		}finally{
			try {
				exitChat();
			} catch (IOException e) {
				logger.logError(e);
			}
		}
	}

	private void exitChat() throws IOException {
		in.close();
		out.flush();
		out.close();
		socket.close();
	
		String msg = String.format("%s has disconnected", who.getName());
		listener.sendMessage(msg.getBytes(Util.getEncoding()), who.getId());
		listener.removeClient(who.getId());
	}

	@Override
	public void sendMessageToSocket(byte[] message) {
		if (message != null){
			try {
				socket.getOutputStream().write(message);
			} catch (IOException e) {
				logger.logError(e);
			}
		}
	}
	
	@Override
	public int getClientId(){
		return this.who.getId();
	}

	@Override
	public void startThread() {
		this.start();
	}

	@Override
	public Client getClient() {
		return who;
	}

}
