package model;

import com.sun.istack.internal.Nullable;
import listener.IChatListener;
import main.Server;
import type.IChat;
import type.ILogger;
import type.ISocketProtocol;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


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
    public void setActiveChat(IChat chat) {
        activeChat = chat;
    }

    @Override
    public void setActiveChat(int guiId) {
        activeChat = getChat(guiId);
    }

    @Override
    public void removeChat(IChat activeChat) {
        chatMap.remove(activeChat.getChatId());
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
        chatMap.put(chat.getChatId(), chat);
        chatIndex++;
    }

    @Override
    public @Nullable
    IChat getChat(int guiId) {

        for (Map.Entry<Integer, IChat> entry : chatMap.entrySet()) {
            int pt = entry.getValue().getGuiId();
            if (pt == guiId) {
                return entry.getValue();
            }
        }

        return null;
    }

    @Override
    public boolean isCurrentChat(int hash) {
        int activeChatGuiId = activeChat.getGuiId();
        return activeChatGuiId == hash;
    }

    @Override
    public boolean chatExists(String port) {
        for (Map.Entry<Integer, IChat> entry : chatMap.entrySet()) {
            int pt = entry.getValue().getPort();
            if (pt == Integer.parseInt(port)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public IChat getChatByPort(int port) {
        for (Map.Entry<Integer, IChat> entry : chatMap.entrySet()) {
            int pt = entry.getValue().getPort();
            if (pt == port) {
                return entry.getValue();
            }
        }

        return null;
    }

    @Override
    public boolean belongsToActiveChat(int port) {
        return activeChat != null && port == activeChat.getPort();
    }

    @Override
    public boolean IsChatAvailable() {
        return activeChat != null;
    }

    @Override
    public IChat setNextChat() {
        if (chatMap.isEmpty()) activeChat = null;
        else {
            Iterator<Map.Entry<Integer, IChat>> it = chatMap.entrySet().iterator();
            activeChat = it.next().getValue();
        }
        return activeChat;
    }
}
