package gui;

import listener.ChatListPanelListener;
import listener.IGuiListener;
import listener.JoinChatDialogListener;
import listener.NewChatDialogListener;
import main.Constant;
import type.ILogger;
import type.IMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class gui implements ChatListPanelListener {
    private final IGuiListener listener;
    private final ILogger logger;
    private JFrame frmChatApp;
    private ChatListPanel chatListPanel;
    private ChatPanel chatPanel;

    public gui(IGuiListener listener, ILogger logger) {
        this.listener = listener;
        this.logger = logger;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */

    private void initialize() {
        GridBagConstraints a = new GridBagConstraints();
        GridBagConstraints b = new GridBagConstraints();

        chatListPanel = new ChatListPanel(this);
        chatPanel = new ChatPanel(listener, logger);

        frmChatApp = setUpChatFrame();

        a.fill = GridBagConstraints.BOTH;
        a.gridx = 0;
        a.gridy = 0;
        a.weighty = 1;
        a.weightx = 0.4;

        b.fill = GridBagConstraints.BOTH;
        b.weightx = 0.6;
        b.weighty = 1;
        b.gridx = 1;
        b.gridy = 0;


        frmChatApp.getContentPane().add(chatListPanel, a);
        frmChatApp.getContentPane().add(chatPanel, b);
        frmChatApp.pack();
        frmChatApp.setVisible(true);
    }

    private JFrame setUpChatFrame() {
        JFrame frmChatApp = new JFrame();
        frmChatApp.setTitle("ChatApp");
        frmChatApp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frmChatApp.getContentPane().setLayout(new GridBagLayout());

        frmChatApp.setMinimumSize(new Dimension(Constant.MIN_WIDTH, Constant.MIN_HEIGHT));
        frmChatApp.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());

        // make the message box get focus
        frmChatApp.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                chatPanel.setFocusToChatWindow();
            }
        });

        return frmChatApp;
    }

    public void displayMessage(IMessage msg) {
        chatPanel.displayMessage(msg, GridBagConstraints.NORTHWEST, Constant.OTHERS_BG);
    }

    void close() {
        frmChatApp.dispatchEvent(new WindowEvent(frmChatApp, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void joinChatRoom() {
        new JoinChatDialog(frmChatApp, new JoinChatDialogListener() {
            @Override
            public boolean joinChat(String ip, String port) {
                if (!ip.isEmpty() && !port.isEmpty()) {
                    return listener.joinChat(ip, port);
                }

                return false;
            }
        });
    }

    @Override
    public void createChatRoom() {
        new NewChatDialog(frmChatApp, new NewChatDialogListener() {
            @Override
            public boolean createChat(String title, String port) {
                if (!title.isEmpty() && !port.isEmpty()) {
                    return listener.createChat(title, port);
                }

                return false;
            }
        });
    }

    public void addChatToList(String title) {
        chatListPanel.addChat(title);
    }
}
