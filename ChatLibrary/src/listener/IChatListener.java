package listener;

import model.User;
import type.IChat;
import type.IMessage;

public interface IChatListener {
    void printToScreen(IMessage msg, int port);

    User getUser();

    void onChatStarted(IChat chat);

    void onServerClosed();
}
