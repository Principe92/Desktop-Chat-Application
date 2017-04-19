package gui;

import listener.ChatListPanelListener;
import listener.IGuiListener;
import listener.JoinChatDialogListener;
import listener.NewChatDialogListener;
import main.Constant;
import net.miginfocom.swing.MigLayout;
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


        frmChatApp.getContentPane().add(chatListPanel, "grow");
        frmChatApp.getContentPane().add(chatPanel, "grow");
        frmChatApp.pack();
        frmChatApp.setVisible(true);
    }

    private JFrame setUpChatFrame() {
        JFrame frmChatApp = new JFrame();
        frmChatApp.setTitle("ChatApp");
        frmChatApp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frmChatApp.getContentPane().setLayout(new MigLayout("fill, insets 0", "[grow 25][grow 75]"));
        frmChatApp.setMinimumSize(new Dimension(Constant.MIN_WIDTH, Constant.MIN_HEIGHT));

        // make the message box get focus
        frmChatApp.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                chatPanel.setFocusToChatWindow();
            }
        });

        return frmChatApp;
    }

    public void displayMessage(IMessage msg) {
        chatPanel.displayMessage(msg, Constant.DOCK_WEST, Constant.OTHERS_BG);
    }

    void close() {
        frmChatApp.dispatchEvent(new WindowEvent(frmChatApp, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Open a dialog to join a chat
     */
    @Override
    public void joinChatRoom() {
        new JoinChatDialog(frmChatApp, new Point(frmChatApp.getX(), frmChatApp.getY()), new JoinChatDialogListener() {
            @Override
            public boolean joinChat(String ip, String port) {
                if (!ip.isEmpty() && !port.isEmpty()) {
                    return listener.joinChat(ip, port);
                }

                return false;
            }
        });
    }

    /**
     * Open dialog to create a chat
     */
    @Override
    public void createChatRoom() {
        new NewChatDialog(frmChatApp, new Point(frmChatApp.getX(), frmChatApp.getY()), new NewChatDialogListener() {
            @Override
            public boolean createChat(String title, String port) {
                if (!title.isEmpty() && !port.isEmpty()) {
                    return listener.createChat(title, port);
                }

                return false;
            }
        });
    }

    @Override
    public void loadChat(Point point) {
        listener.loadChat(point);
    }

    public Point addChatToGui(Integer id, String title) {
        return chatListPanel.addChat(id, title);
    }

    public void removeChatFromGui(Integer id) {
        chatListPanel.removeChat(id);
    }

    public void clearMessageWindow() {
        chatPanel.clearMessageWindow();
    }
}
