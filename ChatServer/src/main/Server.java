package main;

import listener.IChatListener;
import listener.IClientListener;
import model.IClient;
import model.TextMessage;
import model.User;
import type.IChat;
import type.ILogger;
import type.IMessage;
import type.ISocketProtocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Server implements IClientListener, IChat {

    private final ILogger logger;
    private final IChatListener listener;
    private final ISocketProtocol protocol;
    int counter;
    private Map<Integer, IClient> clients;
    private ServerSocket socket;
    private Integer id;

    public Server(Integer id, ILogger logger, IChatListener listener, ISocketProtocol protocol) {
        this.id = id;
        this.listener = listener;
        this.protocol = protocol;
        this.clients = new HashMap<>();
        this.logger = logger;
    }

    void run(int port) throws IOException {
        socket = new ServerSocket(port);

        while (!socket.isClosed()) {
            IClient client = new Client(socket.accept(), counter++, this, logger, protocol);
            client.startThread();
            clients.put(client.getClientId(), client);

            User who = client.getClient();
            String probe = String.format("%s has connected", who.getName());
            String connected = "Connected to chat";

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        client.sendToSocket(new TextMessage(connected));
                        msgFromUser(new TextMessage(probe), who.getId());
                    } catch (IOException e) {
                        logger.logError(e);
                    }
                }
            }).start();
        }
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
        if (socket != null) socket.close();
    }

    @Override
    public void sendToUsers(IMessage msg) throws IOException {

        for (Entry<Integer, IClient> entry : clients.entrySet()) {
            entry.getValue().sendToSocket(msg);
        }
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean start(String[] args) throws IOException {

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
}
