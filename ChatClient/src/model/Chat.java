package model;

import listener.IChatListener;
import listener.IReadSocketListener;
import main.Constant;
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
    private int guiId;
    private String title;
    private String user;
    private int port;
    private String ip;
    private boolean triggeredClose;

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
        this.port = Integer.parseInt(arg[1]);
        this.ip = server;

        socket = new Socket(server, port);
        readThread = new ReadSocketThread(socket, this, logger, protocol);
        writeThread = new WriteSocketThread(socket, protocol);
        writeThread.sendUserName(listener.getUser().getNameOrNick());
        readThread.start();

        return true;
    }

    @Override
    public int getGuiId() {
        return guiId;
    }

    @Override
    public void setGuiId(int guiId) {
        this.guiId = guiId;
    }

    @Override
    public void onChatStarted() {
        listener.onChatStarted(this);
        printToScreen(new TextMessage("Connected to chat"));
    }

    @Override
    public String getChatTitle() {
        return title;
    }

    @Override
    public void setChatTitle(String title) {
        this.title = title;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public Date getCreationDate() {
        return date;
    }

    @Override
    public void close() throws IOException {
        sendToUsers(new QuitMessage());
        triggeredClose = true;

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
        listener.printToScreen(msg, port);
    }

    @Override
    public void onChatExit() throws IOException {
        if (!triggeredClose) {
            printToScreen(new TextMessage(Constant.SERVER_ERROR));

            socket.close();
            listener.onServerClosed();
        }
    }

    @Override
    public String getIp() {
        return ip;
    }
}
