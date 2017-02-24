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
	private boolean run;
	
	public ReadSocketThread(Socket socket, IReadSocketListener listener){
		this.socket = socket;
		this.listener = listener;
		this.run = true;
	}
	
	@Override
	public void run(){
			try {
				
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				while(!socket.isClosed() && run){
					
					String message = in.readLine();
					
					listener.printToScreen(message);
				}
				
			} catch (IOException e) {
				System.out.println("The server has shutdown unexpectedly");
			}finally{
				try {
					exitChat();
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private void exitChat() throws IOException {
			in.close();
			socket.close();
			end();
		}
		
		@Override
		public void end(){
			run = false;
		}

		@Override
		public void begin() {
			this.start();
		}
}