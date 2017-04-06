package listener;

import type.IMessage;

import java.io.IOException;

public interface IReadSocketListener {
    void printToScreen(IMessage msg);

    void close() throws IOException;
}
