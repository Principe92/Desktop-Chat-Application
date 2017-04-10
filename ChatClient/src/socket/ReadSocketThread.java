package socket;

import factory.MessageFactory;
import listener.IReadSocketListener;
import main.Constant;
import model.BaseThread;
import model.TextMessage;
import type.ILogger;
import type.IMessage;
import type.ISocketProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadSocketThread extends BaseThread implements IReadSocket {
    private final IReadSocketListener listener;
    private final ISocketProtocol protocol;
    private final ILogger logger;
    private BufferedReader in;
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

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            IMessage msg = null;

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
                        msg = null;
                    }
                }
            }

        } catch (IOException e) {
            listener.printToScreen(new TextMessage(Constant.SERVER_ERROR));
            logger.logInfo("The server has shutdown unexpectedly");
            logger.logError(e);
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
        end();

        listener.onChatExit();
    }

    @Override
    public void end() {
        run = false;
    }

    @Override
    public void begin() {
        this.start();
    }
}