package main;


import factory.MessageFactory;
import listener.IClientListener;
import model.BaseThread;
import model.IClient;
import model.TextMessage;
import type.ILogger;
import type.IMessage;
import type.ISocketProtocol;

import java.io.IOException;
import java.net.Socket;

class Client extends BaseThread implements IClient {
    private final IClientListener listener;
    private final ISocketProtocol protocol;
    private final int chatId;
    private ILogger logger;
    private String who;

    Client(Socket socket, int id, IClientListener listener, ILogger logger, ISocketProtocol protocol) {
        super(socket, protocol);
        this.listener = listener;
        this.logger = logger;
        this.chatId = id;
        this.protocol = protocol;
    }

    @Override
    public void run() {

        try {

            IMessage msg = null;

            while (!socket.isClosed()) {

                byte[] data = fetch();

                if (protocol.isHandShake(data)) {

                    msg = MessageFactory.getMessage(protocol.getMessageType(data));

                    if (msg != null) {
                        msg.setSender(protocol.getSender(data));
                    }

                } else {

                    if (msg != null) {
                        msg.setData(data);
                        listener.msgFromUser(msg, chatId);
                        msg = null;
                    }
                }
            }

        } catch (IOException ignored) {
            try {
                exitChat();
            } catch (IOException e) {
                logger.logError(e);
            }
        }
    }

    @Override
    public void setUp(String title) throws IOException {
        // get client's name
        byte[] data = fetch();
        who = new String(data, Util.getEncoding());

        // send the chat title
        socket.getOutputStream().write(title.getBytes(Util.getEncoding()));
    }

    private void exitChat() throws IOException {
        socket.close();

        String msg = String.format("%s has disconnected", who);
        listener.msgFromUser(new TextMessage(msg), chatId);
        listener.removeClient(chatId);
    }


    @Override
    public int getChatId() {
        return this.chatId;
    }


    @Override
    public String getUserName() {
        return who;
    }

}
