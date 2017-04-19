package model;

import type.IChat;
import type.IMessage;

import java.util.Collection;

/**
 * Created by okori on 19-Apr-17.
 */
public interface IChatDb {

    void createChat(IChat chat);

    void deleteChat(IChat activeChat);

    void saveMessage(IChat activeChat, IMessage msg);

    Collection<IMessage> getMessages(IChat chat);
}
