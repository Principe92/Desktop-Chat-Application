package listener;

import type.IMessage;

import java.io.IOException;

public interface IClientListener {

    void msgFromUser(IMessage msg, int senderId) throws IOException;

    void removeClient(int id);

}
