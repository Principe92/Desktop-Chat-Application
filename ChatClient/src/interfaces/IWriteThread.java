package interfaces;

import java.io.IOException;

public interface IWriteThread {
	void end() throws IOException;
	
	void sendToSocket(byte[] msg) throws IOException;

} 
