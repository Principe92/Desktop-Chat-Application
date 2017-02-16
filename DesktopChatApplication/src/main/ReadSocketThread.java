package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import interfaces.IReadSocketListener;
import interfaces.IReadThread;

public class ReadSocketThread extends Thread implements IReadThread {
	private final Socket socket;
	private BufferedReader in;
	private IReadSocketListener listener;
	
	public ReadSocketThread(Socket socket, IReadSocketListener listener){
		this.socket = socket;
		this.listener = listener;
	}
	
	@Override
	public void run(){
			try {
				
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				while(!socket.isClosed()){
					
					String message = in.readLine();
					
					if (Util.isNullOrEmpty(message)){
						listener.printToScreen(message);
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
			socket.close();
		}

		@Override
		public void startThread() {
			this.start();
		}
}