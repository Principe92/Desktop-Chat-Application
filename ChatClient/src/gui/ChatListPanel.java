package gui;

import listener.ChatListPanelListener;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class ChatListPanel extends JPanel {

    public static final int MIN_WIDTH = 500;
    private final ChatListPanelListener listener;
    private JPanel chatList;

    public ChatListPanel(ChatListPanelListener listener) {
        this.listener = listener;
        this.setLayout(new GridBagLayout());

        GridBagConstraints a = new GridBagConstraints();
        GridBagConstraints b = new GridBagConstraints();

        a.fill = GridBagConstraints.HORIZONTAL;
        a.gridx = 0;
        a.gridy = 0;
        a.anchor = GridBagConstraints.NORTH;
        this.add(addMenuBar(), a);

        b.fill = GridBagConstraints.BOTH;
        b.gridx = 0;
        b.gridy = 1;
        b.weighty = 1;
        this.add(addChatList(), b);
    }

    private JPanel addChatList() {
        chatList = new JPanel(new MigLayout("fillx"));
        return chatList;
    }

    private JMenuBar addMenuBar() {
        JMenuBar bar = new JMenuBar();
        bar.add(Box.createHorizontalGlue());
        bar.setBorder(BorderFactory.createCompoundBorder(bar.getBorder(), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        bar.setBackground(Color.LIGHT_GRAY);

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

    public void addChat(String title) {
        JLabel label = new JLabel(title);
        chatList.add(label, "spanx, wrap");

        chatList.revalidate();
    }
}
