package model;

import listener.IChatListener;
import listener.IReadSocketListener;
import socket.IReadSocket;
import socket.IWriteSocket;
import socket.ReadSocketThread;
import socket.WriteSocketThread;
import type.IChat;
import type.ILogger;
import type.IMessage;
import type.ISocketProtocol;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by okori on 06-Apr-17.
 */
public class Chat implements IChat, IReadSocketListener {
    private final ILogger logger;
    private final ISocketProtocol protocol;
    private final IChatListener listener;
    private Socket socket;
    private IReadSocket readThread;
    private IWriteSocket writeThread;
    private Integer id;

    public Chat(int id, ILogger logger, ISocketProtocol protocol, IChatListener listener) {
        this.logger = logger;
        this.id = id;
        this.protocol = protocol;
        this.listener = listener;
    }

    @Override
    public boolean start(String[] arg) throws IOException {
        String server = arg[0];
        int port = Integer.parseInt(arg[1]);

        socket = new Socket(server, port);
        readThread = new ReadSocketThread(socket, this, logger, protocol);
        writeThread = new WriteSocketThread(socket, logger, protocol);
        readThread.begin();

        return true;
    }

    public void stop() throws IOException {
        socket.close();
        readThread.end();
        readThread.end();
    }

    @Override
    public void sendToUsers(IMessage msg) throws IOException {
        writeThread.sendToSocket(msg);
    }

    public Integer getId() {
        return id;
    }

    @Override
    public void printToScreen(IMessage msg) {
        listener.printToScreen(msg);
    }

    @Override
    public void close() throws IOException {
        socket.close();
        readThread.end();
        readThread.end();
    }
}
