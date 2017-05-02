package model;

import type.IChat;
import type.IMessage;

import java.io.IOException;
import java.util.Collection;

public interface IChatFile {
    boolean delete();

    String getName();

    void insertChatInfo(IChat chat) throws IOException;

    void write(IMessage msg, boolean fromUser) throws IOException;

    Collection<IMessage> getMessages() throws IOException;
}
