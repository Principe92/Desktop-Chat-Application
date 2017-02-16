package main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread implements IClientThread {
	private final Socket socket;
	private final Client profile;
	private IClientListener listener;
	private PrintWriter out;
	private BufferedReader in;
	
	public ClientThread (Socket socket, IClientListener listener){
		this.socket = socket;
		this.listener = listener;
		this.profile = new Client();
	}
	
	@Override
	public void run(){
		
		try {
			
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while(!socket.isClosed()){
				
				String message = in.readLine();
				
				if (message != null && !message.isEmpty()){
					message = String.format("%s: ", profile.getUserName());
					if (listener != null) listener.sendMessage(message);
				}
			}
			
			exitChat();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void exitChat() throws IOException {
		in.close();
		out.flush();
		out.close();
		socket.close();
		
		if (listener != null){
			listener.sendMessage(String.format("%s has disconnected", profile.getUserName()));
		}
	}

	@Override
	public void sendMessageToSocket(String message) {
		if (!Util.isNullOrEmpty(message)){
			out.println(message);
		}
	}

	@Override
	public void startThread() {
		this.start();
	}

}
