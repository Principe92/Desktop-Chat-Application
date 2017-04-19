package gui;

import listener.ChatListPanelListener;
import main.Constant;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class ChatListPanel extends JPanel {

    private final ChatListPanelListener listener;
    private JPanel chatListPanel;
    private Map<Integer, String> chatList;

    public ChatListPanel(ChatListPanelListener listener) {
        this.listener = listener;
        this.chatList = new HashMap<>();
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

        // menu
        item = new ImageButton("Menu", "ic_more_horiz_black_24dp.png");
        bar.add(item);

        return bar;
    }

    public Point addChat(Integer id, String title) {
        chatList.put(id, title);
        JButton label = new JButton(title);
        label.setBackground(Constant.CHAT_BG);
        label.setOpaque(true);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setBorder(BorderFactory.createCompoundBorder(label.getBorder(), new EmptyBorder(Constant.MAG_24, Constant.MAG_16, Constant.MAG_24, Constant.MAG_16)));
        label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                listener.loadChat(new Point(e.getComponent().getX(), e.getComponent().getY()));
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
        chatListPanel.add(label, "growx");
        chatListPanel.revalidate();

        return new Point(label.getX(), label.getY());
    }

    public void removeChat(Integer id) {
        chatList.remove(id);
        chatListPanel.remove(id);
        chatListPanel.revalidate();
    }
}
