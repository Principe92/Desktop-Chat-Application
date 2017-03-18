package interfaces;

import java.io.IOException;

import type.IMessage;

public interface IWriteThread {
	void end() throws IOException;
	
	void sendToSocket(IMessage msg) throws IOException;

} 
