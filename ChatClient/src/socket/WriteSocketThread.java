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
    private PrintWriter out;
    private BufferedReader in;

    public WriteSocketThread(Socket socket, ILogger logger, ISocketProtocol protocol) {
        super(socket, protocol);
        this.logger = logger;
        setUp();
    }

    public void setUp() {
        try {

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(System.in));

        } catch (IOException e) {
            logger.logError(e);
            logger.logInfo("Unable to get right to write to socket");
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
    }

    @Override
    public void end() throws IOException {
        exitChat();
    }
}
