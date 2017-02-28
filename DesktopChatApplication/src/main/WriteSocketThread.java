package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import interfaces.IWriteSocketListener;
import interfaces.IWriteThread;

public class WriteSocketThread implements IWriteThread {
	private final Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private IWriteSocketListener listener;
	private boolean run;
	
	public WriteSocketThread(Socket socket, IWriteSocketListener listener){
		this.socket = socket;
		this.listener = listener;
		this.run = true;
		setUp();
	}
	
	public void setUp(){
			try {
				
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(System.in));
				
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("The server has shutdown unexpectedly");
				try {
					exitChat();
					return;
				} catch (IOException f) {
					// TODO Auto-generated catch block
					f.printStackTrace();
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
		public void end(){
			try {
				exitChat();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void sendToSocket(String text) {
			if (!Util.isNullOrEmpty(text)){
				out.println(text);
			}
			
		}
}
