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

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * Created by okori on 06-Apr-17.
 */
public class Chat implements IChat, IReadSocketListener {
    private final ILogger logger;
    private final ISocketProtocol protocol;
    private final IChatListener listener;
    private final Date date;
    private Socket socket;
    private IReadSocket readThread;
    private IWriteSocket writeThread;
    private Integer chatId;
    private Point position;

    public Chat(int chatId, ILogger logger, ISocketProtocol protocol, IChatListener listener) {
        this.logger = logger;
        this.chatId = chatId;
        this.protocol = protocol;
        this.listener = listener;
        this.date = new Date();

    }

    @Override
    public boolean start(String[] arg) throws IOException {
        String server = arg[0];
        int port = Integer.parseInt(arg[1]);

        socket = new Socket(server, port);
        readThread = new ReadSocketThread(socket, this, logger, protocol);
        writeThread = new WriteSocketThread(socket, protocol);
        writeThread.sendUserName(arg[2]);
        readThread.begin();

        return true;
    }

    @Override
    public void setGuiPosition(Point point) {
        this.position = point;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void close() throws IOException {
        writeThread.end();
        readThread.end();
        socket.close();
    }

    @Override
    public void sendToUsers(IMessage msg) throws IOException {
        writeThread.sendToSocket(msg);
    }

    public Integer getChatId() {
        return chatId;
    }

    @Override
    public void printToScreen(IMessage msg) {
        listener.printToScreen(msg);
    }

    @Override
    public void onChatExit() throws IOException {
        sendToUsers(new TextMessage("Quit chat room unexpectedly"));
        socket.close();
    }
}
