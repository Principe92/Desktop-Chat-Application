package listener;

import type.IMessage;

import java.awt.*;

public interface IGuiListener {

    void sendMessage(IMessage message);

    boolean joinChat(String ip, String port);

    boolean createChat(String title, String port);

    void quitChat();

    void loadChat(Point point);
}
