package gui;

import factory.MessageFactory;
import listener.IGuiListener;
import main.Constant;
import main.Util;
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
import java.io.IOException;

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
        this.setLayout(new GridBagLayout());

        GridBagConstraints a = new GridBagConstraints();
        GridBagConstraints b = new GridBagConstraints();
        GridBagConstraints c = new GridBagConstraints();

        // menu bar
        a.fill = GridBagConstraints.HORIZONTAL;
        this.add(addMenuBar(), a);

        // message window
        b.weightx = 1;
        b.weighty = 1;
        b.gridx = 0;
        b.gridy = 1;
        b.fill = GridBagConstraints.BOTH;
        this.add(addMessageWindow(), b);

        // base panel
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(addBasePanel(), c);

    }

    private JPanel addBasePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints a = new GridBagConstraints();
        a.weightx = 1;
        a.fill = GridBagConstraints.HORIZONTAL;

        msgBox = addMessageField();
        panel.add(msgBox, a);
        panel.add(addSendButton());

        return panel;
    }

    private JMenuBar addMenuBar() {
        JMenuBar bar = new JMenuBar();
        bar.add(Box.createHorizontalGlue());
        bar.setBorder(BorderFactory.createCompoundBorder(bar.getBorder(), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        bar.setBackground(Color.LIGHT_GRAY);


        // exit
        JButton item = new ImageButton("Exit", "ic_power_settings_new_black_24dp.png");
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
        msgWindow.setLayout(new MigLayout("fillx"));
        msgWindow.setBackground(Constant.MSG_BG);
        msgWindow.setBorder(new CompoundBorder(msgWindow.getBorder(), new EmptyBorder(Constant.MAG_16, Constant.MAG_16, Constant.MAG_16, Constant.MAG_16)));

        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(msgWindow);

        return scroll;
    }

    private JTextArea addMessageField() {
        JTextArea textArea = new JTextArea();
        textArea.setColumns(10);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.addKeyListener(new KeyListener() {

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

        return textArea;
    }

    private void sendText(String text) {

        if (text.isEmpty() || text.trim().isEmpty()) return;

        IMessage message = MessageFactory.getMessage(text.trim());

        if (message != null) {
            try {
                listener.sendText(message);
            } catch (IOException e) {
                logger.logError(e);
            }

            displayMessage(message, GridBagConstraints.NORTHEAST, Constant.USER_BG);
        }
    }

    private void clearText() {
        msgBox.setText(null);
    }

    public void displayMessage(IMessage msg, int alignment, Color color) {
        GridBagConstraints gs = Util.getNoFill(alignment);
        gs.insets = new Insets(Constant.MAG_2, Constant.MAG_16, Constant.MAG_2, Constant.MAG_16);
        gs.gridx = xcord;
        gs.gridy = ycord;
        gs.weightx = 1;
        //gs.weighty = 0.01;

        String align = ycord % 2 == 0 ? "wrap, dock east" : "wrap, dock west";
        Component ct = msg.getMessagePanel(alignment, color);
        msgWindow.add(ct, "wrap");

        gs.weightx = 1;
        gs.weighty = 1;
        //  msgWindow.add(Box.createVerticalStrut(2), gs);
        msgWindow.revalidate();
        ycord++;
    }

    public void setFocusToChatWindow() {
        msgBox.requestFocus();
    }
}
