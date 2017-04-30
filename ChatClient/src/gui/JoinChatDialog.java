package gui;

import listener.JoinChatDialogListener;
import main.Constant;
import main.Util;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by okori on 05-Apr-17.
 */
public class JoinChatDialog extends BaseDialog {
    private final JoinChatDialogListener listener;
    private JTextField ip;
    private JTextField port;

    public JoinChatDialog(JFrame parent, Point point, JoinChatDialogListener listener) {
        super(parent, "ChatApp | Join a chat room");
        this.listener = listener;
        this.setLocation(point);
    }


    @Override
    protected JPanel buildLayout() {
        JPanel panel = new JPanel(new MigLayout("insets 32, nogrid"));

        panel.add(new JLabel(Constant.EMPTY,
                        new ImageIcon(this.getClass().getResource(Util.fillIconPath("join.png"))), JLabel.CENTER),
                "wrap 8px, dock center");
        panel.add(buildIPLabel(), "align label");
        panel.add(buildIPBox(), "wrap 16px, growx, pushx");
        panel.add(buildPortLabel(), "align label");
        panel.add(buildPortBox(), "wrap 16px, growx, pushx");
        panel.add(buildJoinButton(), "dock center, shrink");

        return panel;
    }

    @Override
    protected void onEnterKeyPressed() {
        join();
    }

    private JLabel buildPortLabel() {
        return new JLabel("# Port");
    }

    private JLabel buildIPLabel() {
        return new JLabel("IP address");
    }

    private JButton buildJoinButton() {
        JButton button = new JButton("Join");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                join();
            }
        });
        return button;
    }

    private void join() {
        boolean joined = listener.joinChat(ip.getText(), port.getText());
        if (joined) {
            //  close();
        }
    }

    private JTextField buildIPBox() {
        ip = new JTextField();
        return ip;
    }

    private JTextField buildPortBox() {
        port = new JTextField();
        return port;
    }
}
