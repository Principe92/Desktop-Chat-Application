package main;

import gui.gui;
import listener.IChatListener;
import listener.IGuiListener;
import model.Chat;
import type.IChat;
import type.ILogger;
import type.IMessage;
import type.ISocketProtocol;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App implements IGuiListener, IChatListener {
    private final ILogger logger;
    private final ISocketProtocol protocol;
    private gui gui;
    private Map<Integer, IChat> chatMap;
    private IChat activeChat;
    private int chatIndex;


    public App(ILogger logger, ISocketProtocol protocol) {
        this.logger = logger;
        this.protocol = protocol;
        chatMap = new HashMap<>();

        loadUI();
    }

    private void loadUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager
                            .getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                gui = new gui(App.this, logger);
            }
        });
    }

    @Override
    public void printToScreen(IMessage msg) {
        if (msg != null) {
            gui.displayMessage(msg);
        }
    }

    @Override
    public void sendText(IMessage message) throws IOException {
        if (activeChat != null) {
            activeChat.sendToUsers(message);
        }
    }

    @Override
    public boolean joinChat(String ip, String port) {
        String[] args = {ip, port};
        IChat chat = new Chat(chatIndex, logger, protocol, this);
        boolean added = addChat(args, chat);

        if (added) {
            activeChat = chat;
            gui.addChatToList(String.format("%s | %s", ip, port));
        }
        return added;
    }

    private boolean addChat(String[] args, IChat chat) {
        try {
            boolean started = chat.start(args);

            if (started) {
                chatMap.put(chat.getId(), chat);
                chatIndex++;
            }

            return started;
        } catch (IOException e) {
            logger.logError(e);
        }

        return false;
    }

    @Override
    public boolean createChat(String title, String port) {
        String[] args = {port, String.valueOf(chatIndex)};
        IChat chat = new Server(chatIndex, logger, this, protocol);
        boolean added = addChat(args, chat);

        if (added) {
            activeChat = chat;
            gui.addChatToList(title);
        }

        return added;
    }
}
