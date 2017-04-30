package listener;

import model.User;
import type.IChat;
import type.IMessage;

/**
 * Created by okori on 06-Apr-17.
 */
public interface IChatListener {
    void printToScreen(IMessage msg, int port);

    User getUser();

    void onChatStarted(IChat chat);

    void onServerClosed();
}
