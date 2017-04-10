package main;


import factory.MessageFactory;
import listener.IClientListener;
import model.BaseThread;
import model.IClient;
import model.TextMessage;
import model.User;
import type.ILogger;
import type.IMessage;
import type.ISocketProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Client extends BaseThread implements IClient {
    private final User who;
    private final IClientListener listener;
    private final ISocketProtocol protocol;
    private ILogger logger;
    private PrintWriter out;
    private BufferedReader in;

    Client(Socket socket, int id, IClientListener listener, ILogger logger, ISocketProtocol protocol) {
        super(socket, protocol);
        this.listener = listener;
        this.logger = logger;
        this.who = new User(id);
        this.protocol = protocol;
    }

    @Override
    public void run() {

        try {

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            IMessage msg = null;

            while (!socket.isClosed()) {

                byte[] data = fetch();

                if (protocol.isHandShake(data)) {

                    msg = MessageFactory.getMessage(protocol.getMessageType(data));

                } else {

                    if (msg != null) {
                        msg.setData(data);
                        msg.setSender(who.getName());
                        listener.msgFromUser(msg, who.getId());
                        msg = null;
                    }
                }
            }

        } catch (IOException ignored) {
        } finally {
            try {
                exitChat();
            } catch (IOException e) {
                logger.logError(e);
            }
        }
    }

    private void exitChat() throws IOException {
        in.close();
        out.flush();
        out.close();
        socket.close();

        String msg = String.format("%s has disconnected", who.getName());
        listener.msgFromUser(new TextMessage(msg), who.getId());
        listener.removeClient(who.getId());
    }


    @Override
    public int getClientId() {
        return this.who.getId();
    }

    @Override
    public void startThread() {
        this.start();
    }

    @Override
    public User getClient() {
        return who;
    }

}
