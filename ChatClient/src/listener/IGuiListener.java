package listener;

import model.User;
import type.IMessage;

public interface IGuiListener {

    void sendMessage(IMessage message);

    boolean joinChat(String ip, String port);

    boolean createChat(String title, String port);

    void quitChat();

    void loadChat(int chatHash);

    boolean IsChatAvailable();

    User getUser();
}
