package socket;

import main.Util;
import model.BaseThread;
import type.ISocketProtocol;

import java.io.IOException;
import java.net.Socket;

public class WriteSocketThread extends BaseThread implements IWriteSocket {

    public WriteSocketThread(Socket socket, ISocketProtocol protocol) {
        super(socket, protocol);
    }

    @Override
    public void end() throws IOException {

    }

    @Override
    public void sendUserName(String userName) throws IOException {
        socket.getOutputStream().write(userName.getBytes(Util.getEncoding()));
    }
}
