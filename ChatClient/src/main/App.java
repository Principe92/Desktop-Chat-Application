package main;

import gui.LoginWindow;
import gui.gui;
import listener.AccountListener;
import listener.IChatListener;
import listener.IGuiListener;
import model.*;
import type.IChat;
import type.ILogger;
import type.IMessage;
import type.ISocketProtocol;

import javax.swing.*;
import java.io.IOException;

public class App implements IGuiListener, IChatListener, AccountListener {

    private final ILogger logger;
    private final ISocketProtocol protocol;
    private final IChatDb db;
    private final IChatManager chatManager;
    private gui gui;
    private LoadChatThread loadChatThread;
    private User who;


    public App(ILogger logger, ISocketProtocol protocol, IChatDb db, IChatManager chatManager) {
        this.logger = logger;
        this.protocol = protocol;
        this.db = db;
        this.chatManager = chatManager;


//        this.who = new User();
//        this.who.setNick(String.format("User: %d", Math.toIntExact(System.nanoTime() % 100)));
//        loadGUI();

        loadAcctGUI();
    }

    /**
     * Start the GUI
     */

    @Override
    public void loginAccepted(User user) {
        this.who = user;
        loadGUI();
    }

    private void loadAcctGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                AccountDB accounts = new AccountDB();
                new LoginWindow(accounts, App.this);
            }
        });
    }

    private void loadGUI() {
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
     * @param msg  - A message
     * @param port
     */
    @Override
    public void printToScreen(IMessage msg, int port) {
        if (msg != null) {
            if (chatManager.belongsToActiveChat(port)) {
                gui.displayMessage(msg, false);
                db.saveMessage(chatManager.getActiveChat(), msg, false);
            } else {
                db.saveMessage(chatManager.getChatByPort(port), msg, false);
            }
        }
    }

    @Override
    public User getUser() {
        return who;
    }

    @Override
    public void onChatStarted(IChat chat) {
        int pos = gui.addChatToGui(chat.getChatId(),
                String.format("%s (Port: %s)", chat.getChatTitle(), chat.getPort()));
        chat.setGuiId(pos);
        gui.closeDialog();
        gui.clearMessageWindow();
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
        IChat activeChat = chatManager.getActiveChat();

        if (activeChat != null) {
            try {
                message.setSender(who.getNameOrNick());
                activeChat.sendToUsers(message);
                db.saveMessage(activeChat, message, true);
            } catch (IOException e) {
                logger.logError(e);
            }
        } else {
            gui.alert("No chat available");
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
                gui.clearMessageWindow();
                db.deleteChat(activeChat);
                chatManager.removeChat(activeChat);
                IChat next = chatManager.setNextChat();

                if (next != null) {
                    loadChat(next.getGuiId());
                }
            } catch (IOException e) {
                logger.logError(e);
            }
        }
    }

    @Override
    public void onServerClosed() {
        IChat activeChat = chatManager.getActiveChat();

        if (activeChat != null) {
            gui.alert(String.format("Chat Room: %s has closed", activeChat.getChatTitle()));
            gui.removeChatFromGui(activeChat);
            gui.clearMessageWindow();
            db.deleteChat(activeChat);
            chatManager.removeChat(activeChat);
            IChat next = chatManager.setNextChat();

            if (next != null) {
                loadChat(next.getGuiId());
            }
        }
    }

    @Override
    public void loadChat(int guiId) {
        if (!chatManager.isCurrentChat(guiId)) {
            chatManager.setActiveChat(guiId);

            if (loadChatThread != null && loadChatThread.isAlive()) {
                loadChatThread.cancel();
            }

            loadChatThread = new LoadChatThread(chatManager, db, gui, guiId, logger);
            loadChatThread.run();
        }
    }

    @Override
    public boolean IsChatAvailable() {
        return chatManager.IsChatAvailable();
    }
}
