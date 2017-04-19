package gui;

import factory.MessageFactory;
import listener.IGuiListener;
import main.Constant;
import model.ImageFilter;
import net.miginfocom.swing.MigLayout;
import type.ILogger;
import type.IMessage;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")
public class ChatPanel extends JPanel {
    public static final int MIN_WIDTH = 600;
    private final IGuiListener listener;
    private final ILogger logger;
    private JTextArea msgBox;
    private int xcord;
    private int ycord;
    private JPanel msgWindow;

    public ChatPanel(IGuiListener listener, ILogger logger) {
        this.listener = listener;
        this.logger = logger;
        this.setLayout(new MigLayout("insets 0 0 4 0"));

        this.add(addMenuBar(), "growx, pushx, wrap");
        this.add(addMessageWindow(), "push, grow, wrap");
        this.add(addMessageField(), "growx, pushx, split");
        this.add(addSendButton());

    }

    private JMenuBar addMenuBar() {
        JMenuBar bar = new ChatMenuBar(Constant.MENU_BG);

        // exit
        JButton item = new ImageButton("Exit", "ic_power_settings_new_black_24dp.png");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.quitChat();
            }
        });
        bar.add(item);

        item = new ImageButton("Menu", "ic_more_horiz_black_24dp.png");
        bar.add(item);

        return bar;
    }

    private JButton addSendButton() {
        JButton btn = new ImageButton("Attach a file", "ic_attachment_black_24dp.png");
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JButton open = new JButton();
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
                        fileChooser.setDialogTitle("Attach an image");
                        fileChooser.addChoosableFileFilter(new ImageFilter());
                        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        if (fileChooser.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
                            msgBox.setText(fileChooser.getSelectedFile().getAbsolutePath());
                            setFocusToChatWindow();
                        }
                    }
                }).start();
            }
        });
        return btn;
    }

    private JScrollPane addMessageWindow() {
        msgWindow = new JPanel();
        msgWindow.setLayout(new MigLayout("fillx", "[grow]"));
        msgWindow.setBackground(Constant.MSG_BG);
        msgWindow.setBorder(new CompoundBorder(msgWindow.getBorder(), new EmptyBorder(Constant.MAG_16, Constant.MAG_16, Constant.MAG_16, Constant.MAG_16)));

        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(msgWindow);

        return scroll;
    }

    private JTextArea addMessageField() {
        msgBox = new JTextArea();
        msgBox.setLineWrap(true);
        msgBox.setWrapStyleWord(true);
        msgBox.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String text = msgBox.getText();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sendText(text);
                        }
                    }).start();

                    clearText();
                    setFocusToChatWindow();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

        return msgBox;
    }

    private void sendText(String text) {

        if (text.isEmpty() || text.trim().isEmpty()) return;

        IMessage message = MessageFactory.getMessage(text.trim());

        if (message != null) {
            displayMessage(message, Constant.DOCK_EAST, Constant.USER_BG);
            listener.sendMessage(message);
        }
    }

    private void clearText() {
        msgBox.setText(null);
    }

    public void displayMessage(IMessage msg, String alignment, Color color) {
        JPanel panel = new JPanel(new MigLayout("fill", "[grow]", "[]"));
        panel.add(msg.getMessagePanel(color), alignment);
        panel.setOpaque(false);
        msgWindow.add(panel, "wrap, spanx, growx");

        msgWindow.revalidate();
        ycord++;
    }

    public void setFocusToChatWindow() {
        msgBox.requestFocus();
    }

    public void clearMessageWindow() {
        msgWindow.removeAll();
    }
}
