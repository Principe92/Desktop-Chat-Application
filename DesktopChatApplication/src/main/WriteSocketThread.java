package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import interfaces.IWriteSocketListener;
import interfaces.IWriteThread;

public class WriteSocketThread extends Thread implements IWriteThread {
	private final Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private IWriteSocketListener listener;
	
	public WriteSocketThread(Socket socket, IWriteSocketListener listener){
		this.socket = socket;
		this.listener = listener;
	}
	
	@Override
	public void run(){
			try {
				
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(System.in));
				
				while(!socket.isClosed()){
					
					String message = in.readLine();
					
					if (Util.isNullOrEmpty(message)){
						out.println(message);
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
		}

		@Override
		public void startThread() {
			this.start();
		}
}
