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


    App(ILogger logger, ISocketProtocol protocol) {
        this.logger = logger;
        this.protocol = protocol;
        chatMap = new HashMap<>();

        loadUI();
    }

    /**
     * Start the GUI
     */
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

    /**
     * Display an incoming message to this user's screen
     *
     * @param msg - A message
     */
    @Override
    public void printToScreen(IMessage msg) {
        if (msg != null) {
            gui.displayMessage(msg);
        }
    }

    /**
     * Send a message to other users
     *
     * @param message - Message
     * @throws IOException - Throws an exception on error
     */
    @Override
    public void sendMessage(IMessage message) throws IOException {
        if (activeChat != null) {
            activeChat.sendToUsers(message);
        }
    }

    /**
     * Create chat as a client and request server to join
     *
     * @param ip   - Server IP
     * @param port - Server Port
     * @return - True if joined.
     */
    @Override
    public boolean joinChat(String ip, String port) {
        String[] args = {ip, port};
        IChat chat = new Chat(chatIndex, logger, protocol, this);
        boolean started = startChat(args, chat);

        if (started) {
            activeChat = chat;
            gui.addChatToList(String.format("%s | %s", ip, port));
        }
        return started;
    }

    /**
     * Start new chat and add to the list of chats
     *
     * @param args - Parameters
     * @param chat - Chat
     * @return True if chat was started
     */
    private boolean startChat(String[] args, IChat chat) {
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

    /**
     * Create a new chat as a server
     *
     * @param title - Chat title
     * @param port  - Server port
     * @return True, if success.
     */
    @Override
    public boolean createChat(String title, String port) {
        String[] args = {port, String.valueOf(chatIndex)};
        IChat chat = new Server(chatIndex, logger, this, protocol);
        boolean added = startChat(args, chat);

        if (added) {
            activeChat = chat;
            gui.addChatToList(title);
        }

        return added;
    }
}
