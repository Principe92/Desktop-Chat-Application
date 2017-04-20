package listener;

import model.User;
import type.IChat;
import type.IMessage;

import java.awt.*;

/**
 * Created by okori on 06-Apr-17.
 */
public interface IChatListener {
    void printToScreen(IMessage msg);

    void changeChatTitle(String title, Point position);

    User getUser();

    void onChatStarted(IChat chat);
}
