package gui;

import listener.NewChatDialogListener;
import main.Constant;
import main.Util;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by okori on 06-Apr-17.
 */
public class NewChatDialog extends BaseDialog {
    private final NewChatDialogListener listener;
    private JTextField port;
    private JTextField title;

    public NewChatDialog(JFrame parent, Point point, NewChatDialogListener listener) {
        super(parent, "ChatApp | Create a chat room");
        this.listener = listener;
        this.setLocation(point);
    }


    @Override
    protected JPanel buildLayout() {
        JPanel panel = new JPanel(new MigLayout("insets 32, nogrid"));

        panel.add(new JLabel(Constant.EMPTY,
                        new ImageIcon(this.getClass().getResource(Util.fillIconPath("join.png"))), JLabel.CENTER),
                "wrap 8px, dock center");
        panel.add(buildTitleLabel(), "align label");
        panel.add(buildTitleBox(), "wrap 16px, growx, pushx");
        panel.add(buildPortLabel(), "align label");
        panel.add(buildPortBox(), "wrap 16px, growx, pushx");
        panel.add(buildJoinButton(), "dock center, shrink");

        return panel;
    }

    @Override
    protected void onEnterKeyPressed() {
        create();
    }

    private JLabel buildPortLabel() {
        return new JLabel("# Port");
    }

    private JLabel buildTitleLabel() {
        return new JLabel("Title");
    }

    private JButton buildJoinButton() {
        JButton button = new JButton("Create");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                create();
            }
        });
        return button;
    }

    private void create() {
        boolean created = listener.createChat(title.getText(), port.getText());

        if (created) {
            // close();
        }
    }

    private JTextField buildTitleBox() {
        title = new JTextField();
        return title;
    }

    private JTextField buildPortBox() {
        port = new JTextField();
        return port;
    }
}
