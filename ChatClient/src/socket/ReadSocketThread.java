package socket;

import factory.MessageFactory;
import listener.IReadSocketListener;
import main.Util;
import model.BaseThread;
import type.ILogger;
import type.IMessage;
import type.ISocketProtocol;
import type.MessageType;

import java.io.IOException;
import java.net.Socket;

public class ReadSocketThread extends BaseThread implements IReadSocket {
    private final IReadSocketListener listener;
    private final ISocketProtocol protocol;
    private final ILogger logger;
    private boolean run;

    public ReadSocketThread(Socket socket, IReadSocketListener listener, ILogger logger, ISocketProtocol protocol) {
        super(socket, protocol);
        this.listener = listener;
        this.protocol = protocol;
        this.logger = logger;
        this.run = true;
    }

    @Override
    public void run() {
        try {
            IMessage msg = null;

            setUp();

            while (!socket.isClosed() && run) {

                byte[] data = fetch();

                if (protocol.isHandShake(data)) {

                    msg = MessageFactory.getMessage(protocol.getMessageType(data));

                    if (msg != null) {
                        msg.setSender(protocol.getSender(data));
                    }

                } else {

                    if (msg != null) {
                        msg.setData(data);
                        listener.printToScreen(msg);

                        if (msg.getType() == MessageType.QUIT) exitChat();

                        msg = null;
                    }
                }
            }

        } catch (IOException e) {
            try {
                exitChat();
            } catch (IOException ignored) {

            }
        }
    }

    private void setUp() {
        // Get chat title
        try {
            byte[] data = fetch();
            listener.setChatTitle(new java.lang.String(data, Util.getEncoding()));
            listener.onChatStarted();
        } catch (IOException e) {
            logger.logError(e);
        }
    }

    private void exitChat() throws IOException {
        end();
        listener.onChatExit();
    }

    @Override
    public void end() {
        run = false;
    }
}