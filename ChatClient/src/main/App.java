package main;

import gui.gui;
import listener.IChatListener;
import listener.IGuiListener;
import model.IChatDb;
import model.IChatManager;
import model.LoadChatThread;
import type.IChat;
import type.ILogger;
import type.IMessage;
import type.ISocketProtocol;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class App implements IGuiListener, IChatListener {
    private final ILogger logger;
    private final ISocketProtocol protocol;
    private final IChatDb db;
    private final IChatManager chatManager;
    private gui gui;
    private LoadChatThread loadChatThread;
    //private Map<Integer, IChat> chatMap;
    // private IChat activeChat;
    // private int chatIndex;


    App(ILogger logger, ISocketProtocol protocol, IChatDb db, IChatManager chatManager) {
        this.logger = logger;
        this.protocol = protocol;
        this.db = db;
        this.chatManager = chatManager;
        //  chatMap = new HashMap<>();

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
            db.saveMessage(chatManager.getActiveChat(), msg);
        }
    }

    /**
     * Send a message to other users
     *
     * @param message - Message
     */
    @Override
    public void sendMessage(IMessage message) {
        IChat activeChat = chatManager.getActiveChat();

        if (activeChat != null) {
            try {
                activeChat.sendToUsers(message);
                db.saveMessage(activeChat, message);
            } catch (IOException e) {
                logger.logError(e);
            }
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
        IChat chat = chatManager.getNewClientChat(logger, protocol, this);
        boolean started = startChat(args, chat);

        if (started) {
            Point pos = gui.addChatToGui(chat.getId(), String.format("%s | %s", ip, port));
            chat.setGuiPosition(pos);
            db.createChat(chat);
            chatManager.addChat(chat);
            chatManager.setActiveChat(chat);
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
            return chat.start(args);
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
        IChat chat = chatManager.getNewServerChat(logger, this, protocol);
        String[] args = {port, String.valueOf(chat.getId())};
        boolean added = startChat(args, chat);

        if (added) {
            Point pos = gui.addChatToGui(chat.getId(), String.format("%s | %s", title, port));
            chat.setGuiPosition(pos);
            db.createChat(chat);
            chatManager.addChat(chat);
            chatManager.setActiveChat(chat);
        }

        return added;
    }

    /**
     * Quit from chat
     */
    @Override
    public void quitChat() {
        IChat activeChat = chatManager.getActiveChat();

        if (activeChat != null) {
            try {
                activeChat.close();
                gui.removeChatFromGui(activeChat.getId());
                db.deleteChat(activeChat);
                chatManager.removeChat(activeChat);
                chatManager.setActiveChat((IChat) null);
            } catch (IOException e) {
                logger.logError(e);
            }
        }
    }

    /**
     * Load new chat data to the gui
     *
     * @param point - Location of chat in window
     */
    @Override
    public void loadChat(Point point) {

        if (!chatManager.isCurrentChat(point)) {
            chatManager.setActiveChat(point);

            if (loadChatThread != null && loadChatThread.isAlive()) {
                loadChatThread.cancel();
            }

            loadChatThread = new LoadChatThread(chatManager, db, gui, point);
            loadChatThread.run();
        }
    }
}
