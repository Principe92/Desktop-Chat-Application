package socket;

import type.IMessage;

import java.io.IOException;

public interface IWriteSocket {
    void end() throws IOException;
	
	void sendToSocket(IMessage msg) throws IOException;

    void sendUserName(String userName) throws IOException;
}
