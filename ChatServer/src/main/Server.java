package main;

import listener.IChatListener;
import listener.IClientListener;
import model.IClient;
import model.TextMessage;
import type.IChat;
import type.ILogger;
import type.IMessage;
import type.ISocketProtocol;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Server implements IClientListener, IChat {

    private final ILogger logger;
    private final IChatListener listener;
    private final ISocketProtocol protocol;
    private final Date date;
    private int counter;
    private Map<Integer, IClient> clients;
    private ServerSocket socket;
    private Integer chatId;
    private Point position;
    private String title;
    private int port;

    public Server(Integer id, ILogger logger, IChatListener listener, ISocketProtocol protocol) {
        this.chatId = id;
        this.listener = listener;
        this.protocol = protocol;
        this.clients = new HashMap<>();
        this.logger = logger;
        this.date = new Date();
    }

    void run(int port) throws IOException {
        this.port = port;
        socket = new ServerSocket(port);
        listener.onChatStarted(this);

        while (!socket.isClosed()) {
            IClient client = new Client(socket.accept(), counter++, this, logger, protocol);
            client.setUp(title);
            client.start();
            clients.put(client.getChatId(), client);

            advertise(client);
        }
    }

    private void advertise(IClient client) {
        String probe = String.format("%s has connected", client.getUserName());
        String connected = "Connected to chat";

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    client.sendToSocket(new TextMessage(connected));
                    msgFromUser(new TextMessage(probe), client.getChatId());
                } catch (IOException e) {
                    logger.logError(e);
                }
            }
        }).start();
    }

    @Override
    public void msgFromUser(IMessage message, int id) throws IOException {

        if (message != null) {

            for (Entry<Integer, IClient> entry : clients.entrySet()) {
                if (entry.getKey() != id)
                    entry.getValue().sendToSocket(message);

            }

            listener.printToScreen(message);
        }
    }

    @Override
    public void removeClient(int id) {
        clients.remove(id);
    }

    @Override
    public void close() throws IOException {
        sendToUsers(new TextMessage("Closing chat room"));

        if (socket != null) socket.close();
    }

    @Override
    public void sendToUsers(IMessage msg) throws IOException {

        for (Entry<Integer, IClient> entry : clients.entrySet()) {
            entry.getValue().sendToSocket(msg);
        }
    }

    @Override
    public Integer getChatId() {
        return chatId;
    }

    @Override
    public boolean start(final String[] args) throws IOException {

        Thread td = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Server.this.run(Integer.parseInt(args[0]));

                } catch (IOException e) {
                    logger.logError(e);
                }
            }
        });

        td.start();
        return td.isAlive();
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
}
