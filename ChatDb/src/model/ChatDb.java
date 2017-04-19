package model;

import type.IChat;
import type.IMessage;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by okori on 19-Apr-17.
 */
public class ChatDb implements IChatDb {

    private static IChatDb db;

    public static IChatDb Instance() {
        return db == null ? (db = new ChatDb()) : db;
    }

    @Override
    public void createChat(IChat chat) {

    }

    @Override
    public void deleteChat(IChat activeChat) {

    }

    @Override
    public void saveMessage(IChat activeChat, IMessage msg) {

    }

    @Override
    public Collection<IMessage> getMessages(IChat chat) {
        return new ArrayList<>();
    }
}
