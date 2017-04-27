package model;

import type.IChat;
import type.IMessage;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by okori on 26-Apr-17.
 */
public interface IChatFile {
    boolean delete();

    String getName();

    void insertChatInfo(IChat chat) throws IOException;

    void write(IMessage msg) throws IOException;

    Collection<IMessage> getMessages() throws IOException;
}
