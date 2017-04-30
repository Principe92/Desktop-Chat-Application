package gui;

import listener.ChatListPanelListener;
import listener.IGuiListener;
import main.Constant;
import net.miginfocom.swing.MigLayout;
import type.IChat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class ChatListPanel extends JPanel {

    private final ChatListPanelListener listener;
    private final IGuiListener guiListener;
    private JPanel chatListPanel;
    private JButton activeChat;

    public ChatListPanel(ChatListPanelListener listener, IGuiListener iGuiListener) {
        this.listener = listener;
        guiListener = iGuiListener;
        this.setLayout(new MigLayout("fill, insets 0 0 4 0, wrap 1"));

        this.add(addMenuBar(), "growx");
        this.add(addChatList(), "grow, push");
    }

    private JPanel addChatList() {
        chatListPanel = new JPanel(new MigLayout("fillx, wrap 1, inset 0"));
        chatListPanel.setBackground(Constant.CHAT_LIST_BG);
        return chatListPanel;
    }

    private JMenuBar addMenuBar() {
        JMenuBar bar = new ChatMenuBar(Constant.MENU_BG);

        // join chat room
        JButton item = new ImageButton("Join a chat", "ic_group_add_black_24dp.png");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                listener.joinChatRoom();
            }
        });
        bar.add(item);

        // settings
        item = new ImageButton("New chat", "ic_add_black_24dp.png");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.createChatRoom();
            }
        });
        bar.add(item);

        return bar;
    }

    public int addChat(Integer id, String title) {
        JButton label = new JButton(title);
        label.setUI((ButtonUI) BasicButtonUI.createUI(label));
        label.setBackground(Constant.ACTIVE_CHAT);
        label.setOpaque(true);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setBorder(BorderFactory.createCompoundBorder(label.getBorder(),
                new EmptyBorder(Constant.MAG_24, Constant.MAG_16, Constant.MAG_24, Constant.MAG_16)));
        label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                guiListener.loadChat(e.getComponent().hashCode());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        changeActiveChat(label);
        chatListPanel.add(label, "growx");
        chatListPanel.revalidate();

        return label.hashCode();
    }

    public void removeChat(IChat chat) {
        int index = getChatGuiPosition(chat);

        if (index >= 0) {
            chatListPanel.remove(index);
            chatListPanel.revalidate();
            chatListPanel.repaint();
        }
    }

    private int getChatGuiPosition(IChat chat) {
        int size = chatListPanel.getComponentCount() - 1;

        while (size >= 0) {
            if (chatListPanel.getComponent(size).hashCode() == chat.getGuiId()) {
                break;
            }

            size--;
        }

        return size;
    }

    public void changeChatTitle(String title, Point position) {
        JButton chat = (JButton) chatListPanel.getComponentAt(position);
        chat.setText(title);
    }

    public void setActive(IChat chat) {
        if (chat == null) return;

        int index = getChatGuiPosition(chat);

        if (index >= 0) {
            JButton gui = (JButton) chatListPanel.getComponent(index);
            gui.setBackground(Constant.ACTIVE_CHAT);

            changeActiveChat(gui);
        }
    }

    private void changeActiveChat(JButton gui) {
        if (activeChat != null) {
            activeChat.setBackground(Constant.CHAT_BG);
        }

        activeChat = gui;
    }
}
