package model;

import com.sun.istack.internal.Nullable;
import listener.IChatListener;
import main.Server;
import type.IChat;
import type.ILogger;
import type.ISocketProtocol;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by okori on 19-Apr-17.
 */
public class ChatManager implements IChatManager {
    private static ChatManager manager;

    private Map<Integer, IChat> chatMap;
    private IChat activeChat;
    private int chatIndex;

    ChatManager() {
        chatMap = new HashMap<>();
    }

    public static IChatManager instance() {
        return manager != null ? manager : (manager = new ChatManager());
    }

    @Override
    public IChat getActiveChat() {
        return activeChat;
    }

    @Override
    public void setActiveChat(Point point) {
        activeChat = getChat(point);
    }

    @Override
    public void setActiveChat(IChat chat) {
        activeChat = chat;
    }

    @Override
    public void removeChat(IChat activeChat) {
        chatMap.remove(activeChat.getId());
    }

    @Override
    public IChat getNewClientChat(ILogger logger, ISocketProtocol protocol, IChatListener listener) {
        return new Chat(chatIndex, logger, protocol, listener);
    }

    @Override
    public IChat getNewServerChat(ILogger logger, IChatListener listener, ISocketProtocol protocol) {
        return new Server(chatIndex, logger, listener, protocol);
    }

    @Override
    public void addChat(IChat chat) {
        chatMap.put(chat.getId(), chat);
        chatIndex++;
    }

    @Override
    public @Nullable
    IChat getChat(Point point) {

        for (Map.Entry<Integer, IChat> entry : chatMap.entrySet()) {
            Point pt = entry.getValue().getPosition();
            if (pt.getY() == point.getY()) {
                return entry.getValue();
            }
        }

        return null;
    }

    @Override
    public boolean isCurrentChat(Point point) {
        Point activeChatPosition = activeChat.getPosition();
        return activeChatPosition.getX() == point.getX() && activeChatPosition.getY() == activeChatPosition.getY();
    }
}
