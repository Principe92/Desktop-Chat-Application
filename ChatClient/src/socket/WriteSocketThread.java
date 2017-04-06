package socket;

import model.BaseThread;
import type.ILogger;
import type.ISocketProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WriteSocketThread extends BaseThread implements IWriteSocket {
    private final ILogger logger;
    private final ISocketProtocol protocol;
    private PrintWriter out;
    private BufferedReader in;

    public WriteSocketThread(Socket socket, ILogger logger, ISocketProtocol protocol) {
        super(socket, protocol);
        this.logger = logger;
        this.protocol = protocol;
        setUp();
    }

    public void setUp() {
        try {

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(System.in));

        } catch (IOException e) {
            logger.logError(e);
            logger.logInfo("The server has shutdown unexpectedly");
            try {
                exitChat();
            } catch (IOException f) {
                logger.logError(e);
            }
        }
    }

    private void exitChat() throws IOException {
        if (in != null) in.close();
        if (out != null) out.flush();
        if (out != null) out.close();
        if (socket != null) socket.close();
    }

    @Override
    public void end() throws IOException {
        exitChat();
    }
}
