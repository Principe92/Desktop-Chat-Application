package listener;

import type.IMessage;

public interface IGuiListener {

    void sendMessage(IMessage message);

    boolean joinChat(String ip, String port);

    boolean createChat(String title, String port);

    void quitChat();
}
