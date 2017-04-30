package main;

import listener.IChatListener;
import listener.IClientListener;
import model.IClient;
import model.QuitMessage;
import model.TextMessage;
import type.IChat;
import type.ILogger;
import type.IMessage;
import type.ISocketProtocol;

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
    private int guiId;
    private String title;
    private int port;
    private boolean triggeredClose;
    private String ip;

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
        this.ip = socket.getInetAddress().getHostAddress();
        listener.onChatStarted(this);

        while (!socket.isClosed()) {
            IClient client = new Client(socket.accept(), counter++, this, logger, protocol);
            client.setUp(title);
            client.start();
            clients.put(client.getChatId(), client);

            logger.logInfo("Added new client");
            advertise(client);
        }
    }

    private void advertise(IClient client) {
        String probe = String.format("%s has connected", client.getUserName());
        try {
            msgFromUser(new TextMessage(probe), client.getChatId());
        } catch (IOException e) {

            if (!triggeredClose) logger.logError(e);
        }
    }

    @Override
    public void msgFromUser(IMessage message, int id) throws IOException {

        if (message != null) {

            for (Entry<Integer, IClient> entry : clients.entrySet()) {
                if (entry.getKey() != id)
                    entry.getValue().sendToSocket(message);
            }

            listener.printToScreen(message, port);
        }
    }

    @Override
    public void removeClient(int id) {
        clients.remove(id);
    }

    @Override
    public void close() throws IOException {
        sendToUsers(new QuitMessage("Closing chat room"));

        this.triggeredClose = true;
        if (socket != null) {

            for (Entry<Integer, IClient> entry : clients.entrySet()) {
                entry.getValue().close();
            }

            socket.close();
        }
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
                    if (!triggeredClose)
                        logger.logError(e);
                }
            }
        });

        td.start();
        return td.isAlive();
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
    public String getIp() {
        return ip;
    }
}
