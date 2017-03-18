package main;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import factory.MessageFactory;
import interfaces.IReadSocketListener;
import interfaces.IReadThread;
import type.ILogger;
import type.IMessage;
import type.ISocketProtocol;

public class ReadSocketThread extends Thread implements IReadThread {
	private final Socket socket;
	private BufferedReader in;
	private final IReadSocketListener listener;
	private final ISocketProtocol protocol;
	private final ILogger logger;
	private boolean run;
	
	public ReadSocketThread(Socket socket, IReadSocketListener listener, ILogger logger, ISocketProtocol protocol){
		this.socket = socket;
		this.listener = listener;
		this.protocol = protocol;
		this.logger = logger;
		this.run = true;
	}
	
	@Override
	public void run(){
			try {
				
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				IMessage msg = null;
				
				while(!socket.isClosed() && run){
					
					byte[] data = fetch();
					
					if (protocol.isHandShake(data)){
						
						msg = MessageFactory.getMessage(protocol.getMessageType(data));
						
					}else{
						
						if (msg != null){
							msg.setData(data);
							listener.printToScreen(msg);
							msg = null;
						}
					}
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
	
	private byte[] fetch() throws IOException {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		byte[] data = new byte[Constant.BUFFER_SIZE];
		int off = 0;
		
		while(true){
			int bytes = socket.getInputStream().read(data);
			if (bytes < 0) break;
			bs.write(data, off, bytes);
			
			if (bytes < Constant.BUFFER_SIZE) break;
		}
		
		return bs.toByteArray();
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