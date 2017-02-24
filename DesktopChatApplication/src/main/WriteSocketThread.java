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
	private boolean run;
	
	public WriteSocketThread(Socket socket, IWriteSocketListener listener){
		this.socket = socket;
		this.listener = listener;
		this.run = true;
	}
	
	@Override
	public void run(){
			try {
				
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(System.in));
				
				while(!socket.isClosed() && run){
					
					String message = in.readLine();
					
					if (!Util.isNullOrEmpty(message)){
						out.println(message);
					}
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
			if (in != null) in.close();
			if (out != null) out.flush();
			if (out != null) out.close();
			if (socket != null) socket.close();
		}

		@Override
		public void begin() {
			this.start();
		}
		
		@Override
		public void end(){
			run = false;
		}
}
