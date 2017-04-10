package model;

import main.Constant;
import type.IMessage;
import type.ISocketProtocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by okori on 06-Apr-17.
 */
public class BaseThread extends Thread {
    protected final Socket socket;
    private final ISocketProtocol protocol;

    public BaseThread(Socket socket, ISocketProtocol protocol) {
        this.socket = socket;
        this.protocol = protocol;
    }

    protected byte[] fetch() throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        byte[] data = new byte[Constant.BUFFER_SIZE];
        int off = 0;

        while (true) {
            int bytes = socket.getInputStream().read(data);
            if (bytes < 0) break;
            bs.write(data, off, bytes);

            if (bytes < Constant.BUFFER_SIZE) break;
        }

        return bs.toByteArray();
    }

    public void sendToSocket(IMessage msg) throws IOException {
        if (msg != null) {
            byte[] handshake = protocol.getHandShake(msg);

            if (handshake != null) {
                socket.getOutputStream().write(handshake);
                socket.getOutputStream().write(msg.getData());
            }
        }
    }
}
