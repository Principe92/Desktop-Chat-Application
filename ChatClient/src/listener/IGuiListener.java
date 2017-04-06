package listener;

import type.IMessage;

import java.io.IOException;

public interface IGuiListener {

    void sendMessage(IMessage message) throws IOException;

    boolean joinChat(String ip, String port);

    boolean createChat(String title, String port);
}
