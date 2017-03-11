package main;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import interfaces.IReadSocketListener;
import interfaces.IReadThread;
import model.ILogger;

public class ReadSocketThread extends Thread implements IReadThread {
	private final Socket socket;
	private BufferedReader in;
	private final IReadSocketListener listener;
	private final ILogger logger;
	private boolean run;
	
	public ReadSocketThread(Socket socket, IReadSocketListener listener, ILogger logger){
		this.socket = socket;
		this.listener = listener;
		this.logger = logger;
		this.run = true;
	}
	
	@Override
	public void run(){
			try {
				
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				ByteArrayOutputStream bs = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];
				
				while(!socket.isClosed() && run){
					
					int offset = 0;
					
					while(true){
						int bytes = socket.getInputStream().read(buffer);
						if (bytes < 0) break;
						bs.write(buffer, offset, bytes);
						offset = bytes;
						
						if (bytes < 4096) break;
					}
					
					logger.logInfo("Size of array: " + bs.size());
					if (bs.size() > 0)
					listener.printToScreen(bs.toByteArray());
					
					bs.reset();
				}
				
			} catch (IOException e) {
				logger.logInfo("The server has shutdown unexpectedly");
				logger.logError(e);
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
			socket.close();
			end();
			
			listener.close();
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