package main;

import gui.gui;
import listener.IChatListener;
import listener.IGuiListener;
import model.IChatDb;
import model.IChatManager;
import model.LoadChatThread;
import model.User;
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
    private User user;


    App(ILogger logger, ISocketProtocol protocol, IChatDb db, IChatManager chatManager) {
        this.logger = logger;
        this.protocol = protocol;
        this.db = db;
        this.chatManager = chatManager;

        this.user = new User(Math.toIntExact(System.nanoTime() % 100));
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
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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

    @Override
    public void changeChatTitle(String title, Point position) {
        gui.changeChatTitle(title, position);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void onChatStarted(IChat chat) {
        Point pos = gui.addChatToGui(chat.getChatId(), String.format("%s | %s", chat.getChatTitle(), chat.getPort()));
        chat.setGuiPosition(pos);
        gui.closeDialog();
        db.createChat(chat);
        chatManager.addChat(chat);
        chatManager.setActiveChat(chat);
    }

    /**
     * Send a message to other users
     *
     * @param message - Message
     */
    @Override
    public void sendMessage(IMessage message) {
        message.setSender(user.getName());
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

        if (!chatManager.chatExists(port)) {

            String[] args = {ip, port};
            IChat chat = chatManager.getNewClientChat(logger, protocol, this);
            boolean started = false;
            try {
                started = chat.start(args);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return started;
        } else {
            gui.alert("Chat already exists");
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
        if (!chatManager.chatExists(port)) {

            IChat chat = chatManager.getNewServerChat(logger, this, protocol);
            chat.setChatTitle(title);
            String[] args = {port, String.valueOf(chat.getChatId())};
            boolean started = false;
            try {
                started = chat.start(args);

            } catch (IOException e) {
                logger.logError(e);
            }


            return started;
        } else {
            gui.alert("Chat already exists");
        }

        return false;
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
                gui.removeChatFromGui(activeChat);
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
