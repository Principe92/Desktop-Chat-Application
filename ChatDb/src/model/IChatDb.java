package model;

import type.IChat;
import type.IMessage;

import java.io.IOException;
import java.util.Collection;

public interface IChatDb {

    void createChat(IChat chat);

    void deleteChat(IChat activeChat);

    void saveMessage(IChat activeChat, IMessage msg, boolean fromUser);

    Collection<IMessage> getMessages(IChat chat) throws IOException;
}
