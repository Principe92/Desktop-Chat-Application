package main;

import gui.ChatListPanel;
import gui.ChatPanel;
import interfaces.IGuiListener;
import type.ILogger;
import type.IMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class gui {
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

        chatListPanel = new ChatListPanel();
        chatListPanel.setBackground(Color.blue);
        chatListPanel.setSize(new Dimension(ChatListPanel.MIN_WIDTH, Constant.MIN_HEIGHT));

        chatPanel = new ChatPanel(listener, logger);
        chatPanel.setBackground(Color.CYAN);
        //  chatPanel.setSize(new Dimension(ChatPanel.MIN_WIDTH, Constant.MIN_HEIGHT));

        frmChatApp = setUpChatFrame();

        a.fill = GridBagConstraints.BOTH;
        a.gridx = 0;
        a.gridy = 0;
        a.weighty = 1;
        a.weightx = 0.3;

        b.fill = GridBagConstraints.BOTH;
        b.weightx = 0.7;
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

    void displayMessage(IMessage msg) {
        chatPanel.displayMessage(msg, GridBagConstraints.NORTHWEST);
    }

    void close() {
        frmChatApp.dispatchEvent(new WindowEvent(frmChatApp, WindowEvent.WINDOW_CLOSING));
    }
}
